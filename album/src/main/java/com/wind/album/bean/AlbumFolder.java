package com.wind.album.bean;

/**
 * Created by wind on 16/5/7.
 */
public class AlbumFolder {

    /**
     * 相册路径
     */
    private String dir;


    /**
     * 相册封面照
     */
    private String cover;

    /**
     * 该相册中图片数量
     */
    private int count;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
