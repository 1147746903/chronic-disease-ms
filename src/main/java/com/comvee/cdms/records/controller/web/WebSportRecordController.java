package com.comvee.cdms.records.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.dybloodsugar.dto.DyRememberSportDTO;
import com.comvee.cdms.records.constant.SportRecordConstant;
import com.comvee.cdms.records.model.dto.SportRecordDTO;
import com.comvee.cdms.records.model.po.SportRecordPO;
import com.comvee.cdms.records.service.SportRecordService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/web/sportRecord")
public class WebSportRecordController {
    @Autowired
    private SportRecordService sportService;

    @RequestMapping("listSport")
    public Result listSport(SportRecordDTO dto){
        if(dto.getIntensity() == null){
            dto.setIntensity(1);
        }else if(dto.getIntensity() == 4){
            dto.setIntensity(null);
        }
        return Result.ok(sportService.listSport(dto));
    }

    @RequestMapping("saveSportRecordForDyBlood")
    public Result saveSportRecordForDyBlood(DyRememberSportDTO dto, String sid) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        JSONObject sportMethodJson = JSON.parseObject(dto.getSportMethodJson());
        ValidateTool.checkParamIsNull(dto.getRecordDt(), "recordDt");
        ValidateTool.checkParamIsNull(dto.getMemberId(), "memberDt");
        ValidateTool.checkParamIsNull(dto.getSportEndDt(), "sportEndDt");
        long min = DateHelper.getDistanceTimesMin(dto.getRecordDt(), dto.getSportEndDt());
        if(sportMethodJson == null || !sportMethodJson.containsKey("sportMethodBOList")){
            throw new BusinessException("json格式错误");
        }
        JSONArray array = sportMethodJson.getJSONArray("sportMethodBOList");
        if(StringUtils.isBlank(sid)){
            sid = DaoHelper.getSeq();
        }
        List<SportRecordPO> records = new ArrayList<>();
        for(int i = 0 ; i < array.size(); i ++){
            JSONObject obj = array.getJSONObject(i);
            SportRecordPO record = new SportRecordPO();
            String [] params = new String[]{ "sportMethod",  "sportState", "intensity" };
            for(String param :params){
                if(!obj.containsKey(param)){
                    throw new BusinessException(param + "不能为空");
                }
            }
            record.setRecordMainId(sid);
            record.setSportId(obj.getString("sportId"));
            record.setSportName(obj.getString("sportMethod"));
            record.setTotal((double) min);
            record.setSportState(obj.getInteger("sportState"));
            record.setIntensity(obj.getInteger("intensity"));
            record.setSid(obj.getString("sid"));
            record.setMemberId(dto.getMemberId());
            record.setOrigin(SportRecordConstant.ORIGIN_DY_BLOOD);
            record.setOriginId("");
            record.setOperationType(SportRecordConstant.OPERATION_TYPE_MEDIC);
            record.setOperationId(doctorSessionBO.getDoctorId());
            record.setRecordTime(dto.getRecordDt());
            records.add(record);
        }
        sportService.saveSportRecords(records);
        return Result.ok();
    }

    @RequestMapping("listSportRecordForDyBlood")
    public Result listSportRecordForDyBlood(DyRememberSportDTO dto){
        ValidateTool.checkParamIsNull(dto.getMemberId(), "memberId");

        SportRecordDTO sportDto = new SportRecordDTO();
        sportDto.setRecordDt(dto.getRecordDt());
        sportDto.setMemberId(dto.getMemberId());
        sportDto.setBegin(dto.getSportStartDt());
        sportDto.setEnd(dto.getSportEndDt());
        return Result.ok(sportService.listSportRecordForDyBlood(sportDto));
    }

    @RequestMapping("deleteSportRecord")
    public Result deleteSportRecord(String sid){
        ValidateTool.checkParamIsNull(sid, "sid");
        sportService.deleteSportRecordById(sid);
        return Result.ok();
    }
}
