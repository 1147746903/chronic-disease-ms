package com.comvee.cdms.defender.wechat.service;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.defender.wechat.bo.DailyQuestionBO;
import com.comvee.cdms.defender.wechat.constant.DailyQuestionConstant;
import com.comvee.cdms.defender.wechat.dto.SubmitQuestionDTO;
import com.comvee.cdms.defender.wechat.mapper.DailyQuestionMapper;
import com.comvee.cdms.defender.wechat.mapper.DailyQuestionPushMapper;
import com.comvee.cdms.defender.wechat.mapper.MemberDailyQuestionRecordMapper;
import com.comvee.cdms.defender.wechat.po.DailyQuestionPO;
import com.comvee.cdms.defender.wechat.po.DailyQuestionPushPO;
import com.comvee.cdms.defender.wechat.po.MemberDailyQuestionRecordPO;
import com.comvee.cdms.defender.wechat.vo.DailyQuestionVO;
import com.comvee.cdms.prescription.service.PrescriptionOfEduServiceI;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.prescription.service.PrescriptionOfEduServiceI;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author linr
 * @Date 2021/11/26
 */
@Service
public class DailyQuestionServiceImpl implements DailyQuestionServiceI {

    @Autowired
    private DailyQuestionMapper dailyQuestionMapper;

    @Autowired
    private MemberDailyQuestionRecordMapper memberDailyQuestionRecordMapper;

    @Autowired
    private DailyQuestionPushMapper dailyQuestionPushMapper;

    @Autowired
    private PrescriptionOfEduServiceI prescriptionOfEduServiceI;



    private final static Logger logger = LoggerFactory.getLogger(DailyQuestionServiceImpl.class);

    @Override
    public DailyQuestionVO loadDailyQuestionById(String sid) {
        DailyQuestionVO dailyQuestionVO = dailyQuestionMapper.queryVOById(sid);
        dailyQuestionVO.setAnswerList(JsonSerializer.jsonToMapList(dailyQuestionVO.getAnswerOptions()));
        return dailyQuestionVO;
    }

    @Override
    public void addDailyQuestion(DailyQuestionPO dailyQuestionPO) {
        dailyQuestionPO.setSid(DaoHelper.getSeq());
        dailyQuestionMapper.insert(dailyQuestionPO);
    }

    @Override
    public void updatDailyQuestion(DailyQuestionPO dailyQuestionPO) {
        dailyQuestionMapper.update(dailyQuestionPO);
    }

    @Override
    public void delDailyQuestionPO(String sid) {
        dailyQuestionMapper.deleteById(sid);
    }

    @Override
    public PageResult<DailyQuestionVO> listDailyQuestionPO(PageRequest page) {
        PageHelper.startPage(page.getPage(),page.getRows());
        List<DailyQuestionVO> dailyQuestionVOS = dailyQuestionMapper.queryVOList(new DailyQuestionPO());
        for (DailyQuestionVO dailyQuestionVO : dailyQuestionVOS) {
            dailyQuestionVO.setAnswerList(JsonSerializer.jsonToMapList(dailyQuestionVO.getAnswerOptions()));
        }
        return new PageResult<>(dailyQuestionVOS);
    }

    @Override
    @Transactional
    public String dailyQuestionGenerate(String memberId) {
        String qid = memberDailyQuestionGenerate(memberId);
        return qid;
    }

    @Override
    @Transactional
    public DailyQuestionBO loadDailyQuestion(String memberId) {
        DailyQuestionBO dailyQuestionBO = dailyQuestionPushMapper.queryByMemberId(memberId);
        if (null == dailyQuestionBO){
            //????????????????????????
            String sid = memberDailyQuestionGenerate(memberId);
            DailyQuestionPO dailyQuestionPO = dailyQuestionMapper.queryById(sid);
            dailyQuestionBO = new DailyQuestionBO();
            BeanUtils.copyProperties(dailyQuestionBO,dailyQuestionPO);
            dailyQuestionBO.setIsSubmit(0);
        }
        return dailyQuestionBO;
    }

