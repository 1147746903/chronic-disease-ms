package com.comvee.cdms.sign.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.sign.dto.AddHba1cDTO;
import com.comvee.cdms.sign.dto.AddSuggestDTO;
import com.comvee.cdms.sign.dto.ListHbalcDTO;
import com.comvee.cdms.sign.po.Hba1cPO;
import com.comvee.cdms.sign.po.SignSuggestPO;

import java.util.List;

public interface Hba1cService {

    /**
     * 添加糖化记录
     * @param addHba1cDTO
     * @return
     */
    String addHba1c(AddHba1cDTO addHba1cDTO);

    /**
     * 加载患者糖化列表
     * @param memberId
     * @param startDt
     * @param endDt
     * @return
     */
    List<Hba1cPO> listMemberHba1c(String memberId ,String startDt ,String endDt);

    /**
     * 分页加载患者糖化列表
     * @param pr
     * @param memberId
     * @return
     */
    PageResult<Hba1cPO> listMemberHba1c(PageRequest pr ,String memberId);

    /**
     * 获取最新的糖化记录
     * @param memberId
     * @return
     */
    Hba1cPO getLatestHba1c(String memberId);

    /** v4.0.4
     * 加载患者糖化列表
     * @return
     */
    List<Hba1cPO> listMemberHba1cByParam(ListHbalcDTO listHbalcDTO);

    /**
     * 根据主键获取糖化记录
     * @param sid
     * @return
     */
    Hba1cPO getHba1cById(String sid);

    /**
     * 新增糖化血红蛋白建议
     * v5.1.6
     * @param addSuggestDTO
     */
    String addHba1cSugarSuggest(AddSuggestDTO addSuggestDTO);

    /**
     *  获取糖化血红蛋白建议
     * v5.1.6
     * @param signId
     * @return
     */
    SignSuggestPO getHba1cSuggest(String signId);

    /**
     * 添加糖化记录入库  synToCheckout 是否同步检验表
     * @param hba1cPO
     */
    void addHba1cMapper(Hba1cPO hba1cPO,String hospitalId,String hospitalName,Integer synToCheckout);
}
