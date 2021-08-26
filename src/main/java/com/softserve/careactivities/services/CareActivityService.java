package com.softserve.careactivities.services;

import com.softserve.careactivities.domain.entities.CareActivity;

import java.util.List;

public interface CareActivityService {

    List<CareActivity> getAll();

    List<CareActivity> getAllDeclinedCareActivities();

    CareActivity getCareActivityById(String id);

    List<CareActivity> getDeclinedCareActivitiesForPatientByMpi(String mpi);

    CareActivity create(CareActivity careActivity);

    CareActivity update(CareActivity careActivity);

    boolean delete(String id);
}
