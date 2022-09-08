package com.comvee.cdms.checkresult.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.checkresult.bo.CheckRemindDataBO;
import com.comvee.cdms.checkresult.constant.*;
import com.comvee.cdms.checkresult.dto.*;
import com.comvee.cdms.checkresult.mapper.CheckRemindMapper;
import com.comvee.cdms.checkresult.po.CheckRemindPO;
import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.service.CheckRemindService;
import com.comvee.cdms.checkresult.tool.CheckRemindTool;
import com.comvee.cdms.checkresult.vo.CheckRemindVO;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.EnumUtils;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.member.po.MemberArchivesPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.user.tool.SessionTool;
import com.comvee.cdms.visit.constant.VisitEventEnum;
import com.comvee.cdms.visit.dto.AddVistiEventDTO;
import com.comvee.cdms.visit.service.VisitEventService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;

@Service("memberCheckRemindService")
public class CheckRemindServiceImpl implements CheckRemindService {


    @Autowired
    private CheckRemindMapper checkRemindMapper;

    @Autowired
    private MemberService memberService;

    @Override
    public PageResult<CheckRemindVO> listDoctorCheckRemind(String hospitalId, String start,String end, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<CheckRemindVO> checkRemindPOS = this.checkRemindMapper.listDoctorCheckRemind(hospitalId, start,end);
        for(CheckRemindVO c : checkRemindPOS){
            c.setCheckItemName(CheckRemindCategory.getName(c.getCheckItem()));
        }
        return new PageResult(checkRemindPOS);
    }

    @Override
    public void modifyCheckRemind(ModifyCheckRemindDTO dto) {
        GetMemberCheckRemindDTO getDto = new GetMemberCheckRemindDTO();
        getDto.setSid(dto.getSid());
        CheckRemindPO local = this.checkRemindMapper.getMemberCheckRemind(getDto);
        if(local == null){
            throw new BusinessException("检查提醒记录不存在，修改失败");
        }
        if(local.getFinishStatus() == CheckRemindConstant.FINISH_STATUS_NO){
            throw new BusinessException("未完成状态的记录不可修改");
        }
        CheckRemindPO update = new CheckRemindPO();
        BeanUtils.copyProperties(update ,dto);
        this.checkRemindMapper.updateCheckRemind(update);
    }

