package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.po.DYYPMachinePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DYYPMachinePOMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_machine
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_machine
     *
     * @mbg.generated
     */
    int insert(DYYPMachinePO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_machine
     *
     * @mbg.generated
     */
    int insertSelective(DYYPMachinePO record);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_machine
     *
     * @mbg.generated
     */
    DYYPMachinePO selectByPrimaryKey(Long sid);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_machine
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DYYPMachinePO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dy_yp_machine
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DYYPMachinePO record);
}