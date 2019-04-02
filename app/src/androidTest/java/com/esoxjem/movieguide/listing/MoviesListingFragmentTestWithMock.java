package com.esoxjem.movieguide.listing;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockWebServer;

@RunWith(AndroidJUnit4.class)
public class MoviesListingFragmentTestWithMock {
    @Rule
    public ActivityTestRule<MoviesListingActivity> activityRule = new ActivityTestRule<>(MoviesListingActivity.class);

    private MockWebServer mWebServer;
    @Before
    public void setUp() throws Exception {
        mWebServer = new MockWebServer();
        mWebServer.start(8080);
    }

    @After
    public void tearDown() throws Exception {
        mWebServer.shutdown();
    }
}
