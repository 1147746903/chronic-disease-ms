package com.comvee.cdms.dybloodsugar.contorller.back;


import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodsugar.po.GreenStarPlanCoursePO;
import com.comvee.cdms.dybloodsugar.service.GreenStarPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/back/greenStar")
public class BackGreenStarController {
    @Autowired
    private GreenStarPlanService greenStarPlanService;

    @RequestMapping("/deleteGreenStarPlanById")
    public Result deleteGreenStarPlanById(String sid){
        ValidateTool.checkParamIsNull(sid, "sid");
        greenStarPlanService.deleteGreenStarPlanById(sid);
        return Result.ok();
    }

    @RequestMapping("/listGreenStarPlanCourse")
    public Result listGreenStarPlanCourse(){
        return Result.ok(greenStarPlanService.listGreenStarPlanCourse());
    }

    @RequestMapping("/updateGreenStarPlanCourse")
    public Result updateGreenStarPlanCourse(GreenStarPlanCoursePO course){
       greenStarPlanService.updateGreenStarPlanCourse(course);
        return Result.ok();
    }
}
