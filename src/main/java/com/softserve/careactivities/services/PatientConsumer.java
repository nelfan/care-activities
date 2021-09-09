package com.softserve.careactivities.services;

import com.softserve.careactivities.repositories.CareActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientConsumer {

    private CareActivityRepository careActivityRepository;

    @KafkaListener(topics = "Patient-deactivation-event", groupId = "mygroup")
    public void consume(String deactivatedPatientMPI) {
        careActivityRepository.declineAllCareActivitiesByMPI(deactivatedPatientMPI);
    }
}
