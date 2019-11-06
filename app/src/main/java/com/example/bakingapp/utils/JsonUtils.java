package com.example.bakingapp.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonUtils {

    private static final Gson converter = new Gson();

    public static <T> T fromJson(String json, Type type) {
        return converter.fromJson(json, type);
    }

    public static String toJson(Object object) {
        return converter.toJson(object);
    }
}
