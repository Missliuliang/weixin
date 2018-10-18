package com.vx.vipnc.weixin.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommonTool {

    public static Object invokeModelNoParameterMethod(Object object ,String methodName){
        try{
            Method method = object.getClass().getMethod(methodName);
            return method.invoke(object);
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }
        return  null ;
    }

    public static  String getTopUpperString(String str){
        return  str.substring(0,1).toUpperCase()+str.substring(1);
    }

    public static void main(String[] args) {
        System.out.println( getTopUpperString("sss"));
    }
}
