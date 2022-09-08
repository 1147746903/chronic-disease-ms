package com.comvee.cdms.sign.service;

import com.comvee.cdms.sign.dto.AddSignSuggestDTO;
import com.comvee.cdms.sign.po.SignSuggestPO;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
public interface SignSuggestService {

    /**
     * 根据体征记录id获取建议
     * @param signId
     * @return
     */
    SignSuggestPO getSignSuggetBySignId(String signId);

    /**
     * 新增建议
     * @param addSignSuggestDTO
     */
    String addSignSuggest(AddSignSuggestDTO addSignSuggestDTO);
}
