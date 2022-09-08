package com.comvee.cdms.prescription.tool;

import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.prescription.bo.ApiMemberBO;

import java.util.Map;

/**
 * @author wyc
 * @date 2020/1/20 15:23
 */
public class PrescriptionMemberRangeHelper {

    /**
     * 获取ckd分期
     * @param archivesJson
     * @return
     */
    public static Integer getCkd(String archivesJson){
        Integer ckd = null;
        if (!StringUtils.isBlank(archivesJson)){
            Map<String, Object> archivesJsonMap = JsonSerializer.jsonToMap(archivesJson);
            if (null != archivesJsonMap.get("hypertension") && !StringUtils.isBlank(archivesJsonMap.get("hypertension").toString())){
                String hypertension = archivesJsonMap.get("hypertension").toString();
                Map<String, Object> hypertensionMap = JsonSerializer.jsonToMap(hypertension);
                if (null!= hypertensionMap.get("slowNep") && !StringUtils.isBlank(hypertensionMap.get("slowNep").toString())){
                    String slowNep = hypertensionMap.get("slowNep").toString();
                    if ("MXSZB01".equals(slowNep)){
                        if (null != hypertensionMap.get("slowNepStage") && !StringUtils.isBlank(hypertensionMap.get("slowNepStage").toString())){
                            String slowNepStage = hypertensionMap.get("slowNepStage").toString();
                            if ("MXSZBFQ01".equals(slowNepStage)){
                                ckd = 1;
                            }else if ("MXSZBFQ02".equals(slowNepStage)){
                                ckd = 2;
                            }else if ("MXSZBFQ03".equals(slowNepStage)){
                                ckd = 3;
                            }else if ("MXSZBFQ04".equals(slowNepStage)){
                                ckd = 4;
                            }else if ("MXSZBFQ05".equals(slowNepStage)){
                                ckd = 5;
                            }
                        }
                    }
                }

            }
        }
        return ckd;
    }

    /**
     * 获取该血压并发症
     * 是否有高血压并发症  1 确诊有、疑似、有症状   2 确诊无、未评估、无症状
     * @param archivesJson
     * @return
     */
    public static Integer getHypBfz(String archivesJson){
        Integer hypBfz = 2;  //
        if (!StringUtils.isBlank(archivesJson)){
            Map<String, Object> archivesJsonMap = JsonSerializer.jsonToMap(archivesJson);
            if (null != archivesJsonMap.get("hypertension") && !StringUtils.isBlank(archivesJsonMap.get("hypertension").toString())) {
                String hypertension = archivesJsonMap.get("hypertension").toString();
                Map<String, Object> hypMap = JsonSerializer.jsonToMap(hypertension);
                //心脏疾病
                if (null != hypMap.get("cardiopathy") && !StringUtils.isBlank(hypMap.get("cardiopathy").toString())) {
                    if ("XZJB01".equals(hypMap.get("cardiopathy").toString()) || "XZJB04".equals(hypMap.get("cardiopathy").toString())) {
                        hypBfz = 1;
                    }else if ("XZJB03".equals(hypMap.get("cardiopathy").toString())){
                        if (null != hypMap.get("cardiopathyCms") && !StringUtils.isBlank(hypMap.get("cardiopathyCms").toString())){
                            hypBfz = 1;
                        }
                    }
                }
                //脑血管病
                if (null != hypMap.get("cerebral") && !StringUtils.isBlank(hypMap.get("cerebral").toString())) {
                    if ("NXGB01".equals(hypMap.get("cerebral").toString()) || "NXGB04".equals(hypMap.get("cerebral").toString())) {
                        hypBfz = 1;
                    }else if ("NXGB03".equals(hypMap.get("cerebral").toString())){
                        if (null != hypMap.get("cerebralCms") && !StringUtils.isBlank(hypMap.get("cerebralCms").toString())) {
                            hypBfz = 1;
                        }
                    }
                }
                //高血压眼底视网膜病变
                if (null != hypMap.get("hypRet") && !StringUtils.isBlank(hypMap.get("hypRet").toString())) {
                    if ("GXYYD01".equals(hypMap.get("hypRet").toString()) || "GXYYD04".equals(hypMap.get("hypRet").toString())) {
                        hypBfz = 1;
                    }else if ("GXYYD03".equals(hypMap.get("hypRet").toString())){
                        if (null != hypMap.get("hypRetCms") && !StringUtils.isBlank(hypMap.get("hypRetCms").toString())) {
                            if (!"GXYYDZZ01".equals(hypMap.get("hypRetCms").toString())){
                                hypBfz = 1;
                            }
                        }
                    }
                }
                //慢性肾脏病
                if (null != hypMap.get("slowNep") && !StringUtils.isBlank(hypMap.get("slowNep").toString())) {
                    if ("MXSZB01".equals(hypMap.get("slowNep").toString()) || "MXSZB04".equals(hypMap.get("slowNep").toString())) {
                        hypBfz = 1;
                    }else if ("MXSZB03".equals(hypMap.get("slowNep").toString())){
                        if (null != hypMap.get("slowNepCms") && !StringUtils.isBlank(hypMap.get("slowNepCms").toString())) {
                            if (!"MXSZBZZ01".equals(hypMap.get("slowNepCms").toString())){
                                hypBfz = 1;
                            }
                        }
                    }
                }
                //外周血管疾病
                if (null != hypMap.get("peripheral") && !StringUtils.isBlank(hypMap.get("peripheral").toString())) {
                    if ("WZXG01".equals(hypMap.get("peripheral").toString()) || "WZXG04".equals(hypMap.get("peripheral").toString())) {
                        hypBfz = 1;
                    }else if ("WZXG03".equals(hypMap.get("peripheral").toString())){
                        if (null != hypMap.get("peripheralCms") && !StringUtils.isBlank(hypMap.get("peripheralCms").toString())) {
                            hypBfz = 1;
                        }
                    }
                }
                //糖尿病
                if (null != hypMap.get("diabetes") && !StringUtils.isBlank(hypMap.get("diabetes").toString())) {
                    if ("1".equals(hypMap.get("diabetes").toString())) {
                        hypBfz = 1;
                    }
                }
            }

            //冠心病
            if (null != archivesJsonMap.get("complication") && !StringUtils.isBlank(archivesJsonMap.get("complication").toString())) {
                String complication = archivesJsonMap.get("complication").toString();
                Map<String, Object> complicationMap = JsonSerializer.jsonToMap(complication);
                if (null != complicationMap.get("chd") && !StringUtils.isBlank(complicationMap.get("chd").toString())) {
                    String chd = complicationMap.get("chd").toString();
                    if ("QZ01".equals(chd) || "QZ04".equals(chd)) {
                        hypBfz = 1;
                    } else if ("QZ03".equals(chd)) {
                        if (null != complicationMap.get("chd_symptom") && !StringUtils.isBlank(complicationMap.get("chd_symptom").toString())) {
                            if (!"ZZ01".equals(complicationMap.get("chd_symptom").toString())) {
                                hypBfz = 1;
                            }
                        }
                    }
                }
            }



        }
        return hypBfz;
    }

