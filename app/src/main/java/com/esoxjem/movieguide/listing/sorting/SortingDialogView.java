package com.esoxjem.movieguide.listing.sorting;

import java.util.Set;

/**
 * @author arun
 */
public interface SortingDialogView extends BaseObservableView<SortingDialogView.Listener>
{
    interface Listener {
        void onActionSortSelected();
    }

    void setPopularChecked();

    void setNewestChecked();

    void setHighestRatedChecked();

    void setFavoritesChecked();

    void dismissDialog();

}
