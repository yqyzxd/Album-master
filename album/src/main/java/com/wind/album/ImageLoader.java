package com.wind.album;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by wind on 2018/11/5.
 */

public class ImageLoader {


    public static void inflate(ImageView iv,String uri){
        Glide
                .with(iv.getContext())
                .load(uri)
                //.override(250,250)
                .centerCrop()
                .placeholder(R.drawable.bg_placeholder)
                //.placeholder(R.drawable.prefecture_default_image)
                .crossFade()
                //.error(R.drawable.prefecture_default_image)
                .into(iv);
    }
}
