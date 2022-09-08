package com.comvee.cdms.sign.service.impl;

import com.comvee.cdms.common.cfg.ServerHostConstant;
import com.comvee.cdms.common.utils.HttpUtils;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddBloodPressureMapperDTO;
import com.comvee.cdms.sign.dto.AddBloodSugarMapperDTO;
import com.comvee.cdms.sign.service.SignSyncThirdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/4/12
 */
@Service("signSyncThirdService")
public class SignSyncThirdServiceImpl implements SignSyncThirdService {

    private final static Logger log = LoggerFactory.getLogger(SignSyncThirdServiceImpl.class);

    @Override
    @Async
    public void syncBloodSugar(AddBloodSugarMapperDTO addBloodSugarMapperDTO) {
        try{
            Map<String ,String> paramMap = new HashMap<>();
            paramMap.put("pid" ,addBloodSugarMapperDTO.getMemberId());
            paramMap.put("value" ,addBloodSugarMapperDTO.getParamValue());
            paramMap.put("timeRange" , String.valueOf(PARAM_CODE_MAP.getOrDefault(addBloodSugarMapperDTO.getParamCode() ,0)));
            paramMap.put("recordDt" ,addBloodSugarMapperDTO.getRecordDt());
            paramMap.put("level" ,addBloodSugarMapperDTO.getParamLevel().toString());
            paramMap.put("id" ,addBloodSugarMapperDTO.getSid());
            String url = ServerHostConstant.COMVEE_DEFENDER_HOST + "syndata/bloodSugar.do";
            String res = HttpUtils.doPost(url ,paramMap);
            log.info("同步血糖，请求结果为：{}" ,res);
        }catch (Exception e){
            log.error("同步血糖失败" ,e);
        }
    }

    @Override
    @Async
    public void syncBloodPressure(AddBloodPressureMapperDTO addBloodPressureMapperDTO) {
        try{
            Map<String ,String> paramMap = new HashMap<>();
            paramMap.put("dbp", addBloodPressureMapperDTO.getDbp());
            paramMap.put("sbp", addBloodPressureMapperDTO.getSbp());
            paramMap.put("recordTime", addBloodPressureMapperDTO.getRecordTime());
            paramMap.put("sid", addBloodPressureMapperDTO.getSid());
            paramMap.put("pid", addBloodPressureMapperDTO.getMemberId());
            String url = ServerHostConstant.COMVEE_DEFENDER_HOST + "syndata/bloodPressure.do";
            String res = HttpUtils.doPost(url ,paramMap);
            log.info("同步血压，请求结果为：{}" ,res);
        }catch (Exception e){
            log.error("同步血压失败" ,e);
        }
    }

    static final Map<String ,Integer> PARAM_CODE_MAP = new HashMap<>();
    static {
        PARAM_CODE_MAP.put(SignConstant.PARAM_CODE_3AM , 0);
        PARAM_CODE_MAP.put(SignConstant.PARAM_CODE_BEFORE_BREAKFAST , 1);
        PARAM_CODE_MAP.put(SignConstant.PARAM_CODE_AFTER_BREAKFAST , 2);
        PARAM_CODE_MAP.put(SignConstant.PARAM_CODE_BEFORE_LUNCH , 3);
        PARAM_CODE_MAP.put(SignConstant.PARAM_CODE_AFTER_LUNCH , 4);
        PARAM_CODE_MAP.put(SignConstant.PARAM_CODE_BEFORE_DINNER , 5);
        PARAM_CODE_MAP.put(SignConstant.PARAM_CODE_AFTER_DINNER , 6);
        PARAM_CODE_MAP.put(SignConstant.PARAM_CODE_BEFORE_SLEEP , 7);
    }
}
