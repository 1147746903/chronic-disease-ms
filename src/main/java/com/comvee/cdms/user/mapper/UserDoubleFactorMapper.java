package com.comvee.cdms.user.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserDoubleFactorMapper {

    long countDoubleFactorUser(@Param("userName") String userName ,@Param("userType") Integer userType);
}
