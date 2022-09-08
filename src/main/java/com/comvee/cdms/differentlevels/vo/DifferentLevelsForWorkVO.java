package com.comvee.cdms.differentlevels.vo;

import java.io.Serializable;

/**
 * 工作台分层分级展示对象实体
 */
public class DifferentLevelsForWorkVO implements Serializable {

    /**
     * 患者编号
     */
    private String memberId;
    /**
     * 分层分级记录编号
     */
    private String sid;
    /**
     * 分层日期（yyyy-MM-dd）
     */
    private String changeDate;
    /**
     * 分层日期时间（yyyy-MM-dd HH:mm:ss）
     */
    private String changeDt;
    /**
     * 患者名称
     */
    private String memberName;
    /**
     * 患者性别
     */
    private String sex;
    /**
     * 患者年龄
     */
    private Integer age;
    /**
     * 本次分级分层
     */
    private Integer currentLayer;
    /**
     * 上一次分级分层
     */
    private Integer lastLayer;
    /**
     * 近一周平均空腹血糖
     */
    private String nwGlu0;
    /**
     * 近一周平均餐后血糖
     */
    private String nwGlu;
    /**
     * 糖化血红蛋白
     */
    private String hlb;
    /**
     * 近一周低血糖次数
     */
    private String nwLGlu;
    /**
     * 自我管理行为评分
     */
    private String zwglxwpf;
    /**
     * 糖尿病足部风险评估
     */
    private String tnbzfxpgdj;
    /**
     * 分级 1 一级支持 2 二级支持 3 三级支持
     */
    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangeDt() {
        return changeDt;
    }

    public void setChangeDt(String changeDt) {
        this.changeDt = changeDt;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getCurrentLayer() {
        return currentLayer;
    }

    public void setCurrentLayer(Integer currentLayer) {
        this.currentLayer = currentLayer;
    }

    public Integer getLastLayer() {
        return lastLayer;
    }

    public void setLastLayer(Integer lastLayer) {
        this.lastLayer = lastLayer;
    }

    public String getNwGlu0() {
        return nwGlu0;
    }

    public void setNwGlu0(String nwGlu0) {
        this.nwGlu0 = nwGlu0;
    }

    public String getNwGlu() {
        return nwGlu;
    }

    public void setNwGlu(String nwGlu) {
        this.nwGlu = nwGlu;
    }

    public String getHlb() {
        return hlb;
    }

    public void setHlb(String hlb) {
        this.hlb = hlb;
    }

    public String getNwLGlu() {
        return nwLGlu;
    }

    public void setNwLGlu(String nwLGlu) {
        this.nwLGlu = nwLGlu;
    }

    public String getZwglxwpf() {
        return zwglxwpf;
    }

    public void setZwglxwpf(String zwglxwpf) {
        this.zwglxwpf = zwglxwpf;
    }

    public String getTnbzfxpgdj() {
        return tnbzfxpgdj;
    }

    public void setTnbzfxpgdj(String tnbzfxpgdj) {
        this.tnbzfxpgdj = tnbzfxpgdj;
    }
}
