package com.comvee.cdms.hospital.mapper;

import com.comvee.cdms.hospital.model.po.HospitalPO;
import com.comvee.cdms.hospital.model.po.HospitalRelatedPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wyc
 * @date 2019/12/23 15:21
 */
public interface HospitalRelatedMapper {

    /** v5.1.1
     * 添加医院关联表
     * @param hospitalRelatedPO
     */
    void addHospitalRelated(HospitalRelatedPO hospitalRelatedPO);

    /**
     * v5.1.1
     * 删除上,下级关联医院
     * @param hospitalId
     */
    void deleteHospitalRelated(@Param("hospitalId") String hospitalId);


    /** v5.1.1
     * 查询对应等级的医院列表
     * @param queryType 1查询上级  2查询下级
     * @param hospitalId
     * @return
     */
    List<HospitalPO> listRelatedHospital(@Param("queryType") Integer queryType,@Param("hospitalId") String hospitalId);

    /**
     * 加载医院关联的下级医院id
     * @param hospitalId
     * @return
     */
    List<String> listDownHospitalId(@Param("hospitalId") String hospitalId);
}
