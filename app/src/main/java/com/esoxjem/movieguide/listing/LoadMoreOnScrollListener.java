package com.esoxjem.movieguide.listing;

import android.support.v7.widget.RecyclerView;

public abstract class LoadMoreOnScrollListener extends RecyclerView.OnScrollListener {
    private boolean isLoading = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (!recyclerView.canScrollVertically(1) && !isLoading) {
            isLoading = true;
            loadMore();
        }
    }

    public abstract void loadMore();

    public void setIsLoading(boolean loading) {
        isLoading = loading;
    }
}
