package com.esoxjem.movieguide.details;

import com.esoxjem.movieguide.AppModule;
import com.esoxjem.movieguide.details.domain.Pojo2DomainMapper;
import com.esoxjem.movieguide.details.domain.GetTrailersUseCase;
import com.esoxjem.movieguide.details.domain.GetReviewsUseCase;

/**
 * @author pulkitkumar
 * @author arunsasidharan
 */
public class DetailsModule {

    private final AppModule mAppModule;

    public DetailsModule(AppModule appModule) {
        mAppModule = appModule;
    }

    private GetReviewsUseCase getReviewsUseCase() {
        return new GetReviewsUseCase(mAppModule.getTmdbWebService(), getDomainDataMapper());
    }

    private GetTrailersUseCase getTrailerUseCase() {
        return new GetTrailersUseCase(mAppModule.getTmdbWebService(), getDomainDataMapper());
    }

    private Pojo2DomainMapper getDomainDataMapper() {
        return new Pojo2DomainMapper();
    }


    public MovieDetailsPresenter getMovieDetailsPresenter() {
        return new MovieDetailsPresenterImpl(getTrailerUseCase(), getReviewsUseCase(), mAppModule.getFavoritesInteractor());
    }
}
