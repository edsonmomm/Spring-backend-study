package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.exception.DeviceTypeNotFoundException;
import com.ninjaone.backendinterviewproject.model.DeviceType;
import com.ninjaone.backendinterviewproject.model.dto.DeviceTypeDTO;
import com.ninjaone.backendinterviewproject.model.dto.NewDeviceTypeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Transactional
public class DeviceTypeServiceTest {
    public static final Integer deviceTypeId = 321;
    public static final String deviceName = "Test Device Type";

    @Mock
    private DeviceTypeRepository deviceTypeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DeviceTypeService deviceTypeService;

    private DeviceType deviceTypeEntity;
    private DeviceTypeDTO deviceTypeDTO;

    @BeforeEach
    void setup(){
        deviceTypeEntity = new DeviceType(deviceTypeId, deviceName);
        deviceTypeDTO = new DeviceTypeDTO(deviceTypeId, deviceName);
    }

    @Test
    void getDeviceTypeData() {
        when(deviceTypeRepository.findById(deviceTypeId)).thenReturn(Optional.of(deviceTypeEntity));
        when(modelMapper.map(deviceTypeEntity, DeviceTypeDTO.class)).thenReturn(deviceTypeDTO);

        DeviceTypeDTO actualEntity = deviceTypeService.getDeviceTypeById(deviceTypeId);

        assert actualEntity != null;
        assertEquals(deviceTypeEntity.getId(), actualEntity.getId());
    }

    @Test
    void saveDeviceTypeData() {
        NewDeviceTypeRequest newDeviceTypeRequest = new NewDeviceTypeRequest();
        newDeviceTypeRequest.setDeviceName(deviceName);

        when(deviceTypeService.createDeviceType(newDeviceTypeRequest)).thenReturn(deviceTypeDTO);

        assert deviceTypeDTO.getId() != null;
        assertEquals(deviceTypeEntity.getDeviceName(), newDeviceTypeRequest.getDeviceName());
    }

    @Test
    void deleteDeviceTypeData() throws DeviceTypeNotFoundException {
        Mockito.when(deviceTypeRepository.findById(deviceTypeId)).thenReturn(Optional.of(deviceTypeEntity));

        doNothing().when(deviceTypeRepository).deleteById(deviceTypeId);
        deviceTypeService.deleteDeviceType(deviceTypeId);
        Mockito.verify(deviceTypeRepository, times(1)).deleteById(deviceTypeId);
    }

    @Test
    void deleteDeviceTypeData_deviceNotFound() throws DeviceTypeNotFoundException {
        doNothing().when(deviceTypeRepository).deleteById(deviceTypeId);
        try {
            deviceTypeService.deleteDeviceType(deviceTypeId);
        } catch (DeviceTypeNotFoundException e) {
            assert true;
        }
    }
}