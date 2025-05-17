package com.example.xiaomicar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.xiaomicar.entity.WarningInfo;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 预警信息表 服务类
 * </p>
 *
 * @author jmj
 * @since 2025-05-18
 */
public interface WarningInfoService extends IService<WarningInfo> {
    /**
     * 生成预警信息
     * @param warningInfo 预警信息
     * @return 是否成功
     */
    boolean generateWarning(WarningInfo warningInfo);
    
    /**
     * 批量生成预警信息
     * @param warningInfos 预警信息列表
     * @return 是否成功
     */
    boolean generateBatchWarnings(List<WarningInfo> warningInfos);
    
    /**
     * 处理预警信息
     * @param id 预警ID
     * @param handleResult 处理结果
     * @return 是否成功
     */
    boolean handleWarning(Long id, String handleResult);
    
    /**
     * 查询车辆在指定时间范围内的预警信息
     * @param vehicleId 车辆ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param isHandled 是否已处理
     * @return 预警信息列表
     */
    List<WarningInfo> getWarningsByTimeRange(Long vehicleId, LocalDateTime startTime, LocalDateTime endTime, Boolean isHandled);
    
    /**
     * 获取车辆最新的预警信息
     * @param vehicleId 车辆ID
     * @return 预警信息
     */
    WarningInfo getLatestWarning(Long vehicleId);
}
