package com.esoxjem.movieguide.listing.sorting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.esoxjem.movieguide.R;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author arun
 */
public class SortingDialogFragment extends BaseSortingDialogFragment
        implements SortingDialogView, RadioGroup.OnCheckedChangeListener
{

    Set<Listener> mListeners = new HashSet<>();

    SortingDialogPresenter sortingDialogPresenter;

    @BindView(R.id.most_popular)
    RadioButton mostPopular;
    @BindView(R.id.highest_rated)
    RadioButton highestRated;
    @BindView(R.id.favorites)
    RadioButton favorites;
    @BindView(R.id.newest)
    RadioButton newest;
    @BindView(R.id.sorting_group)
    RadioGroup sortingOptionsGroup;


    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        sortingDialogPresenter = getModule().getSortingDialogPresenter();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        sortingDialogPresenter.setView(this);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.sorting_options, null);
        unbinder = ButterKnife.bind(this, dialogView);
        initViews();

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(dialogView);
        dialog.setTitle(R.string.sort_by);
        dialog.show();
        return dialog;
    }

    private void initViews()
    {
        sortingDialogPresenter.setLastSavedOption();
        sortingOptionsGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void setPopularChecked()
    {
        mostPopular.setChecked(true);
    }


    @Override
    public void setNewestChecked()
    {
        newest.setChecked(true);
    }

    @Override
    public void setHighestRatedChecked()
    {
        highestRated.setChecked(true);
    }

    @Override
    public void setFavoritesChecked()
    {
        favorites.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
    {
        switch (checkedId)
        {
            case R.id.most_popular:
                sortingDialogPresenter.onPopularMoviesSelected();
                break;
            case R.id.highest_rated:
                sortingDialogPresenter.onHighestRatedMoviesSelected();
                break;
            case R.id.favorites:
                sortingDialogPresenter.onFavoritesSelected();
            case R.id.newest:
                sortingDialogPresenter.onNewestMoviesSelected();
                break;
        }
        for (Listener listener : getListeners()) {
            listener.onActionSortSelected();
        }
    }

    @Override
    public void dismissDialog()
    {
        dismiss();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        for (Listener listener : getListeners()) {
            unregisterListener(listener);
        }
        sortingDialogPresenter.destroy();
        unbinder.unbind();
    }

    @Override
    public void registerListener(Listener listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }

    public Set<Listener> getListeners() {
        return mListeners;
    }
}
