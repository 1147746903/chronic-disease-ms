package com.comvee.cdms.professional.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.professional.mapper.ProfessionalMapper;
import com.comvee.cdms.professional.po.ProfessionalPO;
import com.comvee.cdms.professional.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wyc
 * @date 2019/5/13 11:08
 */
@Service("professionalService")
public class ProfessionalServiceImpl implements ProfessionalService {

    @Autowired
    private ProfessionalMapper professionalMapper;
    @Override
    public void addProfessional(ProfessionalPO professionalPO) {
        ProfessionalPO po = this.professionalMapper.getPrefessionalByName(professionalPO.getProfessionalName());
        if (po!= null){
            throw new BusinessException("该职称已经存在");
        }
        professionalPO.setProfessionalId(DaoHelper.getSeq());
        this.professionalMapper.addProfessional(professionalPO);
    }

    @Override
    public List<ProfessionalPO> listPrefessional() {
        return this.professionalMapper.listPrefessional();
    }

    @Override
    public List<ProfessionalPO> queryPrefessionalByName(String professionalName) {
        return this.professionalMapper.queryPrefessionalByName(professionalName);
    }
}
