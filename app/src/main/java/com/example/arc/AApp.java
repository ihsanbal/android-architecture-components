package com.example.arc;

import com.example.arc.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * @author ihsan on 11/29/17.
 */

public class AApp extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .application(this)
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
