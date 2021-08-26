package com.softserve.careactivities.utils.exceptions;

public class CustomFailedToDeleteEntityException extends RuntimeException {
    public CustomFailedToDeleteEntityException() {
        super("Unable to delete");
    }

    public CustomFailedToDeleteEntityException(String message) {
        super("Unable to delete: \n" + message);
    }

    public CustomFailedToDeleteEntityException(Class<?> clazz) {
        super("Unable to delete [" + clazz.getSimpleName().toLowerCase() + "]");
    }
}
