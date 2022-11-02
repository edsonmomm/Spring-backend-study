package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.*;
import com.ninjaone.backendinterviewproject.exception.BusinessException;
import com.ninjaone.backendinterviewproject.exception.CustomerNotFoundException;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final ModelMapper modelMapper;

    private final CustomerRepository customerRepository;
    private final DeviceService deviceService;

    public CustomerService(ModelMapper modelMapper
            ,CustomerRepository customerRepository
            ,DeviceService deviceService) {

        this.customerRepository = customerRepository;
        this.deviceService = deviceService;
        this.modelMapper = modelMapper;
        if (modelMapper.getConfiguration()!= null) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }
    }

    /**
     * Query the customer by ID and return customer info
     *
     * @param customerId Cstomer Id
     * @return CustomerDTO
     */
    public CustomerDTO getCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        return modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Query the customer by national identification number and return customer info
     *
     * @param customerNationalNumber Customer National Identification Number
     * @return CustomerDTO
     */
    public CustomerDTO getCustomerByNationalId(String customerNationalNumber) {
        Customer customer = customerRepository.findByNationalIdentificationNumber(customerNationalNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer was not found"));

        return modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Return customer with its devices
     * @param customerId Customer Id
     * @return CustomerDevicesDTO
     */
    public CustomerDevicesDTO getCustomerAndDevices(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        CustomerDevicesDTO customerDevicesDTO = modelMapper.map(customer, CustomerDevicesDTO.class);

        List<DeviceDTO> deviceDTOList = new ArrayList<>();
        if (customer.getDevices() != null && !customer.getDevices().isEmpty()) {
            for (Device device : customer.getDevices()) {
                deviceDTOList.add(deviceService.getDeviceById(device.getId()));
            }
        }
        customerDevicesDTO.setDevices(deviceDTOList);
        return customerDevicesDTO;
    }

    /**
     * Create new customer from new request
     * If already exists a customer with the Narional Identification Number, throws exception
     *
     * @param newCustomerDTO new customer request
     * @return CustomerDTO
     */
    public CustomerDTO createCustomer(NewCustomerDTO newCustomerDTO) {
        // Check if already exists a customer with the provided National Identification Id
        Optional<Customer> customerExists = customerRepository.findByNationalIdentificationNumber(newCustomerDTO.getNationalIdentificationNumber());

        if (customerExists.isPresent())
            throw new BusinessException("A customer with provided national number already exists.");

        Customer customer = customerRepository.save(modelMapper.map(newCustomerDTO, Customer.class));
        return modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Update the customer from update request
     * If tries to update the National Id Number and already exists another customer with new id,
     * update will be not allowed and an exception will be thrown
     *
     * @param updateCustomerDTO update customer request
     * @return CustomerDTO
     */
    public CustomerDTO updateCustomer(UpdateCustomerDTO updateCustomerDTO) {
        Customer customer = customerRepository.findById(updateCustomerDTO.getId())
                .orElseThrow(() -> new CustomerNotFoundException(updateCustomerDTO.getId()));

        // Verify if exists another customer with the same National Identification Number.
        // If exists, don't let update the information
        Optional<Customer> customerWithSameNationalId = customerRepository.findByNationalIdentificationNumberAndIdNot(
                updateCustomerDTO.getNationalIdentificationNumber(), updateCustomerDTO.getId());

        if (customerWithSameNationalId.isPresent())
            throw new BusinessException("There is another customer with the same National Id Number, update not allowed.");

        // Updates the customer attributes
        customer.setName(updateCustomerDTO.getName());
        customer.setNationalIdentificationNumber(updateCustomerDTO.getNationalIdentificationNumber());
        customer = customerRepository.save(customer);

        return  modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Delete the customer by id
     * Throw exception if not found
     *
     * @param customerId Customer Id
     */
    public void deleteCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        try {
            customerRepository.deleteById(customer.getId());
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(String.format("Customer with ID %s is been used. Cannot be deleted.", customer.getId()));
        }
    }
}