package com.comvee.cdms.glu.service;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.cfg.Config;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.cfg.GroupConstant;
import com.comvee.cdms.doctor.mapper.DoctorMapper;
import com.comvee.cdms.doctor.po.DoctorGroupPO;
import com.comvee.cdms.glu.mapper.GlucometerApplicationMapper;
import com.comvee.cdms.glu.model.*;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanServiceI;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.cfg.ParamLogConstant;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.mapper.DoctorDepartmentMapper;
import com.comvee.cdms.doctor.vo.DoctorDepartmentVO;
import com.comvee.cdms.hospital.mapper.HospitalMapper;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import com.comvee.cdms.hospital.model.po.InHospitalLogPO;
import com.comvee.cdms.machine.mapper.MachineMapper;
import com.comvee.cdms.member.dto.MemberParamsDTO;
import com.comvee.cdms.member.mapper.MemberJoinHospitalMapper;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.vo.MemberJoinHospitalVO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.constant.SignLock;
import com.comvee.cdms.sign.dto.AddBloodSugarServiceDTO;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import com.comvee.cdms.sign.mapper.BloodSugarMapper;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**  
 * @author: nzq
 * @time：2020-07-28  
 * @version:v1.0
 */
@Service("gluService")
public class GluServiceImpl implements GluServiceI {
	
	@Autowired
    private BloodSugarMapper bloodSugarMapper;
	
	@Autowired
    private HospitalMapper hospitalMapper;
	
	@Autowired
    private MemberMapper memberMapper;
	
	@Autowired
    private DoctorDepartmentMapper doctorDepartmentMapper;
	
	@Autowired
    private MachineMapper machineMapper;

	@Autowired
	private GlucometerApplicationMapper glucometerApplicationMapper;

    @Autowired
    private DoctorMapper doctorMapper;

	@Autowired
    @Qualifier("bloodSugarService")
    private BloodSugarService bloodSugarService;
	
	@Autowired
	@Qualifier("memberMonitorPlanService")
	private MemberMonitorPlanServiceI memberMonitorPlanService;

	@Autowired
	private MemberJoinHospitalMapper joinHospitalMapper;


    private final static Logger logger = LoggerFactory.getLogger(GluServiceImpl.class);

    private static String APP_DOWNLOAD_HOST;
    static {
        try{
            APP_DOWNLOAD_HOST = Config.getValueByKey("glu.app.download.host");
        }catch (Exception e){
            logger.error("初始化血糖仪版本下载主机失败" ,e);
        }
    }

