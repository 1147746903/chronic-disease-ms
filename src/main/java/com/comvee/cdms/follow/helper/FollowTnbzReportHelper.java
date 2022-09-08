package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.follow.po.FollowupPO;

import java.util.*;

/** 糖尿病足随访报告
 * @author wyc
 * @date 2019/10/10 14:39
 */
public class FollowTnbzReportHelper {
    public static String getFollowTnbReportReportJson(FollowupPO followupPO) {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> cmjcList = new ArrayList<>();
        if (null != followupPO.getFollowInfo() && !StringUtils.isBlank(followupPO.getFollowInfo().toString())) {
            Map<String, Object> followupMap = JsonSerializer.jsonToMap(followupPO.getFollowInfo().toString());
            resultMap.put("member_name", followupMap.get("member_real_name"));  //姓名
            resultMap.put("sex", followupMap.get("sex"));  //性别  男 女
            resultMap.put("bxb", followupMap.get("bxb"));  //白细胞计数
            resultMap.put("jgsy", followupMap.get("jgsy"));   //降钙素原
            resultMap.put("cfydb", followupMap.get("cfydb"));   //C反应蛋白
            resultMap.put("xc", followupMap.get("xc"));  //血沉
            resultMap.put("xhdb", followupMap.get("xhdb"));  //血红蛋白浓度
            resultMap.put("xqbdb", followupMap.get("xqbdb"));  //白蛋白
            resultMap.put("abiLeft", followupMap.get("abi_l"));  //左脚abi
            resultMap.put("abiRight", followupMap.get("abi_r"));  //右脚abi
//            String vptLeft = followupMap.get("zdgjjc") == null ? "" : getVptText(followupMap.get("zdgjjc").toString());
//            String vptRight = followupMap.get("zdgjjc_r") == null ? "" : getVptText(followupMap.get("zdgjjc_r").toString());
            resultMap.put("vptLeft", followupMap.get("zdgjjc")); //左脚VPT
            resultMap.put("vptRight", followupMap.get("zdgjjc_r")); //右脚VPT
            resultMap.put("tbiLeft", followupMap.get("tbi_l")); //左脚tbi
            resultMap.put("tbiRight", followupMap.get("tbi_r")); //右脚tbi
            resultMap.put("mqzywt", followupMap.get("mqzywt"));   //目前主要问题
            resultMap.put("zygjcs", followupMap.get("zygjcs"));   //主要改进措施
            resultMap.put("yqddmb", followupMap.get("yqddmb"));   //预期达到目标
            //创面(左脚)
            // 2 : 足趾  3 : 足体 4 : 足跟 5 : 踝 6 : 小腿
            if (null != followupMap.get("cmwz_l") && !StringUtils.isBlank(followupMap.get("cmwz_l").toString())) {
                String cmwzLeft = followupMap.get("cmwz_l").toString();
                String[] splitLeft = cmwzLeft.split(",");
                for (String left : splitLeft) {
                    if ("2".equals(left)) {  //足趾
                        String zzResultLeft = "";  //左脚足趾创面检查结果
                        String zzmjLeft = "";  //左脚足趾面积结果
                        String zzsdLeft = "";  //左脚足趾深度结果
                        String zzqxLeft = "";  //左脚足趾窦道/潜行结果
                        //面积
                        if (null != followupMap.get("zzmj_l") && !StringUtils.isBlank(followupMap.get("zzmj_l").toString())) {
                            zzResultLeft +="创面面积";
                            String zzmj_l = followupMap.get("zzmj_l").toString();
                            //1 : <1   2 : 1-2  3 : 2-5  4 : 5-10 5 : 10-30  6 : >30
                            zzmjLeft = getMjText(zzmj_l);
                            String remark = "";
                            if (null != followupMap.get("zzmjbz_l") && !StringUtils.isBlank(followupMap.get("zzmjbz_l").toString())) {
                                remark = "（" + followupMap.get("zzmjbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                zzResultLeft += zzmjLeft + "，";
                            } else {
                                zzResultLeft += zzmjLeft + remark;
                            }
                        }
                        //深度
                        if (null != followupMap.get("zzsd_l") && !StringUtils.isBlank(followupMap.get("zzsd_l").toString())) {
                            zzResultLeft += "创面深度";
                            String zzsd_l = followupMap.get("zzsd_l").toString();
                            //1 : <5  2 : 5-9  3 : 10-20  4 : >20
                            zzsdLeft = getSdText(zzsd_l);

                            String remark = "";
                            if (null != followupMap.get("zzsdbz_l") && !StringUtils.isBlank(followupMap.get("zzsdbz_l").toString())) {
                                remark = "（" + followupMap.get("zzsdbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                zzResultLeft += zzsdLeft + "，";
                            } else {
                                zzResultLeft += zzsdLeft + remark;
                            }
                        }
                        //窦道/潜行
                        if (null != followupMap.get("zzqx_l") && !StringUtils.isBlank(followupMap.get("zzqx_l").toString())) {
                            zzResultLeft += "窦道/潜行";
                            String zzqx_l = followupMap.get("zzqx_l").toString();
                            //1 : <2  2 : 2-5  3 : >5  4 : 无
                            zzqxLeft = getQxText(zzqx_l);
                            String remark = "";
                            if (null != followupMap.get("zzqxbz_l") && !StringUtils.isBlank(followupMap.get("zzqxbz_l").toString())) {
                                remark = "（" + followupMap.get("zzqxbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                zzResultLeft += zzqxLeft + "，";
                            } else {
                                zzResultLeft += zzqxLeft + remark;
                            }
                        }
                        String result = StringUtils.isBlank(zzResultLeft) ? "您的左脚足趾有创面；" : "您的左脚足趾有创面," + zzResultLeft.substring(0, zzResultLeft.length() - 1) + "；";
                        cmjcList.add(result);
                    } else if ("3".equals(left)) {   //足体
                        String ztResultLeft = "";  //左脚足体创面检查结果
                        String ztmjLeft = "";  //左脚足体面积结果
                        String ztsdLeft = "";  //左脚足体深度结果
                        String ztqxLeft = "";  //左脚足体窦道/潜行结果
                        //面积
                        if (null != followupMap.get("ztmj_l") && !StringUtils.isBlank(followupMap.get("ztmj_l").toString())) {
                            ztResultLeft += "创面面积";
                            String ztmj_l = followupMap.get("ztmj_l").toString();
                            //1 : <1   2 : 1-2  3 : 2-5  4 : 5-10 5 : 10-30  6 : >30
                            ztmjLeft = getMjText(ztmj_l);
                            String remark = "";
                            if (null != followupMap.get("ztmjbz_l") && !StringUtils.isBlank(followupMap.get("ztmjbz_l").toString())) {
                                remark = "（" + followupMap.get("ztmjbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                ztResultLeft += ztmjLeft + "，";
                            } else {
                                ztResultLeft += ztmjLeft + remark;
                            }
                        }
                        //深度
                        if (null != followupMap.get("ztsd_l") && !StringUtils.isBlank(followupMap.get("ztsd_l").toString())) {
                            ztResultLeft += "创面深度";
                            String ztsd_l = followupMap.get("ztsd_l").toString();
                            //1 : <5  2 : 5-9  3 : 10-20  4 : >20
                            ztsdLeft = getSdText(ztsd_l);
                            String remark = "";
                            if (null != followupMap.get("ztsdbz_l") && !StringUtils.isBlank(followupMap.get("ztsdbz_l").toString())) {
                                remark = "（" + followupMap.get("ztsdbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                ztResultLeft += ztsdLeft + "，";
                            } else {
                                ztResultLeft += ztsdLeft + remark;
                            }
                        }
                        //窦道/潜行
                        if (null != followupMap.get("ztqx_l") && !StringUtils.isBlank(followupMap.get("ztqx_l").toString())) {
                            ztResultLeft += "窦道/潜行";
                            String ztqx_l = followupMap.get("ztqx_l").toString();
                            //1 : <2  2 : 2-5  3 : >5  4 : 无
                            ztqxLeft = getQxText(ztqx_l);
                            String remark = "";
                            if (null != followupMap.get("ztqxbz_l") && !StringUtils.isBlank(followupMap.get("ztqxbz_l").toString())) {
                                remark = "（" + followupMap.get("ztqxbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                ztResultLeft += ztqxLeft + "，";
                            } else {
                                ztResultLeft += ztqxLeft + remark;
                            }
                        }
                        String result = StringUtils.isBlank(ztResultLeft) ? "您的左脚足体有创面；" : "您的左脚足体有创面，" + ztResultLeft.substring(0, ztResultLeft.length() - 1) + "；";
                        cmjcList.add(result);
                    } else if ("4".equals(left)) {   //足跟
                        String zgResultLeft = "";  //左脚足跟创面检查结果
                        String zgmjLeft = "";      //左脚足跟面积结果
                        String zgsdLeft = "";      //左脚足跟深度结果
                        String zgqxLeft = "";      //左脚足跟窦道/潜行结果
                        //面积
                        if (null != followupMap.get("zgmj_l") && !StringUtils.isBlank(followupMap.get("zgmj_l").toString())) {
                            zgResultLeft += "创面面积";
                            String zgmj_l = followupMap.get("zgmj_l").toString();
                            //1 : <1   2 : 1-2  3 : 2-5  4 : 5-10 5 : 10-30  6 : >30
                            zgmjLeft = getMjText(zgmj_l);
                            String remark = "";
                            if (null != followupMap.get("zgmjbz_l") && !StringUtils.isBlank(followupMap.get("zgmjbz_l").toString())) {
                                remark = "（" + followupMap.get("zgmjbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                zgResultLeft += zgmjLeft + "，";
                            } else {
                                zgResultLeft += zgmjLeft + remark;
                            }
                        }
                        //深度
                        if (null != followupMap.get("zgsd_l") && !StringUtils.isBlank(followupMap.get("zgsd_l").toString())) {
                            zgResultLeft += "创面深度";
                            String zgsd_l = followupMap.get("zgsd_l").toString();
                            //1 : <5  2 : 5-9  3 : 10-20  4 : >20
                            zgsdLeft = getSdText(zgsd_l);
                            String remark = "";
                            if (null != followupMap.get("zgsdbz_l") && !StringUtils.isBlank(followupMap.get("zgsdbz_l").toString())) {
                                remark = "（" + followupMap.get("zgsdbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                zgResultLeft += zgsdLeft + "，";
                            } else {
                                zgResultLeft += zgsdLeft + remark;
                            }
                        }
                        //窦道/潜行
                        if (null != followupMap.get("zgqx_l") && !StringUtils.isBlank(followupMap.get("zgqx_l").toString())) {
                            zgResultLeft += "窦道/潜行";
                            String zgqx_l = followupMap.get("zgqx_l").toString();
                            //1 : <2  2 : 2-5  3 : >5  4 : 无
                            zgqxLeft = getQxText(zgqx_l);
                            String remark = "";
                            if (null != followupMap.get("zgqxbz_l") && !StringUtils.isBlank(followupMap.get("zgqxbz_l").toString())) {
                                remark = "（" + followupMap.get("zgqxbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                zgResultLeft += zgqxLeft + "，";
                            } else {
                                zgResultLeft += zgqxLeft + remark;
                            }
                        }
                        String result = StringUtils.isBlank(zgResultLeft) ? "您的左脚足跟有创面；" : "您的左脚足跟有创面，" + zgResultLeft.substring(0, zgResultLeft.length() - 1) + "；";
                        cmjcList.add(result);
                    } else if ("5".equals(left)) {   //踝
                        String hResultLeft = "";  //左脚踝创面检查结果
                        String hmjLeft = "";      //左脚踝面积结果
                        String hsdLeft = "";      //左脚踝深度结果
                        String hqxLeft = "";      //左脚踝窦道/潜行结果
                        //面积
                        if (null != followupMap.get("hmj_l") && !StringUtils.isBlank(followupMap.get("hmj_l").toString())) {
                            hResultLeft += "创面面积";
                            String hmj_l = followupMap.get("hmj_l").toString();
                            //1 : <1   2 : 1-2  3 : 2-5  4 : 5-10 5 : 10-30  6 : >30
                            hmjLeft = getMjText(hmj_l);
                            String remark = "";
                            if (null != followupMap.get("hmjbz_l") && !StringUtils.isBlank(followupMap.get("hmjbz_l").toString())) {
                                remark = "（" + followupMap.get("hmjbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                hResultLeft += hmjLeft + "，";
                            } else {
                                hResultLeft += hmjLeft + remark;
                            }
                        }
                        //深度
                        if (null != followupMap.get("hsd_l") && !StringUtils.isBlank(followupMap.get("hsd_l").toString())) {
                            hResultLeft += "创面深度";
                            String hsd_l = followupMap.get("hsd_l").toString();
                            //1 : <5  2 : 5-9  3 : 10-20  4 : >20
                            hsdLeft = getSdText(hsd_l);
                            String remark = "";
                            if (null != followupMap.get("hsdbz_l") && !StringUtils.isBlank(followupMap.get("hsdbz_l").toString())) {
                                remark = "（" + followupMap.get("hsdbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                hResultLeft += hsdLeft + "，";
                            } else {
                                hResultLeft += hsdLeft + remark;
                            }
                        }
                        //窦道/潜行
                        if (null != followupMap.get("hqx_l") && !StringUtils.isBlank(followupMap.get("hqx_l").toString())) {
                            hResultLeft += "窦道/潜行";
                            String hqx_l = followupMap.get("hqx_l").toString();
                            //1 : <2  2 : 2-5  3 : >5  4 : 无
                            hqxLeft = getQxText(hqx_l);
                            String remark = "";
                            if (null != followupMap.get("hqxbz_l") && !StringUtils.isBlank(followupMap.get("hqxbz_l").toString())) {
                                remark = "（" + followupMap.get("hqxbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                hResultLeft += hqxLeft + "，";
                            } else {
                                hResultLeft += hqxLeft + remark;
                            }
                        }
                        String result = StringUtils.isBlank(hResultLeft) ? "您的左脚踝有创面；" : "您的左脚踝有创面，" + hResultLeft.substring(0, hResultLeft.length() - 1) + "；";
                        cmjcList.add(result);
                    } else if ("6".equals(left)) {   //小腿
                        String xtResultLeft = "";  //左脚小腿创面检查结果
                        String xtmjLeft = "";      //左脚小腿面积结果
                        String xtsdLeft = "";      //左脚小腿深度结果
                        String xtqxLeft = "";      //左脚小腿窦道/潜行结果
                        //面积
                        if (null != followupMap.get("xtmj_l") && !StringUtils.isBlank(followupMap.get("xtmj_l").toString())) {
                            xtResultLeft += "创面面积";
                            String xtmj_l = followupMap.get("xtmj_l").toString();
                            //1 : <1   2 : 1-2  3 : 2-5  4 : 5-10 5 : 10-30  6 : >30
                            xtmjLeft = getMjText(xtmj_l);
                            String remark = "";
                            if (null != followupMap.get("xtmjbz_l") && !StringUtils.isBlank(followupMap.get("xtmjbz_l").toString())) {
                                remark = "（" + followupMap.get("xtmjbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                xtResultLeft += xtmjLeft + "，";
                            } else {
                                xtResultLeft += xtmjLeft + remark;
                            }
                        }
                        //深度
                        if (null != followupMap.get("xtsd_l") && !StringUtils.isBlank(followupMap.get("xtsd_l").toString())) {
                            xtResultLeft += "创面深度";
                            String xtsd_l = followupMap.get("xtsd_l").toString();
                            //1 : <5  2 : 5-9  3 : 10-20  4 : >20
                            xtsdLeft = getSdText(xtsd_l);
                            String remark = "";
                            if (null != followupMap.get("xtsdbz_l") && !StringUtils.isBlank(followupMap.get("xtsdbz_l").toString())) {
                                remark = "（" + followupMap.get("xtsdbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                xtResultLeft += xtsdLeft + "，";
                            } else {
                                xtResultLeft += xtsdLeft + remark;
                            }
                        }
                        //窦道/潜行
                        if (null != followupMap.get("xtqx_l") && !StringUtils.isBlank(followupMap.get("xtqx_l").toString())) {
                            xtResultLeft += "窦道/潜行";
                            String xtqx_l = followupMap.get("xtqx_l").toString();
                            //1 : <2  2 : 2-5  3 : >5  4 : 无
                            xtqxLeft = getQxText(xtqx_l);
                            String remark = "";
                            if (null != followupMap.get("xtqxbz_l") && !StringUtils.isBlank(followupMap.get("xtqxbz_l").toString())) {
                                remark = "（" + followupMap.get("xtqxbz_l").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                xtResultLeft += xtqxLeft + "，";
                            } else {
                                xtResultLeft += xtqxLeft + remark;
                            }
                        }
                        String result = StringUtils.isBlank(xtResultLeft) ? "您的左脚小腿有创面；" : "您的左脚小腿有创面，" + xtResultLeft.substring(0, xtResultLeft.length() - 1) + "；";
                        cmjcList.add(result);
                    }
                }
            }

            //创面(右脚)
            // 2 : 足趾  3 : 足体 4 : 足跟 5 : 踝 6 : 小腿
            if (null != followupMap.get("cmwz_r") && !StringUtils.isBlank(followupMap.get("cmwz_r").toString())) {
                String cmwzRight = followupMap.get("cmwz_r").toString();
                String[] splitRight = cmwzRight.split(",");
                for (String right : splitRight) {
                    if ("2".equals(right)) {  //足趾
                        String zzResultRight = "";  //右脚足趾创面检查结果
                        String zzmjRight = "";  //右脚足趾面积结果
                        String zzsdRight = "";  //右脚足趾深度结果
                        String zzqxRight = "";  //右脚足趾窦道/潜行结果
                        //面积
                        if (null != followupMap.get("zzmj_r") && !StringUtils.isBlank(followupMap.get("zzmj_r").toString())) {
                            zzResultRight += "创面面积";
                            String zzmj_r = followupMap.get("zzmj_r").toString();
                            //1 : <1   2 : 1-2  3 : 2-5  4 : 5-10 5 : 10-30  6 : >30
                            zzmjRight = getMjText(zzmj_r);
                            String remark = "";
                            if (null != followupMap.get("zzmjbz_r") && !StringUtils.isBlank(followupMap.get("zzmjbz_r").toString())) {
                                remark = "（" + followupMap.get("zzmjbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                zzResultRight += zzmjRight + "，";
                            } else {
                                zzResultRight += zzmjRight + remark;
                            }
                        }
                        //深度
                        if (null != followupMap.get("zzsd_r") && !StringUtils.isBlank(followupMap.get("zzsd_r").toString())) {
                            zzResultRight += "创面深度";
                            String zzsd_r = followupMap.get("zzsd_r").toString();
                            //1 : <5  2 : 5-9  3 : 10-20  4 : >20
                            zzsdRight = getSdText(zzsd_r);
                            String remark = "";
                            if (null != followupMap.get("zzsdbz_r") && !StringUtils.isBlank(followupMap.get("zzsdbz_r").toString())) {
                                remark = "（" + followupMap.get("zzsdbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                zzResultRight += zzsdRight + "，";
                            } else {
                                zzResultRight += zzsdRight + remark;
                            }
                        }
                        //窦道/潜行
                        if (null != followupMap.get("zzqx_r") && !StringUtils.isBlank(followupMap.get("zzqx_r").toString())) {
                            zzResultRight += "窦道/潜行";
                            String zzqx_r = followupMap.get("zzqx_r").toString();
                            //1 : <2  2 : 2-5  3 : >5  4 : 无
                            zzqxRight = getQxText(zzqx_r);
                            String remark = "";
                            if (null != followupMap.get("zzqxbz_r") && !StringUtils.isBlank(followupMap.get("zzqxbz_r").toString())) {
                                remark = "（" + followupMap.get("zzqxbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                zzResultRight += zzqxRight + "，";
                            } else {
                                zzResultRight += zzqxRight + remark;
                            }
                        }
                        String result = StringUtils.isBlank(zzResultRight) ? "您的右脚足趾有创面；" : "您的右脚足趾有创面，" + zzResultRight.substring(0, zzResultRight.length() - 1) + "；";
                        cmjcList.add(result);
                    } else if ("3".equals(right)) {   //足体
                        String ztResultRight = "";  //右脚足体创面检查结果
                        String ztmjRight = "";  //右脚足体面积结果
                        String ztsdRight = "";  //右脚足体深度结果
                        String ztqxRight = "";  //右脚足体窦道/潜行结果
                        //面积
                        if (null != followupMap.get("ztmj_r") && !StringUtils.isBlank(followupMap.get("ztmj_r").toString())) {
                            ztResultRight += "创面面积";
                            String ztmj_r = followupMap.get("ztmj_r").toString();
                            //1 : <1   2 : 1-2  3 : 2-5  4 : 5-10 5 : 10-30  6 : >30
                            ztmjRight = getMjText(ztmj_r);
                            String remark = "";
                            if (null != followupMap.get("ztmjbz_r") && !StringUtils.isBlank(followupMap.get("ztmjbz_r").toString())) {
                                remark = "（" + followupMap.get("ztmjbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                ztResultRight += ztmjRight + "，";
                            } else {
                                ztResultRight += ztmjRight + remark;
                            }
                        }
                        //深度
                        if (null != followupMap.get("ztsd_r") && !StringUtils.isBlank(followupMap.get("ztsd_r").toString())) {
                            ztResultRight += "创面深度";
                            String ztsd_r = followupMap.get("ztsd_r").toString();
                            ztsdRight = getSdText(ztsd_r);
                            String remark = "";
                            if (null != followupMap.get("ztsdbz_r") && !StringUtils.isBlank(followupMap.get("ztsdbz_r").toString())) {
                                remark = "（" + followupMap.get("ztsdbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                ztResultRight += ztsdRight + "，";
                            } else {
                                ztResultRight += ztsdRight + remark;
                            }
                        }
                        //窦道/潜行
                        if (null != followupMap.get("ztqx_r") && !StringUtils.isBlank(followupMap.get("ztqx_r").toString())) {
                            ztResultRight += "窦道/潜行";
                            String ztqx_l = followupMap.get("ztqx_r").toString();
                            ztqxRight = getQxText(ztqx_l);
                            String remark = "";
                            if (null != followupMap.get("ztqxbz_r") && !StringUtils.isBlank(followupMap.get("ztqxbz_r").toString())) {
                                remark = "（" + followupMap.get("ztqxbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                ztResultRight += ztqxRight + "，";
                            } else {
                                ztResultRight += ztqxRight + remark;
                            }
                        }
                        String result = StringUtils.isBlank(ztResultRight) ? "您的右脚足体有创面；" : "您的右脚足体有创面，" + ztResultRight.substring(0, ztResultRight.length() - 1) + "；";
                        cmjcList.add(result);
                    } else if ("4".equals(right)) {   //足跟
                        String zgResultRight = "";  //右脚足跟创面检查结果
                        String zgmjRight = "";      //右脚足跟面积结果
                        String zgsdRight = "";      //右脚足跟深度结果
                        String zgqxRight = "";      //右脚足跟窦道/潜行结果
                        //面积
                        if (null != followupMap.get("zgmj_r") && !StringUtils.isBlank(followupMap.get("zgmj_r").toString())) {
                            zgResultRight += "创面面积";
                            String zgmj_r = followupMap.get("zgmj_r").toString();
                            zgmjRight = getMjText(zgmj_r);
                            String remark = "";
                            if (null != followupMap.get("zgmjbz_r") && !StringUtils.isBlank(followupMap.get("zgmjbz_r").toString())) {
                                remark = "（" + followupMap.get("zgmjbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                zgResultRight += zgmjRight + "，";
                            } else {
                                zgResultRight += zgmjRight + remark;
                            }
                        }
                        //深度
                        if (null != followupMap.get("zgsd_r") && !StringUtils.isBlank(followupMap.get("zgsd_r").toString())) {
                            zgResultRight += "创面深度";
                            String zgsd_r = followupMap.get("zgsd_r").toString();
                            zgsdRight = getSdText(zgsd_r);
                            String remark = "";
                            if (null != followupMap.get("zgsdbz_r") && !StringUtils.isBlank(followupMap.get("zgsdbz_r").toString())) {
                                remark = "（" + followupMap.get("zgsdbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                zgResultRight += zgsdRight + "，";
                            } else {
                                zgResultRight += zgsdRight + remark;
                            }
                        }
                        //窦道/潜行
                        if (null != followupMap.get("zgqx_r") && !StringUtils.isBlank(followupMap.get("zgqx_r").toString())) {
                            zgResultRight += "窦道/潜行";
                            String zgqx_r = followupMap.get("zgqx_r").toString();
                            zgqxRight = getQxText(zgqx_r);
                            String remark = "";
                            if (null != followupMap.get("zgqxbz_r") && !StringUtils.isBlank(followupMap.get("zgqxbz_r").toString())) {
                                remark = "（" + followupMap.get("zgqxbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                zgResultRight += zgqxRight + "，";
                            } else {
                                zgResultRight += zgqxRight + remark;
                            }
                        }
                        String result = StringUtils.isBlank(zgResultRight) ? "您的右脚足跟有创面；" : "您的右脚足跟有创面，" + zgResultRight.substring(0, zgResultRight.length() - 1) + "；";
                        cmjcList.add(result);
                    } else if ("5".equals(right)) {   //踝
                        String hResultRight = "";  //右脚踝创面检查结果
                        String hmjRight = "";      //右脚踝面积结果
                        String hsdRight = "";      //右脚踝深度结果
                        String hqxRight = "";      //右脚踝窦道/潜行结果
                        //面积
                        if (null != followupMap.get("hmj_r") && !StringUtils.isBlank(followupMap.get("hmj_r").toString())) {
                            hResultRight += "创面面积";
                            String hmj_r = followupMap.get("hmj_r").toString();
                            hmjRight = getMjText(hmj_r);
                            String remark = "";
                            if (null != followupMap.get("hmjbz_r") && !StringUtils.isBlank(followupMap.get("hmjbz_r").toString())) {
                                remark = "（" + followupMap.get("hmjbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                hResultRight += hmjRight + "，";
                            } else {
                                hResultRight += hmjRight + remark;
                            }
                        }
                        //深度
                        if (null != followupMap.get("hsd_r") && !StringUtils.isBlank(followupMap.get("hsd_r").toString())) {
                            hResultRight += "创面深度";
                            String hsd_l = followupMap.get("hsd_r").toString();
                            hsdRight = getSdText(hsd_l);
                            String remark = "";
                            if (null != followupMap.get("hsdbz_r") && !StringUtils.isBlank(followupMap.get("hsdbz_r").toString())) {
                                remark = "（" + followupMap.get("hsdbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                hResultRight += hsdRight + "，";
                            } else {
                                hResultRight += hsdRight + remark;
                            }
                        }
                        //窦道/潜行
                        if (null != followupMap.get("hqx_r") && !StringUtils.isBlank(followupMap.get("hqx_r").toString())) {
                            hResultRight += "窦道/潜行";
                            String hqx_r = followupMap.get("hqx_r").toString();
                            hqxRight = getQxText(hqx_r);
                            String remark = "";
                            if (null != followupMap.get("hqxbz_r") && !StringUtils.isBlank(followupMap.get("hqxbz_r").toString())) {
                                remark = "（" + followupMap.get("hqxbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                hResultRight += hqxRight + "，";
                            } else {
                                hResultRight += hqxRight + remark;
                            }
                        }
                        String result = StringUtils.isBlank(hResultRight) ? "您的右脚踝有创面；" : "您的右脚踝有创面，" + hResultRight.substring(0, hResultRight.length() - 1) + "；";
                        cmjcList.add(result);
                    } else if ("6".equals(right)) {   //小腿
                        String xtResultRight = "";  //右脚小腿创面检查结果
                        String xtmjRight = "";      //右脚小腿面积结果
                        String xtsdRight = "";      //右脚小腿深度结果
                        String xtqxRight = "";      //右脚小腿窦道/潜行结果
                        //面积
                        if (null != followupMap.get("xtmj_r") && !StringUtils.isBlank(followupMap.get("xtmj_r").toString())) {
                            xtResultRight += "创面面积";
                            String xtmj_l = followupMap.get("xtmj_r").toString();
                            xtmjRight = getMjText(xtmj_l);
                            String remark = "";
                            if (null != followupMap.get("xtmjbz_r") && !StringUtils.isBlank(followupMap.get("xtmjbz_r").toString())) {
                                remark = "（" + followupMap.get("xtmjbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                xtResultRight += xtmjRight + "，";
                            } else {
                                xtResultRight += xtmjRight + remark;
                            }
                        }
                        //深度
                        if (null != followupMap.get("xtsd_r") && !StringUtils.isBlank(followupMap.get("xtsd_r").toString())) {
                            xtResultRight += "创面深度";
                            String xtsd_r = followupMap.get("xtsd_r").toString();
                            xtsdRight = getSdText(xtsd_r);
                            String remark = "";
                            if (null != followupMap.get("xtsdbz_r") && !StringUtils.isBlank(followupMap.get("xtsdbz_r").toString())) {
                                remark = "（" + followupMap.get("xtsdbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                xtResultRight += xtsdRight + "，";
                            } else {
                                xtResultRight += xtsdRight + remark;
                            }
                        }
                        //窦道/潜行
                        if (null != followupMap.get("xtqx_r") && !StringUtils.isBlank(followupMap.get("xtqx_r").toString())) {
                            xtResultRight += "窦道/潜行";
                            String xtqx_r = followupMap.get("xtqx_r").toString();
                            xtqxRight = getQxText(xtqx_r);
                            String remark = "";
                            if (null != followupMap.get("xtqxbz_r") && !StringUtils.isBlank(followupMap.get("xtqxbz_r").toString())) {
                                remark = "（" + followupMap.get("xtqxbz_r").toString() + "），";
                            }
                            if (StringUtils.isBlank(remark)) {
                                xtResultRight += xtqxRight + "，";
                            } else {
                                xtResultRight += xtqxRight + remark;
                            }
                        }
                        String result = StringUtils.isBlank(xtResultRight) ? "您的右脚小腿有创面；" : "您的右脚小腿有创面，" + xtResultRight.substring(0, xtResultRight.length() - 1) + "；";
                        cmjcList.add(result);
                    }
                }
            }
            //凉、温感觉 1:正常 2:异常 ，
            String lwdjLeft = "";
            String lwdjRight = "";
            if (null != followupMap.get("lwdj") && !StringUtils.isBlank(followupMap.get("lwdj").toString())) {
                lwdjLeft = followupMap.get("lwdj").toString();
            }
            if (null != followupMap.get("lwdj_r") && !StringUtils.isBlank(followupMap.get("lwdj_r").toString())) {
                lwdjRight = followupMap.get("lwdj_r").toString();
            }
            if ("".equals(lwdjLeft)) {
                if ("1".equals(lwdjRight)) {
                    String lwdj = "右脚凉、温度觉正常；";
                    cmjcList.add(lwdj);
                } else if ("2".equals(lwdjRight)) {
                    String lwdj = "右脚凉、温度觉异常；";
                    cmjcList.add(lwdj);
                }
            } else if ("1".equals(lwdjLeft)) {
                if ("".equals(lwdjRight)) {
                    String lwdj = "左脚凉、温度觉正常；";
                    cmjcList.add(lwdj);
                } else if ("1".equals(lwdjRight)) {
                    String lwdj = "左、右脚凉、温度觉正常；";
                    cmjcList.add(lwdj);
                } else if ("2".equals(lwdjRight)) {
                    String lwdj = "左脚凉、温度觉正常，右脚凉、温度觉异常；";
                    cmjcList.add(lwdj);
                }
            } else if ("2".equals(lwdjLeft)) {
                if ("".equals(lwdjRight)) {
                    String lwdj = "左脚凉、温度觉异常；";
                    cmjcList.add(lwdj);
                } else if ("1".equals(lwdjRight)) {
                    String lwdj = "左脚凉、温度觉异常，右脚凉、温度觉正常；";
                    cmjcList.add(lwdj);
                } else if ("2".equals(lwdjRight)) {
                    String lwdj = "左、右脚凉、温度觉异常；";
                    cmjcList.add(lwdj);
                }
            }
            //痛觉 1:正常 2:异常
            String tjLeft = "";
            String tjRight = "";
            if (null != followupMap.get("tj") && !StringUtils.isBlank(followupMap.get("tj").toString())) {
                tjLeft = followupMap.get("tj").toString();
            }
            if (null != followupMap.get("tj_r") && !StringUtils.isBlank(followupMap.get("tj_r").toString())) {
                tjRight = followupMap.get("tj_r").toString();
            }
            if ("".equals(tjLeft)) {
                if ("1".equals(tjRight)) {
                    String tj = "右脚痛觉正常；";
                    cmjcList.add(tj);
                } else if ("2".equals(tjRight)) {
                    String tj = "右脚痛觉异常；";
                    cmjcList.add(tj);
                }
            } else if ("1".equals(tjLeft)) {
                if ("".equals(tjRight)) {
                    String tj = "左脚痛觉正常；";
                    cmjcList.add(tj);
                } else if ("1".equals(tjRight)) {
                    String tj = "左、右脚痛觉正常；";
                    cmjcList.add(tj);
                } else if ("2".equals(tjRight)) {
                    String tj = "左脚痛觉正常，右脚痛觉异常；";
                    cmjcList.add(tj);
                }
            } else if ("2".equals(tjLeft)) {
                if ("".equals(tjRight)) {
                    String tj = "左脚痛觉异常；";
                    cmjcList.add(tj);
                } else if ("1".equals(tjRight)) {
                    String tj = "左脚痛觉异常，右脚痛觉正常；";
                    cmjcList.add(tj);
                } else if ("2".equals(tjRight)) {
                    String tj = "左、右脚痛觉异常；";
                    cmjcList.add(tj);
                }
            }
            //10g尼龙丝触觉 1:正常 2:缺失 3:消失
            String nlsLeft = "";
            String nlsRight = "";
            if (null != followupMap.get("nlscjjc") && !StringUtils.isBlank(followupMap.get("nlscjjc").toString())) {
                nlsLeft = followupMap.get("nlscjjc").toString();
            }
            if (null != followupMap.get("nlscjjc_r") && !StringUtils.isBlank(followupMap.get("nlscjjc_r").toString())) {
                nlsRight = followupMap.get("nlscjjc_r").toString();
            }
            if ("".equals(nlsLeft)) {
                if ("1".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查右脚正常；";
                    cmjcList.add(nls);
                } else if ("2".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查右脚缺失；";
                    cmjcList.add(nls);
                } else if ("3".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查右脚消失；";
                    cmjcList.add(nls);
                }
            } else if ("1".equals(nlsLeft)) {
                if ("".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查左脚正常；";
                    cmjcList.add(nls);
                } else if ("1".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查左，右脚正常；";
                    cmjcList.add(nls);
                } else if ("2".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查左脚正常，右脚缺失；";
                    cmjcList.add(nls);
                } else if ("3".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查左脚正常，右脚消失；";
                    cmjcList.add(nls);
                }
            } else if ("2".equals(nlsLeft)) {
                if ("".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查左脚缺失；";
                    cmjcList.add(nls);
                } else if ("1".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查左脚缺失，右脚正常；";
                    cmjcList.add(nls);
                } else if ("2".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查左，右脚缺失；";
                    cmjcList.add(nls);
                } else if ("3".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查左脚缺失，右脚消失；";
                    cmjcList.add(nls);
                }
            } else if ("3".equals(nlsLeft)) {
                if ("".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查左脚消失；";
                    cmjcList.add(nls);
                } else if ("1".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查左脚消失，右脚正常；";
                    cmjcList.add(nls);
                } else if ("2".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查左脚消失，右脚缺失；";
                    cmjcList.add(nls);
                } else if ("3".equals(nlsRight)) {
                    String nls = "10g尼龙丝触觉检查左，右脚消失；";
                    cmjcList.add(nls);
                }
            }
            //踝反射检查 1:正常 2:异常
            String hfsjcLeft = "";
            String hfsjcRight = "";
            if (null != followupMap.get("hfsjc") && !StringUtils.isBlank(followupMap.get("hfsjc").toString())) {
                hfsjcLeft = followupMap.get("hfsjc").toString();
            }
            if (null != followupMap.get("hfsjc_r") && !StringUtils.isBlank(followupMap.get("hfsjc_r").toString())) {
                hfsjcRight = followupMap.get("hfsjc_r").toString();
            }
            if ("".equals(hfsjcLeft)) {
                if ("1".equals(hfsjcRight)) {
                    String hfs = "踝反射检查右脚正常；";
                    cmjcList.add(hfs);
                } else if ("2".equals(hfsjcRight)) {
                    String hfs = "踝反射检查右脚异常；";
                    cmjcList.add(hfs);
                }
            } else if ("1".equals(hfsjcLeft)) {
                if ("".equals(hfsjcRight)) {
                    String hfs = "踝反射检查左脚正常；";
                    cmjcList.add(hfs);
                } else if ("1".equals(hfsjcRight)) {
                    String hfs = "踝反射检查左，右脚正常；";
                    cmjcList.add(hfs);
                } else if ("2".equals(hfsjcRight)) {
                    String hfs = "踝反射检查左脚正常，右脚异常；";
                    cmjcList.add(hfs);
                }
            } else if ("2".equals(hfsjcLeft)) {
                if ("".equals(hfsjcRight)) {
                    String hfs = "踝反射检查左脚异常；";
                    cmjcList.add(hfs);
                } else if ("1".equals(hfsjcRight)) {
                    String hfs = "踝反射检查左脚异常，右脚正常；";
                    cmjcList.add(hfs);
                } else if ("2".equals(hfsjcRight)) {
                    String hfs = "踝反射检查左，右脚异常；";
                    cmjcList.add(hfs);
                }
            }
        }
        resultMap.put("cmjcList", cmjcList);  //目前创面&检查基本情况
        String jsonString = JsonSerializer.objectToJson(resultMap);
        return jsonString;
    }

    private static String getMjText(String mjType) {
        //面积
        String mjText = "";
        String[] split = mjType.split(",");
        //1 : <1   2 : 1-2  3 : 2-5  4 : 5-10 5 : 10-30  6 : >30
        for (String s : split) {
            if ("1".equals(s)) {
                mjText += "<1cm²";
            } else if ("2".equals(s)) {
                mjText += "1-2cm²";
            } else if ("3".equals(s)) {
                mjText += "2-5cm²";
            } else if ("4".equals(s)) {
                mjText += "5-10cm²";
            } else if ("5".equals(s)) {
                mjText += "10-30cm²";
            } else if ("6".equals(s)) {
                mjText += ">30cm²";
            }
        }
        return mjText;
    }
    private static String getSdText(String sdType){
        String sdText = "";
        String[] split = sdType.split(",");
        //1 : <5  2 : 5-9  3 : 10-20  4 : >20
        for (String s : split) {
            if ("1".equals(s)) {
                sdText += "<5mm";
            } else if ("2".equals(s)) {
                sdText += "5-9mm";
            } else if ("3".equals(s)) {
                sdText += "10-20mm";
            } else if ("4".equals(s)) {
                sdText += ">20mm";
            }
        }
        return sdText;
    }
    private static String getQxText(String qxType){
        String qxText = "";
        String[] split = qxType.split(",");
        //1 : <2  2 : 2-5  3 : >5  4 : 无
        for (String s : split) {
            if ("1".equals(s)) {
                qxText += "<2mm";
            } else if ("2".equals(s)) {
                qxText += "2-5mm";
            } else if ("3".equals(s)) {
                qxText += ">5mm";
            }
        }
        return qxText;
    }


}

