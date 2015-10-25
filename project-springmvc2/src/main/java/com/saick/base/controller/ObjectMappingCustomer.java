package com.saick.base.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.saick.base.controller.util.MyStringUtils;
@Component("objectMapper")
public class ObjectMappingCustomer extends ObjectMapper {

    public ObjectMappingCustomer() {
        super();
        // 允许单引号
        this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 字段和值都加引号
        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 数字也加引号
        this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
        
        // 配置不写value为null的key
        this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        try {
            this.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(
                new JsonSerializer<Object>() {
                    @Override
                    public void serialize(Object value, JsonGenerator jg,
                            SerializerProvider sp) throws IOException,
                            JsonProcessingException {
                        jg.writeString("");
                    }
                });

    }

    @Override
    public void writeValue(File resultFile, Object value) throws IOException,
            JsonGenerationException, JsonMappingException {
        if(StringUtils.isBlank(MyStringUtils.getStringValue(value))){
            value="";
        }
        if(value instanceof Date){
            System.out.println("Date:"+value);
        }
        super.writeValue(resultFile, value);
    }
}