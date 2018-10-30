package com.vx.vipnc.weixin.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.io.UnsupportedEncodingException;

import java.util.*;

public class HttpUtil {

    public static  String getYiYuanSign(Map<String,String> map ,String secret){
        if (map.isEmpty() || map==null ) return  null ;
        Set<String> set = map.keySet();
        ArrayList<String> list = new ArrayList<>(set);
        Collections.sort(list);
        StringBuffer stringBuffer=new StringBuffer();
        for (int i = 0; i <list.size() ; i++) {
            stringBuffer.append(list.get(i));
            stringBuffer.append(map.get(list.get(i))); //map value
        }
        String sign=stringBuffer.toString()+secret;
        try {
            return DigestUtils.md5Hex(sign.getBytes("utf-8"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null ;
    }

    public static String getYiYuanUrl(Map<String ,String> map ,String url){
        if (map.isEmpty() || map==null) return  null ;
        Set<String> keySet = map.keySet();
        List<String> list=new ArrayList<>(keySet);
        Collections.sort(list);
        StringBuffer stringBuffer=new StringBuffer();
        for (int i = 0; i <list.size() ; i++) {
            if (stringBuffer.length()!=0){
                stringBuffer.append("&");
            }
            stringBuffer.append(list.get(i));
            stringBuffer.append("=");
            stringBuffer.append(map.get(list.get(i)));
            System.out.println(stringBuffer.toString());
        }
        return url+"?"+stringBuffer.toString();
    }


    public static  <Q> ResponseEntity sendGet(String url){
        HttpHeaders httpHeaders =new HttpHeaders();
        httpHeaders.set("Content-type","application/json");
        ResponseEntity<String> result;
        RestTemplate restTemplate=new RestTemplate();
        try{
            result=restTemplate.exchange(url, HttpMethod.GET,null ,String.class);
        }catch (Exception e){
            result=new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            e.printStackTrace();
        }
        return  result;
    }

}
