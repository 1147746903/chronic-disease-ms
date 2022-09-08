package com.comvee.cdms.education.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.education.constant.EducationCourseConstant;
import com.comvee.cdms.education.mapper.*;
import com.comvee.cdms.education.model.dto.AddEduCourseCommentDTO;
import com.comvee.cdms.education.model.dto.AddEduCourseDTO;
import com.comvee.cdms.education.model.dto.EduCourseDoctorDTO;
import com.comvee.cdms.education.model.dto.ListEduCourseDTO;
import com.comvee.cdms.education.model.po.*;
import com.comvee.cdms.education.model.vo.DetailEduCourseVO;
import com.comvee.cdms.education.model.vo.ListEduCommentVO;
import com.comvee.cdms.education.model.vo.ListEduCourseVO;
import com.comvee.cdms.education.service.EducationCourseServiceI;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author linr
 * @Date 2022/1/27
 */
@Service
public class EducationCourseServiceImpl implements EducationCourseServiceI {

    private final static Logger logger = LoggerFactory.getLogger(EducationCourseServiceImpl.class);

    @Autowired
    private EduCourseMapper eduCourseMapper;

    @Autowired
    private EduDoctorCollectMapper eduDoctorCollectMapper;

    @Autowired
    private EduViewHistoryMapper eduViewHistoryMapper;

    @Autowired
    private EduCourseClassMapper eduCourseClassMapper;

    @Autowired
    private EduCourseCommentMapper eduCourseCommentMapper;


    @Override
    public PageResult<EduCourseClassPO> loadEducationCourseClass(String className, PageRequest page) {
        EduCourseClassPO getEduCourseClassPO = new EduCourseClassPO();
        getEduCourseClassPO.setIsValid(1);
        getEduCourseClassPO.setClassName(className);
        PageHelper.startPage(page.getPage(),page.getRows());
        List<EduCourseClassPO> classPOList = eduCourseClassMapper.queryAll(getEduCourseClassPO);
        return new PageResult<>(classPOList);
    }

    @Override
    @Transactional
    public void addEducationCourseClass(EduCourseClassPO eduCourseClassPO) {
        eduCourseClassPO.setSid(DaoHelper.getSeq());
        eduCourseClassMapper.insert(eduCourseClassPO) ;
    }

    @Override
    @Transactional
    public void modifyEducationCourseClass(EduCourseClassPO eduCourseClassPO) {
        EduCourseClassPO getEduCourseClassPO = new EduCourseClassPO();
        getEduCourseClassPO.setSid(eduCourseClassPO.getSid());
        List<EduCourseClassPO> classPOList = eduCourseClassMapper.queryAll(getEduCourseClassPO);
        if (classPOList.size() == 0){
            throw new BusinessException("该分类不存在");
        }
        eduCourseClassMapper.update(eduCourseClassPO);
    }

    @Override
    @Transactional
    public void delEducationCourseClass(String classId,String operatorId) {
        EduCourseClassPO getEduCourseClassPO = new EduCourseClassPO();
        getEduCourseClassPO.setSid(classId);
        List<EduCourseClassPO> classPOList = eduCourseClassMapper.queryAll(getEduCourseClassPO);
        if (classPOList.size() == 0){
            throw new BusinessException("该分类不存在");
        }
        EduCoursePO getEduCoursePO = new EduCoursePO();
        getEduCoursePO.setClassId(classId);
        getEduCoursePO.setCourseStatus(EducationCourseConstant.EDUCATION_COURSE_STATUS_ACTIVE);
        List<EduCoursePO> eduCoursePOS = eduCourseMapper.queryAll(getEduCoursePO);
        if (eduCoursePOS.size()>0){
            throw new BusinessException("该分类存在启用的课程");
        }
        EduCourseClassPO eduCourseClassPO = new EduCourseClassPO();
        eduCourseClassPO.setSid(classId);
        eduCourseClassPO.setIsValid(0);
        eduCourseClassPO.setOperatorId(operatorId);
        eduCourseClassMapper.update(eduCourseClassPO);
    }


    @Override
    public PageResult<ListEduCourseVO> loadEducationCourseList(ListEduCourseDTO listEduCourseDTO, PageRequest page) {

        if (StringUtils.isBlank(listEduCourseDTO.getTimeOrder()) &&
                StringUtils.isBlank(listEduCourseDTO.getScoreOrder()) &&
                StringUtils.isBlank(listEduCourseDTO.getViewOrder())){
            listEduCourseDTO.setTimeOrder("desc");
        }
        PageHelper.startPage(page.getPage(),page.getRows());
        List<ListEduCourseVO> eduCourseList = eduCourseMapper.listEduCourseVO(listEduCourseDTO);
        return new PageResult<>(eduCourseList);
    }

