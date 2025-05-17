package com.example.xiaomicar.mq;

import com.example.xiaomicar.config.RabbitMQConfig;
import com.example.xiaomicar.entity.WarningInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class WarningMessageProducer {
    
    private static final Logger logger = LoggerFactory.getLogger(WarningMessageProducer.class);
    
    @Resource
    private RabbitTemplate rabbitTemplate;
    
    /**
     * 发送单个预警消息
     */
    public void sendWarningMessage(WarningInfo warningInfo) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.WARNING_EXCHANGE,
                RabbitMQConfig.WARNING_ROUTING_KEY,
                warningInfo
            );
            logger.info("发送预警消息成功: {}", warningInfo);
        } catch (Exception e) {
            logger.error("发送预警消息失败: {}", warningInfo, e);
        }
    }
    
    /**
     * 批量发送预警消息
     */
    public void sendBatchWarningMessages(List<WarningInfo> warningInfos) {
        for (WarningInfo warningInfo : warningInfos) {
            sendWarningMessage(warningInfo);
        }
    }
} 