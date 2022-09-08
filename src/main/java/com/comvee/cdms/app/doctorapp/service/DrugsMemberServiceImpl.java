package com.comvee.cdms.app.doctorapp.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.drugs.dto.ListDrugsDepotDTO;
import com.comvee.cdms.drugs.mapper.DrugsMemberMapper;
import com.comvee.cdms.drugs.po.DrugsDepotPO;
import com.comvee.cdms.drugs.po.DrugsMemberPO;
import com.comvee.cdms.drugs.vo.DrugSchemeVO;
import com.comvee.cdms.drugs.vo.DrugsMemberVO;
import com.comvee.cdms.member.bo.MemberDrugItemBO;
import com.comvee.cdms.member.dto.GetMemberDrugItemDTO;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.MemberDrugItemPO;
import com.comvee.cdms.member.po.MemberDrugRecordPO;
import com.comvee.cdms.member.service.MemberService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("drugsMemberAppService")
public class DrugsMemberServiceImpl implements DrugsMemberServiceI{

	private final static Logger logger = LoggerFactory.getLogger(DrugsMemberServiceImpl.class);

	@Autowired
	private DrugsMemberMapper drugsMemberMapper;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private MemberService memberService;


	@Override
	public PageResult<DrugsDepotPO> listDrugsDepotPageByHosForDefault(String hosId,String memberId,String type, String drugName, PageRequest page) {
		ListDrugsDepotDTO listDrugsDepotDTO=new ListDrugsDepotDTO();
		if("5".equals(type)){
			listDrugsDepotDTO.setMemberId(memberId);
		}
		if(!StringUtils.isBlank(drugName)){
			listDrugsDepotDTO.setDrugName(drugName);
		}
		listDrugsDepotDTO.setType(type);
		listDrugsDepotDTO.setBelongId(hosId);
		listDrugsDepotDTO.setBelongType(4);

		PageHelper.startPage(page.getPage(),page.getRows());
		List<DrugsDepotPO> list= this.drugsMemberMapper.listDrugsDepot(listDrugsDepotDTO);
		PageResult<DrugsDepotPO> tempPageResult = new PageResult<DrugsDepotPO>(list);
		if(list!=null&&list.size()>0){
			return tempPageResult;
		} else {
			//默认
			listDrugsDepotDTO.setBelongId("-1");
			listDrugsDepotDTO.setBelongType(0);
			PageHelper.startPage(page.getPage(),page.getRows());
			list= this.drugsMemberMapper.listDrugsDepot(listDrugsDepotDTO);
			tempPageResult = new PageResult<DrugsDepotPO>(list);
			return tempPageResult;
		}

	}

	@Override
	public DrugsDepotPO addDrugsDepotOfDefault(String drugName,String unit,String doctorId) {
		DrugsDepotPO drugsDepotPO=new DrugsDepotPO();
		drugsDepotPO.setId(DaoHelper.getSeq());
		drugsDepotPO.setDrugName(drugName);
		drugsDepotPO.setUnit(unit);
		drugsDepotPO.setMemberId(doctorId);
		drugsDepotPO.setPinyin(Pinyin4jUtil.getPinYin(drugsDepotPO.getDrugName()));
		drugsDepotPO.setBelongType(0);
		drugsDepotPO.setBelongId("-1");
		this.drugsMemberMapper.addDrugsDepot(drugsDepotPO);
		return drugsDepotPO;
	}

