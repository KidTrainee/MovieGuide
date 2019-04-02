package com.esoxjem.movieguide.common.mocks;

import com.esoxjem.movieguide.BuildConfig;
import com.esoxjem.movieguide.network.NetworkModule;
import com.esoxjem.movieguide.network.TmdbWebService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class NetworkModuleMock extends NetworkModule {
    @Override
    public Retrofit getRetrofit() {
        return new Retrofit
                .Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    @Override
    public TmdbWebService getTmdbWebService() {
        return new EmptyDataTmdbWebServiceMock();
    }
}
