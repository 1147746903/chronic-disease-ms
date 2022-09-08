package com.comvee.cdms.hospital.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.hospital.model.bo.CheckinInfoBO;
import com.comvee.cdms.hospital.model.bo.InHospitalLogBO;
import com.comvee.cdms.hospital.model.dto.*;
import com.comvee.cdms.hospital.model.po.CheckinInfoPO;
import com.comvee.cdms.hospital.model.po.HosOptionPO;
import com.comvee.cdms.hospital.model.po.HospitalPO;
import com.comvee.cdms.hospital.model.vo.InHospitalLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
public interface HospitalService {

    /**
     * 新增医院
     * @param addHospitalDTO
     * @return
     */
    String addHospital(AddHospitalDTO addHospitalDTO);

    /**
     * 获取医院
     * @param hospitalId
     * @return
     */
    HospitalPO getHospital(String hospitalId);

    /**
     * 加载医院列表
     * @param listHospitalDTO
     * @return
     */
    PageResult<HospitalPO> listHospital(ListHospitalDTO listHospitalDTO, PageRequest pr);

    /**
     * 修改医院
     * @param updateHospitalDTO
     */
    void updateHospital(UpdateHospitalDTO updateHospitalDTO);

    /**
     * 根据省市查询医院
     * @param cityId
     * @param areaId
     * @return
     */
    List<HospitalPO> listHospitalByProvinceAndCity(String provinceId,String cityId);

    /**
     * 获取在院业务信息
     * @param memberId
     * @return
     */
    CheckinInfoBO getCheckinInfoBOByMid(String memberId ,String hospitalId);

    /**
     * 更新在院信息
     * @param updDTO
     */
    void updateCheckinInfo(CheckinInfoDTO updDTO);

    /**
     * 获取出入院信息记录
     * @param memberId
     * @return
     */
    InHospitalLogBO getMemberInHospitalLogBOByMid(String memberId);

    /**
     * 更新出入院信息记录
     * @param updDTO
     */
    void updateInHospitalLog(InHospitalLogDTO updDTO);

    /**
     * 添加出入院信息记录
     * @param addDTO
     */
    void addInHospitalLog(InHospitalLogDTO addDTO);

    /**
     * 获取出入院信息记录列表
     * @param memberId
     * @return
     */
    List<InHospitalLogVO> listMemberInHospitalLogByMid(String memberId);

    /**
     * 加载所有医院
     * @return
     */
    List<HospitalPO> listAllHospital();

    List<HospitalPO> listHospitalByAreaId(String areaId);

    List<HospitalPO> listTownHospitalByAreaId(String areaId);

    /**
     * 获取医院选项设置
     * @param dto
     * @return
     */
    HosOptionPO getHosOption(HosOptionDTO dto);

    /**
     * 获取每个医院虚拟病区（非指定科室）的住院患者
     * @param hospitalId
     * @return
     */
    List<CheckinInfoPO> listCheckinInfoOfV(String hospitalId,String unDepartName);

     //获取地区医院id列表
    List<String> getHospitalIdsByAreaId(String areaId);



}
