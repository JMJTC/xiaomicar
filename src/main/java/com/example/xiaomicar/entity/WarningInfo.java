package com.example.xiaomicar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private Byte warningLevel;

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

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Byte getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(Byte warningLevel) {
        this.warningLevel = warningLevel;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public LocalDateTime getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(LocalDateTime warningTime) {
        this.warningTime = warningTime;
    }

    public Boolean getIsHandled() {
        return isHandled;
    }

    public void setIsHandled(Boolean isHandled) {
        this.isHandled = isHandled;
    }

    public LocalDateTime getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(LocalDateTime handleTime) {
        this.handleTime = handleTime;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

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
