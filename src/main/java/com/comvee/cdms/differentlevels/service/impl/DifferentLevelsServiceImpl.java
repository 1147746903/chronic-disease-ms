package com.comvee.cdms.differentlevels.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.differentlevels.bo.DiffLevelsChartForMemberBO;
import com.comvee.cdms.differentlevels.bo.DiffLevelsChartPointInfoBO;
import com.comvee.cdms.differentlevels.bo.DiffLevelsForMemberBO;
import com.comvee.cdms.differentlevels.dto.ListDiffLevelsDTO;
import com.comvee.cdms.differentlevels.dto.ListDiffLevelsOfMemberDTO;
import com.comvee.cdms.differentlevels.dto.MemberDiffLevelSureDTO;
import com.comvee.cdms.differentlevels.mapper.MemberDifferentLevelsPOMapper;
import com.comvee.cdms.differentlevels.po.MemberDifferentLevelsPO;
import com.comvee.cdms.differentlevels.po.MemberDifferentLevelsPOExample;
import com.comvee.cdms.differentlevels.po.MemberDifferentLevelsPOWithBLOBs;
import com.comvee.cdms.differentlevels.service.DifferentLevelsService;
import com.comvee.cdms.differentlevels.vo.DiffLevelsForMemberVO;
import com.comvee.cdms.differentlevels.vo.DifferentLevelsForWorkVO;
import com.comvee.cdms.differentlevels.vo.MemberCurrentDiffLevelVO;
import com.comvee.cdms.member.po.MemberArchivesPO;
import com.comvee.cdms.member.po.MemberPO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("differentLevelsService")
public class DifferentLevelsServiceImpl implements DifferentLevelsService {

    @Autowired
    private MemberDifferentLevelsPOMapper mapper;

    @Override
    public int getDifferentLevelsLogOfUnRead(String hospitalId) {
        MemberDifferentLevelsPOExample example = new MemberDifferentLevelsPOExample();
        MemberDifferentLevelsPOExample.Criteria criteria = example.createCriteria();
        criteria.andIsValidEqualTo((byte)1);
        criteria.andReadEqualTo((byte)0);
        criteria.andLayerChangeEqualTo((byte)1);
        criteria.andOriginIdEqualTo(hospitalId);
        return this.mapper.countByExample(example);
    }

    @Override
    public void sureUnReadDifferentLevels(MemberDiffLevelSureDTO dto) {
        if(dto.getAdjustment().equals(1)){
            ValidateTool.checkParamIsNull(dto.getAdLayer(),"调整层级");
            ValidateTool.checkParamIsNull(dto.getAdLevel(),"调整等级");
            ValidateTool.checkParamIsNull(dto.getCaReason(),"调整原因");
        }
        MemberDifferentLevelsPOWithBLOBs record = new MemberDifferentLevelsPOWithBLOBs();
        record.setRead(1);
        record.setDoctorId(dto.getDoctorId());
        if(dto.getAdjustment().equals(1)){
            record.setAdjustment(dto.getAdjustment());
            record.setLayer(dto.getAdLayer());
            record.setLevel(dto.getAdLevel());
            record.setCaDesc(dto.getCaReason());
        }
        MemberDifferentLevelsPOExample example = new MemberDifferentLevelsPOExample();
        MemberDifferentLevelsPOExample.Criteria criteria = example.createCriteria();
        criteria.andReadEqualTo((byte)0);
        criteria.andSidEqualTo(dto.getSid());
        this.mapper.updateByExampleSelective(record,example);
    }

    @Override
    public PageResult<DifferentLevelsForWorkVO> pagerDiffLevels(ListDiffLevelsDTO dto, PageRequest pager) {
        PageHelper.startPage(pager.getPage(),pager.getRows());
        MemberDifferentLevelsPOExample example = new MemberDifferentLevelsPOExample();
        MemberDifferentLevelsPOExample.Criteria criteria = example.createCriteria();
        criteria.andIsValidEqualTo((byte)1);
        criteria.andReadEqualTo((byte)0);
        criteria.andLayerChangeEqualTo((byte)1);
        if(!StringUtils.isBlank(dto.getMemberName())){
            criteria.andMemberNameLike("%"+dto.getMemberName()+"%");
        }
        if(!StringUtils.isBlank(dto.getMemberNamePy())){
            criteria.andMemberNamePyLike("%"+dto.getMemberNamePy()+"%");
        }
        if(!StringUtils.isBlank(dto.getOriginId())){
            criteria.andOriginIdEqualTo(dto.getOriginId());
        }
        if(dto.getLayer()!=null){
            criteria.andLayerEqualTo(dto.getLayer().byteValue());
        }
        if(!StringUtils.isBlank(dto.getStartDt()) && !StringUtils.isBlank(dto.getEndDt())){
            criteria.andChangeDtBetween(DateHelper.getDate(dto.getStartDt()+DateHelper.DEFUALT_TIME_START,DateHelper.DATETIME_FORMAT),
                    DateHelper.getDate(dto.getEndDt()+DateHelper.DEFUALT_TIME_END,DateHelper.DATETIME_FORMAT));
        }
        example.setOrderByClause("change_dt desc");
        List<MemberDifferentLevelsPOWithBLOBs> pos = this.mapper.selectByExampleWithBLOBs(example);
        PageResult<MemberDifferentLevelsPOWithBLOBs> poPageResult = new PageResult<MemberDifferentLevelsPOWithBLOBs>(pos);
        List<DifferentLevelsForWorkVO> vos = new ArrayList<>();
        for(MemberDifferentLevelsPOWithBLOBs po : poPageResult.getRows()){
            DifferentLevelsForWorkVO vo = new DifferentLevelsForWorkVO();
            BeanUtils.copyProperties(vo,po);
            vo.setCurrentLayer(po.getLayer());
            MemberDifferentLevelsPO lastPO = this.mapper.getLastLogByNowLog(po.getLayer(),po.getChangeDt(),po.getMemberId(),
                    po.getOriginId());
            if(lastPO!=null){
                vo.setLastLayer(lastPO.getLayer());
            }
            String jsonStr = po.getReasonJson();
            if(!StringUtils.isBlank(jsonStr)){
                //原因结果
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                if(jsonObject!=null){
                    vo.setHlb(jsonObject.getString("jsgythxhdb"));
                    vo.setNwGlu(jsonObject.getString("jyzpjchxt"));
                    vo.setNwGlu0(jsonObject.getString("jyzpjkfxt"));
                    vo.setNwLGlu(jsonObject.getString("jyzdxtcs"));
                    vo.setTnbzfxpgdj(jsonObject.getString("jyctnbzwxdj"));
                    vo.setZwglxwpf(jsonObject.getString("jyczwglxwpf"));
                }
            }
            vos.add(vo);
        }
        PageResult<DifferentLevelsForWorkVO> voPageResult = new PageResult<>();
        BeanUtils.copyProperties(voPageResult,poPageResult);
        voPageResult.setRows(vos);
        return voPageResult;
    }

