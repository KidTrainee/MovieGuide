package com.esoxjem.movieguide.network;


import android.support.annotation.VisibleForTesting;

import com.esoxjem.movieguide.BuildConfig;

import java.util.concurrent.TimeUnit;

import butterknife.internal.ListenerClass;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * @author arunsasidharan
 * @author pulkitkumar
 */
public class NetworkModule {

    public static final int CONNECT_TIMEOUT_IN_MS = 30000;

    public NetworkModule() {
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .addInterceptor(getRequestInterceptor());

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        return builder.build();
    }

    private Interceptor getRequestInterceptor() {
        return new RequestInterceptor();
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public Retrofit getRetrofit() {
        return new Retrofit
                .Builder()
                .baseUrl(BuildConfig.TMDB_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    public TmdbWebService getTmdbWebService() {
        return getRetrofit().create(TmdbWebService.class);
    }

}
