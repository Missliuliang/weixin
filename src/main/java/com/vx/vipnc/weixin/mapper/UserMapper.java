package com.vx.vipnc.weixin.mapper;

import com.vx.vipnc.weixin.bean.UserEntity;
import com.vx.vipnc.weixin.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface UserMapper extends BaseMapper<UserEntity> {

    @Select("select * from ${tableName} where openId = #{openId} ")
    UserEntity getUserByOpenId(@Param("tableName") String  tableName,@Param("openId") String openId);


}
