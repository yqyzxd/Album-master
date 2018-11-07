package com.wind.album;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wind.album.bean.MediaData;
import com.wind.album.busevent.CloseActivityEvent;
import com.wind.rxbus.RxBus;
import com.wind.rxbus.RxBusResultDisposable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class AlbumActivity extends FragmentActivity implements AlbumFragment.OnPhotoItemClickListener{

    public static final String EXTRA_CONFIGURATION="extra_configuration";
    public static final String RESULT_DATA = "result _data";

    private Configuration mConfiguration;
    private  Disposable mDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        mConfiguration=getIntent().getParcelableExtra(EXTRA_CONFIGURATION);

        initFragment();

        mDisposable=RxBus.getDefault().toObservable(CloseActivityEvent.class).subscribeWith(new RxBusResultDisposable<CloseActivityEvent>() {
            @Override
            protected void onEvent(CloseActivityEvent event) throws Exception {
                getActivity().finish();
            }
        });
        RxBus.getDefault().add(mDisposable);

    }
    AlbumFragment albumFragment;
    PhotoPreviewFragment photoPreviewFragment;
    private void initFragment() {
        FragmentManager fm=getSupportFragmentManager();
        albumFragment=new AlbumFragment();
        Bundle args=new Bundle();
        args.putParcelable(AlbumFragment.ARGS_CONFIGURATION,mConfiguration);
        albumFragment.setArguments(args);
        photoPreviewFragment=new PhotoPreviewFragment();
        photoPreviewFragment.setArguments(args);
        FragmentTransaction transaction=fm.beginTransaction();

        transaction
                .add(R.id.fl_container,albumFragment)
                .add(R.id.fl_container,photoPreviewFragment)
                .commitAllowingStateLoss();
        changeFragment(true);
    }

    public void changeFragment(boolean showAlbum){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        if (showAlbum){
            transaction
                    .show(albumFragment)
                    .hide(photoPreviewFragment)
                    .commitAllowingStateLoss();
        }else {
            transaction
                    .show(photoPreviewFragment)
                    .hide(albumFragment)
                    .commitAllowingStateLoss();
        }
    }

    public void confirmSelectPhotos(){
        albumFragment.confirmSelcetPhotos();
    }
    @Override
    public void onPhotoItemClick(List<MediaData> photos, int position,ArrayList<MediaData> selectedPhotos) {

        //显示预览frgament
        changeFragment(false);

        photoPreviewFragment.setPhotos(photos,position,selectedPhotos);
    }

    @Override
    public void onBackPressed() {
        if (photoPreviewFragment.isVisible()){
            changeFragment(true);
            albumFragment.refresh();
        }else {
            super.onBackPressed();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().remove(mDisposable);
    }

    public Activity getActivity(){
        return this;
    }
}
