package com.comvee.cdms.virtualward.service.impl;

import com.comvee.cdms.clinicaldiagnosis.constant.YzConstant;
import com.comvee.cdms.clinicaldiagnosis.dto.ListMemberYzDTO;
import com.comvee.cdms.clinicaldiagnosis.service.YzServiceI;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.department.model.po.DepartmentPO;
import com.comvee.cdms.department.service.DepartmentService;
import com.comvee.cdms.doctor.cfg.DoctorPopupRemindConstant;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.po.DoctorPopupRemindIgnoreTimePO;
import com.comvee.cdms.doctor.service.DoctorPopupRemindIgnoreTimeService;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.dybloodsugar.po.DYMemberSensorPO;
import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DyMemberSensorService;
import com.comvee.cdms.member.constant.MemberConstant;
import com.comvee.cdms.member.mapper.MemberCheckinInfoMapper;
import com.comvee.cdms.member.mapper.MemberInHospitalLogMapper;
import com.comvee.cdms.member.po.MemberCheckinInfoPO;
import com.comvee.cdms.member.po.MemberInHospitalLogPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.virtualward.constant.VirtualWardConstant;
import com.comvee.cdms.virtualward.constant.VirtualWardLock;
import com.comvee.cdms.virtualward.mapper.VirtualWardMapper;
import com.comvee.cdms.virtualward.mapper.VirtualWardTransferApplyMapper;
import com.comvee.cdms.virtualward.model.param.*;
import com.comvee.cdms.virtualward.model.po.*;
import com.comvee.cdms.virtualward.model.vo.*;
import com.comvee.cdms.virtualward.service.VirtualWardService;
import com.comvee.cdms.virtualward.support.ListBloodSugarInfoHandler;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service("virtualWardService")
public class VirtualWardServiceImpl implements VirtualWardService {

    private final static Logger log = LoggerFactory.getLogger(VirtualWardServiceImpl.class);

    @Autowired
    private VirtualWardMapper virtualWardMapper;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private BloodSugarService bloodSugarService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private VirtualWardTransferApplyMapper virtualWardTransferApplyMapper;

    @Autowired
    @Lazy
    private YzServiceI yzService;

    @Autowired
    private DyMemberSensorService dyMemberSensorService;

    @Autowired
    private MemberCheckinInfoMapper memberCheckinInfoMapper;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    @Autowired
    private MemberInHospitalLogMapper memberInHospitalLogMapper;

    @Autowired
    private DoctorPopupRemindIgnoreTimeService doctorPopupRemindIgnoreTimeService;

    @Override
    public PageResult<VirtualWardListVO> listVirtualWardPatient(ListVirtualWardPatientParam param, PageRequest pr) {
        DoctorPO doctorById = this.doctorService.getDoctorById(param.getDoctorId());
        //说明没有勾选虚拟病区
        if (doctorById.getIsVirtual() != 1 ){
            List<String> departmentIds = new ArrayList<>();
            List<DepartmentPO> departmentPOS = this.departmentService.listDoctorManageDepartment(param.getDoctorId());
            for (DepartmentPO departmentPO : departmentPOS){
                departmentIds.add(departmentPO.getDepartmentId());
            }
            param.setDepartIdList(departmentIds);
        }
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<VirtualWardListPO> list = this.virtualWardMapper.listVirtualWardPatient(param);
        PageResult<VirtualWardListPO> pageList = new PageResult(list);
        PageResult<VirtualWardListVO> result = pageList.createEmptyPageResult();
        if(pageList.getRows() == null || pageList.getRows().isEmpty()){
            return result;
        }
        List<String> doctorIdList = new ArrayList<>();
        List<String> memberIdList = new ArrayList<>();
        for(VirtualWardListPO i : pageList.getRows()){
            doctorIdList.add(i.getAllowIntoDoctorId());
            memberIdList.add(i.getMemberId());
            doctorIdList.add(i.getIntoDoctorId());
        }
        Map<String ,DoctorPO> doctorMap = getDoctorMap(doctorIdList);
        Map<String ,BloodSugarPO> bloodSugarMap = getBloodSugarMap(memberIdList);
        VirtualWardListVO item = null;
        List<VirtualWardListVO> resultList = new ArrayList<>();
        for(VirtualWardListPO i : pageList.getRows()){
            item = new VirtualWardListVO();
            BeanUtils.copyProperties(item ,i);
            item.setApplyDoctorName(param.getDoctorName());
            doctorInfoHandler(item ,doctorMap ,"intoDoctorId" ,"applyIntoDoctorName");
            doctorInfoHandler(item ,doctorMap ,"allowIntoDoctorId" ,"allowIntoDoctorName");
            bloodSugarInfoHandler(item ,bloodSugarMap);
            virtualWardListUseMachineInfoHandler(item,i.getMemberId());
            resultList.add(item);
        }
        result.setRows(resultList);
        return result;
    }

