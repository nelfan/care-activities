package com.softserve.careactivities.utils.dateformat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

public class DateFormatUtil {

    public static final DateTimeFormatter FORMATTER = ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMATTER2 = ofPattern("yyyy-[M]M-[d]D[( |T)[H]H:[m]M:[S]S[.F]][time zone]");

    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateTimeDeserializer());
        javaTimeModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer());
        javaTimeModule.addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer());
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

}
