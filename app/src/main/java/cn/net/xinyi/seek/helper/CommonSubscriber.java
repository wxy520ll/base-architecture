package cn.net.xinyi.seek.helper;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xylibrary.base.IBaseView;
import com.xylibrary.base.QzdsException;

import java.net.ConnectException;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 统一处理异常信息
 *
 * @param <T>
 */
public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private IBaseView mView;

    public CommonSubscriber(IBaseView mView) {
        this.mView = mView;
    }

    @Override
    public void onError(Throwable throwable) {
        if (throwable instanceof QzdsException) {
            QzdsException q = (QzdsException) throwable;
            mView.showLoadFail(q.getMessage());
            if (q.getCode() == 401) {
                ActivityUtils.finishAllActivities();
                ToastUtils.showShort("token过期,请重新登录");
            }
        } else if (throwable instanceof ConnectException) {
            if (!NetworkUtils.isConnected()) {
                ToastUtils.showShort("网络异常");
                mView.showNoNetWork();//网络异常
            } else {
                ToastUtils.showShort("服务器异常");
                mView.showLoadFail("服务器异常");
            }
        } else {
            mView.showLoadFail(throwable.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }
}
