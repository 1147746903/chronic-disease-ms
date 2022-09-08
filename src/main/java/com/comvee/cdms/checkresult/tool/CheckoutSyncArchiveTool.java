package com.comvee.cdms.checkresult.tool;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.checkresult.constant.CheckoutDict;
import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 检验同步档案工具类
 */
public class CheckoutSyncArchiveTool {

    private final static Map<String ,ArchiveItem> CHECKOUT_CODE_ARCHIVE_MAP = new HashMap<>();
    static {
        initMap();
    }

    public static void resolveArchiveJson(JSONObject archives ,Map<String , CheckoutDetailPO> detailMap){
        CheckoutDetailPO checkoutDetail = null;
        for(Map.Entry<String ,CheckoutDetailPO> entry : detailMap.entrySet()){
            ArchiveItem archiveItem = CHECKOUT_CODE_ARCHIVE_MAP.get(entry.getKey());
            if(archiveItem == null){
                continue;
            }
            checkoutDetail = entry.getValue();
            JSONObject parentJson = archives.getJSONObject(archiveItem.getParentCode());
            if(parentJson == null){
                parentJson = new JSONObject();
                archives.put(archiveItem.getParentCode() ,parentJson);
            }
            String valueDatetime = parentJson.getString(archiveItem.getDatetimeCode());
            if(StringUtils.isBlank(valueDatetime)){
                parentJson.put(archiveItem.getValueCode() ,checkoutDetail.getCheckoutResult());
                parentJson.put(archiveItem.getDatetimeCode() ,checkoutDetail.getCheckoutDatetime());
                continue;
            }
            String value = parentJson.getString(archiveItem.getValueCode());
            if(StringUtils.isBlank(value)){
                parentJson.put(archiveItem.getValueCode() ,checkoutDetail.getCheckoutResult());
                parentJson.put(archiveItem.getDatetimeCode() ,checkoutDetail.getCheckoutDatetime());
                continue;
            }
            if(archiveItem.getDatetimeCode().compareTo(valueDatetime) >= 0){
                parentJson.put(archiveItem.getValueCode() ,checkoutDetail.getCheckoutResult());
                parentJson.put(archiveItem.getDatetimeCode() ,checkoutDetail.getCheckoutDatetime());
            }
        }
    }

    private static void initMap(){
        //糖化
        addMap( CheckoutDict.HBA ,"lab" ,"lab_hba" ,"lab_hba_dt");
        //血脂
        addMap( CheckoutDict.TC ,"lab" ,"tc" ,"follow_bloodFat_dt");
        addMap( CheckoutDict.LDL ,"lab" ,"ldl" ,"follow_bloodFat_dt");
        addMap( CheckoutDict.HDL ,"lab" ,"hdl" ,"follow_bloodFat_dt");
        addMap( CheckoutDict.TRIGLYCERIDE ,"lab" ,"tg" ,"follow_bloodFat_dt");
        //肝功能检查
        addMap( CheckoutDict.ALT ,"lab" ,"alt" ,"qt_dt");
        addMap( CheckoutDict.AST ,"lab" ,"ast" ,"qt_dt");
        addMap( CheckoutDict.TBIL ,"lab" ,"tbil" ,"qt_dt");
        addMap( CheckoutDict.UAE ,"lab" ,"alb" ,"qt_dt");
        addMap( CheckoutDict.QDB ,"lab" ,"qdb" ,"qt_dt");
        //肾功能检查
        addMap( CheckoutDict.NWLBDB ,"lab" ,"lab_nwlbdb" ,"renal_function_dt");
        addMap( CheckoutDict.BUN ,"lab" ,"bun" ,"renal_function_dt");
        addMap( CheckoutDict.SERUM ,"lab" ,"cr" ,"renal_function_dt");
        addMap( CheckoutDict.NEPH_ACR ,"complication" ,"neph_acr" ,"renal_function_dt");
        addMap( CheckoutDict.BLOOD_URIC ,"lab" ,"lab_xns" ,"renal_function_dt");
        addMap( CheckoutDict.EGFR ,"lab" ,"egfr" ,"renal_function_dt");
        addMap( CheckoutDict.DAY_UAE ,"lab" ,"day_uae" ,"renal_function_dt");
        addMap( CheckoutDict.ACR ,"lab" ,"nbdbjg" ,"renal_function_dt");
        //血常规
        addMap( CheckoutDict.XCGWBC ,"lab" ,"xcgWbc" ,"blood_test_dt");
        addMap( CheckoutDict.XCGRBC ,"lab" ,"xcgRbc" ,"blood_test_dt");
        addMap( CheckoutDict.AVGREDBLOODCELL ,"lab" ,"avgRedBloodCell" ,"blood_test_dt");
        addMap( CheckoutDict.PLT ,"lab" ,"platelet" ,"blood_test_dt");
        addMap( CheckoutDict.MCH ,"lab" ,"avgHemoglobin" ,"blood_test_dt");
        addMap( CheckoutDict.HGB ,"lab" ,"hemoglobin" ,"blood_test_dt");
        addMap( CheckoutDict.MCHC ,"lab" ,"avgHemoglobinConcentration" ,"blood_test_dt");
        addMap( CheckoutDict.CRP ,"lab" ,"crp" ,"blood_test_dt");
        //尿常规
        addMap( CheckoutDict.NBDB ,"lab" ,"lab_nbdb" ,"urine_test_dt");
        addMap( CheckoutDict.NEPH_PRO ,"complication" ,"neph_pro_value" ,"urine_test_dt");
        addMap( CheckoutDict.TT ,"lab" ,"tt" ,"urine_test_dt");
        addMap( CheckoutDict.RBC ,"lab" ,"rbc" ,"urine_test_dt");
        addMap( CheckoutDict.NT ,"lab" ,"nt" ,"urine_test_dt");
        //糖耐量
        addMap( CheckoutDict.OGTT_0M ,"lab" ,"ogtt_0m" ,"glucose_tolerance_dt");
        addMap( CheckoutDict.OGTT_30M ,"lab" ,"ogtt_30m" ,"glucose_tolerance_dt");
        addMap( CheckoutDict.OGTT_60M ,"lab" ,"ogtt_60m" ,"glucose_tolerance_dt");
        addMap( CheckoutDict.OGTT_120M ,"lab" ,"ogtt_120m" ,"glucose_tolerance_dt");
        addMap( CheckoutDict.OGTT_180M ,"lab" ,"ogtt_180m" ,"glucose_tolerance_dt");
    }

    private static void addMap(CheckoutDict checkoutDict , String parentCode , String valueCode , String datetimeCode){
        ArchiveItem archiveItem = new ArchiveItem();
        archiveItem.setParentCode(parentCode);
        archiveItem.setValueCode(valueCode);
        archiveItem.setDatetimeCode(datetimeCode);
        CHECKOUT_CODE_ARCHIVE_MAP.put(checkoutDict.getCode() ,archiveItem);
    }

    static class ArchiveItem {

        private String parentCode;
        private String valueCode;
        private String datetimeCode;

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getValueCode() {
            return valueCode;
        }

        public void setValueCode(String valueCode) {
            this.valueCode = valueCode;
        }

        public String getDatetimeCode() {
            return datetimeCode;
        }

        public void setDatetimeCode(String datetimeCode) {
            this.datetimeCode = datetimeCode;
        }
    }
}
