package com.wind.album;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.wind.album.bean.MediaData;

/**
 * Created by wind on 2018/11/6.
 */

public class PhotoCropActivity extends FragmentActivity {

    public static void start(Context context, MediaData mediaData, Configuration configuration){
        Intent intent=new Intent(context,PhotoCropActivity.class);
        intent.putExtra(EXTRA_MIDEA_DATA,mediaData);
        intent.putExtra(EXTRA_CONFIGURATION,configuration);
        context.startActivity(intent);
    }

    public static final String EXTRA_MIDEA_DATA="extra_media_data";
    public static final String EXTRA_CONFIGURATION="extra_configuration";

    private PhotoCropFragment mPhotoCropFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_crop);
        MediaData mediaData=getIntent().getParcelableExtra(EXTRA_MIDEA_DATA);
        Configuration configuration=getIntent().getParcelableExtra(EXTRA_CONFIGURATION);
        mPhotoCropFragment=PhotoCropFragment.newInstance(mediaData,configuration);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container,mPhotoCropFragment)
                .commit();

        findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoCropFragment.crop();
            }
        });
    }
}
