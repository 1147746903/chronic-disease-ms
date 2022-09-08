package com.comvee.cdms.foot.vo;

/**
 * @author: suyz
 * @date: 2019/6/13
 */
public class NurseAdviceVO {


    private Integer index;

    private String adviceText;
    private Integer check;
    private Integer editStatus;

    public NurseAdviceVO(){}

    public NurseAdviceVO(String adviceText , Integer check , Integer editStatus){
        this.adviceText = adviceText;
        this.check = check;
        this.editStatus = editStatus;
    }

    public NurseAdviceVO(String adviceText , Integer check ){
        this.adviceText = adviceText;
        this.check = check;
        this.editStatus = 1;
    }

    public NurseAdviceVO(Integer index ,String adviceText , Integer check){
        this.index = index;
        this.adviceText = adviceText;
        this.check = check;
        this.editStatus = 1;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getAdviceText() {
        return adviceText;
    }

    public void setAdviceText(String adviceText) {
        this.adviceText = adviceText;
    }

    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }
}
