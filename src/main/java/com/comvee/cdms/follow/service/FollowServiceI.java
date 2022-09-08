package com.comvee.cdms.follow.service;

import com.comvee.cdms.app.doctorapp.vo.CountFollowReqVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.follow.dto.*;
import com.comvee.cdms.follow.model.*;
import com.comvee.cdms.follow.po.*;
import com.comvee.cdms.follow.vo.FollowRemindVO;
import com.comvee.cdms.follow.vo.ListFollowPlatformRecordVO;
import com.comvee.cdms.knowledge.model.PagerModel;
import com.comvee.cdms.prescription.dto.DoctorDTO;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeVO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.wechat.model.WechatSession;
import org.apache.ibatis.annotations.Param;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
public interface FollowServiceI {
    /**
     * 获取随访列表
     * @author 李左河
     * @date 2018年3月22日 上午9:51:45
     * @param memberId
     * @param leaderId
     * @param doctorId
     * @param followType
     * @param deal
     * @return
     */
    List<FollowListModel> listFollow(String memberId, String leaderId, String doctorId, List<Integer> followType, Boolean deal);

    /**
     * 获取随访详情
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param sid
     * @param type
     * @return
     */
    Object getFollowById(String sid,Integer type);

    /**
     * 添加随访
     * @author 李左河
     * @date 2018年3月22日 上午9:51:54
     * @param follow
     * @return
     */
    String insertFollowWithLock(FollowDTO follow);

    /**
     * 修改随访
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param follow
     */
    void modifyFollow(FollowDTO follow);

    /**
     * 分页加载患者随访
     * @author 李左河
     * @date 2018年3月22日 上午9:52:06
     * @param memberId
     * @param leaderId
     * @param doctorId
     * @param followType
     * @param pager
     * @param deal
     * @return
     */
    PageResult<FollowListModel> listFollowOfPager(String memberId, String memberName, String doctorId ,List<Integer> followType, PageRequest pager,Boolean deal);

    /**
     * 获取随访患者
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param leaderId
     * @param doctorId
     * @param followType
     * @param pager
     * @return
     */
    PageResult<FollowMemberModel> listFollowMemberOfPage(String leaderId, String doctorId, Integer followType, PageRequest pager);

    /**
     * 更新患者随访设置
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param set
     */
    void modifyFollowSet(MemberFollowSetPO set);

    /**
     * 添加随访设置
     * @author 李左河
     * @date 2018年3月22日 上午9:52:19
     * @param set
     * @return
     */
    String insertFollowSetWithLock(MemberFollowSetPO set);

    /**
     * 获取患者在医生工作室下的随访设置
     * @author 李左河
     * @date 2018年3月22日 上午9:52:27
     * @param doctor
     * @param memberId
     * @param followType
     * @return
     */
    MemberFollowSetPO getMemberFollowSetByDoc(DoctorSessionBO doctor, String memberId, Integer followType);

    /**
     * 获取患者在医生工作室下的随访设置
     * @author 李左河
     * @date 2018年3月22日 上午9:52:27
     * @param doctor
     * @param memberId
     * @return
     */
    MemberFollowSetPO getMemberFollowSetByDocNew(String doctorId, String memberId);



    /**
     * 获取出院随访患者分页
     * @author 李左河
     * @date 2018/6/12 17:22
     * @param doctor
     * @param outDays
     * @param endDays
     * @param pager
     * @return com.comvee.cdms.common.wrapper.PageResult<com.comvee.cdms.follow.model.FollowMemberModel>
     *
     */
    PageResult<FollowMemberModel> listOutHosFollowMemberPageByDays(DoctorSessionBO doctor, Integer outDays, Integer endDays, PagerModel pager);

    /**
     * 统计随访数据
     * @param countFollowDTO
     * @return
     */
    long countFollow(CountFollowDTO countFollowDTO);

    /**
     * 统计每月随访
     * @param countMonthFollowDTO
     * @return
     */
    List<CountMonthFollowPO> countMonthFollow(CountMonthFollowDTO countMonthFollowDTO);

