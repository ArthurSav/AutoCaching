package com.arthursaveliev.autocaching.data;

import android.content.Context;

import com.arthursaveliev.autocaching.BuildConfig;
import com.arthursaveliev.autocaching.api.model.MyObjectBox;
import com.arthursaveliev.autocaching.api.model.Post;

import java.util.List;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class BoxManager {

    private static BoxManager boxManager;
    private BoxStore boxStore;

    private BoxManager(BoxStore boxStore) {
        this.boxStore = boxStore;
    }

    public static void init(Context context){

        BoxStore boxStore = MyObjectBox.builder()
                .androidContext(context)
                .build();

        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(context);
        }

        boxManager = new BoxManager(boxStore);
    }

    public static BoxStore getStore() {
        return boxManager.boxStore;
    }
}
