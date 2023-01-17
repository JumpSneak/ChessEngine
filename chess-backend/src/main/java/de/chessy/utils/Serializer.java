package de.chessy.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
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

    public static <T> T fromJson(JsonReader reader, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(reader, clazz);
    }

    public static <T> T parse(InputStream body, Class<T> clazz) {
        var jsonReader = new Gson().newJsonReader(new InputStreamReader(body, StandardCharsets.UTF_8));
        return fromJson(jsonReader, clazz);
    }
}
