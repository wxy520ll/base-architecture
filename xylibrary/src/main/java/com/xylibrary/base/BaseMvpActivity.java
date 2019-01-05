package com.xylibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;


/**
 * Created by jiajun.wang on 2018/3/19.
 */

public abstract class BaseMvpActivity<T extends RxPresenter> extends BaseActivity {


    public T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = inject();
        mPresenter.attachView(this);
        super.onCreate(savedInstanceState);
        mLoadingDialig.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode==KeyEvent.KEYCODE_BACK){
                mPresenter.detachView();
                dialog.dismiss();
            }
            return true;
        });
    }

    public abstract T inject();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
