package com.esoxjem.movieguide.listing;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.esoxjem.movieguide.BaseApplication;
import com.esoxjem.movieguide.R;
import com.esoxjem.movieguide.common.mocks.AppModuleMock;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.esoxjem.movieguide.common.assertions.EspRecyclerView.matchCount;
import static com.esoxjem.movieguide.listing.MoviesListingInteractorImpl.NUMBER_OF_ITEM_PER_QUERY;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MoviesListingFragmentTest {

    // region constants ----------------------------------------------------------------------------
    @Rule
    public ActivityTestRule<MoviesListingActivity> activityTestRule = new ActivityTestRule<>(MoviesListingActivity.class);

    private IdlingResource idlingResource;
    // endregion constants -------------------------------------------------------------------------
    // region helper fields ------------------------------------------------------------------------

    // endregion helper fields ---------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        // register IdlingResource
        idlingResource = activityTestRule.getActivity().getCountingIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void ableTo_showSortIcon() throws Exception {
        // Arrange
        // Act
        // Assert
        onView(withId(R.id.action_sort)).check(matches(isDisplayed()));
    }

    @Test
    public void sortIconCLicked_sortDialogDisplayed() throws Exception {
        // Arrange
        // Act
        onView(withId(R.id.action_sort)).perform(click());
        // Assert
        onView(withId(R.id.sorting_group)).check(matches(isDisplayed()));
    }

    @Test
    public void ableTo_showSearchIcon() throws Exception {
        // Arrange
        // Act
        // Assert
        onView(withId(R.id.action_search)).check(matches(isDisplayed()));
    }

    @Test
    public void searchIconClicked_searchViewExpanse() throws Exception {
        // Arrange
        // Act
        onView(withId(R.id.action_search)).perform(click());
        // Assert
        onView(withId(android.support.v7.appcompat.R.id.search_src_text))
                .check(matches(isDisplayed()));

        // how to check for software keyboard visibility
    }

    @Test
    public void ableTo_showMovieList() throws Exception {
        // Arrange
        // Act
        // Assert
        onView(withId(R.id.movies_listing)).check(matches(isDisplayed()));
    }

    @Test
    public void ableTo_scrollView() throws Exception {
        // Arrange
        // Act
        onView(withId(R.id.movies_listing)).perform(RecyclerViewActions.actionOnItemAtPosition(10, click()));
        // Assert
        onView(withText("Summary")).check(matches(isDisplayed()));
    }

    @Test
    public void searchViewClicked_snackbarDisplayed() throws Exception {
        // Arrange
        // Act
        onView(withId(R.id.action_search)).perform(click());
        onView(withId(android.support.v7.appcompat.R.id.search_src_text))
                .perform(ViewActions.replaceText("Captain Marvel"));
        onView(withId(android.support.v7.appcompat.R.id.search_src_text))
                .perform(pressImeActionButton());
        // Assert
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Loading Movies")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void showMovies_success_correctNumberOfItemsCreated() throws Exception {
        // Arrange
        // Act
        // Assert
        onView(withId(R.id.movies_listing)).check(matchCount(NUMBER_OF_ITEM_PER_QUERY));
    }

    @Test
    public void showMovies_emptyList_showSnackbar_noDataMessage() throws Exception {
        // Arrange
        emptyDataWebService();
        // Act
        // Assert
        onView(withId(android.support.design.R.id.snackbar_text)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
    // region test cases
    //* able to show sort icon
    //* sortIconClicked - SortDialog displayed
    //* able to show search icon
    //* searchIconClicked - searchView expanded, keyboard displayed
    //- searchIconClicked - onActionSortSelected dispatched to presenter
    //* able to show movie list
    //* able to scroll on list
    //* searchViewClicked - dispatch event searchViewClicked
    //- searchViewClicked - correct data send to presenter
    //* searchViewClicked - snackbar displayed
    //* showMovies - success - correct number of items created
    //* showMovies - empty list show no data info
    // showMovies - snackbar not displayed

    //* showFirstMovie - correct movie displayed
    // loadingStarted - loading indicator displayed
    // loadingFinished - loading indicator not display
    // loadingFailed - correct error message display
    // onMovieClicked - transit to next screen
    // onMovieClicked - correct data send to next screen
    // showRetryIndicator - retry snackbar displayed

    // searchViewBackButtonClicked - event searchViewBackButtonClicked sent to presenter
    // endregion

    // region helper methods -----------------------------------------------------------------------
    private void emptyDataWebService() {
        BaseApplication app = (BaseApplication) activityTestRule.getActivity().getApplication();
        AppModuleMock appModuleMock = new AppModuleMock(app);
        app.setAppModule(appModuleMock);
//        activityTestRule.launchActivity(new Intent());
    }

    // endregion helper methods --------------------------------------------------------------------
    // region helper class -------------------------------------------------------------------------

    // endregion helper class ----------------------------------------------------------------------
}