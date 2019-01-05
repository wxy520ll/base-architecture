package cn.net.xinyi.seek.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.xylibrary.base.BaseMvpFragment;
import com.xymaplibrary.utils.BaseLocationUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.net.xinyi.seek.R;
import cn.net.xinyi.seek.presenter.LoginPresenter;
import cn.net.xinyi.seek.util.DrawableUtil;


public class MapFragment extends BaseMvpFragment<LoginPresenter> {


    @Bind(R.id.mMapView)
    MapView mMapView;

    @Override
    public LoginPresenter inject() {
        return new LoginPresenter();
    }

    @Override
    public void initView() {

        tvRightTitle.setVisibility(View.VISIBLE);
        DrawableUtil.loadImageView2TextView(R.mipmap.filter, tvRightTitle, "");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseLocationUtil.initLbs(getActivity(), (location, isSuccess) -> {
            BaseLocationUtil.move2Position(location, mMapView);
            BaseLocationUtil.addOverlay(new LatLng(location.getLat(),location.getLongt()),mMapView,R.mipmap.dingwei);
        });
    }

    @Override
    public View inflaterView() {
        return View.inflate(getActivity(), R.layout.fragment_map, null);
    }


}
