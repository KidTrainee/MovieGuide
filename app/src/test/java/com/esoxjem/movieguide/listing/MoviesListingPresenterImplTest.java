package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.RxSchedulerRule;
import com.esoxjem.movieguide.listing.MoviesListingInteractor.Listener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author arunsasidharan
 */
@RunWith(MockitoJUnitRunner.class)
public class MoviesListingPresenterImplTest {
    public static final int FIRST_PAGE = 1;
    public static final int SECOND_PAGE = FIRST_PAGE + 1;
    @Rule
    public RxSchedulerRule rule = new RxSchedulerRule();
    @Mock
    private MoviesListingInteractor mInteractor;
    @Mock
    private MoviesListingView mView;

    @Mock
    private MoviesListingInteractor.Listener mListener;

    private List<Movie> MOVIE_LIST = new ArrayList<>();

    private MoviesListingPresenterImpl SUT;

    @Before
    public void setUp() throws Exception {
        initData();

        SUT = new MoviesListingPresenterImpl(mInteractor);

        SUT.setView(mView);

    }

    // ==> obvious cases
    // setView - register view, view is not null
    // destroy - unregister view, view is null
    // fetchFirstPage - correct page go to mInteractor
    // fetchFirstPage - view show loading indicator
    // fetchFirstPage - success - view show list MOVIE_LIST, hide loading indicator
    // * fetchFirstPage - internet fail - show retry view
    // fetchFirstPage - general fail - view loading failed, hide loading indicator
    // fetchNextPage - correct page to indicator
    // fetchNextPage - view show loading indicator
    // fetchNextPage - success - view show list MOVIE_LIST, hide loading indicator
    // * fetchNextPage - internet fail - view retry view
    // fetchNextPage - general fail - view show load failed, hide loading indicator
    // searchMovie - correct data go to mInteractor
    // searchMovie - view show loading indicator
    // searchMovie - success - view show list MOVIE_LIST, hide loading indicator
    // searchMovie - fail - view show loading failed, hide loading indicator
    // getCurrentData - return current data list
    // getFirstMovie - when movie list not empty - return the first one
    // getFirstMovie - when movie list is empty - do nothing
    // * on retry - correct data send to interactor
    // * on retry - show loading indicator view
    // * on retry - success - hide loading indicator
    // * on retry - success - view show list MOVIE_LIST, hide loading indicator
    // * on retry - fail - view show loading failed, hide loading indicator
    // ==> the following cases are a little implicit, requiring specs
    // searchMovieBackPressed - when showingSearchResult - fetchCurrentPage
    // searchMovieBackPressed - when not showingSearchResult - do nothing
    // ....

    @Test
    public void setView_success_viewRegistered() throws Exception {
        // Arrange
        // Act
        // Assert
        verify(mView).registerListener(SUT);
        assertThat(SUT.getView(), is(notNullValue()));
    }

    @Test
    public void destroy_viewUnregistered() throws Exception {
        // Arrange
        // Act
        SUT.destroy();
        // Assert
        verify(mView).unregisterListener(SUT);
        assertThat(SUT.getView(), is(nullValue()));
    }

    // fetchFirstPage - correct page go to mInteractor
    @Test
    public void fetchFirstPage_correctPageToInteractor() {
        // Arrange
        ArgumentCaptor<Integer> acInt = ArgumentCaptor.forClass(Integer.class);
        // Act
        SUT.fetchFirstPage();

        // Assert
        verify(mInteractor).fetchMovies(acInt.capture(), any(Listener.class));
        assertThat(acInt.getValue(), is(1));
    }

    // fetchFirstPage - view show loading indicator
    @Test
    public void fetchFirstPage_showLoadingIndicator() {
        // Arrange
        // Act
        SUT.fetchFirstPage();
        // Assert
        verify(mView).loadingStarted();
    }

