package com.comvee.cdms.dialogue.mapper;

import com.comvee.cdms.dialogue.dto.ListReplyTextDTO;
import com.comvee.cdms.dialogue.po.ReplyTextPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wyc
 * @date 2019/5/23 14:04
 */
public interface DoctorReplyTextMapper {
    /**
     * 加载医生回复文案列表
     * @param doctorId
     * @param hospitalId
     * @param departmentId
     * @return
     */
    List<ReplyTextPO> listReplyText(ListReplyTextDTO listReplyTextDTO);
    /**
     * 添加医生自定义回复文案
     * @param replyTextPO
     */
    void addReplyText(ReplyTextPO replyTextPO);

    /**
     * 修改医生自定义回复文案
     * @param replyTextPO
     */
    void modifyReplyText(ReplyTextPO replyTextPO);

    /**
     * 删除医生自定义回复文案
     * @param replyTextPO
     */
    void delReplayText(@Param("sid") String sid);

    /**
     * 根据医生自定义回复文案id获取文案信息
     * @param sid
     * @return
     */
    ReplyTextPO getReplyTextById(@Param("sid") String sid);
}
