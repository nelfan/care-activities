package com.softserve.careactivities.domain.entities;

import com.google.cloud.Timestamp;
import com.google.cloud.spring.data.spanner.core.mapping.Column;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CARE_ACTIVITIES")
public class CareActivity {

    @PrimaryKey
    @Column(name = "CARE_ACTIVITY_ID")
    private String careActivityId;

    @Column(name = "MASTER_PATIENT_IDENTIFIER")
    private String masterPatientIdentifier;

    @Column(name = "CREATE_DATE_TIME_GMT")
    private Timestamp createDateTimeGMT;

    @Column(name = "UPDATE_DATE_TIME_GMT")
    private Timestamp updateDateTimeGMT;

    @Column(name = "CARE_ACTIVITY_COMMENT")
    private String careActivityComment;

    @Column(name = "STATE")
    private StateEnum state;

    public enum StateEnum {
        ACTIVE("ACTIVE"),
        DECLINED("DECLINED");

        private final String stateName;

        StateEnum(String status) {
            this.stateName = status;
        }

        public String getStateName() {
            return stateName;
        }
    }
}
