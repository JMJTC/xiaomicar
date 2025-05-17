package com.example.xiaomicar.config;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQConfig {
    
    public static final String WARNING_TOPIC = "warning-topic";
    public static final String WARNING_GROUP = "warning-group";
    
    @Bean
    public RocketMQTemplate rocketMQTemplate() {
        return new RocketMQTemplate();
    }
} 