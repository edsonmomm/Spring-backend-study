package com.ninjaone.backendinterviewproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewServiceCostRequest {
    String description;
    BigDecimal value;
    Boolean mandatory;
    Integer deviceTypeId;
}
