package com.vx.vipnc.weixin.utils;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainUtil {

    private static final Logger log= LoggerFactory.getLogger(MainUtil.class);


    public static  <T> String tToJson(T t){
        return  JSONObject.toJSONString(t);
    }

    //json to object
    public static  <T> T jsonstringToT(String json,Class<T> tClass){

        return JSONObject.parseObject(json,tClass);
    }

    public static String buildTextMessage(Map<String ,String> map ,String content){
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        /**
         * 文本消息XML数据格式
         * <xml>
         <ToUserName><![CDATA[toUser]]></ToUserName>
         <FromUserName><![CDATA[fromUser]]></FromUserName>
         <CreateTime>1348831860</CreateTime>
         <MsgType><![CDATA[text]]></MsgType>
         <Content><![CDATA[this is a test]]></Content>
         <MsgId>1234567890123456</MsgId>
         </xml>
         */
        String str=String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>"+
                        "<FromUserName><![CDATA[%s]]></FromUserName>"+
                        "<CreateTime>%s</CreateTime>"+
                        "<MsgType><![CDATA[text]]></MsgType>"+
                        "<Content><![CDATA[%s]]></Content>"+
                        "</xml>",fromUserName,toUserName,System.currentTimeMillis(),content
        );
        log.error(str);;

        return str;
    }

    public static  Map<String ,String>  parseXml(HttpServletRequest request)throws  Exception{
        Map<String,String> map=new HashMap<>();
        InputStream inputStream = request.getInputStream();
        SAXReader reader=new SAXReader();
        Document document = reader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        elements.forEach(ele->{
            map.put(ele.getName(),ele.getText());
        });
        inputStream.close();
        return  map;
    }

}
