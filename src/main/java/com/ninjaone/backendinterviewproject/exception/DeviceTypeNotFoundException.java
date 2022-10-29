package com.ninjaone.backendinterviewproject.exception;

import javax.persistence.EntityNotFoundException;

/**
 * Handle the exception for devices not found
 */
public class DeviceTypeNotFoundException extends EntityNotFoundException {

    public DeviceTypeNotFoundException() {
        super();
    }

    public DeviceTypeNotFoundException(String message) {
        super(message);
    }

    public DeviceTypeNotFoundException(Integer deviceId) {
        super(String.format("Device Type with id %s was not found.",deviceId));
    }
}