package com.ninjaone.backendinterviewproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewServiceCostRequest {
    private String description;
    private BigDecimal value;
    private Boolean mandatory;
    private Integer deviceTypeId;
}
