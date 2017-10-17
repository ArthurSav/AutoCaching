package com.arthursaveliev.autocaching.api;

import com.arthursaveliev.autocaching.data.BoxManager;
import com.arthursaveliev.autocachingconveter.GsonCacheableConverter;
import com.arthursaveliev.autocachingconveter.GsonResponseListener;

import java.util.ArrayList;
import java.util.Collection;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


class ApiManager implements GsonResponseListener {

    private static final String URL = "https://jsonplaceholder.typicode.com/";
    private static ApiManager apiManager = new ApiManager();

    private ApiService apiService;

    static ApiService getService(){
        if (apiManager.apiService == null) {
            apiManager.apiService = apiManager.createRetrofit().create(ApiService.class);
        }
        return apiManager.apiService;
    }

    private Retrofit createRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(createClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonCacheableConverter.create(this))
                .build();
    }

    private OkHttpClient createClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(logging);
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCacheableResponse(Class type, Object responseBody) {
        if (responseBody instanceof Collection) BoxManager.getStore().boxFor(type).put((ArrayList) responseBody);
        else BoxManager.getStore().boxFor(type).put(responseBody);    }

}
