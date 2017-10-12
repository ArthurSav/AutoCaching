package com.arthursaveliev.autocaching.api.cache.converter;

import com.google.gson.TypeAdapter;

public interface GsonConverterListener {
    void onResponseBody(TypeAdapter typeAdapter, Object responseBody);
}
