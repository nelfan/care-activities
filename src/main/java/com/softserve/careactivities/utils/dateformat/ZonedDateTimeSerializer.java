package com.softserve.careactivities.utils.dateformat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;

import static com.softserve.careactivities.utils.dateformat.DateFormatUtil.FORMATTER2;

public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(FORMATTER2));
    }
}
