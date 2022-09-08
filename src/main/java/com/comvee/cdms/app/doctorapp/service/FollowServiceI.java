package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.model.FollowListModel;

public interface FollowServiceI {

	public PageResult<FollowListModel> selectFollowUpList(String memberId,String type,String isDeal, PageRequest page , String doctorId);
}
