package com.example.xiaomicar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * <p>
 * 预警规则表
 * </p>
 *
 * @author jmj
 * @since 2025-05-18
 */
@Data
@TableName("warning_rule")
public class WarningRule {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 规则编号
     */
    private String ruleCode;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 适用电池类型
     */
    private String batteryType;

    /**
     * 规则表达式
     */
    private String ruleExpression;

    /**
     * 预警等级(0-5)
     */
    private Integer warningLevel;

    /**
     * 预警消息
     */
    private String warningMessage;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

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
        return "WarningRule{" +
        "id = " + id +
        ", ruleCode = " + ruleCode +
        ", ruleName = " + ruleName +
        ", batteryType = " + batteryType +
        ", ruleExpression = " + ruleExpression +
        ", warningLevel = " + warningLevel +
        ", warningMessage = " + warningMessage +
        ", isEnabled = " + isEnabled +
        ", createTime = " + createTime +
        ", updateTime = " + updateTime +
        "}";
    }
}
