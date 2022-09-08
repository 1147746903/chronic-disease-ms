package com.comvee.cdms.dybloodsugar.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dybloodpressure.constant.DyBloodPressureConstant;
import com.comvee.cdms.dybloodpressure.dto.AddDyBloodPressureDiaryDTO;
import com.comvee.cdms.dybloodpressure.service.DyBloodPressureService;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.dto.*;
import com.comvee.cdms.dybloodsugar.mapper.*;
import com.comvee.cdms.dybloodsugar.po.*;
import com.comvee.cdms.dybloodsugar.service.DyRememberService;
import com.comvee.cdms.dybloodsugar.tool.DishIdentifyTool;
import com.comvee.cdms.upload.tool.UploadUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("DyRememberService")
public class DyRememberServiceImpl implements DyRememberService {
    private static Logger log = LoggerFactory.getLogger(DyRememberServiceImpl.class);

    @Autowired
    private DyRememberDietMapper dyRememberDietMapper;
    @Autowired
    private DyRememberSportMapper dyRememberSportMapper;
    @Autowired
    private DyRememberPharmacyMapper dyRememberPharmacyMapper;
    @Autowired
    private DyRememberSleepMapper dyRememberSleepMapper;
    @Autowired
    private DyBloodSugarRemarkMapper dyBloodSugarRemarkMapper;
    @Autowired
    private DyBloodPressureService dyBloodPressureService;

    @Override
    public List<DyRememberSportPO> getDySportRememberPOList(DyRememberSportDTO dyRememberSportDTO) {
        List<DyRememberSportPO> dySportRememberPOList = this.dyRememberSportMapper.getDySportRememberPOList(dyRememberSportDTO);
        return dySportRememberPOList;
    }

    @Override
    public List<DyRememberDietPO> getDyDietRememberPOList(DyRememberDietDTO dyRememberDietDTO) {
        List<DyRememberDietPO>  dyRememberDietPOList = this.dyRememberDietMapper.listDyDietRememberPO(dyRememberDietDTO);

        return dyRememberDietPOList;
    }

    @Override
    public List<DyRememberDietPO> getDyDietRememberPOListForWX(DyRememberDietDTO dto) {
        List<DyRememberDietPO> dyRememberDietPOList = this.dyRememberDietMapper.listDyDietRememberPOForWX(dto);
        return dyRememberDietPOList;
    }


    @Override
    public List<DyRememberDietPO> getDyDietRememberPOListForWXv2(DyRememberDietDTO dto) {
        List<DyRememberDietPO> dyRememberDietPOList = this.dyRememberDietMapper.listDyDietRememberPOForWXv2(dto);
        return dyRememberDietPOList;
    }

    @Override
    public List<String> listDyDietRememberDtForWX(DyRememberDietDTO dto){
        return dyRememberDietMapper.listDyDietRememberDtForWX(dto);
    }

    @Override
    public List<String> listDySportRememberDtForWX(DyRememberSportDTO dto){
        return dyRememberSportMapper.listDySportRememberDtForWX(dto);
    }

    @Override
    public List<DyRememberDietPO> getDyDietRememberPOListForHandleData(DyRememberDietDTO dto) {
        List<DyRememberDietPO> handleDataList = this.dyRememberDietMapper.listDyDietRememberPOForHandleData(dto);
        return handleDataList;
    }

    @Override
    public DyRememberDietPO getDyDietRememberValues(String sid) {
        DyRememberDietPO dyRememberDietPO = this.dyRememberDietMapper.getDyDietRememberValues(sid);
        return dyRememberDietPO;
    }

