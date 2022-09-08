package com.comvee.cdms.member.tool;

import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;

import java.util.Map;

/**  患者档案信息脱敏
 * Created by : wyc
 * Created time 2020/2/19 11:35
 */
public class MemberArchivesTuoMinHelper {

    public static String archivesTuoMin(String archives,Boolean hide,Integer dealStatus){
        Map<String, Object> oldArchivesMap= JsonSerializer.jsonToMap(archives);
        if(archives.indexOf("basic")>=0){
            Object basicObj = oldArchivesMap.get("basic");
            if(basicObj instanceof Map){
                Map<String,Object> basicMap = (Map<String,Object>)basicObj;
                if (archives.indexOf("id_card")>=0){
                    if(basicMap.get("id_card")!=null){
                        String idCard = basicMap.get("id_card").toString();
                        if(!StringUtils.isBlank(idCard)){
                            idCard = ValidateTool.tuoMin(idCard,4,4,"*");
                            basicMap.put("id_card",idCard);
                            oldArchivesMap.put("basic",basicMap);
                            archives = JsonSerializer.objectToJson(oldArchivesMap);
                        }
                    }
                }
                if (null == hide || hide){
                    if (dealStatus == null || (null != dealStatus && dealStatus == 1)){
                        if (archives.indexOf("mobile_phone")>=0){
                            if(basicMap.get("mobile_phone")!=null){
                                String mobilePhone = basicMap.get("mobile_phone").toString();
                                if(!StringUtils.isBlank(mobilePhone)){
                                    mobilePhone = ValidateTool.tuoMin(mobilePhone,3,4,"*");
                                    basicMap.put("mobile_phone",mobilePhone);
                                    oldArchivesMap.put("basic",basicMap);
                                    archives = JsonSerializer.objectToJson(oldArchivesMap);
                                }
                            }
                        }
                    }

                }

            }
        }
        return archives;

    }
}
