package com.comvee.cdms.records.controller.web;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.dybloodsugar.bo.DyRememberDietBO;
import com.comvee.cdms.dybloodsugar.bo.DyRememberDietFoodBO;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.dto.DyRememberDietDTO;
import com.comvee.cdms.records.constant.DietRecordConstant;
import com.comvee.cdms.records.model.dto.DietRecordDTO;
import com.comvee.cdms.records.model.po.DietRecordPO;
import com.comvee.cdms.records.service.DietRecordService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/web/dietRecord")
public class WebDietRecordController {
    @Autowired
    private DietRecordService dietService;

    @RequestMapping("/saveDietRecordForDyBlood")
    public Result saveDietRecordForDyBlood(DyRememberDietDTO dto,String sid){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setOperationType(DynamicBloodSugarConstant.ORIGIN_DOCTOR);
        dto.setOperationId(doctorSessionBO.getDoctorId());
        ValidateTool.checkParamIsNull(dto.getRecordDt(), "recordDt");
        ValidateTool.checkParamIsNull(dto.getParamCode(), "paramCode");
        ValidateTool.checkParamIsNull(dto.getMemberId(), "memberId");
        DyRememberDietBO dyRememberDietBO = null;
        try {
            dyRememberDietBO = JSON.parseObject(dto.getParamIngredientJson(), DyRememberDietBO.class);
        } catch (Exception e) {
            throw new BusinessException("动态血糖记一记饮食数据格式异常");
        }
        if(StringUtils.isBlank(sid)) {
            DietRecordDTO recordDTO = new DietRecordDTO();
            recordDTO.setRecordDt(dto.getRecordDt().substring(0, 10));
            recordDTO.setMemberId(dto.getMemberId());
            recordDTO.setTimeType(Integer.parseInt(dto.getParamCode()));
            sid = dietService.getMainId(recordDTO);
            if(StringUtils.isBlank(sid)) {
                sid = DaoHelper.getSeq();
            }
        }
        List<DietRecordPO> records = new ArrayList<>();
        List<DyRememberDietFoodBO> foodList = dyRememberDietBO.getDietFoodList();
        if (foodList != null && !foodList.isEmpty()) {
            for (DyRememberDietFoodBO food : foodList) {
                DietRecordPO po = new DietRecordPO();
                po.setMemberId(dto.getMemberId());
                po.setFoodId(food.getMultipleFoodId());
                po.setFoodName(food.getMultipleFood());
                po.setTotal(food.getTotal());
                po.setUnit(food.getUnit());
                po.setSid(food.getSid());
                po.setTimeType(Integer.parseInt(dto.getParamCode()));
                po.setRecordTime(dto.getRecordDt());
                po.setRecordMainId(sid);
                dyBloodDietHandler(po, doctorSessionBO.getDoctorId());
                records.add(po);
            }
        }
        this.dietService.saveDietRecords(records);
        return Result.ok("");
    }

    private void dyBloodDietHandler(DietRecordPO po, String doctorId){
        po.setOrigin(DietRecordConstant.ORIGIN_DY_REMEMBER);
        po.setOriginId("");
        po.setOperationType(DietRecordConstant.OPERATION_TYPE_MEDIC);
        po.setOperationId(doctorId);
    }

    @RequestMapping("listDietRecordForDyBlood")
    public Result listDietRecordForDyBlood(DietRecordDTO dto){
        ValidateTool.checkParamIsNull(dto.getRecordDt(), "recordDt");
        ValidateTool.checkParamIsNull(dto.getMemberId(), "memberId");
        return Result.ok(dietService.listDietRecordByDayForDyBlood(dto));
    }

    @RequestMapping("deleteDietRecord")
    public Result deleteDietRecord(String sid){
        ValidateTool.checkParamIsNull(sid, "sid");
        dietService.deleteDietRecordByMainId(sid);
        return Result.ok();
    }

}