    @Override
    public void setDietRemember(DyRememberDietDTO dto,String id) {
        DyRememberDietPO dyDietRemember = null;
        if (id != null && id != ""){
             dyDietRemember = this.dyRememberDietMapper.getDyDietRememberValues(id);
        }else if (DynamicBloodSugarConstant.OTHER_MEAL.equals(dto.getParamCode())){
            //如果是4
            dyDietRemember = this.dyRememberDietMapper.getDyDietRememberOne(dto);
        }else {
            //查询这个患者当天,餐时(早,中,晚,其他),用餐时间,数据来源(1:患者,2医生),有效的数据.
            dyDietRemember = this.dyRememberDietMapper.getDyDietRemember(dto);
        }
        if (dyDietRemember == null && !DynamicBloodSugarConstant.OTHER_MEAL.equals(dto.getParamCode())){
            dyDietRemember = new DyRememberDietPO();
            BeanUtils.copyProperties(dyDietRemember, dto);
            String sid = DaoHelper.getSeq();
            dyDietRemember.setSid(sid);
            this.dyRememberDietMapper.addDietRemember(dyDietRemember);
        }
        //如果餐时为其他类型,则可以添加多次
        else if (DynamicBloodSugarConstant.OTHER_MEAL.equals(dto.getParamCode()) && id == null){
            dyDietRemember = new DyRememberDietPO();
            BeanUtils.copyProperties(dyDietRemember, dto);
            String sid = DaoHelper.getSeq();
            dyDietRemember.setSid(sid);
            this.dyRememberDietMapper.addDietRemember(dyDietRemember);
        }else{
            dyDietRemember = new DyRememberDietPO();
            dyDietRemember.setSid(id);
            dyDietRemember.setOperationId(dto.getOperationId());
            dyDietRemember.setMemberId(dto.getMemberId());
            dyDietRemember.setRecordDt(dto.getRecordDt());
            dyDietRemember.setParamCode(dto.getParamCode());
            dyDietRemember.setParamIngredientJson(dto.getParamIngredientJson());
            dyDietRemember.setParamDt(dto.getParamDt());
            dyDietRemember.setOperationType(dto.getOperationType());
            if (id != null && id != ""){
                this.dyRememberDietMapper.modifyDyDietRememberBySid(dyDietRemember);
            }else{
                this.dyRememberDietMapper.updateDyDietRemember(dyDietRemember);
            }

        }
    }

    @Override
    public void setSportRemember(DyRememberSportDTO dto,String id) {
        DyRememberSportPO dyRememberSport = new DyRememberSportPO();
        if (id != null && id != ""){
            dyRememberSport = this.dyRememberSportMapper.getDySportRememberValues(id);
        }else {
            //先查询同一时间段有没有运动过,有就修改.
             dyRememberSport = this.dyRememberSportMapper.getDySportRememberPO(dto);
        }

        if (dyRememberSport == null){
            dyRememberSport = new DyRememberSportPO();
            BeanUtils.copyProperties(dyRememberSport, dto);
            String sid = DaoHelper.getSeq();
            dyRememberSport.setSid(sid);
            this.dyRememberSportMapper.insertSportRemember(dyRememberSport);
        }  else {
            dyRememberSport = new DyRememberSportPO();
            BeanUtils.copyProperties(dyRememberSport, dto);
            dyRememberSport.setSid(id);

            if (id != null && id != ""){
                this.dyRememberSportMapper.updateDySportRememberBySid(dyRememberSport);
            }else{
                this.dyRememberSportMapper.updateDySportRemember(dyRememberSport);
            }

        }


    }

    @Override
    public void setPharmacyRemember(DyRememberPharmacyDTO dto,String id) {
        DyRememberPharmacyPO dyPharmacyRemember = new DyRememberPharmacyPO();
        if (id != null && id != ""){
            dyPharmacyRemember = this.dyRememberPharmacyMapper.getDyPharmacyRememberValues(id);
        }else{
            //查看表中是否存在这个数据(根据探头和操作者id,这一天的时间)
            dyPharmacyRemember = this.dyRememberPharmacyMapper.getDyPharmacyRememberPO(dto);

        }
        if (dyPharmacyRemember == null){
            dyPharmacyRemember = new DyRememberPharmacyPO();
            BeanUtils.copyProperties(dyPharmacyRemember, dto);
            String sid = DaoHelper.getSeq();
            dyPharmacyRemember.setSid(sid);
            this.dyRememberPharmacyMapper.addDyPharmacyRemember(dyPharmacyRemember);
        }else{
            dyPharmacyRemember = new DyRememberPharmacyPO();
            BeanUtils.copyProperties(dyPharmacyRemember, dto);
            dyPharmacyRemember.setSid(id);
            if (id != null && id != ""){
                this.dyRememberPharmacyMapper.updateDyPharmacyRememberBySid(dyPharmacyRemember);
            }else {
                this.dyRememberPharmacyMapper.updateDyPharmacyRemember(dyPharmacyRemember);
            }

        }
    }

