package com.comvee.cdms.visit.controller.other;

import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.visit.dto.AddVistiEventDTO;
import com.comvee.cdms.visit.service.VisitEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sync/visitEvent")
public class SyncVisitEventController {

    @Autowired
    private VisitEventService visitEventService;

    /**
     * 添加就诊事件
     * @param addDTO
     * @return
     */
    @RequestMapping("addVisitEvent")
    public Result addVisitEvent(@Validated AddVistiEventDTO addDTO){
        this.visitEventService.addVisitEvent(addDTO);
        return Result.ok();
    }
}
