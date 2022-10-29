package com.ninjaone.backendinterviewproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceDTO {
    Integer id;
    String systemName;
    DeviceTypeDTO deviceType;
}
