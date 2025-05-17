package com.example.xiaomicar.mq;

import com.example.xiaomicar.config.RabbitMQConfig;
import com.example.xiaomicar.entity.WarningInfo;
import com.example.xiaomicar.service.WarningInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WarningMessageConsumer {
    
    private static final Logger logger = LoggerFactory.getLogger(WarningMessageConsumer.class);
    
    @Resource
    private WarningInfoService warningInfoService;
    
    /**
     * 消费预警消息
     */
    @RabbitListener(queues = RabbitMQConfig.WARNING_QUEUE)
    public void handleWarningMessage(WarningInfo warningInfo) {
        try {
            logger.info("收到预警消息: {}", warningInfo);
            
            // 处理预警信息
            String handleResult = "系统自动处理完成";
            warningInfoService.handleWarning(warningInfo.getId(), handleResult);
            
            logger.info("预警消息处理完成: {}", warningInfo);
        } catch (Exception e) {
            logger.error("预警消息处理失败: {}", warningInfo, e);
            // 这里可以添加重试逻辑或死信队列处理
        }
    }
} 