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

    public SortingModule(AppModule appModule, AppCompatActivity activity) {
        mAppModule = appModule;
        mActivity = activity;
    }

    private final AppModule mAppModule;
    private final AppCompatActivity mActivity;

    private Context getContext() {
        return mActivity;
    }

    private SortingOptionStore getSortingOptionStore() {
        return mAppModule.getSortingOptionStore();
    }

    SortingDialogPresenter getSortingDialogPresenter()
    {
        return new SortingDialogPresenterImpl(getSortingDialogInteractor());
    }

    private SortingDialogInteractor getSortingDialogInteractor() {
        return new SortingDialogInteractorImpl(getSortingOptionStore());
    }
}
