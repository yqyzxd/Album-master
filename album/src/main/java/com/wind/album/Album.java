package com.wind.album;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.wind.album.busevent.PhotosResultEvent;
import com.wind.album.busevent.PhotoCropResultEvent;
import com.wind.rxbus.BaseResultEvent;
import com.wind.rxbus.RxBus;
import com.wind.rxbus.RxBusResultDisposable;

import io.reactivex.disposables.Disposable;

/**
 * Created by wind on 2018/11/5.
 */

public class Album {
    private Configuration configuration=new Configuration();
    private RxBusResultDisposable<BaseResultEvent> retDisposable;
    private Album(){}

    public static Album with(Context context){
        Album album=new Album();
        album.configuration.setContext(context.getApplicationContext());
        return album;
    }



    public Album radio(){
        configuration.setRadioSelect(true);
        return this;
    }

    public Album maxCount(int maxCount){
        if (maxCount<=0){
            maxCount=1;
        }
        if (maxCount>=100){
            maxCount=100;
        }
        configuration.setMaxCount(maxCount);
        return this;
    }
    /**
     * 设置回调
     */
    public Album subscribe(@NonNull RxBusResultDisposable<? extends BaseResultEvent> rxBusResultSubscriber) {
        this.retDisposable = (RxBusResultDisposable<BaseResultEvent>) rxBusResultSubscriber;
        return this;
    }


    public Album crop(){
        configuration.setCrop(true);
        return this;
    }

    public Album aspectRatio(int aspectRatioX,int aspectRatioY){
        if (aspectRatioX<=0){
            aspectRatioX=1;
        }
        if (aspectRatioY<=0){
            aspectRatioY=1;
        }
        configuration.setAspectRatioX(aspectRatioX);
        configuration.setAspectRatioY(aspectRatioY);
        return this;
    }

    public void open(){
        if (configuration.getContext()==null) {
            return;
        }
        if (retDisposable == null) {
            return;
        }
        Disposable disposable;
        if (configuration.isRadioSelect() && configuration.isCrop()){
            disposable = RxBus.getDefault()
                    .toObservable(PhotoCropResultEvent.class)
                    .subscribeWith(retDisposable);
        }else {
            disposable = RxBus.getDefault()
                    .toObservable(PhotosResultEvent.class)
                    .subscribeWith(retDisposable);
        }

        RxBus.getDefault().add(disposable);

        Intent intent = new Intent(configuration.getContext(), AlbumActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AlbumActivity.EXTRA_CONFIGURATION, configuration);
        intent.putExtras(bundle);
        configuration.getContext().startActivity(intent);
    }

}
