package com.vx.vipnc.weixin.service;

import com.vx.vipnc.weixin.bean.OperationRecordEntity;
import com.vx.vipnc.weixin.mapper.OperationRecordMapper;
import com.vx.vipnc.weixin.mapper.base.BaseMapper;
import com.vx.vipnc.weixin.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationRecordService extends BaseService<OperationRecordEntity> {

    @Autowired
    private OperationRecordMapper operationRecordMapper;
    @Override
    protected BaseMapper getBaseMapper() {
        return operationRecordMapper;
    }

    @Override
    protected String getTableName() {
        return "operation_record";
    }


}
