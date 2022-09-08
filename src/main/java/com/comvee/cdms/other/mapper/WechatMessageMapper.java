package com.comvee.cdms.other.mapper;

import com.comvee.cdms.other.bo.MessageHandlerResultBO;
import com.comvee.cdms.other.po.WechatMessagePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/30
 */
public interface WechatMessageMapper {

    /**
     * 新增微信消息模板
     * @param wechatMessagePO
     */
    void addWechatMessage(WechatMessagePO wechatMessagePO);

    /**
     * 加载微信消息
     * @return
     */
    List<WechatMessagePO> listWechatMessage();

    /**
     * 更新微信消息处理结果
     * @param resultList
     */
    void updateMessageHandlerResult(@Param("resultList") List<MessageHandlerResultBO> resultList);

    /**
     * 移动历史数据
     */
    void insertMessageHistory();

    /**
     * 删除已经移动的数据
     */
    void deleteMessage();
}
