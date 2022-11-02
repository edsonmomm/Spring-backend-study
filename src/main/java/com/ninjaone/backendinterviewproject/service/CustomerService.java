package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.*;
import com.ninjaone.backendinterviewproject.exception.BusinessException;
import com.ninjaone.backendinterviewproject.exception.CustomerNotFoundException;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.dto.CustomerDTO;
import com.ninjaone.backendinterviewproject.model.dto.NewCustomerDTO;
import com.ninjaone.backendinterviewproject.model.dto.UpdateCustomerDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final ModelMapper modelMapper;

    private final CustomerRepository customerRepository;

    public CustomerService(ModelMapper modelMapper, CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        if (modelMapper.getConfiguration()!= null) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }
    }

    /**
     * Query the customer by ID and return customer info
     *
     * @param customerId
     * @return
     */
    public CustomerDTO getCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        return modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Query the customer by national identification number and return customer info
     *
     * @param customerNationalNumber
     * @return
     */
    public CustomerDTO getCustomerByNationalId(String customerNationalNumber) {
        Customer customer = customerRepository.findByNationalIdentificationNumber(customerNationalNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer was not found"));

        return modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Create new customer from new request
     * If already exists a customer with the Narional Identification Number, throws exception
     *
     * @param newCustomerDTO
     * @return
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
     * @param updateCustomerDTO
     * @return
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
     * @param customerId
     */
    public void deleteCustomerById(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            try {
                customerRepository.deleteById(customerId);
            } catch (DataIntegrityViolationException e) {
                throw new BusinessException(String.format("Customer with ID %s is been used. Cannot be deleted.", customerId));
            }

        }
    }
}