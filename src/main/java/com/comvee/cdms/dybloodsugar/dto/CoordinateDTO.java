package com.comvee.cdms.dybloodsugar.dto;

/**
 * @author: chenhb
 * @description: 描述
 * @data: 2021/5/17 16:46
 **/
public class CoordinateDTO {
    private Double x;
    private Double y;
    private String t;

    public CoordinateDTO() {

    }
    public CoordinateDTO(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
