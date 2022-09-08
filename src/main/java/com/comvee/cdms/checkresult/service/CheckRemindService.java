package com.comvee.cdms.checkresult.service;

import com.comvee.cdms.checkresult.dto.ModifyCheckRemindDTO;
import com.comvee.cdms.checkresult.dto.ResolveCheckoutRemindDTO;
import com.comvee.cdms.checkresult.dto.ResolveInspectionRemindDTO;
import com.comvee.cdms.checkresult.po.CheckRemindPO;
import com.comvee.cdms.checkresult.vo.CheckRemindVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.checkresult.dto.ListMemberCheckRemindDTO;

import java.util.List;
import java.util.Map;

public interface CheckRemindService {

    PageResult<CheckRemindVO> listDoctorCheckRemind(String hospitalId, String start, String end, PageRequest pageRequest);

    void modifyCheckRemind(ModifyCheckRemindDTO dto);

    /**
     * 加载需要检查提醒通知的患者
     * @param pr
     * @param dayDiff
     * @return
     */
    PageResult<String> listNeedCheckRemindMember(PageRequest pr, String reviewDt);

    List<CheckRemindPO> listMemberCheckRemind(ListMemberCheckRemindDTO dto);

    /**
     * 处理检验项目的检查提醒
     * @param dto
     */
    Map<String,Object> resolveCheckoutRemind(List<ResolveCheckoutRemindDTO> list);

    /**
     * 处理检查项目的检查提醒
     * @param list
     */
    void resolveInspectionRemind(ResolveInspectionRemindDTO dto);
}
