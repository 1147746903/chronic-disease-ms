package com.comvee.cdms.follow.mapper;

import com.comvee.cdms.follow.dto.FollowupSetDTO;
import com.comvee.cdms.follow.model.FollowSetDTO;
import com.comvee.cdms.follow.po.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @author wangxy
 *
 */
public interface FollowSetMapper {
    /**
     * 获取随访设置
     * @author wangxy
     * @date 2018年3月22日 上午9:51:22
     * @param followSetDTO
     * @return
     */
    FollowSetPO getFollowSetOne(FollowSetDTO followSetDTO);

    /**
     * 获取随访设置列表
     * @author wangxy
     * @date 2018年3月22日 上午9:51:22
     * @param followSetDTO
     * @return
     */
    List<FollowSetPO> listFollowSet(FollowSetDTO followSetDTO);

    /**
     * 获取随访详情
     * @author wangxy
     * @time 2018/03/08 09:00
     * @param sid
     * @return
     */
    FollowSetPO getFollowSetById(@Param("sid") String sid);

    /**
     * 添加随访设置
     * @author wangxy
     * @time 2018/03/08 09:00
     * @param follow
     */
    void insertFollowSet(FollowSetPO follow);

    /**
     * 修改随访设置
     * @author wangxy
     * @time 2018/03/08 09:00
     * @param follow
     */
    void modifyFollowSet(FollowSetPO follow);

    /** v4.0.4
     * 添加随访设置(新)
     * @param followupSetPO
     */
    void insertFollowupSet(FollowupSetPO followupSetPO);

    /**v4.0.4
     * 修改随访设置(新)
     * @param followupSetPO
     */
    void modifyFollowupSet(FollowupSetPO followupSetPO);

    /** v4.0.4
     * 获取随访设置(新)
     * @param followupSetDTO
     * @return
     */
    FollowupSetPO getFollowupSet(FollowupSetDTO followupSetDTO);

    /** v4.0.4
     * 获取随访设置列表(新)
     * @return
     */
    List<FollowupSetPO> listFollowupSet();
}
