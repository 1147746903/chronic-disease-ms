package com.comvee.cdms.follow.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.cfg.FollowCustomConstant;
import com.comvee.cdms.follow.dto.AddFollowCustomDTO;
import com.comvee.cdms.follow.dto.ListFollowCustomDTO;
import com.comvee.cdms.follow.mapper.FollowCustomMapper;
import com.comvee.cdms.follow.po.FollowCustomFormworkPO;
import com.comvee.cdms.follow.po.FollowCustomPO;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/4/15
 */
@Service("followCustomService")
public class FollowCustomServiceImpl implements FollowCustomService{

    @Autowired
    private FollowCustomMapper followCustomMapper;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Override
    public String addFollowCustom(AddFollowCustomDTO addFollowCustomDTO) {
        String followId = DaoHelper.getSeq();
        FollowCustomPO followCustomPO = new FollowCustomPO();
        BeanUtils.copyProperties(followCustomPO ,addFollowCustomDTO);
        followCustomPO.setFollowId(followId);
        this.followCustomMapper.addFollowCustom(followCustomPO);
        addFollowCustomWechatMessageHandler(followCustomPO);
        return followId;
    }

    /**
     * 新增自定义随访- 微信模板提醒
     * @param followCustomPO
     */
    private void addFollowCustomWechatMessageHandler(FollowCustomPO followCustomPO){
        //非患者填写的不提醒
        if(followCustomPO.getFillPerson() != FollowCustomConstant.FILL_PERSON_PATIENT){
            return;
        }
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setForeignId(followCustomPO.getFollowId());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_NEW_CUSTOM_FOLLOW);
        addWechatMessageDTO.setMemberId(followCustomPO.getMemberId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("followName" ,followCustomPO.getFollowName());
        jsonObject.put("followId" ,followCustomPO.getFollowId());
        jsonObject.put("doctorId" ,followCustomPO.getDoctorId());
        jsonObject.put("date" ,DateHelper.getToday());
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    @Override
    public FollowCustomPO getFollowCustomById(String followId) {
        return this.followCustomMapper.getFollowCustomById(followId);
    }

    @Override
    public void deleteFollowCustom(String followId) {
        FollowCustomPO followCustomPO = getFollowCustomById(followId);
        if(followCustomPO == null){
            throw new BusinessException("该随访不存在或已被删除");
        }
        if(FollowCustomConstant.FOLLOW_STATUS_FINISH == followCustomPO.getFollowStatus()){
            throw new BusinessException("已提交的随访不可删除");
        }
        this.followCustomMapper.deleteFollowCustom(followId);
    }

    @Override
    public PageResult<FollowCustomPO> listFollowCustom(ListFollowCustomDTO listFollowCustomDTO, PageRequest pr) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<FollowCustomPO> list = this.followCustomMapper.listFollowCustom(listFollowCustomDTO);
        return new PageResult<>(list);
    }

    @Override
    public void saveDraft(String followId, String dataJson) {
        FollowCustomPO followCustomPO = new FollowCustomPO();
        followCustomPO.setFollowId(followId);
        followCustomPO.setDataJson(dataJson);
        followCustomPO.setFollowStatus(FollowCustomConstant.FOLLOW_STATUS_DRAFT);
        this.followCustomMapper.updateFollowCustom(followCustomPO);
    }

    @Override
    public void commitFollow(String followId, String dataJson, Integer fillPerson) {
        FollowCustomPO followCustomPO = new FollowCustomPO();
        followCustomPO.setFollowId(followId);
        followCustomPO.setDataJson(dataJson);
        followCustomPO.setFollowStatus(FollowCustomConstant.FOLLOW_STATUS_FINISH);
        followCustomPO.setFinishPerson(fillPerson);
        followCustomPO.setFillDt(DateHelper.getNowDate());
        this.followCustomMapper.updateFollowCustom(followCustomPO);
    }

    @Override
    public List<FollowCustomFormworkPO> listFollowCustomFormwork(String doctorId) {
        return this.followCustomMapper.listFollowCustomFormwork(doctorId ,null);
    }

    @Override
    public String addFollowCustomFormwork(FollowCustomFormworkPO followCustomFormworkPO) {
        String sid = DaoHelper.getSeq();
        followCustomFormworkPO.setSid(sid);
        this.followCustomMapper.addFollowCustomFormwork(followCustomFormworkPO);
        return sid;
    }

    @Override
    public void updateFollowCustomFormwork(FollowCustomFormworkPO followCustomFormworkPO) {
        this.followCustomMapper.updateFollowCustomFormwork(followCustomFormworkPO);
    }

    @Override
    public PageResult<FollowCustomFormworkPO> listFollowCustomFormworkPage(String doctorId, PageRequest pr ,String keyword) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<FollowCustomFormworkPO> list = this.followCustomMapper.listFollowCustomFormwork(doctorId ,keyword);
        return new PageResult<>(list);
    }

    @Override
    public void batchDeleteFollowCustomFormwork(List<String> idList, String doctorId) {
        this.followCustomMapper.batchDeleteFollowCustomFormwork(idList ,doctorId);
    }


}
