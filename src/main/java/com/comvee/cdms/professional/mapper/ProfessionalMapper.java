package com.comvee.cdms.professional.mapper;

import com.comvee.cdms.professional.po.ProfessionalPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wyc
 * @date 2019/5/13 11:04
 */
public interface ProfessionalMapper {
    /**
     * 新增职称
     * @param professionalPO
     */
    void addProfessional(ProfessionalPO professionalPO);

    /**
     * 加载职称列表
     * @return
     */
    List<ProfessionalPO> listPrefessional();

    /**
     * 根据职称名称查询信息
     * @param professionalName
     * @return
     */
    ProfessionalPO getPrefessionalByName(@Param("professionalName") String professionalName);

    /**
     * 根据职称名称模糊查询
     * @param professionalName
     * @return
     */
    List<ProfessionalPO> queryPrefessionalByName(@Param("professionalName") String professionalName);
}
