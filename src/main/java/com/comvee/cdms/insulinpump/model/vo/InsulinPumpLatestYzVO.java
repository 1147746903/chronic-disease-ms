package com.comvee.cdms.insulinpump.model.vo;

public class InsulinPumpLatestYzVO {

    private String insulinName; //胰岛素名字
    private String yzItemName;
    private String startDt;
    private String stopDt;
    private String extData;
    private String checkDt;
    private String checkerName;
    private String yzExplain;


    public String getInsulinName() {
        return insulinName;
    }

    public void setInsulinName(String insulinName) {
        this.insulinName = insulinName;
    }

    public String getYzItemName() {
        return yzItemName;
    }

    public void setYzItemName(String yzItemName) {
        this.yzItemName = yzItemName;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getStopDt() {
        return stopDt;
    }

    public void setStopDt(String stopDt) {
        this.stopDt = stopDt;
    }

    public String getExtData() {
        return extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public String getCheckDt() {
        return checkDt;
    }

    public void setCheckDt(String checkDt) {
        this.checkDt = checkDt;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getYzExplain() {
        return yzExplain;
    }

    public void setYzExplain(String yzExplain) {
        this.yzExplain = yzExplain;
    }


    @Override
    public String toString() {
        return "InsulinPumpLatestYzVO{" +
                "insulinName='" + insulinName + '\'' +
                ", yzItemName='" + yzItemName + '\'' +
                ", startDt='" + startDt + '\'' +
                ", stopDt='" + stopDt + '\'' +
                ", extData='" + extData + '\'' +
                ", checkDt='" + checkDt + '\'' +
                ", checkerName='" + checkerName + '\'' +
                ", yzExplain='" + yzExplain + '\'' +

                '}';
    }
}
