package com.example.xiaomicar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
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
@Data
@TableName("battery_signal")
public class BatterySignal {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
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
