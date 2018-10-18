package com.vx.vipnc.weixin.mapper.base;

import com.vx.vipnc.weixin.bean.base.BaseEntity;
import com.vx.vipnc.weixin.utils.CommonTool;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;

public class BaseProvider {


    public static String insertT(BaseEntity baseEntity){
        SQL sql = new SQL().INSERT_INTO(baseEntity.getTableName());
        Class<? extends BaseEntity> aClass = baseEntity.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String key =declaredField.getName();
            Object value= CommonTool.invokeModelNoParameterMethod(baseEntity,
                    "get"+CommonTool.getTopUpperString(key));
            if (null ==value){
                continue;
            }
            sql. VALUES("`" + key + "`", "#{" + key + "}");
        }
        System.out.println(sql.toString());
        return sql.toString() ;
    }

    public static  String updateT(BaseEntity baseEntity){
        SQL sql =new SQL();
        sql.UPDATE(baseEntity.getTableName());
        Field[] declaredFields = baseEntity.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
          String  key=declaredField.getName();
          Object  value=CommonTool.invokeModelNoParameterMethod(baseEntity,"get"+CommonTool.getTopUpperString(key));
          if (value ==null || key.equals(baseEntity.getPrimaryKey())) continue;
          sql.SET("`"+key+"`=#{"+key+"}");
        }
        sql.WHERE("`"+baseEntity.getPrimaryKey()+"`=#{"+baseEntity.getPrimaryKey()+"}");
        System.out.println(sql.toString());
        return  sql.toString();
    }
}
