package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.MoviesWrapper;
import com.esoxjem.movieguide.RxSchedulerRule;
import com.esoxjem.movieguide.favorites.FavoritesInteractor;
import com.esoxjem.movieguide.listing.domain.MoviesListingInteractor.Listener;
import com.esoxjem.movieguide.listing.domain.MoviesListingInteractorImpl;
import com.esoxjem.movieguide.sorting.SortType;
import com.esoxjem.movieguide.sorting.SortingOptionStore;
import com.esoxjem.movieguide.network.TmdbWebService;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoviesListingInteractorImplTest {

    // region constants ----------------------------------------------------------------------------
    private final int PAGE = 1;
    private ArrayList<Movie> MOVIE_ARRAY_LIST = new ArrayList<>();
    private Observable<MoviesWrapper> MOVIES_WRAPPER_OBSERVABLE;
    private Observable<MoviesWrapper> EMPTY_DATA_OBSERVABLE;

    private static final Observable<MoviesWrapper> ERROR_IN_OBSERVABLE =
            Observable.error(new RuntimeException("error in observable"));
    // endregion constants -------------------------------------------------------------------------
    // region helper fields ------------------------------------------------------------------------
    @Rule
    public RxSchedulerRule rule = new RxSchedulerRule();
    @Mock
    FavoritesInteractor mFavoritesInteractor;
    @Mock
    TmdbWebService mTmdbWebService;
    @Mock
    SortingOptionStore mSortingOptionStore;
    @Mock
    Listener mListener;

    // endregion helper fields ---------------------------------------------------------------------

    MoviesListingInteractorImpl SUT;

    @Before
    public void setUp() throws Exception {
        initData();

        SUT = new MoviesListingInteractorImpl(
                mFavoritesInteractor,
                mTmdbWebService,
                mSortingOptionStore);
    }

    // test cases
    // fetchMovies - correct page pass to WebService
    // fetchMovies - correct SortingOption pass to WebService
    @Test
    public void fetchMovies_correctWebServiceFunctionCalled_HIGHEST_RATED() throws Exception {
        // Arrange
        when(mSortingOptionStore.getSelectedOption()).thenReturn(SortType.HIGHEST_RATED.getValue());
        serviceCallSuccess();
        // Act
        SUT.fetchMovies(PAGE, mListener);
        // Assert
        verify(mTmdbWebService).highestRatedMovies(anyInt());
    }

    @Test
    public void fetchMovies_correctWebServiceFunctionCalled_MOST_POPULAR() throws Exception {
        // Arrange
        when(mSortingOptionStore.getSelectedOption()).thenReturn(SortType.MOST_POPULAR.getValue());
        serviceCallSuccess();
        // Act
        SUT.fetchMovies(PAGE, mListener);
        // Assert
        verify(mTmdbWebService).popularMovies(anyInt());
    }

    @Test
    public void fetchMovies_correctWebServiceFunctionCalled_NEWEST() throws Exception {
        // Arrange
        when(mSortingOptionStore.getSelectedOption()).thenReturn(SortType.NEWEST.getValue());
        serviceCallSuccess();
        // Act
        SUT.fetchMovies(PAGE, mListener);
        // Assert
        verify(mTmdbWebService).newestMovies(anyString(), anyInt());
    }

    @Test
    public void fetchMovies_correctFavoriteInteractorFunctionCalled_FAVORITE() throws Exception {
        // Arrange
        when(mSortingOptionStore.getSelectedOption()).thenReturn(SortType.FAVORITES.getValue());
        serviceCallSuccess();

        // Act
        SUT.fetchMovies(PAGE, mListener);
        // Assert
        verify(mFavoritesInteractor).getFavorites();
    }

    // fetchMovies - success - onMoviesFetchSuccess called
    @Test
    public void fetchMovies_success_onMoviesFetchSuccessCalled_HIGHEST_RATE() throws Exception {
        // Arrange
        when(mSortingOptionStore.getSelectedOption()).thenReturn(SortType.HIGHEST_RATED.getValue());
        serviceCallSuccess();

        // Act
        SUT.fetchMovies(PAGE, mListener);
        // Assert
        verify(mListener).onMovieFetchSuccess(anyList());
    }

    @Test
    public void fetchMovies_success_onMoviesFetchSuccessCalled_NEWEST() throws Exception {
        // Arrange
        when(mSortingOptionStore.getSelectedOption()).thenReturn(SortType.NEWEST.getValue());
        serviceCallSuccess();

        // Act
        SUT.fetchMovies(PAGE, mListener);
        // Assert
        verify(mListener).onMovieFetchSuccess(anyList());
    }

    @Test
    public void fetchMovies_success_onMoviesFetchSuccessCalled_MOST_POPULAR() throws Exception {
        // Arrange
        when(mSortingOptionStore.getSelectedOption()).thenReturn(SortType.MOST_POPULAR.getValue());
        serviceCallSuccess();
        // Act
        SUT.fetchMovies(PAGE, mListener);
        // Assert
        verify(mListener).onMovieFetchSuccess(anyList());
    }

    @Test
    public void fetchMovies_success_onMoviesFetchSuccessCalled_FAVORITES() throws Exception {
        // Arrange
        when(mSortingOptionStore.getSelectedOption()).thenReturn(SortType.FAVORITES.getValue());
        serviceCallSuccess();
        // Act
        SUT.fetchMovies(PAGE, mListener);
        // Assert
        verify(mListener).onMovieFetchSuccess(anyList());
    }

    // fetchMovies - fail - onMoviesFetchFailure called
    @Test
    public void fetchMovies_failure_onMoviesFetchFailureCalled_MOST_POPULAR() throws Exception {
        // Arrange
        when(mSortingOptionStore.getSelectedOption()).thenReturn(SortType.HIGHEST_RATED.getValue());
        generalError();
        // Act
        SUT.fetchMovies(PAGE, mListener);
        // Assert
        verify(mListener).onMovieFetchFailed(any(Throwable.class));
    }

    // region helper methods -----------------------------------------------------------------------

    private void serviceCallSuccess() {
        when(mTmdbWebService.highestRatedMovies(anyInt())).thenReturn(MOVIES_WRAPPER_OBSERVABLE);
        when(mTmdbWebService.newestMovies(anyString(),anyInt())).thenReturn(MOVIES_WRAPPER_OBSERVABLE);
        when(mTmdbWebService.popularMovies(anyInt())).thenReturn(MOVIES_WRAPPER_OBSERVABLE);
        when(mFavoritesInteractor.getFavorites()).thenReturn(MOVIE_ARRAY_LIST);
    }

    private void generalError() {
        when(mTmdbWebService.highestRatedMovies(anyInt())).thenReturn(ERROR_IN_OBSERVABLE);
        when(mTmdbWebService.newestMovies(anyString(), anyInt())).thenReturn(ERROR_IN_OBSERVABLE);
        when(mTmdbWebService.popularMovies(anyInt())).thenReturn(ERROR_IN_OBSERVABLE);
//        when(mFavoritesInteractor.getFavorites()).thenThrow(new Throwable("error in observable"));
    }

    private void networkError() {
        when(mTmdbWebService.highestRatedMovies(anyInt())).thenThrow(new Throwable("network error"));
        when(mTmdbWebService.newestMovies(anyString(), anyInt())).thenThrow(new Throwable("network error"));
        when(mTmdbWebService.popularMovies(anyInt())).thenThrow(new Throwable("network error"));
        when(mFavoritesInteractor.getFavorites()).thenThrow(new Throwable("network error"));
    }

    private void emptyDataError() {

        when(mTmdbWebService.highestRatedMovies(anyInt())).thenReturn(EMPTY_DATA_OBSERVABLE);
        when(mTmdbWebService.newestMovies(anyString(), anyInt())).thenReturn(EMPTY_DATA_OBSERVABLE);
        when(mTmdbWebService.popularMovies(anyInt())).thenReturn(EMPTY_DATA_OBSERVABLE);
        when(mFavoritesInteractor.getFavorites()).thenReturn(new ArrayList());
    }

    private void initData() {
        MOVIE_ARRAY_LIST.add(new Movie());
        MOVIE_ARRAY_LIST.add(new Movie());
        MoviesWrapper moviesWrapper = new MoviesWrapper();
        moviesWrapper.setMovieList(MOVIE_ARRAY_LIST);
        MOVIES_WRAPPER_OBSERVABLE = Observable.fromArray(moviesWrapper);

        MoviesWrapper emptyMoviesWrapper= new MoviesWrapper();
        emptyMoviesWrapper.setMovieList(new ArrayList<>());
        EMPTY_DATA_OBSERVABLE = Observable.fromArray(emptyMoviesWrapper);
    }
    // endregion helper methods --------------------------------------------------------------------
    // region helper class -------------------------------------------------------------------------

    // endregion helper class ----------------------------------------------------------------------
}