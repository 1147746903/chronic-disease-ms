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
            //生成每日一答题目
            String sid = memberDailyQuestionGenerate(memberId);
            DailyQuestionPO dailyQuestionPO = dailyQuestionMapper.queryById(sid);
            dailyQuestionBO = new DailyQuestionBO();
            BeanUtils.copyProperties(dailyQuestionBO,dailyQuestionPO);
            dailyQuestionBO.setIsSubmit(0);
        }
        return dailyQuestionBO;
    }

    //处理单人每日一答生成逻辑
    private String memberDailyQuestionGenerate(String memberId){
        DailyQuestionPushPO addPushPO = new DailyQuestionPushPO();
        addPushPO.setSid(DaoHelper.getSeq());
        addPushPO.setMemberId(memberId);
        addPushPO.setPushDt(DateHelper.getToday());
        addPushPO.setIsSubmit(0);
        List<DailyQuestionBO> pushList = dailyQuestionPushMapper.listByMemberId(memberId);//推送历史list
        //未推送过，推第一条
        if (pushList.size() == 0){
            firstDailyQuesPush(addPushPO);
        }else {
            //推送过，按照逻辑推送
            DailyQuestionBO pushQues = pushList.get(0);//最新的一条推送
            Integer type = pushQues.getType();
            String nextTwoWeekTypeQid = getNextTwoWeekQid(type, memberId);
            if (!StringUtils.isBlank(nextTwoWeekTypeQid)){
                //存在两周内
                addPushPO.setStage(DailyQuestionConstant.DAILY_QUES_PUSH_STAGE_1);
                addPushPO.setQid(nextTwoWeekTypeQid);
            }else {
                //两周内的推完了，则推送标签版
                //获取用户标签
                List<String> memberTags = prescriptionOfEduServiceI.getMemberTags(memberId);
                List<Integer> tagTypes = getDQTypeByTagList(memberTags);//标签对应type
                logger.info("每日一答推送患者"+memberId+"标签"+memberTags+"题库标签列"+tagTypes);
                //用户有标签则根据标签匹配
                if (null != memberTags && memberTags.size() > 0){
                    String nextTwoTypeType = getNextTwoTypeQid(tagTypes, type, memberId);
                    if (!StringUtils.isBlank(nextTwoTypeType)){
                        addPushPO.setStage(DailyQuestionConstant.DAILY_QUES_PUSH_STAGE_1);
                        addPushPO.setQid(nextTwoTypeType);
                    }else {
                        //标签推完了推通用
                        stageLeftHandler(type,memberId,addPushPO,pushQues);
                    }
                }else {
                    //用户没标签推通用
                    stageLeftHandler(type,memberId,addPushPO,pushQues);
                }
            }
        }
        if (StringUtils.isBlank(addPushPO.getQid())){
            logger.info("用户"+memberId+"没有可继续推送的每日一答题目...");
            return "-1";
        }
        dailyQuestionPushMapper.insert(addPushPO);
        return addPushPO.getQid();
    }


    //第一次推送
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


    //剩下的通用题目推送处理
    private void stageLeftHandler(Integer type,String memberId,DailyQuestionPushPO addPushPO,DailyQuestionBO pushQues){
        String nextCommonType = getNextCommonTypeQid(type,memberId);
        if (!StringUtils.isBlank(nextCommonType)){
            addPushPO.setStage(DailyQuestionConstant.DAILY_QUES_PUSH_STAGE_1);
            addPushPO.setQid(nextCommonType);
        }else {
            //错题集和做过的题目
            stageTwoThreeHandler(addPushPO,memberId,pushQues);
        }
    }

    //错题集和做过的题目循环
    private void stageTwoThreeHandler(DailyQuestionPushPO addPushPO,String memberId,DailyQuestionBO pushQues){
        //获取下次推送的错题
        MemberDailyQuestionRecordPO errorQues = memberDailyQuestionRecordMapper.queryErrorsExcludeMember(memberId);
        if (null != errorQues){
            addPushPO.setStage(DailyQuestionConstant.DAILY_QUES_PUSH_STAGE_2);
            addPushPO.setQid(errorQues.getQid());
        }else {
            //错题全推完了，则推做过的题目循环
            List<MemberDailyQuestionRecordPO> doneList = memberDailyQuestionRecordMapper.queryDoneExcludeMember(memberId);
            String nextDoneQid = getNextDoneQid(doneList, pushQues.getSid());
            addPushPO.setStage(DailyQuestionConstant.DAILY_QUES_PUSH_STAGE_3);
            addPushPO.setQid(nextDoneQid);
        }
    }

    //获取标签轮次的下一个qid
    private String getNextTwoTypeQid(List<Integer> list,Integer type,String memberId){
        List<DailyQuestionPO> dailyQuestionPOS = dailyQuestionMapper.queryAllByTwoTypeExcludeMember(list,memberId);
        String qid = getNextTypeQid(type, dailyQuestionPOS);
        return qid;
    }

    //获取两周内轮次的下一个qid
    private String getNextTwoWeekQid(Integer type,String memberId){
        //除已推送过的问题列表type,sort排序
        List<DailyQuestionPO> dailyQuestionPOS = dailyQuestionMapper.queryAllByCodeExcludeMember(DailyQuestionConstant.DAILY_QUES_GROUP_CODE_TWO_WEEK,memberId);
        String qid = getNextTypeQid(type, dailyQuestionPOS);
        return qid;
    }

    //获取通用轮次的下一个qid
    private String getNextCommonTypeQid(Integer type,String memberId){
        String qid = null;//没有通用轮次了
        List<String> list = dailyQuestionMapper.queryAllCommonByMemberId(memberId);
        List<DailyQuestionPO> dailyQuestionPOS = dailyQuestionMapper.queryAllCommonExcludeMember(memberId);
        //第一次推通用
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
                //获取下一个type的未推送过到的sort排序第一个
                if (dqPO.getType() > type){
                    qid = dqPO.getSid();
                    break;
                }
            }
            //不存在有下一个type列则取列表第一个
            if (StringUtils.isBlank(qid)){
                qid = list.get(0).getSid();
            }
        }
        return qid;
    }

    //循环获取做过的题目的下一个
    private String getNextDoneQid(List<MemberDailyQuestionRecordPO> list,String currentQid){//当前type，当前Qid
        String qid = null;
        if (list !=null &&list.size()>0){
             qid = list.get(0).getQid();
            if (list != null && list.size()>0){
                for (int i=0;i<list.size();i++){
                    MemberDailyQuestionRecordPO memberDailyQuestionRecordPO = list.get(i);
                    //存在有下一个列的通用
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


    //根据标签生成对应的type列表
    private List<Integer> getDQTypeByTagList(List<String> tags){
        Set<Integer> set = new HashSet<>();
        for (String tag : tags) {
            if (tag.contains("13-")){//13-1胰岛素13-2使用胰岛素未达标13-3女性胰岛素使用者
                set.add(7);//胰岛素
            }else if (tag.contains("12-")){//眼底病变
                set.add(1);//并发症
            }else if (tag.contains("11-1")){//喝酒
                set.add(5);//饮食
            }else if (tag.contains("11-2")){//有运动习惯
                set.add(6);//运动
            }else if (tag.contains("10-")){//10-1餐后高血糖 10-2血糖正常10-3正确使用血糖仪10-4正确监测流程10-5
                if (tag.contains("10-1")){//10-1运动认识不足
                    set.add(6);//运动
                }
                set.add(2);//监测
            }else if (tag.contains("9-")){//糖化血红蛋白
                set.add(2);//监测
            }else if (tag.contains("8-")){//监测频率
                set.add(2);//监测
            }else if (tag.contains("7-")){//病程
                set.add(3);//其他
            }else if (tag.contains("6-")){//6-1无并发症6-2高血压6-3高血脂6-4病足预防6-5少做检查
                set.add(1);//并发症
                set.add(4);//药物
                set.add(2);//监测
            }else if (tag.contains("5-")){//血糖水平
                set.add(2);//监测
            }else if (tag.contains("4-")){//饮食习惯
                set.add(5);//饮食
            }else if (tag.contains("3-")){//身材
                set.add(5);//饮食
                set.add(6);//运动
                set.add(3);//其他
            }else if (tag.contains("2-")){//性别
                set.add(3);//其他
            }else if (tag.contains("1-")){//年龄
                set.add(3);//其他
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
        //每日一答结果入库
        if (memberDailyQuestionRecordMapper.insert(recordPO) > 0){
            //修改推送为已完成提交
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
            throw new BusinessException("答题记录不存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("title",dailyQuestionBO.getTitle());
        map.put("analyse",dailyQuestionBO.getAnalyse());
        map.put("result",dailyQuestionBO.getQuesType().equals(3)?"感谢您的回答"
                :dailyQuestionBO.getIsCorrect() == 1?"恭喜您答对啦":"很遗憾您答错了");
        return map;
    }

    @Override
    public Map<String, Object> loadDailyQuestionRecord(String memberId) {
        HashMap<String, Object> map = new HashMap<>();
        List<MemberDailyQuestionRecordPO> list = memberDailyQuestionRecordMapper.queryRecordByMemberId(memberId);
        List<MemberDailyQuestionRecordPO> recordPOS = memberDailyQuestionRecordMapper.queryByMemberId(memberId);
        //正确的
        List<MemberDailyQuestionRecordPO> rightList = recordPOS.stream().
                filter(e -> e.getIsCorrect().equals(1)).collect(Collectors.toList());
        List<String> dtCollect = recordPOS.stream().map(MemberDailyQuestionRecordPO::getInsertDt).collect(Collectors.toList());
        String days = "";
        try {
            days = String.valueOf(getContinuousSignInDay(dtCollect));
        } catch (Exception e) {
            logger.error("计算连续答题异常",e);
            days = "0";
        }
        map.put("list",list);
        map.put("inDays",days);//连续答题天数
        map.put("total",recordPOS.size());
        map.put("right",rightList.size());
        map.put("error",recordPOS.size()-rightList.size());
        return map;
    }

    //计算连续天数
    private int getContinuousSignInDay(List<String> signInList) {
        int continuousDay = 1;//连续天数
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
                    logger.error("日期类型转换错误");
                    intervalDay = 0;
                }
                //当天签到
                if (intervalDay == 0 && i == 0) {
                    todaySignIn = true;
                }else if (intervalDay == continuousDay) {
                    continuousDay++;
                }else {
                    //不连续，终止判断
                    break;
                }
            }
        }
        if (!todaySignIn) {
            continuousDay--;
        }
        return continuousDay;
    }


    //判断当天日期 与以往签到日期相隔天数
    private static int distanceDay(Date largeDay, Date smallDay) {
        int day = (int) ((largeDay.getTime() - smallDay.getTime()) / (1000 * 60 * 60 * 24));
        return day;
    }
}
