package com.esoxjem.movieguide.moviedetails;

import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.moviedetails.entities.Review;
import com.esoxjem.movieguide.moviedetails.entities.Video;

import java.util.List;

/**
 * @author arun
 */
interface MovieDetailsView
{
    void showDetails(Movie movie);
    void showTrailers(List<Video> trailers);
    void showReviews(List<Review> reviews);
    void showFavorited();
    void showUnFavorited();
}
