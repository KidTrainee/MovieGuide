package com.esoxjem.movieguide.sorting;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import com.esoxjem.movieguide.BaseApplication;

abstract class BaseSortingDialogFragment extends DialogFragment {
    private SortingModule mSortingModule;

    public SortingModule getModule() {
        if (mSortingModule == null) {
            mSortingModule = new SortingModule(
                    ((BaseApplication) getActivity().getApplication()).getAppModule(),
                    (AppCompatActivity) getActivity());
        }
        return mSortingModule;
    }
}
