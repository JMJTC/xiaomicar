package com.example.xiaomicar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xiaomicar.entity.WarningRule;
import com.example.xiaomicar.mapper.WarningRuleMapper;
import com.example.xiaomicar.service.WarningRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class WarningRuleServiceImpl extends ServiceImpl<WarningRuleMapper, WarningRule> implements WarningRuleService {

    @Override
    public List<WarningRule> getByBatteryType(String batteryType) {
        LambdaQueryWrapper<WarningRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarningRule::getBatteryType, batteryType)
               .eq(WarningRule::getIsEnabled, true);
        return list(wrapper);
    }

    @Override
    public WarningRule getByRuleCodeAndBatteryType(String ruleCode, String batteryType) {
        LambdaQueryWrapper<WarningRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarningRule::getRuleCode, ruleCode)
               .eq(WarningRule::getBatteryType, batteryType);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRule(WarningRule warningRule) {
        return save(warningRule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRule(WarningRule warningRule) {
        return updateById(warningRule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRule(Long id) {
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRuleStatus(Long id, Boolean isEnabled) {
        WarningRule warningRule = new WarningRule();
        warningRule.setId(id);
        warningRule.setIsEnabled(isEnabled);
        return updateById(warningRule);
    }
} 