package com.example.xiaomicar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 预警信息表
 * </p>
 *
 * @author jmj
 * @since 2025-05-18
 */
@Data
@TableName("warning_info")
public class WarningInfo implements Serializable {

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
     * 规则ID
     */
    private Long ruleId;

    /**
     * 预警等级
     */
    private Integer warningLevel;

    /**
     * 预警消息
     */
    private String warningMessage;

    /**
     * 预警时间
     */
    private LocalDateTime warningTime;

    /**
     * 是否处理
     */
    private Boolean isHandled;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @Override
    public String toString() {
        return "WarningInfo{" +
        "id = " + id +
        ", vehicleId = " + vehicleId +
        ", ruleId = " + ruleId +
        ", warningLevel = " + warningLevel +
        ", warningMessage = " + warningMessage +
        ", warningTime = " + warningTime +
        ", isHandled = " + isHandled +
        ", handleTime = " + handleTime +
        ", handleResult = " + handleResult +
        ", createTime = " + createTime +
        "}";
    }
}
