package com.ninjaone.backendinterviewproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceDTO {
    private Integer id;
    private String systemName;
    private BigDecimal totalServiceCost;
    private DeviceTypeDTO deviceType;
    private List<ServiceCostDTO> services;

    /**
     * Return the sum of the services chosen by costumer for the device
     */
    public BigDecimal getTotalServiceCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (services != null && !services.isEmpty()) {
            totalCost = services.stream().map(ServiceCostDTO::getValue).reduce(BigDecimal::add).get();
        }
        return totalCost;
    }
}
