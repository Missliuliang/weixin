package com.vx.vipnc.weixin.bean;

import com.vx.vipnc.weixin.bean.base.BaseEntity;

import java.io.Serializable;

public class HanZiEntity extends BaseEntity implements Serializable {

    private String hanZi;
    private String pinYin;
    private String buShou ;
    private String words ;
    private String wuBi;
    private String basic_explain ;
    private String detail_explain ;
    private  String data ;

    public String getHanZi() {
        return hanZi;
    }

    public void setHanZi(String hanZi) {
        this.hanZi = hanZi;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getBuShou() {
        return buShou;
    }

    public void setBuShou(String buShou) {
        this.buShou = buShou;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getWuBi() {
        return wuBi;
    }

    public void setWuBi(String wuBi) {
        this.wuBi = wuBi;
    }

    public String getBasic_explain() {
        return basic_explain;
    }

    public void setBasic_explain(String basic_explain) {
        this.basic_explain = basic_explain;
    }

    public String getDetail_explain() {
        return detail_explain;
    }

    public void setDetail_explain(String detail_explain) {
        this.detail_explain = detail_explain;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
