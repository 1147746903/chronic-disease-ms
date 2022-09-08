package com.comvee.cdms.sign.controller.back;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.sign.dto.ListAllBloodSugarDTO;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.service.BloodSugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author wyc
 * @date 2019/4/15 18:05
 */
@RestController
@RequestMapping("/back/bloodSugar")
public class BackBloodSugarController {

    @Autowired
    private BloodSugarService bloodSugarService;
    /**
     * 加载患者所有血糖记录
     * @param listBloodSugarDTO
     * @param page
     * @return
     */
    @RequestMapping("listAllMemberBloodSugar")
    public Result listAllMemberBloodSugar(String memberId, PageRequest page){
        ValidateTool.checkParameterIsNull(memberId,"memberId");
        PageResult<ListAllBloodSugarDTO> pageResult = this.bloodSugarService.listAllMemberBloodSugar(memberId, page);
        return new Result(pageResult);
    }


    /**
     * 删除患者血糖记录-所有
     * @param memberId
     * @return
     */
    @RequestMapping("deleteMemberBloodSugar")
    public Result deletMemberBloodSugar(String sid){
        ValidateTool.checkParameterIsNull(sid,"sid");
//        ValidateTool.checkParamIsNull(memberId, "memberId");
//        ValidateTool.checkParamIsNull(recordDt, "recordDt");
        Result result = new Result();
        BloodSugarPO bloodSugar = bloodSugarService.getBloodSugar(sid);
        if(bloodSugar ==null){
            throw new BusinessException("血糖记录不存在");
        }
        if(this.bloodSugarService.deleteBloodSugar(sid,bloodSugar.getMemberId(),bloodSugar.getRecordDt())){
            result.setCode("0");
            result.setMessage("删除成功");
        } else {
            result.setMessage("删除失败");
        }
        return result;
    }

    @RequestMapping("test")
    public Result test(ListBloodSugarDTO li){
        List<Map<String, Object>> list = this.bloodSugarService.listBloodSugarByDate(li);
        return new Result(list);
    }


}
