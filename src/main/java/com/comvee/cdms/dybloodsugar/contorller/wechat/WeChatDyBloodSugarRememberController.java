package com.comvee.cdms.dybloodsugar.contorller.wechat;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.dto.*;
import com.comvee.cdms.dybloodsugar.po.*;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DyRememberService;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weChatApp/dy/bloodSugarRemember")
public class WeChatDyBloodSugarRememberController {
    private final static Logger log = LoggerFactory.getLogger(WeChatDyBloodSugarRememberController.class);
    @Autowired
    private DyRememberService dyRememberService;
    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    /**
     * @api {post}/weChatApp/dy/bloodSugarRemember/setDietRemember.do 点击保存,保存饮食的记录
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName setDietRemember 点击保存,保存饮食的记录
     * @apiGroup weChatApp-remember
     * @apiVersion 0.0.1
     * @apiParam {String} memberId  患者id（必填）
     * @apiParam {String} paramCode  餐时（必填）
     * @apiParam {String} paramDt  用餐时间（必填）
     * @apiParam {String} paramIngredientJson  食材(包括其他食材) (必填)
     * @apiParam {Integer} operationType  1:患者  2:医生 (必填)
     * @apiParam {String} recordDt (yyyy-MM-dd HH:mm:ss) 记录日期（必填）
     * @apiParam {String} recordDtStart (2020-07-23 00:00:00) 记录日期（必填）
     * @apiParam {String} recordDtEnd (2020-07-23 23:59:59) 记录日期 必填）
     * @apiParam {String} sid 饮食记录主键id (选填）在点击列表修改时传sid,其他操作不需要传.
     * @apiSampleRequest  {post}/weChatApp/dy/bloodSugarRemember/setDietRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * @apiSuccessExample {json} Success-Response:
     * {
     * 	"dietFoodList": [{
     * 	    "multipleFoodId": "", //多选食材id
     * 		"multipleFood": "", //多选食材
     *        }],
     * 	"otherFood": "12" //其他食材
     * }
     */
    @RequestMapping("/setDietRemember")
    public Result setDietRemember(DyRememberDietDTO dto,String sid){
        MemberPO memberPO = SessionTool.getWechatSession();
        dto.setOperationId(memberPO.getMemberId());
        dto.setOperationType(DynamicBloodSugarConstant.ORIGIN_MEMBER);
        this.dyRememberService.setDietRemember(dto,sid);
        return Result.ok("");
    }

    /**
     * @api {post}/weChatApp/dy/bloodSugarRemember/listDietRemember.do 获取患者的饮食列表
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName listDietRemember 获取患者的饮食列表
     * @apiGroup weChatApp-remember
     * @apiVersion 0.0.1
     * @apiParam {String} memberId  患者id（必填）
     * @apiParam {String} recordDtStart  开始记录时间 yyyy-MM-dd HH:mm:ss（必填）
     * @apiParam {String} recordDtEnd  结束记录时间 yyyy-MM-dd HH:mm:ss（必填）
     * @apiParam {Integer} operationType  数据来源 1:患者 2:医生（必填）
     * @apiSampleRequest  {post}/weChatApp/dy/bloodSugarRemember/listDietRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * @apiSuccessExample {json} Success-Response:
     * {
     * 	"dietFoodList": [{
     * 	    "multipleFoodId": "", //多选食材id
     * 		"multipleFood": "", //多选食材
     *        }],
     * 	"otherFood": "12" //其他食材
     * }
     */
    @RequestMapping("/listDietRemember")
    public Result listDietRemember(@Validated DyRememberDietDTO dyRememberDietDTO){
        List<DyRememberDietPO> dyRememberDietPOList = this.dyRememberService.getDyDietRememberPOList(dyRememberDietDTO);
        return Result.ok(dyRememberDietPOList);
    }

    /**
     * 获取微信小程序患者饮食列表
     * @author
     * @date 2021/6/29
     * @param dyRememberDietDTO
     * @return com.comvee.cdms.common.wrapper.Result
     */
    @RequestMapping("/listDietRememberForWx")
    public Result listDietRememberForWx(@Validated DyRememberDietDTO dyRememberDietDTO){
        List<DyRememberDietPO> dyRememberDietPOList = this.dyRememberService.getDyDietRememberPOListForWXv2(dyRememberDietDTO);
        return Result.ok(dyRememberDietPOList);
    }


