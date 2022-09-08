package com.comvee.cdms.doctor.dto;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/12
 */
public class WebUpdateDoctorDTO implements Serializable{

    private String phoneNo;
    private String skilled;
    private String brief;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSkilled() {
        return skilled;
    }

    public void setSkilled(String skilled) {
        this.skilled = skilled;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
