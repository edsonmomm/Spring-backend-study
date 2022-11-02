package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.model.dto.CustomerDTO;
import com.ninjaone.backendinterviewproject.model.dto.NewCustomerDTO;
import com.ninjaone.backendinterviewproject.model.dto.UpdateCustomerDTO;
import com.ninjaone.backendinterviewproject.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class CustomerControllerTest {
    public static final Integer customerId = 111;

    public static final String customerName = "Customer Name For Test";
    public static final String customerNationalId = "0101010101";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    private CustomerDTO customerDTO;
    private NewCustomerDTO newCustomerDTO;
    private UpdateCustomerDTO updateCustomerDTO;

    @BeforeEach
    void setup(){
        customerDTO = new CustomerDTO(customerId, customerName, customerNationalId);
        newCustomerDTO = new NewCustomerDTO(customerName, customerNationalId);
        updateCustomerDTO = new UpdateCustomerDTO(customerId, customerName, customerNationalId);
    }

    @Test
    void getCustomerById() throws Exception {
        when(customerService.getCustomerById(customerId)).thenReturn(customerDTO);

        mockMvc.perform(get("/customer/" + customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(customerDTO)));
    }

    @Test
    void getCustomerByNationalId() throws Exception {
        when(customerService.getCustomerByNationalId(customerNationalId)).thenReturn(customerDTO);

        mockMvc.perform(get("/customer/getByNationalId/" + customerNationalId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(customerDTO)));
    }

    @Test
    void createCustomer() throws Exception {
        when(customerService.createCustomer(newCustomerDTO)).thenReturn(customerDTO);

        String newCustomerDTOString = objectMapper.writeValueAsString(newCustomerDTO);
        String customerDTOString = objectMapper.writeValueAsString(customerDTO);
        mockMvc.perform(post("/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCustomerDTOString))
                .andExpect(status().isCreated())
                .andExpect(content().string(customerDTOString));
    }

    @Test
    void updateCustomer() throws Exception {
        when(customerService.updateCustomer(updateCustomerDTO)).thenReturn(customerDTO);

        String updateCustomerDTOString = objectMapper.writeValueAsString(updateCustomerDTO);
        String customerDTOString = objectMapper.writeValueAsString(customerDTO);
        mockMvc.perform(put("/customer/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateCustomerDTOString))
                .andExpect(status().isOk())
                .andExpect(content().string(customerDTOString));
    }

    @Test
    void deleteCustomer() throws Exception {
        doNothing().when(customerService).deleteCustomerById(customerId);

        mockMvc.perform(delete("/customer/delete/" + customerId))
                .andExpect(status().isNoContent());
    }
}