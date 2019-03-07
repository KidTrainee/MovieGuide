package com.esoxjem.movieguide.listing.sorting;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.esoxjem.movieguide.AppModule;

/**
 * @author pulkitkumar
 * @author arunsasidharan
 */
public class SortingModule
{

    public SortingModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    private final AppCompatActivity mActivity;

    private Context getContext() {
        return mActivity;
    }

    SortingDialogInteractor providesSortingDialogInteractor()
    {
        return new SortingDialogInteractorImpl(getSortingOptionStore());
    }

    private SortingOptionStore getSortingOptionStore() {
        return new SortingOptionStore(getContext());
    }

    SortingDialogPresenter getSortingDialogPresenter()
    {
        return new SortingDialogPresenterImpl(getSortingDialogInteractor());
    }

    private SortingDialogInteractor getSortingDialogInteractor() {
        return new SortingDialogInteractorImpl(getSortingOptionStore());
    }
}
