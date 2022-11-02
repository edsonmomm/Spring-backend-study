package com.ninjaone.backendinterviewproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewDeviceRequest {
    private Integer customerId;
    private String systemName;
    private Integer deviceTypeId;
    private List<NewDeviceServiceRequest> deviceServiceRequests;
}