package com.comvee.cdms.checkresult.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.checkresult.bo.AddCheckoutDetailBO;
import com.comvee.cdms.checkresult.constant.CheckoutConstant;
import com.comvee.cdms.checkresult.constant.CheckoutDict;
import com.comvee.cdms.checkresult.dto.*;
import com.comvee.cdms.checkresult.mapper.DataSyncTaskMapper;
import com.comvee.cdms.checkresult.mapper.CheckoutDetailMapper;
import com.comvee.cdms.checkresult.mapper.CheckoutMapper;
import com.comvee.cdms.checkresult.po.DataSyncTaskPO;
import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.po.CheckoutPO;
import com.comvee.cdms.checkresult.service.CheckRemindService;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.checkresult.service.CheckoutServiceI;
import com.comvee.cdms.checkresult.tool.CheckoutSyncArchiveTool;
import com.comvee.cdms.checkresult.vo.CheckoutBloodFatVO;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.dto.ListHospitalAllKeyNoteDTO;
import com.comvee.cdms.doctor.model.KeyNoteModel;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.bo.MemberControlTargetBO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.dto.MemberArchivesDTO;
import com.comvee.cdms.member.po.MemberArchivesPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.tool.MemberRangeHelper;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.po.Hba1cPO;
import com.comvee.cdms.sign.service.Hba1cService;
import com.comvee.cdms.visit.constant.VisitEventEnum;
import com.comvee.cdms.visit.dto.AddVistiEventDTO;
import com.comvee.cdms.visit.service.VisitEventService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 李左河
 */
@Service("checkoutService")
public class CheckoutServiceImpl implements CheckoutServiceI {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CheckoutMapper checkoutMapper;

    @Autowired
    private CheckoutDetailServiceI checkoutDetailService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private CheckoutDetailMapper checkoutDetailMapper;

    @Autowired
    private CheckRemindService checkRemindService;

    @Autowired
    private Hba1cService hba1cService;

    @Autowired
    private VisitEventService visitEventService;

    @Autowired
    private DataSyncTaskMapper dataSyncTaskMapper;

    @Override
    public List<CheckoutPO> listCheckout(String memberId) {

        return this.checkoutMapper.listCheckout(memberId);
    }

    @Override
    public CheckoutPO getCheckoutByCheckoutId(String checkoutId, String hospitalId) {

        return this.checkoutMapper.getCheckoutByCheckoutId(checkoutId, hospitalId);
    }

    @Override
    public String getNewestCheckoutResult(Map<String, Object> paramMap) {

        return this.checkoutMapper.getNewestCheckoutResult(paramMap);
    }

    @Override
    public List<CheckoutPO> listCheckoutByParams(Map<String, Object> paramMap) {
        return this.checkoutMapper.listCheckoutByParams(paramMap);
    }

    @Override
    public List<CheckoutDetailPO> listCheckoutDetailByParams(List<String> checkoutIds, String memberId, String hospitalId) {

        return this.checkoutMapper.listCheckoutDetailByParams(checkoutIds, memberId, hospitalId);
    }

    @Override
    public CheckoutPO getCheckoutNew(String memberId) {

        return this.checkoutMapper.getCheckoutNew(memberId);
    }

    @Override
    public List<CheckoutPO> listCheckoutForNearlyYear(String memberId) {

        return this.checkoutMapper.listCheckoutNew(memberId);
    }

    @Override
    public List<CheckoutPO> listCheckoutNearlySixMonths(String memberId, String hospitalId) {

        return this.checkoutMapper.listCheckoutNearlySixMonths(memberId, hospitalId);
    }

