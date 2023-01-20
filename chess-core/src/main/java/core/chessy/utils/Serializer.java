package core.chessy.utils;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Serializer {
    private Serializer() {

    }

    public static String serialize(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String body, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T parse(InputStream body, Class<T> clazz) throws SerializationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new InputStreamReader(body, StandardCharsets.UTF_8), clazz);
        } catch (Exception e) {
            throw new SerializationException(e.getMessage());
        }
    }
}
