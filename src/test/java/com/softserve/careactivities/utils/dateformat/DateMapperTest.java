package com.softserve.careactivities.utils.dateformat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateMapperTest {

    static LocalDateTime localDateTime;

    static ZonedDateTime zonedDateTime;

    static ZoneId zoneId;

    DateMapper dateMapper;

    @BeforeEach
    void setUp() {
        localDateTime = LocalDateTime.of(2021, 1, 2, 10, 20, 30);
        zoneId = ZoneId.systemDefault();
        zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        dateMapper = new DateMapper();
    }

    @Test
    void shouldRepresentLocalDateTimeAsZonedDateTime() {
        LocalDateTime exist = localDateTime;
        ZonedDateTime expected = zonedDateTime;
        ZonedDateTime actual = dateMapper.asZonedDateTime(exist);

        assertEquals(expected, actual);
    }

    @Test
    void shouldRepresentZonedDateTimeAsLocalDateTime() {
        ZonedDateTime exist = zonedDateTime;
        LocalDateTime expected = localDateTime;
        LocalDateTime actual = dateMapper.asLocalDateTime(exist);

        assertEquals(expected, actual);
    }

    @Test
    void ShouldReturnNullWhenUseAsZonedDateTime() {
        ZonedDateTime actual = dateMapper.asZonedDateTime(null);

        assertNull(actual);
    }

    @Test
    void ShouldReturnNullWhenUseAsLocalDateTime() {
        LocalDateTime actual = dateMapper.asLocalDateTime(null);

        assertNull(actual);
    }
}