    private static String SRF_DOWNLOAD_ADDRESS;
    static {
        try{
            SRF_DOWNLOAD_ADDRESS = Config.getValueByKey("glu.srf.download.address");
        }catch (Exception e){
            logger.error("初始化血糖仪输入法下载地址失败" ,e);
        }
    }
	@Override
    public Map<String, Object> listBedWithGroup(PageRequestModel pr, String concernStatus, DoctorSessionBO doctor ,String departmentId) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        Map<String,Object> queryMap = new HashMap<String, Object>();
        queryMap.put("concernStatus", concernStatus);
        queryMap.put("doctorId", doctor.getDoctorId());
        queryMap.put("paramDt", DateHelper.getToday());
        queryMap.put("departmentId", departmentId);
        List<MemberEverydaySugarModel> list = this.memberMapper.getMemberEverydaySugarList(queryMap);
        List<String> midList = new ArrayList<String>();
        for(MemberEverydaySugarModel m : list){
            midList.add(m.getMemberId());
        }
        if(midList.size()>0){
            //获取患者名称
        	MemberParamsDTO memberParamsDTO = new MemberParamsDTO();
        	memberParamsDTO.setMemberIds(midList);
            List<MemberPO> memberModels = this.memberMapper.listMemberByWhere(memberParamsDTO);
            /*
            List<JSONObject> jsonObjects0 = JsonSerializer.convertListJsobObj(memberModels);
            Map<String,Object> map0 = JsonSerializer.convertListToMap(jsonObjects0,"memberId","memberName");
            for(MemberEverydaySugarModel m : list){
                Object obj0 = map0.get(m.getMemberId()+"");
                if(obj0!=null){
                    m.setMemberName(obj0.toString());
                }
            }
            */
            for(MemberEverydaySugarModel m : list){
            	for(MemberPO memberPo : memberModels){
            		if(!StringUtils.isBlank(m.getMemberId()) && m.getMemberId().equals(memberPo.getMemberId())){
                        m.setMemberName(memberPo.getMemberName());
                        m.setAge(DateHelper.getAge(memberPo.getBirthday()));
                    }
            	}
            }
        }
        //分页
        PageResult<MemberEverydaySugarModel> pageResult = new PageResult<MemberEverydaySugarModel>(list);
        Map<String,Object> resultMap = new HashMap<String, Object>();
        //补充血糖数据
        List<Map<String,Object>> bedList = disposeBedListCompress(pageResult.getRows());
        resultMap.put("rows", bedList);
        resultMap.put("totalPages", pageResult.getTotalPages());
        resultMap.put("totalRows", pageResult.getTotalRows());
        resultMap.put("pageNum", pageResult.getPageNum());
        resultMap.put("returnTime", DateHelper.getDateFormatter());
        //取科室列表
        List<DoctorDepartmentVO> departmentModels = this.doctorDepartmentMapper.listDoctorDepartment(doctor.getDoctorId());
        resultMap.put("departmentList",departmentModels);
        return resultMap;
    }
	
    @Override
    public Map<String, Object> listBedWithGroupForRefresh(PageRequestModel pr, String concernStatus, DoctorSessionBO doctor, String refreshTime ,String departmentId) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        Map<String,Object> queryMap = new HashMap<String, Object>();
        queryMap.put("concernStatus", concernStatus);
        queryMap.put("doctorId", doctor.getDoctorId());
        queryMap.put("paramDt", DateHelper.getToday());
        queryMap.put("refreshTime", refreshTime);
        queryMap.put("departmentId", departmentId);
        String nowDateTime = DateHelper.getDateFormatter();		//当前时间
        List<MemberEverydaySugarModel> list = this.memberMapper.getMemberEverydaySugarList(queryMap);
        List<String> midList = new ArrayList<String>();
        for(MemberEverydaySugarModel m : list){
            midList.add(m.getMemberId());
        }
        if(midList.size()>0){
            //获取患者名称
            MemberParamsDTO memberParamsDTO = new MemberParamsDTO();
            memberParamsDTO.setMemberIds(midList);
            List<MemberPO> memberModels = this.memberMapper.listMemberByWhere(memberParamsDTO);
            for(MemberEverydaySugarModel m : list){
                for(MemberPO memberPo : memberModels){
                    if(!StringUtils.isBlank(m.getMemberId()) && m.getMemberId().equals(memberPo.getMemberId())){
                        m.setMemberName(memberPo.getMemberName());
                        m.setAge(DateHelper.getAge(memberPo.getBirthday()));
                    }
                }
            }
        }
        PageResult<MemberEverydaySugarModel> pageResult = new PageResult<MemberEverydaySugarModel>(list);

        Map<String,Object> resultMap = new HashMap<String, Object>();
        List<Map<String,Object>> bedList = disposeBedListCompress(pageResult.getRows());
        resultMap.put("rows", bedList);
        resultMap.put("totalPages", pageResult.getTotalPages());
        resultMap.put("totalRows", pageResult.getTotalRows());
        resultMap.put("pageNum", pageResult.getPageNum());
        resultMap.put("returnTime", nowDateTime);

        //第一页才返回科室和删除的患者列表
        if(pr.getPage() == 1){
            //取科室列表
            List<DoctorDepartmentVO> departmentModels = this.doctorDepartmentMapper.listDoctorDepartment(doctor.getDoctorId());
            resultMap.put("departmentList",departmentModels);
            List<InHospitalLogPO> inHospitalList = this.hospitalMapper.listInHospitalByDoctorId(refreshTime, doctor.getDoctorId(), 0);
            resultMap.put("outHospitalList", ReflectTool.getListFromBean(inHospitalList, new Object[]{"memberId"}));
        }else{
            resultMap.put("departmentList", new ArrayList<Object>());
            resultMap.put("outHospitalList", new ArrayList<Object>());
        }
        return resultMap;
    }

	/**
     * 处理血糖数据
     * @author: nzq
     * @time：2020-07-28  
     */
    private List<Map<String,Object>> disposeBedListCompress(List<MemberEverydaySugarModel> bedList){
        //处理数据将数据按病房分好
        List<Map<String,Object>> mesms = new ArrayList<Map<String,Object>>();;
        for (int i = 0; i < bedList.size(); i++) {
            MemberEverydaySugarModel mesm =  bedList.get(i);
            this.disposeNewSugarCompress(mesm);
            Map<String,Object> memberMap = new HashMap<String,Object>();
            memberMap.put("newSugarMap", mesm.getNewSugarMap());
            memberMap.put("mi",mesm.getMemberId());
            memberMap.put("mn",mesm.getMemberName());
            memberMap.put("bn",mesm.getBedNo().replace("床",""));
            memberMap.put("bi",mesm.getBedId());
            memberMap.put("dn",mesm.getDepartmentName());
            memberMap.put("di",mesm.getDepartmentId());
            memberMap.put("cs",mesm.getConcernStatus());
//            String smbgScheme = dealMonitorPlan(mesm.getSmbgScheme());
            memberMap.put("ss",mesm.getSmbgScheme());
            memberMap.put("sex",mesm.getSex());
            memberMap.put("departId", mesm.getDepartmentId());
            memberMap.put("planType", mesm.getPlanType());
            memberMap.put("patPatientId", mesm.getPatPatientId());
            memberMap.put("inHospitalDate" ,mesm.getInHospitalDate());
            memberMap.put("age" ,mesm.getAge());
            memberMap.put("hospitalNo" ,mesm.getHospitalNo());
            memberMap.put("admNo" ,mesm.getAdmNo());
            mesms.add(memberMap);
        }
        return mesms;
    }
    
    /**
     * 处理监测方案（解决没有3am时间点问题）
     * @author: nzq
     * @time：2020-07-28  
     */
    private String dealMonitorPlan(String smbgScheme){
    	//因为web没有3am这个点，所以测量时间往后挪一格（大于1的+1），同时没有复检前一天这个点，所以去掉8打头的监测方案。
    	smbgScheme = ValidateTool.checkIsNull(smbgScheme) ? smbgScheme.trim() : "";
        if(smbgScheme.length() > 0){
        	String[] smbgArray = smbgScheme.split(";");
        	smbgScheme = "";
        	for (String scheme : smbgArray) {
        		String[] sche = scheme.split("-");
        		if(sche.length > 1){
        			int len1 = Integer.parseInt(sche[0]);
        			int len2 = Integer.parseInt(sche[1]);
        			if(len1 < 8){
        				if(len2 > 1){
        					len2 += 1;
        				}
        				String str = len1 + "-" + len2 + ";";
        				smbgScheme += str;
        			}
        		}
			}
        }
        smbgScheme = smbgScheme.length() > 0 ? smbgScheme.substring( 0, smbgScheme.length() - 1 ) : smbgScheme;
        return smbgScheme;
    }
    
    /**
     * 处理最新血糖数据
     * @author: nzq
     * @time：2020-07-28  
     */
    private Map<String,Object> disposeNewSugarCompress(MemberEverydaySugarModel mesm){
        //处理血糖列表中最新血糖数据
        Map<String,Object> sugarMap = new HashMap<String,Object>();
        sugarMap.put("beforedawn",new HashMap<String,Object>());
        sugarMap.put("bb",new HashMap<String,Object>());
        sugarMap.put("ab",new HashMap<String,Object>());
        sugarMap.put("bl",new HashMap<String,Object>());
        sugarMap.put("al",new HashMap<String,Object>());
        sugarMap.put("bd",new HashMap<String,Object>());
        sugarMap.put("ad",new HashMap<String,Object>());
        sugarMap.put("bs",new HashMap<String,Object>());
        sugarMap.put("randomtime",new HashMap<String,Object>());
        String sugarListJson = mesm.getParamListJson();
        if (ValidateTool.checkIsNull(sugarListJson)){
            List<MemberParamLogModel> sugarList = JSON.parseArray(sugarListJson,MemberParamLogModel.class);
            //有血糖数据才判断
            Map<String,Object> paramMap = null;
            for (MemberParamLogModel memberParamLogModel : sugarList) {
                paramMap = new HashMap<String,Object>();
                paramMap.put("pc",memberParamLogModel.getParamCode());
                paramMap.put("value",memberParamLogModel.getParamValue());
                paramMap.put("rt",memberParamLogModel.getRecordDt());
                paramMap.put("level",memberParamLogModel.getParamLevel());
                paramMap.put("mi",memberParamLogModel.getMemberId());
                paramMap.put("pli",memberParamLogModel.getSid());
                paramMap.put("remark",memberParamLogModel.getRemark());
                paramMap.put("status","1");
                paramMap.put("ro",memberParamLogModel.getOrigin());
                String paramCode = "";
                if (memberParamLogModel.getParamCode().equals(ParamLogConstant.PARAM_CODE_3AM)){
                    paramCode = "3am";
                }else if (memberParamLogModel.getParamCode().equals(ParamLogConstant.PARAM_CODE_BEFOREDAWN)){
                    paramCode = "beforedawn";
                }else if (memberParamLogModel.getParamCode().equals(ParamLogConstant.PARAM_CODE_BEFOREBREAKFAST)){
                    paramCode = "bb";
                }else if (memberParamLogModel.getParamCode().equals(ParamLogConstant.PARAM_CODE_AFTERBREAKFAST)){
                    paramCode = "ab";
                }else if (memberParamLogModel.getParamCode().equals(ParamLogConstant.PARAM_CODE_BEFORELUNCH)){
                    paramCode = "bl";
                }else if (memberParamLogModel.getParamCode().equals(ParamLogConstant.PARAM_CODE_AFTERLUNCH)){
                    paramCode = "al";
                }else if (memberParamLogModel.getParamCode().equals(ParamLogConstant.PARAM_CODE_BEFOREDINNER)){
                    paramCode = "bd";
                }else if (memberParamLogModel.getParamCode().equals(ParamLogConstant.PARAM_CODE_AFTERDINNER)){
                    paramCode = "ad";
                }else if (memberParamLogModel.getParamCode().equals(ParamLogConstant.PARAM_CODE_BEFORESLEEP)){
                    paramCode = "bs";
                }else if (memberParamLogModel.getParamCode().equals(ParamLogConstant.PARAM_CODE_RANDOMTIME)){
                    paramCode = "randomtime";
                }
                sugarMap.put(paramCode,paramMap);
            }
        }
        mesm.setNewSugarMap(sugarMap);
        return sugarMap;
    }
    
    @Override
    public Map<String, Object> getMemberSmbgCount(String doctorId) {
        Map<String,Long> m1 = new HashMap<String, Long>();
        long total = 0L;
        Map<String,Object> returnMap = new HashMap<String, Object>();
        returnMap.put("total", total);
        returnMap.put("countList", m1);
        return returnMap;
    	/*
    	//今日的日期
        String today = DateHelper.getToday();
        String date = today.replace("-","");
        //星期几的code
        int weekCode = DateHelper.getWeekCode();
        Map<String,Object> queryMap = new HashMap<String, Object>();
        queryMap.put("today", today);
        queryMap.put("date", date);
        queryMap.put("weekCode", weekCode);
        queryMap.put("doctorId", doctorId);
        
        List<MemberSugarCodeNumberPO> list1 = this.memberMonitorPlanService.listHasMonitorPeopleNumber(queryMap);
        List<MemberSugarCodeNumberPO> list2 = this.memberMonitorPlanService.listHasMonitorAndRecordPeopleNumber(queryMap);
        Map<String,Long> m1 = new HashMap<String, Long>();
        Map<String,Long> m2 = new HashMap<String, Long>();
        for(MemberSugarCodeNumberPO p : list2){
            m2.put(p.getParamCode(), p.getNumber());
        }
        long total = 0L;
        for(MemberSugarCodeNumberPO p : list1){
            if(p.getParamCode().equals(ParamLogConstant.PARAM_CODE_3AM)){
                continue;
            }
            long recordNumber = m2.get(p.getParamCode()) == null ? 0L:m2.get(p.getParamCode());
            long residue = p.getNumber() - recordNumber;
            m1.put(p.getParamCode(), residue);
            total = total + residue;
        }
        Map<String,Object> returnMap = new HashMap<String, Object>();
        returnMap.put("total", total);
        returnMap.put("countList", m1);
        return returnMap;
        */
    }
    
    @Override
    public MachineVersionModel getMachineVersionModel(GlucometerRequest gr){
        MachineVersionModel model = this.machineMapper.getMachineVersionModel(gr);
        if(APP_DOWNLOAD_HOST == null){
            throw new BusinessException("血糖仪下载地址未配置");
        }
        model.setUpgradeUrl(APP_DOWNLOAD_HOST + model.getUpgradeUrl());
    	return model;
    }

    @Override
    public String getSrfDownLoadAddress(GlucometerRequest gr) {
        if(SRF_DOWNLOAD_ADDRESS == null){
            throw new BusinessException("血糖仪下载地址未配置");
        }
        return SRF_DOWNLOAD_ADDRESS;
    }

    @Override
    public Map<String,Object> listMemberParamLog(ListBloodSugarDTO dto){
    	String startDt = "";
        String endDt = "";
        if (!ValidateTool.checkIsNull(endDt)){
            endDt = DateHelper.getDate(new Date(),DateHelper.DAY_FORMAT);
        }
        endDt += Constant.END_HOURS;
        if (!ValidateTool.checkIsNull(startDt)){
            startDt = DateHelper.plusDate(endDt,2,-3,DateHelper.DAY_FORMAT);
        }
        startDt += Constant.START_HOURS;
        dto.setStartDt(startDt);
        dto.setEndDt(endDt);
        List<BloodSugarPO> bloodSugarList = this.bloodSugarMapper.listBloodSugar(dto);
        List<MemberParamLogModel> paramLogList = changeBloodModel(bloodSugarList);
        List<Map<String,Object>> paramList = resetList(paramLogList);
        Map<String,Object> m = new HashMap<String, Object>();
        Collections.reverse(paramList);
        m.put("memberHistory", paramList);
        m.put("refreshTime", DateHelper.getDateFormatter());
        return m;
    }
    /**
     * 兼容旧版本血糖数据对象
     * @author nzq
     * @date 2020-07-28
     * @param listParamLocal List<MemberParamLogModel>
     * @return List<Map<String,Object>>
     */
 	private  List<MemberParamLogModel> changeBloodModel(List<BloodSugarPO> bloodSugarList){
 		List<MemberParamLogModel> paramLogList = new ArrayList<MemberParamLogModel>();
 		if(bloodSugarList != null){
 	        for (BloodSugarPO bloodPo : bloodSugarList) {
 	        	MemberParamLogModel paramLogModel = new MemberParamLogModel();
 	        	paramLogModel.setMemberId(bloodPo.getMemberId());
 	        	paramLogModel.setSid(bloodPo.getSid());
 	        	paramLogModel.setParamLogId(bloodPo.getSid());
 	        	paramLogModel.setParamCode(bloodPo.getParamCode());
 	        	paramLogModel.setValue(bloodPo.getParamValue());
 	        	paramLogModel.setLevel(bloodPo.getParamLevel() + "");
 	        	paramLogModel.setRecordTime(bloodPo.getRecordDt());
 	        	paramLogModel.setRecordDt(bloodPo.getRecordDt());
 	        	paramLogModel.setRecordOrigin(bloodPo.getOrigin()+ "");
 	        	paramLogModel.setStatus("1");
 	        	paramLogModel.setRemark(bloodPo.getRemark());
 	        	paramLogList.add(paramLogModel);
 			}
 		}
 		return paramLogList;
 	}
    
    /**
    * 组装本地曲线
    * @author nzq
    * @date 2020-07-28
    * @param listParamLocal List<MemberParamLogModel>
    * @return List<Map<String,Object>>
    */
	private List<Map<String,Object>>  resetList(List<MemberParamLogModel> listParamLocal){
       List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
       //最后的日期
       String lastDate = "";   
       Map<String,Object> resMap = null;
       List<MemberParamLogModel> mplm = null;
       for (int i = 0; i < listParamLocal.size(); i++) {
    	   MemberParamLogModel bean = listParamLocal.get(i);
           String recordDate = DateHelper.changeTimeFormat(bean.getRecordTime(),DateHelper.DATETIME_FORMAT,DateHelper.DAY_FORMAT);
           if (!lastDate.equals(recordDate) || i == 0){
               if (resMap != null && mplm != null ){
                   resMap.put("paramLogList",mplm);
                   list.add(resMap);
               }
               resMap = new HashMap<String,Object>();
               mplm = new ArrayList<MemberParamLogModel>();
               resMap.put("date",recordDate);
               lastDate = recordDate;
           }
           mplm.add(bean);
           if (i == listParamLocal.size() - 1){
               resMap.put("paramLogList",mplm);
               list.add(resMap);
           }
       }
       return list;
    }

    @Override
    public Map<String,Object> refreshParamLogList(ListBloodSugarDTO dto){
    	List<BloodSugarPO> bloodSugarList = this.bloodSugarMapper.listBloodSugar(dto);
    	List<MemberParamLogModel> paramLogList = changeBloodModel(bloodSugarList);
        List<Map<String,Object>> paramList = resetList(paramLogList);
        Map<String,Object> m = new HashMap<String, Object>();
        Collections.reverse(paramList);
        m.put("memberHistory", paramList);
        m.put("refreshTime", DateHelper.getDateFormatter());
        return m;
    }
    
    @Override
    public ResultModel insertMemberBloodSugarLogWithLock(MemberParamLogModel model){
    	AddBloodSugarServiceDTO addBloodSugarServiceDTO = new AddBloodSugarServiceDTO();
    	addBloodSugarServiceDTO.setMemberId(model.getMemberId());
    	addBloodSugarServiceDTO.setParamValue(model.getValue());
    	addBloodSugarServiceDTO.setParamCode(model.getParamCode());
    	addBloodSugarServiceDTO.setRecordDt(model.getRecordTime());
    	addBloodSugarServiceDTO.setRemark(model.getRemark());
    	addBloodSugarServiceDTO.setOrigin(StringUtils.isBlank(model.getRecordOrigin())?6:Integer.parseInt(model.getRecordOrigin()));
    	addBloodSugarServiceDTO.setOperatorId(model.getOptionUserId());
    	addBloodSugarServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
    	addBloodSugarServiceDTO.setInHos(SignConstant.IN_HOSPITAL);
        addBloodSugarServiceDTO.setAppId(model.getAppId());
        SignLock.BLOOD_SUGAR_LOCK.lock();
    	try{
            bloodSugarService.addBloodSugar(addBloodSugarServiceDTO);
        }finally {
            SignLock.BLOOD_SUGAR_LOCK.unlock();
        }
    	return new ResultModel("记录成功");
    }

    @Override
    @Cacheable(value = "public" ,key = "'glucometerApplication'")
    public Map<String ,String> listGlucometerApplication() {
	    List<GlucometerApplication> list = this.glucometerApplicationMapper.listGlucometerApplication();
	    if(list == null || list.isEmpty()){
	        return Collections.emptyMap();
        }
	    Map<String ,String> result = list.stream().collect(Collectors.toMap(GlucometerApplication::getAppId ,GlucometerApplication::getAppName));
        return result;
    }

    @Override
    public List<GluGroupVO> listGluMember(ListMemberDto dto){
        List<DoctorGroupPO> groupList = this.doctorMapper.listGroup(dto.getDoctorId());
        defaultGroupHandler(groupList, dto.getDoctorId());
        List<GluGroupVO> result = new ArrayList<>();
        List<ListMemberVO> members = memberMapper.listGluMemberWithGroup(dto);
        members.forEach(m ->{
            m.setAge(DateHelper.getAge(m.getBirthday()));
        });
        groupList.forEach( g -> {
            GluGroupVO vo = new GluGroupVO();
            BeanUtils.copyProperties(vo, g);
            result.add(vo);
            vo.setMembers(members.stream().filter(x -> Objects.equals(x.getGroupId(), g.getGroupId())).collect(Collectors.toList()));
            if(vo.getMembers() != null){
                vo.setPeopleNumber((long) vo.getMembers().size());
            }else {
                vo.setMembers(new ArrayList<>());
            }
        });
        return result;
    }

    @Override
    public List<ListMemberVO> listGluMemberByKeyword(ListMemberDto dto){
        List<ListMemberVO> members = memberMapper.listGluMemberByKeyword(dto);
        if(members != null && !members.isEmpty()) {
            members.forEach(m -> {
                if(!StringUtils.isBlank(m.getBirthday())) {
                    m.setAge(DateHelper.getAge(m.getBirthday()));
                }
            });
        }
        return members;
    }

    @Override
    public Map<String, Object> listMemberJoinHospitalForFresh(String hospitalId, PageRequest pr,String refreshTime) {
        String nowDateTime = DateHelper.getDateFormatter();
        PageHelper.startPage(pr.getPage(), pr.getRows());
        Map<String, Object> param = new HashMap<>();
        param.put("hospitalId",hospitalId);
        param.put("refreshTime",refreshTime);
        //新增患者列表
        List<MemberJoinHospitalVO> newJoinMemberList = joinHospitalMapper.listMemberJoinHospitalForFresh(param);
        newJoinMemberList.forEach(m ->{
            m.setAge(DateHelper.getAge(m.getBirthday()));
        });
        PageResult<MemberJoinHospitalVO> pageResult = new PageResult<>(newJoinMemberList);

        //患者信息更新列表
        List<MemberJoinHospitalVO> memberModifyList = joinHospitalMapper.listMemberForFresh(param);
        memberModifyList.forEach(m ->{
            m.setAge(DateHelper.getAge(m.getBirthday()));
        });
        if (!memberModifyList.isEmpty() && !newJoinMemberList.isEmpty()){
            for (int i = 0; i < memberModifyList.size(); i++) {
                MemberJoinHospitalVO modifyMember = memberModifyList.get(i);
                if (newJoinMemberList.contains(modifyMember)){
                    memberModifyList.remove(modifyMember);
                }
            }
        }
        //删除患者列表
        param.remove("refreshTime");
        param.put("outRefreshTime",refreshTime);
        List<MemberJoinHospitalVO> newDelMemberList = joinHospitalMapper.listMemberJoinHospitalForFresh(param);
        List<String> newDelMemberIdList = newDelMemberList.stream().map(MemberJoinHospitalVO::getMemberId).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("returnTime",nowDateTime);
        result.put("rows",pageResult.getRows());
        result.put("totalPages",pageResult.getTotalPages());
        result.put("totalRows",pageResult.getTotalRows());
        result.put("pageNum",pageResult.getPageNum());
        result.put("newDelMemberIdList",newDelMemberIdList);
        result.put("newModifyCommiteeList",memberModifyList);
        return result;
    }

    @Override
    public Map<String, Object> listMemberJoinHospital(String hospitalId, PageRequest pr) {
        String nowDateTime = DateHelper.getDateFormatter();
        PageHelper.startPage(pr.getPage(), pr.getRows());
        Map<String, Object> param = new HashMap<>();
        param.put("hospitalId",hospitalId);
        param.put("refreshTime",nowDateTime);
        List<MemberJoinHospitalVO> newJoinMemberList = joinHospitalMapper.listGluMemberJoinHospital(param);
        newJoinMemberList.forEach(m ->{
            m.setAge(DateHelper.getAge(m.getBirthday()));
        });
        PageResult<MemberJoinHospitalVO> pageResult = new PageResult<>(newJoinMemberList);
        Map<String, Object> result = new HashMap<>();
        result.put("rows",pageResult.getRows());
        result.put("totalPages",pageResult.getTotalPages());
        result.put("totalRows",pageResult.getTotalRows());
        result.put("pageNum",pageResult.getPageNum());
        result.put("returnTime",nowDateTime);
        return result;
    }

    @Override
    public List<ListMemberVO> listGluManageMemberByKeyword(String hospitalId, String keyword) {
        Map<String, Object> param = new HashMap<>();
        param.put("hospitalId",hospitalId);
        param.put("mobilePhone",keyword);
        param.put("keyword",keyword);
        List<ListMemberVO> members = memberMapper.listGluHospitalMemberByKeyword(param);
        if(members != null && !members.isEmpty()) {
            members.forEach(m -> {
                if(!StringUtils.isBlank(m.getBirthday())) {
                    m.setAge(DateHelper.getAge(m.getBirthday()));
                }
            });
        }
        return members;
    }

    //默认分组处理
    private void defaultGroupHandler(List<DoctorGroupPO> groupList,String doctorId){
        DoctorGroupPO doctorGroupPO = new DoctorGroupPO();
        doctorGroupPO.setDoctorId(doctorId);
        doctorGroupPO.setGroupName(GroupConstant.DEFAULT_GROUP_NAME);
        doctorGroupPO.setGroupId(GroupConstant.DEFAULT_GROUP_ID);
        groupList.add(0, doctorGroupPO);
    }
}
