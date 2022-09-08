package com.comvee.cdms.doctor.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.department.model.po.DepartmentPO;
import com.comvee.cdms.doctor.bo.DoctorHospitalBO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.dto.*;
import com.comvee.cdms.doctor.model.KeyNoteModel;
import com.comvee.cdms.doctor.po.*;
import com.comvee.cdms.doctor.vo.*;
import com.comvee.cdms.member.dto.ListMemberDTO;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
public interface DoctorServiceI {

    /**
     * 根据医生id获取医生信息
     * @author 李左河
     * @date 2018年3月22日 上午9:45:12
     * @param doctorId
     * @return
     */
    DoctorPO getDoctorById(String doctorId);

    /**
     * 加载我的医生
     * @param doctorId
     * @return
     */
    List<DoctorPO> listMyDoctor(String doctorId);

    /**
     * 加载分组
     * @param doctorId
     * @return
     */
    List<DoctorGroupVO> listGroup(String doctorId, String keyword ,Boolean isPeopleNumber);

    /**
     * 新增分组
     * @param addGroupDTO
     * @return
     */
    String addGroup(AddGroupDTO addGroupDTO);

    /**
     * 新增分组
     * @param addGroupDTO
     * @return
     */
    String addGroupHasId(AddGroupDTO addGroupDTO ,String groupId);

    /**
     * 修改分组
     * @param updateGroupDTO
     */
    void updateGroup(UpdateGroupDTO updateGroupDTO);

    /**
     * 删除分组
     * @param deleteGroupDTO
     */
    void deleteGroup(DeleteGroupDTO deleteGroupDTO);

    /**
     * 获取医生待办任务
     * @param doctorId
     * @return
     */
    DoctorTaskVO getDoctorTask(String doctorId,String hospitalId,String authority);

    /**
     * 修改医生信息
     * @param updateDoctorDTO
     */
    void updateDoctor(UpdateDoctorDTO updateDoctorDTO);

    /**
     * 根据医生id批量获取医生信息
     * @param idList
     * @return
     */
    List<DoctorPO> listDoctorInId(List<String> idList);

    /**
     * 加载医生
     * @param pr
     * @param listDoctorDTO
     * @return
     */
    PageResult<DoctorPO> listDoctor(PageRequest pr,ListDoctorDTO listDoctorDTO);
    List<DoctorPO> listDoctorOne(ListDoctorDTO listDoctorDTO);

    /**
     * 获取医生和申请信息
     * @param memberId
     * @param doctorId
     * @return
     */
    DoctorAndApplyInfoVO getDoctorAndApplyInfo(String memberId ,String doctorId);

    /**
     * 加载患者的医生列表
     * @param memberId
     * @return
     */
    List<MemberDoctorListPO> listMemberDoctor(String memberId);

    /**
     * 设置照顾医师
     * @param memberId
     * @param doctorId
     */
    void setAttendDoctor(String memberId, String doctorId);


    /**
     * 获取有医生权限的医生ID串
     * @param doctorId
     * @return
     */
    List<String> listTeamId(String doctorId);

    /**
     * 加载团队里的医生
     * @param doctorId
     * @return
     */
    List<DoctorPO> listGroupDoctor(String doctorId);

    /**
     * 加载医生服务时间设置
     * @param doctorId
     * @return
     */
    List<DoctorServiceTimePO> listDoctorServiceTime(String doctorId, String weekCode ,Integer openStatus);

    /**
     * 新增医生服务时间设置
     * @param doctorServiceTimePO
     */
    String addDoctorServiceTime(DoctorServiceTimePO doctorServiceTimePO);

    /**
     * 删除医生服务时间设置
     * @param sid
     */
    void deleteDoctorServiceTime(String sid);

    /**
     * 修改医生服务时间设置
     * @param doctorServiceTimePO
     */
    void updateDoctorServiceTime(DoctorServiceTimePO doctorServiceTimePO);

    /**
     * 根据id获取分组
     * @param groupId
     * @return
     */
    DoctorGroupPO getGroupById(String groupId);

    /**
     * 新增分组（传入主键）
     * @param groupId
     * @param groupName
     * @param doctorId
     * @return
     */
    void addGroupWithId(String groupId ,String groupName ,String doctorId);

    /**
     * 添加医生信息及账号
     * @param addDoctorAndAccountDTO
     * @return
     */
    String addDoctorAndAccount(AddDoctorAndAccountDTO addDoctorAndAccountDTO);

    /**
     * 添加医生团队关系
     * @param doctorId
     * @param foreignId
     */
    void addDoctorRelation(String doctorId, String foreignId);

    /**
     * 删除团队关系
     * @param doctorId
     * @param foreignId
     */
    void deleteDoctorRelation(String doctorId, String foreignId);

    /**
     * 刷新医生二维码
     * @param doctorId
     * @return
     */
    String refreshDoctorQrCode(String doctorId);

    /**
     * 加载医生列表及登录信息
     * @param listDoctorDTO
     * @return
     */
    PageResult<DoctorPO> listDoctorAndLogin(PageRequest pr,ListDoctorDTO listDoctorDTO);

    /**
     * 获取分组列表 v4
     * @param doctorId
     * @param keyWord
     * @return
     */
    List<GroupsVO> listGroups(ListGroupsDTO dto);

    /**
     * 异常患者数量
     * @param doctorIds
     * @return
     */
    long countDoctorsMemberAbnormalNumber(List<String> doctorIds);

    /**
     * 根据id获取分组视图
     * @param groupId
     * @return
     */
    DoctorGroupVO getDoctorGroupByGroupId(String groupId);

