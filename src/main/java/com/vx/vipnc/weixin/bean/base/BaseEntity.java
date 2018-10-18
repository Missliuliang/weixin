package com.vx.vipnc.weixin.bean.base;

import java.util.Date;


public class BaseEntity {

    private Integer id ;

    private Date insertTime ;

    private String tableName ;

    private String primaryKey ;


    public String getPrimaryKey(){
        return  primaryKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}
