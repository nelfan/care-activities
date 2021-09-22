package com.softserve.careactivities.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.careactivities.domain.entities.CareActivity;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CareActivityDTO {

    private String careActivityId;
    private String masterPatientIdentifier;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssz")
    private ZonedDateTime createDateTimeGMT;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssz")
    private ZonedDateTime updateDateTimeGMT;
    private String careActivityComment;
    private CareActivity.StateEnum state;
}
