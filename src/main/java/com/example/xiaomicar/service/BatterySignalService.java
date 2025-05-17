package com.example.xiaomicar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.xiaomicar.entity.BatterySignal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 电池信号记录表 服务类
 * </p>
 *
 * @author jmj
 * @since 2025-05-18
 */
public interface BatterySignalService extends IService<BatterySignal> {
    /**
     * 保存电池信号
     * @param batterySignal 电池信号
     * @return 是否成功
     */
    boolean saveSignal(BatterySignal batterySignal);
    
    /**
     * 批量保存电池信号
     * @param signals 电池信号列表
     * @return 是否成功
     */
    boolean saveBatchSignals(List<BatterySignal> signals);
    
    /**
     * 查询车辆在指定时间范围内的电池信号
     * @param vehicleId 车辆ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 电池信号列表
     */
    List<BatterySignal> getSignalsByTimeRange(Long vehicleId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取车辆最新的电池信号
     * @param vehicleId 车辆ID
     * @return 电池信号
     */
    BatterySignal getLatestSignal(Long vehicleId);
}
