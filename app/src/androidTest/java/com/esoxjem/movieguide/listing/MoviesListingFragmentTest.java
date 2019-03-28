package com.esoxjem.movieguide.listing;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.esoxjem.movieguide.R;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MoviesListingFragmentTest {

    // region constants ----------------------------------------------------------------------------
    @Rule
    public final ActivityTestRule<MoviesListingActivity> activityTestRule = new ActivityTestRule<>(MoviesListingActivity.class);

    private IdlingResource idlingResource;

    // endregion constants -------------------------------------------------------------------------
    // region helper fields ------------------------------------------------------------------------

    // endregion helper fields ---------------------------------------------------------------------

    MoviesListingFragment SUT;

    @Before
    public void setUp() throws Exception {
        // register IdlingResource
        idlingResource = activityTestRule.getActivity().getCountingIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void ableToShowFilterIcon() throws Exception {
        onView(withId(R.id.action_search)).check(matches(isDisplayed()));
    }

    // region test cases
    // able to show filter icon
    // filterIconClicked - FilterDialog displayed
    // able to show search icon
    // searchIconClicked - searchView open
    // searchIconClicked - onActionSortSelected dispatched to presenter

    // able to show movie list
    // able to scroll on list

    // searchViewClicked - dispatch event searchViewClicked
    // searchViewClicked - correct data send to presenter
    // searchViewClicked - snackbar displayed

    // showMovies - success - correct number of items created
    // showMovies - empty list show no data info
    // showMovies - snackbar not displayed

    // showFirstMovie - correct movie displayed
    // loadingStarted - loading indicator displayed
    // loadingFinished - loading indicator not display
    // loadingFailed - correct error message display
    // onMovieClicked - transit to next screen
    // onMovieClicked - correct data send to next screen
    // showRetryIndicator - retry snackbar displayed

    // searchViewBackButtonClicked - event searchViewBackButtonClicked sent to presenter
    // endregion

    // region helper methods -----------------------------------------------------------------------

    // endregion helper methods --------------------------------------------------------------------
    // region helper class -------------------------------------------------------------------------

    // endregion helper class ----------------------------------------------------------------------
}