    @Override
    public PageResult<VirtualWardVO> listVirtualWardPatientRecord(ListVirtualWardPatientParam param, PageRequest pr) {
        List<String> departmentIds = new ArrayList<>();
        List<VirtualWardVO> resultList = new ArrayList<>();
        DoctorPO doctor = this.doctorService.getDoctorById(param.getDoctorId());
        if (param.getOperation() == 2){   //2:虚拟病区历史通知-历史记录
            List<DepartmentPO> departmentPOS = this.departmentService.listDoctorManageDepartment(param.getDoctorId());
            for (DepartmentPO departmentPO : departmentPOS){
                departmentIds.add(departmentPO.getDepartmentId());
            }
            //说明有勾选虚拟病区
            if (doctor.getIsVirtual() == 1 ){
                List<VirtualWardDepartmentPO> virtualWardDepartmentPOList = this.virtualWardMapper.listAllVirtualWardDepartment(null,param.getHospitalId());
                for (VirtualWardDepartmentPO vo : virtualWardDepartmentPOList) {
                    if(departmentIds.indexOf(vo.getDepartmentId())<0){
                        departmentIds.add(vo.getDepartmentId());
                    }
                }
            }
            param.setDepartIdList(departmentIds);
        }else if (param.getOperation() == 1){ //1:嵌入页虚拟病区-历史记录
            param.setDepartmentId(doctor.getDepartId());
        }

        if (param.getTransferStatus() == null || param.getTransferStatus() == 1 || param.getTransferStatus() == 2){
            param.setTransferStatusList(Arrays.asList(1,2));
            List<VirtualWardListPO> virtualWardListPOS = this.virtualWardMapper.listVirtualWardPatientRecord(param);
            for (VirtualWardListPO virtualWardListPO : virtualWardListPOS){
                VirtualWardVO listVO = new VirtualWardVO();
                listVO.setDepartmentName(virtualWardListPO.getDepartmentName());
                listVO.setBedNo(virtualWardListPO.getBedNo());
                listVO.setMemberName(virtualWardListPO.getMemberName());
                listVO.setHospitalNo(virtualWardListPO.getHospitalNo());
                listVO.setIntoDt(virtualWardListPO.getIntoDate());
                listVO.setOutDt(virtualWardListPO.getOutDate());
                DoctorPO doctorById = this.doctorService.getDoctorById(virtualWardListPO.getIntoDoctorId());
                listVO.setApplyDoctorName(doctorById.getDoctorName());
                listVO.setTransferStatus(virtualWardListPO.getTransferStatus());
                listVO.setInsertDt(virtualWardListPO.getInsertDt());
                resultList.add(listVO);
            }
        }
        if (param.getTransferStatus() == null || param.getTransferStatus() == 3){
            //直接出院
            GetVirtualWardTransferApplyParam applyParam = new GetVirtualWardTransferApplyParam();
            applyParam.setDepartmentId(param.getDepartmentId());
            applyParam.setHospitalId(param.getHospitalId());
            applyParam.setApplyTypeList(Arrays.asList(3));
            applyParam.setApplyStatus(2);
            applyParam.setKeyword(param.getKeyword());
            List<VirtualWardTransferApplyPO> poList = this.virtualWardTransferApplyMapper.listVirtualWardTransferApplyOne(applyParam);
//            List<VirtualWardTransferApplyPO> applyPOS = this.virtualWardTransferApplyMapper.listVirtualWardPatientRecord(param);
            if (poList == null || poList.size() == 0){
                return new PageResult<VirtualWardVO>(resultList);
            }
            for (VirtualWardTransferApplyPO applyPO : poList){
                if (applyPO == null || "".equals(applyPO)){
                    continue;
                }
                VirtualWardVO listVO = new VirtualWardVO();
                listVO.setDepartmentName(applyPO.getDepartmentName());
                listVO.setBedNo(applyPO.getBedNo());
                MemberPO memberById = this.memberService.getMemberById(applyPO.getMemberId());
                listVO.setMemberName(memberById.getMemberName());
                listVO.setHospitalNo(applyPO.getHospitalNo());
                //
                GetVirtualWardTransferApplyParam wardTransferApplyParam = new GetVirtualWardTransferApplyParam();
                wardTransferApplyParam.setDepartmentId(applyPO.getDepartmentId());
                wardTransferApplyParam.setHospitalId(applyPO.getHospitalId());
                wardTransferApplyParam.setApplyTypeList(Arrays.asList(1));
                wardTransferApplyParam.setApplyStatus(1);
                wardTransferApplyParam.setHospitalNo(applyPO.getHospitalNo());
                VirtualWardTransferApplyPO applyOne = this.virtualWardTransferApplyMapper.getVirtualWardTransferApplyOne(wardTransferApplyParam);
                if (applyOne != null && applyOne.getApplyType() == 1 && applyOne.getApplyStatus() == 1){
                    //转入未确认
                    listVO.setIntoDt("");
                    listVO.setOutDt("");
                }else {
                    //查询住院记录表
                    MemberInHospitalLogPO logPO = new MemberInHospitalLogPO();
                    logPO.setMemberId(applyPO.getMemberId());
                    logPO.setHospitalId(applyPO.getHospitalId());
                    logPO.setDepartmentId(applyPO.getDepartmentId());
                    logPO.setHospitalNo(applyPO.getHospitalNo());
                    MemberInHospitalLogPO hospitalLog = this.memberInHospitalLogMapper.getMemberInHospitalLog(logPO);
                    if (hospitalLog != null && !"".equals(hospitalLog.getOutHospitalDate()) && hospitalLog.getOutHospitalDate() != null) {
                        if (applyPO.getAllowDate() == null || "".equals(applyPO.getAllowDate())){
                            GetVirtualWardParam get = new GetVirtualWardParam();
                            get.setMemberId(applyPO.getMemberId());
                            get.setHospitalId(applyPO.getHospitalId());
                            get.setDepartmentId(applyPO.getDepartmentId());
                            get.setHospitalNo(applyPO.getHospitalNo());
                            get.setTransferStatusList(Arrays.asList(1,2,3));
                            VirtualWardPO virtualWard = this.virtualWardMapper.getVirtualWard(get);
                            if (virtualWard != null){
                                listVO.setIntoDt(virtualWard.getIntoDate());
                            }
                        }else{
                            listVO.setIntoDt(applyPO.getAllowDate());
                        }
                        listVO.setOutDt(hospitalLog.getOutHospitalDate());
                    }
                }
                DoctorPO doctorById = this.doctorService.getDoctorById(applyPO.getApplyDoctorId());
                listVO.setApplyDoctorName(doctorById.getDoctorName());
                listVO.setTransferStatus(3);
                listVO.setInsertDt(applyPO.getInsertDt());
                resultList.add(listVO);
            }
        }
        ListUtils.sort(resultList,true,"insertDt");
        //分页
        PageResult<VirtualWardVO>  bloodSugarVOPageResult = new PageResult<VirtualWardVO>(resultList);
        bloodSugarVOPageResult.setPageNum(pr.getPage());
        bloodSugarVOPageResult.setPageSize(pr.getRows());
        bloodSugarVOPageResult.setTotalPages(resultList.size()%pr.getRows() == 0 ? resultList.size()/pr.getRows():resultList.size()/pr.getRows()+1);
        bloodSugarVOPageResult.setTotalRows(resultList.size());
        int startRow = (pr.getPage()-1)*pr.getRows();
        int endRow = pr.getPage()*pr.getRows();
        if (endRow>resultList.size()){
            endRow = resultList.size();
        }
        List<VirtualWardVO> list = new ArrayList<>();
        for (int i = startRow;i<endRow;i++){
            list.add(resultList.get(i));
        }
        bloodSugarVOPageResult.setRows(list);
        return bloodSugarVOPageResult;
    }

