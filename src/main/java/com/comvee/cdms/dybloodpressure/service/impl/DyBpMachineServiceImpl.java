package com.comvee.cdms.dybloodpressure.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dybloodpressure.dto.AddBpMemberMachineDTO;
import com.comvee.cdms.dybloodpressure.dto.GetMemberMachineDTO;
import com.comvee.cdms.dybloodpressure.mapper.BpMemberMachineMapper;
import com.comvee.cdms.dybloodpressure.po.BpMemberMachinePO;
import com.comvee.cdms.dybloodpressure.service.DyBpMachineService;
import com.comvee.cdms.dybloodpressure.vo.ListMemberMachineBindVO;
import com.comvee.cdms.member.constant.MemberConstant;
import com.comvee.cdms.member.dto.GetMemberDTO;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author linr
 * @Date 2021/11/3
 */
@Service
public class DyBpMachineServiceImpl implements DyBpMachineService {

    @Autowired
    private BpMemberMachineMapper bpMemberMachineMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberService memberService;

    private final static Logger log = LoggerFactory.getLogger(DyBpMachineServiceImpl.class);

    @Override
    @Transactional
    public void addBpMemberMachineBind(AddBpMemberMachineDTO addBpMemberMachineDTO) {
        String nowDate = DateHelper.getNowDate();
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(addBpMemberMachineDTO.getMemberId());
        MemberPO member = memberMapper.getMember(getMemberDTO);
        if (null == member){
            throw new BusinessException("该患者不存在");
        }

        BpMemberMachinePO bpMemberMachinePO = new BpMemberMachinePO();
        bpMemberMachinePO.setMemberId(addBpMemberMachineDTO.getMemberId());
        bpMemberMachinePO.setIsValid(1);
        List<BpMemberMachinePO> memberBindList = bpMemberMachineMapper.queryAll(bpMemberMachinePO);
        if (memberBindList.size()>0){
            throw new BusinessException("该患者已存在绑定的设备");
        }
        bpMemberMachinePO.setMemberId(null);
        bpMemberMachinePO.setMachineNo(addBpMemberMachineDTO.getMachineNo());
        List<BpMemberMachinePO> machineBindList = bpMemberMachineMapper.queryAll(bpMemberMachinePO);
        //该设备存在绑定的患者
        if (machineBindList.size()>0){
            BpMemberMachinePO machinePO = machineBindList.get(0);
            getMemberDTO.setMemberId(machinePO.getMemberId());
            MemberPO machineMember = memberMapper.getMember(getMemberDTO);
            //判断患者属于住院还是门诊
            Integer memberType = memberService.judgeMemberType(machinePO.getMemberId(), addBpMemberMachineDTO.getHospitalId());
            if (memberType.equals(MemberConstant.MEMBER_TYPE_OUT_DEPART)){
                throw new BusinessException("该设备已被门诊患者"+machineMember.getMemberName()+"绑定");
            }else if(memberType.equals(MemberConstant.MEMBER_TYPE_IN_HOSP)){
                throw new BusinessException("该设备已被住院患者"+machineMember.getMemberName()+"绑定");
            }else {
                throw new BusinessException("该设备已被患者"+machineMember.getMemberName()+"绑定");
            }
        }
        //入库
        BeanUtils.copyProperties(bpMemberMachinePO,addBpMemberMachineDTO);
        bpMemberMachinePO.setSid(DaoHelper.getSeq());
        bpMemberMachinePO.setPlanStartDt(nowDate);
        String planEndDt = DateHelper.plusDate(nowDate, 5, Integer.parseInt(addBpMemberMachineDTO.getAutoStopMinutes()), DateHelper.DATETIME_FORMAT);
        bpMemberMachinePO.setPlanEndDt(planEndDt);
        bpMemberMachineMapper.insert(bpMemberMachinePO);
    }

    @Override
    @Transactional
    public void removeBpMemberMachineBind(String memberId,String machineNo) {
        BpMemberMachinePO bpMemberMachinePO = new BpMemberMachinePO();
        bpMemberMachinePO.setMemberId(memberId);
        bpMemberMachinePO.setMachineNo(machineNo);
        bpMemberMachinePO.setIsValid(1);
        List<BpMemberMachinePO> memberBindList = bpMemberMachineMapper.queryAll(bpMemberMachinePO);
        if (memberBindList.size() == 0){
            throw new BusinessException("该患者与该设备不存在绑定关系,请确认");
        }
        bpMemberMachinePO.setSid(memberBindList.get(0).getSid());
        bpMemberMachinePO.setIsValid(0);
        bpMemberMachineMapper.update(bpMemberMachinePO);
    }

    @Override
    public PageResult listMemberMachineBind(GetMemberMachineDTO getMemberMachineDTO, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<ListMemberMachineBindVO> listMemberMachineBindVOS = bpMemberMachineMapper.listVO(getMemberMachineDTO);
        return new PageResult(listMemberMachineBindVOS);
    }

    @Override
    public PageResult listAllMemberMachineBind(GetMemberMachineDTO getMemberMachineDTO, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<ListMemberMachineBindVO> listMemberMachineBindVOS = bpMemberMachineMapper.listAllActiveBindList(getMemberMachineDTO);
        if (listMemberMachineBindVOS.size()>0){
            for (ListMemberMachineBindVO listMemberMachineBindVO : listMemberMachineBindVOS) {
                listMemberMachineBindVO.setAge(String.valueOf(DateHelper.getAge(listMemberMachineBindVO.getAge())));
            }
        }
        return new PageResult(listMemberMachineBindVOS);
    }

    @Override
    public Map<String, Object> loadMachineInfo(String machineNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("isBind",0);
        GetMemberMachineDTO getMemberMachineDTO = new GetMemberMachineDTO();
        getMemberMachineDTO.setMachineNo(machineNo);
        List<ListMemberMachineBindVO> machinePOs = bpMemberMachineMapper.listAllActiveBindList(getMemberMachineDTO);
        if (!machinePOs.isEmpty()){
            for (ListMemberMachineBindVO machinePO : machinePOs) {
                machinePO.setAge(String.valueOf(DateHelper.getAge(machinePO.getAge())));
            }
            map.put("isBind",1);
            map.put("data",machinePOs.get(0));
        }
        return map;
    }

    @Override
    @Transactional
    public void autoUnbindMemberMachineHandler() {
        List<BpMemberMachinePO> endBindList = bpMemberMachineMapper.unBindPlanEndBindList();
        if (endBindList != null && endBindList.size()>0){
            List<String> machineIdList = endBindList.stream().map(BpMemberMachinePO::getMachineNo).collect(Collectors.toList());
            List<String> machineList = endBindList.stream().map(BpMemberMachinePO::getSid).collect(Collectors.toList());
            log.info("动态血压设备佩戴计划到期失效列表:"+machineList);
            bpMemberMachineMapper.unBindPlanEnd(machineIdList);
        }
    }
}
