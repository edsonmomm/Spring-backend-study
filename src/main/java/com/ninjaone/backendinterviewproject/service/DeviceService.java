package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    public Optional<Device> getDeviceEntity(Integer id) {
        return deviceRepository.findById(id);
    }

    public BigDecimal getTotalCost() {
        return deviceRepository.getTotalCost();
    }

    public void deleteDeviceEntity(Integer id) {
        deviceRepository.deleteById(id);
    }
}