    @Override
    public PageResult<InHospitalPatientListVO> listInHospitalPatientForVirtualWard(QueryInHospitalPatientListParam param, PageRequest pr) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<InHospitalPatientListPO> list = this.virtualWardMapper.listInHospitalPatientForVirtualWard(param);
        PageResult<InHospitalPatientListPO> pageList = new PageResult(list);
        PageResult<InHospitalPatientListVO> result = pageList.createEmptyPageResult();
        if(list == null || list.isEmpty()){
            return result;
        }
        List<String> memberIdList = new ArrayList<>();
        for(InHospitalPatientListPO item : pageList.getRows()){
            memberIdList.add(item.getMemberId());
        }
        Map<String ,BloodSugarPO> bloodSugarPOMap = getBloodSugarMap(memberIdList);
        List<InHospitalPatientListVO> resultList = new ArrayList<>();
        InHospitalPatientListVO vo = null;
        for(InHospitalPatientListPO inHospitalPatientListPO : pageList.getRows()){
            vo = new InHospitalPatientListVO();
            BeanUtils.copyProperties(vo ,inHospitalPatientListPO);
            bloodSugarInfoHandler(vo ,bloodSugarPOMap);
            virtualWardInfoHandler(vo ,inHospitalPatientListPO ,param.getDepartmentId());
            resultList.add(vo);
        }
        result.setRows(resultList);
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void applyTransferInto(String id, String operatorId,String hospitalId) {
        VirtualWardLock.TRANSFER_LOCK.lock();
        try{
            MemberCheckinInfoPO memberCheckinInfo = this.memberService.getMemberCheckinInfoById(id,hospitalId);
            if(memberCheckinInfo == null){
                throw new BusinessException("患者不存在，请刷新后重新选择");
            }
            if(checkMemberVirtualWardRecord(memberCheckinInfo.getMemberId(),hospitalId)){
                throw new BusinessException("患者已转入虚拟病区");
            }
            if(checkVirtualWardApplyRecord(memberCheckinInfo.getMemberId() ,VirtualWardConstant.APPLY_TYPE_IN,hospitalId,memberCheckinInfo.getHospitalNo())){
                throw new BusinessException("该患者已申请转入，请联系有关医生进行确认");
            }
            VirtualWardTransferApplyPO param = new VirtualWardTransferApplyPO();
            BeanUtils.copyProperties(param ,memberCheckinInfo);
            param.setSid(DaoHelper.getSeq());
            param.setApplyDoctorId(operatorId);
            param.setApplyDate(DateHelper.getNowDate());
            param.setApplyType(VirtualWardConstant.APPLY_TYPE_IN);
            param.setForeignId(memberCheckinInfo.getSid());
            param.setApplyStatus(VirtualWardConstant.APPLY_STATUS_NOT_DEAL);
            param.setRemindDate(DateHelper.getNowDate());
            param.setAllowDoctorId(Constant.DEFAULT_FOREIGN_ID);
            //这里存住院流水号
            param.setHospitalNo(memberCheckinInfo.getAdmNo());
            param.setCardNo(memberCheckinInfo.getHospitalNo());
            this.virtualWardTransferApplyMapper.addVirtualWardTransferApply(param);
        }finally {
            VirtualWardLock.TRANSFER_LOCK.unlock();
        }
    }