	@Override
	public PageResult<DrugsMemberVO> listDrugsMemberPage(String memberId, String doctorId,
														 String teamId,Integer dType,
														 PageRequest page,String drugType,
														 String origin) {
		GetMemberDrugItemDTO listDrugsMemberDTO=new GetMemberDrugItemDTO();
		listDrugsMemberDTO.setMemberId(memberId);
		listDrugsMemberDTO.setTeamId(teamId);
		listDrugsMemberDTO.setDoctorId(doctorId);
		listDrugsMemberDTO.setdType(dType);
		listDrugsMemberDTO.setOrigin(origin);
		if(!StringUtils.isBlank(drugType)){
			listDrugsMemberDTO.setHasDrugType("%\"drugType\":\""+drugType+"\"%");
		}
		PageHelper.startPage(page.getPage(),page.getRows());
		List<MemberDrugItemBO> iList= memberMapper.listDrugsMemberPage(listDrugsMemberDTO);
		PageResult<MemberDrugItemBO> pageResult = new PageResult<MemberDrugItemBO>(iList);
		List<DrugsMemberVO> listVO=new ArrayList<>();
		for(MemberDrugItemBO memberDrugItemBO : iList){
			String logId = memberDrugItemBO.getId();
			DrugsMemberPO smds = new DrugsMemberPO();
			BeanUtils.copyProperties(smds,memberDrugItemBO);
			smds.setSid(logId);
			smds.setSchemeName(memberDrugItemBO.getName());
			if(!StringUtils.isBlank(smds.getDrugJson()) && !"至用药方案".equals(memberDrugItemBO.getName())){
				DrugsMemberVO drugsMemberVO=new DrugsMemberVO();
				BeanUtils.copyProperties(drugsMemberVO,smds);
				List<DrugSchemeVO> drugSchemeVO = StringUtils.parseJSONStringToList(smds.getDrugJson(), DrugSchemeVO.class);
				drugsMemberVO.setDrugList(drugSchemeVO);
				listVO.add(drugsMemberVO);
			}
		}
		if(listVO.size()>0){
			PageResult<DrugsMemberVO> voPageResult = new PageResult<DrugsMemberVO>(listVO);
			voPageResult.setPageSize(page.getRows());
			voPageResult.setPageNum(page.getPage());
			voPageResult.setTotalRows(pageResult.getTotalRows());
			voPageResult.setTotalPages(pageResult.getTotalPages());
			return voPageResult;
		}
		return new PageResult<>(listVO);
	}


	@Override
	public MemberDrugItemPO getDrugsMemberNew(String memberId,String doctorId) {
		MemberDrugItemPO memberDrugItemPO = null;
		GetMemberDrugItemDTO getMemberDrugItemDTO=new GetMemberDrugItemDTO();
		getMemberDrugItemDTO.setMemberId(memberId);
		getMemberDrugItemDTO.setTeamId(doctorId);
		int drugType = this.memberService.getDrugType(doctorId);
		getMemberDrugItemDTO.setdType(drugType);
		List<MemberDrugRecordPO> list = memberMapper.getMemberDrugRecordList(getMemberDrugItemDTO);
		for(MemberDrugRecordPO recordPO : list){
			GetMemberDrugItemDTO getD=new GetMemberDrugItemDTO();
			getD.setRecordId(recordPO.getId());
			memberDrugItemPO = memberMapper.getMemberDrugItem(getD);
			if(memberDrugItemPO != null){
				if(!"至用药方案".equals(memberDrugItemPO.getName())){
					return memberDrugItemPO;
				}
			}
		}
		return memberDrugItemPO;
	}

	@Override
	public MemberDrugItemPO getMemberDrugItem(String id) {
		GetMemberDrugItemDTO getMemberDrugItemDTO=new GetMemberDrugItemDTO();
		getMemberDrugItemDTO.setId(id);
		return this.memberMapper.getMemberDrugItem(getMemberDrugItemDTO);
	}

