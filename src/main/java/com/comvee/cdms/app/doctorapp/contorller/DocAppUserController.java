package com.comvee.cdms.app.doctorapp.contorller;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.app.doctorapp.model.app.HomePageRespModel;
import com.comvee.cdms.app.doctorapp.model.app.MobileVersionModel;
import com.comvee.cdms.app.doctorapp.service.DoctorServiceI;
import com.comvee.cdms.app.doctorapp.vo.MobileDefualtVO;
import com.comvee.cdms.authority.constant.AuthorityConstant;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.RequestTool;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.MobileRequest;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.dto.UpdateDoctorDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.other.bo.DoctorPushTokenBO;
import com.comvee.cdms.other.constant.DoctorPushConstant;
import com.comvee.cdms.other.service.DoctorPushService;
import com.comvee.cdms.packages.dto.ListMemberPackageDTO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.packages.vo.MemberPackageListVO;
import com.comvee.cdms.shiro.cfg.AuthenticationType;
import com.comvee.cdms.shiro.token.DoctorH5WxToken;
import com.comvee.cdms.shiro.token.UserNamePasswordToken;
import com.comvee.cdms.user.cfg.LoginLogConstant;
import com.comvee.cdms.user.dto.*;
import com.comvee.cdms.user.service.UserLoginService;
import com.comvee.cdms.user.service.UserService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/docapp/user")
public class DocAppUserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DoctorServiceI doctorServiceI;

    @Autowired
    private PackageService packageService;

    @Autowired
    private DoctorPushService doctorPushService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserLoginService userLoginService;

    /**
     * 通过用户手机号登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Result login(@Validated LoginDTO loginDTO, MobileDefualtVO mobileDefualtVO) {
        UserNamePasswordToken userLoginTypeToken = new UserNamePasswordToken(loginDTO.getUserName()
                , loginDTO.getPassword(), AuthenticationType.DOCTOR_WEB);
        userLoginTypeToken.setHost(RequestTool.getIpAddr());
        userLoginTypeToken.setUserType(LoginLogConstant.USER_TYPE_MOBILE);
        Subject subject = SecurityUtils.getSubject();
        subject.login(userLoginTypeToken);
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        savePushToken(mobileDefualtVO, doctorModel.getDoctorId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doctor", doctorModel);
        jsonObject.put("sessionId", subject.getSession().getId());
        return Result.ok(jsonObject);
    }

    /**
     * 保存推送token
     *
     * @param mobileDefualtVO
     * @param doctorId
     */
    private void savePushToken(MobileDefualtVO mobileDefualtVO, String doctorId) {
        if (StringUtils.isBlank(mobileDefualtVO.getPushTokenKey())) {
            return;
        }
        DoctorPushTokenBO doctorPushTokenBO = new DoctorPushTokenBO();
        doctorPushTokenBO.setDoctorId(doctorId);
        doctorPushTokenBO.setPushToken(mobileDefualtVO.getPushTokenKey());
        doctorPushTokenBO.setChannelCode(channelCodeHandler(mobileDefualtVO));
        doctorPushTokenBO.setDeviceType(DEVICE_TYPE_MAP.getOrDefault(mobileDefualtVO.getDev_type().toLowerCase(), DoctorPushConstant.DEVICE_TYPE_ANDROID));
        this.doctorPushService.addDoctorPushToken(doctorPushTokenBO);
    }

    /**
     * 渠道处理
     *
     * @param mobileDefualtVO
     * @return
     */
    private String channelCodeHandler(MobileDefualtVO mobileDefualtVO) {
        if ("android".equals(mobileDefualtVO.getDev_type().toLowerCase())) {
            return "11020";
        } else {
            return IOS_PUSH_PLAT_CODE.getOrDefault(mobileDefualtVO.getLoadFrom(), "11010");
        }
    }

    /**
     *  推送code与渠道映射表
     *  渠道 测试 1100200 正式 1100201 企业 1100202
     *  推送code  测试：11010  商店：11011  企业：11012
     */
    private final static Map<String, String> IOS_PUSH_PLAT_CODE = new HashMap<>();
    static {
        IOS_PUSH_PLAT_CODE.put("1100200", "11010");
        IOS_PUSH_PLAT_CODE.put("1100201", "11011");
        IOS_PUSH_PLAT_CODE.put("1100202", "11012");
    }

    /**
     * 设备类型映射
     */
    private final static Map<String, Integer> DEVICE_TYPE_MAP = new HashMap<>();

    static {
        DEVICE_TYPE_MAP.put("iphone", DoctorPushConstant.DEVICE_TYPE_IOS);
        DEVICE_TYPE_MAP.put("android", DoctorPushConstant.DEVICE_TYPE_ANDROID);
    }

    /**
     * 修改密码
     *
     * @param dto
     * @return
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public Result changePwd(@Validated PasswordDTO dto) {
        DoctorSessionBO doctorModel =  SessionTool.getWebSession();
        this.doctorServiceI.changePwd(dto, doctorModel.getUid());
        return Result.ok();
    }

    /**
     * 通过sessionid 获取医生信息
     *
     * @param request
     * @param response
     */
    @RequestMapping("/loadDoctorInfo")
    @ResponseBody
    public Result loadDoctorInfo() {
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        DoctorPO ddModel = this.doctorServiceI.loadDoctorInfo(doctorModel.getDoctorId());
        return Result.ok(ddModel);
    }

    /**
     * 修改医生信息（擅长、简介）
     *
     * @param request
     * @param response
     * @param doctor
     */
    @RequestMapping("/updateDoctor")
    @ResponseBody
    public Result updateDoctor(UpdateDoctorDTO doctor) {
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        doctor.setDoctorId(doctorModel.getDoctorId());
        this.doctorServiceI.updateDoctor(doctor);
        return Result.ok();
    }

    /**
     * 展示主动添加医生的患者列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("/showDoctorPatientList")
    @ResponseBody
    public Result showDoctorPatientList(PageRequest pr) {
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        PageResult result = this.doctorServiceI.showDoctorPatientList(pr, doctorModel.getDoctorId());
        return Result.ok(result);
    }

    /**
     * 加载医生所有患者套餐列表
     *
     * @param pr
     * @param listMemberPackageDTO
     * @return
     */
    @RequestMapping("listMemberPackage")
    @ResponseBody
    public Result listMemberPackage( PageRequest pr, @Validated ListMemberPackageDTO listMemberPackageDTO) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        listMemberPackageDTO.setDoctorId(doctorSessionBO.getDoctorId());
        PageResult<MemberPackageListVO> memberPackageListVOPageResult = this.packageService.listMemberPackage(listMemberPackageDTO, pr);
        return Result.ok(memberPackageListVOPageResult);
    }

    /**
     * 首页工作台的 所有统计数据
     *
     * @apiParam {Integer} dateType 统计血糖异常正常未测试时间段
     * 1:近一周，2：近2周，3：近一个月，4：近半年，5：全部,6:近2个月,7：近3个月
     * ,Integer dateType暂不传值
     * 默认：近一周 (传null或非1-7的值都为 近一周)
     * @apiParam {Integer} visitType 1 住院 2门诊居家 默认门诊居家
     */
    @RequestMapping("/statisticsHomeNumberes")
    @ResponseBody
    public Result statisticsHomeNumberes(Integer visitType) {
        if(visitType==null){
            visitType = 2;
        }

        HomePageRespModel homePageResp = this.doctorServiceI.statisticsHomeNumberes(visitType);
        return Result.ok(homePageResp);
    }

    /**
     * 查询更新版本号和地址
     *
     * @param mr
     * @return
     */
    @RequestMapping("/upgradeVersion")
    @ResponseBody
    public Result upgradeVersion(MobileRequest mr) {
        MobileVersionModel mobileVersionModel = this.doctorServiceI.getMobileNewstVersion(mr);
        return Result.ok(mobileVersionModel);
    }

    /**
     * 添加推送token
     * @param mobileDefualtVO
     * @return
     */
    @RequestMapping("addPushToken")
    @ResponseBody
    public Result addPushToken(MobileDefualtVO mobileDefualtVO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        savePushToken(mobileDefualtVO, doctorSessionBO.getDoctorId());
        return Result.ok();
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("logout")
    @ResponseBody
    public Result logout(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
//        this.userService.logout(doctorSessionBO.getDoctorId());
        SecurityUtils.getSubject().logout();
        return Result.ok();
    }

    /**
     * 加载用户权限
     * @return
     */
    @RequestMapping("listAuthority")
    @ResponseBody
    public Result listAuthority(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        Set<String> set = this.authorityService.listUserAuthority(doctorSessionBO.getDoctorId(), AuthorityConstant.TYPE_FRONT);
        return Result.ok(set);
    }

    /**
     * 医生微信openId登录
     * @param openId
     * @return
     */
    @RequestMapping("/loginForWx")
    @ResponseBody
    public Result loginForWx(String openId){
        ValidateTool.checkParameterIsNull("openId" ,openId);
        JSONObject result = doWxLogin(openId);
        return Result.ok(result);
    }

    /**
     * 医生登录并绑定微信openId
     * @param userName
     * @param password
     * @param openId
     * @return
     */
    @RequestMapping("/loginAndBindWx")
    @ResponseBody
    public Result loginAndBindWx(String userName ,String password ,String openId){
        ValidateTool.checkParameterIsNull("userName" ,userName);
        ValidateTool.checkParameterIsNull("password" ,password);
        ValidateTool.checkParameterIsNull("openId" ,openId);
        this.userLoginService.doctorLoginAndBindWx(userName ,password ,openId ,RequestTool.getIpAddr());
        JSONObject result = doWxLogin(openId);
        return Result.ok(result);
    }

    /**
     * 微信登出
     * @param openId
     * @return
     */
    @RequestMapping("/logoutForWx")
    @ResponseBody
    public Result logoutForWx(){
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        this.userLoginService.logoutFoxWx(doctorSession.getDoctorId());
        SecurityUtils.getSubject().logout();
        return Result.ok();
    }

    /**
     * 执行登录
     * @param openId
     * @return
     */
    private JSONObject doWxLogin(String openId){
        DoctorH5WxToken token = new DoctorH5WxToken(openId);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        JSONObject result = new JSONObject();
        result.put("doctor", subject.getPrincipal());
        result.put("sessionId", subject.getSession().getId());
        return result;
    }

    @RequestMapping("/loginAndBindWxWithCode")
    @ResponseBody
    public Result loginAndBindWxWithCode(String userName ,String password, String code){
        ValidateTool.checkParamIsNull(userName, "userName");
        ValidateTool.checkParamIsNull(password, "password");
        ValidateTool.checkParamIsNull(code, "code");
        String openId = userLoginService.loginAndBindWxWithCode(userName, password, code, RequestTool.getIpAddr());
        JSONObject result = doWxLogin(openId);
        return Result.ok(result);
    }

    @RequestMapping("/loginWithCode")
    @ResponseBody
    public Result loginWithCode(String code){
        ValidateTool.checkParamIsNull(code, "code");
        String openId = userLoginService.getWechatSession(code).getOpenId();
        if(StringUtils.isBlank(openId)){
            throw new BusinessException("获取openId失败");
        }
        JSONObject result = doWxLogin(openId);
        return Result.ok(result);
    }
}
