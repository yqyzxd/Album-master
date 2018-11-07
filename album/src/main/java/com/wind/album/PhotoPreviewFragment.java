package com.wind.album;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wind.album.bean.MediaData;
import com.wind.album.widget.TouchImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wind on 16/5/8.
 */
public class PhotoPreviewFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    public static final String ARGS_CONFIGURATION = "args_configuration";
    ViewPager viewpager;
    private List<MediaData> photos;

    TextView tv_select;

    TextView tv_confirm;

    TextView tv_fraction;

    View toolbar_iv_back;
    ImageView iv_select;
    private int mCurrentPosition;
    private PhotoPagerAdapter photoPagerAdapter;
    private Configuration mConfiguration;
    private int mMaxPhotoCount;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_preview,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        mConfiguration=getArguments().getParcelable(ARGS_CONFIGURATION);
        mMaxPhotoCount = mConfiguration.getMaxCount();
        if (mConfiguration.isRadioSelect()){
            mMaxPhotoCount=1;
        }
        photoPagerAdapter=new PhotoPagerAdapter();
        viewpager.setAdapter(photoPagerAdapter);
        viewpager.setOnPageChangeListener(this);


    }

    private void initView(View view) {
         viewpager= (ViewPager) view.findViewById(R.id.viewpager);
         tv_select= (TextView) view.findViewById(R.id.tv_select);
         tv_select.setOnClickListener(this);
         tv_confirm= (TextView) view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);
         tv_fraction=(TextView) view.findViewById(R.id.tv_fraction);
         iv_select=(ImageView) view.findViewById(R.id.iv_select);
        iv_select.setOnClickListener(this);

        toolbar_iv_back=view.findViewById(R.id.toolbar_iv_back);
        toolbar_iv_back.setOnClickListener(this);

    }


    private ArrayList<MediaData> selectedPhotos;
    public void setPhotos(List<MediaData> photos, int position,ArrayList<MediaData> selectedPhotos) {
        this.photos=photos;
        this.selectedPhotos=selectedPhotos;
        mCurrentPosition=position;
        photoPagerAdapter.addAll(photos,mCurrentPosition);
        iv_select.setActivated(photos.get(mCurrentPosition).isSelected());
        tv_fraction.setText((position+1)+"/"+photos.size());
        setSelectedPhotosCount();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition=position;
        tv_fraction.setText((position+1)+"/"+photos.size());
        if (photos.get(mCurrentPosition).isSelected()){
            iv_select.setActivated(true);
        }else {
            iv_select.setActivated(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    /**
     * 记录选择的照片
     * @param photo
     * @param isChecked
     */
    private void recordSelectedPhotos(MediaData photo, boolean isChecked) {
        photo.setSelected(isChecked);
        if (isChecked){
            if (!selectedPhotos.contains(photo))
                selectedPhotos.add(photo);
        }else {
            if (selectedPhotos.contains(photo))
                selectedPhotos.remove(photo);
        }
        setSelectedPhotosCount();
    }
    private void setSelectedPhotosCount() {
        if (selectedPhotos.size()==0){
            tv_confirm.setText("完成");
            tv_confirm.setTextColor(getResources().getColor(R.color.not_confirm_color));

        }else {
            tv_confirm.setText("完成("+selectedPhotos.size()+"/"+mMaxPhotoCount+")");
            tv_confirm.setTextColor(getResources().getColor(R.color.confirm_color));

        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.tv_confirm){
            if (selectedPhotos.isEmpty()){
                Toast.makeText(getActivity(),"请选择照片",Toast.LENGTH_SHORT).show();
            }else {
                AlbumActivity albumActivity= (AlbumActivity) getActivity();
                albumActivity.confirmSelectPhotos();
            }

        }else if(view.getId()==R.id.tv_select){

        }else if (view.getId()==R.id.iv_select){
            MediaData photo=photos.get(mCurrentPosition);
            if (selectedPhotos.size()>=mMaxPhotoCount){
                if (iv_select.isActivated()){
                    //取消选中状态
                    photo.setSelected(false);
                    iv_select.setActivated(false);
                    recordSelectedPhotos(photo,iv_select.isActivated());

                }else {
                    Toast.makeText(getActivity(),"最多只能选择"+mMaxPhotoCount+"张图片",Toast.LENGTH_SHORT).show();
                }

            }else {

                iv_select.setActivated(!iv_select.isActivated());
                recordSelectedPhotos(photo,iv_select.isActivated());


            }
        }else if (view.getId()==R.id.toolbar_iv_back){
            AlbumActivity albumActivity= (AlbumActivity) getActivity();
            albumActivity.changeFragment(true);
        }


    }

    private class PhotoPagerAdapter extends PagerAdapter{
        private List< MediaData > photos;
        PhotoPagerAdapter (){
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
          //  ImageView iv=new ImageView(getActivity());
           /* GestureImageView iv=new GestureImageView(getActivity());
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);*/
            final  TouchImageView iv = new TouchImageView(getActivity());
            iv.setMaxZoom(5f);
            iv.setMinZoom(1f);

            /*Glide
                    .with(getActivity())
                    .load(this.photos.get(position).getPath())
                    //.fitCenter()
                    //.placeholder(R.mipmap.ic_launcher)
                   // .crossFade()
                    .into(iv);*/

            Glide.with(getActivity())
                    .load(this.photos.get(position).getPath())
                    .asBitmap()
                    .override(600,400)

                    .skipMemoryCache( true )
                    .diskCacheStrategy( DiskCacheStrategy.NONE )
                    .placeholder(R.mipmap.ic_launcher)
                    .into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    iv.setImageBitmap(resource);
                }
            });
            container.addView(iv, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return iv;
        }

        /**
         * 若不返回POSITION_NONE,则即使调用notifyDataSetChanged();
         但是ViewPager还是不会更新原来的数据。
         * @param object
         * @return
         */
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View)object;
            view.clearAnimation();
            ((ViewPager) container).removeView(view);
            view = null;
        }

        public void addAll(List<MediaData> photos,final int curPosition) {
            this.photos=photos;
            notifyDataSetChanged();
            viewpager.setCurrentItem(curPosition);



        }
    }
}
