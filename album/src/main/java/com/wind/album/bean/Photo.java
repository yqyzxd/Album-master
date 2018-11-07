package com.wind.album.bean;

/**
 * Created by wind on 16/5/7.
 */
public class Photo {

    /**
     *  图片路径
     */
    private String path;
    /**
     * 是否被选中
     */
    private boolean isSelected;

    public Photo(String path, boolean isSelected) {
        this.path=path;
        this.isSelected=isSelected;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
