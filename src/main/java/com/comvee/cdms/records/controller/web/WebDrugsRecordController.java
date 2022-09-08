package com.comvee.cdms.records.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.records.constant.DrugsRecordConstant;
import com.comvee.cdms.records.model.dto.DrugsRecordDTO;
import com.comvee.cdms.records.model.po.DrugsRecordPO;
import com.comvee.cdms.records.service.DrugsRecordService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/web/drugsRecord")
public class WebDrugsRecordController {
    @Autowired
    private DrugsRecordService drugsService;

    @RequestMapping("getDrugsByName")
    public Result getDrugsByName(String name){
        ValidateTool.checkParamIsNull(name, "name");
        return Result.ok(drugsService.getDrugsByName(name));
    }


    @RequestMapping("saveDrugsRecordForDyBlood")
    public Result saveDrugsRecordForDyBlood(String recordsStr){
        ValidateTool.checkParamIsNull(recordsStr, "recordsStr");
        List<DrugsRecordDTO> dtoList = JSON.parseArray(recordsStr, DrugsRecordDTO.class);
        List<DrugsRecordPO> records = new ArrayList<>();
        for(DrugsRecordDTO dto : dtoList){
            ValidateTool.checkParamIsNull(dto.getRecordTime(), "recordTime");
            ValidateTool.checkParamIsNull(dto.getMemberId(), "memberId");
            ValidateTool.checkParamIsNull(dto.getDrugsList(), "drugsList");
            List<JSONObject> drugs = dto.getDrugsList();
            if(drugs.size() == 0){
                throw new BusinessException("");
            }
            DoctorSessionBO doctor = SessionTool.getWebSession();
            if(StringUtils.isBlank(dto.getRecordMainId())){
                dto.setRecordMainId(DaoHelper.getSeq());
            }
            for(JSONObject obj: drugs){
                DrugsRecordPO po = drugsService.drugsJsonHandler(obj);
                po.setRecordTime(dto.getRecordTime());
                po.setRecordMainId(dto.getRecordMainId());
                po.setMemberId(dto.getMemberId());
                po.setOperationType(DrugsRecordConstant.OPERATION_TYPE_MEDIC);
                po.setOperationId(doctor.getDoctorId());
                po.setOrigin(DrugsRecordConstant.ORIGIN_DY_BLOOD);
                po.setOriginId("");
                records.add(po);
            }
        }
        drugsService.saveDrugsRecords(records);
        return Result.ok();
    }
    /**
     * 获取记录
     *
     * @param dto
     * @return
     */
    @RequestMapping("listDrugsRecord")
    public Result listDrugsRecord(DrugsRecordDTO dto) {
        ValidateTool.checkParamIsNull(dto.getMemberId(), "memberId");
        return Result.ok(drugsService.listDrugsRecord(dto));
    }

    @RequestMapping("deleteDrugsRecord")
    public Result deleteDrugsRecord(String sid){
        ValidateTool.checkParamIsNull(sid, "sid");
        DrugsRecordDTO dto = new DrugsRecordDTO();
        dto.setRecordMainId(sid);
        drugsService.deleteDrugsRecord(dto);
        return Result.ok();
    }
}
