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
import com.example.shan.merchant.Entity.HairStyleDetail;
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

public class MerchantCompleteinfoActivity extends AppCompatActivity {
    private Button btnCommit;//保存按钮
    private EditText shopName;//店铺名字
    private EditText shopAddress;//店铺地址
    private EditText shopPhone;//电话
    private EditText shopIntroduce;//简介
    private RecyclerView shopPictures;//添加图片
    private MyOnClickListener myOnClickListener;

    //图片适配器
    private GridImageAdapter adapter;
    //存储图片选择完成后的图片地址信息
    private List<LocalMedia> selectList = new ArrayList<>();
    //待上传的图片uri列表
    private List<Uri> uploadPicUriList = new ArrayList<>();

    UploadPictureUtil util = new UploadPictureUtil();
    private String token;
    private Shop shop;

    //上传店铺图片的地址
    private String uploadShopPicUrl = UrlAddress.url + "uploadShopPic.action";
    //更新店铺信息地址
    private String UploadShopInformationUrl = UrlAddress.url + "addShop.action";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_completeinfo);

        //获取控件
        initControl();
        //给recycleView设置adapter
        setNewShopImagesAdapter();


        //设置监听器
        myOnClickListener = new MyOnClickListener();
        btnCommit.setOnClickListener(myOnClickListener);
    }

    /**
     * 初始化控件
     */
    private void initControl() {
        btnCommit = findViewById(R.id.add_information_commit);
        shopName = findViewById(R.id.add_information_name);
        shopAddress = findViewById(R.id.add_information_location);
        shopPhone = findViewById(R.id.add_information_phone);
        shopIntroduce = findViewById(R.id.add_Information_introduce);
        shopPictures = findViewById(R.id.add_information_images);
        token = util.getToken(getApplicationContext());
        shop = new Shop();
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){

                //申请开店按钮
                case R.id.add_information_commit:
                    //只实现跳转
                    Handler handler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Bundle bundle = msg.getData();
                            String picPathStr = bundle.getString("string");
                            Gson gson = new Gson();
                            List<String> picPathList = gson.fromJson(picPathStr, new TypeToken<List<String>>(){}.getType());
                            shop.setShopPicture(picPathList.get(0));
                            picPathList.remove(0);
                            Set<ShopPicture> shopPicSet = new HashSet<>();
                            for(String str: picPathList){
                                ShopPicture shopPic = new ShopPicture();
                                shopPic.setShoppicture_picture(str);
                                shopPicSet.add(shopPic);
                            }
                            shop.setShopPictureSet(shopPicSet);
                            String pro = gson.toJson(shop);
                            util.requestServer(UploadShopInformationUrl,pro,token,null);
                        }
                    };
                    Boolean b = createNewShop();
                    if(!b){
                        Toast.makeText(getApplicationContext(),
                                "您有店铺信息未填写呦！",
                                Toast.LENGTH_SHORT).show();
                    }else if(selectList.size()<1){
                        Toast.makeText(getApplicationContext(),
                                "您的店铺图片未选择呦！",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Uri uri;
                        for (LocalMedia media:selectList){
                            uri = Uri.fromFile(new File(media.getPath()));
                            uploadPicUriList.add(uri);
                        }
                        uploadPicAndGetPathList(handler);
                    }
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }

    /**
     * 给RecycleView设置管理器和适配器
     */
    private void setNewShopImagesAdapter(){
        //管理器
        FullyGridLayoutManager manager = new FullyGridLayoutManager(MerchantCompleteinfoActivity.this, 4, GridLayoutManager.VERTICAL, false);
        shopPictures.setLayoutManager(manager);
        //选择多图的适配器
        adapter = new GridImageAdapter(MerchantCompleteinfoActivity.this, onAddPicClickListener);
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
                    PictureSelector.create(MerchantCompleteinfoActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
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
            PictureSelector.create(MerchantCompleteinfoActivity.this)
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
                Log.i("李垚：：：：：：", "作品图片已选择" + selectList.size());
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                adapter.setList(selectList);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 获取shop对象
     */
    private Boolean createNewShop(){
        boolean bo = false;
        String shopNameStr = shopName.getText().toString();
        String phone = shopPhone.getText().toString();
        String shopDesc = shopIntroduce.getText().toString();
        String addre = shopAddress.getText().toString();
        if(!"".equals(shopNameStr) && !"".equals(phone) && !"".equals(shopDesc) && !"".equals(addre)){
            bo = true;
            shop.setShopName(shopNameStr);
            shop.setShopPhone(phone);
            shop.setShopIntroduce(shopDesc);
            shop.setShopAddress(addre);
        }
        return  bo;
    }

    /**
     * 上传选中的图片并返回图片的访问路径
     */
    private void uploadPicAndGetPathList(Handler uploadPicListhandler) {
        //获取图片信息
        List<String> photoStringList = new ArrayList<>();
        for (Uri uri : uploadPicUriList) {
            try {
                //获取bitmap
//                    //ContentResolver cr = getContentResolver();
//                    //  bitmap = MediaStore.Images.Media.getBitmap(cr,uri);
                String photoStr = util.getStringFromUri(MerchantCompleteinfoActivity.this,uri);
                photoStringList.add(photoStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        final String picList = gson.toJson(photoStringList);

        util.requestServer(uploadShopPicUrl, picList, token, uploadPicListhandler);
        Toast.makeText(getApplicationContext(),
                "正在上传",
                Toast.LENGTH_SHORT).show();
    }
}
