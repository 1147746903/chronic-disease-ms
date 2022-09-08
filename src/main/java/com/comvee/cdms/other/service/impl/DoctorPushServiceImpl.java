package com.comvee.cdms.other.service.impl;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.other.bo.DoctorPushTokenBO;
import com.comvee.cdms.other.constant.DoctorPushConstant;
import com.comvee.cdms.other.mapper.DoctorPushCacheMapper;
import com.comvee.cdms.other.mapper.DoctorPushMapper;
import com.comvee.cdms.other.mapper.DoctorPushTokenMapper;
import com.comvee.cdms.other.po.DoctorPushCachePO;
import com.comvee.cdms.other.po.DoctorPushSetPO;
import com.comvee.cdms.other.po.DoctorPushTokenPO;
import com.comvee.cdms.other.service.DoctorPushService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/2
 */
@Service("doctorPushService")
public class DoctorPushServiceImpl implements DoctorPushService {

    @Autowired
    private DoctorPushMapper doctorPushMapper;

    @Autowired
    private DoctorPushTokenMapper doctorPushTokenMapper;

    @Autowired
    private DoctorPushCacheMapper doctorPushCacheMapper;

    @Override
    @Cacheable(value = "public", key = "'push_' + #doctorId" ,unless="#result == null")
    public DoctorPushSetPO getDoctorPushSet(String doctorId) {
        DoctorPushSetPO doctorPushSetPO = this.doctorPushMapper.getDoctorPushSet(doctorId);
        if(doctorPushSetPO == null){
            doctorPushSetPO = new DoctorPushSetPO();
            doctorPushSetPO.setDoctorId(doctorId);
            doctorPushSetPO.setSignPush(DoctorPushConstant.PUSH_STATUS_OPEN);
            doctorPushSetPO.setDialoguePush(DoctorPushConstant.PUSH_STATUS_OPEN);
            doctorPushSetPO.setWxPush(DoctorPushConstant.PUSH_STATUS_CLOSE);
        }
        return doctorPushSetPO;
    }

    @Override
    @CacheEvict(value = "public", key = "'push_' + #doctorPushSetPO.doctorId")
    public void updateDoctorPushSet(DoctorPushSetPO doctorPushSetPO) {
        doctorPushSetPO.setSid(DaoHelper.getSeq());
        this.doctorPushMapper.addDoctorPushSet(doctorPushSetPO);
    }

    @Override
    public void addDoctorPushToken(DoctorPushTokenBO doctorPushTokenBO) {
        DoctorPushTokenPO doctorPushTokenPO = this.doctorPushTokenMapper.getDoctorPushToken(doctorPushTokenBO.getDoctorId(), null);
        if(doctorPushTokenPO == null){
            deletePushToken(doctorPushTokenBO.getPushToken());
            doctorPushTokenPO = new DoctorPushTokenPO();
            BeanUtils.copyProperties(doctorPushTokenPO, doctorPushTokenBO);
            doctorPushTokenPO.setSid(DaoHelper.getSeq());
            this.doctorPushTokenMapper.addDoctorPushToken(doctorPushTokenPO);
        }else{
            if(!doctorPushTokenBO.getPushToken().equals(doctorPushTokenPO.getPushToken())){
                deletePushToken(doctorPushTokenBO.getPushToken());
                DoctorPushTokenPO updatePushToken = new DoctorPushTokenPO();
                BeanUtils.copyProperties(updatePushToken, doctorPushTokenBO);
                updatePushToken.setSid(doctorPushTokenPO.getSid());
                this.doctorPushTokenMapper.updateDoctorPushToken(updatePushToken);
            }
        }
    }

    @Override
    public void addDoctorPushCache(DoctorPushCachePO doctorPushCachePO) {
        doctorPushCachePO.setSid(DaoHelper.getSeq());
        this.doctorPushCacheMapper.addDoctorPushCache(doctorPushCachePO);
    }

    @Override
    public PageResult<DoctorPushCachePO> listDoctorPushCache(PageRequest pr, Integer sendStatus) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<DoctorPushCachePO> list = this.doctorPushCacheMapper.listDoctorPushCache(sendStatus);
        return new PageResult(list);
    }

    @Override
    public DoctorPushTokenPO getDoctorPushToken(String doctorId) {
        return this.doctorPushTokenMapper.getDoctorPushToken(doctorId , null);
    }

    @Override
    public void updateDoctorPushCache(DoctorPushCachePO doctorPushCachePO) {
        this.doctorPushCacheMapper.updateDoctorPushCache(doctorPushCachePO);
    }

    @Override
    public void deleteDoctorPushTokenByDoctorId(String doctorId) {
        DoctorPushTokenPO doctorPushTokenPO = getDoctorPushToken(doctorId);
        if(doctorPushTokenPO == null){
            return;
        }
        this.doctorPushTokenMapper.deleteDoctorPushTokenById(doctorPushTokenPO.getSid());
    }

    @Override
    public void moveExpireDoctorPushCache() {
        this.doctorPushCacheMapper.addDoctorPushCacheHistory();
        this.doctorPushCacheMapper.deleteExpireDoctorPushCache();
    }

    /**
     * 根据token删除记录
     * @param pushToken
     */
    private void deletePushToken(String pushToken){
        DoctorPushTokenPO doctorPushTokenPO = this.doctorPushTokenMapper.getDoctorPushToken(null, pushToken);
        if(doctorPushTokenPO != null){
            this.doctorPushTokenMapper.deleteDoctorPushTokenById(doctorPushTokenPO.getSid());
        }
    }
}
