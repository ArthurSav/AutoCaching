package com.arthursaveliev.autocaching.api;

import com.arthursaveliev.autocaching.api.model.Post;
import com.arthursaveliev.autocachingconveter.Cacheable;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;

public interface ApiService {

    @Cacheable
    @GET("/posts")
    Observable<Result<List<Post>>> posts();
}