    @Override
    public List<ListEduCourseVO> loadDoctorViewHistory(String doctorId) {
        List<ListEduCourseVO> historyList = eduViewHistoryMapper.loadDoctorViewHistory(doctorId);
        return historyList;
    }

    @Override
    public List<ListEduCourseVO> loadDoctorCollect(String doctorId) {
        List<ListEduCourseVO> collectList = eduDoctorCollectMapper.loadDoctorCollect(doctorId);
        return collectList;
    }

    @Override
    @Transactional
    public void addDoctorCollect(EduCourseDoctorDTO eduCourseDoctorDTO) {
        EduDoctorCollectPO eduDoctorCollectPO = new EduDoctorCollectPO();
        eduDoctorCollectPO.setDoctorId(eduCourseDoctorDTO.getDoctorId());
        eduDoctorCollectPO.setCourseId(eduCourseDoctorDTO.getCourseId());
        List<EduDoctorCollectPO> collectPOList = eduDoctorCollectMapper.queryAll(eduDoctorCollectPO);
        if (collectPOList.size()>0){
            throw new BusinessException("您已经收藏过该课程了");
        }
        eduDoctorCollectPO.setSid(DaoHelper.getSeq());
        eduDoctorCollectPO.setOrigin(eduCourseDoctorDTO.getOrigin());
        eduDoctorCollectMapper.insert(eduDoctorCollectPO);
    }

    @Override
    @Transactional
    public void delDoctorCollect(EduCourseDoctorDTO eduCourseDoctorDTO) {
        EduDoctorCollectPO getCollectPO = new EduDoctorCollectPO();
        getCollectPO.setDoctorId(eduCourseDoctorDTO.getDoctorId());
        getCollectPO.setCourseId(eduCourseDoctorDTO.getCourseId());
        List<EduDoctorCollectPO> collectPOList = eduDoctorCollectMapper.queryAll(getCollectPO);
        if (collectPOList.size() == 0){
            throw new BusinessException("您没有收藏该课程，请确认");
        }
        EduDoctorCollectPO delCollect = collectPOList.get(0);
        eduDoctorCollectMapper.deleteById(delCollect.getSid());
    }

    @Override
    public DetailEduCourseVO loadCourseDetail(EduCourseDoctorDTO eduCourseDoctorDTO) {
        String courseId = eduCourseDoctorDTO.getCourseId();
        DetailEduCourseVO detailEduCourseVO = getDetailEduCourse(courseId);
        detailEduCourseVO.setVedioUrl(null);
        detailEduCourseVO.setArticleContent(null);
        //获取是否收藏
        setCourseDoctorCollect(eduCourseDoctorDTO,detailEduCourseVO);
        return detailEduCourseVO;
    }

    @Override
    public DetailEduCourseVO loadCourseDetailBack(String courseId) {
        DetailEduCourseVO detailEduCourseVO = getDetailEduCourse(courseId);
        return detailEduCourseVO;
    }

    private DetailEduCourseVO getDetailEduCourse(String courseId){
        DetailEduCourseVO detailEduCourseVO = eduCourseMapper.loadEduCourseDetail(courseId);
        if (null == detailEduCourseVO){
            throw new BusinessException("课程不存在");
        }
        return detailEduCourseVO;
    }

    @Override
    @Transactional
    public DetailEduCourseVO learnCourse(EduCourseDoctorDTO eduCourseDoctorDTO) {
        String courseId = eduCourseDoctorDTO.getCourseId();
        DetailEduCourseVO detailEduCourseVO = eduCourseMapper.loadEduCourseDetail(courseId);
        //文章类型直接处理数据
        Integer courseType = detailEduCourseVO.getCourseType();
        if (null != courseType && courseType.equals(EducationCourseConstant.EDUCATION_COURSE_TYPE_ARTICLE)){
            learnCourseDataHandler(eduCourseDoctorDTO);
        }
        //获取是否收藏
        setCourseDoctorCollect(eduCourseDoctorDTO,detailEduCourseVO);
        return detailEduCourseVO;
    }

