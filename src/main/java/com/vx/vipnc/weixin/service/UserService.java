package com.vx.vipnc.weixin.service;

import com.vx.vipnc.weixin.bean.UserEntity;
import com.vx.vipnc.weixin.mapper.UserMapper;
import com.vx.vipnc.weixin.mapper.base.BaseMapper;
import com.vx.vipnc.weixin.service.base.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService  extends BaseService<UserEntity> {

    private  final  static Logger log= LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;


    @Override
    protected BaseMapper getBaseMapper() {
        return userMapper;
    }

    @Override
    protected String getTableName() {
        return "user";
    }

    public UserEntity getUserByOpenId(String openId){
        return  userMapper.getUserByOpenId(getTableName(),openId);
    }
}
