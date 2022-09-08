package com.comvee.cdms.follow.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.dto.AddFollowCustomDTO;
import com.comvee.cdms.follow.dto.ListFollowCustomDTO;
import com.comvee.cdms.follow.po.FollowCustomFormworkPO;
import com.comvee.cdms.follow.po.FollowCustomPO;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/4/15
 */
public interface FollowCustomService {

    /**
     * 新增自定义随访
     * @param followCustomPO
     */
    String addFollowCustom(AddFollowCustomDTO addFollowCustomDTO);

    /**
     * 根据随访id获取自定义随访
     * @param followId
     * @return
     */
    FollowCustomPO getFollowCustomById(String  followId);

    /**
     * 删除自定义随访
     * @param followId
     */
    void deleteFollowCustom(String followId);

    /**
     * 加载自定义处方列表
     * @param listFollowCustomDTO
     * @param pr
     * @return
     */
    PageResult<FollowCustomPO> listFollowCustom(ListFollowCustomDTO listFollowCustomDTO ,PageRequest pr);

    /**
     * 保存草稿
     * @param followId
     * @param dataJson
     */
    void saveDraft(String followId ,String dataJson);

    /**
     * 提交随访
     * @param followId
     * @param dataJson
     * @param fillPerson
     */
    void commitFollow(String followId ,String dataJson ,Integer fillPerson);

    /**
     * 加载自定义随访模板列表
     * @param doctorId
     * @return
     */
    List<FollowCustomFormworkPO> listFollowCustomFormwork(String doctorId);

    /**
     * 新增自定义随访模板
     * @param followCustomFormworkPO
     */
    String addFollowCustomFormwork(FollowCustomFormworkPO followCustomFormworkPO);

    /**
     * 修改自定义随访木板
     * @param followCustomFormworkPO
     */
    void updateFollowCustomFormwork(FollowCustomFormworkPO followCustomFormworkPO);

    /**
     * 加载自定义随访模板列表
     * @param doctorId
     * @return
     */
    PageResult<FollowCustomFormworkPO> listFollowCustomFormworkPage(String doctorId ,PageRequest pr ,String keyword);

    /**
     * 批量删除素芳模板
     * @param idList
     * @param doctorId
     */
    void batchDeleteFollowCustomFormwork(List<String> idList ,String doctorId);
}
