package com.comvee.cdms.differentlevels.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.differentlevels.dto.ListDiffLevelsDTO;
import com.comvee.cdms.differentlevels.dto.ListDiffLevelsOfMemberDTO;
import com.comvee.cdms.differentlevels.dto.MemberDiffLevelSureDTO;
import com.comvee.cdms.differentlevels.po.MemberDifferentLevelsPOWithBLOBs;
import com.comvee.cdms.differentlevels.vo.DiffLevelsForMemberVO;
import com.comvee.cdms.differentlevels.vo.DifferentLevelsForWorkVO;
import com.comvee.cdms.differentlevels.vo.MemberCurrentDiffLevelVO;

import java.util.List;
import java.util.Map;

public interface DifferentLevelsService {
    /**
     * 获取分层分级的未读的记录
     * @return
     */
    int getDifferentLevelsLogOfUnRead(String hospitalId);

    /**
     * 确认未读分层分级，清除标记
     */
    void sureUnReadDifferentLevels(MemberDiffLevelSureDTO dto);

    /**
     * 分页获取工作台分层分级列表
     * @return
     */
    PageResult<DifferentLevelsForWorkVO> pagerDiffLevels(ListDiffLevelsDTO dto, PageRequest pager);

    /**
     * 获取患者的历史分级结果
     * @param dto
     * @return
     */
    DiffLevelsForMemberVO getDiffLevelsForMember(ListDiffLevelsOfMemberDTO dto);

    /**
     * 获取患者当前分层分级结果
     * @return
     */
    MemberCurrentDiffLevelVO getMemberCurrentDiffLevelResult(String memberId,String originId);

    /**
     * 添加
     * @param po
     */
    void insert(MemberDifferentLevelsPOWithBLOBs po);

    /**
     * 删除
     * @param sid
     */
    void delete(String sid);

    /**
     * 分层分级处理(批量处理和保存)
     * @return
     */
    void differentLevelsHandle(List<Map<String,Object>> archives);

    /**
     * 处理输出患者分层分级情况
     * @param memberArchives
     * @param memberQuesScore
     * @return
     */
    JSONObject outHxDifferentLevels(JSONObject memberArchives,
                                           List<Map<String, Object>> memberQuesScore);
}
