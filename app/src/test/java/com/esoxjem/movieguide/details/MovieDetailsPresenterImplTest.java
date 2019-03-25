package com.esoxjem.movieguide.details;

import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.details.domain.GetReviewsUseCase;
import com.esoxjem.movieguide.details.domain.GetTrailersUseCase;
import com.esoxjem.movieguide.details.entities.Review;
import com.esoxjem.movieguide.details.entities.Video;
import com.esoxjem.movieguide.RxSchedulerRule;
import com.esoxjem.movieguide.favorites.FavoritesInteractor;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.SocketTimeoutException;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * @author arunsasidharan
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieDetailsPresenterImplTest {
    @Rule
    public RxSchedulerRule rule = new RxSchedulerRule();
    @Mock
    private MovieDetailsView view;
    @Mock
    private GetTrailersUseCase mGetTrailersUseCase;
    @Mock
    private GetReviewsUseCase mGetReviewsUseCase;
    @Mock
    private FavoritesInteractor favoritesInteractor;
    @Mock
    List<Video> videos;
    @Mock
    Movie movie;
    @Mock
    List<Review> reviews;

    private MovieDetailsPresenterImpl movieDetailsPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        movieDetailsPresenter = new MovieDetailsPresenterImpl(mGetTrailersUseCase, mGetReviewsUseCase, favoritesInteractor);
        movieDetailsPresenter.setView(view);
    }

    @After
    public void teardown() {
        movieDetailsPresenter.destroy();
    }

    @Test
    public void shouldUnfavoriteIfFavoriteTapped() {
        when(movie.getId()).thenReturn("12345");
        when(favoritesInteractor.isFavorite(movie.getId())).thenReturn(true);

        movieDetailsPresenter.onFavoriteClick(movie);

        verify(view).showUnFavorited();
    }

    @Test
    public void shouldFavoriteIfUnfavoriteTapped() {
        when(movie.getId()).thenReturn("12345");
        when(favoritesInteractor.isFavorite(movie.getId())).thenReturn(false);

        movieDetailsPresenter.onFavoriteClick(movie);

        verify(view).showFavorited();
    }

    @Test
    public void shouldBeAbleToShowTrailers() {
        when(movie.getId()).thenReturn("12345");
        Observable<List<Video>> responseObservable = Observable.just(videos);
        when(mGetTrailersUseCase.execute(movie.getId())).thenReturn(responseObservable);

        movieDetailsPresenter.showTrailers(movie);

        verify(view).showTrailers(videos);
    }

    @Test
    public void shouldFailSilentlyWhenNoTrailers() throws Exception {
        when(movie.getId()).thenReturn("12345");
        when(mGetTrailersUseCase.execute(movie.getId())).thenReturn(Observable.error(new SocketTimeoutException()));

        movieDetailsPresenter.showTrailers(movie);

        verifyZeroInteractions(view);
    }

    @Test
    public void shouldBeAbleToShowReviews() {
        Observable<List<Review>> responseObservable = Observable.just(reviews);
        when(movie.getId()).thenReturn("12345");
        when(mGetReviewsUseCase.execute(movie.getId())).thenReturn(responseObservable);

        movieDetailsPresenter.showReviews(movie);

        verify(view).showReviews(reviews);
    }


    @Test
    public void shouldFailSilentlyWhenNoReviews() throws Exception {
        when(movie.getId()).thenReturn("12345");
        when(mGetReviewsUseCase.execute(movie.getId())).thenReturn(Observable.error(new SocketTimeoutException()));

        movieDetailsPresenter.showReviews(movie);

        verifyZeroInteractions(view);
    }

}