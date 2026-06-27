package com.fxy.xixin.exam.mq;

import com.fxy.xixin.common.mq.DbWriteEvent;
import com.fxy.xixin.common.mq.RabbitMQConfig;
import com.fxy.xixin.exam.entity.Appointment;
import com.fxy.xixin.exam.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 预约相关 MQ 消费者
 * <p>
 * 从 RabbitMQ 缓冲队列中读取预约变更事件，异步写入数据库。
 * 利用 MQ 的持久化机制实现写缓冲：突发流量时消息在队列中排队，
 * 消费者按自身处理能力依次消费，防止数据库被打满。
 * </p>
 *
 * <p>缓冲流程：</p>
 * <ol>
 *   <li>Controller 接收到创建预约请求</li>
 *   <li>将预约数据包装为 DbWriteEvent 发到 MQ（立即返回）</li>
 *   <li>此消费者从队列取出事件，执行实际 DB 写入</li>
 *   <li>如果 DB 短暂不可用，消息留在队列中，恢复后继续消费</li>
 * </ol>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = RabbitMQConfig.QUEUE_DB_WRITE)
public class AppointmentEventConsumer {

    private final AppointmentService appointmentService;

    @RabbitHandler
    public void handleDbWriteEvent(DbWriteEvent event) {
        if (!"appointment".equals(event.getEntityType())) {
            return; // 非预约事件，跳过
        }

        log.info("消费预约MQ消息: action={}, data={}", event.getAction(), event.getData());

        try {
            switch (event.getAction()) {
                case CREATE -> {
                    Appointment appointment = convertToEntity(event.getData());
                    appointmentService.save(appointment);
                    log.info("预约创建成功: id={}", appointment.getId());
                }
                case UPDATE -> {
                    updateAppointment(event.getData());
                }
                case DELETE -> {
                    Long id = convertToId(event.getData());
                    appointmentService.removeById(id);
                    log.info("预约删除成功: id={}", id);
                }
            }
        } catch (Exception e) {
            log.error("消费预约MQ消息失败: entityType={}, action={}", event.getEntityType(), event.getAction(), e);
            // 抛出异常让 MQ 重试（重试机制由 spring-rabbit 的 RetryTemplate 控制）
            throw new RuntimeException("预约DB写入失败: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private void updateAppointment(Object data) {
        java.util.LinkedHashMap<String, Object> map = (java.util.LinkedHashMap<String, Object>) data;
        Long id = toLong(map.get("id"));
        if (id == null) {
            log.warn("更新预约失败: data中缺少id");
            return;
        }
        Object status = map.get("status");
        if (status != null) {
            appointmentService.lambdaUpdate()
                    .eq(Appointment::getId, id)
                    .set(Appointment::getStatus, ((Number) status).intValue())
                    .update();
            log.info("预约更新成功: id={}, status={}", id, status);
        }
    }

    @SuppressWarnings("unchecked")
    private Appointment convertToEntity(Object data) {
        java.util.LinkedHashMap<String, Object> map = (java.util.LinkedHashMap<String, Object>) data;
        Appointment appointment = new Appointment();
        appointment.setId(toLong(map.get("id")));
        appointment.setUserId(toLong(map.get("userId")));
        appointment.setPackageId(toLong(map.get("packageId")));
        appointment.setInstitutionId(toLong(map.get("institutionId")));
        appointment.setDoctorId(toLong(map.get("doctorId")));
        Object dateObj = map.get("appointmentDate");
        if (dateObj != null) {
            appointment.setAppointmentDate(java.time.LocalDate.parse(dateObj.toString()));
        }
        appointment.setTimeSlot((String) map.get("timeSlot"));
        appointment.setStatus(map.get("status") != null ? ((Number) map.get("status")).intValue() : 1);
        appointment.setRemark((String) map.get("remark"));
        return appointment;
    }

    private Long toLong(Object value) {
        if (value == null) return null;
        if (value instanceof Number n) return n.longValue();
        return Long.valueOf(value.toString());
    }

    private Long convertToId(Object data) {
        if (data instanceof Number n) return n.longValue();
        if (data instanceof String s) return Long.valueOf(s);
        @SuppressWarnings("unchecked")
        java.util.LinkedHashMap<String, Object> map = (java.util.LinkedHashMap<String, Object>) data;
        return toLong(map.get("id"));
    }
}