package com.comvee.cdms.checkresult.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.checkresult.constant.InspectionDict;
import com.comvee.cdms.checkresult.po.InspectionPO;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.healthrecord.constant.HealthHouseConstant;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddBloodPressureServiceDTO;
import com.comvee.cdms.sign.dto.AddBmiServiceDTO;
import com.comvee.cdms.sign.dto.AddWhrDTO;
import com.comvee.cdms.sign.service.BloodPressureService;
import com.comvee.cdms.sign.service.BmiService;
import com.comvee.cdms.sign.service.WhrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InspectionSyncHandler {

    @Autowired
    private BmiService bmiService;

    @Autowired
    private WhrService whrService;

    @Autowired
    private BloodPressureService bloodPressureService;

    public void resolveInspectionSync(InspectionPO inspection){
        if(InspectionDict.HEALTH_REPORT.getCode().equals(inspection.getInspectCode())){
            resolveHealthReport(inspection);
        }
    }

    private void resolveHealthReport(InspectionPO inspection){
        JSONObject dataJson = JSON.parseObject(inspection.getInspectDataJson());
        //bmi
        JSONObject bmiJson = dataJson.getJSONObject(HealthHouseConstant.CHECKUP_ITEM_HEIGHT_WEIGHT);
        if(bmiJson != null){
            AddBmiServiceDTO addBmiServiceDTO = new AddBmiServiceDTO();
            addBmiServiceDTO.setWeight(bmiJson.getFloat("weight"));
            addBmiServiceDTO.setHeight(bmiJson.getFloat("height"));
            addBmiServiceDTO.setBmi(bmiJson.getString("bmi"));
            addBmiServiceDTO.setRecordDt(bmiJson.getString("checkupDt"));
            addBmiServiceDTO.setMemberId(inspection.getMemberId());
            addBmiServiceDTO.setOrigin(SignConstant.ORIGIN_HEALTH_INTEGRATED_MACHINE);
            addBmiServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
            addBmiServiceDTO.setOperatorId(Constant.DEFAULT_FOREIGN_ID);
            this.bmiService.addBmi(addBmiServiceDTO);
        }
        //腰臀比
        JSONObject whrJson = dataJson.getJSONObject(HealthHouseConstant.CHECKUP_ITEM_WHR);
        if(whrJson != null){
            AddWhrDTO addWhrDTO = new AddWhrDTO();
            addWhrDTO.setMemberId(inspection.getMemberId());
            addWhrDTO.setWaist(whrJson.getString("waistline"));
            addWhrDTO.setHip(whrJson.getString("hipline"));
            addWhrDTO.setWhr(whrJson.getString("whr"));
            addWhrDTO.setRecordDt(whrJson.getString("checkupDt"));
            addWhrDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
            addWhrDTO.setOperatorId(Constant.DEFAULT_FOREIGN_ID);
            addWhrDTO.setOrigin(SignConstant.ORIGIN_HEALTH_INTEGRATED_MACHINE);
            this.whrService.addWhr(addWhrDTO);
        }
        //血压处理
        JSONObject arteriosclerosisJson = dataJson.getJSONObject(HealthHouseConstant.CHECKUP_ITEM_ARTERIOSCLEROSIS);
        if(arteriosclerosisJson != null){
            AddBloodPressureServiceDTO addBloodPressureServiceDTO  = new AddBloodPressureServiceDTO();
            addBloodPressureServiceDTO.setDbp(arteriosclerosisJson.getString("lowPressure"));
            addBloodPressureServiceDTO.setSbp(arteriosclerosisJson.getString("highPressure"));
            addBloodPressureServiceDTO.setHeartRate(arteriosclerosisJson.getString("pulse"));
            addBloodPressureServiceDTO.setMemberId(inspection.getMemberId());
            addBloodPressureServiceDTO.setRecordTime(arteriosclerosisJson.getString("checkupDt"));
            addBloodPressureServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
            addBloodPressureServiceDTO.setOperatorId(Constant.DEFAULT_FOREIGN_ID);
            addBloodPressureServiceDTO.setOrigin(SignConstant.ORIGIN_HEALTH_INTEGRATED_MACHINE);
            this.bloodPressureService.addBloodPressure(addBloodPressureServiceDTO);
        }
    }
}
