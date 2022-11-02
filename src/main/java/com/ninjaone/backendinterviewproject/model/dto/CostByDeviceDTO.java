package com.ninjaone.backendinterviewproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CostByDeviceDTO {
    private String systemName;
    private String deviceTypeName;
    private BigDecimal costValue;
    private CustomerDTO customer;
}
