package com.wind.album.busevent;

import com.wind.album.bean.MediaData;
import com.wind.rxbus.BaseResultEvent;

import java.util.List;

public class PhotosResultEvent extends BaseResultEvent {
    private final List<MediaData> mediaResultList;

    public PhotosResultEvent(List<MediaData> list) {
        this.mediaResultList = list;
    }

    public List<MediaData> getResult() {
        return mediaResultList;
    }
}
