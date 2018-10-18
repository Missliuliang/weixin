package com.vx.vipnc.weixin.mapper.base;

import com.vx.vipnc.weixin.bean.base.BaseEntity;
import org.apache.ibatis.annotations.InsertProvider;

public interface BaseMapper<T extends BaseEntity> {

    //@InsertProvider(type = )
    boolean insertT();

}
