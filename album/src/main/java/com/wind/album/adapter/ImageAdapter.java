package com.wind.album.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.wind.album.ImageLoader;
import com.wind.album.R;
import com.wind.album.bean.MediaData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wind on 16/5/7.
 */
public class ImageAdapter extends BaseAdapter {
    private List<MediaData> photos;
    private LayoutInflater mInflater;
    private Context mContext;
    public ImageAdapter(Context context){
        photos=new ArrayList<>();
        mInflater=LayoutInflater.from(context);
        mContext=context;
    }

    @Override
    public int getCount() {

        return photos.size();
    }

    @Override
    public MediaData getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView=mInflater.inflate(R.layout.item_photo,parent,false);
        }
        final MediaData photo=getItem(position);

        ImageView iv_photo= (ImageView) convertView.findViewById(R.id.iv_photo);
        ImageView iv_select= (ImageView) convertView.findViewById(R.id.iv_select);
        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择之前需要先判断能否选中
                if (photo.isSelected()){
                    listener.onCheckChange(photo,!photo.isSelected());
                    photo.setSelected(false);

                }else {
                    boolean changed=listener.onCheckChange(photo,!photo.isSelected());
                    if (changed)
                        photo.setSelected(true);
                }

                notifyDataSetChanged();
            }
        });

        if (photo.isSelected()){
           // iv_select.setActivated(true);
            iv_select.setImageResource(R.drawable.photo_item_checked);
        }else {
            //iv_select.setActivated(false);
            iv_select.setImageResource(R.drawable.photo_item_normal);

        }
        String path=photo.getPath();
        ImageLoader.inflate(iv_photo,path);

        return convertView;
    }

    public void addAll(List<MediaData> photos) {
        this.photos.clear();
        this.photos.addAll(photos);
        notifyDataSetChanged();
    }

    private OnCheckChangeListener listener;
    public void setOnCheckChangeListener(OnCheckChangeListener listener){
        this.listener=listener;
    }
    public interface OnCheckChangeListener{
        boolean onCheckChange(MediaData photo,boolean isChecked);
    }
   /* private SelectEnableCallback callback;
    public void setSelectEnableCallback(SelectEnableCallback callback){
        this.callback=callback;
    }

    public interface SelectEnableCallback{
        boolean selectEnable();
    }*/
}
