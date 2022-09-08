package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.mapper.FollowMapper;
import com.comvee.cdms.follow.model.FollowListModel;
import com.comvee.cdms.follow.po.MainFollowPO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("followAppService")
public class FollowServiceImpl implements FollowServiceI{

	private final static Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Autowired
	private FollowMapper followMapper;
	
	@Override
	public PageResult<FollowListModel> selectFollowUpList(String memberId,String type,String isDeal, PageRequest page , String doctorId) {
		PageHelper.startPage(page.getPage(), page.getRows());
		Boolean deal = null;
		if(!StringUtils.isBlank(isDeal)){
			if("1".equals(isDeal)){
				deal = true;
			}else{
				deal = false;
			}
		}
		List<FollowListModel> list = this.followMapper.listFollow(memberId, null, null, null, deal);
		PageResult<FollowListModel> pageResult = new PageResult(list);   
		for (FollowListModel followListModel : list) {
			int followType = followListModel.getFollowType();
			String url = "";
			
            /*if(followType==1){
            	url = "/follow/mobile/follow_first.html?followId="+followListModel.getSid()+"&type="+followType+"&memberId="+memberId+"&doctorId="+doctorId;
            } else {
                url = "/follow/mobile/follow_day.html?followId="+followListModel.getSid()+"&type="+followType+"&memberId="+memberId+"&doctorId="+doctorId;
            }*/

			Integer dStatus=1;
			MainFollowPO mainFollowPO = followMapper.getMainFollowByFidAndType(followListModel.getSid(), followType);
			if(null!=mainFollowPO && 1==mainFollowPO.getStatus()){
				dStatus = 2;
			}
			url = "/follow/page_transfer.html?followId="+followListModel.getSid()+"&memberId="+memberId
					+"&doctorId="+doctorId + "&followType=" + followType + "&textType=" + dStatus + "&clientType=2";

			followListModel.setUrl(url);
		}
		return pageResult;
	}
}