    @Override
    public PageResult<TransferApplyListVO> listTransferIntoVirtualWardApply(QueryTransferIntoApplyListParam param, PageRequest pr) {
        List<String> departmentIds = new ArrayList<>();
        List<DepartmentPO> departmentPOS = this.departmentService.listDoctorManageDepartment(param.getDoctorId());
        for (DepartmentPO departmentPO : departmentPOS){
            departmentIds.add(departmentPO.getDepartmentId());
        }
        param.setDepartIdList(departmentIds);
        //查询医生的权限
        DoctorPO po = this.doctorService.getDoctorById(param.getDoctorId());
        //如果有勾选虚拟病区(能接收到全部的虚拟病区转出的通知)
        if (po.getIsVirtual() == 1){
            param.setDepartIdList(null);
        }
        QueryTransferApplyListParam mapperParam = transferIntoApplyListMapperParamHandler(param);
        List<TransferApplyListPO> list = this.virtualWardTransferApplyMapper.listTransferApplyList(mapperParam);
        List<TransferApplyListPO> transferApplyListPOS = new ArrayList<>();
        for (TransferApplyListPO applyListPO : list){
            //医生发起的申请,医生所在科室的医生不能收到通知
            if (!applyListPO.getDepartmentId().equals(param.getDepartId()) ){
                transferApplyListPOS.add(applyListPO);
            }
        }
        if(transferApplyListPOS == null || transferApplyListPOS.isEmpty()){
            return new PageResult<TransferApplyListVO>();
        }
        List<String> doctorIdList = new ArrayList<>();
        for(TransferApplyListPO item : transferApplyListPOS){
            doctorIdList.add(item.getApplyDoctorId());
        }
        Map<String ,DoctorPO> doctorPOMap = getDoctorMap(doctorIdList);
        List<TransferApplyListVO> resultList = new ArrayList<>();
        TransferApplyListVO vo = null;
        for(TransferApplyListPO item : transferApplyListPOS){
            vo = new TransferApplyListVO();
            BeanUtils.copyProperties(vo ,item);
            vo.setOutHospitalDate(item.getAllowDate());
            doctorInfoHandler(vo ,doctorPOMap ,"applyDoctorId" ,"applyDoctorName");
            if (!vo.getDepartmentId().equals(param.getDepartId())){
                resultList.add(vo);
            }
        }
        //分页
        PageResult<TransferApplyListVO>  bloodSugarVOPageResult = new PageResult<TransferApplyListVO>(resultList);
        bloodSugarVOPageResult.setPageNum(pr.getPage());
        bloodSugarVOPageResult.setPageSize(pr.getRows());
        bloodSugarVOPageResult.setTotalPages(resultList.size()%pr.getRows() == 0 ? resultList.size()/pr.getRows():resultList.size()/pr.getRows()+1);
        bloodSugarVOPageResult.setTotalRows(resultList.size());
        int startRow = (pr.getPage()-1)*pr.getRows();
        int endRow = pr.getPage()*pr.getRows();
        if (endRow>resultList.size()){
            endRow = resultList.size();
        }
        List<TransferApplyListVO> listVOS = new ArrayList<>();
        for (int i = startRow;i<endRow;i++){
            listVOS.add(resultList.get(i));
        }
        bloodSugarVOPageResult.setRows(resultList);
        return bloodSugarVOPageResult;
    }