    /**
     * 是否有糖尿病并发症 1 确诊有 2 确诊无
     * @param archivesJson
     * @param member
     * @return
     */
    public static Integer getDiabetesBfz(String archivesJson,ApiMemberBO member) {
        Integer diabetesBfz = 2;
        if (null != member) {
            if (!StringUtils.isBlank(member.getIsDiabetes()) && "1".equals(member.getIsDiabetes())) {
                if (!StringUtils.isBlank(member.getIsDiabetes()) && "1".equals(member.getIsDiabetes())) {
                    if (!StringUtils.isBlank(archivesJson)){
                        Map<String, Object> jsonMap = JsonSerializer.jsonToMap(archivesJson);
                        if (null != jsonMap.get("complication") && !StringUtils.isBlank(jsonMap.get("complication").toString())){
                            String complication = jsonMap.get("complication").toString();
                            Map<String, Object> complicationMap = JsonSerializer.jsonToMap(complication);
                            //糖尿病眼底病变
                            if (null != complicationMap.get("retinal") && !StringUtils.isBlank(complicationMap.get("retinal").toString())) {
                                if ("SWM01".equals(complicationMap.get("retinal").toString())){
                                    diabetesBfz = 1;
                                }
                            }
                            //糖尿病肾病
                            if (null != complicationMap.get("nephropathy") && !StringUtils.isBlank(complicationMap.get("nephropathy").toString())) {
                                if ("SB01".equals(complicationMap.get("nephropathy").toString())){
                                    diabetesBfz = 1;
                                }
                            }
                            //周围神经病变
                            if (null != complicationMap.get("neuropathy") && !StringUtils.isBlank(complicationMap.get("neuropathy").toString())) {
                                if ("ZWSJ01".equals(complicationMap.get("neuropathy").toString())){
                                    diabetesBfz = 1;
                                }
                            }
                            // 糖尿病足
                            if (null != complicationMap.get("diabetic_foot") && !StringUtils.isBlank(complicationMap.get("diabetic_foot").toString())) {
                                if ("TNBZ01".equals(complicationMap.get("diabetic_foot").toString())){
                                    diabetesBfz = 1;
                                }
                            }
                            // 下肢血管病变
                            if (null != complicationMap.get("pad") && !StringUtils.isBlank(complicationMap.get("pad").toString())) {
                                if ("XZXG01".equals(complicationMap.get("pad").toString())){
                                    diabetesBfz = 1;
                                }
                            }
                            //  自主神经病变
                            if (null != complicationMap.get("dan") && !StringUtils.isBlank(complicationMap.get("dan").toString())) {
                                if ("ZZ01".equals(complicationMap.get("dan").toString())){
                                    diabetesBfz = 1;
                                }
                            }
                            //  冠心病
                            if (null != complicationMap.get("chd") && !StringUtils.isBlank(complicationMap.get("chd").toString())) {
                                if ("QZ01".equals(complicationMap.get("chd").toString())){
                                    diabetesBfz = 1;
                                }
                            }
                            //急性并发症
                            if (null != complicationMap.get("jyntnbjxbfz") && !StringUtils.isBlank(complicationMap.get("jyntnbjxbfz").toString())) {
                                if (!"LX01".equals(complicationMap.get("jyntnbjxbfz").toString())){
                                    diabetesBfz = 1;
                                }
                            }
                        }
                    }
                }
            }

        }
        return diabetesBfz;
    }

}
