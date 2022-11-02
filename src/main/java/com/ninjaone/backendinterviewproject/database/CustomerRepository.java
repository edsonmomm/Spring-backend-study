package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    /**
     * Return the customer by is National Id Number
     *
     * @param nationalIdentificationNumber
     * @return
     */
    Optional<Customer> findByNationalIdentificationNumber(String nationalIdentificationNumber);

    /**
     * Returns a customer with National Id Number, but different id
     *
     * @param nationalIdentificationNumber
     * @param id
     * @return
     */
    Optional<Customer> findByNationalIdentificationNumberAndIdNot(String nationalIdentificationNumber, Integer id);
}