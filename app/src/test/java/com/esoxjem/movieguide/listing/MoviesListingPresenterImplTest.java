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

    @Test
    public void fetchFirstPage_success_correctPageToInteractor() {
        // given:
        ArgumentCaptor<Integer> acInt = ArgumentCaptor.forClass(Integer.class);
        // when:
        SUT.fetchFirstPage();

        // then:
        verify(interactor).fetchMovies(acInt.capture(), any(Listener.class));
        assertThat(acInt.getValue(), is(1));
    }

    // fetch first page - success -

    // fetch next page - success - correct page go to interactor

    //
}