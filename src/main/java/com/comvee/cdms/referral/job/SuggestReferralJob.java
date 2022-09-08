package com.comvee.cdms.referral.job;

import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.service.CheckDictCacheI;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.common.cfg.ParamLogConstant;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.foot.po.FootPO;
import com.comvee.cdms.foot.service.FootService;
import com.comvee.cdms.member.bo.MemberCheckinBO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.referral.mapper.ReferralApplyMapper;
import com.comvee.cdms.referral.po.ReferralSuggestPO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import com.comvee.cdms.sign.po.BloodPressurePO;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.service.BloodPressureService;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.xxl.job.core.biz.model.ReturnT;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SuggestReferralJob{
    private final static Logger log = LoggerFactory.getLogger(SuggestReferralJob.class);

    @Autowired
    private ReferralApplyMapper referralApplyMapper;

    @Autowired
    public CheckoutDetailServiceI checkoutDetailService;

    @Autowired
    public MemberService memberService;

    @Autowired
    public BloodSugarService bloodSugarService;

    @Autowired
    public BloodPressureService bloodPressureService;

    @Autowired
    public CheckDictCacheI checkDictCache;

    @Autowired
    private FootService footService;

    private static final int PAGE_SIZE = 20;

    @XxlJob("suggestReferralJob")
    public ReturnT<String> execute(String param) throws Exception {
        log.info("开启建议转诊发现器...");
        int pageNum = 1;
        PageResult<MemberCheckinBO> poPageResult = null;
        do {
            poPageResult = this.memberService.listInHospitalMemberOfLevelLow(pageNum, PAGE_SIZE);
            SuggestReferralMonitor(poPageResult.getRows());
            pageNum ++;
        }while(poPageResult.getTotalPages() > pageNum - 1);
        log.info("结束建议转诊发现器...");
        return ReturnT.SUCCESS;
    }

    private void SuggestReferralMonitor(List<MemberCheckinBO> members) {
        //获取在院患者
//        List<MemberCheckinBO> members = this.memberService.listInHospitalMemberOfLevelLow();
        for(MemberCheckinBO member : members){
            //筛查符合条件列表
            List<ReferralSuggestPO> selects = new ArrayList<>();

            /**
             * 转诊理由 1001：患者为小于25岁糖尿病患者，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗
             * 逻辑：确诊为糖尿病患者，患者年龄<25岁
             */
            String birthday = member.getBirthday();
            String diabetesType = member.getDiabetesType();
            if(!StringUtils.isBlank(birthday) && !StringUtils.isBlank(diabetesType)){
                int age = DateHelper.getAge(birthday);
                //转诊理由1003 条件满足
                if(age<25 && (diabetesType.equals("SUGAR_TYPE_001")||diabetesType.equals("SUGAR_TYPE_002")||diabetesType.equals("SUGAR_TYPE_003"))){
                    ReferralSuggestPO referralSuggest = new ReferralSuggestPO(
                            DaoHelper.getSeq(),
                            member.getMemberId(),
                            member.getHospitalId(),
                            1001,
                            "患者为小于25岁糖尿病患者，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗"
                    );
                    selects.add(referralSuggest);
                }
            }

            //获取患者在院期间所有血糖记录
            List<BloodSugarPO> paramlogs = this.bloodSugarService.listParamLogByMemberIdOfInHos(member.getMemberId());
            boolean flagOf1002 = false, flagOf1003 = false, flagOf1004 = false;
            String today = DateHelper.getToday()+DateHelper.DAY_FORMAT;
            Integer flag1Of1003 = 0,flag2Of1003=0;
            for(BloodSugarPO paramLog : paramlogs){
                String paramCode = paramLog.getParamCode();
                String value = paramLog.getParamValue();
                String recordTime = paramLog.getRecordDt();
                Integer level = paramLog.getParamLevel();
                Integer day = DateHelper.dateCompareGetDay(today,recordTime);
                if(!StringUtils.isBlank(value) && !StringUtils.isBlank(paramCode)){
                    Double doubleValue = Double.parseDouble(value);
                    /**
                     * 转诊理由 1002：患者出现随机血糖大于16.7mmol/L的情况，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗
                     * 逻辑：随机血糖≥16.7mmol／L，出现一次
                     */
                    String paramCodeRandomtime = ParamLogConstant.PARAM_CODE_RANDOMTIME;
                    if(ParamLogConstant.PARAM_CODE_RANDOMTIME.equals(paramCode)){
                        if(doubleValue>=16.7){
                            flagOf1002 = true;
                        }
                    }

                    /**
                     * 转诊理由 1003：患者出现反复低血糖情况，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗
                     * 逻辑：近1个月出现≥3次低血糖、及2周内出现≥2次低血糖的 (血糖值：≤3.9mmol/L）
                     */
                    //转诊理由1003 条件1累计
                    if(day>-1 && day<30 && level < 3 && doubleValue <= 3.9){
                        flag1Of1003++;
                    }
                    //转诊理由1003 条件2累计
                    if(day>-1 && day<14 && level < 3 && doubleValue <= 3.9){
                        flag2Of1003++;
                    }
                    //转诊理由1003 条件1和2满足
                    if(flag1Of1003>=3 && flag2Of1003>=2){
                        flagOf1003 = true;
                    }

                    /**
                     * 转诊理由 1004：患者出现严重低血糖情况，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗
                     * 逻辑：出现一次严重低血糖，即<3.0mmol/L
                     */
                    if(level < 3 && doubleValue<3){
                        flagOf1004 = true;
                    }

                    //提前找到所有转诊理由直接跳出循环
                    if(flagOf1002 && flagOf1004 && flagOf1003){
                        break;
                    }
                }
            }
            //转诊1002 条件满足
            if(flagOf1002){
                ReferralSuggestPO referralSuggest = new ReferralSuggestPO(
                        DaoHelper.getSeq(),
                        member.getMemberId(),
                        member.getHospitalId(),
                        1002,
                        "患者出现随机血糖大于16.7mmol/L的情况，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗"
                );
                referralSuggest.setType(1002);

                selects.add(referralSuggest);
            }
            //转诊1003 条件满足
            if(flagOf1003){
                ReferralSuggestPO referralSuggest = new ReferralSuggestPO(
                        DaoHelper.getSeq(),
                        member.getMemberId(),
                        member.getHospitalId(),
                        1003,
                        "患者出现反复低血糖情况，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗"
                );
                selects.add(referralSuggest);
            }
            //转诊1004 条件满足
            if(flagOf1004){
                ReferralSuggestPO referralSuggest = new ReferralSuggestPO(
                        DaoHelper.getSeq(),
                        member.getMemberId(),
                        member.getHospitalId(),
                        1004,
                        "患者出现严重低血糖情况，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗"
                );
                selects.add(referralSuggest);
            }

            /**
             * 转诊理由 1005：患者血糖、血压及血脂均未达标，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗
             * 逻辑：HbA1c＞7.0%、血压>140／80mmHg、LDL-C>2.6mmoL／L
             */
            //获取患者在院期间的（糖化血红蛋白HbA1C，低密度脂蛋白胆固醇LDL-C）检验
            List<CheckoutDetailPO> checkoutDetails = this.checkoutDetailService.listCheckoutDetailByMemberIdOfInHos(member.getMemberId());
            //获取患者在院期间的血压
            List<BloodPressurePO> memberBloodPressures = this.bloodPressureService.listBloodPressureByMemberIdOfInHos(member.getMemberId());
            boolean flag1Of1005 = false, flag2Of1005 = false, flag3Of1005 = false;
            //筛查转诊理由1005 条件1和2
            for(CheckoutDetailPO checkoutDetail : checkoutDetails){
                String name = checkoutDetail.getCheckoutName();
                String value = checkoutDetail.getCheckoutResult();
                if(!StringUtils.isBlank(name) && !StringUtils.isBlank(value) && StringUtils.checkParam(value)){
                    Double doubleValue = Double.parseDouble(value);
                    if("糖化血红蛋白".equals(name) && doubleValue > 7){
                        flag1Of1005 = true;
                    }

                    if("低密度脂蛋白".equals(name) && doubleValue > 2.6){
                        flag2Of1005 = true;
                    }

                    //两条件都满足，提前退出循环筛查
                    if(flag1Of1005 && flag2Of1005){
                        break;
                    }
                }
            }
            //筛查转诊理由1005 条件3
            for(BloodPressurePO model : memberBloodPressures){
                String dbp = model.getDbp();
                String sbp = model.getSbp();
                if(!StringUtils.isBlank(sbp) && !StringUtils.isBlank(dbp)){
                    Double doubleSbp = Double.parseDouble(sbp);
                    Double doubleDbp = Double.parseDouble(dbp);
                    if(doubleSbp > 140 && doubleDbp > 80){
                        flag3Of1005 = true;
                        break;
                    }
                }
            }
            //转诊理由1005 条件都满足
            if(flag1Of1005 && flag2Of1005 && flag3Of1005){
                ReferralSuggestPO referralSuggest = new ReferralSuggestPO(
                        DaoHelper.getSeq(),
                        member.getMemberId(),
                        member.getHospitalId(),
                        1005,
                        "患者血糖、血压及血脂均未达标，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗"
                );
                selects.add(referralSuggest);
            }

            /**
             * 转诊理由 1006：患者血糖波动较大，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗
             * 逻辑：患者同一天内出现低血糖（≤3.9mmol/L）与高血糖（空腹血糖≥7.0mmol/L，餐后血糖≥10mmol/L）
             */
            //日期时间工具
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, -1);
            String date= DateHelper.getDate(c.getTime(), "yyyy-MM-dd");
            String startDt = date + DateHelper.DEFUALT_TIME_START;
            String endDt = date + DateHelper.DEFUALT_TIME_END;
            //获取患者低血糖最低血糖值
            ListBloodSugarDTO dto1 = new ListBloodSugarDTO();
            dto1.setStartDt(startDt);
            dto1.setEndDt(endDt);
            dto1.setParamLevel("1");
            dto1.setMemberId(member.getMemberId());
            BloodSugarPO lowPo = this.bloodSugarService.listBloodSugarOneDayOfInHos(dto1);
            //获取患者高血糖,空腹血糖最高值
            ListBloodSugarDTO dto2 = new ListBloodSugarDTO();
            dto2.setStartDt(startDt);
            dto2.setEndDt(endDt);
            dto2.setParamLevel("5");
            dto2.setParamCode(SignConstant.PARAM_CODE_BEFORE_BREAKFAST);
            dto2.setMemberId(member.getMemberId());
            BloodSugarPO highKf = this.bloodSugarService.listBloodSugarOneDayOfInHos(dto2);
            //获取患者高血糖,餐后血糖最高值
            List<String> paramCodeList = new ArrayList<>();
            paramCodeList.add(SignConstant.PARAM_CODE_AFTER_BREAKFAST);
            paramCodeList.add(SignConstant.PARAM_CODE_AFTER_BREAKFAST_1H);
            paramCodeList.add(SignConstant.PARAM_CODE_AFTER_DINNER);
            paramCodeList.add(SignConstant.PARAM_CODE_AFTER_DINNER_1H);
            paramCodeList.add(SignConstant.PARAM_CODE_AFTER_LUNCH);
            paramCodeList.add(SignConstant.PARAM_CODE_AFTER_LUNCH_1H);
            ListBloodSugarDTO dto3 = new ListBloodSugarDTO();
            dto3.setStartDt(startDt);
            dto3.setEndDt(endDt);
            dto3.setParamLevel("5");
            dto3.setParamCodeList(paramCodeList);
            dto3.setMemberId(member.getMemberId());
            BloodSugarPO highCh = this.bloodSugarService.listBloodSugarOneDayOfInHos(dto3);
            if (lowPo != null && highKf != null && highCh != null){
                double lowPoValue = Double.parseDouble(lowPo.getParamValue());
                double highKfValue = Double.parseDouble(highKf.getParamValue());
                double highChValue = Double.parseDouble(highCh.getParamValue());
                if (lowPoValue <= 3.9 && highKfValue >= 7 && highChValue >= 10){
                    ReferralSuggestPO referralSuggest = new ReferralSuggestPO(
                            DaoHelper.getSeq(),
                            member.getMemberId(),
                            member.getHospitalId(),
                            1006,
                            "患者血糖波动较大，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗"
                    );
                    selects.add(referralSuggest);
                }
            }

            /**
             * 转诊理由 1007：患者糖尿病足Wagner等级已超过一级，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗
             * 逻辑：糖尿病足Wagner2级及以上
             */
            FootPO footPO = this.footService.listFootByMemberOfInHos(member.getMemberId());
            if (footPO != null){
                if (!StringUtils.isBlank(footPO.getAssessResult())){
                    Map<String, Object> map = JsonSerializer.jsonToMap(footPO.getAssessResult());
                    if (null != map.get("wagner_level") && !StringUtils.isBlank(map.get("wagner_level").toString())){
                        int wagnerLevel = Integer.parseInt(map.get("wagner_level").toString());
                        if (wagnerLevel >= 2){
                            ReferralSuggestPO referralSuggest = new ReferralSuggestPO(
                                    DaoHelper.getSeq(),
                                    member.getMemberId(),
                                    member.getHospitalId(),
                                    1007,
                                    "患者糖尿病足Wagner等级已超过一级，根据糖尿病分级诊疗指南，建议转入县级或市二级以上医院治疗"
                            );
                            selects.add(referralSuggest);
                        }
                    }
                }
            }

            /**筛查新需入库的转诊建议**/
            //患者在院期间发生的建议转诊记录列表
            List<ReferralSuggestPO> referralSuggests = this.referralApplyMapper.listSuggestReferralOfInHos(member.getHospitalId(),member.getMemberId());
            List<ReferralSuggestPO> inserts = new ArrayList<>();
            for(ReferralSuggestPO select :selects){
                boolean isNewLog = true;
                for(ReferralSuggestPO old : referralSuggests){
                    if (select.getType().equals(old.getType()) && select.getMemberId().equals(select.getMemberId())){
                        isNewLog = false;
                        break;
                    }
                }
                if (isNewLog){
                    inserts.add(select);
                }
            }
            //添加
            if(inserts.size()>0){
                this.referralApplyMapper.batchInsertSuggestReferral(inserts);
            }
        }
    }


}
