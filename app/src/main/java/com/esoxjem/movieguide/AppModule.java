package com.esoxjem.movieguide;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.esoxjem.movieguide.favorites.FavoritesInteractor;
import com.esoxjem.movieguide.favorites.FavoritesModule;
import com.esoxjem.movieguide.favorites.FavoritesStore;
import com.esoxjem.movieguide.network.NetworkModule;
import com.esoxjem.movieguide.network.TmdbWebService;

import io.realm.Realm;

/**
 * @author arunsasidharan
 * @author pulkitkumar
 */
public class AppModule
{
    private Context context;

    AppModule(Application application)
    {
        context = application;
    }

    public Context getContext()
    {
        return context;
    }

    public Realm getRealm()
    {
        return Realm.getDefaultInstance();
    }

    public TmdbWebService getTmdbWebService() {
        return getNetworkModule().getTmdbWebService();
    }

    private NetworkModule getNetworkModule() {
        return new NetworkModule();
    }

    public FavoritesStore getFavoritesStore() {
        return new FavoritesStore(getRealm());
    }

    public FavoritesInteractor getFavoritesInteractor() {
        return new FavoritesModule(this).getFavouritesInteractor();
    }
}
