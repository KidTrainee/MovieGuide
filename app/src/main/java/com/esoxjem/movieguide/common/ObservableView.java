package com.esoxjem.movieguide.common;

public interface ObservableView<Listener> {
    void registerListener(Listener listener);
    void unregisterListener(Listener listener);
}
