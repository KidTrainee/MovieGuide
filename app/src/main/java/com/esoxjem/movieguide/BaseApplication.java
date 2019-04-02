package com.esoxjem.movieguide;

import android.app.Application;
import android.os.StrictMode;
import android.support.annotation.VisibleForTesting;

import io.realm.Realm;

/**
 * @author arun
 */
public class BaseApplication extends Application
{
    AppModule mAppModule;

    @Override
    public void onCreate()
    {
        super.onCreate();
        StrictMode.enableDefaults();
        initRealm();
        mAppModule = new AppModule(this);
    }

    public AppModule getAppModule() {
        return mAppModule;
    }

    private void initRealm(){
        Realm.init(this);
    }

    // Only visible for mocking in test, otherwise no other place should have access to this method
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setAppModule(AppModule appModule) {
        mAppModule = appModule;
    }
}
