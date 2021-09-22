package com.softserve.careactivities.feign_clients;

import com.softserve.careactivities.domain.dto.CareActivityExtendedDTO;
import com.softserve.careactivities.domain.entities.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PatientClientFacadeTest {

    @InjectMocks
    PatientClientFacade patientClientFacade;

    @Mock
    PatientsClient patientsClient;

    static Patient patient;

    static CareActivityExtendedDTO careActivityExtendedDTO;

    static LocalDate adult;

    static LocalDate underage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientClientFacade = new PatientClientFacade(patientsClient);

        adult = LocalDate.of(1990, 10, 10);

        underage = LocalDate.of(2010, 11, 11);

        patient = new Patient();
        patient.setMpi("MPI");
        patient.setActive(true);

        careActivityExtendedDTO = new CareActivityExtendedDTO();
        careActivityExtendedDTO.setMasterPatientIdentifier("MPI");
    }

    @Test
    @DisplayName("When patient is >= 18, parameter type = CareActivityExtendedDTO")
    void shouldCheckIsPatientPediatricWithParameterTypeCareActivityExtendedDTO() {
        CareActivityExtendedDTO careActivity = careActivityExtendedDTO;
        Patient patientOld = patient;
        patientOld.setDateOfBirth(adult);

        when(patientsClient.getPatientByMPI(careActivity.getMasterPatientIdentifier())).thenReturn(patientOld);

        boolean isPatientPediatric = patientClientFacade.checkIsPatientPediatric(careActivity);

        assertTrue(isPatientPediatric);

        verify(patientsClient, times(1)).getPatientByMPI(careActivity.getMasterPatientIdentifier());
    }

    @Test
    @DisplayName("When patient is <= 18, parameter type = CareActivityExtendedDTO")
    void shouldCheckIsPatientPediatricWithParameterTypeCareActivityExtendedDTO2() {
        CareActivityExtendedDTO careActivity = careActivityExtendedDTO;
        Patient patientYoung = patient;
        patientYoung.setDateOfBirth(underage);

        when(patientsClient.getPatientByMPI(careActivity.getMasterPatientIdentifier())).thenReturn(patientYoung);

        boolean isPatientPediatric = patientClientFacade.checkIsPatientPediatric(careActivity);

        assertFalse(isPatientPediatric);

        verify(patientsClient, times(1)).getPatientByMPI(careActivity.getMasterPatientIdentifier());
    }

    @Test
    @DisplayName("When patient is >= 18, parameter type = Patient")
    void shouldCheckIsPatientPediatricWithParameterTypePatient() {
        Patient patientOld = patient;
        patientOld.setDateOfBirth(adult);

        boolean isPatientPediatric = patientClientFacade.checkIsPatientPediatric(patientOld);

        assertTrue(isPatientPediatric);
    }

    @Test
    @DisplayName("When patient is <= 18, parameter type = Patient")
    void shouldCheckIsPatientPediatricWithParameterTypePatient2() {
        Patient patientYoung = patient;
        patientYoung.setDateOfBirth(underage);

        boolean isPatientPediatric = patientClientFacade.checkIsPatientPediatric(patientYoung);

        assertFalse(isPatientPediatric);
    }

    @Test
    @DisplayName("When patient is >= 18, parameter type = String")
    void shouldCheckIsPatientPediatricWithParameterTypeString() {
        Patient patientOld = patient;
        patientOld.setDateOfBirth(adult);

        when(patientsClient.getPatientByMPI(patientOld.getMpi())).thenReturn(patientOld);

        boolean isPatientPediatric = patientClientFacade.checkIsPatientPediatric(patientOld.getMpi());

        assertTrue(isPatientPediatric);

        verify(patientsClient, times(1)).getPatientByMPI(patientOld.getMpi());
    }

    @Test
    @DisplayName("When patient is <= 18, parameter type = String")
    void shouldCheckIsPatientPediatricWithParameterTypeString2() {
        Patient patientYoung = patient;
        patientYoung.setDateOfBirth(underage);

        when(patientsClient.getPatientByMPI(patientYoung.getMpi())).thenReturn(patientYoung);

        boolean isPatientPediatric = patientClientFacade.checkIsPatientPediatric(patientYoung.getMpi());

        assertFalse(isPatientPediatric);

        verify(patientsClient, times(1)).getPatientByMPI(patientYoung.getMpi());
    }
}