package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.database.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.exception.BusinessException;
import com.ninjaone.backendinterviewproject.exception.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.exception.DeviceTypeNotFoundException;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.DeviceServiceCost;
import com.ninjaone.backendinterviewproject.model.DeviceType;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.model.dto.NewDeviceRequest;
import com.ninjaone.backendinterviewproject.model.dto.ServiceCostDTO;
import com.ninjaone.backendinterviewproject.model.dto.UpdateDeviceRequest;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class DeviceService {

    private final ModelMapper modelMapper;

    private final DeviceRepository deviceRepository;
    private final DeviceTypeRepository deviceTypeRepository;

    public DeviceService(ModelMapper modelMapper, DeviceRepository deviceRepository, DeviceTypeRepository deviceTypeRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceTypeRepository = deviceTypeRepository;
        this.modelMapper = modelMapper;
        if (modelMapper.getConfiguration()!= null) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }
    }

    /**
     * Return device by id.
     * Return exception if not found
     *
     * @param id
     * @return
     */
    public DeviceDTO getDeviceById(Integer id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id) );

        DeviceDTO deviceDTO = modelMapper.map(device, DeviceDTO.class);

        deviceDTO.setServices(new ArrayList<>());
        if (device.getDeviceServiceCosts() != null && !device.getDeviceServiceCosts().isEmpty()) {
            for (DeviceServiceCost deviceServiceCost : device.getDeviceServiceCosts()) {
                ServiceCostDTO serviceCostDTO = modelMapper.map(deviceServiceCost.getChosenServiceCost(), ServiceCostDTO.class);
                deviceDTO.getServices().add(serviceCostDTO);
            }
        }

        return deviceDTO;
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

    // Create
    public DeviceDTO createDevice(@RequestBody NewDeviceRequest newDeviceRequest) {
        // Verify if the device type exists
        DeviceType deviceType = deviceTypeRepository.findById(newDeviceRequest.getDeviceTypeId())
                .orElseThrow(() -> new DeviceTypeNotFoundException(newDeviceRequest.getDeviceTypeId()));

        // Verify if exists another device with same name
        Optional<Device> deviceExists = deviceRepository.findBySystemName(newDeviceRequest.getSystemName());
        if (deviceExists.isPresent())
            throw new BusinessException("Device " + newDeviceRequest.getSystemName() + " already exists");

        Device device = modelMapper.map(newDeviceRequest, Device.class);
        device.setDeviceType(deviceType);
        device = deviceRepository.save(device);
        return modelMapper.map(device, DeviceDTO.class);
    }

    public DeviceDTO updateDevice(@RequestBody UpdateDeviceRequest updateDeviceRequest) {
        // Verify if the device type exists
        DeviceType deviceType = deviceTypeRepository.findById(updateDeviceRequest.getDeviceTypeId())
                .orElseThrow(() -> new DeviceTypeNotFoundException(updateDeviceRequest.getDeviceTypeId()));

        // Verify if exists another device with same name
        Device deviceExists = deviceRepository.findById(updateDeviceRequest.getId())
                .orElseThrow(() -> new DeviceNotFoundException(updateDeviceRequest.getId()));

        Device device = modelMapper.map(updateDeviceRequest, Device.class);
        device.setDeviceType(deviceType);
        device = deviceRepository.save(device);

        return modelMapper.map(device, DeviceDTO.class);
    }

    /**
     * Delete the device by its id.
     * If not found, raise exception
     *
     * @param id
     */
    public void deleteDevice(Integer id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id) );
        try {
            deviceRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(String.format("Device with ID %s is been used. Cannot be deleted.", id));
        }
    }
}
