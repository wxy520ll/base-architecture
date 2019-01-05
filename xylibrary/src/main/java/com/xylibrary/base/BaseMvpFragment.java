package com.xylibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xylibrary.widget.LoadingDialig;

import butterknife.ButterKnife;


public abstract class BaseMvpFragment<T extends RxPresenter> extends BaseFragment {

    public T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mPresenter = inject();
        mPresenter.attachView(this);
        mLoadingDialig = new LoadingDialig(getActivity());
        mLoadingDialig.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                mPresenter.detachView();
            }
            return true;
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public abstract T inject();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
        ButterKnife.unbind(this);
    }
}
