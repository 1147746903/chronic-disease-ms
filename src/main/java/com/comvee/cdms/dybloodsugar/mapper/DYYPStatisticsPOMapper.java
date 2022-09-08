package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.dto.DYYPStatisticsDTO;
import com.comvee.cdms.dybloodsugar.po.DYYPStatisticsPO;
import com.comvee.cdms.dybloodsugar.po.DYYPStatisticsPOWithBLOBs;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DYYPStatisticsPOMapper {


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_statistics
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_statistics
     *
     * @mbg.generated
     */
    int insert(DYYPStatisticsPOWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_statistics
     *
     * @mbg.generated
     */
    int insertSelective(DYYPStatisticsPOWithBLOBs record);



    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_statistics
     *
     * @mbg.generated
     */
    DYYPStatisticsPOWithBLOBs selectByPrimaryKey(String sid);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_statistics
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DYYPStatisticsPOWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_statistics
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(DYYPStatisticsPOWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_statistics
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DYYPStatisticsPO record);

    long hasDynamicBloodSugarSensorStatistics(GetStatisticsDTO gto);

    List<DYYPStatisticsPOWithBLOBs> listDYYPStatisticsPOWithBLOBs(DYYPStatisticsDTO dto);

    DYYPStatisticsPO getDYYPStatisticsBySid(String sid);
}