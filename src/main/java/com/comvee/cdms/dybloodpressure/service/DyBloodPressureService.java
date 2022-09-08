package com.comvee.cdms.dybloodpressure.service;


import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dybloodpressure.bo.DyBloodPressureDataBO;
import com.comvee.cdms.dybloodpressure.dto.*;
import com.comvee.cdms.dybloodpressure.po.DyBloodPressureReportPO;
import com.comvee.cdms.dybloodpressure.vo.DyBloodPressureDiaryVO;

import java.util.List;
import java.util.Map;

/**
 * @Author linr
 * @Date 2021/10/19
 */
public interface DyBloodPressureService {

    /**
     * 添加动态血压
     * @param
     */
    void addDyBloodPressure(BatchAddDYBloodPressureDTO batchAddDYBloodPressureDTO);

    /***
     * 获取动态血压指标解析数据
     * @param getDynBloodPressureDataDTO
     * @return
     */
    Map<String,Object> getDynBloodPressureData(GetDynBloodPressureDataDTO getDynBloodPressureDataDTO);

    /**
     * 血压数据
     * @param getDynBloodPressureDataDTO
     * @param page
     * @return
     */
    Map<String,Object> listDayBloodPressurePage(GetDynBloodPressureDataDTO getDynBloodPressureDataDTO);

    /**
     * 添加日记
     * @param addDyBloodPressureDiaryDTO
     */
    void addUpdateBpDiary(AddDyBloodPressureDiaryDTO addDyBloodPressureDiaryDTO, Integer origin);


    /**
     * 获取日记
     * @param getDyBloodPressureDiaryDTO
     * @return
     */
    DyBloodPressureDiaryVO getDyBloodPressureDiaryPO(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO);



    /**
     * 添加报告
     * @param addDyBloodPressureReportDTO
     */
    void addUpdateBpReport(AddDyBloodPressureReportDTO addDyBloodPressureReportDTO);

    /**
     * 获取报告
     * @param getDyBloodPressureDiaryDTO
     * @return
     */
    DyBloodPressureReportPO getDyBloodPressureReportPO(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO);



    PageResult pageDayBloodPressureList(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO, PageRequest pr);


    boolean showBloodPressureByDate(String memberId,String startDt,String endDt);



}
