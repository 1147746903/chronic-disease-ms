package com.comvee.cdms.follow.cfg;

import java.util.concurrent.locks.ReentrantLock;

public class FollowLock {

    /**
     * 修改随访锁
     */
    public static final ReentrantLock MODIFY_FOLLOW_LOCK = new ReentrantLock();
}
