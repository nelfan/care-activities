//package com.softserve.careactivities.utils;
//
//import com.softserve.careactivities.domain.entities.Patient;
//
//import java.time.LocalDate;
//import java.time.Period;
//
//public class CheckAgeUtil {
//
//    public static Boolean checkIsPatientPediatric(Patient patient){
//        LocalDate birthDate = patient.getDateOfBirth();
//        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
//    }
//}