    @Override
    public void updateDyDietRemember(String sid) {
        this.dyRememberDietMapper.delDietRemember(sid);
    }

    @Override
    public DyRememberSportPO getDySportRememberValues(String sid) {
        DyRememberSportPO dyRememberSportPO = this.dyRememberSportMapper.getDySportRememberValues(sid);
        return dyRememberSportPO;
    }

    @Override
    public void deleteSportRemember(String sid) {
        this.dyRememberSportMapper.deleteSportRememberBySid(sid);
    }

    @Override
    public void deletePharmacyRemember(String sid) {
        this.dyRememberPharmacyMapper.deletePharmacyRemember(sid);
    }

    @Override
    public DyRememberPharmacyPO getDyPharmacyRememberValues(String sid) {
        DyRememberPharmacyPO dyRememberPharmacyPO = this.dyRememberPharmacyMapper.getDyPharmacyRememberValues(sid);
        return dyRememberPharmacyPO;
    }

    @Override
    public List<DyRememberPharmacyPO> getDyPharmacyRememberPOList(DyRememberPharmacyDTO dyRememberPharmacyDTO) {
        List<DyRememberPharmacyPO> dyRememberPharmacyPOList = this.dyRememberPharmacyMapper.getDyPharmacyRememberPOList(dyRememberPharmacyDTO);
        return dyRememberPharmacyPOList;
    }

    @Override
    public void setSleepRemember(DyRememberSleepDTO dto,Integer origin) {
        //查看表中是否存在这个数据(根据患者id,这一天的时间,有效的和哪个数据来源的)
        DyRememberSleepPO dyRememberSleep = this.dyRememberSleepMapper.getDySleepRememberPO(dto);
        if (dyRememberSleep == null){
            dyRememberSleep = new DyRememberSleepPO();
            BeanUtils.copyProperties(dyRememberSleep, dto);
            String sid = DaoHelper.getSeq();
            dyRememberSleep.setSid(sid);
            this.dyRememberSleepMapper.addDySleepRemember(dyRememberSleep);
        }else{
            this.dyRememberSleepMapper.updateDySleepRemember(dto);
        }
        if (origin == 1){
            //同步动态血压记一记
            dyBloodPressureDiarySycHandler(dto);
        }
    }

    private void dyBloodPressureDiarySycHandler(DyRememberSleepDTO dto){
        AddDyBloodPressureDiaryDTO addDyBloodPressureDiaryDTO = new AddDyBloodPressureDiaryDTO();
        addDyBloodPressureDiaryDTO.setMemberId(dto.getMemberId());
        addDyBloodPressureDiaryDTO.setNightSleepStart(dto.getSleepDt());
        addDyBloodPressureDiaryDTO.setOperationId(dto.getOperationId());
        String recordDt = dto.getRecordDt();
        Boolean after = DateHelper.dateAfter(recordDt.substring(11, 19), DateHelper.TIME_SECOND_FORMAT,
                DyBloodPressureConstant.DEFAULT_DAY_START_TIME, DateHelper.TIME_SECOND_FORMAT);
        String now = recordDt.substring(0, 10);
        String front = DateHelper.plusDate(now, -1);
        String back = DateHelper.plusDate(now, 1);
        if (after){
            addDyBloodPressureDiaryDTO.setStartDt(now);
            addDyBloodPressureDiaryDTO.setEndDt(back);
        }else {
            addDyBloodPressureDiaryDTO.setStartDt(front);
            addDyBloodPressureDiaryDTO.setEndDt(now);
        }
        dyBloodPressureService.addUpdateBpDiary(addDyBloodPressureDiaryDTO,2);
    }

    @Override
    public List<DyRememberSleepPO> getSleepRemember(DyRememberSleepDTO dyRememberSleepDTO) {
        List<DyRememberSleepPO> dyRememberSleepPOList = this.dyRememberSleepMapper.getSleepRemember(dyRememberSleepDTO);
        return dyRememberSleepPOList;
    }

