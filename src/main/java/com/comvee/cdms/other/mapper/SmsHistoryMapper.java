package com.comvee.cdms.other.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface SmsHistoryMapper {

    void addSmsHistory(@Param("map") Map<String ,Object> param);
}
