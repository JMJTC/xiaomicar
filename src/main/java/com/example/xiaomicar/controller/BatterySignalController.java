package com.example.xiaomicar.controller;

import com.example.xiaomicar.common.Result;
import com.example.xiaomicar.dto.BatterySignalDTO;
import com.example.xiaomicar.entity.BatterySignal;
import com.example.xiaomicar.service.BatterySignalService;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 电池信号记录表 前端控制器
 * </p>
 *
 * @author jmj
 * @since 2025-05-18
 */
@RestController
@RequestMapping("/api/signal")
public class BatterySignalController {

    @Resource
    private BatterySignalService batterySignalService;
    
    @PostMapping
    public Result<Boolean> saveSignal(@RequestBody @Valid BatterySignalDTO batterySignalDTO) {
        BatterySignal batterySignal = new BatterySignal();
        BeanUtils.copyProperties(batterySignalDTO, batterySignal);
        boolean result = batterySignalService.saveSignal(batterySignal);
        return Result.success(result);
    }
    
    @PostMapping("/batch")
    public Result<Boolean> saveBatchSignals(@RequestBody @Valid List<BatterySignalDTO> batterySignalDTOs) {
        List<BatterySignal> signals = batterySignalDTOs.stream()
                .map(dto -> {
                    BatterySignal signal = new BatterySignal();
                    BeanUtils.copyProperties(dto, signal);
                    return signal;
                })
                .toList();
        boolean result = batterySignalService.saveBatchSignals(signals);
        return Result.success(result);
    }
    
    @GetMapping("/time-range")
    public Result<List<BatterySignal>> getSignalsByTimeRange(
            @RequestParam Long vehicleId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<BatterySignal> signals = batterySignalService.getSignalsByTimeRange(vehicleId, startTime, endTime);
        return Result.success(signals);
    }
    
    @GetMapping("/latest/{vehicleId}")
    public Result<BatterySignal> getLatestSignal(@PathVariable Long vehicleId) {
        BatterySignal signal = batterySignalService.getLatestSignal(vehicleId);
        return Result.success(signal);
    }
}
