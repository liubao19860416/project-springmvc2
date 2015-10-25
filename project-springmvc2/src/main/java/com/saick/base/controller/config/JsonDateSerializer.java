package com.saick.base.controller.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 自定义的json日期转换器
 * 
 * @author Liubao
 * @2015年1月24日
 * 
 */
public class JsonDateSerializer extends JsonSerializer<Date> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");

    @Override
    public void serialize(Date date, JsonGenerator gen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        String value = dateFormat.format(date);
        gen.writeString(value);
    }
}