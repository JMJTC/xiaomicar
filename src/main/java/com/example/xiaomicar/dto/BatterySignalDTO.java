package com.example.xiaomicar.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BatterySignalDTO {
    private Long id;
    
    @NotNull(message = "车辆ID不能为空")
    private Long vehicleId;
    
    @NotNull(message = "信号时间不能为空")
    private LocalDateTime signalTime;
    
    private BigDecimal mx;
    private BigDecimal mi;
    private BigDecimal ix;
    private BigDecimal ii;
    private BigDecimal soc;
    private BigDecimal soh;
    private BigDecimal temperature;
} 