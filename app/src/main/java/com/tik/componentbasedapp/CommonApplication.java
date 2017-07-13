package com.tik.componentbasedapp;

import android.app.Application;

import com.github.mzule.activityrouter.annotation.Modules;

@Modules({"app", "moduleA"})
public class CommonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
