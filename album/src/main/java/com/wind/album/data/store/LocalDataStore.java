package com.wind.album.data.store;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.wind.album.bean.MediaData;
import com.wind.album.bean.Photo;
import com.wind.album.utils.ContentResolverHelper;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * Created by wind on 16/5/7.
 */
public class LocalDataStore {


    public Observable<Map<File, List<MediaData>>> photos(final Context context){

        return Observable.create(new ObservableOnSubscribe<Map<File, List<MediaData>>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<File, List<MediaData>>> emitter) throws Exception {
                try {
                   /* String[] projection = { MediaStore.Images.Media._ID,
                            MediaStore.Images.Media.DISPLAY_NAME,
                            MediaStore.Images.Media.DATA };
                    String orderBy = MediaStore.Images.Media.DISPLAY_NAME;
                    Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    List<Photo>  photos= getContentProvider(context,uri,projection, orderBy);*/

                    Map<File, List<MediaData>>  imageMap= ContentResolverHelper.getImageGroupByDir(context);
                    emitter.onNext(imageMap);
                    emitter.onComplete();
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }


        });
    }

    public List<Photo> getContentProvider(Context context,Uri uri,String[] projection, String orderBy) {
        List<Photo> listImage = new ArrayList<Photo>();
        Cursor cursor =context.getContentResolver().query(uri, projection, null,
                null, orderBy);
        if (null == cursor) {
            return null;
        }
        while (cursor.moveToNext()) {
            // 获取图片的路径
            String path = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));




            Photo photo=new Photo(path,false);
            listImage.add(photo);
        }

        return listImage;

    }

}
