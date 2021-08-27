package com.softserve.careactivities.services;

import com.softserve.careactivities.domain.dto.CareActivityExtendedDTO;
import com.softserve.careactivities.domain.dto.CareActivityDTO;
import com.softserve.careactivities.domain.entities.CareActivity;

import java.util.List;

public interface CareActivityService {

    List<CareActivityDTO> getAll();

    List<CareActivityExtendedDTO> getAllActiveCareActivities();

    List<CareActivityDTO> getAllDeclinedCareActivities();

    CareActivityDTO getCareActivityById(String id);

    List<CareActivityDTO> getDeclinedCareActivitiesForPatientByMpi(String mpi);

    CareActivity create(CareActivity careActivity);

    CareActivity update(CareActivity careActivity);

    boolean delete(String id);
}
