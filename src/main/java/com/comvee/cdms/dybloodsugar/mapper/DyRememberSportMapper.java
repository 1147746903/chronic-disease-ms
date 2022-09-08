package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.dto.DyRememberSportDTO;
import com.comvee.cdms.dybloodsugar.po.DyRememberSportPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DyRememberSportMapper {
    /**
     * 点击保存,保存运动记录
     * @param dyRememberSportPO
     */
    void insertSportRemember(DyRememberSportPO dyRememberSportPO);

    /**
     * 数据回填
     * @param sid
     */
    DyRememberSportPO getDySportRememberValues(@Param("sid") String sid);
    /**
     * 查询医生记录患者的饮食列表数据
     * @return
     */
    List<DyRememberSportPO> getDySportRememberPOList(DyRememberSportDTO dyRememberSportDTO);

    /**
     * 根据主键id修改is_valid字段为无效
     * @param sid
     */
    void deleteSportRememberBySid(@Param("sid") String sid);

    /**
     * 获取当前运动记录
     */
    DyRememberSportPO getDySportRememberPO(DyRememberSportDTO dto);

    /**
     * 修改运动记录
     * @param dyRememberSportPO
     */
    void updateDySportRemember(DyRememberSportPO dyRememberSportPO);

    /**
     * 根据sid修改数据
     */
    void updateDySportRememberBySid(DyRememberSportPO dyRememberSportPO);

    List<String> listDySportRememberDtForWX(DyRememberSportDTO dto);
}
