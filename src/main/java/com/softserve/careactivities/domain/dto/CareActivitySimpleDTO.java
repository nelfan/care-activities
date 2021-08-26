package com.softserve.careactivities.domain.dto;

import com.softserve.careactivities.domain.entities.CareActivity;
import lombok.Data;

@Data
public class CareActivitySimpleDTO {

    private String careActivityId;
    private String masterPatientIdentifier;
    private String careActivityComment;
    private CareActivity.StateEnum state;
}
