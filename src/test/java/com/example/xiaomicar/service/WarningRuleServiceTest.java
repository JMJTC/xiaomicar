package com.example.xiaomicar.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.xiaomicar.entity.WarningRule;
import com.example.xiaomicar.mapper.WarningRuleMapper;
import com.example.xiaomicar.service.impl.WarningRuleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WarningRuleServiceTest {

    @Mock
    private WarningRuleMapper warningRuleMapper;

    @InjectMocks
    private WarningRuleServiceImpl warningRuleService;

    private WarningRule warningRule;

    @BeforeEach
    void setUp() {
        warningRule = new WarningRule();
        warningRule.setId(1L);
        warningRule.setRuleCode("TEST001");
        warningRule.setRuleName("测试规则");
        warningRule.setBatteryType("ternary");
        warningRule.setRuleExpression("5<=(Mx - Mi),报警等级：0");
        warningRule.setWarningLevel(0);
        warningRule.setWarningMessage("测试预警消息");
        warningRule.setIsEnabled(true);
    }

    @Test
    void createRule_Success() {
        // 准备测试数据
        when(warningRuleMapper.insert(any(WarningRule.class))).thenReturn(1);

        // 执行测试
        boolean result = warningRuleService.addRule(warningRule);

        // 验证结果
        assertTrue(result);
        verify(warningRuleMapper).insert(any(WarningRule.class));
    }

    @Test
    void updateRule_Success() {
        // 准备测试数据
        when(warningRuleMapper.updateById(any(WarningRule.class))).thenReturn(1);

        // 执行测试
        boolean result = warningRuleService.updateRule(warningRule);

        // 验证结果
        assertTrue(result);
        verify(warningRuleMapper).updateById(any(WarningRule.class));
    }

    @Test
    void updateRuleStatus_Success() {
        // 准备测试数据
        when(warningRuleMapper.updateById(any(WarningRule.class))).thenReturn(1);

        // 执行测试
        boolean result = warningRuleService.updateRuleStatus(1L, false);

        // 验证结果
        assertTrue(result);
        verify(warningRuleMapper).updateById(any(WarningRule.class));
    }

    @Test
    void getByBatteryType_Success() {
        // 准备测试数据
        List<WarningRule> expectedRules = Arrays.asList(warningRule);
        when(warningRuleMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(expectedRules);

        // 执行测试
        List<WarningRule> result = warningRuleService.getByBatteryType("ternary");

        // 验证结果
        assertNotNull(result);
        assertEquals(expectedRules.size(), result.size());
        verify(warningRuleMapper).selectList(any(LambdaQueryWrapper.class));
    }
} 