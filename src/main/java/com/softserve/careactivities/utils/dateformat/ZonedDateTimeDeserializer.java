package com.softserve.careactivities.utils.dateformat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

import static com.softserve.careactivities.utils.dateformat.DateFormatUtil.FORMATTER2;

public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return ZonedDateTime.parse(p.getValueAsString(), FORMATTER2);
    }
}
