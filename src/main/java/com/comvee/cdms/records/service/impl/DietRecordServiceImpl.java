package com.comvee.cdms.records.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.dybloodsugar.po.DyRememberDietPO;
import com.comvee.cdms.dybloodsugar.po.DyRememberFoodPO;
import com.comvee.cdms.records.constant.DietRecordConstant;
import com.comvee.cdms.records.mapper.DietRecordMapper;
import com.comvee.cdms.records.model.dto.DietRecordDTO;
import com.comvee.cdms.records.model.po.DietRecordPO;
import com.comvee.cdms.records.service.DietRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DietRecordServiceImpl implements DietRecordService {
    @Autowired
    private DietRecordMapper dietRecordMapper;

    @Override
    public void addDietRecord(DietRecordPO dietRecordPO){
        dietRecordPO.setSid(DaoHelper.getSeq());
        dietRecordMapper.addDietRecord(dietRecordPO);
    }

    @Override
    public void saveDietRecords(List<DietRecordPO> recordList) {
        List<String> idList = new ArrayList<>();
        DietRecordDTO dietRecordDTO = new DietRecordDTO();
        for(DietRecordPO dietRecordPO : recordList){
            recordHandler(dietRecordPO);
            if(StringUtils.isBlank(dietRecordPO.getSid())){
                addDietRecord(dietRecordPO);
            }else {
                modifyDietRecord(dietRecordPO);
            }
            if(idList.size() == 0){
                dietRecordDTO.setRecordMainId(dietRecordPO.getRecordMainId());
            }
            idList.add(dietRecordPO.getSid());
        }
        dietRecordDTO.setIdList(idList);
        dietRecordMapper.deleteDietRecord(dietRecordDTO);
    }

    @Override
    public void modifyDietRecord(DietRecordPO dietRecordPO){
        dietRecordMapper.modifyDietRecord(dietRecordPO);
    }

    private void recordHandler(DietRecordPO dietRecordPO){
        DyRememberFoodPO foodPO = dietRecordMapper.getFoodById(dietRecordPO.getFoodId());
        if(foodPO != null){
            dietRecordPO.setFoodType(DietRecordConstant.FOOD_TYPE_LIB_ITEM);
            dietRecordPO.setFoodName(foodPO.getName());
            double heat = 0;
            try{
                heat = Double.parseDouble(foodPO.getHeat());
                heat = heat * dietRecordPO.getTotal();
            }catch (Exception ignored){}
            dietRecordPO.setHeat(heat);
            dietRecordPO.setUnit(foodPO.getUnit());
        }else {
            dietRecordPO.setFoodType(DietRecordConstant.FOOD_TYPE_USER_DEFINED);
            dietRecordPO.setFoodId("");
        }
        if(StringUtils.isBlank(dietRecordPO.getFoodName())){
            dietRecordPO.setFoodName("");
        }
        if(StringUtils.isBlank(dietRecordPO.getFoodPic())){
            dietRecordPO.setFoodPic("");
        }
        if(dietRecordPO.getHeat() == null) {
            dietRecordPO.setHeat(0.0);
        }
        if(StringUtils.isBlank(dietRecordPO.getUnit())){
            dietRecordPO.setUnit("g");
        }
        if(dietRecordPO.getTotal() == null){
            dietRecordPO.setTotal(0.0);
        }
        Date recordTime = DateHelper.getDate(dietRecordPO.getRecordTime(), DateHelper.DATETIME_FORMAT);
        dietRecordPO.setRecordDt(DateHelper.getDate(recordTime, DateHelper.DAY_FORMAT));
    }

    @Override
    public boolean hasFoodItem(String sid){
       return dietRecordMapper.getFoodById(sid) != null;
    }

    @Override
    public DietRecordPO getDietRecordById(String sid){
        return dietRecordMapper.getDietRecordById(sid);
    }

    @Override
    public List<DyRememberDietPO> listDietRecordByDayForMiniProgram(DietRecordDTO dietRecordDTO){
        Map<String, DyRememberDietPO> recordMap = new HashMap<>();
        Map<String, List<JSONObject>> jsonMap = new HashMap<>();
        List<DietRecordPO> records = dietRecordMapper.listDietRecord(dietRecordDTO);
        for (DietRecordPO record : records){
            String mainId = record.getRecordMainId();
            DyRememberDietPO dietPO;
            List<JSONObject> jsonList;
            if(recordMap.containsKey(mainId)){
                dietPO = recordMap.get(mainId);
                jsonList = jsonMap.get(mainId);
            }else {
                dietPO = castDietRecordPOToOld(record);
                dietPO.setSid(mainId);
                recordMap.put(mainId, dietPO);
                jsonList = new ArrayList<>();
                jsonMap.put(mainId, jsonList);
            }
            JSONObject food = new JSONObject();
            food.put("sid", record.getSid());
            food.put("id", record.getFoodId());
            food.put("name", record.getFoodName());
            food.put("unit", record.getUnit());
            food.put("total", record.getTotal());
            food.put("totalHeat", record.getHeat());
            if(!StringUtils.isBlank(record.getFoodId())){
                DyRememberFoodPO foodPO = dietRecordMapper.getFoodById(record.getFoodId());
                food.put("heat", foodPO.getHeat());
            }
            jsonList.add(food);
            dietPO.setParamIngredientJson(JSON.toJSONString(jsonList));
            dietPO.setContent(dietPO.getParamIngredientJson());
        }
        return new ArrayList<>(recordMap.values());
    }

    private DyRememberDietPO castDietRecordPOToOld(DietRecordPO record) {

        DyRememberDietPO dietPO = new DyRememberDietPO();
        dietPO.setMemberId(record.getMemberId());
        dietPO.setRecordDt(record.getRecordTime());
        dietPO.setParamDt(DateHelper.getDate(DateHelper.getDate(record.getRecordTime(), DateHelper.DATETIME_FORMAT), "HH:mm"));
        dietPO.setParamCode(record.getTimeType().toString());
        dietPO.setInsertDt(record.getInsertDt());
        dietPO.setModifyDt(record.getModifyDt());
        dietPO.setOperationType(record.getOperationType());
        return dietPO;
    }

    @Override
    public List<DyRememberDietPO> listDietRecordByDayForDyBlood(DietRecordDTO dietRecordDTO){
        Map<String, DyRememberDietPO> recordMap = new HashMap<>();
        Map<String, List<JSONObject>> jsonMap = new HashMap<>();
        Map<String, List<JSONObject>> otherMap = new HashMap<>();
        List<DietRecordPO> records = dietRecordMapper.listDietRecord(dietRecordDTO);
        for (DietRecordPO record : records){
            DyRememberDietPO dietPO;
            List<JSONObject> jsonList;
            List<JSONObject> otherList;
            String mainId = record.getRecordMainId();
            if(recordMap.containsKey(mainId)){
                dietPO = recordMap.get(mainId);
                jsonList = jsonMap.get(mainId);
                otherList = otherMap.get(mainId);
            }else {
                jsonList = new ArrayList<>();
                dietPO = castDietRecordPOToOld(record);
                dietPO.setSid(mainId);
                dietPO.setOperationType(DietRecordConstant.OPERATION_TYPE_MEDIC);
                recordMap.put(mainId, dietPO);
                jsonMap.put(mainId, jsonList);
                otherList = new ArrayList<>();
                otherMap.put(mainId, otherList);
            }
            JSONObject food = new JSONObject();
            food.put("sid", record.getSid());
            food.put("multipleFoodId", record.getFoodId());
            food.put("multipleFood", record.getFoodName());
            food.put("unit", record.getUnit());
            food.put("total", record.getTotal());
            if(record.getFoodType() == DietRecordConstant.FOOD_TYPE_LIB_ITEM) {
                jsonList.add(food);
            }else {
                otherList.add(food);
            }
            JSONObject obj = new JSONObject();
            obj.put("dietFoodList", jsonList);
            obj.put("otherFood", otherList);
            dietPO.setParamIngredientJson(JSON.toJSONString(obj));
        }
        return new ArrayList<>(recordMap.values());
    }

    @Override
    public List<DietRecordPO> listDietRecord(DietRecordDTO dietRecordDTO){
        return dietRecordMapper.listDietRecord(dietRecordDTO);
    }

    @Override
    public void deleteDietRecordByMainId(String recordMainId){
        DietRecordDTO dto = new DietRecordDTO();
        dto.setRecordMainId(recordMainId);
        dietRecordMapper.deleteDietRecord(dto);
    }

    @Override
    public JSONObject getDailyDietInfo(DietRecordDTO dto){
        List<DietRecordPO> records = listDietRecord(dto);
        double heat = 0;
        for(DietRecordPO po : records){
            if(po.getHeat() != null) {
                heat += po.getHeat();
            }
        }
        JSONObject obj = new JSONObject();
        obj.put("totalHeat", heat);
        return obj;
    }

    @Override
    public String getMainId(DietRecordDTO dto){
        return dietRecordMapper.getMainId(dto);
    }
}