    @Override
    public DiffLevelsForMemberVO getDiffLevelsForMember(ListDiffLevelsOfMemberDTO dto) {
        MemberDifferentLevelsPOExample example = new MemberDifferentLevelsPOExample();
        MemberDifferentLevelsPOExample.Criteria criteria = example.createCriteria();
        criteria.andIsValidEqualTo((byte)1);
        criteria.andReadEqualTo((byte)1);
        if(!StringUtils.isBlank(dto.getMemberId())){
            criteria.andMemberIdEqualTo(dto.getMemberId());
        }
        if(!StringUtils.isBlank(dto.getOriginId())){
            criteria.andOriginIdEqualTo(dto.getOriginId());
        }
        if(!StringUtils.isBlank(dto.getStartDt()) && !StringUtils.isBlank(dto.getEndDt())){
            criteria.andChangeDtBetween(DateHelper.getDate(dto.getStartDt()+DateHelper.DEFUALT_TIME_START,DateHelper.DATETIME_FORMAT),
                    DateHelper.getDate(dto.getEndDt()+DateHelper.DEFUALT_TIME_END,DateHelper.DATETIME_FORMAT));
        }
        example.setOrderByClause("change_dt desc");
        List<MemberDifferentLevelsPOWithBLOBs> bloBs = this.mapper.selectByExampleWithBLOBs(example);
        if(bloBs!=null && bloBs.size()>0){
            List<String> xArea = new ArrayList<>(bloBs.size());
            List<DiffLevelsChartPointInfoBO> pointInfoBOS = new ArrayList<>();
            DiffLevelsChartForMemberBO chartData = new DiffLevelsChartForMemberBO();
            chartData.setxArea(xArea);
            chartData.setyArea(null);
            chartData.setArrayData(pointInfoBOS);
            List<DiffLevelsForMemberBO> dataInfos = new ArrayList<>(bloBs.size());
            for(MemberDifferentLevelsPOWithBLOBs po : bloBs){
                String jsonStr = po.getReasonJson();
                DiffLevelsChartPointInfoBO diffLevelsChartPointInfoBO =null;
                // 该点需要展示在图表上
                if(!xArea.contains(po.getChangeDate())){
                    xArea.add(po.getChangeDate());
                    diffLevelsChartPointInfoBO = new DiffLevelsChartPointInfoBO();
                    diffLevelsChartPointInfoBO.setDt(po.getChangeDate());
                    diffLevelsChartPointInfoBO.setLayer(po.getLayer());
                    pointInfoBOS.add(diffLevelsChartPointInfoBO);
                    // 设置图表点上详细
                    if(!StringUtils.isBlank(jsonStr)){
                        //原因结果
                        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                        if(jsonObject!=null){
                            diffLevelsChartPointInfoBO.setHlb(jsonObject.getString("jsgythxhdb"));
                            diffLevelsChartPointInfoBO.setNwGlu(jsonObject.getString("jyzpjchxt"));
                            diffLevelsChartPointInfoBO.setNwGlu0(jsonObject.getString("jyzpjkfxt"));
                            diffLevelsChartPointInfoBO.setNwLGlu(jsonObject.getString("jyzdxtcs"));
                            diffLevelsChartPointInfoBO.setTnbzfxpgdj(jsonObject.getString("jyctnbzwxdj"));
                            diffLevelsChartPointInfoBO.setZwglxwpf(jsonObject.getString("jyczwglxwpf"));
                        }
                    }
                }

                // 患者历史分层分级记录
                DiffLevelsForMemberBO diffLevelsForMemberBO = new DiffLevelsForMemberBO();
                diffLevelsForMemberBO.setDt(po.getChangeDate());
                diffLevelsForMemberBO.setLayer(po.getLayer());
                diffLevelsForMemberBO.setLevel(po.getLevel());
                diffLevelsForMemberBO.setAdjustment(po.getAdjustment());
                dataInfos.add(diffLevelsForMemberBO);
                if(!StringUtils.isBlank(jsonStr)){
                    //原因结果
                    JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                    if(jsonObject!=null){
                        //设置患者历史分层分级记录列表
                        diffLevelsForMemberBO.setAnalysis(po.getCaDesc());
                        diffLevelsForMemberBO.setHlb(jsonObject.getString("jsgythxhdb"));
                        diffLevelsForMemberBO.setNwGlu(jsonObject.getString("jyzpjchxt"));
                        diffLevelsForMemberBO.setNwGlu0(jsonObject.getString("jyzpjkfxt"));
                        diffLevelsForMemberBO.setNwLGlu(jsonObject.getString("jyzdxtcs"));
                        List<JSONObject> pgqklb = new ArrayList<>();
                        JSONObject jyczwglxw = jsonObject.getJSONObject("jyczwglxw");
                        if(jyczwglxw!=null){
                            pgqklb.add(jyczwglxw);
                        }
                        JSONObject jyctnbzwxfj = jsonObject.getJSONObject("jyctnbzwxfj");
                        if(jyctnbzwxfj!=null){
                            pgqklb.add(jyctnbzwxfj);
                        }
                        diffLevelsForMemberBO.setPgqk(pgqklb);
                        JSONObject complicationMap = jsonObject.getJSONObject("mxbfz");
                        diffLevelsForMemberBO.setMxbfzqk(complicationMap);
                        diffLevelsForMemberBO.setJxbfzqk(jsonObject.getString("jxbfz"));
                        JSONObject anamnesis = jsonObject.getJSONObject("anamnesis");
                        diffLevelsForMemberBO.setAnamnesis(anamnesis);
                    }
                }
            }

            if(pointInfoBOS.size()>0){
                xArea.clear();
                List<DiffLevelsChartPointInfoBO> pointInfoBOS1 = new ArrayList<>(pointInfoBOS.size());
                for(int i=pointInfoBOS.size()-1;i>=0;i--){
                    DiffLevelsChartPointInfoBO bo = pointInfoBOS.get(i);
                    xArea.add(bo.getDt());
                    pointInfoBOS1.add(bo);
                }
                chartData.setArrayData(pointInfoBOS1);
            }

            DiffLevelsForMemberVO vo = new DiffLevelsForMemberVO();
            vo.setChartData(chartData);
            vo.setDataInfos(dataInfos);
            return vo;
        }
        return null;
    }

    @Override
    public MemberCurrentDiffLevelVO getMemberCurrentDiffLevelResult(String memberId,String originId) {
        MemberDifferentLevelsPO po = this.mapper.getCurrentResultByMid(memberId, originId);
        if(po!=null){
            MemberCurrentDiffLevelVO vo = new MemberCurrentDiffLevelVO();
            BeanUtils.copyProperties(vo,po);
            return vo;
        }
        return null;
    }

