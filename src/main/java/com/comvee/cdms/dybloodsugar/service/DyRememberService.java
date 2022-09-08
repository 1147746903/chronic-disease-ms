package com.comvee.cdms.dybloodsugar.service;

import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dybloodsugar.dto.*;
import com.comvee.cdms.dybloodsugar.po.*;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface DyRememberService {


    /**
     * 查询饮食列表数据
     * @return
     */
    List<DyRememberDietPO> getDyDietRememberPOList(DyRememberDietDTO dyRememberDietDTO);

    /**
     * 获取要处理的小程序饮食列表数据
     * @author
     * @date 2021/6/29
     * @param dto
     * @return java.util.List<com.comvee.cdms.dybloodsugar.po.DyRememberDietPO>
     */
    List<DyRememberDietPO> getDyDietRememberPOListForWX(DyRememberDietDTO dto);

    List<DyRememberDietPO> getDyDietRememberPOListForWXv2(DyRememberDietDTO dto);

    List<String> listDyDietRememberDtForWX(DyRememberDietDTO dto);

    List<String> listDySportRememberDtForWX(DyRememberSportDTO dto);

    /**
     * 小程序饮食列表数据
     * @author
     * @date 2021/7/2
     * @param dto
     * @return java.util.List<com.comvee.cdms.dybloodsugar.po.DyRememberDietPO>
     */
    List<DyRememberDietPO> getDyDietRememberPOListForHandleData(DyRememberDietDTO dto);
    /**
     * 根据主键id查询当前饮食记录做数据回显
     * @param sid
     * @return
     */
    DyRememberDietPO getDyDietRememberValues(String sid);

    /**
     * 存储饮食记录
     */
    void setDietRemember(DyRememberDietDTO dto,String sid);

    /**
     * 饮食记录根据主键id删除选中的记录.
     * @param sid
     */
    void updateDyDietRemember(String sid);


    /**
     * 运动记录根据主键id删除选中的记录.
     * @param sid
     */
    void deleteSportRemember(String sid);

    /**
     * 保存患者运动记录
     */
    void setSportRemember(DyRememberSportDTO dto,String sid);

    /**
     * 运动记录数据回填
     * @param sid
     */
    DyRememberSportPO getDySportRememberValues(String sid);

    /**
     * 查询患者的运动列表数据
     * @return
     */
    List<DyRememberSportPO> getDySportRememberPOList(DyRememberSportDTO dyRememberSportDTO);

    /**
     * 存储用药记录
     */
    void setPharmacyRemember(DyRememberPharmacyDTO dto,String sid );

    /**
     * 用药记录根据主键id删除选中的记录.
     * @param sid
     */
    void deletePharmacyRemember(String sid);

    /**
     * 用药记录数据回填
     * @param sid
     */
    DyRememberPharmacyPO getDyPharmacyRememberValues(String sid);

    /**
     * 查询医生记录的用药列表数据
     * @return
     */
    List<DyRememberPharmacyPO> getDyPharmacyRememberPOList(DyRememberPharmacyDTO dyRememberPharmacyDTO);


    /**
     * 添加睡眠记录
     */
    void setSleepRemember(DyRememberSleepDTO dto,Integer origin);

    /**
     * 获取当前医生记录的患者睡眠时间
     * @return
     */
    List<DyRememberSleepPO> getSleepRemember(DyRememberSleepDTO dto);

    void deleteBloodSugarRemark(String sid);

//    void updateBloodSugarRemarkById(String sid);


    /**
     *  获取食物列表
     * @author
     * @date 2021/5/10
     * @param
     * @return java.util.List<com.comvee.cdms.dybloodsugar.po.DyRememberFoodPO>
     */
    PageResult<DyRememberFoodPO> getFoodList(PageFoodItemDTO dto);

    /**
     * 根据食物名称模糊搜索
     * @author
     * @date 2021/6/9
     * @param dto
     * @return java.util.Map
     */
    Map getFoodListByName(PageFoodItemDTO dto);

    /**
     * 添加或修改饮食记录
     * @author
     * @date 2021/5/10
     * @param dyRememberDietDTO
     * @param sid
     * @return void
     */
    void addDietRemember(DyRememberDietDTO dyRememberDietDTO, String sid);

    /**
     * 菜品识别
     * @author
     * @date 2021/5/10
     * @param is
     * @param extension
     * @return com.alibaba.fastjson.JSONObject
     */
    Map dishIdentify(InputStream is, String extension) throws Exception;

    /**
     * 获取生活方式记录
     * @author
     * @date 2021/5/10
     * @param memberLifeTypeDTO
     * @return java.util.List<com.comvee.cdms.dybloodsugar.po.MemberLifeTypePO>
     */
    List<MemberLifeTypePO> getLiftTypeList(MemberLifeTypeDTO memberLifeTypeDTO);

    /**
     * 添加或修改生活方式
     * @author
     * @date 2021/5/10
     * @param dto
     * @param sid
     * @return void
     */
    void addLifeType(DyRememberDietPO po);

    /**
     * 删除生活方式记录
     * @author
     * @date 2021/5/10
     * @param sid
     * @return void
     */
    void delLifeType(String sid);

    /**
     * 获取单一食物
     * @author
     * @date 2021/6/7
     * @param sid
     * @return com.comvee.cdms.dybloodsugar.po.DyRememberFoodPO
     */
    DyRememberFoodPO getSingleFoodItem(String sid);

    /**
     * 获取食物列表
     * @author
     * @date 2021/6/9
     * @param
     * @return java.util.List<java.lang.String>
     */
    List<String> getFoodType();
}
