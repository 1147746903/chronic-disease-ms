package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.dto.DyRememberPharmacyDTO;
import com.comvee.cdms.dybloodsugar.po.DyRememberPharmacyPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DyRememberPharmacyMapper {


    /**
     * 根据传过来的探头序列号和操作者id查看表中是否有这一条数据.
     * @return
     */
    DyRememberPharmacyPO getDyPharmacyRememberPO(DyRememberPharmacyDTO dto);
    /**
     * 存储用药记录
     * @param dyRememberPharmacyPO
     */
    void addDyPharmacyRemember(DyRememberPharmacyPO dyRememberPharmacyPO);

    /**
     * 修改用药记录
     * @param dyRememberPharmacyPO
     */
    void updateDyPharmacyRemember(DyRememberPharmacyPO dyRememberPharmacyPO);

    /**
     * 修改用药记录
     */
    void updateDyPharmacyRememberBySid(DyRememberPharmacyPO dyPharmacyRemember);

    /**
     * 用药记录根据主键id删除选中的记录.
     * @param sid
     */
    void deletePharmacyRemember(@Param("sid") String sid);

    /**
     * 用药记录数据回填
     * @param sid
     */
    DyRememberPharmacyPO getDyPharmacyRememberValues(@Param("sid") String sid);

    /**
     * 查询医生记录患者的用药列表数据
     * @return
     */
    List<DyRememberPharmacyPO> getDyPharmacyRememberPOList(DyRememberPharmacyDTO dyRememberPharmacyDTO);



}
