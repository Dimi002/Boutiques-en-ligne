package com.ibrasoft.storeStackProd.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializer {
    public static String toJson(Object obj, Class<?> objClass) {
        String json = null;
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        json = gson.toJson(obj, objClass);
        return json;
    }

    public static <T> T fromJson(String json, Class<T> objClass) {
        T obj;
        if (json != null) {
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.create();
            obj = gson.fromJson(json, objClass);
        } else
            obj = null;
        return obj;
    }
}
