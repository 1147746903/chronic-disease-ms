package com.comvee.cdms.level.mapper;

import com.comvee.cdms.level.dto.*;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.vo.MemberLevelVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wyc
 * @date 2019/11/19 20:57
 */
public interface MemberLevelMapper {

    /**
     * 添加高血压分层分级
     * v5.0.0
     * @param levelPO
     */
    void addMemberLevel(MemberLevelPO levelPO);

    /**
     * 获取患者上次的分层分级
     * @param memberId
     * @return
     */
    MemberLevelPO getLastLevel(LastLevelDTO lastLevelDTO);

    /**
     * 加载患者的高血压分层分级列表
     * @param levelDTO
     * @return
     */
    List<MemberLevelPO> listMemberLevel(ListMemberLevelDTO levelDTO);

    //必须带上hospitalId或者hospitalIdList
    List<MemberLevelVO> listMemberLevelVO(ListMemberLevelDTO levelDTO);

    List<MemberLevelVO> listMemberNewstLevelVO(ListMemberLevelDTO levelDTO);

    //统计分层分级人数
    Long listMemberLevelNum(ListMemberLevelDTO levelDTO);

    /**
     * 忽略分层分级
     * @param sid
     */
    void ignoreMemberLevel(@Param("sid") String sid);


    /**
     * 忽略未确认分层分级
     * @param updateLevelDTO
     */
    void clearMemberLastUnConfirm(@Param("idList") List<String> idList);


    /**
     * 加载医生工作台分层分级提醒未确认列表
     * @param listMemberLevelDTO
     * @return
     */
    List<MemberLevelPO> listMemberLevelRemind(ListMemberLevelDTO listMemberLevelDTO);

    /**
     * 根据id查询分层分级详情
     * @param sid
     * @return
     */
    MemberLevelPO getMemberLevelById(@Param("sid") String sid);

    /**
     * 确定分层分级
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
    MemberLevelPO getCurrentGxyLevel(CurrentGxyLevelDTO  currentGxyLevelDTO);

    MemberLevelPO getMemberCurrentLevel(MemberLevelPO memberLevelPO);

    List<MemberLevelPO> listDayLevel(MemberLevelPO memberLevelPO);

    //上次的
    MemberLevelPO getMemberLast2Level(MemberLevelPO memberLevelPO);

    //建议（最新未确认的）
    MemberLevelPO getMemberSuggestLevel(MemberLevelPO memberLevelPO);

    /**
     * 修改分级分层
     * @param update
     */
    void updateMemberLevel(MemberLevelPO update);


}
