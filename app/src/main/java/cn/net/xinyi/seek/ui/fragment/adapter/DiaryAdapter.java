package cn.net.xinyi.seek.ui.fragment.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.net.xinyi.seek.R;

public class DiaryAdapter extends BaseQuickAdapter<Object,BaseViewHolder> {
    public DiaryAdapter(@Nullable List<Object> data) {
        super(R.layout.item_diary,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }
}
