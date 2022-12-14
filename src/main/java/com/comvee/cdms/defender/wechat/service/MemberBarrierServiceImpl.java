package com.comvee.cdms.defender.wechat.service;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.defender.wechat.constant.MemberBarrierConstant;
import com.comvee.cdms.defender.wechat.dto.AddBarrierQuesCfgDTO;
import com.comvee.cdms.defender.wechat.dto.BarrierResultDTO;
import com.comvee.cdms.defender.wechat.dto.SubmitBarrierDTO;
import com.comvee.cdms.defender.wechat.dto.UpdateBarrierQuesCfgDTO;
import com.comvee.cdms.defender.wechat.mapper.*;
import com.comvee.cdms.defender.wechat.po.*;
import com.comvee.cdms.defender.wechat.vo.BarrierQuesCfgVO;
import com.comvee.cdms.defender.wechat.vo.BarrierResultVO;
import com.comvee.cdms.defender.wechat.vo.ListBarrierQuesVO;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author linr
 * @Date 2021/12/3
 */
@Service
public class MemberBarrierServiceImpl implements MemberBarrierServiceI{

    @Autowired
    private MemberBarrierMapper memberBarrierMapper;

    @Autowired
    private PatientCourseMapper patientCourseMapper;

    @Autowired
    private BarrierCourseQuesCfgMapper barrierCourseQuesCfgMapper;

    @Autowired
    private BarrierQuesCfgMapper barrierQuesCfgMapper;

    @Autowired
    private BarrierQuesMapper barrierQuesMapper;

    @Autowired
    private MemberCourseBarrierMapper memberCourseBarrierMapper;


    private final static Logger log = LoggerFactory.getLogger(MemberBarrierServiceImpl.class);

    @Override
    @Transactional
    public List<MemberBarrierPO> listMemberBarrier(String memberId) {

        MemberBarrierPO querryBarrierPO = new MemberBarrierPO();
        querryBarrierPO.setMemberId(memberId);
        List<MemberBarrierPO> barrierPOList = memberBarrierMapper.queryAll(querryBarrierPO);
        //??????????????????????????????
        if (barrierPOList.size() == 0){
            this.trialYardGenerate(memberId);
        }
        //???????????????????????????
        List<String> list = memberBarrierMapper.loadAllBarrCourse();
        int newNum = list.size()/3; //???????????????????????????
        querryBarrierPO.setBarrierType(MemberBarrierConstant.BARRIER_TYPE_COMMON);
        //??????????????????
        List<MemberBarrierPO> commonBarrierPOList = memberBarrierMapper.queryAll(querryBarrierPO);
        int nowNum = commonBarrierPOList.size();
        //????????????
        if (nowNum < newNum){
            //??????????????????????????????????????????
            int doBarrierNum = newNum - nowNum;//????????????????????????
            if (commonBarrierPOList.size() == 0 && doBarrierNum > 0){
                //???????????????
                for (int i = 1; i <= doBarrierNum; i++) {
                    //???????????????
                    ordinaryBarrierGenerate(memberId,i,MemberBarrierConstant.BARRIER_STATUS_LOCKED);
                }
            }else if (nowNum != 0 && doBarrierNum > 0){
                //??????????????????
                MemberBarrierPO lastBarrier = commonBarrierPOList.get(commonBarrierPOList.size() - 1);
                Integer sort = lastBarrier.getSort();//?????????????????????
                for (int i = sort+1; i <= newNum; i++) {
                    ordinaryBarrierGenerate(memberId,i,MemberBarrierConstant.BARRIER_STATUS_LOCKED);
                }
            }
        }else if (nowNum > newNum){
            int doBarrierNum = nowNum - newNum;//????????????????????????
            List<String> delList = memberBarrierMapper.queryIdDescLimit(memberId, doBarrierNum);
            for (String sid : delList) {
                memberBarrierMapper.deleteById(sid);
            }
        }
        ongoingBarrierCheck(memberId);//???????????????????????????
        querryBarrierPO.setBarrierType(null);
        List<MemberBarrierPO> resultList = memberBarrierMapper.queryAll(querryBarrierPO);
        //???????????? ???????????? ?????????????????????
        commingSoonBarrHandler(resultList);
        return resultList;
    }

