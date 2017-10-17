package com.arthursaveliev.autocachingconveter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class GsonCacheableConverter extends Converter.Factory {

  public static GsonCacheableConverter create(GsonResponseListener listener){
    return create(new Gson(), listener);
  }

  /**
   * Create an instance using {@code gson} for conversion. Encoding to JSON and
   * decoding from JSON (when no charset is specified by a header) will use UTF-8.
   */
  @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
  public static GsonCacheableConverter create(Gson gson, GsonResponseListener listener) {
    if (gson == null) throw new NullPointerException("gson == null");
    return new GsonCacheableConverter(gson, listener);
  }

  private final Gson gson;
  private final GsonResponseListener listener;

  private GsonCacheableConverter(Gson gson, GsonResponseListener listener) {
    this.gson = gson;
    this.listener = listener;
  }

  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
    TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
    return new GsonResponseBodyConverter<>(gson, adapter, type, annotations, listener);
  }

  @Override
  public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
    TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
    return new GsonRequestBodyConverter<>(gson, adapter);
  }
}
