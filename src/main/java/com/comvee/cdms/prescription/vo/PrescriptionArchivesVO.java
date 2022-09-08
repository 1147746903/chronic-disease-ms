package com.comvee.cdms.prescription.vo;

/**
 * @author 李左河
 * @date 2018/8/20 14:32.
 */
public class PrescriptionArchivesVO {
    @Override
    public String toString() {
        return "PrescriptionArchivesVO{}";
    }

    //基本信息
    /**
     * 姓名
     */
    private String memberName;
    /**
     * 身高
     */
    private String height;
    /**
     * 孕前体重
     */
    private String progestationalWeight;
    /**
     * 当前体重
     */
    private String weight;
    /**
     * 预产期
     */
    private String expectBirthDate;
    /**
     * 胎数
     */
    private String bornNum;
    /**
     * 糖化血红蛋白
     */
    private String glycosylatedVal;
    /**
     * 糖化监测时间
     */
    private String glycosylatedValDate;
    //血糖情况
    /**
     * 凌晨
     */
    private String beforedawn;
    /**
     * 空腹
     */
    private String beforeBreakfast;
    /**
     * 早餐后
     */
    private String afterBreakfast;
    /**
     * 午餐前
     */
    private String beforeLunch;
    /**
     * 午餐后
     */
    private String afterLunch;
    /**
     * 晚餐前
     */
    private String beforeDinner;
    /**
     * 晚餐后
     */
    private String afterDinner;
    /**
     * 睡前
     */
    private String beforeSleep;
    //血常规
    /**
     * 血红蛋白
     */
    private String hemoglobin;
    /**
     * 平均血红细胞体积
     */
    private String avgRedBloodCell;
    /**
     * 平均血红蛋白量
     */
    private String avgHemoglobin;
    /**
     * 平均血红蛋白浓度
     */
    private String avgHemoglobinConcentration;
    /**
     * 血小板计数
     */
    private String platelet;
    /**
     * c反应蛋白
     */
    private String crp;
    /**
     * 血常规备注
     */
    private String bloodRoutineRemark;
    //肝肾功
    /**
     * 谷丙转氨酶
     */
    private String alt;
    /**
     * 谷草转氨酶
     */
    private String ast;
    /**
     * 总胆红素
     */
    private String tbil;
    /**
     * 总蛋白
     */
    private String tp;
    /**
     * 白蛋白
     */
    private String alb;
    /**
     * ca
     */
    private String ca;
    /**
     * 血尿素氮
     */
    private String bloodUreaNitrogen;
    /**
     * 血清肌酐
     */
    private String cr;
    /**
     * 肾功备注
     */
    private String renalRemark;
    //血脂
    /**
     * 总胆固醇
     */
    private String currentTCholesterol;
    /**
     * 甘油三脂
     */
    private String triglyceride;
    /**
     * 高密度蛋白
     */
    private String currentHDLCholesterol;
    /**
     * 低密度蛋白
     */
    private String currentLDLCholesterol;
    /**
     * 血脂备注
     */
    private String bloodLipidRemark;
    //其他
    /**
     * 糖耐
     */
    private String sugarTolerance;
    /**
     * 甲状腺功能
     */
    private String thyroidFunction;
    /**
     * 胰岛素分泌
     */
    private String insulinSecretion;

    private String idCard;//身份证号
    private String progestationalBMI;//孕前BMI
    private String lastMenstruation;//末次月经
    private String menstruationRate;//月经是否规则
    private String gestationalWeeks;//孕周
    private String pregnancyHistory;//妊娠史
    private String firstProductionInspectionDate;//首次产检时间
    private String bloodPressure;//血压
    private String cjWeight;//产检体重
    private String cqBMI;//产前BMI

    private String gestation;//妊娠反应
    private String fetalMovement;//初感胎动
    private String pregnantMonth;//孕月份
    private String yuntu;//孕吐
    private String ydcx;//阴道出血
    private String fever;//发热
    private String allergy;//过敏
    private String fybyy;//服用避孕药
    private String fyys;//服用叶酸
    private String smoke;//吸烟

