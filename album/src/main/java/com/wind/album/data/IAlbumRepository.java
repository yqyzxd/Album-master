package com.wind.album.data;

import android.content.Context;

import com.wind.album.bean.MediaData;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * Created by wind on 16/5/7.
 */
public interface IAlbumRepository {

    Observable<Map<File, List<MediaData>>> photos(Context context);
}