    /**
     * 获取患者档案，补充三餐饮食情况信息
     * @param archivesJson
     * @return
     */
    String doHistoryFood(String archivesJson);

    /**
     * 生成首诊建议
     * @param followBody
     * @return
     */
    Map<String,Object> outFirstFollow(OutFollowDTO followDTO);

    /**
     * 华西医院患者 分层分级规则
     * @param followBody
     * @return
     */
    Map<String,Object> outHxFollow(String followBody, String levelStr, String memberId,String hospitalId);

    /**
     * 生成行为问卷建议
     * @param followBody
     * @return
     */
    Map<String,Object> outQuesFollow(String followBody,String memberId,String doctorId);

    /**
     * 分页加载随访提醒列表
     * @param listFollowRemindDTO
     * @return
     */
    PageResult<FollowRemindVO> listFollowRemindPage(ListFollowRemindDTO listFollowRemindDTO, PageRequest page);

    PageResult<FollowRemindVO> pageFollowRemindList(ListFollowRemindDTO listFollowRemindDTO, PageRequest page);

    /**
     * 统计随访提醒数据
     * @param followRemindPO
     * @return
     */
    long countFollowRemind(ListFollowRemindDTO listFollowRemindDTO);

    /**
     * 添加随访提醒
     * @param followRemindPO
     * @return
     */
    void insertFollowRemind(FollowRemindPO followRemindPO);

    /**
     * 修改随访提醒
     * @param followRemindPO
     * @return
     */
    void modifyFollowRemind(FollowRemindPO followRemindPO);

    /**
     * 获取随访提醒
     * @param followRemindPO
     * @return
     */
    FollowRemindPO getFollowRemind(FollowRemindPO followRemindPO);

    /**
     * 加载患者需要复诊提醒的医生列表
     * @param memberId
     * @param advanceDays
     * @return
     */
    List<String> listMemberRevisitDoctor(String memberId,Integer advanceDays);

    /**
     * 筛选有首诊的患者列表
     * @param memberList
     * @return
     */
    List<String> listHasFirstFollowMember(List<String> memberList);

    /**
     * 获取最新一条问卷记录
     * @param memberList
     * @return
     */
    Object getFollowQuesNewByType(String memberId, String doctorId,Integer followType);

    /**
     * 根据时间统计随访新增数量
     * @param synthesizeDataDTO
     * @return
     */
    long countNewFollow(SynthesizeDataDTO synthesizeDataDTO);

    /**
     * 保存自定义随访模板
     * @param templatePO
     */
    String saveFollowCustomerTemplate(AddFollowCustomTemplateDTO templateDTO);

    /**
     * 修改自定义随访模板
     * @param templatePO
     */
//    void modifyFollowCustomerTemplate(FollowCustomerTemplatePO  templatePO);
    /**
     * 根据模板id查询模板
     * @param sid
     * @return
     */
    FollowCustomerTemplatePO getTemplateById(String sid);

    /**
     * 加载自定义随访模板列表
     * @param doctorId
     * @param hospitalId
     * @return
     */
    List<FollowCustomerTemplatePO> listFollowCustomerTemplate(String doctorId,String hospitalId);

    /**
     * 获取模板的回显信息
     * @param doctorId
     * @param memberId
     * @return
     */
    Map<String,Object> getCustomerEchoInfo(String doctorId,String memberId,String followId);

    /**
     * 根据条件获取随访主信息
     * @param foreignId
     * @param type
     * @return
     */
    MainFollowPO getMainFollowByFidAndType(String foreignId, Integer type);


    /**
     * 加载模板管理随访模板列表
     * @param doctorId
     * @param hospitalId
     * @return
     */
    PageResult<FollowCustomerTemplatePO> listOperateFollowTemplate(PageRequest page,String doctorId,String keyword);

    /**
     * 删除自定义随访模板
     * @param ids
     */
    void deleteFollowTemplate(String ids);


    /**
     * 加载患者的随访配置
     * @return
     */
    PageResult<FollowMemberModel> listMemberFollowSet(int page,int size);

