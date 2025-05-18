package com.example.xiaomicar.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.xiaomicar.entity.VehicleInfo;
import com.example.xiaomicar.mapper.VehicleInfoMapper;
import com.example.xiaomicar.service.impl.VehicleInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleInfoServiceTest {

    @Mock
    private VehicleInfoMapper vehicleInfoMapper;

    @InjectMocks
    private VehicleInfoServiceImpl vehicleInfoService;

    private VehicleInfo vehicleInfo;

    @BeforeEach
    void setUp() {
        vehicleInfo = new VehicleInfo();
        vehicleInfo.setId(1L);
        vehicleInfo.setVid("V001");
        vehicleInfo.setFrameNumber("F001");
        vehicleInfo.setBatteryType("ternary");
        vehicleInfo.setTotalMileage(new BigDecimal("100.00"));
        vehicleInfo.setBatteryHealth(95);
    }

    @Test
    void createVehicle_Success() {
        // 准备测试数据
        when(vehicleInfoMapper.insert(any(VehicleInfo.class))).thenReturn(1);

        // 执行测试
        boolean result = vehicleInfoService.addVehicle(vehicleInfo);

        // 验证结果
        assertTrue(result);
        verify(vehicleInfoMapper).insert(any(VehicleInfo.class));
    }

    @Test
    void updateVehicle_Success() {
        // 准备测试数据
        when(vehicleInfoMapper.updateById(any(VehicleInfo.class))).thenReturn(1);

        // 执行测试
        boolean result = vehicleInfoService.updateVehicle(vehicleInfo);

        // 验证结果
        assertTrue(result);
        verify(vehicleInfoMapper).updateById(any(VehicleInfo.class));
    }

    @Test
    void deleteVehicle_Success() {
        // 准备测试数据
        when(vehicleInfoMapper.deleteById(anyLong())).thenReturn(1);

        // 执行测试
        boolean result = vehicleInfoService.deleteVehicle(1L);

        // 验证结果
        assertTrue(result);
        verify(vehicleInfoMapper).deleteById(anyLong());
    }

    @Test
    void getVehicleById_Success() {
        // 准备测试数据
        when(vehicleInfoMapper.selectById(anyLong())).thenReturn(vehicleInfo);

        // 执行测试
        VehicleInfo result = vehicleInfoService.getById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(vehicleInfo.getId(), result.getId());
        verify(vehicleInfoMapper).selectById(anyLong());
    }

    @Test
    void list_Success() {
        // 准备测试数据
        List<VehicleInfo> expectedVehicles = Arrays.asList(vehicleInfo);
        when(vehicleInfoMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(expectedVehicles);

        // 执行测试
        List<VehicleInfo> result = vehicleInfoService.list();

        // 验证结果
        assertNotNull(result);
        assertEquals(expectedVehicles.size(), result.size());
        verify(vehicleInfoMapper).selectList(any(LambdaQueryWrapper.class));
    }
} 