package com.comvee.cdms.statistics.cfg;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.sign.po.BloodSugarPO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/10/19
 */
public class ChartItemCode {

    public static final String CHART_ITEM_PRESCRIPTION = "PRESCRIPTION_NUMBER";
    public static final String CHART_ITEM_FOLLOW = "FOLLOW_NUMBER";
    public static final String CHART_ITEM_ORDER = "ORDER_NUMBER";
    public static final String CHART_ITEM_MEMBER = "MEMBER_NUMBER";


    /**
     *
     * 组装本地曲线
     * @param listParamLocal List<MemberParamLogModel>
     * @return List
     * @author 何健
     * @date 2017/3/3 09:57
     */
    public static List<Map<String,Object>> resetList(List<BloodSugarPO> listParamLocal) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        //最后的日期
        String lastDate = "";
        Map<String,Object> resMap = null;
        List<BloodSugarPO> mplm = null;
        for (int i = 0; i < listParamLocal.size(); i++) {
//       for (int i = (listParamLocal.size() - 1); i >= 0; i--) {
            BloodSugarPO bean = listParamLocal.get(i);
            String recordDate = DateHelper.changeTimeFormat(bean.getRecordDt(),DateHelper.DATETIME_FORMAT,DateHelper.DAY_FORMAT);
            if (!lastDate.equals(recordDate) || i == 0){
                if (resMap != null && mplm != null ){
                    resMap.put("paramLogList",mplm);
                    list.add(resMap);
                }
                resMap = new HashMap<String,Object>(2);
                mplm = new ArrayList<BloodSugarPO>();
                resMap.put("date",recordDate);
                lastDate = recordDate;
            }

            mplm.add(bean);

            if (i == listParamLocal.size() - 1){
//           if (i == 0){
                resMap.put("paramLogList",mplm);
                list.add(resMap);
            }
        }
        return list;
    }

}
