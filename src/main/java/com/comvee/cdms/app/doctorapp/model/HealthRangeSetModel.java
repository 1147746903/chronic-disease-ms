package com.comvee.cdms.app.doctorapp.model;

import java.io.Serializable;

public class HealthRangeSetModel implements Serializable{
	
    private static final long serialVersionUID = 1L;

    private String rangeId;//RANGE_ID   NUMBER(15)  Y           
    private String memberId;//MEMBER_ID NUMBER(15)  Y           成员id
    private String lowEmpty;//LOW_RANGE VARCHAR2(32)    Y           空腹范围下线
    private String highEmpty;//HIGH_RANGE   VARCHAR2(32)    Y           空腹范围上线
    private Integer isValid;//IS_VALID  NUMBER(1)   Y   1       1有效0无效
    private String insertDt;//INSERT_DT DATE    Y   sysdate     添加时间
    private String lowFull;//LOW_RANGE  VARCHAR2(32)    Y               餐后范围下线
    private String highFull;//HIGH_RANGE    VARCHAR2(32)    Y           餐后范围上线
    private String lowDiastolicPressure;//LOW_DIASTOLIC_PRESSURE    VARCHAR2(32)    Y           舒张压下限
    private String highDiastolicPressure;//HIGH_DIASTOLIC_PRESSURE  VARCHAR2(32)    Y           舒张压上限
    private String lowSystolicPressure;//LOW_SYSTOLIC_PRESSURE  VARCHAR2(32)    Y           收缩压下限
    private String highSystolicPressure;//HIGH_SYSTOLIC_PRESSURE    VARCHAR2(32)    Y           收缩压上限
    private String lowBmi;//LOW_BMI VARCHAR2(32)    Y           Bmi指数下限
    private String highBmi;//HIGH_BMI   VARCHAR2(32)    Y           Bmi指数上限
    private String lowTotalCholesterol;//LOW_TOTAL_CHOLESTEROL  VARCHAR2(32)    Y           总胆固醇下限
    private String highTotalCholesterol;//HIGH_TOTAL_CHOLESTEROL    VARCHAR2(32)    Y           总胆固醇上限
    private String highWeight;//体重指数上限
    private String lowWeight;//体重指数下限
    private String lowHemoglobin;//糖化血红蛋白下限 
    private String highHemoglobin;//糖化血红蛋白上限 
    private String totalCholesterol;//  总胆固醇TC上限
    private String totalCholesterolMin;//   总胆固醇TC下限
    private String triacylglycerol;//   三酰甘油上限
    private String triacylglycerolMin;//    三酰甘油下限上限
    private String lowCholesterol;//    低密度脂蛋白胆固醇上限
    private String lowCholesterolMin;// 低密度脂蛋白胆固醇下限
    private String highCholesterol;//   高密度脂蛋白胆固醇下限
    private String highCholesterolMax;//    高密度脂蛋白胆固醇上限
    private String modifyDt;   //修改时间
    //微信粉丝新增以下 
    private String fansId;      //粉丝id
    private String sugarType;//糖尿病类型
    private String beforebreakfastLow;// 空腹-低
    private String beforebreakfastHeight;// 空腹 - 高
    private String beforeMealLow;// 餐前 -低
    private String beforeMealHeight;// 餐前  - 高
    private String afterMealLow;// 餐后-低
    private String afterMealHeight;// 餐后  - 高
    private String beforesleepLow;// 睡前-低
    private String beforesleepHeight;// 睡前  - 高
    private String beforedawnLow;// 凌晨-低
    private String beforedawnHeight;// 凌晨  - 高
	

