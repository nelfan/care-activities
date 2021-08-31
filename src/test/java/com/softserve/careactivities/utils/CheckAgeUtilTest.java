package com.softserve.careactivities.utils;

import com.softserve.careactivities.domain.entities.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckAgeUtilTest {

    static LocalDate old;

    static LocalDate young;

    static Patient patient;

    @BeforeEach
    void setUp() {
        old = LocalDate.of(1960, 10, 10);
        young = LocalDate.of(2025, 11, 11);

        patient = new Patient();
    }

    @Test
    @DisplayName("patient is above 18")
    void checkIsPatientPediatric() {
        patient.setDateOfBirth(old);

        Boolean actual = CheckAgeUtil.checkIsPatientPediatric(patient);

        assertEquals(actual, true);
    }

    @Test
    @DisplayName("patient is under 18")
    void checkIsPatientPediatric2() {
        patient.setDateOfBirth(young);

        Boolean actual = CheckAgeUtil.checkIsPatientPediatric(patient);

        assertEquals(actual, false);
    }
}