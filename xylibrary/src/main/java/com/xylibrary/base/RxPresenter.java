package com.xylibrary.base;



import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by codeest on 2016/8/2.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 * <p>
 */
public class RxPresenter<T extends IBaseView> {


    protected CompositeDisposable mCompositeDisposable;
    public T mView;


    public void attachView(T view) {
        this.mView = view;
    }


    public void detachView() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    public void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

}


