package com.example.xiaomicar.utils;

import com.example.xiaomicar.entity.BatterySignal;
import com.example.xiaomicar.entity.WarningRule;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RuleExpressionParser {
    
    private static final Pattern RULE_PATTERN = Pattern.compile("(\\d+(\\.\\d+)?)<=(.+?),(.+?);");
    
    /**
     * 解析规则表达式，返回预警等级
     * @param rule 预警规则
     * @param signal 电池信号
     * @return 预警等级，如果不满足任何规则返回null
     */
    public Integer parseRule(WarningRule rule, BatterySignal signal) {
        String expression = rule.getRuleExpression();
        List<RuleCondition> conditions = parseConditions(expression);
        
        // 计算表达式值
        BigDecimal value = calculateExpression(rule.getRuleCode(), signal);
        
        // 匹配规则条件
        for (RuleCondition condition : conditions) {
            if (value.compareTo(condition.getThreshold()) >= 0) {
                return condition.getWarningLevel();
            }
        }
        
        return null;
    }
    
    /**
     * 解析规则条件
     */
    private List<RuleCondition> parseConditions(String expression) {
        List<RuleCondition> conditions = new ArrayList<>();
        Matcher matcher = RULE_PATTERN.matcher(expression);
        
        while (matcher.find()) {
            BigDecimal threshold = new BigDecimal(matcher.group(1));
            String condition = matcher.group(3);
            String levelStr = matcher.group(4);
            
            // 提取预警等级
            int warningLevel = Integer.parseInt(levelStr.replaceAll("[^0-9]", ""));
            
            conditions.add(new RuleCondition(threshold, warningLevel));
        }
        
        return conditions;
    }
    
    /**
     * 计算表达式值
     */
    private BigDecimal calculateExpression(String ruleCode, BatterySignal signal) {
        switch (ruleCode) {
            case "1": // 电压差
                return signal.getMx().subtract(signal.getMi());
            case "2": // 电流差
                return signal.getIx().subtract(signal.getIi());
            default:
                throw new IllegalArgumentException("Unsupported rule code: " + ruleCode);
        }
    }
    
    /**
     * 规则条件内部类
     */
    private static class RuleCondition {
        private final BigDecimal threshold;
        private final int warningLevel;
        
        public RuleCondition(BigDecimal threshold, int warningLevel) {
            this.threshold = threshold;
            this.warningLevel = warningLevel;
        }
        
        public BigDecimal getThreshold() {
            return threshold;
        }
        
        public int getWarningLevel() {
            return warningLevel;
        }
    }
} 