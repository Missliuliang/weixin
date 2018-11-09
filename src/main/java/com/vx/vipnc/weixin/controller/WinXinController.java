package com.vx.vipnc.weixin.controller;

import com.vx.vipnc.weixin.service.WinXinService;
import com.vx.vipnc.weixin.utils.CheckUtil;
import com.vx.vipnc.weixin.utils.MainUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@RestController
public class WinXinController  {

    private final static Logger log= LoggerFactory.getLogger(WinXinController.class);

    @Autowired
    WinXinService winXinService;

    @GetMapping(value = "weixinChat")
    public void doGet(PrintWriter out ,
                      @RequestParam(name = "signature" ,required = true) String signature,
                      @PathVariable(name = "timestamp" ,required = true) String timestamp,
                      @RequestParam(value = "nonce", required = true) String nonce,
                      @RequestParam(value = "echostr", required = true) String echostr){
        log.debug("验证 ");
        log.error("验证 ");
        if (CheckUtil.checksignature(signature,timestamp,nonce)){
            out.print(echostr);
        }
    }

    @PostMapping("weixinChat")
    public void dopost(HttpServletRequest request , HttpServletResponse response){
        try{
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            String responseMessage;
            Map<String,String> map= MainUtil.parseXml(request);
            responseMessage=winXinService.getContentBykey(map);
            if (responseMessage ==null || responseMessage.isEmpty()){
                responseMessage="未正确响应";
                log.error(responseMessage);
            }
            response.getWriter().print(responseMessage);
        }catch (Exception e){
            e.printStackTrace();
            log.debug(e.getMessage());
        }
    }
}
