package com.comvee.cdms.checkresult.service.impl;

import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.po.CheckoutPO;
import com.comvee.cdms.checkresult.po.InspectionPO;
import com.comvee.cdms.checkresult.service.CheckoutServiceI;
import com.comvee.cdms.checkresult.service.InspectionDetailServiceI;
import com.comvee.cdms.checkresult.service.InspectionServiceI;
import com.comvee.cdms.checkresult.vo.CheckoutAbnormalVO;
import com.comvee.cdms.checkresult.vo.InspectionDetailVO;
import com.comvee.cdms.checkresult.vo.MemberInspectionVO;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.service.ScreeningService;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
@Service("inspectionDetailService")
public class InspectionDetailServiceImpl implements InspectionDetailServiceI {

	@Autowired
	@Qualifier("memberService")
	private MemberService memberService;
	
	@Autowired
	@Qualifier("inspectionService")
	private InspectionServiceI inspectionService;
	
	@Autowired
	@Qualifier("checkoutService")
	private CheckoutServiceI checkoutService;

	@Autowired
	private ScreeningService screeningService;

	@Override
	public Map<String, Object> getInspectionDetailAll(String inspectId, String memberId,String hospitalId) {
		
		Map<String, Object> map = new HashMap<String, Object>(3);
		//1、通过memberid，获取患者信息
		MemberPO memberModel = this.memberService.getMemberById(memberId);
		map.put("member", memberModel);
		
		//2、获取smp_inspection信息
		InspectionPO inspection = this.inspectionService.getInspectionByInspectId(inspectId,hospitalId);
		map.put("inspection", inspection);

		return map;
	}

	@Override
	public List<CheckoutAbnormalVO> listCheckoutAbnormalListByMemberId(String memberId) {
		List<CheckoutAbnormalVO> checkoutAbnormalList = new ArrayList<CheckoutAbnormalVO>();
		//1、根据患者id，获取近一年的最新检验列表 
		List<CheckoutPO> checkoutList = this.checkoutService.listCheckoutForNearlyYear(memberId);
		if(checkoutList != null && checkoutList.size()>0) {
			//2、封装检验id，获取检验详情列表
			List<CheckoutDetailPO> checkoutDetailList = new ArrayList<>();
			for (CheckoutPO checkoutPO : checkoutList) {
				List<String> checkoutIds = new ArrayList<String>();
				checkoutIds.add(checkoutPO.getCheckoutId());
				List<CheckoutDetailPO> templist = this.checkoutService.listCheckoutDetailByParams(checkoutIds,memberId,checkoutPO.getHospitalId());
				if(templist!=null && templist.size()>0){
					checkoutDetailList.addAll(templist);
				}
			}
			if(checkoutDetailList != null && checkoutDetailList.size()>0) {
				//3、将检验和检验详情封装到一点model中
				for (CheckoutPO checkoutPO : checkoutList) {
					List<CheckoutDetailPO> returnCheckoutDetailList = new ArrayList<CheckoutDetailPO>();
					for (CheckoutDetailPO checkoutDetailPO : checkoutDetailList) {
						//检验主表和检验详情表的  检验id一致
						if(checkoutDetailPO.getCheckoutId().equals(checkoutPO.getCheckoutId())) {
							//异常：偏低、偏高、阳性、弱阳性  (+,↑,↓,L,H,P,Q）
							if(!StringUtils.isBlank(checkoutDetailPO.getAbnormalSign())){
								if("+,↑,↓,L,H,P,Q".contains(checkoutDetailPO.getAbnormalSign().trim())) {
									returnCheckoutDetailList.add(checkoutDetailPO);
								}
							}
						}
					}
					//存在异常项，封装返回
					if(returnCheckoutDetailList != null && returnCheckoutDetailList.size()>0) {
						CheckoutAbnormalVO checkoutAbnormal = new CheckoutAbnormalVO();
						checkoutAbnormal.setCheckout(checkoutPO);
						checkoutAbnormal.setCheckoutDetailList(returnCheckoutDetailList);
						checkoutAbnormalList.add(checkoutAbnormal);
					}
				}
			}
			
		}
		//3、返回
		return checkoutAbnormalList;
	}


	@Override
	public PageResult<InspectionPO> listInspectionPageByMemberId(String memberId, PageRequest pr) {
		PageHelper.startPage(pr.getPage(), pr.getRows());
		List<InspectionPO> inspectionList = this.inspectionService.listInspection(memberId);
		PageResult<InspectionPO> inspectionPage = new PageResult<InspectionPO>(inspectionList);
		return inspectionPage;
	}

	@Override
	public PageResult<CheckoutPO> listCheckoutPageByMemberId(String memberId, PageRequest pr) {
		PageHelper.startPage(pr.getPage(), pr.getRows());
		List<CheckoutPO> checkoutList = this.checkoutService.listCheckout(memberId);
		PageResult<CheckoutPO> checkoutPage = new PageResult<CheckoutPO>(checkoutList);
		return checkoutPage;
	}

	@Override
	public List<MemberInspectionVO> listInspectionReportNearlySixMonths(String memberId, String hospitalId,String startDt) {
		String startDate = DateHelper.changeTimeFormat(startDt, DateHelper.DATETIME_FORMAT, DateHelper.DAY_FORMAT);
		List<MemberInspectionVO> inspectionVOList = new ArrayList<>();
		//普检-医院
		List<InspectionPO> inspectionPOS = this.inspectionService.listInspectionByHospital(memberId,hospitalId);
		for(InspectionPO po : inspectionPOS){
			//TODO
/*			List<InspectionDetailPO> inspectionDetailPOS = this.inspectionDetailMapper.listInspectionNearlySixMonths(po.getInspectId(),memberId,hospitalId,startDate);
			if(inspectionDetailPOS!=null && inspectionDetailPOS.size()>0){
				MemberInspectionVO vo = new MemberInspectionVO();
				vo.setType(1);
				vo.setName(po.getInspectTitle());
				vo.setInfo(inspectionDetailPOS);
				inspectionVOList.add(vo);
			}*/
		}

		//并发症-comvee
		List<Map<String,Object>> bfzMap = this.screeningService.listReportNearlySixMonths(memberId,null);
		for(Map<String,Object> map:bfzMap){
			MemberInspectionVO vo = new MemberInspectionVO();
			vo.setType(2);
			vo.setName(map.get("name").toString());
			vo.setInfo(map);
			inspectionVOList.add(vo);
		}
		return inspectionVOList;
	}
}