	@Override
	public List<DrugsMemberVO> listDrugsNearlyTwoMonthsOfMember(String memberId, String startDt, String endDt,Integer dType,String drugType) {
		GetMemberDrugItemDTO listDrugsMemberDTO=new GetMemberDrugItemDTO();
		listDrugsMemberDTO.setMemberId(memberId);
		listDrugsMemberDTO.setStartDt(startDt);
		listDrugsMemberDTO.setEndDt(endDt);
		listDrugsMemberDTO.setdType(dType);
		List<MemberDrugItemBO> iList= memberMapper.listDrugsMemberPage(listDrugsMemberDTO);
		List<DrugsMemberVO> listVO=new ArrayList<>();
		for(MemberDrugItemBO memberDrugItemBO : iList){
			boolean isNotAddList = false;
			DrugsMemberPO smds = new DrugsMemberPO();
			BeanUtils.copyProperties(smds,memberDrugItemBO);
			smds.setSid(memberDrugItemBO.getId());
			smds.setSchemeName(memberDrugItemBO.getName());
			if(!StringUtils.isBlank(smds.getDrugJson()) && !"至用药方案".equals(memberDrugItemBO.getName())){
				DrugsMemberVO drugsMemberVO=new DrugsMemberVO();
				BeanUtils.copyProperties(drugsMemberVO,smds);
				List<DrugSchemeVO> drugSchemeVO = StringUtils.parseJSONStringToList(smds.getDrugJson(), DrugSchemeVO.class);
				List<DrugSchemeVO> drugSchemeVOS;
				if(!StringUtils.isBlank(drugType)){
					drugSchemeVOS = new ArrayList<>();
					for(DrugSchemeVO vo:drugSchemeVO){
						if(drugType.equals(vo.getDrugType())){
							drugSchemeVOS.add(vo);
							continue;
						}
					}
					if(drugSchemeVOS==null || drugSchemeVOS.size()<=0){
						isNotAddList = true;
					}
				} else {
					drugSchemeVOS = drugSchemeVO;
				}
				if(!isNotAddList){
					drugsMemberVO.setDrugList(drugSchemeVOS);
					drugsMemberVO.setDrugJson("");
					listVO.add(drugsMemberVO);
				}
			}
		}
		return listVO;
	}


	@Override
	public void addDrugsMember(String memberId, String doctorId, String drugType, String schemeName, String drugJson,
								 String startDt,String endDt,String teamId,String oldSchemeId) {

		if(StringUtils.isBlank(teamId)){
			teamId=doctorId;
		}
		List<DrugSchemeVO> memberDrugItemPOS = JsonSerializer.jsonToList(drugJson, DrugSchemeVO.class);

		GetMemberDrugItemDTO getMemberDrugItemDTO=new GetMemberDrugItemDTO();
		getMemberDrugItemDTO.setTeamId(teamId);
		getMemberDrugItemDTO.setMemberId(memberId);
		MemberDrugRecordPO memberDrugRecord = this.memberService.getMemberDrugRecord(getMemberDrugItemDTO);
		if(null != memberDrugRecord){
			this.memberService.delMemberDrugRecord(memberDrugRecord.getId());
			this.memberService.delMemberDrugItem(memberDrugRecord.getId());
		}
		MemberDrugRecordPO memberDrugRecordPO=new MemberDrugRecordPO();
		String  sid = DaoHelper.getSeq();
		memberDrugRecordPO.setId(sid);
		memberDrugRecordPO.setTeamId(teamId);
		memberDrugRecordPO.setDoctorId(doctorId);
		memberDrugRecordPO.setMemberId(memberId);
		memberDrugRecordPO.setdType(1);
		this.memberService.addMemberDrugRecord(memberDrugRecordPO);


		MemberDrugItemPO memberDrugItemPO = new MemberDrugItemPO();
		memberDrugItemPO.setRecordId(sid);
		memberDrugItemPO.setId(DaoHelper.getSeq());
		memberDrugItemPO.setDrugJson(drugJson);
		memberDrugItemPO.setStartDt(startDt);
		memberDrugItemPO.setEndDt(endDt);
		memberDrugItemPO.setOrigin("3");
		memberDrugItemPO.setName(schemeNameHandler(memberDrugItemPO));
		this.memberService.addMemberDrugItem(memberDrugItemPO);

		for (int i = 0; i < memberDrugItemPOS.size(); i++) {
			DrugSchemeVO drugSchemeVO = memberDrugItemPOS.get(i);
			ListDrugsDepotDTO listDrugsDepotDTO=new ListDrugsDepotDTO();
			listDrugsDepotDTO.setMemberId(teamId);
			listDrugsDepotDTO.setDrugName(drugSchemeVO.getDrugName());
			List<DrugsDepotPO> drugsDepotPOS = drugsMemberMapper.listDrugsDepot(listDrugsDepotDTO);
			if(null!=drugsDepotPOS && drugsDepotPOS.size()>0){
			}else{
				DrugsDepotPO drugsDepotPO=new DrugsDepotPO();
				String id =DaoHelper.getSeq();
				drugsDepotPO.setId(id);
				drugsDepotPO.setPinyin(Pinyin4jUtil.getPinYin(drugsDepotPO.getDrugName()));
				drugsDepotPO.setMemberId(teamId);
				drugsDepotPO.setDrugName(drugSchemeVO.getDrugName());
				drugsDepotPO.setUnit(drugSchemeVO.getUnit());
				drugsDepotPO.setType("5");
				drugsMemberMapper.addDrugsDepot(drugsDepotPO);
			}
		}
	}

