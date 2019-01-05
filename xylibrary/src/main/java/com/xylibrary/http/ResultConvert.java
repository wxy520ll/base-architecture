package com.xylibrary.http;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.convert.Converter;
import com.xylibrary.base.QzdsException;

import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.xylibrary.application.Constants.NOACCESS;
import static com.xylibrary.application.Constants.NOTFOUND;
import static com.xylibrary.application.Constants.REQUESTFAIL;
import static com.xylibrary.application.Constants.REQUESTSUCCESSCODE;
import static com.xylibrary.application.Constants.SUCCESSCODE;
import static com.xylibrary.application.Constants.UNAUTHORIZED;


/**
 * Created by jiajun.wang on 2018/2/25.
 * T为 要转换的数据类型
 */

public class ResultConvert<T> implements Converter<T> {
    private TypeReference typeReference;


    public ResultConvert(TypeReference typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        if (typeReference == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        if (response.code() == SUCCESSCODE) {
            String c = body.string();
            JSONObject i = JSONObject.parseObject(c);
            if (i != null && i.getIntValue("code") == REQUESTSUCCESSCODE) {
                String json = JSONObject.toJSONString(i.get("data"));
                if (StringUtils.isEmpty(json)||"null".equals(json)){
                    json="{}";
                }
                return (T) JSONObject.parseObject(json, typeReference);
            } else if (i != null && i.getIntValue("code") != REQUESTSUCCESSCODE) {
                throw new QzdsException(i.getString("msg"), i.getIntValue("code"));
            }
        } else if (response.code() == 401) {
            throw new QzdsException(UNAUTHORIZED, 401);
        } else if (response.code() == 403) {
            throw new QzdsException(NOACCESS, 403);
        } else if (response.code() == 404) {
            throw new QzdsException(NOTFOUND, 404);
        } else if (response.code() == 400) {
            throw new QzdsException("请求失败", 400);
        } else {
            throw new QzdsException(REQUESTFAIL, 405);
        }
        return null;
    }
}
