package com.comvee.cdms.records.controller.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodsugar.dto.DyRememberDietDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.records.constant.DietRecordConstant;
import com.comvee.cdms.records.model.dto.DietRecordDTO;
import com.comvee.cdms.records.model.dto.DrugsRecordDTO;
import com.comvee.cdms.records.model.dto.SportRecordDTO;
import com.comvee.cdms.records.model.po.DietRecordPO;
import com.comvee.cdms.records.service.DietRecordService;
import com.comvee.cdms.records.service.DrugsRecordService;
import com.comvee.cdms.records.service.SportRecordService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wechat/dietRecord")
public class WechatDietRecordController {
    @Autowired
    private DietRecordService dietRecordService;
    @Autowired
    private SportRecordService sportRecordService;
    @Autowired
    private DrugsRecordService drugsRecordService;

    /**
     * 添加饮食记录
     *
     * @param dyRememberDietDTO
     * @return
     */
    @RequestMapping("/addDietRecordForMiniProgram")
    public Result addDietRecordForMiniProgram(DyRememberDietDTO dyRememberDietDTO) {
        ValidateTool.checkParamIsNull(dyRememberDietDTO.getParamCode(), "paramCode");
        ValidateTool.checkParamIsNull(dyRememberDietDTO.getRecordDt(), "recordDt");
        ValidateTool.checkParamIsNull(dyRememberDietDTO.getParamIngredientJson(), "paramIngredientJson");
        List<JSONObject> foodList = JSON.parseArray(dyRememberDietDTO.getParamIngredientJson(), JSONObject.class);
        MemberPO memberPO = SessionTool.getWechatSession();
        List<DietRecordPO> records = new ArrayList<>();
        if (foodList.size() == 0) {
            throw new BusinessException("至少包含一种食物");
        }
        if (dyRememberDietDTO.getSid() == null) {
            dyRememberDietDTO.setSid(DaoHelper.getSeq());
        }
        for (JSONObject food : foodList) {
            String foodId = food.getString("id");
            double total = 0.0;
            try {
                total = Double.parseDouble(food.getString("total"));
            } catch (Exception e) {
                throw new BusinessException("total必须为数值");
            }
            String foodName = food.getString("name");
            if (StringUtils.isBlank(foodId) && StringUtils.isBlank(foodName)) {
                throw new BusinessException("食材id和食材名称不能同时为空");
            }
            ValidateTool.checkParamIsNull(total, "total");
            if (!StringUtils.isBlank(foodId) && !dietRecordService.hasFoodItem(foodId)) {
                throw new BusinessException("食材库未找到对应食材");
            }
            DietRecordPO dietRecordPO = new DietRecordPO();
            dietRecordPO.setFoodId(foodId);
            dietRecordPO.setTotal(total);
            try {
                if (food.containsKey("totalHeat")) {
                    dietRecordPO.setHeat(Double.parseDouble(food.getString("totalHeat")));
                }
            } catch (Exception ignored) {
            }
            dietRecordPO.setTimeType(Integer.parseInt(dyRememberDietDTO.getParamCode()));
            dietRecordPO.setRecordTime(dyRememberDietDTO.getRecordDt());
            dietRecordPO.setMemberId(memberPO.getMemberId());
            dietRecordPO.setFoodName(foodName);
            dietRecordPO.setSid(food.getString("sid"));
            dietRecordPO.setOperationType(DietRecordConstant.OPERATION_TYPE_MEMBER);
            dietRecordPO.setOperationId(memberPO.getMemberId());
            dietRecordPO.setOrigin(DietRecordConstant.ORIGIN_MINI_PROGRAM);
            dietRecordPO.setOriginId("");
            dietRecordPO.setFoodPic(food.getString(""));
            dietRecordPO.setRecordMainId(dyRememberDietDTO.getSid());
            records.add(dietRecordPO);
        }
        dietRecordService.saveDietRecords(records);
        return Result.ok();
    }