    @Override
    public PageResult<TransferApplyListVO> listTransferOutVirtualWardApply(QueryTransferIntoApplyListParam param, PageRequest pr) {
        List<String> departmentIds = new ArrayList<>();
        List<DepartmentPO> departmentPOS = this.departmentService.listDoctorManageDepartment(param.getDoctorId());
        for (DepartmentPO departmentPO : departmentPOS){
            departmentIds.add(departmentPO.getDepartmentId());
        }
        param.setDepartIdList(departmentIds);
        //查询医生的权限
        DoctorPO po = this.doctorService.getDoctorById(param.getDoctorId());
        //如果有勾选虚拟病区(能接收到全部的虚拟病区转出的通知)
        if (po.getIsVirtual() == 1){
            param.setDepartIdList(null);
        }
        QueryTransferApplyListParam mapperParam = transferOutApplyListMapperParamHandler(param);
        List<TransferApplyListPO> list = this.virtualWardTransferApplyMapper.listTransferApplyList(mapperParam);
         //新建一个list接受去除本科室的患者
        List<TransferApplyListPO> transferApplyListPOS = new ArrayList<>();
        for (TransferApplyListPO applyListPO : list){
            DoctorPO doctorPO = this.doctorService.getDoctorById(applyListPO.getApplyDoctorId());
            if (doctorPO != null && !doctorPO.getDepartId().equals(param.getDepartId())){
                transferApplyListPOS.add(applyListPO);
            }
        }
        if(transferApplyListPOS == null || transferApplyListPOS.isEmpty()){
            return new PageResult<TransferApplyListVO>();
        }
        List<String> doctorIdList = new ArrayList<>();
        for(TransferApplyListPO item : transferApplyListPOS){
            doctorIdList.add(item.getApplyDoctorId());
        }
        Map<String ,DoctorPO> doctorPOMap = getDoctorMap(doctorIdList);
        List<TransferApplyListVO> resultList = new ArrayList<>();
        TransferApplyListVO vo = null;
        for(TransferApplyListPO item : transferApplyListPOS){
            vo = new TransferApplyListVO();
            BeanUtils.copyProperties(vo ,item);
            doctorInfoHandler(vo ,doctorPOMap ,"applyDoctorId" ,"applyDoctorName");
            DoctorPO doctorPO = this.doctorService.getDoctorById(item.getApplyDoctorId());
            if (!doctorPO.getDepartId().equals(param.getDepartId())){
                resultList.add(vo);
            }
        }
        //分页
        PageResult<TransferApplyListVO>  bloodSugarVOPageResult = new PageResult<TransferApplyListVO>(resultList);
        bloodSugarVOPageResult.setPageNum(pr.getPage());
        bloodSugarVOPageResult.setPageSize(pr.getRows());
        bloodSugarVOPageResult.setTotalPages(resultList.size()%pr.getRows() == 0 ? resultList.size()/pr.getRows():resultList.size()/pr.getRows()+1);
        bloodSugarVOPageResult.setTotalRows(resultList.size());
        int startRow = (pr.getPage()-1)*pr.getRows();
        int endRow = pr.getPage()*pr.getRows();
        if (endRow>resultList.size()){
            endRow = resultList.size();
        }
        List<TransferApplyListVO> listVOS = new ArrayList<>();
        for (int i = startRow;i<endRow;i++){
            listVOS.add(resultList.get(i));
        }
        bloodSugarVOPageResult.setRows(resultList);
        return bloodSugarVOPageResult;
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void allowTransferInto(String id, String operatorId,String hospitalId) {
        VirtualWardLock.TRANSFER_LOCK.lock();
        try{
            VirtualWardTransferApplyPO apply = getNotDealTransferApplyById(id);
            if(checkMemberVirtualWardRecord(apply.getMemberId(),hospitalId)){
                throw new BusinessException("患者已加入虚拟病区，无法重复操作");
            }
            String allowDate = DateHelper.getNowDate();
            apply.setApplyStatus(VirtualWardConstant.APPLY_STATUS_AGREE);
            apply.setAllowDoctorId(operatorId);
            apply.setAllowDate(allowDate);
            this.virtualWardTransferApplyMapper.updateVirtualWardTransferApply(apply);

            addVirtualWardRecord(apply);
        }finally {
            VirtualWardLock.TRANSFER_LOCK.unlock();
        }
    }

    @Override
    public void delayTransferIntoApplyRemind(String id, String operatorId) {
        VirtualWardTransferApplyPO apply = getNotDealTransferApplyById(id);
        String delayRemindDate = getDelayRemindDate(apply.getRemindDate());
        apply.setRemindDate(delayRemindDate);
        this.virtualWardTransferApplyMapper.updateVirtualWardTransferApply(apply);
    }

    @Override
    public TransferApplyDetailVO getTransferApplyDetail(GetVirtualWardTransferApplyParam param) {
        VirtualWardTransferApplyPO po = this.virtualWardTransferApplyMapper.getVirtualWardTransferApplyOne(param);
        if(po == null){
            return null;
        }
        TransferApplyDetailVO vo = new TransferApplyDetailVO();
        BeanUtils.copyProperties(vo ,po);

        MemberPO member = this.memberService.getMemberById(po.getMemberId());
        if(member != null){
            vo.setMemberName(member.getMemberName());
            vo.setBirthday(member.getBirthday());
            vo.setSex(member.getSex());
        }
        DoctorPO doctor = this.doctorService.getDoctorById(po.getApplyDoctorId());
        if(doctor != null){
            vo.setApplyDoctorName(doctor.getDoctorName());
        }
        return vo;
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void applyTransferOut(String id, String applyText, String operatorId) {
        VirtualWardLock.TRANSFER_LOCK.lock();
        try{
            GetVirtualWardParam param = new GetVirtualWardParam();
            param.setId(id);
            VirtualWardPO virtualWard = this.virtualWardMapper.getVirtualWard(param);
            if(virtualWard == null){
                throw new BusinessException("患者未入住或已转出虚拟病区，无法转出");
            }
            if(VirtualWardConstant.APPLY_OUT_STATUS_YES == virtualWard.getApplyStatus()){
                throw new BusinessException("已申请转出，请勿重复申请");
            }

            checkHasInsulinPumpYz(virtualWard.getMemberId(),virtualWard.getHospitalId());
            //添加申请记录
            VirtualWardTransferApplyPO insertParam = new VirtualWardTransferApplyPO();
            BeanUtils.copyProperties(insertParam ,virtualWard);
            insertParam.setSid(DaoHelper.getSeq());
            insertParam.setApplyDoctorId(operatorId);
            insertParam.setApplyDate(DateHelper.getNowDate());
            insertParam.setApplyType(VirtualWardConstant.APPLY_TYPE_OUT);
            insertParam.setForeignId(virtualWard.getSid());
            insertParam.setApplyStatus(VirtualWardConstant.APPLY_STATUS_NOT_DEAL);
            insertParam.setRemindDate(DateHelper.getNowDate());
            insertParam.setApplyText(applyText);
            insertParam.setAllowDoctorId(Constant.DEFAULT_FOREIGN_ID);
            this.virtualWardTransferApplyMapper.addVirtualWardTransferApply(insertParam);

            //修改状态
            virtualWard.setApplyStatus(VirtualWardConstant.APPLY_OUT_STATUS_YES);
            virtualWard.setOutDoctorId(operatorId);
            this.virtualWardMapper.updateVirtualWard(virtualWard);
        }finally {
            VirtualWardLock.TRANSFER_LOCK.unlock();
        }
    }

    @Override
    public void allowTransferOut(String id, String operatorId,String departId) {
        VirtualWardTransferApplyPO apply = getNotDealTransferApplyById(id);

        GetVirtualWardParam get = new GetVirtualWardParam();
        get.setId(apply.getForeignId());
        VirtualWardPO virtualWard = this.virtualWardMapper.getVirtualWard(get);
        if(virtualWard == null){
            throw new BusinessException("虚拟病区入住记录不存在");
        }
        if(VirtualWardConstant.TRANSFER_STATUS_OUT == virtualWard.getTransferStatus()){
            throw new BusinessException("患者已转出虚拟病区");
        }
        //如果是当前医生所在科室发起的转出申请
        DoctorPO doctorById = this.doctorService.getDoctorById(apply.getApplyDoctorId());
        if (doctorById.getDepartId().equals(departId)){
            throw new BusinessException("请联系有关医生进行确认");
        }

        //处理胰岛素泵剂量有关的长期用药医嘱未停止
        List list = this.yzService.listYzByMemberIdAndTypeAndItemType(apply.getMemberId(),Arrays.asList(YzConstant.YZ_STATUS_SEND,YzConstant.YZ_STATUS_EXECUTING,YzConstant.YZ_STATUS_EXECUTED), 1,YzConstant.LONG_ADVICE,YzConstant.YZ_ITEM_CODE_INSULIN_PUMP,apply.getHospitalId());
        if (list.size()>0 && list !=null){
            throw new BusinessException("请先停止泵剂量有关医嘱");
        }
        virtualWard.setAllowOutDoctorId(operatorId);
        virtualWard.setOutDate(DateHelper.getNowDate());
        virtualWard.setTransferStatus(VirtualWardConstant.TRANSFER_STATUS_OUT);
        this.virtualWardMapper.updateVirtualWard(virtualWard);

        apply.setApplyStatus(VirtualWardConstant.APPLY_STATUS_AGREE);
        apply.setAllowDoctorId(operatorId);
        apply.setAllowDate(DateHelper.getNowDate());
        this.virtualWardTransferApplyMapper.updateVirtualWardTransferApply(apply);
    }

    @Override
    public List<VirtualWardListPO> listVirtualWardPatient(ListVirtualWardPatientParam param) {
        return this.virtualWardMapper.listVirtualWardPatient(param);
    }

    @Override
    public long countVirtualWardApply(QueryTransferApplyListParam param) {
        return this.virtualWardTransferApplyMapper.countVirtualWardApply(param);
    }

    @Override
    public List<VirtualWardDepartmentPO> listVirtualWardDepartment(String hospitalId) {
        return this.virtualWardMapper.listVirtualWardDepartment(hospitalId);
    }

    @Override
    public VirtualWardPO getCurrentVirtualWard(String memberId, String hospitalId) {
        return this.virtualWardMapper.getCurrentVirtualWard(memberId ,hospitalId);
    }

    @Override
    public PageResult<VirtualWardListPO> listHistoryVirtualWardWithNurseRecord(PageRequest pr, ListHistoryVirtualWardWithNurseRecordParam param) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<VirtualWardListPO> list = this.virtualWardMapper.listHistoryVirtualWardWithNurseRecord(param);
        return new PageResult<>(list);
    }

    @Override
    public void updateVirtualWard(VirtualWardPO update) {
        this.virtualWardMapper.updateVirtualWard(update);
    }

    @Override
    public VirtualWardPO getVirtualWard(GetVirtualWardParam get) {
        return this.virtualWardMapper.getVirtualWard(get);
    }

    @Override
    public List<String> listVirtualWardMemberByMonitor(ListVirtualWardMemberByMonitorParam param) {
        return this.virtualWardMapper.listVirtualWardMemberByMonitor(param);
    }

    /**
     * 获取医生信息映射集合
     * @param doctorIdList
     * @return
     */
    private Map<String ,DoctorPO> getDoctorMap(List<String> doctorIdList){
        List<DoctorPO> doctorList = this.doctorService.listDoctorInId(doctorIdList);
        return doctorList.stream().collect(Collectors.toMap(DoctorPO::getDoctorId, x -> x ,(a ,b) -> a));
    }

    /**
     * 获取血糖集合
     * @param memberIdList
     * @return
     */
    private Map<String, BloodSugarPO> getBloodSugarMap(List<String> memberIdList){
        List<BloodSugarPO> list = this.bloodSugarService.listMembersLatestBloodSugarRecord(memberIdList);
        return list.stream().collect(Collectors.toMap(BloodSugarPO::getMemberId, x -> x ,(a ,b) -> a));
    }

    /**
     * 医生信息处理
     * @param item
     * @param doctorMap
     * @param getProperty
     * @param writeProperty
     */
    private void doctorInfoHandler(Object item ,Map<String ,DoctorPO> doctorMap ,String getProperty ,String writeProperty){
        try{
            PropertyDescriptor pd = new PropertyDescriptor(getProperty ,item.getClass());
            Method method = pd.getReadMethod();
            String doctorId = (String) method.invoke(item);
            DoctorPO doctor = doctorMap.get(doctorId);
            String doctorName = doctor == null ? "--" : doctor.getDoctorName();
            pd = new PropertyDescriptor(writeProperty ,item.getClass());
            method = pd.getWriteMethod();
            method.invoke(item ,doctorName);
        }catch (Exception e){
            log.warn("医生信息赋值处理失败" ,e);
        }
    }

    /**
     * 血糖信息处理
     * @param item
     * @param bloodSugarMap
     */
    private void bloodSugarInfoHandler(ListBloodSugarInfoHandler item , Map<String ,BloodSugarPO> bloodSugarMap){
        BloodSugarPO bloodSugar = bloodSugarMap.get(item.getMemberId());
        if(bloodSugar == null){
            return;
        }
        item.setBloodSugarCode(bloodSugar.getParamCode());
        item.setBloodSugarLevel(bloodSugar.getParamLevel());
        item.setBloodSugarRecordTime(bloodSugar.getRecordDt());
        item.setBloodSugarValue(bloodSugar.getParamValue());
    }

    /**
     * 判断患者虚拟病区入住记录
     * @param memberId
     * @return true 已入住 false 未入住
     */
    private boolean checkMemberVirtualWardRecord(String memberId,String hospitalId){
        GetVirtualWardParam param = new GetVirtualWardParam();
        param.setMemberId(memberId);
        param.setTransferStatus(VirtualWardConstant.TRANSFER_STATUS_IN);
        param.setHospitalId(hospitalId);
        VirtualWardPO virtualWard = this.virtualWardMapper.getVirtualWard(param);
        return virtualWard != null ? true : false;
    }

    /**
     * 判断患者虚拟病区申请记录
     * @param memberId
     * @param applyType
     * @return true 存在 false 不存在
     */
    private boolean checkVirtualWardApplyRecord(String memberId ,Integer applyType,String hospitalId,String hospitalNo){
        GetVirtualWardTransferApplyParam param = new GetVirtualWardTransferApplyParam();
        param.setMemberId(memberId);
        param.setApplyType(applyType);
        param.setApplyStatus(VirtualWardConstant.APPLY_STATUS_NOT_DEAL);
        param.setHospitalId(hospitalId);
//        param.setHospitalNo(hospitalNo);
        VirtualWardTransferApplyPO virtualWardTransferApply = this.virtualWardTransferApplyMapper.getVirtualWardTransferApplyOne(param);
        return virtualWardTransferApply != null ? true : false;
    }

    /**
     * 转入申请列表请求参数处理
     * @param param
     * @return
     */
    private QueryTransferApplyListParam transferIntoApplyListMapperParamHandler(QueryTransferIntoApplyListParam param){
        QueryTransferApplyListParam result = new QueryTransferApplyListParam();
        BeanUtils.copyProperties(result ,param);
        result.setApplyStatus(param.getType());
        result.setApplyType(VirtualWardConstant.APPLY_TYPE_IN);
        if(VirtualWardConstant.APPLY_LIST_ORIGIN_POP == param.getOrigin()){
            result.setRemindDateLess(DateHelper.getNowDate());
            DoctorPopupRemindIgnoreTimePO ignoreTime = this.doctorPopupRemindIgnoreTimeService.getDoctorPopupRemindIgnoreTime(param.getDoctorId()
                    , DoctorPopupRemindConstant.REMIND_TYPE_INTO_VIRTUAL_WARD_APPLY);
            if(ignoreTime != null){
                result.setRemindDateMore(ignoreTime.getIgnoreDt());
            }
        }
        return result;
    }

    /**
     * 转出申请列表请求参数处理
     * @param param
     * @return
     */
    private QueryTransferApplyListParam transferOutApplyListMapperParamHandler(QueryTransferIntoApplyListParam param){
        QueryTransferApplyListParam result = new QueryTransferApplyListParam();
        BeanUtils.copyProperties(result ,param);
        result.setApplyStatus(VirtualWardConstant.APPLY_STATUS_NOT_DEAL);
        result.setApplyType(VirtualWardConstant.APPLY_TYPE_OUT);
        if(VirtualWardConstant.APPLY_LIST_ORIGIN_POP == param.getOrigin()){
            result.setRemindDateLess(DateHelper.getNowDate());
            DoctorPopupRemindIgnoreTimePO ignoreTime = this.doctorPopupRemindIgnoreTimeService.getDoctorPopupRemindIgnoreTime(param.getDoctorId()
                    , DoctorPopupRemindConstant.REMIND_TYPE_OUT_VIRTUAL_WARD_APPLY);
            if(ignoreTime != null){
                result.setRemindDateMore(ignoreTime.getIgnoreDt());
            }
        }
        return result;
    }

    /**
     * 新增虚拟病区记录
     * @param apply
     */
    private void addVirtualWardRecord(VirtualWardTransferApplyPO apply){
        VirtualWardPO virtualWard = new VirtualWardPO();
        BeanUtils.copyProperties(virtualWard ,apply);
        virtualWard.setIntoDoctorId(apply.getApplyDoctorId());
        virtualWard.setIntoDate(apply.getAllowDate());
        virtualWard.setAllowIntoDoctorId(apply.getAllowDoctorId());
        virtualWard.setTransferStatus(VirtualWardConstant.TRANSFER_STATUS_IN);
        virtualWard.setApplyStatus(VirtualWardConstant.APPLY_OUT_STATUS_NO);

//        virtualWard.setUseInsulinPumpDate();
        virtualWard.setUseInsulinPumpStatus(VirtualWardConstant.USE_INSULIN_PUMP_STATUS_NO);
        virtualWard.setOutDoctorId(Constant.DEFAULT_FOREIGN_ID);
        this.virtualWardMapper.addVirtualWard(virtualWard);
    }

    /**
     * 根据id获取未处理的申请记录
     * @param id
     * @return
     */
    private VirtualWardTransferApplyPO getNotDealTransferApplyById(String id){
        GetVirtualWardTransferApplyParam getVirtualWardTransferApplyParam = new GetVirtualWardTransferApplyParam();
        getVirtualWardTransferApplyParam.setId(id);
        getVirtualWardTransferApplyParam.setApplyStatus(VirtualWardConstant.APPLY_STATUS_NOT_DEAL);
        VirtualWardTransferApplyPO apply = this.virtualWardTransferApplyMapper.getVirtualWardTransferApplyOne(getVirtualWardTransferApplyParam);
        if(apply == null){
            throw new BusinessException("申请记录不存在或已被处理");
        }
        return apply;
    }

    /**
     * 获取推迟的提醒时间
     * @param now
     * @return
     */
    private String getDelayRemindDate(String now){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateHelper.getDate(now ,DateHelper.DATETIME_FORMAT));
        calendar.add(Calendar.MINUTE ,5);
        return DateHelper.getDate(calendar.getTime() ,DateHelper.DATETIME_FORMAT);
    }

