package com.comvee.cdms.prescription.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.checkresult.dto.AddCheckoutDTO;
import com.comvee.cdms.checkresult.service.CheckoutServiceI;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.member.dto.MemberDataDTO;
import com.comvee.cdms.prescription.bo.*;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.dto.*;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.*;
import com.comvee.cdms.prescription.vo.*;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeTreeVO;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeVO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import com.comvee.cdms.user.tool.SessionTool;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author 李左河
 * @date 2018/8/3 10:29
 *
 */
@Service("prescriptionService")
public class PrescriptionServiceImpl implements PrescriptionServiceI {

    private final static Logger log = LoggerFactory.getLogger(PrescriptionServiceImpl.class);

    @Autowired
    @Lazy
    private PrescriptionApiI prescriptionApi;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    @Lazy
    private PrescriptionOfDietServiceI prescriptionOfDietService;
    
    @Autowired
    @Lazy
    private PrescriptionOfSportServiceI prescriptionOfSportService;

    @Autowired
    @Lazy
    private PrescriptionOfTargetServiceI prescriptionTargetService;

    @Autowired
    @Lazy
    private PrescriptionOfEduServiceI prescriptionOfEduService;
    
    @Autowired
    @Lazy
    private PrescriptionOfSugarmonitorServiceI prescriptionOfSugarmonitorService;

    @Autowired
    @Lazy
    private PrescriptionOfArchivesI prescriptionOfArchives;

    @Autowired
    @Lazy
    private CheckoutServiceI checkoutServiceI;

    @Override
    public String createPrescription(PrescriptionDTO prescriptionDTO, DoctorDTO doctor) {
//        boolean checkResult = this.prescriptionApi.checkDoctorMemberRelationExists(prescriptionDTO.getMemberId() ,prescriptionDTO.getDoctorId());
//        if(!checkResult){
//            log.warn("医患关系不存在，创建管理处方失败！医生id:{},患者id:{},操作者id:{}" ,prescriptionDTO.getDoctorId() ,prescriptionDTO.getMemberId() , doctor.getDoctorId());
//            throw new BusinessException("不存在有效的医患关系，创建处方失败...");
//        }
        String module = prescriptionDTO.getModule();
        String[] arrayModule = module.split(",");
        String prescriptionId = DaoHelper.getSeq();
        //添加处方
        PrescriptionPO prescription = new PrescriptionPO();
        prescription.setSid(prescriptionId);
        prescription.setMemberInfoJson(prescriptionDTO.getMemberInfoJson());
        prescription.setEduCycle(prescriptionDTO.getEduCycle());
        prescription.setEohType(prescriptionDTO.getEohType());
        prescription.setMemberId(prescriptionDTO.getMemberId());
        prescription.setVersion(prescriptionDTO.getVersion());
        prescription.setModule(prescriptionDTO.getModule());

        prescription.setDoctorId(doctor.getDoctorId());
        prescription.setTeamId(prescriptionDTO.getDoctorId());
        prescription.setHospitalId(doctor.getHospitalId());
        this.prescriptionMapper.insertPrescription(prescription);
        //添加处方模块
        List<PrescriptionDetailPO> listDetail = new ArrayList<PrescriptionDetailPO>(arrayModule.length);
        for(String moduleType : arrayModule){
            PrescriptionDetailPO prescriptionDetail = new PrescriptionDetailPO();
            prescriptionDetail.setSid(DaoHelper.getSeq());
            prescriptionDetail.setMemberId(prescriptionDTO.getMemberId());
            prescriptionDetail.setPrescriptionId(prescriptionId);
            prescriptionDetail.setType(Integer.parseInt(moduleType));
            listDetail.add(prescriptionDetail);
        }
        this.prescriptionMapper.batchInsertPrescriptionDetail(listDetail);
        return prescriptionId;
    }

    @Override
    public PrescriptionDetailVO intelligentRecommendationByModule(PrescriptionDTO prescriptionDTO) {
        //控制目标
        if(prescriptionDTO.getType() == PrescriptionConstant.MODULE_TYPE_TARGET){
            PrescriptionDetailVO model = new PrescriptionDetailVO<PrescriptionTargetVO>();
            PrescriptionTargetVO targetVO = this.prescriptionTargetService.RecommendationOfTarget(prescriptionDTO.getBaseJson(),prescriptionDTO.getMemberId(),prescriptionDTO.getDoctorId());
            model.setModule(targetVO);
//            model.setModuleSid();
            model.setModuleName(PrescriptionConstant.MODULE_NAME_TARGET);
            model.setModuleType(PrescriptionConstant.MODULE_TYPE_TARGET);
            return model;
        }
        else if(prescriptionDTO.getType() == PrescriptionConstant.MODULE_TYPE_MONITOR){
            //监测方案智能推荐
        	PrescriptionDetailVO<PrescriptionForSugarmonitorVO> model = new PrescriptionDetailVO<PrescriptionForSugarmonitorVO>();
        	PrescriptionForSugarmonitorVO diet = this.prescriptionOfSugarmonitorService.intelligentRecommendationOfSugarmonitor(prescriptionDTO.getBaseJson());
            model.setModule(diet);
            model.setModuleSid(diet.getSid());
            model.setModuleName(PrescriptionConstant.MODULE_NAME_MONITOR);
            model.setModuleType(PrescriptionConstant.MODULE_TYPE_MONITOR);
            return model;
        } else if(prescriptionDTO.getType() == PrescriptionConstant.MODULE_TYPE_DIET){

            //饮食智能推荐
            PrescriptionDetailVO<PrescriptionForDietVO> model = new PrescriptionDetailVO<PrescriptionForDietVO>();
            PrescriptionForDietVO diet = this.prescriptionOfDietService.intelligentRecommendationOfDiet(prescriptionDTO.getBaseJson());
            model.setModule(diet);
            model.setModuleSid(diet.getSid());
            model.setModuleName(PrescriptionConstant.MODULE_NAME_DIET);
            model.setModuleType(PrescriptionConstant.MODULE_TYPE_DIET);
            return model;
        } else if(prescriptionDTO.getType() == PrescriptionConstant.MODULE_TYPE_SPORT){
            //运动智能推荐
        	PrescriptionDetailVO<PrescriptionForSportVO> model = new PrescriptionDetailVO<PrescriptionForSportVO>();
        	PrescriptionForSportVO diet = this.prescriptionOfSportService.intelligentRecommendationOfSport(prescriptionDTO.getBaseJson(),prescriptionDTO.getEohType());
            model.setModule(diet);
            model.setModuleSid(diet.getSid());
            model.setModuleName(PrescriptionConstant.MODULE_NAME_SPORT);
            model.setModuleType(PrescriptionConstant.MODULE_TYPE_SPORT);
            return model;
        } else if(prescriptionDTO.getType() == PrescriptionConstant.MODULE_TYPE_EDU){
            //知识智能推荐
            PrescriptionDetailVO<KnowledgeVO> model = this.prescriptionOfEduService.intelligentRecommendationOfEdu(prescriptionDTO);
            return model;
        }
        return null;
    }

