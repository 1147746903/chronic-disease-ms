package com.comvee.cdms.consultation.constant;

public class RemoteConsultationConstant {

    /**
     * 会诊状态 1 未确认 2 会诊中 3 完成 4 过期
     */
    public static final int CONSULTATION_STATUS_UN_CONFIRM = 1;
    public static final int CONSULTATION_STATUS_ING = 2;
    public static final int CONSULTATION_STATUS_FINISH = 3;
    public static final int CONSULTATION_STATUS_OVERDUE = 4;

    /**
     * 提醒状态 1 提醒 0 不提醒
     */
    public static final int REMIND_STATUS_YES = 1;
    public static final int REMIND_STATUS_NO = 0;
}
