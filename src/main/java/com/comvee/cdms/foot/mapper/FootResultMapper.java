package com.comvee.cdms.foot.mapper;

import com.comvee.cdms.foot.po.FootResultPO;
import org.apache.ibatis.annotations.Param;

/**
 * @author: suyz
 * @date: 2019/6/18
 */
@Deprecated // 20190707弃用
public interface FootResultMapper {

    /**
     * 添加足部评估结果
     * @param footResultPO
     */
    void addFootResult(FootResultPO footResultPO);

    /**
     * 删除患者足部处方评估结果
     * @param doctorId
     * @param memberId
     */
    void deleteMemberFootResult(@Param("doctorId") String doctorId , @Param("memberId") String memberId);

    /**
     * 获取足部处方评估结果
     * @param doctorId
     * @param memberId
     * @param assessCode
     * @return
     */
    FootResultPO getFootResult(@Param("doctorId") String doctorId ,@Param("memberId")String memberId ,@Param("assessCode")String assessCode);

    /**
     * 修改足部处方评估结果
     * @param footResultPO
     */
    void updateFootResult(FootResultPO footResultPO);
}
