# Album

照片选择器，高仿微信相册

使用
----


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        TextView tv= (TextView) findViewById(R.id.tv_album);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AlbumActivity.class),10000);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK==resultCode){
            ArrayList<MediaData> datas=data.getParcelableArrayListExtra(AlbumActivity.RESULT_DATA);

            for (MediaData mediaData:datas){
                Log.e("MainActivity",mediaData.getPath());
            }
        }
    }
}

