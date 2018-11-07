package com.wind.album.presenter;

import com.wind.album.view.MvpView;

/**
 * Created by wind on 16/5/7.
 */
public class MvpBasePresenter<T> implements Presenter {
    MvpView mvpView;
    @Override
    public void attachView(MvpView view) {
        this.mvpView=view;
    }

    @Override
    public void detachView() {
        this.mvpView=null;
    }


    public T getMvpView() {
        return (T)mvpView;
    }
}
