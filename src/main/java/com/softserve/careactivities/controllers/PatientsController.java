package com.softserve.careactivities.controllers;

import com.softserve.careactivities.domain.entities.Patient;
import com.softserve.careactivities.feign_clients.PatientsClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientsController {

    private final PatientsClient patientsClient;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientsClient.getAllPatients();
    }

    @GetMapping("/{MPI}")
    public Patient getPatientByMPI(@PathVariable String MPI) {
        return patientsClient.getPatientByMPI(MPI);
    }

}
