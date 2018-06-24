package com.example.shan.merchant.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.shan.merchant.Entity.Barber;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.R;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shan on 2018/5/30.
 */

public class BarberAdapter extends BaseAdapter{
    private Context mContext;
    private int mLayout;
    private List<Barber> barbers;
    OkHttpClient okHttpClient = new OkHttpClient();
    private Barber barber;

    public BarberAdapter(Context mContext, int mLayout, List<Barber> barbers) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.barbers = barbers;
    }

    //数据源的数量
    @Override
    public int getCount() {
        return barbers.size();
    }

    //返回选择的item项的数据
    @Override
    public Object getItem(int position) {
        return barbers.get(position);
    }

    //返回当前选择了第几个item项
    @Override
    public long getItemId(int position) {
        return position;
    }

    //返回item的布局视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView && null != mContext) {
            //获取子项目item的布局文件
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //布局文件赋值给convertView参数
            convertView = mInflater.inflate(mLayout, null);
        }
        //获取布局文件中的控件对象
        ImageView img = convertView.findViewById(R.id.merchant_employess_item_picture);
        TextView name = convertView.findViewById(R.id.merchant_employess_item_name);
        TextView introduce = convertView.findViewById(R.id.merchant_employess_item_introduce);

        Button button = convertView.findViewById(R.id.merchant_employess_item_delete);
        final Barber deleteBarber = barbers.get(position);
        barber = barbers.get(position);
        //设置删除按钮点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(mContext,"点击了第"+shop.getShopId()+"item",Toast.LENGTH_SHORT).show();*/
                barbers.remove(deleteBarber);
                notifyDataSetChanged();
                deleteBarber(deleteBarber.getBarberId());
            }
        });
        //利用传递的数据源给相应的控件对象赋值
        Log.i("barberaaaaaaa",barber.getUser().getUserHeader());
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .transform(new CircleCrop());
        Glide.with(mContext)
                .load(UrlAddress.url+barber.getUser().getUserHeader())
                .apply(requestOptions)
                .into(img);
        String str = "理发师理发师，擅长理光头";
        if(str.length() > 10){
            str = str.substring(0,10)+"...";
        }
        name.setText(barber.getUser().getUserName());
        introduce.setText(str);
        //返回子项目布局视图
        return convertView;
    }
    /**
     * 删除店员
     * */
    public void deleteBarber(int id){
        String barberId = String.valueOf(id);
        Log.i("barberIdbarberId",barberId);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("barberId",barberId);
        final FormBody body = builder.build();
        Request request = new Request.Builder().post(body).url(UrlAddress.url+"deleteBarber.action").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String deletebarber = response.body().string();
                Log.i("deleteBarber",deletebarber);
                /*Message message = Message.obtain();
                   message.what = 2;
                   Bundle bundle = new Bundle();
                   bundle.putString("isCollection",isCollection);
                   message.setData(bundle);
                   handler.sendMessage(message);*/
            }
        });
    }
}
