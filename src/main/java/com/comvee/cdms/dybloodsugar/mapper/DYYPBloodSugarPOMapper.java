package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.dto.DyBloodSugarDTO;
import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DYYPBloodSugarPOMapper {


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_blood_sugar
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_blood_sugar
     *
     * @mbg.generated
     */
    int insert(DYYPBloodSugarPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_blood_sugar
     *
     * @mbg.generated
     */
    int insertSelective(DYYPBloodSugarPO record);



    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_blood_sugar
     *
     * @mbg.generated
     */
    DYYPBloodSugarPO selectByPrimaryKey(Long sid);




    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_blood_sugar
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DYYPBloodSugarPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_blood_sugar
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DYYPBloodSugarPO record);

    /**
     * 时间点
     * @param startDt
     * @param endDt
     * @param sn
     * @return
     */
    List<String> listMemberTimeOfRecordLog(@Param("startDt") String startDt,
                                           @Param("endDt") String endDt,
                                           @Param("sensorNo") String sn);

    /**
     * 数据源
     * @param time
     * @param startDt
     * @param endDt
     * @param sensorNo
     * @return
     */
    List<DYYPBloodSugarPO> listDataSourceOfYPParamLogOfTimeASC(@Param("time") String time,
                                                             @Param("startDt") String startDt,
                                                             @Param("endDt") String endDt,
                                                             @Param("sensorNo") String sensorNo);

    /**
     * 获取时间范围内满数据数量
     * @param startDt
     * @param endDt
     * @param sn
     * @return
     */
    Long getFullDataOfDayCount(@Param("startDt") String startDt,
                               @Param("endDt") String endDt,
                               @Param("sensorNo") String sensorNo);

    /**
     * 获取患者最新的动态血糖记录
     * @param memberId
     * @param sensorNo
     * @return
     */
    DYYPBloodSugarPO getLatestDyBloodSugar(@Param("sensorNo") String sensorNo);


    /**
     * 获取当前探头的动态血糖记录
     * @param sensorNo
     * @return
     */
    DYYPBloodSugarPO getDyBloodSugar(String sensorNo);

    /**
     * 获取当前范围内所有的探头血糖值
     * @param sensorNo
     * @param startTime
     * @param endTime
     * @return
     */
    List<DYYPBloodSugarPO> getDyBloodSugarList(@Param("sensorNo") String sensorNo,@Param("startTime")String startTime,@Param("endTime")String endTime);

    DYYPBloodSugarPO getDyBloodSugarBySid(String bid);

    /**
     * 获取IMei最新传输数据的一次时间
     * @param imei
     * @return
     */
    DYYPBloodSugarPO getMachineNoLastUseDt(@Param("imei") String imei);

    List<DYYPBloodSugarPO> listBloodSugarByInsertDt(@Param("sensorNo") String sensorNo,@Param("startTime")String startTime,@Param("endTime")String endTime);

    List<DYYPBloodSugarPO> getDataSimulationList(@Param("sensorNo") String sensorNo,@Param("startDt")String startDt,@Param("endDt")String endDt);

    List<String> listBloodSugarRecordDt(@Param("sensorNo") String sensorNo);

    String getFirstBloodSugarRecordTime(String sensorNo);

    List<DYYPBloodSugarPO> listBloodSugar(DyBloodSugarDTO dto);
}