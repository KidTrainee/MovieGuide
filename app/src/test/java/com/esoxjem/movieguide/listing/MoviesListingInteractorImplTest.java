package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.MoviesWrapper;
import com.esoxjem.movieguide.RxSchedulerRule;
import com.esoxjem.movieguide.favorites.FavoritesInteractor;
import com.esoxjem.movieguide.listing.MoviesListingInteractor.Listener;
import com.esoxjem.movieguide.listing.sorting.SortType;
import com.esoxjem.movieguide.listing.sorting.SortingOptionStore;
import com.esoxjem.movieguide.network.TmdbWebService;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.any;
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
    List<String> methodsToMock = Arrays.asList("highestRatedMovies", "newestMovies", "popularMovies");

    // endregion helper fields ---------------------------------------------------------------------

    MoviesListingInteractorImpl SUT;

    @Before
    public void setUp() {
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
        mTmdbWebService = mock(TmdbWebService.class, invocationOnMock -> {
            Method mockedMethod = invocationOnMock.getMethod();
            if (methodsToMock.contains(mockedMethod.getName())) {
                return MOVIES_WRAPPER_OBSERVABLE;
            }
            return null;
        });
        when(mFavoritesInteractor.getFavorites()).thenReturn(MOVIE_ARRAY_LIST);
        SUT = new MoviesListingInteractorImpl(mFavoritesInteractor,
                mTmdbWebService, mSortingOptionStore);
    }

    private void generalError() throws Exception {
        mTmdbWebService = mock(TmdbWebService.class, invocationOnMock -> {
            Method mockedMethod = invocationOnMock.getMethod();
            if (methodsToMock.contains(mockedMethod.getName())) {
                return ERROR_IN_OBSERVABLE;
            }
            return null;
        });

        setUp();
    }

    private void networkError() throws Exception {
        mTmdbWebService = mock(TmdbWebService.class, invocationOnMock -> {
            Method mockedMethod = invocationOnMock.getMethod();
            if (methodsToMock.contains(mockedMethod.getName())) {
                return new UnknownHostException();
            }
            return null;
        });
        when(mFavoritesInteractor.getFavorites()).thenThrow(new UnknownHostException());

        setUp();
    }

    private void emptyDataError() throws Exception{
        mTmdbWebService = mock(TmdbWebService.class, invocationOnMock -> {
            Method mockedMethod = invocationOnMock.getMethod();
            if (methodsToMock.contains(mockedMethod.getName())) {
                return EMPTY_DATA_OBSERVABLE;
            }
            return null;
        });
        when(mFavoritesInteractor.getFavorites()).thenReturn(new ArrayList());
        setUp();
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