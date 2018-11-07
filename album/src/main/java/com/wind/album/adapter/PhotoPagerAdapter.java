package com.wind.album.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wind.album.R;
import com.wind.album.bean.MediaData;

import java.util.List;

public class PhotoPagerAdapter extends PagerAdapter {
        private List<MediaData> photos;
        private Context context;
        public PhotoPagerAdapter (Context context){
            this.context=context;
        }
        @Override
        public int getCount() {
            return this.photos==null?0:this.photos.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public View instantiateItem(final ViewGroup container, final int position) {
            ImageView iv=new ImageView(context);

            Glide
                    .with(context)
                    .load(this.photos.get(position).getPath())
                    .fitCenter()
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(iv);

            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View)object;
            ((ViewPager) container).removeView(view);
            view = null;
        }

        public void addAll(List<MediaData> photos) {
            this.photos=photos;
            notifyDataSetChanged();
        }
    }