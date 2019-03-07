package com.esoxjem.movieguide.listing.sorting;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

abstract class BaseSortingDialogFragment extends DialogFragment {
    private SortingModule mSortingModule;

    public SortingModule getModule() {
        if (mSortingModule == null) {
            mSortingModule = new SortingModule((AppCompatActivity) getActivity());
        }
        return mSortingModule;
    }
}