    @Override
    public void addCheckout(AddCheckoutDTO dto, DoctorPO doctorPO) {
        String reportDt = dto.getCheckoutDate() + " " + dto.getCheckoutTime();
        if (StringUtils.isBlank(reportDt) || StringUtils.isBlank(dto.getCheckoutDate()) ||
                StringUtils.isBlank(dto.getCheckoutTime())) {
            Date date = new Date();
            reportDt = DateHelper.getDate(date, DateHelper.DATETIME_FORMAT);
            dto.setCheckoutDate(DateHelper.getDate(date, DateHelper.DAY_FORMAT));
            dto.setCheckoutTime(DateHelper.getDate(date, DateHelper.TIME_FORMAT));
        }
        String hospitalId = "-1";
        String hospitalName = "-1";
        String departId = "-1";
        String departName = "-1";
        String doctorId = "-1";
        String doctorName = "-1";
        if (doctorPO != null) {
            hospitalId = doctorPO.getHospitalId();
            hospitalName = doctorPO.getHospitalName();
            departId = doctorPO.getDepartId();
            departName = doctorPO.getDepartName();
            doctorId = doctorPO.getDoctorId();
            doctorName = doctorPO.getDoctorName();
        }
        AddCheckoutWithDetailDTO add = new AddCheckoutWithDetailDTO();
        add.setCheckoutTitle(dto.getCheckoutTitle());
        add.setCheckoutId(DaoHelper.getSeq());
        add.setCheckoutDoctorId(doctorId);
        add.setCheckoutDoctorName(doctorName);
        add.setYzId(dto.getYzId());
        add.setMemberId(dto.getMemberId());
        add.setVisitNo(dto.getVisitNo());
        add.setReportDate(reportDt);
        add.setDepartmentId(departId);
        add.setDepartmentName(departName);
        add.setRecordOrigin(dto.getRecordOrigin());
        add.setHospitalId(hospitalId);
        add.setHospitalName(hospitalName);
        add.setDetailList(JSON.parseArray(dto.getDetailJSON() ,AddCheckoutDetailBO.class));
        add.setVisitType(CheckoutConstant.VISIT_TYPE_OTHER);
        addCheckout(add);
    }

    @Override
    public void deleteCheckout(String checkoutId) {
        CheckoutPO checkoutPO = new CheckoutPO();
        checkoutPO.setIsValid(0);
        checkoutPO.setCheckoutId(checkoutId);
        checkoutPO.setSid(checkoutId);
        this.checkoutMapper.modifyCheckoutByCheckoutId(checkoutPO);
    }

