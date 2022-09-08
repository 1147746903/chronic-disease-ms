package com.comvee.cdms.healthrecord.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.healthrecord.mapper.HealthRecordMapper;
import com.comvee.cdms.healthrecord.model.dto.CountHealthRecordDTO;
import com.comvee.cdms.healthrecord.model.dto.CountHealthRecordDistinctDTO;
import com.comvee.cdms.healthrecord.model.dto.ListHealthRecordDTO;
import com.comvee.cdms.healthrecord.model.po.HealthRecordDetailPO;
import com.comvee.cdms.healthrecord.model.po.HealthRecordPO;
import com.comvee.cdms.healthrecord.model.vo.HealthRecordStatsVO;
import com.comvee.cdms.healthrecord.model.vo.HealthRecordWithMemberVO;
import com.comvee.cdms.healthrecord.service.HealthRecordService;
import com.comvee.cdms.member.constant.DiabetesConstant;
import com.comvee.cdms.tcm.utils.TcmCollectUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HealthRecordServiceImpl implements HealthRecordService {

    @Autowired
    private HealthRecordMapper healthRecordMapper;

    @Override
    public PageResult<HealthRecordWithMemberVO> listHealthRecord(ListHealthRecordDTO dto, PageRequest pr) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<HealthRecordWithMemberVO> list = this.healthRecordMapper.listHealthRecord(dto);
        healthRecordListDesensitization(list);
        return new PageResult(list);
    }

    @Override
    public HealthRecordStatsVO getHealthRecordStats(String hospitalId) {
        CountHealthRecordDTO dto = new CountHealthRecordDTO();
        dto.setHospitalId(hospitalId);
        Long totalCheckup = this.healthRecordMapper.countHealthRecord(dto);

        dto.setCheckupDate(DateHelper.getToday());
        Long todayCheckup = this.healthRecordMapper.countHealthRecord(dto);

        CountHealthRecordDistinctDTO distinctDTO = new CountHealthRecordDistinctDTO();
        distinctDTO.setHospitalId(hospitalId);
        Long totalPeople = this.healthRecordMapper.countHealthRecordDistinctPeople(distinctDTO);

        distinctDTO.setIsDiabetes(DiabetesConstant.DIABETES_YES);
        Long diabetesPeople = this.healthRecordMapper.countHealthRecordDistinctPeople(distinctDTO);

        distinctDTO.setIsDiabetes(null);
        distinctDTO.setEssentialHyp("1");
        Long hypertensionPeople = this.healthRecordMapper.countHealthRecordDistinctPeople(distinctDTO);

        HealthRecordStatsVO result = new HealthRecordStatsVO();
        result.setTodayCheckup(todayCheckup);
        result.setTotalCheckup(totalCheckup);
        result.setTotalPeople(totalPeople);
        result.setDiabetesPeople(diabetesPeople);
        result.setHypertensionPeople(hypertensionPeople);
        return result;
    }

    @Override
    public Map<String, Object> getHealthRecordDetail(String id) {
        List<HealthRecordDetailPO> detailList = this.healthRecordMapper.listHealthRecordDetail(id);
        Map<String, Object> resultMap = detailList.stream().collect(Collectors.toMap(
                HealthRecordDetailPO::getCheckupItem
                ,val -> JSONObject.parseObject(val.getCheckupResult())
                ,(a ,b) -> a));
        return resultMap;
    }

    /**
     * 列表脱敏
     * @param list
     */
    private void healthRecordListDesensitization(List<HealthRecordWithMemberVO> list){
        if(list == null || list.isEmpty()){
            return;
        }
        for(HealthRecordWithMemberVO vo : list){
            vo.setMobilePhone(ValidateTool.tuoMin(vo.getMobilePhone(),3,4,"*"));
        }
    }

    @Override
    public JSONObject getLastConstitution(String memberId) {
        HealthRecordDetailPO detailPO = healthRecordMapper.getLastConstitutionHealthRecordDetail(memberId);
        JSONObject obj = new JSONObject();
        if(detailPO != null && !StringUtils.isBlank(detailPO.getCheckupResult())){
            try {
                JSONObject result = JSON.parseObject(detailPO.getCheckupResult());
                if(result != null && result.containsKey("constitution")){
                    String constitutionStr = resolveHealthReportConstitution(result.getString("constitution"));
                    int constitution = TcmCollectUtils.convertConstitutionCode(constitutionStr);
                    obj.put("constitution", "" + constitution);
                    if(constitution != -1){
                        obj.put("constitutionName", constitutionStr);
                    }else {
                        obj.put("constitutionName", "");
                    }
                }
            }catch (Exception ignored){
            }
        }
        if(!obj.containsKey("constitution")){
            obj.put("constitution", "-1");
            obj.put("constitutionName", "");
        }
        return obj;
    }

    @Override
    public Boolean updateHealthRecord(HealthRecordPO healthRecordPO) {
        HealthRecordPO getHealthRecord = new HealthRecordPO();
        getHealthRecord.setSid(healthRecordPO.getSid());
        HealthRecordPO healthRecord = healthRecordMapper.getHealthRecord(getHealthRecord);
        if (null == healthRecord){
            throw new BusinessException("该健康记录不存在");
        }
        return healthRecordMapper.updateHealthRecord(healthRecordPO)>0;
    }

    private String resolveHealthReportConstitution(String constitution){
        if(StringUtils.isBlank(constitution)){
            return null;
        }
        int index = constitution.indexOf("质");
        if(index < 0){
            return constitution;
        }
        return constitution.substring(0 ,index + 1);
    }

}
