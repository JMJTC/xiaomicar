package com.example.xiaomicar.controller;

import com.example.xiaomicar.common.Result;
import com.example.xiaomicar.dto.VehicleInfoDTO;
import com.example.xiaomicar.entity.VehicleInfo;
import com.example.xiaomicar.service.VehicleInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 车辆信息表 前端控制器
 * </p>
 *
 * @author jmj
 * @since 2025-05-18
 */
@RestController
@RequestMapping("/api/vehicle")
public class VehicleInfoController {
    
    @Resource
    private VehicleInfoService vehicleInfoService;
    
    @GetMapping("/{vid}")
    public Result<VehicleInfo> getByVid(@PathVariable String vid) {
        VehicleInfo vehicleInfo = vehicleInfoService.getByVid(vid);
        return Result.success(vehicleInfo);
    }
    
    @PostMapping
    public Result<Boolean> addVehicle(@RequestBody @Valid VehicleInfoDTO vehicleInfoDTO) {
        VehicleInfo vehicleInfo = new VehicleInfo();
        BeanUtils.copyProperties(vehicleInfoDTO, vehicleInfo);
        boolean result = vehicleInfoService.addVehicle(vehicleInfo);
        return Result.success(result);
    }
    
    @PutMapping
    public Result<Boolean> updateVehicle(@RequestBody @Valid VehicleInfoDTO vehicleInfoDTO) {
        VehicleInfo vehicleInfo = new VehicleInfo();
        BeanUtils.copyProperties(vehicleInfoDTO, vehicleInfo);
        boolean result = vehicleInfoService.updateVehicle(vehicleInfo);
        return Result.success(result);
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteVehicle(@PathVariable Long id) {
        boolean result = vehicleInfoService.deleteVehicle(id);
        return Result.success(result);
    }
}
