package com.comvee.cdms.sign.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.checkresult.dto.AddCheckoutDTO;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.dto.MemberArchivesDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.sign.dto.AddWhrDTO;
import com.comvee.cdms.sign.dto.ListWhrDTO;
import com.comvee.cdms.sign.mapper.WhrPOMapper;
import com.comvee.cdms.sign.po.WhrPO;
import com.comvee.cdms.sign.po.WhrPOExample;
import com.comvee.cdms.sign.service.WhrService;
import com.comvee.cdms.sign.vo.WhrVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("whrService")
public class WhrServiceImpl implements WhrService {

    @Autowired
    private WhrPOMapper whrPOMapper;

    @Autowired
    private MemberService memberService;

    @Override
    public String addWhr(AddWhrDTO addWhrDTO) {
        WhrPO add = new WhrPO();
        BeanUtils.copyProperties(add ,addWhrDTO);
        String sid = DaoHelper.getSeq();
        add.setSid(sid);
        addWhrMapper(add);
        return sid;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addWhrMapper(WhrPO po) {
        WhrPOExample whrPOExample = new WhrPOExample();
        whrPOExample.createCriteria().andMemberIdEqualTo(po.getMemberId())
                .andRecordDtEqualTo(po.getRecordDt());
        int count = this.whrPOMapper.countByExample(whrPOExample);
        if(count > 0){
            return;
        }
        if(StringUtils.isBlank(po.getSid())){
            String sid = DaoHelper.getSeq();
            po.setSid(sid);
        }
        this.whrPOMapper.insert(po);

        syncWhrToMemberArchives(po.getMemberId());
    }

    @Override
    public PageResult<WhrVO> listWhr(ListWhrDTO dto, PageRequest pager) {
        MemberPO member = this.memberService.getMemberById(dto.getMemberId());
        Integer sex = null;
        if(member!=null){
            sex = member.getSex();
        }
        WhrPOExample example = new WhrPOExample();
        example.createCriteria().andMemberIdEqualTo(dto.getMemberId());
        example.createCriteria().andOperatorTypeEqualTo(dto.getOperatorType());
        example.createCriteria().andOperatorIdEqualTo(dto.getOperatorId());
        example.createCriteria().andRecordDtBetween(dto.getStartDt(),dto.getEndDT());
        example.createCriteria().andOriginEqualTo(dto.getOrigin());
        example.setOrderByClause("record_dt");
        PageHelper.startPage(pager.getPage(),pager.getRows());
        List<WhrPO> whrPOS = this.whrPOMapper.selectByExample(example);
        PageResult<WhrPO> poPageResult = new PageResult<WhrPO>(whrPOS);
        if(whrPOS!=null && whrPOS.size()>0){
            List<WhrVO> whrVOS = new ArrayList<>(whrPOS.size());
            for(WhrPO po : whrPOS){
                WhrVO vo = new WhrVO();
                BeanUtils.copyProperties(vo,po);
                if(sex!=null && sex.equals(2)){
                    vo.setLessWaist("85");
                    vo.setLessWhr("0.85");
                }
                whrVOS.add(vo);
            }
            PageResult<WhrVO> result = new PageResult<WhrVO>(whrVOS);
            result.setPageNum(pager.getPage());
            result.setPageSize(pager.getRows());
            result.setTotalPages(poPageResult.getTotalPages());
            result.setTotalRows(poPageResult.getTotalRows());
            return result;
        }
        return null;
    }

    @Override
    public WhrPO getLatestWhr(String memberId) {
        return this.whrPOMapper.getNewWhr(memberId);
    }

    /**
     * 同步腰臀比到档案
     * @param whr
     */
    private void syncWhrToMemberArchives(String memberId){
        WhrPO newestRecord = getLatestWhr(memberId);
        if(newestRecord == null){
            return;
        }
        JSONObject archivesJson = new JSONObject();
        JSONObject signJson = new JSONObject();
        archivesJson.put("sign" ,signJson);
        signJson.put("whrDt" ,newestRecord.getRecordDt());
        signJson.put("waistline" ,newestRecord.getWaist());
        signJson.put("whr" ,newestRecord.getWhr());
        signJson.put("hipline" ,newestRecord.getHip());

        MemberArchivesDTO update = new MemberArchivesDTO();
        update.setMemberId(memberId);
        update.setArchivesJson(archivesJson.toJSONString());

        this.memberService.updateMemberArchive(update);
    }
}
