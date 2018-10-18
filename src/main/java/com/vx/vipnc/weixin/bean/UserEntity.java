package com.vx.vipnc.weixin.bean;

import com.vx.vipnc.weixin.bean.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class UserEntity extends BaseEntity implements Serializable {

    private String name;

    private String nickname;

    private String openId;

    private Integer sex;

    private Integer subscribe;

    private String country;

    private String province;

    private String city;

    private String language;

    private String headimgurl;

    private Date subscribeTime;

    private String remark;

    private String subscribe_scene;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSubscribe_scene() {
        return subscribe_scene;
    }

    public void setSubscribe_scene(String subscribe_scene) {
        this.subscribe_scene = subscribe_scene;
    }
}

