package com.fxy.xixin.common.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 消息发送器
 * <p>
 * 封装 RabbitMQ 消息发布操作，业务层通过此工具类发送消息。
 * 仅在 {@code xixin.rabbitmq.enabled=true} 时生效。
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "xixin.rabbitmq.enabled", havingValue = "true")
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送数据库写入事件到 MQ 缓冲队列
     * <p>
     * 业务层将 DB 操作封装为 {@link DbWriteEvent} 发送到 MQ，
     * 由异步消费者执行实际写入。调用方立即返回，不阻塞。
     * </p>
     *
     * @param event 数据库写入事件
     */
    public void sendDbWriteEvent(DbWriteEvent event) {
        String routingKey = RabbitMQConfig.ROUTING_KEY_PREFIX + event.getEntityType();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DB_WRITE, routingKey, event);
        log.info("MQ消息已发送: entityType={}, action={}, routingKey={}",
                event.getEntityType(), event.getAction(), routingKey);
    }
}