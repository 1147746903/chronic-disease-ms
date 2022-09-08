package com.comvee.cdms.defender.wechat.service;

import com.comvee.cdms.defender.wechat.dto.AddBarrierQuesCfgDTO;
import com.comvee.cdms.defender.wechat.dto.BarrierResultDTO;
import com.comvee.cdms.defender.wechat.dto.SubmitBarrierDTO;
import com.comvee.cdms.defender.wechat.dto.UpdateBarrierQuesCfgDTO;
import com.comvee.cdms.defender.wechat.po.MemberBarrierPO;
import com.comvee.cdms.defender.wechat.vo.BarrierQuesCfgVO;
import com.comvee.cdms.defender.wechat.vo.BarrierResultVO;
import com.comvee.cdms.defender.wechat.vo.ListBarrierQuesVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @Date 2021/12/3
 */
public interface MemberBarrierServiceI {

    /**
     * 加载用户闯关列表
     * @param memberId
     * @return
     */
    List<MemberBarrierPO> listMemberBarrier(String memberId);

    /**
     * 根据关卡id获取问题列表
     * @param sid
     * @return
     */
    List<ListBarrierQuesVO> loadBarrierBySid(String sid);

    /**
     * 提交闯关知识挑战
     * @param submitBarrierDTO
     */
    String submitBarrierQues(SubmitBarrierDTO submitBarrierDTO);

    /**
     * 关卡结果页
     * @param sid 关卡id
     * @return
     */
    BarrierResultVO resultBarrier(BarrierResultDTO barrierResultDTO);

    /**
     * 错题集
     * @param sid 关卡id
     * @return
     */
    List<Map<String, Object>> listBarrierError(String sid, String batchId);


    /**
     * ---------------------------知识挑战后台题库配置-------------------------------
     */

    void addBarrierQuesCfg(AddBarrierQuesCfgDTO addBarrierQuesCfgDTO);

    void delBarrierQuesCfg(String sid);

    void updateBarrierQuesCfg(UpdateBarrierQuesCfgDTO updateBarrierQuesCfgDTO);

    PageResult<BarrierQuesCfgVO> listBarrierQuesCfgVO(String title, PageRequest page);

    BarrierQuesCfgVO detailBarrierQuesCfgVO(String sid);


}
