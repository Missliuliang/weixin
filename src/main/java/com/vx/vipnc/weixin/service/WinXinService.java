package com.vx.vipnc.weixin.service;

import com.vx.vipnc.weixin.bean.UserEntity;
import com.vx.vipnc.weixin.bean.api.GetAccessTokenRespone;
import com.vx.vipnc.weixin.utils.HttpUtil;
import com.vx.vipnc.weixin.utils.MainUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class WinXinService {
    private  final  static Logger log= LoggerFactory.getLogger(WinXinService.class);


    @Autowired
    private UserService userService;

    @Autowired
    private KeyService keyService;

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.secret}")
    private String secret ;

    public String getContentBykey(Map<String ,String> map){
        UserEntity userEntity = getWeixinUser(map.get("FromUserName"));
        return MainUtil.buildTextMessage(map,keyService.getContentBykey(map,userEntity));

    }

    public String getAccessToken(){
        //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxdda693860abc31b7&secret=65ff50b51212a21495b778b5d36c55e8
        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
        ResponseEntity responseEntity = HttpUtil.sendGet(url);
        GetAccessTokenRespone getAccessTokenRespone = MainUtil.jsonstringToT(responseEntity.getBody().toString(), GetAccessTokenRespone.class);
        if (getAccessTokenRespone ==null) return null ;
        if (getAccessTokenRespone!=null && getAccessTokenRespone.getErrcode()!=0) return null;
        return getAccessTokenRespone.getAccess_token();
    }

    public UserEntity getWeixinUser(String openId){
        UserEntity user = userService.getUserByOpenId(openId);
        if (user == null) {
            user=new UserEntity();
            user.setOpenId(openId);
            userService.insertT(user);
        }
        return  user;
    }


}
