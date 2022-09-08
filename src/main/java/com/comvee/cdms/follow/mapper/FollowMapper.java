package com.comvee.cdms.follow.mapper;

import com.comvee.cdms.app.doctorapp.vo.CountFollowReqVO;
import com.comvee.cdms.follow.dto.*;
import com.comvee.cdms.follow.model.FollowDayDTO;
import com.comvee.cdms.follow.model.FollowDayModel;
import com.comvee.cdms.follow.model.FollowListModel;
import com.comvee.cdms.follow.model.FollowMemberModel;
import com.comvee.cdms.follow.po.*;
import com.comvee.cdms.follow.vo.FollowRemindVO;
import com.comvee.cdms.follow.vo.ListFollowPlatformRecordVO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 *
 * @author 李左河
 *
 */
public interface FollowMapper {
    /**
     * 获取随访列表
     * @author 李左河
     * @date 2018年3月22日 上午9:51:22
     * @param memberId
     * @param doctorId
     * @param followType
     * @param deal
     * @return
     */
    List<FollowListModel> listFollow(@Param("memberId") String memberId,
                                     @Param("memberName") String memberName,
                                     @Param("doctorId") String doctorId,
                                     @Param("followType")List<Integer> followType,
                                     @Param("deal")Boolean deal);

    /**
     * 获取华西 首次首诊评估的分层结果 列表
     * @author wangxy
     * @date 2019年3月29日 上午9:51:22
     */
    List<FollowListModel> listFollowHx();

    /**
     * 获取随访详情
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param sid
     * @return
     */
    FollowPO getFollowById(@Param("sid") String sid);

    /**
     * 添加随访
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param follow
     */
    void insertFollow(FollowPO follow);

    /**
     * 修改随访
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param follow
     */
    void modifyFollow(FollowPO follow);

    /**
     * 获取随访患者编号列表
     * @author 林雨堆
     * @time 2018/07/03 11:00
     * @param leaderId
     * @return
     */
    List<String> listFollowMemberId(@Param("leaderId") String leaderId);

    /**
     * 根据患者编号列表获取患者随访设置列表
     * @author 林雨堆
     * @time 2018/07/03 11:00
     * @param leaderId
     * @return
     */
    List<FollowMemberModel> listFollowSetByMemberIds(@Param("leaderId") String leaderId,@Param("memberIds") List<String> memberIds);

    /**
     * 获取患者的随访设置根据主键
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param sid
     * @return
     */
    MemberFollowSetPO getMemberFollowSetById(@Param("sid") String sid);

    /**
     * 获取患者的随访根据患者编号和首席编号
     * @author 李左河
     * @date 2018年3月22日 上午9:51:30
     * @param memberId
     * @param leaderId
     * @param followType
     * @return
     */
    MemberFollowSetPO getMemberFollowSetByDoc(@Param("memberId") String memberId,
                                              @Param("leaderId") String leaderId,
                                              @Param("followType") Integer followType);

    /**
     * 获取患者的随访根据患者编号和首席编号
     * @author wangxy
     * @date 2018年10月25日 上午9:51:30
     * @param memberId
     * @param leaderId
     * @return
     */
    MemberFollowSetPO getMemberFollowSetByDocNew(@Param("memberId") String memberId,
                                              @Param("leaderId") String leaderId);



    /**
     * 添加患者随访设置
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param po
     */
    void insertMemberFollowSet(MemberFollowSetPO po);

    /**
     * 修改患者随访设置
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param po
     */
    void modifyMemberFollowSet(MemberFollowSetPO po);

    /**
     * 添加随访主表（创建随访2.0)
     * @param mainFollowPO
     */
    void insertMainFollow(MainFollowPO mainFollowPO);

    /**
     * 根据主键获取随访详情
     * @param sid
     * @return
     * @author 林雨堆
     * @date 2018-03-28 10:00
     */
    FollowupPO getFollowupById(String sid);

    /**
     * 更新随访信息
     * @param po
     */
    void modifyFollowup(@Param("po") FollowupPO po);

    /**
     * 根据条件获取随访主信息
     * @param foreignId
     * @param type
     * @return
     */
    MainFollowPO getMainFollowByFidAndType(@Param("foreignId") String foreignId, @Param("type") Integer type);

    /**
     * 更新随访主信息
     * @param mainFollowPO
     */
    void modifyMainFollow(MainFollowPO mainFollowPO);

    /**
     * 更新随访主信息
     * @param mainFollowPO
     */
    void modifyMainFollowByFid(MainFollowPO mainFollowPO);

