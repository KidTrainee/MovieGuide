package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.RxSchedulerRule;
import com.esoxjem.movieguide.listing.MoviesListingInteractor.Listener;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author arunsasidharan
 */
@RunWith(MockitoJUnitRunner.class)
public class MoviesListingPresenterImplTest {
    @Rule
    public RxSchedulerRule rule = new RxSchedulerRule();
    @Mock
    private MoviesListingInteractor interactor;
    @Mock
    private MoviesListingView view;

    private List<Movie> movies = new ArrayList<>();

    private MoviesListingPresenterImpl SUT;

    @Before
    public void setUp() throws Exception {

        SUT = new MoviesListingPresenterImpl(interactor);
        movies.add(new Movie());
        SUT.setView(view);

    }

    @After
    public void teardown() {
        SUT.destroy();
    }

    // ==> obvious cases
    // setView - register view, view is not null
    // destroy - unregister view, view is null
    // fetchFirstPage - correct page go to interactor
    // fetchFirstPage - view show loading indicator
    // fetchFirstPage - success - view show list movies, hide loading indicator
    // fetchFirstPage - fail - view loading failed, hide loading indicator
    // fetchNextPage - correct page to indicator
    // fetchNextPage - view show loading indicator
    // fetchNextPage - success - view show list movies, hide loading indicator
    // fetchNextPage - fail - view show load failed, hide loading indicator
    // searchMovie - correct data go to interactor
    // searchMovie - view show loading indicator
    // searchMovie - success - view show list movies, hide loading indicator
    // searchMovie - fail - view show loading failed, hide loading indicator
    // getCurrentData - return current data list
    // getFirstMovie - when movie list not empty - return the first one
    // getFirstMovie - when movie list is empty - do nothing

    // ==> the following cases are a little implicit, requiring specs
    // searchMovieBackPressed - when showingSearchResult - fetchCurrentPage
    // searchMovieBackPressed - when not showingSearchResult - do nothing
    // ....

    @Test
    public void fetchFirstPage_correctPageToInteractor() {
        // given:
        ArgumentCaptor<Integer> acInt = ArgumentCaptor.forClass(Integer.class);
        // when:
        SUT.fetchFirstPage();

        // then:
        verify(interactor).fetchMovies(acInt.capture(), any(Listener.class));
        assertThat(acInt.getValue(), is(1));
    }

}