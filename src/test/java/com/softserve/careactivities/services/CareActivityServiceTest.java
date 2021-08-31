package com.softserve.careactivities.services;

import com.softserve.careactivities.domain.dto.CareActivityDTO;
import com.softserve.careactivities.domain.dto.CareActivityExtendedDTO;
import com.softserve.careactivities.domain.entities.CareActivity;
import com.softserve.careactivities.domain.entities.Patient;
import com.softserve.careactivities.domain.mappers.CareActivityMapper;
import com.softserve.careactivities.feign_clients.PatientsClient;
import com.softserve.careactivities.repositories.CareActivityRepository;
import com.softserve.careactivities.services.implementations.CareActivityServiceImpl;
import com.softserve.careactivities.utils.exceptions.CustomEntityFailedToCreate;
import com.softserve.careactivities.utils.exceptions.CustomEntityNotFoundException;
import com.softserve.careactivities.utils.exceptions.CustomFailedToDeleteEntityException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
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

    @Mock
    CareActivityServiceImpl careActivityServiceMock;

    static CareActivity careActivity;

    static CareActivityDTO careActivityDTO;

    static CareActivityExtendedDTO careActivityExtendedDTO;

    static Patient patient;

    static LocalDateTime localDateTime;

    static ZonedDateTime zonedDateTime;

    static List<CareActivity> careActivityList;

    static List<CareActivityDTO> careActivityDTOList;

    static List<CareActivityExtendedDTO> extendedDTOList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        careActivityService = new CareActivityServiceImpl(careActivityRepository, patientsClient, careActivityMapper);

        localDateTime = LocalDateTime.of(2000, 10, 10, 10, 10);
        zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());

        careActivity = new CareActivity();
        careActivity.setCareActivityId("12345");
        careActivity.setCareActivityComment("comment");
        careActivity.setMasterPatientIdentifier("MPI");
        careActivity.setCreateDateTimeGMT(localDateTime);
        careActivity.setUpdateDateTimeGMT(localDateTime);
        careActivity.setState(CareActivity.StateEnum.ACTIVE);

        careActivityDTO = new CareActivityDTO();
        careActivityDTO.setCareActivityId("12345");
        careActivityDTO.setCareActivityComment("comment");
        careActivityDTO.setMasterPatientIdentifier("MPI");
        careActivityDTO.setCreateDateTimeGMT(zonedDateTime);
        careActivityDTO.setUpdateDateTimeGMT(zonedDateTime);
        careActivityDTO.setState(CareActivity.StateEnum.ACTIVE);

        careActivityExtendedDTO = new CareActivityExtendedDTO();
        careActivityExtendedDTO.setCareActivityId("12345");
        careActivityExtendedDTO.setCareActivityComment("comment");
        careActivityExtendedDTO.setMasterPatientIdentifier("MPI");
        careActivityExtendedDTO.setCreateDateTimeGMT(zonedDateTime);
        careActivityExtendedDTO.setUpdateDateTimeGMT(zonedDateTime);
        careActivityExtendedDTO.setState(CareActivity.StateEnum.ACTIVE);
        careActivityExtendedDTO.setIsPatientPediatric(true);

        patient = new Patient();
        patient.setMpi("MPI");
        patient.setDateOfBirth(LocalDate.of(2000, 10, 10));
        patient.setActive(true);

        careActivityList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CareActivity careActivity = new CareActivity();
            careActivity.setCareActivityId("id_" + i);
            careActivity.setMasterPatientIdentifier("MPI_" + i);
            careActivity.setCreateDateTimeGMT(LocalDateTime.now());
            careActivity.setUpdateDateTimeGMT(LocalDateTime.now());
            careActivity.setCareActivityComment("Comment_" + i);
            careActivity.setState(CareActivity.StateEnum.ACTIVE);
            careActivityList.add(careActivity);
        }

        careActivityDTOList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CareActivityDTO careActivityDTO = new CareActivityDTO();
            careActivityDTO.setCareActivityId("id_" + i);
            careActivityDTO.setMasterPatientIdentifier("MPI_" + i);
            careActivityDTO.setCreateDateTimeGMT(zonedDateTime);
            careActivityDTO.setUpdateDateTimeGMT(zonedDateTime);
            careActivityDTO.setCareActivityComment("Comment_" + i);
            careActivityDTO.setState(CareActivity.StateEnum.ACTIVE);
            careActivityDTOList.add(careActivityDTO);
        }

        extendedDTOList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CareActivityExtendedDTO careActivityExtendedDTO = new CareActivityExtendedDTO();
            careActivityExtendedDTO.setCareActivityId("id_" + i);
            careActivityExtendedDTO.setMasterPatientIdentifier("MPI_" + i);
            careActivityExtendedDTO.setCreateDateTimeGMT(zonedDateTime);
            careActivityExtendedDTO.setUpdateDateTimeGMT(zonedDateTime);
            careActivityExtendedDTO.setCareActivityComment("Comment_" + i);
            careActivityExtendedDTO.setState(CareActivity.StateEnum.ACTIVE);
            careActivityExtendedDTO.setIsPatientPediatric(true);
            extendedDTOList.add(careActivityExtendedDTO);
        }

    }

    @AfterEach
    void tearDown() {
        localDateTime = null;
        zonedDateTime = null;
    }

    @Test
    void shouldGetAllCareActivities() {
        List<CareActivity> careActivities = careActivityList;
        List<CareActivityDTO> careActivityDTOS = careActivityDTOList;

        for (int i = 0; i < careActivities.size(); i++) {
            when(careActivityMapper.CAToCADTO(careActivities.get(i))).thenReturn(careActivityDTOS.get(i));
        }

        when(careActivityRepository.findAll()).thenReturn(careActivities);
        List<CareActivityDTO> actual = careActivityService.getAll();

        assertEquals(4, actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(actual.get(i), careActivityDTOS.get(i));
        }
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

    @Test
    void shouldThrowCustomEntityNotFoundException() {
        Assertions.assertThrows(CustomEntityNotFoundException.class, () -> {
            CareActivity expected = careActivity;
            CareActivityDTO expectedDTO = careActivityDTO;

            when(careActivityRepository.findById(expected.getCareActivityId())).thenReturn(Optional.of(expected));

            when(careActivityMapper.CAToCADTO(expected)).thenReturn(expectedDTO);

            CareActivityDTO actual = careActivityService.getCareActivityById("??????");

            assertEquals(actual, careActivityMapper.CAToCADTO(expected));
            verify(careActivityRepository, times(1)).findById(expected.getCareActivityId());
        });
    }

    @Test
    void shouldCreateCareActivity() {
        CareActivity ca = careActivity;

        CareActivity expected = careActivity;

        Patient activePatient = patient;

        when(careActivityRepository.save(ca)).thenReturn(expected);
        when(patientsClient.getPatientByMPI(ca.getMasterPatientIdentifier())).thenReturn(activePatient);

        CareActivity actual = careActivityService.create(ca);

        assertEquals(expected, actual);
        verify(careActivityRepository, times(1)).save(ca);
    }

    @Test
    void shouldThrowCustomEntityFailedToCreateException() {
        Assertions.assertThrows(CustomEntityFailedToCreate.class, () -> {
            CareActivity ca = careActivity;

            Patient inactivePatient = patient;
            inactivePatient.setActive(false);

            when(patientsClient.getPatientByMPI(ca.getMasterPatientIdentifier())).thenReturn(inactivePatient);

            careActivityService.create(ca);
        });
    }

    @Test
    void shouldUpdateCareActivity() {
        CareActivity expected = careActivity;
        CareActivityDTO expectedDTO = careActivityDTO;

        when(careActivityRepository.findById(expected.getCareActivityId())).thenReturn(Optional.of(expected));
        when(careActivityMapper.CAToCADTO(expected)).thenReturn(expectedDTO);

        CareActivityDTO actual = careActivityService.getCareActivityById(expected.getCareActivityId());

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Patient pa = patient;

        CareActivity caSaved = careActivity;
        CareActivity caUpdated = careActivity;

        when(careActivityMapper.CADTOtoCA(actual)).thenReturn(caSaved);

        when(patientsClient.getPatientByMPI(caSaved.getMasterPatientIdentifier())).thenReturn(pa);

        when(careActivityRepository.save(caSaved)).thenReturn(caUpdated);

        CareActivity actualCA = careActivityService.update(caSaved);

        assertEquals(caSaved, actualCA);

        verify(careActivityRepository, times(1)).save(caSaved);
        verify(careActivityRepository, times(1)).save(caUpdated);
    }

    @Test
    void shouldDeleteCareActivity() {
        CareActivityDTO actual = careActivityDTO;

        Boolean isDeleted = careActivityService.delete(actual.getCareActivityId());

        assertEquals(true, isDeleted);
        verify(careActivityRepository, times(1)).deleteById(actual.getCareActivityId());
    }

    @Test
    void shouldThrowCustomFailedToDeleteExceptionWhenCareActivityDoesntExist() {
        Assertions.assertThrows(CustomFailedToDeleteEntityException.class, () -> {
            CareActivity expected = careActivity;
            expected.setCareActivityId("unknown_id");
            CareActivityDTO expectedDTO = careActivityDTO;

            when(careActivityRepository.findById(expected.getCareActivityId())).thenReturn(Optional.of(expected));
            when(careActivityMapper.CAToCADTO(expected)).thenReturn(expectedDTO);

            CareActivityDTO actual = careActivityService.getCareActivityById(expected.getCareActivityId());

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////

            careActivityService.delete(actual.getCareActivityId());
        });
    }

    @Test
    void shouldGetAllDeclinedCareActivities() {
        List<CareActivity> careActivities = careActivityList;
        List<CareActivityDTO> expected = careActivityDTOList;

        expected.get(0).setState(CareActivity.StateEnum.DECLINED);
        expected.get(1).setState(CareActivity.StateEnum.DECLINED);

        when(careActivityRepository.findAll()).thenReturn(careActivities);

        for (int i = 0; i < careActivities.size(); i++) {
            when(careActivityMapper.CAToCADTO(careActivities.get(i))).thenReturn(expected.get(i));
        }

        List<CareActivityDTO> actual = careActivityService.getAllDeclinedCareActivities();

        assertEquals(actual.size(), 2);
        verify(careActivityRepository, times(1)).findAll();

    }

    @Test
    void shouldGetDeclinedCareActivitiesForPatientByMpi() {
    }
}