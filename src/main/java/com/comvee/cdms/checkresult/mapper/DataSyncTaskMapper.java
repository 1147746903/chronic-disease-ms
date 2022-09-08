package com.comvee.cdms.checkresult.mapper;

import com.comvee.cdms.checkresult.po.DataSyncTaskPO;

import java.util.List;

/**
 * 检验检查刷新任务请求表(CheckSyncTask)表数据库访问层
 *
 * @author lr
 * @since 2022-06-30
 */
public interface DataSyncTaskMapper {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param dataSyncTaskPO 实例对象
     * @return 对象列表
     */
    List<DataSyncTaskPO> getListByModel(DataSyncTaskPO dataSyncTaskPO);

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    DataSyncTaskPO getOneById(String sid);


    /**
     * 新增数据
     *
     * @param dataSyncTaskPO 实例对象
     * @return 影响行数
     */
    int insert(DataSyncTaskPO dataSyncTaskPO);



    /**
     * 修改数据
     *
     * @param dataSyncTaskPO 实例对象
     * @return 影响行数
     */
    int update(DataSyncTaskPO dataSyncTaskPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

}


