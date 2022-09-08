package com.comvee.cdms.defender.mapper;

import org.apache.ibatis.annotations.Param;

import com.comvee.cdms.defender.model.PatientLabelModel;
import com.comvee.cdms.defender.model.PatientModel;

/**
 * @File name:   PatientMapper.java   TODO
 * @Create on:   2018年8月2日
 * @Author   :  zqx
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
public interface PatientMapper {

//	/**
//	 * 根据SIM记录的微信唯一id获取患者信息
//	 * @TODO
//	 * @param simuid
//	 * @return
//	 * @author zqx
//	 * @date 2018年8月2日
//	 */
//	PatientModel loadPatientBySimuid(@Param("simuid") String simuid);
//	
//
//	/**
//	 * 根据SIM记录的微信唯一id获取患者信息
//	 * @TODO
//	 * @param simuid
//	 * @return
//	 * @author zqx
//	 * @date 2018年8月2日
//	 */
//	PatientModel loadPatientByImei(@Param("imei") String imei);

	/**
	 * 根据pid获取患者信息
	 * @param pid
	 * @return
	 */
	PatientModel loadPatientByPid(@Param("pid") String pid);
	
	/**
	 * @TODO  添加用户信息记录
	 * @param PatientModel 用户信息　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-12-20 15:42:12
	 */
	void addPatient(PatientModel patientModel) ;
	
	/**
	 * @TODO  修改用户信息记录
	 * @param PatientModel 用户信息　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-12-20 15:42:12
	 */
	void modifyPatient(PatientModel patientModel) ;

	/**
	 * @TODO  删除用户信息记录
	 * @param pid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-12-20 15:42:12
	 */
	void delPatient(@Param("pid") String  pid) ;
	
	/**
	 * 用户标签
	 * @param pid
	 * @return
	 */
	PatientLabelModel loadPatientLabel(@Param("pid") String pid);
	

}
