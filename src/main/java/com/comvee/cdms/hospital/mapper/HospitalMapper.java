package com.comvee.cdms.hospital.mapper;

import com.comvee.cdms.hospital.model.dto.HosOptionDTO;
import com.comvee.cdms.hospital.model.dto.ListHospitalDTO;
import com.comvee.cdms.hospital.model.dto.UpdateHospitalDTO;
import com.comvee.cdms.hospital.model.po.CheckinInfoPO;
import com.comvee.cdms.hospital.model.po.HosOptionPO;
import com.comvee.cdms.hospital.model.po.HospitalPO;
import com.comvee.cdms.hospital.model.po.InHospitalLogPO;
import com.comvee.cdms.hospital.model.vo.InHospitalLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
public interface HospitalMapper {

    /**
     * 添加医院
     * @param hospitalPO
     */
    void addHospital(HospitalPO hospitalPO);

    /**
     * 修改医院
     * @param hospitalPO
     */
    void updateHospital(UpdateHospitalDTO updateHospitalDTO);

    /**
     * 根据id获取医院
     * @param hospitalId
     * @return
     */
    HospitalPO getHospital(String hospitalId);

    /**
     * 加载医院列表
     * @param listHospitalDTO
     * @return
     */
    List<HospitalPO> listHospital(ListHospitalDTO listHospitalDTO);

    /**
     * 删除医院
     * @param hospitalId
     */
    void deleteHospital(String hospitalId);

    /**
     * 根据省市查询医院
     * @param cityId
     * @param areaId
     * @return
     */
    List<HospitalPO> listHospitalByProvinceAndCity(@Param("provinceId") String provinceId,@Param("cityId") String cityId);

    /**
     * 获取患者在院信息
     * @param memberId
     * @return
     */
    CheckinInfoPO getCheckinInfoByMid(@Param("memberId")String memberId ,@Param("hospitalId")String hospitalId);

    /**
     * 获取患者当前出入院在院日记
     * @param memberId
     * @return
     */
    InHospitalLogPO getMemberInHospitalLogBOByMid(String memberId);

    /**
     * 更新在院记录
     * @param checkinInfoPO
     */
    void updateCheckinInfo(@Param("item") CheckinInfoPO item);

    /**
     * 更新出入院日记
     * @param inHospitalLogPO
     */
    void updateInHospitalLog(@Param("item") InHospitalLogPO item);

    /**
     * 添加出入院日记
     * @param inHospitalLogPO
     */
    void addInHospitalLog(@Param("item") InHospitalLogPO item);

    /**
     * 获取患者出入院在院日记列表
     * @param memberId
     * @return
     */
    List<InHospitalLogVO> listMemberInHospitalLogByMid(String memberId);

    /**
     * 获取医院选项
     * @param dto
     * @return
     */
    HosOptionPO getHosOption(HosOptionDTO dto);

    /**
     *
     * @param hospitalId
     * @param unDepartName
     * @return
     */
    List<CheckinInfoPO> listCheckinInfoOfV(String hospitalId, String unDepartName);
    
    /**
     * 加载医生下的出/入院列表
     * @param refreshTime
     * @param doctorId
     * @param inStatus
     * @return
     */
    List<InHospitalLogPO> listInHospitalByDoctorId(@Param("refreshTime")String refreshTime,@Param("doctorId")String doctorId,@Param("inStatus")Integer inStatus);

    /**
     * 获取医院信息
     * @param hospitalId
     * @return
     */
    HospitalPO getHospitalByHospitalId(String hospitalId);

    List<String> getHospitalIdsByAreaId(@Param("areaId") String areaId);

}
