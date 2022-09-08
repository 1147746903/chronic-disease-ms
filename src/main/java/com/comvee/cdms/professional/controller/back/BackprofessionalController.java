package com.comvee.cdms.professional.controller.back;

import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.professional.po.ProfessionalPO;
import com.comvee.cdms.professional.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wyc
 * @date 2019/5/13 11:00
 */
@RestController
@RequestMapping("/back/professional")
public class BackprofessionalController {

    @Autowired
    private ProfessionalService professionalService;

    /**
     * 新增职称
     * @param professionalPO
     * @return
     */
    @RequestMapping("addProfessional")
    public Result addProfessional(ProfessionalPO professionalPO) {
        this.professionalService.addProfessional(professionalPO);
        return new Result("添加成功");
    }

    /**
     * 加载职称列表
     * @return
     */
    @RequestMapping("listPrefessional")
    public List<ProfessionalPO> listPrefessional() {
        return this.professionalService.listPrefessional();
    }

    /**
     * 根据职称名称模糊查询职称
     * @param professionalName
     * @return
     */
    @RequestMapping("queryPrefessionalByName")
    public Result queryPrefessionalByName(String professionalName) {
        List<ProfessionalPO> list = this.professionalService.queryPrefessionalByName(professionalName);
        return new Result(list);
    }
}
