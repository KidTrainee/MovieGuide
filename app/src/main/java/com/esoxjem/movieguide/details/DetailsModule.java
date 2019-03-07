package com.esoxjem.movieguide.details;

import com.esoxjem.movieguide.AppModule;

/**
 * @author pulkitkumar
 * @author arunsasidharan
 */
public class DetailsModule {

    private final AppModule mAppModule;

    public DetailsModule(AppModule appModule) {
        mAppModule = appModule;
    }

    private MovieDetailsInteractor getDetailsInteractor() {
        return new MovieDetailsInteractorImpl(mAppModule.getTmdbWebService());
    }


    public MovieDetailsPresenter getMovieDetailsPresenter() {
        return new MovieDetailsPresenterImpl(getDetailsInteractor(), mAppModule.getFavoritesInteractor());
    }
}
