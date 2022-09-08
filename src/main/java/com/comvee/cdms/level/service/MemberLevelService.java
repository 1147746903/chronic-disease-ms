package com.comvee.cdms.level.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.level.bo.MemberCurrentLeverBO;
import com.comvee.cdms.level.dto.ConfirmLevelDTO;
import com.comvee.cdms.level.dto.CurrentGxyLevelDTO;
import com.comvee.cdms.level.dto.LastLevelDTO;
import com.comvee.cdms.level.dto.ListMemberLevelDTO;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.vo.MemberLevelVO;

import java.util.List;
import java.util.Map;


/**
 * @author wyc
 * @date 2019/11/19 20:58
 */
public interface MemberLevelService {
    /**
     * 加载患者的高血压分层分级列表
     * @param levelDTO
     * @return
     */
    List<MemberLevelPO> listMemberLevel(ListMemberLevelDTO levelDTO);

    PageResult<MemberLevelVO> pagerMemberLevel(ListMemberLevelDTO levelDTO,PageRequest page);

    Map<String, Object> loadMemberLevelStatics(List<String> hospitalIdList);

    Map<String,Object> listMemberLevelHistory(String memberId,Integer levelType,String hospitalId,PageRequest pageRequest);

    /**
     * 添加分层分级
     * @param levelPO
     */
    String addMemberLevel(MemberLevelPO levelPO);

    /**
     * 加载医生工作台分层分级提醒未确认列表
     * @param listMemberLevelDTO
     * @return
     */
    PageResult<MemberLevelVO> listMemberLevelRemind(ListMemberLevelDTO listMemberLevelDTO, PageRequest page);

    /**
     * 根据id查询分层分级详情
     * @param sid
     * @return
     */
    MemberLevelPO getMemberLevelById(String sid);

    /**
     * 确认分层分级
     * @param confirmLevelDTO
     */
    void confirmMemberLevel(ConfirmLevelDTO confirmLevelDTO);

    /**
     * 查询分层分级提醒(未确认)次数
     * @param levelDTO
     * @return
     */
    long countMemberLevelRemind(ListMemberLevelDTO levelDTO);

    /**
     * 获取患者当前的高血压分层分级
     * @return
     */
    MemberLevelPO getCurrentGxyLevel(CurrentGxyLevelDTO currentGxyLevelDTO);

    /**
     * 获取患者上次的分层分级
     * @param memberId
     * @return
     */
    MemberLevelPO getLastLevel(LastLevelDTO lastLevelDTO);


    /**
     * 添加高血压分层分级
     * @param levelPO
     */
    void addHypertensionLevel(MemberLevelPO levelPO);

    /**
     * 修改分级分层
     * @param update
     */
    void updateMemberLevel(MemberLevelPO update);

    /**
     * 获取患者当前分层分级
     */
    MemberCurrentLeverBO getMemberCurrentLevel(String memberId);


    Map<String, MemberLevelPO> getMemberLeverMap(ListMemberLevelDTO levelDTO);
}
