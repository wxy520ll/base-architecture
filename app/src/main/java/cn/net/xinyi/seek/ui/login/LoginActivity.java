package cn.net.xinyi.seek.ui.login;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.xylibrary.base.BaseMvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.net.xinyi.seek.R;
import cn.net.xinyi.seek.presenter.LoginPresenter;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> {
    @Bind(R.id.btLogin)
    Button btLogin;

    @Override
    public LoginPresenter inject() {
        return new LoginPresenter();
    }

    @Override
    public void initView() {

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(MainActivity.class);
                finish();
            }
        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

}