    /**
     * 获取一天饮食记录
     *
     * @param dietRecordDTO
     * @return
     */
    @RequestMapping("/listDietRecordByDayForMiniProgram")
    public Result listDietRecordByDayForMiniProgram(DietRecordDTO dietRecordDTO) {
        ValidateTool.checkParamIsNull(dietRecordDTO.getMemberId(), "memberId");
        ValidateTool.checkParamIsNull(dietRecordDTO.getRecordDt(), "recordDt");
        MemberPO memberPO = SessionTool.getWechatSession();
        dietRecordDTO.setMemberId(memberPO.getMemberId());
        dietRecordDTO.setOrigin(DietRecordConstant.ORIGIN_MINI_PROGRAM);
        dietRecordDTO.setOperationType(DietRecordConstant.OPERATION_TYPE_MEMBER);
        return Result.ok(dietRecordService.listDietRecordByDayForMiniProgram(dietRecordDTO));
    }

    /**
     * 获取饮食记录
     *
     * @param dietRecordDTO
     * @return
     */
    @RequestMapping("listDietRecord")
    public Result listDietRecord(DietRecordDTO dietRecordDTO) {
        MemberPO memberPO = SessionTool.getWechatSession();
        dietRecordDTO.setMemberId(memberPO.getMemberId());
        return Result.ok(dietRecordService.listDietRecord(dietRecordDTO));
    }

    /**
     * 删除饮食记录
     *
     * @param mainId
     * @return
     */
    @RequestMapping("deleteDietRecordByMainId")
    public Result deleteDietRecordByMainId(String mainId) {
        ValidateTool.checkParamIsNull(mainId, "mainId");
        dietRecordService.deleteDietRecordByMainId(mainId);
        return Result.ok();
    }

    @RequestMapping("listMemberRecord")
    public Result listMemberRecord(String recordDt){
        MemberPO memberPO = SessionTool.getWechatSession();
        String lastDt = DateHelper.plusDate(recordDt, -1);
        if(lastDt == null){
            throw new BusinessException("日期格式异常");
        }
        JSONObject obj = new JSONObject();
        DietRecordDTO dietDto = new DietRecordDTO();
        dietDto.setMemberId(memberPO.getMemberId());
        dietDto.setRecordDt(recordDt);
        obj.put("diet", dietRecordService.listDietRecord(dietDto));
        SportRecordDTO sportDto = new SportRecordDTO();
        sportDto.setMemberId(memberPO.getMemberId());
        sportDto.setRecordDt(recordDt);
        obj.put("sport", sportRecordService.listSportRecord(sportDto));
        DrugsRecordDTO drugDto = new DrugsRecordDTO();
        drugDto.setMemberId(memberPO.getMemberId());
        drugDto.setRecordDt(recordDt);
        obj.put("drug", drugsRecordService.listDrugsRecord(drugDto));
        JSONObject info = new JSONObject();
        JSONObject dietJson = dietRecordService.getDailyDietInfo(dietDto);
        info.put("heat", dietJson.getDouble("totalHeat"));
        dietDto.setRecordDt(lastDt);
        dietJson = dietRecordService.getDailyDietInfo(dietDto);
        info.put("lastHeat", dietJson.getDouble("totalHeat"));
        JSONObject drugJson = drugsRecordService.getDailyDrugInfo(drugDto);
        info.put("insulinUsage", drugJson.getDoubleValue("insulinUsage"));
        info.put("oralCount", drugJson.getDoubleValue("oralCount"));
        drugDto.setRecordDt(lastDt);
        drugJson = drugsRecordService.getDailyDrugInfo(drugDto);
        info.put("lastInsulinUsage", drugJson.getDoubleValue("insulinUsage"));
        info.put("lastOralCount", drugJson.getDoubleValue("oralCount"));
        obj.put("recordInfo", info);
        JSONObject sportJson = sportRecordService.getDailySportInfo(sportDto, memberPO.getWeight());
        info.put("costHeat", sportJson.getDoubleValue("costHeat"));
        sportDto.setRecordDt(lastDt);
        sportJson = sportRecordService.getDailySportInfo(sportDto, memberPO.getWeight());
        info.put("lastCostHeat", sportJson.getDoubleValue("costHeat"));
        return Result.ok(obj);
    }

}
