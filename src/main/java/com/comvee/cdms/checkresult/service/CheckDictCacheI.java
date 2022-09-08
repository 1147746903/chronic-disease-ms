package com.comvee.cdms.checkresult.service;

import java.util.List;
import java.util.Map;

public interface CheckDictCacheI {
    /**
     * 特别关注检验编码列表
     * @param hospitalId
     * @return
     */
    List<String> IMPORTANT_CODES(String hospitalId);

    /**
     * 特别关注检验编码映射
     * @param hospitalId
     * @return
     */
    Map<String,String> TYPE_MAP(String hospitalId);

    /**
     * 检验编码映射
     * @param hospitalId
     * @return
     */
    Map<String, String> C_TYPE_MAP(String hospitalId);

    String HBA1C_CODE(String hospitalId);

    String LDLC_CODE(String hospitalId);
}
