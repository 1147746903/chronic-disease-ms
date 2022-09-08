package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.po.DyBloodSugarInformPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DyBloodSugarInformMapper {

    /**
     * 将探头传过来的数据存入数据通知表
     * @param dyBloodSugarInformPo
     */
    void addWebBloodSugar(DyBloodSugarInformPO dyBloodSugarInformPo);


    /**
     * 获取患者最新的动态血糖记录
     * @return
     */
    List<DyBloodSugarInformPO> listLatestDyBloodSugarInform(@Param("latestDt") String latestDt, @Param("listMemberId") List<String> listMemberId);

    DyBloodSugarInformPO getInfoData(@Param("memberId") String memberId);

    void updateInfo(DyBloodSugarInformPO dyBloodSugarInformPo);
}
