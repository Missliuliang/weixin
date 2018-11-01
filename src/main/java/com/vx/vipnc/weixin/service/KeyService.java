package com.vx.vipnc.weixin.service;

import com.vx.vipnc.weixin.bean.HanZiEntity;
import com.vx.vipnc.weixin.bean.KeyEntity;
import com.vx.vipnc.weixin.bean.OperationRecordEntity;
import com.vx.vipnc.weixin.bean.UserEntity;
import com.vx.vipnc.weixin.bean.api.GetZiDianPack;
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
        if (keyEntity!=null){
            operationRecordService.insertT(recordEntity);
            return keyEntity.getContent();
        }
        OperationRecordEntity operation = operationRecordService.getAllWithCondition(null, null, "insertTime desc");
        if(operation!=null){
            if (Magic.APITYPE_ZIDIAN_ID.equals(operation.getType())){
                recordEntity.setType(Magic.APITYPE_ZIDIAN_ID);
                if (Magic.APITYPE_ZIDIAN_DETAIL.equals(content)){
                    return  getZiDianDetail(operation.getKeyWord()) ;
                }
                operationRecordService.insertT(recordEntity);
                return getZiDian(content) ;
            }else if(Magic.APITYPE_CHENGYU_ID.equals(operation.getType())){
                recordEntity.setType(Magic.APITYPE_CHENGYU_KEY_ID);
                operationRecordService.insertT(recordEntity);
                return null ;
            }
        }
        return  null ;
    }


    private String getChengyuzhushi(){

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


