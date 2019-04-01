package com.esoxjem.movieguide.listing;

import android.os.Bundle;

import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.common.ObservableView;
import com.esoxjem.movieguide.listing.sorting.SortingDialogFragment;

import java.util.List;

/**
 * @author arun
 */
public interface MoviesListingView extends ObservableView<MoviesListingView.Listener>
{
    // view generates these events
    interface Listener extends SortingDialogFragment.Listener {
        void searchViewClicked(String searchText);

        void searchViewBackButtonClicked();

        void onDestroyView();

        void onViewCreated(Bundle savedInstanceState);

        void onActionSortSelected();

        void onSaveInstanceState(Bundle outState);

        void onLoadMore();

        void onRetry();

        List<Movie> getCurrentData();

        Movie getFirstMovie();
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
