package com.comvee.cdms.other.service.impl;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.other.bo.MessageHandlerResultBO;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.mapper.WechatMessageMapper;
import com.comvee.cdms.other.po.WechatMessagePO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/30
 */
@Service("wechatMessage")
public class WechatMessageServiceImpl implements WechatMessageService {

    @Autowired
    private WechatMessageMapper wechatMessageMapper;

    @Override
    public String addWechatMessage(AddWechatMessageDTO addWechatMessageDTO) {
        //设置默认用户类型，默认患者类型
        if(addWechatMessageDTO.getUserType() == null){
            addWechatMessageDTO.setUserType(WechatMessageConstant.USER_TYPE_PATIENT);
        }
        WechatMessagePO wechatMessagePO = new WechatMessagePO();
        BeanUtils.copyProperties(wechatMessagePO, addWechatMessageDTO);
        String sid = DaoHelper.getSeq();
        wechatMessagePO.setSid(sid);
        this.wechatMessageMapper.addWechatMessage(wechatMessagePO);
        return sid;
    }

    @Override
    public PageResult<WechatMessagePO> listWechatMessage(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<WechatMessagePO> list = this.wechatMessageMapper.listWechatMessage();
        return new PageResult<>(list);
    }

    @Override
    public void updateMessageHandlerResult(List<MessageHandlerResultBO> resultList) {
        this.wechatMessageMapper.updateMessageHandlerResult(resultList);
    }

    @Override
    public void moveMessageToHistory() {

        this.wechatMessageMapper.insertMessageHistory();
        this.wechatMessageMapper.deleteMessage();

    }
}
