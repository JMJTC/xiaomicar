package com.example.xiaomicar.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.xiaomicar.entity.WarningInfo;
import com.example.xiaomicar.mapper.WarningInfoMapper;
import com.example.xiaomicar.mq.WarningMessageProducer;
import com.example.xiaomicar.service.impl.WarningInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WarningInfoServiceTest {

    @Mock
    private WarningInfoMapper warningInfoMapper;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @Mock
    private WarningMessageProducer warningMessageProducer;

    @InjectMocks
    private WarningInfoServiceImpl warningInfoService;

    private WarningInfo warningInfo;

    @BeforeEach
    void setUp() {
        warningInfo = new WarningInfo();
        warningInfo.setId(1L);
        warningInfo.setVehicleId(1L);
        warningInfo.setRuleId(1L);
        warningInfo.setWarningLevel(0);
        warningInfo.setWarningMessage("测试预警消息");
        warningInfo.setWarningTime(LocalDateTime.now());
        warningInfo.setIsHandled(false);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void generateWarning_Success() {
        // 准备测试数据
        when(warningInfoMapper.insert(any(WarningInfo.class))).thenReturn(1);

        // 执行测试
        boolean result = warningInfoService.generateWarning(warningInfo);

        // 验证结果
        assertTrue(result);
        verify(warningInfoMapper).insert(any(WarningInfo.class));
        verify(warningMessageProducer).sendWarningMessage(any(WarningInfo.class));
    }

    @Test
    void generateBatchWarnings_Success() {
        // 准备测试数据
        List<WarningInfo> warnings = Arrays.asList(warningInfo);
        when(warningInfoMapper.insert(any(WarningInfo.class))).thenReturn(1);

        // 执行测试
        boolean result = warningInfoService.generateBatchWarnings(warnings);

        // 验证结果
        assertTrue(result);
        verify(warningInfoMapper, times(warnings.size())).insert(any(WarningInfo.class));
        verify(warningMessageProducer).sendBatchWarningMessages(anyList());
    }

    @Test
    void handleWarning_Success() {
        // 准备测试数据
        when(warningInfoMapper.updateById(any(WarningInfo.class))).thenReturn(1);

        // 执行测试
        boolean result = warningInfoService.handleWarning(1L, "处理完成");

        // 验证结果
        assertTrue(result);
        verify(warningInfoMapper).updateById(any(WarningInfo.class));
    }

    @Test
    void getLatestWarning_Success() {
        // 准备测试数据
        when(valueOperations.get(anyString())).thenReturn(null);
        when(warningInfoMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(warningInfo);

        // 执行测试
        WarningInfo result = warningInfoService.getLatestWarning(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(warningInfo.getId(), result.getId());
        verify(valueOperations).get(anyString());
        verify(warningInfoMapper).selectOne(any(LambdaQueryWrapper.class));
        verify(valueOperations).set(anyString(), any(WarningInfo.class), anyLong(), any());
    }

    @Test
    void getLatestWarning_FromCache() {
        // 准备测试数据
        when(valueOperations.get(anyString())).thenReturn(warningInfo);

        // 执行测试
        WarningInfo result = warningInfoService.getLatestWarning(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(warningInfo.getId(), result.getId());
        verify(valueOperations).get(anyString());
        verify(warningInfoMapper, never()).selectOne(any(LambdaQueryWrapper.class));
    }
} 