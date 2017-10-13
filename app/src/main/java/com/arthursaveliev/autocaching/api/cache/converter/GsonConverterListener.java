package com.arthursaveliev.autocaching.api.cache.converter;

import com.google.gson.TypeAdapter;

import java.lang.reflect.Type;

public interface GsonConverterListener {
    void onResponseBody(TypeAdapter typeAdapter, Type type, Object responseBody);
}
