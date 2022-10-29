package com.ninjaone.backendinterviewproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceServiceDTO {
    private Integer id;
    private DeviceTypeDTO deviceType;
    private ServiceCostDTO serviceCost;
}