    /**
     * 自动忽略到期的提前提醒
     * @param listFollowRemindDTO
     */
    void ignoreFollowRemind(String doctorId);

    /**
     * 统计每月随访
     * @param countMonthFollowDTO
     * @return
     */
    List<CountMonthFollowPO> countMonthFollow(GetStatisticsDTO dto);
///////////
    /**
     * 获取患者上次2型糖尿病随访信息
     * @param memberId
     * @param doctorId
     * @return
     */
    FollowDiabetesPO getNewFollowDiabetes( String memberId, String doctorId ,Integer followType);

    /**
     * 统计随访数据
     * @param dto
     * @return
     */
    long countFollow(GetStatisticsDTO dto);

    /**
     * 分页随访和问卷列表
     * @param flage
     * @param memberId
     * @param memberName
     * @param doctorId
     * @param type
     * @param pager
     * @param deal
     * @return
     */
    PageResult<FollowListModel> listFollowAndQuestion(String flag, String memberId, String memberName, String doctorId, Integer type, PageRequest pager, Boolean deal ,String operatorId,String hospitalId,String authority);
    /**
     * 根据随访id获取报告详情
     * @param followId
     * @return
     */
    FollowReportPO getFollowReportByFollowId(String followId);

    /**
     * 加载随访报告列表
     * @param memberId
     * @param doctorId
     * @param reportType 报告类型
     * @return
     */
    PageResult<FollowReportDTO> listFollowReport(ListFollowReportDTO followReportDTO,PageRequest page);

    /**
     * 根据随访报告id查询报告信息
     * @param sid
     * @return
     */
    FollowReportPO getFollowReportById(String sid);

    Map getHealthAccessResult(String memberInfo , String followInfo);

    HealthAccessModel getPrintAccessDiaControl(String sid , String memberId);

    /**
     * 获取健康评估患者信息
     * @param doctorId
     * @param memberId
     * @return
     */
    Map<String,Object> getHealthAccessMemberInfo(String doctorId,String memberId,String followId);

    /**
     * 小程序获取随访列表
     * @author 李左河
     * @date 2018年3月22日 上午9:51:45
     * @param memberId
     * @param leaderId
     * @param doctorId
     * @param followType
     * @param deal
     * @return
     */
    List<FollowListModel> wechatListFollow(String memberId, String memberName, String doctorId, Integer followType, Boolean deal , String fillFormBy);


    /**
     * 高血压分层分级评估
     * v5.0.0
     * @param dataJson
     * @return
     */
    Map<String,Object> outFirstGxyFollow(String dataJson);

    /** v4.0.4
     *  加载对应状态下所有的随访
     * @param followDTO
     * @return
     */
    List<FollowListModel> listFollowByParam(ListFollowDTO followDTO);

    /** v4.0.4
     * 加载随访提醒列表
     * @param followRemindDTO
     * @return
     */
    List<FollowRemindPO> listFollowRemind(ListFollowRemindDTO followRemindDTO);


    /** v4.2.1
     * 患者端加载患者的随访列表
     * @param memberId
     * @return
     */
    PageResult<FollowListModel> listMemberFollow(PageRequest page,String memberId,List<Integer> typeList);

    /**
     * 问卷数据接口
     * @return
     */
    List<FollowQuestionPO> getQuestionnaire(String followtype);

    /**
     * 用药数据接口
     * @return
     */
    List<FollowDrugPO> getDrugDICT();

    /**
     * 创建糖尿病首诊随访提醒
     * @param doctorId 医生id
     * @param memberId 患者id
     */
    void createSZRemind(String doctorId, String memberId);


    PageResult<ListFollowPlatformRecordVO> pagerMemberFollow(DoctorSessionBO doctorSessionBO,ListMemberFollowDTO listMemberFollowDTO, PageRequest page);

    PageResult<ListFollowPlatformRecordVO> pagerMemberUnDoFollow(DoctorSessionBO doctorSessionBO,ListMemberFollowDTO listMemberFollowDTO, PageRequest page);

    Map<String,Object> committeeFollowStatics(DoctorSessionBO doctorSessionBO);
}
