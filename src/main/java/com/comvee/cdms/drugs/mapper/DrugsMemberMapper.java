package com.comvee.cdms.drugs.mapper;

import com.comvee.cdms.drugs.dto.DrugsDTO;
import com.comvee.cdms.drugs.dto.ListDrugsDepotDTO;
import com.comvee.cdms.drugs.dto.ListDrugsMemberDTO;
import com.comvee.cdms.drugs.po.DrugsDepotPO;
import com.comvee.cdms.drugs.po.DrugsMemberPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public interface DrugsMemberMapper {

    /**
     * 加载药品库列表
     * @param listDrugsDepotDTO
     * @return
     */
    List<DrugsDepotPO> listDrugsDepot(ListDrugsDepotDTO listDrugsDepotDTO);

    /**
     * 获取药品
     * @param sid
     * @return
     */
    DrugsDepotPO getDrugsDepot(@Param("id") String id);

    /**
     * 新增药品记录
     * @param drugsDepotPO
     */
    void addDrugsDepot(DrugsDepotPO drugsDepotPO);

    /**
     * 根据参数获取药品
     * @param drugsDTO
     * @return
     */
    DrugsDepotPO getDrugsDepotByParam(DrugsDTO drugsDTO);


}
