package com.arthursaveliev.autocaching.api.cache.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final Gson gson;
  private final TypeAdapter<T> adapter;
  private final GsonConverterListener listener;

  GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, GsonConverterListener listener) {
    this.gson = gson;
    this.adapter = adapter;
    this.listener = listener;
  }

  @Override public T convert(ResponseBody value) throws IOException {
    JsonReader jsonReader = gson.newJsonReader(value.charStream());
    try {
      T response = adapter.read(jsonReader);
      if (listener != null) listener.onResponseBody(adapter, response);
      return response;
    } finally {
      value.close();
    }
  }
}
