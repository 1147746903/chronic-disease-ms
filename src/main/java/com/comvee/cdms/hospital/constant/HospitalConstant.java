package com.comvee.cdms.hospital.constant;

import com.comvee.cdms.common.cfg.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wyc
 * @date 2020/6/22 10:15
 */
public class HospitalConstant {

    /**
     * 1上级医院  2下级医院
     */
    public static final int DOWN_HOSPITAL = 0;
    public static final int UP_HOSPITAL = 1;


    /**
     * 医院编码
     */
    public final static Map<String,String> HOSPITAL_MAP = new HashMap<>();
    static {
        //政和县医院
        HOSPITAL_MAP.put("1",Config.getValueByKey("hospital.his.zhx"));
        //政和县中医院
        HOSPITAL_MAP.put("2",Config.getValueByKey("hospital.his.zhxzyy"));

    }

}
