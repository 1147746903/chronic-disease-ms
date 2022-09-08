package com.comvee.cdms.prescription.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.ParamLogConstant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.member.bo.MemberControlTargetBO;
import com.comvee.cdms.member.constant.ControlTargetConstant;
import com.comvee.cdms.prescription.bo.*;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.dto.GetPrescriptionDetailDTO;
import com.comvee.cdms.prescription.dto.PrescriptionDTO;
import com.comvee.cdms.prescription.po.PrescriptionDetailPO;
import com.comvee.cdms.prescription.vo.PrescriptionDetailVO;
import com.comvee.cdms.prescription.vo.PrescriptionMemberVO;
import com.comvee.cdms.prescription.vo.PrescriptionTargetVO;
import com.comvee.cdms.prescription.vo.PrescriptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 李左河
 * @date 2018/8/2 14:59.
 */
@Service("prescriptionTargetService")
public class PrescriptionOfTargetServiceImpl implements PrescriptionOfTargetServiceI {

    @Autowired
    private PrescriptionApiI prescriptionApi;

    @Autowired
    private PrescriptionServiceI prescriptionService;

    /**
     * 获取控制目标
     * @author 李左河
     * @date 2018/8/2 11:55
     * @param prescriptionDTO
     * @return com.comvee.cdms.prescription.model.PrescriptionDetailModel<com.comvee.cdms.prescription.vo.PrescriptionTargetVO>
     *
     */
    @Override
    public PrescriptionDetailVO<PrescriptionTargetVO> loadPrescriptionTarget(PrescriptionDTO prescriptionDTO) {

        PrescriptionDetailVO<PrescriptionTargetVO> model = new PrescriptionDetailVO();
        model.setModuleType(prescriptionDTO.getType());
        model.setModuleName(PrescriptionConstant.MODULE_NAME_TARGET);

        //1、获取控制目标模块
        GetPrescriptionDetailDTO dto = new GetPrescriptionDetailDTO();
        dto.setPrescriptionId(prescriptionDTO.getPrescriptionId());
        dto.setType(PrescriptionConstant.MODULE_TYPE_TARGET);
        PrescriptionDetailPO detail = this.prescriptionService.getPrescriptionDetailByParam(dto);
        //2、判断是否保存过，json是否为空
        if (null != detail ) {
            PrescriptionTargetVO prescriptionTargetVO = null;
            if (0 == detail.getSaveState() || StringUtils.isBlank(detail.getDetailJson())) {
                //3、空，从控制目标表获取
                String memberId = detail.getMemberId();
                ApiRangeBO rangeModel = this.prescriptionApi.getRangeBO(memberId);
                if(null == rangeModel) {
                    rangeModel = this.prescriptionApi.insertMemberRangeWithLock(memberId);
                }
                prescriptionTargetVO = new PrescriptionTargetVO();
                BeanUtils.copyProperties(prescriptionTargetVO, rangeModel);
                prescriptionTargetVO.setLowBmi(ParamLogConstant.RANGE_BMI_STANDARD_LOW + "");
                prescriptionTargetVO.setHighBmi(ParamLogConstant.RANGE_BMI_STANDARD_HIGH + "");

                //获取当前值
                this.fullCurrentValue(prescriptionTargetVO, memberId, prescriptionDTO.getPrescriptionId());

            }else {
                //4、非空，直接返回
                prescriptionTargetVO = JSONObject.parseObject(detail.getDetailJson(), PrescriptionTargetVO.class);
                PrescriptionMemberVO prescriptionMemberVO = this.prescriptionService.getPrescriptMemberInfo(prescriptionDTO.getPrescriptionId());
                if (null != prescriptionMemberVO) {
                    prescriptionTargetVO.setGlycosylatedVal(prescriptionMemberVO.getGlycosylatedVal());
                }
                prescriptionTargetVO.setLowBmi(ParamLogConstant.RANGE_BMI_STANDARD_LOW + "");
                prescriptionTargetVO.setHighBmi(ParamLogConstant.RANGE_BMI_STANDARD_HIGH + "");
            }

            //肾小球滤过率 计算egfr值
            if(0==detail.getSaveState()){
                String  egfrStr = "";
                PrescriptionVO pre = this.prescriptionService.getPrescriptionById(prescriptionDTO.getPrescriptionId());
                ApiMemberArchivesBO memberArchives = this.prescriptionApi.getMemberArchivesById(detail.getMemberId(), pre.getTeamId());
                String archivesJson ="";
                if(null!=memberArchives){
                    archivesJson = memberArchives.getArchivesJson();
                }
                Map<String, Object> archivesMap = JsonSerializer.jsonToMap(archivesJson);
                if(null!=archivesMap && null!=archivesMap.get("lab") && null!=archivesMap.get("basic")){
                    String labs=JsonSerializer.objectToJson(archivesMap.get("lab"));
                    String basics=JsonSerializer.objectToJson(archivesMap.get("basic"));
                    Map<String, Object> lab = JsonSerializer.jsonToMap(labs);
                    Map<String, Object> basic = JsonSerializer.jsonToMap(basics);
                    egfrStr = egfrHandler(lab,basic);
                    prescriptionTargetVO.setEgfr(egfrStr);
                }
            }

            model.setModuleSid(detail.getSid());
            model.setModule(prescriptionTargetVO);
            model.setSaveState(detail.getSaveState());

        }
        return model;
    }
    /**
     * 肾小球滤过率 计算egfr值
     */
    private String egfrHandler(Map<String, Object> lab,Map<String, Object> basic){
        if(basic == null || lab == null){
            return null;
        }
        String scr = StringUtils.converParamToString(lab.get("cr"));
        String sex = StringUtils.converParamToString(basic.get("sex"));
        String birthday = StringUtils.converParamToString(basic.get("birthday"));

        if(StringUtils.isBlank(scr) || StringUtils.isBlank(sex)
                || StringUtils.isBlank(birthday)){
            return null;
        }

        String egfr = "";
        int age = DateHelper.getAge(birthday);
        double f_scr = Double.parseDouble(scr);//*0.01131
        f_scr=f_scr/88.4;
        double d_egfr = 0d;
        double agepow = Math.pow(0.993,age);
        if("1".equals(sex)){ //男
            double flag = f_scr/0.9;
            double bc = 0d;
            if(f_scr<=0.7){
                bc = (Math.pow(flag,-0.411));
            } else {
                bc = (Math.pow(flag,-1.209));
            }
            d_egfr = 141 * bc * agepow;
        }else if ("2".equals(sex)){//女
            double flag = f_scr/0.7;
            double bc = 0d;
            if(f_scr<=0.7){
                bc = (Math.pow(flag,-0.329));
            } else {
                bc = (Math.pow(flag,-1.209));
            }
            d_egfr = 144 * bc * agepow;
        }

        if(d_egfr>0){
            egfr = String.format("%.2f", d_egfr);
        }
        return egfr;
    }

