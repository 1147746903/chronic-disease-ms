package com.comvee.cdms.sign.bo;

public class BloodAuthorityBO {
    private String inHos; //住院权限
    private String   virtualWard; //虚拟病区权限

    public String getInHos() {
        return inHos;
    }

    public void setInHos(String inHos) {
        this.inHos = inHos;
    }

    public String getVirtualWard() {
        return virtualWard;
    }

    public void setVirtualWard(String virtualWard) {
        this.virtualWard = virtualWard;
    }

    @Override
    public String toString() {
        return "BloodAuthorityBO{" +
                "inHos='" + inHos + '\'' +
                ", virtualWard='" + virtualWard + '\'' +
                '}';
    }
}
