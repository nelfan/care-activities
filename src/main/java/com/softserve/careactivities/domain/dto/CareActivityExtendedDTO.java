package com.softserve.careactivities.domain.dto;

import com.google.cloud.Timestamp;
import com.softserve.careactivities.domain.entities.CareActivity;
import lombok.Data;

@Data
public class CareActivityExtendedDTO {

    private String careActivityId;
    private String masterPatientIdentifier;
    private Timestamp createDateTimeGMT;
    private Timestamp updateDateTimeGMT;
    private String careActivityComment;
    private CareActivity.StateEnum state;
    private Boolean isPatientPediatric;
}
