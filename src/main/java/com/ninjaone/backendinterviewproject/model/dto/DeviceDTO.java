package com.ninjaone.backendinterviewproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceDTO {
    private Integer id;
    private String systemName;
    private DeviceTypeDTO deviceType;
    private List<ServiceCostDTO> services;
}
