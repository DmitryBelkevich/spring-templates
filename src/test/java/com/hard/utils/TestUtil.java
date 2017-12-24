package com.hard.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;

public class TestUtil {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    public static <T> String convertObjectToJson(T object) {
        ObjectMapper mapper = new ObjectMapper();

        String str = null;

        try {
            str = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return str;
    }
}
