package com.esoxjem.movieguide.common.mocks;

import com.esoxjem.movieguide.MoviesWrapper;
import com.esoxjem.movieguide.ReviewsWrapper;
import com.esoxjem.movieguide.VideoWrapper;
import com.esoxjem.movieguide.network.TmdbWebService;

import io.reactivex.Observable;

public class EmptyDataTmdbWebServiceMock implements TmdbWebService {
    @Override
    public Observable<MoviesWrapper> popularMovies(int page) {
        return Observable.just(new MoviesWrapper());
    }

    @Override
    public Observable<MoviesWrapper> highestRatedMovies(int page) {
        return Observable.just(new MoviesWrapper());
    }

    @Override
    public Observable<MoviesWrapper> newestMovies(String maxReleaseDate, int minVoteCount) {
        return Observable.just(new MoviesWrapper());
    }

    @Override
    public Observable<VideoWrapper> trailers(String movieId) {
        return null;
    }

    @Override
    public Observable<ReviewsWrapper> reviews(String movieId) {
        return null;
    }

    @Override
    public Observable<MoviesWrapper> searchMovies(String searchQuery) {
        return Observable.just(new MoviesWrapper());
    }
}