    @Override
    public void modifyPrescriptionTarget(PrescriptionDTO prescriptionDTO) {
        PrescriptionBO prescriptionBO = new PrescriptionBO();
        BeanUtils.copyProperties(prescriptionBO, prescriptionDTO);
        this.prescriptionService.modifyPrescriptionDetail(prescriptionBO);
    }

    @Override
    public PrescriptionTargetVO RecommendationOfTarget(String baseJson,String memberId,String doctorId) {
        PrescriptionTargetVO prescriptionTargetVO = new PrescriptionTargetVO();
//        GetTargetRecommendDTO getTargetRecommendDTO = JSON.parseObject(baseJson ,GetTargetRecommendDTO.class);
//        ApiRangeBO rangeModel = this.prescriptionApi.rangeRecommend(getTargetRecommendDTO);

        ApiRangeBO rangeModel = this.prescriptionApi.getRangeBO(memberId);

        ControlTargetConstant.BaseInfo baseInfo = this.prescriptionApi.baseInfoHandler(memberId,doctorId);
        bloodFatTarget(rangeModel, baseInfo);


//        prescriptionTargetVO = new PrescriptionTargetVO();
        BeanUtils.copyProperties(prescriptionTargetVO, rangeModel);
        prescriptionTargetVO.setLowBmi(ParamLogConstant.RANGE_BMI_STANDARD_LOW + "");
        prescriptionTargetVO.setHighBmi(ParamLogConstant.RANGE_BMI_STANDARD_HIGH + "");
        return prescriptionTargetVO;
    }



