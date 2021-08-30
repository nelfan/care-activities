package com.softserve.careactivities.services;

import com.softserve.careactivities.domain.dto.CareActivityDTO;
import com.softserve.careactivities.domain.entities.CareActivity;
import com.softserve.careactivities.domain.mappers.CareActivityMapper;
import com.softserve.careactivities.feign_clients.PatientsClient;
import com.softserve.careactivities.repositories.CareActivityRepository;
import com.softserve.careactivities.services.implementations.CareActivityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CareActivityServiceTest {

    @Mock
    CareActivityRepository careActivityRepository;

    @Mock
    PatientsClient patientsClient;

    @Mock
    CareActivityMapper careActivityMapper;

    @InjectMocks
    CareActivityServiceImpl careActivityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        careActivityService = new CareActivityServiceImpl(careActivityRepository, patientsClient, careActivityMapper);
    }


    @Test
    void shouldGetAllCareActivities() {
        List<CareActivity> careActivities = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CareActivity careActivity = new CareActivity();
            careActivity.setCareActivityId("id_" + i);
            careActivity.setMasterPatientIdentifier("MPI_" + i);
            careActivity.setCreateDateTimeGMT(LocalDateTime.now());
            careActivity.setUpdateDateTimeGMT(LocalDateTime.now());
            careActivity.setCareActivityComment("Comment_" + i);
            careActivity.setState(CareActivity.StateEnum.ACTIVE);
            careActivities.add(careActivity);
        }

        when(careActivityRepository.findAll()).thenReturn(careActivities);

        List<CareActivityDTO> careActivityDTOList = careActivityService.getAll();

        assertEquals(3, careActivityDTOList.size());
        verify(careActivityRepository, times(1)).findAll();
    }

}