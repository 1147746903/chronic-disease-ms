package com.comvee.cdms.dybloodsugar.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.dybloodsugar.dto.DyBloodSugarRemarkDTO;
import com.comvee.cdms.dybloodsugar.dto.DyStaticsDTO;
import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import com.comvee.cdms.dybloodsugar.po.DyBloodSugarRemarkPO;

import java.util.List;

public interface DyBloodSugarService {
    /**
     * 血糖数据处理入库
     * @param uploadLogMap
     */
    void bloodSugarHandle(JSONObject uploadLogMap);

    /**
     * 血糖记录列表(asc排序)
     * @param startDt
     * @param endDt
     * @param sensorNo
     * @return
     */
    List<DYYPBloodSugarPO> listDataSourceOfYPParamLogOfOBDTASC(String startDt, String endDt, String sensorNo);

    /**
     * 血糖记录列表(asc排序)
     * @param startDt
     * @param endDt
     * @param sensorNo
     * @return
     */
    List<DYYPBloodSugarPO> listDataWechatSourceOfYPParamLogOfOBDTASC(String startDt, String endDt, String sensorNo);


    /**
     * 血糖记录列表(asc排序)
     * @param dateList
     * @param sensorNo
     * @return
     */
    List<DYYPBloodSugarPO> listDataSourceOfYPParamLogOfOBDTASC(List<String> dateList, String sensorNo);

    /**
     * 时间点
     * @param startDt
     * @param endDt
     * @param sensorNo
     * @return
     */
    List<String> listMemberTimeOfRecordLog(String startDt, String endDt, String sensorNo);

    /**
     * 数据源（asc排序）
     * @param time
     * @param startDt
     * @param endDt
     * @param sensorNo
     * @return
     */
    List<DYYPBloodSugarPO> listDataSourceOfYPParamLogOfTimeASC(String time, String startDt, String endDt, String sensorNo);

    /**
     * 获取时间范围内满数据数量
     * @param startDt
     * @param endDt
     * @param sensorNo
     * @return
     */
    Long getFullDataOfDayCount(String startDt, String endDt, String sensorNo);

    /**
     * 获取探头所有的血糖数据-record_time-asc排序
     * @param sensorNo
     * @return
     */
    List<DYYPBloodSugarPO> listDYYPBloodSugarByNoASC(String sensorNo);

    /**
     * 添加备注
     * @param dto
     * @return
     */
    String addBloodSugarRemark(DyBloodSugarRemarkDTO dto);

    /**
     * 删除备注
     * @param id
     * @return
     */
    void deleteBloodSugarRemarkById(String id);

    /**
     * 加载备注
     * @param bid
     * @return
     */
    List<DyBloodSugarRemarkPO> listBloodSugarRemarkByBid(String bid);

    /**
     * 获取最新的动态血糖记录
     * @param memberId
     * @param SensorNo
     * @return
     */
    DYYPBloodSugarPO getLatestDyBloodSugar(String sensorNo);

    /**
     * 上传动态血糖后处理
     * @param sensorNo
     */
    void uploadDynamicBloodSugarHandler(String sensorNo ,Long lastUpTimestamp, String machineEqRemind);

    /**
     * 获取当前探头的动态血糖记录
     * @param sensorNo
     * @return
     */
    DYYPBloodSugarPO getDyBloodSugar(String sensorNo);

    /**
     * 获取当前探头的动态血糖记录
     * @param sensorNo
     * @return
     */
    List<DYYPBloodSugarPO> getDyBloodSugarList(String sensorNo,String startTime,String endTime);

    /**
     * 获取探头的血糖记录的备注数据
     * @param bid
     * @return
     */
    List<DyBloodSugarRemarkPO> listBloodSugarRemarkBySensorNo(String sensorNo,String startTime,String endTime ,Integer origin);

    String addBloodSugarRememberRemark(DyBloodSugarRemarkDTO dto,String sid);

    DYYPBloodSugarPO getRemarkByBid(String bid);

    /**
     * 上传动态血糖通知
     * @param sensorNo
     * @param machineNo
     * @param machineEq
     */
    void uploadDynamicBloodSugarInform(String sensorNo ,String machineNo ,String machineEq);

    List<DYYPBloodSugarPO> getDataSimulationList(DyStaticsDTO dto);

    List<String> listBloodSugarRecordDt(String sensorNo);

    String getFirstBloodSugarRecordTime(String sensorNo);
}
