package com.wind.album;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wind on 2018/11/5.
 */

public class Configuration implements Parcelable{

    private Context context;
    private boolean radioSelect;
    private boolean crop;
    private int aspectRatioX;
    private int aspectRatioY;
    private int maxCount;
    public Configuration(){}
    protected Configuration(Parcel in) {
        radioSelect=in.readByte()==1;
        crop=in.readByte()==1;
        aspectRatioX=in.readInt();
        aspectRatioY=in.readInt();
        maxCount=in.readInt();
    }

    public static final Creator<Configuration> CREATOR = new Creator<Configuration>() {
        @Override
        public Configuration createFromParcel(Parcel in) {
            return new Configuration(in);
        }

        @Override
        public Configuration[] newArray(int size) {
            return new Configuration[size];
        }
    };

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte)(radioSelect?1:0));
        dest.writeByte((byte)(crop?1:0));
        dest.writeInt(aspectRatioX);
        dest.writeInt(aspectRatioY);
        dest.writeInt(maxCount);
    }


    public boolean isRadioSelect() {
        return radioSelect;
    }

    public void setRadioSelect(boolean radioSelect) {
        this.radioSelect = radioSelect;
    }

    public boolean isCrop() {
        return crop;
    }

    public void setCrop(boolean crop) {
        this.crop = crop;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }


    public int getAspectRatioX() {
        return aspectRatioX;
    }

    public void setAspectRatioX(int aspectRatioX) {
        this.aspectRatioX = aspectRatioX;
    }

    public int getAspectRatioY() {
        return aspectRatioY;
    }

    public void setAspectRatioY(int aspectRatioY) {
        this.aspectRatioY = aspectRatioY;
    }
}