    @Override
    public void deleteBloodSugarRemark(String sid) {
        this.dyBloodSugarRemarkMapper.deleteyBloodSugarRemarkById(sid);
    }

    @Override
    public PageResult<DyRememberFoodPO> getFoodList(PageFoodItemDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getSize());
        PageResult<DyRememberFoodPO> result ;
        log.info("获取食物开始：" + System.currentTimeMillis());
        if ( "水果类".equals(dto.getType())){
            dto.setType("水果");
        }
//        Map<String, Object> result = new HashMap<>();
        List<DyRememberFoodPO> total = dyRememberDietMapper.getFoodLsit(dto.getFoodName(), dto.getType());
//        List<DyRememberFoodPO> grain = new ArrayList<>();
//        List<DyRememberFoodPO> beans = new ArrayList<>();
//        List<DyRememberFoodPO> vegetables = new ArrayList<>();
//        List<DyRememberFoodPO> fruit = new ArrayList<>();
//        List<DyRememberFoodPO> meatAndEgg = new ArrayList<>();
//        List<DyRememberFoodPO> milk = new ArrayList<>();
//        List<DyRememberFoodPO> grease = new ArrayList<>();
//        List<DyRememberFoodPO> other = new ArrayList<>();
//
        //食物分类
        for (DyRememberFoodPO dyRememberFoodPO: total) {
            if (StringUtils.isNullOrEmpty(dyRememberFoodPO.getOneName())) continue;
            if (dyRememberFoodPO.getOneName().contains("乳类")) { dyRememberFoodPO.setType("乳类"); continue; }
            if (dyRememberFoodPO.getOneName().contains("干豆")) { dyRememberFoodPO.setType("干豆类"); continue; }
            if (dyRememberFoodPO.getOneName().contains("水果")) { dyRememberFoodPO.setType("水果类"); continue; }
            if (dyRememberFoodPO.getOneName().contains("油脂")) { dyRememberFoodPO.setType("油脂类"); continue; }
            if (dyRememberFoodPO.getOneName().contains("肉蛋")) { dyRememberFoodPO.setType("肉蛋类"); continue; }
            if (dyRememberFoodPO.getOneName().contains("蔬菜")) { dyRememberFoodPO.setType("蔬菜类"); continue; }
            if (dyRememberFoodPO.getOneName().contains("谷薯")) { dyRememberFoodPO.setType("谷薯类"); continue; }
            if (dyRememberFoodPO.getOneName().contains("其它")) { dyRememberFoodPO.setType("其它类"); continue; }
        }
//
//        result.put("谷薯类", grain);
//        result.put("干豆类", beans);
//        result.put("蔬菜类", vegetables);
//        result.put("水果类", fruit);
//        result.put("肉蛋类", meatAndEgg);
//        result.put("乳类", milk);
//        result.put("油脂类", grease);
//        result.put("其他类", other);
        result = new PageResult<>(total);
        log.info("处理食物结束：" + System.currentTimeMillis());

