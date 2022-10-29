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

    /**
     * return the cost of all devices
     * considering that all items available would be selected.
     *
     * @return BigDecimal
     */
    public BigDecimal getCompleteDeviceTotalCost() {
        return deviceRepository.getCompleteDeviceTotalCost();
    }

    /**
     * return the cost for all devices
     * considering only the services selected.
     *
     * @return BigDecimal
     */
    public BigDecimal getDeviceTotalCost() {
        return deviceRepository.getDeviceTotalCost();
    }
    public void deleteDeviceEntity(Integer id) {
        deviceRepository.deleteById(id);
    }
}
