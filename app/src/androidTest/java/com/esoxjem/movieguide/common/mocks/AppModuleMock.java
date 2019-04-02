package com.esoxjem.movieguide.common.mocks;

import android.app.Application;

import com.esoxjem.movieguide.AppModule;
import com.esoxjem.movieguide.network.NetworkModule;

public class AppModuleMock extends AppModule {
    public AppModuleMock(Application application) {
        super(application);
    }

    @Override
    public NetworkModule getNetworkModule() {
        return new NetworkModuleMock();
    }
}
