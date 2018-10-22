package com.vx.vipnc.weixin.mapper.base;

import com.vx.vipnc.weixin.bean.base.BaseEntity;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

public interface BaseMapper<T extends BaseEntity> {

    @InsertProvider(type =BaseProvider.class , method = "insertT")
    boolean insertT(BaseEntity baseEntity);

    @UpdateProvider(type = BaseProvider.class ,method = "updateByPrimaryKey")
    boolean updateT(BaseEntity baseEntity);

    @DeleteProvider(type = BaseProvider.class ,method = "deleteT")
    boolean deleteT(BaseEntity baseEntity);

    @SelectProvider(type = BaseProvider.class ,method = "getAllTWithCondition")
    List<T> getAllTWithCondition(String selectStatement ,String tableName ,String condition,
                                 String orderCondition,Integer start ,Integer size );

}
