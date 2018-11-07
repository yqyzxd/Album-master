package com.wind.album;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edmodo.cropper.CropImageView;
import com.wind.album.bean.MediaData;
import com.wind.album.bean.PhotoCropBean;
import com.wind.album.busevent.CloseActivityEvent;
import com.wind.album.busevent.PhotoCropResultEvent;
import com.wind.rxbus.RxBus;

import java.io.File;

/**
 * Created by wind on 2018/11/6.
 */

public class PhotoCropFragment extends Fragment {
    public static final String ARGS_MEDIA_DATA="args_media_data";
    public static final String ARGS_CONFIGURATION="args_configuration";
    CropImageView crop_iv;

    private String mSourcePath;
    private Configuration mConfiguration;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentContentView=inflater.inflate(R.layout.fragment_photo_crop,container,false);
        return fragmentContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        crop_iv=view.findViewById(R.id.crop_iv);

        MediaData mediaData=getArguments().getParcelable(ARGS_MEDIA_DATA);
        mSourcePath=mediaData.getPath();
        mConfiguration=getArguments().getParcelable(ARGS_CONFIGURATION);
        int aspectRatioX=mConfiguration.getAspectRatioX();
        int aspectRatioY=mConfiguration.getAspectRatioY();
        crop_iv.setAspectRatio(aspectRatioX,aspectRatioY);
        crop_iv.setFixedAspectRatio(true);

        BitmapFactory.Options options=new BitmapFactory.Options();

        options.inSampleSize=ImageUtil.getSampleSize(mSourcePath,getScreenWidth(),getScreenHeight());
        Bitmap bitmap=BitmapFactory.decodeFile(mSourcePath);
        crop_iv.setImageBitmap(bitmap);
    }

    public int getScreenWidth(){
        DisplayMetrics out=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(out);
        return out.widthPixels;
    }
    public int getScreenHeight(){
        DisplayMetrics out=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(out);
        return out.heightPixels;
    }


    public static PhotoCropFragment newInstance(MediaData mediaData, Configuration configuration){
        PhotoCropFragment fragment=new PhotoCropFragment();
        Bundle args=new Bundle();
        args.putParcelable(ARGS_MEDIA_DATA,mediaData);
        args.putParcelable(ARGS_CONFIGURATION,configuration);
        fragment.setArguments(args);
        return fragment;
    }

    public void crop() {
        Bitmap croppedBitmap=crop_iv.getCroppedImage();
        //保存到本地
        File savedFile=new File(getSavePath());
        FileUtil.saveBitmapToFile(croppedBitmap,savedFile);
        PhotoCropBean cropBean=new PhotoCropBean();
        cropBean.setCropPath(savedFile.getPath());
        PhotoCropResultEvent event=new PhotoCropResultEvent(cropBean);
        RxBus.getDefault().post(event);
        RxBus.getDefault().post(new CloseActivityEvent());
        getActivity().finish();
    }

    private String getSavePath(){
        return getActivity().getExternalCacheDir().getAbsolutePath()+"/album/"+System.currentTimeMillis()+".jpg";
    }
}