    @Override
    public void modifyCheckout(ModifyCheckoutDTO dto) {
        CheckoutPO checkoutPO = new CheckoutPO();
        BeanUtils.copyProperties(checkoutPO, dto);
        this.checkoutMapper.modifyCheckoutByCheckoutId(checkoutPO);
        String detailJson = dto.getDetailJSON();
        if (!StringUtils.isBlank(detailJson)) {
            checkoutPO = this.checkoutMapper.getCheckoutByCheckoutId(dto.getCheckoutId(), dto.getHospitalId());
            //删除详情-真删除
            this.checkoutDetailService.deleteCheckoutDetailByCheckoutId(dto.getCheckoutId(), dto.getHospitalId());
            //重新保存详情
            String reportDt = dto.getCheckoutDate() + " " + dto.getCheckoutTime();
            JSONArray array = JSONArray.parseArray(detailJson);
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                CheckoutDetailPO checkoutDetailPO = new CheckoutDetailPO();
                checkoutDetailPO.setCheckoutCode(object.getString("code"));
                checkoutDetailPO.setCheckoutName(object.getString("name"));
                checkoutDetailPO.setCheckoutResult(object.getString("value"));
                checkoutDetailPO.setAbnormalSign(object.getString("abnormalSign"));
                checkoutDetailPO.setResultUnit(object.getString("unit"));
                checkoutDetailPO.setAcronym(object.getString("acronym"));
                checkoutDetailPO.setCheckoutDatetime(reportDt);
                checkoutDetailPO.setCheckoutId(dto.getCheckoutId());
                checkoutDetailPO.setMemberId(dto.getMemberId());
                checkoutDetailPO.setHospitalId(checkoutPO.getHospitalId());
                checkoutDetailPO.setHospitalName(checkoutPO.getHospitalName());
                checkoutDetailPO.setSid(DaoHelper.getSeq());
                //获取上一次检验结果
                DecimalFormat df = new DecimalFormat("###########0.00");
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("checkoutCode", checkoutDetailPO.getCheckoutCode());
                paramMap.put("memberId", dto.getMemberId());
                paramMap.put("checkoutDatetime", reportDt);
                String checkoutResultLast = this.checkoutMapper.getNewestCheckoutResult(paramMap);
                if (!StringUtils.isBlank(checkoutResultLast) && !StringUtils.isBlank(checkoutDetailPO.getCheckoutResult())) {
                    Double diff = Double.valueOf(checkoutDetailPO.getCheckoutResult()) - Double.valueOf(checkoutResultLast);
                    if (diff > 0) {
                        checkoutResultLast = df.format(diff);
                    } else {
                        checkoutResultLast = df.format(diff);
                    }
                }
                checkoutDetailPO.setCheckoutResultLast(checkoutResultLast);
                this.checkoutMapper.saveCheckoutDetail(checkoutDetailPO);
            }
        }
    }

    @Override
    public CheckoutBloodFatVO getMemberLatestBloodFat(String memberId) {
        CheckoutBloodFatVO checkoutBloodFatVO = new CheckoutBloodFatVO();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberId", memberId);

        //高密度脂蛋白
        paramMap.put("checkoutCode", "add_check_hdl");
        String result = this.checkoutMapper.getNewestCheckoutResult(paramMap);
        checkoutBloodFatVO.setHdl(result);

        //低密度脂蛋白
        paramMap.put("checkoutCode", "add_check_ldl");
        result = this.checkoutMapper.getNewestCheckoutResult(paramMap);
        checkoutBloodFatVO.setLdl(result);

        //甘油三酯
        paramMap.put("checkoutCode", "add_check_triglyceride");
        result = this.checkoutMapper.getNewestCheckoutResult(paramMap);
        checkoutBloodFatVO.setTriglyceride(result);

        //总胆固醇
        paramMap.put("checkoutCode", "add_check_tc");
        result = this.checkoutMapper.getNewestCheckoutResult(paramMap);
        checkoutBloodFatVO.setTc(result);
        return checkoutBloodFatVO;
    }

    @Override
    public PageResult<CheckoutPO> pagerMemberCheckoutWithNote(GetMemberCheckoutDTO dto, PageRequest pr) {
        String memberId = dto.getMemberId();
        Map<String, Object> param = new HashMap<>();
        param.put("memberId",memberId);
        param.put("recordOrigin",dto.getRecordOrigin());
        //查询患者所有一级检查
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<CheckoutPO> checkoutPOS = checkoutMapper.listCheckoutByParams(param);
        //所有非his来源的数据，不要显示检验科室和检验医师的内容
        checkoutPOS.forEach(e->{
            if (e.getRecordOrigin() != CheckoutConstant.RECORD_ORIGIN_HIS){
                e.setDepartmentName("");
                e.setCheckoutDoctorName("");
            }
        });
        return new PageResult(checkoutPOS);
    }

    @Override
    public PageResult<CheckoutDetailPO> pagerMemberCheckoutDetailByForeignId(DoctorSessionBO doctorSessionBO, String checkoutId,PageRequest pr) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<CheckoutDetailPO> checkoutDetailList = this.checkoutDetailMapper.listCheckoutDetail(checkoutId,null);
        return new PageResult(checkoutDetailList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addCheckout(AddCheckoutWithDetailDTO add) {
        String sid = DaoHelper.getSeq();
        CheckoutPO checkoutPO = new CheckoutPO();
        BeanUtils.copyProperties(checkoutPO ,add);
        checkoutPO.setSid(sid);
        checkoutPO.setCheckoutDate(add.getReportDate().substring(0 ,10));
        checkoutPO.setCheckoutTime(add.getReportDate().substring(11 ,19));
        this.checkoutMapper.addOrUpdateCheckout(checkoutPO);

        //非his同步过来的数据，需要自己处理异常标识
        RangeBO rangeBo = null;
        MemberControlTargetBO bo = null;
        List<KeyNoteModel> keyNotesModels = null;
        if(CheckoutConstant.RECORD_ORIGIN_HIS != add.getRecordOrigin()){
            rangeBo = this.memberService.getMemberRange(add.getMemberId());
            bo = this.memberService.getMemberDefaultControlTarget(add.getMemberId());
            ListHospitalAllKeyNoteDTO listHospitalAllKeyNoteDTO = new ListHospitalAllKeyNoteDTO();
            listHospitalAllKeyNoteDTO.setHospitalId(add.getHospitalId());
            keyNotesModels = this.doctorService.listHospitalAllKeyNotes(listHospitalAllKeyNoteDTO);
        }

        //添加子项目
        Map<String ,CheckoutDetailPO> detailMap = new HashMap<>();
        List<AddCheckoutDetailBO> detailList = add.getDetailList();
        CheckoutDetailPO checkoutDetailPO = null;
        List<CheckoutDetailPO> detailResultList = new ArrayList<>();
        for (AddCheckoutDetailBO detail : detailList) {
            String checkoutName = detail.getName();
            String checkoutResult = detail.getValue();
            String rangeStr = detail.getRange();
            String abnormalSign = detail.getAbnormalSign();
            String unit = detail.getUnit();
            if(CheckoutConstant.RECORD_ORIGIN_HIS != add.getRecordOrigin()){
                rangeStr = MemberRangeHelper.getCheckoutDetailRangeStr(checkoutName, keyNotesModels, rangeBo, bo);
                abnormalSign = MemberRangeHelper.getCheckoutAbnormalSign(checkoutName, checkoutResult, rangeStr);
                if (StringUtils.isBlank(unit)){
                    unit = getCheckoutDetailUnit(checkoutName, keyNotesModels);
                }
            }
            checkoutDetailPO = new CheckoutDetailPO();
            checkoutDetailPO.setReferenceRange(rangeStr);
            checkoutDetailPO.setCheckoutCode(detail.getCode());
            checkoutDetailPO.setCheckoutName(checkoutName);
            checkoutDetailPO.setCheckoutResult(checkoutResult);
            checkoutDetailPO.setAbnormalSign(abnormalSign);
            checkoutDetailPO.setResultUnit(detail.getUnit());
            checkoutDetailPO.setAcronym(detail.getAcronym());
            checkoutDetailPO.setCheckoutDatetime(add.getReportDate());
            checkoutDetailPO.setCheckoutId(add.getCheckoutId());
            checkoutDetailPO.setMemberId(add.getMemberId());
            checkoutDetailPO.setHospitalId(add.getHospitalId());
            checkoutDetailPO.setHospitalName(add.getHospitalName());
            checkoutDetailPO.setSid(DaoHelper.getSeq());
            checkoutDetailPO.setResultUnit(unit);
            detailResultList.add(checkoutDetailPO);
            detailMap.put(detail.getCode() ,checkoutDetailPO);
        }
        resolveCheckoutDetailCompare(detailMap ,add.getReportDate() ,add.getMemberId());
        this.checkoutDetailMapper.batchAddOrUpdateCheckoutDetail(detailResultList);
        Map<String, Object> map = resolveCheckoutRemind(detailResultList);
        resolveSyncMemberArchive(detailMap ,add.getMemberId());
        if(add.getSyncSign()){
            syncCheckoutToSign(detailMap ,add);
        }
        //就诊事件
        for (String checkoutCode : map.keySet()) {
            memberVisitEventHandler(add,checkoutCode,map.get(checkoutCode));
        }
        return sid;
    }

    private void memberVisitEventHandler(AddCheckoutWithDetailDTO add,String checkoutCode,Object reviewDt){
        String reviewDtStr = "暂无";
        String hospitalName = "未知";
        if (null != reviewDt){
            reviewDtStr = String.valueOf(reviewDt);
        }
        if (!StringUtils.isBlank(add.getHospitalName())){
            hospitalName = add.getHospitalName();
        }
        AddVistiEventDTO addVistiEventDTO = new AddVistiEventDTO();
        addVistiEventDTO.setMemberId(add.getMemberId());
        addVistiEventDTO.setHospitalName(hospitalName);
        addVistiEventDTO.setHospitalId(add.getHospitalId());
        addVistiEventDTO.setReCheckDt(reviewDtStr);
        addVistiEventDTO.setParamCode(checkoutCode);
        addVistiEventDTO.setParamName(CheckoutDict.getName(checkoutCode));
        addVistiEventDTO.setEventType(VisitEventEnum.MEMBER_TIME_CHECK_CHANGE.getEventType());
        visitEventService.addVisitEvent(addVistiEventDTO);
    }

    /**
     * 处理检验细项对比
     * @param detailMap
     */
    private void resolveCheckoutDetailCompare(Map<String ,CheckoutDetailPO> detailMap ,String reportDt ,String memberId){
        List<String> checkoutCodeList = detailMap.keySet().stream()
                .filter(x -> !x.equals(CheckoutDict.OTHER.getCode()))
                .collect(Collectors.toList());
        ListMemberNewestCheckoutDetailDTO listMemberNewestCheckoutDetailDTO = new ListMemberNewestCheckoutDetailDTO();
        listMemberNewestCheckoutDetailDTO.setMemberId(memberId);
        listMemberNewestCheckoutDetailDTO.setCheckoutCodeList(checkoutCodeList);
        listMemberNewestCheckoutDetailDTO.setEndCheckoutDt(reportDt);
        List<CheckoutDetailPO> list = this.checkoutDetailMapper.listMemberNewestCheckoutDetail(listMemberNewestCheckoutDetailDTO);
        CheckoutDetailPO checkoutDetail = null;
        for(CheckoutDetailPO detail : list){
            checkoutDetail = detailMap.get(detail.getCheckoutCode());
            if(checkoutDetail == null){
                continue;
            }
            String compareResult = null;
            try{
                double diff = Double.parseDouble(checkoutDetail.getCheckoutResult()) - Double.parseDouble(detail.getCheckoutResult());
                compareResult = new BigDecimal(diff).setScale(2 , RoundingMode.HALF_UP).toString();
            }catch (Exception e){}
            if(compareResult == null){
                compareResult = detail.getCheckoutResult();
            }
            checkoutDetail.setCheckoutResultLast(compareResult);
        }
    }

    /**
     * 处理检查提醒
     * @param detailList
     */
    private Map<String,Object> resolveCheckoutRemind(List<CheckoutDetailPO> detailList){
        List<ResolveCheckoutRemindDTO> checkoutList = new ArrayList<>();
        ResolveCheckoutRemindDTO resolveCheckoutRemindDTO = null;
        for(CheckoutDetailPO detail : detailList){
            resolveCheckoutRemindDTO = new ResolveCheckoutRemindDTO();
            resolveCheckoutRemindDTO.setMemberId(detail.getMemberId());
            resolveCheckoutRemindDTO.setCheckoutCode(detail.getCheckoutCode());
            resolveCheckoutRemindDTO.setValue(detail.getCheckoutResult());
            resolveCheckoutRemindDTO.setRecordDt(detail.getCheckoutDatetime());
            checkoutList.add(resolveCheckoutRemindDTO);
        }
        return this.checkRemindService.resolveCheckoutRemind(checkoutList);
    }

    /**
     * 获取患者检验项单位
     * @param checkoutName
     * @return
     */
    private String getCheckoutDetailUnit(String checkoutName, List<KeyNoteModel> keyNoteModels) {
        if (StringUtils.isBlank(checkoutName)) {
            return null;
        }
        if (keyNoteModels != null && keyNoteModels.size() > 0) {
            for (KeyNoteModel keyNoteModel : keyNoteModels) {
                String keyNoteName = keyNoteModel.getDisplayName();
                if (!StringUtils.isBlank(keyNoteName) &&
                        keyNoteName.equals(checkoutName)) {
                    return keyNoteModel.getUnit();
                }
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void resolveSyncMemberArchive(Map<String ,CheckoutDetailPO> detailMap ,String memberId){
        MemberArchivesPO memberArchives = this.memberService.getMemberArchives(memberId ,null);
        JSONObject archives = null;
        if(memberArchives != null && !StringUtils.isBlank(memberArchives.getArchivesJson())){
            archives = JSON.parseObject(memberArchives.getArchivesJson());
        }else{
            archives = new JSONObject();
        }
        CheckoutSyncArchiveTool.resolveArchiveJson(archives ,detailMap);
        MemberArchivesDTO memberArchivesDTO = new MemberArchivesDTO();
        memberArchivesDTO.setMemberId(memberId);
        memberArchivesDTO.setArchivesJson(archives.toJSONString());
        this.memberService.updateMemberArchive(memberArchivesDTO);
    }

    @Override
    public DataSyncTaskPO addDataSyncTask(DataSyncTaskPO dataSyncTaskPO) {
        DataSyncTaskPO getParam = new DataSyncTaskPO();
        getParam.setDoctorId(dataSyncTaskPO.getDoctorId());
        getParam.setStatus(CheckoutConstant.DATA_SYNC_TASK_IN);
        getParam.setTaskType(dataSyncTaskPO.getTaskType());
        getParam.setMemberId(dataSyncTaskPO.getMemberId());
        //进行中的任务
        List<DataSyncTaskPO> taskList = dataSyncTaskMapper.getListByModel(getParam);
        String sid = DaoHelper.getSeq();
        if (taskList.isEmpty()){
            dataSyncTaskPO.setSid(sid);
            dataSyncTaskMapper.insert(dataSyncTaskPO);
        }else {
            dataSyncTaskPO = taskList.get(0);
        }
        return dataSyncTaskPO;
    }

    @Override
    public DataSyncTaskPO getDataSyncTask(DataSyncTaskPO dataSyncTaskPO) {
        List<DataSyncTaskPO> listByModel = dataSyncTaskMapper.getListByModel(dataSyncTaskPO);
        DataSyncTaskPO result = null;
        if (!listByModel.isEmpty()){
            result = listByModel.get(0);
        }
        return result;
    }

    @Override
    public void updateDataSyncTask(DataSyncTaskPO dataSyncTaskPO) {
        dataSyncTaskMapper.update(dataSyncTaskPO);
    }

    private void syncCheckoutToSign(Map<String ,CheckoutDetailPO> detailMap ,AddCheckoutWithDetailDTO add){
        //糖化
        CheckoutDetailPO hba1c = detailMap.get(CheckoutDict.HBA.getCode());
        if(hba1c != null){
            try{
                Hba1cPO addHba1c = new Hba1cPO();
                addHba1c.setOrigin(SignConstant.ORIGIN_WEB_PROGRAM);
                addHba1c.setMemberId(add.getMemberId());
                addHba1c.setRecordValue(Float.parseFloat(hba1c.getCheckoutResult()));
                addHba1c.setOperatorId(add.getCheckoutDoctorId());
                addHba1c.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
                addHba1c.setRecordDt(add.getReportDate());
                this.hba1cService.addHba1cMapper(addHba1c,add.getHospitalId(),add.getHospitalName(),SignConstant.SYN_TO_CHECKOUT_NO);
            }catch (Exception e){
                log.error("糖化检验数据同步体征时异常,患者id:{} ,糖化数据:{}" ,add.getMemberId() ,JSON.toJSONString(hba1c));
            }
        }
    }

}
