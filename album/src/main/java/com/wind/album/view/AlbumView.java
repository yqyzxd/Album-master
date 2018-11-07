package com.wind.album.view;

import com.wind.album.bean.MediaData;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by wind on 16/5/7.
 */
public interface AlbumView extends MvpView {
    void showLoading();
    void loadAlbum();

    void loadAlbumSuccess(Map<File, List<MediaData>> photos);
}
