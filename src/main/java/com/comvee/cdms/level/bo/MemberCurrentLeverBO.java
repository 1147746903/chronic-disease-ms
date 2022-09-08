package com.comvee.cdms.level.bo;

/**
 * @Author linr
 * @Date 2022/3/2
 */
public class MemberCurrentLeverBO {

    private Integer sugarLevel; //血糖分标
    private String sugarLevelDesc; //血糖分标中文
    private Integer pressureLevel;  //血压分级
    private String pressureLevelDesc;
    private Integer pressureLayer;//血压分层
    private String pressureLayerDesc;
    private String sugarConfirmDt;
    private String pressureConfirmDt;

    public Integer getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(Integer sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public String getSugarLevelDesc() {
        return sugarLevelDesc;
    }

    public void setSugarLevelDesc(String sugarLevelDesc) {
        this.sugarLevelDesc = sugarLevelDesc;
    }

    public Integer getPressureLevel() {
        return pressureLevel;
    }

    public void setPressureLevel(Integer pressureLevel) {
        this.pressureLevel = pressureLevel;
    }

    public String getPressureLevelDesc() {
        return pressureLevelDesc;
    }

    public void setPressureLevelDesc(String pressureLevelDesc) {
        this.pressureLevelDesc = pressureLevelDesc;
    }

    public Integer getPressureLayer() {
        return pressureLayer;
    }

    public void setPressureLayer(Integer pressureLayer) {
        this.pressureLayer = pressureLayer;
    }

    public String getPressureLayerDesc() {
        return pressureLayerDesc;
    }

    public void setPressureLayerDesc(String pressureLayerDesc) {
        this.pressureLayerDesc = pressureLayerDesc;
    }

    public String getSugarConfirmDt() {
        return sugarConfirmDt;
    }

    public void setSugarConfirmDt(String sugarConfirmDt) {
        this.sugarConfirmDt = sugarConfirmDt;
    }

    public String getPressureConfirmDt() {
        return pressureConfirmDt;
    }

    public void setPressureConfirmDt(String pressureConfirmDt) {
        this.pressureConfirmDt = pressureConfirmDt;
    }
}
