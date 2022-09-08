package com.comvee.cdms.clinic.service.impl;

import com.comvee.cdms.clinic.dto.AddClinicRecordDTO;
import com.comvee.cdms.clinic.mapper.ClinicRecordMapper;
import com.comvee.cdms.clinic.po.ClinicRecordPO;
import com.comvee.cdms.clinic.service.ClinicRecordService;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wyc
 * @date 2019/11/29 11:14
 */
@Service("clinicService")
public class ClinicRecordServiceImpl implements ClinicRecordService {

    @Autowired
    private ClinicRecordMapper clinicRecordMapper;

    @Override
    public void addClinicRecord(AddClinicRecordDTO clinicRecordDTO) {
        ClinicRecordPO recordPO = new ClinicRecordPO();
        BeanUtils.copyProperties(recordPO,clinicRecordDTO);
        String sid = DaoHelper.getSeq();
        recordPO.setSid(sid);
        this.clinicRecordMapper.addClinicRecord(recordPO);
//        //将单天其他记录修改为过期记录
//        DelClinicRecordDTO recordDTO = new DelClinicRecordDTO();
//        recordDTO.setSid(sid);
//        recordDTO.setClinicType(clinicRecordDTO.getClinicType());
//        recordDTO.setClinicId(clinicRecordDTO.getClinicId());
//        String today = DateHelper.getToday();
//        recordDTO.setStartDt(today +" 00:00:00");
//        recordDTO.setEndDt(today + " 23:59:59");
//        this.clinicRecordMapper.delClinicRecord(recordDTO);
    }



    @Override
    public PageResult<ClinicRecordPO> getClinicRecordByClinicIdAndType(PageRequest page,String clinicId, Integer clinicType) {
        PageHelper.startPage(page.getPage(),page.getRows());
        List<ClinicRecordPO> record = this.clinicRecordMapper.getClinicRecordByClinicIdAndType(clinicId, clinicType);
        return new PageResult<ClinicRecordPO>(record);
    }




}
