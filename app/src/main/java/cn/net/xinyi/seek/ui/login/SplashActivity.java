package cn.net.xinyi.seek.ui.login;

import com.xylibrary.base.BaseMvpActivity;
import com.xylibrary.base.RxPresenter;

import cn.net.xinyi.seek.R;

import cn.net.xinyi.seek.presenter.LoginPresenter;

public class SplashActivity extends BaseMvpActivity<LoginPresenter> {
    @Override
    public LoginPresenter inject() {
        return new LoginPresenter();
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_splash;
    }
}
