package com.ninjaone.backendinterviewproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDeviceRequest {
    private Integer id;
    private String systemName;
    private Integer deviceTypeId;
    private List<NewDeviceServiceRequest> deviceServiceRequests;
}