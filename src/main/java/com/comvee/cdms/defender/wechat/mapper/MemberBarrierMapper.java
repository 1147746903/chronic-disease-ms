package com.comvee.cdms.defender.wechat.mapper;

import com.comvee.cdms.defender.wechat.po.MemberBarrierPO;
import com.comvee.cdms.defender.wechat.vo.BarrierResultVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 患者关卡表(MemberBarrierPO)表数据库访问层
 *
 * @author makejava
 * @since 2021-12-03 10:32:55
 */
public interface MemberBarrierMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    MemberBarrierPO queryById(@Param("sid") String sid);

    //统计关卡答题总人数
    int countBarrPersonNumBySort(@Param("sort") Integer sort);

    //统计大于分数的人数
    int countBarrPersonOverScoreNum(@Param("sort") Integer sort, @Param("score") Integer score);
    int countBarrPersonUnderScoreNum(@Param("sort") Integer sort, @Param("score") Integer score);


    /**
     * 获取同一关其他用户的分数
     * @param memberId
     * @param sort
     * @return
     */
    List<Integer> queryScoreExcludeSelfWithSort(@Param("sort") Integer sort, @Param("memberId") String memberId);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param memberBarrierPO 实例对象
     * @return 对象列表
     */
    List<MemberBarrierPO> queryAll(MemberBarrierPO memberBarrierPO);

    List<String> queryIdDescLimit(@Param("memberId") String memberId, @Param("limit") Integer limit);


    List<String> loadAllBarrCourse();

    BarrierResultVO queryResult(@Param("sid") String sid, @Param("batchId") String batchId);

    /**
     * 新增数据
     *
     * @param memberBarrierPO 实例对象
     * @return 影响行数
     */
    int insert(MemberBarrierPO memberBarrierPO);



    /**
     * 修改数据
     *
     * @param memberBarrierPO 实例对象
     * @return 影响行数
     */
    int update(MemberBarrierPO memberBarrierPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

}

