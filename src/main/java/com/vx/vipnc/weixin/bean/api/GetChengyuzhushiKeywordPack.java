package com.vx.vipnc.weixin.bean.api;

import java.util.List;

public class GetChengyuzhushiKeywordPack {

    private  String ret_code ;

    private List<GetChengyuzhushiKeyword> data;

    private String total ;

    private String ret_message ;

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public List<GetChengyuzhushiKeyword> getData() {
        return data;
    }

    public void setData(List<GetChengyuzhushiKeyword> data) {
        this.data = data;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRet_message() {
        return ret_message;
    }

    public void setRet_message(String ret_message) {
        this.ret_message = ret_message;
    }
}
