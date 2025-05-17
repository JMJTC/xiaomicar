package com.example.xiaomicar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.xiaomicar.entity.VehicleInfo;

/**
 * <p>
 * 车辆信息表 服务类
 * </p>
 *
 * @author jmj
 * @since 2025-05-18
 */
public interface VehicleInfoService extends IService<VehicleInfo> {
    /**
     * 根据车辆唯一标识查询车辆信息
     * @param vid 车辆唯一标识
     * @return 车辆信息
     */
    VehicleInfo getByVid(String vid);
    
    /**
     * 新增车辆信息
     * @param vehicleInfo 车辆信息
     * @return 是否成功
     */
    boolean addVehicle(VehicleInfo vehicleInfo);
    
    /**
     * 更新车辆信息
     * @param vehicleInfo 车辆信息
     * @return 是否成功
     */
    boolean updateVehicle(VehicleInfo vehicleInfo);
    
    /**
     * 删除车辆信息
     * @param id 车辆ID
     * @return 是否成功
     */
    boolean deleteVehicle(Long id);
}
