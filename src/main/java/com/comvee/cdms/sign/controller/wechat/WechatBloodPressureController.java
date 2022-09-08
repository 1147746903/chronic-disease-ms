package com.comvee.cdms.sign.controller.wechat;

import com.comvee.cdms.checkresult.constant.CheckoutConstant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddBloodPressureDTO;
import com.comvee.cdms.sign.dto.AddBloodPressureServiceDTO;
import com.comvee.cdms.sign.dto.ListBloodPressureDTO;
import com.comvee.cdms.sign.dto.ListMemberBloodPressureDTO;
import com.comvee.cdms.sign.po.BloodPressurePO;
import com.comvee.cdms.sign.service.BloodPressureService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/2
 */
@RestController
@RequestMapping("/wechat/bloodPressure")
@RequiresUser
public class WechatBloodPressureController {

    @Autowired
    private BloodPressureService bloodPressureService;

    @RequestMapping("addBloodPressure")
    public Result addBloodPressure(AddBloodPressureDTO addBloodPressureDTO){
        MemberPO memberPO = SessionTool.getWechatSession();
        AddBloodPressureServiceDTO addBloodSugarServiceDTO = new AddBloodPressureServiceDTO();
        BeanUtils.copyProperties(addBloodSugarServiceDTO, addBloodPressureDTO);
        addBloodSugarServiceDTO.setMemberId(memberPO.getMemberId());
        addBloodSugarServiceDTO.setOperatorId(memberPO.getMemberId());
        addBloodSugarServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_MEMBER);
        if (null == addBloodPressureDTO.getOrigin()){
            addBloodPressureDTO.setOrigin(CheckoutConstant.RECORD_ORIGIN_PATIENT_WECHAT);
        }
        String sid = this.bloodPressureService.addBloodPressureForWechat(addBloodSugarServiceDTO);
        return new Result(sid);
    }


    @RequestMapping("addBloodPressureWithOrigin")
    public Result addBloodPressureWithOrigin(@Validated AddBloodPressureDTO addBloodPressureDTO){
        MemberPO memberPO = SessionTool.getWechatSession();
        AddBloodPressureServiceDTO addBloodSugarServiceDTO = new AddBloodPressureServiceDTO();
        BeanUtils.copyProperties(addBloodSugarServiceDTO, addBloodPressureDTO);
        addBloodSugarServiceDTO.setMemberId(memberPO.getMemberId());
        addBloodSugarServiceDTO.setOperatorId(memberPO.getMemberId());
        addBloodSugarServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_MEMBER);
        ValidateTool.checkParamIsNull(addBloodPressureDTO.getOrigin(), "origin");
        String sid = this.bloodPressureService.addBloodPressureForWechat(addBloodSugarServiceDTO);
        return new Result(sid);
    }
    /**
     * 加载患者bmi记录
     * @param
     * @return
     */
    @RequestMapping("listMemberBloodPressure")
    public Result listMemberBmi(ListMemberBloodPressureDTO listMemberBloodPressureDTO){
    	MemberPO memberPO = SessionTool.getWechatSession();
    	ListBloodPressureDTO listBloodPressureDTO  = new ListBloodPressureDTO();
        BeanUtils.copyProperties(listBloodPressureDTO, listMemberBloodPressureDTO );
        listBloodPressureDTO.setMemberId(memberPO.getMemberId());
        List<BloodPressurePO> list = this.bloodPressureService.listBloodPressure(listBloodPressureDTO);
        return new Result(list);
    }
}
