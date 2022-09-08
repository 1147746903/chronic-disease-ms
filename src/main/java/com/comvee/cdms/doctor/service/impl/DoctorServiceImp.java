package com.comvee.cdms.doctor.service.impl;

import com.comvee.cdms.authority.constant.AuthorityCode;
import com.comvee.cdms.authority.constant.AuthorityConstant;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.authority.service.RoleService;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.department.mapper.DepartmentMapper;
import com.comvee.cdms.department.model.dto.UpdateDepartmentDTO;
import com.comvee.cdms.department.model.po.DepartmentPO;
import com.comvee.cdms.department.service.DepartmentService;
import com.comvee.cdms.dialogue.dto.UnReadDialogueDTO;
import com.comvee.cdms.dialogue.po.UnReadDialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.differentlevels.service.DifferentLevelsService;
import com.comvee.cdms.doctor.bo.DoctorHospitalBO;
import com.comvee.cdms.doctor.bo.DoctorMemberBO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.cfg.DoctorConstant;
import com.comvee.cdms.doctor.cfg.GroupConstant;
import com.comvee.cdms.doctor.dto.*;
import com.comvee.cdms.doctor.mapper.DoctorDepartmentMapper;
import com.comvee.cdms.doctor.mapper.DoctorMapper;
import com.comvee.cdms.doctor.mapper.DoctorServiceTimeMapper;
import com.comvee.cdms.doctor.model.KeyNoteModel;
import com.comvee.cdms.doctor.po.*;
import com.comvee.cdms.doctor.service.DoctorCacheServiceI;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.doctor.vo.*;
import com.comvee.cdms.follow.dto.ListFollowRemindDTO;
import com.comvee.cdms.follow.mapper.FollowMapper;
import com.comvee.cdms.hospital.model.dto.UpdateHospitalDTO;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import com.comvee.cdms.hospital.service.CommitteeService;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.level.dto.ListMemberLevelDTO;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.constant.MemberApplyConstant;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.po.DoctorMemberApplyPO;
import com.comvee.cdms.member.service.MemberApplyService;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.vo.SugarMemberVO;
import com.comvee.cdms.other.constant.WechatQrCodeConstant;
import com.comvee.cdms.other.dto.CreateStrQrCodeDTO;
import com.comvee.cdms.other.dto.GetWechatQrCodeDTO;
import com.comvee.cdms.other.po.WechatQrcodePO;
import com.comvee.cdms.other.service.WechatQrCodeService;
import com.comvee.cdms.packages.dto.CountMemberPackageDTO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.prescription.dto.GetPrescriptionDTO;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.PrescriptionPO;
import com.comvee.cdms.sign.dto.ListBloodKetoneDTO;
import com.comvee.cdms.sign.dto.ListBloodSugarByDayDTO;
import com.comvee.cdms.sign.mapper.BloodKetoneMapper;
import com.comvee.cdms.sign.mapper.BloodSugarMapper;
import com.comvee.cdms.sign.po.BloodKetonePO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.user.cfg.UserConstant;
import com.comvee.cdms.user.dto.AddUserDTO;
import com.comvee.cdms.user.service.UserService;
import com.comvee.cdms.virtualward.constant.VirtualWardConstant;
import com.comvee.cdms.virtualward.model.param.QueryTransferApplyListParam;
import com.comvee.cdms.virtualward.service.VirtualWardService;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.crypto.hash.Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 
 * @author 李左河
 *
 */
@Service("doctorService")
public class DoctorServiceImp implements DoctorServiceI {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    @Lazy
    private MemberApplyService memberApplyService;

    @Autowired
    @Lazy
    private DialogueService dialogueService;

    @Autowired
    private DoctorServiceTimeMapper doctorServiceTimeMapper;

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private DepartmentService departmentService;

    @Autowired
    @Lazy
    private HospitalService hospitalService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private WechatQrCodeService wechatQrCodeService;

    @Autowired
    private BloodSugarMapper bloodSugarMapper;

    @Autowired
    @Lazy
    private BloodSugarService bloodSugarService;

    @Autowired
    @Lazy
    private PackageService packageService;

    @Autowired
    private DifferentLevelsService differentLevelsService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    @Lazy
    private MemberLevelService memberLevelService;

    @Autowired
    private DoctorDepartmentMapper doctorDepartmentMapper;

    @Autowired
    private DoctorCacheServiceI doctorCacheService;

    @Autowired
    @Lazy
    private VirtualWardService virtualWardService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private BloodKetoneMapper bloodKetoneMapper;

    @Autowired
    private CommitteeService committeeService;


    @Override
    public DoctorPO getDoctorById(String doctorId) {
        return this.doctorCacheService.getDoctorById(doctorId);
    }

    @Override
    public List<DoctorPO> listMyDoctor(String doctorId) {
        return this.doctorCacheService.listMyDoctor(doctorId);
    }

    @Override
    public List<DoctorGroupVO> listGroup(String doctorId,String keyword ,Boolean isPeopleNumber) {
        List<DoctorGroupPO> groupList = this.doctorMapper.listGroup(doctorId);
        defaultGroupHandler(groupList, doctorId);
        List<DoctorGroupVO> groupVOList = new ArrayList<>();
        groupList.forEach(x -> {
            DoctorGroupVO vo = new DoctorGroupVO();
            BeanUtils.copyProperties(vo, x);
            ListMemberDTO listMemberDTO = new ListMemberDTO();
            listMemberDTO.setDoctorId(doctorId);
            listMemberDTO.setKeyWord(keyword);
            listMemberDTO.setGroupId(x.getGroupId());
            if(isPeopleNumber == null || isPeopleNumber){
                vo.setPeopleNumber(this.memberService.countGroupMemberForV4(listMemberDTO));
            }
            groupVOList.add(vo);
        });
        return groupVOList;
    }

    //默认分组处理
    private void defaultGroupHandler(List<DoctorGroupPO> groupList,String doctorId){
        DoctorGroupPO doctorGroupPO = new DoctorGroupPO();
        doctorGroupPO.setDoctorId(doctorId);
        doctorGroupPO.setGroupName(GroupConstant.DEFAULT_GROUP_NAME);
        doctorGroupPO.setGroupId(GroupConstant.DEFAULT_GROUP_ID);
        groupList.add(0, doctorGroupPO);
    }
    //虚拟病区处理
    private void virtualWardGroupHandler(List<DoctorGroupPO> groupList,String doctorId){
        //判断是否有有虚拟病区权限
        Set<String> strings = this.authorityService.listUserAuthority(doctorId , AuthorityConstant.TYPE_FRONT);
        if(strings == null || !strings.contains(AuthorityCode.WEB_VIRTUAL_WARD)){
            return;
        }
        DoctorGroupPO doctorGroupPO = new DoctorGroupPO();
        doctorGroupPO.setDoctorId(doctorId);
        doctorGroupPO.setGroupName(GroupConstant.VIRTUAL_WARD_GROUP_NAME);
        doctorGroupPO.setGroupId(GroupConstant.VIRTUAL_WARD_GROUP_ID);
        groupList.add(doctorGroupPO);
    }
    //虚拟病区处理
    private void virtualWardGroupHandler2(List<DoctorDepartVO> groupList,String doctorId){
        //判断是否有有虚拟病区权限
        Set<String> strings = this.authorityService.listUserAuthority(doctorId , AuthorityConstant.TYPE_FRONT);
        if(strings == null || !strings.contains(AuthorityCode.WEB_VIRTUAL_WARD)){
            return;
        }
        DoctorDepartVO doctorGroupPO = new DoctorDepartVO();
        doctorGroupPO.setDepartId(GroupConstant.VIRTUAL_WARD_GROUP_ID);
        doctorGroupPO.setDepartName(GroupConstant.VIRTUAL_WARD_GROUP_NAME);
        groupList.add(doctorGroupPO);
    }

    @Override
    public String addGroup(AddGroupDTO addGroupDTO) {
        String groupId = DaoHelper.getSeq();
        AddGroupMapperDTO addGroupMapperDTO = new AddGroupMapperDTO();
        BeanUtils.copyProperties(addGroupMapperDTO, addGroupDTO);
        addGroupMapperDTO.setGroupId(groupId);
        this.doctorMapper.addGroup(addGroupMapperDTO);
        return groupId;
    }

