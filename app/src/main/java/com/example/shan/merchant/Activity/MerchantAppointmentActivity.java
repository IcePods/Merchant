package com.example.shan.merchant.Activity;



import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shan.merchant.Fragment.MerchantAppointmentDoneFragment;
import com.example.shan.merchant.Fragment.MerchantAppointmentNotDoneFragment;
import com.example.shan.merchant.Fragment.MerchantAppointmentWaitingFragment;
import com.example.shan.merchant.R;

public class MerchantAppointmentActivity extends AppCompatActivity {
    private TextView tvWaitingFragment;//待处理标签
    private TextView tvNotDoneFragment;//未完成标签
    private TextView tvDoneFragment;//已完成标签
    private ImageButton imageButtonBack;//顶部返回箭头
    //定义Fragment的管理器
    private FragmentManager fragmentManager;
    //定义当前页面fragment
    private Fragment currentFragment = new Fragment();
    //定义页面
    private Fragment WaitingFragment; //待处理约页面
    private Fragment NotDoneFragment;  //未完成约页面
    private Fragment DoneFragment;   //已完成页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_appointment);

        //获取控件
        getView();

        //给选项卡绑定事件监听器
        onClickListenerImpl listener = new onClickListenerImpl();
        tvWaitingFragment.setOnClickListener(listener);
        tvNotDoneFragment.setOnClickListener(listener);
        tvDoneFragment.setOnClickListener(listener);
        imageButtonBack.setOnClickListener(listener);

        //初始化页面对象
        WaitingFragment = new MerchantAppointmentWaitingFragment();
        NotDoneFragment = new MerchantAppointmentNotDoneFragment();
        DoneFragment = new MerchantAppointmentDoneFragment();

        //默认显示待处理页面
        ChangeFragment(WaitingFragment);

    }

    //获取布局文件中的控件对象
    private void getView() {
        tvWaitingFragment = findViewById(R.id.tv_appointment_waitForDealing);
        tvNotDoneFragment = findViewById(R.id.tv_appointment_NotDone);
        tvDoneFragment = findViewById(R.id.tv_appointment_Done);
        imageButtonBack = findViewById(R.id.appointment_back);
    }

    //切换页面的选项卡
    private void ChangeFragment(Fragment fragment) {
        //借助FragmentManage 和 FragmentTransac
        //判断FragmentManager 是否为空
        if (null == fragmentManager) {
            fragmentManager = getFragmentManager();
        }
        //切换页面每次切换页面，进行页面切换事物
        if (currentFragment != fragment) {//如果当前显示的页面和目标要显示的页面不同
            //创建事务
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            //隐藏当前页面
            transaction.hide(currentFragment);
            //判断待显示的页面是是否已经添加过
            if (!fragment.isAdded()) {//待显示的页面没有被添加过
                transaction.add(R.id.appointment_content, fragment);
            }
            //显示目标页面
            transaction.show(fragment);
            //提交事务
            transaction.commit();
            //赋值给当前页面
            currentFragment = fragment;
        }
    }

    // ragmentManage的监听器 点击切换页面
    private class onClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_appointment_waitForDealing:
                    tvWaitingFragment.setTextColor(0xFFF7A113);//更改标签颜色
                    //更改页面
                    ChangeFragment(WaitingFragment);
                    tvNotDoneFragment.setTextColor(Color.GRAY);
                    tvDoneFragment.setTextColor(Color.GRAY);
                    break;
                case R.id.tv_appointment_NotDone:
                    tvNotDoneFragment.setTextColor(0xFFF7A113);//更改标签颜色
                    //更改页面
                    ChangeFragment(NotDoneFragment);
                    tvWaitingFragment.setTextColor(Color.GRAY);
                    tvDoneFragment.setTextColor(Color.GRAY);
                    break;
                case R.id.tv_appointment_Done:
                    tvDoneFragment.setTextColor(0xFFF7A113);//更改标签颜色
                    //更改页面
                    ChangeFragment(DoneFragment);
                    tvWaitingFragment.setTextColor(Color.GRAY);
                    tvNotDoneFragment.setTextColor(Color.GRAY);
                    break;
                case R.id.appointment_back:
                    finish();
                    break;
            }
        }
    }
}
