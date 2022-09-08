package com.comvee.cdms.doctor.mapper;

import com.comvee.cdms.app.doctorapp.model.app.MobileVersionModel;
import com.comvee.cdms.doctor.bo.DoctorHospitalBO;
import com.comvee.cdms.doctor.bo.DoctorMemberBO;
import com.comvee.cdms.doctor.dto.*;
import com.comvee.cdms.doctor.model.KeyNoteModel;
import com.comvee.cdms.doctor.po.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author 李左河
 *
 */
public interface DoctorMapper {

    /**
     * 根据医生id获取医生信息
     * @param doctorId
     * @return
     */
    DoctorPO getDoctorById(@Param("doctorId") String doctorId);

    /**
     * 根据医生id批量获取医生信息
     * @param idList
     * @return
     */
    List<DoctorPO> listDoctorInId(List<String> idList);

    /**
     * 加载医生的关系列表
     * @param doctorId
     * @return
     */
    List<DoctorRelationPO> listDoctorRelation(@Param("doctorId") String doctorId,@Param("groupId") String groupId);

    /**
     * 加载医生分组列表
     * @param doctorId
     * @return
     */
    List<DoctorGroupPO> listGroup(@Param("doctorId") String doctorId);

    /**
     * 新增分组
     * @param addGroupDTO
     * @return
     */
    void addGroup(AddGroupMapperDTO addGroupDTO);

    /**
     * 修改分组
     * @param updateGroupDTO
     */
    void updateGroup(UpdateGroupDTO updateGroupDTO);

    /**
     * 删除分组
     * @param groupId
     */
    void deleteGroup(@Param("groupId") String groupId);

    /**
     * 修改医生信息
     * @param updateDoctorDTO
     */
    void updateDoctor(UpdateDoctorDTO updateDoctorDTO);

    /**
     * 根据id获取分组
     * @param groupId
     * @return
     */
    DoctorGroupPO getGroupById(@Param("groupId") String groupId);

    /**
     * 加载医生列表
     * @param listDoctorDTO
     * @return
     */
    List<DoctorPO> listDoctor(ListDoctorDTO listDoctorDTO);

    /**
     * 加载患者的医生列表
     * @param memberId
     * @return
     */
    List<MemberDoctorListPO> listMemberDoctor(@Param("memberId") String memberId);
    
    /**
     * 获取手机最新版本
     * @param query
     * @return
     */
    MobileVersionModel getMobileNewestVersion(Map<String,Object> query);

    /**
     * 添加医生
     * @param doctorPO
     */
    void addDoctor(DoctorPO doctorPO);

    /**
     * 添加医生团队关系
     * @param doctorId
     * @param foreignId
     */
    void addDoctorRelation(DoctorRelationPO doctorRelationPO);

    /**
     * 删除团队关系
     * @param doctorId
     * @param foreignId
     */
    void deleteDoctorRelation(@Param("doctorId") String doctorId,@Param("foreignId") String foreignId);

    /**
     * 加载医生列表及登录信息
     * @param listDoctorDTO
     * @return
     */
    List<DoctorPO> listDoctorAndLogin(ListDoctorDTO listDoctorDTO);

    /**
     * 加载医生科室分组
     * @param doctorId
     * @return
     */
    List<DoctorGroupPO> listDepartmentByDid(String doctorId);

    /**
     * 根据分组id 和 医生id，获取患者ids
     * @param doctorMemberBO
     * @return
     */
    List<String> listMemberIdsByGroupId(DoctorMemberBO doctorMemberBO);

    /**
     * 批量添加分组患者信息
     * @param doctorMemberBO
     */
    void batchUpdateDoctorMemberGroupId(DoctorMemberBO doctorMemberBO);

    /**
     * 根据医院编号和医生工号获取医生信息
     * @param workNo
     * @param hospitalId
     */
    DoctorPO getDoctorByWorkNo(@Param("workNo") String workNo,@Param("hospitalId") String hospitalId);

