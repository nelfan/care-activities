package com.softserve.careactivities.controllers;

import com.google.cloud.Timestamp;
import com.softserve.careactivities.domain.dto.CareActivityExtendedDTO;
import com.softserve.careactivities.domain.dto.CareActivityFullDTO;
import com.softserve.careactivities.domain.dto.CareActivitySimpleDTO;
import com.softserve.careactivities.domain.entities.CareActivity;
import com.softserve.careactivities.domain.mappers.CareActivityMapper;
import com.softserve.careactivities.feign_clients.PatientsClient;
import com.softserve.careactivities.services.CareActivityService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/care-activities")
public class CareActivitiesController {

    private final CareActivityService careActivityService;

    private final CareActivityMapper careActivityMapper;

    private final PatientsClient patientsClient;

    @GetMapping()
    public List<CareActivity> getAllCareActivities() {
        return careActivityService.getAll();
    }

    @GetMapping("/active")
    public List<CareActivityExtendedDTO> getAllActiveCareActivities() {
        return careActivityService.getAllActiveCareActivities();
    }

    @GetMapping("/declined")
    public List<CareActivity> getAllDeclinedCareActivities() {
        return careActivityService.getAllDeclinedCareActivities();
    }

    @GetMapping("/declined/{MPI}")
    public List<CareActivity> getAllDeclinedCareActivitiesForPatientByMPI(@PathVariable String MPI) {
        return careActivityService.getDeclinedCareActivitiesForPatientByMpi(MPI);
    }

    @GetMapping("/{careActivityId}")
    public CareActivity getCareActivityById(@PathVariable String careActivityId) {
        return careActivityService.getCareActivityById(careActivityId);
    }

    @PostMapping()
    public ResponseEntity<CareActivityFullDTO> createCareActivity(@RequestBody CareActivitySimpleDTO careActivitySimpleDTO) {
        CareActivity careActivity = careActivityMapper.careActivitySimpleDTOToCA(careActivitySimpleDTO);
        careActivity.setCreateDateTimeGMT(Timestamp.now());

        careActivityService.create(careActivity);

        CareActivityFullDTO careActivityDTO = careActivityMapper.CAToFullDTO(careActivity);
        return new ResponseEntity<>(careActivityDTO, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<CareActivityFullDTO> updateCareActivity(@RequestBody CareActivitySimpleDTO careActivitySimpleDTO) {
        CareActivity careActivity = careActivityMapper.careActivitySimpleDTOToCA(careActivitySimpleDTO);
        careActivity.setUpdateDateTimeGMT(Timestamp.now());

        careActivityService.update(careActivity);

        CareActivityFullDTO careActivityDTO = careActivityMapper.CAToFullDTO(careActivity);

        return new ResponseEntity<>(careActivityDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCareActivityById(@PathVariable String id) {
        careActivityService.delete(id);

        return new ResponseEntity<>("Care Activity was removed successfully", HttpStatus.OK);
    }
}
