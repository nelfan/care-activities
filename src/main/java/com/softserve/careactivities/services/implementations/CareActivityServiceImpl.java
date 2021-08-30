package com.softserve.careactivities.services.implementations;

import com.softserve.careactivities.domain.dto.CareActivityDTO;
import com.softserve.careactivities.domain.dto.CareActivityExtendedDTO;
import com.softserve.careactivities.domain.entities.CareActivity;
import com.softserve.careactivities.domain.mappers.CareActivityMapper;
import com.softserve.careactivities.feign_clients.PatientsClient;
import com.softserve.careactivities.repositories.CareActivityRepository;
import com.softserve.careactivities.services.CareActivityService;
import com.softserve.careactivities.utils.CheckAgeUtil;
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

    private final CareActivityMapper careActivityMapper;

    @Override
    public List<CareActivityDTO> getAll() {
        List<CareActivity> careActivities = (List<CareActivity>) careActivityRepository.findAll();
        return careActivities.stream().map(careActivityMapper::CAToCADTO).collect(Collectors.toList());
    }

    @Override
    public CareActivityDTO getCareActivityById(String id) {
        return careActivityRepository.findById(id)
                .map(careActivityMapper::CAToCADTO)
                .orElseThrow(() -> new CustomEntityNotFoundException(CareActivity.class));
    }

    @Override
    public List<CareActivityExtendedDTO> getAllActiveCareActivities() {
        List<CareActivityExtendedDTO> activeCA = getAll().stream()
                .filter(i -> i.getState().equals(CareActivity.StateEnum.ACTIVE))
                .map(careActivityMapper::CADTOToExtendedDTO)
                .collect(Collectors.toList());

        activeCA.forEach(p -> p.setIsPatientPediatric(CheckAgeUtil
                .checkIsPatientPediatric(patientsClient
                        .getPatientByMPI(p.getMasterPatientIdentifier())
                        .getDateOfBirth())));

        return activeCA;
    }

    @Override
    public List<CareActivityDTO> getAllDeclinedCareActivities() {
        return getAll().stream()
                .filter(i -> i.getState().equals(CareActivity.StateEnum.DECLINED))
                .collect(Collectors.toList());
    }

    @Override
    public List<CareActivityDTO> getDeclinedCareActivitiesForPatientByMpi(String mpi) {
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
            //CareActivity existingCareActivity = careActivityMapper.CADTOtoCA(getCareActivityById(careActivity.getCareActivityId()));
            CareActivityDTO existingCareActivityDTO = getCareActivityById(careActivity.getCareActivityId());
            CareActivity existingCareActivity = careActivityMapper.CADTOtoCA(existingCareActivityDTO);
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
