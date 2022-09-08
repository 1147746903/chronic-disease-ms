package com.comvee.cdms.hospital.service;

import com.comvee.cdms.hospital.model.po.HospitalPO;

import java.util.List;

/**
 * @author wyc
 * @date 2019/12/23 17:17
 */
public interface HospitalRelatedService {


    /** v5.1.1
     * 查询对应等级的医院列表
     * @param queryType 1查询上级  2查询下级
     * @param hospitalId
     * @return
     */
    List<HospitalPO> listRelatedHospital(Integer queryType,String hospitalId);

}