    @Override
    public String addGroupHasId(AddGroupDTO addGroupDTO, String groupId) {
        AddGroupMapperDTO addGroupMapperDTO = new AddGroupMapperDTO();
        BeanUtils.copyProperties(addGroupMapperDTO, addGroupDTO);
        addGroupMapperDTO.setGroupId(groupId);
        this.doctorMapper.addGroup(addGroupMapperDTO);
        return groupId;
    }

    @Override
    public void updateGroup(UpdateGroupDTO updateGroupDTO) {
        this.doctorMapper.updateGroup(updateGroupDTO);
    }

    @Override
    public void deleteGroup(DeleteGroupDTO deleteGroupDTO) {
        DoctorGroupPO doctorGroupPO = this.doctorMapper.getGroupById(deleteGroupDTO.getGroupId());
        if(doctorGroupPO == null){
            throw new BusinessException("分组不存在，请确认");
        }
        //处理该分组下的患者
        UpdateDoctorMemberDTO updateDoctorMemberDTO = new UpdateDoctorMemberDTO();
        updateDoctorMemberDTO.setGroupId(GroupConstant.DEFAULT_GROUP_ID);
        updateDoctorMemberDTO.setGroupIdWhere(deleteGroupDTO.getGroupId());
        this.memberService.updateMemberDoctor(updateDoctorMemberDTO);
        //删除分组
        this.doctorMapper.deleteGroup(deleteGroupDTO.getGroupId());
    }


    @Override
    public List<String> listTeamId(String doctorId) {
        return this.doctorCacheService.listTeamId(doctorId);
    }

    @Override
    public List<DoctorPO> listGroupDoctor(String doctorId) {
        return this.listMyTeamDoctor(doctorId);
    }

    @Override
    public List<DoctorServiceTimePO> listDoctorServiceTime(String doctorId, String weekCode,Integer openStatus) {
        return this.doctorServiceTimeMapper.listDoctorServiceTime(doctorId, weekCode, openStatus);
    }

    @Override
    public String addDoctorServiceTime(DoctorServiceTimePO doctorServiceTimePO) {
        String sid = DaoHelper.getSeq();
        doctorServiceTimePO.setSid(sid);
        checkDoctorServiceTime(doctorServiceTimePO);
        this.doctorServiceTimeMapper.addDoctorServiceTime(doctorServiceTimePO);
        return sid;
    }

    /**
     * 判断医生服务时间正确性
     * @param doctorServiceTimePO
     */
    private void checkDoctorServiceTime(DoctorServiceTimePO doctorServiceTimePO){
        if(doctorServiceTimePO.getStartTime().compareTo(doctorServiceTimePO.getEndTime()) >= 0){
            throw new BusinessException("服务时间设置错误~");
        }
    }

    @Override
    public void deleteDoctorServiceTime(String sid) {
        this.doctorServiceTimeMapper.deleteDoctorServiceTime(sid);
    }

    @Override
    public void updateDoctorServiceTime(DoctorServiceTimePO doctorServiceTimePO) {
        checkDoctorServiceTime(doctorServiceTimePO);
        this.doctorServiceTimeMapper.updateDoctorServiceTime(doctorServiceTimePO);
    }

    @Override
    public DoctorGroupPO getGroupById(String groupId) {
        return this.doctorMapper.getGroupById(groupId);
    }

    @Override
    public void addGroupWithId(String groupId, String groupName, String doctorId) {
        AddGroupMapperDTO addGroupMapperDTO = new AddGroupMapperDTO();
        addGroupMapperDTO.setGroupId(groupId);
        addGroupMapperDTO.setGroupName(groupName);
        addGroupMapperDTO.setDoctorId(doctorId);
        this.doctorMapper.addGroup(addGroupMapperDTO);
    }

    @Override
    public String addDoctorAndAccount(AddDoctorAndAccountDTO addDoctorAndAccountDTO) {
        //添加医生
        String doctorId = DaoHelper.getSeq();
        if (!StringUtils.isBlank(addDoctorAndAccountDTO.getPhoneNo())){
            PhoneUtil.isPhone(addDoctorAndAccountDTO.getPhoneNo());
        }
        DoctorPO doctorPO = new DoctorPO();
        doctorPO.setDoctorId(doctorId);
        BeanUtils.copyProperties(doctorPO, addDoctorAndAccountDTO);
        doctorPO.setBirthday(StringUtils.isBlank(doctorPO.getBirthday())?null:doctorPO.getBirthday());
        String qrCodeUrl = null;
        try{
            WechatQrcodePO wechatQrcodePO = createDoctorQrCode(doctorId);
            if(wechatQrcodePO != null){
                qrCodeUrl = wechatQrcodePO.getQrcodeUrl();
            }
        } catch (Exception e){
            log.warn("生成医生二维码失败,医生id:{}", doctorId ,e);
//            throw new BusinessException("生成医生微信二维码失败！");
        }
        doctorPO.setWechatUrl(qrCodeUrl);
        doctorPO.setRemark(addDoctorAndAccountDTO.getRemark());
        doctorPO.setIsVirtual(addDoctorAndAccountDTO.getIsVirtual());
        doctorPO.setIsAgentDoctor(addDoctorAndAccountDTO.getIsAgentDoctor() == null? 0 :addDoctorAndAccountDTO.getIsAgentDoctor());
        this.doctorMapper.addDoctor(doctorPO);
        addDefaultServiceTime(doctorId);
        addDefaultServiceRemind(doctorId);
        peopleNumberHandler(doctorPO);
        //添加团队关系
        this.addDoctorRelation(doctorId, doctorId);
        //添加村医关联
        addDoctorCommitteeRelation(addDoctorAndAccountDTO.getDoctorLevel(),addDoctorAndAccountDTO.getCommitteeIds(),doctorId);
        //添加账号
        AddUserDTO addUserDTO = new AddUserDTO();
        addUserDTO.setUserId(doctorId);
        addUserDTO.setUserName(addDoctorAndAccountDTO.getUserName());
        addUserDTO.setPassword(addDoctorAndAccountDTO.getPassword());
        addUserDTO.setUserType(UserConstant.USER_TYPE_DOCTOR);
        String uid = this.userService.addUser(addUserDTO);
        if(!StringUtils.isBlank(addDoctorAndAccountDTO.getRoleList())){
            //添加医生角色关联
            this.roleService.addUserRole(addDoctorAndAccountDTO.getRoleList(), doctorId, AuthorityConstant.TYPE_FRONT);
        }
        if(!StringUtils.isBlank(addDoctorAndAccountDTO.getRelateDoctorList())){
            //添加医生助理关系
            addDoctorIdsRelation(addDoctorAndAccountDTO.getRelateDoctorList(), doctorId);
        }
        //添加数据权限
        this.addDoctorDepartment(addDoctorAndAccountDTO.getDataAuth(),doctorId,addDoctorAndAccountDTO.getDepartId(),addDoctorAndAccountDTO.getHospitalId());


        //添加医生可切换医院
        if (null != addDoctorAndAccountDTO.getSwitchHospital() && addDoctorAndAccountDTO.getSwitchHospital() == 1 && !StringUtils.isBlank(addDoctorAndAccountDTO.getHospitalIds())){
            String[] hospitalIdList = addDoctorAndAccountDTO.getHospitalIds().split(",");
            DoctorHospitalPO doctorHospital = null;
            for (String hospitalId : hospitalIdList) {
                doctorHospital = new DoctorHospitalPO();
                doctorHospital.setSid(DaoHelper.getSeq());
                doctorHospital.setDoctorId(doctorId);
                doctorHospital.setHospitalId(hospitalId);
                this.doctorMapper.addDoctorHospital(doctorHospital);
            }
        }
        return doctorId;
    }


