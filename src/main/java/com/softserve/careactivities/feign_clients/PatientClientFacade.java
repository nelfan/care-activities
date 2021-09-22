package com.softserve.careactivities.feign_clients;

import com.softserve.careactivities.domain.dto.CareActivityExtendedDTO;
import com.softserve.careactivities.domain.entities.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
@AllArgsConstructor
public class PatientClientFacade {

    private PatientsClient patientsClient;

    public boolean checkIsPatientPediatric(CareActivityExtendedDTO careActivity) {
        Patient patient = patientsClient.getPatientByMPI(careActivity.getMasterPatientIdentifier());
        LocalDate birthDate = patient.getDateOfBirth();
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    public boolean checkIsPatientPediatric(String MPI) {
        Patient patient = patientsClient.getPatientByMPI(MPI);
        LocalDate birthDate = patient.getDateOfBirth();
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    public boolean checkIsPatientPediatric(Patient patient) {
        LocalDate birthDate = patient.getDateOfBirth();
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }
}
