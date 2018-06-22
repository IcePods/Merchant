package com.example.shan.merchant.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shan.merchant.Adapter.FullyGridLayoutManager;
import com.example.shan.merchant.Adapter.GridImageAdapter;
import com.example.shan.merchant.Entity.HairStyle;
import com.example.shan.merchant.Entity.HairStyleDetail;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.MyTools.UploadPictureUtil;
import com.example.shan.merchant.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MerchantNewProductionActivity extends AppCompatActivity {
    //返回按钮
    private ImageButton Back;
    //作品名
    private EditText productionName;
    //作品头像
    private TextView productionHead;
    //作品描述
    private EditText productionDescription;
    //作品价格
    private EditText productionPrice;
    //作品展示图片
    private RecyclerView productionPicture;
    //已选择的head图片的Uri
    private Uri headPicUri;

    //图片适配器
    private GridImageAdapter adapter;
    //存储图片选择完成后的图片地址信息
    private List<LocalMedia> selectList = new ArrayList<>();
    //待上传的图片uri列表
    private List<Uri> uploadPicUriList = new ArrayList<>();
    private Boolean headPicAlready = false;

    //发布作品
    private Button publish;
    private MyClickListener myClickListener;
    private Spinner spinner;

    //作品对象
    private HairStyle production;
    UploadPictureUtil util = new UploadPictureUtil();
    private String UploadPicUrl = UrlAddress.url + "uploadProductionPic.action";
    private String UploadProductionUrl = UrlAddress.url + "createNewProduction.action";
    private String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_new_production);
        //获取控件
        initControl();
        //给发型类型选择框绑定适配器并获取选择的值
        getHairStyleType();
        //上传作品图片绑定适配器
        setNewProductionImagesAdapter();
        //
        productionHead.setOnClickListener(myClickListener);
        Back.setOnClickListener(myClickListener);
        publish.setOnClickListener(myClickListener);
    }

    /**
     * 给发型类型选择框绑定适配器并获取选择的值
     */
    private void getHairStyleType() {
        String[] mItems = getResources().getStringArray(R.array.hairtype);
        //建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str=adapterView.getItemAtPosition(i).toString();
                Log.i("ztl","点击了"+str);
                //发型类型数据
                production.setHairstyleType(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * 初始化控件对象和监听器
     */
    private void initControl() {
        Back = findViewById(R.id.merchant_new_production_back);
        productionName = findViewById(R.id.new_production_title);
        productionDescription = findViewById(R.id.new_production_description);
        productionPrice = findViewById(R.id.new_production_price);
        productionPicture = findViewById(R.id.new_production_images);
        productionHead = findViewById(R.id.new_production_head);
        publish = findViewById(R.id.commit_new_production);
        myClickListener = new MyClickListener();
        spinner = findViewById(R.id.new_production_type);
        production = new HairStyle();

        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("merchanttoken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
    }

    /**
     * 点击事件监听器
     */
    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.merchant_new_production_back:
                    //返回按钮
                    finish();
                    break;
                case R.id.commit_new_production:
                    Handler handler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Bundle bundle = msg.getData();
                            String picPathStr = bundle.getString("string");
                            Gson gson = new Gson();
                            List<String> picPathList = gson.fromJson(picPathStr, new TypeToken<List<String>>(){}.getType());
                            production.setHairstylePicture(picPathList.get(0));
                            picPathList.remove(0);
                            Set<HairStyleDetail> hairStyleDetailsSet = new HashSet<>();
                            for(String str: picPathList){
                                HairStyleDetail detail = new HairStyleDetail();
                                detail.setHairstyle_detail_picture(str);
                                hairStyleDetailsSet.add(detail);
                            }
                            production.setHairStyleDetailSet(hairStyleDetailsSet);
                            String pro = gson.toJson(production);
                            util.requestServer(UploadProductionUrl,pro,token,null);
                        }
                    };
                    Boolean b = createNewProduction();
                    if(!b){
                        Toast.makeText(getApplicationContext(),
                                "您有作品信息未填写呦！",
                                Toast.LENGTH_SHORT).show();
                    }else if(!headPicAlready){
                        Toast.makeText(getApplicationContext(),
                                "您的作品头像未选择呦！",
                                Toast.LENGTH_SHORT).show();
                    }else if(selectList.size()<1){
                        Toast.makeText(getApplicationContext(),
                                "您的作品图片未选择呦！",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        uploadPicUriList.add(0,headPicUri);
                        Uri uri;
                        for (LocalMedia media:selectList){
                            uri = Uri.fromFile(new File(media.getPath()));
                            uploadPicUriList.add(uri);
                        }
                        uploadPicAndGetPathList(handler);
                    }
                    //发布作品按钮
                    finish();
                    break;
                case R.id.new_production_head:
                    Intent intentFromGallery = new Intent();
                    // 设置文件类型
                    intentFromGallery.setType("image/*");
                    intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intentFromGallery, 3);
                    break;
            }
        }
    }

    /**
     * 给RecycleView设置管理器和适配器
     */
    private void setNewProductionImagesAdapter(){
        //管理器
        FullyGridLayoutManager manager = new FullyGridLayoutManager(MerchantNewProductionActivity.this, 4, GridLayoutManager.VERTICAL, false);
        productionPicture.setLayoutManager(manager);
        //选择多图的适配器
        adapter = new GridImageAdapter(MerchantNewProductionActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(9);
        productionPicture.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    // 预览图片 可自定长按保存路径
                    //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                    PictureSelector.create(MerchantNewProductionActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
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
            PictureSelector.create(MerchantNewProductionActivity.this)
                    .openGallery(PictureMimeType.ofImage())//启用相册类型（图片）
                    .theme(R.style.picture_default_style)//样式
                    .maxSelectNum(9)//最大选择数量
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
            //选择headPic的回调处理
            case 3:
                headPicUri = data.getData();
                headPicAlready = true;
                Log.i("李垚::::::::::","headstr已复制");
                break;
        }
    }
    /**
     * 获取作品对象
     */
    private Boolean createNewProduction(){
        boolean bo = false;
        String proName = productionName.getText().toString();
        String proDesc = productionDescription.getText().toString();
        if(!"".equals(proDesc) && !"".equals(proName)){
            bo = true;
            production.setHairstyleName(proName);
            production.setHairstyleIntroduce(proDesc);
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
                String photoStr = util.getStringFromUri(MerchantNewProductionActivity.this,uri);
                photoStringList.add(photoStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        final String picList = gson.toJson(photoStringList);

        util.requestServer(UploadPicUrl, picList, token, uploadPicListhandler);
        Toast.makeText(getApplicationContext(),
                "正在上传",
                Toast.LENGTH_SHORT).show();
    }
}
