package com.comvee.cdms.member.constant;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: suyz
 * @date: 2019/8/2
 */
public class MemberLock {

    /**
     * 添加患者锁
     */
    public static final ReentrantLock ADD_MEMBER_LOCK = new ReentrantLock();

    /**
     * 提交检查提醒锁
     */
    public static final ReentrantLock CHECK_REMIND_LOCK = new ReentrantLock();

    /**
     * 添加患者锁
     */
    public static final ReentrantLock UPDATE_MEMBER_LOCK = new ReentrantLock();

    /**
     * 医患关系锁
     */
    public static final ReentrantLock DOCTOR_MEMBER_RELATION_LOCK = new ReentrantLock();

    /**
     * 床位锁
     */
    public static final ReentrantLock BED_RELATION_LOCK = new ReentrantLock();


}