    private void addDoctorCommitteeRelation(Integer doctorLevel,String committeeIds,String doctorId){
        if (null != doctorLevel && !StringUtils.isBlank(committeeIds) &&
                doctorLevel.equals(DoctorConstant.DOCTOR_LEVEL_VILLAGE)){
            List<String> committeeIdList = Arrays.asList(committeeIds.split(","));
            List<DoctorCommitteeRelationPO> addList = new ArrayList<>(committeeIdList.size());
            committeeService.deleteRelationByDoctorId(doctorId);
            committeeIdList.forEach(e ->
            {
                DoctorCommitteeRelationPO doctorCommitteeRelationPO = new DoctorCommitteeRelationPO();
                doctorCommitteeRelationPO.setSid(DaoHelper.getSeq());
                doctorCommitteeRelationPO.setCommitteeId(e);
                doctorCommitteeRelationPO.setDoctorId(doctorId);
                addList.add(doctorCommitteeRelationPO);
            });
            committeeService.insertBatchDoctorRelation(addList);
        }else if (null != doctorLevel  &&
                !doctorLevel.equals(DoctorConstant.DOCTOR_LEVEL_VILLAGE)){
            committeeService.deleteRelationByDoctorId(doctorId);
        }
    }



    /**
     * 添加数据权限
     * @param dataAuth
     * @param doctorId
     * @param departId
     * @param hospitalId
     */
    private void addDoctorDepartment(String dataAuth,String doctorId,String departId,String hospitalId){
        if (!StringUtils.isBlank(dataAuth)){
            //添加数据权限
            Map<String, Object> map = JsonSerializer.jsonToMap(dataAuth);
            if (null != map.get("type") && !StringUtils.isBlank(map.get("type").toString())){
                String authType = map.get("type").toString();
                DoctorDepartmentPO departmentPO = null;
                //个人权限  添加个人科室
                if ("1".equals(authType)){
                    departmentPO = new DoctorDepartmentPO();
                    departmentPO.setDepartmentId(departId);
                    departmentPO.setDoctorId(doctorId);
                    this.doctorCacheService.addDoctorDepartment(departmentPO);
                }else if ("2".equals(authType)){
                    //全院权限  添加医院所有科室
                    //查询医院所有科室
                    List<DepartmentPO> departmentList = this.departmentService.listDepartmentByHospitalId(hospitalId);
                    if (null != departmentList && departmentList.size() > 0){
                        for (DepartmentPO po : departmentList) {
                            departmentPO = new DoctorDepartmentPO();
                            departmentPO.setDepartmentId(po.getDepartmentId());
                            departmentPO.setDoctorId(doctorId);
                            this.doctorCacheService.addDoctorDepartment(departmentPO);
                        }
                    }
                }else if ("3".equals(authType)){
                    //科室权限  添加选中科室
                    if (null != map.get("list") && !StringUtils.isBlank(map.get("list").toString())){
                        String[] lists = map.get("list").toString().split(",");
                        if (null != lists && lists.length > 0){
                            for (String dId : lists) {
                                departmentPO = new DoctorDepartmentPO();
                                departmentPO.setDepartmentId(dId);
                                departmentPO.setDoctorId(doctorId);
                                this.doctorCacheService.addDoctorDepartment(departmentPO);
                            }
                        }
                    }

                }
            }
        }
    }

    /**
     * 添加默认服务时间
     * @param doctorId
     */
    private void addDefaultServiceTime(String doctorId){
        DoctorServiceTimePO doctorServiceTimePO = new DoctorServiceTimePO();
        doctorServiceTimePO.setDoctorId(doctorId);
        doctorServiceTimePO.setStartTime("00:00");
        doctorServiceTimePO.setEndTime("23:59");
        doctorServiceTimePO.setWeekCode("1,2,3,4,5,6,7");
        addDoctorServiceTime(doctorServiceTimePO);
    }

    /**
     * 添加默认提醒信息
     * @param doctorId
     */
    private void addDefaultServiceRemind(String doctorId){
        AddDoctorServiceRemindDTO remindDTO = new AddDoctorServiceRemindDTO();
        remindDTO.setDoctorId(doctorId);
        remindDTO.setRemindInfo("当前不在服务时间，请于服务时间进行咨询。");
        remindDTO.setOpenStatus(1);
        saveDoctorServiceRemindWithLock(remindDTO);
    }
    /**
     * 生成医生二维码
     * @param doctorId
     * @return
     */
    private WechatQrcodePO createDoctorQrCode(String doctorId){
        CreateStrQrCodeDTO createStrQrCodeDTO = new CreateStrQrCodeDTO();
        createStrQrCodeDTO.setForeignId(doctorId);
        createStrQrCodeDTO.setUploadOSS(true);
        createStrQrCodeDTO.setBusinessType(WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_DOCTOR);
        createStrQrCodeDTO.setDescription("医生二维码");
        WechatQrcodePO wechatQrcodePO = this.wechatQrCodeService.createForeverStrQrCode(createStrQrCodeDTO);
        return wechatQrcodePO;
    }

    public void addDoctorIdsRelation(String doctorIds, String foreignId) {
        String[] docIds = doctorIds.split(",");
        for (int i = 0; i < docIds.length; i++) {
            String docId = docIds[i];
            try{
                this.addDoctorRelation(docId,foreignId);
            }catch (BusinessException be){
                //TODO add warnLog
            }
        }
    }

    /**
     * 科室/医院 人数处理
     * @param doctorPO
     */
    private void peopleNumberHandler(DoctorPO doctorPO){
        //科室人数处理
        if(!StringUtils.isBlank(doctorPO.getDepartId())){
            UpdateDepartmentDTO updateDepartmentDTO = new UpdateDepartmentDTO();
            updateDepartmentDTO.setDepartmentId(doctorPO.getDepartId());
            updateDepartmentDTO.setDoctorNumberIncr(1);
            this.departmentService.updateDepartment(updateDepartmentDTO);
        }
        if(!StringUtils.isBlank(doctorPO.getHospitalId())){
            UpdateHospitalDTO updateHospitalDTO = new UpdateHospitalDTO();
            updateHospitalDTO.setHospitalId(doctorPO.getHospitalId());
            updateHospitalDTO.setPeopleNumberIncr(1);
            this.hospitalService.updateHospital(updateHospitalDTO);
        }
    }

    @Override
    public void addDoctorRelation(String doctorId, String foreignId) {
        this.doctorCacheService.addDoctorRelation(doctorId,foreignId);
    }

    @Override
    public void deleteDoctorRelation(String doctorId, String foreignId) {
        this.doctorCacheService.deleteDoctorRelation(doctorId, foreignId);
    }

    @Override
    public String refreshDoctorQrCode(String doctorId) {
        GetWechatQrCodeDTO getWechatQrCodeDTO = new GetWechatQrCodeDTO();
        getWechatQrCodeDTO.setBusinessType(WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_DOCTOR);
        getWechatQrCodeDTO.setForeignId(doctorId);
        WechatQrcodePO wechatQrcodePO = this.wechatQrCodeService.getWechatQrCode(getWechatQrCodeDTO);
        if(wechatQrcodePO == null){
            wechatQrcodePO = createDoctorQrCode(doctorId);
        }
        UpdateDoctorDTO updateDoctorDTO = new UpdateDoctorDTO();
        updateDoctorDTO.setDoctorId(doctorId);
        updateDoctorDTO.setWechatUrl(wechatQrcodePO.getQrcodeUrl());
        updateDoctor(updateDoctorDTO);
        return wechatQrcodePO.getQrcodeUrl();
    }

