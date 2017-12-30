package com.example.arc.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.arc.BuildConfig;
import com.example.arc.api.Api;
import com.example.arc.core.AppSchedulerProvider;
import com.example.arc.core.Constants;
import com.example.arc.model.db.AppDatabase;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author ihsan on 12/2/17.
 */

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    AppSchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    SharedPreferences provideSplashViewModel(Application context) {
        return context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    LoggingInterceptor provideInterceptor() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttp(LoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://newsapi.org/v2/")
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Application context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, Constants.DB)
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }
}
