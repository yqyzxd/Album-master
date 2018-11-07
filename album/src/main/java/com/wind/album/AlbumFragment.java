package com.wind.album;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wind.album.adapter.ImageAdapter;
import com.wind.album.bean.MediaData;
import com.wind.album.busevent.PhotosResultEvent;
import com.wind.album.presenter.AlbumPresenter;
import com.wind.album.presenter.Presenter;
import com.wind.album.utils.SystemUtil;
import com.wind.album.view.AlbumView;
import com.wind.album.view.fragment.MvpFragment;
import com.wind.rxbus.RxBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by wind on 16/5/7.
 */
public class AlbumFragment extends MvpFragment<AlbumPresenter> implements AlbumView, AdapterView.OnItemClickListener, ImageAdapter.OnCheckChangeListener, View.OnClickListener {

    public static final String ARGS_CONFIGURATION = "args_configuration";
    GridView gv_album;
    TextView tv_album_name;
    TextView tv_confirm;
    View toolbar_iv_back;
    TextView tv_preview;
    RelativeLayout rl_bottom;
    ImageAdapter mAdapter;
    private ArrayList<MediaData> selectedPhotos;
    private int mMaxPhotoCount;
    public static final int MAX_PHOTO_COUNT = 9;
    private Configuration mConfiguration;
    @Override
    protected Presenter createPresenter() {
        return new AlbumPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_album;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnPhotoItemClickListener) {
            listener = (OnPhotoItemClickListener) activity;
        }
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
        mAdapter = new ImageAdapter(getActivity());
        gv_album.setAdapter(mAdapter);
        mAdapter.setOnCheckChangeListener(this);
        gv_album.setOnItemClickListener(this);

