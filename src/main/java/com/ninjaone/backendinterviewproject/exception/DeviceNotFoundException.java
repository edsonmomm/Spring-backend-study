package com.ninjaone.backendinterviewproject.exception;


import javax.persistence.EntityNotFoundException;

/**
 * Handle the exception for devices not found
 */
public class DeviceNotFoundException extends EntityNotFoundException {

    public DeviceNotFoundException() {
        super();
    }

    public DeviceNotFoundException(String message) {
        super(message);
    }

    public DeviceNotFoundException(Integer deviceId) {
        super(String.format("Device id %s was not found.",deviceId));
    }

}
