package com.comvee.cdms.sign.controller.wechat;

import com.comvee.cdms.checkresult.constant.CheckoutConstant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddHba1cDTO;
import com.comvee.cdms.sign.dto.ListHbalcDTO;
import com.comvee.cdms.sign.dto.ListMemberHbalcDTO;
import com.comvee.cdms.sign.po.Hba1cPO;
import com.comvee.cdms.sign.service.Hba1cService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/wechat/hba1c")
@RestController
public class WechatHba1cController {

    @Autowired
    private Hba1cService hba1cService;

    /**
     * 添加糖化
     * @param addHba1cDTO
     * @return
     * http://192.168.7.120:9080/intelligent-prescription/wechat/hba1c/addHba1c.do?memberId=&recordValue=&recordDt=&remark=
     */
    @RequestMapping("addHba1c")
    public Result addHba1c(@Validated AddHba1cDTO addHba1cDTO){
        if(StringUtils.isBlank(addHba1cDTO.getMemberId())){
            MemberPO memberPO = SessionTool.getWechatSession();
            addHba1cDTO.setMemberId(memberPO.getMemberId());
        }
        addHba1cDTO.setMemberId(addHba1cDTO.getMemberId());
        addHba1cDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_MEMBER);
        if (null == addHba1cDTO.getOrigin()){
            addHba1cDTO.setOrigin(CheckoutConstant.RECORD_ORIGIN_PATIENT_WECHAT);
        }
        addHba1cDTO.setOperatorId(addHba1cDTO.getMemberId());
        String sid = this.hba1cService.addHba1c(addHba1cDTO);
        return Result.ok(sid);
    }

    /**
     * 加载患者糖化记录列表
     * @param startDt
     * @param endDt
     * @return
     * http://192.168.7.120:9080/intelligent-prescription/wechat/hba1c/listMemberHba1c.do?startDt=&endDt=
     */
    @RequestMapping("listMemberHba1c")
    public Result listMemberHba1c(ListMemberHbalcDTO listMemberHbalcDTO){
        MemberPO memberPO = SessionTool.getWechatSession();
        ListHbalcDTO listHbalcDTO = new ListHbalcDTO();
        BeanUtils.copyProperties(listHbalcDTO, listMemberHbalcDTO );
        listHbalcDTO.setMemberId(memberPO.getMemberId());
        List<Hba1cPO> list = this.hba1cService.listMemberHba1cByParam(listHbalcDTO);
        return Result.ok(list);
    }

    /**
     * 加载患者糖化记录列表-不用session
     * @param startDt
     * @param endDt
     * @return
     * http://192.168.7.120:9080/intelligent-prescription/wechat/hba1c/listMemberHba1cByMid.do?memberId=190629144040100001&startDt=&endDt=
     */
    @RequestMapping("listMemberHba1cByMid")
    public Result listMemberHba1cByMid(String startDt ,String endDt,String memberId){
        List<Hba1cPO> list = this.hba1cService.listMemberHba1c(memberId ,startDt ,endDt);
        return Result.ok(list);
    }
}
