package com.ninjaone.backendinterviewproject.exception;

import javax.persistence.EntityNotFoundException;

public class CustomerNotFoundException extends EntityNotFoundException {
    public CustomerNotFoundException() {
        super();
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(Integer customerId) {
        super(String.format("Customer id %s was not found.",customerId));
    }

}