    @Override
    public void insert(MemberDifferentLevelsPOWithBLOBs po) {
        this.mapper.insert(po);
    }

    private void clearMemberLastUnRead(String unSid,String memberId,String hospitalId) {
        MemberDifferentLevelsPOExample example = new MemberDifferentLevelsPOExample();
        MemberDifferentLevelsPOExample.Criteria criteria = example.createCriteria();
        criteria.andSidNotEqualTo(unSid);
        criteria.andMemberIdEqualTo(memberId);
        criteria.andOriginIdEqualTo(hospitalId);
        criteria.andReadEqualTo((byte)0);
        MemberDifferentLevelsPOWithBLOBs po = new MemberDifferentLevelsPOWithBLOBs();
        po.setRead(-1);//忽略
        this.mapper.updateByExampleSelective(po,example);
    }

    @Override
    public void delete(String sid) {
        MemberDifferentLevelsPOWithBLOBs po = new MemberDifferentLevelsPOWithBLOBs();
        po.setIsValid(0);
        this.mapper.updateByPrimaryKey(po);
    }

    @Override
    public void differentLevelsHandle(List<Map<String,Object>> archives) {
        if(archives!=null){
            for(Map<String,Object> map : archives){
                //使用患者在该医院的档案进行分层分级处理-患者基本信息
                MemberPO member = map.get("member")!=null?(MemberPO)map.get("member"):null;
                //患者档案-并发症，近一周血糖，糖化等
                MemberArchivesPO memberArchives = map.get("memberArchives")!=null?(MemberArchivesPO)map.get("memberArchives"):null;
                String archiveJson = memberArchives.getArchivesJson();
                JSONObject archiveJsonObj = null;
                if(!StringUtils.isBlank(archiveJson)){
                    archiveJsonObj = JSONObject.parseObject(archiveJson);
                }
                //患者随访问卷评分
                List<Map<String, Object>> memberQuesScore = map.get("memberQuesScore")!=null?(List<Map<String, Object>>)map.get("memberQuesScore"):null;
                //人工调整记录
                MemberDiffLevelSureDTO memberDiffLevelSureDTO = map.get("memberDiffLevelSureDTO")!=null?(MemberDiffLevelSureDTO)map.get("memberDiffLevelSureDTO"):null;
                if(member!=null && archiveJsonObj!=null){
                    JSONObject reason = this.outHxDifferentLevels(archiveJsonObj,memberQuesScore);
                    //患者分级保存
                    this.saveMemberDifferentLevels(reason,member,archiveJsonObj,memberArchives.getHospitalId(),memberDiffLevelSureDTO);
                }
            }
        }
    }

    /**
     * 患者分级保存
     * @param reason
     * @param member
     * @param archiveJsonObj
     * @param hospitalId
     */
    private void saveMemberDifferentLevels(JSONObject reason, MemberPO member, JSONObject archiveJsonObj, String hospitalId,MemberDiffLevelSureDTO dto) {
        // 是否人工调整
        boolean adjustment = false;
        if(dto!=null && dto.getAdjustment()!=null && dto.getAdjustment().equals(1)){
            ValidateTool.checkParamIsNull(dto.getAdLayer(),"人工调整,调整层级");
            ValidateTool.checkParamIsNull(dto.getAdLevel(),"人工调整,调整等级");
            ValidateTool.checkParamIsNull(dto.getCaReason(),"人工调整,调整原因");
            adjustment = true;
        }

        MemberDifferentLevelsPOWithBLOBs newLog = new MemberDifferentLevelsPOWithBLOBs();
        //患者在该医院当前的分层分级
        MemberCurrentDiffLevelVO currentDiffLevelResult = this.getMemberCurrentDiffLevelResult(member.getMemberId(),hospitalId);
        // 不是人工调整
        if(!adjustment){
            if(member!=null && archiveJsonObj!=null && reason!=null){
                //患者在该医院当前的分层分级的原因
                MemberDifferentLevelsPOWithBLOBs bloBs = null;
                // 判断是否首次分层分级-没有分层分级记录
                if(currentDiffLevelResult!=null){
                    bloBs = this.mapper.selectByPrimaryKey(currentDiffLevelResult.getSid());
                    String oldReasonJson = bloBs.getReasonJson();
                    JSONObject oldObj = JSONObject.parseObject(oldReasonJson);
                    // 判断是否发生分层分级记录改变-原因和层级串联的后md5进行比较
                    if(reason.getString("md5").equals(oldObj.getString("md5"))){
                        // 原因和层级都没有发生改变-不做任何操作
                        return;
                    } else {
                        /**
                         * 原因或层级发生改变
                         */
                        //判断层级是否相同
                        if(reason.getInteger("layer").equals(currentDiffLevelResult.getLayer())){
                            // 判断原因是否相同（有无对比分析）
                            if(!this.reasonChangeHandle(newLog,reason,oldObj)){
                                // 原因也相同没有任何变化-不做任何操作
                                return;
                            } else {
                                //层级未发生改变
                                newLog.setLayerChange(0);
                                //默认确认
                                newLog.setRead(1);
                            }
                        } else { //层级发生改变
                            this.layerChangeHandle(newLog,reason,oldObj);
                            //层级发生改变
                            newLog.setLayerChange(1);
                            //未读未确认
                            newLog.setRead(0);
                        }
                    }
                } else {
                    // 首次分层分级处理
                    this.layerChangeHandle(newLog,reason,null);
                    //层级发生改变
                    newLog.setLayerChange(1);
                    //未读未确认
                    newLog.setRead(0);
                }

                /**
                 * 层级发生变化 | 分级原因发生变化
                 */
                newLog.setLayer(reason.getInteger("layer"));
                newLog.setLevel(reason.getInteger("level"));
                newLog.setAdjustment(0);
            }
        } else {
            newLog.setAdjustment(dto.getAdjustment());
            newLog.setLayer(dto.getAdLayer());
            newLog.setLevel(dto.getAdLevel());
            newLog.setCaDesc(dto.getCaReason());
            reason.put("layer",dto.getAdLayer());
            reason.put("level",dto.getAdLevel());
            String md5Str = reason.getString("md5Str");
            md5Str+=dto.getAdLayer()+"&"+dto.getAdLevel();
            reason.put("md5",ComveeMd5Util.md5(md5Str));
            if(currentDiffLevelResult!=null && !reason.getInteger("layer").equals(currentDiffLevelResult.getLayer())){
                newLog.setLayerChange(1);
                //未读未确认
                newLog.setRead(0);
            } else {
                newLog.setLayerChange(0);
                //默认确认
                newLog.setRead(1);
            }
        }
        newLog.setReasonJson(reason.toJSONString());
        newLog.setMemberId(member.getMemberId());
        newLog.setMemberName(member.getMemberName());
        newLog.setMemberNamePy(member.getMemberNamePy());
        newLog.setSex(member.getSex());
        newLog.setAge(getAge(member,archiveJsonObj));
        newLog.setOrigin(1);
        newLog.setOriginId(hospitalId);
        newLog.setDoctorId("-1");
        newLog.setChangeDate(DateHelper.getToday());
        Date nowDate = new Date();
        newLog.setChangeDt(DateHelper.getDate(nowDate,DateHelper.DATETIME_FORMAT));
        String sid = DaoHelper.getSeq();
        newLog.setSid(sid);
        // 判断有无确认人
        if(dto!=null && !StringUtils.isBlank(dto.getDoctorId())){
            newLog.setDoctorId(dto.getDoctorId());
            //有,不需要提醒和确认
            newLog.setRead(1);
        }
        this.insert(newLog);

        // 将患者这次分级之前未确认的提醒标志清除
        this.clearMemberLastUnRead(sid,member.getMemberId(),hospitalId);
    }

