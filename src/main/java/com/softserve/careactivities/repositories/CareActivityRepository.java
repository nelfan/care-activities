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
    List<CareActivity> findAllActiveCareActivitiesByState(@Param("state") CareActivity.StateEnum state);
}