    @Override
    public DoctorTaskVO getDoctorTask(String doctorId,String hospitalId,String authority) {
        List<String> doctorIdList = this.listTeamId(doctorId);
        if (doctorIdList == null || doctorIdList.size() <=0 ){
            return getDefaultTaskVO();
        }
        //获取管理处方未完成数量
        GetPrescriptionDTO dto = new GetPrescriptionDTO();
        dto.setDoctorIdList(doctorIdList);
        dto.setComplete(false);
        List<PrescriptionPO> list = this.prescriptionMapper.listPrescriptionByParam(dto);
        Integer preNum = 0;
        if(null!=list && list.size()>=0 ){
            preNum = list.size();
        }

        //获取随访提醒数量
        ListFollowRemindDTO listFollowRemindDTO=new ListFollowRemindDTO();
        listFollowRemindDTO.setDoctorIdList(doctorIdList);
        Integer followNum=0;
        if (authority != null && authority.length()>0 && !"".equals(authority)){
            String [] str = authority.split(",");
            List<String> authorityList = Arrays.asList(str);
            listFollowRemindDTO.setFollowTypeList(authorityList);
            long followL = this.followMapper.countFollowRemind(listFollowRemindDTO);
            followNum =(int)followL;
        }


        //统计医生未读消息数
        UnReadDialogueDTO unReadDialogueDTO=new UnReadDialogueDTO();
        unReadDialogueDTO.setDoctorList(doctorIdList);
        UnReadDialoguePO unReadDialoguePO = this.dialogueService.countUnRead(unReadDialogueDTO);
        int msgNum = 0;
        if(null!=unReadDialoguePO){
            String udNum = unReadDialoguePO.getDoctorUnRead().toString();
            msgNum=Integer.parseInt(udNum);
        }

        //统计申请数
        ListDoctorMemberApplyDTO listDoctorMemberApplyDTO=new ListDoctorMemberApplyDTO();
        listDoctorMemberApplyDTO.setDoctorId(doctorId);
        long addL = this.memberApplyService.countDoctorMemberApply(listDoctorMemberApplyDTO);
        int addNum=0;
        addNum =(int)addL;

        //血糖异常患者数量(30天内未监测的数量+7天血糖异常的数量)
        ListBloodSugarByDayDTO listBloodSugarByDayDTO = new ListBloodSugarByDayDTO();
        listBloodSugarByDayDTO.setCodeDt("2");
        listBloodSugarByDayDTO.setDoctorId(doctorId);
        PageRequest page = new PageRequest();
        PageResult<SugarMemberVO> re = this.bloodSugarService.loadNotBloodSugarByDay(listBloodSugarByDayDTO, page);
        long wjc1 = re.getTotalRows();
        listBloodSugarByDayDTO.setCodeDt("1");
        re = this.bloodSugarService.loadNotBloodSugarByDay(listBloodSugarByDayDTO, page);
        long wjc2 = re.getTotalRows();
        Long abnormalNum = this.countDoctorsMemberAbnormalNumber(doctorIdList)+wjc1+wjc2;

        //订单数量
//        ListMemberPackageDTO listMemberPackageDTO = new ListMemberPackageDTO();
//        listMemberPackageDTO.setDoctorList(doctorIdList);
//        listMemberPackageDTO.setIsRead(0);
//        long orderNum = this.packageService.listOrderPackage(listMemberPackageDTO, page).getTotalRows();



        //套餐数据
        CountMemberPackageDTO countMemberPackageDTO = new CountMemberPackageDTO();
        countMemberPackageDTO.setDoctorId(doctorId);
        countMemberPackageDTO.setIsRead(0);
        long packageNum = this.packageService.countMemberPackage(countMemberPackageDTO);

        //分层分级改变提醒
        DoctorPO doctor = this.getDoctorById(doctorId);
        int dlcNum = this.differentLevelsService.getDifferentLevelsLogOfUnRead(doctor.getHospitalId());

        //高血压分层分级提醒次数
        ListMemberLevelDTO levelDTO = new ListMemberLevelDTO();
        levelDTO.setDoctorIdList(doctorIdList);
        levelDTO.setHospitalId(hospitalId);
        long levelNum = this.memberLevelService.countMemberLevelRemind(levelDTO);


        //虚拟病区转入申请
        QueryTransferApplyListParam queryTransferApplyListParam = new QueryTransferApplyListParam();
//        queryTransferApplyListParam.setDepartmentId(doctor.getDepartId());
        queryTransferApplyListParam.setApplyType(VirtualWardConstant.APPLY_TYPE_IN);
        queryTransferApplyListParam.setApplyStatus(VirtualWardConstant.APPLY_STATUS_NOT_DEAL);
        queryTransferApplyListParam.setHospitalId(doctor.getHospitalId());
        long virtualWardApplyIntoNum = this.virtualWardService.countVirtualWardApply(queryTransferApplyListParam);

        DoctorTaskVO taskVO = new DoctorTaskVO();
        taskVO.setFollowNum(followNum);
        taskVO.setPrescriptionNum(preNum);
        taskVO.setMsgNum(msgNum);
        taskVO.setAddNum(addNum);
        taskVO.setAbnormalNum(abnormalNum);
        taskVO.setOrderNum(packageNum);
        taskVO.setPackageNum(0L);
        taskVO.setDlcNum(dlcNum);
        taskVO.setLevelNum(levelNum);
        taskVO.setVirtualWardApplyIntoNum(virtualWardApplyIntoNum);
        return taskVO;
    }

    /**
     * 获取默认的铃铛提示值
     * @return
     */
    private DoctorTaskVO getDefaultTaskVO(){
        DoctorTaskVO taskVO = new DoctorTaskVO();
        taskVO.setFollowNum(0);
        taskVO.setPrescriptionNum(0);
        taskVO.setMsgNum(0);
        taskVO.setAddNum(0);
        taskVO.setAbnormalNum(0l);
        taskVO.setOrderNum(0L);
        taskVO.setDlcNum(0);
        taskVO.setLevelNum(0l);
        return taskVO;
    }

    @Override
    public void updateDoctor(UpdateDoctorDTO updateDoctorDTO) {
        if (!StringUtils.isBlank(updateDoctorDTO.getPhoneNo())){
            PhoneUtil.isPhone(updateDoctorDTO.getPhoneNo());
        }
        DoctorPO doctorPO = this.getDoctorById(updateDoctorDTO.getDoctorId());
        if(doctorPO == null){
            throw new BusinessException("医生不存在或已被删除");
        }
        departmentPeopleNumberHandler(updateDoctorDTO, doctorPO);
        if (updateDoctorDTO.getAddFlag()){ //医生没有登录账号信息需要添加
            //添加医生自己与自己团队关系
            List<DoctorRelationPO> list = this.doctorMapper.listDoctorRelation(updateDoctorDTO.getDoctorId(), updateDoctorDTO.getDoctorId());
            if (list == null || list.size() <= 0){
                this.addDoctorRelation(updateDoctorDTO.getDoctorId(), updateDoctorDTO.getDoctorId());
            }
            //添加账号
            AddUserDTO addUserDTO = new AddUserDTO();
            addUserDTO.setUserId(doctorPO.getDoctorId());
            addUserDTO.setUserName(updateDoctorDTO.getUserName());
            addUserDTO.setPassword(updateDoctorDTO.getPassword());
            addUserDTO.setUserType(UserConstant.USER_TYPE_DOCTOR);
            String uid = this.userService.addUser(addUserDTO);
        }
        //修改医生角色关联
        if(!StringUtils.isBlank(updateDoctorDTO.getRoleList())){
            this.roleService.updateUserRole(doctorPO.getDoctorId(), updateDoctorDTO.getRoleList(), AuthorityConstant.TYPE_FRONT);
        }
        if (null != updateDoctorDTO.getUpdateOrigin() && updateDoctorDTO.getUpdateOrigin() == 1){
            //更新医生数据权限
            //删除医生数据权限
            this.doctorCacheService.deleteDoctorDepartmentByDoctorId(updateDoctorDTO.getDoctorId());
            //插入医生数据权限
            this.addDoctorDepartment(updateDoctorDTO.getDataAuth(),updateDoctorDTO.getDoctorId(),updateDoctorDTO.getDepartId(),updateDoctorDTO.getHospitalId());

            //删除医生可切换医院列表 v5.1.1
            this.doctorMapper.deleteDoctorHospital(updateDoctorDTO.getDoctorId());
            if (!StringUtils.isBlank(updateDoctorDTO.getHospitalIds())){
                String[] hospitalIdList = updateDoctorDTO.getHospitalIds().split(",");
                DoctorHospitalPO doctorHospital = null;
                for (String hospitalId : hospitalIdList) {
                    doctorHospital = new DoctorHospitalPO();
                    doctorHospital.setSid(DaoHelper.getSeq());
                    doctorHospital.setDoctorId(updateDoctorDTO.getDoctorId());
                    doctorHospital.setHospitalId(hospitalId);
                    this.doctorMapper.addDoctorHospital(doctorHospital);
                }
            }
        }
        //添加村医关联
        addDoctorCommitteeRelation(updateDoctorDTO.getDoctorLevel(),updateDoctorDTO.getCommitteeIds(),updateDoctorDTO.getDoctorId());
        this.doctorCacheService.updateDoctor(updateDoctorDTO);
    }

