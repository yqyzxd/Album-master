package com.wind.simple;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wind.album.Album;
import com.wind.album.busevent.PhotoCropResultEvent;
import com.wind.rxbus.RxBusResultDisposable;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

/**
 * Created by wind on 16/5/9.
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        TextView tv = (TextView) findViewById(R.id.tv_album);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndPermission.with(MainActivity.this)
                        .runtime()
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                Album
                                        .with(getContext())
                                        .maxCount(6)
                                        .radio()
                                        .crop()
                                        .aspectRatio(1,1)
                                        .subscribe(new RxBusResultDisposable<PhotoCropResultEvent>() {
                                            @Override
                                            protected void onEvent(PhotoCropResultEvent event) throws Exception {
                                                   String path=event.getResult().getCropPath();
                                                    Log.e("MainActivity",path);
                                                    Toast.makeText(getContext(),path,Toast.LENGTH_SHORT).show();

                                            }
                                        })
                                        .open();
                                /*Album
                                        .with(getContext())
                                        .maxCount(6)
                                        .radio()
                                        .crop()
                                        .aspectRatio(1,1)
                                        .subscribe(new RxBusResultDisposable<PhotosResultEvent>() {
                                            @Override
                                            protected void onEvent(PhotosResultEvent event) throws Exception {
                                                for (MediaData mediaData:event.getResult()){
                                                    Log.e("MainActivity",mediaData.getPath());
                                                    Toast.makeText(getContext(),mediaData.getPath(),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .open();*/
                                ;

                            }
                        }).start();

            }
        });
    }


    public Context getContext() {
        return this;
    }
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK==resultCode){
            ArrayList<MediaData> datas=data.getParcelableArrayListExtra(AlbumActivity.RESULT_DATA);

            for (MediaData mediaData:datas){
                Log.e("MainActivity",mediaData.getPath());
            }
        }
    }*/
}
