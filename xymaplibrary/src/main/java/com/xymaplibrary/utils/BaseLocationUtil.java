package com.xymaplibrary.utils;

import android.content.Context;
import android.os.Bundle;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.xymaplibrary.R;
import com.xymaplibrary.modle.LocaionInfo;

public class BaseLocationUtil {

    public static void initLbs(Context context,final  LocationListener mLocationListener) {
        LocationClient  mLocationClient = new LocationClient(context);
        //声明LocationClient类
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(0);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedAddress(true);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5*60*1000);
        option.setOpenGps(true);//打开Gps
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    mLocationListener.receiveLocation(BaiduMapUtil.bdLocation2LocationInfo(location), true);
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    mLocationListener.receiveLocation(BaiduMapUtil.bdLocation2LocationInfo(location), true);
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    mLocationListener.receiveLocation(BaiduMapUtil.bdLocation2LocationInfo(location), true);
                } else if (location.getLocType() == BDLocation.TypeServerError) { //服务端网络定位失败
                    mLocationListener.receiveLocation(BaiduMapUtil.bdLocation2LocationInfo(location),false);
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) { //网络不同导致定位失败，请检查网络是否通畅
                    mLocationListener.receiveLocation(BaiduMapUtil.bdLocation2LocationInfo(location),false);
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) { //无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机
                    mLocationListener.receiveLocation(BaiduMapUtil.bdLocation2LocationInfo(location),false);
                }
            }
        });
        mLocationClient.start();
    }

    public interface LocationListener{
        public  void receiveLocation(LocaionInfo location, boolean isSuccess);

    }
    //地图移动到对应的LatLng位置
    public  static void move2Position(LocaionInfo location, MapView mapView) {
        LatLng point = new LatLng(location.getLat(), location.getLongt());
        mapView.requestFocus();
        MapStatus.Builder builder = new MapStatus.Builder().zoom(16);
        builder.target(point);
        mapView.getMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    public static Marker addOverlay(LatLng latLng,MapView mapView,int source) {
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(source);
        MarkerOptions oStart = new MarkerOptions();//地图标记类型的图层参数配置类
        oStart.position(latLng);//图层位置点，第一个点为起点
        oStart.icon(bitmap);//设置图层图片
        oStart.zIndex(1);//设置图层Index
        Marker m = (Marker) (mapView.getMap().addOverlay(oStart));
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("latLng", latLng);
        m.setExtraInfo(mBundle);
        return m;
    }

}
