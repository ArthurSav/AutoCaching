package com.arthursaveliev.autocaching;

import android.app.Application;

import com.arthursaveliev.autocaching.data.BoxManager;

import io.objectbox.android.AndroidObjectBrowser;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BoxManager.init(this);
    }
}
