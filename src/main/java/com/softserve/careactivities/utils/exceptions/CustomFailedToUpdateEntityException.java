package com.softserve.careactivities.utils.exceptions;

public class CustomFailedToUpdateEntityException extends RuntimeException {
    public CustomFailedToUpdateEntityException() {
        super("Unable to update");
    }

    public CustomFailedToUpdateEntityException(String message) {
        super("Unable to update: \n" + message);
    }

    public CustomFailedToUpdateEntityException(Class<?> clazz) {
        super("Unable to update [" + clazz.getSimpleName().toLowerCase() + "]");
    }
}
