package com.softserve.careactivities.utils.exceptions;

public class PatientIsNotActiveException extends RuntimeException {
    public PatientIsNotActiveException() {
        super("Patient is not active");
    }

    public PatientIsNotActiveException(String message) {
        super("Patient is not active : \n" + message);
    }

}
