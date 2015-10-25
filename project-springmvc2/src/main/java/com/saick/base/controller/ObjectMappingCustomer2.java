package com.saick.base.controller;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;

@SuppressWarnings("serial")
public class ObjectMappingCustomer2 extends ObjectMapper {

    public ObjectMappingCustomer2() {
        super();
        // 允许单引号
        this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 字段和值都加引号
        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 数字也加引号
        this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
        
        // 配置不写value为null的key
        this.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        
        try {
            this.getFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //this.getSerializerProvider().setDefaultKeySerializer(new JsonStringSerializer());
        //this.getSerializerProvider().setNullKeySerializer(new JsonStringSerializer());
        
        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(
                new JsonSerializer<Object>() {
                    @Override
                    public void serialize(Object value, JsonGenerator jg,
                            SerializerProvider sp) throws IOException,
                            JsonProcessingException {
                        jg.writeString(" ");
                    }
                });

    }
    
}