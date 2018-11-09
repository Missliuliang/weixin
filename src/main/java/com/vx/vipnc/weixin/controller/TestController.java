package com.vx.vipnc.weixin.controller;

import com.vx.vipnc.weixin.bean.UserEntity;
import com.vx.vipnc.weixin.service.KeyService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    private KeyService keyService;

    @RequestMapping("downLoads")
    public String downloadclassmate(HttpServletResponse response)throws IOException {
        HSSFWorkbook workbook =new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        List<UserEntity>  list=new ArrayList<>();
        UserEntity userEntity=new UserEntity();
        userEntity.setId(1);
        userEntity.setName("zhangsan");
        userEntity.setOpenId("0000000000000000sss");
        list.add(userEntity);

        UserEntity userEntity1= new UserEntity();
        userEntity1.setId(2);
        userEntity1.setName("张三");
        userEntity1.setOpenId("weqweqweqwaewqwdwdsadadadq");

        list.add(userEntity1);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(3);
        userEntity2.setName("张三李四网吗毛遂大师大师的打打赏多打打赏多大萨达所多大大大");
        userEntity2.setOpenId("weqweqweqwaewqwdwdsadadadq");
        list.add(userEntity2);

        String xlsName="userEntityInfo.xls";

        //headers表示excel表中第一行的表头
        String head[] =new String[]{"学号", "姓名", "张三李四王五麻溜"};
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i <head.length ; i++) {
            //在excel表中添加表头
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString hssfRichTextString=new HSSFRichTextString(head[i]);
            cell.setCellValue(hssfRichTextString);

        }
        int rownum=1;
        for (UserEntity user:list) {
            HSSFRow row1 = sheet.createRow(rownum);
            row1.createCell(0).setCellValue(user.getId());
            row1.createCell(1).setCellValue(user.getName());
            row1.createCell(2).setCellValue(user.getOpenId());
            rownum++ ;
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + xlsName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        return "success";
    }
}