    /**
     * 添加随访
     * @param followup
     */
    void insertFollowup(FollowupPO followup);

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
     * 随访提醒列表
     * @param listFollowRemindDTO
     * @return
     */
    List<FollowRemindPO> listFollowRemind(ListFollowRemindDTO listFollowRemindDTO);

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

    //gwx
    /**
     * 统计患者随访数据
     * @param
     * @return
     */
    long countFollowForMember(CountFollowReqVO countFollowReq);

    /**
     * 加载患者需要复诊提醒的医生列表
     * @param memberId
     * @param nextDt
     * @return
     */
    List<String> listMemberRevisitDoctor(@Param("memberId") String memberId,@Param("nextDt") String nextDt);

    /**
     * 筛选有首诊的患者列表
     * @param memberList
     * @param followType
     * @return
     */
    List<String> listHasFirstFollowMember(@Param("memberList") List<String> memberList,@Param("followType") Integer followType);

    /**
     * 根据时间统计随访新增数量
     * @param synthesizeDataDTO
     * @return
     */
    long countNewFollow(SynthesizeDataDTO synthesizeDataDTO);


    /**
     * 获取随访表
     * @param followDayDTO
     * @return
     */
    List<FollowDayModel> listFollowDay(FollowDayDTO followDayDTO);

//    3.6.0

    /**
     * 添加自定义随访模板
     * @param templatePO
     */
    void addFollowCustomerTemplate(FollowCustomerTemplatePO  templatePO);

    /**
     * 修改自定义随访模板
     * @param templatePO
     */
    void modifyFollowCustomerTemplate(FollowCustomerTemplatePO  templatePO);

    /**
     * 根据自定义随访名称查询随访
     * @param followName
     * @return
     */
    FollowCustomerTemplatePO getTemplateByName(@Param("followName")String followName);

    /**
     * 根据模板id查询模板
     * @param sid
     * @return
     */
    FollowCustomerTemplatePO getTemplateById(@Param("sid") String sid);

    /**
     * 加载自定义随访模板列表
     * @param doctorId
     * @param hospitalId
     * @return
     */
    List<FollowCustomerTemplatePO> listFollowCustomerTemplate(@Param("doctorId") String doctorId,@Param("hospitalId")String hospitalId,@Param("permission") String permission);

    /**
     * 添加自定义随访内容
     * @param contentPO
     */
    void insertFollowCustomContent(FollowCustomContentPO contentPO);
    /**
     * 修改自定义随访内容
     * @param contentPO
     */
    void modifyFollowCustomContent (FollowCustomContentPO contentPO);

    /**
     * 根据id获取自定义随访内容详情
     * @param sid
     * @return
     */
    FollowCustomContentPO getFollowCustomContentById(@Param("sid") String sid);

    /**
     * 加载患者的随访配置
     * @return
     */
    List<FollowMemberModel> listMemberFollowSet();

    /**
     * 统计每月随访-医院
     * @param dto
     * @return
     */
    List<CountMonthFollowPO> countMonthFollowForHos(GetStatisticsDTO dto);

    /**
     * 统计随访数据-医院
     * @param getStatisticsDTO
     * @return
     */
    long countFollowForHos(GetStatisticsDTO getStatisticsDTO);

    /**
     * 根据创建人id查询模板列表
     * @param creatorId
     * @return
     */
    List<FollowCustomerTemplatePO> listFollowTemplateByPerson(@Param("doctorList") List<String> doctorList,@Param("keyword") String keyword);

    /**
     * 删除自定义随访模板
     * @param ids
     */
    void deleteFollowTemplate(DeleteFollowCustomDTO deleteFollowCustomDTO);

    /**
     * 随访和问卷列表
     * @param memberId
     * @param memberName
     * @param doctorId
     * @param deal
     * @param followType
     * @param questionType
     * @return
     */
    List<FollowListModel> listFollowAndQuestion(@Param("memberId") String memberId,
                                                @Param("memberName") String memberName,
                                                @Param("doctorId") String doctorId,
                                                @Param("deal") Boolean deal,
                                                @Param("followType") Integer followType,
                                                @Param("questionType") Integer questionType,
                                                @Param("operatorId")String operatorId,
                                                @Param("hospitalId")String hospitalId,
                                                @Param("authorityList") List<String> authorityList);

    ////////
    /**
     * 添加2型糖尿病随访详情表
     * @param followDiabetesPO
     */
    void insertFollowDiabetes(FollowDiabetesPO followDiabetesPO);

    /**
     * 修改2型糖尿病随访详情表
     * @param followDiabetesPO
     */
    void modifyFollowDiabetes(FollowDiabetesPO followDiabetesPO);

