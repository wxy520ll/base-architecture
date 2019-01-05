package com.xylibrary.base;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fingdo.statelayout.StateLayout;
import com.xylibrary.utils.StateBarUtil;
import com.xylibrary.widget.LoadingDialig;

import butterknife.ButterKnife;
import xinyi.com.xylibrary.R;

public abstract class BaseFragment extends Fragment implements IBaseView {

    public View mRootView;

    @Nullable
    public Toolbar toolBar;

    @Nullable
    public TextView centerTitle;
    @Nullable
    public TextView tvRightTitle;

    public RelativeLayout mRelativeLayout;//设置padding值与状态栏匹配

    public LoadingDialig mLoadingDialig;

    public StateLayout mStateLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflaterView();
        ButterKnife.bind(this, mRootView);
        toolBar = mRootView.findViewById(R.id.toolbar_normal);
        centerTitle = mRootView.findViewById(R.id.centerTitle);
        tvRightTitle = mRootView.findViewById(R.id.tvRightTitle);
        mRelativeLayout = mRootView.findViewById(R.id.mRelativeLayout);
        if (toolBar!=null) {
            toolBar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        mStateLayout = mRootView.findViewById(R.id.mStatelayout);
        initView();
        return mRootView;
    }

    public abstract void initView();

    public abstract View inflaterView();


    @Override
    public void showNoNetWork() {
        if (mStateLayout != null)
            mStateLayout.showNoNetworkView();
    }

    @Override
    public void showLoading() {
        mLoadingDialig.show();
    }

    @Override
    public void showLoadFail(String s) {
        ToastUtils.showShort(s);
        if (mStateLayout != null)
            mStateLayout.showErrorView();
    }

    @Override
    public void showContentView() {
        if (mStateLayout != null)
            mStateLayout.showContentView();
    }

    @Override
    public void hideLoading() {
        mLoadingDialig.dismiss();
    }

    @Override
    public void showEmptyView() {
        if (mLoadingDialig != null)
            mLoadingDialig.dismiss();
    }


}