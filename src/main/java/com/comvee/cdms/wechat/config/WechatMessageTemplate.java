package com.comvee.cdms.wechat.config;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.ProfileUtil;
import com.comvee.cdms.common.utils.ResourcesUtils;
import com.comvee.cdms.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author: suyz
 * @date: 2018/12/4
 */
public class WechatMessageTemplate {

    private final static Logger log = LoggerFactory.getLogger(WechatMessageTemplate.class);

    private final static String FILE_NAME = "wechat-template.properties";

    /**
     * 测量结果通知
     */
    public static final String MEASUREMENT_RESULT_NOTIFICATION = "measurement.result.notification";
    /**
     * 复查提醒
     */
    public static final String REVIEW_REMINDER = "review.reminder";
    /**
     * 报告生成通知
     */
    public static final String REPORT_GENERATION_NOTIFICATION = "report.generation.notification";
    /**
     *  随访提醒
     */
    public static final String FOLLOW_UP_REMINDER = "follow.up.reminder";
    /**
     * 智能看护启动提醒
     */
    public static final String INTELLIGENT_NURSING_START_REMINDER  = "intelligent.nursing.start.reminder";
    /**
     *  医生建议通知
     */
    public static final String DOCTOR_ADVICE_NOTIFICATION  = "doctor.advice.notification";
    /**
     * 学习进度提醒
     */
    public static final String LEARNING_PROGRESS_REMINDER  = "learning.progress.reminder";
    /**
     * 预约到期提醒
     */
    public static final String APPOINTMENT_EXPIRATION_REMINDER  = "appointment.expiration.reminder";
    /**
     * 报名成功提醒
     */
    public static final String SIGN_UP_SUCCESS_REMINDER  = "sign.up.success.reminder";
    /**
     * 健康预警
     */
    public static final String HEALTH_WARNING_REMINDER  = "health.warning.reminder";

    private static Properties properties = null;
    static {
        properties = new Properties();
        try {
            properties.load(ResourcesUtils.loadStream(ProfileUtil.getProfileActiveName(FILE_NAME)));
        } catch (Exception e) {
            log.error("微信模板消息配置初始化失败~", e);
        }
    }

    /**
     * 获取配置
     * @param key
     * @return
     */
    public static String getTemplateId(String key){
        String templateId = properties.getProperty(key);
        if(StringUtils.isBlank(templateId)){
            throw new BusinessException("微信模板消息id的值为空");
        }
        return templateId.trim();
    }

}
