package com.xylibrary.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import io.reactivex.Flowable;
import okhttp3.MediaType;

public class BasePresenter {

    public <T> Flowable<T> requestData(HashMap<String, Object> org, String url, TypeReference typeReference) {
        org.put("timestamp",String.valueOf(new Date().getTime()));
        org.put("version","1");
        org.put("nonce", UUID.randomUUID().toString());
        org.put("deviceType","1");
        org.put("userToken", SPUtils.getInstance().getString("userToken"));
        org.put("signature", SPUtils.getInstance().getString("signature"));
        return OkGo.<T>post(url)
                .upString(JSONObject.toJSONString(org), MediaType.parse("application/json"))
                .converter(new ResultConvert<T>(typeReference))
                .adapt(new FlowableBody<T>());
    }

    public <T> Flowable<T> uploadFile(String type, File file , String url, TypeReference typeReference) {
        return OkGo.<T>post(url)
                .params("file",file)
                .params("timestamp",String.valueOf(new Date().getTime()))
                .params("version","1")
                .params("nonce",UUID.randomUUID().toString())
                .params("deviceType","1")
                .params("imageType",type)
                .params("userToken",SPUtils.getInstance().getString("userToken"))
                .params("signature",SPUtils.getInstance().getString("signature"))
                .converter(new ResultConvert<T>(typeReference))
                .adapt(new FlowableBody<T>());
    }


}
