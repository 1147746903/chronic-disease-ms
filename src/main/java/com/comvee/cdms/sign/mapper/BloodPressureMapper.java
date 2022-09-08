package com.comvee.cdms.sign.mapper;

import com.comvee.cdms.sign.dto.AddBloodPressureMapperDTO;
import com.comvee.cdms.sign.dto.CountBloodSugarDTO;
import com.comvee.cdms.sign.dto.GetBloodPressureDTO;
import com.comvee.cdms.sign.dto.ListBloodPressureDTO;
import com.comvee.cdms.sign.po.BloodPressurePO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
public interface BloodPressureMapper {

    /**
     * 加载血压列表
     * @param bloodPressureListMapperDTO
     * @return
     */
    List<BloodPressurePO> listBloodPressure(ListBloodPressureDTO bloodPressureListMapperDTO);

    List<BloodPressurePO> listBloodPressureByHospitalId(ListBloodPressureDTO bloodPressureListMapperDTO);

    //去重人
    List<String> listBloodPressureMemberList(ListBloodPressureDTO bloodPressureListMapperDTO);


    /**
     * 低压峰值 舒张压
     * @param countBloodSugarDTO
     */
    Map<String, Object> loadBloodPressureDbpMax(CountBloodSugarDTO countBloodSugarDTO);

    /**
     * 高压峰值 收缩压
     * @param countBloodSugarDTO
     */
    Map<String, Object> loadBloodPressureSbpMax(CountBloodSugarDTO countBloodSugarDTO);

    /**
     * 正常次数
     * @param countBloodSugarDTO
     */
    Integer loadBloodPressureNomal(CountBloodSugarDTO countBloodSugarDTO);

    /**
     * 偏高次数
     * @param countBloodSugarDTO
     */
    Integer loadBloodPressureHigh(CountBloodSugarDTO countBloodSugarDTO);

    /**
     * 偏低次数
     * @param countBloodSugarDTO
     */
    Integer loadBloodPressureLow(CountBloodSugarDTO countBloodSugarDTO);

    /**
     * 获取血压记录
     * @param param
     * @return
     */
    BloodPressurePO getBloodPressure(GetBloodPressureDTO param);

    /**
     * 新增血压记录
     * @param addBloodPressureMapperDTO
     */
    void addBloodPressure(AddBloodPressureMapperDTO addBloodPressureMapperDTO);


    //更新血压表心率
    void updateHeartRateForBP(@Param("sid") String sid, @Param("heartRate") String heartRate);

    /**
     * 血压达标率统计数据源
     * @param dto
     * @return
     */
    List<BloodPressurePO> listBloodPressureOfStatistics(GetStatisticsDTO dto);

    /**
     * 患者目前在院期间血压
     * @param memberId
     * @return
     */
    List<BloodPressurePO> listBloodPressureByMemberIdOfInHos(String memberId);

    /**
     * 获取患者最新血压记录
     * v5.0.0
     * @param memberId
     * @return
     */
    BloodPressurePO getNewBloodPressure(@Param("memberId") String memberId);

    /**
     * v6.0.0
     * 出院患者血压列表
     * @param dto
     * @return
     */
    List<BloodPressurePO> listBloodPressureOfStatisticsOutHos(GetStatisticsDTO dto);

}
