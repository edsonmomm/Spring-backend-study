package com.ninjaone.backendinterviewproject.model.dto;

import com.ninjaone.backendinterviewproject.model.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceCostDTO {
    private Integer id;
    private String description;
    private BigDecimal value;
    private Boolean mandatory;
    private DeviceType deviceType;
}