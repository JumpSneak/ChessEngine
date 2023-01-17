package de.chessy.utils;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Serializer {
    private Serializer() {

    }

    public static String serialize(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T fromJson(String body, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(body, clazz);
    }

    public static <T> T parse(InputStream body, Class<T> clazz) {
        try {
            String parsedBody = read(body);
            return fromJson(parsedBody, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String read(InputStream body) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = body.read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toString(StandardCharsets.UTF_8);
    }
}
