package com.edsandrof.softdesign.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 400660744461460474L;

    public ResourceNotFoundException(Object id) {
        super("Resource id " + id + " not found");
    }
}
