package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.Movie;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @author arun
 */
public interface MoviesListingInteractor
{
    interface Listener {

        // region InteractorListener methods
        void onMovieFetchSuccess(List<Movie> movies);

        void onMovieFetchFailed(Throwable e);

        void onMovieSearchSuccess(List<Movie> movies);

        void onMovieSearchFailed(Throwable e);

        void onNetworkError();
    }

    boolean isPaginationSupported();
    void fetchMovies(int page, Listener listener);
    Observable<List<Movie>> searchMovie(String searchQuery);
    void searchMovie(String searchText, Listener listener);
    void cancel();
}
