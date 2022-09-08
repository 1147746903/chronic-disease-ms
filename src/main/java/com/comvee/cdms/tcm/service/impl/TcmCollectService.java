package com.comvee.cdms.tcm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.healthrecord.model.po.HealthRecordDetailPO;
import com.comvee.cdms.healthrecord.service.HealthRecordService;
import com.comvee.cdms.member.dto.MemberArchivesDTO;
import com.comvee.cdms.member.dto.UpdateMemberDTO;
import com.comvee.cdms.member.po.MemberArchivesPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.tcm.constant.TcmConstant;
import com.comvee.cdms.tcm.mapper.TcmCollectDataMapper;
import com.comvee.cdms.tcm.mapper.TcmCollectQueMapper;
import com.comvee.cdms.tcm.mapper.TcmCollectReportMapper;
import com.comvee.cdms.tcm.mapper.TcmCollectTaskMapper;
import com.comvee.cdms.tcm.model.dto.TcmCollectQueDTO;
import com.comvee.cdms.tcm.model.dto.TcmCollectTaskDTO;
import com.comvee.cdms.tcm.model.po.*;
import com.comvee.cdms.tcm.model.vo.TcmCollectQueVO;
import com.comvee.cdms.tcm.model.vo.TcmHealthElementVO;
import com.comvee.cdms.tcm.service.ITcmCollectService;
import com.comvee.cdms.tcm.utils.TcmCollectUtils;
import com.comvee.cdms.visit.constant.VisitEventEnum;
import com.comvee.cdms.visit.dto.AddVistiEventDTO;
import com.comvee.cdms.visit.service.VisitEventService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TcmCollectService implements ITcmCollectService {
    @Autowired
    private TcmCollectTaskMapper taskMapper;
    @Autowired
    private TcmCollectDataMapper dataMapper;
    @Autowired
    private TcmCollectReportMapper reportMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private TcmCollectQueMapper queMapper;
    @Autowired
    private VisitEventService visitEventService;
    @Autowired
    private HealthRecordService healthRecordService;

    @Override
    public JSONObject createTcmCollectTask(TcmCollectTaskPO po) {
        String sid = DaoHelper.getSeq();
        po.setSid(sid);
        MemberPO member = memberService.getMemberById(po.getMemberId());
        if (po.getTaskType() == null || (TcmConstant.TCM_COLLECT_TYPE_UN_START != po.getTaskType() && TcmConstant.TCM_COLLECT_TYPE_START != po.getTaskType())) {
            po.setTaskType(TcmConstant.TCM_COLLECT_TYPE_UN_START);
        }
        po.setMemberName(member.getMemberName());
        po.setSex(member.getSex());
        po.setMobilePhone(member.getMobilePhone());
        if (StringUtils.isBlank(po.getCreateDt())) {
            po.setCreateDt(DateHelper.getToday());
        }
        taskMapper.createTcmCollectTask(po);
        JSONObject obj = new JSONObject();
        obj.put("sid", sid);
        JSONObject detailPO = healthRecordService.getLastConstitution(member.getMemberId());
        obj.put("constitution", detailPO.get("constitution"));
        obj.put("constitutionName", detailPO.get("constitutionName"));
        return obj;
    }

    public JSONObject getLastConstitution(String memberId) {
        JSONObject obj = new JSONObject();
        JSONObject detailPO = healthRecordService.getLastConstitution(memberId);
        obj.put("constitution",detailPO.get("constitution"));
        obj.put("constitutionName",detailPO.get("constitutionName"));
        return obj;
    }

    @Override
    public void startTcmCollectTask(String sid) {
        TcmCollectTaskPO po = taskMapper.getTcmCollectTaskById(sid);
        if (po == null) {
            throw new BusinessException("采集单不存在");
        }
        if (TcmConstant.TCM_COLLECT_TYPE_UN_START == po.getTaskType()) {
            po.setTaskType(TcmConstant.TCM_COLLECT_TYPE_START);
            taskMapper.updateTcmCollectTask(po);
        } else {
            throw new BusinessException("采集单采集中或者已结束");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finishTcmCollectTask(String sid) {
        TcmCollectTaskPO po = taskMapper.getTcmCollectTaskById(sid);
        if (po == null) {
            throw new BusinessException("采集单不存在");
        }
        if (TcmConstant.TCM_COLLECT_TYPE_START == po.getTaskType()) {
            po.setTaskType(TcmConstant.TCM_COLLECT_TYPE_FINISH);
            po.setFinishDt(DateHelper.getNowDate());
            taskMapper.updateTcmCollectTask(po);
            analyzeCollectData(sid);
            TcmCollectDataPO data = dataMapper.getTcmCollectDataByTaskId(sid);
            if (data != null) {
                updateMemberInfo(data);
            }
        } else {
            throw new BusinessException("采集单未在采集中");
        }
    }

    private void updateMemberInfo(TcmCollectDataPO data) {
        MemberArchivesPO archives = memberService.getMemberArchives(data.getMemberId(), "-1");
        if(archives != null){
            JSONObject obj = null;
            if(!StringUtils.isBlank(archives.getArchivesJson())){
                obj = JSON.parseObject(archives.getArchivesJson());
            }else {
                obj = new JSONObject();
            }
            JSONObject basic = null;
            if(obj.containsKey("basic")){
                basic = obj.getJSONObject("basic");
            }else {
                basic = new JSONObject();
            }
            basic.put("pastHistoryRemark", data.getMedicalHistory());
            if(!StringUtils.isBlank(data.getMedicalHistory())){
                basic.put("user_past_history", "1");
            }else {
                basic.put("user_past_history", "0");
            }
            obj.put("basic", basic);
            MemberArchivesDTO dto = new MemberArchivesDTO();
            dto.setMemberId(data.getMemberId());
            dto.setArchivesJson(obj.toJSONString());
            memberService.updateMemberArchive(dto);
        }
        UpdateMemberDTO dto = new UpdateMemberDTO();
        dto.setMemberId(data.getMemberId());
        if (!StringUtils.isBlank(data.getHeight())) {
            dto.setHeight(data.getHeight());
        }
        if (!StringUtils.isBlank(data.getWeight())) {
            dto.setWeight(data.getWeight());
        }
        if (!StringUtils.isBlank(data.getBmi())) {
            dto.setBmi(data.getBmi());
        }
        memberService.updateMember(dto);
    }

    @Override
    public void deleteTcmCollectTask(String sid) {
        taskMapper.deleteTcmCollectTask(sid);
    }

    @Override
    public void savaTcmCollectData(TcmCollectDataPO data, Integer taskType, DoctorSessionBO webSession) {
        TcmCollectTaskPO task = taskMapper.getTcmCollectTaskById(data.getTaskId());
        if (task == null) {
            throw new BusinessException("采集单不存在");
        }
        if (TcmConstant.TCM_COLLECT_TYPE_UN_START != task.getTaskType() && TcmConstant.TCM_COLLECT_TYPE_START != task.getTaskType()) {
            throw new BusinessException("采集单不是采集中");
        }
        if (TcmConstant.TCM_COLLECT_TYPE_UN_START == task.getTaskType()) {
            startTcmCollectTask(task.getSid());
        }
        TcmCollectDataPO po = dataMapper.getTcmCollectDataByTaskId(data.getTaskId());
        data.setMemberId(task.getMemberId());
        if (po == null) {
            data.setSid(DaoHelper.getSeq());
            dataMapper.addTcmCollectData(data);
        } else {
            data.setSid(po.getSid());
            dataMapper.updateTcmCollectData(data);
        }
        if (TcmConstant.TCM_COLLECT_TYPE_FINISH == taskType) {
            finishTcmCollectTask(task.getSid());
            //就诊事件
            this.memberVisitEventHandler(webSession, data.getMemberId(),task.getSid());
        }
    }

    private void memberVisitEventHandler(DoctorSessionBO webSession, String memberId,String taskId) {
        AddVistiEventDTO addVistiEventDTO = new AddVistiEventDTO();
        addVistiEventDTO.setMemberId(memberId);
        addVistiEventDTO.setHospitalName(webSession.getHospitalName());
        addVistiEventDTO.setHospitalId(webSession.getHospitalId());
        addVistiEventDTO.setOperatorId(webSession.getDoctorId());
        addVistiEventDTO.setOperatorName(webSession.getDoctorName());
        addVistiEventDTO.setEventType(VisitEventEnum.MEMBER_TCM_ASSESS.getEventType());
        addVistiEventDTO.setForeignId(taskId);
        visitEventService.addVisitEvent(addVistiEventDTO);
    }

    @Override
    public PageResult<TcmCollectTaskPO> listTcmCollectTask(TcmCollectTaskDTO dto, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<TcmCollectTaskPO> taskPOList = taskMapper.listTcmCollectTask(dto);
        for (TcmCollectTaskPO tcmCollectTaskPO : taskPOList) {
            String mobileNumber = tcmCollectTaskPO.getMobilePhone();
            if (!StringUtils.isBlank(mobileNumber) && mobileNumber.length() >= 8){
                StringBuilder stringBuilder = new StringBuilder(mobileNumber);
                tcmCollectTaskPO.setMobilePhone(stringBuilder.replace(3,7,"****").toString());
            }
        }
        return new PageResult<>(taskPOList);
    }

    @Override
    public List<TcmCollectQuePO> listTcmCollectQue(TcmCollectQueDTO dto) {
        if (dto.getSex() == null) {
            MemberPO member = memberService.getMemberById(dto.getMemberId());
            if (member != null) {
                if (1 == member.getSex()) {
                    dto.setQueType(2);
                } else if (2 == member.getSex()) {
                    dto.setQueType(3);
                }
            }
        } else {
            dto.setQueType(dto.getSex() == 1 ? 2 : dto.getSex() == 2 ? 3 : null);
        }
        List<TcmCollectQueVO> queVOS = queMapper.listTcmCollectQue(dto);
        Map<String, Integer> index = new HashMap<>();
        Map<Integer, TcmCollectQuePO> map = new TreeMap<>();
        for (TcmCollectQueVO vo : queVOS) {
            TcmCollectQuePO po = null;
            if (index.containsKey(vo.getQueId())) {
                po = map.get(index.get(vo.getQueId()));
            } else {
                po = new TcmCollectQuePO();
                po.setSid(vo.getQueId());
                po.setQueIndex(vo.getQueIndex());
                po.setQueTopic(vo.getQueTopic());
                po.setQueType(vo.getQueType());
                po.setAnsType(vo.getAnsType());
                po.setAns(new ArrayList<>());
                map.put(vo.getQueIndex(), po);
                index.put(vo.getQueId(), vo.getQueIndex());
            }
            TcmCollectQueAnsPO ans = new TcmCollectQueAnsPO();
            ans.setAnsDesc(vo.getAnsDesc());
            ans.setQueId(vo.getQueId());
            ans.setSid(vo.getAnsId());
            ans.setAnsIndex(vo.getAnsIndex());
            po.getAns().add(ans);
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public TcmCollectDataPO getTcmCollectDataByTaskId(String taskId) {
        return dataMapper.getTcmCollectDataByTaskId(taskId);
    }

    @Override
    public TcmCollectDataPO getLastTcmCollectData(String memberId) {
        return dataMapper.getLastTcmCollectData(memberId);
    }

    private void analyzeCollectData(String taskId) {
        TcmCollectDataPO data = dataMapper.getTcmCollectDataByTaskId(taskId);
        TcmCollectTaskPO task = taskMapper.getTcmCollectTaskById(taskId);
        TcmHealthElementVO elementVO = new TcmHealthElementVO();
        TcmCollectUtils.calculateFeature(elementVO, data.getFeature());
        TcmCollectUtils.calculateCoatingColor(elementVO, data.getCoatingColor());
        TcmCollectUtils.calculateTongueColor(elementVO, data.getTongueColor());
        TcmCollectUtils.calculateTongueFur(elementVO, data.getTongueFur());
        TcmCollectUtils.calculateTongueBody(elementVO, data.getTongueBody());
        TcmCollectUtils.calculateTongueCondition(elementVO, data.getTongueCondition());
        MemberPO member = memberService.getMemberById(data.getMemberId());
        TcmCollectQueDTO queDTO = new TcmCollectQueDTO();
        queDTO.setSex(member.getSex());
        HashMap<String, TcmCollectQuePO> queMap = new HashMap<>();
        for (TcmCollectQuePO po : listTcmCollectQue(queDTO)) {
            queMap.put(po.getSid(), po);
        }
        HashMap<String, String> ans = convertTcmCollectQueAns(data.getQuestionnaireJson());
        TcmCollectUtils.calculateQue(elementVO, ans);
        String finishDt = task.getFinishDt();
        if (StringUtils.isBlank(finishDt)) {
            finishDt = DateHelper.getNowDate();
        }
        TcmCollectUtils.calculateFinishDt(elementVO, finishDt);
        TcmCollectReportPO po = new TcmCollectReportPO();
        po.setSid(DaoHelper.getSeq());
        po.setTaskId(data.getTaskId());
        double bmi = 0;
        try {
            bmi = Double.parseDouble(data.getBmi());
        } catch (Exception ignored) {
        }
        TcmCollectUtils.calculateBmi(elementVO, bmi);
        po.setElementObj(elementVO);
        po.setElementJson(JSON.toJSONString(elementVO));
        po.setFigureDesc(TcmCollectUtils.getFigureDescription(bmi));
        po.setConstitutionDesc(TcmCollectUtils.getTcmConstitutionAssess(data.getConstitution()));
        String diseaseRisk = TcmCollectUtils.calculateRisk(member.getAge(), bmi, data.getSex(), member.getIsDiabetes(), queMap, ans);
        po.setDiseaseRisk(diseaseRisk);
        po.setFeatureDesc(TcmCollectUtils.getTcmFeatureDesc(data.getFeature()));
        JSONObject birthObj = TcmCollectUtils.getWuYunLiuQi(data.getBirthday(), true);
        po.setBirthObj(birthObj);
        po.setBirthFortuneJson(birthObj.toJSONString());
        JSONObject currentObj = TcmCollectUtils.getWuYunLiuQi(DateHelper.getDate(DateHelper.getDate(finishDt, DateHelper.DATETIME_FORMAT), DateHelper.DAY_FORMAT), false);
        po.setCurrentObj(currentObj);
        po.setCurFortuneJson(currentObj.toJSONString());
        List<String> diseaseList = TcmCollectUtils.listDiseases(elementVO);
        List<String> zhDiseaseList = new ArrayList<>();
        StringBuilder healthStatus = new StringBuilder();
        JSONArray elementArray = new JSONArray();
        for (String dis : diseaseList) {
            if (healthStatus.length() > 0) {
                healthStatus.append(",");
            }
            JSONObject h = new JSONObject();
            String zhDis = TcmCollectUtils.convertZhDisease(dis);
            zhDiseaseList.add(zhDis);
            healthStatus.append(zhDis);
            h.put("element", zhDis);
            h.put("desc", TcmCollectUtils.getHealthElementDesc(dis));
            elementArray.add(h);
        }
        po.setHealthStatus(healthStatus.toString());
        List<String> proDiseaseList = TcmCollectUtils.getComingDisease(elementVO);
        StringBuilder comingDisease = new StringBuilder();
        for (String dis : proDiseaseList) {
            if (comingDisease.length() > 0) {
                comingDisease.append(",");
            }
            String zhDis = TcmCollectUtils.convertZhDisease(dis);
            comingDisease.append(zhDis);
        }
        po.setComingDisease(comingDisease.toString());
        po.setHealthList(elementArray);
        po.setHealthJson(JSON.toJSONString(elementArray));
        po.setTeaNurse(TcmCollectUtils.getTeaNurse(zhDiseaseList));
        po.setPorridgeNurse(TcmCollectUtils.getPorridgeNurse(zhDiseaseList));
        po.setDietNurse(TcmCollectUtils.getDietNurse(zhDiseaseList));
        po.setDrinkNurse(TcmCollectUtils.getDrinkNurse(zhDiseaseList));
        po.setSportNurse(TcmCollectUtils.getSportNurse(zhDiseaseList));
        reportMapper.addTcmCollectReport(po);
    }

    private HashMap<String, String> convertTcmCollectQueAns(String json) {
        HashMap<String, String> map = new HashMap<>();
        if (!StringUtils.isBlank(json)) {
            JSONArray array = JSON.parseArray(json);
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = array.getJSONObject(i);
                map.put(obj.getString("que_sid"), obj.getString("ans_sid_list"));
            }
        }
        return map;
    }

    @Override
    public TcmCollectReportPO getTcmCollectReport(String taskId) {
        TcmCollectReportPO po = reportMapper.getTcmCollectReport(taskId);
        TcmCollectTaskPO task = taskMapper.getTcmCollectTaskById(taskId);
        if (po != null) {
            po.setFinishDt(task.getFinishDt());
            if (!StringUtils.isBlank(po.getElementJson())) {
                po.setElementObj(JSON.parseObject(po.getElementJson(), TcmHealthElementVO.class));
            }
            if (!StringUtils.isBlank(po.getHealthJson())) {
                po.setHealthList(JSON.parseArray(po.getHealthJson()));
            }
            if (!StringUtils.isBlank(po.getBirthFortuneJson())) {
                po.setBirthObj(JSON.parseObject(po.getBirthFortuneJson()));
            }
            if (!StringUtils.isBlank(po.getBirthFortuneJson())) {
                po.setCurrentObj(JSON.parseObject(po.getCurFortuneJson()));
            }
        }
        return po;
    }
}