    /**
     * 判断是否存在未停止的胰岛素泵医嘱
     * @param memberId
     */
    private void checkHasInsulinPumpYz(String memberId,String hospitalId){
//        List<String> yzStatusList = new ArrayList<>();
//        yzStatusList.add(YzConstant.YZ_STATUS_NEW + "");
//        yzStatusList.add(YzConstant.YZ_STATUS_SEND + "");
//        yzStatusList.add(YzConstant.YZ_STATUS_EXECUTING + "");
//        yzStatusList.add(YzConstant.YZ_STATUS_EXECUTED + "");
//        ListMemberYzDTO dto = new ListMemberYzDTO();
//        dto.setMemberId(memberId);
//        dto.setYzStatusList(yzStatusList);
//        dto.setYzItemCodeList(Collections.singletonList(YzConstant.YZ_ITEM_CODE_INSULIN_PUMP));
//        List list = this.yzService.listMemberYz(dto);
//        if(list != null && !list.isEmpty()){
//            throw new BusinessException("请先停止泵有关医嘱");
//        }
        List list = this.yzService.listYzByMemberIdAndTypeAndItemType(memberId,Arrays.asList(YzConstant.YZ_STATUS_SEND,YzConstant.YZ_STATUS_EXECUTING,YzConstant.YZ_STATUS_EXECUTED), 1,YzConstant.LONG_ADVICE,YzConstant.YZ_ITEM_CODE_INSULIN_PUMP,hospitalId);
        if (list.size()>0 && list !=null){
            throw new BusinessException("请先停止泵剂量有关医嘱");
        }
    }

