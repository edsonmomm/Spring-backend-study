package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.*;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.CustomerDTO;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Transactional
public class DeviceServiceTest {
    //public static final Integer customerId = 1111;
    public static final Integer deviceId = 991;
    public static final String systemName = "Test device system";
    public static final Integer deviceTypeId = 1;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    private DeviceDTO deviceDTO;
    private Device deviceEntity;
    //private CustomerDTO customerDTO;
    //private Customer customerEntity;

    @BeforeEach
    void setup(){
        deviceDTO = new DeviceDTO();
        deviceDTO.setId(deviceId);
        deviceDTO.setSystemName(systemName);

        deviceEntity = new Device();
        deviceEntity.setId(deviceId);
        deviceEntity.setSystemName(systemName);
    }
    // Test getDeviceById
    @Test
    void getDeviceDataById() {
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(deviceEntity));
        when(modelMapper.map(deviceEntity, DeviceDTO.class)).thenReturn(deviceDTO);

        DeviceDTO actualEntity = deviceService.getDeviceById(deviceId);

        assert actualEntity != null;
        assertEquals(deviceEntity.getId(), actualEntity.getId());
        assertEquals(deviceEntity.getSystemName(), actualEntity.getSystemName());
    }
    // Test getCostByDevice
    // Test createDevice
    // Test updateDevice
    // Test deleteDevice
    // Test getDeviceById_notfound
}