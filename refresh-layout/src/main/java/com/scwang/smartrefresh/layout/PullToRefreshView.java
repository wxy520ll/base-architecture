package com.scwang.smartrefresh.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fingdo.statelayout.StateLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.MaterialHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.util.NetworkUtil;
import com.xylibrary.utils.recycleview.RecyclerViewHelper;

import java.util.List;

/**
 * 下拉刷新 上啦加载，网络连接异常，数据为null，连接超时
 */
public class PullToRefreshView extends FrameLayout {

    private View mPullToRefreshView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private BaseQuickAdapter mAdapter;

    private PullToRefreshLoadMoreListener mPullToRefreshLoadMoreListener;
    private OnRefreshLoadMoreListener mOnRefreshLoadMoreListener;
    private StateLayout.OnViewRefreshListener mOnViewRefreshListener;
    private static int PAGENUMBER = 10;//每页的数目
    private StateLayout mStatelayout;

    private static final int FRESH=1;//下拉刷新操作
    private static final int LOAD=2;//上啦加载更多操作
    private static final int FAIL=3;//加载失败
    private static int TYPE=-1;


    public PullToRefreshView(@NonNull Context context) {
        super(context);
    }

    public PullToRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        mPullToRefreshView = View.inflate(getContext(), R.layout.activity_pullto_refresh, this);
        mStatelayout = (StateLayout) mPullToRefreshView.findViewById(R.id.mStatelayout);
        mRecyclerView = (RecyclerView) mPullToRefreshView.findViewById(R.id.mrecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSmartRefreshLayout = (SmartRefreshLayout) mPullToRefreshView.findViewById(R.id.mSmartRefreshLayout);
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setEnableHeaderTranslationContent(true);
                layout.setPrimaryColorsId(R.color.colorPrimary, R.color.colorPrimary);//全局设置主题颜色
                return new MaterialHeader(context);
            }
        });
        mOnRefreshLoadMoreListener = new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (checkNetWork()) {
                    if (mPullToRefreshLoadMoreListener != null) {
                        TYPE = 1;
                        mSmartRefreshLayout.setEnableLoadMore(false);
                        mPullToRefreshLoadMoreListener.onRefresh(refreshLayout);
                    } else {
                        mSmartRefreshLayout.finishRefresh();
                    }
                } else {
                    mSmartRefreshLayout.finishRefresh();
                    Toast.makeText(getContext(), "网络异常,请检查网络", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (checkNetWork()) {
                    TYPE = 2;
                    if (mPullToRefreshLoadMoreListener != null) {
                        mSmartRefreshLayout.setEnableRefresh(false);
                        mPullToRefreshLoadMoreListener.onLoadMore(refreshLayout);
                    } else {
                        mSmartRefreshLayout.finishLoadMore();
                    }
                } else {
                    mSmartRefreshLayout.finishLoadMore();
                }
            }
        };
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(mOnRefreshLoadMoreListener);

        mStatelayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                TYPE = 3;
                mAdapter.getData().clear();
                mAdapter.notifyDataSetChanged();
                mStatelayout.showContentView();
                if (mOnViewRefreshListener != null)
                    mOnViewRefreshListener.refreshClick();
            }
        });
        boolean mb = NetworkUtil.isNetworkConnected(getContext());
        if (!mb) {
            mStatelayout.showNoNetworkView();
        }
    }


    /**
     * 数据从下拉刷新来
     *
     * @param data
     * @param <T>
     */
    public <T> void addDataFromRefresh(List<T> data, boolean isLoadMore) {
        mStatelayout.showContentView();
        mAdapter.getData().clear();
        mAdapter.addData(data);//添加新数据
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.setEnableLoadMore(isLoadMore);
        checkShowState(data);
    }


    public <T> void addDataFromLoadMore(List<T> data, boolean isRefresh) {
        mStatelayout.showContentView();
        mAdapter.addData(data);
        mSmartRefreshLayout.finishLoadMore();
        mSmartRefreshLayout.setEnableRefresh(isRefresh);
        checkShowState(data);
    }

    public <T> void addData(List<T> data) {
        if (TYPE == 1 || TYPE == 3) {
            addDataFromRefresh(data, true);
        } else {
            addDataFromLoadMore(data, true);
        }
    }

    /**
     * 根据内容的条数判断是否为显示EmptyView 还是没有更多
     */
    public void checkShowState(List data) {
        if (mAdapter.getData().size() == 0) {
            mStatelayout.showEmptyView();
            return;
        }
        if (data.size() < PAGENUMBER) {
            if (mAdapter.getData().size() > PAGENUMBER) {
                ToastUtils.showShort("(*>﹏<*)已经到底了");
            }
            mSmartRefreshLayout.setEnableLoadMore(false);
        }
        mStatelayout.showContentView();
    }

    /**
     * 判断是否有网络
     */
    public boolean checkNetWork() {
        boolean mb = NetworkUtil.isNetworkConnected(getContext());
        if (!mb) {
            mStatelayout.showNoNetworkView();
        }
        return mb;
    }


    /**
     * 调用PullToRefreshView.setAdapter   得到要用的mAdapter 对象
     *
     * @param <T>
     */
    public <T, K extends BaseViewHolder> void setAdapter(BaseQuickAdapter<T, K> mAdapter) {
        this.mAdapter = mAdapter;
        RecyclerViewHelper.initRecyclerViewV(mRecyclerView, true, mAdapter);
    }


    public <T, K extends BaseViewHolder> void setAdapter(BaseQuickAdapter<T, K> mAdapter, boolean isDivided) {
        this.mAdapter = mAdapter;
        RecyclerViewHelper.initRecyclerViewV(mRecyclerView, isDivided, mAdapter);
    }

    /**
     * 上拉，下拉在activity中的回调
     */
    public interface PullToRefreshLoadMoreListener {
        public void onRefresh(RefreshLayout refreshLayout);

        public void onLoadMore(RefreshLayout refreshLayout);
    }

    public static void setPAGENUMBER(int PAGENUMBER) {
        PullToRefreshView.PAGENUMBER = PAGENUMBER;
    }

    public void setmPullToRefreshLoadMoreListener(PullToRefreshLoadMoreListener mPullToRefreshLoadMoreListener) {
        this.mPullToRefreshLoadMoreListener = mPullToRefreshLoadMoreListener;
    }

    public void autoRefresh() {
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.autoRefresh();
    }


    public void setEnableRefresh(boolean bl) {
        mSmartRefreshLayout.setEnableRefresh(bl);
    }

    public void setEnableLoadMore(boolean bl) {
        mSmartRefreshLayout.setEnableLoadMore(bl);
    }


    public void setRefreshListener(StateLayout.OnViewRefreshListener m) {
        this.mOnViewRefreshListener = m;
    }

    public int getLoadType() {
        return TYPE;
    }

    public PullToRefreshView setLoadType(int loadType) {
        this.TYPE = loadType;
        return this;
    }
}
