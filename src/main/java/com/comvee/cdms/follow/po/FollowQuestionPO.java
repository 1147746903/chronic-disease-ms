package com.comvee.cdms.follow.po;

/**
 * Created with IntelliJ IDEA.
 * User: xuds
 * Date: 2020/8/18
 * Time: 11:01
 * Description: 问卷数据字典表
 **/
public class FollowQuestionPO {

    private String optionId;  // 主键(选项ID)
    private String optionCode;  // 选项code
    private String optionContent;  // 选项内容
    private String subQuestionCode; // 问题code
    private String subQuestionContent;  // 问题内容
    private String optionType;  // 答题类型（1、单选 2、多选 3、填写）
    private String followType;  // 随访类型（15、糖尿病病人问卷调查；16、三个月随访；17、半年随访）
    private String parentName;  // 父类名称 (模块）
    private String foreignkey;  // 自定义，备用

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionCode() {
        return optionCode;
    }

    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getSubQuestionCode() {
        return subQuestionCode;
    }

    public void setSubQuestionCode(String subQuestionCode) {
        this.subQuestionCode = subQuestionCode;
    }

    public String getSubQuestionContent() {
        return subQuestionContent;
    }

    public void setSubQuestionContent(String subQuestionContent) {
        this.subQuestionContent = subQuestionContent;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getForeignkey() {
        return foreignkey;
    }

    public void setForeignkey(String foreignkey) {
        this.foreignkey = foreignkey;
    }
}
