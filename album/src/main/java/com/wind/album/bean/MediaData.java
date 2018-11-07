package com.wind.album.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaData implements Parcelable {

    private int fileId;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 文件名
     */
    private String displayName;
    /**
     * 加入时间
     */
    private long addedTime;
    /**
     * 文件大小
     */
    private long size;

    private boolean isSelected;
    public MediaData(){}
    protected MediaData(Parcel in) {
        fileId = in.readInt();
        path = in.readString();
        displayName = in.readString();
        addedTime = in.readLong();
        size = in.readLong();
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(fileId);
        dest.writeString(path);
        dest.writeString(displayName);
        dest.writeLong(addedTime);
        dest.writeLong(size);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MediaData> CREATOR = new Creator<MediaData>() {
        @Override
        public MediaData createFromParcel(Parcel in) {
            return new MediaData(in);
        }

        @Override
        public MediaData[] newArray(int size) {
            return new MediaData[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public MediaData(int fileId, String path, String displayName, long addedTime, long size) {
        this.path = path;
        this.fileId = fileId;
        this.displayName = displayName;
        this.addedTime = addedTime;
        this.size = size;
        isSelected=false;
    }

    public int getFileId() {
        return this.fileId;
    }

    public String getPath() {
        return this.path;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public long getAddedTime() {
        return this.addedTime;
    }

    public long getSize() {
        return this.size;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj==this){
            return true;
        }
        if (!(obj instanceof MediaData)){
            return false;
        }
        MediaData that= (MediaData) obj;
        if (!that.getPath().equals(getPath())){
            return false;
        }
        if (that.getSize()!=getSize()){
            return false;
        }
        return true;
    }
}