package com.esoxjem.movieguide.listing;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoviesListingFragmentTest {

    // region constants ----------------------------------------------------------------------------

    // endregion constants -------------------------------------------------------------------------
    // region helper fields ------------------------------------------------------------------------

    // endregion helper fields ---------------------------------------------------------------------

    MoviesListingFragment SUT;

    @Before
    public void setUp() throws Exception {
        SUT = new MoviesListingFragment();

    }


    // region helper methods -----------------------------------------------------------------------

    // endregion helper methods --------------------------------------------------------------------
    // region helper class -------------------------------------------------------------------------

    // endregion helper class ----------------------------------------------------------------------
}