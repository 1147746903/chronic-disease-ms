package com.comvee.cdms.dybloodsugar.po;

/**
 * @author hbj
 * @description
 * @date 2021/5/10 10:18
 */
public class DyRememberFoodPO {
    /**
     * 主键
     */
    private long id;
    /**
     * 食材名称
     */
    private String name;
    /**
     * 交换倍数
     */
    private double changeDelta;
    /**
     * 拼音首字母
     */
    private String spell;
    /**
     * 地区
     */
    private String addr;
    /**
     * 可食部分
     */
    private String ksbf;
    /**
     * 热量（每克的热量）千卡
     */
    private String heat;
    /**
     * 一份食物的克数
     */
    private double gram;
    /**
     * 图片地址
     */
    private String picture;
    /**
     * 食材类型
     * 谷薯类 1001001,蔬菜类1001003,水果类1001004,肉蛋类1001007,豆类1001002,浆乳类1001012,油脂类（烹饪油）1001013油脂类（坚果类）1001014
     */
    private long ingredientsType;
    /**
     * 单位
     */
    private String unit;
    /**
     * 排序权重
     */
    private long weight;
    /**
     * 酒精（ml）
     */
    private String alcoholMl;
    /**
     * 酒精（g）
     */
    private String alcoholG;
    /**
     * 水分
     */
    private String water;
    /**
     * 蛋白质
     */
    private String protein;
    /**
     * 脂肪
     */
    private String totalfats;
    /**
     * 膳食纤维(不溶性纤维)
     */
    private String dietaryfiber;
    /**
     * 碳水化合物
     */
    private String carbohydrates;
    /**
     * 胆固醇
     */
    private String dgc;
    /**
     * 灰分
     */
    private String ash;
    /**
     * 总维生素A
     */
    private String totalWss;
    /**
     * 胡萝卜素
     */
    private String carotene;
    /**
     * 视黄醇当量
     */
    private String shcdl;
    /**
     * 硫胺素
     */
    private String las;
    /**
     * 核黄素
     */
    private String hhs;
    /**
     * 尼克酸
     */
    private String nks;
    /**
     * 维生素c
     */
    private String wssc;
    /**
     * 维生素e
     */
    private String wsse;
    /**
     * 钙
     */
    private String gai;
    /**
     * 磷
     */
    private String phosphor;
    /**
     * 钾
     */
    private String kalium;
    /**
     * 钠
     */
    private String na;
    /**
     * 镁
     */
    private String mg;
    /**
     * 铁
     */
    private String tie;
    /**
     * 锌
     */
    private String zn;
    /**
     * 硒
     */
    private String se;
    /**
     * 铜
     */
    private String copper;
    /**
     * 锰
     */
    private String manganese;
    /**
     * gi
     */
    private String gi;
    /**
     * gl
     */
    private String gl;
    /**
     * 类别
     */
    private String leib;
    /**
     * 二级分类
     */
    private String twoType;
    private String twoName;
    /**
     * 一级分类
     */
    private String oneType;

    private String oneName;
    /**
     * 是否有效 0 无效 1 有效
     */
    private long isValid;
    /**
     * 添加时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;
    /**
     * 是否在用 0 否 1 是
     */
    private long isUse;

    /**
     * 食用建议
     */
    private String suggest;

    /**
     * 食用量
     */
    private String eatAmount;

