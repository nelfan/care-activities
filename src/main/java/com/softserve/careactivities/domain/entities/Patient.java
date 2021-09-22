package com.softserve.careactivities.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    private String mpi;
    private LocalDate dateOfBirth;
    private boolean isActive;

}