    private String drink;//饮酒
    private String viralInfection;//病毒感染
    private String jcyhwz;//接触有害物质
    private String jcfsxwz;//接触放射性物质
    private String xcgWbc;//白细胞计数
    private String xcgRbc;//红细胞计数
    private String appearance;//外观
    private String nsjd;//尿酸碱度
    private String nbz;//尿比重
    private String rbc;//红细胞

    private String wbc;//白细胞
    private String nt;//尿糖
    private String ndb;//尿蛋白
    private String dhs;//胆红素
    private String ndy;//尿胆原
    private String tt;//酮体
    private String yxsy;//亚硝酸盐
    private String urineRoutineRemark;//尿常规备注
    private String alp;//碱性磷酸酶
    private String ua;//尿酸

    private String k;//钾
    private String na;//钠
    private String cl;//氯
    private String fe;//血清铁
    private String mg;//镁
    private String pHoS;//磷
    private String electrolyteRemark;//电解质备注
    private String ft3;//游离三碘甲状腺原氨酸

    private String ft4;//血清游离甲状腺素
    private String tsh;//促甲状腺激素
    private String tgab;//抗甲状腺球蛋白抗体
    private String tpoab;//抗甲状腺过氧化物酶抗体
    private String trab;//促甲状腺素受体抗体
    private String aob;//AOB血型
    private String rh;//RH血型
    private String hbsag;//乙肝表面抗原
    private String hbsab;//乙肝表面抗体

    private String hbeag;//乙肝e抗原
    private String hbeab;//乙肝e抗体
    private String hbcab;//乙肝核心抗体
    private String hcvag;//丙型肝炎核心抗原
    private String hcvab;//丙型肝炎抗体
    private String mdlxtkt;//梅毒螺旋体抗体
    private String khiv;//抗HIV
    private String nxqt;//凝血全套
    private String qwssd;//25羟维生素d
    private String systolicPressure;//收缩压
    private String diastolicPressure;//舒张压

