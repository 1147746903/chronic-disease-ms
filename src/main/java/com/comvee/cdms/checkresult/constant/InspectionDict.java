package com.comvee.cdms.checkresult.constant;

/**
 * 检查项目字典
 */
public enum InspectionDict {

    ABI("abi" ,"踝肱指数（ABI）检查"),
    VPT("vpt" ,"足部震动感觉阈值（VPT）检查"),
    EYES("eyes" ,"眼底检查"),
    //（不会单独存在，和abi一起做）
    PWV("pwv" ,"脉搏波传导速度（PWV）检查"),
    HEALTH_REPORT("healthReport" ,"健康记录报告"),
    CCDEB("ccdeb","彩超(同时检查两个计价部位以上的，第二部"),
    SXZDJMXGCSDPLCS("sxzdjmxgcsdplcs","双下肢动静脉血管彩色多普勒超声"),
    CCSSSNGPG2("ccsssngpg2","彩超(双肾、输尿管、膀胱)"),
    SXZJMCC("sxzjmcc","双下肢静脉彩超"),
    QBCC("qbcc","腔内彩超"),
    XZDJMXGCSDOLCS("xzdjmxgcsdolcs","下肢动静脉血管彩色多普勒超声"),
    ZZXW("zzxw","足正斜位"),
    CK1JST("ck1jst","产科I级（双胎）"),
    GGDRZCW("ggDRzcw","肱骨DR正侧位"),
    CBCSJC("cbcsjc","床边超声检查"),
    CRGDRZCW("crgDRzcw","尺桡骨DR正侧位"),
    DWGCW("dwgcw","骶尾骨侧位"),
    FBTS("fbts","腹部透视"),
    XFBCTPS("xfbCTps","下腹部CT平扫"),
    JDMCC("jdmcc","劲动脉彩超"),
    YCXZDMCC("ycxzdmcc","一侧下肢动脉彩超"),
    MRAXGCX("MRAxgcx","MRA血管成像"),
    JYDCC("jydcc","经阴道彩超"),
    MNXTCCN("mnxtccn","泌尿系统彩超(女)"),
    YJFGZCW("yjfgzcw","右胫腓骨正侧位"),
    CCDEB2("ccdeb2","彩超(同时检查两个计价部位以上的，第二部"),
    JBXGCSDPLCS("jbxgcsdplcs","颈部血管彩色多普勒超声"),
    CTZJZQHCL("CTzjzqhcl","（16排）CT直接增强+后处理"),
    CCSSSNGPGQLX("ccsssngpgqlx","彩超(双肾、输尿管、膀胱、前列腺)"),
    ZGGCW("zggcw","左跟骨侧位"),
    KGJDRZW("kgjDRzw","髋关节DR正位"),
    CTPS("CTps","（16排）CT平扫"),
    GGZWP("ggzwp","跟骨轴位片"),
    SBYDFX("sbydfx","室壁运动分析"),
    CK1JSC("ck1jsc","产科Ⅰ级筛查"),
    XBPSZQ("xbpszq","胸部平扫+增强"),
    YKCTPS("ykCTps","眼眶CT平扫"),
    DEBWJS("debwjs","第二部位加收"),
    CTMRIPSJZQ("ctMRIpsjzq","垂体MRI平扫加增强"),
    XFSDY2("xfsdy2","胸腹水定位"),
    XZCSDPLCS("xzcsdplcs","心脏彩色多普勒超声"),
    CTXFB2("CTxfb2","CT(下腹部)"),
    ZSZZXW("zszzxw","左手掌正斜位"),
    SXZDJMCC("sxzdjmcc","双下肢动静脉彩超"),
    YCXZJMCC("ycxzjmcc","一侧下肢静脉彩超"),
    FBZP2("fbzp2","腹部平片"),
    MRPS("MRps","MR平扫"),
    TLPS("tlps","头颅平扫"),
    CCGDYP2("ccgdyp2","彩超(肝、胆、胰、脾）"),
    XGJDRZCW("xgjDRzcw","膝关节DR正侧位"),
    XFSDY("xfsdy","胸腹水定位"),
    GGZCW("ggzcw","跟骨侧位片"),
    XHXTCC2("xhxtcc2","消化系统彩超"),
    XBCTPS("xbCTps","胸部CT平扫"),
    XBPS("xbps","胸部平扫"),
    QBXQGCC("qbxqgcc","浅表小器官彩超"),
    HGJDRZCW("hgjDRzcw","踝关节DR正侧位"),
    DEBWJS2("debwjs2","第二部位加收"),
    JSJTWBG("jsjtwbg","计算机图文报告"),
    KGJZW("kgjzw","髋关节正位"),
    DRZWP("DRzwp","DR正位片"),
    QBXQGCC2("qbxqgcc2","浅表小器官彩超"),
    SZJMXGDC("szjmxgdc","上肢静脉血管（单侧）"),
    SZDMXGDC("szdmxgdc","上肢动脉血管（单侧）"),
    DBL2("dbl2","碘帕醇注射液（典比乐）[37g：100ml/瓶]"),
    JBXGCSDPLCS2("jbxgcsdplcs2","颈部血管彩色多普勒超声"),
    JZCGZ2("jzcgz2","颈椎磁共振"),
    SXZDJMCC2("sxzdjmcc2","双下肢动静脉彩超"),
    ZJGJZXW("zjgjzxw","左肩关节正斜位"),
    XWWSEZCJJCDZJDCH("xwwsezcjjcdzjdcH","纤维胃十二指肠镜检查+电子镜+彩色打印照片+HP"),
    SFBMRIPSZQX("sfbMRIpszqx","上腹部MRI平扫加增强"),
    XHXCCDEBW("xhxccdebw","消化系彩超第二部位"),
    ZXGJZCW("zxgjzcw","左膝关节正侧位"),
    CRSFB("CRsfb","CT(上腹部)"),
    CCCSDPLCSCGJCTWBG("cccsdplcscgjctwbg","彩超彩色多普勒超声常规检查+图文报告"),
    YZDRZCW("yzDRzcw","腰椎DR正侧位"),
    XDTJCNKCB("xdtjcnkcb","心电图检查(内科床边)"),
    JZXCC2("jzxcc2","甲状腺彩超"),
    YHGJZCW2("yhgjzcw2","右踝关节正侧位"),
    XBDRZW("xbDRzw","胸部DR正位"),
    YCSZDMCC("ycszdmcc","一侧上肢动脉彩超"),
    DRXZCWPTJZY("DRxzcwptjzy","DR胸正侧位片（体检专用）"),
    ZZZXW2("zzzxw2","左足正斜位"),
    YZJPCTPS("yzjpCTps","腰椎间盘CT平扫"),
    SJZDMXGCSDPLCS("sjzdmxgcsdplcs","双下肢动脉血管彩色多普勒超声"),
    SXZJMXGCSDPLCS("sxzjmxgcsdplcs","双下肢静脉血管彩色多普勒超声"),
    XBZCWPBCP("xbzcwpbcp","胸部正侧位片(不出片)"),
    ZGSFJCC2("zgsfjcc2","子宫双附件彩超"),
    GPPP("gppp","骨盆平片"),
    YJGJZXW2("yjgjzxw2","右肩关节正斜位"),
    FBTS2("fbts2","腹部透视"),
    CCTEGQ2("cctegq2","彩超(胎儿、宫腔)"),
    CTPSDG("CTpsdg","CT平扫(单个)"),
    XBTS2("xbts2","胸部透视"),
    ZFBCTPS("zfbCTps","中腹部CT平扫"),
    YCXZDMCC2("ycxzdmcc2","一侧下肢动脉彩超"),
    YZMRIPS("yzMRIps","腰椎MRI平扫"),
    CTPSDG2("CTpsdg2","CT平扫(单个)"),
    YJFGZCW2("yjfgzcw2","右胫腓骨正侧位"),
    ZJGJZW("zjgjzw","左肩关节正位"),
    TECC("tecc","胎儿彩超"),
    ZXGJZCW2("zxgjzcw2","左膝关节正侧位"),
    CTPSHCL2("CTpshcl2","（16排）CT平扫+后处理"),
    XWJCJJC("xwjcjjc","纤维结肠镜检查"),
    JBXGCSDPLCSJS("jbxgcsdplcsjs","颈部血管彩色多普勒超声(每增加两根加收)"),
    CGXDTCED("cgxdtced","常规心电图+常二导"),
    TLCTPS("tlCTps","头颅CT平扫"),
    ZZGJZCW("zzgjzcw","左肘关节正侧位"),
    JZZKZW("jzzkzw","颈椎张口正位"),
    CCXZ2("ccxz2","彩超(心脏)"),
    CYNCD("cyncd","残余尿测定"),
    SZDMCC("szdmcc","上肢动脉彩超（单侧）"),
    CTTL("CTtl","CT(头颅)"),
    ZRCGZCW("zrcgzcw","左桡尺骨正侧位"),
    XGJMRIPS("xgjMRIps","膝关节MRI平扫"),
    JGJMRIPS("jgjMRIps","肩关节MRI平扫"),
    JBXGCC("jbxgcc","颈部血管彩超"),
    JZDRZCW("jzDRzcw","颈椎DR正侧位"),
    GPDRZW("gpDRzw","骨盆DR正位"),
    CTXGCX("CTxgcx","（16排）CT血管成像"),
    CGZPS("cgzps","磁共振平扫"),
    ZJFGZCW("zjfgzcw","左胫腓骨正侧位"),
    XHXTCCMMXTCC("xhxtccmmxtcc","消化系统彩超+门脉系统彩超"),
    JZXJBLBCC("jzxjblbcc","甲状腺+颈部淋巴结彩超"),
    SCDMJMCC2("scdmjmcc2","双侧动脉(静脉)彩超"),
    JYXPJS2("jyxpjs2","基因芯片技术"),
    HGJMRIPS("hgjMRIps","踝关节MRI平扫"),
    ZZDPLXX("zzdplxx","组织多普勒显象"),
    SGBCTS("sgbcts","食管钡餐透视"),
    JSJMCC("jsjmcc","精索静脉彩超"),
    YCSZJMCC("ycszjmcc","一侧上肢静脉彩超"),
    YXBZXW2("yxbzxw2","右胸部正斜位"),
    DRCWP("DRcwp","DR侧位片"),
    JGJZW("jgjzw","肩关节正位"),
    CCDPLCSCGJCXZ("ccdplcscgjcxz","彩超多普勒超声常规检查(心脏)"),
    FBDMHSW("fbdmhsw","付鼻窦面华氏位"),
    MNXCCDEBW("mnxccdebw","泌尿系彩超第二部位"),
    YGGZW("yggzw","右跟骨轴位"),
    SCSZDJMCC("scszdjmcc","双侧上肢动静脉彩超"),
    XBTS("xbts","胸部透视"),
    WGJDRZCW("wgjDRzcw","腕关节DR正侧位"),
    WJ("wj","胃镜"),
    DRZWPBCP("DRzwpbcp","DR正位片(不出片)"),
    QNCSDPLCSJCJYD("qncsdplcsjcjyd","腔内彩色多普勒超声检查[经阴道]"),
    CCRXJYW("ccrxjyw","彩超（乳腺及腋窝）"),
    DWGZCW("dwgzcw","骶尾骨正侧位"),
    CBCSYD("cbcsyd","床边超声引导"),
    XZCC("xzcc","心脏彩超"),
    YZZXW("yzzxw","右足正斜位"),
    CGZZQSM("cgzzqsm","磁共振增强扫描"),
    ZGJDRZCW("zgjDRzcw","肘关节DR正侧位"),
    YHGJZCW("yhgjzcw","右踝关节正侧位"),
    ZSZZXW2("zszzxw2","左手掌正斜位"),
    FBZP("fbzp","腹部平片"),
    CTPSSQZQHCL("CTpssqzqhcl","（16排）CT平扫+三期增强+后处理"),
    NQFCC("nqfcc","男全腹彩超"),
    GBPAZSY("gbpazsy","钆贝葡胺注射液"),
    CCWCD2("ccwcd2","彩超(胃肠道)"),
    JZCW2("jzcw2","颈椎侧位"),
    SXZDMCC2("sxzdmcc2","双下肢动脉彩超"),
    GWJFGCC("gwjfgcc","睾丸及附睾彩超"),
    YGGZCW("yggzcw","右股骨正侧位"),
    QBQGCC("qbqgcc","浅表器官彩超"),
    JDMCC2("jdmcc2","颈动脉彩超"),
    CCJSJTWBG2("ccjsjtwbg2","彩超计算机图文报告"),
    CTGGJSWCX("CTggjswcx","(16排)CT骨关节三维成像"),
    DFCZSY("dfczsy","碘佛醇注射液[100ml]"),
    ZBCTPS("zbCTps","足部CT平扫"),
    CCQBQGCSDPLCSJC("ccqbqgcsdplcsjc","彩超浅表器官彩色多普勒超声检查"),
    XJZCW("xjzcw","胸椎正侧位"),
    ZGZXGCXMRA ("zgzxgcxMRA ","磁共振血管成象（MRA）"),
    CTFB("CTfb","CT(肺部)"),
    GGDRZCW2("ggDRzcw2","股骨DR正侧位"),
    YZGJZCW("yzgjzcw","右肘关节正侧位"),
    SFBCTPSZJ("sfbCTpszj","上腹部CT平扫+增强"),
    CTGGJSWCX2("CTggjswcx2","(16排)CT骨关节三维成像"),
    GZMRIPSJZQ("gzMRIpsjzq","肝脏MRI平扫加增强"),
    XZMRIPS("xzMRIps","胸椎MRI平扫"),
    CTXFB("CTxfb","CT(下腹部)"),
    DRZCP("DRzcp","DR正侧位片"),
    CTZFB2("CTzfb2","CT(中腹部)"),
    YCXZDJMCC("ycxzdjmcc","一侧下肢动静脉彩超"),
    YZJPCTPSJSWCK("yzjpCTpsjswck","腰椎间盘CT平扫加三维重建"),
    CK1JSC2("ck1jsc2","产科Ⅰ级筛查"),
    JBXGCSDPLCSEGXG("jbxgcsdplcsegxg","颈部血管彩色多普勒超声 二根血管"),
    DEBWSXHX("debwsxhx","第二部位收（消化系）"),
    JYDCC2("jydcc2","经阴道彩超"),
    JZZCW2("jzzcw2","颈椎正侧位"),
    ZJXJBLBJCC("zjxjblbjcc","甲状腺+颈部淋巴结彩超"),
    CGZSCX("cgzscx","磁共振水成象（MRCP，MRM，MRU）"),
    QNCC("qncc","腔内彩超"),
    XZCCCZST("xzccczsT","心脏彩超（彩色多普勒+左心功能测定+室壁运动分析+TDI）"),
    TLCTPSJZQ("tlCTpsjzq","头颅CT平扫加增强"),
    XBZCW2("xbzcw2","胸部正侧位"),
    XZDMXGCSDPLCSDC("xzdmxgcsdplcsdc","下肢动脉血管彩色多普勒超声（单侧）"),
    XBZCWPBCP2("xbzcwpbcp2","胸部正侧位片(不出片)"),
    JZMRIPS("jzMRIps","颈椎MRI平扫"),
    JZZCW("jzzcw","颈椎正侧位"),
    RXCC("rxcc","乳腺彩超"),
    XFZDMCC("xfzdmcc","胸腹主动脉彩超"),
    SZJMXGCSDPLCSDC("szjmxgcsdplcsdc","上肢静脉血管彩色多普勒超声（单侧）"),
    FBPPDRLW("fbppDRlw","腹部平片DR（立位）"),
    XZJMXGCSDPLCSDC("xzjmxgcsdplcsdc","下肢静脉血管彩色多普勒超声（单侧）"),
    CCWCD("ccwcd","彩超(胃肠道)"),
    DKGJMRIPS("dkgjMRIps","骶髂关节MRI平扫"),
    CCSSSNGPG("ccsssngpg","彩超(双肾、输尿管、膀胱)"),
    FKCC3("fkcc3","妇科彩超"),
    FKCC2("fkcc2","妇科彩超"),
    XHXCC("xhxcc","消化系彩超"),
    CTMRIPS("ctMRIps","垂体MRI平扫"),
    ZWGJZCW("zwgjzcw","左腕关节正侧位"),
    ZGGCW2("zggcw2","左跟骨侧位"),
    ZHGJZCW("zhgjzcw","左踝关节正侧位"),
    LNMRIPSJZQ("lnMRIpsjzq","颅脑MRI平扫加增强"),
    CTFB2("CTfb2","CT(肺部)"),
    YCXZJMCC2("ycxzjmcc2","一侧下肢静脉彩超"),
    KGJZW2("kgjzw2","髋关节正位"),
    YCXZJMCC3("ycxzjmcc3","胃镜"),
    DQGJDRZW("dqgjDRzw","骶髂关节DR正位"),
    JZXLBJCC("jzxlbjcc","甲状腺+淋巴结彩超"),
    XHMNXTCC("xhmnxtcc","消化+泌尿系统彩超"),
    XZCCEMCZ("xzcceMcz","心脏彩超(二维+M型+彩色多普勒+左心功能测定）"),
    CCMNX("ccmnx","彩超(泌尿系)"),
    YZCGZ("yzcgz","腰椎磁共振"),
    LNPS("lnps","颅脑平扫"),
    XWWSEZCJJCDZJHP("xwwsezcjjcdzjHP","纤维胃十二指肠镜检查+电子镜+HP"),
    SSJSXGCC2("ssjsxgcc2","双肾及肾血管彩超"),
    C14HQSY("C14hqsy","14碳呼气试验"),
    ZFBCTPSZJ("zfbCTpszj","中腹部CT平扫+增强"),
    SCDMJMCC("scdmjmcc","双侧动脉(静脉)彩超"),
    MNXCC3("mnxcc3","泌尿系彩超"),
    LNMRIPS("lnMRIps","颅脑MRI平扫"),
    ZXGNCD("zxgncd","左心功能测定"),
    ZHGJZCW2("zhgjzcw2","左踝关节正侧位"),
    MNXCC2("mnxcc2","泌尿系彩超"),
    CCJSJTWBG("ccjsjtwbg","彩超计算机图文报告"),
    XZCC3("xzcc3","心脏彩超"),
    XZCC2("xzcc2","心脏彩超"),
    JZCGZ("jzcgz","颈椎磁共振"),
    ZZZXW("zzzxw","左足正斜位"),
    GPPP2("gppp2","骨盆平片"),
    CCXZ("ccxz","彩超(心脏)"),
    CTPSZJ("CTpszj","CT平扫+增强"),
    YZZCW("yzzcw","腰椎正侧位"),
    YZZCW2("yzzcw2","腰椎正侧位"),
    CGZPSPTDG("cgzpsptdg","磁共振平扫(普通:单个)"),
    SJZJMCC("sjzjmcc","双下肢静脉彩超"),
    YSZZXW("yszzxw","右手掌正斜位"),
    CCJZXJJBLBJ("ccjzxjjblbj","彩超（甲状腺及颈部淋巴结）"),
    YJGJZXW("yjgjzxw","右肩关节正斜位"),
    KGJZCW("kgjzcw","髋关节正斜位"),
    CGZXGCXMRA("cgzxgcxMRA","磁共振血管成像（MRA）"),
    XZDRZCW("xzDRzcw","胸椎DR正侧位"),
    ZGGZCW("zggzcw","左股骨正侧位"),
    FBCTPS("fbCTps","肺部CT平扫"),
    XWWSEZCJJC("xwwsezcjjc","纤维胃十二指肠镜检查"),
    ZDRZXW("zDRzxw","足DR正斜位"),
    SWSGJJC("swsgjjc","纤维食管镜检查"),
    SZDMXGCSDPLCSDZ2G("szdmxgcsdplcsdz2g","上肢动脉血管彩色多普勒超声（单做2根）[单做的2根血管]"),
    FMHCC("fmhcc","腹膜后彩超"),
    CTTL2("CTtl2","CT(头颅)"),
    YJGJZW("yjgjzw","右肩关节正位"),
    YCXZDJMCC2("ycxzdjmcc2","一侧下肢动静脉彩超"),
    JZSGXDMJS("jzsgxdmjs","加做锁骨下动脉加收"),
    XBDRZCW("xbDRzcw","胸部DR正侧位"),
    KGJMRIPS("kgjMRIps","髋关节MRI平扫"),
    CTZFB("CTzfb","CT(中腹部)"),
    XHMNXTCC2("xhmnxtcc2","消化+泌尿系统彩超"),
    JCJJCDZJCSDYZP("jcjjcdzjcsdyzp","结肠镜检查+电子镜+彩色打印照片"),
    CGSPSDG("cgspsdg","磁共振平扫(普通:单个)"),
    CGZXGCXZJ("cgzxgcxzj","磁共振血管成象（MRV）"),
    MRPSZQ("MRpszq","MR平扫+增强"),
    DGZDRZCW("dgzDRzcw","骶尾椎DR正侧位"),
    TLZW("tlzw","头颅正位"),
    CTSFB("CTsfb","CT(上腹部)"),
    JZXCC("jzxcc","甲状腺彩超"),
    CGXDT("cgxdt","常规心电图"),
    CCGDYP("ccgdyp","彩超(肝、胆、胰、脾）"),
    DRZWP3("DRzwp3","DR正位片"),
    DRZWP4("DRzwp4","DR正位片"),
    CCZGFJ("cczgfj","彩超(子宫、附件)"),
    DRZWP2("DRzwp2","DR正位片"),
    XBZCW("xbzcw","胸部正侧位"),
    YWGJZCW("ywgjzcw","右桡尺骨正侧位"),
    XFBCTPSZJ("xfbCTpszj","下腹部CT平扫+增强"),
    SBXDTCED("sbxdtced","床边心电图+常二导"),
    XGJZCW("xgjzcw","膝关节正侧位"),
    YZZXW2("yzzxw2","右足正斜位"),
    YGGCW("yggcw","右跟骨侧位"),
    JGJDRZW("jgjDRzw","肩关节DR正位"),
    DRZCWP("DRzcwp","DR正侧位片"),
    YXGJZCW("yxgjzcw","右膝关节正侧位"),
    RXYWLBJCC2("rxywlbjcc2","乳腺+腋窝淋巴结彩超"),
    JSJMCC2("jsjmcc2","精索静脉彩超"),
    RXYWLBJCC3("rxywlbjcc3","乳腺+腋窝淋巴结彩超"),
    YZCGZ2("yzcgz2","腰椎磁共振"),
    XBZW("xbzw","胸部正位"),
    SSXQCC2("ssxqcc2","肾上腺区彩超"),
    JZDRZKW("jzDRzkw","颈椎DR张口位"),
    CCZGFJ2("cczgfj2","彩超(子宫、附件)"),
    XBZW2("xbzw2","胸部正位"),
    XJZCW2("xjzcw2","胸椎正侧位"),
    CCRXJYW2("ccrxjyw2","彩超（乳腺及腋窝）"),
    GMJJC("gmjjc","肛门镜检查"),
    MNXCC("mnxcc","泌尿系彩超"),
    DRZWPBCP2("DRzwpbcp2","DR正位片(不出片)"),
    CTPS3("CTps3","（16排）CT平扫"),
    XZCTPS("xzCTps","胸椎CT平扫"),
    HCGZXGCXMRA("hcgzxgcxMRA","磁共振血管成像（MRA）"),
    JSJTWBG2("jsjtwbg2","计算机图文报告"),
    DBL("dbl","碘帕醇注射液（典比乐）[37g：100ml/瓶]"),
    JSJTWBG3("jsjtwbg3","计算机图文报告"),
    CGZPS1D5T2("cgzps1d5T2","磁共振平扫1.5T"),
    RXCC2("rxcc2","乳腺彩超"),
    SSXQCC("ssxqcc","肾上腺区彩超"),
    GBPAZSY2("gbpazsy2","钆贝葡胺注射液"),
    JZXLBJCC2("jzxlbjcc2","甲状腺+淋巴结彩超"),
    YXBZXW("yxbzxw","右胸部正斜位"),
    PTTS("ptts","普通透视"),
    CGZPS1D5T("cgzps1d5T","磁共振平扫1.5T"),
    RXYWLBJCC("rxywlbjcc","乳腺+腋窝淋巴结彩超"),
    SSJSXGCC("ssjsxgcc","双肾及肾血管彩超"),
    CGZPSZJ("cgzpszj","磁共振平扫+增强"),
    CCDEBWJS("ccdebwjs","彩超第二部位加收"),
    YWGJZCW2("ywgjzcw2","右腕关节正侧位"),
    GGZCW2("ggzcw2","肱骨正侧位"),
    SXZDMCC("sxzdmcc","双下肢动脉彩超"),
    DEBWSFK("debwsfk","第二部位收（妇科）"),
    CCQBQGCSDPLCSJC2("ccqbqgcsdplcsjc2","彩超浅表器官彩色多普勒超声检查"),
    QBQGCC2("qbqgcc2","浅表器官彩超"),
    FZDMCC3("fzdmcc3","腹主动脉彩超"),
    FKCC("fkcc","妇科彩超"),
    FBDCTPS("fbdCTps","副鼻窦CT平扫"),
    QBQGCC3("qbqgcc3","浅表器官彩超"),
    JYXPJS("jyxpjs","基因芯片技术"),
    CTPS2("ctps2","16排CT平扫"),
    JNCSDPLCSJC("jncsdplcsjc","腔内彩色多普勒超声检查[经阴道]"),
    CK2JSC("ck2jsc","产科Ⅱ级筛查"),
    SFBCTPS("sfbCTps","上腹部CT平扫"),
    CCJZXJJBLBJ2("ccjzxjjblbj2","彩超（甲状腺及颈部淋巴结）"),
    KGJZXW("kgjzxw","髋关节正斜位"),
    FBPPLW("fbpplw","腹部平片（立位）"),
    CCTEGQ("cctegq","彩超(胎儿、宫腔)"),
    CTPSHCL("CTpshcl","（16排）CT平扫+后处理"),
    CBXDT("cbxdt","床边心电图"),
    GGCZWP("ggczwp","跟骨侧轴位片"),
    CCCSJSJTWBG("cccsjsjtwbg","(彩超)超声计算机图文报告"),
    YXGJZCW2("yxgjzcw2","右膝关节正侧位"),
    YJGJZW2("yjgjzw2","右肩关节正位"),
    XFZDMCC2("xfzdmcc2","胸腹主动脉彩超"),
    NQFCC2("nqfcc2","女全腹彩超"),
    DEBWSMNX("debwsmnx","第二部位收（泌尿系）"),
    CTSQZQHCL("CTsqzqhcl","（16排）CT三期增强+后处理"),
    CCCSDPLCSCGJCTWBG2("cccsdplcscgjctwbg2","彩超彩色多普勒超声常规检查+图文报告"),
    ZGSFJCC("zgsfjcc","子宫双附件彩超"),
    JFGDRZCW("jfgDRzcw","胫腓骨DR正侧位"),
    CYNCD2("cyncd2","残余尿量测定"),
    JZCW("jzcw","颈椎侧位"),
    JZXLLBJCCDEBW("jzxllbjccdebw","甲状腺+淋巴结彩超第二部位"),
    GZMRIPSZQX("gzMRIpszqx","肝脏MRI平扫加增强"),
    XHXTCC("xhxtcc","消化系统彩超"),
    SCRTCTPS("scrtCTps","双侧乳突CT平扫"),
    XHXCC2("xhxcc2","消化系彩超"),
    DCSZDJMCC("dcszdjmcc","单侧上肢动静脉彩超"),
    UNKNOWN("unknown" ,"未知检查")
    ;

    InspectionDict(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    public String getCode() {
        return code;
    }


    public String getName() {
        return name;
    }

}
