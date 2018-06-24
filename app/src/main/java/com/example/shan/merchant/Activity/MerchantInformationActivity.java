package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shan.merchant.Adapter.FullyGridLayoutManager;
import com.example.shan.merchant.Adapter.GridImageAdapter;
import com.example.shan.merchant.Entity.Shop;
import com.example.shan.merchant.Entity.ShopPicture;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.MyTools.UploadPictureUtil;
import com.example.shan.merchant.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MerchantInformationActivity extends AppCompatActivity {
    private Button btnback; //返回按钮
    private EditText shopName;//店铺名字
    private TextView shopHeader;//店铺头像
    private EditText shopAddress;//店铺地址
    private EditText shopPhone;//电话
    private RecyclerView shopPictures;//添加图片
    private Button btnCommit;
    private myClickListener myListener;
    private EditText shopIntroduce;

    //图片适配器
    private GridImageAdapter adapter;
    //存储图片选择完成后的图片地址信息
    private List<LocalMedia> selectList = new ArrayList<>();
    //已选择的head图片的Uri
    private Uri headPicUri;
    //待上传的图片uri列表
    private List<Uri> uploadPicUriList = new ArrayList<>();

    //用户是否选择了头像
    private boolean headPicAlready = false;
    //只上传头像
    private boolean head = false;
    //只上传店铺图片
    private boolean shoppic = false;
    //同时上传了头像和店铺图片
    private boolean headAndShopPic = false;

    //作品对象
    private Shop oldShop;
    private Shop newShop;
    UploadPictureUtil util = new UploadPictureUtil();
    //只上传头像地址
    private String uploadHeaderUrl = UrlAddress.url + "uploadHeader.action";
    //上传店铺图片的地址
    private String uploadShopPicUrl = UrlAddress.url + "uploadShopPic.action";
    //更新店铺信息地址
    private String UploadShopInformationUrl = UrlAddress.url + "updateShopInformation.action";

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_information);

        //初始化控件
        initControl();
        //初始化数据
        initData();
        //adapter
        setNewShopImagesAdapter();

        btnback.setOnClickListener(myListener);
        shopHeader.setOnClickListener(myListener);
        shopAddress.setOnClickListener(myListener);
        shopPictures.setOnClickListener(myListener);
        btnCommit.setOnClickListener(myListener);
    }

    /**
     * 初始化控件对象
     */
    private void initControl() {
        btnback = findViewById(R.id.merchant_information_back);
        shopName = findViewById(R.id.merchant_information_name);
        shopHeader = findViewById(R.id.merchant_information_picture);
        shopAddress = findViewById(R.id.merchant_information_location);
        shopPhone = findViewById(R.id.merchant_information_phone);
        shopPictures = findViewById(R.id.merchant_information_images);
        btnCommit = findViewById(R.id.merchant_information_commit);
        shopIntroduce = findViewById(R.id.shop_introduce);
        myListener = new myClickListener();
        token = util.getToken(getApplicationContext());
        newShop = new Shop();
    }

    /**
     * 初始化页面数据
     */
    private void initData(){
        Intent intent = getIntent();
        oldShop = (Shop)intent.getSerializableExtra("shop");
        setControl();
    }

    /**
     * 给控件赋值
     */
    private void setControl(){
        newShop.setShopId(oldShop.getShopId());
        shopName.setText(oldShop.getShopName());
        shopAddress.setText(oldShop.getShopAddress());
        Log.i("李垚：：：", oldShop.getShopAddress()+"");
        shopIntroduce.setText(oldShop.getShopIntroduce());
        shopPhone.setText(oldShop.getShopPhone());
    }

    class myClickListener implements ImageView.OnClickListener{
        Intent intent = new Intent();
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                //点击返回按钮
                case R.id.merchant_information_back:
                    finish();
                    break;

                //点击选择店铺头像TextView
                case R.id.merchant_information_picture:
                    Intent intentFromGallery = new Intent();
                    // 设置文件类型
                    intentFromGallery.setType("image/*");
                    intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intentFromGallery, 3);
                    break;

                //点击保存按钮
                case R.id.merchant_information_commit:
                    Handler handler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Bundle bundle = msg.getData();
                            String picPathStr = bundle.getString("string");
                            Gson gson = new Gson();
                            if(head){
                                String path = picPathStr;
                                newShop.setShopPicture(path);
                            }
                            if(shoppic){
                                List<String> picPathList = gson.fromJson(picPathStr, new TypeToken<List<String>>(){}.getType());
                                Set<ShopPicture> shopPictureSet = new HashSet<>();
                                for(String str: picPathList){
                                    ShopPicture shopPic = new ShopPicture();
                                    shopPic.setShoppicture_picture(str);
                                    shopPictureSet.add(shopPic);
                                }
                                newShop.setShopPictureSet(shopPictureSet);
                            }
                            if(headAndShopPic){
                                List<String> picPathList = gson.fromJson(picPathStr, new TypeToken<List<String>>(){}.getType());
                                newShop.setShopPicture(picPathList.get(0));
                                picPathList.remove(0);
                                Set<ShopPicture> shopPictureSet = new HashSet<>();
                                for(String str: picPathList){
                                    ShopPicture shopPic = new ShopPicture();
                                    shopPic.setShoppicture_picture(str);
                                    shopPictureSet.add(shopPic);
                                }
                                newShop.setShopPictureSet(shopPictureSet);
                            }
                            List<Shop> oldAndNewShop = new ArrayList<>();
                            oldAndNewShop.add(oldShop);
                            oldAndNewShop.add(newShop);
                            String shopStr = gson.toJson(oldAndNewShop);
                            util.requestServer(UploadShopInformationUrl,shopStr,token,null);
                            finish();
                        }
                    };

                    //从控件中获得内容
                    createNewShop();
                    if(headPicAlready && selectList.size()<1){
                        //用户只重新选择了头像
                        String photoStr = util.getStringFromUri(MerchantInformationActivity.this,headPicUri);
                        util.requestServer(uploadHeaderUrl,photoStr,token,handler);
                        head = true;
                        shoppic = false;
                        headAndShopPic = false;
                        newShop.setShopPictureSet(oldShop.getShopPictureSet());
                    }else if(!headPicAlready && selectList.size()>0){
                        //只上传店铺图片
                        Uri uri;
                        for (LocalMedia media:selectList){
                            uri = Uri.fromFile(new File(media.getPath()));
                            uploadPicUriList.add(uri);
                        }
                        uploadPicAndGetPathList(uploadShopPicUrl, handler);
                        head = false;
                        shoppic = true;
                        headAndShopPic = false;
                        newShop.setShopPicture(oldShop.getShopPicture());
                    }else if(headPicAlready && selectList.size()>0){
                        //重新选择了头像和店铺图片
                        uploadPicUriList.add(0,headPicUri);
                        Uri uri;
                        for (LocalMedia media:selectList){
                            uri = Uri.fromFile(new File(media.getPath()));
                            uploadPicUriList.add(uri);
                        }
                        uploadPicAndGetPathList(uploadShopPicUrl,handler);
                        head = false;
                        shoppic = false;
                        headAndShopPic = true;
                    }else {
                        //没有重新选择头像和店铺图片
                        newShop.setShopPicture(oldShop.getShopPicture());
                        newShop.setShopPictureSet(oldShop.getShopPictureSet());
                        List<Shop> oldAndNewShop = new ArrayList<>();
                        oldAndNewShop.add(oldShop);
                        oldAndNewShop.add(newShop);
                        Gson gson = new Gson();
                        String shopStr = gson.toJson(oldAndNewShop);
                        util.requestServer(UploadShopInformationUrl,shopStr,token,null);
                        finish();
                    }
                    break;
            }
        }
    }

    /**
     * 给RecycleView设置管理器和适配器
     */
    private void setNewShopImagesAdapter(){
        //管理器
        FullyGridLayoutManager manager = new FullyGridLayoutManager(MerchantInformationActivity.this, 4, GridLayoutManager.VERTICAL, false);
        shopPictures.setLayoutManager(manager);
        //选择多图的适配器
        adapter = new GridImageAdapter(MerchantInformationActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(5);
        shopPictures.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    // 预览图片 可自定长按保存路径
                    //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                    PictureSelector.create(MerchantInformationActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
                }
            }
        });

    }

    /**
     * 给添加图片按钮设置点击事件监听器
     */
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(MerchantInformationActivity.this)
                    .openGallery(PictureMimeType.ofImage())//启用相册类型（图片）
                    .theme(R.style.picture_default_style)//样式
                    .maxSelectNum(5)//最大选择数量
                    .minSelectNum(1)//最小选择数量
                    .imageSpanCount(4)//相册每行显示数量
                    .selectionMode(PictureConfig.MULTIPLE)//多选
                    .previewImage(true)//预览图片
                    .isCamera(false)//显示启用相机
                    .selectionMedia(selectList)//显示已选列表
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    };

    /**
     * 选择图片完成以后的回调方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) switch (requestCode) {
            //选择多张图片的回调处理
            case PictureConfig.CHOOSE_REQUEST:
                // 图片、视频、音频选择结果回调
                selectList = PictureSelector.obtainMultipleResult(data);
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                adapter.setList(selectList);
                adapter.notifyDataSetChanged();
                break;
            //选择headPic的回调处理
            case 3:
                headPicUri = data.getData();
                shopHeader.setText("已选择");
                headPicAlready = true;
                break;
        }
    }

    /**
     * 获取shop对象
     */
    private void createNewShop(){
        newShop.setShopName(shopName.getText().toString());
        newShop.setShopPhone(shopPhone.getText().toString());
        newShop.setShopIntroduce(shopIntroduce.getText().toString());
        newShop.setShopAddress(shopAddress.getText().toString());
    }

    /**
     * 上传选中的图片并返回图片的访问路径
     */
    private void uploadPicAndGetPathList(String url, Handler uploadPicListhandler) {
        //获取图片信息
        List<String> photoStringList = new ArrayList<>();
        for (Uri uri : uploadPicUriList) {
            try {
                //获取bitmap
//                    //ContentResolver cr = getContentResolver();
//                    //  bitmap = MediaStore.Images.Media.getBitmap(cr,uri);
                String photoStr = util.getStringFromUri(MerchantInformationActivity.this,uri);
                photoStringList.add(photoStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        final String picList = gson.toJson(photoStringList);

        util.requestServer(url, picList, token, uploadPicListhandler);
        Toast.makeText(getApplicationContext(),
                "正在上传",
                Toast.LENGTH_SHORT).show();
    }
}
