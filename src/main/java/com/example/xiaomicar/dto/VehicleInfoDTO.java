package com.example.xiaomicar.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class VehicleInfoDTO {
    private Long id;
    
    @NotBlank(message = "车辆唯一标识不能为空")
    private String vid;
    
    @NotBlank(message = "车架编号不能为空")
    private String frameNumber;
    
    @NotBlank(message = "电池类型不能为空")
    private String batteryType;
    
    @NotNull(message = "总里程不能为空")
    private BigDecimal totalMileage;
    
    @NotNull(message = "电池健康状态不能为空")
    private Integer batteryHealth;
} 