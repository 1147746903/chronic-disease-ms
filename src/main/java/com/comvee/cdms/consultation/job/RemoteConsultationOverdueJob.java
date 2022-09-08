package com.comvee.cdms.consultation.job;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.consultation.constant.RemoteConsultationConstant;
import com.comvee.cdms.consultation.mapper.RemoteConsultationMapper;
import com.comvee.cdms.consultation.model.param.UpdateRemoteConsultationWhereParam;
import com.comvee.cdms.consultation.model.po.RemoteConsultationPO;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 远程会诊过期的申请状态处理（30秒执行一次）
 */
@Component
public class RemoteConsultationOverdueJob{

    @Autowired
    private RemoteConsultationMapper remoteConsultationMapper;

    @XxlJob("remoteConsultationOverdueJob")
    public ReturnT<String> execute(String param) throws Exception {
        String remindDt = DateHelper.getNowDate();
        RemoteConsultationPO update = new RemoteConsultationPO();
        update.setFromRemindDt(remindDt);
        update.setFromRemindStatus(RemoteConsultationConstant.REMIND_STATUS_YES);
        update.setConsultationStatus(RemoteConsultationConstant.CONSULTATION_STATUS_OVERDUE);

        UpdateRemoteConsultationWhereParam where = new UpdateRemoteConsultationWhereParam();
        where.setConsultationStatusList(Collections.singletonList(RemoteConsultationConstant.CONSULTATION_STATUS_UN_CONFIRM));
        where.setOverdueDtValidate(true);
        this.remoteConsultationMapper.updateRemoteConsultation(update ,where);
        return ReturnT.SUCCESS;
    }
}
