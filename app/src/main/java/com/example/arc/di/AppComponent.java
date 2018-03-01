package com.example.arc.di;

import com.example.arc.AApp;
import com.example.arc.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author ihsan on 12/2/17.
 */
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent extends AndroidInjector<AApp> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<AApp> {
    }
}