	/**
	 * 用药方案过期处理
	 * @param oldSchemeId
	 */
	/*private void schemePastDueHandler(String oldSchemeId){
		if(StringUtils.isBlank(oldSchemeId)){
			return;
		}
		DrugsMemberPO dmdm = this.drugsMemberMapper.getDrugsMember(oldSchemeId);
		if(dmdm == null){
			return;
		}
		DrugsMemberPO updateModel = new DrugsMemberPO();
		updateModel.setSid(oldSchemeId);
		updateModel.setSchemeStatus("2");  //  2 已过期
		if(checkSchemeDateValid(dmdm.getStartDt(),dmdm.getEndDt())){
			updateModel.setStartDt(dmdm.getStartDt());
			updateModel.setEndDt(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
			updateModel.setSchemeName(schemeNameHandler(updateModel));
			updateModel.setDrugJson(drugListPastDueHandler(dmdm.getDrugJson()));
		}
		this.drugsMemberMapper.updateDrugsMember(updateModel);
	}*/

	/**
	 * 用药方案名称处理
	 * @param scheme
	 */
	private String schemeNameHandler(MemberDrugItemPO scheme){
		StringBuilder s = new StringBuilder();
		s.append(schemeDateHandler(scheme.getStartDt())).append("至").append(schemeDateHandler(scheme.getEndDt()))
				.append("用药方案");
		return s.toString();
	}

	/**
	 * 用药方案时间处理
	 * @param date
	 * @return
	 */
	private static String schemeDateHandler(String date){
		String re="";
		if(!StringUtils.isBlank(date)){
			if(date.length() < 11){
				re= date;
			}else{
				re =date.substring(5,10);
			}
		}
		return re;
	}

	/**
	 * 判断用药方案周期是否是进行中
	 * @param scheme
	 * @return
	 */
	private boolean checkSchemeDateValid(String startDt,String endDt){
		if(StringUtils.isBlank(startDt)
				|| StringUtils.isBlank(endDt)){
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = sdf.format(new Date());
		if(nowDate.compareTo(startDt) >= 0
				&& endDt.compareTo(nowDate) >= 0){
			return true;
		}
		return false;
	}

	/**
	 * 用药方案详情过期处理
	 * @param drugListJson
	 * @return
	 */
	private String drugListPastDueHandler(String drugListJson){
		if(StringUtils.isBlank(drugListJson)){
			return "";
		}
		List<DrugSchemeVO> list = StringUtils.parseJSONStringToList(drugListJson, DrugSchemeVO.class);
		if(list == null ||list.size() == 0){
			return "";
		}
		for(DrugSchemeVO d : list){
			if(d.getCycle() != null && d.getCycle().intValue() == 0){
				if(checkSchemeDateValid(d.getStartDt(), d.getEndDt())){
					d.setEndDt(DateHelper.getDate(new Date(), "yyyy-MM-dd"));
				}
			}
		}
		return JSONObject.toJSONString(list);
	}

}
