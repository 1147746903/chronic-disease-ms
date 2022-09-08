package com.comvee.cdms.drugs.service.impl;


import java.util.List;
import java.util.Map;

import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.drugs.constant.DrugsDepotConstant;
import com.comvee.cdms.drugs.dto.DrugsDTO;
import com.comvee.cdms.drugs.dto.DrugsDepotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.drugs.dto.ListDrugsDepotDTO;
import com.comvee.cdms.drugs.mapper.DrugsMemberMapper;
import com.comvee.cdms.drugs.po.DrugsDepotPO;
import com.comvee.cdms.drugs.service.DrugsMemberService;
import com.github.pagehelper.PageHelper;


/**
 * @author: wangyx
 * @date: 2018/12/27
 */
@Service("drugsMemberService")
public class DrugsMemberServiceImpl implements DrugsMemberService {

    @Autowired
    private DrugsMemberMapper drugsMemberMapper;


    @Override
    public List<DrugsDepotPO> listDrugsDepot(ListDrugsDepotDTO listDrugsDepotDTO) {
        return this.drugsMemberMapper.listDrugsDepot(listDrugsDepotDTO);
    }

    @Override
    public PageResult<DrugsDepotPO> listDrugsDepotPageByHosForDefault(ListDrugsDepotDTO listDrugsDepotDTO,PageRequest page) {

        PageHelper.startPage(page.getPage(),page.getRows());
        List<DrugsDepotPO> list= this.drugsMemberMapper.listDrugsDepot(listDrugsDepotDTO);
        PageResult<DrugsDepotPO> tempPageResult = new PageResult<DrugsDepotPO>(list);
        if(list!=null&&list.size()>0){
            return tempPageResult;
        } else {
            // 非默认
            if(listDrugsDepotDTO.getBelongType()!=0){
                listDrugsDepotDTO.setBelongType(0);
                listDrugsDepotDTO.setBelongId("-1");
                PageHelper.startPage(page.getPage(),page.getRows());
                list= this.drugsMemberMapper.listDrugsDepot(listDrugsDepotDTO);
                tempPageResult = new PageResult<DrugsDepotPO>(list);
            }
            return tempPageResult;
        }

    }

    @Override
    public DrugsDepotPO getDrugsDepot(String sid) {
        return this.drugsMemberMapper.getDrugsDepot(sid);
    }

    @Override
    public String addDrugsDepotOfHospital(DrugsDepotDTO dto) {
        DrugsDepotPO drugsDepotPO = new DrugsDepotPO();
        BeanUtils.copyProperties(drugsDepotPO,dto);
        String id =DaoHelper.getSeq();
        drugsDepotPO.setId(id);
        drugsDepotPO.setPinyin(Pinyin4jUtil.getPinYin(drugsDepotPO.getDrugName()));
        this.drugsMemberMapper.addDrugsDepot(drugsDepotPO);
        return id;
    }

    @Override
    public void addDrugsDeportNj(String drugsDrportJson,String hospitalId) {
        if (!StringUtils.isBlank(drugsDrportJson)){
            List<Map<String, Object>> mapList = JsonSerializer.jsonToMapList(drugsDrportJson);
            if (null != mapList && mapList.size() > 0){
                mapList.forEach(x->{
                    String drugName = x.get("name").toString();
                    int type = Integer.parseInt(x.get("type").toString());
                    DrugsDTO dto = new DrugsDTO();
                    dto.setBelongId(hospitalId);
                    dto.setDrugName(drugName);
                    dto.setType(type);
                    dto.setBelongType(DrugsDepotConstant.DRUGS_TYPE_HOSPITAL);
                    DrugsDepotPO param = this.drugsMemberMapper.getDrugsDepotByParam(dto);
                    if (param == null){
                        DrugsDepotPO depotPO = new DrugsDepotPO();
                        depotPO.setId(DaoHelper.getSeq());
                        depotPO.setDrugName(drugName);
                        depotPO.setType(type + "");
                        depotPO.setPinyin(Pinyin4jUtil.getPinYin(drugName));
                        depotPO.setInitials(Pinyin4jUtil.getPinYinHeadChar(drugName));
                        depotPO.setBelongId(hospitalId);
                        depotPO.setBelongType(DrugsDepotConstant.DRUGS_TYPE_HOSPITAL);
                        this.drugsMemberMapper.addDrugsDepot(depotPO);
                    }
                });
            }
        }
    }


}
