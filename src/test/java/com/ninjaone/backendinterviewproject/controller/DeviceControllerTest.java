package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.model.dto.DeviceTypeDTO;
import com.ninjaone.backendinterviewproject.model.dto.NewDeviceRequest;
import com.ninjaone.backendinterviewproject.model.dto.UpdateDeviceRequest;
import com.ninjaone.backendinterviewproject.security.BasicAuthWebSecurityConfiguration;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BackendInterviewProjectApplication.class})
@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Import(BasicAuthWebSecurityConfiguration.class)
public class DeviceControllerTest {
    public static final Integer deviceId = 991;
    public static final String systemName = "Test device system";
    public static final Integer deviceTypeId = 1;
    public static final String deviceTypeName = "Test for Device Type";
    public static final Integer customerId = 1111;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeviceService deviceService;

    private DeviceDTO deviceDTO;
    private DeviceTypeDTO deviceTypeDTO;
    HttpHeaders httpHeaders= new HttpHeaders();

    @BeforeEach
    void setup(){
        deviceTypeDTO = new DeviceTypeDTO(deviceTypeId, deviceTypeName);

        deviceDTO = new DeviceDTO();
        deviceDTO.setId(deviceId);
        deviceDTO.setSystemName(systemName);
        deviceDTO.setDeviceType(deviceTypeDTO);

        httpHeaders.add("Authorization","Basic " + ControllerUtils.getEncodedPassword());
    }

    @Test
    void getDeviceDataById() throws Exception {
        when(deviceService.getDeviceById(deviceId)).thenReturn(deviceDTO);

        mockMvc.perform(get("/device/" + deviceId)
                        .headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(deviceDTO)));
    }

    @Test
    void createDevice() throws Exception {
        NewDeviceRequest newDeviceRequest = new NewDeviceRequest();
        newDeviceRequest.setCustomerId(customerId);
        newDeviceRequest.setSystemName(systemName);
        newDeviceRequest.setDeviceTypeId(deviceTypeId);
        newDeviceRequest.setDeviceServiceRequests(new ArrayList<>());

        when(deviceService.createDevice(newDeviceRequest)).thenReturn(deviceDTO);

        String newDeviceRequestString = objectMapper.writeValueAsString(newDeviceRequest);
        String deviceDTOString = objectMapper.writeValueAsString(deviceDTO);
        mockMvc.perform(post("/device/create")
                        .headers(httpHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newDeviceRequestString))
                .andExpect(status().isCreated())
                .andExpect(content().string(deviceDTOString));
    }

    @Test
    void updateDevice() throws Exception {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setId(deviceId);
        updateDeviceRequest.setCustomerId(customerId);
        updateDeviceRequest.setSystemName(systemName);
        updateDeviceRequest.setDeviceTypeId(deviceTypeId);
        updateDeviceRequest.setDeviceServiceRequests(new ArrayList<>());

        when(deviceService.updateDevice(updateDeviceRequest)).thenReturn(deviceDTO);

        String updateDeviceRequestString = objectMapper.writeValueAsString(updateDeviceRequest);
        String deviceDTOString = objectMapper.writeValueAsString(deviceDTO);
        mockMvc.perform(put("/device/update")
                        .headers(httpHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateDeviceRequestString))
                .andExpect(status().isCreated())
                .andExpect(content().string(deviceDTOString));
    }

    @Test
    void deleteDevice() throws Exception {
        doNothing().when(deviceService).deleteDevice(deviceId);

        mockMvc.perform(delete("/device/delete/" + deviceId)
                        .headers(httpHeaders))
                .andExpect(status().isNoContent());
    }
}