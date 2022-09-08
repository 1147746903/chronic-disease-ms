package com.comvee.cdms.defender.wechat.mapper;

import com.comvee.cdms.defender.wechat.bo.DailyQuestionBO;
import com.comvee.cdms.defender.wechat.po.MemberDailyQuestionRecordPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 每日一答用户记录表(MemberDailyQuestionRecordPO)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-29 10:19:36
 */
public interface MemberDailyQuestionRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    MemberDailyQuestionRecordPO queryById(@Param("sid") String sid);

    MemberDailyQuestionRecordPO queryErrorsExcludeMember(@Param("memberId") String memberId);

    List<MemberDailyQuestionRecordPO>  queryDoneExcludeMember(@Param("memberId") String memberId);

    DailyQuestionBO loadQuesPOById(@Param("sid") String sid);

    List<MemberDailyQuestionRecordPO> queryRecordByMemberId(@Param("memberId") String memberId);

    List<MemberDailyQuestionRecordPO> queryByMemberId(@Param("memberId") String memberId);

    /**
     * 新增数据
     *
     * @param memberDailyQuestionRecordPO 实例对象
     * @return 影响行数
     */
    int insert(MemberDailyQuestionRecordPO memberDailyQuestionRecordPO);


}

