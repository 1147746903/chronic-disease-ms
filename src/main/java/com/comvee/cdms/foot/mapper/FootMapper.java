package com.comvee.cdms.foot.mapper;

import com.comvee.cdms.foot.dto.ListFootDTO;
import com.comvee.cdms.foot.po.FootPO;
import com.comvee.cdms.foot.po.FootReportPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public interface FootMapper {

    /**
     * 加载足部管理处方列表
     * @param listFootDTO
     * @return
     */
    List<FootPO> listFoot(ListFootDTO listFootDTO);

    /**
     * 获取足部管理处方
     * @param sid
     * @return
     */
    FootPO getFoot(@Param("followId") String followId);

    /**
     * 新增记录
     * @param FootPO
     */
    void addFoot(FootPO FootPO);

    /**
     * 更新记录
     * @param FootPO
     */
    void modifyFoot(FootPO FootPO);

    /**
     * 加载足部处方关联的筛查报告
     * @param prescriptId
     * @return
     */
    List<FootReportPO> listFootRelateReport(@Param("prescriptId") String prescriptId);

    /**
     * 新增足部处方报告关联
     * @param footReportPO
     */
    void addFootReportRelate(FootReportPO footReportPO);

    /**
     * 修改足部处方报告关联
     * @param footReportPO
     */
    void updateFootReportRelate(FootReportPO footReportPO);

    /**
     * 根据获取报告关联记录
     * @param id
     * @return
     */
    FootReportPO getFootReportById(@Param("id") String id);

    /**
     * 删除处方报告关联
     * @param prescriptId
     */
    void deleteFootReportRelate(@Param("prescriptId") String prescriptId);

    /**
     * 加载住院患者最新的足处方记录
     * v 5.1.6
     * @param memberId
     * @return
     */
    FootPO listFootByMemberOfInHos(@Param("memberId") String memberId);


}
