package com.comvee.cdms.follow.mapper;

import com.comvee.cdms.follow.dto.ListFollowCustomDTO;
import com.comvee.cdms.follow.po.FollowCustomFormworkPO;
import com.comvee.cdms.follow.po.FollowCustomPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/4/15
 */
public interface FollowCustomMapper {

    /**
     * 新增自定义随访
     * @param followCustomPO
     */
    void addFollowCustom(FollowCustomPO followCustomPO);

    /**
     * 根据随访id获取自定义随访
     * @param followId
     * @return
     */
    FollowCustomPO getFollowCustomById(@Param("followId")String  followId);

    /**
     * 修改自定义随访
     * @param followCustomPO
     */
    void updateFollowCustom(FollowCustomPO followCustomPO);

    /**
     * 删除自定义随访
     * @param followId
     */
    void deleteFollowCustom(@Param("followId")String  followId);

    /**
     * 加载自定义处方列表
     * @param listFollowCustomDTO
     * @return
     */
    List<FollowCustomPO> listFollowCustom(ListFollowCustomDTO listFollowCustomDTO);

    /**
     * 加载自定义随访模板列表
     * @param doctorId
     * @return
     */
    List<FollowCustomFormworkPO> listFollowCustomFormwork(@Param("doctorId") String doctorId ,@Param("keyword") String keyword);

    /**
     * 新增自定义随访模板
     * @param followCustomFormworkPO
     */
    void addFollowCustomFormwork(FollowCustomFormworkPO followCustomFormworkPO);

    /**
     * 修改自定义随访木板
     * @param followCustomFormworkPO
     */
    void updateFollowCustomFormwork(FollowCustomFormworkPO followCustomFormworkPO);

    /**
     * 批量删除自定义随访模板
     * @param idList
     * @param doctorId
     */
    void batchDeleteFollowCustomFormwork(@Param("idList") List<String> idList,@Param("doctorId") String doctorId);
}