    //????????????????????????????????????
    private String memberDailyQuestionGenerate(String memberId){
        DailyQuestionPushPO addPushPO = new DailyQuestionPushPO();
        addPushPO.setSid(DaoHelper.getSeq());
        addPushPO.setMemberId(memberId);
        addPushPO.setPushDt(DateHelper.getToday());
        addPushPO.setIsSubmit(0);
        List<DailyQuestionBO> pushList = dailyQuestionPushMapper.listByMemberId(memberId);//????????????list
        //???????????????????????????
        if (pushList.size() == 0){
            firstDailyQuesPush(addPushPO);
        }else {
            //??????????????????????????????
            DailyQuestionBO pushQues = pushList.get(0);//?????????????????????
            Integer type = pushQues.getType();
            String nextTwoWeekTypeQid = getNextTwoWeekQid(type, memberId);
            if (!StringUtils.isBlank(nextTwoWeekTypeQid)){
                //???????????????
                addPushPO.setStage(DailyQuestionConstant.DAILY_QUES_PUSH_STAGE_1);
                addPushPO.setQid(nextTwoWeekTypeQid);
            }else {
                //??????????????????????????????????????????
                //??????????????????
                List<String> memberTags = prescriptionOfEduServiceI.getMemberTags(memberId);
                List<Integer> tagTypes = getDQTypeByTagList(memberTags);//????????????type
                logger.info("????????????????????????"+memberId+"??????"+memberTags+"???????????????"+tagTypes);
                //????????????????????????????????????
                if (null != memberTags && memberTags.size() > 0){
                    String nextTwoTypeType = getNextTwoTypeQid(tagTypes, type, memberId);
                    if (!StringUtils.isBlank(nextTwoTypeType)){
                        addPushPO.setStage(DailyQuestionConstant.DAILY_QUES_PUSH_STAGE_1);
                        addPushPO.setQid(nextTwoTypeType);
                    }else {
                        //????????????????????????
                        stageLeftHandler(type,memberId,addPushPO,pushQues);
                    }
                }else {
                    //????????????????????????
                    stageLeftHandler(type,memberId,addPushPO,pushQues);
                }
            }
        }
        if (StringUtils.isBlank(addPushPO.getQid())){
            logger.info("??????"+memberId+"??????????????????????????????????????????...");
            return "-1";
        }
        dailyQuestionPushMapper.insert(addPushPO);
        return addPushPO.getQid();
    }


    //???????????????
    private void firstDailyQuesPush(DailyQuestionPushPO addPushPO){
        DailyQuestionPO queryPO = new DailyQuestionPO();
        queryPO.setType(DailyQuestionConstant.DAILY_QUES_TYPE_COMPLICATION);
        queryPO.setTwoType(DailyQuestionConstant.DAILY_QUES_TWO_TYPE_COMMON);
        queryPO.setGroupCode(DailyQuestionConstant.DAILY_QUES_GROUP_CODE_TWO_WEEK);
        queryPO.setSort(1);
        DailyQuestionPO dailyQuestionPO = dailyQuestionMapper.getDailyQuestionPO(queryPO);
        addPushPO.setStage(DailyQuestionConstant.DAILY_QUES_PUSH_STAGE_1);
        addPushPO.setQid(dailyQuestionPO.getSid());
    }


    //?????????????????????????????????
    private void stageLeftHandler(Integer type,String memberId,DailyQuestionPushPO addPushPO,DailyQuestionBO pushQues){
        String nextCommonType = getNextCommonTypeQid(type,memberId);
        if (!StringUtils.isBlank(nextCommonType)){
            addPushPO.setStage(DailyQuestionConstant.DAILY_QUES_PUSH_STAGE_1);
            addPushPO.setQid(nextCommonType);
        }else {
            //???????????????????????????
            stageTwoThreeHandler(addPushPO,memberId,pushQues);
        }
    }

    //?????????????????????????????????
    private void stageTwoThreeHandler(DailyQuestionPushPO addPushPO,String memberId,DailyQuestionBO pushQues){
        //???????????????????????????
        MemberDailyQuestionRecordPO errorQues = memberDailyQuestionRecordMapper.queryErrorsExcludeMember(memberId);
        if (null != errorQues){
            addPushPO.setStage(DailyQuestionConstant.DAILY_QUES_PUSH_STAGE_2);
            addPushPO.setQid(errorQues.getQid());
        }else {
            //????????????????????????????????????????????????
            List<MemberDailyQuestionRecordPO> doneList = memberDailyQuestionRecordMapper.queryDoneExcludeMember(memberId);
            String nextDoneQid = getNextDoneQid(doneList, pushQues.getSid());
            addPushPO.setStage(DailyQuestionConstant.DAILY_QUES_PUSH_STAGE_3);
            addPushPO.setQid(nextDoneQid);
        }
    }

