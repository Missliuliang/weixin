package com.vx.vipnc.weixin.utils;

import com.alibaba.fastjson.JSONObject;

public class MainUtil {


    public static  <T> String tToJson(T t){
        return  JSONObject.toJSONString(t);
    }

    //json to object
    public static  <T> T jsonstringToT(String json,Class<T> tClass){
        return JSONObject.parseObject(json,tClass);
    }
}
