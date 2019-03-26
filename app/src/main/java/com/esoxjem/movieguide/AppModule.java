package com.esoxjem.movieguide;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.esoxjem.movieguide.favorites.FavoritesInteractor;
import com.esoxjem.movieguide.favorites.FavoritesModule;
import com.esoxjem.movieguide.favorites.FavoritesStore;
import com.esoxjem.movieguide.sorting.SortingOptionStore;
import com.esoxjem.movieguide.network.NetworkModule;
import com.esoxjem.movieguide.network.TmdbWebService;
import com.esoxjem.movieguide.util.schedulers.BaseSchedulerProvider;
import com.esoxjem.movieguide.util.schedulers.SchedulerProvider;

import io.realm.Realm;

import static com.esoxjem.movieguide.sorting.SortingOptionStore.PREF_NAME;

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

    public SharedPreferences getAppDefaultSharedPref() {
        SharedPreferences pref =getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return pref;
    }

    public SortingOptionStore getSortingOptionStore() {
        return new SortingOptionStore(getAppDefaultSharedPref());
    }

    public BaseSchedulerProvider getSchedulerProvider() {
        return new SchedulerProvider();
    }
}
