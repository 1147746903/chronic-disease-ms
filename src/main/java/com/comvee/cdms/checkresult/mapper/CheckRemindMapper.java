package com.comvee.cdms.checkresult.mapper;

import com.comvee.cdms.checkresult.dto.GetMemberCheckRemindDTO;
import com.comvee.cdms.checkresult.dto.ListMemberCheckRemindDTO;
import com.comvee.cdms.checkresult.po.CheckRemindPO;
import com.comvee.cdms.checkresult.vo.CheckRemindVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/7/5
 */
public interface CheckRemindMapper {

    /**
     * 添加检查提醒
     * @param checkRemindPO
     */
    void addCheckRemind(CheckRemindPO checkRemindPO);

    /**
     * 修改检查提醒
     * @param checkRemindPO
     */
    void updateCheckRemind(CheckRemindPO checkRemindPO);


    List<CheckRemindVO> listDoctorCheckRemind(@Param("hospitalId") String hospitalId, @Param("startDt") String startDt, @Param("endDt") String endDt);

    /**
     * 获取患者检查提醒
     * @param idCard
     * @param reviewDt
     * @param checkItem
     * @return
     */
    CheckRemindPO getMemberCheckRemind(GetMemberCheckRemindDTO dto);

    /**
     * 加载需要检查提醒的患者
     * @param dayDiff
     * @return
     */
    List<String> listNeedCheckRemindMember(@Param("reviewDt") String reviewDt);


    List<CheckRemindPO> listMemberCheckRemind(ListMemberCheckRemindDTO dto);
}
