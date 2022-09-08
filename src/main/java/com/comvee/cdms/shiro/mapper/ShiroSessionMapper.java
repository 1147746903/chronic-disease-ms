package com.comvee.cdms.shiro.mapper;

import com.comvee.cdms.shiro.model.ShiroSessionPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShiroSessionMapper {

    /**
     * 获取session
     * @param sessionId
     * @return
     */
    ShiroSessionPO getShiroSession(@Param("sessionId") String sessionId);

    /**
     * 新增session
     * @param shiroSessionPO
     */
    void addShiroSession(ShiroSessionPO shiroSessionPO);

    /**
     * 修改session
     * @param shiroSessionPO
     */
    void updateShiroSession(ShiroSessionPO shiroSessionPO);

    /**
     * 删除session
     * @param sessionId
     */
    void deleteShiroSession(@Param("sessionId") String sessionId);

    /**
     * 加载过期session
     * @return
     */
    List<ShiroSessionPO> listTimeoutSession();
}
