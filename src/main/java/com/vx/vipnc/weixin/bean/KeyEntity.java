package com.vx.vipnc.weixin.bean;

import com.vx.vipnc.weixin.bean.base.BaseEntity;

import java.io.Serializable;

public class KeyEntity extends BaseEntity implements Serializable {

    private  Integer id ;

    private  String name ;

    private String content ;

    private  String image ;

    private  String type ;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