    private void learnCourseDataHandler(EduCourseDoctorDTO eduCourseDoctorDTO){
        EduCoursePO eduCoursePO = courseExistCheck(eduCourseDoctorDTO.getCourseId());
        Integer viewNum = Integer.valueOf(eduCoursePO.getViewNum());//观看次数
        Integer peopleNum = Integer.valueOf(eduCoursePO.getPeopleNum());//观看人数

        EduViewHistoryPO eduViewHistoryPO = new EduViewHistoryPO();
        eduViewHistoryPO.setCourseId(eduCourseDoctorDTO.getCourseId());
        eduViewHistoryPO.setDoctorId(eduCourseDoctorDTO.getDoctorId());
        List<EduViewHistoryPO> viewHis = eduViewHistoryMapper.queryAll(eduViewHistoryPO);
        if (viewHis.size() == 0){
            eduCoursePO.setPeopleNum(String.valueOf(peopleNum+1));
        }
        eduCoursePO.setViewNum(String.valueOf(viewNum+1));
        eduCourseMapper.update(eduCoursePO);

        eduViewHistoryPO.setOrigin(eduCourseDoctorDTO.getOrigin());
        eduViewHistoryPO.setSid(DaoHelper.getSeq());
        eduViewHistoryMapper.insert(eduViewHistoryPO);

    }


    @Override
    @Transactional
    public void publishComment(AddEduCourseCommentDTO addEduCourseCommentDTO) {
        EduCoursePO eduCoursePO = courseExistCheck(addEduCourseCommentDTO.getCourseId());
        Integer star = addEduCourseCommentDTO.getStar();
        Integer score = star * 20;
        Integer lever = courseCommentHandler(score);
        EduCourseCommentPO eduCourseCommentPO = new EduCourseCommentPO();
        BeanUtils.copyProperties(eduCourseCommentPO,addEduCourseCommentDTO);
        eduCourseCommentPO.setScore(String.valueOf(score));
        eduCourseCommentPO.setLever(lever);
        eduCourseCommentPO.setSid(DaoHelper.getSeq());
        eduCourseCommentPO.setIsShow(EducationCourseConstant.EDUCATION_COURSE_COMMENT_SHOW);
        eduCourseCommentMapper.insert(eduCourseCommentPO);
        courseCommentDataHandler(eduCoursePO);
    }


    private Integer courseCommentHandler(Integer score){
        Integer lever = EducationCourseConstant.EDUCATION_COMMENT_LEVER_PRAISE;
        if (score <= 40){
            lever = EducationCourseConstant.EDUCATION_COMMENT_LEVER_NEGATIVE;
        }else if (score == 60){
            lever = EducationCourseConstant.EDUCATION_COMMENT_LEVER_MID;
        }
        return lever;
    }

    @Override
    @Transactional
    public void modifyComment(EduCourseCommentPO eduCourseCommentPO){
        EduCourseCommentPO commentPO = eduCourseCommentMapper.queryById(eduCourseCommentPO.getSid());
        if (null == commentPO){
            throw new BusinessException("评论不存在");
        }
        EduCoursePO getEduCoursePO = new EduCoursePO();
        getEduCoursePO.setSid(commentPO.getCourseId());
        List<EduCoursePO> eduCoursePOS = eduCourseMapper.queryAll(getEduCoursePO);
        if (eduCoursePOS.size() == 0){
            throw new BusinessException("课程不存在");
        }
        eduCourseCommentMapper.update(eduCourseCommentPO);
        Integer valid = eduCourseCommentPO.getIsValid();
        Integer isShow = eduCourseCommentPO.getIsShow();
        if (null != valid  || null != isShow ){
            //删除评论处理课程评分
            courseCommentDataHandler(eduCoursePOS.get(0));
        }
    }

    @Override
    @Transactional
    public void addCourse(AddEduCourseDTO addEduCourseDTO) {
        String courseId = DaoHelper.getSeq();
        String courseNo = "";
        try {
            courseNo = "YJ"+courseId.substring(courseId.length() - 9);
        }catch (Exception e){
            throw new BusinessException("生成课程编号失败");
        }
        EduCoursePO eduCoursePO = new EduCoursePO();
        BeanUtils.copyProperties(eduCoursePO,addEduCourseDTO);
        eduCoursePO.setSid(courseId);
        eduCoursePO.setCourseNo(courseNo);
        eduCoursePO.setScore("0");
        eduCoursePO.setPraise("0%");
        eduCoursePO.setViewNum("0");
        eduCoursePO.setPeopleNum("0");
        eduCourseMapper.insert(eduCoursePO);
    }

    @Override
    @Transactional
    public void modifyCourse(AddEduCourseDTO addEduCourseDTO) {
        courseExistCheck(addEduCourseDTO.getSid());
        EduCoursePO eduCoursePO = new EduCoursePO();
        BeanUtils.copyProperties(eduCoursePO,addEduCourseDTO);
        eduCourseMapper.updateCourse(eduCoursePO);
    }

    @Override
    @Transactional
    public void delCourse(String courseId) {
        courseExistCheck(courseId);
        EduCoursePO eduCoursePO = new EduCoursePO();
        eduCoursePO.setIsValid(0);
        eduCoursePO.setSid(courseId);
        eduCourseMapper.update(eduCoursePO);
    }

