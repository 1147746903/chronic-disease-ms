package com.comvee.cdms.sign.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.app.doctorapp.model.app.SugarDetailListModel;
import com.comvee.cdms.bloodmonitor.constant.MonitorPlanConstant;
import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorTaskCardMapper;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorTaskCardPO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanRecordService;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanServiceI;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.department.mapper.DepartmentMapper;
import com.comvee.cdms.department.model.po.DepartmentPO;
import com.comvee.cdms.department.service.DepartmentService;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.differentlevels.service.DifferentLevelsService;
import com.comvee.cdms.differentlevels.vo.MemberCurrentDiffLevelVO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.cfg.DoctorPopupRemindConstant;
import com.comvee.cdms.doctor.dto.AddMemberHealthWarningNoticeDTO;
import com.comvee.cdms.doctor.dto.ListDoctorDTO;
import com.comvee.cdms.doctor.mapper.DoctorMapper;
import com.comvee.cdms.doctor.mapper.DoctorServiceSettingMapper;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.po.DoctorPopupRemindIgnoreTimePO;
import com.comvee.cdms.doctor.po.DoctorServiceSettingPO;
import com.comvee.cdms.doctor.po.MemberDoctorListPO;
import com.comvee.cdms.doctor.service.DoctorNoticeService;
import com.comvee.cdms.doctor.service.DoctorPopupRemindIgnoreTimeService;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.doctor.vo.DepartmentVO;
import com.comvee.cdms.doctor.vo.DoctorDepartVO;
import com.comvee.cdms.doctor.vo.DoctorDepartmentVO;
import com.comvee.cdms.dybloodsugar.dto.DyMemberSensorDTO;
import com.comvee.cdms.dybloodsugar.mapper.DYMemberSensorPOMapper;
import com.comvee.cdms.dybloodsugar.po.DYMemberSensorPO;
import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import com.comvee.cdms.dybloodsugar.po.DyRecordSettingPO;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DyMemberSettingService;
import com.comvee.cdms.dybloodsugar.service.DynamicBloodSugarStatService;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarIndexBaseVO;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarSettingsVO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.mapper.MemberInHospitalLogMapper;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.*;
import com.comvee.cdms.member.service.InHospitalMemberService;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.vo.*;
import com.comvee.cdms.other.constant.DoctorPushConstant;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.po.DoctorPushCachePO;
import com.comvee.cdms.other.service.DoctorPushService;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.packages.dto.ListValidMemberPackageDTO;
import com.comvee.cdms.packages.po.MemberPackagePO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.remind.constant.MemberRemindTypeConstant;
import com.comvee.cdms.remind.dto.AddMemberRemindDTO;
import com.comvee.cdms.remind.service.MemberRemindService;
import com.comvee.cdms.sign.bo.*;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.constant.SignDialogueConstant;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.mapper.BloodSugarMapper;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.po.MemberBloodSugarPO;
import com.comvee.cdms.sign.po.MemberParamValuePO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.sign.service.SignSuggestService;
import com.comvee.cdms.sign.service.SignSyncThirdService;
import com.comvee.cdms.sign.tool.BloodSugarTool;
import com.comvee.cdms.sign.vo.*;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import com.comvee.cdms.user.tool.SessionTool;
import com.comvee.cdms.virtualward.mapper.VirtualWardMapper;
import com.comvee.cdms.virtualward.model.param.ListVirtualWardMemberByMonitorParam;
import com.comvee.cdms.virtualward.model.po.VirtualWardDepartmentPO;
import com.comvee.cdms.virtualward.model.po.VirtualWardPO;
import com.comvee.cdms.virtualward.service.VirtualWardService;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
@Service("bloodSugarService")
public class BloodSugarServiceImpl implements BloodSugarService {

    private final static Logger log = LoggerFactory.getLogger(BloodSugarServiceImpl.class);

    @Autowired
    private BloodSugarMapper bloodSugarMapper;

    @Autowired
    private SignSuggestService signSuggestService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private MemberRemindService memberRemindService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private DoctorPushService doctorPushService;

    @Autowired
    private SignSyncThirdService signSyncThirdService;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private DifferentLevelsService differentLevelsService;

    @Autowired
    MemberMonitorPlanServiceI memberMonitorPlanService;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private VirtualWardMapper virtualWardMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private DYMemberSensorPOMapper dyMemberSensorPOMapper;

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private MemberInHospitalLogMapper memberInHospitalLogMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private DoctorServiceSettingMapper doctorServiceSettingMapper;

    @Autowired
    @Lazy
    private VirtualWardService virtualWardService;

    @Autowired
    private MemberMonitorTaskCardMapper memberMonitorTaskCardMapper;

    @Autowired
    private MemberMonitorPlanRecordService memberMonitorPlanRecordService;

    @Autowired
    @Lazy
    private InHospitalMemberService inHospitalMemberService;

    @Autowired
    private DynamicBloodSugarStatService dynamicBloodSugarStatService;

    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    @Autowired
    private DyMemberSettingService dyMemberSettingService;

    @Autowired
    private DoctorPopupRemindIgnoreTimeService doctorPopupRemindIgnoreTimeService;

    @Autowired
    private DoctorNoticeService doctorNoticeService;

    @Override
    public List<BloodSugarPO> listBloodSugar(ListBloodSugarDTO listBloodSugarDTO) {
        return this.bloodSugarMapper.listBloodSugar(listBloodSugarDTO);
    }

