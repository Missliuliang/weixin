package com.vx.vipnc.weixin.service.base;

import com.vx.vipnc.weixin.bean.base.BaseEntity;
import com.vx.vipnc.weixin.mapper.base.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class BaseService<T extends BaseEntity > extends LoggerConfig {
    protected abstract BaseMapper getBaseMapper();

    protected abstract String getTableName();

    public List<T> getAllWithCondition(String selectStatement ,String condition ,String orderCondition
                                        ,int start ,int size){
        return getBaseMapper().getAllTWithCondition(selectStatement,getTableName(),condition,orderCondition,start,size);
    }

    public T getAllWithCondition(String selectStatement ,String condition ,String orderCondition ){
        try {
            List<T> list=getBaseMapper().getAllTWithCondition(selectStatement,getTableName(),condition,orderCondition,0,1);
            if (list !=null && !list.isEmpty()) return  list.get(0);
        }catch (Exception e){
            log.error("数据库搜索关键词" + e.getMessage());
            log.debug("数据库搜索关键词" + e.getMessage());
        }
        return null;

    }

    public boolean insertT(T t){
        try {
            log.error("insertT__" + t.toString());
            return getBaseMapper().insertT(t);
        }catch (Exception e){
            log.debug("insertT__" + t.toString() + "--message--" + e.getMessage());
            log.error("insertT__" + t.toString() + "--message--" + e.getMessage());
        }
        return false;
    }

    public boolean updateT(T t){
        try {
            log.error("updateT__" + t.toString());
            return getBaseMapper().updateT(t);
        }catch (Exception e){
            log.debug("updateT__" + t.toString() + "--message--" + e.getMessage());
            log.error("updateT__" + t.toString() + "--message--" + e.getMessage());
        }
        return false;
    }

    public boolean deleteT(T t){
        try {
            log.error("deleteT__" + t.toString());
            return getBaseMapper().deleteT(t);
        }catch (Exception e){
            log.debug("deleteT__" + t.toString() + "--message--" + e.getMessage());
            log.error("deleteT__" + t.toString() + "--message--" + e.getMessage());
        }
        return false;
    }

}
