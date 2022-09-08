package com.comvee.cdms.dybloodsugar.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorDataAuthBO;
import com.comvee.cdms.doctor.mapper.DoctorMapper;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.po.DoctorRelationPO;
import com.comvee.cdms.dybloodsugar.bo.DynamicBloodSugarMAGEItemBO;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.dto.*;
import com.comvee.cdms.dybloodsugar.mapper.*;
import com.comvee.cdms.dybloodsugar.po.*;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DyMemberSensorService;
import com.comvee.cdms.dybloodsugar.tool.DynamicBloodSugarTool;
import com.comvee.cdms.dybloodsugar.vo.*;
import com.comvee.cdms.hospital.model.bo.CheckinInfoBO;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.member.mapper.MemberCheckinInfoMapper;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.DoctorMemberPO;
import com.comvee.cdms.member.po.MemberCheckinInfoPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.constant.WechatQrCodeConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.dto.CreateStrQrCodeDTO;
import com.comvee.cdms.other.dto.GetWechatQrCodeDTO;
import com.comvee.cdms.other.po.WechatQrcodePO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.other.service.WechatQrCodeService;
import com.comvee.cdms.virtualward.mapper.VirtualWardMapper;
import com.comvee.cdms.virtualward.model.po.VirtualWardPO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("dyMemberSensorService")
public class DyMemberSensorServiceImpl implements DyMemberSensorService {
    @Autowired
    private DYMemberSensorPOMapper dyMemberSensorPOMapper;

    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    @Autowired
    private WechatQrCodeService wechatQrCodeService;

    @Autowired
    private DYMemberSensorShowPOMapper dyMemberSensorShowPOMapper;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private DYYPMachinePOMapper dyypMachinePOMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DyMemberSettingMapper dyMemberSettingMapper;

    @Autowired
    private DyBloodSugarInformMapper dyBloodSugarInformMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private MemberCheckinInfoMapper memberCheckinInfoMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private VirtualWardMapper virtualWardMapper;

    @Override
    public int bindSensor(BindSensorDTO dto) {
        if (dyMemberSensorPOMapper.hasValidSensor(dto.getSensorNo())) {
            throw new BusinessException("该探头已经被绑定，请不要重复绑定");
        }
        MemberPO member = this.memberService.getMemberById(dto.getMemberId());
        if (member == null) {
            throw new BusinessException("患者不存在,请确认");
        }
        //绑定
        DYMemberSensorPO record = new DYMemberSensorPO();
        String dateStr = DateHelper.getDate(new Date(), DateHelper.DATETIME_FORMAT);
        record.setInsertDt(dateStr);
        record.setOperationId(dto.getOperationId());
        record.setOperationType(dto.getOperationType());
        record.setMemberId(dto.getMemberId());
        record.setModifyDt(dateStr);
        record.setBindType(dto.getBindType());
        List<DYYPBloodSugarPO> dyypBloodSugarPOS = this.dyBloodSugarService.listDYYPBloodSugarByNoASC(dto.getSensorNo());
        String monitoringTimes = "0";
        Byte runStatus = null;
        if (dyypBloodSugarPOS != null && dyypBloodSugarPOS.size() > 0) {
            DYYPBloodSugarPO dyypBloodSugarPO = dyypBloodSugarPOS.get(0);
            monitoringTimes = dyypBloodSugarPO.getRecordTime().getTime() + "";
            if (dyypBloodSugarPOS.size() >= (96 * 14)) {
                runStatus = 3;
            } else {
                runStatus = 1;
            }
        } else {
            monitoringTimes = System.currentTimeMillis() + "";
            runStatus = 0;
        }
        String seq = DaoHelper.getSeq();
        record.setMonitoringTime(monitoringTimes);
        record.setRunStatus(runStatus);
        record.setSensorNo(dto.getSensorNo());
        record.setSid(seq);
        int count = this.dyMemberSensorPOMapper.insertSelective(record);

        //添加微信消息
        addBindOrUnBindWechatMessage(dto.getMemberId(), dto.getSensorNo(), dto.getMemberId(), seq,
                WechatMessageConstant.DATA_TYPE_BLOODSUGAR_BIND, dateStr.substring(0, 10));
        return count;
    }

    @Override
    public int unBindSensor(UnBindSensorDTO dto) {
        DYMemberSensorPO dyMemberSensor = getMemberSensorBySensorNo(dto.getSensorNo());
        if (dyMemberSensor == null) {
            throw new BusinessException("探头未被绑定");
        }
        if (!Constant.DEFAULT_FOREIGN_ID.equals(dyMemberSensor.getAppId())) {
            throw new BusinessException("请到院内系统进行解绑");
        }

        int count = this.dyMemberSensorPOMapper.unBindSensor(dto);

        String date = DateHelper.getDate(new Date(), DateHelper.DAY_FORMAT);
        //添加微信消息
        addBindOrUnBindWechatMessage(dto.getMemberId(), dto.getSensorNo(), dto.getMemberId(), dto.getSid(),
                WechatMessageConstant.DATA_TYPE_BLOODSUGAR_UNBIND, date);

        MemberSensorShowDTO showDTO = new MemberSensorShowDTO();
        showDTO.setSid(dto.getSid());
        List<DYMemberSensorShowPO> pos = this.dyMemberSensorShowPOMapper.listMemberSensorShow(showDTO);
        if (pos != null && pos.size() > 0) {
            //分享者添加微信消息
            for (DYMemberSensorShowPO po : pos) {
                addBindOrUnBindWechatMessage(dto.getMemberId(), dto.getSensorNo(), po.getMemberId(), dto.getSid(),
                        WechatMessageConstant.DATA_TYPE_BLOODSUGAR_UNBIND, date);
            }

            //清除分享记录
            this.dyMemberSensorShowPOMapper.deleteById(dto.getSid());
        }

        return count;
    }

