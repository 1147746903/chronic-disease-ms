package com.comvee.cdms.tMember.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 患者档案基本信息
 * </p>
 *
 * @author wqe
 * @since 2022-09-08
 */
@Getter
@Setter
@TableName("t_member")
@ApiModel(value = "患者档案基本信息")
public class TMember {

    /**
     * 患者id
     */
    @TableId(value = "member_id", type = IdType.AUTO)
    @ApiModelProperty(value = "患者id")
    private Long memberId;

    /**
     * 患者姓名
     */
    @ApiModelProperty(value = "患者姓名")
    private String memberName;

    /**
     * 患者姓名拼音
     */
    @ApiModelProperty(value = "患者姓名拼音")
    private String memberNamePy;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址")
    private String picUrl;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobilePhone;

    /**
     * 性别 1男2女
     */
    @ApiModelProperty(value = "性别 1男2女")
    private String sex;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

    private String idCard;

    /**
     * 职业
     */
    @ApiModelProperty(value = "职业")
    private String profession;

    /**
     * 是否有糖尿病 1:有 2:无
     */
    @ApiModelProperty(value = "是否有糖尿病 1:有 2:无")
    private String isDiabetes;

    /**
     * 糖尿病类型：1型SUGAR_TYPE_001)、2型(SUGAR_TYPE_002)、妊娠(SUGAR_TYPE_003)、其他(SUGAR_TYPE_004)	当糖尿病诊断选项选“确诊有”时出现糖尿病类型选项	是
     */
    @ApiModelProperty(value = "糖尿病类型")
    private String diabetesType;

    /**
     * 确诊日期
     */
    @ApiModelProperty(value = "确诊日期")
    private LocalDate diabetesDate;

    /**
     * 是否有效
     */
    @ApiModelProperty(value = "是否有效")
    private Integer isValid;

    /**
     * 插入时间
     */
    @ApiModelProperty(value = "插入时间")
    private LocalDateTime insertDt;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyDt;

    private String height;

    private String weight;

    /**
     * bmi
     */
    @ApiModelProperty(value = "bmi")
    private String bmi;

    /**
     * 收缩压
     */
    @ApiModelProperty(value = "收缩压")
    private String sbpPressure;

    /**
     * 舒张压
     */
    @ApiModelProperty(value = "舒张压")
    private String dbpPressure;

    /**
     * 患者详细信息
     */
    @ApiModelProperty(value = "患者详细信息")
    private String memberInfo;

    /**
     * 国家
     */
    @ApiModelProperty(value = "国家")
    private String country;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String address;

    /**
     * 高血压:1有、2无
     */
    @ApiModelProperty(value = "高血压:1有、2无")
    private String essentialHyp;

    /**
     * 原发性高血压:HYP_TYPE_001  继发性高血压:HYP_TYPE_002 其他:HYP_TYPE_003
     */
    @ApiModelProperty(value = "原发性高血压")
    private String hypType;

    /**
     * 高血压确诊日期
     */
    @ApiModelProperty(value = "高血压确诊日期")
    private String hypDate;

    /**
     * 是否有冠心病:确诊有(QZ01)、确诊无(QZ02)、未评估(QZ03)、疑似（界面中不显示）(QZ04)
     */
    @ApiModelProperty(value = "是否有冠心病")
    private String chd;

    /**
     * 是否有糖尿病肾病:确诊有SB01、确诊无SB02、未确诊  未评估SB03疑似SB04
     */
    @ApiModelProperty(value = "是否有糖尿病肾病")
    private String nephropathy;

    /**
     * 劳动强度 RCHDQD01：轻体力劳动 RCHDQD02：中体力劳动 RCHDQD03:重体力劳动 RCHDQD04:非劳动/卧床
     */
    @ApiModelProperty(value = "劳动强度")
    private String labourIntensity;

    /**
     * 心率
     */
    @ApiModelProperty(value = "心率")
    private String hreatRate;

    /**
     * 分级分层标识: 1平稳层、2中危层、3高危层
     */
    @ApiModelProperty(value = "分级分层标识: 1平稳层、2中危层、3高危层")
    private Integer levelHx;

    /**
     * 分级标识: 1级、2级、3级
     */
    @ApiModelProperty(value = "分级标识: 1级、2级、3级")
    private Integer dsmeHx;

    /**
     * 患者来源 1 web端医生添加  2 APP医生添加 3  小程序添加 4 筛查系统同步患者 5 其他
     */
    @ApiModelProperty(value = "患者来源")
    private Integer memberSource;

    /**
     * 是否肥胖 1有 0 无
     */
    @ApiModelProperty(value = "是否肥胖 1有 0 无")
    private Boolean fat;

    /**
     * 用户标签
     */
    @ApiModelProperty(value = "用户标签")
    private String memberTag;

    /**
     * 治疗方式：1.口服降糖药2.胰岛素3.胰岛素+口服降糖药4.其他
     */
    @ApiModelProperty(value = "治疗方式：1.口服降糖药2.胰岛素3.胰岛素+口服降糖药4.其他")
    private Integer treatment;

    /**
     * 患者姓名 拼音缩写
     */
    @ApiModelProperty(value = "患者姓名 拼音缩写")
    private String memberNamePys;

    /**
     * 社保卡号
     */
    @ApiModelProperty(value = "社保卡号")
    private String socialCard;

    /**
     * 委员会id
     */
    @ApiModelProperty(value = "委员会id")
    private Long committeeId;

    /**
     * 委员会名称
     */
    @ApiModelProperty(value = "委员会名称")
    private String committeeName;

}
