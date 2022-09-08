package com.comvee.cdms.sign.mapper;

import com.comvee.cdms.sign.dto.AddSignSuggestMapperDTO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import org.apache.ibatis.annotations.Param;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
public interface SignSuggestMapper {

    /**
     * 根据体征记录id获取建议
     * @param signId
     * @return
     */
    SignSuggestPO getSignSuggetBySignId(@Param("signId") String signId);

    /**
     * 新增建议
     * @param addSignSuggestMapperDTO
     */
    void addSignSuggest(AddSignSuggestMapperDTO addSignSuggestMapperDTO);
}
