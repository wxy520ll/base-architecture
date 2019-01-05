package com.xylibrary.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fingdo.statelayout.StateLayout;

import com.xylibrary.widget.LoadingDialig;

import butterknife.ButterKnife;
import xinyi.com.xylibrary.R;

/**
 * Created by jiajun.wang on 2018/3/19.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    @Nullable
    public Toolbar toolBar;

    @Nullable
    public TextView centerTitle;//中间的标题
    @Nullable
    public TextView tvRightTitle;//右边的标题
    @Nullable
    public TextView tvLeftTitle;//左边的标题

    @Nullable
    public StateLayout mStatelayout;



    public LoadingDialig mLoadingDialig;
    public String baiduAddress = "";//百度地图定位的地址
    public double latitude=0d;// 百度定位的纬度
    public double longitude=0d;//百度定位经度



    public static final int MESSAGE_VALID_NFCBUTTON = 16;
    public static final int MESSAGE_VALID_NFCSTART = 17;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        if (toolBar!=null) {
            toolBar = findViewById(R.id.toolbar_normal);
        }
        centerTitle = findViewById(R.id.centerTitle);
        tvRightTitle = findViewById(R.id.tvRightTitle);
        tvLeftTitle = findViewById(R.id.tvLeftTitle);
        mStatelayout=findViewById(R.id.mStatelayout);
        mLoadingDialig = new LoadingDialig(this);
        initView();
    }



    public abstract void initView();

    public abstract int getLayoutResource();


    @Override
    public void showNoNetWork() {
        if (mStatelayout!=null)
            mStatelayout.showNoNetworkView();
    }

    @Override
    public void showLoading() {
        mLoadingDialig.show();
    }

    @Override
    public void showLoadFail(String s) {
        ToastUtils.showShort(s);
        if (mStatelayout!=null)
            mStatelayout.showErrorView();
    }

    @Override
    public void showContentView() {
        if (mStatelayout!=null)
            mStatelayout.showContentView();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialig != null)
            mLoadingDialig.dismiss();
    }

    @Override
    public void showEmptyView() {
        if (mLoadingDialig != null)
            mStatelayout.showEmptyView();
    }


    public void initToorBar(String s) {
        toolBar.setBackgroundColor(Color.parseColor("#3A71FF"));
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        TextView title = toolBar.findViewById(xinyi.com.xylibrary.R.id.centerTitle);
        title.setText(s);
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(v -> finish());
    }

    /**
     * 定位成功之后 得到经纬度
     */
    public void locationSuccess(int requestCode, int resultCode, Intent data, TextView etPosition) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                baiduAddress = data.getStringExtra("address");
                latitude = data.getDoubleExtra("latitude", 0d);
                longitude = data.getDoubleExtra("longitude", 0d);
                etPosition.setText(baiduAddress);
            }
        }
    }


}
