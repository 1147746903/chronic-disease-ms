
package com.comvee.cdms.defender.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.comvee.cdms.defender.model.DictModel;

public interface DictMapper {

	List<DictModel> listDictByPcode(@Param("pcode")String pcode);
	
}
