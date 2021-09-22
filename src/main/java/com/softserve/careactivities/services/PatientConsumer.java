package com.softserve.careactivities.services;

import com.softserve.careactivities.repositories.CareActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("consume")
@AllArgsConstructor
public class PatientConsumer implements Consumer<String> {

    private CareActivityRepository careActivityRepository;

    @Override
    public void accept(String s) {
        careActivityRepository.declineAllCareActivitiesByMPI(s);
    }
}