    /**
     * @api {post}/weChatApp/dy/bloodSugarRemember/getDietRemember.do 返回饮食记录数据
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName getDietRemember 返回饮食记录数据
     * @apiGroup weChatApp-remember
     * @apiVersion 0.0.1
     * @apiParam {String} sid  饮食记录的主键id（必填）
     * @apiSampleRequest  {post}/weChatApp/dy/bloodSugarRemember/getDietRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/getDietRemember")
    public Result getDietRemember(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        DyRememberDietPO dyRememberDietPO = this.dyRememberService.getDyDietRememberValues(sid);
        return  Result.ok(dyRememberDietPO);
    }

    /**
     * @api {post}/weChatApp/dy/bloodSugarRemember/updateDyDietRemember.do 删除饮食记录
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName updateDyDietRemember 删除饮食记录
     * @apiGroup weChatApp-remember
     * @apiVersion 0.0.1
     * @apiParam {String} sid  饮食记录的主键id（必填）
     * @apiSampleRequest  {post}/weChatApp/dy/bloodSugarRemember/updateDyDietRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/updateDyDietRemember")
    public Result updateDyDietRemember(String sid, String origin){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        if ("3".equals(origin)){
            this.dyRememberService.delLifeType(sid);
        }else {
            this.dyRememberService.updateDyDietRemember(sid);
        }

        return Result.ok("");
    }

    /**
     * @api {post}/weChatApp/dy/bloodSugarRemember/setSportRemember.do 添加运动记录
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName setSportRemember 添加运动记录
     * @apiGroup weChatApp-remember
     * @apiVersion 0.0.1
     * @apiParam {String} memberId  患者id（必填）
     * @apiParam {String} sportStartDt  开始运动时间 yyyy-MM-dd HH:mm:ss（必填）
     * @apiParam {String} sportEndDt  结束运动时间 yyyy-MM-dd HH:mm:ss（必填）
     * @apiParam {Integer} operationType  1:患者  2:医生 (必填)
     * @apiParam {String} recordDt (yyyy-MM-dd HH:mm:ss) 记录日期（必填）
     * @apiParam {String} recordDtStart (2020-07-23 00:00:00) 记录日期（必填）
     * @apiParam {String} recordDtEnd (2020-07-23 23:59:59) 记录日期 (必填）
     * @apiParam {String} sid 运动记录主键id (选填）在点击列表修改时传sid,其他操作不需要传.
     * @apiSampleRequest  {post}/weChatApp/dy/bloodSugarRemember/setSportRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/setSportRemember")
    public Result setSportRemember(DyRememberSportDTO dto,String sid){
        MemberPO memberPO = SessionTool.getWechatSession();
        dto.setOperationId(memberPO.getMemberId());
        dto.setOperationType(DynamicBloodSugarConstant.ORIGIN_MEMBER);
        this.dyRememberService.setSportRemember(dto,sid);
        return Result.ok("");
    }

    /**
     * @api {post}/weChatApp/dy/bloodSugarRemember/listSportRemember.do 获取医生记录的患者运动列表
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName listSportRemember 获取医生记录的患者运动列表
     * @apiGroup weChatApp-remember
     * @apiVersion 0.0.1
     * @apiParam {String} memberId  患者id（必填）
     * @apiParam {String} recordDtStart  开始记录时间 yyyy-MM-dd HH:mm:ss（必填）
     * @apiParam {String} recordDtEnd  结束记录时间 yyyy-MM-dd HH:mm:ss（必填）
     * @apiParam {Integer} operationType  数据来源 1:医生  0:患者（必填）
     * @apiSampleRequest  {post}/weChatApp/dy/bloodSugarRemember/listSportRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/listSportRemember")
    public Result listSportRemember(@Validated DyRememberSportDTO dyRememberSportDTO){
        List<DyRememberSportPO> dyRememberSportPOList = this.dyRememberService.getDySportRememberPOList(dyRememberSportDTO);
        return Result.ok(dyRememberSportPOList);
    }

    /**
     * @api {post}/weChatApp/dy/bloodSugarRemember/getSportRememberBySid.do 处理运动记录数据回填
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName getSportRememberBySid 处理运动记录数据回填
     * @apiGroup weChatApp-remember
     * @apiVersion 0.0.1
     * @apiParam {String} sid  运动记录的主键id（必填）
     * @apiSampleRequest  {post}/weChatApp/dy/bloodSugarRemember/getSportRememberBySid.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/getSportRememberBySid")
    public Result getSportRememberBySid(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        DyRememberSportPO dyRememberSportPO = this.dyRememberService.getDySportRememberValues(sid);
        return Result.ok(dyRememberSportPO);
    }

    /**
     * @api {post}/weChatApp/dy/bloodSugarRemember/deleteSportRemember.do 删除运动记录数据回填
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName deleteSportRemember 删除运动记录数据回填
     * @apiGroup weChatApp-remember
     * @apiVersion 0.0.1
     * @apiParam {String} sid  运动记录的主键id（必填）
     * @apiSampleRequest  {post}/weChatApp/dy/bloodSugarRemember/deleteSportRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/deleteSportRemember")
    public Result deleteSportRemember(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        this.dyRememberService.deleteSportRemember(sid);
        return Result.ok("");
    }

    /**
     * @api {post}/weChatApp/dy/bloodSugarRemember/addBloodSugarRememberRemark.do 添加血糖备注信息
     * @author 林雨堆
     * @time 2019/04/19 17:00
     * @apName 添加血糖备注信息
     * @apiGroup weChatApp-remember
     * @apiVersion 0.0.1
     * @apiParam {GlucometerRequest} mr 默认参数
     * @apiParam {String} bid  血糖记录编号（必填）
     * @apiParam {String} content  备注内容（必填）
     * @apiParam {String} sid  点击列表的做修改的时候传值,其他操作不传值
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 0000 OK
     * {
     *     "obj":null,
     *     "code":"0",
     *     "msg":"添加成功",
     *     "success":true
     * }
     * or
     * {
     *     "obj":null,
     *     "code":"-1",
     *     "msg":"登录超时，请重新登录",
     *     "success":true
     * }
     * or
     * {
     *     "obj":null,
     *     "code":"-1",
     *     "msg":"系统错误",
     *     "success":false
     * }
     */
    @RequestMapping("addBloodSugarRememberRemark")
    public Result addBloodSugarRememberRemark(@Validated DyBloodSugarRemarkDTO dto, String sid){
        MemberPO memberPO = SessionTool.getWechatSession();
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_MEMBER);
        dto.setOperatorId(memberPO.getMemberId());
        return Result.ok(this.dyBloodSugarService.addBloodSugarRememberRemark(dto,sid));
    }

    /**
     * 获取食物列表
     * @author
     * @date 2021/6/7
     * @param foodName
     * @return com.comvee.cdms.common.wrapper.Result
     */
    @RequestMapping("getFoodList")
    public Result getFoodList(PageFoodItemDTO dto){
        return Result.ok(dyRememberService.getFoodList(dto));
    }


    /**
     * 根据id获取食物
     * @author
     * @date 2021/6/7
     * @param sid
     * @return com.comvee.cdms.common.wrapper.Result
     */
    @RequestMapping("getSingleFoodItem")
    public Result getSingleFoodItem(@NotBlank(message = "id不能为空") String id){

        return Result.ok(dyRememberService.getSingleFoodItem(id));
    }

    /**
     * 添加修改饮食记录
     * @author
     * @date 2021/6/7
     * @param dyRememberDietDTO
     * @param sid
     * @return com.comvee.cdms.common.wrapper.Result
     */
    @RequestMapping("addDietRemember")
    public Result addDietRemember(DyRememberDietDTO dyRememberDietDTO, String sid){
        MemberPO memberPO = SessionTool.getWechatSession();
        dyRememberDietDTO.setOperationId(memberPO.getMemberId());
        dyRememberDietDTO.setOperationType(DynamicBloodSugarConstant.ORIGIN_MEMBER);
        dyRememberService.addDietRemember(dyRememberDietDTO, sid);
        return Result.ok("");
    }

    /**
     * 菜品识别
     * @author
     * @date 2021/6/7
     * @param request
     * @return com.comvee.cdms.common.wrapper.Result
     */