    /**
     * 层级发生改变处理
     * @param newLog
     * @param reason
     * @param oldObj
     */
    private void layerChangeHandle(MemberDifferentLevelsPOWithBLOBs newLog,JSONObject reason,JSONObject oldObj){
        if(oldObj==null){
            newLog.setCaDesc("这是该患者的首次分层分级，并无任何对比分析");
        } else {
            //层级对比分析
            String caDesc = getLayerCaDesc(reason,oldObj);
            newLog.setCaDesc(caDesc);
        }
    }

    /**
     * 原因发生改变处理
     * @param newLog
     * @param reason
     * @param oldObj
     * @return
     */
    private boolean reasonChangeHandle(MemberDifferentLevelsPOWithBLOBs newLog,JSONObject reason,JSONObject oldObj){
        //原因对比分析
        String caDesc = getReasonCaDesc(reason,oldObj);
        if(StringUtils.isBlank(caDesc)){
            return false;
        }
        newLog.setCaDesc(caDesc);
        return true;
    }

    /**
     * 获取年龄
     * @param member
     * @param archiveJsonObj
     * @return
     */
    private Integer getAge(MemberPO member, JSONObject archiveJsonObj) {
        int age = 0;//年龄
        JSONObject basicMap = archiveJsonObj.getJSONObject("basic");
        if(basicMap!=null){
            String birthday = archiveJsonObj.getString("birthday");
            if(!StringUtils.isBlank(birthday)){
                age = DateHelper.getAge(birthday);
            }
        }
        if(age==0) {
            String birthday = member.getBirthday();
            if(!StringUtils.isBlank(birthday)){
                age = DateHelper.getAge(birthday);
            }
        }
        return age;
    }

    /**
     * 层级对比分析
     * @param reason
     * @param oldObj
     * @return
     */
    private String getLayerCaDesc(JSONObject reason, JSONObject oldObj) {
        DecimalFormat df = new DecimalFormat("######0.0");
        int t = 0;
        String result = "层级由"+this.getLayer(oldObj.getString("layer"))+"转至"+this.getLayer(reason.getString("layer"));
        String reasonPjo = "当前患者的";
        String desc = "";
        Double d = this.diffTwoVal(reason.getString("jyzpjkfxt"),oldObj.getString("jyzpjkfxt"));
        if(d>0){
            desc+="近一周平均空腹血糖的值上升了"+df.format(d)+"mmol/l，";
        } else if(d<0){
            desc+="近一周平均空腹血糖的值下降了"+df.format(-d)+"mmol/l，";
        }
        if(d!=0){
            t = 1;
            reasonPjo += "近一周平均空腹血糖，";
        }

        d = this.diffTwoVal(reason.getString("jyzpjchxt"),oldObj.getString("jyzpjchxt"));
        if(d>0){
            desc+="近一周平均餐后血糖的值上升了"+df.format(d)+"mmol/l，";
        } else if(d<0){
            desc+="近一周平均餐后血糖的值下降了"+df.format(-d)+"mmol/l，";
        }
        if(d!=0){
            t = 1;
            reasonPjo += "近一周平均餐后血糖，";
        }

        d = this.diffTwoVal(reason.getString("jsgythxhdb"),oldObj.getString("jsgythxhdb"));
        if(d>0){
            desc+="糖化血红蛋白的值上升了"+df.format(d)+"%，";
        } else if(d<0){
            desc+="糖化血红蛋白的值下降了"+df.format(-d)+"%，";
        }
        if(d!=0){
            t = 1;
            reasonPjo += "糖化血红蛋白，";
        }

        d = this.diffTwoVal(reason.getString("jyzdxtcs"),oldObj.getString("jyzdxtcs"));
        /*if(d>0){
            desc+="近一周低血糖次数的增加了"+df.format(d)+"次，";
        } else if(d<0) {
            desc+="近一周低血糖次数的减少了"+df.format(-d)+"次，";
        }*/
        if(d!=0){
            reasonPjo += "近一周低血糖次数，";
        }

        d = this.diffTwoVal(reason.getString("jyczwglxwpf"),oldObj.getString("jyczwglxwpf"));
        /*if(d>0){
            desc+="自我管理行为评分上升了"+df.format(d)+"分，";
        } else if(d<0) {
            desc+="自我管理行为评分下降了"+df.format(-d)+"分，";
        }*/
        if(d!=0){
            reasonPjo += "自我管理行为评分，";
        }

        d = this.diffTwoVal(reason.getString("jyctnbzwxdj"),oldObj.getString("jyctnbzwxdj"));
        /*if(d>0){
            desc+="糖尿病足危险分级上升了"+df.format(d)+"级，";
        } else if(d<0) {
            desc+="糖尿病足危险分级下降了"+df.format(-d)+"级，";
        }*/
        if(d!=0){
            reasonPjo += "糖尿病足危险分级，";
        }

        boolean jxbfzcs = false;
        d = this.diffTwoVal(reason.getString("tzszdcs"),oldObj.getString("tzszdcs"));
        /*if(d>0){
            desc+="酮症酸中毒增加了"+df.format(d)+"次，";
        } else if(d<0) {
            desc+="酮症酸中毒减少了"+df.format(-d)+"次，";
        }*/
        if(d!=0){
            jxbfzcs = true;
        }

        d = this.diffTwoVal(reason.getString("gsxfmcs"),oldObj.getString("gsxfmcs"));
        /*if(d>0){
            desc+="高渗性昏迷增加了"+df.format(d)+"次，";
        } else if(d<0) {
            desc+="高渗性昏迷减少了"+df.format(-d)+"次，";
        }*/
        if(d!=0){
            jxbfzcs = true;
        }


        d = this.diffTwoVal(reason.getString("rsszdcs"),oldObj.getString("rsszdcs"));
        /*if(d>0){
            desc+="乳酸酸中毒增加了"+df.format(d)+"次，";
        } else if(d<0) {
            desc+="乳酸酸中毒减少了"+df.format(-d)+"次，";
        }*/
        if(d!=0){
            jxbfzcs = true;
        }
        if(jxbfzcs){
            reasonPjo += "近一年急性并发症发生次数，";
        }

        String mxbfz = reason.getString("mxbfzflag")+reason.getString("anamnesis");
        String oldmxbfz = oldObj.getString("mxbfzflag")+oldObj.getString("anamnesis");
        if(!StringUtils.isBlank(mxbfz)&&!mxbfz.equals(oldmxbfz)){
            reasonPjo += "慢性并发症病情，";
        } else if(StringUtils.isBlank(mxbfz)&&!StringUtils.isBlank(oldmxbfz)){
            reasonPjo += "慢性并发症病情，";
        }

        if(!StringUtils.isBlank(reasonPjo)){
            reasonPjo = reasonPjo.substring(0,reasonPjo.length()-1);
        }

        if(Integer.parseInt(reason.getString("layer"))-Integer.parseInt(oldObj.getString("layer"))>0){
            if(t==1){
                if(!StringUtils.isBlank(desc)){
                    desc = desc.substring(0,desc.length()-1);
                }
                result = reasonPjo+"有所改变，" + result + "，对比上次：患者的" + desc;
            } else {
                result = reasonPjo+"有所改变，" + result;
            }
            result += "；说明这段时间针对该患者的管理不佳，请针对患者的具体情况及时调整管理方案，同时提醒患者注意保持良好的自我监测行为哦！";
        } else {
            if(t==1){
                if(!StringUtils.isBlank(desc)){
                    desc = desc.substring(0,desc.length()-1);
                }
                result = reasonPjo+"有所改变，" + result + "，对比上次：患者的" + desc;
            } else {
                result = reasonPjo+"有所改变，" + result;
            }
            result += "；说明这段时间针对患者的管理良好，请鼓励患者继续保持良好的自我监测行为哦！";
        }
        return result;
    }

