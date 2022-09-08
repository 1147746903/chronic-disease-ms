/**    
 * 文件名：SessionTool.java    
 *    
 * 版本信息：    
 * 日期：2017-12-25    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.comvee.cdms.user.tool;

import com.comvee.cdms.admin.model.po.AdminPO;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.exception.SessionTimeOutException;
import com.comvee.cdms.common.utils.RequestTool;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import com.comvee.cdms.user.vo.ChannelLoginReturnVO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**  
 *   
 * @author: 苏友智
 * @time：2017-12-25 下午9:02:22     
 * @version:       
 */
@Component
public class SessionTool {

    private static SessionTool sessionTool;

    @Autowired
    private MemberService memberService;

    @PostConstruct
    public void init(){
        sessionTool = this;
        sessionTool.memberService = this.memberService;
    }

    /**
     * 从session获取用户对象
     * @author 苏友智
     * @time：2017-12-25 下午9:04:15
     */
    public static DoctorSessionBO getWebSession(){
        Object obj = getSubjectObject();
        if(obj instanceof DoctorSessionBO){
            return (DoctorSessionBO) obj;
        }
        throw new SessionTimeOutException(getFullErrMsg("不是医生的session"));
    }

    /**
     * 获取管理员session
     * @return
     */
    public static AdminPO getAdminSession(){
        Object obj = getSubjectObject();
        if(obj instanceof AdminPO){
            return (AdminPO) obj;
        }
        throw new SessionTimeOutException(getFullErrMsg("不是后台管理员的session"));
    }



    /**
     * 获取微信会话
     */
    public static MemberPO getWechatSession(){
        Object principal = getSubjectObject();
        if(principal instanceof MemberPO){
            return (MemberPO) principal;
        }
        if(principal instanceof UserWechatJoinPO){
            UserWechatJoinPO userWechatJoinPO = (UserWechatJoinPO) principal;
            if(userWechatJoinPO == null){
                throw new SessionTimeOutException(getFullErrMsg("无法找到小程序授权的session信息"));
            }
            MemberPO memberPO = sessionTool.memberService.getMemberByIdCache(userWechatJoinPO.getForeignId());
            if(memberPO == null){
                throw new BusinessException("-2" ,"小程序授权信息未绑定用户信息");
            }
            return memberPO;
        }
        throw new SessionTimeOutException(getFullErrMsg("不是小程序登陆产生的session"));
    }

    /**
     * 获取session对象
     * @return
     */
    private static Object getSubjectObject(){
        Object obj = SecurityUtils.getSubject().getPrincipal();
        if(obj == null){
            throw new SessionTimeOutException(getFullErrMsg("找不到匹配的session记录"));
        }
        return obj;
    }

    private static String getFullErrMsg(String msg){
        HttpServletRequest request = RequestTool.getRequest();
        String paramSessionId = request.getParameter(SESSION_KEY);
        String cookieSessionId = RequestTool.findCookie(request.getCookies() ,SESSION_KEY);
        return msg + "  -> 请求参数中的session值:[" + paramSessionId + "],cookie中的session值:[" + cookieSessionId + "]";
    }


    private static final String SESSION_KEY = "JSESSIONID";

}
