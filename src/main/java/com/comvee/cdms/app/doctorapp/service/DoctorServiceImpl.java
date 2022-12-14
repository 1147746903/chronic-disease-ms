package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.app.doctorapp.model.app.HomePageRespModel;
import com.comvee.cdms.app.doctorapp.model.app.MobileVersionModel;
import com.comvee.cdms.app.doctorapp.model.app.NormalSugerListResqModel;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.wrapper.MobileRequest;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.dto.UpdateDoctorDTO;
import com.comvee.cdms.doctor.mapper.DoctorMapper;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorCacheServiceI;
import com.comvee.cdms.dybloodsugar.service.DyMemberSensorService;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.dto.CountDoctorMemberDTO;
import com.comvee.cdms.member.dto.ListMemberDTO;
import com.comvee.cdms.member.mapper.MemberApplyMapper;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.DoctorMemberApplyPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.packages.mapper.PackageMapper;
import com.comvee.cdms.sign.mapper.BloodSugarMapper;
import com.comvee.cdms.user.dto.GetUserDTO;
import com.comvee.cdms.user.dto.PasswordDTO;
import com.comvee.cdms.user.dto.UpdateUserDTO;
import com.comvee.cdms.user.mapper.UserMapper;
import com.comvee.cdms.user.po.UserPO;
import com.comvee.cdms.user.service.UserService;
import com.comvee.cdms.user.tool.PasswordTool;
import com.comvee.cdms.user.tool.PushTokenTool;
import com.comvee.cdms.user.tool.SessionTool;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("doctorAppService")
public class DoctorServiceImpl implements DoctorServiceI{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PackageMapper packageMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private MemberApplyMapper memberApplyMapper;	
	
	@Autowired
	private BloodSugarMapper bloodSugarMapper;
	
	@Autowired
    private DoctorMapper doctorMapper;
	
	@Autowired
    private UserMapper userMapper;

	@Autowired
	private DoctorCacheServiceI doctorCacheService;

	@Autowired
	private DyMemberSensorService dyMemberSensorService;

	@Autowired
	private MemberService memberService;

	@Override
	public HomePageRespModel statisticsHomeNumberes(Integer visitType) {
		HomePageRespModel homePageRespModel = new HomePageRespModel();
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		//??????????????????
		String startDt = "";
		String endDt = DateHelper.getToday() + " 23:59:59";
		//1 ?????? 2 ??????
		Integer inHos = 1,outHos=2;
		if(outHos.equals(visitType)){
			startDt = DateHelper.plusDate(endDt,-7,DateHelper.DATETIME_FORMAT);

			ListMemberDTO listMemberDTO = new ListMemberDTO();
			listMemberDTO.setDoctorId(doctorModel.getDoctorId());
//			listMemberDTO.setType(1);
			//???????????????(?????????)
			Long patientNum = this.memberService.countGroupMemberForV4(listMemberDTO);
//			Long patientNum = this.memberMapper.countMemberDoctor(countDoctorMemberDTO);
			homePageRespModel.setPatientNum(String.valueOf(patientNum));
			//????????????
			CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
			countDoctorMemberDTO.setDoctorId(doctorModel.getDoctorId());
			countDoctorMemberDTO.setPayStatus(MemberDoctorConstant.PAY_STATUS_YES);
			//???????????????
			Long hasPayNum = this.memberMapper.countMemberDoctor(countDoctorMemberDTO);
			homePageRespModel.setHasPayNum(String.valueOf(hasPayNum));//?????????????????????
			//????????????
			Long noPayNum = patientNum - hasPayNum;
			homePageRespModel.setNoPayNum(String.valueOf(noPayNum));//?????????????????????

			//?????????
			Long abnormalNum = this.bloodSugarMapper.countDoctorMemberAbnormalNumber(doctorModel.getDoctorId(), startDt, endDt,doctorModel.getHospitalId());
			//?????????
			List<NormalSugerListResqModel> list = this.bloodSugarMapper.normalSugerList(doctorModel.getDoctorId(), startDt, endDt, null,doctorModel.getHospitalId());
			Long normalNum = new Integer(list.size()).longValue();
			//???????????????????????????
			Long unMeasuredNum = patientNum - abnormalNum - normalNum;
			Long bindSensorMemberNum = this.dyMemberSensorService.countMemberBindCount(doctorModel.getDoctorId());
			homePageRespModel.setAbnormalNum(String.valueOf(abnormalNum));
			homePageRespModel.setNormalNum(String.valueOf(normalNum));
			homePageRespModel.setUnMeasuredNum(String.valueOf(unMeasuredNum));
			homePageRespModel.setTotalMemberNum(patientNum);
			homePageRespModel.setBindSensorMemberNum(bindSensorMemberNum);
		} else if(inHos.equals(visitType)){
			startDt = DateHelper.getToday() + " 00:00:00";
			//??????????????????
			Long totalPatient = this.memberMapper.countInHospitalPatient(doctorModel.getDepartId());
			//??????????????????????????????
			Long abnormalNum = this.bloodSugarMapper.countInHospitalPatientAbnormalNumber(doctorModel.getDepartId() ,startDt ,endDt);
			//????????????????????????
			Long unMeasuredNum = this.bloodSugarMapper.countInHospitalPatientUnMeasureNumber(doctorModel.getDepartId() ,startDt ,endDt);
			//???????????????????????????
			Long normalNum = totalPatient - abnormalNum - unMeasuredNum;
			if(normalNum < 0L){
				normalNum = 0L;
			}
			//????????????????????????
			Long bindSensorMemberNum = this.dyMemberSensorService.countInHospitalBindSensorPatient(doctorModel.getDepartId());
			homePageRespModel.setAbnormalNum(abnormalNum.toString());
			homePageRespModel.setNormalNum(normalNum.toString());
			homePageRespModel.setUnMeasuredNum(unMeasuredNum.toString());
			homePageRespModel.setTotalMemberNum(totalPatient);
			homePageRespModel.setBindSensorMemberNum(bindSensorMemberNum);
		}
		return homePageRespModel;
	}

