package com.comvee.cdms.records.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.drugs.po.DrugsDepotPO;
import com.comvee.cdms.records.constant.DrugsRecordConstant;
import com.comvee.cdms.records.mapper.DrugsRecordMapper;
import com.comvee.cdms.records.model.dto.DrugsRecordDTO;
import com.comvee.cdms.records.model.po.DrugsRecordPO;
import com.comvee.cdms.records.model.vo.DrugsRecordVO;
import com.comvee.cdms.records.service.DrugsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DrugsRecordServiceImp implements DrugsRecordService {
    @Autowired
    private DrugsRecordMapper drugsRecordMapper;

    @Override
    public void saveDrugsRecords(List<DrugsRecordPO> records) {
        List<String> idList = new ArrayList<>();
        DrugsRecordDTO dto = new DrugsRecordDTO();
        for (DrugsRecordPO record : records) {
            if (!StringUtils.isBlank(record.getRecordMainId())) {
                dto.setRecordMainId(record.getRecordMainId());
            }
            saveDrugsRecord(record);
            idList.add(record.getSid());
        }
        if(!StringUtils.isBlank(dto.getRecordMainId())) {
            dto.setIdList(idList);
            drugsRecordMapper.deleteDrugsRecord(dto);
        }
    }

    @Override
    public void saveDrugsRecord(DrugsRecordPO record) {
        drugsHandler(record);
        if (StringUtils.isBlank(record.getSid())) {
            record.setSid(DaoHelper.getSeq());
            drugsRecordMapper.addDrugsRecord(record);
        } else {
            drugsRecordMapper.updateDrugsRecord(record);
        }
    }

    private void drugsHandler(DrugsRecordPO record) {
        if (!StringUtils.isBlank(record.getDrugId())) {
            record.setRecordType(DrugsRecordConstant.DRUG_TYPE_LIB_ITEM);
        } else {
            record.setRecordType(DrugsRecordConstant.DRUG_TYPE_USER_DEFINED);
            record.setDrugId("");
        }
        Date recordTime = DateHelper.getDate(record.getRecordTime(), DateHelper.DATETIME_FORMAT);
        record.setRecordDt(DateHelper.getDate(recordTime, DateHelper.DAY_FORMAT));
    }

    @Override
    public List<DrugsDepotPO> getDrugsByName(String drugName) {
        return drugsRecordMapper.getDrugsByName(drugName);
    }

    @Override
    public void deleteDrugsRecord(DrugsRecordDTO dto) {
        drugsRecordMapper.deleteDrugsRecord(dto);
    }

    @Override
    public List<DrugsRecordPO> listRecentDrugsRecord(DrugsRecordDTO dto) {
        return drugsRecordMapper.listRecentDrugsRecord(dto);
    }

    @Override
    public List<DrugsRecordVO> listDrugsRecord(DrugsRecordDTO dto) {
        List<DrugsRecordPO> dataList = drugsRecordMapper.listDrugsRecord(dto);
        Map<String, DrugsRecordVO> records = new HashMap<>();
        for (DrugsRecordPO po : dataList) {
            DrugsRecordVO vo;
            if (records.containsKey(po.getRecordMainId())) {
                vo = records.get(po.getRecordMainId());
            } else {
                vo = new DrugsRecordVO();
                BeanUtils.copyProperties(vo, po);
                vo.setParamTime(DateHelper.getDate(DateHelper.getDate(po.getRecordTime(), DateHelper.DATETIME_FORMAT), "HH:mm"));
                vo.setDataList(new ArrayList<>());
                records.put(po.getRecordMainId(), vo);
            }
            vo.getDataList().add(po);
        }
        return new ArrayList<>(records.values());
    }

    @Override
    public DrugsRecordPO drugsJsonHandler(JSONObject obj) {
        DrugsRecordPO po = new DrugsRecordPO();
        String drugName = obj.getString("drugName");
        if (StringUtils.isBlank(drugName)) {
            throw new BusinessException("drugName参数为空");
        }
        Integer drugType = obj.getInteger("drugType");
        if (drugType == null) {
            drugType = -1;
        }
        Double total = obj.getDouble("total");
        if (total == null) {
            throw new BusinessException("参数total为空");
        }
        String unit = obj.getString("unit");
        if (unit == null) {
            throw new BusinessException("参数unit为空");
        }
        po.setSid(obj.getString("sid"));
        po.setDrugName(drugName);
        po.setTotal(total);
        po.setUnit(unit);
        po.setDrugId(obj.getString("drugId"));
        po.setDrugType(drugType);
        return po;
    }

    @Override
    public JSONObject getDailyDrugInfo(DrugsRecordDTO dto){
        double usage = 0;
        List<DrugsRecordPO> records = drugsRecordMapper.listDrugsRecord(dto);
        Set<String> mainIds = new HashSet<>();
        for(DrugsRecordPO record: records){
            if(record.getRecordType() != null && record.getRecordType() == 4){
                if(record.getUnit().equalsIgnoreCase("u")){
                    usage += record.getTotal();
                }else {
                    mainIds.add(record.getRecordMainId());
                }
            }else {
                mainIds.add(record.getRecordMainId());
            }
        }
        JSONObject obj = new JSONObject();
        obj.put("insulinUsage", usage);
        obj.put("oralCount", mainIds.size());
        return obj;
    }
}