    /**
     * 科室医生数量处理
     * @param updateDoctorDTO
     * @param doctorPO
     */
    private void departmentPeopleNumberHandler(UpdateDoctorDTO updateDoctorDTO, DoctorPO doctorPO){
        if(StringUtils.isBlank(updateDoctorDTO.getDepartId())){
            return;
        }
        if(updateDoctorDTO.getDepartId().equals(doctorPO.getDepartId())){
            return;
        }
        //新科室人数+1
        UpdateDepartmentDTO updateDepartmentDTO = new UpdateDepartmentDTO();
        updateDepartmentDTO.setDepartmentId(updateDoctorDTO.getDepartId());
        updateDepartmentDTO.setDoctorNumberIncr(1);
        this.departmentService.updateDepartment(updateDepartmentDTO);
        if(StringUtils.isBlank(doctorPO.getDepartId())){
            return;
        }
        //旧科室人数-1
        updateDepartmentDTO = new UpdateDepartmentDTO();
        updateDepartmentDTO.setDepartmentId(doctorPO.getDepartId());
        updateDepartmentDTO.setDoctorNumberIncr(-1);
        this.departmentService.updateDepartment(updateDepartmentDTO);
    }

    @Override
    public List<DoctorPO> listDoctorInId(List<String> idList) {
        return this.doctorMapper.listDoctorInId(idList);
    }