    /**
     * 根据分组id，修改分组信息 （新增患者）
     * @author 李左河
     * @date 2018/7/26 11:08
     * @param doctorModel
     * @param updateDoctorGroupDTO
     * @return void
     *
     */
    void updateDoctorGroupByGroupId(DoctorSessionBO doctorSessionBO, UpdateDoctorGroupDTO updateDoctorGroupDTO);

    /**
     * 新增分组
     * @author 李左河
     * @date 2018/7/25 9:34
     * @param doctorModel
     * @param insertDoctorGroupDTO
     * @return String
     *
     */
    String insertDoctorGroup(DoctorSessionBO doctorSessionBO, InsertDoctorGroupDTO insertDoctorGroupDTO);

    /**
     * 加载科室医生
     * @param departmentId
     * @return
     */
    List<DoctorPO> listDoctorByDepartId(String departmentId);

    /**
     * 根据医院编号和医生工号获取医生信息
     * @param workNo
     * @param hospitalId
     * @return
     */
    DoctorPO getDoctorByWordNo(String workNo, String hospitalId);


    /** v5.1.1
     * 加载医生可切换医院列表
     * @param doctorId
     * @return
     */
    List<DoctorHospitalBO> listDoctorHospitalByDoctorId(String doctorId);

    /** v5.1.1
     *  根据医院id加载医生列表
     * @param hospitalId
     */
    List<DoctorPO> listDoctorByHospitalId(String hospitalId);

    /**v4.0.4
     * 禁用和开启医生服务时间
     * @param doctorServiceTimePO
     */
    void openOrCloseDoctorServiceTime(DoctorServiceTimePO doctorServiceTimePO );


    /** v4.0.4
     * 根据id获取医生服务时间详细信息
     * @param sid
     * @return
     */
    DoctorServiceTimePO getDoctorServiceTimeById(String sid);

    /** v4.0.4
     * 保存医生服务时间外提示信息
     * @param
     */
    void saveDoctorServiceRemindWithLock(AddDoctorServiceRemindDTO addDoctorServiceRemindDTO);

    /** v4.0.4
     * 通过医生id获取医生服务时间外提示信息
     * @param doctorId
     * @return
     */
    DoctorServiceRemindPO getDoctorServiceRemindByDoctorId(String doctorId,Integer openStatus);

    /** v4.0.4
     * 分页加载医生服务时间设置
     * @param doctorId
     * @return
     */
    PageResult<DoctorServiceTimePO> pageListDoctorServiceTime(String doctorId, String weekCode ,Integer openStatus,PageRequest page);

    /**
     * 加载我团队里的医生
     * @param doctorId
     * @return
     */
    List<DoctorPO> listMyTeamDoctor(String doctorId);

    /*************************************************邪恶的分割线******************************************************
     * @version v6.0.0
     * @author: linyd
     * @date: 2020/02/27
     *****************************************************************************************************************/
    /**
     * 获取医护人员可管理的科室
     * @param listMemberDTO
     * @return
     */
    List<DoctorDepartVO> listDoctorDepart(ListMemberDTO listMemberDTO);

    /** v 6.0.0
     * 加载医生的科室权限
     * @param doctorId
     * @return
     */
    List<DoctorDepartmentVO> listDoctorDepartment(String doctorId);

    /** v6.0.0
     * 根据医院id加载医生有账号的科室
     * @param hospitalId
     * @return
     */
    List<DepartmentPO> listDoctorDepartHasAccountByHosId(String hospitalId);

    /** v6.0.0
     * 添加医生重点关注患者项目
     * @param dto
     */
    void saveDoctorKeyNotes(DoctorKeyNoteDTO dto);

    /**
     * 查询医生所有重点关注配置字段
     * @param listHospitalAllKeyNoteDTO
     * @return
     */
    List<KeyNoteModel> listHospitalAllKeyNotes(ListHospitalAllKeyNoteDTO listHospitalAllKeyNoteDTO);

    /**
     * 获取医生重点关注项字段
     * @param dto
     * @return
     */
    List<KeyNoteModel> listSelectedKeyNotes(ListSelectedKeyNoteDTO dto);

    /**
     * 根据医院id获取有切换医院的医生id
     * @param hospitalId
     * @return
     */
    List<String> getDoctorIdsSwitch(String hospitalId);

    /**
     * 删除医生关联医院
     * @param doctorIdList
     * @param hospitalIdList
     */
    void deleteDoctorHospitalByParam( List<String> doctorIdList, List<String> hospitalIdList);

    /**
     * 根据openId获取医生信息
     * @param openId
     * @return
     */
    DoctorPO getDoctorByOpenId(String openId);

    /**
     * h5医生查看血酮记录
     * @param memberId
     * @return
     */
    Map<String, Object> listBloodKetone(String memberId);

    /**
     * 根据手机号获取医生
     * @param phoneNo
     * @return
     */
    DoctorPO queryDoctorByPhone(String phoneNo);
    /**
     * 渠道获取医生的服务包所被用户购买的次数
     * @param doctorId
     * @return
     */
    List<Integer> getDoctorSumUser(String doctorId);

    /**
     * 渠道获取医生当前患者数量
     * @param doctorId
     * @return
     */
    Integer getDoctorCurrentPatient(String doctorId);



    List<DoctorTeamWithGroupsVO> listTeamWithGroups(ListTeamWithGroupsDTO dto);

    Map<String,Object> listDoctorCommittee(String doctorId);
}
