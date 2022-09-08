package com.comvee.cdms.other.controller.back;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.other.po.AreaPO;
import com.comvee.cdms.other.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
@RestController
@RequestMapping("/back/area")
public class BackAreaController {

    @Autowired
    private AreaService areaService;

    /**
     * 加载区域信息
     * @param areaPid
     * @param rank
     * @return
     */
    @RequestMapping("listArea")
    public Result listArea(String areaPid, Integer rank){
        ValidateTool.checkParamIsNull(rank, "rank");
        List<AreaPO> list = this.areaService.listArea(areaPid, rank);
        return Result.ok(list);
    }
}
