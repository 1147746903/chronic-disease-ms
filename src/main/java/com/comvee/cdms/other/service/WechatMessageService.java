package com.comvee.cdms.other.service;

import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.other.bo.MessageHandlerResultBO;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.po.WechatMessagePO;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/30
 */
public interface WechatMessageService {

    /**
     * 新增微信消息
     * @param addWechatMessageDTO
     * @return
     */
    String addWechatMessage(AddWechatMessageDTO addWechatMessageDTO);

    /**
     * 加载微信消息
     * @param page
     * @param rows
     * @return
     */
    PageResult<WechatMessagePO> listWechatMessage(int page,int rows);

    /**
     * 更新微信消息处理结果
     * @param resultList
     */
    void updateMessageHandlerResult(List<MessageHandlerResultBO> resultList);

    /**
     * 移动数据
     */
    void moveMessageToHistory();

}
