package cn.net.xinyi.seek.ui.fragment;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.xylibrary.base.BaseMvpFragment;

import cn.net.xinyi.seek.R;
import cn.net.xinyi.seek.presenter.LoginPresenter;

/**
 * 最新
 */
public class NewFragment extends BaseMvpFragment<LoginPresenter> {
    @Override
    public LoginPresenter inject() {
        return new LoginPresenter();
    }

    @Override
    public void initView() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            ToastUtils.showShort("news");
        }
    }

    @Override
    public View inflaterView() {
        return View.inflate(getActivity(), R.layout.fragment_new, null);
    }
}
