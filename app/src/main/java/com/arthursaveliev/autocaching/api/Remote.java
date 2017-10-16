package com.arthursaveliev.autocaching.api;

import com.arthursaveliev.autocaching.api.model.Post;

import com.arthursaveliev.autocaching.api.model.User;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Remote {
    public static Observable<List<Post>> syncPosts() {
        return ApiManager.getService()
                .posts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(Results.isSuccessful())
                .map(listResult -> listResult.response().body());
    }
    public static Observable<List<User>> syncUsers() {
        return ApiManager.getService()
            .users()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter(Results.isSuccessful())
            .map(listResult -> listResult.response().body());
    }
    public static Observable<User> syncUser() {
        return ApiManager.getService()
            .user()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter(Results.isSuccessful())
            .map(listResult -> listResult.response().body());
    }
}
