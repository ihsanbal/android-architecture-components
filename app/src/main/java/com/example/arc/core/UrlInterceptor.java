package com.example.arc.core;

import android.support.annotation.NonNull;

import com.example.arc.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author ihsan on 12/30/17.
 */

public class UrlInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter("apiKey", BuildConfig.API_KEY).build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
