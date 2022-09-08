package com.comvee.cdms.defender.wechat.mapper;

import com.comvee.cdms.defender.wechat.bo.DailyQuestionBO;
import com.comvee.cdms.defender.wechat.po.DailyQuestionPushPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 每日一答用户推送表(DailyQuestionPushPO)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-29 10:50:36
 */
public interface DailyQuestionPushMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    DailyQuestionPushPO queryById(String sid);

    //获取用户最新一条每日一答题目
    DailyQuestionBO queryByMemberId(@Param("memberId") String memberId);

    List<DailyQuestionBO> listByMemberId(@Param("memberId") String memberId);

    List<DailyQuestionBO> listByQuesPO(Map params);

    List<String> listAllPushMembers();

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DailyQuestionPushPO> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dailyQuestionPushPO 实例对象
     * @return 对象列表
     */
    List<DailyQuestionPushPO> queryAll(DailyQuestionPushPO dailyQuestionPushPO);

    /**
     * 新增数据
     *
     * @param dailyQuestionPushPO 实例对象
     * @return 影响行数
     */
    int insert(DailyQuestionPushPO dailyQuestionPushPO);



    /**
     * 修改数据
     *
     * @param dailyQuestionPushPO 实例对象
     * @return 影响行数
     */
    int update(DailyQuestionPushPO dailyQuestionPushPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

}

