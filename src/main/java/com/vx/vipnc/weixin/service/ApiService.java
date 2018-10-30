package com.vx.vipnc.weixin.service;

import com.vx.vipnc.weixin.bean.api.*;
import com.vx.vipnc.weixin.service.base.LoggerConfig;
import com.vx.vipnc.weixin.utils.HttpUtil;
import com.vx.vipnc.weixin.utils.MainUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService extends LoggerConfig {

    @Value("${showapi.showapiappid}")
    private String showapiAppid;

    @Value("${showapi.showapiappsecret}")
    private String showapiappsecret;//秘诀; 奥秘; 秘密，机密;

    @Value("${showapi.zidianappid}")
    private  String zidianappid;

    @Value("${showapi.zidianappsecret}")
    private String zidianAppsecret;

    @Value("${showapi.showapiresgzip}")
    private String showapiResGzip ;

    @Value("${showapi.tulingurl}")
    private String tulingUrl ;

    @Value("${showapi.chengyuzhushiurl}")
    private String chengyuzhushiUrl ;

    @Value("${showapi.chengyubykeywordurl}")
    private String chengyuBykeyUrl;

    @Value("${showapi.zidianurl}")
    private String zidianUrl ;

    public GetTuLingPack getTuLing(String info ,String userId){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HHmmss");
        Map<String ,String>  map =new HashMap<>();
        map.put("showapi_appid",showapiAppid);
        map.put("showapi_timestamp" ,sdf.format(new Date()));
        map.put("userId" ,userId);
        map.put("info" ,info);
        String yiYuanSign = HttpUtil.getYiYuanSign(map, showapiappsecret);
        map.put("showapi_sign",yiYuanSign);
        ResponseEntity responseEntity= HttpUtil.sendGet(HttpUtil.getYiYuanUrl(map,tulingUrl));
        GetTuLingRespone getTuLingRespone = MainUtil.jsonstringToT(responseEntity.getBody().toString(), GetTuLingRespone.class);
        if ("0".equals(getTuLingRespone.getShowapi_res_code()) && null != getTuLingRespone.getShowapi_res_body()){
            return  getTuLingRespone.getShowapi_res_body();
        }
        return null;
    }

    public GetChengyuzhushi getChengyuzhushi(String keyword){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HHmmss");
        Map<String ,String>  map =new HashMap<>();
        map.put("showapi_appid",showapiAppid);
        map.put("showapi_timestamp" ,sdf.format(new Date()));
        map.put("keyword" ,keyword);
        String yiYuanSign = HttpUtil.getYiYuanSign(map, showapiappsecret);
        map.put("showapi_sign" ,yiYuanSign);
        ResponseEntity responseEntity = HttpUtil.sendGet(HttpUtil.getYiYuanUrl(map, chengyuzhushiUrl));
        GetChengyuzhushiRespone getChengyuzhusiRespone = MainUtil.jsonstringToT(responseEntity.getBody().toString(), GetChengyuzhushiRespone.class);
        if ("0".equals(getChengyuzhusiRespone.getShowapi_res_code()) && null !=getChengyuzhusiRespone.getShowapi_res_body()){
            return  getChengyuzhusiRespone.getShowapi_res_body().getData();
        }
        return  null;
    }

    public GetChengyuzhushiPack GetChengyuzhushiByKeyWord(String key , String page , String rows){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HHmmss");
        Map<String ,String>  map =new HashMap<>();
        map.put("showapi_appid",showapiAppid);
        map.put("showapi_timestamp" ,sdf.format(new Date()));
        map.put("keyword",key);
        map.put("page",page);
        map.put("rows",rows);

        String yiYuanSign = HttpUtil.getYiYuanSign(map, showapiappsecret);
        map.put("showapi_sign" ,yiYuanSign);
        ResponseEntity responseEntity = HttpUtil.sendGet(HttpUtil.getYiYuanUrl(map, chengyuzhushiUrl));
        GetChengyuzhushiRespone respone = MainUtil.jsonstringToT(responseEntity.getBody().toString(), GetChengyuzhushiRespone.class);
        if ("0".equals(respone.getShowapi_res_code()) && null != respone.getShowapi_res_body() && "0".equals(respone.getShowapi_res_body().getRet_code())) {
            return respone.getShowapi_res_body();
        }
        return null;
    }

    public GetZiDianPack getZiDianPack(String hanzi){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HHmmss");
        Map<String ,String>  map =new HashMap<>();
        map.put("showapi_appid",showapiAppid);
        map.put("showapi_timestamp" ,sdf.format(new Date()));
        map.put("hanzi",hanzi);
        String sign = HttpUtil.getYiYuanSign(map, showapiappsecret);
        map.put("sign" , sign);
        ResponseEntity responseEntity = HttpUtil.sendGet(HttpUtil.getYiYuanUrl(map, zidianUrl));
        GetZiDianRespone respone = MainUtil.jsonstringToT(responseEntity.getBody().toString(), GetZiDianRespone.class);
        if ("0".equals(respone.getShowapi_res_code()) && null != respone.getShowapi_ret_body() && "0".equals(respone.getShowapi_ret_body().getRet_code())) {
            return respone.getShowapi_ret_body();
        }
        return null;

    }

}
