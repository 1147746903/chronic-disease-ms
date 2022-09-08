package com.comvee.cdms.follow.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.cfg.FollowupCareConstant;
import com.comvee.cdms.follow.mapper.FollowupCareMapper;
import com.comvee.cdms.follow.po.FollowupCarePO;
import com.comvee.cdms.member.dto.ListDoctorMemberDTO;
import com.comvee.cdms.member.po.DoctorMemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/7/24
 */
@Service("followupCareService")
public class FollowupCareServiceImpl implements FollowupCareService {

    @Autowired
    private FollowupCareMapper followupCareMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Override
    public String addFollowupCare(FollowupCarePO followupCarePO) {
        String sid = DaoHelper.getSeq();
        followupCarePO.setSid(sid);
        //如果下发对象为全部患者则患者id修改为-1
        if(followupCarePO.getReceiveType().intValue() == FollowupCareConstant.RECEIVE_TYPE_ALL){
            followupCarePO.setMemberId(Constant.DEFAULT_FOREIGN_ID);
        }
        boolean todayFollowCare = todaySendDateCheck(followupCarePO);
        this.followupCareMapper.addFollowupCare(followupCarePO);
        if(todayFollowCare){
            sendFollowupCareToWechat(followupCarePO);
        }
        return sid;
    }

    @Override
    public void updateFollowupCare(FollowupCarePO followupCarePO) {
        boolean todayFollowCare = todaySendDateCheck(followupCarePO);
        this.followupCareMapper.updateFollowupCare(followupCarePO);
        FollowupCarePO followupCare = getFollowupCareById(followupCarePO.getSid());
        if(todayFollowCare){
            sendFollowupCareToWechat(followupCare);
        }

    }

    @Override
    public void deleteFollowupCare(String sid) {
        FollowupCarePO followupCarePO = new FollowupCarePO();
        followupCarePO.setValid(0);
        followupCarePO.setSid(sid);
        this.followupCareMapper.updateFollowupCare(followupCarePO);
    }

    @Override
    public PageResult<FollowupCarePO> listMemberFollowupCare(PageRequest pr, String memberId, String doctorId) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<FollowupCarePO> list = this.followupCareMapper.listMemberFollowupCare(memberId ,doctorId);
        return new PageResult<>(list);
    }

    @Override
    public void sendFollowupCareToWechat(FollowupCarePO followupCarePO) {
        if(followupCarePO.getReceiveType().intValue() == FollowupCareConstant.RECEIVE_TYPE_ONLY){
            addFollowupCareWechatMessage(followupCarePO , followupCarePO.getMemberId());
        }else if(followupCarePO.getReceiveType().intValue() == FollowupCareConstant.RECEIVE_TYPE_ALL){
            ListDoctorMemberDTO listDoctorMemberDTO = new ListDoctorMemberDTO();
            listDoctorMemberDTO.setDoctorId(followupCarePO.getDoctorId());
            List<DoctorMemberPO> list = this.memberService.listDoctorMember(listDoctorMemberDTO);
            if(list == null || list.size() == 0){
                return;
            }
            list.forEach(x ->{
                addFollowupCareWechatMessage(followupCarePO ,x.getMemberId());
            });
        }
    }

    @Override
    public PageResult<FollowupCarePO> listToBeSendFollowupCare(int page, int rows) {
        PageHelper.startPage(page ,rows);
        List<FollowupCarePO> list = this.followupCareMapper.listToBeSendFollowupCare();
        return new PageResult<>(list);
    }

    @Override
    public FollowupCarePO getFollowupCareById(String sid) {
        return this.followupCareMapper.getFollowupCareById(sid);
    }

    /**
     * 添加关怀微信消息
     * @param memberId
     */
    private void addFollowupCareWechatMessage(FollowupCarePO followupCarePO,String memberId){
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setForeignId(followupCarePO.getSid());
        addWechatMessageDTO.setMemberId(memberId);
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_FOLLOWUP_CARE);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doctorId" ,followupCarePO.getDoctorId());
        jsonObject.put("careContent" ,followupCarePO.getCareContent());
        jsonObject.put("senderInfo" ,followupCarePO.getSenderInfo());
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    /**
     * 判断下发日期是否为当天
     * @param sendDate
     */
    private boolean todaySendDateCheck(FollowupCarePO followupCarePO){
        if(StringUtils.isBlank(followupCarePO.getSendDate())){
            return false;
        }
        String today = DateHelper.getToday();
        if(today.equals(followupCarePO.getSendDate())){
            followupCarePO.setSendStatus(1);
            return true;
        }else{
            followupCarePO.setSendStatus(0);
            return false;
        }

    }

}
