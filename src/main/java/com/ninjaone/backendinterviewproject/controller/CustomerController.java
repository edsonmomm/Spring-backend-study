package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.dto.CustomerDTO;
import com.ninjaone.backendinterviewproject.model.dto.NewCustomerDTO;
import com.ninjaone.backendinterviewproject.model.dto.UpdateCustomerDTO;
import com.ninjaone.backendinterviewproject.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private  final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Find the user by id and return user info
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        CustomerDTO customerDTO = customerService.getCustomerById(id);

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    /**
     * Find the customer by national identification number and return customer info
     */
    @GetMapping("/getByNationalId/{nationalId}")
    public ResponseEntity<CustomerDTO> getCustomerByNationalId(@PathVariable String nationalId) {
        CustomerDTO customerDTO = customerService.getCustomerByNationalId(nationalId);

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    /**
     * Create new customer from new request
     * If already exists a customer with the Narional Identification Number, throws exception
     *
     * @param newCustomerDTO
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody NewCustomerDTO newCustomerDTO) {
        CustomerDTO customerDTO = customerService.createCustomer(newCustomerDTO);

        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    /**
     * Update the customer from update request
     * If tries to update the National Id Number and already exists another customer with new id,
     * update will be not allowed and an exception will be thrown
     *
     * @param updateCustomerDTO
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody UpdateCustomerDTO updateCustomerDTO) {
        CustomerDTO customerDTO = customerService.updateCustomer(updateCustomerDTO);

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    /**
     * Delete a customer by Id
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerById(@PathVariable Integer id) {
        customerService.deleteCustomerById(id);
    }
}