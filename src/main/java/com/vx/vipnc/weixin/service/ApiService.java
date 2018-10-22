package com.vx.vipnc.weixin.service;

import com.vx.vipnc.weixin.bean.api.GetTuLingPack;
import com.vx.vipnc.weixin.service.base.LoggerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService extends LoggerConfig {

    @Value("${showapi.showapiappid}")
    private String showapiAppid;

    @Value("${showapi.showapiappsecret}")
    private String showapiappsecret;

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
        Map<String ,String>  map =new HashMap<>();
        map.put("showapi_appid",showapiAppid);
        map.put("showapi_timestamp" ,"");
        return null;
    }



}
