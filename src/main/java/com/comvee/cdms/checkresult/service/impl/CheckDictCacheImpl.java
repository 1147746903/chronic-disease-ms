package com.comvee.cdms.checkresult.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.checkresult.constant.CheckConstant;
import com.comvee.cdms.checkresult.service.CheckDictCacheI;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.hospital.model.dto.HosOptionDTO;
import com.comvee.cdms.hospital.model.po.HosOptionPO;
import com.comvee.cdms.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("checkDictCache")
public class CheckDictCacheImpl implements CheckDictCacheI {

    @Autowired
    private HospitalService hospitalService;

    @Override
    @Cacheable(key = "'CHECK_IMPORTANT_CODES' + #hospitalId" , value = "public")
    public List<String> IMPORTANT_CODES(String hospitalId) {
        List<String> result = null;
        HosOptionDTO dto = new HosOptionDTO();
        dto.setPjoType(2);
        dto.setHospitalId(hospitalId);
        HosOptionPO optionPO = this.hospitalService.getHosOption(dto);
        if(optionPO!=null && !StringUtils.isBlank(optionPO.getOptionJson())){
            Map<String,String> option = JSONObject.parseObject(optionPO.getOptionJson(),Map.class);
            if(option!=null){
                result = new ArrayList<>();
                for(String key:option.keySet()){
                    result.add(option.get(key).toString());
                }
            }
        }
        if(result==null){
            result = CheckConstant.IMPORTANT_CODES;
        }
        return result;
    }

    @Override
    @Cacheable(key = "'CHECK_TYPE_MAP' + #hospitalId" , value = "public")
    public Map<String, String> TYPE_MAP(String hospitalId) {
        Map<String, String> result = null;
        HosOptionDTO dto = new HosOptionDTO();
        dto.setPjoType(2);
        dto.setHospitalId(hospitalId);
        HosOptionPO optionPO = this.hospitalService.getHosOption(dto);
        if(optionPO!=null && !StringUtils.isBlank(optionPO.getOptionJson())){
            Map<String,String> option = JSONObject.parseObject(optionPO.getOptionJson(),Map.class);
            if(option!=null){
                result = option;
            }
        }
        if(result==null){
            result = CheckConstant.TYPE_MAP;
        }
        return result;
    }

    @Override
    @Cacheable(key = "'CHECK_TYPE_3_MAP' + #hospitalId" , value = "public")
    public Map<String, String> C_TYPE_MAP(String hospitalId) {
        Map<String, String> result = null;
        HosOptionDTO dto = new HosOptionDTO();
        dto.setPjoType(3);
        dto.setHospitalId(hospitalId);
        HosOptionPO optionPO = this.hospitalService.getHosOption(dto);
        if(optionPO!=null && !StringUtils.isBlank(optionPO.getOptionJson())){
            Map<String,String> option = JSONObject.parseObject(optionPO.getOptionJson(),Map.class);
            if(option!=null){
                result = option;
            }
        }
        if(result==null){
            result = CheckConstant.TYPE_MAP;
        }
        return result;
    }

    @Override
    public String HBA1C_CODE(String hospitalId) {
        Map<String, String> result = this.C_TYPE_MAP(hospitalId);
        return result.getOrDefault("hba1c","hba1c");
    }

    @Override
    public String LDLC_CODE(String hospitalId) {
        Map<String, String> result = this.C_TYPE_MAP(hospitalId);
        return result.getOrDefault("ldlc","ldlc");
    }
}