    @Override
    public PageResult<DoctorPO> listDoctor(PageRequest pr,ListDoctorDTO listDoctorDTO) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<DoctorPO> list = this.doctorMapper.listDoctor(listDoctorDTO);
        return new PageResult<>(list);
    }

    @Override
    public List<DoctorPO> listDoctorOne(ListDoctorDTO listDoctorDTO) {
        return this.doctorMapper.listDoctor(listDoctorDTO);
    }

    @Override
    public DoctorAndApplyInfoVO getDoctorAndApplyInfo(String memberId, String doctorId) {
        DoctorAndApplyInfoVO doctorAndApplyInfoVO = new DoctorAndApplyInfoVO();
        //获取医生信息
        DoctorPO doctorPO = this.getDoctorById(doctorId);
        doctorAndApplyInfoVO.setDoctor(doctorPO);
        int bindStatus = BIND_STATUS_NO;
        //获取医患关系
        CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
        countDoctorMemberDTO.setDoctorId(doctorId);
        countDoctorMemberDTO.setMemberId(memberId);
        long count = this.memberService.countMemberDoctor(countDoctorMemberDTO);
        if(count > 0){
            bindStatus =BIND_STATUS_YES;
        }else{
            DoctorMemberApplyPO doctorMemberApplyPO = this.memberApplyService.getDoctorMemberApply(memberId, doctorId);
            if(doctorMemberApplyPO == null){
                bindStatus = BIND_STATUS_NO;
            }else if(MemberApplyConstant.APPLY_STATUS_ING == doctorMemberApplyPO.getApplyStatus()){
                bindStatus = BIND_STATUS_ING;
            }else if(MemberApplyConstant.APPLY_STATUS_PASS == doctorMemberApplyPO.getApplyStatus()){
                bindStatus = BIND_STATUS_YES;
            }else if(MemberApplyConstant.APPLY_STATUS_REFUSE == doctorMemberApplyPO.getApplyStatus()){
                bindStatus = BIND_STATUS_NO;
            }
        }
        doctorAndApplyInfoVO.setBindStatus(bindStatus);
        return doctorAndApplyInfoVO;
    }

    @Override
    public List<MemberDoctorListPO> listMemberDoctor(String memberId) {
        return this.doctorMapper.listMemberDoctor(memberId);
    }

    @Override
    public void setAttendDoctor(String memberId, String doctorId) {
        UpdateDoctorMemberDTO updateDoctorMemberDTO = new UpdateDoctorMemberDTO();
        updateDoctorMemberDTO.setMemberId(memberId);
        updateDoctorMemberDTO.setIsAttend(MemberDoctorConstant.MEMBER_DOCTOR_ATTEND_NO);
        this.memberService.updateMemberDoctor(updateDoctorMemberDTO);
        updateDoctorMemberDTO = new UpdateDoctorMemberDTO();
        updateDoctorMemberDTO.setMemberId(memberId);
        updateDoctorMemberDTO.setDoctorId(doctorId);
        updateDoctorMemberDTO.setIsAttend(MemberDoctorConstant.MEMBER_DOCTOR_ATTEND_YES);
        this.memberService.updateMemberDoctor(updateDoctorMemberDTO);
    }

    @Override
    public PageResult<DoctorPO> listDoctorAndLogin(PageRequest pr, ListDoctorDTO listDoctorDTO) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<DoctorPO> list = this.doctorMapper.listDoctorAndLogin(listDoctorDTO);
        return new PageResult<>(list);
    }

    @Override
    public List<GroupsVO> listGroups(ListGroupsDTO dto) {
        //1 查询糖尿病患者人数 2 查询高血压患者人数 null 查询所有人数
        Integer type = null;
        if (DoctorConstant.ENTITY_TYPE_TNB.equals(dto.getEntityType())){
            type = 1;
        }else if (DoctorConstant.ENTITY_TYPE_GXY.equals(dto.getEntityType())){
            type = 2;
        }
        List<GroupsVO> vos = new ArrayList<>(2);
        GroupsVO<List<DoctorGroupVO>> vo1 = new GroupsVO<List<DoctorGroupVO>>();
        vo1.setName("住院患者");
        vo1.setOrderIndex(0);
        // 住院分组
        List<DoctorGroupPO> groupList1 = new ArrayList<DoctorGroupPO>();
        if (!StringUtils.isBlank(dto.getHospitalId())){
            groupList1 = this.doctorCacheService.listDepartmentByHid(dto.getHospitalId() ,dto.getDoctorId());
        }else{
            groupList1 = this.doctorCacheService.listDepartmentByDid(dto.getDoctorId());
            //virtualWardGroupHandler(groupList1,doctorId);
        }
        List<DoctorGroupVO> items1 = new ArrayList<>();
        for (DoctorGroupPO doctorGroupPO : groupList1) {
            DoctorGroupVO vo = new DoctorGroupVO();
            BeanUtils.copyProperties(vo, doctorGroupPO);
            vo.setPeopleNumber(0L);
            if(dto.isCountPeopleNumber()){
                ListMemberDTO listMemberDTO = new ListMemberDTO();
                listMemberDTO.setGroupId(doctorGroupPO.getGroupId());
                listMemberDTO.setKeyWord(dto.getKeyWord());
                listMemberDTO.setType(type);
                vo.setPeopleNumber(this.memberService.countDepartMember(listMemberDTO));
            }
            items1.add(vo);
        }
        vo1.setItems(items1);
        vo1.setType(GroupConstant.IN_HOSPITAL);
        vos.add(vo1);

        GroupsVO<List<Map<String,Object>>> vo2 = new GroupsVO<List<Map<String,Object>>>();
        vo2.setName("门诊/居家患者");
        vo2.setOrderIndex(1);
        // 门诊/居家患者分组
        List<DoctorPO> listMyDoctor = new ArrayList<>();
        if (!StringUtils.isBlank(dto.getHospitalId())){
            listMyDoctor = this.listDoctorByHospitalId(dto.getHospitalId());
        }else{
            listMyDoctor = this.listMyDoctor(dto.getDoctorId());
        }
        if(listMyDoctor!=null&&listMyDoctor.size()>0){
            List<Map<String,Object>> items2 = new ArrayList(listMyDoctor.size());
            for(DoctorPO doctor : listMyDoctor){
                Map<String,Object> items3 = new HashMap<>(3);
                items3.put("doctorId",doctor.getDoctorId());
                items3.put("doctorName",doctor.getDoctorName());
                List<DoctorGroupPO> groupList2 = this.doctorMapper.listGroup(doctor.getDoctorId());
                defaultGroupHandler(groupList2, doctor.getDoctorId());
                List<DoctorGroupVO> items4 = new ArrayList<>();
                for (DoctorGroupPO doctorGroupPO : groupList2) {
                    DoctorGroupVO vo = new DoctorGroupVO();
                    BeanUtils.copyProperties(vo, doctorGroupPO);
                    vo.setPeopleNumber(0L);
                    if(dto.isCountPeopleNumber()){
                        ListMemberDTO listMemberDTO = new ListMemberDTO();
                        listMemberDTO.setDoctorId(doctor.getDoctorId());
                        listMemberDTO.setKeyWord(dto.getKeyWord());
                        listMemberDTO.setGroupId(doctorGroupPO.getGroupId());
                        listMemberDTO.setType(type);
                        // 去除住院患者数量和近7日门诊的患者数量
                        if (!StringUtils.isBlank(dto.getHospitalId())){
                            listMemberDTO.setHospitalId(dto.getHospitalId());
                            vo.setPeopleNumber(this.memberService.countGroupMemberForHospital(listMemberDTO));
                        }else{
                            vo.setPeopleNumber(this.memberService.countGroupMemberForV4(listMemberDTO));
                        }
                    }
                    items4.add(vo);
                }
                items3.put("groups",items4);
                items2.add(items3);
            }
            vo2.setItems(items2);
            vo2.setType(GroupConstant.OUT_HOSPITAL);
        }
        vos.add(vo2);
        return vos;
    }

    @Override
    public long countDoctorsMemberAbnormalNumber(List<String> doctorIds){
        return this.bloodSugarMapper.countDoctorsMemberAbnormalNumber(doctorIds);
    }

    @Override
    public DoctorGroupVO getDoctorGroupByGroupId(String groupId) {
        DoctorGroupPO doctorGroupPO = this.getGroupById(groupId);

        DoctorGroupVO doctorGroupVO = new DoctorGroupVO();
        if (null != doctorGroupPO) {
            doctorGroupVO.setGroupId(doctorGroupPO.getGroupId());
            doctorGroupVO.setGroupName(doctorGroupPO.getGroupName());
        }
        return doctorGroupVO;
    }

    @Override
    public void updateDoctorGroupByGroupId(DoctorSessionBO doctorSessionBO, UpdateDoctorGroupDTO updateDoctorGroupDTO) {
        String doctorId = updateDoctorGroupDTO.getDoctorId();
        if (StringUtils.isBlank(doctorId)){
            doctorId = doctorSessionBO.getDoctorId();
            updateDoctorGroupDTO.setDoctorId(doctorId);
        }
        //1、修改分组名称
        UpdateGroupDTO doctorGroupBO = new UpdateGroupDTO();
        doctorGroupBO.setGroupId(updateDoctorGroupDTO.getGroupId());
        doctorGroupBO.setGroupName(updateDoctorGroupDTO.getGroupName());
        this.doctorMapper.updateGroup(doctorGroupBO);

        //2、获取当前医生的患者id
        DoctorMemberBO doctorMemberBO = new DoctorMemberBO();
        doctorMemberBO.setGroupId(updateDoctorGroupDTO.getGroupId());
        doctorMemberBO.setDoctorId(doctorId);
        List<String> memberIdDeleteList = this.doctorMapper.listMemberIdsByGroupId(doctorMemberBO);

        //3、筛选出新增患者，和删除患者
        List<String> memberIdAddList = new ArrayList<>();
        if (!StringUtils.isBlank(updateDoctorGroupDTO.getMemberIds())) {
            String[] memberIds = updateDoctorGroupDTO.getMemberIds().split(",");
            if (null != memberIds && memberIds.length > 0) {
                for (String memberId: memberIds) {
                    // 判断医生团队和患者是否存在医患关系，不存在则添加
                    AddMemberDoctorRelateDTO addMemberDoctorRelateDTO = new AddMemberDoctorRelateDTO();
                    addMemberDoctorRelateDTO.setMemberId(memberId);
                    addMemberDoctorRelateDTO.setDoctorId(doctorId);
                    //医患关系创建方式
                    addMemberDoctorRelateDTO.setRelationWay(updateDoctorGroupDTO.getRelationWay());
                    synchronized (this){
                        this.memberService.addMemberDoctorRelate(addMemberDoctorRelateDTO);
                    }
                    // 做业务
                    memberIdAddList.add(memberId);
                }
            }
        }
        //获取出公共的memberIds
        List<String> memberIdCommonList = new ArrayList<>();
        memberIdCommonList.addAll(memberIdAddList);
        memberIdCommonList.retainAll(memberIdDeleteList);

        //4、新增memberIds
        memberIdAddList.removeAll(memberIdCommonList);
        if (null != memberIdAddList && memberIdAddList.size() > 0) {
            String[] memberIds = memberIdAddList.toArray(new String[memberIdAddList.size()]);
            this.updateDoctorMemberGroupId(updateDoctorGroupDTO, memberIds);
        }

        //5、删除memberIds
        memberIdDeleteList.removeAll(memberIdCommonList);
        if (null != memberIdDeleteList && memberIdDeleteList.size() > 0) {
            UpdateDoctorGroupDTO deleteDoctorGroupDTO = new UpdateDoctorGroupDTO();
            deleteDoctorGroupDTO.setGroupId("0");
            deleteDoctorGroupDTO.setDoctorId(doctorId);
            String[] memberIds = memberIdDeleteList.toArray(new String[memberIdDeleteList.size()]);
            this.updateDoctorMemberGroupId(deleteDoctorGroupDTO, memberIds);
        }
    }

    @Override
    public String insertDoctorGroup(DoctorSessionBO doctorSessionBO, InsertDoctorGroupDTO insertDoctorGroupDTO) {
        String doctorId = insertDoctorGroupDTO.getDoctorId();
        if (StringUtils.isBlank(doctorId)){
            doctorId = doctorSessionBO.getDoctorId();
        }
        //1、新增分组
        String groupId = DaoHelper.getSeq();
        AddGroupMapperDTO doctorGroupPO = new AddGroupMapperDTO();
        doctorGroupPO.setGroupId(groupId);
        doctorGroupPO.setGroupName(insertDoctorGroupDTO.getGroupName());
        doctorGroupPO.setDoctorId(doctorId);
        this.doctorMapper.addGroup(doctorGroupPO);

        //2、更新医患关系表的分组id
        if (!StringUtils.isBlank(insertDoctorGroupDTO.getMemberIds())) {
            String[] memberIds = insertDoctorGroupDTO.getMemberIds().split(",");
            if (null != memberIds && memberIds.length > 0) {
                UpdateDoctorGroupDTO updateDoctorGroupDTO = new UpdateDoctorGroupDTO();
                updateDoctorGroupDTO.setGroupId(groupId);
                updateDoctorGroupDTO.setDoctorId(doctorId);
                this.updateDoctorMemberGroupId(updateDoctorGroupDTO, memberIds);
            }
        }
        return groupId;
    }

    @Override
    public List<DoctorPO> listDoctorByDepartId(String departmentId) {
        ListDoctorDTO doctorDTO = new ListDoctorDTO();
        doctorDTO.setDepartmentId(departmentId);
        return this.doctorMapper.listDoctor(doctorDTO);
    }

    @Override
    public DoctorPO getDoctorByWordNo(String workNo, String hospitalId) {
        DoctorPO doctorPO = this.doctorMapper.getDoctorByWorkNo(workNo,hospitalId);
        return doctorPO;
    }

    /**
     * 填充批量修改分组id
     * @author 李左河
     * @date 2018/7/26 14:36
     * @param updateDoctorGroupDTO
     * @param memberIds
     * @return void
     *
     */
    private void updateDoctorMemberGroupId(UpdateDoctorGroupDTO updateDoctorGroupDTO, String[] memberIds) {
        if (null != memberIds && memberIds.length > 0) {
            DoctorMemberBO doctorMemberBO = new DoctorMemberBO();
            doctorMemberBO.setDoctorId(updateDoctorGroupDTO.getDoctorId());
            doctorMemberBO.setGroupId(updateDoctorGroupDTO.getGroupId());
            doctorMemberBO.setMemberIds(memberIds);
            doctorMemberBO.setModifyTimestamp(System.currentTimeMillis());
            this.doctorMapper.batchUpdateDoctorMemberGroupId(doctorMemberBO);
        }
    }

    @Override
    public List<DoctorHospitalBO> listDoctorHospitalByDoctorId(String doctorId) {
        return this.doctorMapper.listDoctorHospitalByDoctorId(doctorId);
    }

    @Override
    public List<DoctorPO> listDoctorByHospitalId(String hospitalId) {
        return this.doctorMapper.listDoctorByHospitalId(hospitalId);
    }

    @Override
    public List<DoctorPO> listMyTeamDoctor(String doctorId) {
        return this.doctorCacheService.listMyTeamDoctor(doctorId);
    }

    @Override
    public void openOrCloseDoctorServiceTime(DoctorServiceTimePO doctorServiceTimePO) {
        this.doctorServiceTimeMapper.updateDoctorServiceTime(doctorServiceTimePO);
    }

    @Override
    public DoctorServiceTimePO getDoctorServiceTimeById(String sid) {
        return this.doctorServiceTimeMapper.getDoctorServiceTimeById(sid);
    }

    @Override
    public void saveDoctorServiceRemindWithLock(AddDoctorServiceRemindDTO dto) {
        //通过医生id查询医生服务时间外提示信息,存在就更新,否则就新增
        DoctorServiceRemindPO remindPO = this.doctorServiceTimeMapper.getDoctorServiceRemindByDoctorId(dto.getDoctorId(),null);
        DoctorServiceRemindPO serviceRemindPO = new DoctorServiceRemindPO();
        BeanUtils.copyProperties(serviceRemindPO,dto);
        if (null != remindPO){
            this.doctorServiceTimeMapper.modifyDoctorServiceRemind(serviceRemindPO);
        }else{
            serviceRemindPO.setSid(DaoHelper.getSeq());
            this.doctorServiceTimeMapper.insertDoctorServiceRemind(serviceRemindPO);
        }
    }

    @Override
    public DoctorServiceRemindPO getDoctorServiceRemindByDoctorId(String doctorId,Integer openStatus) {
        return this.doctorServiceTimeMapper.getDoctorServiceRemindByDoctorId(doctorId,openStatus);
    }
    @Override
    public PageResult<DoctorServiceTimePO> pageListDoctorServiceTime(String doctorId, String weekCode,Integer openStatus,PageRequest page) {
        PageHelper.startPage(page.getPage(),page.getRows());
        List<DoctorServiceTimePO> list = this.doctorServiceTimeMapper.listDoctorServiceTime(doctorId, weekCode, openStatus);
        return new PageResult<DoctorServiceTimePO>(list);
    }

    @Override
    public List<DoctorDepartmentVO> listDoctorDepartment(String doctorId) {
        return this.doctorDepartmentMapper.listDoctorDepartment(doctorId);
    }

    @Override
    public List<DepartmentPO> listDoctorDepartHasAccountByHosId(String hospitalId) {
        return this.doctorDepartmentMapper.listDoctorDepartHasAccountByHosId(hospitalId);
    }

    /**
     * 申请状态 1 未申请或未有医患关系 2  申请通过或已有医患关系 3 申请中
     */
    private final static int BIND_STATUS_NO = 1;
    private final static int BIND_STATUS_YES = 2;
    private final static int BIND_STATUS_ING = 3;

    /*************************************************邪恶的分割线******************************************************
     * @version v6.0.0
     * @author: linyd
     * @date: 2020/02/27
     *****************************************************************************************************************/
    @Override
    public void saveDoctorKeyNotes(DoctorKeyNoteDTO dto) {
        //删除旧的关注项目
        this.doctorMapper.deleteKeyNotes(dto.getHospitalId(),dto.getDoctorId(),dto.getMemberId(),dto.getInHos(),dto.getType());
        //保存新的关注项目
        String[] ids = dto.getKeyIds().split(",");
        for (String id : ids) {
            AddDoctorKeyNoteDTO addDto = new AddDoctorKeyNoteDTO();
            BeanUtils.copyProperties(addDto,dto);
            addDto.setKeynoteId(id);
            addDto.setSid(DaoHelper.getSeq());
            this.doctorMapper.addDoctorKeyNotes(addDto);
        }
    }

    @Override
    public List<KeyNoteModel> listSelectedKeyNotes(ListSelectedKeyNoteDTO listSelectedKeyNoteDTO) {
        List<KeyNoteModel> list = this.doctorMapper.listSelectedKeyNotes(listSelectedKeyNoteDTO);
        if (list != null && list.size() > 0){
            return list;
        }
        //医生没有配置的时候选择默认的
        listSelectedKeyNoteDTO.setDoctorId("-1");
        list = this.doctorMapper.listSelectedKeyNotes(listSelectedKeyNoteDTO);
        if (list != null && list.size() > 0){
            return list;
        }
        //医院没有配置的时候选择默认系统的
        listSelectedKeyNoteDTO.setHospitalId("-1");
        list = this.doctorMapper.listSelectedKeyNotes(listSelectedKeyNoteDTO);
        return list;
    }

    @Override
    public List<KeyNoteModel> listHospitalAllKeyNotes(ListHospitalAllKeyNoteDTO listHospitalAllKeyNoteDTO) {
        List<KeyNoteModel> list = this.doctorMapper.listHospitalAllKeyNotes(listHospitalAllKeyNoteDTO);
        if(list==null ||list.size()<=0){
            listHospitalAllKeyNoteDTO.setHospitalId("-1");
            list = this.doctorMapper.listHospitalAllKeyNotes(listHospitalAllKeyNoteDTO);
        }
        return list;
    }

    @Override
    public List<DoctorDepartVO> listDoctorDepart(ListMemberDTO listMemberDTO) {
        List<DoctorDepartVO> items = new ArrayList<>();
        Set<String> departmentSet = new HashSet<>();
        List<DepartmentPO> departmentPOS = this.departmentService.listDoctorManageDepartment(listMemberDTO.getDoctorId());
        for(DepartmentPO doctorGroupPO : departmentPOS){
            DoctorDepartVO vo = new DoctorDepartVO();
            vo.setDepartId(doctorGroupPO.getDepartmentId());
            vo.setDepartName(doctorGroupPO.getDepartmentName());
            listMemberDTO.setGroupId(doctorGroupPO.getDepartmentId());
            vo.setPeopleNum(this.memberService.countDepartMember(listMemberDTO));
            vo.setIsVirtual(doctorGroupPO.getIsVirtual());
            items.add(vo);
            departmentSet.add(vo.getDepartId());
        }
        //虚拟病区科室处理
//        if(listMemberDTO.getVirtualWardFlag() != null && 1 == listMemberDTO.getVirtualWardFlag()){
//            if(!StringUtils.isBlank(listMemberDTO.getHospitalId())){
//                List<VirtualWardDepartmentPO> virtualWardDepartmentPOList = this.virtualWardService.listVirtualWardDepartment(listMemberDTO.getHospitalId());
//                for(VirtualWardDepartmentPO vo : virtualWardDepartmentPOList){
//                    if(departmentSet.contains(vo.getDepartmentId())){
//                        continue;
//                    }
//                    DoctorDepartVO departVO = new DoctorDepartVO();
//                    departVO.setDepartId(vo.getDepartmentId());
//                    departVO.setDepartName(vo.getDepartmentName());
//                    items.add(departVO);
//                }
//            }
//        }
        //有虚拟病区模块权限,则显示虚拟病区
        DepartmentPO departmentPO = this.departmentMapper.getDepartmentByIsVirtual(listMemberDTO.getHospitalId(),2);
        if(departmentPO != null && !"".equals(departmentPO)){
            DoctorDepartVO departVO = new DoctorDepartVO();
            departVO.setDepartId(departmentPO.getDepartmentId());
            departVO.setDepartName(departmentPO.getDepartmentName());
            departVO.setIsVirtual(departmentPO.getIsVirtual());
            items.add(departVO);
        }
        List<DoctorDepartVO> uniqueByName = items.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(DoctorDepartVO::getDepartId))), ArrayList::new)
        );
        ListUtils.sort(uniqueByName, true, "isVirtual");
        return uniqueByName;
