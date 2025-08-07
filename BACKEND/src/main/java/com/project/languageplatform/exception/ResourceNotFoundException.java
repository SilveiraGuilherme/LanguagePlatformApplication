package com.project.languageplatform.exception;

/**
 * Custom exception thrown when a requested resource is not found in the system.
 * Typically used for handling cases such as missing entities or invalid identifiers.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
