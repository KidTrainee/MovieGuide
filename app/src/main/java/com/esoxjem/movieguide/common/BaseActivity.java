package com.esoxjem.movieguide.common;

import com.esoxjem.movieguide.BaseApplication;
import com.esoxjem.movieguide.details.DetailsModule;
import com.esoxjem.movieguide.listing.ListingModule;

public abstract class BaseActivity extends LogcatActivity {

    private ListingModule mListingModule;
    private DetailsModule mDetailsModule;

    public ListingModule getListingModule() {
        if (mListingModule == null) {
            mListingModule = new ListingModule(
                    ((BaseApplication) getApplication()).getAppModule(),
                    this
            );
        }
        return mListingModule;
    }

    public DetailsModule getDetailsModule() {
        if (mDetailsModule == null) {
            mDetailsModule = new DetailsModule(
                    ((BaseApplication) getApplication()).getAppModule());
        }
        return mDetailsModule;
    }
}
