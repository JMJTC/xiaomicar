package com.example.xiaomicar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.xiaomicar.entity.WarningRule;
import java.util.List;

/**
 * <p>
 * 预警规则表 服务类
 * </p>
 *
 * @author jmj
 * @since 2025-05-18
 */
public interface WarningRuleService extends IService<WarningRule> {
    /**
     * 根据电池类型查询预警规则
     * @param batteryType 电池类型
     * @return 预警规则列表
     */
    List<WarningRule> getByBatteryType(String batteryType);
    
    /**
     * 根据规则编号和电池类型查询预警规则
     * @param ruleCode 规则编号
     * @param batteryType 电池类型
     * @return 预警规则
     */
    WarningRule getByRuleCodeAndBatteryType(String ruleCode, String batteryType);
    
    /**
     * 新增预警规则
     * @param warningRule 预警规则
     * @return 是否成功
     */
    boolean addRule(WarningRule warningRule);
    
    /**
     * 更新预警规则
     * @param warningRule 预警规则
     * @return 是否成功
     */
    boolean updateRule(WarningRule warningRule);
    
    /**
     * 删除预警规则
     * @param id 规则ID
     * @return 是否成功
     */
    boolean deleteRule(Long id);
    
    /**
     * 启用/禁用预警规则
     * @param id 规则ID
     * @param isEnabled 是否启用
     * @return 是否成功
     */
    boolean updateRuleStatus(Long id, Boolean isEnabled);
}
