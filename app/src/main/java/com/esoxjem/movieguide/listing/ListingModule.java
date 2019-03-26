package com.esoxjem.movieguide.listing;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.esoxjem.movieguide.AppModule;

/**
 * @author pulkitkumar
 * @author arunsasidharan
 */
public class ListingModule {

    private final AppModule mAppModule;
    private final AppCompatActivity mActivity;

    public ListingModule(AppModule appModule, AppCompatActivity activity) {
        mAppModule = appModule;
        mActivity = activity;
    }

    public Context getContext() {
        return mActivity;
    }

    MoviesListingInteractor getMovieListingInteractor() {
        return new MoviesListingInteractorImpl(
                mAppModule.getFavoritesInteractor(),
                mAppModule.getTmdbWebService(),
                mAppModule.getSortingOptionStore());
    }

    public MoviesListingPresenter getMoviesListingPresenter() {
        return new MoviesListingPresenterImpl(getMovieListingInteractor());
    }
}