    /**
     * 层级文案
     * @param layer
     * @return
     */
    private String getLayer(String layer) {
        if("1".equals(layer)){
            return "平稳";
        }
        else if("2".equals(layer)){
            return "中危";
        }
        else if("3".equals(layer)){
            return "高危";
        }
        return "未知层（系统出错）";
    }

    /**
     * 原因对比分析
     * @param reason
     * @param oldObj
     * @return
     */
    private String getReasonCaDesc(JSONObject reason, JSONObject oldObj) {
        DecimalFormat df = new DecimalFormat("######0.0");
        int t=0;
        String result = null;
        String desc = "";
        String reasonPjo = "当前患者的";
        Double d = this.diffTwoVal(reason.getString("jyzpjkfxt"),oldObj.getString("jyzpjkfxt"));
        if(d>0){
            desc+="近一周平均空腹血糖的值上升了"+df.format(d)+"mmol/l，";
        } else if(d<0){
            desc+="近一周平均空腹血糖的值下降了"+df.format(-d)+"mmol/l，";
        }
        if(d!=0){
            reasonPjo += "近一周平均空腹血糖，";
            t=1;
        }

        d = this.diffTwoVal(reason.getString("jyzpjchxt"),oldObj.getString("jyzpjchxt"));
        if(d>0){
            desc+="近一周平均餐后血糖的值上升了"+df.format(d)+"mmol/l，";
        } else if(d<0){
            desc+="近一周平均餐后血糖的值下降了"+df.format(-d)+"mmol/l，";
        }
        if(d!=0){
            reasonPjo += "近一周平均餐后血糖，";
            t=1;
        }

        d = this.diffTwoVal(reason.getString("jsgythxhdb"),oldObj.getString("jsgythxhdb"));
        if(d>0){
            desc+="糖化血红蛋白的值上升了"+df.format(d)+"%，";
        } else if(d<0){
            desc+="糖化血红蛋白的值下降了"+df.format(-d)+"%，";
        }
        if(d!=0){
            reasonPjo += "糖化血红蛋白，";
            t=1;
        }

        d = this.diffTwoVal(reason.getString("jyzdxtcs"),oldObj.getString("jyzdxtcs"));
        /*if(d>0){
            desc+="近一周低血糖次数的增加了"+df.format(d)+"次，";
        } else if(d<0) {
            desc+="近一周低血糖次数的减少了"+df.format(-d)+"次，";
        }*/
        if(d!=0){
            reasonPjo += "近一周低血糖次数，";
        }

        d = this.diffTwoVal(reason.getString("jyczwglxwpf"),oldObj.getString("jyczwglxwpf"));
        /*if(d>0){
            desc+="自我管理行为评分上升了"+df.format(d)+"分，";
        } else if(d<0) {
            desc+="自我管理行为评分下降了"+df.format(-d)+"分，";
        }*/
        if(d!=0){
            reasonPjo += "自我管理行为评分，";
        }


        d = this.diffTwoVal(reason.getString("jyctnbzwxdj"),oldObj.getString("jyctnbzwxdj"));
        /*if(d>0){
            desc+="糖尿病足危险分级上升了"+df.format(d)+"级，";
        } else if(d<0) {
            desc+="糖尿病足危险分级下降了"+df.format(-d)+"级，";
        }*/
        if(d!=0){
            reasonPjo += "糖尿病足危险分级，";
        }

        boolean jxbfzcs = false;
        d = this.diffTwoVal(reason.getString("tzszdcs"),oldObj.getString("tzszdcs"));
        /*if(d>0){
            desc+="酮症酸中毒增加了"+df.format(d)+"次，";
        } else if(d<0) {
            desc+="酮症酸中毒减少了"+df.format(-d)+"次，";
        }*/
        if(d!=0){
            jxbfzcs = true;
        }

        d = this.diffTwoVal(reason.getString("gsxfmcs"),oldObj.getString("gsxfmcs"));
        /*if(d>0){
            desc+="高渗性昏迷增加了"+df.format(d)+"次，";
        } else if(d<0) {
            desc+="高渗性昏迷减少了"+df.format(-d)+"次，";
        }*/
        if(d!=0){
            jxbfzcs = true;
        }

        d = this.diffTwoVal(reason.getString("rsszdcs"),oldObj.getString("rsszdcs"));
        /*if(d>0){
            desc+="乳酸酸中毒增加了"+df.format(d)+"次，";
        } else if(d<0) {
            desc+="乳酸酸中毒减少了"+df.format(-d)+"次，";
        }*/
        if(d!=0){
            jxbfzcs = true;
        }
        if(jxbfzcs){
            reasonPjo += "近一年急性并发症发生次数，";
        }

        String mxbfz = reason.getString("mxbfzflag")+reason.getString("anamnesis");
        String oldmxbfz = oldObj.getString("mxbfzflag")+oldObj.getString("anamnesis");
        if(!StringUtils.isBlank(mxbfz)&&!mxbfz.equals(oldmxbfz)){
            reasonPjo += "慢性并发症病情，";
        } else if(StringUtils.isBlank(mxbfz)&&!StringUtils.isBlank(oldmxbfz)){
            reasonPjo += "慢性并发症病情，";
        }

        if(!StringUtils.isBlank(reasonPjo)){
            reasonPjo = reasonPjo.substring(0,reasonPjo.length()-1);
        }
        if(t==1){
            result = reasonPjo+"有所改变，层级未改变，对比上次：患者的"+desc;
            result += "请根据患者病情及时调整管理方案，同时提醒患者注意保持良好的自我监测行为哦！";
        } else {
            result = reasonPjo+"有所改变，层级未改变，请根据患者病情及时调整管理方案，同时提醒患者注意保持良好的自我监测行为哦！";
        }
        return result;
    }

