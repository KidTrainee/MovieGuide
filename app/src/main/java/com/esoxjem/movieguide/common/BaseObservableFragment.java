package com.esoxjem.movieguide.common;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseObservableFragment<Listener> extends LogcatFragment
        implements ObservableView<Listener> {
    private Set<Listener> mListeners = new HashSet<>();

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