/*        String doctorId = listMemberDTO.getDoctorId();
        String hospitalId = listMemberDTO.getHospitalId();
        if(!StringUtils.isBlank(hospitalId)){
            List<DepartmentPO> vos = this.doctorDepartmentMapper.listDoctorDepartHasAccountByHosId(hospitalId);
            List<DoctorDepartVO> items = new ArrayList<>();
            Set<String> departmentSet = new HashSet<>();
            for(DepartmentPO doctorGroupPO:vos){
                DoctorDepartVO vo = new DoctorDepartVO();
                vo.setDepartId(doctorGroupPO.getDepartmentId());
                vo.setDepartName(doctorGroupPO.getDepartmentName());
                listMemberDTO.setGroupId(doctorGroupPO.getDepartmentId());
                vo.setPeopleNum(this.memberService.countDepartMember(listMemberDTO));
                items.add(vo);
                departmentSet.add(vo.getDepartId());
            }

            //虚拟病区科室处理
            if(listMemberDTO.getVirtualWardFlag() != null && 1 == listMemberDTO.getVirtualWardFlag()){
                List<VirtualWardDepartmentPO> virtualWardDepartmentPOList = this.virtualWardService.listVirtualWardDepartment(hospitalId);
                for(VirtualWardDepartmentPO vo : virtualWardDepartmentPOList){
                    if(departmentSet.contains(vo.getDepartmentId())){
                        continue;
                    }
                    DoctorDepartVO departVO = new DoctorDepartVO();
                    departVO.setDepartId(vo.getDepartmentId());
                    departVO.setDepartName(vo.getDepartmentName());
                    items.add(departVO);
                }
            }
            return items;
        } else {
            List<DoctorDepartmentVO> vos = this.doctorDepartmentMapper.listDoctorDepartment(doctorId);
            List<DoctorDepartVO> items = new ArrayList<>();
            for(DoctorDepartmentVO doctorGroupPO:vos){
                DoctorDepartVO vo = new DoctorDepartVO();
                vo.setDepartId(doctorGroupPO.getDepartmentId());
                vo.setDepartName(doctorGroupPO.getDepartmentName());
                listMemberDTO.setGroupId(doctorGroupPO.getDepartmentId());
                vo.setPeopleNum(this.memberService.countDepartMember(listMemberDTO));
                items.add(vo);
            }
            virtualWardGroupHandler2(items,doctorId);
            return items;
        }*/
    }

    @Override
    public List<String> getDoctorIdsSwitch(String hospitalId) {
        return this.doctorMapper.getDoctorIdsSwitch(hospitalId);
    }

    @Override
    public void deleteDoctorHospitalByParam(List<String> doctorIdList, List<String> hospitalIdList) {
        this.doctorMapper.deleteDoctorHospitalByParam(doctorIdList,hospitalIdList);
    }

    @Override
    public DoctorPO getDoctorByOpenId(String openId) {
        return this.doctorMapper.getDoctorByOpenId(openId);
    }

    @Override
    public Map<String, Object> listBloodKetone(String memberId) {

        ListBloodKetoneDTO listBloodKetoneDTO = new ListBloodKetoneDTO();
        listBloodKetoneDTO.setMemberId(memberId);
        //根据患者id查询血酮记录
        List<BloodKetonePO> list = bloodKetoneMapper.listBloodKetone(listBloodKetoneDTO);
        //查询患者存不存在,根据患者id查询控制目标
        RangeBO rangeBO = memberService.getMemberRange(memberId);
        log.info("患者id:{},患者的控制目标血酮数据:{}", memberId, rangeBO.getHighKetone());

        //记录总次数,正常次数,异常次数
        Integer totalNum = list.size();
        Integer normalNum=0;
        Integer abnormalNum=0;

        if (list.size() != 0 && list != null){
            for (BloodKetonePO bloodKetonePO : list) {
                if (bloodKetonePO.getKetoneStatus() == 1){
                    normalNum = normalNum + 1;
                }else {
                    abnormalNum = abnormalNum + 1;
                }
            }
        }


        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("totalNum", totalNum);
        map.put("normalNum", normalNum);
        map.put("abnormalNum", abnormalNum);
        map.put("highKetone", rangeBO.getHighKetone());

        return map;
    }

    /**
     * 根据手机号获取医生
     * @param phoneNo
     * @return
     */
    @Override
    public DoctorPO queryDoctorByPhone(String phoneNo) {
        return doctorMapper.queryDoctorByPhone(phoneNo);
    }
    /**
     * 渠道获取医生的服务包所被用户购买的次数
     * @param doctorId
     * @return
     */
    @Override
    public List<Integer> getDoctorSumUser(String doctorId) {
        return doctorMapper.getDoctorSumUser(doctorId);
    }

    /**
     * 渠道获取医生当前患者数量
     * @param doctorId
     * @return
     */
    @Override
    public Integer getDoctorCurrentPatient(String doctorId) {
        return doctorMapper.getDoctorCurrentPatient(doctorId);
    }

