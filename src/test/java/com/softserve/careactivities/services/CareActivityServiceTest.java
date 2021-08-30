package com.softserve.careactivities.services;

import com.softserve.careactivities.domain.dto.CareActivityDTO;
import com.softserve.careactivities.domain.entities.CareActivity;
import com.softserve.careactivities.domain.mappers.CareActivityMapper;
import com.softserve.careactivities.feign_clients.PatientsClient;
import com.softserve.careactivities.repositories.CareActivityRepository;
import com.softserve.careactivities.services.implementations.CareActivityServiceImpl;
import com.softserve.careactivities.utils.exceptions.CustomEntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    static CareActivity careActivity = new CareActivity();

    static CareActivityDTO careActivityDTO = new CareActivityDTO();

    static LocalDateTime localDateTime;

    static ZonedDateTime zonedDateTime;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        careActivityService = new CareActivityServiceImpl(careActivityRepository, patientsClient, careActivityMapper);

        localDateTime = LocalDateTime.of(2000, 10, 10, 10, 10);
        zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());

        careActivity.setCareActivityId("12345");
        careActivity.setCareActivityComment("comment");
        careActivity.setMasterPatientIdentifier("MPI");
        careActivity.setCreateDateTimeGMT(localDateTime);
        careActivity.setUpdateDateTimeGMT(localDateTime);
        careActivity.setState(CareActivity.StateEnum.ACTIVE);

        careActivityDTO.setCareActivityId("12345");
        careActivityDTO.setCareActivityComment("comment");
        careActivityDTO.setMasterPatientIdentifier("MPI");
        careActivityDTO.setCreateDateTimeGMT(zonedDateTime);
        careActivityDTO.setUpdateDateTimeGMT(zonedDateTime);
        careActivityDTO.setState(CareActivity.StateEnum.ACTIVE);
    }

    @AfterEach
    void tearDown() {

        localDateTime = null;
        zonedDateTime = null;

        careActivity.setCareActivityId(null);
        careActivity.setCareActivityComment(null);
        careActivity.setMasterPatientIdentifier(null);
        careActivity.setCreateDateTimeGMT(null);
        careActivity.setUpdateDateTimeGMT(null);
        careActivity.setState(null);

        careActivityDTO.setCareActivityId(null);
        careActivityDTO.setCareActivityComment(null);
        careActivityDTO.setMasterPatientIdentifier(null);
        careActivityDTO.setCreateDateTimeGMT(null);
        careActivityDTO.setUpdateDateTimeGMT(null);
        careActivityDTO.setState(null);
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

    @Test
    void shouldGetCareActivityById() throws CustomEntityNotFoundException {
        CareActivity expected = careActivity;

        CareActivityDTO expectedDTO = careActivityDTO;

        when(careActivityRepository.findById(expected.getCareActivityId())).thenReturn(Optional.of(expected));

        when(careActivityMapper.CAToCADTO(expected)).thenReturn(expectedDTO);

        CareActivityDTO actual = careActivityService.getCareActivityById(expected.getCareActivityId());

        assertEquals(actual, careActivityMapper.CAToCADTO(expected));
        verify(careActivityRepository, times(1)).findById(expected.getCareActivityId());

    }

}