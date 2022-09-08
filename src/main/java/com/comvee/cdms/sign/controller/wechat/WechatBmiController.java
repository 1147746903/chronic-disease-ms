package com.comvee.cdms.sign.controller.wechat;

import com.comvee.cdms.checkresult.constant.CheckoutConstant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddBmiDTO;
import com.comvee.cdms.sign.dto.AddBmiServiceDTO;
import com.comvee.cdms.sign.dto.ListBmiDTO;
import com.comvee.cdms.sign.dto.ListMemberBmiDTO;
import com.comvee.cdms.sign.po.BmiPO;
import com.comvee.cdms.sign.service.BmiService;
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
@RequestMapping("/wechat/bmi")
@RequiresUser
public class WechatBmiController {

    @Autowired
    private BmiService bmiService;

    /**
     * 新增bmi
     * @param addBmiDTO
     * @return
     */
    @RequestMapping("addBmi")
    public Result addBmi(@Validated AddBmiDTO addBmiDTO){
        MemberPO memberPO = SessionTool.getWechatSession();
        AddBmiServiceDTO addBmiServiceDTO = new AddBmiServiceDTO();
        BeanUtils.copyProperties(addBmiServiceDTO, addBmiDTO);
        addBmiServiceDTO.setMemberId(memberPO.getMemberId());
        addBmiServiceDTO.setOperatorId(memberPO.getMemberId());
        addBmiServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_MEMBER);
        if (null == addBmiServiceDTO.getOrigin()){
            addBmiServiceDTO.setOrigin(CheckoutConstant.RECORD_ORIGIN_PATIENT_WECHAT);
        }
        String id = this.bmiService.addBmi(addBmiServiceDTO);
        return new Result(id);
    }

    /**
     * 加载患者bmi记录
     * @param listMemberBmiDTO
     * @return
     */
    @RequestMapping("listMemberBmi")
    public Result listMemberBmi(@Validated ListMemberBmiDTO listMemberBmiDTO){
    	MemberPO memberPO = SessionTool.getWechatSession();
        ListBmiDTO listBmiDTO = new ListBmiDTO();
        BeanUtils.copyProperties(listBmiDTO, listMemberBmiDTO );
        listBmiDTO.setMemberId(memberPO.getMemberId());
        List<BmiPO> list = this.bmiService.listBmi(listBmiDTO);
        return new Result(list);
    }
}
