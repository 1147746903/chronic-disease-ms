package com.comvee.cdms.doctor.bo;

public class DoctorDataAuthBO {
    private String type; //权限类型(1:个人 2:全员的 3:科室的)
    private String list; //权限

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "DoctorDataAuthBO{" +
                "type='" + type + '\'' +
                ", list='" + list + '\'' +
                '}';
    }
}
