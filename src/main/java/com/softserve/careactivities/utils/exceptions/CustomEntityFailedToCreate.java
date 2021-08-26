package com.softserve.careactivities.utils.exceptions;

public class CustomEntityFailedToCreate extends RuntimeException {
    public CustomEntityFailedToCreate() {
        super("Unable to create");
    }

    public CustomEntityFailedToCreate(String message) {
        super("Unable to create: \n" + message);
    }

    public CustomEntityFailedToCreate(Class<?> clazz) {
        super("Unable to create [" + clazz.getSimpleName().toLowerCase() + "]");
    }
}
