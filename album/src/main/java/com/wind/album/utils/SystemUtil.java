package com.wind.album.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by wind on 16/5/8.
 */
public class SystemUtil {

    public static int getScreenWidth(Activity context){
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w = dm.widthPixels;
        return w;
    }
}
