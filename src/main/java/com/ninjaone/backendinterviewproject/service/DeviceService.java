package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.exception.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Service
public class DeviceService {

    private final ModelMapper modelMapper;

    @Autowired
    private DeviceRepository deviceRepository;

    public DeviceService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        if (modelMapper.getConfiguration()!= null) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }
    }

    public DeviceDTO getDeviceById(Integer id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(String.format("Device id %s was not found.",id) ));

        return modelMapper.map(device, DeviceDTO.class);
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
