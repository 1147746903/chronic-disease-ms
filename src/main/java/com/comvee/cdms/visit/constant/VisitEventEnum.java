package com.comvee.cdms.visit.constant;


/**
 * @Author linr
 * @Date 2022/2/25
 */
public enum VisitEventEnum {

    MEMBER_JOIN_HOSPITAL(1,"患者建档","患者：{0}在{1}纳入管理"),
    MEMBER_TIME_CHECK_CHANGE(2,"定期检查改变","患者：{0}在{1}做了检查项目：{2}【查看】，下次复查时间：{3}"),
    MEMBER_REFERRAL_MESSAGE(3,"转诊信息","患者：{0}，由{1}转诊到{2}"),
    MEMBER_FINISH_SCREEN(4,"完成并发症筛查","患者：{0}在{1}完成了并发症筛查项目：{2}【查看】"),
    MEMBER_TCM_ASSESS(5,"中医评估","患者：{0}在{1}完成：中医健康评估【查看】"),
    MEMBER_IN_HOSPITAL(6,"患者住院","患者：{0}在{1}{2}科室住院"),
    MEMBER_OUT_HOSPITAL(7,"患者出院","患者：{0}在{1}{2}科室出院"),
    MEMBER_OUTPATIENT_TREATMENT(8,"门诊就诊","患者：{0}在{1}门诊就诊")
    ;


    private Integer eventType;
    private String eventDesc;
    private String eventDetail;

    VisitEventEnum(Integer eventType, String eventDesc, String eventDetail) {
        this.eventType = eventType;
        this.eventDesc = eventDesc;
        this.eventDetail = eventDetail;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }

    public static String getDesc(Integer eventType){
        VisitEventEnum[] values = VisitEventEnum.values();
        for (VisitEventEnum val: values) {
            if(val.eventType.equals(eventType) ){
                return val.eventDesc;
            }
        }
        return null;
    }

    public static String getDetail(Integer eventType){
        VisitEventEnum[] values = VisitEventEnum.values();
        for (VisitEventEnum val: values) {
            if(val.eventType.equals(eventType) ){
                return val.eventDetail;
            }
        }
        return null;
    }

}