	public String getRangeId() {
		return rangeId;
	}
	public void setRangeId(String rangeId) {
		this.rangeId = rangeId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getLowEmpty() {
		return lowEmpty;
	}
	public void setLowEmpty(String lowEmpty) {
		this.lowEmpty = lowEmpty;
	}
	public String getHighEmpty() {
		return highEmpty;
	}
	public void setHighEmpty(String highEmpty) {
		this.highEmpty = highEmpty;
	}
	public String getLowFull() {
		return lowFull;
	}
	public void setLowFull(String lowFull) {
		this.lowFull = lowFull;
	}
	public String getHighFull() {
		return highFull;
	}
	public void setHighFull(String highFull) {
		this.highFull = highFull;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public String getInsertDt() {
		return insertDt;
	}
	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}
	public String getLowDiastolicPressure() {
		return lowDiastolicPressure;
	}
	public void setLowDiastolicPressure(String lowDiastolicPressure) {
		this.lowDiastolicPressure = lowDiastolicPressure;
	}
	public String getHighDiastolicPressure() {
		return highDiastolicPressure;
	}
	public void setHighDiastolicPressure(String highDiastolicPressure) {
		this.highDiastolicPressure = highDiastolicPressure;
	}
	public String getLowSystolicPressure() {
		return lowSystolicPressure;
	}
	public void setLowSystolicPressure(String lowSystolicPressure) {
		this.lowSystolicPressure = lowSystolicPressure;
	}
	public String getHighSystolicPressure() {
		return highSystolicPressure;
	}
	public void setHighSystolicPressure(String highSystolicPressure) {
		this.highSystolicPressure = highSystolicPressure;
	}
	public String getLowBmi() {
		return lowBmi;
	}
	public void setLowBmi(String lowBmi) {
		this.lowBmi = lowBmi;
	}
	public String getHighBmi() {
		return highBmi;
	}
	public void setHighBmi(String highBmi) {
		this.highBmi = highBmi;
	}
	public String getLowTotalCholesterol() {
		return lowTotalCholesterol;
	}
	public void setLowTotalCholesterol(String lowTotalCholesterol) {
		this.lowTotalCholesterol = lowTotalCholesterol;
	}
	public String getHighTotalCholesterol() {
		return highTotalCholesterol;
	}
	public void setHighTotalCholesterol(String highTotalCholesterol) {
		this.highTotalCholesterol = highTotalCholesterol;
	}
	public String getModifyDt() {
		return modifyDt;
	}
	public void setModifyDt(String modifyDt) {
		this.modifyDt = modifyDt;
	}
	public String getHighWeight() {
		return highWeight;
	}
	public void setHighWeight(String highWeight) {
		this.highWeight = highWeight;
	}
	public String getLowWeight() {
		return lowWeight;
	}
	public void setLowWeight(String lowWeight) {
		this.lowWeight = lowWeight;
	}
	public String getLowHemoglobin() {
		return lowHemoglobin;
	}
	public void setLowHemoglobin(String lowHemoglobin) {
		this.lowHemoglobin = lowHemoglobin;
	}
	public String getHighHemoglobin() {
		return highHemoglobin;
	}
	public void setHighHemoglobin(String highHemoglobin) {
		this.highHemoglobin = highHemoglobin;
	}
    public String getTotalCholesterol() {
        return totalCholesterol;
    }
    public void setTotalCholesterol(String totalCholesterol) {
        this.totalCholesterol = totalCholesterol;
    }
    public String getTotalCholesterolMin() {
        return totalCholesterolMin;
    }
    public void setTotalCholesterolMin(String totalCholesterolMin) {
        this.totalCholesterolMin = totalCholesterolMin;
    }
    public String getTriacylglycerol() {
        return triacylglycerol;
    }
    public void setTriacylglycerol(String triacylglycerol) {
        this.triacylglycerol = triacylglycerol;
    }
    public String getTriacylglycerolMin() {
        return triacylglycerolMin;
    }
    public void setTriacylglycerolMin(String triacylglycerolMin) {
        this.triacylglycerolMin = triacylglycerolMin;
    }
    public String getLowCholesterol() {
        return lowCholesterol;
    }
    public void setLowCholesterol(String lowCholesterol) {
        this.lowCholesterol = lowCholesterol;
    }
    public String getLowCholesterolMin() {
        return lowCholesterolMin;
    }
    public void setLowCholesterolMin(String lowCholesterolMin) {
        this.lowCholesterolMin = lowCholesterolMin;
    }
    public String getHighCholesterol() {
        return highCholesterol;
    }
    public void setHighCholesterol(String highCholesterol) {
        this.highCholesterol = highCholesterol;
    }
    public String getHighCholesterolMax() {
        return highCholesterolMax;
    }
    public void setHighCholesterolMax(String highCholesterolMax) {
        this.highCholesterolMax = highCholesterolMax;
    }
    public String getFansId() {
        return fansId;
    }
    public void setFansId(String fansId) {
        this.fansId = fansId;
    }
    public String getSugarType() {
        return sugarType;
    }
    public void setSugarType(String sugarType) {
        this.sugarType = sugarType;
    }
    public String getBeforebreakfastLow() {
        return beforebreakfastLow;
    }
    public void setBeforebreakfastLow(String beforebreakfastLow) {
        this.beforebreakfastLow = beforebreakfastLow;
    }
    public String getBeforebreakfastHeight() {
        return beforebreakfastHeight;
    }
    public void setBeforebreakfastHeight(String beforebreakfastHeight) {
        this.beforebreakfastHeight = beforebreakfastHeight;
    }
    public String getBeforeMealLow() {
        return beforeMealLow;
    }
    public void setBeforeMealLow(String beforeMealLow) {
        this.beforeMealLow = beforeMealLow;
    }
    public String getBeforeMealHeight() {
        return beforeMealHeight;
    }
    public void setBeforeMealHeight(String beforeMealHeight) {
        this.beforeMealHeight = beforeMealHeight;
    }
    public String getAfterMealLow() {
        return afterMealLow;
    }
    public void setAfterMealLow(String afterMealLow) {
        this.afterMealLow = afterMealLow;
    }
    public String getAfterMealHeight() {
        return afterMealHeight;
    }
    public void setAfterMealHeight(String afterMealHeight) {
        this.afterMealHeight = afterMealHeight;
    }
    public String getBeforesleepLow() {
        return beforesleepLow;
    }
    public void setBeforesleepLow(String beforesleepLow) {
        this.beforesleepLow = beforesleepLow;
    }
    public String getBeforesleepHeight() {
        return beforesleepHeight;
    }
    public void setBeforesleepHeight(String beforesleepHeight) {
        this.beforesleepHeight = beforesleepHeight;
    }
    public String getBeforedawnLow() {
        return beforedawnLow;
    }
    public void setBeforedawnLow(String beforedawnLow) {
        this.beforedawnLow = beforedawnLow;
    }
    public String getBeforedawnHeight() {
        return beforedawnHeight;
    }
    public void setBeforedawnHeight(String beforedawnHeight) {
        this.beforedawnHeight = beforedawnHeight;
    } 
    
}
