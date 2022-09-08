package com.comvee.cdms.actiontrack.core;

import com.comvee.cdms.admin.model.po.AdminPO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import org.springframework.stereotype.Service;

@Service("sessionSupport")
public class SessionSupportImpl implements SessionSupport {

    /**
     * 用户身份 1 医生 2患者 3 管理员
     */
    public static final int IDENTIFY_DOCTOR = 1;
    public static final int IDENTIFY_MEMBER = 2;
    public static final int IDENTIFY_ADMIN = 3;

    @Override
    public Integer getIdentify(Object sessionObject) {
        Integer result = null;
        if(sessionObject instanceof DoctorSessionBO){
            result = IDENTIFY_DOCTOR;
        }else if(sessionObject instanceof UserWechatJoinPO){
            result = IDENTIFY_MEMBER;
        }else if(sessionObject instanceof AdminPO){
            result = IDENTIFY_ADMIN;
        }
        return result;
    }

    @Override
    public String getUid(Object sessionObject) {
        String result = null;
        if(sessionObject instanceof DoctorSessionBO){
            result = ((DoctorSessionBO) sessionObject).getDoctorId();
        }else if(sessionObject instanceof UserWechatJoinPO){
            result = ((UserWechatJoinPO) sessionObject).getForeignId();
        }else if(sessionObject instanceof AdminPO){
            result = ((AdminPO) sessionObject).getAdminId();
        }
        return result;
    }
}
