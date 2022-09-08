package com.comvee.cdms.checkresult.mapper;

import com.comvee.cdms.checkresult.po.MemberCheckResultPO;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
public interface MemberCheckResultMapper {

	/**
	 * 获取分页总记录数
	 * @param map
	 * @return
	 * 李左河
	 */
	Long listMemberCheckResultByPageCount(Map<String, Object> map);

	/**
	 * 获取分页数据
	 * @param map
	 * @return
	 * 李左河
	 */
	List<MemberCheckResultPO> listMemberCheckResultByPageList(Map<String, Object> map);
    
}