    @Override
    public List<Map<String ,Object>> listBloodSugarPage(ListBloodSugarDTO listBloodSugarDTO, PageRequest page) {
        Integer loadAll = listBloodSugarDTO.getLoadAll();
        List<Map<String ,Object>> reList=new ArrayList<>();
        Integer day=0;
        if("1".equals(listBloodSugarDTO.getCodeDt())){
            day=1;
        }else if("2".equals(listBloodSugarDTO.getCodeDt())){
            day=3;
        }else if("3".equals(listBloodSugarDTO.getCodeDt())){
            day=7;
        }else if("4".equals(listBloodSugarDTO.getCodeDt())){
            day=30;
        }else if("5".equals(listBloodSugarDTO.getCodeDt())){
            day=90;
        } else {
            String startDt = listBloodSugarDTO.getStartDt();
            String endDt = listBloodSugarDTO.getEndDt();
            if(!StringUtils.isBlank(startDt) && !StringUtils.isBlank(endDt)){
                day = DateHelper.dateCompareGetDay(endDt,startDt)+1;
            }
        }
        Integer inHos = listBloodSugarDTO.getInHos();
        for (int j = 0; j < day; j++) {
            Map<String ,Object> map=new HashMap<>();
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, -j);
            String startDate=DateHelper.getDate(c.getTime(), "yyyy-MM-dd");
            map.put("day",startDate);

            //随机血糖
            List<BloodSugarPO> randomtime = findSugar("randomtime", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos,loadAll);
            map.put("randomtimeList",randomtime);
            BloodSugarPO po = null;
            if(randomtime.size()>0){
                po = randomtime.get(0);
            }
            map.put("randomtime",po);

            List<BloodSugarPO> beforedawn = findSugar("beforedawn", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos,loadAll);
            map.put("beforedawnList",beforedawn);
            po = null;
            if(beforedawn.size()>0){
                po = beforedawn.get(0);
            }
            map.put("beforedawn",po);

            List<BloodSugarPO> beforeBreakfast = findSugar("beforeBreakfast", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos,loadAll);
            map.put("beforeBreakfastList",beforeBreakfast);
            po = null;
            if(beforeBreakfast.size()>0){
                po = beforeBreakfast.get(0);
            }
            map.put("beforeBreakfast",po);

            List<BloodSugarPO> afterBreakfast = findSugar("afterBreakfast", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos,loadAll);
            map.put("afterBreakfastList",afterBreakfast);
            po = null;
            if(afterBreakfast.size()>0){
                po = afterBreakfast.get(0);
            }
            map.put("afterBreakfast",po);

            List<BloodSugarPO> beforeLunch = findSugar("beforeLunch", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos,loadAll);
            map.put("beforeLunchList",beforeLunch);
            po = null;
            if(beforeLunch.size()>0){
                po = beforeLunch.get(0);
            }
            map.put("beforeLunch",po);

            List<BloodSugarPO> afterLunch = findSugar("afterLunch", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos,loadAll);
            map.put("afterLunchList",afterLunch);
            po = null;
            if(afterLunch.size()>0){
                po = afterLunch.get(0);
            }
            map.put("afterLunch",po);

            List<BloodSugarPO> beforeDinner = findSugar("beforeDinner", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos,loadAll);
            map.put("beforeDinnerList",beforeDinner);
            po = null;
            if(beforeDinner.size()>0){
                po = beforeDinner.get(0);
            }
            map.put("beforeDinner",po);

            List<BloodSugarPO> afterDinner = findSugar("afterDinner", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos,loadAll);
            map.put("afterDinnerList",afterDinner);
            po = null;
            if(afterDinner.size()>0){
                po = afterDinner.get(0);
            }
            map.put("afterDinner",po);

            List<BloodSugarPO> beforeSleep = findSugar("beforeSleep", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos,loadAll);
            map.put("beforeSleepList",beforeSleep);
            po = null;
            if(beforeSleep.size()>0){
                po = beforeSleep.get(0);
            }
            map.put("beforeSleep",po);

            List<BloodSugarPO> afterBreakfast1h = findSugar("afterBreakfast1h", listBloodSugarDTO.getMemberId(), startDate, startDate,null,loadAll);
            map.put("afterBreakfast1hList",afterBreakfast1h);
            po = null;
            if(afterBreakfast1h.size()>0){
                po = afterBreakfast1h.get(0);
            }
            map.put("afterBreakfast1h",po);

            List<BloodSugarPO> afterDinner1h = findSugar("afterDinner1h", listBloodSugarDTO.getMemberId(), startDate, startDate,null,loadAll);
            map.put("afterDinner1hList",afterDinner1h);
            po = null;
            if(afterDinner1h.size()>0){
                po = afterDinner1h.get(0);
            }
            map.put("afterDinner1h",po);

            List<BloodSugarPO> afterLunch1h = findSugar("afterLunch1h", listBloodSugarDTO.getMemberId(), startDate, startDate,null,loadAll);
            map.put("afterLunch1hList",afterLunch1h);
            po = null;
            if(afterLunch1h.size()>0){
                po = afterLunch1h.get(0);
            }
            map.put("afterLunch1h",po);

            reList.add(map);
        }
        return  reList;
    }

    @Override
    public List<Map<String, Object>> listBloodSugarByDate(ListBloodSugarDTO listBloodSugarDTO) {
        List<Map<String ,Object>> reList=new ArrayList<>();
        int day = DateHelper.dateCompareGetDay(listBloodSugarDTO.getEndDt(),listBloodSugarDTO.getStartDt());
        for (int j = 0; j <= day; j++) {
            Map<String ,Object> map=new HashMap<>();
            Calendar c = Calendar.getInstance();
            c.setTime(DateHelper.getDate(listBloodSugarDTO.getEndDt(),"yyyy-MM-dd"));
            c.add(Calendar.DATE, -j);
            String startDate=DateHelper.getDate(c.getTime(), "yyyy-MM-dd");
            map.put("day",startDate);

            BloodSugarPO beforeBreakfast = getNewBloodSugar("beforeBreakfast", listBloodSugarDTO.getMemberId(), startDate, startDate);
            map.put("beforeBreakfast",beforeBreakfast);

            BloodSugarPO afterBreakfast = getNewBloodSugar("afterBreakfast", listBloodSugarDTO.getMemberId(), startDate, startDate);
            map.put("afterBreakfast",afterBreakfast);

            BloodSugarPO beforeLunch = getNewBloodSugar("beforeLunch", listBloodSugarDTO.getMemberId(), startDate, startDate);
            map.put("beforeLunch",beforeLunch);

            BloodSugarPO afterLunch = getNewBloodSugar("afterLunch", listBloodSugarDTO.getMemberId(), startDate, startDate);
            map.put("afterLunch",afterLunch);

            BloodSugarPO beforeDinner = getNewBloodSugar("beforeDinner", listBloodSugarDTO.getMemberId(), startDate, startDate);
            map.put("beforeDinner",beforeDinner);

            BloodSugarPO afterDinner = getNewBloodSugar("afterDinner", listBloodSugarDTO.getMemberId(), startDate, startDate);
            map.put("afterDinner",afterDinner);

            reList.add(map);
        }
        return  reList;
    }

    //处理责任人信息 查所有类型血糖recordtype
    private List<BloodSugarListVO> findSugarWithOpName(String code,String mid,String startDt,String endDt,Integer inHos){
        List<BloodSugarListVO> sugarWithOpName = findSugarWithOpName(Collections.singletonList(code), mid, startDt, endDt, inHos);
        return sugarWithOpName;
    }

    private List<BloodSugarListVO> findSugarWithOpName(List<String> codes,String mid,String startDt,String endDt,Integer inHos){
        ListBloodSugarDTO min=new ListBloodSugarDTO();
        min.setMemberId(mid);
        min.setParamCodeList(codes);
        if (!StringUtils.isBlank(endDt)){
            min.setEndDt(endDt+ " 23:59:59");
        }
        if (!StringUtils.isBlank(startDt)){
            min.setStartDt(startDt+ " 00:00:00");
        }
        min.setInHos(inHos);
        List<BloodSugarPO> sugars = this.bloodSugarMapper.listBloodSugar(min);
        List<BloodSugarListVO> result = new ArrayList<>();
        sugars.forEach(e->{
            BloodSugarListVO vo = new BloodSugarListVO();
            BeanUtils.copyProperties(vo,e);
            if(e.getOperatorType().equals(1)){
                GetMemberDTO getMemberDTO = new GetMemberDTO();
                getMemberDTO.setMemberId(e.getOperatorId());
                MemberPO member = memberMapper.getMember(getMemberDTO);
                if (null != member){
                    vo.setOperatorName(member.getMemberName());
                }
            }else if (e.getOperatorType().equals(2)){
                DoctorPO doctor = doctorMapper.getDoctorById(e.getOperatorId());
                if (null != doctor){
                    vo.setOperatorName(doctor.getDoctorName());
                }
            }
            result.add(vo);
        });
        return  result;
    }

    // 查询一天的一个测量时间段 全部血糖值  loadAll传1为加载全部血糖记录类型，空则只加载监测类型recordType
    private List<BloodSugarPO> findSugar(String code,String mid,String startDt,String endDt,Integer inHos,Integer loadAll){
        return  findSugar(Collections.singletonList(code) ,mid ,startDt ,endDt, inHos,loadAll);
    }

    // 查询一天的多个测量时间段 全部血糖值
    private List<BloodSugarPO> findSugar(List<String> codes,String mid,String startDt,String endDt,Integer inHos,Integer loadAll){
        ListBloodSugarDTO min=new ListBloodSugarDTO();
        min.setMemberId(mid);
        min.setParamCodeList(codes);
        if (!StringUtils.isBlank(endDt)){
            min.setEndDt(endDt+ " 23:59:59");
        }
        if (!StringUtils.isBlank(startDt)){
            min.setStartDt(startDt+ " 00:00:00");
        }
        min.setInHos(inHos);
        if (null == loadAll || loadAll != 1){
            min.setRecordType(1);//监测类型
        }

        List<BloodSugarPO> listMin = this.bloodSugarMapper.listBloodSugar(min);
        return  listMin;
    }

    private BloodSugarPO getNewBloodSugar(String code,String mid,String startDt,String endDt){
        ListBloodSugarDTO min=new ListBloodSugarDTO();
        min.setMemberId(mid);
        List<String> paramCodeList=new ArrayList<>();
        paramCodeList.add(code);
        min.setParamCodeList(paramCodeList);
        min.setEndDt(endDt+ " 23:59:59");
        min.setStartDt(startDt+ " 00:00:00");
        BloodSugarPO po = this.bloodSugarMapper.getNewBloodSugar(min);
        return  po;
    }



    @Override
    public BloodSugarPO getBloodSugar(String sid) {
        return this.bloodSugarMapper.getBloodSugar(sid);
    }

    @Override
    public SignSuggestPO getBloodSugarSuggest(String signId) {
        return this.signSuggestService.getSignSuggetBySignId(signId);
    }

    @Override
    public String addBloodSugarSuggest(AddSuggestDTO addSuggestDTO) {
        BloodSugarPO bloodSugarPO = getBloodSugar(addSuggestDTO.getSignId());
        if(bloodSugarPO == null){
            throw new BusinessException("血糖记录已被删除");
        }
        AddSignSuggestDTO addSignSuggestDTO = new AddSignSuggestDTO();
        BeanUtils.copyProperties(addSignSuggestDTO, addSuggestDTO);
        addSignSuggestDTO.setSignType(SignConstant.SIGN_TYPE_BLOOD_SUGAR);
        addSignSuggestDTO.setMemberId(bloodSugarPO.getMemberId());
        return this.signSuggestService.addSignSuggest(addSignSuggestDTO);
    }

    @Override
    public String addBloodSugar(AddBloodSugarServiceDTO addBloodSugarServiceDTO) {
        //默认为监测类型
        addBloodSugarServiceDTO.setRecordType(null == addBloodSugarServiceDTO.getRecordType()?1:addBloodSugarServiceDTO.getRecordType());
        GetBloodSugarDTO get = new GetBloodSugarDTO();
        get.setMemberId(addBloodSugarServiceDTO.getMemberId());
        get.setRecordDt(addBloodSugarServiceDTO.getRecordDt());
        get.setParamValue(addBloodSugarServiceDTO.getParamValue());
        get.setParamCode(addBloodSugarServiceDTO.getParamCode());
        BloodSugarPO bloodSugar = this.bloodSugarMapper.searchBloodSugar(get);
        if(bloodSugar != null){
            log.warn("重复上传的血糖记录,血糖记录id:{} ,上传的血糖记录对象:{}" ,bloodSugar.getSid() ,JSON.toJSONString(addBloodSugarServiceDTO));
            return bloodSugar.getSid();
        }

        AddBloodSugarMapperDTO addBloodSugarMapperDTO = new AddBloodSugarMapperDTO();
        BeanUtils.copyProperties(addBloodSugarMapperDTO, addBloodSugarServiceDTO);
        sidHandler(addBloodSugarMapperDTO);
        //血糖等级判断
        bloodSugarLevelHandler(addBloodSugarMapperDTO);
        abnormalHandler(addBloodSugarMapperDTO);
        //设置默认的是否住院血糖值
        addBloodSugarMapperDTO.setInHos(Optional.ofNullable(addBloodSugarServiceDTO.getInHos()).orElse(0));
        //部门床号住院号处理（管理处方过来的这三个值为空）
        dauSugarRecordValueSet(addBloodSugarServiceDTO,addBloodSugarMapperDTO);
        this.bloodSugarMapper.addBloodSugar(addBloodSugarMapperDTO);
        //住院患者更新今日血糖记录
        if (addBloodSugarMapperDTO.getInHos() == 1){
            Date recordDt = DateHelper.getDate(addBloodSugarServiceDTO.getRecordDt(),"yyyy-MM-dd");
            String recordDtStr = DateHelper.getDate(recordDt,"yyyy-MM-dd");
            MemberParamValuePO valuePO = this.bloodSugarMapper.getWorkParamValueOfMember(addBloodSugarMapperDTO.getMemberId(), recordDtStr);
            if (null == valuePO){
                valuePO = new MemberParamValuePO();
            }
            this.setMemberParamValuePO(valuePO,addBloodSugarMapperDTO);
            if (!StringUtils.isBlank(valuePO.getSid()) && addBloodSugarServiceDTO.getRecordType().equals(1)){
                //判断是否查看
                DoctorServiceSettingPO po = this.doctorServiceSettingMapper.getServiceSetting(addBloodSugarServiceDTO.getOperatorId());
                BigDecimal paramValue = new BigDecimal(addBloodSugarServiceDTO.getParamValue());
                if (po == null){
                    BigDecimal maxDefault = new BigDecimal(SignConstant.SUGAR_HIGH);
                    BigDecimal minDefault = new BigDecimal(SignConstant.SUGAR_LOW);
                    if (maxDefault.compareTo(paramValue)<0 || minDefault.compareTo(paramValue)>0){
                        valuePO.setIsLook(0);
                    }
                }else{
                    BigDecimal max = new BigDecimal(po.getBloodSugarMax());
                    BigDecimal min = new BigDecimal(po.getBloodSugarMin());
                    if ( max.compareTo(paramValue) <0 || min.compareTo(paramValue) >0){
                        valuePO.setIsLook(0);
                    }
                }
                this.bloodSugarMapper.updateMemberBloodSugarOfEverDay(valuePO);
            }else{
                valuePO.setSid(DaoHelper.getSeq());
                valuePO.setIsLook(0);
                if (valuePO.getStandardValue() == null){
                    valuePO.setStandardValue(0D);
                }
                this.bloodSugarMapper.insertMemberBloodSugarOfEverDay(valuePO);
            }
        }
        dayMemberSugarRecordHandler(addBloodSugarServiceDTO,addBloodSugarMapperDTO);
        syncMemberArchives(addBloodSugarServiceDTO);
        syncMemberMonitorTaskCard(addBloodSugarServiceDTO);
        addHealthWarnWechatMessage(addBloodSugarMapperDTO);
        return addBloodSugarMapperDTO.getSid();
    }



    /**
     * 修改该患者所添加血糖/血压日期的任务卡为已检测
     * @param addBloodSugarServiceDTO
     */
    private void syncMemberMonitorTaskCard(AddBloodSugarServiceDTO addBloodSugarServiceDTO) {
        MemberMonitorTaskCardPO taskCardPO = new MemberMonitorTaskCardPO();
        taskCardPO.setParamCode(paramCodeValue(addBloodSugarServiceDTO.getParamCode()));
        taskCardPO.setMemberId(addBloodSugarServiceDTO.getMemberId());
        taskCardPO.setMonitorDt(addBloodSugarServiceDTO.getRecordDt());
        JSONObject json = new JSONObject();
        json.put("bs",addBloodSugarServiceDTO.getParamValue());
        String paramValue = json.toString();
        taskCardPO.setParamValue(paramValue);
        taskCardPO.setInsertDt(addBloodSugarServiceDTO.getRecordDt().substring(0,10));
        taskCardPO.setIsMonitor("1");
        //根据患者id,时段,日期查询该患者的任务卡为已检测
        this.memberMonitorPlanRecordService.updateMonitorTaskCard(taskCardPO);

    }

    private String paramCodeValue(String memberParamCode) {
        String paramCode  = "";
        if (memberParamCode.equals(SignConstant.PARAM_CODE_BEFORE_DAWN)){
            paramCode = "1";
        }else if (memberParamCode.equals(SignConstant.PARAM_CODE_BEFORE_BREAKFAST)){
            paramCode = "2";
        }else if (memberParamCode.equals(SignConstant.PARAM_CODE_AFTER_BREAKFAST)){
            paramCode = "3";
        }else if (memberParamCode.equals(SignConstant.PARAM_CODE_BEFORE_LUNCH)){
            paramCode = "4";
        }else if (memberParamCode.equals(SignConstant.PARAM_CODE_AFTER_LUNCH)){
            paramCode = "5";
        }else if (memberParamCode.equals(SignConstant.PARAM_CODE_BEFORE_DINNER)){
            paramCode = "6";
        }else if (memberParamCode.equals(SignConstant.PARAM_CODE_AFTER_DINNER)){
            paramCode = "7";
        }else if (memberParamCode.equals(SignConstant.PARAM_CODE_BEFORE_SLEEP)){
            paramCode = "8";
        }
        return paramCode;
    }

    /**
     * 主键处理
     * @param addBloodSugarMapperDTO
     */
    private void sidHandler(AddBloodSugarMapperDTO addBloodSugarMapperDTO){
        if(StringUtils.isBlank(addBloodSugarMapperDTO.getSid())){
            addBloodSugarMapperDTO.setSid(DaoHelper.getSeq());
        }
    }

    // 录入血糖同步到档案
    private void syncMemberArchives(AddBloodSugarServiceDTO addBloodSugarServiceDTO){

        if("beforeBreakfast".equals(addBloodSugarServiceDTO.getParamCode())||
                "afterBreakfast".equals(addBloodSugarServiceDTO.getParamCode())||
                "beforeSleep".equals(addBloodSugarServiceDTO.getParamCode())||
                "afterLunch".equals(addBloodSugarServiceDTO.getParamCode())||
                "afterDinner".equals(addBloodSugarServiceDTO.getParamCode())){

            Map<String,Object> archivesMap=new HashMap<>();
            Map<String,Object> treatmentMap=new HashMap<>();
            //mq_fbg : //空腹血糖
            //blg : //非空腹血糖
            //bsg ://睡觉前血糖
            //nw_mq_fbg 近一周空腹血糖
            //nw_blg //近一周平均餐后血糖
            //rec_blg最近一次餐后血糖
            String code="";
            String code1="";
            if("beforeBreakfast".equals(addBloodSugarServiceDTO.getParamCode())){
                code="mq_fbg";
                code1="nw_mq_fbg";
            }else if("afterBreakfast".equals(addBloodSugarServiceDTO.getParamCode())){
                code="blg";
                code1="nw_blg";
            }else if("beforeSleep".equals(addBloodSugarServiceDTO.getParamCode())){
                code="bsg";
            }
            if (!StringUtils.isBlank(code)){
                treatmentMap.put(code,addBloodSugarServiceDTO.getParamValue());
            }

            if("afterBreakfast".equals(addBloodSugarServiceDTO.getParamCode())||
                    "afterLunch".equals(addBloodSugarServiceDTO.getParamCode())||
                    "afterDinner".equals(addBloodSugarServiceDTO.getParamCode())){
                treatmentMap.put("rec_blg",addBloodSugarServiceDTO.getParamValue());
            }
            archivesMap.put("treatment",treatmentMap);

            MemberArchivesDTO memberArchivesDTO=new MemberArchivesDTO();
            memberArchivesDTO.setMemberId(addBloodSugarServiceDTO.getMemberId());
            memberArchivesDTO.setDoctorId(addBloodSugarServiceDTO.getOperatorId());
            memberArchivesDTO.setArchivesJson(JsonSerializer.objectToJson(archivesMap));
            this.memberService.updateMemberArchive(memberArchivesDTO);

            String memberId = addBloodSugarServiceDTO.getMemberId();
            Map<String, Object> map = this.getAvgWeekBlood(memberId);
            if("nw_mq_fbg".equals(code1)){
                treatmentMap.put(code1,map.get("kfAvg"));
            } else if("nw_blg".equals(code1)){
                treatmentMap.put(code1,map.get("chAvg"));
            }
            archivesMap.put("treatment",treatmentMap);
            memberArchivesDTO.setArchivesJson(JsonSerializer.objectToJson(archivesMap));
            this.memberService.updateMemberArchive(memberArchivesDTO);
        }
    }

    /**
     * 血糖范围处理
     * @param addBloodSugarMapperDTO
     */
    private void rangeHandler(AddBloodSugarMapperDTO addBloodSugarMapperDTO){
        RangeBO rangeBO = this.memberService.getMemberRange(addBloodSugarMapperDTO.getMemberId());
        if(rangeBO == null){
            throw new BusinessException("患者不存在，无法录入血糖");
        }
        switch (addBloodSugarMapperDTO.getParamCode()){
            case SignConstant.PARAM_CODE_BEFORE_BREAKFAST:
                addBloodSugarMapperDTO.setRangeMin(rangeBO.getLowBeforeBreakfast());
                addBloodSugarMapperDTO.setRangeMax(rangeBO.getHighBeforeBreakfast());
                break;
            case SignConstant.PARAM_CODE_AFTER_BREAKFAST:
            case SignConstant.PARAM_CODE_AFTER_DINNER:
            case SignConstant.PARAM_CODE_AFTER_LUNCH:
                addBloodSugarMapperDTO.setRangeMin(rangeBO.getLowAfterMeal());
                addBloodSugarMapperDTO.setRangeMax(rangeBO.getHighAfterMeal());
                break;
            case SignConstant.PARAM_CODE_AFTER_BREAKFAST_1H:
            case SignConstant.PARAM_CODE_AFTER_DINNER_1H:
            case SignConstant.PARAM_CODE_AFTER_LUNCH_1H:
                addBloodSugarMapperDTO.setRangeMin(rangeBO.getLowAfterMealOneHour());
                addBloodSugarMapperDTO.setRangeMax(rangeBO.getHighAfterMealOneHour());
                break;
            case SignConstant.PARAM_CODE_BEFORE_DINNER:
            case SignConstant.PARAM_CODE_BEFORE_LUNCH:
                addBloodSugarMapperDTO.setRangeMin(rangeBO.getLowBeforeMeal());
                addBloodSugarMapperDTO.setRangeMax(rangeBO.getHighBeforeMeal());
                break;
            case SignConstant.PARAM_CODE_BEFORE_SLEEP:
                addBloodSugarMapperDTO.setRangeMin(rangeBO.getLowBeforeSleep());
                addBloodSugarMapperDTO.setRangeMax(rangeBO.getHighBeforeSleep());
                break;
            case SignConstant.PARAM_CODE_BEFORE_DAWN:
                addBloodSugarMapperDTO.setRangeMin(rangeBO.getLowBeforedawn());
                addBloodSugarMapperDTO.setRangeMax(rangeBO.getHighBeforedawn());
                break;
            default:
                addBloodSugarMapperDTO.setRangeMin(rangeBO.getLowAfterMeal());
                addBloodSugarMapperDTO.setRangeMax(rangeBO.getHighAfterMeal());
                break;
        }
    }

    /**
     * 同步至第三方处理
     * @param addBloodSugarMapperDTO
     * @return
     */
    private boolean syncThirdHandler(AddBloodSugarMapperDTO addBloodSugarMapperDTO){
        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(addBloodSugarMapperDTO.getMemberId());
        listValidMemberPackageDTO.setCodeList(Collections.singletonList(ServiceCode.XUE_TANG_ZHI_NENG_GAN_YU));
        List<MemberPackagePO> list = this.packageService.listValidMemberPackage(listValidMemberPackageDTO);
        if(list == null || list.size() == 0){
            return false;
        }
        this.signSyncThirdService.syncBloodSugar(addBloodSugarMapperDTO);
        return true;
    }

    /**
     * 新增血糖微信消息
     * @param addBloodSugarMapperDTO
     */
    private void addBloodSugarWechatMessage(AddBloodSugarMapperDTO addBloodSugarMapperDTO ,String suggest){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", addBloodSugarMapperDTO.getRecordDt());
        jsonObject.put("value", addBloodSugarMapperDTO.getParamValue());
        jsonObject.put("level", addBloodSugarMapperDTO.getParamLevel());
        jsonObject.put("paramCode", addBloodSugarMapperDTO.getParamCode());
        jsonObject.put("suggest", suggest);
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(addBloodSugarMapperDTO.getMemberId());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR);
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        addWechatMessageDTO.setForeignId(addBloodSugarMapperDTO.getSid());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    /**
     * 添加健康预警微信消息-血糖
     * @param addBloodSugarMapperDTO
     */
    private void addHealthWarnWechatMessage(AddBloodSugarMapperDTO addBloodSugarMapperDTO){
        if (SignConstant.SIGN_LEVEL_NORMAL == addBloodSugarMapperDTO.getParamLevel()){
            return;
        }
        AddMemberHealthWarningNoticeDTO addMemberHealthWarningNoticeDTO = new AddMemberHealthWarningNoticeDTO();
        addMemberHealthWarningNoticeDTO.setMemberId(addBloodSugarMapperDTO.getMemberId());
        addMemberHealthWarningNoticeDTO.setDatetime(addBloodSugarMapperDTO.getRecordDt());
        addMemberHealthWarningNoticeDTO.setWarningTitle("血糖");
        addMemberHealthWarningNoticeDTO.setWarningContent(healthWarnValueHandler(addBloodSugarMapperDTO));
        addMemberHealthWarningNoticeDTO.setForeignId(addBloodSugarMapperDTO.getSid());
        this.doctorNoticeService.memberHealthWarningNotice(addMemberHealthWarningNoticeDTO);
    }

    private String healthWarnValueHandler(AddBloodSugarMapperDTO addBloodSugarMapperDTO){
        Integer paramLevel = addBloodSugarMapperDTO.getParamLevel();
        String level = paramLevel == 1?"偏低":"偏高";
        return addBloodSugarMapperDTO.getParamValue() + "mmol/L("+level + ")";
    }



    /**
     * 血糖级别处理
     * @param addBloodSugarServiceDTO
     * @return
     */
    private int bloodSugarLevelHandler(AddBloodSugarMapperDTO addBloodSugarServiceDTO){
        int level = SignConstant.SIGN_LEVEL_NORMAL;

            RangeBO rangeBO = this.memberService.getMemberRange(addBloodSugarServiceDTO.getMemberId());
            String rangeMin = addBloodSugarServiceDTO.getRangeMin();
            String rangeMax = addBloodSugarServiceDTO.getRangeMax();
            //录入血糖的控制范围为空时，取患者的控制目标
            if(StringUtils.isBlank(rangeMin) || StringUtils.isBlank(rangeMax)){
                switch (addBloodSugarServiceDTO.getParamCode()){
                    // 凌晨3点
                    case SignConstant.PARAM_CODE_3AM:
                        rangeMin = rangeBO.getLowBeforedawn();
                        rangeMax = rangeBO.getHighBeforedawn();
                        break;
                    //早餐后 午餐后 晚餐后
                    case SignConstant.PARAM_CODE_AFTER_BREAKFAST:
                    case SignConstant.PARAM_CODE_AFTER_DINNER:
                    case SignConstant.PARAM_CODE_AFTER_LUNCH:
                        rangeMin = rangeBO.getLowAfterMeal();
                        rangeMax = rangeBO.getHighAfterMeal();
                        break;
                    //空腹
                    case SignConstant.PARAM_CODE_BEFORE_BREAKFAST:
                        rangeMin = rangeBO.getLowBeforeBreakfast();
                        rangeMax = rangeBO.getHighBeforeBreakfast();
                        break;
                    //凌晨
                    case SignConstant.PARAM_CODE_BEFORE_DAWN:
                        rangeMin = rangeBO.getLowBeforedawn();
                        rangeMax = rangeBO.getHighBeforedawn();
                        break;
                    //晚餐前
                    //午餐前
                    case SignConstant.PARAM_CODE_BEFORE_DINNER:
                    case SignConstant.PARAM_CODE_BEFORE_LUNCH:
                        rangeMin = rangeBO.getLowBeforeMeal();
                        rangeMax = rangeBO.getHighBeforeMeal();
                        break;
                    //睡前
                    case SignConstant.PARAM_CODE_BEFORE_SLEEP:
                        rangeMin = rangeBO.getLowBeforeSleep();
                        rangeMax = rangeBO.getHighBeforeSleep();
                        break;
                    //随机血糖
                    case SignConstant.PARAM_CODE_RANDOM_TIME:
                        rangeMin = rangeBO.getLowRandomSugar();
                        rangeMax = rangeBO.getHighRandomSugar();
                        break;
                    //餐后1小时
                    case SignConstant.PARAM_CODE_AFTER_BREAKFAST_1H:
                    case SignConstant.PARAM_CODE_AFTER_DINNER_1H:
                    case SignConstant.PARAM_CODE_AFTER_LUNCH_1H:
                        rangeMin = rangeBO.getLowAfterMealOneHour();
                        rangeMax = rangeBO.getHighAfterMealOneHour();
                        break;
                    default:
                        break;
                }
            }
        if (addBloodSugarServiceDTO.getRecordType().equals(1)){
            Float value = Float.parseFloat(addBloodSugarServiceDTO.getParamValue());
            if(value > Float.parseFloat(rangeMax)){
                level = SignConstant.SIGN_LEVEL_HIGH;
            }else if(value < Float.parseFloat(rangeMin)){
                level = SignConstant.SIGN_LEVEL_LOW;
            }
        }else {
            level = -1;
        }
         addBloodSugarServiceDTO.setRangeMax(rangeMax);
         addBloodSugarServiceDTO.setRangeMin(rangeMin);
         addBloodSugarServiceDTO.setParamLevel(level);

        return level;
    }

    /**
     * 血糖异常处理
     * @param addBloodSugarMapperDTO
     */
    private void abnormalHandler(AddBloodSugarMapperDTO addBloodSugarMapperDTO){
        //非监测类型不提醒
        if(!addBloodSugarMapperDTO.getRecordType().equals(1)){
            return;
        }
        //患者录入的血糖则下发异常提醒
        if(SignConstant.SIGN_OPERATOR_TYPE_MEMBER != addBloodSugarMapperDTO.getOperatorType()){
            return;
        }

        sendIntelligentTrackRemind(addBloodSugarMapperDTO);
        String suggest = BloodSugarTool.getIntelligentSuggestion(addBloodSugarMapperDTO.getParamCode(), addBloodSugarMapperDTO.getParamLevel()
                ,Float.parseFloat(addBloodSugarMapperDTO.getParamValue()));
        addBloodSugarWechatMessage(addBloodSugarMapperDTO ,suggest);

        List<ServiceCode> serviceCodes = new ArrayList<>();
        serviceCodes.add(ServiceCode.ZHI_NENG_ZHUI_ZONG);
        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(addBloodSugarMapperDTO.getMemberId());
        listValidMemberPackageDTO.setCodeList(serviceCodes);
        List<MemberPackagePO> list = this.packageService.listValidMemberPackage(listValidMemberPackageDTO);
        boolean hasValidPackage = list != null && !list.isEmpty();
        if(hasValidPackage){
            if(SignConstant.SIGN_LEVEL_NORMAL != addBloodSugarMapperDTO.getParamLevel()){
                sendAbnormalDialogue(addBloodSugarMapperDTO ,list);
            }
        }
    }


    /**
     * 下发智能追踪提醒
     */
    private void sendIntelligentTrackRemind(AddBloodSugarMapperDTO addBloodSugarMapperDTO){
        AddMemberRemindDTO addMemberRemindDTO = new AddMemberRemindDTO();
        addMemberRemindDTO.setForeignId(addBloodSugarMapperDTO.getSid());
        addMemberRemindDTO.setMemberId(addBloodSugarMapperDTO.getMemberId());
        addMemberRemindDTO.setRemindMessage(BLOOD_SUGAR_DIALOGUE_TITLE);
        addMemberRemindDTO.setRemindType(MemberRemindTypeConstant.REMIND_TYPE_ABNORMAL_BLOOD_SUGAR);
        BloodSugarAbnormalRemindBO bo = new BloodSugarAbnormalRemindBO();
        bo.setCode(addBloodSugarMapperDTO.getParamCode());
        bo.setDate(addBloodSugarMapperDTO.getRecordDt());
        bo.setLevel(addBloodSugarMapperDTO.getParamLevel().toString());
        bo.setLogId(addBloodSugarMapperDTO.getSid());
        bo.setTime(addBloodSugarMapperDTO.getRecordDt());
        bo.setTitle(BLOOD_SUGAR_DIALOGUE_TITLE);
        bo.setUnit(BLOOD_SUGAR_UNIT);
        bo.setContent(BloodSugarTool.getIntelligentSuggestion(addBloodSugarMapperDTO.getParamCode(), addBloodSugarMapperDTO.getParamLevel()
                ,Float.parseFloat(addBloodSugarMapperDTO.getParamValue())));
        bo.setValue(addBloodSugarMapperDTO.getParamValue());
        addMemberRemindDTO.setDataJsonStr(JSON.toJSONString(bo));
        this.memberRemindService.addMemberRemind(addMemberRemindDTO);
    }

    /**
     * 发送异常对话
     * @param addBloodSugarMapperDTO
     */
    private void sendAbnormalDialogue(AddBloodSugarMapperDTO addBloodSugarMapperDTO ,List<MemberPackagePO> list){
        Set<String> set = list.stream().map(MemberPackagePO::getDoctorId).collect(Collectors.toSet());
        set.forEach(x -> {
            DialoguePO dialoguePO = new DialoguePO();
            dialoguePO.setDoctorId(x);
            dialoguePO.setMemberId(addBloodSugarMapperDTO.getMemberId());
            dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_MEMBER);
            dialoguePO.setPatientMsg(BLOOD_SUGAR_DIALOGUE_TITLE);
            dialoguePO.setDoctorMsg(BLOOD_SUGAR_DIALOGUE_TITLE);
            dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
            dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
            dialoguePO.setMsgType(DialogueConstant.DIALOGUE_MSG_TYPE_SIGN_ABNORMAL);
            dialoguePO.setSenderId(x);
            dialoguePO.setForeignId(addBloodSugarMapperDTO.getSid());
            BloodSugarAbnormalDialogueBO bloodSugarAbnormalDialogueBO = new BloodSugarAbnormalDialogueBO();
            bloodSugarAbnormalDialogueBO.setCode(addBloodSugarMapperDTO.getParamCode());
            bloodSugarAbnormalDialogueBO.setDate(addBloodSugarMapperDTO.getRecordDt());
            bloodSugarAbnormalDialogueBO.setLevel(addBloodSugarMapperDTO.getParamLevel().toString());
            bloodSugarAbnormalDialogueBO.setLogId(addBloodSugarMapperDTO.getSid());
            bloodSugarAbnormalDialogueBO.setTime(addBloodSugarMapperDTO.getRecordDt());
            bloodSugarAbnormalDialogueBO.setTitle(BLOOD_SUGAR_DIALOGUE_TITLE);
            bloodSugarAbnormalDialogueBO.setValue(addBloodSugarMapperDTO.getParamValue());
            bloodSugarAbnormalDialogueBO.setUnit(BLOOD_SUGAR_UNIT);
            bloodSugarAbnormalDialogueBO.setTextType(SignDialogueConstant.DIALOGUE_TEXT_TYPE_SUGAR);
            dialoguePO.setDataStr(JSON.toJSONString(bloodSugarAbnormalDialogueBO));
            this.dialogueService.addDialogue(dialoguePO);
//            addBloodSugarPush(addBloodSugarMapperDTO, x);
        });
    }

    /**
     * 添加血糖异常推送
     * @param addBloodSugarMapperDTO
     * @param doctorId
     */
    private void addBloodSugarPush(AddBloodSugarMapperDTO addBloodSugarMapperDTO, String doctorId){
        DoctorPushCachePO doctorPushCachePO = new DoctorPushCachePO();
        doctorPushCachePO.setDoctorId(doctorId);
        doctorPushCachePO.setPushType(DoctorPushConstant.PUSH_TYPE_SIGN);
        doctorPushCachePO.setPushMessage(bloodSugarPushMessageHandler(addBloodSugarMapperDTO));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("t", DoctorPushConstant.PUSH_BUSINESS_TYPE_SIGN_BLOOD_SUGAR);
        doctorPushCachePO.setCustomInfo(jsonObject.toJSONString());
        this.doctorPushService.addDoctorPushCache(doctorPushCachePO);
    }

    /**
     * 血糖异常推送消息
     * @param addBloodSugarMapperDTO
     * @return
     */
    private String bloodSugarPushMessageHandler(AddBloodSugarMapperDTO addBloodSugarMapperDTO){
        String memberName = getMemberNameById(addBloodSugarMapperDTO.getMemberId());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(memberName)
                .append("的")
                .append(SignConstant.BLOOD_SUGAR_TIME_TEXT.get(addBloodSugarMapperDTO.getParamCode()))
                .append("血糖为:")
                .append(addBloodSugarMapperDTO.getParamValue())
                .append(BLOOD_SUGAR_UNIT)
                .append(",")
                .append(addBloodSugarMapperDTO.getParamLevel() == SignConstant.SIGN_LEVEL_HIGH ? "偏高":"偏低");
        return stringBuilder.toString();
    }

    /**
     * 获取患者姓名
     * @param memberId
     * @return
     */
    private String getMemberNameById(String memberId){
        MemberPO memberPO = this.memberService.getMemberById(memberId);
        if(memberPO == null){
            return "未知患者";
        }
        return memberPO.getMemberName();
    }

    @Override
    public Map<String, Object>  loadBloodAvgMaxMin(CountBloodSugarDTO countBloodSugarDTO) {
        doCountBloodSugarDTO(countBloodSugarDTO);
        Map<String, Object>  vo = this.bloodSugarMapper.loadBloodAvgMaxMin(countBloodSugarDTO);
        return vo;
    }

    private void doCountBloodSugarDTO(CountBloodSugarDTO countBloodSugarDTO){
        if(!StringUtils.isBlank(countBloodSugarDTO.getCodeDt())){
            //日期时间工具
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            //1、今日  2、三天  3、七天 4、一个月（30天）
            if("1".equals(countBloodSugarDTO.getCodeDt())){//
                c.add(Calendar.DATE, 0);
            }else if("2".equals(countBloodSugarDTO.getCodeDt())){
                c.add(Calendar.DATE, -2);
            }else if("3".equals(countBloodSugarDTO.getCodeDt())){
                c.add(Calendar.DATE, -6);
            }else if("4".equals(countBloodSugarDTO.getCodeDt())){
                c.add(Calendar.DATE, -29);
            }else if("5".equals(countBloodSugarDTO.getCodeDt())){
                c.add(Calendar.DATE, -89);
            }else if("6".equals(countBloodSugarDTO.getCodeDt())){
                c.add(Calendar.DATE, -179);
            }
            String startDate=DateHelper.getDate(c.getTime(), "yyyy-MM-dd");
            String endDate= DateHelper.getDate(new Date(), "yyyy-MM-dd");
            countBloodSugarDTO.setStartDt(startDate +" 00:00:00");
            countBloodSugarDTO.setEndDt(endDate +" 23:59:59");
        }
        List<String> paramCodeList=new ArrayList<>();
        if(!StringUtils.isBlank(countBloodSugarDTO.getParamCode())){
            if("after".equals(countBloodSugarDTO.getParamCode())){
                paramCodeList.add("afterBreakfast");
                paramCodeList.add("afterLunch");
                paramCodeList.add("afterDinner");

                paramCodeList.add(SignConstant.PARAM_CODE_AFTER_BREAKFAST_1H);
                paramCodeList.add(SignConstant.PARAM_CODE_AFTER_DINNER_1H);
                paramCodeList.add(SignConstant.PARAM_CODE_AFTER_LUNCH_1H);

            }else{
                paramCodeList.add(countBloodSugarDTO.getParamCode());
                if ("afterBreakfast".equals(countBloodSugarDTO.getParamCode())){
                    paramCodeList.add(SignConstant.PARAM_CODE_AFTER_BREAKFAST_1H);
                }else if ("afterLunch".equals(countBloodSugarDTO.getParamCode())){
                    paramCodeList.add(SignConstant.PARAM_CODE_AFTER_LUNCH_1H);
                }else if ("afterDinner".equals(countBloodSugarDTO.getParamCode())){
                    paramCodeList.add(SignConstant.PARAM_CODE_AFTER_DINNER_1H);
                }
            }
        }else{
            paramCodeList.add("beforedawn");
            paramCodeList.add("beforeBreakfast");
            paramCodeList.add("afterBreakfast");
            paramCodeList.add("beforeLunch");
            paramCodeList.add("afterLunch");
            paramCodeList.add("beforeDinner");
            paramCodeList.add("afterDinner");
            paramCodeList.add("beforeSleep");
            paramCodeList.add(SignConstant.PARAM_CODE_AFTER_BREAKFAST_1H);
            paramCodeList.add(SignConstant.PARAM_CODE_AFTER_DINNER_1H);
            paramCodeList.add(SignConstant.PARAM_CODE_AFTER_LUNCH_1H);
        }
        countBloodSugarDTO.setParamCodeList(paramCodeList);
    }



    @Override
    public Map<String, Object> loadBloodNumHigLow(CountBloodSugarDTO countBloodSugarDTO) {
        doCountBloodSugarDTO(countBloodSugarDTO);
        return this.bloodSugarMapper.loadBloodNumHigLow(countBloodSugarDTO);
    }

    @Override
    public Map<String, Object> findBloodNormalByStageDay(CountBloodSugarDTO countBloodSugarDTO) {
        Map<String,Object> map=new HashMap<String, Object>();
        doCountBloodSugarDTO(countBloodSugarDTO);
        List<Map<String, Object>> list = this.bloodSugarMapper.findBloodNormalByStageDay(countBloodSugarDTO);
        //计算指定两日期相隔的天数,精确到天
        int sum= DateHelper.dateCompareGetDay(countBloodSugarDTO.getEndDt(), countBloodSugarDTO.getStartDt())+1;
        List<String> xlist=new ArrayList<String>();
        List<String> xlist2=new ArrayList<String>();
        for (int i = 0; i < sum; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, -i);
            String date = DateHelper.getDate(c.getTime(), "MM-dd");
            String date2 = DateHelper.getDate(c.getTime(), "yyyy-MM-dd");
            xlist.add(date);
            xlist2.add(date2);
        }
        //list头尾调换顺序
        Collections.reverse(xlist);
        Collections.reverse(xlist2);
        String datalist="";
        List<Double> avglist=new ArrayList<Double>();
        if(list.size()>0){
            RangeBO rangeBO = memberService.getMemberRange(countBloodSugarDTO.getMemberId());

            for (int j = 0; j < xlist.size(); j++) {
                int count = 0;
                Double avgSum = 0D;
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).get("day").equals(xlist2.get(j))) {
                        // status 血糖状态
                        String sugarRange = list.get(i).get("paramCode").toString();
                        String oValue = list.get(i).get("sugarValue").toString();
                        int status = checkSugarRange(sugarRange,oValue,rangeBO);
                        datalist += j+","+oValue +","+status+"&";
                        if(!oValue.equals(null)&&!oValue.equals("")) {
                            avgSum +=Double.parseDouble(oValue.toString());
                            count++;
                        }
                    }
                }
                if(count>0){
                    avglist.add(avgSum/count);
                }
                else{
                    avglist.add(0D);
                }
            }
        }
        map.put("xlist", xlist);
        map.put("datalist", datalist);
        map.put("linelist", avglist);
        return map;
    }

    @Override
    public List<BloodSugarPO> listMemberBloodSugarByDoctorId(String startDt, String endDt, String doctorId) {
        return this.bloodSugarMapper.listMemberBloodSugarByDoctorId(startDt, endDt, doctorId);
    }

    @Override
    public BloodSugarPO getLatestBloodSugar(String memberId ,String paramCode,String recordDt) {
        return this.bloodSugarMapper.getLatestBloodSugar(memberId ,paramCode,recordDt);
    }

    @Override
    public BloodSugarInHosBO getLatestBloodSugarInHos(String memberId, String inHospitalDt) {
        return this.bloodSugarMapper.getLatestBloodSugarInHos(memberId,inHospitalDt);
    }

    private int checkSugarRange(String sugarRange,String sugarValue, RangeBO healthRangeSet) {
        int re=3;
        float sValue=Float.parseFloat(sugarValue);
        //beforeBreakfast 空腹
        if("beforeBreakfast".equals(sugarRange)){
            re=checkSugarRange(sValue, healthRangeSet.getHighBeforeBreakfast(), healthRangeSet.getLowBeforeBreakfast());
        }
        // 餐前
        else if("beforeLunch".equals(sugarRange)||
                "beforeDinner".equals(sugarRange)){
            re=checkSugarRange(sValue,healthRangeSet.getHighBeforeMeal(),healthRangeSet.getLowBeforeMeal());
        }
        // 餐后
        else if("afterBreakfast".equals(sugarRange)||
                "afterLunch".equals(sugarRange)||
                "afterDinner".equals(sugarRange)){
            re=checkSugarRange(sValue,healthRangeSet.getHighAfterMeal(),healthRangeSet.getLowAfterMeal());
        }
        // 睡前
        else if("beforeSleep".equals(sugarRange)){
            re=checkSugarRange(sValue,healthRangeSet.getHighBeforeSleep(),healthRangeSet.getLowBeforeSleep());
        }
        // 凌晨
        else if("beforedawn".equals(sugarRange)){
            re=checkSugarRange(sValue, healthRangeSet.getHighBeforedawn(), healthRangeSet.getLowBeforedawn());
        }
        return re;
    }

    private int checkSugarRange(float sugarValue, String maxStr, String minStr) {
        int re=3;
        if(StringUtils.isBlank(maxStr)||StringUtils.isBlank(minStr)){
            return re;
        }
        float max=Float.parseFloat(maxStr);
        float min=Float.parseFloat(minStr);
        if(sugarValue < min) {
            re=1;
        }
        if(sugarValue > max) {
            re=5;
        }
        return re;
    }


    @Override
    public PageResult<SugarMemberVO> loadBloodSugarByDay(ListBloodSugarByDayDTO listBloodSugarByDayDTO, PageRequest page) {
        doListBloodSugarByDayDTO(listBloodSugarByDayDTO);
        DoctorPO doctorPO = this.doctorService.getDoctorById(listBloodSugarByDayDTO.getDoctorId());
        String hospitalId = doctorPO.getHospitalId();
        PageHelper.startPage(page.getPage(),page.getRows());
        List<SugarMemberVO> list = this.bloodSugarMapper.loadBloodSugarByDay(listBloodSugarByDayDTO);
        if(list!=null&&list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                SugarMemberVO sugarMemberVO = list.get(i);
                listBloodSugarByDayDTO.setMemberId(sugarMemberVO.getMemberId());
                Map numMap = this.bloodSugarMapper.loadLowBloodSugarByMemberId(listBloodSugarByDayDTO);
                if(numMap!=null && numMap.size()>0){
                    Object numObj = numMap.get("num");
                    if(numObj!=null){
                        sugarMemberVO.setNum(numObj.toString());
                    }
                    Object sidListObj = numMap.get("sidList");
                    if(sidListObj!=null){
                        sugarMemberVO.setLogIdList(sidListObj.toString());
                    }
                }


                MemberCurrentDiffLevelVO memberCurrentDiffLevelResult = this.differentLevelsService.getMemberCurrentDiffLevelResult(sugarMemberVO.getMemberId(),hospitalId);
                if(memberCurrentDiffLevelResult!=null){
                    sugarMemberVO.setDsmeHx(memberCurrentDiffLevelResult.getLevel()+"");
                    sugarMemberVO.setLevelHx(memberCurrentDiffLevelResult.getLayer()+"");
                } else {
                    sugarMemberVO.setDsmeHx(null);
                    sugarMemberVO.setLevelHx(null);
                }
            }
        }
        return  new PageResult<SugarMemberVO>(list);
    }

    @Override
    public PageResult<SugarMemberVO> loadNotBloodSugarByDay(ListBloodSugarByDayDTO listBloodSugarByDayDTO, PageRequest page) {
        DoctorPO doctorPO = this.doctorService.getDoctorById(listBloodSugarByDayDTO.getDoctorId());
        String hospitalId = doctorPO.getHospitalId();
        // doListBloodSugarByDayDTO(listBloodSugarByDayDTO);
        if("1".equals(listBloodSugarByDayDTO.getCodeDt())){
            if(page.getPage()==1){
                //30天未测血糖的患者
                ListBloodSugarByDayDTO param = new ListBloodSugarByDayDTO();
                BeanUtils.copyProperties(param,listBloodSugarByDayDTO);
                param.setCodeDt("2");
                List<SugarMemberVO> sugarMemberListFor30Day = this.bloodSugarMapper.loadNotBloodSugarByDay(param);
                //7天未测血糖的患者
                param.setCodeDt("1");
                List<SugarMemberVO> sugarMemberListFor7Day = this.bloodSugarMapper.loadNotBloodSugarByDay(param);
                //7天未测血糖的患者(不含30天未测血糖的患者）
                List<SugarMemberVO> sugarMemberList = new ArrayList<>();
                for(SugarMemberVO sugarMemberVO1 : sugarMemberListFor7Day){
                    boolean flag = false;
                    for(SugarMemberVO sugarMemberVO2 : sugarMemberListFor30Day){
                        if(sugarMemberVO1.getMemberId().equals(sugarMemberVO2.getMemberId())){
                            flag = true;
                            break;
                        }
                    }
                    if(!flag){
                        MemberCurrentDiffLevelVO memberCurrentDiffLevelResult = this.differentLevelsService.getMemberCurrentDiffLevelResult(sugarMemberVO1.getMemberId(),hospitalId);
                        if(memberCurrentDiffLevelResult!=null){
                            sugarMemberVO1.setDsmeHx(memberCurrentDiffLevelResult.getLevel()+"");
                            sugarMemberVO1.setLevelHx(memberCurrentDiffLevelResult.getLayer()+"");
                        } else {
                            sugarMemberVO1.setDsmeHx(null);
                            sugarMemberVO1.setLevelHx(null);
                        }
                        sugarMemberList.add(sugarMemberVO1);
                    }
                }
                PageResult<SugarMemberVO> pageResult = new PageResult<SugarMemberVO>(sugarMemberList);
                pageResult.setTotalPages(1);
                pageResult.setTotalRows(sugarMemberList.size());
                pageResult.setPageNum(page.getPage());
                pageResult.setPageSize(sugarMemberList.size());
                return pageResult;
            } else {
                return new PageResult<SugarMemberVO>(new ArrayList<SugarMemberVO>());
            }

        } else {
            PageHelper.startPage(page.getPage(),page.getRows());
            List<SugarMemberVO> sugarMemberList = this.bloodSugarMapper.loadNotBloodSugarByDay(listBloodSugarByDayDTO);
            if(sugarMemberList!=null&&sugarMemberList.size()>0){
                for (int i = 0; i < sugarMemberList.size(); i++) {
                    SugarMemberVO sugarMemberVO = sugarMemberList.get(i);
                    MemberCurrentDiffLevelVO memberCurrentDiffLevelResult = this.differentLevelsService.getMemberCurrentDiffLevelResult(sugarMemberVO.getMemberId(),hospitalId);
                    if(memberCurrentDiffLevelResult!=null){
                        sugarMemberVO.setDsmeHx(memberCurrentDiffLevelResult.getLevel()+"");
                        sugarMemberVO.setLevelHx(memberCurrentDiffLevelResult.getLayer()+"");
                    } else {
                        sugarMemberVO.setDsmeHx(null);
                        sugarMemberVO.setLevelHx(null);
                    }
                }
            }
            return new PageResult<SugarMemberVO>(sugarMemberList);
        }
    }

    @Override
    public List<BloodSugarPO> loadBloodSugarByMemberIdAndDay(String memberId,String codeDt) {
        ListBloodSugarByDayDTO listBloodSugarByDayDTO = new ListBloodSugarByDayDTO();
        listBloodSugarByDayDTO.setMemberId(memberId);
        listBloodSugarByDayDTO.setCodeDt(codeDt);
        doListBloodSugarByDayDTO(listBloodSugarByDayDTO);
        return this.bloodSugarMapper.loadBloodSugarByMemberIdAndDay(listBloodSugarByDayDTO);
    }

    @Override
    public Map<String, Object> countBloodSugarWeek(ListBloodSugarByDayDTO listBloodSugarByDayDTO) {
        doListBloodSugarByDayDTO(listBloodSugarByDayDTO);
        return this.bloodSugarMapper.countBloodSugarWeek(listBloodSugarByDayDTO);
    }

    @Override
    public List<SugarDetailListModel> getBloodThreeDayListByStatus(ListBloodSugarByDayDTO listBloodSugarByDayDTO) {
        doListBloodSugarByDayDTO(listBloodSugarByDayDTO);
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        return this.bloodSugarMapper.getBloodThreeDayListByStatus(listBloodSugarByDayDTO.getDoctorId(),listBloodSugarByDayDTO.getStartDt(),
                listBloodSugarByDayDTO.getEndDt(),null,doctorModel.getHospitalId());
    }

    private void doListBloodSugarByDayDTO(ListBloodSugarByDayDTO listBloodSugarByDayDTO){
        if(!StringUtils.isBlank(listBloodSugarByDayDTO.getCodeDt())){
            //日期时间工具
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            //1、七天 2、一个月（30天） 3、2周  4、3周
            if("1".equals(listBloodSugarByDayDTO.getCodeDt())){
                c.add(Calendar.DATE, -6);
            }else if("2".equals(listBloodSugarByDayDTO.getCodeDt())){
                c.add(Calendar.DATE, -29);
            }else if("3".equals(listBloodSugarByDayDTO.getCodeDt())){
                c.add(Calendar.DATE, -13);
            }else if("4".equals(listBloodSugarByDayDTO.getCodeDt())){
                c.add(Calendar.DATE, -20);
            }
            String startDate=DateHelper.getDate(c.getTime(), "yyyy-MM-dd");
            String endDate= DateHelper.getDate(new Date(), "yyyy-MM-dd");
            listBloodSugarByDayDTO.setStartDt(startDate +" 00:00:00");
            listBloodSugarByDayDTO.setEndDt(endDate +" 23:59:59");
        }
    }

    @Override
    public PageResult<ListAllBloodSugarDTO> listAllMemberBloodSugar(String memberId, PageRequest page) {
        PageHelper.startPage(page.getPage(),page.getRows());
        List<ListAllBloodSugarDTO> list = this.bloodSugarMapper.listAllMemberBloodSugar(memberId);
        return new PageResult<>(list);
    }

    @Override
    public Map<String, Object> countNewBloodAndMember(SynthesizeDataDTO synthesizeDataDTO) {

        ListDoctorDTO doctorDTO = new ListDoctorDTO();
//        doctorDTO.setHospitalName(synthesizeDataDTO.getHospitalName());
//        doctorDTO.setDepartmentName(synthesizeDataDTO.getDepartmentName());
//        doctorDTO.setDoctorName(synthesizeDataDTO.getDoctorName());
        doctorDTO.setHospitalId(synthesizeDataDTO.getHospitalId());
        doctorDTO.setDepartmentId(synthesizeDataDTO.getDepartmentId());
        doctorDTO.setDoctorId(synthesizeDataDTO.getDoctorId());
        List<DoctorPO> result = this.doctorService.listDoctorOne( doctorDTO);
        List<String> doctorIdList = new ArrayList<>();
        if (result != null && result.size()>0){
            for (DoctorPO po : result) {
                doctorIdList.add(po.getDoctorId());
            }
        }
        ListMemberBloodNumDTO bloodNumDTO = new ListMemberBloodNumDTO();
        bloodNumDTO.setStartDt(synthesizeDataDTO.getStartDt());
        bloodNumDTO.setEndDt(synthesizeDataDTO.getEndDt());
        bloodNumDTO.setDoctorList(doctorIdList);
        Map<String, Object> map = this.bloodSugarMapper.countNewBloodAndMember(bloodNumDTO);


        return map;
    }


    @Override
    public void deletMemberBloodSugar(String sid) {

        return;
    }

    @Override
    public Map<String, Object> countWeekAndAll(MemberDataDTO memberDataDTO) {
        Map<String, Object> map = new HashMap<>();
        long countBlood = this.bloodSugarMapper.countBloodSugar(memberDataDTO);
        map.put("countBlood",countBlood);
        long week = DateHelper.getDistanceWeek(memberDataDTO.getStartDt(), memberDataDTO.getEndDt());
        if (week == 0){
            week = 1L;
        }
        long countWeek = countBlood / week;
        map.put("countWeek",countWeek);
        return map;
    }


    @Override
    public List<BloodSugarPO> listSugar(ListBloodSugarDTO listBloodSugarDTO) {
        return this.bloodSugarMapper.listSugar(listBloodSugarDTO);
    }

    @Override
    public PageResult<Map<String, Object>> listBloodSugarPageOfInHos(ListBloodSugarDTO listBloodSugarDTO, PageRequest page) {
        Integer inHos = listBloodSugarDTO.getInHos();
        if(!StringUtils.isBlank(listBloodSugarDTO.getStartDt())){
            listBloodSugarDTO.setStartDt(listBloodSugarDTO.getStartDt()+ " 00:00:00");
        }
        if(!StringUtils.isBlank(listBloodSugarDTO.getEndDt())){
            listBloodSugarDTO.setEndDt(listBloodSugarDTO.getEndDt()+ " 23:59:59");
        }
        List<Map<String ,Object>> reList=new ArrayList<>();
        PageHelper.startPage(page.getPage(),page.getRows());
        List<String> dayList = bloodSugarMapper.getSugarRecordDayByMemberId(listBloodSugarDTO);
        if (dayList.size() == 0){
            return null;
        }
        for (String startDate : dayList) {
            Map<String ,Object> map=new HashMap<>();
            map.put("day",startDate);
            //随机血糖
            List<BloodSugarListVO> randomtime = findSugarWithOpName("randomtime", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos);
            map.put("randomtimeList",randomtime);
            BloodSugarListVO vo = null;
            if(randomtime.size()>0){
                vo = randomtime.get(0);
            }
            map.put("randomtime",vo);


            List<String> codeList = new ArrayList<>();
            codeList.add(SignConstant.PARAM_CODE_BEFORE_DAWN);
            codeList.add(SignConstant.PARAM_CODE_3AM);
            List<BloodSugarListVO> beforedawn = findSugarWithOpName(codeList, listBloodSugarDTO.getMemberId(), startDate, startDate, inHos);
            map.put("beforedawnList",beforedawn);
            vo = null;
            if(beforedawn.size()>0){
                vo = beforedawn.get(0);
            }
            map.put("beforedawn",vo);

            List<BloodSugarListVO> beforeBreakfast = findSugarWithOpName("beforeBreakfast", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos);
            map.put("beforeBreakfastList",beforeBreakfast);
            vo = null;
            if(beforeBreakfast.size()>0){
                vo = beforeBreakfast.get(0);
            }
            map.put("beforeBreakfast",vo);

            List<BloodSugarListVO> afterBreakfast = findSugarWithOpName("afterBreakfast", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos);
            map.put("afterBreakfastList",afterBreakfast);
            vo = null;
            if(afterBreakfast.size()>0){
                vo = afterBreakfast.get(0);
            }
            map.put("afterBreakfast",vo);

            List<BloodSugarListVO> beforeLunch = findSugarWithOpName("beforeLunch", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos);
            map.put("beforeLunchList",beforeLunch);
            vo = null;
            if(beforeLunch.size()>0){
                vo = beforeLunch.get(0);
            }
            map.put("beforeLunch",vo);

            List<BloodSugarListVO> afterLunch = findSugarWithOpName("afterLunch", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos);
            map.put("afterLunchList",afterLunch);
            vo = null;
            if(afterLunch.size()>0){
                vo = afterLunch.get(0);
            }
            map.put("afterLunch",vo);

            List<BloodSugarListVO> beforeDinner = findSugarWithOpName("beforeDinner", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos);
            map.put("beforeDinnerList",beforeDinner);
            vo = null;
            if(beforeDinner.size()>0){
                vo = beforeDinner.get(0);
            }
            map.put("beforeDinner",vo);

            List<BloodSugarListVO> afterDinner = findSugarWithOpName("afterDinner", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos);
            map.put("afterDinnerList",afterDinner);
            vo = null;
            if(afterDinner.size()>0){
                vo = afterDinner.get(0);
            }
            map.put("afterDinner",vo);

            List<BloodSugarListVO> beforeSleep = findSugarWithOpName("beforeSleep", listBloodSugarDTO.getMemberId(), startDate, startDate,inHos);
            map.put("beforeSleepList",beforeSleep);
            vo = null;
            if(beforeSleep.size()>0){
                vo = beforeSleep.get(0);
            }
            map.put("beforeSleep",vo);

            List<BloodSugarListVO> afterBreakfast1h = findSugarWithOpName("afterBreakfast1h", listBloodSugarDTO.getMemberId(), startDate, startDate,null);
            map.put("afterBreakfast1hList",afterBreakfast1h);
            vo = null;
            if(afterBreakfast1h.size()>0){
                vo = afterBreakfast1h.get(0);
            }
            map.put("afterBreakfast1h",vo);

            List<BloodSugarListVO> afterDinner1h = findSugarWithOpName("afterDinner1h", listBloodSugarDTO.getMemberId(), startDate, startDate,null);
            map.put("afterDinner1hList",afterDinner1h);
            vo = null;
            if(afterDinner1h.size()>0){
                vo = afterDinner1h.get(0);
            }
            map.put("afterDinner1h",vo);

            List<BloodSugarListVO> afterLunch1h = findSugarWithOpName("afterLunch1h", listBloodSugarDTO.getMemberId(), startDate, startDate,null);
            map.put("afterLunch1hList",afterLunch1h);
            vo = null;
            if(afterLunch1h.size()>0){
                vo = afterLunch1h.get(0);
            }
            map.put("afterLunch1h",vo);

            reList.add(map);
        }
//        PageResult<Map<String, Object>> pageResult = new PageResult<>(reList);
//        pageResult.setTotalRows(day);
//        if(day%page.getRows()==0){
//            pageResult.setTotalPages(day/page.getRows());
//        } else {
//            pageResult.setTotalPages(day/page.getRows()+1);
//        }
//        pageResult.setPageSize(page.getRows());
//        pageResult.setPageNum(page.getPage());
        PageResult<String> pageResult = new PageResult<>(dayList);
        PageResult emptyPageResult = pageResult.createEmptyPageResult();
        emptyPageResult.setRows(reList);
        return  emptyPageResult;
    }


    @Override
    public PageResult<BloodSugarPO> listRandomBloodSugarPageOfInHos(ListBloodSugarDTO listBloodSugarDTO, PageRequest page) {
        PageHelper.startPage(page.getPage(),page.getRows());
        List<BloodSugarPO> reList = findSugar("randomtime", listBloodSugarDTO.getMemberId(),
                listBloodSugarDTO.getStartDt(), listBloodSugarDTO.getEndDt()
                ,listBloodSugarDTO.getInHos(),null);
        return new PageResult<BloodSugarPO>(reList);
    }

    @Override
    public List<BloodSugarOfParamCodeBO> listMemberParamValuesOfStatistics(GetStatisticsDTO dto) {
        return this.bloodSugarMapper.listMemberParamValuesOfStatistics(dto);
    }

    @Override
    public Map<String, Object> loadBloodNumHigLow2(CountBloodSugarDTO countBloodSugarDTO) {
        doCountBloodSugarDTO(countBloodSugarDTO);
        return this.bloodSugarMapper.loadBloodNumHigLow2(countBloodSugarDTO);
    }

    @Override
    public List<BloodSugarPO> listParamLogByMemberIdOfInHos(String memberId) {
        return this.bloodSugarMapper.listParamLogByMemberIdOfInHos(memberId);
    }

    @Override
    public Map<String, Object> getAvgWeekBlood(String memberId) {
        Map<String, Object> resultMap = new HashMap<>();
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        listBloodSugarDTO.setMemberId(memberId);
        listBloodSugarDTO.setCodeDt("3"); //3  一周
        List<Map<String, Object>> mapList = this.listBloodSugarPage(listBloodSugarDTO, null);
        int kfCount = 0; //空腹血糖次数
        int chCount = 0; //餐后血糖次数
        double kfBloodSum = 0;  //空腹血糖
        double chBloodSum = 0;  //餐后血糖
        double kfBloodAvg = 0;  //空腹血糖平均值
        double chBloodAvg = 0;  //餐后血糖平均值
        if (null != mapList && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                //早餐前(空腹)
                if (null != map.get("beforeBreakfast") && !StringUtils.isBlank(map.get("beforeBreakfast").toString())) {
//                    String kf = map.get("beforeBreakfast").toString();
                    String kf =  JsonSerializer.objectToJson(map.get("beforeBreakfast"));
                    Map<String, Object> kfMap = JsonSerializer.jsonToMap(kf);
                    if (null != kfMap && kfMap.size() > 0) {
                        kfCount += 1;
                        if (null != kfMap.get("paramValue") && !StringUtils.isBlank(kfMap.get("paramValue").toString())) {
                            kfBloodSum += Double.parseDouble(kfMap.get("paramValue").toString());
                        }
                    }
                }
                //早餐后
                if (null != map.get("afterBreakfast") && !StringUtils.isBlank(map.get("afterBreakfast").toString())) {
//                        String afterBreakfast = map.get("afterBreakfast").toString();
                    String afterBreakfast =  JsonSerializer.objectToJson(map.get("afterBreakfast"));
                    Map<String, Object> afterBreakfastMap = JsonSerializer.jsonToMap(afterBreakfast);
                    if (null != afterBreakfastMap && afterBreakfastMap.size() > 0) {
                        chCount += 1;
                        if (null != afterBreakfastMap.get("paramValue") && !StringUtils.isBlank(afterBreakfastMap.get("paramValue").toString())) {
                            chBloodSum += Double.parseDouble(afterBreakfastMap.get("paramValue").toString());
                        }
                    }
                }
                //午餐后
                if (null != map.get("afterLunch") && !StringUtils.isBlank(map.get("afterLunch").toString())) {
//                            String afterLunch = map.get("afterLunch").toString();
                    String afterLunch =  JsonSerializer.objectToJson(map.get("afterLunch"));
                    Map<String, Object> afterLunchMap = JsonSerializer.jsonToMap(afterLunch);
                    if (null != afterLunchMap && afterLunchMap.size() > 0) {
                        chCount += 1;
                        if (null != afterLunchMap.get("paramValue") && !StringUtils.isBlank(afterLunchMap.get("paramValue").toString())) {
                            chBloodSum += Double.parseDouble(afterLunchMap.get("paramValue").toString());
                        }
                    }
                }
                //午餐后
                if (null != map.get("afterDinner") && !StringUtils.isBlank(map.get("afterDinner").toString())) {
//                            String afterDinner = map.get("afterDinner").toString();
                    String afterDinner =  JsonSerializer.objectToJson(map.get("afterDinner"));
                    Map<String, Object> afterDinnerMap = JsonSerializer.jsonToMap(afterDinner);
                    if (null != afterDinnerMap && afterDinnerMap.size() > 0) {
                        chCount += 1;
                        if (null != afterDinnerMap.get("paramValue") && !StringUtils.isBlank(afterDinnerMap.get("paramValue").toString())) {
                            chBloodSum += Double.parseDouble(afterDinnerMap.get("paramValue").toString());
                        }
                    }
                }
            }
        }
        if (kfCount != 0){
            kfBloodAvg  = kfBloodSum / kfCount;
        }
        if (chCount != 0){
            chBloodAvg = chBloodSum /chCount;
        }
        resultMap.put("kfAvg", String.format("%.1f",kfBloodAvg));
        resultMap.put("chAvg", String.format("%.1f",chBloodAvg));
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> countBloodSugarMaxMin(String memberId,String startDt,String endDt,String paramCode) {
        return fingCount(memberId,startDt,endDt,paramCode);
    }

    private List<Map<String, Object>> fingCount(String memberId,String startDt,String endDt,String paramCode){
        CountBloodSugarDTO dto = new CountBloodSugarDTO();
        dto.setMemberId(memberId);
        dto.setStartDt(startDt);
        dto.setEndDt(endDt);
        dto.setParamCode(paramCode);
        return this.bloodSugarMapper.countBloodSugarMaxMin(dto);
    }

    @Override
    public PageResult<MemberBloodSugarVO> listTodayBloodSugarOfMember(MemberBloodSugarDTO memberBloodSugarDTO, PageRequest page) {
        Integer inHos = memberBloodSugarDTO.getInHos();
        DoctorPO doctorPO = this.doctorMapper.getDoctorById(memberBloodSugarDTO.getDoctorId());
        List<MemberBloodSugarVO> memberBloodSugarVOList = new ArrayList<>();
        List<String> departmentIdList = null;
        List<String> departIdList = null;
        List<MemberBloodSugarVO> unique = null;
        if(StringUtils.isBlank(memberBloodSugarDTO.getStartDt())){
            String startDate = DateHelper.getToday() + DateHelper.DEFUALT_TIME_START;
            String endDate = DateHelper.getToday() + DateHelper.DEFUALT_TIME_END;
            memberBloodSugarDTO.setStartDt(startDate);
            memberBloodSugarDTO.setEndDt(endDate);
        }
        //存放医院全部虚拟病区的科室
        List<DoctorDepartVO> departVOList = new ArrayList<>();
        if(!StringUtils.isBlank(memberBloodSugarDTO.getDepartmentIdStr())){
            departmentIdList = new ArrayList<>(Arrays.asList(memberBloodSugarDTO.getDepartmentIdStr().split(",")));
        }else{
            ListMemberDTO listMemberDTO = new ListMemberDTO();
            listMemberDTO.setDoctorId(memberBloodSugarDTO.getDoctorId());
            listMemberDTO.setHospitalId(memberBloodSugarDTO.getHospitalId());
            List<DoctorDepartVO> departVOS = this.doctorService.listDoctorDepart(listMemberDTO);
            if(departVOS != null && !departVOS.isEmpty()){
                departmentIdList = departVOS.stream().map(DoctorDepartVO::getDepartId).collect(Collectors.toList());
            }
        }
        if (null != departmentIdList && departmentIdList.size() > 0){
            memberBloodSugarDTO.setDepartmentIdList(departmentIdList);
            if (memberBloodSugarDTO.getIsLoadVirtualWard() == 0 && memberBloodSugarDTO.getLoadAll() == 0){
                List<MemberBloodSugarPO> memberBloodSugarPOS = this.bloodSugarMapper.listTodayBloodSugarOfMember(memberBloodSugarDTO);
                //将院内患者Po转换为Model
                memberBloodSugarVOList = this.convertBSPoToWorkMemberBSugarModel(memberBloodSugarPOS,doctorPO.getDoctorName(),memberBloodSugarDTO.getDoctorId());
            }else if (memberBloodSugarDTO.getIsLoadVirtualWard() == 1 && memberBloodSugarDTO.getLoadAll() == 1){
                //加载全部的虚拟病区的患者(只加载虚拟病区)
                if (memberBloodSugarDTO.getVirtualWardAuthority() == 1 && memberBloodSugarDTO.getIsLoadVirtualWard() == 1) {
                    if (doctorPO.getIsVirtual() == 1){ //该医生有勾选虚拟病区模块
                        if (!StringUtils.isBlank(memberBloodSugarDTO.getHospitalId())) {
                            List<VirtualWardDepartmentPO> virtualWardDepartmentPOList = this.virtualWardService.listVirtualWardDepartment(memberBloodSugarDTO.getHospitalId());
                            for (VirtualWardDepartmentPO vo : virtualWardDepartmentPOList) {
                                DoctorDepartVO departVO = new DoctorDepartVO();
                                departVO.setDepartId(vo.getDepartmentId());
                                departVO.setDepartName(vo.getDepartmentName());
                                departVOList.add(departVO);
                            }
                        }
                        if (departVOList != null && !departVOList.isEmpty()) {
                            departIdList = departVOList.stream().map(DoctorDepartVO::getDepartId).collect(Collectors.toList());
                        }
                        memberBloodSugarDTO.setDepartmentIdList(departIdList);
                    }else{
                        memberBloodSugarDTO.setDepartmentIdList(departmentIdList);
                    }
                }
                List<VirtualWardPO> virtualWardPOS = this.virtualWardMapper.listTodayBloodSugarByVirtualWard(memberBloodSugarDTO);
                //将院内患者Po转换为Model
                memberBloodSugarVOList = this.convertVirtualWard(virtualWardPOS,doctorPO.getDoctorName(),memberBloodSugarDTO.getDoctorId());
            }else if (memberBloodSugarDTO.getLoadAll() == 2){
                //全部
                List<MemberBloodSugarPO> memberBloodSugarPOS = this.bloodSugarMapper.listTodayBloodSugarOfMember(memberBloodSugarDTO);
                //将院内患者Po转换为Model
                List<MemberBloodSugarVO> vosOList = this.convertBSPoToWorkMemberBSugarModel(memberBloodSugarPOS,doctorPO.getDoctorName(),memberBloodSugarDTO.getDoctorId());
                for (MemberBloodSugarVO vo : vosOList){
                    memberBloodSugarVOList.add(vo);
                }
                if (memberBloodSugarDTO.getVirtualWardAuthority() == 1 && memberBloodSugarDTO.getIsLoadVirtualWard() == 1){
                    //有虚拟病区模块权限+虚拟病区数据
                    if (!StringUtils.isBlank(memberBloodSugarDTO.getHospitalId())) {
                        if (doctorPO.getIsVirtual() == 1){ //该医生有勾选虚拟病区模块
                            if (!StringUtils.isBlank(memberBloodSugarDTO.getHospitalId())) {
                                List<VirtualWardDepartmentPO> virtualWardDepartmentPOList = this.virtualWardService.listVirtualWardDepartment(memberBloodSugarDTO.getHospitalId());
                                for (VirtualWardDepartmentPO vo : virtualWardDepartmentPOList) {
                                    DoctorDepartVO departVO = new DoctorDepartVO();
                                    departVO.setDepartId(vo.getDepartmentId());
                                    departVO.setDepartName(vo.getDepartmentName());
                                    departVOList.add(departVO);
                                }
                            }
                            if (departVOList != null && !departVOList.isEmpty()) {
                                departIdList = departVOList.stream().map(DoctorDepartVO::getDepartId).collect(Collectors.toList());
                            }
                            memberBloodSugarDTO.setDepartmentIdList(departIdList);
                        }else{
                            memberBloodSugarDTO.setDepartmentIdList(departmentIdList);
                        }
                    }
                }else if (memberBloodSugarDTO.getVirtualWardAuthority() == 1){
                    //有虚拟病区模块权限
                    memberBloodSugarDTO.setDepartmentIdList(departmentIdList);
                }
                List<VirtualWardPO> virtualWardPOS = this.virtualWardMapper.listTodayBloodSugarByVirtualWard(memberBloodSugarDTO);
                List<MemberBloodSugarVO> vos = this.convertVirtualWard(virtualWardPOS,doctorPO.getDoctorName(),memberBloodSugarDTO.getDoctorId());
                for (MemberBloodSugarVO sugarVO : vos){
                    memberBloodSugarVOList.add(sugarVO);
                }
            }
            unique = memberBloodSugarVOList.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(MemberBloodSugarVO::getMemberId))), ArrayList::new)
                );
            List<String> midList = new ArrayList<String>();
            for (MemberBloodSugarVO sugarVO : unique) {
                midList.add(sugarVO.getMemberId());
                //查询患者的每日血糖
                Map<String ,Object> map=new HashMap<>();

                for (List<String> paramCodes : getParamCodeLists()) {
                    //血糖
                    List<BloodSugarListVO> sugarWithOpName = findSugarWithOpName(paramCodes, sugarVO.getMemberId(),
                            memberBloodSugarDTO.getStartDt(), memberBloodSugarDTO.getEndDt(), inHos);
                    map.put(paramCodes.get(0) + "List",sugarWithOpName);
                    BloodSugarListVO po = null;
                    if(sugarWithOpName.size()>0){
                        po = sugarWithOpName.get(0);
                    }
                    map.put(paramCodes.get(0),po);
                }
                sugarVO.setMemberSugar(map);
            }

            if (midList != null && midList.size() > 0){
                //获取患者的监测计划
//                List<TodaySchemaModel> list1 = this.memberMonitorPlanService.listTodaySchemaOfMember(midList, MonitorPlanConstant.ILLNESS_TYPE_DIABETES);  //糖尿病监测计划
//                List<JSONObject> jsonObjects1 = JsonSerializer.convertListJsobObj(list1);
//                Map<String,Object> map1 = JsonSerializer.convertListToMap(jsonObjects1,"memberId","todayPlan");

                //补充model
                for (MemberBloodSugarVO bloodSugarVO : unique) {
                    List<MemberMonitorTaskCardPO> todaySchemaModels = this.memberMonitorTaskCardMapper.listTaskPoOfMember(bloodSugarVO.getMemberId(), memberBloodSugarDTO.getStartDt(),MonitorPlanConstant.ILLNESS_TYPE_DIABETES);  //糖尿病监测计划
                    List<String> list = new ArrayList<>();
                    for (MemberMonitorTaskCardPO taskCardPO : todaySchemaModels){
                        list.add(taskCardPO.getParamCode());
                    }
                    String todaySchema = Joiner.on(",").join(list);
                    bloodSugarVO.setTodaySchema(todaySchema);
                }
            }
        }
        if (unique != null){
            ListUtils.sort(unique,true,"departmentId","bedNo");
        }
        //分页
        PageResult<MemberBloodSugarVO>  bloodSugarVOPageResult = new PageResult<MemberBloodSugarVO>(unique);
        bloodSugarVOPageResult.setPageNum(page.getPage());
        bloodSugarVOPageResult.setPageSize(page.getRows());
        bloodSugarVOPageResult.setTotalPages(unique.size()%page.getRows() == 0 ? unique.size()/page.getRows():unique.size()/page.getRows()+1);
        bloodSugarVOPageResult.setTotalRows(unique.size());
        int startRow = (page.getPage()-1)*page.getRows();
        int endRow = page.getPage()*page.getRows();
        if (endRow>unique.size()){
            endRow = unique.size();
        }
        List<MemberBloodSugarVO> list = new ArrayList<>();
        for (int i = startRow;i<endRow;i++){
            list.add(unique.get(i));
        }
        bloodSugarVOPageResult.setRows(list);
        return bloodSugarVOPageResult;
    }

    @Override
    public PageResult<MemberBloodSugarVO> listAllBloodSugarOfMember(MemberBloodSugarDTO memberBloodSugarDTO, PageRequest page) {

        Integer inHos = memberBloodSugarDTO.getInHos();
        PageResult<MemberBloodSugarVO> memberBloodSugarVOPageResult = new PageResult<MemberBloodSugarVO>();
        PageHelper.startPage(page.getPage(),page.getRows());
        List<MemberBloodSugarPO> memberBloodSugarPOS = this.bloodSugarMapper.listTodayBloodSugarOfMemberByLog(memberBloodSugarDTO);
        PageResult<MemberBloodSugarPO> result = new PageResult<MemberBloodSugarPO>(memberBloodSugarPOS);
        List<MemberBloodSugarPO> poRows = result.getRows();
        //将院内患者Po转换为Model
        List<MemberBloodSugarVO> memberBloodSugarVO = this.convertMemberBSugarModel(poRows);
        List<String> midList = new ArrayList<String>();
        for (MemberBloodSugarVO sugarVO : memberBloodSugarVO) {
            midList.add(sugarVO.getMemberId());
            //查询患者的每日血糖
            Map<String ,Object> map=new HashMap<>();

            for (List<String> paramCodes : getParamCodeLists()) {
                //血糖
                List<BloodSugarPO> sugarPOList = findSugar(paramCodes, sugarVO.getMemberId(),
                        memberBloodSugarDTO.getStartDt(), memberBloodSugarDTO.getEndDt(),inHos,null);
                map.put(paramCodes.get(0) + "List",sugarPOList);
                BloodSugarPO po = null;
                if(sugarPOList.size()>0){
                    po = sugarPOList.get(0);
                }
                map.put(paramCodes.get(0),po);
            }
            sugarVO.setMemberSugar(map);
        }
        memberBloodSugarVOPageResult = new PageResult<MemberBloodSugarVO>(memberBloodSugarVO);
        memberBloodSugarVOPageResult.setPageNum(result.getPageNum());
        memberBloodSugarVOPageResult.setPageSize(result.getPageSize());
        memberBloodSugarVOPageResult.setTotalPages(result.getTotalPages());
        memberBloodSugarVOPageResult.setTotalRows(result.getTotalRows());
        return memberBloodSugarVOPageResult;
}


    @Override
    public DoctorMemberVo getDoctorDepartName(MemberBloodSugarDTO memberBloodSugarDTO) {
        DoctorMemberVo doctorMemberVo = new DoctorMemberVo();
        DoctorPO doctorPO = this.doctorMapper.getDoctorById(memberBloodSugarDTO.getDoctorId());
        doctorMemberVo.setDepartName(doctorPO.getDepartName());
        List<MemberBloodSugarPO> memberBloodSugarPOS = this.bloodSugarMapper.listTodayBloodSugarOfMemberByLog(memberBloodSugarDTO);
        doctorMemberVo.setCount(memberBloodSugarPOS.size());
        return doctorMemberVo;
    }

    @Override
    public Long countHighRiskParamLogOfMember(MemberParamValueDTO paramDTO) {
        long result = 0L;
        DoctorServiceSettingPO po = this.doctorServiceSettingMapper.getServiceSetting(paramDTO.getDoctorId());
        //如果没有条件筛选
        if (paramDTO.getHighValue() == null || "".equals(paramDTO.getHighValue()) && paramDTO.getLowValue() == null || "".equals(paramDTO.getLowValue())){
            if (po == null){
                paramDTO.setHighValue(SignConstant.SUGAR_HIGH);
                paramDTO.setLowValue(SignConstant.SUGAR_LOW);
            }else{
                paramDTO.setHighValue(po.getBloodSugarMax());
                paramDTO.setLowValue(po.getBloodSugarMin());
            }
        }
        Boolean loadVirtualWard = true;
        List<String> departmentIdList = getDepartIdsByDoctorId(paramDTO.getDoctorId());
        List<String> virtualWardDepartmentIdList = listDoctorManageVirtualDepart(paramDTO.getDoctorId());
        paramDTO.setLoadVirtualWard(loadVirtualWard);
        paramDTO.setVirtualWardDepartmentIdList(virtualWardDepartmentIdList);
        if (null != departmentIdList && departmentIdList.size() > 0){
            DoctorPopupRemindIgnoreTimePO ignoreTime = this.doctorPopupRemindIgnoreTimeService.getDoctorPopupRemindIgnoreTime(paramDTO.getDoctorId() , DoctorPopupRemindConstant.REMIND_TYPE_HIGH_BLOOD_SUGAR);
            if(ignoreTime != null){
                paramDTO.setMoreModifyDt(ignoreTime.getIgnoreDt());
            }
            paramDTO.setDepartmentIdList(departmentIdList);
            //异常血糖
            paramDTO.setBloodType(SignConstant.BLOOD_TYPE_ABNORMAL);
            paramDTO.setIsLook(0);
            paramDTO.setParamDt(DateHelper.getToday());
            List<MemberParamValueVO> valueVOS = this.bloodSugarMapper.listWorkParamValueOfMember(paramDTO);
            result = valueVOS.size();

        }
        return result;
    }

    @Override
    public PageResult<MemberSugarHospitalListVO> listMemberBloodHospital(ListMemberBloodSugarHospitalDTO dto, PageRequest page) {
        List<String> groupIds = null;
        if(StringUtils.isBlank(dto.getDepartmentId())){
            ListMemberDTO listMemberDTO = new ListMemberDTO();
            listMemberDTO.setDoctorId(dto.getDoctorId());
            listMemberDTO.setGroupId(dto.getDepartmentId());
            List<DoctorDepartVO> list = this.doctorService.listDoctorDepart(listMemberDTO);
            if(list!=null&&list.size()>0){
                groupIds = new ArrayList<>(list.size());
                for(DoctorDepartVO vo : list){
                    groupIds.add(vo.getDepartId());
                }
            }
            listMemberDTO.setGroupId(null);
        }
        dto.setDepartmentIdList(groupIds);
        PageHelper.startPage(page.getPage(),page.getRows());
        List<MemberSugarDayRecordPO> list = bloodSugarMapper.listAllBloodMemberHospital(dto);
        PageResult<MemberSugarDayRecordPO> pageResult = new PageResult(list);
        List<MemberSugarHospitalListVO> result = new ArrayList<>(list.size());
        for (MemberSugarDayRecordPO po : list) {
            MemberSugarHospitalListVO vo = new MemberSugarHospitalListVO();
            BeanUtils.copyProperties(vo,po);
            //随机
            String random = po.getRandomtimeJson();
            List<Map> randomList = JSON.parseArray(random, Map.class);
            if (null != randomList && randomList.size()>0){
                vo.setRandomtime(randomList.get(0));
            }
            vo.setRandomtimeList(randomList);
            //凌晨
            String beforedawn =po.getBeforedawnJson();
            List<Map> beforedawnList = JSON.parseArray(beforedawn, Map.class);
            if (null != beforedawnList && beforedawnList.size()>0){
                vo.setBeforedawn(beforedawnList.get(0));
            }
            vo.setBeforedawnList(beforedawnList);
            //空腹
            String beforebreakfast =po.getBeforebreakfastJson();
            List<Map> beforebreakfastList = JSON.parseArray(beforebreakfast, Map.class);
            if (null != beforebreakfastList && beforebreakfastList.size()>0){
                vo.setBeforeBreakfast(beforebreakfastList.get(0));
            }
            vo.setBeforeBreakfastList(beforebreakfastList);
            //早餐后
            String afterbreakfast =po.getAfterbreakfastJson();
            List<Map> afterbreakfastList = JSON.parseArray(afterbreakfast, Map.class);
            if (null != afterbreakfastList && afterbreakfastList.size()>0){
                vo.setAfterBreakfast(afterbreakfastList.get(0));
            }
            vo.setAfterBreakfastList(afterbreakfastList);
            //午餐前
            String beforelunch =po.getBeforelunchJson();
            List<Map> beforelunchList = JSON.parseArray(beforelunch, Map.class);
            if (null != beforelunchList && beforelunchList.size()>0){
                vo.setBeforeLunch(beforelunchList.get(0));
            }
            vo.setBeforeLunchList(beforelunchList);
            //午餐后
            String afterlunch =po.getAfterlunchJson();
            List<Map> afterlunchList = JSON.parseArray(afterlunch, Map.class);
            if (null != afterlunchList && afterlunchList.size()>0){
                vo.setAfterLunch(afterlunchList.get(0));
            }
            vo.setAfterLunchList(afterlunchList);
            //晚餐前
            String beforedinner =po.getBeforedinnerJson();
            List<Map> beforedinnerList = JSON.parseArray(beforedinner, Map.class);
            if (null != beforedinnerList && beforedinnerList.size()>0){
                vo.setBeforeDinner(beforedinnerList.get(0));
            }
            vo.setBeforeDinnerList(beforedinnerList);
            //晚餐后
            String afterdinner =po.getAfterdinnerJson();
            List<Map> afterdinnerList = JSON.parseArray(afterdinner, Map.class);
            if (null != afterdinnerList && afterdinnerList.size()>0){
                vo.setAfterDinner(afterdinnerList.get(0));
            }
            vo.setAfterDinnerList(afterdinnerList);
            //睡觉前
            String beforesleep =po.getBeforesleepJson();
            List<Map> beforesleepList = JSON.parseArray(beforesleep, Map.class);
            if (null != beforesleepList && beforesleepList.size()>0){
                vo.setBeforeSleep(beforesleepList.get(0));
            }
            vo.setBeforeSleepList(beforesleepList);
            vo.setMemberStatus(iconHandler(vo.getMemberId(),dto.getDoctorId()));
            result.add(vo);
        }
        PageResult<MemberSugarHospitalListVO> pager = pageResult.createEmptyPageResult();
        pager.setRows(result);
        return pager;
    }

    @Override
    public PageResult<DySugarDataListVO> listMemberDySugarData(ListMemberDTO dto, PageRequest page) {
        inHospitalMemberService.listMemberDTOAuthHandler(dto);
        PageHelper.startPage(page.getPage(), page.getRows());
        List<MemberListPO> list = this.memberMapper.listHospitalBedWithSensor(dto);
        PageResult<MemberListPO> memberListPOPageResult = new PageResult<>(list);
        List<DySugarDataListVO> result = new ArrayList(list.size());
        for (MemberListPO row : list) {
            DySugarDataListVO dySugarDataListVO = new DySugarDataListVO();
            DyMemberSensorDTO sensorDTO = new DyMemberSensorDTO();
            sensorDTO.setMemberId(row.getMemberId());
            sensorDTO.setValid(1);
            List<DYMemberSensorPO> sensorList = this.dyMemberSensorPOMapper.listMemberSensor(sensorDTO);
            DYMemberSensorPO dYMemberSensorPO = null;
            if (sensorList != null && sensorList.size()>0){
                dYMemberSensorPO = sensorList.get(0);
                Date startDate = new Date(Long.parseLong(dYMemberSensorPO.getMonitoringTime()));
                String startDt = DateHelper.getDate(startDate,DateHelper.DAY_FORMAT);
                String endDt = DateHelper.plusDate(startDt,14);
                DynamicBloodSugarIndexBaseVO baseVO = this.dySugarDataHandler(startDt,endDt,dYMemberSensorPO.getSensorNo());
                BeanUtils.copyProperties(dySugarDataListVO,baseVO);
                Boolean after = DateHelper.dateAfter(endDt, DateHelper.DAY_FORMAT, DateHelper.getToday(), DateHelper.DAY_FORMAT);
                endDt = after?"至今":endDt;
                dySugarDataListVO.setMonitorDt(startDt+ "-"+endDt);
                dySugarDataListVO.setSensorValid(after?1:0);
                dySugarDataListVO.setSensorNo(dYMemberSensorPO.getSensorNo());
            }
            BeanUtils.copyProperties(dySugarDataListVO,row);
            dySugarDataListVO.setDoctorId(row.getDoctorZgCode());
            dySugarDataListVO.setDoctorName(row.getDoctorZg());
            result.add(dySugarDataListVO);
        }

        PageResult<DySugarDataListVO> resultPage = memberListPOPageResult.createEmptyPageResult();
        resultPage.setRows(result);
        return resultPage;
    }


    private DynamicBloodSugarIndexBaseVO dySugarDataHandler(String startDt,String endDt,String sensorNo){
        List<DYYPBloodSugarPO> paramLogOfYPPOS = this.dyBloodSugarService
                .listDataWechatSourceOfYPParamLogOfOBDTASC(startDt ,endDt ,sensorNo);
        DynamicBloodSugarIndexBaseVO base = new DynamicBloodSugarIndexBaseVO();
        if(paramLogOfYPPOS != null){
            Date zeroAmStartDate = DateHelper.getDate(startDt + " 00:00:00" , DateHelper.DATETIME_FORMAT);
            Date zeroAmEndDate = DateHelper.getDate(endDt + " 00:00:00" , DateHelper.DATETIME_FORMAT);
            List<DYYPBloodSugarPO> zeroAmToZeroAmDataList = paramLogOfYPPOS.stream()
                    .filter(x -> x.getRecordTime().after(zeroAmStartDate) && x.getRecordTime().before(zeroAmEndDate))
                    .collect(Collectors.toList());
            DyRecordSettingPO settingRecordPO = this.dyMemberSettingService.getSettingValues(sensorNo);
            DynamicBloodSugarSettingsVO settingsVO = new DynamicBloodSugarSettingsVO();
            BeanUtils.copyProperties(settingsVO ,settingRecordPO);
            base = dynamicBloodSugarStatService.getDynamicBloodSugarIndexBase(zeroAmToZeroAmDataList ,settingsVO);
        }
        return base;
    }

    @Override
    public PageResult<DySugarDataListVO> listMemberDySugarDataOut(ListMemberDTO listMemberDTO, PageRequest page) {
        inHospitalMemberService.listMemberDTOAuthHandler(listMemberDTO);
        PageHelper.startPage(page.getPage(), page.getRows());
        List<MemberListPO> list = this.memberMapper.listHospitalOutWithSensor(listMemberDTO);
        PageResult<MemberListPO> pageResult = new PageResult<>(list);
        List<DySugarDataListVO> result = new ArrayList(list.size());
        for (MemberListPO row : list) {
            DySugarDataListVO dySugarDataListVO = new DySugarDataListVO();
            DyMemberSensorDTO sensorDTO = new DyMemberSensorDTO();
            sensorDTO.setMemberId(row.getMemberId());
            sensorDTO.setValid(1);
            List<DYMemberSensorPO> sensorList = this.dyMemberSensorPOMapper.listMemberSensor(sensorDTO);
            DYMemberSensorPO dYMemberSensorPO = null;
            if (sensorList != null && sensorList.size()>0){
                dYMemberSensorPO = sensorList.get(0);
                Date startDate = new Date(Long.parseLong(dYMemberSensorPO.getMonitoringTime()));
                String startDt = DateHelper.getDate(startDate,DateHelper.DAY_FORMAT);
                String endDt = DateHelper.plusDate(startDt,14);
                DynamicBloodSugarIndexBaseVO baseVO = this.dySugarDataHandler(startDt,endDt,dYMemberSensorPO.getSensorNo());
                BeanUtils.copyProperties(dySugarDataListVO,baseVO);
                Boolean after = DateHelper.dateAfter(endDt, DateHelper.DAY_FORMAT, DateHelper.getToday(), DateHelper.DAY_FORMAT);
                endDt = after?"至今":endDt;
                dySugarDataListVO.setMonitorDt(startDt+ "-"+endDt);
                dySugarDataListVO.setSensorValid(after?1:0);
                dySugarDataListVO.setSensorNo(dYMemberSensorPO.getSensorNo());
            }
            BeanUtils.copyProperties(dySugarDataListVO,row);
            dySugarDataListVO.setDoctorName(row.getDoctorZg());
            result.add(dySugarDataListVO);
        }
        PageResult<DySugarDataListVO> resultPage = pageResult.createEmptyPageResult();
        resultPage.setRows(result);
        return resultPage;
    }

    @Override
    public PageResult<ListMemberBloodStaticsVO> listMemberStatics(ListMemberBloodStaticsDTO listMemberBloodStaticsDTO , PageRequest page) {
        ListMemberDTO listMemberDTO = new ListMemberDTO();
        listMemberDTO.setDoctorId(listMemberBloodStaticsDTO.getDoctorId());
        listMemberDTO.setHospitalId(listMemberBloodStaticsDTO.getHospitalId());
        listMemberDTO.setGroupId(listMemberBloodStaticsDTO.getDepartmentId());
        List<ListMemberBloodStaticsVO> resultList = new ArrayList<>();
        inHospitalMemberService.listMemberDTOAuthHandler(listMemberDTO);
        List<MemberListPO> list= null;//存放患者列表
        PageHelper.startPage(page.getPage(), page.getRows());
         if (listMemberBloodStaticsDTO.getType().equals(4)){
            //科室统计
             List<DoctorDepartVO> departList = this.doctorService.listDoctorDepart(listMemberDTO);
             for (DoctorDepartVO vo : departList) {
                 listMemberDTO.setGroupId(vo.getDepartId());
                 ListMemberBloodStaticsVO staticsVO = bloodSugarMapper.getMemberBloodStaticsList(null,vo.getDepartId());
                 staticsVO.setMemberName(null);
                 staticsVO.setDepartmentName(vo.getDepartName());
                 //所有住过院的人（住院+出院）
                 staticsVO.setMemberNum(String.valueOf(this.memberMapper.listHospitalAllLogMember(listMemberDTO).size()));
                 if (!StringUtils.isBlank(staticsVO.getMonitorNum()) && !"0".equals(staticsVO.getMonitorNum())){
                     NumberFormat numberFormat = NumberFormat.getInstance();
                     numberFormat.setMaximumFractionDigits(2);
                     String rate = numberFormat.format(Float.parseFloat(staticsVO.getNormalSugarNum())/Float.parseFloat(staticsVO.getMonitorNum())*100);
                     staticsVO.setRate(rate+"%");
                 }
                 resultList.add(staticsVO);
             }
             PageResult<DoctorDepartVO> departmentPOPageResult = new PageResult<>(departList);
             PageResult<ListMemberBloodStaticsVO> emptyPageResult = departmentPOPageResult.createEmptyPageResult();
             emptyPageResult.setRows(resultList);
             return emptyPageResult;
        }

        if (listMemberBloodStaticsDTO.getType().equals(1)){
            //住院患者
            listMemberDTO.setCheckinStatus("1");
            list = this.memberMapper.listHospitalBedWithMember(listMemberDTO);

        }else if (listMemberBloodStaticsDTO.getType().equals(2)){
            //出院患者
            list = this.memberMapper.listHospitalOutMember(listMemberDTO);

        }else if (listMemberBloodStaticsDTO.getType().equals(3)){
            //所有患者(住院+出院)
           list = this.memberMapper.listHospitalAllLogMember(listMemberDTO);
        }
        if (list != null && list.size()>0){
            for (MemberListPO memberListPO : list) {
                ListMemberBloodStaticsVO staticsVO = bloodSugarMapper.getMemberBloodStaticsList(memberListPO.getMemberId(),null);
                staticsVO.setDoctorName(memberListPO.getDoctorZg());
                staticsVO.setMemberName(memberListPO.getMemberName());
                staticsVO.setDepartmentName(memberListPO.getDepartmentName());
                staticsVO.setMemberId(memberListPO.getMemberId());
                if (!StringUtils.isBlank(staticsVO.getMonitorNum()) && !"0".equals(staticsVO.getMonitorNum())){
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    numberFormat.setMaximumFractionDigits(2);
                    String rate = numberFormat.format(Float.parseFloat(staticsVO.getNormalSugarNum())/Float.parseFloat(staticsVO.getMonitorNum())*100);
                    staticsVO.setRate(rate+"%");
                }
                resultList.add(staticsVO);
            }
        }
        PageResult<MemberListPO> pageResult = new PageResult<>(list);
        PageResult<ListMemberBloodStaticsVO> emptyPageResult = pageResult.createEmptyPageResult();
        emptyPageResult.setRows(resultList);
        return emptyPageResult;

    }


    @Override
    public Map<String, Object> bloodSugarWarnList(SugarMonitorStaticsDTO dto, PageRequest page) {
        List<ListBloodSugarWarnVO> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        List<String> departmentIdList = null;
        if(StringUtils.isBlank(dto.getDepartmentId())){
            ListMemberDTO listMemberDTO = new ListMemberDTO();
            listMemberDTO.setDoctorId(dto.getDoctorId());
            listMemberDTO.setHospitalId(dto.getHospitalId());
            List<DoctorDepartVO> departVOSlist = this.doctorService.listDoctorDepart(listMemberDTO);
            if(list!=null&&list.size()>0){
                departmentIdList = new ArrayList<>(list.size());
                for(DoctorDepartVO vo : departVOSlist){
                    departmentIdList.add(vo.getDepartId());
                }
            }
            dto.setDepartmentId(null);
        }
        dto.setDepartmentIdList(departmentIdList);
        map.put("num",bloodSugarMapper.getBloodSugarWarnNum(dto));
        if (null == dto.getCodeList() || dto.getCodeList().size() == 0){
            map.put("list",new PageResult<>(list));
            return map;
        }
        PageHelper.startPage(page.getPage(),page.getRows());
        list = bloodSugarMapper.getBloodSugarWarnVOList(dto);
        for (ListBloodSugarWarnVO listBloodSugarWarnVO : list) {
            //图标处理
            listBloodSugarWarnVO.setMemberStatus(iconHandler(listBloodSugarWarnVO.getMemberId(),dto.getDoctorId()));
        }
        PageResult<ListBloodSugarWarnVO> pageResult = new PageResult<>(list);
        map.put("list",pageResult);
        return map;
    }

    @Override
    public boolean handleBloodSugarBySid(String sid, String status) {
        return bloodSugarMapper.handleBloodSugarBySid(sid,status) > 0;
    }

    @Override
    public PageResult<MemberBloodSugarVO> listBloodSugarTask(MemberBloodSugarDTO dto, PageRequest page) {
        String startDt = DateHelper.getToday();
        String endDt = DateHelper.getToday();
        if (!StringUtils.isBlank(dto.getStartDt())){
            startDt = dto.getStartDt();
            endDt= dto.getEndDt();
        }
        List<String> departmentIdList = null;
        if(StringUtils.isBlank(dto.getDepartmentId())){
            ListMemberDTO listMemberDTO = new ListMemberDTO();
            listMemberDTO.setDoctorId(dto.getDoctorId());
            listMemberDTO.setHospitalId(dto.getHospitalId());
            List<DoctorDepartVO> list = this.doctorService.listDoctorDepart(listMemberDTO);
            if(list!=null&&list.size()>0){
                departmentIdList = new ArrayList<>(list.size());
                for(DoctorDepartVO vo : list){
                    departmentIdList.add(vo.getDepartId());
                }
            }
            dto.setDepartmentId(null);
        }
        dto.setDepartmentIdList(departmentIdList);
        dto.setMonitorDt(startDt);
        PageHelper.startPage(page.getPage(),page.getRows());
        //有监测任务的患者列表
        List<MemberBloodSugarVO> memberList = this.memberMonitorTaskCardMapper.listTaskPoMemberList(dto);
        for (MemberBloodSugarVO vo : memberList) {
            //具体监测任务
            List<MemberMonitorTaskCardPO> todaySchemaModels = this.memberMonitorTaskCardMapper.listTaskByPlanId(vo.getPlanId(),startDt);  //糖尿病监测计划
            if (todaySchemaModels.size() > 0){
                List<String> list = new ArrayList<>();
                for (MemberMonitorTaskCardPO taskCardPO : todaySchemaModels){
                    list.add(taskCardPO.getParamCode());
                }
                String todaySchema = Joiner.on(",").join(list);
                vo.setTodaySchema(todaySchema);
            }
            dto.setStartDt(startDt);
            dto.setEndDt(endDt);
            Map<String ,Object> map=new HashMap<>();
            for (List<String> paramCodes : getParamCodeLists()) {
                //血糖信息
                List<BloodSugarListVO> sugarWithOpName = findSugarWithOpName(paramCodes, vo.getMemberId(),
                        dto.getStartDt(), dto.getEndDt(), 1);
                map.put(paramCodes.get(0) + "List",sugarWithOpName);
                BloodSugarListVO po = null;
                if(sugarWithOpName.size()>0){
                    po = sugarWithOpName.get(0);
                }
                map.put(paramCodes.get(0),po);
            }
            vo.setMemberSugar(map);
        }
        return new PageResult<>(memberList);
    }

    @Override
    public MemberSugarDayRecordPO getSugarDayPOByMemberIdAndDt(String memberId, String recordDt) {
        MemberSugarDayRecordPO sugarDayPO = bloodSugarMapper.getSugarDayPOByMemberIdAndDt(memberId, recordDt);
        return sugarDayPO;
    }


    private String iconHandler(String memberId,String doctorId){
        String memberStatus = "";//1我的2出院
        //是否出院
        List<MemberInHospitalLogPO> logs = memberInHospitalLogMapper.getMemberInHospitalLogByMemberId(memberId, null, null);
        if (logs != null && logs.size()>0){
            MemberInHospitalLogPO logPO = logs.get(0);
            if (!StringUtils.isBlank(logPO.getDoctorZgCode()) && logPO.getDoctorZgCode().equals(doctorId)){
                memberStatus = "1";//我的患者(责任医生是我)
            }
//            if (!StringUtils.isBlank(logs.get(0).getOutHospitalDate())){
//                memberStatus = "2";//出院
//            }

        }
        return memberStatus;
    }



    @Override
    public TodayBloodNumVO getTodayMemberBloodNum(TodayMemberBloodNumDTO numDto) {
        Integer inHos = numDto.getInHos();
        TodayBloodNumVO bloodNumVO = new TodayBloodNumVO();
        String hospitalId = numDto.getHospitalId();
        String doctorId = numDto.getDoctorId();
        //加载医生的科室权限
        List<String> departmentIdList = new ArrayList<String>();
        if (!StringUtils.isBlank(hospitalId)){
            ListMemberDTO listMemberDTO = new ListMemberDTO();
            listMemberDTO.setHospitalId(hospitalId);
            listMemberDTO.setDoctorId(doctorId);
            List<DoctorDepartVO> doctorDepartVOS = this.doctorService.listDoctorDepart(listMemberDTO);
            for (DoctorDepartVO doctorDepartVO : doctorDepartVOS){
                departmentIdList.add(doctorDepartVO.getDepartId());
            }
        }
        if (departmentIdList != null && departmentIdList.size() > 0){

            //今日时间初始化
            String today = DateHelper.getToday();
            String startDt = today+ DateHelper.DEFUALT_TIME_START;
            String endDt = today+ DateHelper.DEFUALT_TIME_END;
            //获取今日已经监测血糖次数
            PageRequest pr = new PageRequest();
            TodayHosMemberBloodDTO todayHosMemberBloodDTO = new TodayHosMemberBloodDTO();
            todayHosMemberBloodDTO.setDoctorId(doctorId);
            todayHosMemberBloodDTO.setHospitalId(hospitalId);
            todayHosMemberBloodDTO.setStartDt(startDt);
            todayHosMemberBloodDTO.setEndDt(endDt);
            todayHosMemberBloodDTO.setIsLoadVirtualWard(0);
            Long todayMemberBloodNum = listMemberBloodByWhere(todayHosMemberBloodDTO ,pr).getTotalRows();

            //获取今日异常血糖人数(高危预警)
            MemberParamValueDTO memberParamValueDTO = new MemberParamValueDTO();
            memberParamValueDTO.setDoctorId(doctorId);
            memberParamValueDTO.setHospitalId(hospitalId);
            memberParamValueDTO.setBloodType(0);
            memberParamValueDTO.setParamDt(today);
            memberParamValueDTO.setIsLoadVirtualWard(0);
            memberParamValueDTO.setDealIsLook(false);
            Long highRiskNumber = listHighRiskParamLogOfMember(memberParamValueDTO ,pr).getTotalRows();

            //今日已经监测人数
            //虚拟病区的
            List<String> virtualWardDepartIdList = listDoctorManageVirtualDepart(doctorId);
            ListVirtualWardMemberByMonitorParam listVirtualWardMemberByMonitorParam = new ListVirtualWardMemberByMonitorParam();
            listVirtualWardMemberByMonitorParam.setHospitalId(hospitalId);
            listVirtualWardMemberByMonitorParam.setDepartmentIdList(virtualWardDepartIdList);
            listVirtualWardMemberByMonitorParam.setMonitor(1);
            List<String> virtualWardMemberList = this.virtualWardService.listVirtualWardMemberByMonitor(listVirtualWardMemberByMonitorParam);
            Set<String> memberList = new HashSet<>(virtualWardMemberList);
            //住院的
            ListMemberDTO listMemberDTO = new ListMemberDTO();
            listMemberDTO.setHospitalId(hospitalId);
            listMemberDTO.setGroupIdList(departmentIdList);
            listMemberDTO.setDoctorId(doctorId);
            List<MemberListPO> memberListPOS = null;
            listMemberDTO.setMonitor(1);
            memberListPOS = this.memberService.listInHospitalMember(listMemberDTO);
            for(MemberListPO m : memberListPOS){
                memberList.add(m.getMemberId());
            }
            Integer todayTrueMonitorNumber = memberList.size();
            //今日未监测人数
            //虚拟病区的
            listVirtualWardMemberByMonitorParam.setMonitor(0);
            virtualWardMemberList = this.virtualWardService.listVirtualWardMemberByMonitor(listVirtualWardMemberByMonitorParam);
            memberList = new HashSet<>(virtualWardMemberList);
            //住院的
            listMemberDTO.setMonitor(0);
            memberListPOS = this.memberService.listInHospitalMember(listMemberDTO);
            for(MemberListPO m : memberListPOS){
                memberList.add(m.getMemberId());
            }
            Integer todayfalseMonitorNumber = memberList.size();

            bloodNumVO.setTodayTrueMonitorNumber(todayTrueMonitorNumber);
            bloodNumVO.setTodayfalseMonitorNumber(todayfalseMonitorNumber);
            bloodNumVO.setAllBloodSugarNumber(todayMemberBloodNum);
            bloodNumVO.setHighRiskNumber(highRiskNumber);
        }
        return bloodNumVO;
    }

    @Override
    public List<MemberBloodSugarVO> convertBSPoToWorkMemberBSugarModel(List<MemberBloodSugarPO> po,String doctorName,String doctorId) {
        List<MemberBloodSugarVO> todayMemberBSugarModels = new ArrayList<MemberBloodSugarVO>(po.size());
        for(MemberBloodSugarPO memberBloodSugarPO : po){
            MemberBloodSugarVO model = new MemberBloodSugarVO();
            model.setBedNo(memberBloodSugarPO.getBedNo());
            model.setConcernStatus(memberBloodSugarPO.getConcernStatus());
            DepartmentPO departmentPO =  this.departmentMapper.listDepartmentByDepartId(memberBloodSugarPO.getDepartmentId());
            model.setDepartmentName(departmentPO.getDepartmentName());
            model.setMemberId(memberBloodSugarPO.getMemberId());
            model.setMemberName(memberBloodSugarPO.getMemberName());
            model.setDepartmentId(memberBloodSugarPO.getDepartmentId());
            model.setMemberName(memberBloodSugarPO.getMemberName());
            model.setDiabetesType(memberBloodSugarPO.getDiabetesType());
            model.setHospitalNo(memberBloodSugarPO.getHospitalNo());
            model.setDoctorName(doctorName);
            List<MemberDoctorListPO> memberDoctorListPOS = doctorService.listMemberDoctor(memberBloodSugarPO.getMemberId());
            if (memberDoctorListPOS != null && memberDoctorListPOS.size()>0){
                model.setMemberStatus(memberDoctorListPOS.get(0).getDoctorId().equals(doctorId)?"1":"0");
            }
            todayMemberBSugarModels.add(model);
        }
        return todayMemberBSugarModels;
    }

    public List<MemberBloodSugarVO> convertVirtualWard(List<VirtualWardPO> po,String doctorName,String doctorId) {
        List<MemberBloodSugarVO> todayMemberBSugarModels = new ArrayList<MemberBloodSugarVO>(po.size());
        for(VirtualWardPO virtualWardPO : po){
            MemberBloodSugarVO model = new MemberBloodSugarVO();
            model.setBedNo(virtualWardPO.getBedNo());
            DepartmentPO departmentPO =  this.departmentMapper.listDepartmentByDepartId(virtualWardPO.getDepartmentId());
            model.setDepartmentName(departmentPO.getDepartmentName());
            model.setMemberId(virtualWardPO.getMemberId());
            MemberPO memberPO = this.memberMapper.getMemberByMemberId(virtualWardPO.getMemberId());
            model.setMemberName(memberPO.getMemberName());
            model.setDepartmentId(virtualWardPO.getDepartmentId());
            model.setDoctorName(doctorName);
            model.setHospitalNo(virtualWardPO.getHospitalNo());
            List<MemberDoctorListPO> memberDoctorListPOS = doctorService.listMemberDoctor(virtualWardPO.getMemberId());
            if (memberDoctorListPOS != null && memberDoctorListPOS.size()>0){
                model.setMemberStatus(memberDoctorListPOS.get(0).getDoctorId().equals(doctorId)?"1":"0");
            }
            todayMemberBSugarModels.add(model);
        }
        return todayMemberBSugarModels;
    }

    public List<MemberBloodSugarVO> convertMemberBSugarModel(List<MemberBloodSugarPO> po) {
        List<MemberBloodSugarVO> todayMemberBSugarModels = new ArrayList<MemberBloodSugarVO>(po.size());
        for(MemberBloodSugarPO memberBloodSugarPO : po){
            MemberBloodSugarVO model = new MemberBloodSugarVO();
            model.setBedNo(memberBloodSugarPO.getBedNo());
            model.setConcernStatus(memberBloodSugarPO.getConcernStatus());
            model.setMemberId(memberBloodSugarPO.getMemberId());
            model.setMemberName(memberBloodSugarPO.getMemberName());
            model.setDepartmentId(memberBloodSugarPO.getDepartmentId());
            model.setMemberName(memberBloodSugarPO.getMemberName());
            model.setDiabetesType(memberBloodSugarPO.getDiabetesType());
            model.setDepartmentName(memberBloodSugarPO.getDepartmentName());
            todayMemberBSugarModels.add(model);
        }
        return todayMemberBSugarModels;
    }

    @Override
    public PageResult<TodayHosMemberBloodVO> listMemberBloodByWhere(TodayHosMemberBloodDTO dto, PageRequest page) {
        List<String> departmentIdList = null;
        List<String> departIdVirtualList = listDoctorManageVirtualDepart(dto.getDoctorId());
        Boolean loadVirtualWard = true;
        if (!StringUtils.isBlank(dto.getDepartmentIdStr())){
            departmentIdList = new ArrayList<>(Arrays.asList(dto.getDepartmentIdStr().split(",")));
            loadVirtualWard = false;
        }else{
            if(dto.getIsLoadVirtualWard() != null && 1 == dto.getIsLoadVirtualWard()){
                departmentIdList  = new ArrayList<>();
                departmentIdList.add(Constant.DEFAULT_FOREIGN_ID);
            }else{
                departmentIdList = getDepartIdsByDoctorId(dto.getDoctorId());
            }

        }
        dto.setLoadVirtualWard(loadVirtualWard);
        dto.setDepartmentIdList(departmentIdList);
        dto.setVirtualWardDepartIdList(departIdVirtualList);
        PageHelper.startPage(page.getPage(),page.getRows());
        List<TodayHosMemberBloodVO> vos = this.bloodSugarMapper.listMemberBloodByWhere(dto);
        return new PageResult<>(vos);
    }

    @Override
    public PageResult<MemberParamValueVO> listHighRiskParamLogOfMember(MemberParamValueDTO paramDTO, PageRequest page) {
        //设置筛选条件
        if(!StringUtils.isBlank(paramDTO.getKeyValueJson())){
            JSONObject jsonObject = JSONObject.parseObject(paramDTO.getKeyValueJson());
            Double highRate = jsonObject.getDouble("highRate");
            paramDTO.setHighRate(highRate);
            Boolean treeTimesHigh = jsonObject.getBoolean("treeTimesHigh");
            if(treeTimesHigh!=null){
                paramDTO.setTreeTimesHigh(treeTimesHigh==true ?1:0);
            }
            Double highValue = jsonObject.getDouble("highValue");
            paramDTO.setHighValue(highValue);
            Double lowValue = jsonObject.getDouble("lowValue");
            paramDTO.setLowValue(lowValue);
            Double standardValue = jsonObject.getDouble("standardValue");
            paramDTO.setStandardValue(standardValue);
        }
        DoctorServiceSettingPO po = this.doctorServiceSettingMapper.getServiceSetting(paramDTO.getDoctorId());
        //如果没有条件筛选
        if (paramDTO.getHighValue() == null || "".equals(paramDTO.getHighValue()) && paramDTO.getLowValue() == null || "".equals(paramDTO.getLowValue())){
            if (po == null){
                paramDTO.setHighValue(SignConstant.SUGAR_HIGH);
                paramDTO.setLowValue(SignConstant.SUGAR_LOW);
            }else{
                paramDTO.setHighValue(po.getBloodSugarMax());
                paramDTO.setLowValue(po.getBloodSugarMin());
            }
        }
        Boolean loadVirtualWard = true;
        PageResult<MemberParamValueVO> pageResult = new PageResult<>();
        List<String> departmentIdList = null;
        List<String> virtualWardDepartmentIdList = listDoctorManageVirtualDepart(paramDTO.getDoctorId());
        if (!StringUtils.isBlank(paramDTO.getDepartmentIdStr())){
            departmentIdList = new ArrayList<>(Arrays.asList(paramDTO.getDepartmentIdStr().split(",")));
            loadVirtualWard =  false;
        }else{
            if(paramDTO.getIsLoadVirtualWard() != null && 1 == paramDTO.getIsLoadVirtualWard()){
                departmentIdList = new ArrayList<>();
                departmentIdList.add(Constant.DEFAULT_FOREIGN_ID);
            }else{
                departmentIdList = getDepartIdsByDoctorId(paramDTO.getDoctorId());
            }
        }
        paramDTO.setLoadVirtualWard(loadVirtualWard);
        paramDTO.setVirtualWardDepartmentIdList(virtualWardDepartmentIdList);
        if (null != departmentIdList && departmentIdList.size() > 0){
            paramDTO.setDepartmentIdList(departmentIdList);
            if(paramDTO.getBloodType()==null){
                //异常血糖
                paramDTO.setBloodType(SignConstant.BLOOD_TYPE_ABNORMAL);
            }
            PageHelper.startPage(page.getPage(),page.getRows());
            List<MemberParamValueVO> valueVOS = this.bloodSugarMapper.listWorkParamValueOfMember(paramDTO);
            pageResult = new PageResult<>(valueVOS);
            List<MemberParamValueVO> rows = pageResult.getRows();
            List<String> midList = new ArrayList<String>();
            for (MemberParamValueVO row : rows) {
                midList.add(row.getMemberId());
            }

            if (midList.size() > 0){

                for(MemberParamValueVO row : rows){
                    //查询患者当天的全部血糖
                    List<BloodSugarPO> sugarHighDt = this.bloodSugarMapper.getBloodSugarByMemberIdAndRecordDt(row.getMemberId(),row.getInsertDt().substring(0,10));
                    if(sugarHighDt != null && !sugarHighDt.isEmpty()){
                        sugarHighDt.sort((a , b) ->{
                            double diff = Double.parseDouble(a.getParamValue()) - Double.parseDouble(b.getParamValue());
                            if(diff < 0 ){
                                return -1;
                            }else if(diff > 0){
                                return 1;
                            }else{
                                return 0;
                            }
                        });

                        BloodSugarPO highBloodSugar = sugarHighDt.get(sugarHighDt.size() - 1);
                        BloodSugarPO lowBloodSugar = sugarHighDt.get(0);

                        Double highValue = Double.parseDouble(highBloodSugar.getParamValue());
                        Double lowValue = Double.parseDouble(lowBloodSugar.getParamValue());

                        row.setHighValue(highValue);
                        row.setHighValueDt(highBloodSugar.getRecordDt().substring(11 ,16));
                        row.setHighLevel(highBloodSugar.getParamLevel());
                        row.setHighBloodSugarValue(highValue);

                        row.setLowValueDt(lowBloodSugar.getRecordDt().substring(11 ,16));
                        row.setLowValue(lowValue);
                        row.setLowLevel(lowBloodSugar.getParamLevel());
                        row.setLowBloodSugarValue(lowValue);
                    }

                    row.setTotalTestNum(sugarHighDt.size());
                }
                //修改数据为已查看
                if(paramDTO.getDealIsLook() != null && paramDTO.getDealIsLook()){
                    this.bloodSugarMapper.updateBloodSugarEveryDayByMemberId(1,DateHelper.getToday());
                }
            }
        }
        return pageResult;
    }

    private List<String> getDepartIdsByDoctorId(String doctorId){
        List<String> departmentIdList = new ArrayList<>();
        //获取医生的科室权限
        List<DoctorDepartmentVO> departVOs = this.doctorService.listDoctorDepartment(doctorId);
        if(departVOs != null && departVOs.size() > 0) {
            for (DoctorDepartmentVO departVO : departVOs) {
                departmentIdList.add(departVO.getDepartmentId());
            }
        }
        return departmentIdList;
    }

    private List<String> getDepartIdsByHospitalId(String hospitalId){
        List<String> departmentIdList = new ArrayList<>();
        List<DepartmentPO> departmentPOS = this.doctorService.listDoctorDepartHasAccountByHosId(hospitalId);
        if (departmentPOS != null && departmentPOS.size() > 0){
            for (DepartmentPO departmentPO : departmentPOS) {
                departmentIdList.add(departmentPO.getDepartmentId());
            }
        }
        return departmentIdList;
    }

    private static List<String> getParamCodeList(){
        List<String> list = new ArrayList<String>();
        //随机
        list.add(SignConstant.PARAM_CODE_RANDOM_TIME);
        //凌晨
        list.add(SignConstant.PARAM_CODE_BEFORE_DAWN);
        //早餐前
        list.add(SignConstant.PARAM_CODE_BEFORE_BREAKFAST);
        //早餐后
        list.add(SignConstant.PARAM_CODE_AFTER_BREAKFAST);
        //早餐后1H
        list.add(SignConstant.PARAM_CODE_AFTER_BREAKFAST_1H);
        //午餐前
        list.add(SignConstant.PARAM_CODE_BEFORE_LUNCH);
        //午餐后
        list.add(SignConstant.PARAM_CODE_AFTER_LUNCH);
        //午餐后1H
        list.add(SignConstant.PARAM_CODE_AFTER_LUNCH_1H);
        //晚餐前
        list.add(SignConstant.PARAM_CODE_BEFORE_DINNER);
        //晚餐后
        list.add(SignConstant.PARAM_CODE_AFTER_DINNER);
        //晚餐后1H
        list.add(SignConstant.PARAM_CODE_AFTER_DINNER_1H);
        //睡前
        list.add(SignConstant.PARAM_CODE_BEFORE_SLEEP);
        return list;
    }

    private static List<List<String>> getParamCodeLists(){
        List<List<String>> results = new ArrayList<>();
        //随机
        List<String> list = new ArrayList<String>();
        list.add(SignConstant.PARAM_CODE_RANDOM_TIME);
        results.add(list);
        //凌晨
        list = new ArrayList<String>();
        list.add(SignConstant.PARAM_CODE_BEFORE_DAWN);
        list.add(SignConstant.PARAM_CODE_3AM);
        results.add(list);
        //早餐前
        list = new ArrayList<String>();
        list.add(SignConstant.PARAM_CODE_BEFORE_BREAKFAST);
        results.add(list);
        //早餐后
        list = new ArrayList<String>();
        list.add(SignConstant.PARAM_CODE_AFTER_BREAKFAST);
        list.add(SignConstant.PARAM_CODE_AFTER_BREAKFAST_1H);
        results.add(list);
        //午餐前
        list = new ArrayList<String>();
        list.add(SignConstant.PARAM_CODE_BEFORE_LUNCH);
        results.add(list);
        //午餐后
        list = new ArrayList<String>();
        list.add(SignConstant.PARAM_CODE_AFTER_LUNCH);
        list.add(SignConstant.PARAM_CODE_AFTER_LUNCH_1H);
        results.add(list);
        //晚餐前
        list = new ArrayList<String>();
        list.add(SignConstant.PARAM_CODE_BEFORE_DINNER);
        results.add(list);
        //晚餐后
        list = new ArrayList<String>();
        list.add(SignConstant.PARAM_CODE_AFTER_DINNER);
        list.add(SignConstant.PARAM_CODE_AFTER_DINNER_1H);
        results.add(list);
        //睡前
        list = new ArrayList<String>();
        list.add(SignConstant.PARAM_CODE_BEFORE_SLEEP);
        results.add(list);
        return results;
    }

    private static String numToParamCode(String numCode){
        String result = "";
        if (!StringUtils.isBlank(numCode)){
            result = numCode.replace("1",SignConstant.PARAM_CODE_BEFORE_DAWN).replace("2",SignConstant.PARAM_CODE_BEFORE_BREAKFAST)
                    .replace("3",SignConstant.PARAM_CODE_AFTER_BREAKFAST).replace("4",SignConstant.PARAM_CODE_BEFORE_LUNCH)
                    .replace("5",SignConstant.PARAM_CODE_AFTER_LUNCH).replace("6",SignConstant.PARAM_CODE_BEFORE_DINNER)
                    .replace("7",SignConstant.PARAM_CODE_AFTER_DINNER).replace("8",SignConstant.PARAM_CODE_BEFORE_SLEEP);
        }
        return result;
    }

    private void setMemberParamValuePO(MemberParamValuePO po,AddBloodSugarMapperDTO dto){
        String memberId = dto.getMemberId();
        Integer paramLevel = dto.getParamLevel();

        Date recordDt = DateHelper.getDate(dto.getRecordDt(),"yyyy-MM-dd");

        //判断是否连续三次高血糖
        Boolean threeTimesHigh = false;
        if((po.getTreeTimesHigh() == null || po.getTreeTimesHigh() == 0) && paramLevel != -1) {
            threeTimesHigh = isThreeTimesHighBloodSugar(memberId,paramLevel,recordDt);
        }

        //设置高血糖/低血糖比例
        String recordDtStr = DateHelper.getDate(recordDt,"yyyy-MM-dd");
        TodayBloodDTO bloodDTO = new TodayBloodDTO();
        bloodDTO.setMemberId(memberId);
        bloodDTO.setStartDt(recordDtStr+DateHelper.DEFUALT_TIME_START);
        bloodDTO.setEndDt(recordDtStr+DateHelper.DEFUALT_TIME_END);
        Long abnormaSugarNum = this.bloodSugarMapper.getTodayMemberBlood(bloodDTO);
        bloodDTO.setHighAbnormal(true);
        Long highSuagrNum = this.bloodSugarMapper.getTodayMemberBlood(bloodDTO);
        bloodDTO.setHighAbnormal(false);
        bloodDTO.setLowAbnormal(true);
        Long lowSuagrNum = this.bloodSugarMapper.getTodayMemberBlood(bloodDTO);
        Double dAbnormaSugarNum = Double.parseDouble(abnormaSugarNum+"");
        Long highRate = 0L;
        Long lowRate = 0L;
        if(abnormaSugarNum!=null&&abnormaSugarNum>0){
            if(highSuagrNum!=null){
                Double dHighSuagrNum = Double.parseDouble(highSuagrNum+"");
                Double d = (dHighSuagrNum/dAbnormaSugarNum) * 100;
                highRate = (long)d.doubleValue();
            }

            if(lowSuagrNum!=null){
                Double dLowSuagrNum = Double.parseDouble(lowSuagrNum+"");
                Double d = (dLowSuagrNum/dAbnormaSugarNum) * 100;
                lowRate =  (long)d.doubleValue();
            }
        }
        po.setMemberId(memberId);
        po.setParamDt(recordDtStr);
        po.setTreeTimesHigh(threeTimesHigh == true ? 1 : 0);
        po.setHighRate(Double.parseDouble(highRate.toString()));
        po.setLowRate(Double.parseDouble(lowRate.toString()));
        int str3 = 3;
        if (paramLevel == -1){
            //非监测类型
            po.setParamLevel(dto.getParamLevel());
        }else {
            if(paramLevel<str3){
                if(po.getLowValue() == null || Double.valueOf(dto.getParamValue()) < po.getLowValue() ) {
                    po.setLowValue(Double.parseDouble(dto.getParamValue()));
                }
                po.setParamLevel(dto.getParamLevel());
            } else if(paramLevel>str3){
                if(po.getHighValue() == null || Double.valueOf(dto.getParamValue()) > po.getHighValue() ) {
                    po.setHighValue(Double.parseDouble(dto.getParamValue()));
                }
                po.setParamLevel(dto.getParamLevel());
            } else {
                if(po.getParamLevel()!=null){
                    po.setParamLevel(po.getParamLevel());
                } else {
                    po.setParamLevel(str3);
                }
            }
        }

        //设置标准差
        Double standardValue = 0D;
        Double[] arr = null;
        //设置/更新 po的paramValueJson
        String paramValueJson = po.getParamValueJson();
        DoctorPO doctor = this.doctorService.getDoctorById(dto.getOperatorId());
        String doctorName = doctor == null ? "" : doctor.getDoctorName();
        if (StringUtils.isBlank(paramValueJson)){
            arr = new Double[1];
            Map<String,Object> map = new HashMap<String,Object>(1);
            map.put("paramLevel",paramLevel);
            map.put("memberId",memberId);
            map.put("paramCode",dto.getParamCode());
            map.put("sid",dto.getSid());
            map.put("origin",dto.getOrigin());
            map.put("recordDt",dto.getRecordDt());
            map.put("remark",dto.getRemark());
            map.put("paramValue",dto.getParamValue());
            map.put("optionUserName",doctorName);
            map.put("logCount",1);
            List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>(1);
            maps.add(map);
            paramValueJson = JSON.toJSONString(maps);
            if (dto.getRecordType().equals(1)){
                arr[0] = Double.parseDouble(dto.getParamValue());
            }
        }else{
            List<Map> maps = JSON.parseArray(paramValueJson,Map.class);
            String paramCode = dto.getParamCode();
            Boolean flag = false;
            for(Map map : maps){
                if(paramCode.equals(map.get("paramCode").toString())){
                    flag = true;
                    map.put("paramLevel",paramLevel);
                    map.put("memberId",memberId);
                    map.put("sid",dto.getSid());
                    map.put("origin",dto.getOrigin());
                    map.put("recordDt",dto.getRecordDt());
                    map.put("remark",dto.getRemark());
                    map.put("paramValue",dto.getParamValue());
                    map.put("optionUserName",doctorName);
                    Object logCountObj = map.get("logCount");
                    if(logCountObj!=null){
                        Integer logCount = Integer.parseInt(logCountObj.toString());
                        map.put("logCount",++logCount);
                    } else {
                        map.put("logCount",1);
                    }
                    break;
                }
            }
            if(!flag){
                Map<String,Object> map = new HashMap<String,Object>(1);
                map.put("paramLevel",paramLevel);
                map.put("memberId",memberId);
                map.put("paramCode",dto.getParamCode());
                map.put("sid",dto.getSid());
                map.put("origin",dto.getOrigin());
                map.put("recordDt",dto.getRecordDt());
                map.put("remark",dto.getRemark());
                map.put("paramValue",dto.getParamValue());
                map.put("optionUserName",doctorName);
                map.put("logCount",1);
                maps.add(map);
            }
            paramValueJson = JSON.toJSONString(maps);
            if (dto.getRecordType().equals(1)){
                arr = new Double[maps.size()];
                for(int i = 0;i<maps.size();i++){
                    arr[i] = 0D;
                    Map map = maps.get(i);
                    if(map.get("paramValue")!=null&&!StringUtils.isBlank(map.get("paramValue").toString())){
                        arr[i] = Double.parseDouble(map.get("paramValue").toString());
                    }
                }
            }
        }
        po.setParamValueJson(paramValueJson);
        if (dto.getRecordType().equals(1)){
            standardValue = getStandardValue(arr);
            po.setStandardValue(Double.parseDouble(String.format("%.2f",standardValue)));
        }else {
            po.setStandardValue(null);
        }
        po.setParamType(dto.getInHos());
    }

    /**
     * 判断是否连续3次高血糖
     * @param memberId
     * @param paramLevel
     * @return
     */
    private Boolean isThreeTimesHighBloodSugar(String memberId,Integer paramLevel,Date paramDt){
        Boolean threeTimesHigh = false;
        List<BloodSugarPO> bloodSugarPOS = this.bloodSugarMapper.listTreeTimesParamLogsByMid(memberId,DateHelper.getDate(paramDt, "yyyy-MM-dd"));
        if(bloodSugarPOS.size() >=3) {
            int heightCnt = 0; //连续偏高次数
            for (Iterator<BloodSugarPO> iterator = bloodSugarPOS.iterator(); iterator.hasNext();) {
                BloodSugarPO bloodSugarPO = (BloodSugarPO) iterator.next();

                if(heightCnt >= 3) {
                    break;
                }

                Integer mLevel = bloodSugarPO.getParamLevel();
                if (mLevel > 3){
                    heightCnt ++;
                }else {
                    heightCnt = 0;
                }
            }

            if(heightCnt >= 3) {
                threeTimesHigh = true;
            }
        }
        /*
        int str2 = 2;
        if (bloodSugarPOS.size() == str2){
            int flag = str2;
            int str3 = 3;
            for (BloodSugarPO po : bloodSugarPOS) {
                Integer mLevel = po.getParamLevel();
                if (mLevel > str3){
                    flag ++;
                }else if (mLevel < str3){
                    flag --;
                }
            }
            if (flag > str3){
                if (paramLevel > str3){
                    threeTimesHigh = true;
                }
            }
        }
        */
        return threeTimesHigh;
    }

    /**
     * 计算标准差
     * @author 李左河
     * @date 2018年3月21日 上午11:25:45
     * @param arr
     * @return
     */
    public static Double getStandardValue(Double ...arr){
        Double standardValue = Math.sqrt(getVariance(arr));
        return standardValue;
    }

    /**
     * 计算方差
     * @author 李左河
     * @date 2018年3月21日 上午11:25:52
     * @param arr
     * @return
     */
    public static Double getVariance(Double ...arr){
        Double variance = 0D;
        Double sum = 0D;
        Double avg = getAvg(arr);
        Integer n = 0;
        for(Double d : arr){
            sum += d*d-2*d*avg+avg*avg;
            n++;
        }
        if(n>0){
            variance = sum/n;
        }
        return variance;
    }
    /**
     * 计算平均值
     * @author 李左河
     * @date 2018年3月21日 上午11:25:59
     * @param arr
     * @return
     */
    public static Double getAvg(Double ...arr){
        Double avg = 0D;
        Double sum = getSum(arr);
        Integer n = arr.length;
        if(n>0){
            avg = sum/n;
        }
        return avg;
    }
    /**
     * 求和
     * @author 李左河
     * @date 2018年3月21日 上午11:26:05
     * @param arr
     * @return
     */
    public static Double getSum(Double ...arr){
        Double sum = 0D;
        for(Double d : arr){
            sum+=d;
        }
        return sum;
    }


    //每日血糖记录
    @Override
    @Transactional
    public void dayMemberSugarRecordHandler(AddBloodSugarServiceDTO dto,AddBloodSugarMapperDTO mDto) {
        MemberSugarDayRecordPO sugarDayPO = this.getSugarDayPOByMemberIdAndDt(dto.getMemberId(), dto.getRecordDt());
        Map<String, Object> map = new HashMap<>();
        map.put("sid", mDto.getSid());
        map.put("origin", dto.getOrigin());
        map.put("memberId", dto.getMemberId());
        map.put("paramCode", dto.getParamCode());
        map.put("paramValue", dto.getParamValue());
        map.put("paramLevel", mDto.getParamLevel());
        map.put("recordDt", dto.getRecordDt());
        map.put("remark", dto.getRemark());
        map.put("insertDt", DateHelper.getNowDate());
        map.put("strongCode", mDto.getStrongCode());
        DoctorPO doctor = this.doctorService.getDoctorById(dto.getOperatorId());
        String doctorName = doctor == null ? "" : doctor.getDoctorName();
        map.put("optionUserName", doctorName);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>(1);
        maps.add(map);
        String json = JSON.toJSONString(maps);
        if (null == sugarDayPO) {
            MemberSugarDayRecordPO dayPO = new MemberSugarDayRecordPO();
            dayPO.setSid(DaoHelper.getSeq());
            dayPO.setMemberId(dto.getMemberId());
            GetMemberDTO getMemberDTO = new GetMemberDTO();
            getMemberDTO.setMemberId(dto.getMemberId());
            dayPO.setMemberName(memberMapper.getMember(getMemberDTO).getMemberName());
            dayPO.setRecordDt(dto.getRecordDt());
            dayPO.setHospitalNo(StringUtils.isBlank(dto.getHospitalNo())?null:dto.getHospitalNo());
            dayPO.setBedNo(dto.getBedNo());
            dayPO.setDepartmentId(mDto.getDepartmentId());
            switch (dto.getParamCode()) {
                case SignConstant.PARAM_CODE_RANDOM_TIME:
                    dayPO.setRandomtimeJson(json);
                    break;
                case SignConstant.PARAM_CODE_BEFORE_DAWN:
                case SignConstant.PARAM_CODE_3AM:
                    dayPO.setBeforedawnJson(json);
                    break;
                case SignConstant.PARAM_CODE_BEFORE_BREAKFAST:
                    dayPO.setBeforebreakfastJson(json);
                    break;
                case SignConstant.PARAM_CODE_AFTER_BREAKFAST:
                case SignConstant.PARAM_CODE_AFTER_BREAKFAST_1H:
                    dayPO.setAfterbreakfastJson(json);
                    break;
                case SignConstant.PARAM_CODE_BEFORE_LUNCH:
                    dayPO.setBeforelunchJson(json);
                    break;
                case SignConstant.PARAM_CODE_AFTER_LUNCH:
                case SignConstant.PARAM_CODE_AFTER_LUNCH_1H:
                    dayPO.setAfterlunchJson(json);
                    break;
                case SignConstant.PARAM_CODE_BEFORE_DINNER:
                    dayPO.setBeforedinnerJson(json);
                    break;
                case SignConstant.PARAM_CODE_AFTER_DINNER:
                case SignConstant.PARAM_CODE_AFTER_DINNER_1H:
                    dayPO.setAfterdinnerJson(json);
                    break;
                case SignConstant.PARAM_CODE_BEFORE_SLEEP:
                    dayPO.setBeforesleepJson(json);
                    break;
                default:
                    dayPO.setRandomtimeJson(json);
                    log.warn("新增每日血糖记录冗余数据时，paramCode无法匹配到现有逻辑!血糖记录id:{},血糖paramCode:{}" ,mDto.getSid() ,dto.getParamCode());
                    break;
            }
            bloodSugarMapper.addMemberSugarDayRecord(dayPO);
        } else {
            switch (dto.getParamCode()) {
                case SignConstant.PARAM_CODE_RANDOM_TIME:
                    String randomtimeJson = sugarDayPO.getRandomtimeJson();
                    if (StringUtils.isBlank(randomtimeJson)) {
                        sugarDayPO.setRandomtimeJson(json);
                    } else {
                        List<Map> mapList = JSON.parseArray(randomtimeJson, Map.class);
                        mapList.add(map);
                        ListUtils.sortListMapByKey(mapList,"recordDt",false);
                        sugarDayPO.setRandomtimeJson(JSON.toJSONString(mapList));
                    }
                    break;
                case SignConstant.PARAM_CODE_BEFORE_DAWN:
                case SignConstant.PARAM_CODE_3AM:
                    String beforedawn = sugarDayPO.getBeforedawnJson();
                    if (StringUtils.isBlank(beforedawn)) {
                        sugarDayPO.setBeforedawnJson(json);
                    } else {
                        List<Map> mapList = JSON.parseArray(beforedawn, Map.class);
                        mapList.add(map);
                        ListUtils.sortListMapByKey(mapList,"recordDt",false);
                        sugarDayPO.setBeforedawnJson(JSON.toJSONString(mapList));
                    }

                    break;
                case SignConstant.PARAM_CODE_BEFORE_BREAKFAST:
                    String beforebreakfast = sugarDayPO.getBeforebreakfastJson();
                    if (StringUtils.isBlank(beforebreakfast)) {
                        sugarDayPO.setBeforebreakfastJson(json);
                    } else {
                        List<Map> mapList = JSON.parseArray(beforebreakfast, Map.class);
                        mapList.add(map);
                        ListUtils.sortListMapByKey(mapList,"recordDt",false);
                        sugarDayPO.setBeforebreakfastJson(JSON.toJSONString(mapList));
                    }
                    break;
                case SignConstant.PARAM_CODE_AFTER_BREAKFAST:
                case SignConstant.PARAM_CODE_AFTER_BREAKFAST_1H:
                    String afterbreakfast = sugarDayPO.getAfterbreakfastJson();
                    if (StringUtils.isBlank(afterbreakfast)) {
                        sugarDayPO.setAfterbreakfastJson(json);
                    } else {
                        List<Map> mapList = JSON.parseArray(afterbreakfast, Map.class);
                        mapList.add(map);
                        ListUtils.sortListMapByKey(mapList,"recordDt",false);
                        sugarDayPO.setAfterbreakfastJson(JSON.toJSONString(mapList));
                    }
                    break;
                case SignConstant.PARAM_CODE_BEFORE_LUNCH:
                    String beforelunch = sugarDayPO.getBeforelunchJson();
                    if (StringUtils.isBlank(beforelunch)) {
                        sugarDayPO.setBeforelunchJson(json);
                    } else {
                        List<Map> mapList = JSON.parseArray(beforelunch, Map.class);
                        mapList.add(map);
                        ListUtils.sortListMapByKey(mapList,"recordDt",false);
                        sugarDayPO.setBeforelunchJson(JSON.toJSONString(mapList));
                    }
                    break;
                case SignConstant.PARAM_CODE_AFTER_LUNCH:
                case SignConstant.PARAM_CODE_AFTER_LUNCH_1H:
                    String afterlunch = sugarDayPO.getAfterlunchJson();
                    if (StringUtils.isBlank(afterlunch)) {
                        sugarDayPO.setAfterlunchJson(json);
                    } else {
                        List<Map> mapList = JSON.parseArray(afterlunch, Map.class);
                        mapList.add(map);
                        ListUtils.sortListMapByKey(mapList,"recordDt",false);
                        sugarDayPO.setAfterlunchJson(JSON.toJSONString(mapList));
                    }
                    break;
                case SignConstant.PARAM_CODE_BEFORE_DINNER:
                    String beforedinner = sugarDayPO.getBeforedinnerJson();
                    if (StringUtils.isBlank(beforedinner)) {
                        sugarDayPO.setBeforedinnerJson(json);
                    } else {
                        List<Map> mapList = JSON.parseArray(beforedinner, Map.class);
                        mapList.add(map);
                        ListUtils.sortListMapByKey(mapList,"recordDt",false);
                        sugarDayPO.setBeforedinnerJson(JSON.toJSONString(mapList));
                    }
                    break;
                case SignConstant.PARAM_CODE_AFTER_DINNER:
                case SignConstant.PARAM_CODE_AFTER_DINNER_1H:
                    String afterdinner = sugarDayPO.getAfterdinnerJson();
                    if (StringUtils.isBlank(afterdinner)) {
                        sugarDayPO.setAfterdinnerJson(json);
                    } else {
                        List<Map> mapList = JSON.parseArray(afterdinner, Map.class);
                        mapList.add(map);
                        ListUtils.sortListMapByKey(mapList,"recordDt",false);
                        sugarDayPO.setAfterdinnerJson(JSON.toJSONString(mapList));
                    }
                    break;
                case SignConstant.PARAM_CODE_BEFORE_SLEEP:
                    String beforesleep = sugarDayPO.getBeforesleepJson();
                    if (StringUtils.isBlank(beforesleep)) {
                        sugarDayPO.setBeforesleepJson(json);
                    } else {
                        List<Map> mapList = JSON.parseArray(beforesleep, Map.class);
                        mapList.add(map);
                        ListUtils.sortListMapByKey(mapList,"recordDt",false);
                        sugarDayPO.setBeforesleepJson(JSON.toJSONString(mapList));
                    }
                    break;
                default:
                    log.warn("修改每日血糖记录冗余数据时，paramCode无法匹配到现有逻辑!血糖记录id:{},血糖paramCode:{}" ,mDto.getSid() ,dto.getParamCode());
                    String defaultJson = sugarDayPO.getRandomtimeJson();
                    if (StringUtils.isBlank(defaultJson)) {
                        sugarDayPO.setRandomtimeJson(json);
                    } else {
                        List<Map> mapList = JSON.parseArray(defaultJson, Map.class);
                        mapList.add(map);
                        ListUtils.sortListMapByKey(mapList,"recordDt",false);
                        sugarDayPO.setRandomtimeJson(JSON.toJSONString(mapList));
                    }
                    break;
            }
            bloodSugarMapper.updateMemberSugarDayRecord(sugarDayPO);
        }
    }

    @Override
    public BloodSugarPO listBloodSugarOneDayOfInHos(ListBloodSugarDTO listBloodSugarDTO) {
        return this.bloodSugarMapper.listBloodSugarOneDayOfInHos(listBloodSugarDTO);
    }

    @Override
    public PageResult<BloodSugarPO> pageBloodSugar(String sidStr, PageRequest pager) {
        String[] sidArray = sidStr.split(",");
        ListBloodSugarDTO dto = new ListBloodSugarDTO();
        dto.setSidArray(sidArray);
        PageHelper.startPage(pager.getPage(),pager.getRows());
        List<BloodSugarPO> list = this.bloodSugarMapper.listBloodSugar(dto);
        return new PageResult<>(list);
    }


    @Override
    @Transactional
    public boolean deleteBloodSugar(String sid, String memberId, String recordDt) {
        BloodSugarPO bloodSugarPO = this.bloodSugarMapper.getBloodSugar(sid);
        if(bloodSugarPO ==null){
            throw new BusinessException("血糖记录不存在");
        }
        this.bloodSugarMapper.deleteMemberBloodSugar(sid);
        this.bloodSugarDayRecordHandler(sid,memberId,recordDt,bloodSugarPO.getParamCode());
        MemberParamValuePO memberParamValuePO = this.bloodSugarMapper.getMemberEveryDaySugarByParams(sid,memberId,recordDt);
        if(memberParamValuePO!=null&&!StringUtils.isBlank(memberParamValuePO.getParamValueJson())){
            String json = memberParamValuePO.getParamValueJson();
            if(!StringUtils.isBlank(json)){
                int index = -1;
                String paramCode = null;
                JSONArray jsonArray = JSONArray.parseArray(json);
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if(jsonObject!=null){
                        if(sid.equals(jsonObject.getString("sid"))){
                            index = i;
                            paramCode = jsonObject.getString("paramCode");
                            break;
                        }
                    }
                }
                if(index>=0 && !StringUtils.isBlank(paramCode)){
                    BloodSugarPO bloodSugarPO1 = this.bloodSugarMapper.getLatestBloodSugar(memberId,paramCode,recordDt);
                    if(bloodSugarPO1!=null && !sid.equals(bloodSugarPO1.getSid())){
                        if(jsonArray == null || jsonArray.size()<=0){
                            jsonArray = new JSONArray();
                            jsonArray.add(bloodSugarPO1);
                        } else {
                            jsonArray.set(index,bloodSugarPO1);
                        }
                    } else {
                        jsonArray.remove(index);
                    }
                    if(jsonArray == null || jsonArray.size()<=0){
                        this.bloodSugarMapper.deleteMemberBloodSugarOfEverDay(memberParamValuePO.getSid());
                    } else {
                        memberParamValuePO.setParamValueJson(JSONArray.toJSONString(jsonArray));
                        if(bloodSugarPO1!=null){
                            AddBloodSugarMapperDTO dto = new AddBloodSugarMapperDTO();
                            BeanUtils.copyProperties(dto,bloodSugarPO1);
                            this.setMemberParamValuePO(memberParamValuePO,dto);
                        } else {
                            bloodSugarPO = this.bloodSugarMapper.getLatestBloodSugar(memberId,null,recordDt);
                            if(bloodSugarPO!=null){
                                AddBloodSugarMapperDTO dto = new AddBloodSugarMapperDTO();
                                BeanUtils.copyProperties(dto,bloodSugarPO);
                                this.setMemberParamValuePO(memberParamValuePO,dto);
                            }
                        }
                        this.bloodSugarMapper.updateMemberBloodSugarOfEverDay(memberParamValuePO);
                    }
                }
            }
        }
        //查询是否有记录的血糖
        List<BloodSugarPO> bloodSugarPOS = this.bloodSugarMapper.listBloodSugarByMemberIdAndRecordDt(bloodSugarPO);
        if (bloodSugarPOS == null || bloodSugarPOS.size() == 0){
            MemberMonitorTaskCardPO taskCardPO = new MemberMonitorTaskCardPO();
            taskCardPO.setParamCode(paramCodeValue(bloodSugarPO.getParamCode()));
            taskCardPO.setMemberId(memberId);
            taskCardPO.setMonitorDt(recordDt);
            taskCardPO.setInsertDt(recordDt);
            taskCardPO.setIsMonitor("0");
            memberMonitorPlanRecordService.updateMonitorTaskCard(taskCardPO);
        }
        return true;
    }

    @Override
    @Transactional
    public void bloodSugarDayRecordHandler(String sid,String memberId,String recordDt,String paramCode){
        //全院血糖每日血糖记录表
        MemberSugarDayRecordPO sugarDayPO = bloodSugarMapper.getSugarDayPOByMemberIdAndDt(memberId, recordDt);
        if (null == sugarDayPO){
            //throw new BusinessException("血糖记录不存在1");
            return;
        }
        if (paramCode.equals(SignConstant.PARAM_CODE_RANDOM_TIME)){
            String json = sugarDayPO.getRandomtimeJson();
            List<Map> mapList = JSON.parseArray(json, Map.class);
            List<Map> list = mapList.stream().filter(e ->
                    !e.get("sid").equals(sid)
            ).collect(Collectors.toList());
            if (list.size()>0){
                sugarDayPO.setRandomtimeJson(JSON.toJSONString(list));
            }else {
                sugarDayPO.setRandomtimeJson(null);
            }

        }else if (paramCode.equals(SignConstant.PARAM_CODE_BEFORE_DAWN) || paramCode.equals(SignConstant.PARAM_CODE_3AM)){
            String json = sugarDayPO.getBeforedawnJson();
            List<Map> mapList = JSON.parseArray(json, Map.class);
            List<Map> list = mapList.stream().filter(e ->
                    !e.get("sid").equals(sid)
            ).collect(Collectors.toList());
            if (list.size()>0){
                sugarDayPO.setBeforedawnJson(JSON.toJSONString(list));
            }else {
                sugarDayPO.setBeforedawnJson(null);
            }

        }else if (paramCode.equals(SignConstant.PARAM_CODE_BEFORE_BREAKFAST)){
            String json = sugarDayPO.getBeforebreakfastJson();
            List<Map> mapList = JSON.parseArray(json, Map.class);
            List<Map> list = mapList.stream().filter(e ->
                    !e.get("sid").equals(sid)
            ).collect(Collectors.toList());
            if (list.size()>0){
                sugarDayPO.setBeforebreakfastJson(JSON.toJSONString(list));
            }else {
                sugarDayPO.setBeforebreakfastJson(null);
            }
        }else if (paramCode.equals(SignConstant.PARAM_CODE_AFTER_BREAKFAST) || paramCode.equals(SignConstant.PARAM_CODE_AFTER_BREAKFAST_1H)){
            String json = sugarDayPO.getAfterbreakfastJson();
            List<Map> mapList = JSON.parseArray(json, Map.class);
            List<Map> list = mapList.stream().filter(e ->
                    !e.get("sid").equals(sid)
            ).collect(Collectors.toList());
            if (list.size()>0){
                sugarDayPO.setAfterbreakfastJson(JSON.toJSONString(list));
            }else {
                sugarDayPO.setAfterbreakfastJson(null);
            }
        }else if (paramCode.equals(SignConstant.PARAM_CODE_BEFORE_LUNCH)){
            String json = sugarDayPO.getBeforelunchJson();
            List<Map> mapList = JSON.parseArray(json, Map.class);
            List<Map> list = mapList.stream().filter(e ->
                    !e.get("sid").equals(sid)
            ).collect(Collectors.toList());
            if (list.size()>0){
                sugarDayPO.setBeforelunchJson(JSON.toJSONString(list));
            }else {
                sugarDayPO.setBeforelunchJson(null);
            }

        }else if (paramCode.equals(SignConstant.PARAM_CODE_AFTER_LUNCH) || paramCode.equals(SignConstant.PARAM_CODE_AFTER_LUNCH_1H)){
            String json = sugarDayPO.getAfterlunchJson();
            List<Map> mapList = JSON.parseArray(json, Map.class);
            List<Map> list = mapList.stream().filter(e ->
                    !e.get("sid").equals(sid)
            ).collect(Collectors.toList());
            if (list.size()>0){
                sugarDayPO.setAfterlunchJson(JSON.toJSONString(list));
            }else {
                sugarDayPO.setAfterlunchJson(null);
            }

        }else if (paramCode.equals(SignConstant.PARAM_CODE_BEFORE_DINNER)){
            String json = sugarDayPO.getBeforedinnerJson();
            List<Map> mapList = JSON.parseArray(json, Map.class);
            List<Map> list = mapList.stream().filter(e ->
                    !e.get("sid").equals(sid)
            ).collect(Collectors.toList());
            if (list.size()>0){
                sugarDayPO.setBeforedinnerJson(JSON.toJSONString(list));
            }else {
                sugarDayPO.setBeforedinnerJson(null);
            }
        }else if (paramCode.equals(SignConstant.PARAM_CODE_AFTER_DINNER) || paramCode.equals(SignConstant.PARAM_CODE_AFTER_DINNER_1H)){
            String json = sugarDayPO.getAfterdinnerJson();
            List<Map> mapList = JSON.parseArray(json, Map.class);
            List<Map> list = mapList.stream().filter(e ->
                    !e.get("sid").equals(sid)
            ).collect(Collectors.toList());
            if (list.size()>0){
                sugarDayPO.setAfterdinnerJson(JSON.toJSONString(list));
            }else {
                sugarDayPO.setAfterdinnerJson(null);
            }
        }else if (paramCode.equals(SignConstant.PARAM_CODE_BEFORE_SLEEP)){
            String json = sugarDayPO.getBeforesleepJson();
            List<Map> mapList = JSON.parseArray(json, Map.class);
            List<Map> list = mapList.stream().filter(e ->
                    !e.get("sid").equals(sid)
            ).collect(Collectors.toList());
            if (list.size()>0){
                sugarDayPO.setBeforesleepJson(JSON.toJSONString(list));
            }else {
                sugarDayPO.setBeforesleepJson(null);
            }
        }
        if (StringUtils.isBlank(sugarDayPO.getRandomtimeJson()) &&
                StringUtils.isBlank(sugarDayPO.getBeforedawnJson()) &&
                StringUtils.isBlank(sugarDayPO.getBeforebreakfastJson())&&
                StringUtils.isBlank(sugarDayPO.getAfterbreakfastJson())&&
                StringUtils.isBlank(sugarDayPO.getBeforelunchJson())&&
                StringUtils.isBlank(sugarDayPO.getAfterlunchJson())&&
                StringUtils.isBlank(sugarDayPO.getBeforedinnerJson())&&
                StringUtils.isBlank(sugarDayPO.getAfterdinnerJson())&&
                StringUtils.isBlank(sugarDayPO.getBeforesleepJson())
        ){
            sugarDayPO.setIsValid(0);
        }
        bloodSugarMapper.deleteMemberSugarDayRecord(sugarDayPO);
    }
    @Override
    public List<BloodSugarPO> listMembersLatestBloodSugarRecord(List<String> memberIdList) {
        return this.bloodSugarMapper.listMembersLatestBloodSugarRecord(memberIdList);
    }

    @Override
    public List<BloodSugarPO> listOneDayLatestBloodSugarGroupParamCode(String memberId, String startDt ,String endDt) {
        return this.bloodSugarMapper.listOneDayLatestBloodSugarGroupParamCode(memberId ,startDt ,endDt);
    }


    @Override
    public PageResult<BloodGlucoseRecordPrintingVO> listMemberBlood(BloodGlucoseRecordPrintingDTO dto, PageRequest pager) {
        ListInHospitalOrVirtualWardHasBloodSugarDataDTO dataDTO = listInHospitalOrVirtualWardHasBloodSugarDataDTOHandler(dto);
        PageHelper.startPage(pager.getPage() ,pager.getRows());
        List<InHospitalMemberInfoWithBloodSugarVO> list = this.memberInHospitalLogMapper.listInHospitalOrVirtualWardHasBloodSugarData(dataDTO);
        PageResult<InHospitalMemberInfoWithBloodSugarVO> pageResult = new PageResult(list);
        PageResult<BloodGlucoseRecordPrintingVO> result = pageResult.createEmptyPageResult();
        List<BloodGlucoseRecordPrintingVO> resultList = new ArrayList<>();
        BloodGlucoseRecordPrintingVO vo;
        ListMembersLatestBloodSugarRecordByRecordDtDTO listMembersLatestBloodSugarRecordByRecordDtDTO;
        for(InHospitalMemberInfoWithBloodSugarVO log : list){
            vo = new BloodGlucoseRecordPrintingVO();
            vo.setBedNo(log.getBedNo());
            vo.setMemberName(log.getMemberName());
            vo.setDepartmentId(log.getDepartmentId());
            vo.setDepartmentName(log.getDepartmentName());
            vo.setRecordDt(log.getRecordDate());

            listMembersLatestBloodSugarRecordByRecordDtDTO = new ListMembersLatestBloodSugarRecordByRecordDtDTO();
            listMembersLatestBloodSugarRecordByRecordDtDTO.setStartDt(log.getRecordDate() + " 00:00:00");
            listMembersLatestBloodSugarRecordByRecordDtDTO.setEndDt(log.getRecordDate() + " 23:59:59");
            listMembersLatestBloodSugarRecordByRecordDtDTO.setMemberId(log.getMemberId());
            listMembersLatestBloodSugarRecordByRecordDtDTO.setInHos(1);
            listMembersLatestBloodSugarRecordByRecordDtDTO.setParamLevel(dto.getParamLevel());
            List<BloodSugarPO> bloodSugarList = this.bloodSugarMapper
                    .listMembersLatestBloodSugarRecordByRecordDt(listMembersLatestBloodSugarRecordByRecordDtDTO);
            for(BloodSugarPO bloodSugar : bloodSugarList){
                String paramCode = bloodSugar.getParamCode();
                String paramValue = bloodSugar.getParamValue();
                Integer paramLevel = bloodSugar.getParamLevel();
                if(SignConstant.PARAM_CODE_RANDOM_TIME.equals(paramCode)){
                    vo.setRandomSugar(paramValue);
                    vo.setRandomSugarLevel(paramLevel);
                }else if(SignConstant.PARAM_CODE_BEFORE_DAWN.equals(paramCode)){
                    vo.setBeforedawnSugar(paramValue);
                    vo.setBeforedawnSugarLevel(paramLevel);
                }else if(SignConstant.PARAM_CODE_BEFORE_BREAKFAST.equals(paramCode)){
                    vo.setBeforeBreakfastSugar(paramValue);
                    vo.setBeforeBreakfastSugarLevel(paramLevel);
                }else if(SignConstant.PARAM_CODE_AFTER_BREAKFAST.equals(paramCode) || SignConstant.PARAM_CODE_AFTER_BREAKFAST_1H.equals(paramCode)){
                    vo.setAfterBreakfastSugar(paramValue);
                    vo.setAfterBreakfastSugarLevel(paramLevel);
                }else if(SignConstant.PARAM_CODE_BEFORE_LUNCH.equals(paramCode)){
                    vo.setBeforeLunchSugar(paramValue);
                    vo.setBeforeLunchSugarLevel(paramLevel);
                }else if(SignConstant.PARAM_CODE_AFTER_LUNCH.equals(paramCode) || SignConstant.PARAM_CODE_AFTER_LUNCH_1H.equals(paramCode)){
                    vo.setAfterLunchSugar(paramValue);
                    vo.setAfterLunchSugarLevel(paramLevel);
                }else if(SignConstant.PARAM_CODE_BEFORE_DINNER.equals(paramCode)){
                    vo.setBeforeDinnerSugar(paramValue);
                    vo.setBeforeDinnerSugarLevel(paramLevel);
                }else if(SignConstant.PARAM_CODE_AFTER_DINNER.equals(paramCode) || SignConstant.PARAM_CODE_AFTER_DINNER_1H.equals(paramCode)){
                    vo.setAfterDinnerSugar(paramValue);
                    vo.setAfterDinnerSugarLevel(paramLevel);
                }else if(SignConstant.PARAM_CODE_BEFORE_SLEEP.equals(paramCode)){
                    vo.setBeforeSleepSugar(paramValue);
                    vo.setBeforeSleepSugarLevel(paramLevel);
                }
            }
            resultList.add(vo);
        }
        result.setRows(resultList);
        return result;
    }

    /**
     * 返回科室
     * @param dto
     * @return
     */
    @Override
    public List<DepartmentVO> listDepartment(BloodGlucoseRecordPrintingDTO dto) {
        //存放科室
        List<DepartmentVO> departmentVOS = new ArrayList<>();
        //2: 判断传过来的type(1:全部数据 2:住院患者数据 3:虚拟病区患者数据)
        if ("1".equals(dto.getType())){
        }else if ("2".equals(dto.getType())){
            //获取医生(权限)科室
            List<DepartmentPO> result = this.departmentService.listDoctorManageDepartment(dto.getOperationId());
            for (DepartmentPO departmentPO : result){
                DepartmentVO departmentVO = new DepartmentVO();
                departmentVO.setDepartmentId(departmentPO.getDepartmentId());
                departmentVO.setDepartmentName(departmentPO.getDepartmentName());
                departmentVO.setIsVirtual(departmentPO.getIsVirtual());
                departmentVOS.add(departmentVO);
            }
        }else {
            List<VirtualWardPO> virtualWardPOS = null;
            DoctorPO doctorPO = this.doctorMapper.getDoctorById(dto.getOperationId());
            if (doctorPO.getIsVirtual() == 1){
                //有勾选虚拟病区
                //虚拟病区的全部患者(已转入的)
                virtualWardPOS = this.virtualWardMapper.listAllVirtualWard(dto.getHospitalId(), dto.getDepartmentId(),1,null, dto.getStartDt(), dto.getEndDt());

            }else{
                virtualWardPOS = this.virtualWardMapper.listAllVirtualWard(dto.getHospitalId(), doctorPO.getDepartId(),1,null, dto.getStartDt(), dto.getEndDt());
            }
           for (VirtualWardPO virtualWardPO : virtualWardPOS) {
                DepartmentVO departmentVO = new DepartmentVO();
                departmentVO.setDepartmentId(virtualWardPO.getDepartmentId());
                departmentVO.setDepartmentName(virtualWardPO.getDepartmentName());
                departmentVOS.add(departmentVO);
            }

        }
        List<DepartmentVO> depart = null;
        if (departmentVOS != null && departmentVOS.size() > 0) {
            //去重
            depart = departmentVOS.stream().collect(
                    Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getDepartmentId() + ";" + o.getDepartmentId()))), ArrayList::new)
            );
        }
        return depart;
    }


    private final static String BLOOD_SUGAR_DIALOGUE_TITLE = "血糖测量结果通知";
    /**
     * 血糖单位
     */
    private final static String BLOOD_SUGAR_UNIT = "mmol/L";

    /**
     * 加载医生管理的虚拟病区科室
     * @param doctorId
     * @return
     */
    private List<String> listDoctorManageVirtualDepart(String doctorId){
        DoctorPO doctor = this.doctorService.getDoctorById(doctorId);
        if(doctor.getIsVirtual() == 1){
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject(doctor.getDataAuth());
        Integer type = jsonObject.getInteger("type");
        if(type == null){
            result.add(doctor.getDepartId());
            return result;
        }
        if(1 == type){
            result.add(doctor.getDepartId());
        }else if(3 == type){
            result = getDepartIdsByDoctorId(doctor.getDoctorId());
        }
        return result;
    }

    /**
     * 加载有血糖的住院或者虚拟病区患者参数处理
     * @param dto
     * @return
     */
    private ListInHospitalOrVirtualWardHasBloodSugarDataDTO listInHospitalOrVirtualWardHasBloodSugarDataDTOHandler(BloodGlucoseRecordPrintingDTO dto){
        BloodAuthorityBO bloodAuthorityBO = JSON.parseObject(dto.getAuthority(),BloodAuthorityBO.class);
        Boolean loadInHospitalDataAuthority = false;
        Boolean loadVirtualWardDataAuthority = false;
        if(!StringUtils.isBlank(bloodAuthorityBO.getInHos())){
            loadInHospitalDataAuthority = true;
        }
        if(!StringUtils.isBlank(bloodAuthorityBO.getVirtualWard())){
            loadVirtualWardDataAuthority = true;
        }
        Boolean loadInHospitalData = false;
        Boolean loadVirtualWardData = false;

        List<String> inHospitalDepartIdList = null;
        List<String> virtualWardDepartIdList = null;

        //全部
        if("1".equals(dto.getType())){
            if(loadInHospitalDataAuthority){
                loadInHospitalData = true;
            }
            if(loadVirtualWardDataAuthority){
                loadVirtualWardData = true;
            }
            virtualWardDepartIdList = listDoctorManageVirtualDepart(dto.getOperationId());
            inHospitalDepartIdList = listDoctorManageInHospitalDepart(dto.getOperationId());
        }
        //选择住院
        else if("2".equals(dto.getType())){
            if(loadInHospitalDataAuthority){
                loadInHospitalData = true;
            }
            if(!StringUtils.isBlank(dto.getDepartmentId())){
                inHospitalDepartIdList = Collections.singletonList(dto.getDepartmentId());
            }else{
                inHospitalDepartIdList = listDoctorManageInHospitalDepart(dto.getOperationId());
            }
        }
        //选择虚拟病区
        else if("3".equals(dto.getType())){
            if(loadVirtualWardDataAuthority){
                loadVirtualWardData = true;
            }
            if(!StringUtils.isBlank(dto.getDepartmentId())){
                virtualWardDepartIdList = Collections.singletonList(dto.getDepartmentId());
            }else{
                virtualWardDepartIdList = listDoctorManageVirtualDepart(dto.getOperationId());
            }
        }
        ListInHospitalOrVirtualWardHasBloodSugarDataDTO result = new ListInHospitalOrVirtualWardHasBloodSugarDataDTO();
        result.setHospitalId(dto.getHospitalId());
        result.setStartDt(dto.getStartDt());
        result.setEndDt(dto.getEndDt());
        result.setInHospitalDepartIdList(inHospitalDepartIdList);
        result.setVirtualWardDepartIdList(virtualWardDepartIdList);
        result.setLoadInHospitalData(loadInHospitalData);
        result.setLoadVirtualWardData(loadVirtualWardData);
        result.setParamLevel(dto.getParamLevel());
        return result;
    }

    /**
     * 加载医生管理的住院科室
     * @param doctorId
     * @return
     */
    private List<String> listDoctorManageInHospitalDepart(String doctorId){
        List<DepartmentPO> list = this.departmentService.listDoctorManageDepartment(doctorId);
        if(list == null || list.isEmpty()){
            return null;
        }
        return list.stream().map(DepartmentPO::getDepartmentId).collect(Collectors.toList());
    }

    /**
     * 每日血糖记录基础信息处理
     * @param addBloodSugarServiceDTO
     * @param addBloodSugarMapperDTO
     */
    private void dauSugarRecordValueSet(AddBloodSugarServiceDTO addBloodSugarServiceDTO,AddBloodSugarMapperDTO addBloodSugarMapperDTO){
        MemberCheckinInfoPO memberCheckinInfoByMemberId = memberMapper.getMemberCheckinInfoByMemberId(addBloodSugarServiceDTO.getMemberId());
        //设置默认部门
        if (StringUtils.isBlank(addBloodSugarServiceDTO.getDepartmentId())){
            addBloodSugarMapperDTO.setDepartmentId("-1");
            if (null != memberCheckinInfoByMemberId){
                //有住院
                String departmentId =memberCheckinInfoByMemberId.getDepartmentId();
                addBloodSugarMapperDTO.setDepartmentId(departmentId);
            }
        }
        //设置住院号
        if (StringUtils.isBlank(addBloodSugarServiceDTO.getHospitalNo())){
            if (null != memberCheckinInfoByMemberId){
                addBloodSugarServiceDTO.setHospitalNo(memberCheckinInfoByMemberId.getHospitalNo());
            }
        }
        //设置床号
        if (StringUtils.isBlank(addBloodSugarServiceDTO.getBedNo())){
            if (null != memberCheckinInfoByMemberId){
                addBloodSugarServiceDTO.setBedNo(memberCheckinInfoByMemberId.getBedNo());
            }
        }
    }
}
