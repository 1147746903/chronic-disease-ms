package com.comvee.cdms.drugs.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.drugs.dto.DrugsDepotDTO;
import com.comvee.cdms.drugs.dto.ListDrugsDepotDTO;
import com.comvee.cdms.drugs.dto.ListDrugsMemberDTO;
import com.comvee.cdms.drugs.po.DrugsDepotPO;
import com.comvee.cdms.drugs.po.DrugsMemberPO;

import java.util.List;

/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public interface DrugsMemberService {

    /**
     * 加载药品列表
     * @param listDrugsDepotDTO
     * @return
     */
    List<DrugsDepotPO> listDrugsDepot(ListDrugsDepotDTO listDrugsDepotDTO);

    /**
     * 加载药品列表
     * @param listDrugsDepotDTO
     * @return
     */
    PageResult<DrugsDepotPO> listDrugsDepotPageByHosForDefault(ListDrugsDepotDTO listDrugsDepotDTO, PageRequest page);


    /**
     * 获取药品
     * @param sid
     * @return
     */
    DrugsDepotPO getDrugsDepot(String sid);


    /**
     * 新增药品记录-医院
     * @param drugsDepotPO
     * @return
     */
    String addDrugsDepotOfHospital(DrugsDepotDTO dto);

    /**
     * 新增药品记录-南京
     * @param drugsDrportJson
     */
    void addDrugsDeportNj(String drugsDrportJson,String hospitalId);

}
