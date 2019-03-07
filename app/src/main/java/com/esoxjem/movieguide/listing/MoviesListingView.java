package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.common.ObservableView;

import java.util.List;

/**
 * @author arun
 */
interface MoviesListingView extends ObservableView<MoviesListingView.Listener>
{
    interface Listener {
        void searchViewClicked(String searchText);
        void searchViewBackButtonClicked();

        void onDestroyView();
    }

    void searchViewClicked(String searchText);
    void searchViewBackButtonClicked();
    void showMovies(List<Movie> movies);
    void loadingStarted();
    void loadingFailed(String errorMessage);
    void onMovieClicked(Movie movie);
}
