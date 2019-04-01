package com.esoxjem.movieguide.listing.sorting;

import java.util.Set;

interface BaseObservableView<ListenterType> {
    Set<ListenterType> getListeners();

    void registerListener(ListenterType listener);

    void unregisterListener(ListenterType listener);
}
