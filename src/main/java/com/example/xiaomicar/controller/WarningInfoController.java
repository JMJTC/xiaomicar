package com.example.xiaomicar.controller;

import com.example.xiaomicar.common.Result;
import com.example.xiaomicar.dto.WarningInfoDTO;
import com.example.xiaomicar.entity.WarningInfo;
import com.example.xiaomicar.service.WarningInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 预警信息表 前端控制器
 * </p>
 *
 * @author jmj
 * @since 2025-05-18
 */
@RestController
@RequestMapping("/api/warning")
public class WarningInfoController {
    
    @Resource
    private WarningInfoService warningInfoService;
    
    @PostMapping
    public Result<Boolean> generateWarning(@RequestBody @Valid WarningInfoDTO warningInfoDTO) {
        WarningInfo warningInfo = new WarningInfo();
        BeanUtils.copyProperties(warningInfoDTO, warningInfo);
        boolean result = warningInfoService.generateWarning(warningInfo);
        return Result.success(result);
    }
    
    @PostMapping("/batch")
    public Result<Boolean> generateBatchWarnings(@RequestBody @Valid List<WarningInfoDTO> warningInfoDTOs) {
        List<WarningInfo> warnings = warningInfoDTOs.stream()
                .map(dto -> {
                    WarningInfo warning = new WarningInfo();
                    BeanUtils.copyProperties(dto, warning);
                    return warning;
                })
                .toList();
        boolean result = warningInfoService.generateBatchWarnings(warnings);
        return Result.success(result);
    }
    
    @PutMapping("/{id}/handle")
    public Result<Boolean> handleWarning(
            @PathVariable Long id,
            @RequestParam String handleResult) {
        boolean result = warningInfoService.handleWarning(id, handleResult);
        return Result.success(result);
    }
    
    @GetMapping("/time-range")
    public Result<List<WarningInfo>> getWarningsByTimeRange(
            @RequestParam Long vehicleId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(required = false) Boolean isHandled) {
        List<WarningInfo> warnings = warningInfoService.getWarningsByTimeRange(vehicleId, startTime, endTime, isHandled);
        return Result.success(warnings);
    }
    
    @GetMapping("/latest/{vehicleId}")
    public Result<WarningInfo> getLatestWarning(@PathVariable Long vehicleId) {
        WarningInfo warning = warningInfoService.getLatestWarning(vehicleId);
        return Result.success(warning);
    }
}