    private void commingSoonBarrHandler(List<MemberBarrierPO> resultList){
        MemberBarrierPO commonBarrier = new MemberBarrierPO();
        commonBarrier.setBarrierType(MemberBarrierConstant.BARRIER_TYPE_COMMON);
        commonBarrier.setStatus(MemberBarrierConstant.BARRIER_STATUS_COMING_SOON);
        commonBarrier.setSort(resultList.get(resultList.size()-1).getSort()+1);
        resultList.add(commonBarrier);
    }


    //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????+??????????????????????????????
    private void ongoingBarrierCheck(String memberId){
        boolean coursePass = false;
        boolean preBarPass = false;
        MemberBarrierPO querry = new MemberBarrierPO();
        querry.setBarrierType(MemberBarrierConstant.BARRIER_TYPE_COMMON);
        querry.setStatus(MemberBarrierConstant.BARRIER_STATUS_ONGOING);
        querry.setMemberId(memberId);
        querry.setIsValid(1);
        List<MemberBarrierPO> ongoingBars = memberBarrierMapper.queryAll(querry);//????????????????????????
        if (ongoingBars.size()==0){
            querry.setStatus(MemberBarrierConstant.BARRIER_STATUS_LOCKED);
            List<MemberBarrierPO> lockedBars = memberBarrierMapper.queryAll(querry);
            if (lockedBars.size()>0){
                MemberBarrierPO lockedBar = lockedBars.get(0);
                Integer sort = lockedBar.getSort();
                //????????????????????????????????????????????????
                querry.setStatus(null);
                querry.setSort(sort-1);
                List<MemberBarrierPO> barrierPOList = memberBarrierMapper.queryAll(querry);
                if (barrierPOList.size() == 0 || barrierPOList.get(0).getStatus() == MemberBarrierConstant.BARRIER_STATUS_FINISHED){
                    preBarPass = true;
                }
                //?????????????????????????????????
                MemberCourseBarrierPO querryCoursePO = new MemberCourseBarrierPO();
                querryCoursePO.setMemberId(memberId);
                querryCoursePO.setIsValid(1);
                List<MemberCourseBarrierPO> courseList = memberCourseBarrierMapper.queryAll(querryCoursePO);
                int ongoingBarNum = courseList.size()/3;//?????????????????????
                if (ongoingBarNum >= sort){
                    coursePass = true;
                }
                if (preBarPass && coursePass){
                    lockedBar.setStatus(MemberBarrierConstant.BARRIER_STATUS_ONGOING);
                    memberBarrierMapper.update(lockedBar);
                }
            }
        }
    }