    //??????????????????????????????qid
    private String getNextTwoTypeQid(List<Integer> list,Integer type,String memberId){
        List<DailyQuestionPO> dailyQuestionPOS = dailyQuestionMapper.queryAllByTwoTypeExcludeMember(list,memberId);
        String qid = getNextTypeQid(type, dailyQuestionPOS);
        return qid;
    }

    //?????????????????????????????????qid
    private String getNextTwoWeekQid(Integer type,String memberId){
        //??????????????????????????????type,sort??????
        List<DailyQuestionPO> dailyQuestionPOS = dailyQuestionMapper.queryAllByCodeExcludeMember(DailyQuestionConstant.DAILY_QUES_GROUP_CODE_TWO_WEEK,memberId);
        String qid = getNextTypeQid(type, dailyQuestionPOS);
        return qid;
    }

    //??????????????????????????????qid
    private String getNextCommonTypeQid(Integer type,String memberId){
        String qid = null;//?????????????????????
        List<String> list = dailyQuestionMapper.queryAllCommonByMemberId(memberId);
        List<DailyQuestionPO> dailyQuestionPOS = dailyQuestionMapper.queryAllCommonExcludeMember(memberId);
        //??????????????????
        if (list == null || list.size() == 0){
            qid = dailyQuestionPOS.get(0).getSid();
        }else {
            qid = getNextTypeQid(type,dailyQuestionPOS);
        }
        return qid;
    }

    private String getNextTypeQid(Integer type,List<DailyQuestionPO> list){
        String qid = null;
        if (list != null && list.size()>0){
            for (int i=0;i<list.size();i++){
                DailyQuestionPO dqPO = list.get(i);
                //???????????????type?????????????????????sort???????????????
                if (dqPO.getType() > type){
                    qid = dqPO.getSid();
                    break;
                }
            }
            //?????????????????????type????????????????????????
            if (StringUtils.isBlank(qid)){
                qid = list.get(0).getSid();
            }
        }
        return qid;
    }

    //???????????????????????????????????????
    private String getNextDoneQid(List<MemberDailyQuestionRecordPO> list,String currentQid){//??????type?????????Qid
        String qid = null;
        if (list !=null &&list.size()>0){
             qid = list.get(0).getQid();
            if (list != null && list.size()>0){
                for (int i=0;i<list.size();i++){
                    MemberDailyQuestionRecordPO memberDailyQuestionRecordPO = list.get(i);
                    //??????????????????????????????
                    if (memberDailyQuestionRecordPO.getQid().equals(currentQid)){
                        if (i == list.size()-1){
                            break;
                        }else {
                            qid = list.get(i+1).getSid();
                            break;
                        }
                    }
                }
            }
        }
        return qid;
    }


    //???????????????????????????type??????
    private List<Integer> getDQTypeByTagList(List<String> tags){
        Set<Integer> set = new HashSet<>();
        for (String tag : tags) {
            if (tag.contains("13-")){//13-1?????????13-2????????????????????????13-3????????????????????????
                set.add(7);//?????????
            }else if (tag.contains("12-")){//????????????
                set.add(1);//?????????
            }else if (tag.contains("11-1")){//??????
                set.add(5);//??????
            }else if (tag.contains("11-2")){//???????????????
                set.add(6);//??????
            }else if (tag.contains("10-")){//10-1??????????????? 10-2????????????10-3?????????????????????10-4??????????????????10-5
                if (tag.contains("10-1")){//10-1??????????????????
                    set.add(6);//??????
                }
                set.add(2);//??????
            }else if (tag.contains("9-")){//??????????????????
                set.add(2);//??????
            }else if (tag.contains("8-")){//????????????
                set.add(2);//??????
            }else if (tag.contains("7-")){//??????
                set.add(3);//??????
            }else if (tag.contains("6-")){//6-1????????????6-2?????????6-3?????????6-4????????????6-5????????????
                set.add(1);//?????????
                set.add(4);//??????
                set.add(2);//??????
            }else if (tag.contains("5-")){//????????????
                set.add(2);//??????
            }else if (tag.contains("4-")){//????????????
                set.add(5);//??????
            }else if (tag.contains("3-")){//??????
                set.add(5);//??????
                set.add(6);//??????
                set.add(3);//??????
            }else if (tag.contains("2-")){//??????
                set.add(3);//??????
            }else if (tag.contains("1-")){//??????
                set.add(3);//??????
            }
        }
        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        return list;
    }


