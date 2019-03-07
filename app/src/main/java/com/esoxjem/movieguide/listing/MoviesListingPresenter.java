package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.Movie;

import java.util.List;

/**
 * @author arun
 */
public interface MoviesListingPresenter
{
    void setView(MoviesListingView view);

    void destroy();

    void fetchFirstPage();

    void fetchNextPage();

    void searchMovie(String searchText);

    void searchMovieBackPressed();

    List<Movie> getCurrentData();

    Movie getFirstMovie();
}
