package com.comvee.cdms.checkresult.controller.web;

import com.comvee.cdms.checkresult.dto.ListMemberCheckRemindDTO;
import com.comvee.cdms.checkresult.dto.ModifyCheckRemindDTO;
import com.comvee.cdms.checkresult.po.CheckRemindPO;
import com.comvee.cdms.checkresult.service.CheckRemindService;
import com.comvee.cdms.checkresult.vo.CheckRemindVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/checkRemind")
@RequiresUser
public class WebCheckRemindController {

    @Autowired
    private CheckRemindService checkRemindService;

    /**
     * 加载本院的检查提醒（工作台待办事项）
     *
     * @param
     * @return
     */
    @RequestMapping("listDoctorCheckRemind")
    public Result listPatientCheckRemind(String startDt, String endDt,PageRequest page) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        PageResult<CheckRemindVO> pageResult = this.checkRemindService.listDoctorCheckRemind(doctorSessionBO.getHospitalId(), startDt,endDt,page);
        return Result.ok(pageResult);
    }

    /**
     * 修改检查提醒
     * @param checkRemindPO
     * @return
     */
    @RequestMapping("modifyCheckRemind")
    public Result modifyCheckRemind(@Validated ModifyCheckRemindDTO dto) {
        this.checkRemindService.modifyCheckRemind(dto);
        return Result.ok();
    }

    /**
     * 加载患者检查提醒
     * @return
     */
    @RequestMapping("listMemberCheckRemind")
    public Result listMemberCheckRemind(@Validated ListMemberCheckRemindDTO dto){
        List<CheckRemindPO> result = this.checkRemindService.listMemberCheckRemind(dto);
        return Result.ok(result);
    }


}