    /**
     * 获取当前值
     * @author 李左河
     * @date 2018/8/2 17:43
     * @param prescriptionTargetVO
     * @param memberId
     * @param prescriptionId
     * @return void
     *
     */
    private void fullCurrentValue(PrescriptionTargetVO prescriptionTargetVO, String memberId, String prescriptionId) {
        //获取bim
        ApiMemberBO memberPO = this.prescriptionApi.getMemberByMemberId(memberId);
        if (null != memberPO) {
            prescriptionTargetVO.setBmi(memberPO.getBmi());
        }

        //获取近三个月的空腹血糖（beforeBreakfast），非空腹血糖值(afterBreakfast)
        MemberParamBO memberParamBO = new MemberParamBO();
        memberParamBO.setMemberId(memberId);
        memberParamBO.setParamCode("beforeBreakfast");
        String beforeBreakfast = this.prescriptionApi.getBloodSugarThreeMonthNew(memberParamBO);
        prescriptionTargetVO.setBeforeBreakfast(beforeBreakfast);

        memberParamBO.setParamCode("afterBreakfast");
        String afterBreakfast = this.prescriptionApi.getBloodSugarThreeMonthNew(memberParamBO);
        prescriptionTargetVO.setAfterMeal(afterBreakfast);


        //获取血压值
        ApiBloodPressureBO bloodPressurePO = this.prescriptionApi.getBloodPressureNewByMemberId(memberId);
        if (null != bloodPressurePO) {
            prescriptionTargetVO.setSystolicPress(bloodPressurePO.getSbp());
            prescriptionTargetVO.setDiastolicPress(bloodPressurePO.getDbp());
        }


        //获取血脂情况、糖尿病肾病评估
        CheckoutResultBO resultMap = this.prescriptionApi.getMemberCheckoutResultOfEoh(memberId);
        if (null != resultMap) {
            BeanUtils.copyProperties(prescriptionTargetVO,resultMap);
            //糖化血红蛋白
            PrescriptionVO prescriptionVO = this.prescriptionService.getPrescriptionById(prescriptionId);
            if (null != prescriptionVO) {
                String memberInfoJson = prescriptionVO.getMemberInfoJson();
                if (!StringUtils.isBlank(memberInfoJson)) {
                    Map<String, String> map = JsonSerializer.jsonToStringMap(memberInfoJson);
                    prescriptionTargetVO.setGlycosylatedVal(map.get("glycosylatedVal"));
                }
            }
        }
    }

    private void bloodFatTarget(ApiRangeBO rangeBO,ControlTargetConstant.BaseInfo baseInfo){

        MemberControlTargetBO memberControlTargetBO = ControlTargetConstant.getDefaultControlTarget(baseInfo);

        rangeBO.setHighTCholesterol(memberControlTargetBO.getHighTCholesterol()+"");  //总胆固醇上限
        rangeBO.setHighTCholesterol(memberControlTargetBO.getHighTCholesterol()+"");  //甘油三酯上限
        rangeBO.setLowHDLCholesterol(memberControlTargetBO.getLowHDLCholesterol()+"");  //高密度脂蛋白下限
        rangeBO.setHighLDLCholesterol(memberControlTargetBO.getHighLDLCholesterol()+"");  //低密度脂蛋白上限

    }



}
