package cn.net.xinyi.seek.ui.fragment;

import android.view.View;

import com.xylibrary.base.BaseMvpFragment;

import cn.net.xinyi.seek.R;
import cn.net.xinyi.seek.presenter.LoginPresenter;


public class MineFragment extends BaseMvpFragment<LoginPresenter> {

    @Override
    public LoginPresenter inject() {
        return new LoginPresenter();
    }

    @Override
    public void initView() {
        centerTitle.setText("我的");
    }

    @Override
    public View inflaterView() {
        return View.inflate(getActivity(), R.layout.fragment_mine, null);
    }

}

