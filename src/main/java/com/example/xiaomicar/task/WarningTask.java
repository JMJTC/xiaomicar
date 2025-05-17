package com.example.xiaomicar.task;

import com.example.xiaomicar.entity.BatterySignal;
import com.example.xiaomicar.entity.VehicleInfo;
import com.example.xiaomicar.entity.WarningInfo;
import com.example.xiaomicar.entity.WarningRule;
import com.example.xiaomicar.mq.WarningMessageProducer;
import com.example.xiaomicar.service.BatterySignalService;
import com.example.xiaomicar.service.VehicleInfoService;
import com.example.xiaomicar.service.WarningRuleService;
import com.example.xiaomicar.utils.RuleExpressionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class WarningTask {
    
    private static final Logger logger = LoggerFactory.getLogger(WarningTask.class);
    
    @Resource
    private BatterySignalService batterySignalService;
    
    @Resource
    private VehicleInfoService vehicleInfoService;
    
    @Resource
    private WarningRuleService warningRuleService;
    
    @Resource
    private RuleExpressionParser ruleExpressionParser;
    
    @Resource
    private WarningMessageProducer warningMessageProducer;
    
    /**
     * 每5分钟执行一次预警检查
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void checkWarning() {
        logger.info("开始执行预警检查任务");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveMinutesAgo = now.minusMinutes(5);
        
        try {
            // 1. 获取所有车辆信息
            List<VehicleInfo> vehicles = vehicleInfoService.list();
            
            for (VehicleInfo vehicle : vehicles) {
                // 2. 获取车辆最新的电池信号
                BatterySignal signal = batterySignalService.getLatestSignal(vehicle.getId());
                if (signal == null || signal.getSignalTime().isBefore(fiveMinutesAgo)) {
                    continue;
                }
                
                // 3. 获取车辆适用的预警规则
                List<WarningRule> rules = warningRuleService.getByBatteryType(vehicle.getBatteryType());
                
                // 4. 检查每个规则
                List<WarningInfo> warnings = new ArrayList<>();
                for (WarningRule rule : rules) {
                    Integer warningLevel = ruleExpressionParser.parseRule(rule, signal);
                    if (warningLevel != null) {
                        // 生成预警信息
                        WarningInfo warning = new WarningInfo();
                        warning.setVehicleId(vehicle.getId());
                        warning.setRuleId(rule.getId());
                        warning.setWarningLevel(warningLevel);
                        warning.setWarningMessage(rule.getWarningMessage());
                        warning.setWarningTime(now);
                        warning.setIsHandled(false);
                        warnings.add(warning);
                    }
                }
                
                // 5. 通过消息队列发送预警信息
                if (!warnings.isEmpty()) {
                    warningMessageProducer.sendBatchWarningMessages(warnings);
                    logger.info("车辆[{}]生成{}条预警信息", vehicle.getVid(), warnings.size());
                }
            }
        } catch (Exception e) {
            logger.error("预警检查任务执行异常", e);
        }
        
        logger.info("预警检查任务执行完成");
    }
} 