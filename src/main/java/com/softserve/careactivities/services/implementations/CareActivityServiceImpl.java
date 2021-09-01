package com.softserve.careactivities.services.implementations;

import com.softserve.careactivities.domain.dto.CareActivityDTO;
import com.softserve.careactivities.domain.dto.CareActivityExtendedDTO;
import com.softserve.careactivities.domain.entities.CareActivity;
import com.softserve.careactivities.domain.mappers.CareActivityMapper;
import com.softserve.careactivities.feign_clients.PatientClientFacade;
import com.softserve.careactivities.feign_clients.PatientsClient;
import com.softserve.careactivities.repositories.CareActivityRepository;
import com.softserve.careactivities.services.CareActivityService;
import com.softserve.careactivities.utils.exceptions.CustomEntityNotFoundException;
import com.softserve.careactivities.utils.exceptions.PatientIsNotActiveException;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Log
@AllArgsConstructor
public class CareActivityServiceImpl implements CareActivityService {

    private final CareActivityRepository careActivityRepository;

    private final PatientsClient patientsClient;

    private final CareActivityMapper careActivityMapper;

    private final PatientClientFacade patientClientFacade;

    @Override
    public List<CareActivityDTO> getAll() {
        Iterable<CareActivity> careActivitiesIter = careActivityRepository.findAll();

        List<CareActivity> careActivities = StreamSupport.stream(careActivitiesIter.spliterator(), false)
                .collect(Collectors.toList());

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
        List<CareActivityExtendedDTO> activeCA = careActivityRepository
                .findAllCareActivitiesByState(CareActivity.StateEnum.ACTIVE)
                .stream()
                .map(careActivityMapper::CAtoExtendedDTO)
                .collect(Collectors.toList());

        activeCA.forEach(p -> p.setIsPatientPediatric(patientClientFacade
                .checkIsPatientPediatric(p)));

        return activeCA;
    }

    @Override
    public List<CareActivityDTO> getAllDeclinedCareActivities() {
        return careActivityRepository
                .findAllCareActivitiesByState(CareActivity.StateEnum.DECLINED)
                .stream()
                .map(careActivityMapper::CAToCADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CareActivityDTO> getDeclinedCareActivitiesForPatientByMpi(String mpi) {
        return careActivityRepository
                .findAllCareActivitiesByMPIAndByState(mpi, CareActivity.StateEnum.DECLINED)
                .stream()
                .map(careActivityMapper::CAToCADTO)
                .collect(Collectors.toList());
    }

    @Override
    public CareActivity create(CareActivity careActivity) {
        if (patientsClient.getPatientByMPI((careActivity.getMasterPatientIdentifier()))
                .isActive()) {
            careActivity.setCreateDateTimeGMT(LocalDateTime.now());
            return careActivityRepository.save(careActivity);
        } else {
            throw new PatientIsNotActiveException();
        }
    }

    @Override
    public CareActivity update(CareActivity careActivity) {
        CareActivity existingCareActivity = careActivityMapper
                .CADTOtoCA(getCareActivityById(careActivity.getCareActivityId()));

        existingCareActivity.setCareActivityComment(careActivity.getCareActivityComment());
        existingCareActivity.setState(careActivity.getState());
        existingCareActivity.setUpdateDateTimeGMT(LocalDateTime.now());

        return careActivityRepository.save(existingCareActivity);
    }

    @Override
    public boolean delete(String id) {
        careActivityRepository.deleteById(id);
        return true;
    }
}