    // fetchFirstPage - success - view show list MOVIE_LIST, hide loading indicator
    @Test
    public void fetchFirstPage_success_loadMoviesToView_hideLoadingIndicator() {
        // Arrange
        // Act
        SUT.onMovieFetchSuccess(MOVIE_LIST);
        // Assert
        verify(mView).loadingFinished();
        verify(mView).showMovies();
    }

    // fetchFirstPage - fail
    // - view loading failed
    // - hide loading indicator (no need since using snackbar)
    @Test
    public void fetchFirstPage_fail_showLoadingFailed() {
        // Arrange
        // Act
        SUT.onMovieFetchFailed(new Throwable(""));
        // Assert
        verify(mView, times(0)).showMovies();
        verify(mView).loadingFailed("");
    }

    // fetchFirstPage - internet fail - show retry indicator

    @Test
    public void fetchFirstPage_internetError_showRetryIndicator() throws Exception {
        // Arrange
        // Act
        SUT.onNetworkError();
        // Assert
        verify(mView).showRetryIndicator();
    }

    // fetchNextPage - internet fail - view retry view

    @Test
    public void fetchNextPage_internetError_showRetryIndicator() throws Exception {
        // Arrange
        // Act
        SUT.onNetworkError();
        // Assert
        verify(mView).showRetryIndicator();
    }
    
    // fetchFirstPage - retry - correct data send to interactor
    @Test
    public void fetchFirstPage_retry_correctDataSendToInteractor() throws Exception {
        // Arrange
        ArgumentCaptor<Integer> acInt = ArgumentCaptor.forClass(Integer.class);

        // Act
        SUT.fetchFirstPage();
        SUT.onRetry();
        // Assert
        // mInteractor call twice, one with fetch first movies, one after getting error.
        verify(mInteractor, times(2)).fetchMovies(acInt.capture(), any(Listener.class));
        assertThat(acInt.getValue(), is(FIRST_PAGE));
    }
    
    // fetchNextPage - retry - correct data send to interactor
    @Test
    public void fetchNextPage_retry_correctDataSendToInteractor() throws Exception {
        // Arrange
        ArgumentCaptor<Integer> acInt = ArgumentCaptor.forClass(Integer.class);
        when(mInteractor.isPaginationSupported()).thenReturn(true);
        // Act
        SUT.fetchFirstPage();
        SUT.fetchNextPage();
        SUT.onRetry();
        // Assert
        // mInteractor call twice, one with fetch first movies, one after getting error.
        verify(mInteractor, times(3)).fetchMovies(acInt.capture(), any(Listener.class));
        assertThat(acInt.getValue(), is(SECOND_PAGE));
    }
    
    // on retry - show loading indicator view

    @Test
    public void retry_showLoadingIndicator() throws Exception {
        // Arrange
        // Act
        SUT.onRetry();
        // Assert
        verify(mView).loadingStarted();
    }

    // on retry - success - view show list MOVIE_LIST, hide loading indicator
    @Test
    public void retry_success_hideLoadingIndicator() throws Exception {
        // Arrange
        // Act
        SUT.onRetry();
        SUT.onMovieFetchSuccess(MOVIE_LIST);
        // Assert
        verify(mView).loadingFinished();
        verify(mView).showMovies();
    }

    // on retry - fail - view show loading failed, hide loading indicator
    @Test
    public void retry_fail_hideLoadingIndicator() throws Exception {
        // Arrange

        // Act
        SUT.onRetry();
        SUT.onMovieFetchFailed(new Throwable(""));
        // Assert
        verify(mView).loadingFinished();
        verify(mView).loadingFailed("");
    }

    // on retry - internet error - view show retry indicator, hide loading indicator
    @Test
    public void retry_internetError_hideLoadingIndicator() throws Exception {
        // Arrange
        // Act
        SUT.onRetry();
        SUT.onNetworkError();
        // Assert
        verify(mView).showRetryIndicator();
    }

    // region Helper Methods

    private void initData() {
        MOVIE_LIST.add(new Movie());
    }

    // endregion

    // region Helper Classes

    // endregion
}