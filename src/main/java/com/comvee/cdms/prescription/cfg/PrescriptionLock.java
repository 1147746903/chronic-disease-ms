package com.comvee.cdms.prescription.cfg;

import java.util.concurrent.locks.ReentrantLock;

public class PrescriptionLock {

    /**
     * 下发管理处方锁
     */
    public final static ReentrantLock HAND_DOWN_PRESCRIPTION_LOCK = new ReentrantLock();
}
