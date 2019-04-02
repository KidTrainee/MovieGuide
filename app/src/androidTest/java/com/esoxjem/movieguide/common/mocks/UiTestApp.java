package com.esoxjem.movieguide.common.mocks;

import com.esoxjem.movieguide.AppModule;
import com.esoxjem.movieguide.BaseApplication;

public class UiTestApp extends BaseApplication {

    @Override
    public AppModule getAppModule() {
        return new AppModuleMock(this);
    }
}
