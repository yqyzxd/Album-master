package com.wind.album;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wind on 2018/11/7.
 */

public class FileUtil {


    public static boolean saveBitmapToFile(Bitmap bitmap, File destFile){
        try {
            if (destFile==null){
                return false;
            }
            if (destFile.isDirectory()){
                return false;
            }
            if (!destFile.exists()){
                destFile.getParentFile().mkdirs();
                destFile.createNewFile();
            }
            OutputStream ops=new FileOutputStream(destFile);
            boolean success=bitmap.compress(Bitmap.CompressFormat.JPEG,100,ops);
            if (success){
                ops.flush();
                ops.close();
            }
            return success;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;

    }
}
