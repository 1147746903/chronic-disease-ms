package com.comvee.cdms.visit.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.visit.dto.AddVistiEventDTO;
import com.comvee.cdms.visit.dto.GetListVisitEventDTO;
import com.comvee.cdms.visit.po.VisitEventPO;

import java.util.List;

/**
 * @Author linr
 * @Date 2022/2/25
 */
public interface VisitEventService {

    void addVisitEvent(AddVistiEventDTO addDTO);

    PageResult pageVisitEventList(GetListVisitEventDTO getListVisitEventDTO, PageRequest pr);
}
