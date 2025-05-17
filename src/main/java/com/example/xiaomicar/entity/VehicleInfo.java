package com.example.xiaomicar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("vehicle_info")
public class VehicleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    private Byte batteryHealth;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    public BigDecimal getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(BigDecimal totalMileage) {
        this.totalMileage = totalMileage;
    }

    public Byte getBatteryHealth() {
        return batteryHealth;
    }

    public void setBatteryHealth(Byte batteryHealth) {
        this.batteryHealth = batteryHealth;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

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