        selectedPhotos = new ArrayList<>();
        loadAlbum();
    }

    private void initView(View view) {
        gv_album = (GridView) view.findViewById(R.id.gv_album);


        tv_album_name = (TextView) view.findViewById(R.id.tv_album_name);
        tv_album_name.setOnClickListener(this);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);

        tv_preview = (TextView) view.findViewById(R.id.tv_preview);
        tv_preview.setOnClickListener(this);
        rl_bottom = (RelativeLayout) view.findViewById(R.id.rl_bottom);
        ;


        toolbar_iv_back = view.findViewById(R.id.toolbar_iv_back);
        toolbar_iv_back.setOnClickListener(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void loadAlbum() {
        presenter.loadAlbum(getActivity());
    }

    private Map<File, List<MediaData>> imageMap;
    private File mCurrentDir;

    @Override
    public void loadAlbumSuccess(Map<File, List<MediaData>> imageMap) {
        //  mAdapter.addAll(photos);
        this.imageMap = imageMap;
        mCurrentDir = new File("所有图片");
        mAdapter.addAll(imageMap.get(mCurrentDir));
        tv_album_name.setText("所有图片");
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //预览图片
        List<MediaData> photos = imageMap.get(mCurrentDir);

        listener.onPhotoItemClick(photos, i, selectedPhotos);

    }

    private OnPhotoItemClickListener listener;

    @Override
    public boolean onCheckChange(MediaData photo, boolean isChecked) {

        if (isChecked){
            //判断是否达到最大可选照片数
            boolean canSelect=selectedPhotos.size()<mMaxPhotoCount;
            if (!canSelect){
                Toast.makeText(getActivity(),"最多只能选择"+mMaxPhotoCount+"张图片",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        recordSelectedPhotos(photo, isChecked);
        return true;
    }

    /**
     * 记录选择的照片
     *
     * @param photo
     * @param isChecked
     */
    private void recordSelectedPhotos(MediaData photo, boolean isChecked) {
        if (isChecked) {
            if (!selectedPhotos.contains(photo))
                selectedPhotos.add(photo);
        } else {
            if (selectedPhotos.contains(photo))
                selectedPhotos.remove(photo);
        }

        setSelectedPhotosCount();


    }

    private void setSelectedPhotosCount() {
        if (selectedPhotos.size() == 0) {
            tv_confirm.setText("完成");
            tv_confirm.setTextColor(getResources().getColor(R.color.not_confirm_color));
            tv_preview.setText("预览");
            tv_preview.setTextColor(getResources().getColor(R.color.not_confirm_color));
        } else {
            tv_confirm.setText("完成(" + selectedPhotos.size() + "/" + mMaxPhotoCount + ")");
            tv_confirm.setTextColor(getResources().getColor(R.color.confirm_color));

            tv_preview.setText("预览(" + selectedPhotos.size() + ")");
            tv_preview.setTextColor(getResources().getColor(R.color.confirm_color));
        }
    }

    public void refresh() {
        mAdapter.notifyDataSetChanged();
        setSelectedPhotosCount();
    }

    public interface OnPhotoItemClickListener {
        void onPhotoItemClick(List<MediaData> photos, int position, ArrayList<MediaData> selectedPhotos);
    }


    public void onClick(View v) {
        if (v.getId() == R.id.tv_album_name) {
            showDirPopupWindow();
        } else if (v.getId() == R.id.tv_preview) {
            if (!selectedPhotos.isEmpty()) {
                //选中的图片预览
                listener.onPhotoItemClick(selectedPhotos, 0, selectedPhotos);
            }
        } else if (v.getId() == R.id.tv_confirm) {
            confirmSelcetPhotos();
        } else if (v.getId() == R.id.toolbar_iv_back) {
            getActivity().finish();
        }

    }


    public void confirmSelcetPhotos() {
        if (selectedPhotos.isEmpty()) {
            Toast.makeText(getActivity(), "请选择照片", Toast.LENGTH_SHORT).show();
        } else {

          /*  Intent data = new Intent();
            data.putParcelableArrayListExtra(AlbumActivity.RESULT_DATA, selectedPhotos);
            getActivity().setResult(getActivity().RESULT_OK, data);
            getActivity().finish();*/
           if (mMaxPhotoCount==1&& mConfiguration.isCrop()){
               //进入裁剪页面
                PhotoCropActivity.start(getActivity(),selectedPhotos.get(0),mConfiguration);
           }else {
               PhotosResultEvent event=new PhotosResultEvent(selectedPhotos);
               RxBus.getDefault().post(event);
               getActivity().finish();
           }

        }
    }

    PopupWindow popupWindow;

    private void showDirPopupWindow() {

        int screenHeight = SystemUtil.getScreenWidth(getActivity());

        ListView contentView = (ListView) LayoutInflater.from(getActivity()).inflate(R.layout.popup_folder, null, false);
        final AlbumAdapter albumAdapter = new AlbumAdapter();
        contentView.setAdapter(albumAdapter);
        contentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                List<MediaData> photos = albumAdapter.getItem(i);
                mAdapter.addAll(photos);
                mCurrentDir = albumAdapter.getFolder(i);
                tv_album_name.setText(mCurrentDir.getName());
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, screenHeight);
        ColorDrawable colorDrawable = new ColorDrawable(Color.WHITE);
        popupWindow.setBackgroundDrawable(colorDrawable);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
       /* popupWindow
                .setAnimationStyle(R.style.anim_popup_dir);*/
        int[] location = new int[2];
        rl_bottom.getLocationOnScreen(location);

        popupWindow.showAtLocation(rl_bottom, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1);
            }
        });

        darkenBackground(0.5f);
    }

    private void darkenBackground(float alpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = alpha;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);

    }

    private class AlbumAdapter extends BaseAdapter {
        private List<File> mDirNames;

        AlbumAdapter() {
            mDirNames = new ArrayList<>();
            mDirNames.addAll(imageMap.keySet());
        }

        @Override
        public int getCount() {
            return imageMap.size();
        }

        @Override
        public List<MediaData> getItem(int i) {
            return imageMap.get(mDirNames.get(i));
        }

        private File getFolder(int i) {
            return mDirNames.get(i);
        }


        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_item_folder, viewGroup, false);
            }

            ImageView ivFolder = (ImageView) view.findViewById(R.id.iv);
            ImageView iv_folder_select = (ImageView) view.findViewById(R.id.iv_folder_select);
            TextView tv_folder_name = (TextView) view.findViewById(R.id.tv_folder_name);
            TextView tv_photo_count = (TextView) view.findViewById(R.id.tv_photo_count);


            File dir = mDirNames.get(i);
            if (dir.getPath().equals(mCurrentDir.getPath())) {
                iv_folder_select.setVisibility(View.VISIBLE);
            } else {
                iv_folder_select.setVisibility(View.GONE);
            }
            List<MediaData> photos = imageMap.get(dir);
            tv_photo_count.setText(photos.size() + "张");

            ImageLoader.inflate(ivFolder, imageMap.get(dir).get(0).getPath());

            tv_folder_name.setText(dir.getName());
            return view;
        }
    }
}
