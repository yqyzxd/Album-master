package com.wind.album.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wind.album.presenter.Presenter;
import com.wind.album.view.MvpView;


/**
 * Created by wind on 16/5/7.
 */
public abstract class MvpFragment<P extends Presenter> extends Fragment implements MvpView{

    protected P presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView=inflater.inflate(getLayoutRes(),container,false);
        presenter=(P)createPresenter();
        presenter.attachView(this);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    protected abstract Presenter createPresenter();
    protected abstract int getLayoutRes();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
