package com.vx.vipnc.weixin.service;

import com.vx.vipnc.weixin.bean.HanZiEntity;
import com.vx.vipnc.weixin.mapper.HanziMapper;
import com.vx.vipnc.weixin.mapper.base.BaseMapper;
import com.vx.vipnc.weixin.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HanZiService extends BaseService<HanZiEntity> {

    @Autowired
    private HanziMapper hanziMapper;
    @Override
    protected BaseMapper getBaseMapper() {
        return hanziMapper;
    }

    @Override
    protected String getTableName() {
        return "hanzi";
    }
}
