package com.softserve.careactivities.services.implementations;

import com.softserve.careactivities.domain.entities.CareActivity;
import com.softserve.careactivities.feign_clients.PatientsClient;
import com.softserve.careactivities.repositories.CareActivityRepository;
import com.softserve.careactivities.services.CareActivityService;
import com.softserve.careactivities.utils.exceptions.*;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
@AllArgsConstructor
public class CareActivityServiceImpl implements CareActivityService {

    private final CareActivityRepository careActivityRepository;

    private final PatientsClient patientsClient;

    @Override
    public List<CareActivity> getAll() {
        return (List<CareActivity>) careActivityRepository.findAll();
    }

    @Override
    public CareActivity getCareActivityById(String id) {
        return careActivityRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(CareActivity.class));
    }

    @Override
    public List<CareActivity> getAllActiveCareActivities() {
        return getAll().stream()
                .filter(i -> i.getState().equals(CareActivity.StateEnum.ACTIVE))
                .collect(Collectors.toList());
    }

    @Override
    public List<CareActivity> getAllDeclinedCareActivities() {
        return getAll().stream()
                .filter(i -> i.getState().equals(CareActivity.StateEnum.DECLINED))
                .collect(Collectors.toList());
    }

    @Override
    public List<CareActivity> getDeclinedCareActivitiesForPatientByMpi(String mpi) {
        return getAllDeclinedCareActivities().stream()
                .filter(i -> i.getMasterPatientIdentifier()
                        .equals(mpi))
                .collect(Collectors.toList());
    }

    @Override
    public CareActivity create(CareActivity careActivity) {
        try {
            if (patientsClient.getPatientByMPI((careActivity.getMasterPatientIdentifier()))
                    .isActive()) {
                return careActivityRepository.save(careActivity);
            } else {
                throw new PatientIsNotActiveException();
            }
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new CustomEntityFailedToCreate(CareActivity.class);
        }
    }

    @Override
    public CareActivity update(CareActivity careActivity) {
        try {
            CareActivity existingCareActivity = getCareActivityById(careActivity.getCareActivityId());
            patientsClient.getPatientByMPI(existingCareActivity.getMasterPatientIdentifier());

            existingCareActivity.setCareActivityComment(careActivity.getCareActivityComment());
            existingCareActivity.setState(careActivity.getState());
            existingCareActivity.setUpdateDateTimeGMT(careActivity.getUpdateDateTimeGMT());

            return careActivityRepository.save(existingCareActivity);
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new CustomFailedToUpdateEntityException();
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            getCareActivityById(id);
            careActivityRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new CustomFailedToDeleteEntityException(CareActivity.class);
        }
    }
}