    @Override
    public PageResult<String> listNeedCheckRemindMember(PageRequest pr, String reviewDt) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<String> list = this.checkRemindMapper.listNeedCheckRemindMember(reviewDt);
        return new PageResult<>(list);
    }

    @Override
    public List<CheckRemindPO> listMemberCheckRemind(ListMemberCheckRemindDTO dto) {
        return this.checkRemindMapper.listMemberCheckRemind(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class ,isolation = Isolation.READ_UNCOMMITTED)
    public Map<String,Object> resolveCheckoutRemind(List<ResolveCheckoutRemindDTO> list) {
        Map<String, Object> reviewResult = new HashMap<>();
        MultiValueMap<String ,ResolveCheckoutRemindDTO> checkoutMap = new LinkedMultiValueMap<>();
        for(ResolveCheckoutRemindDTO r: list){
            String category = CheckRemindConstant.getCategoryByCheckoutCode(r.getCheckoutCode());
            if(category != null){
                checkoutMap.add(category ,r);
            }
        }
        if(checkoutMap.isEmpty()){
            return reviewResult;
        }
        String memberId = list.get(0).getMemberId();
        CheckRemindTool.PatientInfo patientInfo = getPatientInfo(memberId);

        GetMemberCheckRemindDTO getDTO = null;
        CheckRemindPO checkRemind = null;
        Map<String ,CheckRemindDataBO> dataMap = null;
        CheckRemindItem item = null;
        CheckRemindDataBO data = null;

        for(Map.Entry<String ,List<ResolveCheckoutRemindDTO>> entry : checkoutMap.entrySet()){
            getDTO = new GetMemberCheckRemindDTO();
            getDTO.setMemberId(memberId);
            getDTO.setCheckItem(entry.getKey());
            checkRemind = this.checkRemindMapper.getMemberCheckRemind(getDTO);
            List<CheckRemindDataBO> dataList = null;
            if(checkRemind != null){
                dataList = JSON.parseArray(checkRemind.getCheckData() ,CheckRemindDataBO.class);
            }
            if(dataList == null){
                dataList = new ArrayList<>();
            }
            dataMap = dataList.stream().collect(Collectors.toMap(x -> x.getItemCode() ,x -> x));
            for(ResolveCheckoutRemindDTO d : entry.getValue()){
                item = CheckRemindConstant.getCheckRemindItemByCheckoutCode(d.getCheckoutCode());
                if(item == null){
                    continue;
                }
                String itemCode = item.toString();
                data = dataMap.get(itemCode);
                if(data == null){
                    data = new CheckRemindDataBO();
                }
                if(!StringUtils.isBlank(data.getRecordDt())){
                    if(data.getRecordDt().compareTo(d.getRecordDt()) >= 0){
                        continue;
                    }
                }
                String reviewDt = CheckRemindTool.of(item ,d.getValue() ,d.getRecordDt() ,patientInfo).access();
                if(reviewDt == null){
                    continue;
                }
                data.setItemCode(itemCode);
                data.setReviewDt(reviewDt);
                data.setData(d.getValue());
                data.setRecordDt(d.getRecordDt());
                dataMap.put(itemCode ,data);
                //就诊事件
                reviewResult.put(d.getCheckoutCode(),reviewDt);
            }
            if(dataMap.isEmpty()){
                continue;
            }
            dataList = dataMap.values().stream().collect(Collectors.toList());
            String reviewDt = dataList.stream().min(Comparator.comparing(CheckRemindDataBO::getReviewDt)).get().getReviewDt();
            boolean addCheckRemind = false;
            if(checkRemind == null){
                checkRemind = new CheckRemindPO();
                addCheckRemind = true;
            }
            Integer finishStatus = CheckRemindConstant.getCheckoutListByCategory(entry.getKey()).size() == dataList.size()
                    ? CheckRemindConstant.FINISH_STATUS_YES : CheckRemindConstant.FINISH_STATUS_NO;
            checkRemind.setFinishStatus(finishStatus);
            checkRemind.setCheckData(JSON.toJSONString(dataList));
            if(finishStatus == CheckRemindConstant.FINISH_STATUS_YES){
                checkRemind.setReviewDt(reviewDt);
            }
            if(addCheckRemind){
                checkRemind.setSid(DaoHelper.getSeq());
                checkRemind.setMemberId(memberId);
                checkRemind.setCheckItem(entry.getKey());
                this.checkRemindMapper.addCheckRemind(checkRemind);
            }else{
                this.checkRemindMapper.updateCheckRemind(checkRemind);
            }
        }
        return reviewResult;
    }

    @Override
    public void resolveInspectionRemind(ResolveInspectionRemindDTO dto) {
        CheckRemindItem item = CheckRemindConstant.getCheckRemindItemByCheckoutCode(dto.getInspectionCode());
        if(item == null){
            return;
        }
        CheckRemindCategory checkRemindCategory = CheckRemindConstant.getCategoryByInspectionCode(dto.getInspectionCode());
        if(checkRemindCategory == null){
            return;
        }
        CheckRemindTool.PatientInfo patientInfo = getPatientInfo(dto.getMemberId());
        String reviewDt = CheckRemindTool.of(item ,JSON.parseObject(dto.getDataJson()) ,dto.getReportDt() ,patientInfo).access();
        if(reviewDt == null){
            return;
        }
        CheckRemindDataBO data = new CheckRemindDataBO();
        data.setItemCode(item.toString());
        data.setReviewDt(reviewDt);
        data.setData(dto.getDataJson());

        GetMemberCheckRemindDTO getDTO = new GetMemberCheckRemindDTO();
        getDTO.setMemberId(dto.getMemberId());
        getDTO.setCheckItem(checkRemindCategory.getCode());
        CheckRemindPO checkRemind = this.checkRemindMapper.getMemberCheckRemind(getDTO);
        boolean doAdd = false;
        if(checkRemind == null){
            doAdd = true;
            checkRemind = new CheckRemindPO();
            checkRemind.setSid(DaoHelper.getSeq());
            checkRemind.setMemberId(dto.getMemberId());
            checkRemind.setCheckItem(checkRemindCategory.getCode());
        }
        checkRemind.setReviewDt(reviewDt);
        checkRemind.setFinishStatus(CheckRemindConstant.FINISH_STATUS_YES);
        checkRemind.setCheckData(JSON.toJSONString(Collections.singletonList(data)));
        if(doAdd){
            this.checkRemindMapper.addCheckRemind(checkRemind);
        }else{
            this.checkRemindMapper.updateCheckRemind(checkRemind);
        }
    }

    private void checkRemindPatientInfoHandler(CheckRemindTool.PatientInfo patientInfo ,MemberArchivesPO archivesPO){
        if(archivesPO == null){
            patientInfo.setChd(0);
            return;
        }
        String jsonString = archivesPO.getArchivesJson();
        if(StringUtils.isBlank(jsonString)){
            patientInfo.setChd(0);
            return;
        }
        JSONObject jsonObject = JSON.parseObject(jsonString);

        JSONObject complication = jsonObject.getJSONObject("complication");
        if(complication == null){
            patientInfo.setChd(0);
        }else{
            String chd = complication.getString("chd");
            if(chd != null && chd.equals("QZ01")){
                patientInfo.setChd(1);
            }else{
                patientInfo.setChd(0);
            }
        }
    }

    private CheckRemindTool.PatientInfo getPatientInfo(String memberId){
        CheckRemindTool.PatientInfo patientInfo = CheckRemindTool.createPatientInfo();
        MemberPO memberPO = this.memberService.getMemberById(memberId);
        MemberArchivesPO archivesPO = this.memberService.getMemberArchives(memberId ,null);
        patientInfo.setSex(memberPO.getSex());
        checkRemindPatientInfoHandler(patientInfo ,archivesPO);
        return patientInfo;
    }

}
