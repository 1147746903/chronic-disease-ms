package com.comvee.cdms.qualitycontrol.model.param;

import javax.validation.constraints.NotEmpty;

public class AddTestPaperParam {

    /**
     * 生产日期
     * produce_date
     */
    @NotEmpty(message = "produceDate 不能为空")
    private String produceDate;

    /**
     * 失效日期
     * invalid_date
     */
    @NotEmpty(message = "invalidDate 不能为空")
    private String invalidDate;

    /**
     * 试纸批号
     * batch_number
     */
    @NotEmpty(message = "batchNo 不能为空")
    private String batchNo;

    /**
     * 范围配置(json)
     * range_config
     */
    @NotEmpty(message = "rangeConfig 不能为空")
    private String rangeConfig;


    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(String invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRangeConfig() {
        return rangeConfig;
    }

    public void setRangeConfig(String rangeConfig) {
        this.rangeConfig = rangeConfig;
    }
}
