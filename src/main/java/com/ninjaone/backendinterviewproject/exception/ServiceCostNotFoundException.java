package com.ninjaone.backendinterviewproject.exception;

import javax.persistence.EntityNotFoundException;

/**
 * Handle the exception for service not found
 */
public class ServiceCostNotFoundException extends EntityNotFoundException {
    public ServiceCostNotFoundException() {
        super();
    }

    public ServiceCostNotFoundException(String message) {
        super(message);
    }

    public ServiceCostNotFoundException(Integer id) {
        super(String.format("Service Cost with id %s was not found.",id));
    }
}