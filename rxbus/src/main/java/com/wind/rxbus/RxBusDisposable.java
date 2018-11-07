package com.wind.rxbus;

import android.util.Log;

import io.reactivex.observers.DisposableObserver;

public abstract class RxBusDisposable<T> extends DisposableObserver<T> {

    @Override
    public void onNext(T t) {
        try {
            onEvent(t);
        } catch (Exception e) {
            e.printStackTrace();
            onError(e);
        }
    }


    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        Log.e("RxBusDisposable",e.getMessage());
    }

    protected abstract void onEvent(T t) throws Exception;

}