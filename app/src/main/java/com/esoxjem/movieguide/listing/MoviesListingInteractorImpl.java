package com.esoxjem.movieguide.listing;

import android.support.annotation.NonNull;

import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.MoviesWrapper;
import com.esoxjem.movieguide.favorites.FavoritesInteractor;
import com.esoxjem.movieguide.sorting.SortType;
import com.esoxjem.movieguide.sorting.SortingOptionStore;
import com.esoxjem.movieguide.network.TmdbWebService;
import com.esoxjem.movieguide.util.EspressoIdlingResource;
import com.esoxjem.movieguide.util.rx.RxUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author arun
 */
class MoviesListingInteractorImpl implements MoviesListingInteractor {
    private static final String TAG = MoviesListingInteractorImpl.class.getSimpleName();
    private FavoritesInteractor favoritesInteractor;
    private TmdbWebService tmdbWebService;
    private SortingOptionStore sortingOptionStore;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final int NEWEST_MIN_VOTE_COUNT = 50;

    private Disposable movieSearchSubscription;
    private Disposable fetchSubscription;

    MoviesListingInteractorImpl(FavoritesInteractor favoritesInteractor,
                                TmdbWebService tmdbWebService,
                                SortingOptionStore store) {
        this.favoritesInteractor = favoritesInteractor;
        this.tmdbWebService = tmdbWebService;
        sortingOptionStore = store;
    }

    @Override
    public void cancel() {
        RxUtils.unsubscribe(fetchSubscription, movieSearchSubscription);
    }

    @Override
    public boolean isPaginationSupported() {
        int selectedOption = sortingOptionStore.getSelectedOption();
        return selectedOption != SortType.FAVORITES.getValue();
    }

    private Observable<List<Movie>> fetchMovies(int page) {
        int selectedOption = sortingOptionStore.getSelectedOption();
        if (selectedOption == SortType.MOST_POPULAR.getValue()) {
            return tmdbWebService.popularMovies(page).map(moviesWrapper -> moviesWrapper.getMovieList());
        } else if (selectedOption == SortType.HIGHEST_RATED.getValue()) {
            return tmdbWebService.highestRatedMovies(page).map(MoviesWrapper::getMovieList);
        } else if (selectedOption == SortType.NEWEST.getValue()) {
            Calendar cal = Calendar.getInstance();
            String maxReleaseDate = dateFormat.format(cal.getTime());
            return tmdbWebService.newestMovies(maxReleaseDate, NEWEST_MIN_VOTE_COUNT).map(MoviesWrapper::getMovieList);
        } else {
            return Observable.just(favoritesInteractor.getFavorites());
        }
    }

    @Override
    public Observable<List<Movie>> searchMovie(@NonNull String searchQuery) {
        return tmdbWebService.searchMovies(searchQuery).map(MoviesWrapper::getMovieList);
    }

    @Override
    public void searchMovie(String searchText, Listener listener) {
        movieSearchSubscription = searchMovie(searchText).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onMovieSearchSuccess, listener::onMovieSearchFailed);
    }

    @Override
    public void fetchMovies(int page, Listener listener) {
        EspressoIdlingResource.increment();
        fetchSubscription = fetchMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement(); // Set app as idle.
                    }
                })
                .subscribe(listener::onMovieFetchSuccess, listener::onMovieFetchFailed);
    }
}