//    @RequestMapping("dishIdentify")
    public Result dishIdentify(HttpServletRequest request){
        Map result = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        /** 页面控件的文件流 **/
        MultipartFile multipartFile = multipartRequest.getFile("file");
        String name = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(name);

        try{
            result = dyRememberService.dishIdentify(multipartFile.getInputStream(), extension);
            return Result.ok(result);
        }catch (Exception e){
            log.error("菜品识别失败");
            e.printStackTrace();
        }

        return new Result("1", "菜品识别失败", false);

    }

    /**
     * 添加修改生活方式
     * @author
     * @date 2021/6/7
     * @param po
     * @return com.comvee.cdms.common.wrapper.Result
     */
    @RequestMapping("addLifeType")
    public Result addLifeType(@Valid  DyRememberDietPO po){
        ValidateTool.checkParameterIsNull("content", po.getContent());
        MemberPO memberPO = SessionTool.getWechatSession();
        po.setMemberId(memberPO.getMemberId());
        po.setOperationId(po.getMemberId());
        po.setOperationType(DynamicBloodSugarConstant.ORIGIN_MEMBER);
        po.setIsLife("1");
        dyRememberService.addLifeType(po);

        return Result.ok();
    }

    /**
     * 获取生活方式记录
     * @author
     * @date 2021/5/10
     * @param memberLifeTypeDTO
     * @return com.comvee.cdms.common.wrapper.Result
     */
    @RequestMapping("getLifeTypeList")
    public Result getLifeTypeList(MemberLifeTypeDTO memberLifeTypeDTO){
        List<MemberLifeTypePO> liftTypeList = dyRememberService.getLiftTypeList(memberLifeTypeDTO);
        return Result.ok(liftTypeList);
    }

    /**
     * 删除生活方式
     * @author
     * @date 2021/6/7
     * @param sid
     * @return com.comvee.cdms.common.wrapper.Result
     */
    @RequestMapping("delLifeType")
    public Result delLifeType(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        dyRememberService.updateDyDietRemember(sid);
        return Result.ok();
    }

    /**
     * 获取食物类别
     * @author
     * @date 2021/6/9
     * @param
     * @return com.comvee.cdms.common.wrapper.Result
     */
    @RequestMapping("getFoodType")
    public Result getFoodType(){

        return Result.ok(dyRememberService.getFoodType());
    }


}
