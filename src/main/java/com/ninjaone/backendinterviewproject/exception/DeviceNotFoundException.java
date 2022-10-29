package com.ninjaone.backendinterviewproject.exception;

public class DeviceNotFoundException  extends RuntimeException {

    public DeviceNotFoundException() {
        super();
    }

    public DeviceNotFoundException(String message) {
        super(message);
    }
}
