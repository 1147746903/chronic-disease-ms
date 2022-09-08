package com.comvee.cdms.follow.service;


import com.comvee.cdms.follow.dto.FollowupSetDTO;
import com.comvee.cdms.follow.model.FollowSetDTO;

import com.comvee.cdms.follow.po.FollowSetPO;
import com.comvee.cdms.follow.po.FollowupSetPO;


import java.util.List;


/**
 * 
 * @author wangxy
 *
 */
public interface FollowSetServiceI {
    /**
     * 获取随访设置
     * @author wangxy
     * @date 2018年3月22日 上午9:51:22
     * @param followSetDTO
     * @return
     */
    FollowSetPO loadFollowSet(FollowSetDTO followSetDTO);

    /**
     * 获取随访详情
     * @author wangxy
     * @time 2018/03/08 09:00
     * @param sid
     * @return
     */
    FollowSetPO getFollowSetById(String sid);

    /**
     * 添加随访设置
     * @author wangxy
     * @time 2018/03/08 09:00
     * @param follow
     */
    void insertFollowSet(FollowSetPO follow);

    /** v4.0.4
     * 修改随访设置
     * @author wangxy
     * @time 2018/03/08 09:00
     * @param follow
     */
    void modifyFollowSet(FollowSetPO follow);

    /**v4.0.4
     * 获取随访设置(新)
     * @param followupSetDTO
     * @return
     */
    FollowupSetPO getFollowupSet(FollowupSetDTO followupSetDTO);

    /**v4.0.4
     * 修改随访设置(新)
     * @author wangxy
     * @time 2018/03/08 09:00
     * @param follow
     */
    void modifyFollowupSet(FollowupSetPO follow);

    /**v4.0.4
     * 获取随访设置列表(新)
     * @return
     */
    List<FollowupSetPO> listFollowupSet();

}