    @Override
    @Transactional
    public List<ListBarrierQuesVO> loadBarrierBySid(String sid) {
        MemberBarrierPO memberBarrierPO = memberBarrierMapper.queryById(sid);
        if (null == memberBarrierPO){
            throw new BusinessException("???????????????");
        }
        List<String> queIdList = null;//????????????
        String memberId = memberBarrierPO.getMemberId();
        Integer status = memberBarrierPO.getStatus();
        Integer sort = memberBarrierPO.getSort();
        if (memberBarrierPO.getBarrierType() == 1){
            //?????????????????????
            queIdList = trialYardQuesGenerate();
        }else {
            //????????????????????????
            queIdList = commonQuesGenerate(memberId, status, sort, sid);
        }
        List<BarrierQuesCfgPO> quesList = new ArrayList<>();
        for (String quesId : queIdList) {
            BarrierQuesCfgPO barrierQuesCfgPO = barrierQuesCfgMapper.queryById(quesId);
            quesList.add(barrierQuesCfgPO);
        }
        List<ListBarrierQuesVO> result = null;
        //??????????????????
        try {
            if (quesList.size()>0){
                result = quesList.stream().map(e ->
                        new ListBarrierQuesVO(e.getSid(),e.getTitle(), JsonSerializer.jsonToMapList(e.getAnswerOptions()),
                                e.getAnswerKey(),e.getAnalyse(),e.getType(),e.getTag(),e.getQuesType()))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("json?????????????????????qid"+quesList,e);
            throw new BusinessException("??????????????????");
        }
        return result;
    }


    private List<String> commonQuesGenerate(String memberId,Integer status,Integer sort,String sid){
        MemberCourseBarrierPO querryCoursePO = new MemberCourseBarrierPO();
        querryCoursePO.setMemberId(memberId);
        querryCoursePO.setIsValid(1);
        List<MemberCourseBarrierPO> courseList = memberCourseBarrierMapper.queryAll(querryCoursePO);
        //???????????????id
        List<String> courseIdList = null;
        //???????????????
        if (status.equals(MemberBarrierConstant.BARRIER_STATUS_LOCKED)){
            lockedBarrierHandler(courseList,sort,memberId);
        }else {

            try {
                courseIdList = Arrays.asList(courseList.get(sort * 3 - 3).getCourseId(),
                        courseList.get(sort * 3 - 2).getCourseId(), courseList.get(sort * 3 - 1).getCourseId());
            }catch (Exception e){
                throw new BusinessException("?????????????????????????????????????????????????????????????????????????????????????????????");
            }
        }
        //????????????
        List<String> list = barrierCourseQuesGenerate(sid, courseIdList);
        return list;
    }

    private void lockedBarrierHandler(List<MemberCourseBarrierPO> courseList,Integer sort,String memberId){
        MemberBarrierPO querry = new MemberBarrierPO();
        querry.setBarrierType(MemberBarrierConstant.BARRIER_TYPE_COMMON);
        querry.setStatus(MemberBarrierConstant.BARRIER_STATUS_RETRY);
        querry.setMemberId(memberId);
        querry.setIsValid(1);
        List<MemberBarrierPO> retryBars = memberBarrierMapper.queryAll(querry);//????????????
        querry.setStatus(MemberBarrierConstant.BARRIER_STATUS_ONGOING);
        List<MemberBarrierPO> goingBars = memberBarrierMapper.queryAll(querry);//?????????
        if (retryBars.size()==0 && goingBars.size() == 0 ){
            int courseNum = courseList.size();//??????????????????
            int diff = sort * 3 - courseNum;//???????????????????????????
            if (diff > 0 && diff <= 3){
                throw new BusinessException("?????????"+diff+"??????????????????????????????~");
            }
        }
        throw new BusinessException("???????????????????????????~");
    }

    @Override
    @Transactional
    public String submitBarrierQues(SubmitBarrierDTO submitBarrierDTO) {
       //??????????????????
        boolean scorePass = false;
        //????????????????????????
        if (!StringUtils.isBlank(submitBarrierDTO.getScore()) && Float.parseFloat(submitBarrierDTO.getScore()) >= 60){
            scorePass = true;
        }
        MemberBarrierPO memberBarrierPO = memberBarrierMapper.queryById(submitBarrierDTO.getSid());
        if (null == memberBarrierPO){
            throw new BusinessException("???????????????????????????");
        }
        MemberBarrierPO updateMemberBarrierPO = new MemberBarrierPO();
        updateMemberBarrierPO.setSid(submitBarrierDTO.getSid());
        //????????????
        String score = memberBarrierPO.getScore();
        if (!StringUtils.isBlank(score)){
            //?????????????????????????????????
            if (Integer.parseInt(submitBarrierDTO.getScore())> Integer.parseInt(score)){
                updateMemberBarrierPO.setScore(submitBarrierDTO.getScore());
            }
        }else {
            updateMemberBarrierPO.setScore(submitBarrierDTO.getScore());
        }

        String batchId = barrierQuesHandler(submitBarrierDTO.getAnswer(), submitBarrierDTO.getSid(),submitBarrierDTO.getOverTime());
        if (memberBarrierPO.getStatus() != MemberBarrierConstant.BARRIER_STATUS_FINISHED){
            if (scorePass){//??????
                //????????????????????????
                updateMemberBarrierPO.setStatus(MemberBarrierConstant.BARRIER_STATUS_FINISHED);
            }else {
                //???????????????????????????????????????
                updateMemberBarrierPO.setStatus(MemberBarrierConstant.BARRIER_STATUS_RETRY);
            }
        }
        memberBarrierMapper.update(updateMemberBarrierPO);
        return batchId;
    }

    @Override
    public BarrierResultVO resultBarrier(BarrierResultDTO barrierResultDTO) {
        String sid = barrierResultDTO.getSid();
        String batchId = barrierResultDTO.getBatchId();
        String score = barrierResultDTO.getScore();
        BarrierResultVO barrierResultVO = memberBarrierMapper.queryResult(sid,batchId);
        if (null == barrierResultVO){
            throw new BusinessException("???????????????????????????");
        }
        barrierResultVO.setScore(score);
        int scoreI = Integer.parseInt(score);
        Integer isSuccess = MemberBarrierConstant.BARRIER_RESULT_FAILED;
            //?????????60???????????????
        if (scoreI >= 60 ){
            isSuccess = MemberBarrierConstant.BARRIER_RESULT_SUCCESS;
            MemberBarrierPO memberBarrierPO = memberBarrierMapper.queryById(sid);
            Integer sort = memberBarrierPO.getSort();//????????????
            int totalNum = memberBarrierMapper.countBarrPersonNumBySort(sort);//????????????????????????
            int overNum = memberBarrierMapper.countBarrPersonOverScoreNum(sort, scoreI);//?????????????????????????????????????????????????????????
            int underNum = memberBarrierMapper.countBarrPersonUnderScoreNum(sort, scoreI);//?????????????????????????????????????????????????????????
            if (totalNum == 1){
                //?????????????????????
                barrierResultVO.setRankRate("100%");
            }else {
                if (overNum == 0){//??????????????????
                    barrierResultVO.setRankRate("100%");
                }else {
                    float rank = (float)underNum / (float) totalNum;
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    numberFormat.setMaximumFractionDigits(0);
                    String format = numberFormat.format(rank * 100);
                    barrierResultVO.setRankRate(format+"%");
                }
            }
        }
        barrierResultVO.setIsSuccess(isSuccess);
        getTipByScore(barrierResultVO,Integer.parseInt(score));
        return barrierResultVO;
    }

    private String getTipByScore(BarrierResultVO barrierResultVO,Integer score){
        String tip = "";
            switch (score){
                case 100:
                    barrierResultVO.setTipTitle(MemberBarrierConstant.BARRIER_RESULT_TITLE_100);
                    barrierResultVO.setTip(MemberBarrierConstant.BARRIER_RESULT_100);
                    break;
                case 80:
                    barrierResultVO.setTipTitle(MemberBarrierConstant.BARRIER_RESULT_TITLE_80);
                    barrierResultVO.setTip(MemberBarrierConstant.BARRIER_RESULT_80);
                    break;
                case 60:
                    barrierResultVO.setTipTitle(MemberBarrierConstant.BARRIER_RESULT_TITLE_60);
                    barrierResultVO.setTip(MemberBarrierConstant.BARRIER_RESULT_60);
                    break;
                case 40:
                    barrierResultVO.setTipTitle(MemberBarrierConstant.BARRIER_RESULT_TITLE_40);
                    barrierResultVO.setTip(MemberBarrierConstant.BARRIER_RESULT_40);
                    break;
                case 20:
                    barrierResultVO.setTipTitle(MemberBarrierConstant.BARRIER_RESULT_TITLE_20);
                    barrierResultVO.setTip(MemberBarrierConstant.BARRIER_RESULT_20);
                    break;
                case 0:
                    barrierResultVO.setTipTitle(MemberBarrierConstant.BARRIER_RESULT_TITLE_0);
                    barrierResultVO.setTip(MemberBarrierConstant.BARRIER_RESULT_0);
                    break;
                default:
                    break;
            }
        return tip;
    }

    @Override
    public List<Map<String, Object>> listBarrierError(String sid,String batchId) {
        //???????????????
        Map<String, Object> param = new HashMap<>();
        param.put("sid",sid);
        param.put("batchId",batchId);
        List<BarrierQuesCfgPO> quesList = barrierQuesMapper.queryErrorQuesByBarrierId(param);
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (quesList.size()>0){
            for (BarrierQuesCfgPO barrierQuesCfgPO : quesList) {
                BarrierQuesPO barrierQuesPO = new BarrierQuesPO();
                barrierQuesPO.setBarrierId(sid);
                barrierQuesPO.setQuesId(barrierQuesCfgPO.getSid());
                barrierQuesPO.setBatchId(batchId);
                List<BarrierQuesPO> barrierQuesPOS = barrierQuesMapper.queryAll(barrierQuesPO);
                if (null != barrierQuesPOS){
                    BarrierQuesPO quesPO = barrierQuesPOS.get(0);//????????????
                    Map<String, Object> map = new HashMap<>();
                    map.put("sid",barrierQuesCfgPO.getSid());
                    map.put("title",barrierQuesCfgPO.getTitle());
                    map.put("quesType",barrierQuesCfgPO.getQuesType());
                    map.put("answerOptions",JsonSerializer.jsonToList(barrierQuesCfgPO.getAnswerOptions(), Map.class));
                    map.put("answerKey",barrierQuesCfgPO.getAnswerKey());
                    map.put("analyse",barrierQuesCfgPO.getAnalyse());
                    map.put("memberAnswer",quesPO.getAnswer());
                    resultList.add(map);
                }
            }
        }
        return resultList;
    }

    @Override
    @Transactional
    public void addBarrierQuesCfg(AddBarrierQuesCfgDTO addBarrierQuesCfgDTO) {
        String quesId = DaoHelper.getSeq();
        BarrierQuesCfgPO addPO = new BarrierQuesCfgPO();
        BeanUtils.copyProperties(addPO,addBarrierQuesCfgDTO);
        addPO.setSid(quesId);
        barrierQuesCfgMapper.insert(addPO);
        if (!StringUtils.isBlank(addBarrierQuesCfgDTO.getCourseId())){
            BarrierCourseQuesCfgPO cfgPO = new BarrierCourseQuesCfgPO();
            cfgPO.setSid(DaoHelper.getSeq());
            cfgPO.setQuesId(quesId);
            cfgPO.setCourseId(addBarrierQuesCfgDTO.getCourseId());
            barrierCourseQuesCfgMapper.insert(cfgPO);
        }
    }

    @Override
    @Transactional
    public void delBarrierQuesCfg(String sid) {
        barrierQuesCfgMapper.deleteById(sid);
        barrierCourseQuesCfgMapper.deleteByQId(sid);
    }

    @Override
    @Transactional
    public void updateBarrierQuesCfg(UpdateBarrierQuesCfgDTO updateBarrierQuesCfg) {
        BarrierQuesCfgPO updatePO = new BarrierQuesCfgPO();
        BeanUtils.copyProperties(updatePO,updateBarrierQuesCfg);
        barrierQuesCfgMapper.update(updatePO);
        barrierCourseQuesCfgMapper.deleteByQId(updateBarrierQuesCfg.getSid());
        if (!StringUtils.isBlank(updateBarrierQuesCfg.getCourseId())){
            BarrierCourseQuesCfgPO cfgPO = new BarrierCourseQuesCfgPO();
            cfgPO.setSid(DaoHelper.getSeq());
            cfgPO.setQuesId(updateBarrierQuesCfg.getSid());
            cfgPO.setCourseId(updateBarrierQuesCfg.getCourseId());
            barrierCourseQuesCfgMapper.insert(cfgPO);
        }
    }

    @Override
    public PageResult<BarrierQuesCfgVO> listBarrierQuesCfgVO(String title, PageRequest page) {
        PageHelper.startPage(page.getPage(),page.getRows());
        List<BarrierQuesCfgVO> barrierQuesCfgVOS = barrierQuesCfgMapper.listAllQuesCfg(title);
        for (BarrierQuesCfgVO barrierQuesCfgVO : barrierQuesCfgVOS) {
            barrierQuesCfgVO.setAnswerList(JsonSerializer.jsonToMapList(barrierQuesCfgVO.getAnswerOptions()));
        }
        return new PageResult<>(barrierQuesCfgVOS);
    }

    @Override
    public BarrierQuesCfgVO detailBarrierQuesCfgVO(String sid) {
        BarrierQuesCfgVO quesCfgById = barrierQuesCfgMapper.getQuesCfgById(sid);
        quesCfgById.setAnswerList(JsonSerializer.jsonToMapList(quesCfgById.getAnswerOptions()));
        return quesCfgById;
    }

    //???????????????
    private void trialYardGenerate(String memberId){
        MemberBarrierPO trialYard = new MemberBarrierPO();
        trialYard.setSid(DaoHelper.getSeq());
        trialYard.setMemberId(memberId);
        trialYard.setBarrierType(MemberBarrierConstant.BARRIER_TYPE_TRIALYARD);
        trialYard.setStatus(MemberBarrierConstant.BARRIER_STATUS_ONGOING);
        trialYard.setSort(0);
        memberBarrierMapper.insert(trialYard);
    }

    //?????????????????????
    private List<String>  trialYardQuesGenerate(){
        BarrierQuesCfgPO querryCfgPO = new BarrierQuesCfgPO();
        querryCfgPO.setIsValid(1);
        querryCfgPO.setBarrierType(MemberBarrierConstant.BARRIER_TYPE_TRIALYARD);
        List<BarrierQuesCfgPO> barrierQuesCfgPOS = barrierQuesCfgMapper.queryAll(querryCfgPO);
        List<String> collect = barrierQuesCfgPOS.stream().map(BarrierQuesCfgPO::getSid).collect(Collectors.toList());
        List<String> resultList = new ArrayList<>(5);
        while (resultList.size() < 5){
            if (collect.size() == 0){
                return resultList;
            }
            if (collect.size()>0){
                Random rand = new Random();
                String quesId = collect.get(rand.nextInt(collect.size()));
                resultList.add(quesId);
                collect.remove(quesId);
            }
        }
        return resultList;
    }



    //??????????????????
    private void ordinaryBarrierGenerate(String memberId,Integer sort,Integer status){
        MemberBarrierPO commonBarrier = new MemberBarrierPO();
        String barrierId = DaoHelper.getSeq();
        commonBarrier.setSid(barrierId);
        commonBarrier.setMemberId(memberId);
        commonBarrier.setBarrierType(MemberBarrierConstant.BARRIER_TYPE_COMMON);
        commonBarrier.setStatus(status);
        commonBarrier.setSort(sort);
        memberBarrierMapper.insert(commonBarrier);
    }

    //??????????????????????????????
    private List<String> barrierCourseQuesGenerate(String barrierId,List<String> courseIdList){
        //String nowDate = DateHelper.getNowDate();
        if (null == courseIdList || courseIdList.size() != 3){
            throw new BusinessException("????????????????????????????????????");
        }
        List<String> quesList = new ArrayList<>();
        for (String courseId : courseIdList) {
            if (StringUtils.isBlank(courseId)){
                throw new BusinessException("???????????????");
            }
            //????????????????????????
            BarrierCourseQuesCfgPO querryQuesCfg = new BarrierCourseQuesCfgPO();
            querryQuesCfg.setCourseId(courseId);
            querryQuesCfg.setIsValid(1);
            List<BarrierCourseQuesCfgPO> courseQuesList = barrierCourseQuesCfgMapper.queryAll(querryQuesCfg);
            //?????????????????????
            if (courseQuesList.size()>0){
                Random rand = new Random();
                BarrierCourseQuesCfgPO cfgPO = courseQuesList.get(rand.nextInt(courseQuesList.size()));
                quesList.add(cfgPO.getQuesId());//????????????????????????????????????
            }
        }
        suppQuesList(courseIdList,quesList);//????????????????????????
        if (quesList.size() == 0){
            log.error("??????id:"+barrierId+"????????????????????????????????????"+courseIdList+"???????????????");
            throw new BusinessException("??????????????????????????????????????????????????????????????????");
        }
        return quesList;
    }

    //??????????????????????????????????????????????????????????????????????????????????????????5???
    private void  suppQuesList(List<String> courseIdList,List<String> quesList){
        //??????????????????????????????????????????
        List<String> leftQueslist = barrierCourseQuesCfgMapper.loadLeftCourseQues(courseIdList, quesList);
        //???????????????????????????
        while (quesList.size() < 5){
            if (leftQueslist.size() == 0){
                return;
            }
            Random rand = new Random();
            String quesId1 = leftQueslist.get(rand.nextInt(leftQueslist.size()));
            quesList.add(quesId1);
            leftQueslist.remove(quesId1);
        }
    }

    //???????????????????????????
    private String barrierQuesHandler(String answer,String barrierId,Integer overTime){
        String nowDate = DateHelper.getNowDate();
        String batchId = DaoHelper.getSeq();
        if (!StringUtils.isBlank(answer)){
            List<Map> mapList = JsonSerializer.jsonToList(answer, Map.class);
            int i =0;
            for (Map map : mapList) {
                i++;
                String qid = String.valueOf(map.get("qid"));
                ValidateTool.checkParamIsNull(qid,"qid");
                BarrierQuesPO barrierQuesPO = new BarrierQuesPO();
                barrierQuesPO.setSid(DaoHelper.getSeq());
                barrierQuesPO.setBarrierId(barrierId);
                barrierQuesPO.setBatchId(batchId);
                barrierQuesPO.setQuesId(qid);
                barrierQuesPO.setSort(i);
                barrierQuesPO.setInsertDt(nowDate);
                barrierQuesPO.setModifyDt(nowDate);
                barrierQuesPO.setOverTime(overTime);
                if (!ObjectUtils.isEmpty(map.get("answer"))){
                    String qAnswer = String.valueOf(map.get("answer"));
                    barrierQuesPO.setAnswer(qAnswer);
                }else {
                    barrierQuesPO.setAnswer(null);
                }

                if (!ObjectUtils.isEmpty(map.get("isCorrect"))){
                    Integer isCorrect = Integer.valueOf(String.valueOf(map.get("isCorrect")));
                    barrierQuesPO.setIsCorrect(isCorrect);
                }else {
                    barrierQuesPO.setIsCorrect(null);
                }
                barrierQuesMapper.insert(barrierQuesPO);
            }
        }
        return batchId;
    }
}
