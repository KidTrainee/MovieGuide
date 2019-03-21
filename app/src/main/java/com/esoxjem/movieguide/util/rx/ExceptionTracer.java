package com.esoxjem.movieguide.util.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.Function;

public final class ExceptionTracer<T> implements ObservableTransformer<T, T> {
    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        final BreadcrumbException breadcrumb = new BreadcrumbException();
        return upstream.onErrorResumeNext((Function<Throwable, ObservableSource<? extends T>>) throwable -> {
            throw new CompositeException(throwable, breadcrumb);
        });
    }
}

class BreadcrumbException extends Exception {
}