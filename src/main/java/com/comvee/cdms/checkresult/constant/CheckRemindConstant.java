package com.comvee.cdms.checkresult.constant;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 检查提醒常量类
 */
public class CheckRemindConstant {

    /**
     * 完成状态 1 已完成 0 未完成
     */
    public static final int FINISH_STATUS_YES = 1;
    public static final int FINISH_STATUS_NO = 0;

    /**
     * 提醒类别 - 检验项目 映射
     */
    private static final MultiValueMap<String ,CheckoutDict> CATEGORY_CHECKOUT_MAP = new LinkedMultiValueMap<>();
    /**
     * 检验项目 - 提醒类别 映射
     */
    private static final Map<String ,String> CHECKOUT_CATEGORY_MAP = new ConcurrentHashMap<>();
    /**
     * 检验检查项目code - 检查提醒项目 映射
     */
    private static final Map<String ,CheckRemindItem> CHECKOUT_CODE_CHECK_REMIND_ITEM_MAP = new ConcurrentHashMap<>();
    /**
     * 检查项目 - 提醒类别
     */
    private static final Map<String ,CheckRemindCategory> INSPECTION_CODE_CATEGORY_MAP = new ConcurrentHashMap<>();
    static {
        initCategoryCheckoutMap();
        reverseCategoryCheckoutMap();
        initCheckoutCodeCheckRemindItemMap();
        initInspectionCodeCategoryMap();
    }

    public static List<CheckoutDict> getCheckoutListByCategory(String category){
        return CATEGORY_CHECKOUT_MAP.get(category);
    }

    public static String getCategoryByCheckoutCode(String code){
        return CHECKOUT_CATEGORY_MAP.get(code);
    }

    public static CheckRemindItem getCheckRemindItemByCheckoutCode(String checkoutCode){
        return CHECKOUT_CODE_CHECK_REMIND_ITEM_MAP.get(checkoutCode);
    }

    public static CheckRemindCategory getCategoryByInspectionCode(String inspectionCode){
        return INSPECTION_CODE_CATEGORY_MAP.get(inspectionCode);
    }

    private static void initCategoryCheckoutMap(){
        //糖化检查
        addCategoryCheckoutMap(CheckRemindCategory.hba1c ,CheckoutDict.HBA);
        //尿常规
        addCategoryCheckoutMap(CheckRemindCategory.urinalysis ,CheckoutDict.NT);
        addCategoryCheckoutMap(CheckRemindCategory.urinalysis ,CheckoutDict.TT);
        addCategoryCheckoutMap(CheckRemindCategory.urinalysis ,CheckoutDict.NEPH_PRO);
        addCategoryCheckoutMap(CheckRemindCategory.urinalysis ,CheckoutDict.NBDB);
        //血脂检查
        addCategoryCheckoutMap(CheckRemindCategory.bloodFat ,CheckoutDict.TC);
        addCategoryCheckoutMap(CheckRemindCategory.bloodFat ,CheckoutDict.HDL);
        addCategoryCheckoutMap(CheckRemindCategory.bloodFat ,CheckoutDict.LDL);
        addCategoryCheckoutMap(CheckRemindCategory.bloodFat ,CheckoutDict.TRIGLYCERIDE);
        //肾功能相关检查
        addCategoryCheckoutMap(CheckRemindCategory.renal ,CheckoutDict.NWLBDB);
        addCategoryCheckoutMap(CheckRemindCategory.renal ,CheckoutDict.NEPH_ACR);
        addCategoryCheckoutMap(CheckRemindCategory.renal ,CheckoutDict.SERUM);
        addCategoryCheckoutMap(CheckRemindCategory.renal ,CheckoutDict.BUN);
        //肝功能检查
        addCategoryCheckoutMap(CheckRemindCategory.liver ,CheckoutDict.TBIL);
        addCategoryCheckoutMap(CheckRemindCategory.liver ,CheckoutDict.ALT);
        addCategoryCheckoutMap(CheckRemindCategory.liver ,CheckoutDict.AST);
        addCategoryCheckoutMap(CheckRemindCategory.liver ,CheckoutDict.GGT);
    }

    private static void addCategoryCheckoutMap(CheckRemindCategory checkRemindCategory ,CheckoutDict checkoutDict){
        CATEGORY_CHECKOUT_MAP.add(checkRemindCategory.getCode() ,checkoutDict);
    }

    private static void reverseCategoryCheckoutMap(){
        for(Map.Entry<String , List<CheckoutDict>> entry : CATEGORY_CHECKOUT_MAP.entrySet()){
            entry.getValue().forEach(x -> {
                CHECKOUT_CATEGORY_MAP.put(x.getCode() ,entry.getKey());
            });
        }
    }

    private static void initCheckoutCodeCheckRemindItemMap(){
        //检验
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.HBA ,CheckRemindItem.hba1c);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.TC ,CheckRemindItem.tc);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.LDL ,CheckRemindItem.ldl);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.HDL ,CheckRemindItem.hdl);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.TRIGLYCERIDE ,CheckRemindItem.triglyceride);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.NWLBDB ,CheckRemindItem.aib);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.NEPH_ACR ,CheckRemindItem.nbdb);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.SERUM ,CheckRemindItem.scr);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.BUN ,CheckRemindItem.dun);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.TBIL ,CheckRemindItem.tbil);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.ALT ,CheckRemindItem.alt);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.AST ,CheckRemindItem.ast);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.GGT ,CheckRemindItem.ggt);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.NT ,CheckRemindItem.urineSugar);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.TT ,CheckRemindItem.urinaryKetone);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.NEPH_PRO ,CheckRemindItem.urineProtein);
        addCheckoutCodeCheckRemindItemMap(CheckoutDict.NBDB ,CheckRemindItem.urinaryLeukocyte);

        //检查
        addInspectionCodeCheckRemindItemMap(InspectionDict.ABI ,CheckRemindItem.abi);
        addInspectionCodeCheckRemindItemMap(InspectionDict.EYES ,CheckRemindItem.eyes);
        addInspectionCodeCheckRemindItemMap(InspectionDict.VPT ,CheckRemindItem.vpt);
        addInspectionCodeCheckRemindItemMap(InspectionDict.PWV ,CheckRemindItem.pwv);
    }

    private static void addCheckoutCodeCheckRemindItemMap(CheckoutDict dict ,CheckRemindItem item){
        CHECKOUT_CODE_CHECK_REMIND_ITEM_MAP.put(dict.getCode() ,item);
    }

    private static void addInspectionCodeCheckRemindItemMap(InspectionDict dict ,CheckRemindItem item){
        CHECKOUT_CODE_CHECK_REMIND_ITEM_MAP.put(dict.getCode() ,item);
    }

    private static void initInspectionCodeCategoryMap(){
        addInspectionCodeCategoryMap(InspectionDict.ABI ,CheckRemindCategory.footVessels);
        addInspectionCodeCategoryMap(InspectionDict.VPT ,CheckRemindCategory.neuropathy);
        addInspectionCodeCategoryMap(InspectionDict.EYES ,CheckRemindCategory.eyes);
        addInspectionCodeCategoryMap(InspectionDict.PWV ,CheckRemindCategory.arteriosclerosis);
    }

    private static void addInspectionCodeCategoryMap(InspectionDict inspectionDict ,CheckRemindCategory category){
        INSPECTION_CODE_CATEGORY_MAP.put(inspectionDict.getCode() ,category);
    }

}
