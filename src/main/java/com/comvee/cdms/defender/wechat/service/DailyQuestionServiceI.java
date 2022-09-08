package com.comvee.cdms.defender.wechat.service;

import com.comvee.cdms.defender.wechat.bo.DailyQuestionBO;
import com.comvee.cdms.defender.wechat.dto.SubmitQuestionDTO;
import com.comvee.cdms.defender.wechat.po.DailyQuestionPO;
import com.comvee.cdms.defender.wechat.vo.DailyQuestionVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;

import java.util.Map;

/**
 * @Author linr
 * @Date 2021/11/26
 */
public interface DailyQuestionServiceI {

    /**
     * 通过ID获取每日一答题目信息
     *
     * @param sid 主键
     * @return 实例对象
     */
    DailyQuestionVO loadDailyQuestionById(String sid);


    /**
     * 新增数据每日一答题库
     *
     * @param dailyQuestionPO 实例对象
     * @return
     */
    void addDailyQuestion(DailyQuestionPO dailyQuestionPO);


    /**
     * 修改数据每日一答题库
     *
     * @param dailyQuestionPO 实例对象
     * @return
     */
    void updatDailyQuestion(DailyQuestionPO dailyQuestionPO);

    /**
     * 删除题库
     * @param sid
     */
    void delDailyQuestionPO(String sid);

    /**
     * 题库列表
     * @param page
     * @return
     */
    PageResult<DailyQuestionVO> listDailyQuestionPO(PageRequest page);




    /**
     * 生成每日一答推送定时器处理
     * @param memberId
     */
    String dailyQuestionGenerate(String memberId);

    /**
     * 用户获取每日一答
     * @param memberId
     * @return
     */
    DailyQuestionBO loadDailyQuestion(String memberId);


    /**
     * 提交每日一答
     * @param submitQuestionDTO
     */
    String submitMemberDailyQuestion(SubmitQuestionDTO submitQuestionDTO);

    /**
     * 获取每日一答结果
     * @param sid
     * @return
     */
    Map<String,Object> loadDailyQuestionResultById(String sid);


    /**
     * 获取每日一答记录页信息
     * @param memberId
     * @return
     */
    Map<String,Object> loadDailyQuestionRecord(String memberId);






}
