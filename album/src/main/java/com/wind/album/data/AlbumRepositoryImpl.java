package com.wind.album.data;

import android.content.Context;

import com.wind.album.bean.MediaData;
import com.wind.album.data.store.LocalDataStore;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * Created by wind on 16/5/7.
 */
public class AlbumRepositoryImpl implements IAlbumRepository {
    @Override
    public Observable<Map<File, List<MediaData>>> photos(Context context) {

        return new LocalDataStore().photos(context);
    }
}
