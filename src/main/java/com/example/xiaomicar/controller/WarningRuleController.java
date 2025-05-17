package com.example.xiaomicar.controller;

import com.example.xiaomicar.common.Result;
import com.example.xiaomicar.dto.WarningRuleDTO;
import com.example.xiaomicar.entity.WarningRule;
import com.example.xiaomicar.service.WarningRuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 预警规则表 前端控制器
 * </p>
 *
 * @author jmj
 * @since 2025-05-18
 */
@RestController
@RequestMapping("/api/rule")
public class WarningRuleController {
    
    @Resource
    private WarningRuleService warningRuleService;
    
    @GetMapping("/battery-type/{batteryType}")
    public Result<List<WarningRule>> getByBatteryType(@PathVariable String batteryType) {
        List<WarningRule> rules = warningRuleService.getByBatteryType(batteryType);
        return Result.success(rules);
    }
    
    @GetMapping("/{ruleCode}/{batteryType}")
    public Result<WarningRule> getByRuleCodeAndBatteryType(
            @PathVariable String ruleCode,
            @PathVariable String batteryType) {
        WarningRule rule = warningRuleService.getByRuleCodeAndBatteryType(ruleCode, batteryType);
        return Result.success(rule);
    }
    
    @PostMapping
    public Result<Boolean> addRule(@RequestBody @Valid WarningRuleDTO warningRuleDTO) {
        WarningRule warningRule = new WarningRule();
        BeanUtils.copyProperties(warningRuleDTO, warningRule);
        boolean result = warningRuleService.addRule(warningRule);
        return Result.success(result);
    }
    
    @PutMapping
    public Result<Boolean> updateRule(@RequestBody @Valid WarningRuleDTO warningRuleDTO) {
        WarningRule warningRule = new WarningRule();
        BeanUtils.copyProperties(warningRuleDTO, warningRule);
        boolean result = warningRuleService.updateRule(warningRule);
        return Result.success(result);
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteRule(@PathVariable Long id) {
        boolean result = warningRuleService.deleteRule(id);
        return Result.success(result);
    }
    
    @PutMapping("/{id}/status")
    public Result<Boolean> updateRuleStatus(
            @PathVariable Long id,
            @RequestParam Boolean isEnabled) {
        boolean result = warningRuleService.updateRuleStatus(id, isEnabled);
        return Result.success(result);
    }
}
