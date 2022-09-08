package com.comvee.cdms.sign.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.sign.dto.AddWhrDTO;
import com.comvee.cdms.sign.dto.ListWhrDTO;
import com.comvee.cdms.sign.po.WhrPO;
import com.comvee.cdms.sign.vo.WhrVO;

public interface WhrService {

    /**
     * 添加腰臀比记录
     * @return
     */
    String addWhr(AddWhrDTO dto);

    /**
     * 腰臀比列表
     * @param dto
     * @return
     */
    PageResult<WhrVO> listWhr(ListWhrDTO dto, PageRequest pager);

    /**
     * 获取最新的腰臀比
     * @param memberId
     * @return
     */
    WhrPO getLatestWhr(String memberId);

    /**
     * 腰臀比记录入库
     * @param whrPO
     */
    void addWhrMapper(WhrPO whrPO);
}
