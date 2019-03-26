package com.esoxjem.movieguide.sorting;

import android.content.SharedPreferences;

/**
 * @author arun
 */
public class SortingOptionStore {
    private final SharedPreferences mPref;
    private static final String SELECTED_OPTION = "selectedOption";
    public static final String PREF_NAME = "SortingOptionStore";

    public SortingOptionStore(SharedPreferences pref) {
        mPref = pref;
    }

    public void setSelectedOption(SortType sortType) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putInt(SELECTED_OPTION, sortType.getValue());
        editor.apply();
    }

    public int getSelectedOption() {
        return mPref.getInt(SELECTED_OPTION, 0);
    }
}
