package com.wind.album.busevent;

import com.wind.album.bean.PhotoCropBean;
import com.wind.rxbus.BaseResultEvent;


public class PhotoCropResultEvent extends BaseResultEvent {
    private final PhotoCropBean resultBean;

    public PhotoCropResultEvent(PhotoCropBean bean) {
        this.resultBean = bean;
    }

    public PhotoCropBean getResult() {
        return resultBean;
    }

}
