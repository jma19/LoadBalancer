package com.uci.utils;

import com.google.api.client.testing.json.AbstractJsonParserTest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;

public class JsonUtils {
    private JsonUtils() {
    }

    private final static Gson gson = new Gson();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T toObject(String json, Class<T> clas) {
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, clas);
    }

    public static <T> T fromJson(String json, TypeToken<T> type) {
        return gson.fromJson(json, type.getType());
    }

}