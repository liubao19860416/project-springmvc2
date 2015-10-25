package com.saick.base.controller;

import java.io.IOException;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MyJackson2Converter extends MappingJackson2HttpMessageConverter {
    private ObjectMapper objectMapper;
    private boolean prefixJson=false;

    public boolean isPrefixJson() {
        return prefixJson;
    }

    public void setPrefixJson(boolean prefixJson) {
        this.prefixJson = prefixJson;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders()
                .getContentType());
        JsonGenerator jsonGenerator = this.objectMapper.getJsonFactory()
                .createJsonGenerator(outputMessage.getBody(), encoding);
        try {
            if (this.prefixJson) {
                jsonGenerator.writeRaw("{} && ");
            }
            // 允许单引号
            this.objectMapper
                    .configure(
                            com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES,
                            true);
            // 字段和值都加引号
            this.objectMapper
                    .configure(
                            com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
                            true);
            // 数字也加引号
            this.objectMapper
                    .configure(
                            com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS,
                            true);
            this.objectMapper
                    .configure(
                            com.fasterxml.jackson.core.JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS,
                            true);

            this.getObjectMapper().getSerializerProvider()
                    .setNullValueSerializer(new JsonSerializer<Object>() {
                        @Override
                        public void serialize(Object value, JsonGenerator jg,
                                SerializerProvider sp) throws IOException,
                                JsonProcessingException {
                            jg.writeString("");
                        }
                    });

            this.objectMapper.writeValue(jsonGenerator, object);

        } catch (JsonGenerationException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: "
                    + ex.getMessage(), ex);
        }
    }
}
