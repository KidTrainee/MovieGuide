package com.esoxjem.movieguide.favorites;

import com.esoxjem.movieguide.AppModule;

/**
 * @author pulkitkumar
 */
public class FavoritesModule
{
    private final AppModule mAppModule;

    public FavoritesModule(AppModule appModule) {
        mAppModule = appModule;
    }

    public FavoritesInteractor getFavouritesInteractor()
    {
        return new FavoritesInteractorImpl(mAppModule.getFavoritesStore());
    }
}