    @Override
    public void modifyCourseStatus(String courseId, Integer status) {
        courseExistCheck(courseId);
        EduCoursePO eduCoursePO = new EduCoursePO();
        eduCoursePO.setCourseStatus(status);
        eduCoursePO.setSid(courseId);
        eduCourseMapper.update(eduCoursePO);
    }

    @Override
    public PageResult<ListEduCommentVO> loadCourseCommentList(PageRequest page, String courseId,Integer lever,Boolean showAnoy) {
        EduCourseCommentPO getCommentPO = new EduCourseCommentPO();
        getCommentPO.setCourseId(courseId);
        getCommentPO.setIsValid(1);
        getCommentPO.setLever(lever);
        PageHelper.startPage(page.getPage(),page.getRows());
        List<ListEduCommentVO> commentList;
        if(null != showAnoy && showAnoy == true){
            //匿名也展示，用于后台
            commentList = eduCourseCommentMapper.loadEduCommentByCourseId(courseId,lever);
        }else {
            getCommentPO.setIsShow(EducationCourseConstant.EDUCATION_COURSE_COMMENT_SHOW);
            commentList = eduCourseCommentMapper.loadEduCommentVOList(getCommentPO);
        }
        return new PageResult<>(commentList);
    }

    @Override
    public List<ListEduCourseVO> recommendCourseList(String courseId) {
        EduCoursePO eduCoursePO = courseExistCheck(courseId);
        List<ListEduCourseVO> listEduCourseVOS = eduCourseMapper.listRecEduCourseVO(courseId, eduCoursePO.getClassId());
        return listEduCourseVOS;
    }

    @Override
    public void playVedioCourse(EduCourseDoctorDTO eduCourseDoctorDTO) {
        learnCourseDataHandler(eduCourseDoctorDTO);
    }

    //课程主表评论相关数据处理
    private void courseCommentDataHandler(EduCoursePO eduCoursePO){
        EduCourseCommentPO getCourseCommentPO = new EduCourseCommentPO();
        getCourseCommentPO.setCourseId(eduCoursePO.getSid());
        getCourseCommentPO.setIsValid(1);
        getCourseCommentPO.setIsShow(1);
        List<EduCourseCommentPO> commentList = eduCourseCommentMapper.queryAll(getCourseCommentPO);
        if (commentList.size()>0){
            long sumScore = commentList.stream().mapToInt(eduCourseCommentPO -> Integer.valueOf(eduCourseCommentPO.getScore())).summaryStatistics().getSum();
            List<EduCourseCommentPO> praiseList = commentList.stream().filter(e -> e.getLever().equals(1)).collect(Collectors.toList());
            Integer totalNum = commentList.size();
            double courseSc = (double) sumScore/ (double)totalNum;
            double coursePr = (double) praiseList.size()/ (double)totalNum;
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(1);//小数点后1位
            String courseScore = numberFormat.format(courseSc);
            String coursePraise = numberFormat.format(coursePr*100)+"%";
            eduCoursePO.setScore(courseScore);
            eduCoursePO.setPraise(coursePraise);
            eduCourseMapper.update(eduCoursePO);
        }else {
            //没有评论为0则置空
            eduCoursePO.setScore("--");
            eduCoursePO.setPraise("--");
            eduCourseMapper.update(eduCoursePO);
        }
    }


    private void setCourseDoctorCollect(EduCourseDoctorDTO eduCourseDoctorDTO,DetailEduCourseVO detailEduCourseVO){
        EduDoctorCollectPO getCollectPO = new EduDoctorCollectPO();
        getCollectPO.setDoctorId(eduCourseDoctorDTO.getDoctorId());
        getCollectPO.setCourseId(eduCourseDoctorDTO.getCourseId());
        getCollectPO.setIsValid(1);
        List<EduDoctorCollectPO> collectPOList = eduDoctorCollectMapper.queryAll(getCollectPO);
        detailEduCourseVO.setIsCollect(EducationCourseConstant.EDUCATION_COURSE_COLLECT_NO);
        if (collectPOList.size()>0){
            detailEduCourseVO.setIsCollect(EducationCourseConstant.EDUCATION_COURSE_COLLECT_YES);
        }

    }

    private EduCoursePO courseExistCheck(String courseId){
        if (StringUtils.isBlank(courseId)){
            throw new BusinessException("课程Id不允许为空");
        }
        EduCoursePO getEduCoursePO = new EduCoursePO();
        getEduCoursePO.setSid(courseId);
        List<EduCoursePO> eduCoursePOS = eduCourseMapper.queryAll(getEduCoursePO);
        if (eduCoursePOS.size() == 0){
            throw new BusinessException("课程不存在");
        }
        return eduCoursePOS.get(0);
    }
}
