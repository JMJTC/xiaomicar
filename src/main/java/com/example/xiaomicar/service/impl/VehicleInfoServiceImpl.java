package com.example.xiaomicar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xiaomicar.entity.VehicleInfo;
import com.example.xiaomicar.mapper.VehicleInfoMapper;
import com.example.xiaomicar.service.VehicleInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleInfoServiceImpl extends ServiceImpl<VehicleInfoMapper, VehicleInfo> implements VehicleInfoService {

    @Override
    public VehicleInfo getByVid(String vid) {
        LambdaQueryWrapper<VehicleInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VehicleInfo::getVid, vid);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addVehicle(VehicleInfo vehicleInfo) {
        return save(vehicleInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateVehicle(VehicleInfo vehicleInfo) {
        return updateById(vehicleInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteVehicle(Long id) {
        return removeById(id);
    }
} 