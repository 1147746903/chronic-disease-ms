package com.comvee.cdms.records.controller.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.records.constant.DrugsRecordConstant;
import com.comvee.cdms.records.model.dto.DrugsRecordDTO;
import com.comvee.cdms.records.model.po.DrugsRecordPO;
import com.comvee.cdms.records.service.DrugsRecordService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wechat/drugsRecord")
public class WechatDrugsRecordController {
    @Autowired
    private DrugsRecordService drugsService;

    @RequestMapping("getDrugsByName")
    public Result getDrugsByName(String drugName){
        ValidateTool.checkParamIsNull(drugName, "drugName");
        return Result.ok(drugsService.getDrugsByName(drugName));
    }

    @RequestMapping("saveDrugsRecordForMini")
    public Result saveDrugsRecordForMini(@RequestBody DrugsRecordDTO dto){
        List<DrugsRecordPO> recordList = castToDrugRecord(dto);
        drugsService.saveDrugsRecords(recordList);
        return Result.ok();
    }

    private List<DrugsRecordPO> castToDrugRecord(DrugsRecordDTO dto){
        ValidateTool.checkParamIsNull(dto.getRecordTime(), "recordTime");
        ValidateTool.checkParamIsNull(dto.getDrugsList(), "drugsList");
        List<JSONObject> drugs = dto.getDrugsList();
        List<DrugsRecordPO> recordList = new ArrayList<>();
        if(drugs.size() == 0){
            throw new BusinessException("");
        }
        MemberPO memberPO = SessionTool.getWechatSession();
        if(StringUtils.isBlank(dto.getRecordMainId())){
            dto.setRecordMainId(DaoHelper.getSeq());
        }
        if(drugs.size() == 0){
            throw new BusinessException("用药记录为空");
        }
        for(JSONObject obj: drugs){
            DrugsRecordPO po = drugsService.drugsJsonHandler(obj);
            po.setRecordTime(dto.getRecordTime());
            po.setRecordMainId(dto.getRecordMainId());
            po.setMemberId(memberPO.getMemberId());
            po.setOperationType(DrugsRecordConstant.OPERATION_TYPE_MEMBER);
            po.setOperationId(memberPO.getMemberId());
            po.setOrigin(DrugsRecordConstant.ORIGIN_MINI_PROGRAM);
            po.setOriginId("");
            recordList.add(po);
        }
        return recordList;
    }

    @RequestMapping("saveMulDrugsRecordForMini")
    public Result saveDrugsRecordsForMini(String recordsStr){
        ValidateTool.checkParamIsNull(recordsStr, "recordsStr");
        List<DrugsRecordDTO> dtoList = JSON.parseArray(recordsStr, DrugsRecordDTO.class);
        List<DrugsRecordPO> records = new ArrayList<>();
        for(DrugsRecordDTO dto : dtoList){
            records.addAll(castToDrugRecord(dto));
        }
        drugsService.saveDrugsRecords(records);
        return Result.ok();
    }

    @RequestMapping("deleteDrugsRecordByMainId")
    public Result deleteDrugsRecordByMainId(String mainId){
        ValidateTool.checkParamIsNull(mainId, "mainId");
        DrugsRecordDTO dto = new DrugsRecordDTO();
        dto.setRecordMainId(mainId);
        drugsService.deleteDrugsRecord(dto);
        return Result.ok();
    }
    /**
     * 获取最近6条记录
     *
     * @param dto
     * @return
     */
    @RequestMapping("listRecentDrugsRecord")
    public Result listRecentDrugsRecord(DrugsRecordDTO dto) {
        MemberPO memberPO = SessionTool.getWechatSession();
        dto.setMemberId(memberPO.getMemberId());
        return Result.ok(drugsService.listRecentDrugsRecord(dto));
    }


    /**
     * 获取记录
     *
     * @param dto
     * @return
     */
    @RequestMapping("listDrugsRecord")
    public Result listDrugsRecord(DrugsRecordDTO dto) {
        MemberPO memberPO = SessionTool.getWechatSession();
        dto.setMemberId(memberPO.getMemberId());
        return Result.ok(drugsService.listDrugsRecord(dto));
    }
}
