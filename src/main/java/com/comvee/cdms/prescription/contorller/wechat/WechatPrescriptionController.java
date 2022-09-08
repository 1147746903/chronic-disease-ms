package com.comvee.cdms.prescription.contorller.wechat;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.dto.ListMemberPrescriptionDTO;
import com.comvee.cdms.prescription.po.MemberPrescriptionPO;
import com.comvee.cdms.prescription.po.PrescriptionKnowledgePO;
import com.comvee.cdms.prescription.service.PrescriptionServiceI;
import com.comvee.cdms.prescription.vo.ArticleVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/9
 */
@RestController
@RequestMapping("/wechat/prescription")
public class WechatPrescriptionController {

    @Autowired
    private PrescriptionServiceI prescriptionService;

    /**
     * 加载管理处方列表
     * @param pr
     * @return
     */
    @RequestMapping("listMemberPrescription")
    public Result listMemberPrescription(PageRequest pr){
        MemberPO memberPO = SessionTool.getWechatSession();
        ListMemberPrescriptionDTO listMemberPrescriptionDTO = new ListMemberPrescriptionDTO();
        listMemberPrescriptionDTO.setMemberId(memberPO.getMemberId());
        listMemberPrescriptionDTO.setHandDown(PrescriptionConstant.HAND_DOWN);
        PageResult<MemberPrescriptionPO> pageResult = this.prescriptionService.listMemberPrescription(pr, listMemberPrescriptionDTO);
        return new Result(pageResult);
    }

    /**
     * 加载管理处方知识学习列表
     * @param pr
     * @return
     */
    @RequestMapping("listMemberKnowledgePrescription")
    public Result listMemberKnowledgePrescription(PageRequest pr){
        MemberPO memberPO = SessionTool.getWechatSession();
        ListMemberPrescriptionDTO listMemberPrescriptionDTO = new ListMemberPrescriptionDTO();
        listMemberPrescriptionDTO.setMemberId(memberPO.getMemberId());
        listMemberPrescriptionDTO.setModuleString(String.valueOf(PrescriptionConstant.MODULE_TYPE_EDU));
        listMemberPrescriptionDTO.setHandDown(PrescriptionConstant.HAND_DOWN);
        PageResult<MemberPrescriptionPO> pageResult = this.prescriptionService.listMemberPrescription(pr, listMemberPrescriptionDTO);
        return new Result(pageResult);
    }

    /**
     * 加载管理处方知识学习
     * @param prescriptionId
     * @return
     */
    @RequestMapping("listPrescriptionKnowledge")
    public Result listPrescriptionKnowledge(String prescriptionId){
        ValidateTool.checkParamIsNull(prescriptionId, "prescriptionId");
        List<PrescriptionKnowledgePO>  list = this.prescriptionService.listPrescriptionKnowledge(prescriptionId);
        return new Result(list);
    }

    /**
     * 加载管理处方知识内容
     * @param eohKnowledgeId
     * @return
     */
    @RequestMapping("loadArticleInfo")
    public Result loadArticleInfo(String eohKnowledgeId){
        ValidateTool.checkParamIsNull(eohKnowledgeId, "eohKnowledgeId");
        MemberPO memberPO = SessionTool.getWechatSession();
        ArticleVO articleVO = this.prescriptionService.loadArticleInfo(eohKnowledgeId ,memberPO.getMemberId());
        return new Result(articleVO);
    }
}
