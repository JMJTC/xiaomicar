package com.example.xiaomicar.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String WARNING_EXCHANGE = "warning.exchange";
    public static final String WARNING_QUEUE = "warning.queue";
    public static final String WARNING_ROUTING_KEY = "warning.routing.key";
    
    @Bean
    public DirectExchange warningExchange() {
        return new DirectExchange(WARNING_EXCHANGE);
    }
    
    @Bean
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE)
                .withArgument("x-message-ttl", 86400000) // 消息过期时间24小时
                .build();
    }
    
    @Bean
    public Binding warningBinding() {
        return BindingBuilder.bind(warningQueue())
                .to(warningExchange())
                .with(WARNING_ROUTING_KEY);
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
} 