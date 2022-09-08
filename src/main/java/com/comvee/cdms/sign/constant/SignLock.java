package com.comvee.cdms.sign.constant;

import java.util.concurrent.locks.ReentrantLock;

public class SignLock {

    public static final ReentrantLock BLOOD_SUGAR_LOCK = new ReentrantLock();

    public static final ReentrantLock WEB_ADD_BLOOD_SUGAR_LOCK = new ReentrantLock();
}
