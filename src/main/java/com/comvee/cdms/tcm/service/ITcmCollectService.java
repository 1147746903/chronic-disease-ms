package com.comvee.cdms.tcm.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.tcm.model.dto.TcmCollectQueDTO;
import com.comvee.cdms.tcm.model.dto.TcmCollectTaskDTO;
import com.comvee.cdms.tcm.model.po.TcmCollectDataPO;
import com.comvee.cdms.tcm.model.po.TcmCollectQuePO;
import com.comvee.cdms.tcm.model.po.TcmCollectReportPO;
import com.comvee.cdms.tcm.model.po.TcmCollectTaskPO;

import java.util.List;

public interface ITcmCollectService {

    JSONObject createTcmCollectTask(TcmCollectTaskPO po);

    void startTcmCollectTask(String sid);

    void finishTcmCollectTask(String sid);

    void deleteTcmCollectTask(String sid);

    void savaTcmCollectData(TcmCollectDataPO data,Integer taskType, DoctorSessionBO webSession);

    PageResult<TcmCollectTaskPO> listTcmCollectTask(TcmCollectTaskDTO dto, PageRequest pr);

    List<TcmCollectQuePO> listTcmCollectQue(TcmCollectQueDTO dto);

    TcmCollectDataPO getTcmCollectDataByTaskId(String taskId);

    TcmCollectDataPO getLastTcmCollectData(String memberId);

    TcmCollectReportPO getTcmCollectReport(String taskId);
}
