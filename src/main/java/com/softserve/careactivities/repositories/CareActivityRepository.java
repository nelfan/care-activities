package com.softserve.careactivities.repositories;

import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import com.google.cloud.spring.data.spanner.repository.query.Query;
import com.softserve.careactivities.domain.entities.CareActivity;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareActivityRepository extends SpannerRepository<CareActivity, String> {

    @Query("SELECT * FROM CARE_ACTIVITIES WHERE STATE = @state")
    List<CareActivity> findAllCareActivitiesByState(@Param("state") CareActivity.StateEnum state);

    @Query("SELECT * FROM CARE_ACTIVITIES WHERE MASTER_PATIENT_IDENTIFIER = @MPI and STATE = @state")
    List<CareActivity> findAllCareActivitiesByMPIAndByState(@Param("MPI") String MPI,
                                                            @Param("state") CareActivity.StateEnum state);

    @Query(value = "DELETE FROM CARE_ACTIVITIES WHERE CARE_ACTIVITY_ID = @id", dmlStatement = true)
    int customDeleteById(@Param("id") String careActivityId);

    @Query(value = "UPDATE CARE_ACTIVITIES SET STATE='DECLINED' WHERE MASTER_PATIENT_IDENTIFIER = @MPI", dmlStatement = true)
    void declineAllCareActivitiesByMPI(@Param("MPI") String MPI);
}
