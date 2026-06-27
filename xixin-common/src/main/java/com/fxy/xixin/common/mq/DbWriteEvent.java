package com.fxy.xixin.common.mq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据库写入事件
 * <p>
 * 封装需要写入数据库的业务数据，通过 RabbitMQ 传输。
 * 生产者（Controller/Service）将变更操作发布到 MQ，
 * 消费者（异步 Listener）从队列读取并执行实际的 DB 写入。
 * 利用 MQ 的持久化机制实现写入缓冲，防止突发流量打垮数据库。
 * </p>
 *
 * <p>使用示例：</p>
 * <pre>
 * // 预约创建
 * DbWriteEvent event = DbWriteEvent.builder()
 *     .entityType("appointment")
 *     .action(Action.CREATE)
 *     .data(appointment)
 *     .operatorId(userId)
 *     .build();
 * messageSender.sendDbWriteEvent(event);
 * </pre>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DbWriteEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 操作类型
     */
    public enum Action {
        CREATE, UPDATE, DELETE
    }

    /**
     * 实体类型标识（如 appointment, user, report）
     */
    private String entityType;

    /**
     * 数据库操作类型
     */
    private Action action;

    /**
     * 业务数据（JSON 序列化后的实体数据）
     * 消费者据此执行实际的 DB 写入
     */
    private Object data;

    /**
     * 操作人用户ID
     */
    private Long operatorId;

    /**
     * 事件产生时间
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}