    private Double diffTwoVal(String str1,String str2){
        if(StringUtils.isBlank(str1)){
            str1 = "0.0";
        }
        if(StringUtils.isBlank(str2)){
            str2 = "0.0";
        }
        Double val1 = Double.parseDouble(str1);
        Double val2 = Double.parseDouble(str2);
        return val1-val2;
    }

    //华西医院患者 分层分级规则
    @Override
    public JSONObject outHxDifferentLevels(JSONObject memberArchives,
                                                  List<Map<String, Object>> memberQuesScore){
        JSONObject resultMap = new JSONObject();
        String reLevel="3";
        String relayer="1";

        JSONObject basicMap = memberArchives.getJSONObject("basic");
        JSONObject labMap = memberArchives.getJSONObject("lab");
        JSONObject complicationMap = memberArchives.getJSONObject("complication");
        JSONObject hypoglycemiaMap = memberArchives.getJSONObject("hypoglycemia");
        JSONObject treatmentMap = memberArchives.getJSONObject("treatment");
        JSONObject anamnesis = memberArchives.getJSONObject("anamnesis");

        //性别 1男 2女
        String sex = null;
        String birthday = null;
        if(basicMap!=null){
            sex = basicMap.getString("sex");
            birthday = basicMap.getString("birthday");
        }
        int age = 0;//年龄
        if(!StringUtils.isBlank(birthday)){
            age = DateHelper.getAge(birthday);
        }

        //1、近一周平均空腹 nw_mq_fbg、 平均餐后 nw_blg
        Double beforeNum = null;
        Double afterNum = null;
        if(treatmentMap!=null){
            String mqFbg = treatmentMap.getString("nw_mq_fbg");
            String blg = treatmentMap.getString("nw_blg");
            if (!StringUtils.isBlank(mqFbg)){
                beforeNum = Double.parseDouble(mqFbg);
                resultMap.put("jyzpjkfxt",beforeNum);
            }
            if (!StringUtils.isBlank(blg)){
                afterNum = Double.parseDouble(blg);
                resultMap.put("jyzpjchxt",afterNum);
            }
        }


        //2、最近3个月内的糖化 lab  lab_hba
        Double th=0.0;
        if(labMap!=null){
            if(null!=labMap.get("lab_hba") && !StringUtils.isBlank(labMap.getString("lab_hba"))){
                th = Double.parseDouble(labMap.getString("lab_hba"));
                resultMap.put("jsgythxhdb",th);
            }
        }


        //3、近一周内发生低血糖 hypoglycemia  hyp_frequency
        Double dxt=0.0;
        //是否发生过低血糖 1是
        String hyp="";
        if(hypoglycemiaMap!=null){
            if(null!=hypoglycemiaMap.get("hyp") && !StringUtils.isBlank(hypoglycemiaMap.getString("hyp"))){
                hyp = hypoglycemiaMap.getString("hyp");
                resultMap.put("jyzsffsdxt",hyp);
            }
            if("1".equals(hyp)){
                if(null!=hypoglycemiaMap.get("hyp_frequency") && !StringUtils.isBlank(hypoglycemiaMap.getString("hyp_frequency"))){
                    dxt = Double.parseDouble(hypoglycemiaMap.getString("hyp_frequency").replace("rr",""));
                    resultMap.put("jyzdxtcs",dxt);
                }
            }
        }

        //4、近一次 2 自我管理行为得分 & 4 ADA糖尿病足危险分级
        Double q1_num=0.0;
        Double q3_level=0.0;
        for(Map<String,Object> mqs :memberQuesScore){
            if(mqs.get("type")!=null && "2".equals(mqs.get("type").toString())){
                if(!StringUtils.isBlank(mqs.get("score").toString())){
                    q1_num = Double.parseDouble(mqs.get("score").toString());
                    resultMap.put("jyczwglxw",mqs);
                    resultMap.put("jyczwglxwpf",mqs.get("score").toString());
                }
            } else if(mqs.get("type")!=null && "4".equals(mqs.get("type").toString())){
                if(!StringUtils.isBlank(mqs.get("level").toString())){
                    q3_level = Double.parseDouble(mqs.get("level").toString());
                    resultMap.put("jyctnbzwxfj",mqs);
                    resultMap.put("jyctnbzwxdj",mqs.get("level").toString());
                }
            }
        }

        //5、近1年糖尿病  酮症酸中毒 高渗性昏迷 乳酸酸中毒 总次数  complication  jyntnbjxbfz
        Integer rsszdNum =0;
        Integer rsszdNum2 =0;
        Integer rsszdNum4 =0;
        Integer rsszdNum5 =0;
        String jxbfz = "";
        //糖尿病肾病 nephropathy SB01
        String  nephropathy= "";
        //糖尿病眼底病变 SWM01有
        String  retinal= "";
        //1、糖尿病肾病类型选择
        String neph_type_cms= "";
        //2、糖尿病视网膜病变选择
        String  retinal_type_cms= "";
        //3、糖尿病周围神经病变选择
        String  neuropathy= "";
        //5、是否心脑血管疾病选择
        String  has_xnxg= "";
        String  xnxg_show= "";
        String  neu_symptom_cms= "";
        //糖尿病足 TNBZ01有
        String  diabetic_foot= "";
        if(complicationMap!=null ){

            String jyntnbjxbfzStr = complicationMap.getString("jyntnbjxbfz");
            if(!StringUtils.isBlank(jyntnbjxbfzStr)&&jyntnbjxbfzStr.contains("LX02")){
                if(null!=complicationMap.get("complications_diabetes_status_02") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_02").toString())){
                    rsszdNum2= Integer.parseInt(complicationMap.get("complications_diabetes_status_02").toString());
                    resultMap.put("tzszdcswa","酮症酸中毒"+rsszdNum2+"次");
                    resultMap.put("tzszdcs",rsszdNum2);
                    jxbfz += "酮症酸中毒"+rsszdNum2+"次,";
                }
            }
            if(!StringUtils.isBlank(jyntnbjxbfzStr)&&jyntnbjxbfzStr.contains("LX04")){
                if(null!=complicationMap.get("complications_diabetes_status_04") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_04").toString())){
                    rsszdNum4= Integer.parseInt(complicationMap.get("complications_diabetes_status_04").toString());
                    resultMap.put("gsxfmcswa","高渗性昏迷"+rsszdNum4+"次");
                    resultMap.put("gsxfmcs",rsszdNum4);
                    jxbfz += "高渗性昏迷"+rsszdNum4+"次,";
                }
            }
            if(!StringUtils.isBlank(jyntnbjxbfzStr)&&jyntnbjxbfzStr.contains("LX05")){
                if(null!=complicationMap.get("complications_diabetes_status_05") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_05").toString())){
                    rsszdNum5= Integer.parseInt(complicationMap.get("complications_diabetes_status_05").toString());
                    resultMap.put("rsszdcswa","乳酸酸中毒"+rsszdNum5+"次");
                    resultMap.put("rsszdcs",rsszdNum5);
                    jxbfz += "乳酸酸中毒"+rsszdNum5+"次,";
                }
            }
            rsszdNum= rsszdNum2 + rsszdNum4 + rsszdNum5;
            if(jxbfz.length()>0){
                jxbfz = jxbfz.substring(0,jxbfz.length()-1);
            }
            resultMap.put("jxbfz",jxbfz);

            String mxbfzflag = "";
            if(null!=complicationMap.get("nephropathy") && !StringUtils.isBlank(complicationMap.get("nephropathy").toString())){
                nephropathy= complicationMap.get("nephropathy").toString();
            }
            if("SB01".equals(nephropathy)){
                if(null!=complicationMap.get("neph_type_cms") && !StringUtils.isBlank(complicationMap.get("neph_type_cms").toString())){
                    neph_type_cms= complicationMap.get("neph_type_cms").toString();
                    mxbfzflag+=neph_type_cms;
                }
            }
            if(null!=complicationMap.get("retinal") && !StringUtils.isBlank(complicationMap.get("retinal").toString())){
                retinal= complicationMap.get("retinal").toString();
            }
            if("SWM01".equals(retinal)){
                if(null!=complicationMap.get("retinal_type_cms") && !StringUtils.isBlank(complicationMap.get("retinal_type_cms").toString())){
                    retinal_type_cms= complicationMap.get("retinal_type_cms").toString();
                    mxbfzflag+=retinal_type_cms;
                }
            }
            if(null!=complicationMap.get("neuropathy") && !StringUtils.isBlank(complicationMap.get("neuropathy").toString())){
                neuropathy= complicationMap.get("neuropathy").toString();
                mxbfzflag+=neuropathy;
            }
            if(null!=complicationMap.get("neu_symptom_cms") && !StringUtils.isBlank(complicationMap.get("neu_symptom_cms").toString())){
                neu_symptom_cms= complicationMap.get("neu_symptom_cms").toString();
                mxbfzflag+=neu_symptom_cms;
            }
            if(null!=complicationMap.get("has_xnxg") && !StringUtils.isBlank(complicationMap.get("has_xnxg").toString())){
                has_xnxg= complicationMap.get("has_xnxg").toString();
                mxbfzflag+=has_xnxg;
            }
            if(null!=complicationMap.get("xnxg_show") && !StringUtils.isBlank(complicationMap.get("xnxg_show").toString())){
                xnxg_show= complicationMap.get("xnxg_show").toString();
                mxbfzflag+=xnxg_show;
            }
            if(complicationMap!=null && null!=complicationMap.get("diabetic_foot") && !StringUtils.isBlank(complicationMap.get("diabetic_foot").toString())){
                diabetic_foot= complicationMap.get("diabetic_foot").toString();
                mxbfzflag+=diabetic_foot;
            }
            resultMap.put("mxbfz",complicationMap);
            resultMap.put("mxbfzflag",mxbfzflag);
            resultMap.put("anamnesis",anamnesis);
        }


        String md5Str = "comvee";

        //高级
        //1、糖尿病肾病类型选择：临床糖尿病肾病病期LX04 或肾衰竭期LX05 complication  neph_type_cms
        //2、糖尿病视网膜病变选择：重度非增生型视网膜病变LX04 或 增殖期糖尿病视网膜病变LX02 complication retinal_type_cms
        //3、糖尿病周围神经病变选择：确诊有ZWSJ01 且症状表现选择“无症状”ZZ01 外的其他选项  周围神经neuropathy  症状neu_symptom_cms
        //4、ADA糖尿病足危险分级：3级
        //5、是否心脑血管疾病选择“确诊有” complication  has_xnxg 1

        //中级
        //1、糖尿病肾病类型选择：肾小球高过滤和肾脏肥大LX01 或无临床表现的肾损害期LX02 或早起糖尿病肾病期LX03
        //2、糖尿病视网膜病变眼部疾病类型选择：轻度非增殖期视网膜病变LX01 或轻度~中度非增值性视网膜病变LX03
        //3、糖尿病周围神经病变选择：确诊有确诊有ZWSJ01 且症状表现选择“无症状”ZZ01
        //4、ADA糖尿病足危险分级：1~2级
        //5、心脑血管疾病选择“确诊无” complication  has_xnxg 2
        // 且心脑血管疾病因素选择了“早发性心血管疾病家族史、吸烟、高血压、血脂紊乱或蛋白尿”任意一项或多项 complication xnxg_show
        // 且年龄≥40岁

        //低级
        //1、ADA糖尿病足危险分级：0级
        //2、心脑血管疾病选择“确诊无” complication  has_xnxg 2
        // 且心脑血管疾病因素选择了“早发性心血管疾病家族史、吸烟、高血压、血脂紊乱或蛋白尿”任意一项或多项 complication xnxg_show
        // 且年龄＜40岁

        //低危 三级支持
        if(age < 65 && beforeNum!=null && afterNum!=null) {
            //4.4≤空腹＜7 餐后≤10
            if( 4.4 <= beforeNum && beforeNum <7  && afterNum<=10 ){
                reLevel="3";
                relayer="1";
            }
            if(th <7 ){
                reLevel="3";
                relayer="1";
            }
        }else if(age >= 65 && beforeNum!=null && afterNum!=null){
            //8≤空腹＜10 且 8≤餐后＜12
            if( 8 <= beforeNum && beforeNum <10  && 8<=afterNum && afterNum<12 ){
                reLevel="3";
                relayer="1";
            }
            if(th <8 ){
                reLevel="3";
                relayer="1";
            }
        }

        if(dxt==0){
            reLevel="3";
            relayer="1";
        }

        if(q1_num>=5.6){
            reLevel="3";
            relayer="1";
        }

        if(rsszdNum==0){
            reLevel="3";
            relayer="1";
        }

        //1、ADA糖尿病足危险分级：0级
        if(q3_level == 0){
            reLevel="3";
            relayer="1";
        }

        //2、心脑血管疾病选择“确诊无” complication  has_xnxg 2
        // 且心脑血管疾病因素选择了“早发性心血管疾病家族史、吸烟、高血压、血脂紊乱或蛋白尿”任意一项或多项 complication xnxg_show
        // 且年龄＜40岁
        if(has_xnxg.contains("2") && !StringUtils.isBlank(xnxg_show) && age<40){
            reLevel="3";
            relayer="1";
        }

        //中危 二级支持
        if(age < 65 && beforeNum!=null && afterNum!=null){
            //7≤空腹＜10  或 空腹≥10、餐后≤13.9
            if((7 <= beforeNum && beforeNum <10) || (beforeNum>=10 &&  afterNum<=13.9) ){
                reLevel="2";
                relayer="2";
            }
            if(7<=th  && th<9){
                reLevel="2";
                relayer="2";
            }
        }else if(age >= 65 && beforeNum!=null && afterNum!=null){
            //空腹＜10、餐后≥12 或  空腹≥10、餐后≤13.9
            if((beforeNum<10 && afterNum>=12) || (beforeNum>=10 && afterNum<=13.9)){
                reLevel="2";
                relayer="2";
            }
            if(8<=th  && th<9){
                reLevel="2";
                relayer="2";
            }
        }

        if(dxt==1){
            reLevel="2";
            relayer="2";
            md5Str+="dxt";
        }

        if(4.2<=q1_num && q1_num<=5.5){
            reLevel="2";
            relayer="2";
        }

        if(rsszdNum==1){
            reLevel="2";
            relayer="2";
        }

        //1、糖尿病肾病类型选择：肾小球高过滤和肾脏肥大LX01 或无临床表现的肾损害期LX02 或早起糖尿病肾病期LX03
        if(neph_type_cms.contains("LX01") || neph_type_cms.contains("LX02") || neph_type_cms.contains("LX03")){
            reLevel="2";
            relayer="2";
        }

        //2、糖尿病视网膜病变眼部疾病类型选择：轻度非增殖期视网膜病变LX01 或轻度~中度非增值性视网膜病变LX03
        if(retinal_type_cms.contains("LX01") || retinal_type_cms.contains("LX03")){
            reLevel="2";
            relayer="2";
        }

        //3、糖尿病周围神经病变选择：确诊有确诊有ZWSJ01 且症状表现选择“无症状”ZZ01
        if(neuropathy.contains("ZWSJ01") && neu_symptom_cms.contains("ZZ01")){
            reLevel="2";
            relayer="2";
            md5Str+="neuropathy";
        }

        //4、ADA糖尿病足危险分级：1~2级
        if(q3_level == 1 || q3_level == 2){
            reLevel="2";
            relayer="2";
        }

        //5、心脑血管疾病选择“确诊无” complication  has_xnxg 2
        // 且心脑血管疾病因素选择了“早发性心血管疾病家族史、吸烟、高血压、血脂紊乱或蛋白尿”任意一项或多项 complication xnxg_show
        // 且年龄≥40岁
        if(has_xnxg.contains("2") && !StringUtils.isBlank(xnxg_show) && age>=40){
            reLevel="2";
            relayer="2";
        }

        //高危 一级支持
        if(beforeNum!=null && afterNum!=null && beforeNum >=10 && afterNum >13.9 ){
            reLevel="1";
            relayer="3";
        }

        if(th >=9 ){
            reLevel="1";
            relayer="3";
        }

        if(dxt >=2 ){
            reLevel="1";
            relayer="3";
        }

        if(q1_num <=4.1){
            reLevel="1";
            relayer="3";
        }

        if(rsszdNum >=2){
            reLevel="1";
            relayer="3";
        }

        //1、糖尿病肾病类型选择：临床糖尿病肾病病期LX04 或肾衰竭期LX05 complication  neph_type_cms
        if(neph_type_cms.contains("LX04") || neph_type_cms.contains("LX05")){
            reLevel="1";
            relayer="3";
        }

        //2、糖尿病视网膜病变选择：重度非增生型视网膜病变LX04 或 增殖期糖尿病视网膜病变LX02 complication retinal_type_cms
        if(retinal_type_cms.contains("LX04") || retinal_type_cms.contains("LX02")){
            reLevel="1";
            relayer="3";
        }

        //3、糖尿病周围神经病变选择：确诊有ZWSJ01 且症状表现选择“无症状”ZZ01 外的其他选项  周围神经neuropathy  症状neu_symptom_cms
        if(neuropathy.contains("ZWSJ01") && !neu_symptom_cms.contains("ZZ01") && !StringUtils.isBlank(neu_symptom_cms)){
            reLevel="1";
            relayer="3";
        }

        //4、ADA糖尿病足危险分级：3级
        if(q3_level == 3){
            reLevel="1";
            relayer="3";
        }

        //5、是否心脑血管疾病选择“确诊有” complication  has_xnxg 1
        if(has_xnxg.contains("1")){
            reLevel="1";
            relayer="3";
        }

        if("TNBZ01".equals(diabetic_foot)){
            reLevel="1";
            relayer="3";
            md5Str+="diabetic_foot";
        }

        md5Str+="&beforeNum="+beforeNum;
        md5Str+="&afterNum="+afterNum;
        md5Str+="&th="+th;
        md5Str+="&dxt="+dxt;
        md5Str+="&q1_num="+q1_num;
        md5Str+="&jxbfz="+jxbfz;
        md5Str+="&rsszdNum="+rsszdNum;
        md5Str+="&q3_level="+q3_level;
        md5Str+="&has_xnxg="+has_xnxg;
        md5Str+="&xnxg_show="+xnxg_show;
        md5Str+="&neph_type_cms="+neph_type_cms;
        md5Str+="&retinal_type_cms="+retinal_type_cms;
        md5Str+="&neuropathy="+neuropathy;
        md5Str+="&neu_symptom_cms="+neu_symptom_cms;
        md5Str+="&diabetic_foot="+diabetic_foot;
        resultMap.put("md5Str",md5Str);
        md5Str+=relayer+"&"+reLevel;
        resultMap.put("md5",ComveeMd5Util.md5(md5Str));

        //reLevel 1 平稳层、2 中危层、3 高危层
        resultMap.put("layer",relayer);
        //reLevel 1 一级支持、2 二级支持、3 三级支持
        resultMap.put("level",reLevel);
        return  resultMap;
    }
}
