package com.comvee.cdms.remind.service.impl;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.remind.dto.AddMemberRemindDTO;
import com.comvee.cdms.remind.dto.ListMemberRemindMapperDTO;
import com.comvee.cdms.remind.dto.UpdateMemberRemindLatestDTO;
import com.comvee.cdms.remind.mapper.MemberRemindMapper;
import com.comvee.cdms.remind.po.MemberRemindLatestPO;
import com.comvee.cdms.remind.po.MemberRemindPO;
import com.comvee.cdms.remind.service.MemberRemindService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/7
 */
@Service("memberRemindService")
public class MemberRemindServiceImpl implements MemberRemindService {

    @Autowired
    private MemberRemindMapper memberRemindMapper;

    @Override
    public MemberRemindLatestPO getMemberRemindLatest(String memberId) {
        return this.memberRemindMapper.getMemberRemindLatest(memberId);
    }

    @Override
    public PageResult<MemberRemindPO> listMemberRemind(ListMemberRemindMapperDTO listMemberRemindMapperDTO, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<MemberRemindPO> list = this.memberRemindMapper.listMemberRemind(listMemberRemindMapperDTO);
        return new PageResult<>(list);
    }

    @Override
    public String addMemberRemind(AddMemberRemindDTO addMemberRemindDTO) {
        MemberRemindPO memberRemindPO = new MemberRemindPO();
        BeanUtils.copyProperties(memberRemindPO, addMemberRemindDTO);
        memberRemindPO.setSid(DaoHelper.getSeq());
        memberRemindPO.setSendTimestamp(String.valueOf(System.currentTimeMillis()));
        memberRemindPO.setUpdateTimestamp(String.valueOf(System.currentTimeMillis()));
        this.memberRemindMapper.addMemberRemind(memberRemindPO);
        refreshMemberRemindLatest(addMemberRemindDTO);
        return memberRemindPO.getSid();
    }

    @Override
    public void clearUnRead(String memberId) {
        UpdateMemberRemindLatestDTO updateMemberRemindLatestDTO = new UpdateMemberRemindLatestDTO();
        updateMemberRemindLatestDTO.setMemberId(memberId);
        updateMemberRemindLatestDTO.setResetUnReadNum(0);
        this.memberRemindMapper.updateMemberRemindLatest(updateMemberRemindLatestDTO);;
    }

    /**
     * 刷新患者提醒最新消息
     * @param addMemberRemindDTO
     */
    private void refreshMemberRemindLatest(AddMemberRemindDTO addMemberRemindDTO){
        MemberRemindLatestPO memberRemindLatestPO = getMemberRemindLatest(addMemberRemindDTO.getMemberId());
        if(memberRemindLatestPO == null){
            memberRemindLatestPO = new MemberRemindLatestPO();
            memberRemindLatestPO.setSid(DaoHelper.getSeq());
            memberRemindLatestPO.setTimeStamp(String.valueOf(System.currentTimeMillis()));
            memberRemindLatestPO.setMemberId(addMemberRemindDTO.getMemberId());
            memberRemindLatestPO.setRemindMessage(addMemberRemindDTO.getRemindMessage());
            memberRemindLatestPO.setUnReadNum(1);
            this.memberRemindMapper.addMemberRemindLatest(memberRemindLatestPO);
        }else{
            UpdateMemberRemindLatestDTO updateMemberRemindLatestDTO = new UpdateMemberRemindLatestDTO();
            updateMemberRemindLatestDTO.setMemberId(addMemberRemindDTO.getMemberId());
            updateMemberRemindLatestDTO.setRemindMessage(addMemberRemindDTO.getRemindMessage());
            updateMemberRemindLatestDTO.setUnReadNum(1);
            updateMemberRemindLatestDTO.setTimeStamp(String.valueOf(System.currentTimeMillis()));
            this.memberRemindMapper.updateMemberRemindLatest(updateMemberRemindLatestDTO);
        }
    }
}