    @Override
    public void modifyPrescriptionDetail(PrescriptionBO prescriptionBO) {

        //更新处方模块
        PrescriptionDetailPO prescriptionDetail = new PrescriptionDetailPO();
        prescriptionDetail.setDetailJson(prescriptionBO.getDetailJson());
        prescriptionDetail.setSaveState(1);
        prescriptionDetail.setType(prescriptionBO.getType());
        prescriptionDetail.setPrescriptionId(prescriptionBO.getPrescriptionId());
        this.prescriptionMapper.modifyPrescriptionDetail(prescriptionDetail);

        //更新处方主表
        PrescriptionPO prescription = new PrescriptionPO();
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        if(null!=doctorModel){
            prescription.setDoctorId(doctorModel.getDoctorId());
        }

        prescription.setSchedule(prescriptionBO.getSchedule());
        prescription.setSid(prescriptionBO.getPrescriptionId());
        //封装患者基本信息
        String memberInfoJson = prescriptionBO.getMemberInfoJson();
        Map<String, String> newMap = null;
        if (StringUtils.isBlank(memberInfoJson)) {
            newMap = new HashMap<>(1);
        }else {
            newMap = JsonSerializer.jsonToStringMap(memberInfoJson);
        }
        GetPrescriptionDTO dto = new GetPrescriptionDTO();
        dto.setSid(prescriptionBO.getPrescriptionId());
        PrescriptionPO prescriptionPO = this.prescriptionMapper.getPrescriptionByParam(dto);
        if (null != prescriptionPO) {
            if (!StringUtils.isBlank(prescriptionBO.getDetailJson())) {
                if (prescriptionBO.getType() == 1) {
                    PrescriptionTargetVO prescriptionTargetVO = JSONObject.parseObject(prescriptionBO.getDetailJson(), PrescriptionTargetVO.class);
                    if (null != prescriptionTargetVO && !StringUtils.isBlank(prescriptionTargetVO.getGlycosylatedVal())) {
                        newMap.put("glycosylatedVal",prescriptionTargetVO.getGlycosylatedVal());
                    }
                }
                if (prescriptionBO.getType() == 5) {
                    PrescriptionEduVO prescriptionEduVO = JSONObject.parseObject(prescriptionBO.getDetailJson(), PrescriptionEduVO.class);
                    if (null != prescriptionEduVO && !StringUtils.isBlank(prescriptionEduVO.getGlycosylatedVal())) {
                        newMap.put("glycosylatedVal",prescriptionEduVO.getGlycosylatedVal());
                    }
                }
            }

            String oldMemberInfoJson = prescriptionPO.getMemberInfoJson();
            if (StringUtils.isBlank(oldMemberInfoJson)) {
                prescription.setMemberInfoJson(JSON.toJSONString(newMap));
            }else {
                Map<String, String> oldMap = JsonSerializer.jsonToStringMap(oldMemberInfoJson);
                if (oldMap != null && newMap != null) {
                    for (Map.Entry<String, String> entry: newMap.entrySet()) {
                        if(!StringUtils.isBlank(entry.getValue())){
                            oldMap.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                prescription.setMemberInfoJson(JSON.toJSONString(oldMap));
            }
        }

        this.prescriptionMapper.modifyPrescription(prescription);
    }

    @Override
    public PrescriptionDetailVO loadPrescriptionDetailByModule(PrescriptionDTO prescriptionDTO) {
        PrescriptionVO prescriptionVO = getPrescriptionById(prescriptionDTO.getPrescriptionId());
        if(prescriptionVO == null){
            throw new BusinessException("该处方不存在或已被删除~");
        }
        PrescriptionDetailVO prescriptionDetailVO = new PrescriptionDetailVO();
        if(prescriptionDTO.getType() == PrescriptionConstant.MODULE_TYPE_TARGET){
            //获取控制目标
            prescriptionDetailVO = this.prescriptionTargetService.loadPrescriptionTarget(prescriptionDTO);
        } else if(prescriptionDTO.getType() == PrescriptionConstant.MODULE_TYPE_MONITOR){
        	//获取监测方案
        	PrescriptionForSugarmonitorVO diet = this.prescriptionOfSugarmonitorService.getPrescriptionSugarmonitor(prescriptionDTO.getPrescriptionId());
            prescriptionDetailVO.setModule(diet);
            prescriptionDetailVO.setSaveState(diet.getSaveState());
            prescriptionDetailVO.setModuleName(PrescriptionConstant.MODULE_NAME_MONITOR);
            prescriptionDetailVO.setModuleType(PrescriptionConstant.MODULE_TYPE_MONITOR);
            PrescriptionPO prescriptionPO = this.prescriptionMapper.getPrescriptionById(prescriptionDTO.getPrescriptionId());
            prescriptionDetailVO.setEduCycle(prescriptionPO.getEduCycle());
        } else if(prescriptionDTO.getType() == PrescriptionConstant.MODULE_TYPE_DIET){
        	//获取饮食
            PrescriptionForDietVO diet = this.prescriptionOfDietService.getPrescriptionDiet(prescriptionDTO.getPrescriptionId());
            prescriptionDetailVO.setModuleSid(diet.getSid());
            prescriptionDetailVO.setModule(diet);
            prescriptionDetailVO.setSaveState(diet.getSaveState());
            prescriptionDetailVO.setModuleName(PrescriptionConstant.MODULE_NAME_DIET);
            prescriptionDetailVO.setModuleType(PrescriptionConstant.MODULE_TYPE_DIET);
        } else if(prescriptionDTO.getType() == PrescriptionConstant.MODULE_TYPE_SPORT){
        	//获取运动
            PrescriptionForSportVO sport = this.prescriptionOfSportService.getPrescriptionSport(prescriptionDTO.getPrescriptionId(),prescriptionDTO.getEohType());
            prescriptionDetailVO.setModuleSid(sport.getSid());
            prescriptionDetailVO.setModule(sport);
            prescriptionDetailVO.setSaveState(sport.getSaveState());
            prescriptionDetailVO.setModuleName(PrescriptionConstant.MODULE_NAME_SPORT);
            prescriptionDetailVO.setModuleType(PrescriptionConstant.MODULE_TYPE_SPORT);
        } else if(prescriptionDTO.getType() == PrescriptionConstant.MODULE_TYPE_EDU){
        	//获取知识学习
            prescriptionDetailVO = this.prescriptionOfEduService.getPrescriptionEdu(prescriptionDTO);
        } else if (prescriptionDTO.getType() == PrescriptionConstant.MODULE_TYPE_ARCHIVES) {
            //获取患者档案
            prescriptionDetailVO = this.prescriptionOfArchives.getPrescriptionArchives(prescriptionDTO);
        }
        prescriptionDetailVO.setDoctorId(prescriptionVO.getDoctorId());
        return prescriptionDetailVO;
    }

    @Override
    public PrescriptionVO getPrescriptionById(String prescriptId) {
        GetPrescriptionDTO dto = new GetPrescriptionDTO();
        dto.setSid(prescriptId);
        PrescriptionPO prescription = this.prescriptionMapper.getPrescriptionByParam(dto);
        if(prescription!=null){
            PrescriptionVO model = new PrescriptionVO();
            BeanUtils.copyProperties(model,prescription);
            ApiDoctorBO apiDoctorBO = this.prescriptionApi.getDoctorById(prescription.getDoctorId());
            if(null!=apiDoctorBO){
                model.setDoctorName(apiDoctorBO.getDoctorName());
            }
            return model;
        }
        return null;
    }

    @Override
    public void deleteUnFinalPrescriptionByMid(String memberId, String currentLeaderId) {
        /**删除管理处方，删除管理处方模块**/
        //获取要删除的管理处方列表
        GetPrescriptionDTO dto = new GetPrescriptionDTO();
        dto.setDoctorId(currentLeaderId);
        dto.setMemberId(memberId);
        dto.setComplete(false);
        List<PrescriptionPO> deleteList = this.prescriptionMapper.listPrescriptionByParam(dto);
        //删除管理处方和模块
        if(deleteList!=null){
            List<String> delSidList = new ArrayList<String>(deleteList.size());
            for(PrescriptionPO po : deleteList){
                delSidList.add(po.getSid());
            }
            DelPrescriptionDTO delDto1 = new DelPrescriptionDTO();
            delDto1.setSidList(delSidList);
            this.prescriptionMapper.deletePrescription(delDto1);
            DelPrescriptionDetailDTO delDto2 = new DelPrescriptionDetailDTO();
            delDto2.setPrescriptionIdList(delSidList);
            this.prescriptionMapper.deletePrescriptionDetail(delDto2);
        }
    }

    @Override
    public List<PrescriptionVO> listPrescriptionOfMember(GetPrescriptionDTO dto) {
        List<PrescriptionPO> list = this.prescriptionMapper.listPrescriptionByParam(dto);
        if(list!=null){
            List<PrescriptionVO> result = new ArrayList<PrescriptionVO>(list.size());
            for(PrescriptionPO po:list){
                PrescriptionVO model = new PrescriptionVO();
                if (null != po) {
                    BeanUtils.copyProperties(model,po);
                }
                result.add(model);
            }
            return result;
        }
        return null;
    }

    /**
     * 获取管理处方患者信息-通用方法
     * @param prescriptionId 处方编号
     * @return
     */
    @Override
    public PrescriptionMemberVO getPrescriptMemberInfo(String prescriptionId){

        GetPrescriptionDTO dto = new GetPrescriptionDTO();
        dto.setSid(prescriptionId);
        PrescriptionPO prescription = this.prescriptionMapper.getPrescriptionByParam(dto);

        String memberInfo ="";
        if(null!=prescription){
            memberInfo = prescription.getMemberInfoJson();
        }
        PrescriptionMemberVO member = null;
        //将保存大基本信息给返回对象
        member = JSONObject.parseObject(memberInfo,PrescriptionMemberVO.class);
        if (prescription.getSchedule() == 0) {
             //获取大档案信息并给返回对象设值
            ApiMemberArchivesBO archives = null;
            if (null != prescription) {
                archives = prescriptionApi.getMemberArchivesById(prescription.getMemberId(), prescription.getTeamId());
            }
            if (null != archives) {
                String baseInfo = archives.getBasic();
                JSONObject base = StringUtils.isBlank(baseInfo) ? null : JSONObject.parseObject(baseInfo);
                String diabetesJson = archives.getDiabetesStatus();
                JSONObject diabetes = StringUtils.isBlank(diabetesJson) ? null : JSONObject.parseObject(diabetesJson);
                String diabetesComplication = archives.getDiabetesComplication();
                JSONObject complication = StringUtils.isBlank(diabetesComplication) ? null : JSONObject.parseObject(diabetesComplication);
                String anamnesis = archives.getAnamnesis();
                JSONObject anamnesisObj = StringUtils.isBlank(anamnesis) ? null : JSONObject.parseObject(anamnesis);
                String archivesJson = archives.getArchivesJson();
                JSONObject archivesJsonObj = StringUtils.isBlank(archivesJson) ? null : JSONObject.parseObject(archivesJson);
                //有处方患者信息
                if (member != null) {
                    if (base != null) {
                        if (StringUtils.isBlank(member.getLabourIntensity())) {
                            member.setLabourIntensity(base.getString("labourIntensity"));
                        }
                    }
                    if (complication != null) {
                        if (member.getHyp() == null) {
                            //member.setHyp(anamnesisObj.getInteger("essential_hyp"));
                            member.setHyp(2);
                        }
                        member.setChd(complication.getString("chd"));
                    }
                } else {
                    member = new PrescriptionMemberVO();
                    if (base != null) {
                        member.setLabourIntensity(base.getString("labourIntensity"));
                        member.setBirthday(base.getString("birthday"));
                        member.setHeight(base.getString("height"));
                        member.setMemberName(base.getString("memberRealName"));
                        member.setSex(base.getString("sex"));
                        member.setWeight(base.getString("weight"));
                    }

                    if (diabetes != null) {
                        member.setDiabetesType(diabetes.getString("diabetesType"));
                    }

                    if (complication != null) {
                        if (member.getHyp() == null) {
                            //member.setHyp(anamnesisObj.getInteger("essential_hyp"));
                            member.setHyp(2);
                        }
                        member.setChd(complication.getString("chd"));
                    }
                    if (archivesJsonObj != null) {
                        JSONObject jsonObject = archivesJsonObj.getJSONObject("history");
                        String strXy = null;
                        if (jsonObject != null) {
                            strXy = jsonObject.getString("bs_smoke");
                        }
                        member.setSmoke(strXy);
                    }
                }
            }
        }
        if (!StringUtils.isBlank(member.getBmi()) && StringUtils.isBlank(member.getBodyType())){
            Double bmi = Double.parseDouble(member.getBmi());
            if (bmi < 18.5){
                member.setBodyType("0");
            }else if ( bmi>= 18.5 && bmi <24){
                member.setBodyType("1");
            }else if (bmi>= 24 && bmi <28){
                member.setBodyType("2");
            }else {
                member.setBodyType("3");
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("memberId",member.getMemberId());
        map.put("checkoutCode","add_check_waist");
        String waistline = this.checkoutServiceI.getNewestCheckoutResult(map);
        if (!StringUtils.isBlank(waistline)){
            member.setWaistline(waistline);
        }
        return member;
    }

    @Override
    public PageResult<PrescriptionVO> listPrescriptionPage(GetPrescriptionPageDTO pageDTO) {
        String memberId = pageDTO.getMemberId();
        String memberName = pageDTO.getMemberName();
        String memberNamePy = pageDTO.getMemberNamePy();
        String mobilePhone = pageDTO.getMobilePhone();
        //设置参数
        GetPrescriptionDTO dto = new GetPrescriptionDTO();

        if(null!=pageDTO.getSubmit() && pageDTO.getSubmit()==false){
            List<String> doctorIdList = this.prescriptionApi.getDoctorIdList(pageDTO.getDoctorId(),null);
            dto.setDoctorIdList(doctorIdList);
            dto.setHospitalId(null);  //工作台查询当前医生团队的
        }
        /*else{
            dto.setTeamId(pageDTO.getDoctorId());
        }*/
        dto.setComplete(pageDTO.getSubmit());
        dto.setHospitalId(pageDTO.getHospitalId());
        dto.setMemberName(pageDTO.getMemberName());
        dto.setOperatorId(pageDTO.getOperatorId());
        dto.setEohType(pageDTO.getEohType());

        //查询患者信息
        List<ApiMemberBO> members = null;
        if(!StringUtils.isBlank(memberId)||
                !StringUtils.isBlank(memberName)||
                !StringUtils.isBlank(memberNamePy)||
                !StringUtils.isBlank(mobilePhone)){

            GetMemberPerDTO getMemberPerDTO = new GetMemberPerDTO();
            getMemberPerDTO.setMemberName(memberName);
            getMemberPerDTO.setMemberNamePy(memberNamePy);
            getMemberPerDTO.setMobilePhone(mobilePhone);
            getMemberPerDTO.setMemberId(memberId);
            members = this.prescriptionApi.listMemberByWhere(getMemberPerDTO);
            if(members!=null && members.size()>0){
                List<String> memberIds = new ArrayList<String>(members.size());
                for(ApiMemberBO member : members){
                    memberIds.add(member.getMemberId());
                }
                dto.setMemberIdList(memberIds);
            }
        }

        //获取
        PageHelper.startPage(pageDTO.getPage(),pageDTO.getRows());
        if (!StringUtils.isBlank(pageDTO.getHospitalId())){  //有切换医院权限
            dto.setTeamId(null);
        }
        List<PrescriptionPO> list = this.prescriptionMapper.listPrescriptionByParam(dto);
        PageResult<PrescriptionPO> tempPageResult = new PageResult<PrescriptionPO>(list);

        if(list!=null&&list.size()>0){
            List<PrescriptionVO> result = new ArrayList<PrescriptionVO>(list.size());
            List<String> memberIds = new ArrayList<String>();
            for(PrescriptionPO po:list){
                PrescriptionVO model = new PrescriptionVO();
                if (null != po) {
                    BeanUtils.copyProperties(model,po);
                }
                if(members == null || members.size()>0){
                    memberIds.add(po.getMemberId());
                }
                ApiDoctorBO apiDoctorBO = this.prescriptionApi.getDoctorById(model.getDoctorId());
                if(null!= apiDoctorBO){
                    model.setDoctorName(apiDoctorBO.getDoctorName());
                }
                result.add(model);
            }
            PageResult<PrescriptionVO> webPageResult = new PageResult<PrescriptionVO>(result);
            webPageResult.setTotalPages(tempPageResult.getTotalPages());
            webPageResult.setTotalRows(tempPageResult.getTotalRows());
            webPageResult.setPageNum(tempPageResult.getPageNum());
            webPageResult.setPageSize(tempPageResult.getPageSize());

            //处理患者信息
            if(members == null){
                GetMemberPerDTO getMemberPerDTO = new GetMemberPerDTO();
                getMemberPerDTO.setMemberIds(memberIds);
                members = this.prescriptionApi.listMember(getMemberPerDTO);
            }
            if(members!=null && members.size()>0){
                if(!StringUtils.isBlank(memberId)){
                    ApiMemberBO member = members.get(0);
                    for(PrescriptionVO model:webPageResult.getRows()){
                        if(member.getMemberId().equals(model.getMemberId())){
                            model.setSex(member.getSex());
                            model.setMemberName(member.getMemberName());
                            model.setMobilePhone(member.getMobilePhone());
                            model.setAge(DateHelper.getAge(member.getBirthday()));
                            model.setInHos(member.getInHos());
                            model.setDepartmentId(member.getDepartmentId());
                        }
                    }
                } else {
                    for(ApiMemberBO member : members){
                        String mid = member.getMemberId();
                        for(PrescriptionVO model:webPageResult.getRows()){
                            if(mid.equals(model.getMemberId())){
                                model.setSex(member.getSex());
                                model.setMemberName(member.getMemberName());
                                model.setMobilePhone(member.getMobilePhone());
                                model.setAge(DateHelper.getAge(member.getBirthday()));
                                model.setInHos(member.getInHos());
                                model.setDepartmentId(member.getDepartmentId());
                            }
                        }
                    }
                }
            }
            //返回
            return webPageResult;
        }
        //返回
        return new PageResult<PrescriptionVO>();
    }

    @Override
    public PrescriptionDetailPO getPrescriptionDetailByParam(GetPrescriptionDetailDTO dto) {
        return this.prescriptionMapper.getPrescriptionDetailByParam(dto);
    }


	@Override
	public List<PrescriptionSugarmonitorVO> loadEohSugarmonitorByItem(GetPrescriptionSugarmonitorDTO sugarmonitor) {

		return this.prescriptionMapper.loadEohSugarmonitorByItem(sugarmonitor);
	}

    @Override
    public void modifyPrescriptionByModule(PrescriptionDTO prescriptionDTO, DoctorDTO doctorModel) {
        PrescriptionVO prescriptionVO = getPrescriptionById(prescriptionDTO.getPrescriptionId());
        if(prescriptionVO == null){
            throw new BusinessException("数据不存在或已被删除，请确认~");
        }
        Integer type = prescriptionDTO.getType();
        if (null != type) {
            switch (type) {
                case PrescriptionConstant.MODULE_TYPE_TARGET:
                    this.prescriptionTargetService.modifyPrescriptionTarget(prescriptionDTO);
                    //控制目标
                    break;
                case PrescriptionConstant.MODULE_TYPE_MONITOR:
                    //监测方案
                	this.prescriptionOfSugarmonitorService.modifySugarmonitor(prescriptionDTO);
                    break;
                case PrescriptionConstant.MODULE_TYPE_DIET:
                    //饮食
                    this.prescriptionOfDietService.modifyPrescriptionDiet(prescriptionDTO);
                    break;
                case PrescriptionConstant.MODULE_TYPE_SPORT:
                    //运动
                	this.prescriptionOfSportService.modifySport(prescriptionDTO);
                    break;
                case PrescriptionConstant.MODULE_TYPE_EDU:
                    //知识
                    this.prescriptionOfEduService.modifyPrescriptionEdu(prescriptionDTO);
                    break;
                case PrescriptionConstant.MODULE_TYPE_ARCHIVES:
                    //患者档案
                    this.prescriptionOfArchives.modifyPrescriptionArchives(prescriptionDTO);
                default:
                    break;
            }
        }

        //提交并下发
        if (prescriptionDTO.getSchedule() == 3) {
            if(prescriptionVO.getSchedule() == 3){
                throw new BusinessException("处方已提交，请勿重复提交");
            }
            //将管理处方信息，同步到档案
            this.synchronizePrescription(prescriptionDTO.getPrescriptionId(), doctorModel);


            //下发管理处方
            String name = "糖尿病自我管理处方";
            if (prescriptionDTO.getEohType()!=null && !StringUtils.isBlank(String.valueOf(prescriptionDTO.getEohType()))) {
                if (prescriptionDTO.getEohType() == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_FAT) {
                    name = "肥胖自我管理处方";
                    // 同步腰围、身高、体重到检验检查
                    if (!StringUtils.isBlank(prescriptionDTO.getMemberInfoJson())) {
                        PrescriptionMemberVO member = JSONObject.parseObject(prescriptionDTO.getMemberInfoJson(), PrescriptionMemberVO.class);
                        AddCheckoutDTO addCheckoutDTO = new AddCheckoutDTO();
                        addCheckoutDTO.setCheckoutTitle("生命体征记录");
                        addCheckoutDTO.setMemberId(member.getMemberId());
                        addCheckoutDTO.setVisitNo("-1");
                        addCheckoutDTO.setYzId("-1");
                        addCheckoutDTO.setRecordOrigin(4);
                        List<Map<String, String>> list = new ArrayList<>();
                        if (member != null) {
                            if(!StringUtils.isBlank(member.getWaistline())) {
                                Map<String, String> wMAP = new HashMap<>();
                                wMAP.put("name", "腰围");
                                wMAP.put("value", member.getWaistline());
                                wMAP.put("code", "add_check_waist");
                                wMAP.put("unit", "cm");
                                list.add(wMAP);
                            }
                            if(!StringUtils.isBlank(member.getHeight())) {
                                Map<String, String> wMAP = new HashMap<>();
                                wMAP.put("name", "身高");
                                wMAP.put("value", member.getHeight());
                                wMAP.put("code", "add_check_height");
                                wMAP.put("unit", "cm");
                                list.add(wMAP);
                            }
                            if(!StringUtils.isBlank(member.getWeight())) {
                                Map<String, String> wMAP = new HashMap<>();
                                wMAP.put("name", "体重");
                                wMAP.put("value", member.getWeight());
                                wMAP.put("code", "add_check_weight");
                                wMAP.put("unit", "kg");
                                list.add(wMAP);
                            }
                            if(!StringUtils.isBlank(member.getBmi())) {
                                Map<String, String> wMAP = new HashMap<>();
                                wMAP.put("name", "bmi");
                                wMAP.put("value", member.getBmi());
                                wMAP.put("code", "add_check_bmi");
                                list.add(wMAP);
                            }
                        }
                        addCheckoutDTO.setDetailJSON(JSONObject.toJSONString(list));
                        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
                        DoctorPO doctorPO = new DoctorPO();
                        BeanUtils.copyProperties(doctorPO, doctorSessionBO);
                        if (list.size()>0) {
                            this.checkoutServiceI.addCheckout(addCheckoutDTO, doctorPO);
                        }
                    }
                }
            }
            this.handDownPrescription(prescriptionDTO.getPrescriptionId(), doctorModel,name);

            //修改主表下发状态
            PrescriptionPO prescription = new PrescriptionPO();
            prescription.setHandDown(1);
            prescription.setSid(prescriptionDTO.getPrescriptionId());
            prescription.setDoctorId(doctorModel.getDoctorId());
            this.prescriptionMapper.modifyPrescription(prescription);
            //服务扣次
            this.prescriptionApi.useService(prescription.getSid());
        }
    }

    @Override
    //@Cacheable(cacheNames = "prescriptionResources", key = "'foodItem'+#dto.type+#dto.param")
    public List<FoodItemBO> listIngredientsItem(GetFoodItemDTO dto){
        return this.prescriptionOfDietService.listIngredientsItem(dto);
    }

    @Override
    @Cacheable(cacheNames = "prescriptionResources", key = "'foodItem'+#dto.type+#dto.param")
    public List<FoodItemBO> listIngredientsTypeOfFood(GetFoodItemDTO dto){
        return this.prescriptionOfDietService.listIngredientsTypeOfFood(dto);
    }

    @Override
    public void handDownPrescription(String prescriptionId, DoctorDTO doctorModel,String name) {
        PrescriptionVO prescriptionVO = this.getPrescriptionById(prescriptionId);

        if (null != prescriptionVO) {
/*            long count = this.prescriptionApi.countDoctorMemberRelation(prescriptionVO.getTeamId() ,prescriptionVO.getMemberId());
            if(count == 0){
                return;
            }*/
            String title = "管理处方下发成功通知";
            String date = DateHelper.getToday();
            String time = DateHelper.getDate(new Date(),DateHelper.TIME_FORMAT);

            PrescriptionHandDownBO prescriptionHandDownBO = new PrescriptionHandDownBO();
            prescriptionHandDownBO.setContent("");
            prescriptionHandDownBO.setDate(date);
            prescriptionHandDownBO.setLogId(prescriptionId);
            prescriptionHandDownBO.setName(name);
            //管理处方类型
            prescriptionHandDownBO.setTextType(prescriptionVO.getEohType());
            prescriptionHandDownBO.setTime(time);
            prescriptionHandDownBO.setTitle(title);
            prescriptionHandDownBO.setUrl("/prescription/mobile/care_prescript.html?doctorId="+doctorModel.getDoctorId()+
                    "&memberId="+prescriptionVO.getMemberId()+"&prescriptionId="+ Base64Util.encodeBase64(prescriptionId)+"&isApp=1");

            PrescriptionDialogueBO prescriptionDialogueBO = new PrescriptionDialogueBO();
            prescriptionDialogueBO.setSenderId(doctorModel.getDoctorId());
            prescriptionDialogueBO.setTextType(0);
            prescriptionDialogueBO.setMemberId(prescriptionVO.getMemberId());
            prescriptionDialogueBO.setDoctorId(prescriptionVO.getTeamId());
            prescriptionDialogueBO.setMsg(title);

            //下发管理处方卡片
            this.prescriptionApi.sendPrescriptionDialogue(prescriptionDialogueBO,prescriptionHandDownBO);
            //添加管理处方微信消息
            this.prescriptionApi.addPrescriptionWechatMessage(prescriptionVO);
        }
    }

    @Override
    public void deletePrescriptionById(PrescriptionDTO prescriptionDTO) {
        PrescriptionPO prescriptionPO = new PrescriptionPO();
        prescriptionPO.setSid(prescriptionDTO.getPrescriptionId());
        prescriptionPO.setIsValid(0);
        this.prescriptionMapper.modifyPrescription(prescriptionPO);
    }

    @Override
    public long countPrescription(CountPrescriptionDTO countPrescriptionDTO) {
        List<String> doctorIdList = this.prescriptionApi.getDoctorIdList(countPrescriptionDTO.getDoctorId(),null);
        countPrescriptionDTO.setDoctorIdList(doctorIdList);
        return this.prescriptionMapper.countPrescription(countPrescriptionDTO);
    }

    @Override
    public List<CountMonthPrescriptionPO> countMonthPrescription(CountMonthPrescriptionDTO countMonthPrescriptionDTO) {
        return this.prescriptionMapper.countMonthPrescription(countMonthPrescriptionDTO);
    }

    @Override
    public PageResult<MemberPrescriptionPO> listMemberPrescription(PageRequest pr, ListMemberPrescriptionDTO listMemberPrescriptionDTO) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<MemberPrescriptionPO> list = this.prescriptionMapper.listMemberPrescription(listMemberPrescriptionDTO);
        return new PageResult<>(list);
    }

    @Override
    public List<PrescriptionKnowledgePO> listPrescriptionKnowledge(String prescriptionId) {
        return this.prescriptionMapper.listPrescriptionKnowledge(prescriptionId);
    }

    @Override
    public ArticleVO loadArticleInfo(String eohKnowledgeId ,String memberId) {
        //点击埋点
        this.prescriptionApi.incrMemberArticleClick(memberId);

        PrescriptionKnowledgePO prescriptionKnowledgePO = this.prescriptionMapper.loadEohKnowledgeById(eohKnowledgeId);
        if(prescriptionKnowledgePO == null){
            return null;
        }
        //根据文章id获取文章正文信息
        ApiArticleTxtBO apiArticleTxtBO = this.prescriptionApi.loadArticleTxtByArticleId(Long.parseLong(prescriptionKnowledgePO.getArticleId()));
        if(apiArticleTxtBO == null){
            return null;
        }
        ArticleVO articleVO = new ArticleVO();
        articleVO.setContent(apiArticleTxtBO.getContent());
        articleVO.setPushDt(prescriptionKnowledgePO.getPlanPushDt());
        articleVO.setTitle(prescriptionKnowledgePO.getTitle());

        this.prescriptionMapper.updateEohKnowledgeLearn(eohKnowledgeId);
        return articleVO;
    }


    /**
     * 将管理处方信息，同步到档案
     * @author 李左河
     * @date 2018/8/15 10:38
     * @param prescriptionId
     * @return void
     *
     */
    private void synchronizePrescription(String prescriptionId, DoctorDTO doctorModel) {
        //获取主表信息
        GetPrescriptionDTO dto = new GetPrescriptionDTO();
        dto.setSid(prescriptionId);
        PrescriptionPO prescriptionPO = this.prescriptionMapper.getPrescriptionByParam(dto);
        if (null != prescriptionPO) {
            String memberInfoJson = prescriptionPO.getMemberInfoJson();
            if (!StringUtils.isBlank(memberInfoJson)) {
                MemberInfoBO memberInfoBO = JsonSerializer.jsonToObject(memberInfoJson, MemberInfoBO.class);
                if (null != memberInfoBO) {
                    //同步患者信息
                    this.prescriptionApi.synchronizeMember(memberInfoBO);
                }
            }
        }

        //同步子模块
        GetPrescriptionDetailDTO getPrescriptionDetailDTO = new GetPrescriptionDetailDTO();
        getPrescriptionDetailDTO.setPrescriptionId(prescriptionId);
        List<PrescriptionDetailPO> prescriptionDetailPOList = this.prescriptionMapper.listPrescriptionDetailByParam(getPrescriptionDetailDTO);
        if (null != prescriptionDetailPOList && prescriptionDetailPOList.size() > 0) {
            for (PrescriptionDetailPO prescriptionDetailPO: prescriptionDetailPOList) {
                Integer type = prescriptionDetailPO.getType();
                if (null != type) {
                    switch (type) {
                        case 1:
                            //1控制目标，
                            this.prescriptionApi.synchronizeTarget(prescriptionDetailPO, doctorModel);
                            continue;
                        case 2:
                            //2监测方案，
                        	this.prescriptionApi.synchronizeMonitor(prescriptionDetailPO, doctorModel);
                            continue;
                        case 3:
                            //3饮食，
                            this.prescriptionApi.synchronizeDiet(prescriptionDetailPO, doctorModel);
                            continue;
                        case 4:
                            //4运动，
                        	this.prescriptionApi.synchronizeSport(prescriptionDetailPO, doctorModel);
                            continue;
                        case 5:
                            //5知识学习
                            this.prescriptionApi.synchronizeEdu(prescriptionDetailPO);
                            continue;
                        default:
                            continue;
                    }
                }
            }
        }

    }

    @Override
    public Integer getHandDownCount(String doctorId) {
        return this.prescriptionMapper.getHandDownCount(doctorId);
    }

    @Override
    public List<KnowledgeTreeVO> knowledgeTree(PrescriptionEduDTO prescriptionEduDTO) {

        List<KnowledgeTreeVO> list = this.prescriptionOfEduService.knowledgeTree(prescriptionEduDTO);
        return list;
    }

    @Override
    public List<KnowledgeTreeVO> loadArticle(PrescriptionEduDTO prescriptionEduDTO, PagerBO pager) {
        return this.prescriptionOfEduService.loadArticle(prescriptionEduDTO, pager);
    }

    @Override
    public Long countPrescriptionForMember(String memberId){
        return  this.prescriptionMapper.countPrescriptionForMember(memberId);
    }

    @Override
    public long countNewPrescription(SynthesizeDataDTO synthesizeDataDTO) {
        return this.prescriptionMapper.countNewPrescription(synthesizeDataDTO);
    }

    @Override
    public PrescriptionPO getEarlyMemberPrescription(SynthesizeDataDTO synthesizeDataDTO) {
        List<PrescriptionPO> list = this.prescriptionMapper.getEarlyMemberPrescription(synthesizeDataDTO);
        PrescriptionPO prescriptionPO = null;
        if (list != null && list.size() > 0){
            prescriptionPO = list.get(0);
        }
        return prescriptionPO;
    }

    @Override
    public PrescriptionDetailPO getNewPrescriptionDetail(MemberDataDTO memberDataDTO) {
        List<PrescriptionDetailPO> list = this.prescriptionMapper.getPrescriptionDetail(memberDataDTO);
        PrescriptionDetailPO detailPO = new PrescriptionDetailPO();
        if (list != null && list.size() > 0){
            detailPO = list.get(0);
        }
        return detailPO;
    }

    @Override
    public List<PrescriptionPO> listPrescriptionOfStatistics(GetStatisticsDTO dto) {
        return this.prescriptionMapper.listPrescriptionOfStatistics(dto);
    }

    @Override
    public List<CountMonthPrescriptionPO> countMonthPrescription(GetStatisticsDTO dto) {
        return this.prescriptionMapper.countMonthPrescriptionForHos(dto);
    }

    @Override
    public long countPrescription(GetStatisticsDTO dto) {
        return this.prescriptionMapper.countPrescriptionForHos(dto);
    }

    @Override
    public void addMemberDataOfPrescription(String type, String dataJson , String doctorId) {
        if(PrescriptionConstant.ADD_MEMBER_DATA_BMI_TYPE.equals(type)){
            this.prescriptionApi.addBmi(dataJson);
        } else if(PrescriptionConstant.ADD_MEMBER_DATA_BP_TYPE.equals(type)){
            this.prescriptionApi.addBloodPressure(dataJson);
        } else if(PrescriptionConstant.ADD_MEMBER_DATA_BS_TYPE.equals(type)){
            this.prescriptionApi.addBloodSugar(dataJson);
            this.prescriptionApi.addHba1c(dataJson);
        } else if(PrescriptionConstant.ADD_MEMBER_DATA_BT_TYPE.equals(type)){
            this.prescriptionApi.addBloodFat(dataJson ,doctorId);
        }
    }

    @Override
    public MemberLatestSignVO getMemberLatestSign(String memberId) {
        return this.prescriptionApi.getMemberLatestSign(memberId);
    }

    @Override
    public PrescriptionDetailPO getNewPrescriptionDetailByType(GetPrescriptionDetailDTO dto) {
        return this.prescriptionMapper.getNewPrescriptionDetailByType(dto);
    }


    @Override
    public PageResult<NutritionCateringPO> listRecipetemplates(String doctorId,String mealsModel,String eohType,int rows,int page,String nutritionCateringId) {
        Map<String,String> map = new HashMap<>();
        map.put("doctorId",doctorId);
        map.put("eohType",eohType);
        map.put("mealsModel",mealsModel);
        map.put("id",nutritionCateringId);
        PageHelper.startPage(page,rows);
        List<NutritionCateringPO> list = this.prescriptionMapper.listCustomRecipetemplates(map);
        PageResult<NutritionCateringPO> result = new PageResult<>(list);
        return result;
    }

    @Override
    public String insertCustomRecipes(String nutritionCateringJson,Integer foodVersion,String doctorId,Integer eohType) {
        String nid = this.prescriptionOfDietService.insertCustomRecipesDiet(nutritionCateringJson,foodVersion,doctorId,eohType);
        return nid;
    }

    @Override
    public String modifyCustomRecipes(String nutritionCateringJson,String recipesCaloricId,String nutritionCateringId,String mealsModel,String eohType) {
        NutritionCateringPO nutritionCateringPO = this.saveCustomRecipe(nutritionCateringJson,Integer.parseInt(eohType));
        if (nutritionCateringPO!=null){
            String md5 = nutritionCateringPO.getRecipesCaloricRelationMD5();
            String objectToJson = JsonSerializer.objectToJson(nutritionCateringPO);
            Map<String, String> jsonToStringMap = JsonSerializer.jsonToStringMap(objectToJson);
            jsonToStringMap.put("sid",recipesCaloricId);
            jsonToStringMap.put("id",nutritionCateringId);
            jsonToStringMap.put("md5", md5);
            jsonToStringMap.put("mealsModel",mealsModel);
            jsonToStringMap.put("eohType",eohType);
            this.prescriptionMapper.modifyCustomRecipe(jsonToStringMap);
            jsonToStringMap.remove("md5");
            md5 = nutritionCateringPO.getNutritionCateringMD5();
            jsonToStringMap.put("md5", md5);
            this.prescriptionMapper.modifyCustomRecipeCatering(jsonToStringMap);
            return "success";
        }
        return null;
    }

    @Override
    public void delCustomRecipes(String nutritionCateringId) {
        this.prescriptionMapper.delCustomRecipes(nutritionCateringId);
    }

    @Override
    public List<FoodNutrientBO> listFoodNutrient(String id,String name) {
        return this.prescriptionOfDietService.listFoodNutrient(id,name);
    }

    @Override
    public  Map<String,Object> getFoodcookbook(String labourIntensity, String bodyType, String mealsModel,Double height,String prescriptionId,String sex,String isjejunitas,Integer type) {
        return this.prescriptionOfDietService.getFoodcookbook(labourIntensity,bodyType,mealsModel,height,prescriptionId,sex,isjejunitas,type);
    }

    /**
     * 保存自定义食谱2
     * @param nutritionCateringJson
     * @return
     */
    private NutritionCateringPO saveCustomRecipe(String nutritionCateringJson,Integer eohType){
        NutritionCateringPO nutritionCateringPO = null;
        Map<String, Object> recMap = null;
        List<JSONObject> breakfastFoodList1 = new ArrayList<JSONObject>();
        List<JSONObject> lunchFoodList2 = new ArrayList<JSONObject>();
        List<JSONObject> dinnerFoodList3 = new ArrayList<JSONObject>();
        if(!StringUtils.isBlank(nutritionCateringJson)) {
            nutritionCateringPO = new NutritionCateringPO();
            nutritionCateringPO.setVersion(2);
            NutritionCateringItemVO nutritionCateringItem = JSONObject.parseObject(nutritionCateringJson,NutritionCateringItemVO.class);
            nutritionCateringPO.setMealsModel(nutritionCateringItem.getMealsModel());
            nutritionCateringPO.setRecipeName(nutritionCateringItem.getName());  //设置食谱名称
            recMap = new HashMap<>(3);

            String tempObject = nutritionCateringItem.getBreakfastFoodJson();
            if(!StringUtils.isBlank(tempObject)) {
                breakfastFoodList1 =JSONObject.parseArray(tempObject,JSONObject.class);
                nutritionCateringPO.setBreakfastFoodJson(tempObject.toString());
            }
            tempObject = nutritionCateringItem.getBreakfastFoodAddJson();
            if(!StringUtils.isBlank(tempObject)) {
                List<JSONObject> tempbreakfastFoodList = JSONObject.parseArray(tempObject,JSONObject.class);
                breakfastFoodList1.addAll(tempbreakfastFoodList);
                nutritionCateringPO.setBreakfastFoodAddJson(tempObject.toString());
            }
            if(breakfastFoodList1!=null && breakfastFoodList1.size()>0){
                recMap.put("breakfastFoodList", breakfastFoodList1);
            }

            tempObject = nutritionCateringItem.getLunchFoodJson();
            if(!StringUtils.isBlank(tempObject)) {
                lunchFoodList2 = JSONObject.parseArray(tempObject,JSONObject.class);
                nutritionCateringPO.setLunchFoodJson(tempObject.toString());
            }
            tempObject = nutritionCateringItem.getLunchFoodAddJson();
            if(!StringUtils.isBlank(tempObject)) {
                List<JSONObject> templunchFoodList = JSONObject.parseArray(tempObject,JSONObject.class);
                lunchFoodList2.addAll(templunchFoodList);
                nutritionCateringPO.setLunchFoodAddJson(tempObject.toString());
            }
            if(lunchFoodList2!=null&&lunchFoodList2.size()>0){
                recMap.put("lunchFoodList", lunchFoodList2);
            }

            tempObject = nutritionCateringItem.getDinnerFoodJson();
            if(!StringUtils.isBlank(tempObject)) {
                dinnerFoodList3 = JSONObject.parseArray(tempObject,JSONObject.class);
                nutritionCateringPO.setDinnerFoodJson(tempObject.toString());
            }
            tempObject = nutritionCateringItem.getDinnerFoodAddJson();
            if(!StringUtils.isBlank(tempObject)) {
                List<JSONObject> tempdinnerFoodList = JSONObject.parseArray(tempObject,JSONObject.class);
                dinnerFoodList3.addAll(tempdinnerFoodList);
                nutritionCateringPO.setDinnerFoodAddJson(tempObject.toString());
            }
            if(dinnerFoodList3!=null && dinnerFoodList3.size()>0){
                recMap.put("dinnerFoodList", dinnerFoodList3);
            }
        }

        if(recMap!=null&&nutritionCateringPO!=null){
            String programmeJson = JSON.toJSONString(recMap);
            nutritionCateringPO.setProgrammeJson(programmeJson);
            List<Map<String, Object>> breakfastFoodList = nutritionCateringPO.getBreakfastFoodList();
            List<Map<String, Object>> lunchFoodList = nutritionCateringPO.getLunchFoodList();
            List<Map<String, Object>> dinnerFoodList = nutritionCateringPO.getDinnerFoodList();
            double heat = 0;
            double foodNum=0,greaseNum=0,fruitsNum=0,meatNum=0,vegetablesNum=0,soymilkNum=0,
                    cerealNum=0;

            double eggNum = 0,beansNum=0;
            String egg_String = "180518092801997,180518092801998,180518092801999,180518092802000,180518092802001,180518092802200,180518092802201,180518092802202,";
            egg_String += "180518092802203,180518092802204,180518092802205,180518092802206,180518092802207,180518092802208,180518092802209,180518092802210,180518092802211,";
            egg_String += "180518092802212,180518092802213,180518092802214,180518092802215,180518092802327,";
            Integer version = nutritionCateringPO.getVersion();
            if(version!=null && version==2){
                if ( eohType == 3){
                    for(Map<String, Object> map : breakfastFoodList1){
                        Object tempObject = map.get("ftype");
                        if(tempObject!=null){
                            String ftype = tempObject.toString();
                            tempObject = map.get("weight");
                            if(tempObject!=null){
                                String numString = tempObject.toString();
                                String allHeat = map.get("allheat").toString();
                                double num = Double.parseDouble(numString);
                                double iHeat = Double.parseDouble(allHeat);
                                foodNum += num;
                                heat += iHeat;
                                // 谷物类
                                if("1001001".equals(ftype)){
                                    cerealNum += num;
                                } else if("1001003".equals(ftype)){
                                    // 蔬菜类
                                    vegetablesNum += num;
                                } else if("1001007".equals(ftype) || "1001002".equals(ftype)){
                                    // 肉蛋豆类
                                    if (eohType == 3){
                                        if ("1001002".equals(ftype)) {
                                            beansNum += num;
                                        }else {
                                            String id = map.get("id").toString();
                                            if (egg_String.contains(id)){
                                                eggNum += num;
                                            }else {
                                                meatNum += num;
                                            }
                                        }
                                    }else {
                                        meatNum += num;
                                    }
                                } else if("1001012".equals(ftype)){
                                    // 浆乳类
                                    soymilkNum += num;
                                } else if("1001013".equals(ftype) || "1001014".equals(ftype)){
                                    // 油脂类
                                    greaseNum += num;
                                } else if("1001004".equals(ftype)) {
                                    //水果
                                    fruitsNum += num;
                                }
                            }
                        }
                    }

                    for(Map<String, Object> map : lunchFoodList2){
                        Object tempObject = map.get("ftype");
                        if(tempObject!=null){
                            String ftype = tempObject.toString();
                            tempObject = map.get("weight");
                            if(tempObject!=null){
                                String numString = tempObject.toString();
                                String allHeat = map.get("allheat").toString();
                                double num = Double.parseDouble(numString);
                                double iHeat = Double.parseDouble(allHeat);
                                foodNum += num;
                                heat +=iHeat;
                                if("1001001".equals(ftype)){
                                    // 谷物类
                                    cerealNum += num;
                                } else if("1001003".equals(ftype)){
                                    // 蔬菜类
                                    vegetablesNum += num;
                                } else if("1001007".equals(ftype) || "1001002".equals(ftype)){
                                    // 肉蛋豆类
                                    if (eohType == 3){
                                        if ("1001002".equals(ftype)) {
                                            beansNum += num;
                                        }else {
                                            String id = map.get("id").toString();
                                            if (egg_String.contains(id)){
                                                eggNum += num;
                                            }else {
                                                meatNum += num;
                                            }
                                        }
                                    }else {
                                        meatNum += num;
                                    }
                                } else if("1001012".equals(ftype)){
                                    // 浆乳类
                                    soymilkNum += num;
                                } else if("1001013".equals(ftype) || "1001014".equals(ftype)){
                                    // 油脂类
                                    greaseNum += num;
                                } else if("1001004".equals(ftype)) {
                                    //水果
                                    fruitsNum += num;
                                }
                            }
                        }
                    }

                    for(Map<String, Object> map : dinnerFoodList3){
                        Object tempObject = map.get("ftype");
                        if(tempObject!=null){
                            String ftype = tempObject.toString();
                            tempObject = map.get("weight");
                            if(tempObject!=null){
                                String numString = tempObject.toString();
                                String allHeat = map.get("allheat").toString();
                                double num = Double.parseDouble(numString);
                                double iHeat = Double.parseDouble(allHeat);
                                foodNum += num;
                                heat +=iHeat;
                                if("1001001".equals(ftype)){
                                    // 谷物类
                                    cerealNum += num;
                                } else if("1001003".equals(ftype)){
                                    // 蔬菜类
                                    vegetablesNum += num;
                                } else if("1001007".equals(ftype) || "1001002".equals(ftype)){
                                    // 肉蛋豆类
                                    if (eohType == 3){
                                        if ("1001002".equals(ftype)) {
                                            beansNum += num;
                                        }else {
                                            String id = map.get("id").toString();
                                            if (egg_String.contains(id)){
                                                eggNum += num;
                                            }else {
                                                meatNum += num;
                                            }
                                        }
                                    }else {
                                        meatNum += num;
                                    }
                                } else if("1001012".equals(ftype)){
                                    // 浆乳类
                                    soymilkNum += num;
                                } else if("1001013".equals(ftype) || "1001014".equals(ftype)){
                                    // 油脂类
                                    greaseNum += num;
                                } else if("1001004".equals(ftype)) {
                                    //水果
                                    fruitsNum += num;
                                }
                            }
                        }
                    }
                }else {
                    for (Map<String, Object> map : breakfastFoodList) {
                        Object tempObject = map.get("ftype");
                        if (tempObject != null) {
                            String ftype = tempObject.toString();
                            tempObject = map.get("fnum");
                            if (tempObject != null) {
                                String numString = tempObject.toString();
                                String allHeat = map.get("allheat").toString();
                                double num = Double.parseDouble(numString);
                                double iHeat = Double.parseDouble(allHeat);
                                foodNum += num;
                                heat += iHeat;
                                // 谷物类
                                if ("1001001".equals(ftype)) {
                                    cerealNum += num;
                                } else if ("1001003".equals(ftype)) {
                                    // 蔬菜类
                                    vegetablesNum += num;
                                } else if ("1001007".equals(ftype) || "1001002".equals(ftype)) {
                                    // 肉蛋豆类
                                    meatNum += num;
                                } else if ("1001012".equals(ftype)) {
                                    // 浆乳类
                                    soymilkNum += num;
                                } else if ("1001013".equals(ftype) || "1001014".equals(ftype)) {
                                    // 油脂类
                                    greaseNum += num;
                                } else if ("1001004".equals(ftype)) {
                                    //水果
                                    fruitsNum += num;
                                }
                            }
                        }
                    }

                    for (Map<String, Object> map : lunchFoodList) {
                        Object tempObject = map.get("ftype");
                        if (tempObject != null) {
                            String ftype = tempObject.toString();
                            tempObject = map.get("fnum");
                            if (tempObject != null) {
                                String numString = tempObject.toString();
                                String allHeat = map.get("allheat").toString();
                                double num = Double.parseDouble(numString);
                                double iHeat = Double.parseDouble(allHeat);
                                foodNum += num;
                                heat += iHeat;
                                if ("1001001".equals(ftype)) {
                                    // 谷物类
                                    cerealNum += num;
                                } else if ("1001003".equals(ftype)) {
                                    // 蔬菜类
                                    vegetablesNum += num;
                                } else if ("1001007".equals(ftype) || "1001002".equals(ftype)) {
                                    // 肉蛋豆类
                                    meatNum += num;
                                } else if ("1001012".equals(ftype)) {
                                    // 浆乳类
                                    soymilkNum += num;
                                } else if ("1001013".equals(ftype) || "1001014".equals(ftype)) {
                                    // 油脂类
                                    greaseNum += num;
                                } else if ("1001004".equals(ftype)) {
                                    //水果
                                    fruitsNum += num;
                                }
                            }
                        }
                    }

                    for (Map<String, Object> map : dinnerFoodList) {
                        Object tempObject = map.get("ftype");
                        if (tempObject != null) {
                            String ftype = tempObject.toString();
                            tempObject = map.get("fnum");
                            if (tempObject != null) {
                                String numString = tempObject.toString();
                                String allHeat = map.get("allheat").toString();
                                double num = Double.parseDouble(numString);
                                double iHeat = Double.parseDouble(allHeat);
                                foodNum += num;
                                heat += iHeat;
                                if ("1001001".equals(ftype)) {
                                    // 谷物类
                                    cerealNum += num;
                                } else if ("1001003".equals(ftype)) {
                                    // 蔬菜类
                                    vegetablesNum += num;
                                } else if ("1001007".equals(ftype) || "1001002".equals(ftype)) {
                                    // 肉蛋豆类
                                    meatNum += num;
                                } else if ("1001012".equals(ftype)) {
                                    // 浆乳类
                                    soymilkNum += num;
                                } else if ("1001013".equals(ftype) || "1001014".equals(ftype)) {
                                    // 油脂类
                                    greaseNum += num;
                                } else if ("1001004".equals(ftype)) {
                                    //水果
                                    fruitsNum += num;
                                }
                            }
                        }
                    }
                }
            } else {
                for(Map<String, Object> map : breakfastFoodList){
                    Object tempObject = map.get("ftype");
                    if(tempObject!=null){
                        String ftype = tempObject.toString();
                        tempObject = map.get("fnum");
                        if(tempObject!=null){
                            String numString = tempObject.toString();
                            double num = Double.parseDouble(numString);
                            foodNum += num;
                            // 谷物类
                            if("1001001".equals(ftype)){
                                cerealNum += num;
                            } else if("1001003".equals(ftype)){
                                // 蔬菜类
                                vegetablesNum += num;
                            } else if("1001007".equals(ftype) || "1001002".equals(ftype)){
                                // 肉蛋豆类
                                meatNum += num;
                            } else if("1001012".equals(ftype)){
                                // 浆乳类
                                soymilkNum += num;
                            } else if("1001013".equals(ftype) || "1001014".equals(ftype)){
                                // 油脂类
                                greaseNum += num;
                            } else if("1001004".equals(ftype)) {
                                //水果
                                fruitsNum += num;
                            }
                        }
                    }
                }

                for(Map<String, Object> map : lunchFoodList){
                    Object tempObject = map.get("ftype");
                    if(tempObject!=null){
                        String ftype = tempObject.toString();
                        tempObject = map.get("fnum");
                        if(tempObject!=null){
                            String numString = tempObject.toString();
                            double num = Double.parseDouble(numString);
                            foodNum += num;
                            if("1001001".equals(ftype)){
                                // 谷物类
                                cerealNum += num;
                            } else if("1001003".equals(ftype)){
                                // 蔬菜类
                                vegetablesNum += num;
                            } else if("1001007".equals(ftype) || "1001002".equals(ftype)){
                                // 肉蛋豆类
                                meatNum += num;
                            } else if("1001012".equals(ftype)){
                                // 浆乳类
                                soymilkNum += num;
                            } else if("1001013".equals(ftype) || "1001014".equals(ftype)){
                                // 油脂类
                                greaseNum += num;
                            } else if("1001004".equals(ftype)) {
                                //水果
                                fruitsNum += num;
                            }
                        }
                    }
                }

                for(Map<String, Object> map : dinnerFoodList){
                    Object tempObject = map.get("ftype");
                    if(tempObject!=null){
                        String ftype = tempObject.toString();
                        tempObject = map.get("fnum");
                        if(tempObject!=null){
                            String numString = tempObject.toString();
                            double num = Double.parseDouble(numString);
                            foodNum += num;
                            if("1001001".equals(ftype)){
                                // 谷物类
                                cerealNum += num;
                            } else if("1001003".equals(ftype)){
                                // 蔬菜类
                                vegetablesNum += num;
                            } else if("1001007".equals(ftype) || "1001002".equals(ftype)){
                                // 肉蛋豆类
                                meatNum += num;
                            } else if("1001012".equals(ftype)){
                                // 浆乳类
                                soymilkNum += num;
                            } else if("1001013".equals(ftype) || "1001014".equals(ftype)){
                                // 油脂类
                                greaseNum += num;
                            } else if("1001004".equals(ftype)) {
                                //水果
                                fruitsNum += num;
                            }
                        }
                    }
                }

                heat = foodNum*90;
            }

            String dfStr = String.format("%.1f",heat);
            nutritionCateringPO.setCaloriesLevel(dfStr);
            dfStr = String.format("%.1f",foodNum);
            nutritionCateringPO.setFoodNum(dfStr);

            dfStr = String.format("%.1f",cerealNum);
            nutritionCateringPO.setCerealNum(dfStr);

            dfStr = String.format("%.1f",meatNum);
            nutritionCateringPO.setMeatNum(dfStr);

            dfStr = String.format("%.1f",fruitsNum);
            nutritionCateringPO.setFruitsNum(dfStr);

            dfStr = String.format("%.1f",vegetablesNum);
            nutritionCateringPO.setVegetablesNum(dfStr);

            dfStr = String.format("%.1f",greaseNum);
            nutritionCateringPO.setGreaseNum(dfStr);

            dfStr = String.format("%.1f",soymilkNum);
            nutritionCateringPO.setSoymilkNum(dfStr);

            dfStr = String.format("%.1f",eggNum);
            nutritionCateringPO.setEggNum(dfStr);

            dfStr = String.format("%.1f",beansNum);
            nutritionCateringPO.setBeansNum(dfStr);

            return nutritionCateringPO;
        }
        return null;
    }

    /**
     * 食谱成分统计
     * @param stat
     * @param type
     * @param weight
     */
    private void ingredientStatHandler(Map<String ,Float> stat ,String type ,Object weight){
        Float g = stat.getOrDefault(type ,0f);
        g += Float.parseFloat(weight.toString());
        stat.put(type ,g);
    }
}