    /**
     * 虚拟病区列表使用设备情况处理
     * @param item
     */
    private void virtualWardListUseMachineInfoHandler(VirtualWardListVO item,String memberId){
        List<Integer> machineList = new ArrayList<>();
        //动态血糖

        List<DYMemberSensorPO> memberSensorPOS = this.dyMemberSensorService.getDYMemberSensorPOByMemberId(memberId);
        for (DYMemberSensorPO po : memberSensorPOS) {
            //1:查询这个探头绑定的信息
            DYYPBloodSugarPO dyypBloodSugarPO = this.dyBloodSugarService.getDyBloodSugar(po.getSensorNo());
            String startTime = DateHelper.getNowDate();
            String endTime = "";
            if (dyypBloodSugarPO != null) {
                //2:计算探头从第一条上传血糖开始时间到现在使用了多少天.
                endTime = DateHelper.dateToString(dyypBloodSugarPO.getRecordTime());
            } else {
                //2:计算探头从绑定开始时间到现在使用了多少天.
                endTime = po.getInsertDt();
            }
            int day = DateHelper.dateCompareGetDay(startTime, endTime);
            if (day <= 14) {
                if (machineList.indexOf(MemberConstant.MEMBER_MACHINE_DYNAMIC_BLOOD_SUGAR)<0){
                    machineList.add(MemberConstant.MEMBER_MACHINE_DYNAMIC_BLOOD_SUGAR);
                }
            }
        }
        //胰岛素泵
        ListMemberYzDTO dto = new ListMemberYzDTO();
        dto.setYzItemCodeList(Collections.singletonList(YzConstant.YZ_ITEM_CODE_INSULIN_PUMP));
        dto.setMemberId(item.getMemberId());
        dto.setRecordOriginList(Collections.singletonList(YzConstant.RECORD_ORIGIN_VIRTUAL_WARD));
        dto.setYzStatusList(new ArrayList<>(Arrays.asList(
                String.valueOf(YzConstant.YZ_STATUS_EXECUTED)
                ,String.valueOf(YzConstant.YZ_STATUS_STOP)
        )));
        dto.setVisitNo(item.getHospitalNo());
        dto.setHospitalId(item.getHospitalId());
        List list = this.yzService.listMemberYz(dto);
        if(list != null && !list.isEmpty()){
            machineList.add(MemberConstant.MEMBER_MACHINE_INSULIN_PUMP);
        }
        item.setUseMachineInfo(Joiner.on(",").join(machineList));
    }

