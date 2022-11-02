package com.ninjaone.backendinterviewproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDevicesDTO extends CustomerDTO {
    private BigDecimal totalDeviceCost;
    private List<DeviceDTO> devices;

    /**
     * return the sum of all device service costs
     */
    public BigDecimal getTotalDeviceCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (devices != null && !devices.isEmpty()) {
            totalCost = devices.stream().map(DeviceDTO::getTotalServiceCost).reduce(BigDecimal::add).get();
        }
        return totalCost;
    }

}