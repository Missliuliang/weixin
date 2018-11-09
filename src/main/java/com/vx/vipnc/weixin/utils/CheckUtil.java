package com.vx.vipnc.weixin.utils;

import java.util.Arrays;

public class CheckUtil {

    public static final String token="ltltoken";
    public static boolean checksignature(String signature,String timestamp,
                                         String nonce){
        String arr[] =new String[]{token ,timestamp,nonce};
        Arrays.sort(arr);
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i <arr.length ; i++) {
            sb.append(i);
        }
        return true;
    }
}
