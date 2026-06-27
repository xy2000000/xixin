package com.fxy.xixin.common.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 消息队列配置
 * <p>
 * 提供 DB 写入缓冲的消息队列基础设施。
 * 核心思路：业务操作先发消息到 MQ，再由消费者异步写入 DB，
 * 利用 MQ 的持久化机制实现写入缓冲和削峰填谷。
 * </p>
 *
 * <p><b>启用方式</b>：在 application.yml 中配置：</p>
 * <pre>
 * xixin.rabbitmq.enabled: true
 * spring.rabbitmq.host: 127.0.0.1
 * spring.rabbitmq.port: 5672
 * spring.rabbitmq.username: guest
 * spring.rabbitmq.password: guest
 * </pre>
 *
 * <p><b>交换机/队列说明</b>：</p>
 * <ul>
 *   <li>{@link #EXCHANGE_DB_WRITE} - Topic 交换机，按实体类型路由</li>
 *   <li>{@link #QUEUE_DB_WRITE} - 持久队列，DB 写入请求由此消费</li>
 *   <li>Routing key 格式：{@code xixin.db.write.实体名}（如 xixin.db.write.appointment）</li>
 * </ul>
 */
@Configuration
@EnableRabbit
@ConditionalOnProperty(value = "xixin.rabbitmq.enabled", havingValue = "true")
public class RabbitMQConfig {

    public static final String EXCHANGE_DB_WRITE = "xixin.db.write";
    public static final String QUEUE_DB_WRITE = "xixin.db.write.queue";
    public static final String ROUTING_KEY_PREFIX = "xixin.db.write.";

    @Bean
    public TopicExchange dbWriteExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_DB_WRITE).durable(true).build();
    }

    @Bean
    public Queue dbWriteQueue() {
        return QueueBuilder.durable(QUEUE_DB_WRITE).build();
    }

    @Bean
    public Binding dbWriteBinding() {
        return BindingBuilder.bind(dbWriteQueue())
                .to(dbWriteExchange())
                .with(ROUTING_KEY_PREFIX + "*");
    }

    /**
     * 配置 JSON 消息转换器，使 RabbitMQ 消息以 JSON 格式序列化/反序列化。
     */
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 配置 RabbitTemplate 使用 JSON 转换器。
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

    /**
     * 配置监听器容器工厂，使用 JSON 反序列化。
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter converter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);
        // 预取 10 条，提高消费吞吐
        factory.setPrefetchCount(10);
        return factory;
    }
}