    /**
     * 虚拟病区信息处理
     * @param vo
     * @param inHospitalPatientList
     * @param departmentId
     */
    private void virtualWardInfoHandler(InHospitalPatientListVO vo ,InHospitalPatientListPO inHospitalPatientList ,String departmentId){
        if(StringUtils.isBlank(inHospitalPatientList.getVirtualWardId())){
            vo.setVirtualWardStatus(VIRTUAL_WARD_STATUS_NO);
        }else{
            vo.setVirtualWardId(inHospitalPatientList.getVirtualWardId());
            GetVirtualWardTransferApplyParam param = new GetVirtualWardTransferApplyParam();
            param.setForeignId(inHospitalPatientList.getVirtualWardId());
            param.setApplyStatus(VirtualWardConstant.APPLY_STATUS_NOT_DEAL);
            param.setApplyType(VirtualWardConstant.APPLY_TYPE_OUT);
            VirtualWardTransferApplyPO apply = this.virtualWardTransferApplyMapper.getVirtualWardTransferApplyOne(param);
            if(apply == null){
                vo.setVirtualWardStatus(VIRTUAL_WARD_STATUS_IN);
            }else{
                DoctorPO doctor = this.doctorService.getDoctorById(apply.getApplyDoctorId());
                if(doctor != null){
                    vo.setApplyDoctorName(doctor.getDoctorName());
                    if(departmentId.equals(doctor.getDepartId())){
                        vo.setVirtualWardStatus(VIRTUAL_WARD_STATUS_APPLY_OUT);
                    }else{
                        vo.setVirtualWardStatus(VIRTUAL_WARD_STATUS_PRE_CONFIRM);
                    }
                }
            }
        }
    }

    /**
     * 嵌入页床位列表，标识虚拟病区状态  1 未进入虚拟病区 2 已在虚拟病区 3 已申请转出 4 转出待确定
     */
    private final static int VIRTUAL_WARD_STATUS_NO = 1;
    private final static int VIRTUAL_WARD_STATUS_IN = 2;
    private final static int VIRTUAL_WARD_STATUS_APPLY_OUT = 3;
    private final static int VIRTUAL_WARD_STATUS_PRE_CONFIRM = 4;
}
