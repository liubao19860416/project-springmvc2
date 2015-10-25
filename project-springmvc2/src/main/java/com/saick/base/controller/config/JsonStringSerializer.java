package com.saick.base.controller.config;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.saick.base.controller.util.MyStringUtils;

public class JsonStringSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        if (StringUtils.isBlank(MyStringUtils.getStringValue(value))) {
            value = "";
        }
        gen.writeString(MyStringUtils.getStringValue(value));
    }

}
