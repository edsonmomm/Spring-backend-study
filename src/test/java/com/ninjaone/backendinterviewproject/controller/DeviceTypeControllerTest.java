package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.model.dto.DeviceTypeDTO;
import com.ninjaone.backendinterviewproject.model.dto.NewDeviceTypeRequest;
import com.ninjaone.backendinterviewproject.model.dto.UpdateDeviceTypeRequest;
import com.ninjaone.backendinterviewproject.security.BasicAuthWebSecurityConfiguration;
import com.ninjaone.backendinterviewproject.service.DeviceTypeService;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BackendInterviewProjectApplication.class})
@WebMvcTest(DeviceTypeController.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Import(BasicAuthWebSecurityConfiguration.class)
public class DeviceTypeControllerTest {
    public static final Integer deviceTypeId = 321;
    public static final String deviceName = "Test Device Type";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeviceTypeService deviceTypeService;

    private DeviceTypeDTO deviceTypeDTO;
    HttpHeaders httpHeaders= new HttpHeaders();

    @BeforeEach
    void setup(){
        deviceTypeDTO = new DeviceTypeDTO(deviceTypeId, deviceName);
        httpHeaders.add("Authorization","Basic " + ControllerUtils.getEncodedPassword());
    }

    @Test
    void getDeviceTypeById() throws Exception {
        when(deviceTypeService.getDeviceTypeById(deviceTypeId)).thenReturn(deviceTypeDTO);

        mockMvc.perform(get("/deviceType/" + deviceTypeId).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(deviceTypeDTO)));
    }

    @Test
    void createDeviceType() throws Exception {
        NewDeviceTypeRequest newDeviceTypeRequest = new NewDeviceTypeRequest();
        newDeviceTypeRequest.setDeviceName(deviceName);

        when(deviceTypeService.createDeviceType(newDeviceTypeRequest)).thenReturn(deviceTypeDTO);

        String newDeviceTypeRequestString = objectMapper.writeValueAsString(newDeviceTypeRequest);
        String deviceTypeDTOString = objectMapper.writeValueAsString(deviceTypeDTO);
        mockMvc.perform(post("/deviceType/create")
                        .headers(httpHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newDeviceTypeRequestString))
                .andExpect(status().isCreated())
                .andExpect(content().string(deviceTypeDTOString));
    }

    @Test
    void updateDeviceType() throws Exception {
        UpdateDeviceTypeRequest updateDeviceTypeRequest = new UpdateDeviceTypeRequest();
        updateDeviceTypeRequest.setId(deviceTypeId);
        updateDeviceTypeRequest.setDeviceName(deviceName);

        when(deviceTypeService.updateDeviceType(updateDeviceTypeRequest)).thenReturn(deviceTypeDTO);

        String updateDeviceTypeRequestString = objectMapper.writeValueAsString(updateDeviceTypeRequest);
        String deviceTypeDTOString = objectMapper.writeValueAsString(deviceTypeDTO);
        mockMvc.perform(put("/deviceType/update")
                        .headers(httpHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateDeviceTypeRequestString))
                .andExpect(status().isOk())
                .andExpect(content().string(deviceTypeDTOString));
    }

    @Test
    void deleteCustomer() throws Exception {
        doNothing().when(deviceTypeService).deleteDeviceType(deviceTypeId);

        mockMvc.perform(delete("/deviceType/delete/" + deviceTypeId).headers(httpHeaders))
                .andExpect(status().isNoContent());
    }
}