	@Override
	public void statisticsMemberAbnormalsNew(String payStatus, String startDt, String endDt) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * ????????????
	 * @param dto 		PasswordDTO
	 * @param uid       String
	 * @param verification
	 */
	@Override
	public void changePwd(PasswordDTO dto,String uid){
		GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setUid(uid);
        UserPO userPO = this.userMapper.getUser(getUserDTO);
        if(userPO == null){
            throw new BusinessException("???????????????");
        }
        if(!userPO.getPassword().equals(PasswordTool.passwordSaltHandler(dto.getOldPassword(), userPO.getSalt()))){
            throw new BusinessException("???????????????");
        }
        String password = PasswordTool.passwordSaltHandler(dto.getNewPassword(), userPO.getSalt());
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setPassword(password);
        updateUserDTO.setUid(uid);
        this.userMapper.updateUser(updateUserDTO);
	}
	/**
	 * ??????doctorId ??????????????????
	 * @param doctorId       String
	 * @param verification
	 */
	@Override
	public DoctorPO loadDoctorInfo(String doctorId){
		return this.doctorCacheService.getDoctorById(doctorId);
	}
	
	/**
	 * ??????????????????
	 * @param updateDoctorDTO       UpdateDoctorDTO
	 */
	@Override
	public void updateDoctor(UpdateDoctorDTO updateDoctorDTO){
		this.doctorCacheService.updateDoctor(updateDoctorDTO);
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param pr       		 PagerModel
	 * @param doctorId       String
	 * @param start       	 int
	 * @param rows           int
	 */
	@Override
	public PageResult<DoctorMemberApplyPO> showDoctorPatientList(PageRequest pr, String doctorId){
		PageHelper.startPage(pr.getPage(), pr.getRows());
		List<DoctorMemberApplyPO> list = this.memberApplyMapper.getDoctorMemberApplyList(doctorId);
		return new PageResult(list);
	}

	@Override
	public MobileVersionModel getMobileNewstVersion(MobileRequest mr) {
        Map<String,Object> m = new HashMap();
        m.put("osType", PushTokenTool.getOSTypeByDev(mr.getDevType()));
        return this.doctorMapper.getMobileNewestVersion(m);
	}

}