        return result;
    }

    @Override
    public Map getFoodListByName(PageFoodItemDTO dto) {
        Map<String, Object> result = new HashMap<>();
        List<DyRememberFoodPO> total = dyRememberDietMapper.getFoodLsit(dto.getFoodName(), dto.getType());
        List<DyRememberFoodPO> grain = new ArrayList<>();
        List<DyRememberFoodPO> beans = new ArrayList<>();
        List<DyRememberFoodPO> vegetables = new ArrayList<>();
        List<DyRememberFoodPO> fruit = new ArrayList<>();
        List<DyRememberFoodPO> meatAndEgg = new ArrayList<>();
        List<DyRememberFoodPO> milk = new ArrayList<>();
        List<DyRememberFoodPO> grease = new ArrayList<>();
        List<DyRememberFoodPO> other = new ArrayList<>();

        //食物分类
        for (DyRememberFoodPO dyRememberFoodPO: total) {
            if (StringUtils.isNullOrEmpty(dyRememberFoodPO.getOneName())) continue;
            if (dyRememberFoodPO.getOneName().contains("乳类")) { milk.add(dyRememberFoodPO); continue; }
            if (dyRememberFoodPO.getOneName().contains("干豆")) { beans.add(dyRememberFoodPO); continue; }
            if (dyRememberFoodPO.getOneName().contains("水果")) { fruit.add(dyRememberFoodPO); continue; }
            if (dyRememberFoodPO.getOneName().contains("油脂")) { grease.add(dyRememberFoodPO); continue; }
            if (dyRememberFoodPO.getOneName().contains("肉蛋")) { meatAndEgg.add(dyRememberFoodPO); continue; }
            if (dyRememberFoodPO.getOneName().contains("蔬菜")) { vegetables.add(dyRememberFoodPO); continue; }
            if (dyRememberFoodPO.getOneName().contains("谷薯")) { grain.add(dyRememberFoodPO); continue; }
            if (dyRememberFoodPO.getOneName().contains("其它")) { other.add(dyRememberFoodPO); continue; }
        }

        result.put("谷薯类", grain);
        result.put("干豆类", beans);
        result.put("蔬菜类", vegetables);
        result.put("水果类", fruit);
        result.put("肉蛋类", meatAndEgg);
        result.put("乳类", milk);
        result.put("油脂类", grease);
        result.put("其他类", other);

        return result;
    }

    @Override
    public void addDietRemember(DyRememberDietDTO dyRememberDietDTO, String sid){

        DyRememberDietPO dyDietRemember = new DyRememberDietPO();
        BeanUtils.copyProperties(dyDietRemember, dyRememberDietDTO);
        if (StringUtils.isNullOrEmpty(sid)){
            sid = DaoHelper.getSeq();
            dyDietRemember.setSid(sid);
            this.dyRememberDietMapper.addDietRememberNew(dyDietRemember);
        }else {
            this.dyRememberDietMapper.updateDyDietRememberNew(dyDietRemember);
        }
    }

    @Override
    public Map dishIdentify(InputStream is, String extension) throws Exception {
        Map<String, Object> result = new HashMap<>();
        byte[] bytes = IOUtils.toByteArray(is);
        String url = UploadUtils.uploadFile(bytes ,extension);
        JSONObject jsonObject = DishIdentifyTool.dishIdentify(url);

        result.put("picUrl", url);
        result.put("dish", jsonObject);

        return result;
    }

    @Override
    public List<MemberLifeTypePO> getLiftTypeList(MemberLifeTypeDTO memberLifeTypeDTO) {
        List<MemberLifeTypePO> liftTypeList = dyRememberDietMapper.getLiftTypeList(memberLifeTypeDTO);
        return liftTypeList;
    }

    @Override
    public void addLifeType(DyRememberDietPO po) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dietFoodList", new ArrayList<>());
        jsonObject.put("otherFood", po.getContent());
        po.setContent(jsonObject.toJSONString());

        if (StringUtils.isNullOrEmpty(po.getSid())){
            String sid = DaoHelper.getSeq();
            po.setSid(sid);
            po.setParamCode("4");
            this.dyRememberDietMapper.addLifeType(po);
        }else {
            this.dyRememberDietMapper.updateLifeType(po);
        }
    }

    @Override
    public void delLifeType(String sid) {
        dyRememberDietMapper.delLifeType(sid);
    }

    @Override
    public DyRememberFoodPO getSingleFoodItem(String id) {
        return dyRememberDietMapper.getFoodItemById(id);
    }

    @Override
    public List<String> getFoodType() {
        List<String> foodType = dyRememberDietMapper.getFoodType();
        for (int i = 0; i < foodType.size(); i++) {
            if (foodType.get(i).contains("乳类")) { foodType.set(i,"乳类"); continue; }
            if (foodType.get(i).contains("干豆")) { foodType.set(i,"干豆类"); continue; }
            if (foodType.get(i).contains("水果")) { foodType.set(i,"水果类"); continue; }
            if (foodType.get(i).contains("油脂")) { foodType.set(i,"油脂类"); continue; }
            if (foodType.get(i).contains("肉蛋")) { foodType.set(i,"肉蛋类"); continue; }
            if (foodType.get(i).contains("蔬菜")) { foodType.set(i,"蔬菜类"); continue; }
            if (foodType.get(i).contains("谷薯")) { foodType.set(i,"谷薯类"); continue; }
            if (foodType.get(i).contains("其它")) { foodType.set(i,"其它类"); continue; }
        }
        return foodType;
    }

}
