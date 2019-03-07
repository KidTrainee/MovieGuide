package com.esoxjem.movieguide;

import android.app.Application;
import android.os.StrictMode;

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
}
