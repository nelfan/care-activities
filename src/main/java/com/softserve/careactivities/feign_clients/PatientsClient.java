package com.softserve.careactivities.feign_clients;

import com.softserve.careactivities.domain.entities.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "patients", url = "http://localhost:8086/patients")
public interface PatientsClient {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    List<Patient> getAllPatients();

    @RequestMapping(method = RequestMethod.GET, value = "/{MPI}")
    Patient getPatientByMPI(@PathVariable("MPI") String MPI);

}