    /**
     * 微信消息-探头绑定
     *
     * @param sendMemberId 绑定动态血糖的患者id
     * @param sensorNo     探头号
     * @param memberId     消息所有者的id
     * @param foreignId    动态血糖绑定主键id
     * @param dataType     消息类型
     */
    private void addBindOrUnBindWechatMessage(String sendMemberId, String sensorNo, String memberId, String foreignId, int dataType, String date) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sendMemberId", sendMemberId);
        jsonObject.put("date", date);
        jsonObject.put("sensorNo", sensorNo);
        AddWechatMessageDTO addWechatMessage = new AddWechatMessageDTO();
        addWechatMessage.setMemberId(memberId);
        addWechatMessage.setForeignId(StringUtils.isBlank(foreignId) ? "-1" : foreignId);
        addWechatMessage.setDataType(dataType);
        addWechatMessage.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessage);
    }

    @Override
    public PageResult<MySensorVO> pageSensorBySelf(PagerSensorDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getRows());
        List<DYMemberSensorPO> list = null;
        if (dto.getOrigin() == 1) {
            DyMemberSensorDTO sensorDTO = new DyMemberSensorDTO();
            sensorDTO.setMemberId(dto.getMemberId());
            sensorDTO.setValid(1);
            list = this.dyMemberSensorPOMapper.listMemberSensor(sensorDTO);
        } else if (dto.getOrigin() == 2) {
            list = this.dyMemberSensorPOMapper.listMemberAllSensor(dto.getMemberId());
        }

        PageResult<DYMemberSensorPO> poPageResult = new PageResult<>(list);
        PageResult<MySensorVO> pageResult = new PageResult<>();
        if (poPageResult.getRows() != null && poPageResult.getRows().size() > 0) {
            List<MySensorVO> vos = new ArrayList<>(poPageResult.getRows().size());
            for (DYMemberSensorPO po : poPageResult.getRows()) {
                //2:计算探头从开始时间到现在使用了多少天.
                DYYPBloodSugarPO dyypBloodSugarPO = this.dyBloodSugarService.getDyBloodSugar(po.getSensorNo());
                MySensorVO vo = new MySensorVO();
                Date startDate = new Date(Long.parseLong(po.getMonitoringTime()));
                String startDt = DateHelper.getDate(startDate, DateHelper.DATETIME_FORMAT);
                vo.setStartDt(startDt);
                String endDt = DateHelper.plusDate(startDt, 14);
                vo.setEndDt(endDt);
                String endTime = DateHelper.getNowDate();
                //使用时间
                Integer day = DateHelper.dateCompareGetDay(endTime, startDt);
                if (day > 14) {
                    vo.setUserDays(14);
                } else {
                    vo.setUserDays(day);
                }
                if (dyypBloodSugarPO != null && dyypBloodSugarPO.getRecordTime() != null) {
                    //已获取的数据百分比
                    List<DYYPBloodSugarPO> dyBloodSugarList = dyBloodSugarService.getDyBloodSugarList(po.getSensorNo(), startDt, endTime);
                    Float resData = dyBloodSugarList.size() * 100 / (96 * 14F);
                    if (resData >= 100) {
                        vo.setAlreadyGetData("100%");
                    } else {
                        BigDecimal bigDecimal = new BigDecimal(resData);
                        //保留小数点后两位，四舍五入
                        Float data = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                        String alreadyGetData = new Float(data).toString() + "%";
                        vo.setAlreadyGetData(alreadyGetData);
                    }
                } else {
                    vo.setAlreadyGetData("0%");
                }

                vo.setSensorNo(po.getSensorNo());
                vo.setRunStatus(po.getRunStatus());
                vo.setSid(po.getSid());
                vo.setMemberId(po.getMemberId());
                vo.setBindType(po.getBindType());
                sensorOwnerNameHandler(vo);
                vos.add(vo);
            }
            pageResult.setTotalRows(poPageResult.getTotalRows());
            pageResult.setTotalPages(poPageResult.getTotalPages());
            pageResult.setPageNum(poPageResult.getPageNum());
            pageResult.setPageSize(poPageResult.getPageSize());
            pageResult.setRows(vos);
        }
        return pageResult;
    }

    /**
     * 探头所有者姓名处理
     *
     * @param vo
     */
    private void sensorOwnerNameHandler(MySensorVO vo) {
        MemberPO memberPO = this.memberService.getMemberByIdCache(vo.getMemberId());
        if (memberPO != null) {
            vo.setMemberName(memberPO.getMemberName());
        }
    }

    @Override
    public int bindShowSensor(BindShowSensorDTO dto) {

        DYMemberSensorPO dyMemberSensorPO = this.dyMemberSensorPOMapper.selectByPrimaryKey(dto.getMemberSensorSid());
        if (dyMemberSensorPO != null && dyMemberSensorPO.getMemberId().equals(dto.getMemberId())) {
            return 0;
        }

        MemberSensorShowDTO showDTO = new MemberSensorShowDTO();
        showDTO.setMemberSensorSid(dto.getMemberSensorSid());
        showDTO.setMemberId(dto.getMemberId());
        List<DYMemberSensorShowPO> pos = this.dyMemberSensorShowPOMapper.listMemberSensorShow(showDTO);
        if (pos != null && pos.size() > 0) {
            return 1;
        }
        DYMemberSensorShowPO record = new DYMemberSensorShowPO();
        String dateStr = DateHelper.getDate(new Date(), DateHelper.DATETIME_FORMAT);
        record.setInserDt(dateStr);
        record.setModifyDt(dateStr);
        record.setSid(DaoHelper.getSeq());
        record.setIsValid((byte) 1);
        record.setMemberId(dto.getMemberId());
        record.setMemberSensorSid(dto.getMemberSensorSid());
        int count = this.dyMemberSensorShowPOMapper.insertSelective(record);

        DYMemberSensorPO sensorPO = this.dyMemberSensorPOMapper.selectByPrimaryKey(dto.getMemberSensorSid());
        if (sensorPO != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sid", sensorPO.getSid());
            jsonObject.put("sendMemberId", sensorPO.getMemberId());
            jsonObject.put("date", dateStr.substring(0, 10));
            jsonObject.put("sensorNo", sensorPO.getSensorNo());
            jsonObject.put("monitorTime", DateHelper.getDate(new Date(Long.parseLong(sensorPO.getMonitoringTime())), DateHelper.DATETIME_FORMAT));
            AddWechatMessageDTO addWechatMessage = new AddWechatMessageDTO();
            addWechatMessage.setMemberId(dto.getMemberId());
            addWechatMessage.setForeignId(dto.getMemberSensorSid());
            addWechatMessage.setDataType(WechatMessageConstant.DATA_TYPE_BLOODSUGAR_SHARE_BIND);
            addWechatMessage.setDataJson(jsonObject.toJSONString());
            this.wechatMessageService.addWechatMessage(addWechatMessage);
/*            addBindOrUnBindWechatMessage(sensorPO.getMemberId(),sensorPO.getSensorNo(),dto.getMemberId(),
                    dto.getMemberSensorSid(),WechatMessageConstant.DATA_TYPE_BLOODSUGAR_SHARE_BIND,dateStr.substring(0,10));*/
        }
        return 2;
    }

    @Override
    public void unBindShowSensor(UnBindShowSensorDTO dto) {
        MemberSensorShowDTO showDTO = new MemberSensorShowDTO();
        showDTO.setMemberSensorSid(dto.getMemberSensorSid());
        showDTO.setMemberId(dto.getMemberId());
        this.dyMemberSensorShowPOMapper.deleteByDTO(showDTO);
    }

    @Override
    public PageResult<MyShowSensorVO> pageSensorByShow(PagerSensorOfShareDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getRows());
        if (1 == dto.getShareType()) {  //分享给我的
            List<MyShowSensorVO> sensorList = this.dyMemberSensorPOMapper.listShowSensorForOther(dto.getMemberId());
            PageResult<MyShowSensorVO> pageResult = new PageResult<>(sensorList);
            if (pageResult != null && pageResult.getRows().size() > 0) {
                for (MyShowSensorVO sensorVO : pageResult.getRows()) {
                    Date startDate = new Date(Long.parseLong(sensorVO.getMonitoringTime()));
                    String startDt = DateHelper.getDate(startDate, DateHelper.DATETIME_FORMAT);
                    sensorVO.setStartDt(startDt);
                    String endDt = DateHelper.plusDate2(startDt, 14);
                    sensorVO.setEndDt(endDt);
                    sensorVO.setType(1);  //1:动态血糖
                }
            }
            return pageResult;
        } else {  //我分享的
            List<MyShowSensorVO> voList = this.dyMemberSensorPOMapper.listShowSensorForMe(dto.getMemberId());
            PageResult<MyShowSensorVO> pageResult = new PageResult<>(voList);
            if (pageResult != null && pageResult.getRows().size() > 0) {
                for (MyShowSensorVO sensorVO : pageResult.getRows()) {
                    List<ShowSensorVO> vos = this.dyMemberSensorPOMapper.listShowSensorBySensorSid(sensorVO.getSid());
                    if (vos != null && vos.size() > 0) {
                        String receiveMemberName = "";
                        for (int i = 0; i < vos.size(); i++) {
                            if (i == vos.size() - 1) {
                                receiveMemberName += vos.get(i).getReceiveMemberName();
                            } else {
                                receiveMemberName += vos.get(i).getReceiveMemberName() + "、";
                            }
                        }
                        sensorVO.setReceiveMemberName(receiveMemberName);
                    }
                    Date startDate = new Date(Long.parseLong(sensorVO.getMonitoringTime()));
                    String startDt = DateHelper.getDate(startDate, DateHelper.DATETIME_FORMAT);
                    sensorVO.setStartDt(startDt);
                    String endDt = DateHelper.plusDate2(startDt, 14);
                    sensorVO.setEndDt(endDt);
                    sensorVO.setType(1);  //1:动态血糖
                }
            }
            return pageResult;
        }

    }

    @Override
    public MemberSensorInfoQrCodeVO createQrCodeForShareSensor(ShareSensorDTO dto) {
        DyMemberSensorDTO sensorDTO = new DyMemberSensorDTO();
        sensorDTO.setSensorNo(dto.getSensorNo());
        sensorDTO.setValid(1);
        List<DYMemberSensorPO> dyMemberSensors = this.dyMemberSensorPOMapper.listMemberSensor(sensorDTO);
        if (dyMemberSensors != null & dyMemberSensors.size() > 0) {
            GetWechatQrCodeDTO getWechatQrCodeDTO = new GetWechatQrCodeDTO();
            getWechatQrCodeDTO.setBusinessType(WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_MEMBER_SENSOR_SID);
            getWechatQrCodeDTO.setForeignId(dyMemberSensors.get(0).getSid());
            CreateStrQrCodeDTO createStrQrCodeDTO = new CreateStrQrCodeDTO();
            WechatQrcodePO wechatQrcodePO = wechatQrCodeService.getUnexpiredWechatQrCode(getWechatQrCodeDTO);
            if (wechatQrcodePO == null) {
                createStrQrCodeDTO.setDescription("患者动态血糖传感器绑定编号");
                createStrQrCodeDTO.setBusinessType(WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_MEMBER_SENSOR_SID);
                createStrQrCodeDTO.setForeignId(dyMemberSensors.get(0).getSid().toString());
                createStrQrCodeDTO.setExpireSeconds(14 * 24 * 60 * 60);//14天过期
                createStrQrCodeDTO.setUploadOSS(true);
                wechatQrcodePO = this.wechatQrCodeService.createTemporaryStrQrCode(createStrQrCodeDTO);
            }
            MemberSensorInfoQrCodeVO qrCodeVO = new MemberSensorInfoQrCodeVO();
            if (wechatQrcodePO != null) {
                qrCodeVO.setQrcodeUrl(wechatQrcodePO.getQrcodeUrl());
                qrCodeVO.setDataUrl(wechatQrcodePO.getDataUrl());
                qrCodeVO.setInvalidDt(wechatQrcodePO.getInvalidDt());
            } else {
                throw new BusinessException("生成分享二维码失败");
            }
            return qrCodeVO;
        }
        throw new BusinessException("找不到对应的探头绑定信息");
    }

    @Override
    public DYMemberSensorPO getSensorBySid(String sid) {
        return this.dyMemberSensorPOMapper.selectByPrimaryKey(sid);
    }

    @Override
    public DySensorStasticVO getSensorStatistics(ListMemberSensorDTO dto) {
        DySensorStasticVO stasticVO = new DySensorStasticVO();
        //获取佩戴人数
        if (dto.getInHos() == 0) {  //门诊居家
            Long number = this.dyMemberSensorPOMapper.countBindNumber(dto.getDoctorId(), dto.getHospitalId());
            stasticVO.setAllNumber(number == null ? 0 : number);
        } else {  //住院
            Long number = this.dyMemberSensorPOMapper.countBindNumberForHos(dto.getHospitalId());
            stasticVO.setAllNumber(number == null ? 0 : number);
        }
        return stasticVO;
    }

    @Override
    public PageResult<MemberSensorVO> listMemberSensor(ListMemberSensorDTO dto, PageRequest page) {
        PageHelper.startPage(page.getPage(), page.getRows());
        PageResult<MemberSensorVO> pageResult = new PageResult<>();
        if (dto.getInHos() == 0) {  //门诊居家
            List<MemberSensorVO> sensorVOS = this.dyMemberSensorPOMapper.listSensorMember(dto.getDoctorId(), dto.getHospitalId());
            pageResult = new PageResult<>(sensorVOS);
        } else {  //住院
            List<MemberSensorVO> sensorVOS = this.dyMemberSensorPOMapper.listSensorMemberForHos(dto.getHospitalId());
            pageResult = new PageResult<>(sensorVOS);
        }
        if (pageResult != null && pageResult.getRows().size() > 0) {
            for (MemberSensorVO sensorVO : pageResult.getRows()) {
                Date startDate = new Date(Long.parseLong(sensorVO.getMonitoringTime()));
                String startDt = DateHelper.getDate(startDate, DateHelper.DATETIME_FORMAT);
                sensorVO.setStartDt(startDt);
                String endDt = DateHelper.plusDate2(startDt, 14);
                sensorVO.setEndDt(endDt);
                MemberCheckinInfoPO memberCheckinInfoPO = this.memberCheckinInfoMapper.getMemberCheckinInfoByMemberId(sensorVO.getMemberId(), dto.getHospitalId());
                if (memberCheckinInfoPO != null) {
                    sensorVO.setBedNo(memberCheckinInfoPO.getBedNo());
                    sensorVO.setDepartName(memberCheckinInfoPO.getDepartmentName());
                }

            }
        }
        return pageResult;
    }


    @Override
    public void updateSensorMonitorTimes(String sensorNo) {
        DyMemberSensorDTO dto = new DyMemberSensorDTO();
        dto.setSensorNo(sensorNo);
        dto.setValid(1);
        List<Integer> runStatusList = new ArrayList() {
            {
                add(1);
                add(2);
            }
        };
        dto.setRunStatusList(runStatusList);
        List<DYMemberSensorPO> memberSensorPOS = this.dyMemberSensorPOMapper.listMemberSensor(dto);
        if (memberSensorPOS != null && memberSensorPOS.size() > 0) {
            DYMemberSensorPO dyMemberSensorPO = memberSensorPOS.get(0);
            List<DYYPBloodSugarPO> dyypBloodSugarPOS = this.dyBloodSugarService.listDYYPBloodSugarByNoASC(sensorNo);
            if (dyMemberSensorPO != null && dyypBloodSugarPOS != null && dyypBloodSugarPOS.size() > 0) {
                String monitoringTimes = "";
                Byte runStatus = null;
                DYYPBloodSugarPO dyypBloodSugarPO = dyypBloodSugarPOS.get(0);
                monitoringTimes = dyypBloodSugarPO.getRecordTime().getTime() + "";
                if (dyypBloodSugarPOS.size() >= (96 * 14)) {
                    runStatus = 3;
                } else {
                    runStatus = 1;
                }
                if (!StringUtils.isBlank(monitoringTimes) &&
                        !monitoringTimes.equals(dyMemberSensorPO.getMonitoringTime())) {
                    DYMemberSensorPO record = new DYMemberSensorPO();
                    record.setMonitoringTime(monitoringTimes);
                    if (runStatus != null && !runStatus.equals(dyMemberSensorPO.getRunStatus())) {
                        record.setRunStatus(runStatus);
                    }
                    record.setModifyDt(DateHelper.getDate(new Date(), DateHelper.DATETIME_FORMAT));
                    record.setSid(dyMemberSensorPO.getSid());
                    this.dyMemberSensorPOMapper.updateByPrimaryKeySelective(record);
                }
            }
        }
    }


    @Override
    public String getMachineEqRemind(String machineEq) {
        String remind = "";
        if (!StringUtils.isBlank(machineEq)) {
            //计算电量
            Double EQStandard = 3.7D;
            Double EQStandardMin = 3.65D;
            Double EQStandardMax = 3.8D;
            Double EQStandardFull = 0.55D;
            Double eq = Double.parseDouble(machineEq);
            Double eqValueRate = this.jisuandianliangbi(eq, EQStandardMin, EQStandardFull);
            if (eq <= EQStandard && eq >= EQStandardMin) {
                eqValueRate = this.jisuandianliangbi(eq, EQStandardMin, EQStandardFull);
                remind = "您的设备电量剩余" + eqValueRate + "%，为避免影响使用，请充电！";
            } else if (eq > EQStandard && eq <= EQStandardMax) {
                eqValueRate = this.jisuandianliangbi(eq, EQStandardMin, EQStandardFull);
                remind = "您的设备电量剩余" + eqValueRate + "%，为避免影响使用，请及时充电！";
            }
        }
        return remind;
    }

    @Override
    public Long countMemberBindCount(String doctorId) {
        Long num = this.dyMemberSensorPOMapper.countMemberBindCount(doctorId);
        return num;
    }

    @Override
    public Long countInHospitalBindSensorPatient(String departmentId) {
        return this.dyMemberSensorPOMapper.countInHospitalBindSensorPatient(departmentId);
    }

    @Override
    public long countMemberSensor(String memberId) {
        return this.dyMemberSensorPOMapper.countMemberSensor(memberId);
    }

    @Override
    public JSONObject checkSensorBindStatus(String sensorNo, String hospitalId) {

        JSONObject result = new JSONObject();
        result.put("bindStatus", 0);
        //绑定状态 1 已绑定 0 未绑定
        DyMemberSensorDTO dto = new DyMemberSensorDTO();
        dto.setSensorNo(sensorNo);
        dto.setValid(1);
        List<DYMemberSensorPO> list = this.dyMemberSensorPOMapper.listMemberSensor(dto);
        if (list != null && !list.isEmpty()) {
            result.put("bindStatus", 1);
            DYMemberSensorPO po = list.get(0);
            String memberId = po.getMemberId();
            MemberPO memberPO = this.memberService.getMemberById(memberId);
            CheckinInfoBO checkinInfoBO = this.hospitalService.getCheckinInfoBOByMid(memberId, hospitalId);
            if (checkinInfoBO != null) {
                result.put("bedNo", checkinInfoBO.getBedNo());
            }
            if (memberPO != null) {
                result.put("memberId", memberPO.getMemberId());
                result.put("memberName", memberPO.getMemberName());
            }
        }
        return result;
    }

    @Override
    public DYMemberSensorPO getMemberSensorBySensorNo(String sensorNo) {
        return this.dyMemberSensorPOMapper.getMemberSensorBySensorNo(sensorNo);
    }

    @Override
    public List<ShowSensorVO> listShowSensorBySensorSid(String sensorSid) {
        return this.dyMemberSensorPOMapper.listShowSensorBySensorSid(sensorSid);
    }

    @Override
    public JSONObject checkSensorAndMemberBindStatus(String sensorNo, String memberId) {
        JSONObject result = new JSONObject();
        DYMemberSensorPO dyMemberSensorPO = getMemberSensorBySensorNo(sensorNo);
        // 1 探头未被绑定  2 探头和当前患者已绑定 3 探头被其他患者绑定
        if (dyMemberSensorPO == null) {
            result.put("status", 1);
        } else if (memberId.equals(dyMemberSensorPO.getMemberId())) {
            result.put("status", 2);
        } else {
            MemberPO memberPO = this.memberService.getMemberById(dyMemberSensorPO.getMemberId());
            result.put("status", 3);
            if (memberPO == null) {
                result.put("bindMemberId", Constant.DEFAULT_FOREIGN_ID);
                result.put("bindMemberName", "未知");
            } else {
                result.put("bindMemberId", memberPO.getMemberId());
                result.put("bindMemberName", memberPO.getMemberName());
            }
        }
        return result;
    }

    @Override
    public void changeSensorBind(BindSensorDTO bindSensorDTO) {
        DYMemberSensorPO dyMemberSensorPO = getMemberSensorBySensorNo(bindSensorDTO.getSensorNo());
        if (bindSensorDTO.getMemberId().equals(dyMemberSensorPO.getMemberId())) {
            throw new BusinessException("该探头已和患者绑定，无需修改");
        }
        UnBindSensorDTO unBindSensorDTO = new UnBindSensorDTO();
        unBindSensorDTO.setMemberId(dyMemberSensorPO.getMemberId());
        unBindSensorDTO.setSensorNo(bindSensorDTO.getSensorNo());
        unBindSensorDTO.setSid(dyMemberSensorPO.getSid());
        unBindSensor(unBindSensorDTO);
        bindSensor(bindSensorDTO);
    }

    /**
     * 计算电量
     *
     * @param eq
     * @return
     */
    private Double jisuandianliangbi(Double eq, Double min, Double fullValue) {
        //数值格式化对象
        DecimalFormat df = new DecimalFormat("#0");
        Double rate = (eq - min) / fullValue * 100;
        return Double.parseDouble(df.format(rate));
    }


    /**
     * 获取患者绑定探头的数据
     *
     * @param sensorNo
     * @return
     */
    @Override
    public DYMemberSensorPO getDYMemberSensorPO(String sensorNo) {
        DYMemberSensorPO dyMemberSensorPO = this.dyMemberSensorPOMapper.getDYMemberSensorPO(sensorNo);
        return dyMemberSensorPO;
    }

    @Override
    public DynamicBloodSugarMonitorVO getCount(String doctorId, String hospitalId, String departmentId, Integer virtualWardAuthority) {

        //取医生权限:
        // 1:个人(医生所在科室的患者绑定的探头, 2:全院(医生所在全院的患者绑定的探头),
        // 3:科室(若勾选子权限,等于医生所在科室和勾选的子权限科室患者绑定的探头)
        DoctorPO doctorPO = this.doctorMapper.getDoctorById(doctorId);
        //创建返回监测的对象
        DynamicBloodSugarMonitorVO dynamicBloodSugarMonitorVO = new DynamicBloodSugarMonitorVO();
        if (doctorPO.getDataAuth() != null) {
            DoctorDataAuthBO departIds = JSON.parseObject(doctorPO.getDataAuth(), DoctorDataAuthBO.class);
            String doctorDataAuth = departIds.getList();
            String[] departId = doctorDataAuth.split(",");
            List<String> listDataAuth = Arrays.asList(departId);
            Integer countHospital = null;
            List<DYMemberSensorPO> dyMemberSensorList = null;
            //储存患者id
            List<String> listMemberId = new ArrayList<>();
            if (departIds.getType() != null && departIds.getType() != "") {
                if ("1".equals(departIds.getType())) {
                    //查询这个医生所在科室的患者
                    dyMemberSensorList = this.dyMemberSensorPOMapper.getCountHospitalDYMemberSensor(departmentId, hospitalId);

                } else if ("2".equals(departIds.getType())) {
                    //查询这个医生所在医院的全部患者
                    dyMemberSensorList = this.dyMemberSensorPOMapper.getCountHospitalDYMemberSensorPO(doctorPO.getHospitalId());
                } else if ("3".equals(departIds.getType())) {
                    //查询这个医生所管辖科室的患者
                    dyMemberSensorList = this.dyMemberSensorPOMapper.getCountHospitalByDataAuth(doctorId, listDataAuth);
                }
                List<VirtualWardPO> virtualWardPOS = null;
                //有虚拟病区模块权限,没有勾选虚拟病区.
                if (virtualWardAuthority == 1 && doctorPO.getIsVirtual() == 0 && !"2".equals(departIds.getType())) {
                    virtualWardPOS = this.virtualWardMapper.listAllVirtualWardByDepartment(hospitalId, departmentId);
                }
                //有虚拟病区权限,并且有勾选虚拟病区(查询全院虚拟病区的患者)或者该医生的权限为全院
                if (virtualWardAuthority == 1 && doctorPO.getIsVirtual() == 1 || "2".equals(departIds.getType())) {
                    virtualWardPOS = this.virtualWardMapper.listAllVirtualWardByHospitalId(hospitalId);
                }
                for (DYMemberSensorPO sensorPO : dyMemberSensorList) {
                    if (listMemberId.indexOf(sensorPO.getMemberId()) < 0) {
                        listMemberId.add(sensorPO.getMemberId());
                    }
                }
                if (virtualWardPOS != null && virtualWardPOS.size() > 0) {
                    for (VirtualWardPO virtualWardPO : virtualWardPOS) {
                        if (listMemberId.indexOf(virtualWardPO.getMemberId()) < 0) {
                            listMemberId.add(virtualWardPO.getMemberId());
                        }
                    }
                }

            }
            //1:先获取医生团队t_doctor和t_doctor_relation.
            //1.1通过当前登陆医生作为doctor_id的值查询有多少医生.
            List<String> list = new ArrayList<>();
            List<DoctorRelationPO> doctorIds = this.doctorMapper.listDoctors(hospitalId, doctorId);
            for (DoctorRelationPO doctorRelationPO : doctorIds) {
                list.add(doctorRelationPO.getForeignId());
            }
            List<DYMemberSensorPO> dyMemberSensorPOList = null;
            if (doctorIds.size() > 1) {
                //大于1,说明被当前医院的登陆医生添加为助理
                //获取居家的人数
                dyMemberSensorPOList = this.dyMemberSensorPOMapper.getCountHomeDYMemberSensorPO(hospitalId, list);
            } else {
                //说明是医生(只看自己的)
                //获取居家的人数
                dyMemberSensorPOList = this.dyMemberSensorPOMapper.getCountHomeDYMemberSensorPOByDoctorId(doctorId, hospitalId);
            }
            List<String> homeMemberId = new ArrayList<>();
            for (DYMemberSensorPO po : dyMemberSensorPOList) {
                if (homeMemberId.indexOf(po.getMemberId()) < 0) {
                    homeMemberId.add(po.getMemberId());
                }
            }
            Integer countHome = list(homeMemberId);
            dynamicBloodSugarMonitorVO.setCountMonitorHome(countHome);
            //获取住院的人数
//            List<DYMemberSensorPO> dyMemberSensorList = this.dyMemberSensorPOMapper.getCountHospitalDYMemberSensorPO("4");
            countHospital = list(listMemberId);
            dynamicBloodSugarMonitorVO.setCountMonitorHospital(countHospital);
            //取住院和居家门诊的总人数
            Integer countAll = countHome + countHospital;
            dynamicBloodSugarMonitorVO.setCountMonitorAll(countAll);
            //获取指标异常的住院患者人数
            Integer countAbnormityHospital = getCountHospital(listMemberId);
            dynamicBloodSugarMonitorVO.setCountAbnormityHospital(countAbnormityHospital);
            //获取指标异常的门诊/居家的患者人数
            Integer countAbnormityHome = getCountHome(dyMemberSensorPOList);
            dynamicBloodSugarMonitorVO.setCountAbnormityHome(countAbnormityHome);


            //获取异常达标的总人数
            Integer countAbnormityAll = countAbnormityHospital + countAbnormityHome;
            dynamicBloodSugarMonitorVO.setCountAbnormityAll(countAbnormityAll);
        }

        return dynamicBloodSugarMonitorVO;
    }

    private Integer list(List<String> listMemberId) {
        //创建一个list集合装符合要求的数据(探头在有效期,患者绑定探头开始,往后计算十四天)
        List<String> list = new ArrayList<>();
        for (String memberId : listMemberId) {
            List<DYMemberSensorPO> poList = this.dyMemberSensorPOMapper.getDYMemberSensorPOByMemberId(memberId);
            for (DYMemberSensorPO po : poList) {
                DYYPBloodSugarPO dyypBloodSugar = this.dyBloodSugarService.getDyBloodSugar(po.getSensorNo());
                String startTime = DateHelper.getNowDate();
                String endTime = "";
                if (dyypBloodSugar != null) {
                    //2:计算探头从第一条上传血糖开始时间到现在使用了多少天.
                    endTime = DateHelper.dateToString(dyypBloodSugar.getRecordTime());
                } else {
                    DYMemberSensorPO dyMemberSensor = this.dyMemberSensorPOMapper.getDYMemberSensorPO(po.getSensorNo());
                    //2:计算探头从绑定开始时间到现在使用了多少天.
                    endTime = dyMemberSensor.getInsertDt();
                }
                int day = DateHelper.dateCompareGetDay(startTime, endTime);
                //如果探头天数小于十四天,则添加到集合中.
                if (day <= DynamicBloodSugarConstant.DAY) {
                    //说明进来的是符合要求的探头
                    if (list.indexOf(memberId) < 0) {
                        list.add(memberId);
                    }
                }
            }
        }
        return list.size();
    }

    //获取住院异常达标的患者人数
    public Integer getCountHome(List<DYMemberSensorPO> dyMemberSensorPOList) {
        //存储小于十四天的探头
        List<DYMemberSensorPO> list = new ArrayList<>();
        for (DYMemberSensorPO dyMemberSensorPO : dyMemberSensorPOList) {
            DYYPBloodSugarPO dyypBloodSugarPO = this.dyBloodSugarService.getDyBloodSugar(dyMemberSensorPO.getSensorNo());
            if (dyypBloodSugarPO != null) {
                String startTime = "";
                String endTime = "";
                if (dyypBloodSugarPO != null) {
                    startTime = DateHelper.getNowDate();
                    endTime = DateHelper.getDate(dyypBloodSugarPO.getRecordTime(), "yyyy-MM-dd HH:mm:ss");

                } else { //探头在有效期,但没有记录血糖
                    startTime = DateHelper.getNowDate();
                    endTime = dyMemberSensorPO.getInsertDt();
                }
                int day = DateHelper.dateCompareGetDay(startTime, endTime);
                if (day <= DynamicBloodSugarConstant.DAY) {
                    //说明进来的是符合要求的探头数据(这些探头都是在十四天内的)
                    list.add(dyMemberSensorPO);
                }
            }
        }
        Integer count = count(list);
        return count;
    }

    //获取住院异常达标的患者人数
    public Integer getCountHospital(List<String> listMemberId) {
        //存储小于十四天的探头
        List<DYMemberSensorPO> list = new ArrayList<>();
        for (String memberId : listMemberId) {
            List<DYMemberSensorPO> poList = this.dyMemberSensorPOMapper.getDYMemberSensorPOByMemberId(memberId);
            for (DYMemberSensorPO dyMemberSensorPO : poList) {
                DYYPBloodSugarPO dyypBloodSugarPO = this.dyBloodSugarService.getDyBloodSugar(dyMemberSensorPO.getSensorNo());
                String startTime = "";
                String endTime = "";
                if (dyypBloodSugarPO != null) {
                    startTime = DateHelper.getNowDate();
                    endTime = DateHelper.getDate(dyypBloodSugarPO.getRecordTime(), "yyyy-MM-dd HH:mm:ss");

                } else { //探头在有效期,但没有记录血糖
                    startTime = DateHelper.getNowDate();
                    endTime = dyMemberSensorPO.getInsertDt();
                }
                int day = DateHelper.dateCompareGetDay(startTime, endTime);
                if (day <= DynamicBloodSugarConstant.DAY) {
                    //说明进来的是符合要求的探头数据(这些探头都是在十四天内的)
                    list.add(dyMemberSensorPO);
                }
            }
        }
        Integer count = count(list);
        return count;
    }

    private Integer count(List<DYMemberSensorPO> hospitalAbnormityList) {
        int count = 0;
        List<String> list = new ArrayList<>();
        //遍历符合要求的探头,查看没个探头的全部血糖记录,计算每个探头的血糖记录占比标准
        for (DYMemberSensorPO dyMemberSensor : hospitalAbnormityList) {
            Integer totalEvent = 0, lowEvent = 0, highEvent = 0, normalEvent = 0;
            //1:查询每个探头的所有动态血糖数据
            DYYPBloodSugarPO dyypBloodSugarPO = this.dyBloodSugarService.getDyBloodSugar(dyMemberSensor.getSensorNo());
            if (dyypBloodSugarPO != null) {
                //查询十四天内,所有血糖的数据.
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startTime = sdf.format(dyypBloodSugarPO.getRecordTime());
                List<DYYPBloodSugarPO> dyYpBloodSugarPOList = this.dyBloodSugarService.getDyBloodSugarList(dyMemberSensor.getSensorNo(), startTime, DateHelper.plusDate2(startTime, 14));
                //获取当前患者的指标参数
                DyMemberSettingPO dyMemberSetting = this.dyMemberSettingMapper.getSystemSetting(dyMemberSensor.getMemberId());
                if (dyMemberSetting == null) {
                    dyMemberSetting = defaultValues();
                }
                if (dyYpBloodSugarPOList != null) {
                    if (dyMemberSetting != null) {
                        //用于mage运算的数据
                        List<DynamicBloodSugarMAGEItemBO> itemList = new ArrayList<>();
                        //每个血糖记录表示记录时间前15分钟的血糖情况，所以每天前15分钟内的血糖不参与占比计算
                        for (DYYPBloodSugarPO item : dyYpBloodSugarPOList) {
                            addMAGEItem(itemList, item);
                            if (!checkRecordDateIsDayFirst15Minutes(item.getRecordTime())) {
                                totalEvent++;
                                if (item.getValue().doubleValue() > dyMemberSetting.getBloodSugarMax()) {//偏高
                                    highEvent++;
                                } else if (item.getValue().doubleValue() < dyMemberSetting.getBloodSugarMin()) {//偏低
                                    lowEvent++;
                                } else {//正常
                                    normalEvent++;
                                }
                            }
                        }
                        if (list.indexOf(dyMemberSensor.getMemberId()) < 0) {
                            if (DynamicBloodSugarTool.tir(normalEvent, totalEvent) < dyMemberSetting.getBloodSugarNorm()) {
                                count++;
                                list.add(dyMemberSensor.getMemberId());
                            }
                        }

                    }
                }
            }
        }
        return count;
    }


    /**
     * 添加mage运算需要的数据
     *
     * @param itemList
     * @param po
     */
    private void addMAGEItem(List<DynamicBloodSugarMAGEItemBO> itemList, DYYPBloodSugarPO po) {
        DynamicBloodSugarMAGEItemBO item = new DynamicBloodSugarMAGEItemBO();
        item.setTimestamp(po.getRecordTime().getTime());
        item.setValue(po.getValue().doubleValue());
        itemList.add(item);
    }

    /**
     * 判断血糖录入时间是否是每天的前15分钟
     *
     * @param date
     * @return true 是 false 不是
     */
    private boolean checkRecordDateIsDayFirst15Minutes(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) < 15) {
            return true;
        }
        return false;
    }

    private DyMemberSettingPO defaultValues() {
        DyMemberSettingPO dyMemberSettingPO = new DyMemberSettingPO();
        dyMemberSettingPO.setBreakfastDt("06:30");
        dyMemberSettingPO.setLunchDt("11:30");
        dyMemberSettingPO.setDinnerDt("18:10");
        dyMemberSettingPO.setSleepDt("22:30");
        dyMemberSettingPO.setBloodSugarMin(3.9D);
        dyMemberSettingPO.setBloodSugarMax(10.0D);
        dyMemberSettingPO.setBloodSugarNorm(70);
        dyMemberSettingPO.setBloodSugarNormLess(4);
        dyMemberSettingPO.setBloodSugarNormThan(25);
        dyMemberSettingPO.setBloodTimeRatioMin(3.0D);
        dyMemberSettingPO.setBloodTimeRatioMax(13.9D);
        return dyMemberSettingPO;
    }

    /**
     * 实时更新上传通知
     */
    @Override
    public List<DyBloodSugarInformPO> listLatestDyBloodSugarInform(String doctorId, String hospitalId, String departmentId, String latestDt) {

        //取医生权限:
        // 1:个人(医生所在科室的患者绑定的探头, 2:全院(医生所在全院的患者绑定的探头),
        // 3:科室(若勾选子权限,等于医生所在科室和勾选的子权限科室患者绑定的探头)
        DoctorPO doctorPO = this.doctorMapper.getDoctorById(doctorId);
        //存放最终符合条件的memberId
        List<String> listMemberId = new ArrayList<>();
        //存储住院和门诊居家的患者
        if (doctorPO.getDataAuth() != null) {
            DoctorDataAuthBO departIds = JSON.parseObject(doctorPO.getDataAuth(), DoctorDataAuthBO.class);
            String doctorDataAuth = departIds.getList();
            String[] departId = doctorDataAuth.split(",");
            List<String> listDataAuth = Arrays.asList(departId);
            List<DYMemberSensorPO> dyMemberSensorList = null;
            if (departIds.getType() == null) {
                departIds.setType("1");
            }
            if (departIds.getType() != null && departIds.getType() != "") {
                if ("1".equals(departIds.getType())) {
                    //查询这个医生所在科室的患者
                    dyMemberSensorList = this.dyMemberSensorPOMapper.getCountHospitalDYMemberSensor(doctorPO.getDepartId(), hospitalId);
                } else if ("2".equals(departIds.getType())) {
                    //查询这个医生所在医院的全部患者
                    dyMemberSensorList = this.dyMemberSensorPOMapper.getCountHospitalDYMemberSensorPO(doctorPO.getHospitalId());
                } else if ("3".equals(departIds.getType())) {
                    //查询这个医生所管辖科室的患者
                    dyMemberSensorList = this.dyMemberSensorPOMapper.getCountHospitalByDataAuth(doctorId, listDataAuth);
                }
            }
            for (DYMemberSensorPO dyMemberSensorPO : dyMemberSensorList) {
                if (listMemberId.indexOf(dyMemberSensorPO.getMemberId()) < 0) {
                    listMemberId.add(dyMemberSensorPO.getMemberId());
                }
            }
            //获取虚拟病区的患者id
            List<VirtualWardPO> virtualWardPOS = this.virtualWardMapper.listAllVirtualWardByHospitalId(hospitalId);
            for (VirtualWardPO virtualWardPO : virtualWardPOS) {
                if (listMemberId.indexOf(virtualWardPO.getMemberId()) < 0) {
                    listMemberId.add(virtualWardPO.getMemberId());
                }
            }

            //1:先获取医生团队t_doctor和t_doctor_relation.
            //1.1通过当前登陆医生作为doctor_id的值查询有多少医生.
            List<String> list = new ArrayList<>();
            List<DoctorRelationPO> doctorIds = this.doctorMapper.listDoctors(hospitalId, doctorId);
            for (DoctorRelationPO doctorRelationPO : doctorIds) {
                list.add(doctorRelationPO.getForeignId());
            }

            //居家的
            List<DYMemberSensorPO> dyMemberSensorPOList = null;
            if (doctorIds.size() > 1) {
                //大于1,说明被当前医院的登陆医生添加为助理
                //获取居家的人数
                dyMemberSensorPOList = this.dyMemberSensorPOMapper.getCountHomeDYMemberSensorPO(hospitalId, list);
            } else {
                //说明是医生(只看自己的)
                //获取居家的人数
                dyMemberSensorPOList = this.dyMemberSensorPOMapper.getCountHomeDYMemberSensorPOByDoctorId(doctorId, hospitalId);
            }

            for (DYMemberSensorPO dyMemberSensorPO : dyMemberSensorPOList) {
                if (listMemberId.indexOf(dyMemberSensorPO.getMemberId()) < 0) {
                    listMemberId.add(dyMemberSensorPO.getMemberId());
                }
            }

        }

        List<DyBloodSugarInformPO> list = new ArrayList<>();
        if (listMemberId != null && listMemberId.size() > 0) {
            //查询今日的数据
            List<DyBloodSugarInformPO> dyBloodSugarInformPOList = this.dyBloodSugarInformMapper.listLatestDyBloodSugarInform(latestDt, listMemberId);
            for (DyBloodSugarInformPO dyBloodSugarInform : dyBloodSugarInformPOList) {

                //是否住院
                MemberCheckinInfoPO memberCheckinInfoPO = this.memberCheckinInfoMapper.getMemberCheckinInfoByMemberId(dyBloodSugarInform.getMemberId(), hospitalId);
                //获取医生id,查询住院表和医患关系表
                DoctorMemberPO doctorMemberPO = this.memberMapper.getDoctorByMemberId(dyBloodSugarInform.getMemberId());

                //虚拟病区
                VirtualWardPO virtualWardPO = this.virtualWardMapper.getCurrentVirtualWard(dyBloodSugarInform.getMemberId(), hospitalId);

                if (memberCheckinInfoPO != null || virtualWardPO != null) {
                    dyBloodSugarInform.setDepartmentId(memberCheckinInfoPO.getDepartmentId());
                    dyBloodSugarInform.setInHos(1);
                    dyBloodSugarInform.setDoctorId(doctorId);
                    list.add(dyBloodSugarInform);
                } else {
                    dyBloodSugarInform.setInHos(0);
                    if (doctorMemberPO != null) {
                        dyBloodSugarInform.setDoctorId(doctorMemberPO.getDoctorId());
                    } else {
                        dyBloodSugarInform.setDoctorId(doctorId);
                    }
                    list.add(dyBloodSugarInform);
                }
            }
        }
        return list;
    }

    @Override
    public List<DYMemberSensorPO> getDYMemberSensorPOByMemberId(String memberId) {
        List<DYMemberSensorPO> memberSensorPOS = this.dyMemberSensorPOMapper.getDYMemberSensorPOByMemberId(memberId);
        return memberSensorPOS;
    }

    /**
     * 判断探头是否过期
     * /hasExpired
     *
     * @param sensorNo
     * @return
     */
    @Override
    public Object hasExpired(String sensorNo) {
        DYMemberSensorPO po = getDYMemberSensorPO(sensorNo);
        if (po == null || po.getMonitoringTime() == null)
            throw new BusinessException("探头没有检测时间");
        Date startDate = new Date(Long.parseLong(po.getMonitoringTime()));
        String startDt = DateHelper.getDate(startDate, DateHelper.DATETIME_FORMAT);
        String t = DateHelper.plusDate2(startDt, 14);
        return Boolean.TRUE.equals(DateHelper.dateAfter(DateHelper.getNowDate(), DateHelper.DATETIME_FORMAT, t, DateHelper.DATETIME_FORMAT));
    }
}
