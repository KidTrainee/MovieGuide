package com.esoxjem.movieguide.moviedetails;

import com.esoxjem.movieguide.BaseApplication;
import com.esoxjem.movieguide.common.BaseObservableFragment;

public abstract class BaseMovieDetailsFragment extends BaseObservableFragment {

    private DetailsModule mDetailsModule;

    public DetailsModule getModule() {
        if (mDetailsModule == null) {
            mDetailsModule = new DetailsModule(
                    ((BaseApplication) getActivity().getApplication()).getAppModule());
        }
        return mDetailsModule;
    }
}
