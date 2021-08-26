package com.softserve.careactivities.repositories;

import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import com.softserve.careactivities.domain.entities.CareActivity;
import org.springframework.stereotype.Repository;

@Repository
public interface CareActivityRepository extends SpannerRepository<CareActivity, String> {
}
