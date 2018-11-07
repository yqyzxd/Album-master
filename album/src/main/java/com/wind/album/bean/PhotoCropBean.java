package com.wind.album.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class PhotoCropBean extends MediaData implements Parcelable {
    public static final Creator<PhotoCropBean> CREATOR = new Creator<PhotoCropBean>() {
        @Override
        public PhotoCropBean createFromParcel(Parcel in) {
            return new PhotoCropBean(in);
        }

        @Override
        public PhotoCropBean[] newArray(int size) {
            return new PhotoCropBean[size];
        }
    };
    private String cropPath;
    private float aspectRatio;

    public PhotoCropBean(){}
    private PhotoCropBean(Parcel in) {
        super(in);
        cropPath = in.readString();
        aspectRatio = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(cropPath);
        dest.writeFloat(aspectRatio);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCropPath() {
        return cropPath;
    }

    public void setCropPath(String cropPath) {
        this.cropPath = cropPath;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public void copyMediaBean(PhotoCropBean mediaBean) {

    }
}
