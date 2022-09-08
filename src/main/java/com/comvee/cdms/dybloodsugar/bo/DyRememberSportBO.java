package com.comvee.cdms.dybloodsugar.bo;

import java.util.List;

public class DyRememberSportBO {
    private List<String> otherSport; //其他的运动方式
    private List<DyRememberSportMethodBO> sportMethodBOList; //运动方式

    public List<String> getOtherSport() {
        return otherSport;
    }

    public void setOtherSport(List<String> otherSport) {
        this.otherSport = otherSport;
    }

    public List<DyRememberSportMethodBO> getSportMethodBOList() {
        return sportMethodBOList;
    }

    public void setSportMethodBOList(List<DyRememberSportMethodBO> sportMethodBOList) {
        this.sportMethodBOList = sportMethodBOList;
    }
}
