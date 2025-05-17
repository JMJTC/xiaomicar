package com.example.xiaomicar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 车辆信息表
 * </p>
 *
 * @author jmj
 * @since 2025-05-18
 */
@Data
@TableName("vehicle_info")
public class VehicleInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 车辆唯一标识
     */
    private String vid;

    /**
     * 车架编号
     */
    private String frameNumber;

    /**
     * 电池类型：ternary(三元电池)、lfp(铁锂电池)
     */
    private String batteryType;

    /**
     * 总里程(km)
     */
    private BigDecimal totalMileage;

    /**
     * 电池健康状态(%)
     */
    private Integer batteryHealth;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @Override
    public String toString() {
        return "VehicleInfo{" +
        "id = " + id +
        ", vid = " + vid +
        ", frameNumber = " + frameNumber +
        ", batteryType = " + batteryType +
        ", totalMileage = " + totalMileage +
        ", batteryHealth = " + batteryHealth +
        ", createTime = " + createTime +
        ", updateTime = " + updateTime +
        "}";
    }
}
