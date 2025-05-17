package com.example.xiaomicar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 电池信号记录表
 * </p>
 *
 * @author jmj
 * @since 2025-05-18
 */
@TableName("battery_signal")
public class BatterySignal implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 车辆ID
     */
    private Long vehicleId;

    /**
     * 信号时间
     */
    private LocalDateTime signalTime;

    /**
     * 最高电压
     */
    private BigDecimal mx;

    /**
     * 最低电压
     */
    private BigDecimal mi;

    /**
     * 最高电流
     */
    private BigDecimal ix;

    /**
     * 最低电流
     */
    private BigDecimal ii;

    /**
     * 电池荷电状态
     */
    private BigDecimal soc;

    /**
     * 电池健康状态
     */
    private BigDecimal soh;

    /**
     * 电池温度
     */
    private BigDecimal temperature;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDateTime getSignalTime() {
        return signalTime;
    }

    public void setSignalTime(LocalDateTime signalTime) {
        this.signalTime = signalTime;
    }

    public BigDecimal getMx() {
        return mx;
    }

    public void setMx(BigDecimal mx) {
        this.mx = mx;
    }

    public BigDecimal getMi() {
        return mi;
    }

    public void setMi(BigDecimal mi) {
        this.mi = mi;
    }

    public BigDecimal getIx() {
        return ix;
    }

    public void setIx(BigDecimal ix) {
        this.ix = ix;
    }

    public BigDecimal getIi() {
        return ii;
    }

    public void setIi(BigDecimal ii) {
        this.ii = ii;
    }

    public BigDecimal getSoc() {
        return soc;
    }

    public void setSoc(BigDecimal soc) {
        this.soc = soc;
    }

    public BigDecimal getSoh() {
        return soh;
    }

    public void setSoh(BigDecimal soh) {
        this.soh = soh;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BatterySignal{" +
        "id = " + id +
        ", vehicleId = " + vehicleId +
        ", signalTime = " + signalTime +
        ", mx = " + mx +
        ", mi = " + mi +
        ", ix = " + ix +
        ", ii = " + ii +
        ", soc = " + soc +
        ", soh = " + soh +
        ", temperature = " + temperature +
        ", createTime = " + createTime +
        "}";
    }
}