//    @Override
//    public List<ProductPO> getDoctorPagesList(String doctorId) {
//        return doctorMapper.getDoctorPagesList(doctorId);
//    }

    @Override
    public List<DoctorTeamWithGroupsVO> listTeamWithGroups(ListTeamWithGroupsDTO dto) {
        DoctorPO sessionDoctor = getDoctorById(dto.getDoctorId());

        List<DoctorTeamWithGroupsVO> result = new ArrayList<>();

        List<DoctorPO> doctorList = null;
        if(!StringUtils.isBlank(dto.getHospitalId()) && DoctorConstant.SWITCH_HOS_YES == sessionDoctor.getSwitchHospital()){
            doctorList = listDoctorByHospitalId(dto.getHospitalId());
        }else{
            doctorList = listMyDoctor(dto.getDoctorId());
        }

        List<DoctorGroupPO> doctorGroup = null;
        List<DoctorGroupVO> groupList = null;
        DoctorTeamWithGroupsVO vo = null;

        for(DoctorPO doctor : doctorList){
            vo = new DoctorTeamWithGroupsVO();
            vo.setDoctorId(doctor.getDoctorId());
            vo.setDoctorName(doctor.getDoctorName());

            doctorGroup = this.doctorMapper.listGroup(doctor.getDoctorId());
            if(doctorGroup == null){
                doctorGroup = new ArrayList<>();
            }
            defaultGroupHandler(doctorGroup, doctor.getDoctorId());
            groupList = BeanUtils.copyListProperties(DoctorGroupVO.class ,doctorGroup);
            vo.setGroupList(groupList);

            result.add(vo);
        }

        return result;
    }

    @Override
    public Map<String, Object> listDoctorCommittee(String doctorId) {
        DoctorPO doctorById = doctorMapper.getDoctorById(doctorId);
        String hospitalId = doctorById.getHospitalId();
        HospitalCommitteePO param = new HospitalCommitteePO();
        param.setHospitalId(hospitalId);
        List<HospitalCommitteePO> hospitalCommiteeList  = committeeService.listHospitalCommittee(param);
        List<HospitalCommitteePO> doctorIdCommiteeList = committeeService.listCommitteeByDoctorId(doctorId);
        if (!hospitalCommiteeList.isEmpty() && !doctorIdCommiteeList.isEmpty()){
            for (int i = 0; i < hospitalCommiteeList.size(); i++) {
                HospitalCommitteePO committeePO = hospitalCommiteeList.get(i);
                if (doctorIdCommiteeList.contains(committeePO)){
                    hospitalCommiteeList.remove(committeePO);
                }
            }


        }
        Map<String, Object> result = new HashMap<>();
        result.put("selected",doctorIdCommiteeList);
        result.put("unSelected",hospitalCommiteeList);
        return result;
    }
}
