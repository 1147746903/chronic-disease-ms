package com.comvee.cdms.follow.service;


import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.follow.dto.FollowupSetDTO;
import com.comvee.cdms.follow.mapper.FollowSetMapper;
import com.comvee.cdms.follow.model.FollowSetDTO;
import com.comvee.cdms.follow.po.*;
import com.comvee.cdms.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *
 * @author wangxy
 *
 */
@Service("followSetService")
public class FollowSetServiceImp implements FollowSetServiceI {

    @Autowired
    private FollowSetMapper followSetMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DoctorServiceI doctorService;

    @Override
    public FollowSetPO loadFollowSet(FollowSetDTO followSetDTO) {
        FollowSetDTO fDTO=new FollowSetDTO();
        fDTO.setDoctorId(followSetDTO.getDoctorId());
        fDTO.setMemberId(followSetDTO.getMemberId());
        fDTO.setSetUser("1");
        FollowSetPO followSetPO = this.followSetMapper.getFollowSetOne(fDTO);
        if(null!=followSetPO){
            return followSetPO;
        }else{
            fDTO=new FollowSetDTO();
            fDTO.setDoctorId(followSetDTO.getDoctorId());
            fDTO.setSetUser("2");
            return this.followSetMapper.getFollowSetOne(fDTO);
        }
    }

    @Override
    public FollowSetPO getFollowSetById(String sid) {
        return this.followSetMapper.getFollowSetById(sid);
    }

    @Override
    public void insertFollowSet(FollowSetPO follow) {
        follow.setSid(DaoHelper.getSeq());
        this.followSetMapper.insertFollowSet(follow);
    }

    @Override
    public void modifyFollowSet(FollowSetPO follow) {
        String mid="";
        //设置对象 1当前患者 2所有患者
        if("1".equals(follow.getSetUser())){
            mid = follow.getMemberId();
        }
        FollowSetDTO followSetDTO=new FollowSetDTO();
        followSetDTO.setDoctorId(follow.getDoctorId());
        followSetDTO.setMemberId(mid);
        followSetDTO.setSetUser(follow.getSetUser());

        FollowSetPO followSetPO = this.followSetMapper.getFollowSetOne(followSetDTO);
        if(null!=followSetPO){
            follow.setSid(followSetPO.getSid());
            this.followSetMapper.modifyFollowSet(follow);
        }else{
            follow.setSid(DaoHelper.getSeq());
            this.followSetMapper.insertFollowSet(follow);
        }
    }

    @Override
    public FollowupSetPO getFollowupSet(FollowupSetDTO followupSetDTO) {
        return this.followSetMapper.getFollowupSet(followupSetDTO);
    }

    @Override
    public void modifyFollowupSet(FollowupSetPO follow) {
        FollowupSetDTO followupSetDTO = new FollowupSetDTO();
        followupSetDTO.setMemberId(follow.getMemberId());
        followupSetDTO.setDoctorId(follow.getDoctorId());
        FollowupSetPO followupSet = this.followSetMapper.getFollowupSet(followupSetDTO);
        if (null != followupSet){
            follow.setSid(followupSet.getSid());
            this.followSetMapper.modifyFollowupSet(follow);
        }else{
            follow.setSid(DaoHelper.getSeq());
            this.followSetMapper.insertFollowupSet(follow);
        }
    }

    @Override
    public List<FollowupSetPO> listFollowupSet() {
        return this.followSetMapper.listFollowupSet();
    }
}
