package com.softserve.careactivities.utils;

import java.time.LocalDate;
import java.time.Period;

public class CheckAgeUtil {

    public static Boolean checkIsPatientPediatric(LocalDate birthDate){
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }
}
