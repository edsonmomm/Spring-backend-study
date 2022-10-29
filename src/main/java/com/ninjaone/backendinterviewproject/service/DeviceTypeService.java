package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.exception.BusinessException;
import com.ninjaone.backendinterviewproject.exception.DeviceTypeNotFoundException;
import com.ninjaone.backendinterviewproject.model.DeviceType;
import com.ninjaone.backendinterviewproject.model.dto.DeviceTypeDTO;
import com.ninjaone.backendinterviewproject.model.dto.NewDeviceTypeRequest;
import com.ninjaone.backendinterviewproject.model.dto.UpdateDeviceTypeRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceTypeService {
    private final ModelMapper modelMapper;

    private final DeviceTypeRepository deviceTypeRepository;

    public DeviceTypeService(ModelMapper modelMapper, DeviceTypeRepository deviceTypeRepository) {
        this.deviceTypeRepository = deviceTypeRepository;
        this.modelMapper = modelMapper;
        if (modelMapper.getConfiguration()!= null) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }
    }

    /**
     * Return device type by id.
     * Return exception if not found
     */
    public DeviceTypeDTO getDeviceTypeById(Integer id) {
        DeviceType deviceType = deviceTypeRepository.findById(id).orElseThrow(() -> new DeviceTypeNotFoundException(id) );

        return modelMapper.map(deviceType, DeviceTypeDTO.class);
    }

    /**
     * Creates a device type
     * throw exception if the device already exists with same name
     *
     */
    public DeviceTypeDTO createDeviceType(NewDeviceTypeRequest newDeviceTypeRequest) {
        // Verify if already exists a device with same name
        Optional<DeviceType> deviceTypeExists = deviceTypeRepository.findByDeviceName(newDeviceTypeRequest.getDeviceName());

        if (deviceTypeExists.isPresent())
            throw new BusinessException("Device " + newDeviceTypeRequest.getDeviceName() + " already exists");

        DeviceType deviceType = deviceTypeRepository.save(modelMapper.map(newDeviceTypeRequest, DeviceType.class));

        return modelMapper.map(deviceType, DeviceTypeDTO.class);
    }

    /**
     * Update a device type
     */
    public DeviceTypeDTO updateDeviceType(UpdateDeviceTypeRequest updateDeviceTypeRequest) {
        // Verify device exists, so can update
        DeviceType deviceType = deviceTypeRepository.findById(updateDeviceTypeRequest.getId())
                .orElseThrow(() -> new DeviceTypeNotFoundException(updateDeviceTypeRequest.getId()) );

        deviceType.setDeviceName(updateDeviceTypeRequest.getDeviceName());
        deviceType = deviceTypeRepository.save(modelMapper.map(updateDeviceTypeRequest, DeviceType.class));

        return modelMapper.map(deviceType, DeviceTypeDTO.class);
    }

    // Delete a device type
    public void deleteDeviceType(Integer deviceTypeId) {
        DeviceType deviceTypeExists = deviceTypeRepository.findById(deviceTypeId).orElseThrow(() -> new DeviceTypeNotFoundException(deviceTypeId) );

        try {
            deviceTypeRepository.deleteById(deviceTypeExists.getId());
        } catch (DataIntegrityViolationException e){
            throw new BusinessException(String.format("Device Type %s is been used. Cannot be deleted.", deviceTypeId));
        }
    }
}