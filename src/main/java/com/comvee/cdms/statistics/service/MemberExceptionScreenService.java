package com.comvee.cdms.statistics.service;

import com.comvee.cdms.statistics.vo.ListMemberExceptionVO;

import java.util.List;
import java.util.Map;

/**
 * @Author linr
 * @Date 2022/2/18
 */
public interface MemberExceptionScreenService {


    /**
     * 获取患者异常图表信息
     * @param hospitalId
     * @return
     */
    Map<String,Object> loadMemberExceptionDayList(String hospitalId);


    /**
     * 患者异常信息入库处理
     */
    void memberExceptionInfoHandler();
}
