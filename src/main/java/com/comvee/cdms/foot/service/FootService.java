package com.comvee.cdms.foot.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.model.po.ScreeningReportPO;
import com.comvee.cdms.foot.dto.ListFootDTO;
import com.comvee.cdms.foot.po.FootPO;
import com.comvee.cdms.foot.po.FootResultPO;
import com.comvee.cdms.foot.vo.FootVO;
import com.comvee.cdms.foot.vo.ReportRelateStatusVO;

import java.util.List;
import java.util.Map;

/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public interface FootService {

    /**
     * 加载足部管理处方列表
     * @param listFootDTO
     * @return
     */
    List<FootPO> listFoot(ListFootDTO listFootDTO);

    /**
     * 加载足部管理处方列表
     * @param listFootDTO
     * @return
     */
    PageResult<FootPO> listFootPage(ListFootDTO listFootDTO, PageRequest page);


    /**
     * 获取足部管理处方
     * @param sid
     * @return
     */
    FootPO getFoot(String sid);


    /**
     * 新增足部管理处方记录
     * @param FootPO
     * @return
     */
    String addFoot(FootPO footPO);

    /**
     * 更新记录
     * @param FootPO
     */
    void modifyFoot(FootPO FootPO);

    /**
     * 足部管理处方评估逻辑（危险等级，治疗建议）
     * @param FootPO
     */
    Map<String,Object> outFootSuggest(String memberId, FootVO followModel) throws Exception;

    /**
     * 获取患者最新的足部管理处方
     * @param listFootDTO
     * @return
     */
    FootPO getFootNew(ListFootDTO listFootDTO);

    /**
     * 加载处方关联的筛查报告
     * @param prescriptId
     * @return
     */
    List<ScreeningReportPO> listPrescriptionScreeningReport(String prescriptId);

    /**
     * 添加足部处方结果
     * @param prescriptionFootResultPO
     */
    void addPrescriptionFootResultPO(FootResultPO prescriptionFootResultPO);

    /**
     * 判断报告可关联状态
     * @param idCard
     * @return
     */
    ReportRelateStatusVO checkScreeningReportRelate(String idCard ,String doctorId);

    /**
     * 筛查足部处方评估输出
     * @param footVO
     * @param idCard
     * @return
     */
    Map<String,Object> outScreeningFootSuggest(FootVO footVO ,String memberId);

    /**
     * 删除患者足部处方评估结果
     * @param doctorId
     * @param memberId
     */
    void deleteMemberFootResult(String doctorId ,String memberId);

    /**
     * 添加筛查报告关联
     * @param footReportPO
     */
    void addFootReportRelate(String listJson);

    /**
     * 添加筛查的足处方
     * @param footPO
     * @return
     */
    String addScreeningFoot(FootPO footPO);

    /**
     * 更新筛查足处方记录
     * @param FootPO
     */
    void modifyScreeningFoot(FootPO FootPO);

    /**
     * 添加足处方微信下发消息
     * @param footPO
     */
   void addFootPrescriptionWechatMessage(FootPO footPO,String sysOrigin);

    /**
     * 删除报告关联
     * @param prescriptId
     */
   void deleteFootReportRelate(String prescriptId);

    /**
     * 加载住院患者最新的足处方记录
     * v 5.1.6
     * @param memberId
     * @return
     */
    FootPO listFootByMemberOfInHos( String memberId);
}
