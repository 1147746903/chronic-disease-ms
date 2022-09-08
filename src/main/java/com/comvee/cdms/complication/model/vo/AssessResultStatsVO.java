package com.comvee.cdms.complication.model.vo;

import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/6/14
 */
public class AssessResultStatsVO {

    private Long total;
    private Map<String ,Long> detailStats;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Map<String, Long> getDetailStats() {
        return detailStats;
    }

    public void setDetailStats(Map<String, Long> detailStats) {
        this.detailStats = detailStats;
    }
}
