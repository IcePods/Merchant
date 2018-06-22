package com.example.shan.merchant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shan.merchant.Entity.HairStyle;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.Entity.Works;
import com.example.shan.merchant.R;

import java.util.List;

/**
 * Created by 天龙 on 2018/5/30.
 */

public class MerchantProductionListAdopter extends BaseAdapter {
    //上下文环境
    private Context mContext;
    //声明列表项的布局itemID
    private int mLauoutId;
    //声明数据源
    private List<HairStyle> dataSource;

    public MerchantProductionListAdopter(Context mContext,int mLauoutId,List<HairStyle> worksList){
        this.mContext = mContext;
        this.mLauoutId = mLauoutId;
        this.dataSource = worksList;
    }

    //数据源的数量
    @Override
    public int getCount() {
        return dataSource.size();
    }
//返回选择的item项的数据
    @Override
    public Object getItem(int i) {
        return dataSource.get(i);
    }
//返回当前选择了第几个item项
    @Override
    public long getItemId(int i) {
        return i;
    }
//返回item的布局视图
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view && null!=mContext){
            //获取子项目item的布局文件
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //布局文件赋值给view参数
            view = mInflater.inflate(mLauoutId,null);
        }
        //获取布局文件中的控件对象
        ImageView img = view.findViewById(R.id.item_production_image);
        TextView title = view.findViewById(R.id.merchant_production_title);
        TextView price = view.findViewById(R.id.merchant_production_price);
        TextView description = view.findViewById(R.id.merchant_production_description);
        HairStyle hairStyle = dataSource.get(i);
        //设置图片
        Glide.with(mContext).load(UrlAddress.url + hairStyle.getHairstylePicture()).into(img);

        title.setText(hairStyle.getHairstyleName());
        price.setText("100");
        String str = hairStyle.getHairstyleIntroduce();
        if(str.length() > 20){
            str = str.substring(0,20)+"...";
        }
        description.setText(str);
        return view;
    }

    public void add(List<HairStyle> addList){
        //增加数据
        int position = dataSource.size();
        dataSource.addAll(position,addList);
        //notifyItemInserted(position);
        notifyDataSetChanged();
    }

    public void refresh(List<HairStyle> newList){
        //刷新数据
        dataSource = newList;
        notifyDataSetChanged();
    }

}
