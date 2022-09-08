package com.comvee.cdms.dybloodsugar.mapper;


import com.comvee.cdms.dybloodsugar.po.DyBloodSugarRemarkPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DyBloodSugarRemarkMapper {

    void deleteyBloodSugarRemarkById(@Param("sid") String sid);

    void addDyBloodSugarRemark(DyBloodSugarRemarkPO record);

    List<DyBloodSugarRemarkPO> listDyBloodSugarRemarkByBid(@Param("bid") String bid);

    List<DyBloodSugarRemarkPO> listDyBloodSugarRemarkByBidList(@Param("bidList") List<String> bidList ,@Param("origin") Integer origin);

    void updateBloodSugarRemarkById(@Param("content") String content,@Param("sid") String sid);

    DyBloodSugarRemarkPO getBloodSugarRemarkById(@Param("sid") String sid);
}