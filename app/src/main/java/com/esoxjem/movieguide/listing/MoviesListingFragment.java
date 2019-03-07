package com.esoxjem.movieguide.listing;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.esoxjem.movieguide.Constants;
import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.R;
import com.esoxjem.movieguide.listing.sorting.SortingDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MoviesListingFragment
        extends BaseMoviesListingFragment<MoviesListingView.Listener>
        implements MoviesListingView {

    MoviesListingPresenter moviesPresenter;

    @BindView(R.id.movies_listing)
    RecyclerView moviesListingRC;

    @BindView(R.id.fragmentMoviesContainer)
    FrameLayout fragmentMoviesContainer;

    private RecyclerView.Adapter adapter;

    private Callback callback;
    private Unbinder unbinder;

    public MoviesListingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        moviesPresenter = getModule().getMoviesListingPresenter();
    }

    private LoadMoreOnScrollListener mLoadMoreOnScrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initLayoutReferences();
        mLoadMoreOnScrollListener = new LoadMoreOnScrollListener() {
            @Override
            public void loadMore() {
                for (Listener listener : getListeners()) {
                    listener.onLoadMore();
                }
            }
        };
        moviesListingRC.addOnScrollListener(mLoadMoreOnScrollListener);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesPresenter.setView(this);
        for (Listener listener : getListeners()) {
            listener.onViewCreated(savedInstanceState);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                for (Listener listener : getListeners()) {
                    listener.onActionSortSelected();
                }
                displaySortingOptions();
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySortingOptions() {
        DialogFragment sortingDialogFragment = SortingDialogFragment.newInstance(moviesPresenter);
        sortingDialogFragment.show(getFragmentManager(), "Select Quantity");
    }

    private void initLayoutReferences() {
        moviesListingRC.setHasFixedSize(true);

        int columns;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columns = 2;
        } else {
            columns = getResources().getInteger(R.integer.no_of_columns);
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);

        moviesListingRC.setLayoutManager(layoutManager);
        adapter = new MoviesListingAdapter(moviesPresenter.getCurrentData(), this);
        moviesListingRC.setAdapter(adapter);
    }

    @Override
    public void showMovies() {
        mLoadMoreOnScrollListener.setIsLoading(false);
        moviesListingRC.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showFirstMovie() {
        callback.onMoviesLoaded(moviesPresenter.getFirstMovie());
    }

    @Override
    public void loadingStarted() {
        Snackbar.make(moviesListingRC, R.string.loading_movies, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void loadingFailed(String errorMessage) {
        mLoadMoreOnScrollListener.setIsLoading(false);
        Snackbar.make(moviesListingRC, errorMessage, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void onMovieClicked(Movie movie) {
        callback.onMovieClicked(movie);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (Listener listener : getListeners()) {
            listener.onDestroyView();
        }
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        for (Listener listener : getListeners()) {
            listener.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void searchViewClicked(String searchText){
        for (Listener listener : getListeners()) {
            listener.searchViewClicked(searchText);
        }
    }

    @Override
    public void searchViewBackButtonClicked() {
        for (Listener listener : getListeners()) {
            listener.searchViewBackButtonClicked();
        }
    }

    public interface Callback {
        void onMoviesLoaded(Movie movie);

        void onMovieClicked(Movie movie);
    }


}