    /** v5.1.1
     * 添加医生可切换医院表
     * @param doctorHospitalPO
     */
    void addDoctorHospital(DoctorHospitalPO doctorHospitalPO);

    /** v5.1.1
     * 删除医生可切换医院
     * @param doctorId
     */
    void deleteDoctorHospital(@Param("doctorId") String doctorId);

    /** v5.1.1
     * 加载医生可切换医院列表
     * @param doctorId
     * @return
     */
    List<DoctorHospitalBO> listDoctorHospitalByDoctorId(@Param("doctorId") String doctorId);

    /** v5.1.1
     *  根据医院id加载医生列表
     * @param hospitalId
     */
    List<DoctorPO> listDoctorByHospitalId(@Param("hospitalId") String hospitalId);

    /** v5.1.1
     *  根据医院id加载医生科室分组
     * @param hospitalId
     */
    List<DoctorGroupPO> listDepartmentByHid(@Param("hospitalId") String hospitalId,@Param("doctorId") String doctorId);

    /*************************************************邪恶的分割线******************************************************
     * @version v6.0.0
     * @author: linyd
     * @date: 2020/02/27
     *****************************************************************************************************************/
    /** v6.0.0
     * 删除医生重点关注项的配置字段
     * @param doctorId
     */
    void deleteKeyNotes(@Param("hospitalId") String hospitalId,@Param("doctorId") String doctorId,
                        @Param("memberId") String memberId ,@Param("inHos") Integer inHos,
                        @Param("type")Integer type);

    /** v6.0.0
     * 添加医生重点关注项的配置字段
     */
    void addDoctorKeyNotes(AddDoctorKeyNoteDTO doctorKeyNoteDTO);

    /** v6.0.0
     * 查询医院所有可配置字段
     * @return
     */
    List<KeyNoteModel> listHospitalAllKeyNotes(ListHospitalAllKeyNoteDTO listKeyNoteDTO);

    /**
     * 获取医生的配置字段数
     * @param doctorId
     * @return
     */
    Long getDoctorKeyNotesNum(@Param("hospitalId") String hospitalId,@Param("doctorId") String doctorId,@Param("memberId") String memberId,@Param("inHos") Integer inHos);

    /**
     * 获取医生配置的关注项目
     * @param listSelectedKeyNoteDTO
     * @return
     */
    List<KeyNoteModel> listSelectedKeyNotes(ListSelectedKeyNoteDTO listSelectedKeyNoteDTO);

    /**
     * 根据医院id获取有切换医院的医生id
     * @param hospitalId
     * @return
     */
    List<String> getDoctorIdsSwitch(@Param("hospitalId") String hospitalId);

    /**
     * 删除医生关联医院
     * @param doctorIdList
     * @param hospitalIdList
     */
    void deleteDoctorHospitalByParam(@Param("doctorIdList") List<String> doctorIdList, @Param("hospitalIdList") List<String> hospitalIdList);

    /**
     * 获取医生id
     * @return
     */
    List<DoctorRelationPO>  listDoctors(@Param("hospitalId")String hospitalId,@Param("doctorId")String doctorId);

    /**
     * 根据oepnId获取医生信息
     * @param doctorId
     * @return
     */
    DoctorPO getDoctorByOpenId(@Param("openId") String openId);
    /**
     * 根据手机号获取医生
     * @param phoneNo
     * @return
     */
    DoctorPO queryDoctorByPhone(@Param("phoneNo") String phoneNo);

    /**
     * 渠道获取医生的服务包所被用户购买的次数
     * @param doctorId
     * @return
     */
    List<Integer> getDoctorSumUser(@Param("doctorId") String doctorId);

    /**
     * 获取当前患者
     * @return
     */
    Integer getDoctorCurrentPatient(@Param("doctorId") String doctorId);


}