    /**
     * 食物展示分类
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEatAmount() {
        return eatAmount;
    }

    public void setEatAmount(String eatAmount) {
        this.eatAmount = eatAmount;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getChangeDelta() {
        return changeDelta;
    }

    public void setChangeDelta(double changeDelta) {
        this.changeDelta = changeDelta;
    }


    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }


    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }


    public String getKsbf() {
        return ksbf;
    }

    public void setKsbf(String ksbf) {
        this.ksbf = ksbf;
    }


    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }


    public double getGram() {
        return gram;
    }

    public void setGram(double gram) {
        this.gram = gram;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public long getIngredientsType() {
        return ingredientsType;
    }

    public void setIngredientsType(long ingredientsType) {
        this.ingredientsType = ingredientsType;
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }


    public String getAlcoholMl() {
        return alcoholMl;
    }

    public void setAlcoholMl(String alcoholMl) {
        this.alcoholMl = alcoholMl;
    }


    public String getAlcoholG() {
        return alcoholG;
    }

    public void setAlcoholG(String alcoholG) {
        this.alcoholG = alcoholG;
    }


    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }


    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }


    public String getTotalfats() {
        return totalfats;
    }

    public void setTotalfats(String totalfats) {
        this.totalfats = totalfats;
    }


    public String getDietaryfiber() {
        return dietaryfiber;
    }

    public void setDietaryfiber(String dietaryfiber) {
        this.dietaryfiber = dietaryfiber;
    }


    public String getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = carbohydrates;
    }


    public String getDgc() {
        return dgc;
    }

    public void setDgc(String dgc) {
        this.dgc = dgc;
    }


    public String getAsh() {
        return ash;
    }

    public void setAsh(String ash) {
        this.ash = ash;
    }


    public String getTotalWss() {
        return totalWss;
    }

    public void setTotalWss(String totalWss) {
        this.totalWss = totalWss;
    }


    public String getCarotene() {
        return carotene;
    }

    public void setCarotene(String carotene) {
        this.carotene = carotene;
    }


    public String getShcdl() {
        return shcdl;
    }

    public void setShcdl(String shcdl) {
        this.shcdl = shcdl;
    }


    public String getLas() {
        return las;
    }

    public void setLas(String las) {
        this.las = las;
    }


    public String getHhs() {
        return hhs;
    }

    public void setHhs(String hhs) {
        this.hhs = hhs;
    }


    public String getNks() {
        return nks;
    }

    public void setNks(String nks) {
        this.nks = nks;
    }


    public String getWssc() {
        return wssc;
    }

    public void setWssc(String wssc) {
        this.wssc = wssc;
    }


    public String getWsse() {
        return wsse;
    }

    public void setWsse(String wsse) {
        this.wsse = wsse;
    }


    public String getGai() {
        return gai;
    }

    public void setGai(String gai) {
        this.gai = gai;
    }


    public String getPhosphor() {
        return phosphor;
    }

    public void setPhosphor(String phosphor) {
        this.phosphor = phosphor;
    }


    public String getKalium() {
        return kalium;
    }

    public void setKalium(String kalium) {
        this.kalium = kalium;
    }


    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }


    public String getMg() {
        return mg;
    }

    public void setMg(String mg) {
        this.mg = mg;
    }


    public String getTie() {
        return tie;
    }

    public void setTie(String tie) {
        this.tie = tie;
    }


    public String getZn() {
        return zn;
    }

    public void setZn(String zn) {
        this.zn = zn;
    }


    public String getSe() {
        return se;
    }

    public void setSe(String se) {
        this.se = se;
    }


    public String getCopper() {
        return copper;
    }

    public void setCopper(String copper) {
        this.copper = copper;
    }


    public String getManganese() {
        return manganese;
    }

    public void setManganese(String manganese) {
        this.manganese = manganese;
    }


    public String getGi() {
        return gi;
    }

    public void setGi(String gi) {
        this.gi = gi;
    }


    public String getGl() {
        return gl;
    }

    public void setGl(String gl) {
        this.gl = gl;
    }


    public String getLeib() {
        return leib;
    }

    public void setLeib(String leib) {
        this.leib = leib;
    }


    public String getTwoType() {
        return twoType;
    }

    public void setTwoType(String twoType) {
        this.twoType = twoType;
    }


    public String getTwoName() {
        return twoName;
    }

    public void setTwoName(String twoName) {
        this.twoName = twoName;
    }


    public String getOneType() {
        return oneType;
    }

    public void setOneType(String oneType) {
        this.oneType = oneType;
    }


    public String getOneName() {
        return oneName;
    }

    public void setOneName(String oneName) {
        this.oneName = oneName;
    }


    public long getIsValid() {
        return isValid;
    }

    public void setIsValid(long isValid) {
        this.isValid = isValid;
    }


    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }


    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }


    public long getIsUse() {
        return isUse;
    }

    public void setIsUse(long isUse) {
        this.isUse = isUse;
    }

    @Override
    public String toString() {
        return "DyRememberFoodPO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", changeDelta=" + changeDelta +
                ", spell='" + spell + '\'' +
                ", addr='" + addr + '\'' +
                ", ksbf='" + ksbf + '\'' +
                ", heat='" + heat + '\'' +
                ", gram=" + gram +
                ", picture='" + picture + '\'' +
                ", ingredientsType=" + ingredientsType +
                ", unit='" + unit + '\'' +
                ", weight=" + weight +
                ", alcoholMl='" + alcoholMl + '\'' +
                ", alcoholG='" + alcoholG + '\'' +
                ", water='" + water + '\'' +
                ", protein='" + protein + '\'' +
                ", totalfats='" + totalfats + '\'' +
                ", dietaryfiber='" + dietaryfiber + '\'' +
                ", carbohydrates='" + carbohydrates + '\'' +
                ", dgc='" + dgc + '\'' +
                ", ash='" + ash + '\'' +
                ", totalWss='" + totalWss + '\'' +
                ", carotene='" + carotene + '\'' +
                ", shcdl='" + shcdl + '\'' +
                ", las='" + las + '\'' +
                ", hhs='" + hhs + '\'' +
                ", nks='" + nks + '\'' +
                ", wssc='" + wssc + '\'' +
                ", wsse='" + wsse + '\'' +
                ", gai='" + gai + '\'' +
                ", phosphor='" + phosphor + '\'' +
                ", kalium='" + kalium + '\'' +
                ", na='" + na + '\'' +
                ", mg='" + mg + '\'' +
                ", tie='" + tie + '\'' +
                ", zn='" + zn + '\'' +
                ", se='" + se + '\'' +
                ", copper='" + copper + '\'' +
                ", manganese='" + manganese + '\'' +
                ", gi='" + gi + '\'' +
                ", gl='" + gl + '\'' +
                ", leib='" + leib + '\'' +
                ", twoType='" + twoType + '\'' +
                ", twoName='" + twoName + '\'' +
                ", oneType='" + oneType + '\'' +
                ", oneName='" + oneName + '\'' +
                ", isValid=" + isValid +
                ", insertDt=" + insertDt +
                ", modifyDt=" + modifyDt +
                ", isUse=" + isUse +
                '}';
    }
}