    @Override
    @Transactional
    public String submitMemberDailyQuestion(SubmitQuestionDTO submitQuestionDTO) {
        MemberDailyQuestionRecordPO recordPO = new MemberDailyQuestionRecordPO();
        DailyQuestionBO pushQues = dailyQuestionPushMapper.queryByMemberId(submitQuestionDTO.getMemberId());
        String sid = DaoHelper.getSeq();
        recordPO.setSid(sid);
        recordPO.setQid(submitQuestionDTO.getQid());
        recordPO.setAnswer(submitQuestionDTO.getAnswer());
        recordPO.setMemberId(submitQuestionDTO.getMemberId());
        recordPO.setIsCorrect(Integer.valueOf(submitQuestionDTO.getIsCorrect()));
        recordPO.setPushId(pushQues.getPushId());
        //????????????????????????
        if (memberDailyQuestionRecordMapper.insert(recordPO) > 0){
            //??????????????????????????????
            DailyQuestionPushPO dailyQuestionPushPO = new DailyQuestionPushPO();
            dailyQuestionPushPO.setSid(pushQues.getPushId());
            dailyQuestionPushPO.setIsSubmit(1);
            dailyQuestionPushMapper.update(dailyQuestionPushPO);
        }
        return sid;
    }

    @Override
    public Map<String, Object> loadDailyQuestionResultById(String sid) {
        DailyQuestionBO dailyQuestionBO = memberDailyQuestionRecordMapper.loadQuesPOById(sid);
        if (null == dailyQuestionBO){
            throw new BusinessException("?????????????????????");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("title",dailyQuestionBO.getTitle());
        map.put("analyse",dailyQuestionBO.getAnalyse());
        map.put("result",dailyQuestionBO.getQuesType().equals(3)?"??????????????????"
                :dailyQuestionBO.getIsCorrect() == 1?"??????????????????":"?????????????????????");
        return map;
    }

    @Override
    public Map<String, Object> loadDailyQuestionRecord(String memberId) {
        HashMap<String, Object> map = new HashMap<>();
        List<MemberDailyQuestionRecordPO> list = memberDailyQuestionRecordMapper.queryRecordByMemberId(memberId);
        List<MemberDailyQuestionRecordPO> recordPOS = memberDailyQuestionRecordMapper.queryByMemberId(memberId);
        //?????????
        List<MemberDailyQuestionRecordPO> rightList = recordPOS.stream().
                filter(e -> e.getIsCorrect().equals(1)).collect(Collectors.toList());
        List<String> dtCollect = recordPOS.stream().map(MemberDailyQuestionRecordPO::getInsertDt).collect(Collectors.toList());
        String days = "";
        try {
            days = String.valueOf(getContinuousSignInDay(dtCollect));
        } catch (Exception e) {
            logger.error("????????????????????????",e);
            days = "0";
        }
        map.put("list",list);
        map.put("inDays",days);//??????????????????
        map.put("total",recordPOS.size());
        map.put("right",rightList.size());
        map.put("error",recordPOS.size()-rightList.size());
        return map;
    }

    //??????????????????
    private int getContinuousSignInDay(List<String> signInList) {
        int continuousDay = 1;//????????????
        boolean todaySignIn = false;
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todays = sdf.format(today);
        if (signInList.size()>0){
            for (int i = 0; i < signInList.size(); i++) {
                int intervalDay = 0;
                try {
                    Date parse = sdf.parse(todays);
                    String s = signInList.get(i);
                    Date parse1 = sdf.parse(s);
                    intervalDay = distanceDay(parse, parse1);
                } catch (ParseException e) {
                    logger.error("????????????????????????");
                    intervalDay = 0;
                }
                //????????????
                if (intervalDay == 0 && i == 0) {
                    todaySignIn = true;
                }else if (intervalDay == continuousDay) {
                    continuousDay++;
                }else {
                    //????????????????????????
                    break;
                }
            }
        }
        if (!todaySignIn) {
            continuousDay--;
        }
        return continuousDay;
    }


    //?????????????????? ?????????????????????????????????
    private static int distanceDay(Date largeDay, Date smallDay) {
        int day = (int) ((largeDay.getTime() - smallDay.getTime()) / (1000 * 60 * 60 * 24));
        return day;
    }
}
