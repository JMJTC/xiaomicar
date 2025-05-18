package com.example.xiaomicar.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.xiaomicar.entity.BatterySignal;
import com.example.xiaomicar.mapper.BatterySignalMapper;
import com.example.xiaomicar.service.impl.BatterySignalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatterySignalServiceTest {

    @Mock
    private BatterySignalMapper batterySignalMapper;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private BatterySignalServiceImpl batterySignalService;

    private BatterySignal batterySignal;

    @BeforeEach
    void setUp() {
        batterySignal = new BatterySignal();
        batterySignal.setId(1L);
        batterySignal.setVehicleId(1L);
        batterySignal.setSignalTime(LocalDateTime.now());
        batterySignal.setMx(new BigDecimal("100.00"));
        batterySignal.setMi(new BigDecimal("80.00"));
        batterySignal.setIx(new BigDecimal("50.00"));
        batterySignal.setIi(new BigDecimal("30.00"));
        batterySignal.setSoc(new BigDecimal("90.00"));
        batterySignal.setSoh(new BigDecimal("95.00"));
        batterySignal.setTemperature(new BigDecimal("25.00"));

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void saveSignal_Success() {
        // 准备测试数据
        when(batterySignalMapper.insert(any(BatterySignal.class))).thenReturn(1);

        // 执行测试
        boolean result = batterySignalService.saveSignal(batterySignal);

        // 验证结果
        assertTrue(result);
        verify(batterySignalMapper).insert(any(BatterySignal.class));
        verify(valueOperations).set(anyString(), any(BatterySignal.class), anyLong(), any());
    }

    @Test
    void getLatestSignal_Success() {
        // 准备测试数据
        when(valueOperations.get(anyString())).thenReturn(null);
        when(batterySignalMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(batterySignal);

        // 执行测试
        BatterySignal result = batterySignalService.getLatestSignal(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(batterySignal.getId(), result.getId());
        verify(valueOperations).get(anyString());
        verify(batterySignalMapper).selectOne(any(LambdaQueryWrapper.class));
        verify(valueOperations).set(anyString(), any(BatterySignal.class), anyLong(), any());
    }

    @Test
    void getLatestSignal_FromCache() {
        // 准备测试数据
        when(valueOperations.get(anyString())).thenReturn(batterySignal);

        // 执行测试
        BatterySignal result = batterySignalService.getLatestSignal(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(batterySignal.getId(), result.getId());
        verify(valueOperations).get(anyString());
        verify(batterySignalMapper, never()).selectOne(any(LambdaQueryWrapper.class));
    }
} 