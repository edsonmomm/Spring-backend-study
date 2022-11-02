package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.exception.CustomerNotFoundException;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.dto.CustomerDTO;
import com.ninjaone.backendinterviewproject.model.dto.NewCustomerDTO;
import com.ninjaone.backendinterviewproject.model.dto.UpdateCustomerDTO;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceTest {
    public static final Integer customerId = 111;
    public static final String customerName = "Customer Name For Test";
    public static final String customerNationalId = "0101010101";
    public static final String updCustomerName = "New Customer Name for Test";
    public static final String updCustomerNationalId = "0202020202";

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer customerEntity;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setup(){
        customerEntity = new Customer(customerId, customerName, customerNationalId);
        customerDTO = new CustomerDTO(customerId, customerName, customerNationalId);
    }

    @Test
    void getCustomerById() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
        when(modelMapper.map(customerEntity, CustomerDTO.class)).thenReturn(customerDTO);

        CustomerDTO actualEntity = customerService.getCustomerById(customerId);

        assert actualEntity != null;
        assertEquals(customerEntity.getId(), actualEntity.getId());
        assertEquals(customerEntity.getName(), actualEntity.getName());
        assertEquals(customerEntity.getNationalIdentificationNumber(), actualEntity.getNationalIdentificationNumber());
    }

    @Test
    void getCustomerByNationalId() {
        when(customerRepository.findByNationalIdentificationNumber(customerNationalId)).thenReturn(Optional.of(customerEntity));
        when(modelMapper.map(customerEntity, CustomerDTO.class)).thenReturn(customerDTO);

        CustomerDTO actualEntity = customerService.getCustomerByNationalId(customerNationalId);

        assert actualEntity != null;
        assertEquals(customerEntity.getId(), actualEntity.getId());
        assertEquals(customerEntity.getName(), actualEntity.getName());
        assertEquals(customerEntity.getNationalIdentificationNumber(), actualEntity.getNationalIdentificationNumber());
    }

    @Test
    void createCustomer() {
        NewCustomerDTO newCustomerDTO = new NewCustomerDTO(customerName, customerNationalId);
        when(customerService.createCustomer(newCustomerDTO)).thenReturn(customerDTO);

        assert customerDTO.getId() != null;
        assertEquals(customerEntity.getName(), newCustomerDTO.getName());
        assertEquals(customerEntity.getNationalIdentificationNumber(), newCustomerDTO.getNationalIdentificationNumber());

    }

    @Test
    void updateCustomer() {
        // Create a request changing the name and national id
        UpdateCustomerDTO updateCustomerDTO = new UpdateCustomerDTO(
                customerEntity.getId(),updCustomerName,updCustomerNationalId);

        when(customerRepository.findById(updateCustomerDTO.getId())).thenReturn(Optional.of(customerEntity));
        when(customerService.updateCustomer(updateCustomerDTO)).thenReturn(customerDTO);

        assert customerDTO.getId() != null;
        assertEquals(customerDTO.getId(), updateCustomerDTO.getId());
        assertNotEquals(customerDTO.getName(), updateCustomerDTO.getName());
        assertNotEquals(customerDTO.getNationalIdentificationNumber(), updateCustomerDTO.getNationalIdentificationNumber());
    }

    @Test
    void deleteCustomer() {
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));

        doNothing().when(customerRepository).deleteById(customerId);
        customerService.deleteCustomerById(customerId);
        Mockito.verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void getCustomerById_NotFound() {
        try {
            customerService.getCustomerById(customerId);
        } catch (CustomerNotFoundException e) {
            assert true;
        }
    }

    @Test
    void getCustomerByNationalId_NotFound() {
        try {
            customerService.getCustomerByNationalId(customerNationalId);
        } catch (CustomerNotFoundException e) {
            assert true;
        }
    }
}