package com.wind.album.presenter;

import com.wind.album.view.MvpView;

/**
 * Created by wind on 16/5/7.
 */
public interface Presenter {

    void attachView(MvpView view);

    void detachView();

}
