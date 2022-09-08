package com.comvee.cdms.prescription.service;

import com.comvee.cdms.prescription.bo.PagerBO;
import com.comvee.cdms.prescription.dto.PrescriptionDTO;
import com.comvee.cdms.prescription.dto.PrescriptionEduDTO;
import com.comvee.cdms.prescription.po.PrescriptionKnowledgePO;
import com.comvee.cdms.prescription.vo.PrescriptionDetailVO;
import com.comvee.cdms.prescription.vo.PrescriptionEduVO;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeTreeVO;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeVO;

import java.util.List;

/**
 * @author 李左河
 * @date 2018/8/3 10:10.
 */
public interface PrescriptionOfEduServiceI {
    /**
     * 获取管理处方 知识模块
     * @author 李左河
     * @date 2018/8/3 11:29
     * @param prescriptionDTO
     * @return com.comvee.cdms.prescription.vo.PrescriptionDetailVO<com.comvee.cdms.prescription.vo.PrescriptionEduVO>
     *
     */
    PrescriptionDetailVO<PrescriptionEduVO> getPrescriptionEdu(PrescriptionDTO prescriptionDTO);

    /**
     * 知识智能推荐
     * @author 李左河
     * @date 2018/8/6 10:24
     * @param prescriptionDTO
     * @return com.comvee.cdms.prescription.vo.PrescriptionDetailVO<com.comvee.cdms.prescription.vo.eduplan.KnowledgeVO>
     *
     */
    PrescriptionDetailVO<KnowledgeVO> intelligentRecommendationOfEdu(PrescriptionDTO prescriptionDTO);

    /**
     * 修改管理处方 知识模块
     * @author 李左河
     * @date 2018/8/7 9:34
     * @param prescriptionDTO
     * @return void
     *
     */
    void modifyPrescriptionEdu(PrescriptionDTO prescriptionDTO);

    /**
     * 知识点树型结构
     * @author 李左河
     * @date 2018/8/13 16:26
     * @param prescriptionEduDTO
     * @return java.util.List<com.comvee.cdms.prescription.vo.eduplan.KnowledgeTreeVO>
     *
     */
    List<KnowledgeTreeVO> knowledgeTree(PrescriptionEduDTO prescriptionEduDTO);

    /**
     * 获取文章表分页信息
     * @author 李左河
     * @date 2018/8/13 16:51
     * @param prescriptionEduDTO
     * @param pager
     * @return java.util.List<com.comvee.cdms.prescription.vo.eduplan.KnowledgeTreeVO>
     *
     */
    List<KnowledgeTreeVO> loadArticle(PrescriptionEduDTO prescriptionEduDTO, PagerBO pager);

    /** v4.2.1
     * 获取随访处方最新下发的课程
     * @return
     */
    List<PrescriptionKnowledgePO> loadNewSendArticle(String eohType,String memberId);

    //获取用户标签
    List<String> getMemberTags(String memberId);
}
