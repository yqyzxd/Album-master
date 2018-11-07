package com.wind.album.presenter;

import android.content.Context;

import com.wind.album.bean.MediaData;
import com.wind.album.data.AlbumRepositoryImpl;
import com.wind.album.data.IAlbumRepository;
import com.wind.album.view.AlbumView;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wind on 16/5/7.
 */
public class AlbumPresenter extends MvpBasePresenter<AlbumView> {

    public void loadAlbum(Context context) {

        IAlbumRepository repository = new AlbumRepositoryImpl();

        repository
                .photos(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AlbumSubscriber());


    }

    private final class AlbumSubscriber implements Observer<Map<File, List<MediaData>>> {


        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Map<File, List<MediaData>> photos) {

            getMvpView().loadAlbumSuccess(photos);
        }
    }
}