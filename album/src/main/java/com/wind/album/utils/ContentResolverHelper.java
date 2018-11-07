package com.wind.album.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.wind.album.bean.MediaData;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wind on 16/5/7.
 */
public class ContentResolverHelper {
    public static Map<File, List<MediaData>> getImageGroupByDir(Context context) {
        LinkedHashMap result = new LinkedHashMap();
        List<MediaData> imageFiles = getImageFiles(context);
        //所有图片文件
        result.put(new File("所有图片"),imageFiles);
        if(imageFiles != null && imageFiles.size() > 0) {
            Iterator iterator = imageFiles.iterator();

            while(iterator.hasNext()) {
                MediaData imageFile = (MediaData)iterator.next();
                if(imageFile != null && !TextUtils.isEmpty(imageFile.getPath())) {
                    File dir = (new File(imageFile.getPath())).getParentFile();
                    if(dir != null) {
                        if(result.containsKey(dir)) {
                            ((List)result.get(dir)).add(imageFile);
                        } else {
                            ArrayList dirImages = new ArrayList();
                            result.put(dir, dirImages);
                            dirImages.add(imageFile);
                        }
                    }
                }
            }
        }

        return result;
    }
    public static List<MediaData> getImageFiles(Context context) {
        ArrayList audioList = new ArrayList();

        try {
            ContentResolver e = context.getContentResolver();
            Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = new String[]{"_data", "_display_name", "_size", "date_added", "_id"};
            Cursor cursor = e.query(uri, projection, (String)null, (String[])null, "date_modified DESC");
            if(cursor != null) {
                analyzeCursorToBaseFile(cursor, audioList);
                cursor.close();
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return audioList;
    }

    private static void analyzeCursorToBaseFile(Cursor cursor, List<MediaData> list) {
        if(list == null) {
            throw new NullPointerException();
        } else {
            while(cursor.moveToNext()) {
                String path = cursor.getString(0);
                String name = cursor.getString(1);
                long size = cursor.getLong(2);
                long addedTime = cursor.getLong(3) * 1000L;
                //Log.e("DATE ADDED", String.valueOf(addedTime));
                int fileId = cursor.getInt(4);
                list.add(new MediaData(fileId, path, name, addedTime, size));
            }

        }
    }
}
