package com.comvee.cdms.sign.controller.wechat;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddBloodSugarDTO;
import com.comvee.cdms.sign.dto.AddBloodSugarServiceDTO;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/11/2
 */
@RestController
@RequestMapping("/wechat/bloodSugar")
@RequiresUser
public class WechatBloodSugarController {

    @Autowired
    private BloodSugarService bloodSugarService;

    /**
     * 新增血糖
     * @param addBloodSugarDTO
     * @return
     */
    @RequestMapping("addBloodSguar")
    public Result addBloodSguar(@Validated AddBloodSugarDTO addBloodSugarDTO){
        MemberPO memberPO = SessionTool.getWechatSession();
        AddBloodSugarServiceDTO addBloodSugarServiceDTO = new AddBloodSugarServiceDTO();
        BeanUtils.copyProperties(addBloodSugarServiceDTO, addBloodSugarDTO);
        addBloodSugarServiceDTO.setMemberId(memberPO.getMemberId());
        addBloodSugarServiceDTO.setOperatorId(memberPO.getMemberId());
        addBloodSugarServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_MEMBER);
        addBloodSugarServiceDTO.setOrigin(SignConstant.ORIGIN_MINI_PROGRAM);
        String id = this.bloodSugarService.addBloodSugar(addBloodSugarServiceDTO);
        return new Result(id);
    }

    /**
     * 加载血糖记录
     * @param listBloodSugarDTO
     * @return
     */
    @RequestMapping("listBloodSugar")
    public Result listBloodSugar(ListBloodSugarDTO listBloodSugarDTO) {
        MemberPO memberPO = SessionTool.getWechatSession();
        listBloodSugarDTO.setMemberId(memberPO.getMemberId());
        List<BloodSugarPO> list = this.bloodSugarService.listBloodSugar(listBloodSugarDTO);
        return new Result(list);
    }

    /**
     * 获取一周平均空腹血糖   所有餐后血糖
     * @param memberId
     * @return
     */
    @RequestMapping("/getAvgWeekBlood")
    public Result getAvgWeekBlood(String memberId){
        ValidateTool.checkParamIsNull(memberId, "memberId");
        Map<String, Object> map = this.bloodSugarService.getAvgWeekBlood(memberId);
        return Result.ok(map);
    }
}
