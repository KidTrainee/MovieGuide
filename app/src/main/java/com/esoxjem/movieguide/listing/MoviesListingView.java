package com.esoxjem.movieguide.listing;

import android.os.Bundle;

import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.common.ObservableView;

import java.util.List;

/**
 * @author arun
 */
interface MoviesListingView extends ObservableView<MoviesListingView.Listener>
{
    // view generates these events
    interface Listener {
        void searchViewClicked(String searchText);

        void searchViewBackButtonClicked();

        void onDestroyView();

        void onViewCreated(Bundle savedInstanceState);

        void onActionSortSelected();

        void onSaveInstanceState(Bundle outState);

        void onLoadMore();

        void onRetry();
    }

    void searchViewClicked(String searchText);
    void searchViewBackButtonClicked();
    void showMovies();
    void showFirstMovie();
    void loadingStarted();
    void loadingFinished();
    void loadingFailed(String errorMessage);
    void onMovieClicked(Movie movie);
    void showRetryIndicator();
}
