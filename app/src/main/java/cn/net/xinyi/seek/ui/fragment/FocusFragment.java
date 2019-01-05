package cn.net.xinyi.seek.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.fingdo.statelayout.StateLayout;
import com.scwang.smartrefresh.layout.PullToRefreshView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xylibrary.base.BaseMvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.net.xinyi.seek.R;
import cn.net.xinyi.seek.presenter.LoginPresenter;
import cn.net.xinyi.seek.ui.fragment.adapter.DiaryAdapter;

/**
 * 关注
 */
public class FocusFragment extends BaseMvpFragment<LoginPresenter> implements PullToRefreshView.PullToRefreshLoadMoreListener, StateLayout.OnViewRefreshListener {
    @Bind(R.id.mPullToRefreshView)
    PullToRefreshView mPullToRefreshView;

    public DiaryAdapter mDiaryAdapter;

    @Override
    public LoginPresenter inject() {
        return new LoginPresenter();
    }

    @Override
    public void initView() {
        mPullToRefreshView.setEnableLoadMore(true);
        mPullToRefreshView.setEnableRefresh(true);

        List<Object> mlist=new ArrayList<Object>();
        mlist.add(new Object());
        mlist.add(new Object());
        mlist.add(new Object());

        mDiaryAdapter=new DiaryAdapter(mlist);
        mPullToRefreshView.setAdapter(mDiaryAdapter);
        mPullToRefreshView.setRefreshListener(this);
        mPullToRefreshView.setmPullToRefreshLoadMoreListener(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ToastUtils.showShort("foucs");
        }
    }

    @Override
    public View inflaterView() {
        return View.inflate(getActivity(), R.layout.fragment_focus, null);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {

    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {

    }

    @Override
    public void refreshClick() {

    }
}
