package com.comvee.cdms.actiontrack.core;

public interface SessionSupport {

    /**
     * 获取用户身份
     * @param sessionObject
     * @return
     */
    Integer getIdentify(Object sessionObject);

    /**
     * 获取用户id
     * @param sessionObject
     * @return
     */
    String getUid(Object sessionObject);
}
