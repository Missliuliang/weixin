package com.vx.vipnc.weixin.service;

import com.vx.vipnc.weixin.bean.HanZiEntity;
import com.vx.vipnc.weixin.bean.KeyEntity;
import com.vx.vipnc.weixin.bean.OperationRecordEntity;
import com.vx.vipnc.weixin.bean.UserEntity;
import com.vx.vipnc.weixin.bean.api.*;
import com.vx.vipnc.weixin.bean.base.Magic;
import com.vx.vipnc.weixin.mapper.KeyMapper;
import com.vx.vipnc.weixin.mapper.OperationRecordMapper;
import com.vx.vipnc.weixin.mapper.base.BaseMapper;
import com.vx.vipnc.weixin.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeyService extends BaseService<KeyEntity> {

    @Autowired
    private KeyMapper keyMapper;

    @Autowired
    private ApiService apiService;

    @Autowired
    private HanZiService hanZiService;

    @Autowired
    private OperationRecordService operationRecordService;

    @Override
    protected BaseMapper getBaseMapper() {
        return keyMapper;
    }

    @Override
    protected String getTableName() {
        return "key";
    }

    public String getContentBykey(Map<String,String> map , UserEntity userEntity){
        String content = map.get("Content");
        String msgType = map.get("MsgType");

        if (!Magic.MSGTYPE_TEXT.equals(msgType) || null ==content) return "请尝试回复:操作指南";
        OperationRecordEntity recordEntity=new OperationRecordEntity();
        recordEntity.setUserId(userEntity.getId());
        recordEntity.setKeyWord(content);

        if (Magic.APITYPE_ZIDIAN.equals(content)){
            recordEntity.setType(Magic.APITYPE_ZIDIAN_DETAIL_ID);
            operationRecordService.insertT(recordEntity);
            return "请输入你要查询的汉字_例:路";
        }else  if (Magic.APITYPE_CHENGYU.equals(content)){
            recordEntity.setType(Magic.APITYPE_CHENGYU_KEY_ID);
            operationRecordService.insertT(recordEntity);
            return "请输入你要查询的成语_例:马到成功";
        }else if(Magic.APITYPE_CHENGYU_KEY.equals(content)){
            recordEntity.setType(Magic.APITYPE_CHENGYU_KEY_ID);
            operationRecordService.insertT(recordEntity);
            return "请输入你要查询的成语_例:成功";
        }
        KeyEntity keyEntity = getAllWithCondition(null, "name='" + content + "'", null);
        if (keyEntity!=null) {
            operationRecordService.insertT(recordEntity);
            return keyEntity.getContent();
        }

            OperationRecordEntity operation = operationRecordService.getAllWithCondition(null, null, "insertTime desc");
            if (operation != null) {
                if (Magic.APITYPE_ZIDIAN_ID.equals(operation.getType())) {
                    recordEntity.setType(Magic.APITYPE_ZIDIAN_ID);
                    if (Magic.APITYPE_ZIDIAN_DETAIL.equals(content)) {
                        return getZiDianDetail(operation.getKeyWord());
                    }
                    operationRecordService.insertT(recordEntity);
                    return getZiDian(content);
                } else if (Magic.APITYPE_CHENGYU_ID.equals(operation.getType())) {
                    // 之前用户操作的是成语注释
                    recordEntity.setType(Magic.APITYPE_CHENGYU_KEY_ID);
                    operationRecordService.insertT(recordEntity);
                    return getChengyuzhushi(content);
                } else if (Magic.APITYPE_CHENGYU_KEY_ID.equals(operation.getType())) {
                    // 之前用户操作的是 查找成语
                    recordEntity.setType(Magic.APITYPE_CHENGYU_KEY_ID);
                    Integer page = 1;
                    if (Magic.MSGTYPE_TEXT.equals(content)) {
                        recordEntity.setKeyWord(operation.getKeyWord());
                        page = operation.getPage() - 1;
                    }
                    recordEntity.setPage(page);
                    operationRecordService.insertT(recordEntity);
                    return getchengyuByKey(recordEntity.getKeyWord(), page.toString(), "10");
                }
            }
        GetTuLingPack getTulingPack = apiService.getTuLing(content, map.get("FromUserName"));
        if (getTulingPack!=null){
            return "图灵机器人:" + getTulingPack.getText();
        }
        return "请尝试回复:操作指南";
    }

    private  String getchengyuByKey(String content,String page ,String rows){
        StringBuffer sb=new StringBuffer();
        GetChengyuzhushiPack chengyuzhushiByKeyWord = apiService.getChengyuzhushiByKeyWord(content, page, rows);
        if (chengyuzhushiByKeyWord==null )  return "没有找到包含改关键字的成语" ;
        List<GetChengyuzhushiKeyword> list= (List<GetChengyuzhushiKeyword>) chengyuzhushiByKeyWord.getData();
        sb.delete(0,sb.length());
        sb.append("\r\n\t");
        sb.append("包含该关键词的成语有");
        sb.append(chengyuzhushiByKeyWord.getTotal());
        sb.append("条————展示第");
        sb.append(((Integer.valueOf(page)-1)* Integer.valueOf(rows) )+1);
        sb.append("到");
        sb.append((Integer.valueOf(page)-1)*Integer.valueOf(rows));
        sb.append("条");
        list.forEach(chengyu->{
            sb.append("\r\n\t");
            sb.append(chengyu.getTitle());
        });
        sb.append("\r\n\t");
        sb.append("回复‘下一页’查看第");
        sb.append(((Integer.valueOf(page)) * Integer.valueOf(rows)) + 1);
        sb.append("到");
        sb.append((Integer.valueOf(page) + 1) * Integer.valueOf(rows));
        sb.append("条");
        return sb.toString();
    }


    private String getChengyuzhushi(String content){
        GetChengyuzhushi chengyuzhushi = apiService.getChengyuzhushi(content);
        if( chengyuzhushi==null){
            return "请输入正确的成语";
        }
        StringBuffer stringBuilder=new StringBuffer();
        stringBuilder.delete(0,stringBuilder.length());
        stringBuilder.append("成语名称:\n\t");
        stringBuilder.append(chengyuzhushi.getTitle());
        stringBuilder.append("\r\n拼写读音:\n\t");
        stringBuilder.append(chengyuzhushi.getSpell());
        stringBuilder.append("\r\n解释:\n\t");
        stringBuilder.append(chengyuzhushi.getContent());
        stringBuilder.append("\r\n出自典故:\n\t");
        stringBuilder.append(chengyuzhushi.getDerivation());
        stringBuilder.append("\r\n例子:\n\t");
        stringBuilder.append(chengyuzhushi.getSample());
        return stringBuilder.toString();

    }

    private String getZiDian(String hanzi){
        if (hanzi.length()>1){
            return "目前只支持单字查询";
        }
        HanZiEntity hanZiEntity = hanZiService.getAllWithCondition(null, "hanzi='" + hanzi + "'", null);
        if (hanZiEntity!=null){
            return hanZiEntity.getData();
        }
        StringBuffer stringBuilder=new StringBuffer();
        GetZiDianPack ziDianPack = apiService.getZiDianPack(hanzi);
        if (ziDianPack!=null){
            return  "查询失败";
        }
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.append("\r\n\t");
        stringBuilder.append("新华字典");
        stringBuilder.append("\r\n\t");
        stringBuilder.append("你查询的是:");
        stringBuilder.append(hanzi);
        stringBuilder.append("\r\n\t拼音:");
        stringBuilder.append(ziDianPack.getPinyin());
        stringBuilder.append("\r\n\t笔画:");
        stringBuilder.append(ziDianPack.getBihua());
        stringBuilder.append("\r\n\t部首:");
        stringBuilder.append(ziDianPack.getBushou());
        stringBuilder.append("\r\n\t组词:");
        stringBuilder.append(ziDianPack.getWords());
        stringBuilder.append("\r\n\t基本解释:");
        List<String> basic_explain =(List<String>) ziDianPack.getBasic_explain();
        for (String basic: basic_explain) {
            stringBuilder.append("\r\n\t");
            stringBuilder.append(basic);
        }
        stringBuilder.append("\r\n\t输入‘详细解释’查看更多内容_例:详细解释");
        hanZiEntity=new HanZiEntity();
        hanZiEntity.setHanZi(ziDianPack.getHanzi());
        hanZiEntity.setPinYin(ziDianPack.getPinyin());
        hanZiEntity.setBuShou(ziDianPack.getBushou());
        hanZiEntity.setBihua(ziDianPack.getBihua());
        hanZiEntity.setWords(ziDianPack.getWords());
        hanZiEntity.setWuBi(ziDianPack.getWubi());
        hanZiEntity.setBasic_explain(ziDianPack.getBasic_explain().toString());
        hanZiEntity.setDetail_explain(ziDianPack.getDetail_explain().toString());
        hanZiEntity.setData(stringBuilder.toString());
        hanZiService.insertT(hanZiEntity);
        return  stringBuilder.toString();
    }

    private  String getZiDianDetail(String hanzi){
        StringBuffer stringBuffer=new StringBuffer();
        HanZiEntity hanZiEntity = hanZiService.getAllWithCondition(null, "hanzi='" + hanzi + "'", null);
        if (hanZiEntity!=null && !hanZiEntity.getDetail_explain().toString().isEmpty()){
            stringBuffer.append("\r\n\t");
            stringBuffer.append("新华字典");
            stringBuffer.append("\r\n\t");
            stringBuffer.append("你查询的是:");
            stringBuffer.append(hanzi);
            stringBuffer.append("\r\n\t");
            stringBuffer.append("详细解释:");

            String substring = hanZiEntity.getDetail_explain().substring(1, hanZiEntity.getDetail_explain().length() - 1);
            System.err.println(substring);
            List<String> list = Arrays.asList(substring.split(","));
            for (String str: list) {
                stringBuffer.append("\r\n\t");
                stringBuffer.append(str.substring(1,str.length()-1));
                System.err.println(stringBuffer.toString());
            }
            return stringBuffer.toString();
        }
        return "查询失败";
    }


}


