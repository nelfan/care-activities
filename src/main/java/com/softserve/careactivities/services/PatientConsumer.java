package com.softserve.careactivities.services;

import com.softserve.careactivities.repositories.CareActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@AllArgsConstructor
public class PatientConsumer {

    private CareActivityRepository careActivityRepository;

    @Bean()
    public Consumer<String> consume() {
        return s -> careActivityRepository.declineAllCareActivitiesByMPI(s);
    }
}
