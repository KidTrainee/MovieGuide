package com.esoxjem.movieguide.listing;

import android.support.v7.app.AppCompatActivity;

import com.esoxjem.movieguide.BaseApplication;
import com.esoxjem.movieguide.common.BaseObservableFragment;

public abstract class BaseMoviesListingFragment<ListenerType>
        extends BaseObservableFragment<ListenerType>{

    private ListingModule mListingModule;

    public ListingModule getModule() {
        if (mListingModule == null) {
            mListingModule = new ListingModule(
                    ((BaseApplication) getActivity().getApplication()).getAppModule(),
                    (AppCompatActivity) getActivity()
            );
        }
        return mListingModule;
    }
}
