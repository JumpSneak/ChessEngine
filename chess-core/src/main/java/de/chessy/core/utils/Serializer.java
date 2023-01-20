package de.chessy.core.utils;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Serializer {
    private Serializer() {

    }

    public static String serialize(Object object) {
        ObjectMapper mapper = getMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String body, Class<T> clazz) {
        ObjectMapper mapper = getMapper();
        try {
            return mapper.readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T parse(InputStream body, Class<T> clazz) throws SerializationException {
        ObjectMapper mapper = getMapper();
        try {
            return mapper.readValue(new InputStreamReader(body, StandardCharsets.UTF_8), clazz);
        } catch (Exception e) {
            throw new SerializationException(e.getMessage());
        }
    }

    private static ObjectMapper getMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }
}
