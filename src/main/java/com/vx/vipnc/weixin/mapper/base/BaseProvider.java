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

    public static  String updateByPrimaryKey(BaseEntity baseEntity){
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

    public static String deleteT(BaseEntity baseEntity){
        SQL sql =new SQL();
        Field[] declaredFields = baseEntity.getClass().getDeclaredFields();
        for (Field  declaredField :declaredFields) {
            String key = declaredField.getName();
            Object value=CommonTool.invokeModelNoParameterMethod(baseEntity,"get"+CommonTool.getTopUpperString(key));
           if (value ==null || key.equals(baseEntity.getPrimaryKey())) continue; ;
            sql.WHERE("`"+baseEntity.getPrimaryKey()+"`=#{"+baseEntity.getPrimaryKey()+"}");
            System.out.println(sql);
        }
        return  sql.toString();
    }

    public static String getAllTWithCondition(String selectStatement,String tableName ,
                                              String condition ,String orderCondition ,
                                              Integer start ,Integer size){
        StringBuffer sb=new StringBuffer();
        if (selectStatement==null){
            sb.append("* ");
        }else {
            sb.append(selectStatement +" , ");
        }
        sb.append(" from") ;
        sb.append( tableName + "  ");
        if ( condition !=null){
            sb.append( "where " + condition+"  ");
        }
        if ( orderCondition !=null){
            sb.append( "order by "+ orderCondition);
        }
        if ( start !=null || size != null){
            sb.append("limit ");
            sb.append(null !=start && start>=0 ?start:0);
            sb.append(", ");
            sb.append(size !=null && size>=0?size:10);
        }
        System.err.println(sb.toString());
        return sb.toString();

    }

}
