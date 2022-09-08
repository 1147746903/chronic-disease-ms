package com.comvee.cdms.professional.service;

import com.comvee.cdms.professional.po.ProfessionalPO;

import java.util.List;

/**
 * @author wyc
 * @date 2019/5/13 11:04
 */
public interface ProfessionalService {

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
     * 根据职称名称模糊查询
     * @param professionalName
     * @return
     */
    List<ProfessionalPO> queryPrefessionalByName(String professionalName);
}