    /**
     * 根据id获取2型糖尿病随访详情信息
     * @param sid
     * @return
     */
    FollowDiabetesPO getFollowDiabetesById(@Param("sid") String sid);

    /**
     * 获取患者上次2型糖尿病随访信息
     * @param memberId
     * @param doctorId
     * @return
     */
    FollowDiabetesPO getNewFollowDiabetes(@Param("memberId") String memberId,@Param("doctorId") String doctorId ,@Param("followType") Integer followType);

    /**
     * 添加随访报告
     * @param followReportPO
     */
    void insertFollowReport(FollowReportPO followReportPO);

    /**
     * 根据随访id获取报告详情
     * @param followId
     * @return
     */
    FollowReportPO getFollowReportByFollowId(@Param("followId") String followId,
                                             @Param("deal") Boolean deal,
                                             @Param("reportType") Integer reportType);

    /**
     * 加载随访报告列表
     * @param memberId
     * @param doctorId
     * @param reportType 报告类型
     * @return
     */
    List<FollowReportDTO> listFollowReport(ListFollowReportDTO followReportDTO);

    /**
     * 加载随访主表信息
     * @param memberId
     * @param doctorId
     * @return
     */
    List<MainFollowPO> listMainFollow(@Param("memberId") String memberId,@Param("doctorId") String doctorId);

    /**
     * 根据随访报告id查询报告信息
     * @param sid
     * @return
     */
    FollowReportPO getFollowReportById(@Param("sid") String sid);

    /**
     * 获取随访列表
     * @author 李左河
     * @date 2018年3月22日 上午9:51:22
     * @param memberId
     * @param doctorId
     * @param followType
     * @param deal
     * @return
     */
    List<FollowListModel> listFollowWechat(@Param("memberId") String memberId,
                                     @Param("memberName") String memberName,
                                     @Param("doctorId") String doctorId,
                                     @Param("followType")Integer followType,
                                     @Param("deal")Boolean deal,
                                     @Param("fillFormBy")String fillFormBy);

    /** v4.0.4
     * 通过条件查询随访任务列表
     * @param listFollowRemindDTO
     * @return
     */
    List<FollowRemindVO> listFollowRemindByParam(ListFollowRemindDTO listFollowRemindDTO);

    /** v4.0.4
     *  加载对应状态下所有的随访
     * @param followDTO
     * @return
     */
    List<FollowListModel> listFollowByParam(ListFollowDTO followDTO);


    /** v4.2.1
     * 患者端加载患者的随访列表
     * @param listMemberFollowDTO
     * @return
     */
    List<FollowListModel> listMemberFollow(ListMemberFollowDTO listMemberFollowDTO);

    /** v4.0.5
     * 添加随访信息表记录
     * @return
     */
    void insertFollowMmcVisit(FollowMmcVisitPO followMmcVisitPO);

    /** v4.0.5
     * 修改随访信息表记录
     * @return
     */
    void modifyFollowMmcVisit(FollowMmcVisitPO followMmcVisitPO);

    /** v4.0.5
     * 获取随访问卷字典表数据
     * @return
     */
    List<FollowQuestionPO> getQuestiondate(@Param("followType") String followType);

    /** v4.0.5
     * 获取随访用药字典表数据
     * @return
     */
    List<FollowDrugPO> getDrugDICT();

    /** v4.0.5
     * 获取新版随访信息表
     * @return
     */
    List<FollowMmcVisitPO> listFollowMmcVisit(FollowMmcVisitPO followMmcVisitPO);

    /**
     * 查询
     * @param
     * @return
     */
    List<MainFollowPO> getMainFollowByType(ListMemberFollowDTO followDTO);

    Long getFollowNumByHospitalId(@Param("hospitalIdList") List<String> hospitalIdList, @Param("startDt") String startDt, @Param("endDt") String endDt);

    Long getFollowMemberNumByHospitalId(@Param("hospitalIdList") List<String> hospitalIdList, @Param("startDt") String startDt, @Param("endDt") String endDt);

    List<ListFollowPlatformRecordVO> listMemberFollowForPlatform(ListMemberFollowDTO listMemberFollowDTO);

    List<ListFollowPlatformRecordVO> listMemberUnDoFollowForPlatform(ListMemberFollowDTO listMemberFollowDTO);

    //随访人数
    Long countFollowMemberNum(ListMemberFollowDTO listMemberFollowDTO);
    //随访条数
    Long countFollowNum(ListMemberFollowDTO listMemberFollowDTO);
}
