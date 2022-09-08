package com.comvee.cdms.sign.mapper;

import com.comvee.cdms.sign.dto.ListHbalcDTO;
import com.comvee.cdms.sign.po.Hba1cPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Hba1cMapper {

    /**
     * 添加糖化记录
     * @param hba1cPO
     */
    void addHba1c(Hba1cPO hba1cPO);

    /**
     * 加载患者的糖化记录
     * @param memberId
     * @param startDt
     * @param endDt
     * @return
     */
    List<Hba1cPO> listMemberHba1c(@Param("memberId") String memberId ,@Param("startDt")String startDt ,@Param("endDt")String endDt);

    List<String> listHba1cMemberIdsByDto(ListHbalcDTO listHbalcDTO);

    /**
     * 获取糖化记录
     * @param sid
     * @return
     */
    Hba1cPO getHba1cById(@Param("sid")String sid);

    /**
     * 获取患者最新的糖化记录
     * @param memberId
     * @return
     */
    Hba1cPO getNewHba1c(@Param("memberId") String memberId);
}
