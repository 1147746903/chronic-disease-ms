package com.comvee.cdms.defender.wechat.mapper;

import com.comvee.cdms.defender.wechat.bo.DailyQuestionBO;
import com.comvee.cdms.defender.wechat.po.DailyQuestionPO;
import com.comvee.cdms.defender.wechat.vo.DailyQuestionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 每日一答题库(DailyQuestionPO)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-29 09:24:10
 */
public interface DailyQuestionMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    DailyQuestionPO queryById(@Param("sid") String sid);
    DailyQuestionVO queryVOById(@Param("sid") String sid);

    DailyQuestionBO queryBoById(@Param("sid") String sid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DailyQuestionPO> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dailyQuestionPO 实例对象
     * @return 对象列表
     */
    List<DailyQuestionPO> queryAll(DailyQuestionPO dailyQuestionPO);
    List<DailyQuestionVO> queryVOList(DailyQuestionPO dailyQuestionPO);

    List<DailyQuestionPO> queryAllByCodeExcludeMember(@Param("groupCode") String groupCode, @Param("memberId") String memberId);
    List<DailyQuestionPO> queryAllByTwoTypeExcludeMember(@Param("list") List<Integer> list, @Param("memberId") String memberId);
    List<DailyQuestionPO> queryAllCommonExcludeMember(@Param("memberId") String memberId);
    List<String> queryAllCommonByMemberId(@Param("memberId") String memberId);

    DailyQuestionPO getDailyQuestionPO(DailyQuestionPO dailyQuestionPO);


    /**
     * 新增数据
     *
     * @param dailyQuestionPO 实例对象
     * @return 影响行数
     */
    int insert(DailyQuestionPO dailyQuestionPO);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DailyQuestionPO> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DailyQuestionPO> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DailyQuestionPO> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<DailyQuestionPO> entities);

    /**
     * 修改数据
     *
     * @param dailyQuestionPO 实例对象
     * @return 影响行数
     */
    int update(DailyQuestionPO dailyQuestionPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

}

