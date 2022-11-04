package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.*;
import com.ninjaone.backendinterviewproject.exception.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.DeviceServiceCost;
import com.ninjaone.backendinterviewproject.model.DeviceType;
import com.ninjaone.backendinterviewproject.model.dto.*;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Transactional
public class DeviceServiceTest {
    public static final Integer deviceId = 991;
    public static final String systemName = "Test for device system";
    public static final Integer deviceTypeId = 1;
    public static final String deviceTypeName = "Test for Device Type";
    public static final Integer customerId = 1111;
    public static final String customerName = "Customer Name For Test";
    public static final String customerNationalId = "0101010101";


    @Mock
    private ModelMapper modelMapper;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private DeviceTypeRepository deviceTypeRepository;
    @Mock
    private DeviceServiceCostRepository deviceServiceCostRepository;

    @InjectMocks
    private DeviceService deviceService;

    private DeviceDTO deviceDTO;
    private Device deviceEntity;
    private Customer customerEntity;
    private DeviceType deviceTypeEntity;
    private DeviceTypeDTO deviceTypeDTO;

    @BeforeEach
    void setup(){
        customerEntity = new Customer(customerId, customerName, customerNationalId, new ArrayList<>());
        deviceTypeDTO = new DeviceTypeDTO(deviceTypeId, deviceTypeName);
        deviceTypeEntity = new DeviceType(deviceTypeId, deviceTypeName);

        deviceDTO = new DeviceDTO();
        deviceDTO.setId(deviceId);
        deviceDTO.setSystemName(systemName);
        deviceDTO.setDeviceType(deviceTypeDTO);

        deviceEntity = new Device();
        deviceEntity.setId(deviceId);
        deviceEntity.setSystemName(systemName);
    }

    @Test
    void getDeviceDataById() {
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(deviceEntity));
        when(modelMapper.map(deviceEntity, DeviceDTO.class)).thenReturn(deviceDTO);

        DeviceDTO actualEntity = deviceService.getDeviceById(deviceId);

        assert actualEntity != null;
        assertEquals(deviceEntity.getId(), actualEntity.getId());
        assertEquals(deviceEntity.getSystemName(), actualEntity.getSystemName());
    }

    @Test
    void createDevice() {
        NewDeviceRequest newDeviceRequest = new NewDeviceRequest();
        newDeviceRequest.setCustomerId(customerId);
        newDeviceRequest.setSystemName(systemName);
        newDeviceRequest.setDeviceTypeId(deviceTypeId);
        newDeviceRequest.setDeviceServiceRequests(new ArrayList<>());

        when(deviceTypeRepository.findById(deviceTypeId)).thenReturn(Optional.of(deviceTypeEntity));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
        when(deviceRepository.findBySystemName(systemName)).thenReturn(Optional.empty());
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(deviceEntity));
        when(deviceRepository.save(deviceEntity)).thenReturn(deviceEntity);
        when(modelMapper.map(deviceEntity, DeviceDTO.class)).thenReturn(deviceDTO);
        when(modelMapper.map(newDeviceRequest, Device.class)).thenReturn(deviceEntity);

        deviceDTO = deviceService.createDevice(newDeviceRequest);

        assert deviceDTO.getId() != null;
        assertEquals(deviceDTO.getSystemName(), systemName);
        assertEquals(deviceDTO.getDeviceType().getId(), deviceTypeId);
        assertEquals(deviceDTO.getTotalServiceCost(), BigDecimal.ZERO);
    }

    @Test
    void updateDevice() {
        List<DeviceServiceCost> deviceServiceCostList = new ArrayList<>();
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setId(deviceId);
        updateDeviceRequest.setCustomerId(customerId);
        updateDeviceRequest.setSystemName(systemName);
        updateDeviceRequest.setDeviceTypeId(deviceTypeId);
        updateDeviceRequest.setDeviceServiceRequests(new ArrayList<>());

        when(deviceTypeRepository.findById(deviceTypeId)).thenReturn(Optional.of(deviceTypeEntity));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
        when(deviceRepository.findBySystemName(systemName)).thenReturn(Optional.empty());
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(deviceEntity));
        when(deviceRepository.save(deviceEntity)).thenReturn(deviceEntity);
        when(modelMapper.map(deviceEntity, DeviceDTO.class)).thenReturn(deviceDTO);
        when(modelMapper.map(updateDeviceRequest, Device.class)).thenReturn(deviceEntity);
        when(deviceServiceCostRepository.findByDeviceId(deviceId)).thenReturn(deviceServiceCostList);
        doNothing().when(deviceServiceCostRepository).deleteById(0);

        deviceDTO = deviceService.updateDevice(updateDeviceRequest);

        assert deviceDTO.getId() != null;
        assertEquals(deviceDTO.getSystemName(), systemName);
        assertEquals(deviceDTO.getDeviceType().getId(), deviceTypeId);
        assertEquals(deviceDTO.getTotalServiceCost(), BigDecimal.ZERO);
    }

    @Test
    void deleteDevice() {
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(deviceEntity));
        doNothing().when(deviceRepository).deleteById(deviceEntity.getId());

        deviceService.deleteDevice(deviceId);
        Mockito.verify(deviceRepository, times(1)).deleteById(deviceId);
    }

    @Test
    void getDeviceById_notfound() {
        try {
            DeviceDTO actualEntity = deviceService.getDeviceById(deviceId);
        } catch (DeviceNotFoundException e) {
            assert true;
        }
    }
}