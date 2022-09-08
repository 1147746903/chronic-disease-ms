package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.dto.DyRememberDietDTO;
import com.comvee.cdms.dybloodsugar.dto.MemberLifeTypeDTO;
import com.comvee.cdms.dybloodsugar.po.DyRememberDietPO;
import com.comvee.cdms.dybloodsugar.po.DyRememberFoodPO;
import com.comvee.cdms.dybloodsugar.po.MemberLifeTypePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DyRememberDietMapper {
    /**
     * 根据主键id查询做饮食记录数据回填
     * @param sid
     * @return
     */
    DyRememberDietPO getDyDietRememberValues(@Param("sid") String sid);

    /**
     * 存储饮食记录
     * @param dyRememberDietPO
     */
    void addDietRemember(DyRememberDietPO dyRememberDietPO);

    /**
     * 根据传过来的探头序列号和操作者id查看表中是否有这一条数据.
     * @return
     */
    DyRememberDietPO getDyDietRemember(DyRememberDietDTO dto);

    /**
     * 修改饮食记录
     */
    void updateDyDietRemember(DyRememberDietPO dyRememberDietPO);

    /**
     * 根据主键id修改is_valid字段为无效
     * @param sid
     */
    void updateDyDietRememberBySid(@Param("sid") String sid);
    /**
     * 查询饮食列表数据
     * @return
     */
    List<DyRememberDietPO> listDyDietRememberPO(DyRememberDietDTO dyRememberDietDTO);

    /**
     * 获取小程序记录的饮食
     * @author
     * @date 2021/6/29
     * @param dto
     * @return java.util.List<com.comvee.cdms.dybloodsugar.po.DyRememberDietPO>
     */
    List<DyRememberDietPO > listDyDietRememberPOForWX(DyRememberDietDTO dto);

    /**
     * 获取小程序记录的饮食
     * @author
     * @date 2021/6/29
     * @param dto
     * @return java.util.List<com.comvee.cdms.dybloodsugar.po.DyRememberDietPO>
     */
    List<DyRememberDietPO > listDyDietRememberPOForWXv2(DyRememberDietDTO dto);


    /**
     * 获取日期列表
     * @param dto
     * @return
     */
    List<String>  listDyDietRememberDtForWX(DyRememberDietDTO dto);
    /**
     * 要处理的小程序端饮食记录
     * @author
     * @date 2021/7/2
     * @param dto
     * @return java.util.List<com.comvee.cdms.dybloodsugar.po.DyRememberDietPO>
     */
    List<DyRememberDietPO> listDyDietRememberPOForHandleData(DyRememberDietDTO dto);


    void modifyDyDietRememberBySid(DyRememberDietPO dyRememberDietPO);

    DyRememberDietPO getDyDietRememberOne(DyRememberDietDTO dto);

    /**
     * 获取食物库
     * @author
     * @date 2021/5/10
     * @param foodName
     * @return java.util.List<com.comvee.cdms.dybloodsugar.po.DyRememberFoodPO>
     */
    List<DyRememberFoodPO> getFoodLsit(@Param("foodName") String foodName, @Param("type") String type);

    /**
     * 获取生活方式记录
     * @author
     * @date 2021/5/10
     * @param dto
     * @return java.util.List<com.comvee.cdms.dybloodsugar.po.MemberLifeTypePO>
     */
    List<MemberLifeTypePO> getLiftTypeList(MemberLifeTypeDTO dto);

    /**
     * 添加生活记录
     * @author
     * @date 2021/5/10
     * @param memberLifeTypePO
     * @return void
     */
    void addLifeType(DyRememberDietPO dyRememberDietPO);

    /**
     * 更新生活方式
     * @author
     * @date 2021/5/10
     * @param memberLifeTypePO
     * @return void
     */
    void updateLifeType(DyRememberDietPO dyRememberDietPO);

    /**
     * 删除生活方式记录
     * @author
     * @date 2021/5/10
     * @param sid
     * @return void
     */
    void delLifeType(String sid);

    /**
     * 新版饮食记录添加
     * @author
     * @date 2021/5/20
     * @param dyRememberDietPO
     * @return void
     */
    void addDietRememberNew(DyRememberDietPO dyRememberDietPO);

    /**
     * 新版饮食记录修改
     * @author
     * @date 2021/5/20
     * @param dyRememberDietPO
     * @return void
     */
    void updateDyDietRememberNew(DyRememberDietPO dyRememberDietPO);

    /**
     * 删除饮食记录
     * @author
     * @date 2021/6/7
     * @param sid
     * @return void
     */
    void delDietRemember(@Param("sid") String sid);


    /**
     * 根据id获取食物
     * @author
     * @date 2021/6/7
     * @param sid
     * @return void
     */
    DyRememberFoodPO getFoodItemById(@Param("id") String id);

    /**
     * 获取食物类别
     * @author
     * @date 2021/6/9
     * @param
     * @return java.util.List<java.lang.String>
     */
    List<String> getFoodType();
}
