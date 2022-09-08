package com.comvee.cdms.sign.service.impl;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.mapper.BloodKetoneMapper;
import com.comvee.cdms.sign.po.BloodKetonePO;
import com.comvee.cdms.sign.service.BloodKetoneService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author: 罗伟雄
 * @date: 2021/1/20
 */
@Service("BloodKetoneService")
public class BloodKetoneServiceImpl implements BloodKetoneService {

    private final static Logger log = LoggerFactory.getLogger(BloodKetoneServiceImpl.class);

    @Autowired
    private BloodKetoneMapper bloodKetoneMapper;

    @Autowired
    private MemberService memberService;

    @Override
    public String addBloodKetone(AddBloodKetoneServiceDTO dto) {
        //重复数据不添加
        GetBloodKetoneDTO getBloodKetoneDTO = new GetBloodKetoneDTO();
        getBloodKetoneDTO.setRecordDt(dto.getRecordDt());
        getBloodKetoneDTO.setMemberId(dto.getMemberId());
        getBloodKetoneDTO.setParamValue(dto.getParamValue());
        BloodKetonePO bloodKetone = bloodKetoneMapper.getBloodKetone(getBloodKetoneDTO);
        if (bloodKetone != null){
            log.warn("重复上传的血酮记录,血酮记录id:{} ,上传的血酮记录对象:{}" ,bloodKetone.getSid() , JSON.toJSONString(dto));
            return bloodKetone.getSid();
        }

        //新增血酮记录开始
        AddBloodKetoneMapperDTO addBloodKetoneMapperDTO = new AddBloodKetoneMapperDTO();
        BeanUtils.copyProperties(addBloodKetoneMapperDTO, dto);
        addBloodKetoneMapperDTO.setSid(DaoHelper.getSeq());
        //参考范围去控制目标拿
        RangeBO rangeBO = memberService.getMemberRange(dto.getMemberId());
        log.info("患者id:{},患者的控制目标血酮数据:{}", dto.getMemberId(), rangeBO.getHighKetone());
        addBloodKetoneMapperDTO.setReferenceRange(rangeBO.getHighKetone());
        //比较血酮和参考范围
        if (Double.parseDouble(dto.getParamValue()) >= Double.parseDouble(rangeBO.getHighKetone())){
            addBloodKetoneMapperDTO.setKetoneStatus(2);
        }else {
            addBloodKetoneMapperDTO.setKetoneStatus(1);
        }
        //查询该患者是否有记录数据
        GetBloodKetoneDTO bloodKetoneDTO = new GetBloodKetoneDTO();
        bloodKetoneDTO.setMemberId(dto.getMemberId());
        bloodKetoneDTO.setLatestType(SignConstant.LATEST_DATA_YES);
        BloodKetonePO bloodKetonePO = bloodKetoneMapper.getBloodKetone(bloodKetoneDTO);

        //为空则该条新增为最新，不为空则对比记录时间
        if (bloodKetonePO == null){
            addBloodKetoneMapperDTO.setLatestType(SignConstant.LATEST_DATA_YES);

        }else if (DateHelper.dateAfter(dto.getRecordDt(), DateHelper.DATETIME, bloodKetonePO.getRecordDt(), DateHelper.DATETIME)){
            addBloodKetoneMapperDTO.setLatestType(SignConstant.LATEST_DATA_YES);
            //修改之前数据，不为最新数据
            UpdateBloodKetoneDTO updateDTO = new UpdateBloodKetoneDTO();
            updateDTO.setSid(bloodKetonePO.getSid());
            updateDTO.setLatestType(SignConstant.LATEST_DATA_NO);
            bloodKetoneMapper.modifyBloodKetone(updateDTO);
        } else {
            addBloodKetoneMapperDTO.setLatestType(SignConstant.LATEST_DATA_NO);
        }


        bloodKetoneMapper.addBloodKetone(addBloodKetoneMapperDTO);

        return addBloodKetoneMapperDTO.getSid();
    }

    @Override
    public PageResult<BloodKetonePO> listBloodKetone(ListBloodKetoneDTO listBloodKetoneDTO, PageRequest pr) {
        PageHelper.startPage(pr.getPage(),pr.getRows());
        List<BloodKetonePO> bloodKetonePOList = bloodKetoneMapper.listBloodKetone(listBloodKetoneDTO);

        return new PageResult<BloodKetonePO>(bloodKetonePOList);
    }

    @Override
    public List<BloodKetonePO> bloodKetoneList(ListBloodKetoneDTO listBloodKetoneDTO) {
        List<BloodKetonePO> bloodKetonePOList = bloodKetoneMapper.listBloodKetone(listBloodKetoneDTO);
        return bloodKetonePOList;
    }
}
