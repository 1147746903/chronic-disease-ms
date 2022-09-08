package com.comvee.cdms.dybloodsugar.dto;

/**
 * @author: chenhb
 * @description: 描述
 * @data: 2021/5/18 9:36
 **/
public class HasFewDataDTO {
    private CoordinateDTO pointHead;
    private Integer countHead;
    private CoordinateDTO pointTail;
    private Integer countTail;

    public CoordinateDTO getPointHead() {
        return pointHead;
    }

    public void setPointHead(CoordinateDTO pointHead) {
        this.pointHead = pointHead;
    }

    public Integer getCountHead() {
        return countHead;
    }

    public void setCountHead(Integer countHead) {
        this.countHead = countHead;
    }

    public CoordinateDTO getPointTail() {
        return pointTail;
    }

    public void setPointTail(CoordinateDTO pointTail) {
        this.pointTail = pointTail;
    }

    public Integer getCountTail() {
        return countTail;
    }

    public void setCountTail(Integer countTail) {
        this.countTail = countTail;
    }
}
