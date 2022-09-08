package com.comvee.cdms.dialogue.constant;

/**
 * @author ZhiGe
 * @description
 * @date 2018/1/30 15:19 create
 */
public class DialogueConstant {

    /**
     * 消息展示客户端类型 1  医生 2 患者    3  全部
     */
    public static final int DIALOGUE_SHOW_CLIENT_DOCTOR = 1;
    public static final int DIALOGUE_SHOW_CLIENT_PATIENT = 2;
    public static final int DIALOGUE_SHOW_CLIENT_ALL = 3;

    /**
     * 消息所有者 1 患者 2  医生
     */
    public static final int DIALOGUE_OWNER_TYPE_MEMBER = 1;
    public static final int DIALOGUE_OWNER_TYPE_DOCTOR = 2;

    /**
     * 消息展示位置类型  1 只首页 2 只内部对话 3 全部
     */
    public static final int DIALOGUE_SHOW_TYPE_HOME = 1;
    public static final int DIALOGUE_SHOW_TYPE_INSIDE = 2;
    public static final int DIALOGUE_SHOW_TYPE_ALL = 3;

    /**
     * 普通消息类型 1  文本(随访下发) 2  图片（随访完成）  3 语音 0保留类型
     */
    public static final int DIALOGUE_TEXT_TYPE_TEXT = 1;
    public static final int DIALOGUE_TEXT_TYPE_IMAGE = 2;
    public static final int DIALOGUE_TEXT_TYPE_VOICE = 3;
    public static final int DIALOGUE_TEXT_TYPE_NO = 0;

    /**
     * 消息类型 1普通对话 2 体征异常 3管理处方 4随访消息  5控制目标 6监测方案 11系统消息(普通)
     *         13 患教知识 14 套餐过期提醒 15 套餐即将过期警告 16 新增套餐提醒 17 自动回复(添加患者)
     *         18 动态血糖报告提醒 19电子病历下发通知
     */
    public static final int DIALOGUE_MSG_TYPE = 1;
    public static final int DIALOGUE_MSG_TYPE_SIGN_ABNORMAL = 2;
    public static final int DIALOGUE_TARGET_MSG_TYPE = 5;
    public static final int DIALOGUE_PRESCRIPT_MSG_TYPE = 3;
    public static final int DIALOGUE_MONITORPLAN_MSG_TYPE = 6;
    public static final int DIALOGUE_FOLLOW_MSG_TYPE = 4;
    public static final int DIALOGUE_SYSTEM_MSG_TYPE = 11;
    public static final int DIALOGUE_ARTICLE_MSG_TYPE = 13;
    public static final int DIALOGUE_PACKAGE_EXPIRE_REMIND_MSG_TYPE = 14;
    public static final int DIALOGUE_PACKAGE_EXPIRE_WARN_MSG_TYPE = 15;
    public static final int DIALOGUE_ADD_PACKAGE_MSG_TYPE = 16;
    public static final int DIALOGUE_ADD_MEMBER_MSG_TYPE = 17;
    public static final int DIALOGUE_DY_REPORT_MSG_TYPE = 18;
    public static final int DIALOGUE_SEND_CASE_MSG_TYPE = 19;

    /**
     * 系统消息子类型 1 添加医患关系
     */
    public static final int DIALOGUE_SYSTEM_MSG_TEXT_TYPE_MEMBER_DOCTOR = 1;
    /**
     * 系统消息子类型 2 撤回系统消息
     */
    public static final int DIALOGUE_SYSTEM_MSG_TEXT_TYPE_RECALL_DOCTOR = 2;

    /**
     * 访问类型 1  患者 2  医生
     */
    public static final int DIALOGUE_VISIT_OWNER_MEMBER = 1;
    public static final int DIALOGUE_VISIT_OWNER_DOCTOR = 2;

    /**
     * 对话-随访子类型 1  下发问卷 2 评估报告  3随访总结报告
     */
    public static final int DIALOGUE_FOLLOW_TEXT_TYPE_QUESTION = 1;
    public static final int DIALOGUE_FOLLOW_TEXT_TYPE_REPORT = 2;
    public static final int DIALOGUE_FOLLOW_TEXT_TYPE_ZJ = 3;

    /**
     * 删除状态 0 正常 1 已被删除
     */
    public static final int DIALOGUE_BE_DELETE_NORMAL = 0;
    public static final int DIALOGUE_BE_DELETE_DELETE = 1;

}
