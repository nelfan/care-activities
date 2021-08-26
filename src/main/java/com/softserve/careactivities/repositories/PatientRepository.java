package com.softserve.careactivities.repositories;

import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import com.softserve.careactivities.domain.entities.Patient;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends SpannerRepository<Patient, String> {
}
