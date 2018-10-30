package com.vx.vipnc.weixin.bean.api;

public class GetChengyuzhushiPack {

    private String ret_code ;

    private GetChengyuzhushi data;

    private String ret_message ;

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public GetChengyuzhushi getData() {
        return data;
    }

    public void setData(GetChengyuzhushi data) {
        this.data = data;
    }

    public String getRet_message() {
        return ret_message;
    }

    public void setRet_message(String ret_message) {
        this.ret_message = ret_message;
    }
}
