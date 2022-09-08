package com.comvee.cdms.records.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.dybloodsugar.po.DyRememberSportPO;
import com.comvee.cdms.records.constant.SportRecordConstant;
import com.comvee.cdms.records.mapper.SportRecordMapper;
import com.comvee.cdms.records.model.dto.SportRecordDTO;
import com.comvee.cdms.records.model.po.SportPO;
import com.comvee.cdms.records.model.po.SportRecordPO;
import com.comvee.cdms.records.service.SportRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SportRecordServiceImpl implements SportRecordService {
    @Autowired
    private SportRecordMapper sportRecordMapper;

    @Override
    public List<SportPO> listSport(SportRecordDTO sportRecordDTO){
        return sportRecordMapper.listSport(sportRecordDTO);
    }

    @Override
    public void deleteSportRecordById(String sid){
        SportRecordDTO dto = new SportRecordDTO();
        dto.setRecordMainId(sid);
        sportRecordMapper.deleteSportRecord(dto);
    }

    @Override
    public void saveSportRecord(SportRecordPO sportRecordPO){
        sportHandler(sportRecordPO);
        if(StringUtils.isBlank(sportRecordPO.getSid())){
            sportRecordPO.setSid(DaoHelper.getSeq());
            sportRecordMapper.addSportRecord(sportRecordPO);
        }else {
            sportRecordMapper.updateSportRecord(sportRecordPO);
        }
    }

    private void sportHandler(SportRecordPO sportRecordPO){
        if(sportRecordMapper.getSportById(sportRecordPO.getSportId()) != null){
            sportRecordPO.setSportType(SportRecordConstant.SPORT_TYPE_LIB_ITEM);
        }else {
            sportRecordPO.setSportType(SportRecordConstant.SPORT_TYPE_USER_DEFINED);
            sportRecordPO.setSportId("");
        }
        Date recordTime = DateHelper.getDate(sportRecordPO.getRecordTime(), DateHelper.DATETIME_FORMAT);
        sportRecordPO.setRecordDt(DateHelper.getDate(recordTime, DateHelper.DAY_FORMAT));
    }

    @Override
    public void saveSportRecords(List<SportRecordPO> sports) {
        List<String> idList = new ArrayList<>();
        SportRecordDTO dto = new SportRecordDTO();
        for(SportRecordPO sport: sports){
            dto.setRecordMainId(sport.getRecordMainId());
            saveSportRecord(sport);
            idList.add(sport.getSid());
        }
        if(!StringUtils.isBlank(dto.getRecordMainId())){
            dto.setIdList(idList);
            sportRecordMapper.deleteSportRecord(dto);
        }
    }

    @Override
    public List<SportRecordPO> listRecentSportRecord(SportRecordDTO sportRecordDTO){
        return sportRecordMapper.listRecentSportRecord(sportRecordDTO);
    }

    @Override
    public List<SportRecordPO> listSportRecord(SportRecordDTO sportRecordDTO) {
        return sportRecordMapper.listSportRecord(sportRecordDTO);
    }

    @Override
    public List<DyRememberSportPO> listSportRecordForDyBlood(SportRecordDTO dto) {
        Map<String, DyRememberSportPO> recordMap = new HashMap<>();
        Map<String, List<JSONObject>> jsonMap = new HashMap<>();
        List<SportRecordPO> records = sportRecordMapper.listSportRecord(dto);
        for (SportRecordPO record : records){
            DyRememberSportPO sportPO;
            List<JSONObject> jsonList;
            String mainId = record.getRecordMainId();
            Date recordTime = DateHelper.getDate(record.getRecordTime(), DateHelper.DATETIME_FORMAT);
            Date endTime = DateHelper.getDate(record.getSportEndTime(), DateHelper.DATETIME_FORMAT);
            if(recordTime == null){
                recordTime = new Date();
            }
            if(endTime == null){
                endTime = new Date();
            }
            if(recordMap.containsKey(mainId)){
                sportPO = recordMap.get(mainId);
                jsonList = jsonMap.get(mainId);
            }else {
                jsonList = new ArrayList<>();
                sportPO = new DyRememberSportPO();
                sportPO.setSid(mainId);
                sportPO.setRecordDt(record.getRecordTime());
                sportPO.setMemberId(record.getMemberId());
                sportPO.setSportStartDt(DateHelper.getDate(recordTime, "HH:mm"));
                sportPO.setSportEndDt(DateHelper.getDate(endTime, "HH:mm"));
                sportPO.setOperationId(record.getOperationId());
                sportPO.setOperationType(record.getOperationType());
                recordMap.put(mainId, sportPO);
                jsonMap.put(mainId, jsonList);
            }
            JSONObject sport = new JSONObject();
            sport.put("sid", record.getSid());
            sport.put("sportId", record.getSportId());
            sport.put("sportMethod", record.getSportName());
            sport.put("sportState", record.getSportState());
            sport.put("total", record.getTotal());
            sport.put("intensity", record.getIntensity());
            jsonList.add(sport);
            JSONObject obj = new JSONObject();
            obj.put("sportMethodBOList", jsonList);
            sportPO.setSportMethodJson(JSON.toJSONString(obj));
        }
        return new ArrayList<>(recordMap.values());
    }


    @Override
    public JSONObject getDailySportInfo(SportRecordDTO dto, String weight){
        double w = 0;
        try{
            w = Double.parseDouble(weight);
        }catch (Exception ignored){
        }
        List<SportRecordPO> records = listSportRecord(dto);
        double total = 0;//消耗卡路里
        if(w > 0) {
            for (SportRecordPO po : records) {
                double met = 0;//代谢当量 不同运动强度默认值：低 3 中 4 高 6
                SportPO sportPO = sportRecordMapper.getSportById(po.getSportId());
                if (sportPO != null) {
                    met = sportPO.getMet();
                } else {
                    switch (po.getIntensity()) {
                        case 1:
                            met = SportRecordConstant.DEFEAT_LOW_MET;
                            break;
                        case 2:
                            met = SportRecordConstant.DEFEAT_MIDDLE_MET;
                            break;
                        case 3:
                            met = SportRecordConstant.DEFEAT_HIGH_MET;
                            break;
                    }
                }
                //消耗卡路里 = 代谢当量（静息坐位时的代谢水平的比值） * 静息坐位时的代谢水平（0.0167千卡/千克/分钟） * 体重 * 运动时间(分钟)
                total += met * SportRecordConstant.MET * w * po.getTotal();
            }
        }
        JSONObject obj = new JSONObject();
        obj.put("costHeat", Math.round(total*100)/100);
        return obj;
    }
}
