package com.epf.rentmanager.service.Exceptions;

public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }
    public ServiceException(String message) {
        super(message);
    }
}
