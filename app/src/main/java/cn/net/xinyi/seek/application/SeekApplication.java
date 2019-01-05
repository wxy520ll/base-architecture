package cn.net.xinyi.seek.application;

import com.xylibrary.application.XinYiApplication;
import com.xymaplibrary.base.XinYiMapSdk;

public class SeekApplication extends XinYiApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        XinYiMapSdk.initMapSdk(this);
    }
}
