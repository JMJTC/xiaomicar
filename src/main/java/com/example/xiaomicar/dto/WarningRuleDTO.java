package com.example.xiaomicar.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WarningRuleDTO {
    private Long id;
    
    @NotBlank(message = "规则编号不能为空")
    private String ruleCode;
    
    @NotBlank(message = "规则名称不能为空")
    private String ruleName;
    
    @NotBlank(message = "电池类型不能为空")
    private String batteryType;
    
    @NotBlank(message = "规则表达式不能为空")
    private String ruleExpression;
    
    @NotNull(message = "预警等级不能为空")
    private Integer warningLevel;
    
    @NotBlank(message = "预警消息不能为空")
    private String warningMessage;
    
    private Boolean isEnabled;
} 