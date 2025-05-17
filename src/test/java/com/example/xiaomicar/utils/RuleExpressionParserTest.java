package com.example.xiaomicar.utils;

import com.example.xiaomicar.entity.BatterySignal;
import com.example.xiaomicar.entity.WarningRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RuleExpressionParserTest {

    private RuleExpressionParser parser;
    private BatterySignal signal;
    private WarningRule rule;

    @BeforeEach
    void setUp() {
        parser = new RuleExpressionParser();
        
        // 准备电池信号数据
        signal = new BatterySignal();
        signal.setId(1L);
        signal.setVehicleId(1L);
        signal.setSignalTime(LocalDateTime.now());
        signal.setMx(new BigDecimal("3.8"));
        signal.setMi(new BigDecimal("3.6"));
        signal.setIx(new BigDecimal("100.0"));
        signal.setIi(new BigDecimal("80.0"));
        signal.setSoc(new BigDecimal("80.0"));
        signal.setSoh(new BigDecimal("95.0"));
        signal.setTemperature(new BigDecimal("25.0"));
        
        // 准备预警规则数据
        rule = new WarningRule();
        rule.setId(1L);
        rule.setRuleCode("TEST001");
        rule.setRuleName("测试规则");
        rule.setBatteryType("ternary");
        rule.setWarningLevel(0);
        rule.setWarningMessage("测试预警消息");
    }

    @Test
    void parseRule_VoltageDifference_Level0() {
        // 准备测试数据
        rule.setRuleExpression("5<=(Mx - Mi),报警等级：0;3<=(Mx - Mi)<5,报警等级：1;1<=(Mx - Mi)<3,报警等级：2;0.6<=(Mx - Mi)<1,报警等级：3;0.2<=(Mx - Mi)<0.6,报警等级：4;(Mx - Mi)<0.2, 不报警");
        signal.setMx(new BigDecimal("4.0"));
        signal.setMi(new BigDecimal("3.0"));

        // 执行测试
        Integer result = parser.parseRule(rule, signal);

        // 验证结果
        assertNotNull(result);
        assertEquals(0, result);
    }

    @Test
    void parseRule_VoltageDifference_Level1() {
        // 准备测试数据
        rule.setRuleExpression("5<=(Mx - Mi),报警等级：0;3<=(Mx - Mi)<5,报警等级：1;1<=(Mx - Mi)<3,报警等级：2;0.6<=(Mx - Mi)<1,报警等级：3;0.2<=(Mx - Mi)<0.6,报警等级：4;(Mx - Mi)<0.2, 不报警");
        signal.setMx(new BigDecimal("3.8"));
        signal.setMi(new BigDecimal("3.0"));

        // 执行测试
        Integer result = parser.parseRule(rule, signal);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result);
    }

    @Test
    void parseRule_CurrentDifference_Level0() {
        // 准备测试数据
        rule.setRuleExpression("3<=(Ix - Ii),报警等级：0;1<=(Ix - Ii)<3,报警等级：1;0.2<=(Ix - Ii)<1,报警等级：2;(Ix - Ii)<0.2, 不报警");
        signal.setIx(new BigDecimal("100.0"));
        signal.setIi(new BigDecimal("80.0"));

        // 执行测试
        Integer result = parser.parseRule(rule, signal);

        // 验证结果
        assertNotNull(result);
        assertEquals(0, result);
    }

    @Test
    void parseRule_NoWarning() {
        // 准备测试数据
        rule.setRuleExpression("5<=(Mx - Mi),报警等级：0;3<=(Mx - Mi)<5,报警等级：1;1<=(Mx - Mi)<3,报警等级：2;0.6<=(Mx - Mi)<1,报警等级：3;0.2<=(Mx - Mi)<0.6,报警等级：4;(Mx - Mi)<0.2, 不报警");
        signal.setMx(new BigDecimal("3.7"));
        signal.setMi(new BigDecimal("3.6"));

        // 执行测试
        Integer result = parser.parseRule(rule, signal);

        // 验证结果
        assertNull(result);
    }

    @Test
    void parseRule_InvalidExpression() {
        // 准备测试数据
        rule.setRuleExpression("invalid expression");

        // 执行测试
        Integer result = parser.parseRule(rule, signal);

        // 验证结果
        assertNull(result);
    }
} 