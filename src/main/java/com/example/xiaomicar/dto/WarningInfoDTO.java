package com.example.xiaomicar.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class WarningInfoDTO {
    private Long id;
    
    @NotNull(message = "车辆ID不能为空")
    private Long vehicleId;
    
    @NotNull(message = "规则ID不能为空")
    private Long ruleId;
    
    @NotNull(message = "预警等级不能为空")
    private Integer warningLevel;
    
    @NotBlank(message = "预警消息不能为空")
    private String warningMessage;
    
    @NotNull(message = "预警时间不能为空")
    private LocalDateTime warningTime;
    
    private Boolean isHandled;
    private LocalDateTime handleTime;
    private String handleResult;
} 