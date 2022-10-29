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
    Integer id;
    String description;
    BigDecimal value;
    Boolean mandatory;
    DeviceType deviceType;
}