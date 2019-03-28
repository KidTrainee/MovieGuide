package com.esoxjem.movieguide.listing;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.esoxjem.movieguide.Constants;
import com.esoxjem.movieguide.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * @author arun
 */
class MoviesListingPresenterImpl implements MoviesListingPresenter {

    private MoviesListingView mView;
    private MoviesListingInteractor moviesInteractor;

    private int mCurrentPage = 1;

    private boolean showingSearchResult = false;

    private List<Movie> mMovies = new ArrayList<>();

    MoviesListingPresenterImpl(MoviesListingInteractor interactor) {
        moviesInteractor = interactor;
    }

    @Override
    public void setView(MoviesListingView view) {
        mView = view;
        mView.registerListener(this);
    }

    public MoviesListingView getView() {
        return mView;
    }

    @Override
    public void destroy() {
        mView.unregisterListener(this);
        mView = null;
        moviesInteractor.cancel();
    }

    @Override
    public List<Movie> getCurrentData() {
        return mMovies;
    }

    @Override
    public Movie getFirstMovie() {
        return mMovies.get(0);
    }

    private void fetchMoviesCurrentPage() {
        showLoading();
        moviesInteractor.fetchMovies(mCurrentPage, this);
    }

    private void fetchMovieSearchResult(@NonNull final String searchText) {
        showingSearchResult = true;
        showLoading();
        moviesInteractor.searchMovie(searchText, this);
    }

    @Override
    public void fetchFirstPage() {
        mCurrentPage = 1;
        mMovies.clear();
        fetchMoviesCurrentPage();
    }

    @Override
    public void fetchNextPage() {
        if(showingSearchResult)
            return;
        if (moviesInteractor.isPaginationSupported()) {
            mCurrentPage++;
            fetchMoviesCurrentPage();
        }
    }

    @Override
    public void searchMovie(final String searchText) {
        if(searchText == null || searchText.length() < 1) {
            fetchMoviesCurrentPage();
        } else {
            fetchMovieSearchResult(searchText);
        }
    }

    @Override
    public void searchMovieBackPressed() {
        if(showingSearchResult) {
            showingSearchResult = false;
            mMovies.clear();
            fetchMoviesCurrentPage();
        }
    }

    private void showLoading() {
        if (isViewAttached()) {
            mView.loadingStarted();
        }
    }

    // region Helper methods
    private boolean isViewAttached() {
        return mView != null;
    }
    // endregion

    // region InteractorListener methods
    @Override
    public void onMovieFetchSuccess(List<Movie> movies) {
        if (moviesInteractor.isPaginationSupported()) {
            mMovies.addAll(movies);
        } else {
            mMovies.clear();
            mMovies.addAll(movies);
        }
        if (isViewAttached()) {
            mView.loadingFinished();
            mView.showMovies();
        }
    }

    @Override
    public void onMovieFetchFailed(Throwable e) {
        if (isViewAttached()) {
            mView.loadingFailed(e.getMessage());
        }
    }

    @Override
    public void onMovieSearchSuccess(List<Movie> movies) {
        if (isViewAttached()) {
            mMovies.clear();
            mMovies.addAll(movies);
            mView.loadingFinished();
            mView.showMovies();
        }
    }

    @Override
    public void onMovieSearchFailed(Throwable e) {
        if (isViewAttached()) {
            mView.loadingFailed(e.getMessage());
        }
    }
    // endregion

    // region ViewListener methods
    @Override
    public void searchViewClicked(String searchText) {
        searchMovie(searchText);
    }

    @Override
    public void searchViewBackButtonClicked() {
        searchMovieBackPressed();
    }

    @Override
    public void onDestroyView() {
        destroy();
    }

    @Override
    public void onViewCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.mMovies.addAll(savedInstanceState.getParcelableArrayList(Constants.MOVIE));
            if (!mMovies.isEmpty() && isViewAttached()) {
                mView.showMovies();
                mView.showFirstMovie();
            }
        } else {
            fetchFirstPage();
        }
    }

    @Override
    public void onActionSortSelected() {
        fetchFirstPage();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.MOVIE, (ArrayList<? extends Parcelable>) mMovies);
    }

    @Override
    public void onLoadMore() {
        fetchNextPage();
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }
    // endregion
}