    public String getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(String systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public String getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(String diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getProgestationalBMI() {
        return progestationalBMI;
    }

    public void setProgestationalBMI(String progestationalBMI) {
        this.progestationalBMI = progestationalBMI;
    }

    public String getLastMenstruation() {
        return lastMenstruation;
    }

    public void setLastMenstruation(String lastMenstruation) {
        this.lastMenstruation = lastMenstruation;
    }

    public String getMenstruationRate() {
        return menstruationRate;
    }

    public void setMenstruationRate(String menstruationRate) {
        this.menstruationRate = menstruationRate;
    }

    public String getGestationalWeeks() {
        return gestationalWeeks;
    }

    public void setGestationalWeeks(String gestationalWeeks) {
        this.gestationalWeeks = gestationalWeeks;
    }

    public String getPregnancyHistory() {
        return pregnancyHistory;
    }

    public void setPregnancyHistory(String pregnancyHistory) {
        this.pregnancyHistory = pregnancyHistory;
    }

    public String getFirstProductionInspectionDate() {
        return firstProductionInspectionDate;
    }

    public void setFirstProductionInspectionDate(String firstProductionInspectionDate) {
        this.firstProductionInspectionDate = firstProductionInspectionDate;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getCjWeight() {
        return cjWeight;
    }

    public void setCjWeight(String cjWeight) {
        this.cjWeight = cjWeight;
    }

    public String getCqBMI() {
        return cqBMI;
    }

    public void setCqBMI(String cqBMI) {
        this.cqBMI = cqBMI;
    }

    public String getGestation() {
        return gestation;
    }

    public void setGestation(String gestation) {
        this.gestation = gestation;
    }

    public String getFetalMovement() {
        return fetalMovement;
    }

    public void setFetalMovement(String fetalMovement) {
        this.fetalMovement = fetalMovement;
    }

    public String getPregnantMonth() {
        return pregnantMonth;
    }

    public void setPregnantMonth(String pregnantMonth) {
        this.pregnantMonth = pregnantMonth;
    }

    public String getYuntu() {
        return yuntu;
    }

    public void setYuntu(String yuntu) {
        this.yuntu = yuntu;
    }

    public String getYdcx() {
        return ydcx;
    }

    public void setYdcx(String ydcx) {
        this.ydcx = ydcx;
    }

    public String getFever() {
        return fever;
    }

    public void setFever(String fever) {
        this.fever = fever;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getFybyy() {
        return fybyy;
    }

    public void setFybyy(String fybyy) {
        this.fybyy = fybyy;
    }

    public String getFyys() {
        return fyys;
    }

    public void setFyys(String fyys) {
        this.fyys = fyys;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getViralInfection() {
        return viralInfection;
    }

    public void setViralInfection(String viralInfection) {
        this.viralInfection = viralInfection;
    }

    public String getJcyhwz() {
        return jcyhwz;
    }

    public void setJcyhwz(String jcyhwz) {
        this.jcyhwz = jcyhwz;
    }

    public String getJcfsxwz() {
        return jcfsxwz;
    }

    public void setJcfsxwz(String jcfsxwz) {
        this.jcfsxwz = jcfsxwz;
    }

    public String getXcgWbc() {
        return xcgWbc;
    }

    public void setXcgWbc(String xcgWbc) {
        this.xcgWbc = xcgWbc;
    }

    public String getXcgRbc() {
        return xcgRbc;
    }

    public void setXcgRbc(String xcgRbc) {
        this.xcgRbc = xcgRbc;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getNsjd() {
        return nsjd;
    }

    public void setNsjd(String nsjd) {
        this.nsjd = nsjd;
    }

    public String getNbz() {
        return nbz;
    }

    public void setNbz(String nbz) {
        this.nbz = nbz;
    }

    public String getRbc() {
        return rbc;
    }

    public void setRbc(String rbc) {
        this.rbc = rbc;
    }

    public String getWbc() {
        return wbc;
    }

    public void setWbc(String wbc) {
        this.wbc = wbc;
    }

    public String getNt() {
        return nt;
    }

    public void setNt(String nt) {
        this.nt = nt;
    }

    public String getNdb() {
        return ndb;
    }

    public void setNdb(String ndb) {
        this.ndb = ndb;
    }

    public String getDhs() {
        return dhs;
    }

    public void setDhs(String dhs) {
        this.dhs = dhs;
    }

    public String getNdy() {
        return ndy;
    }

    public void setNdy(String ndy) {
        this.ndy = ndy;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    public String getYxsy() {
        return yxsy;
    }

    public void setYxsy(String yxsy) {
        this.yxsy = yxsy;
    }

    public String getUrineRoutineRemark() {
        return urineRoutineRemark;
    }

    public void setUrineRoutineRemark(String urineRoutineRemark) {
        this.urineRoutineRemark = urineRoutineRemark;
    }

    public String getAlp() {
        return alp;
    }

    public void setAlp(String alp) {
        this.alp = alp;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public String getFe() {
        return fe;
    }

    public void setFe(String fe) {
        this.fe = fe;
    }

    public String getMg() {
        return mg;
    }

    public void setMg(String mg) {
        this.mg = mg;
    }

    public String getPHoS() {
        return pHoS;
    }

    public void setPHoS(String pHoS) {
        this.pHoS = pHoS;
    }

    public String getElectrolyteRemark() {
        return electrolyteRemark;
    }

    public void setElectrolyteRemark(String electrolyteRemark) {
        this.electrolyteRemark = electrolyteRemark;
    }

    public String getFt3() {
        return ft3;
    }

    public void setFt3(String ft3) {
        this.ft3 = ft3;
    }

    public String getFt4() {
        return ft4;
    }

    public void setFt4(String ft4) {
        this.ft4 = ft4;
    }

    public String getTsh() {
        return tsh;
    }

    public void setTsh(String tsh) {
        this.tsh = tsh;
    }

    public String getTgab() {
        return tgab;
    }

    public void setTgab(String tgab) {
        this.tgab = tgab;
    }

    public String getTpoab() {
        return tpoab;
    }

    public void setTpoab(String tpoab) {
        this.tpoab = tpoab;
    }

    public String getTrab() {
        return trab;
    }

    public void setTrab(String trab) {
        this.trab = trab;
    }

    public String getAob() {
        return aob;
    }

    public void setAob(String aob) {
        this.aob = aob;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getHbsag() {
        return hbsag;
    }

    public void setHbsag(String hbsag) {
        this.hbsag = hbsag;
    }

    public String getHbsab() {
        return hbsab;
    }

    public void setHbsab(String hbsab) {
        this.hbsab = hbsab;
    }

    public String getHbeag() {
        return hbeag;
    }

    public void setHbeag(String hbeag) {
        this.hbeag = hbeag;
    }

    public String getHbeab() {
        return hbeab;
    }

    public void setHbeab(String hbeab) {
        this.hbeab = hbeab;
    }

    public String getHbcab() {
        return hbcab;
    }

    public void setHbcab(String hbcab) {
        this.hbcab = hbcab;
    }

    public String getHcvag() {
        return hcvag;
    }

    public void setHcvag(String hcvag) {
        this.hcvag = hcvag;
    }

    public String getHcvab() {
        return hcvab;
    }

    public void setHcvab(String hcvab) {
        this.hcvab = hcvab;
    }

    public String getMdlxtkt() {
        return mdlxtkt;
    }

    public void setMdlxtkt(String mdlxtkt) {
        this.mdlxtkt = mdlxtkt;
    }

    public String getKhiv() {
        return khiv;
    }

    public void setKhiv(String khiv) {
        this.khiv = khiv;
    }

    public String getNxqt() {
        return nxqt;
    }

    public void setNxqt(String nxqt) {
        this.nxqt = nxqt;
    }

    public String getQwssd() {
        return qwssd;
    }

    public void setQwssd(String qwssd) {
        this.qwssd = qwssd;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getProgestationalWeight() {
        return progestationalWeight;
    }

    public void setProgestationalWeight(String progestationalWeight) {
        this.progestationalWeight = progestationalWeight;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getExpectBirthDate() {
        return expectBirthDate;
    }

    public void setExpectBirthDate(String expectBirthDate) {
        this.expectBirthDate = expectBirthDate;
    }

    public String getBornNum() {
        return bornNum;
    }

    public void setBornNum(String bornNum) {
        this.bornNum = bornNum;
    }

    public String getGlycosylatedVal() {
        return glycosylatedVal;
    }

    public void setGlycosylatedVal(String glycosylatedVal) {
        this.glycosylatedVal = glycosylatedVal;
    }

    public String getGlycosylatedValDate() {
        return glycosylatedValDate;
    }

    public void setGlycosylatedValDate(String glycosylatedValDate) {
        this.glycosylatedValDate = glycosylatedValDate;
    }

    public String getBeforedawn() {
        return beforedawn;
    }

    public void setBeforedawn(String beforedawn) {
        this.beforedawn = beforedawn;
    }

    public String getBeforeBreakfast() {
        return beforeBreakfast;
    }

    public void setBeforeBreakfast(String beforeBreakfast) {
        this.beforeBreakfast = beforeBreakfast;
    }

    public String getAfterBreakfast() {
        return afterBreakfast;
    }

    public void setAfterBreakfast(String afterBreakfast) {
        this.afterBreakfast = afterBreakfast;
    }

    public String getBeforeLunch() {
        return beforeLunch;
    }

    public void setBeforeLunch(String beforeLunch) {
        this.beforeLunch = beforeLunch;
    }

    public String getAfterLunch() {
        return afterLunch;
    }

    public void setAfterLunch(String afterLunch) {
        this.afterLunch = afterLunch;
    }

    public String getBeforeDinner() {
        return beforeDinner;
    }

    public void setBeforeDinner(String beforeDinner) {
        this.beforeDinner = beforeDinner;
    }

    public String getAfterDinner() {
        return afterDinner;
    }

    public void setAfterDinner(String afterDinner) {
        this.afterDinner = afterDinner;
    }

    public String getBeforeSleep() {
        return beforeSleep;
    }

    public void setBeforeSleep(String beforeSleep) {
        this.beforeSleep = beforeSleep;
    }

    public String getHemoglobin() {
        return hemoglobin;
    }

    public void setHemoglobin(String hemoglobin) {
        this.hemoglobin = hemoglobin;
    }

    public String getAvgRedBloodCell() {
        return avgRedBloodCell;
    }

    public void setAvgRedBloodCell(String avgRedBloodCell) {
        this.avgRedBloodCell = avgRedBloodCell;
    }

    public String getAvgHemoglobin() {
        return avgHemoglobin;
    }

    public void setAvgHemoglobin(String avgHemoglobin) {
        this.avgHemoglobin = avgHemoglobin;
    }

    public String getAvgHemoglobinConcentration() {
        return avgHemoglobinConcentration;
    }

    public void setAvgHemoglobinConcentration(String avgHemoglobinConcentration) {
        this.avgHemoglobinConcentration = avgHemoglobinConcentration;
    }

    public String getPlatelet() {
        return platelet;
    }

    public void setPlatelet(String platelet) {
        this.platelet = platelet;
    }

    public String getCrp() {
        return crp;
    }

    public void setCrp(String crp) {
        this.crp = crp;
    }

    public String getBloodRoutineRemark() {
        return bloodRoutineRemark;
    }

    public void setBloodRoutineRemark(String bloodRoutineRemark) {
        this.bloodRoutineRemark = bloodRoutineRemark;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAst() {
        return ast;
    }

    public void setAst(String ast) {
        this.ast = ast;
    }

    public String getTbil() {
        return tbil;
    }

    public void setTbil(String tbil) {
        this.tbil = tbil;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getAlb() {
        return alb;
    }

    public void setAlb(String alb) {
        this.alb = alb;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getBloodUreaNitrogen() {
        return bloodUreaNitrogen;
    }

    public void setBloodUreaNitrogen(String bloodUreaNitrogen) {
        this.bloodUreaNitrogen = bloodUreaNitrogen;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public String getRenalRemark() {
        return renalRemark;
    }

    public void setRenalRemark(String renalRemark) {
        this.renalRemark = renalRemark;
    }

    public String getCurrentTCholesterol() {
        return currentTCholesterol;
    }

    public void setCurrentTCholesterol(String currentTCholesterol) {
        this.currentTCholesterol = currentTCholesterol;
    }

    public String getTriglyceride() {
        return triglyceride;
    }

    public void setTriglyceride(String triglyceride) {
        this.triglyceride = triglyceride;
    }

    public String getCurrentHDLCholesterol() {
        return currentHDLCholesterol;
    }

    public void setCurrentHDLCholesterol(String currentHDLCholesterol) {
        this.currentHDLCholesterol = currentHDLCholesterol;
    }

    public String getCurrentLDLCholesterol() {
        return currentLDLCholesterol;
    }

    public void setCurrentLDLCholesterol(String currentLDLCholesterol) {
        this.currentLDLCholesterol = currentLDLCholesterol;
    }

    public String getBloodLipidRemark() {
        return bloodLipidRemark;
    }

    public void setBloodLipidRemark(String bloodLipidRemark) {
        this.bloodLipidRemark = bloodLipidRemark;
    }

    public String getSugarTolerance() {
        return sugarTolerance;
    }

    public void setSugarTolerance(String sugarTolerance) {
        this.sugarTolerance = sugarTolerance;
    }

    public String getThyroidFunction() {
        return thyroidFunction;
    }

    public void setThyroidFunction(String thyroidFunction) {
        this.thyroidFunction = thyroidFunction;
    }

    public String getInsulinSecretion() {
        return insulinSecretion;
    }

    public void setInsulinSecretion(String insulinSecretion) {
        this.insulinSecretion = insulinSecretion;
    }
}
