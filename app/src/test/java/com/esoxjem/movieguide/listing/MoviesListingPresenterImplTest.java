package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.RxSchedulerRule;
import com.esoxjem.movieguide.listing.domain.MoviesListingInteractor;
import com.esoxjem.movieguide.listing.domain.MoviesListingInteractor.Listener;

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
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author arunsasidharan
 */
@RunWith(MockitoJUnitRunner.class)
public class MoviesListingPresenterImplTest {
    @Rule
    public RxSchedulerRule rule = new RxSchedulerRule();
    @Mock
    private MoviesListingInteractor mInteractor;
    @Mock
    private MoviesListingView mView;

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
    // fetchFirstPage - fail - view loading failed, hide loading indicator
    // fetchNextPage - correct page to indicator
    // fetchNextPage - view show loading indicator
    // fetchNextPage - success - view show list MOVIE_LIST, hide loading indicator
    // fetchNextPage - fail - view show load failed, hide loading indicator
    // searchMovie - correct data go to mInteractor
    // searchMovie - view show loading indicator
    // searchMovie - success - view show list MOVIE_LIST, hide loading indicator
    // searchMovie - fail - view show loading failed, hide loading indicator
    // getCurrentData - return current data list
    // getFirstMovie - when movie list not empty - return the first one
    // getFirstMovie - when movie list is empty - do nothing
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
        verify(mView).showMovies();
        // don't test hideLoadingIndicator since show loading indicator with snackbar
    }

    // fetchFirstPage - fail -
    // - view loading failed
    // - hide loading indicator (no need since using snackbar
    @Test
    public void fetchFirstPage_fail_showLoadingFailed() {
        // Arrange
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        // Act
        SUT.onMovieFetchFailed(new Throwable("network error"));
        // Assert
        verify(mView, times(0)).showMovies();
        verify(mView).loadingFailed(ac.capture());
        assertThat(ac.getValue(), is("network error"));
        // don't test hideLoadingIndicator since show loading indicator with snackbar
    }

    // region Helper Methods

    private void initData() {
        MOVIE_LIST.add(new Movie());
    }

    // endregion
}