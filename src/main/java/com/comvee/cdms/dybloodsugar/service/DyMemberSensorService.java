package com.comvee.cdms.dybloodsugar.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dybloodsugar.dto.*;
import com.comvee.cdms.dybloodsugar.po.DYMemberSensorPO;
import com.comvee.cdms.dybloodsugar.po.DyBloodSugarInformPO;
import com.comvee.cdms.dybloodsugar.vo.*;

import java.util.List;

public interface DyMemberSensorService {
    /**
     * 绑定传感器
     * @param dto
     * @return
     */
    int bindSensor(BindSensorDTO dto);

    /**
     * 解绑传感器
     * @param dto
     * @return
     */
    int unBindSensor(UnBindSensorDTO dto);

    /**
     * 分页获取自己在用的传感器
     * @param dto
     * @return
     */
    PageResult<MySensorVO> pageSensorBySelf(PagerSensorDTO dto);

    /**
     * 绑定分享自己的传感器
     * @param dto
     * @return
     */
    int bindShowSensor(BindShowSensorDTO dto);

    /**
     * 解绑分享自己的传感器
     * @param dto
     * @return
     */
    void unBindShowSensor(UnBindShowSensorDTO dto);

    /**
     * 分页获取分享自己的传感器
     * @param dto
     * @return
     */
    PageResult<MyShowSensorVO> pageSensorByShow(PagerSensorOfShareDTO dto);

    /**
     * 生成分享二维码(公众号）
     * @param dto
     * @return
     */
    MemberSensorInfoQrCodeVO createQrCodeForShareSensor(ShareSensorDTO dto);

    /**
     * 根据id获取动态血糖绑定系信息
     * @param sid
     * @return
     */
    DYMemberSensorPO getSensorBySid(String sid);

    /**
     * app 获取动态血糖情况
     * @param dto
     * @return
     */
    DySensorStasticVO getSensorStatistics(ListMemberSensorDTO dto);

    /**
     * 加载动态血糖患者列表
     * @param dto
     * @param page
     * @return
     */
    PageResult<MemberSensorVO> listMemberSensor(ListMemberSensorDTO dto, PageRequest page);



    /**
     * 生成分享二维码（小程序）
     * @param dto
     * @return
     */
//    MemberSensorInfoQrCodeVO createForeverStrMiniQrCode(ShareSensorDTO dto) throws Exception;

    /**
     * 更新探头的开始时间
     * @param sensorNo
     */
    void updateSensorMonitorTimes(String sensorNo);

    String getMachineEqRemind(String machineEq);

    /**
     * 更新/添加扫描设备信息
     * @param machineNo
     * @param machineEq
     */
    //接口废弃
//    void updateOrInsertMachineInfo(String machineNo, String machineEq,String sensorNo);

    /**
     * 患者绑定数量
     * @param doctorId
     * @return
     */
    Long countMemberBindCount(String doctorId);

    /**
     * 统计住院患者绑定探头人数
     * @param departmentId
     * @return
     */
    Long countInHospitalBindSensorPatient(String departmentId);

    /**
     * 统计患者探头数量
     * @param memberId
     * @return
     */
    long countMemberSensor(String memberId);

    /**
     * 判断探头绑定状态
     * @param sensorNo
     * @return
     */
    JSONObject checkSensorBindStatus(String sensorNo ,String hospitalId);

    /**
     * 根据探头号获取绑定信息
     * @param sensorNo
     * @return
     */
    DYMemberSensorPO getMemberSensorBySensorNo(String sensorNo);

    /**
     * 根据sensorId获取分享对象
     * @param sensorId
     * @return
     */
    List<ShowSensorVO> listShowSensorBySensorSid(String sensorSid);

    /**
     * 判断探头与患者的绑定情况
     * @param sensorNo
     * @param memberId
     * @return
     */
    JSONObject checkSensorAndMemberBindStatus(String sensorNo ,String memberId);

    /**
     * 修改探头绑定
     * @param sensorNo
     * @param memberId
     * @return
     */
    void changeSensorBind(BindSensorDTO bindSensorDTO);


    /**
     * 根据sensorNo获取患者绑定探头的数据
     * @param sensorNo
     * @return
     */
    DYMemberSensorPO getDYMemberSensorPO(String sensorNo);

    /**
     * 获取动态血糖监测的总人数
     * @return
     */
    DynamicBloodSugarMonitorVO getCount(String doctorId,String hospitalId,String departmentId,Integer virtualWardAuthority);

    /**
     * 获取通知
     * @param latestDt
     * @return
     */
    List<DyBloodSugarInformPO> listLatestDyBloodSugarInform(String doctorId,String hospitalId,String departmentId,String latestDt);


    /**
     * 通过memberId查询患者绑定的探头数据
     * @param memberId
     * @return
     */
    List<DYMemberSensorPO> getDYMemberSensorPOByMemberId(String memberId);

    Object hasExpired(String sensorNo);
}
