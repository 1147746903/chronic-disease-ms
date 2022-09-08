package com.comvee.cdms.bloodmonitor.dto;

import java.util.List;

/**
 * @author wyc
 * @date 2019/6/18 15:07
 */
public class DeleteMonitorTemplateDTO {

    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
