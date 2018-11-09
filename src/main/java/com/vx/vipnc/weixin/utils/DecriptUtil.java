package com.vx.vipnc.weixin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DecriptUtil {



    public static String decript(String data ,String type){
        try{
            MessageDigest digest=MessageDigest.getInstance(type);
            digest.update(data.getBytes());
            byte[] bytes = digest.digest();
            StringBuffer sb=new StringBuffer();
            for (int i = 0; i <bytes.length ; i++) {
                String toHexString = Integer.toHexString(bytes[i] & 0xFF);
                if (toHexString.length()<2){
                    sb.append(0);
                }
                sb.append(toHexString);
            }
            return  sb.toString();

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }
}
