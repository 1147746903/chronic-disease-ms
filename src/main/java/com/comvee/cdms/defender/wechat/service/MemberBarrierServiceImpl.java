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
        //第一次进入，从未生成
        if (barrierPOList.size() == 0){
            this.trialYardGenerate(memberId);
        }
        //检查课程库更新关卡
        List<String> list = memberBarrierMapper.loadAllBarrCourse();
        int newNum = list.size()/3; //可以生成的关卡数量
        querryBarrierPO.setBarrierType(MemberBarrierConstant.BARRIER_TYPE_COMMON);
        //普通关卡列表
        List<MemberBarrierPO> commonBarrierPOList = memberBarrierMapper.queryAll(querryBarrierPO);
        int nowNum = commonBarrierPOList.size();
        //题库变化
        if (nowNum < newNum){
            //有新增的线上关卡，需新增关卡
            int doBarrierNum = newNum - nowNum;//需新生成关卡数量
            if (commonBarrierPOList.size() == 0 && doBarrierNum > 0){
                //第一次生成
                for (int i = 1; i <= doBarrierNum; i++) {
                    //预生成关卡
                    ordinaryBarrierGenerate(memberId,i,MemberBarrierConstant.BARRIER_STATUS_LOCKED);
                }
            }else if (nowNum != 0 && doBarrierNum > 0){
                //后续题库新增
                MemberBarrierPO lastBarrier = commonBarrierPOList.get(commonBarrierPOList.size() - 1);
                Integer sort = lastBarrier.getSort();//最后的关卡序号
                for (int i = sort+1; i <= newNum; i++) {
                    ordinaryBarrierGenerate(memberId,i,MemberBarrierConstant.BARRIER_STATUS_LOCKED);
                }
            }
        }else if (nowNum > newNum){
            int doBarrierNum = nowNum - newNum;//需删除的关卡数量
            List<String> delList = memberBarrierMapper.queryIdDescLimit(memberId, doBarrierNum);
            for (String sid : delList) {
                memberBarrierMapper.deleteById(sid);
            }
        }
        ongoingBarrierCheck(memberId);//处理进行中关卡更新
        querryBarrierPO.setBarrierType(null);
        List<MemberBarrierPO> resultList = memberBarrierMapper.queryAll(querryBarrierPO);
        //末尾新增 敬请期待 关卡返回给前端
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


    //若一个进行中都不存在，则判断完成课程数量是否足以开启第一个关卡（放在这里做而不是提交时是为了确保最后一关提交后+题库更新后也能更新）
    private void ongoingBarrierCheck(String memberId){
        boolean coursePass = false;
        boolean preBarPass = false;
        MemberBarrierPO querry = new MemberBarrierPO();
        querry.setBarrierType(MemberBarrierConstant.BARRIER_TYPE_COMMON);
        querry.setStatus(MemberBarrierConstant.BARRIER_STATUS_ONGOING);
        querry.setMemberId(memberId);
        querry.setIsValid(1);
        List<MemberBarrierPO> ongoingBars = memberBarrierMapper.queryAll(querry);//进行中的所有关卡
        if (ongoingBars.size()==0){
            querry.setStatus(MemberBarrierConstant.BARRIER_STATUS_LOCKED);
            List<MemberBarrierPO> lockedBars = memberBarrierMapper.queryAll(querry);
            if (lockedBars.size()>0){
                MemberBarrierPO lockedBar = lockedBars.get(0);
                Integer sort = lockedBar.getSort();
                //判断上一关是否为已完成或者不存在
                querry.setStatus(null);
                querry.setSort(sort-1);
                List<MemberBarrierPO> barrierPOList = memberBarrierMapper.queryAll(querry);
                if (barrierPOList.size() == 0 || barrierPOList.get(0).getStatus() == MemberBarrierConstant.BARRIER_STATUS_FINISHED){
                    preBarPass = true;
                }
                //判断完成课程数是否达标
                MemberCourseBarrierPO querryCoursePO = new MemberCourseBarrierPO();
                querryCoursePO.setMemberId(memberId);
                querryCoursePO.setIsValid(1);
                List<MemberCourseBarrierPO> courseList = memberCourseBarrierMapper.queryAll(querryCoursePO);
                int ongoingBarNum = courseList.size()/3;//可激活的关卡数
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
            throw new BusinessException("关卡不存在");
        }
        List<String> queIdList = null;//题目列表
        String memberId = memberBarrierPO.getMemberId();
        Integer status = memberBarrierPO.getStatus();
        Integer sort = memberBarrierPO.getSort();
        if (memberBarrierPO.getBarrierType() == 1){
            //试炼场题目生成
            queIdList = trialYardQuesGenerate();
        }else {
            //普通关卡题目生成
            queIdList = commonQuesGenerate(memberId, status, sort, sid);
        }
        List<BarrierQuesCfgPO> quesList = new ArrayList<>();
        for (String quesId : queIdList) {
            BarrierQuesCfgPO barrierQuesCfgPO = barrierQuesCfgMapper.queryById(quesId);
            quesList.add(barrierQuesCfgPO);
        }
        List<ListBarrierQuesVO> result = null;
        //处理答案选项
        try {
            if (quesList.size()>0){
                result = quesList.stream().map(e ->
                        new ListBarrierQuesVO(e.getSid(),e.getTitle(), JsonSerializer.jsonToMapList(e.getAnswerOptions()),
                                e.getAnswerKey(),e.getAnalyse(),e.getType(),e.getTag(),e.getQuesType()))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("json格式转换错误，qid"+quesList,e);
            throw new BusinessException("获取题目失败");
        }
        return result;
    }


    private List<String> commonQuesGenerate(String memberId,Integer status,Integer sort,String sid){
        MemberCourseBarrierPO querryCoursePO = new MemberCourseBarrierPO();
        querryCoursePO.setMemberId(memberId);
        querryCoursePO.setIsValid(1);
        List<MemberCourseBarrierPO> courseList = memberCourseBarrierMapper.queryAll(querryCoursePO);
        //对应的课程id
        List<String> courseIdList = null;
        //关卡为锁定
        if (status.equals(MemberBarrierConstant.BARRIER_STATUS_LOCKED)){
            lockedBarrierHandler(courseList,sort,memberId);
        }else {

            try {
                courseIdList = Arrays.asList(courseList.get(sort * 3 - 3).getCourseId(),
                        courseList.get(sort * 3 - 2).getCourseId(), courseList.get(sort * 3 - 1).getCourseId());
            }catch (Exception e){
                throw new BusinessException("未找到关卡对应课程，请联系管理员确认课程或者课程题目是否已删除");
            }
        }
        //获取题目
        List<String> list = barrierCourseQuesGenerate(sid, courseIdList);
        return list;
    }

    private void lockedBarrierHandler(List<MemberCourseBarrierPO> courseList,Integer sort,String memberId){
        MemberBarrierPO querry = new MemberBarrierPO();
        querry.setBarrierType(MemberBarrierConstant.BARRIER_TYPE_COMMON);
        querry.setStatus(MemberBarrierConstant.BARRIER_STATUS_RETRY);
        querry.setMemberId(memberId);
        querry.setIsValid(1);
        List<MemberBarrierPO> retryBars = memberBarrierMapper.queryAll(querry);//重新闯关
        querry.setStatus(MemberBarrierConstant.BARRIER_STATUS_ONGOING);
        List<MemberBarrierPO> goingBars = memberBarrierMapper.queryAll(querry);//进行中
        if (retryBars.size()==0 && goingBars.size() == 0 ){
            int courseNum = courseList.size();//完成课程数量
            int diff = sort * 3 - courseNum;//开启关卡所需课程数
            if (diff > 0 && diff <= 3){
                throw new BusinessException("再学习"+diff+"门课就可以开启该关哦~");
            }
        }
        throw new BusinessException("先闯过上一关再来吧~");
    }

    @Override
    @Transactional
    public String submitBarrierQues(SubmitBarrierDTO submitBarrierDTO) {
       //闯关失败处理
        boolean scorePass = false;
        //判断分数是否达标
        if (!StringUtils.isBlank(submitBarrierDTO.getScore()) && Float.parseFloat(submitBarrierDTO.getScore()) >= 60){
            scorePass = true;
        }
        MemberBarrierPO memberBarrierPO = memberBarrierMapper.queryById(submitBarrierDTO.getSid());
        if (null == memberBarrierPO){
            throw new BusinessException("关卡不存在！请确认");
        }
        MemberBarrierPO updateMemberBarrierPO = new MemberBarrierPO();
        updateMemberBarrierPO.setSid(submitBarrierDTO.getSid());
        //填充答案
        String score = memberBarrierPO.getScore();
        if (!StringUtils.isBlank(score)){
            //重复闯关仅保存最高分数
            if (Integer.parseInt(submitBarrierDTO.getScore())> Integer.parseInt(score)){
                updateMemberBarrierPO.setScore(submitBarrierDTO.getScore());
            }
        }else {
            updateMemberBarrierPO.setScore(submitBarrierDTO.getScore());
        }

        String batchId = barrierQuesHandler(submitBarrierDTO.getAnswer(), submitBarrierDTO.getSid(),submitBarrierDTO.getOverTime());
        if (memberBarrierPO.getStatus() != MemberBarrierConstant.BARRIER_STATUS_FINISHED){
            if (scorePass){//及格
                //设置本关卡为完成
                updateMemberBarrierPO.setStatus(MemberBarrierConstant.BARRIER_STATUS_FINISHED);
            }else {
                //分数未达标则设置为再次闯关
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
            throw new BusinessException("关卡不存在，请确认");
        }
        barrierResultVO.setScore(score);
        int scoreI = Integer.parseInt(score);
        Integer isSuccess = MemberBarrierConstant.BARRIER_RESULT_FAILED;
            //仅大于60才计算排名
        if (scoreI >= 60 ){
            isSuccess = MemberBarrierConstant.BARRIER_RESULT_SUCCESS;
            MemberBarrierPO memberBarrierPO = memberBarrierMapper.queryById(sid);
            Integer sort = memberBarrierPO.getSort();//关卡序号
            int totalNum = memberBarrierMapper.countBarrPersonNumBySort(sort);//进行排名的总人数
            int overNum = memberBarrierMapper.countBarrPersonOverScoreNum(sort, scoreI);//大于分数的人数（排除自己的已提交分数）
            int underNum = memberBarrierMapper.countBarrPersonUnderScoreNum(sort, scoreI);//小于分数的人数（排除自己的已提交分数）
            if (totalNum == 1){
                //只有一个人在比
                barrierResultVO.setRankRate("100%");
            }else {
                if (overNum == 0){//没人分数更高
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
        //关卡错题集
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
                    BarrierQuesPO quesPO = barrierQuesPOS.get(0);//用户答案
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

    //生成试炼场
    private void trialYardGenerate(String memberId){
        MemberBarrierPO trialYard = new MemberBarrierPO();
        trialYard.setSid(DaoHelper.getSeq());
        trialYard.setMemberId(memberId);
        trialYard.setBarrierType(MemberBarrierConstant.BARRIER_TYPE_TRIALYARD);
        trialYard.setStatus(MemberBarrierConstant.BARRIER_STATUS_ONGOING);
        trialYard.setSort(0);
        memberBarrierMapper.insert(trialYard);
    }

    //生成试炼场题目
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



    //生成普通关卡
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

    //处理关卡问题题目关系
    private List<String> barrierCourseQuesGenerate(String barrierId,List<String> courseIdList){
        //String nowDate = DateHelper.getNowDate();
        if (null == courseIdList || courseIdList.size() != 3){
            throw new BusinessException("获取关卡对应课程数量错误");
        }
        List<String> quesList = new ArrayList<>();
        for (String courseId : courseIdList) {
            if (StringUtils.isBlank(courseId)){
                throw new BusinessException("课程不存在");
            }
            //获取课程题目列表
            BarrierCourseQuesCfgPO querryQuesCfg = new BarrierCourseQuesCfgPO();
            querryQuesCfg.setCourseId(courseId);
            querryQuesCfg.setIsValid(1);
            List<BarrierCourseQuesCfgPO> courseQuesList = barrierCourseQuesCfgMapper.queryAll(querryQuesCfg);
            //随机取一道题目
            if (courseQuesList.size()>0){
                Random rand = new Random();
                BarrierCourseQuesCfgPO cfgPO = courseQuesList.get(rand.nextInt(courseQuesList.size()));
                quesList.add(cfgPO.getQuesId());//每个课程至少保证一个题目
            }
        }
        suppQuesList(courseIdList,quesList);//补充关联题目数量
        if (quesList.size() == 0){
            log.error("关卡id:"+barrierId+"每日挑战生成关卡对应课程"+courseIdList+"未含有题目");
            throw new BusinessException("关联课程不含有任何闯关题目，请联系管理员补充");
        }
        return quesList;
    }

    //题目除每个题目至少关联一条外，还要补充关联课程随机两条，补全5条
    private void  suppQuesList(List<String> courseIdList,List<String> quesList){
        //获取除已添加题目外剩下的题库
        List<String> leftQueslist = barrierCourseQuesCfgMapper.loadLeftCourseQues(courseIdList, quesList);
        //随机取不重复的两个
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

    //填充关卡题目表信息
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
