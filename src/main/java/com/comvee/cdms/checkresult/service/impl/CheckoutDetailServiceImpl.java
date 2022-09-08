package com.comvee.cdms.checkresult.service.impl;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.checkresult.constant.CheckConstant;
import com.comvee.cdms.checkresult.constant.CheckoutConstant;
import com.comvee.cdms.checkresult.constant.CheckoutDict;
import com.comvee.cdms.checkresult.dto.*;
import com.comvee.cdms.checkresult.mapper.CheckoutDetailMapper;
import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.po.CheckoutPO;
import com.comvee.cdms.checkresult.service.CheckDictCacheI;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.checkresult.service.CheckoutServiceI;
import com.comvee.cdms.checkresult.tool.CheckoutSyncArchiveTool;
import com.comvee.cdms.checkresult.vo.CheckoutDetailVO;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.constant.ScreeningConstant;
import com.comvee.cdms.complication.model.po.ScreeningReportPO;
import com.comvee.cdms.complication.service.ScreeningService;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.dto.DoctorKeyNoteDTO;
import com.comvee.cdms.doctor.dto.ListHospitalAllKeyNoteDTO;
import com.comvee.cdms.doctor.dto.ListSelectedKeyNoteDTO;
import com.comvee.cdms.doctor.model.KeyNoteModel;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.bo.MemberControlTargetBO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.dto.MemberArchivesDTO;
import com.comvee.cdms.member.mapper.MemberCheckinInfoMapper;
import com.comvee.cdms.member.po.MemberArchivesPO;
import com.comvee.cdms.member.po.MemberCheckinInfoPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.tool.MemberArchivesSyncHelper;
import com.comvee.cdms.member.tool.MemberRangeHelper;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddBloodPressureServiceDTO;
import com.comvee.cdms.sign.dto.AddBloodSugarServiceDTO;
import com.comvee.cdms.sign.dto.AddBmiServiceDTO;
import com.comvee.cdms.sign.dto.AddHba1cDTO;
import com.comvee.cdms.sign.po.WhrPO;
import com.comvee.cdms.sign.service.*;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.user.tool.SessionTool;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * 
 * @author 李左河
 *
 */
@Service("checkoutDetailService")
public class CheckoutDetailServiceImpl implements CheckoutDetailServiceI {
	private static Map<Object,Object> staticMapCache = new HashMap<>();

	@Autowired
	private CheckoutDetailMapper checkoutDetailMapper;
	
	@Autowired
	@Qualifier("memberService")
	@Lazy
	private MemberService memberService;
	
	@Autowired
	@Qualifier("checkoutService")
	@Lazy
	private CheckoutServiceI checkoutService;

	@Autowired
	private ScreeningService screeningService;

	@Autowired
	private CheckDictCacheI checkDictCache;

	@Autowired
	private DoctorServiceI doctorService;

	@Autowired
	private Hba1cService hba1cService;

	@Autowired
	private BmiService bmiService;

	@Autowired
	private WhrService whrService;

	@Autowired
	private BloodPressureService bloodPressureService;

	@Autowired
	private BloodSugarService bloodSugarService;

	@Autowired
	private MemberCheckinInfoMapper memberCheckinInfoMapper;

	@Override
	public Map<String, Object> getCheckoutDetailAll(String checkoutId, String memberId,String reportDate,String hospitalId) {
		
		Map<String, Object> map = new HashMap<String, Object>(3);
		//1、获取患者信息
		MemberPO memberModel = this.memberService.getMemberById(memberId);
		map.put("member", memberModel);
		
		//2、获取Checkout
		CheckoutPO checkout = this.checkoutService.getCheckoutByCheckoutId(checkoutId,hospitalId);
		map.put("checkout",checkout);
		
		//3、获取CheckoutDetail
		List<CheckoutDetailPO> checkoutDetailList = this.checkoutDetailMapper.listCheckoutDetail(checkoutId,hospitalId);
		if (null != checkoutDetailList && checkoutDetailList.size() > 0) {
			for (CheckoutDetailPO checkoutDetailPO: checkoutDetailList) {
				if (ValidateTool.isNum(checkoutDetailPO.getCheckoutResult())) {
					String referenceRange = checkoutDetailPO.getReferenceRange();
					if(!StringUtils.isBlank(referenceRange)){
						String[] split = referenceRange.split("--");
						if (null != split && split.length == 2) {
							Double value = Double.valueOf(checkoutDetailPO.getCheckoutResult());
							Double lowValue = Double.valueOf(split[0]);
							Double highValue = Double.valueOf(split[1]);
							if (value >= lowValue && value <= highValue) {
								checkoutDetailPO.setCheckoutResultLast("--");
							}
						}
					}
				}
			}
		}
		map.put("checkoutDetailList", checkoutDetailList);
		
		return map;
	}

	@Override
	public List<CheckoutDetailVO> listCheckoutDetailByCheckoutId(String checkoutId,String hospitalId) {
		List<CheckoutDetailPO> checkoutDetailList = this.checkoutDetailMapper.listCheckoutDetail(checkoutId,hospitalId);
		List<CheckoutDetailVO> vos = new ArrayList<>();
		checkoutDetailList.forEach(item->{
			CheckoutDetailVO vo = new CheckoutDetailVO();
			BeanUtils.copyProperties(vo,item);
			vos.add(vo);
		});
		return vos;
	}

	@Override
	public List<CheckoutDetailPO> listFatByCodeOfStatistics(GetStatisticsDTO dto) {
		return this.checkoutDetailMapper.listFatByCodeOfStatistics(dto);
	}

	@Override
	public void deleteCheckoutDetailByCheckoutId(String checkoutId,String hospitalId) {
		this.checkoutDetailMapper.deleteCheckoutDetailByCheckoutId(checkoutId,hospitalId);
	}

	@Override
	public List<CheckoutDetailPO> listCheckoutDetailByMemberIdOfInHos(String memberId) {
		return this.checkoutDetailMapper.listCheckoutDetailByMemberIdOfInHos(memberId);
	}


	/*************************************************邪恶的分割线******************************************************
	 * @version v6.0.0
	 * @author: linyd
	 * @date: 2020/03/03
	 *****************************************************************************************************************/
	@Override
	public List<CheckoutDetailVO> listMemberHistoryCheckResult(ListCheckResultDTO listCheckResultDTO) {
		listCheckResultDTO.setHospitalId(null);
		List<CheckoutDetailPO> checkoutDetailList = this.checkoutDetailMapper.listMemberHistoryCheckResult(listCheckResultDTO);
		List<CheckoutDetailVO> vos = new ArrayList<>();
		checkoutDetailList.forEach(item->{
			CheckoutDetailVO vo = new CheckoutDetailVO();
			BeanUtils.copyProperties(vo,item);
			vos.add(vo);
		});
		return vos;
	}

	@Override
	public List<CheckoutDetailVO> listCheckoutDetailNearlySixMonths(ListCheckoutDetailNearlySixMonthsDTO dto) {
		String memberId = dto.getMemberId();
		Integer type = dto.getType();
		String hospitalId = dto.getHospitalId();
		String doctorId = dto.getDoctorId();
		Integer inHos = dto.getInHos();
		List<CheckoutDetailVO> returnList = new ArrayList<CheckoutDetailVO>();
		MemberPO member = this.memberService.getMemberById(memberId);
		//医院-获取患者近6个月的最新检验列表
		List<CheckoutDetailPO> checkoutDetailList = this.listCheckoutDetail(memberId,hospitalId);
		//康为-获取患者近6个月并发症筛查获取ACR,糖化血红蛋白列表
		List<ScreeningReportPO> intelligentResultModels = this.screeningService.listScreeningReportOfTypeNearlySixMonth(memberId,null);
		//处理数据
		if(checkoutDetailList != null && checkoutDetailList.size()>0 || intelligentResultModels!=null && intelligentResultModels.size()>0) {
			//医院-特别关注项目代码列表
			List<String> importantCodesOfHos = this.checkDictCache.IMPORTANT_CODES(hospitalId);
			//医院-特殊处理检验项代码列表
			Map<String,String> TYPE_MAP = this.checkDictCache.TYPE_MAP(hospitalId);
			//医生特别关注项目列表
			ListSelectedKeyNoteDTO selectedKeyNoteDTO = new ListSelectedKeyNoteDTO();
			selectedKeyNoteDTO.setDoctorId(doctorId);
			selectedKeyNoteDTO.setHospitalId(hospitalId);
			selectedKeyNoteDTO.setInHos(inHos);
			List<KeyNoteModel> keyNoteModels = this.doctorService.listSelectedKeyNotes(selectedKeyNoteDTO);
			List<String> importantCodes = null;
			List<String> importantNames = null;
			//医生特别关注项目列表为空时处理
			if(keyNoteModels!=null&&keyNoteModels.size()>0){
				importantCodes = this.initImportantCodes(keyNoteModels);
				importantNames = this.initImportantNames(keyNoteModels);
			} else {
				importantCodes = importantCodesOfHos;
				importantNames = importantCodesOfHos;
			}
			//重点关注项目处理
			if(type!=null && CheckConstant.TYPE_IMPORTANT.equals(type)){
				List<CheckoutDetailVO> returnCheckoutDetailList = new ArrayList<CheckoutDetailVO>();
				//医院检验是否有ACR,糖化血红蛋白检验标志 false 没有，true 有
				boolean flagAcr = false,flagHbA1c = false;
				//ACR
				CheckoutDetailPO acrPO = null;
				//糖化血红蛋白
				CheckoutDetailPO hbA1cPO = null;
				//医院-检验详情封装到model中
				if(checkoutDetailList!=null && checkoutDetailList.size()>0){
					for (CheckoutDetailPO checkoutDetailPO : checkoutDetailList) {
						//代码和名称匹配
						if(importantCodes.contains(checkoutDetailPO.getCheckoutCode())||
								importantNames.contains(checkoutDetailPO.getCheckoutName())){
							if(checkoutDetailPO.getCheckoutCode().equals(TYPE_MAP.get("ACR"))){
								//特殊处理ACR
								flagAcr = true;
								acrPO = checkoutDetailPO;
							} else if(checkoutDetailPO.getCheckoutCode().equals(TYPE_MAP.get("GHB"))){
								//特殊处理GHB
								flagHbA1c = true;
								hbA1cPO = checkoutDetailPO;
							} else if(checkoutDetailPO.getCheckoutCode().equals(TYPE_MAP.get("CR"))){
								//特殊处理eGFR
								CheckoutDetailPO efgrPO = new CheckoutDetailPO();
								efgrPO.setCheckoutName("eGFR");
								efgrPO.setCheckoutCode("eGFR");
								efgrPO.setCheckoutDatetime(checkoutDetailPO.getCheckoutDatetime());
								String eGFR = egfrHandler(checkoutDetailPO.getCheckoutResult(),member.getSex()+"",member.getBirthday());
								efgrPO.setCheckoutResult(eGFR);
								CheckoutDetailVO efgrVO = new CheckoutDetailVO();
								com.comvee.cdms.common.utils.BeanUtils.copyProperties(efgrVO,efgrPO);
								returnCheckoutDetailList.add(efgrVO);
								if(!StringUtils.isBlank(eGFR)){
									//特殊处理CKD
									Double dEGFR = Double.parseDouble(eGFR);
									CheckoutDetailPO ckdPO = new CheckoutDetailPO();
									ckdPO.setCheckoutName("CKD");
									ckdPO.setCheckoutCode("CKD");
									ckdPO.setCheckoutDatetime(checkoutDetailPO.getCheckoutDatetime());
									if(dEGFR<30){
										ckdPO.setCheckoutResult("A1(正常~轻度增加)");
									} else if(dEGFR>300){
										ckdPO.setCheckoutResult("A3(显著增加)");
									} else {
										ckdPO.setCheckoutResult("A2(中度增加)");
									}
									CheckoutDetailVO ckdVO = new CheckoutDetailVO();
									com.comvee.cdms.common.utils.BeanUtils.copyProperties(ckdVO,ckdPO);
									returnCheckoutDetailList.add(ckdVO);
									//特殊处理G
									CheckoutDetailPO gPO = new CheckoutDetailPO();
									gPO.setCheckoutName("G");
									gPO.setCheckoutCode("G");
									gPO.setCheckoutDatetime(checkoutDetailPO.getCheckoutDatetime());
									if(dEGFR>=90){
										gPO.setCheckoutResult("G1(正常或高)");
									} else if(dEGFR>=60){
										gPO.setCheckoutResult("G2(轻度减退)");
									} else if(dEGFR>=45){
										gPO.setCheckoutResult("G3a(轻度～中度减退)");
									} else if(dEGFR>=30){
										gPO.setCheckoutResult("G3b(中度～重度减退)");
									} else if(dEGFR>=15){
										gPO.setCheckoutResult("G4(重度减退)");
									} else {
										gPO.setCheckoutResult("G5(肾衰竭)");
									}
									CheckoutDetailVO gVO = new CheckoutDetailVO();
									com.comvee.cdms.common.utils.BeanUtils.copyProperties(gVO,gPO);
									returnCheckoutDetailList.add(gVO);
								}
							} else {
								//检验项处理
								CheckoutDetailVO checkoutDetailVO = new CheckoutDetailVO();
								com.comvee.cdms.common.utils.BeanUtils.copyProperties(checkoutDetailVO,checkoutDetailPO);
								returnCheckoutDetailList.add(checkoutDetailVO);
							}
						}
					}
				}

				//康为-并发症检验详情封装到model中
				for(ScreeningReportPO model : intelligentResultModels){
					String reslutJson = model.getDataJson();
					if(!StringUtils.isBlank(reslutJson)){
						JSONObject jsonObject = JSONObject.parseObject(reslutJson);
						if(ScreeningConstant.SCREENING_TYPE_ACR == model.getScreeningType()){
							//ACR处理
							String acr = jsonObject.getString("ACR");
							if(!flagAcr && acrPO==null){
								//医院检验不存在ACR-直接实例化
								acrPO = new CheckoutDetailPO();
								acrPO.setCheckoutName("ACR");
								acrPO.setCheckoutCode(TYPE_MAP.get("ACR"));
								acrPO.setCheckoutDatetime(model.getInsertDt());
								acrPO.setCheckoutResult(acr);
							} else {
								//医院检验存在ACR-判断前后顺序-实例化
								boolean huan = false;
								Date d1 = DateHelper.getDate(acrPO.getCheckoutDatetime(),DateHelper.DATETIME_FORMAT);
								String d2Str = model.getInsertDt();
								if(d1!=null){
									if(!StringUtils.isBlank(d2Str)){
										Date d2 = DateHelper.getDate(d2Str,DateHelper.DATETIME_FORMAT);
										if(d2!=null){
											if(d2.getTime()>d1.getTime()){
												huan = true;
											}
										}
									}
								} else {
									huan = true;
								}
								if(huan){
									acrPO = new CheckoutDetailPO();
									acrPO.setCheckoutName("ACR");
									acrPO.setCheckoutCode(TYPE_MAP.get("ACR"));
									acrPO.setCheckoutDatetime(model.getInsertDt());
									acrPO.setCheckoutResult(acr);
								}
							}
						} else if(ScreeningConstant.SCREENING_TYPE_HBA1C == model.getScreeningType()){
							//糖化血红蛋白处理
							String HbA1c = jsonObject.getString("HbA1c");
							if(!flagHbA1c && hbA1cPO==null){
								//医院检验不存在糖化血红蛋白-直接实例化
								hbA1cPO = new CheckoutDetailPO();
								hbA1cPO.setCheckoutName("糖化血红蛋白");
								hbA1cPO.setCheckoutResult(HbA1c);
								hbA1cPO.setCheckoutCode(TYPE_MAP.get("GHB"));
								hbA1cPO.setCheckoutDatetime(model.getInsertDt());
								hbA1cPO.setResultUnit("%");
							} else {
								//医院检验存在糖化血红蛋白-判断前后顺序-实例化
								boolean huan = false;
								Date d1 = DateHelper.getDate(hbA1cPO.getCheckoutDatetime(),DateHelper.DATETIME_FORMAT);
								String d2Str = model.getInsertDt();
								if(d1!=null){
									if(!StringUtils.isBlank(d2Str)){
										Date d2 = DateHelper.getDate(d2Str,DateHelper.DATETIME_FORMAT);
										if(d2!=null){
											if(d2.getTime()>d1.getTime()){
												huan = true;
											}
										}
									}
								} else {
									huan = true;
								}
								if(huan){
									hbA1cPO = new CheckoutDetailPO();
									hbA1cPO.setCheckoutName("糖化血红蛋白");
									hbA1cPO.setCheckoutResult(HbA1c);
									hbA1cPO.setCheckoutCode(TYPE_MAP.get("GHB"));
									hbA1cPO.setCheckoutDatetime(model.getInsertDt());
									hbA1cPO.setResultUnit("%");
								}
							}
						}
					}
				}
				//有ACR实例化-封装model
				if(acrPO!=null){
					CheckoutDetailVO checkoutDetailVO = new CheckoutDetailVO();
					com.comvee.cdms.common.utils.BeanUtils.copyProperties(checkoutDetailVO,acrPO);
					returnCheckoutDetailList.add(checkoutDetailVO);
				}
				//有糖化血红蛋白实例化-封装model
				if(hbA1cPO!=null){
					CheckoutDetailVO checkoutDetailVO = new CheckoutDetailVO();
					com.comvee.cdms.common.utils.BeanUtils.copyProperties(checkoutDetailVO,hbA1cPO);
					returnCheckoutDetailList.add(checkoutDetailVO);
				}
				//存在重要项，封装返回
				if(returnCheckoutDetailList != null && returnCheckoutDetailList.size()>0) {
					returnList.addAll(returnCheckoutDetailList);
				}
			}
			//异常检验项目处理
			else if(CheckConstant.TYPE_ABNORMAL.equals(type)) {
				//将检验详情封装到model中
				List<CheckoutDetailVO> returnCheckoutDetailList = new ArrayList<CheckoutDetailVO>();
				if(checkoutDetailList!=null){
					for (CheckoutDetailPO checkoutDetailPO : checkoutDetailList) {
						//异常：偏低、偏高、阳性、弱阳性  (+,↑,↓,L,H,P,Q）
						if(!StringUtils.isBlank(checkoutDetailPO.getAbnormalSign())
								&&!importantCodes.contains(checkoutDetailPO.getCheckoutCode())
								&&!importantNames.contains(checkoutDetailPO.getCheckoutName())){
							boolean detailFlag = false;
							for(CheckoutDetailVO vo : returnCheckoutDetailList){
								if(vo.getCheckoutName().equals(checkoutDetailPO.getCheckoutName())){
									detailFlag = true;
									break;
								}
							}
							if(!detailFlag){
								if("+,↑,↓,L,H,P,Q,偏低、偏高、阳性、弱阳性".contains(checkoutDetailPO.getAbnormalSign().trim())) {
									CheckoutDetailVO vo = new CheckoutDetailVO();
									com.comvee.cdms.common.utils.BeanUtils.copyProperties(vo,checkoutDetailPO);
									returnCheckoutDetailList.add(vo);
								}
							}
						}
					}
				}
				//存在异常项，封装返回
				if(returnCheckoutDetailList != null && returnCheckoutDetailList.size()>0) {
					returnList.addAll(returnCheckoutDetailList);
				}
			}
		}
		//返回
		return returnList;
	}

	@Override
	public List<CheckoutDetailVO> getCheckoutDetailChartList(List<CheckoutDetailVO> checkoutDetailVOS) {
		List<CheckoutDetailVO> result = new ArrayList<>();
		if(checkoutDetailVOS!=null&&checkoutDetailVOS.size()>0){
			LinkedMap<String,CheckoutDetailVO> map = new LinkedMap<>();
			for(int i=checkoutDetailVOS.size()-1;i>-1;i--){
				CheckoutDetailVO vo = checkoutDetailVOS.get(i);
				String dt = vo.getCheckoutDatetime();
				String date = DateHelper.getDate(DateHelper.getDate(dt,DateHelper.DAY_FORMAT),DateHelper.DAY_FORMAT);
				map.put(date,vo);
			}
			for(String key : map.keySet()){
				result.add(map.get(key));
			}
		}
		return result;
	}

	@Override
	@Cacheable(value = "checkResources",key = "#dto.cacheKey" ,unless="#result == null")
	public PageResult<CheckoutDetailPO> pagerCheckoutDetailGroupByNameOfDoctor(ListSelectedKeyNoteDTO dto,PageRequest pager,String startDt,String endDt) {
		String memberId = dto.getMemberId();
		String hospitalId = dto.getHospitalId();
		dto.setMemberId(null);
		List<KeyNoteModel> list = this.doctorService.listSelectedKeyNotes(dto);
		KeyNoteModel keyNoteModel = null;
		if(list!=null&&list.size()>0){
			keyNoteModel = list.get(0);
		}
		PageHelper.startPage(pager.getPage(),pager.getRows());
		List<CheckoutDetailPO> pos = this.checkoutDetailMapper.listCheckoutDetailGroupByNameByCodeOrNames(memberId,
				list,startDt,endDt,null,keyNoteModel);
		PageResult<CheckoutDetailPO> poPageResult = new PageResult<>(pos);

		DecimalFormat df = new DecimalFormat("###########0.00");
		RangeBO rangeBo = this.memberService.getMemberRange(memberId);
		MemberControlTargetBO controlTarget = this.memberService.getMemberDefaultControlTarget(memberId);
		for(CheckoutDetailPO po : poPageResult.getRows()){
			String checkoutResultLast = po.getCheckoutResultLast();
			if(checkoutResultLast==null){
				checkoutResultLast="";
			}
			if(StringUtils.isBlank(checkoutResultLast.trim())){
				//首次检查
				po.setCheckoutResultLastStr("首次检查");
			} else if("--".equals(checkoutResultLast.trim())){
				//补录检查
				po.setCheckoutResultLastStr("--");
			} else if(StringUtils.checkParam(checkoutResultLast.trim())){
				//是定量检验比较结果
				Double diff = Double.valueOf(checkoutResultLast.trim());
				if(diff > 0) {
					//升高了diff
					po.setCheckoutResultLastStr("升高了"+df.format(diff));
				}else if(diff < 0) {
					String str = (diff+"").replace("-","");
					//降低了diff
					po.setCheckoutResultLastStr("降低了"+df.format(Double.parseDouble(str)));
				} else {
					po.setCheckoutResultLastStr("无变化");
				}
			} else {
				//非定量检验比较结果
				po.setCheckoutResultLastStr(checkoutResultLast);
			}
			//......烦！！！！
			String rangeStr = MemberRangeHelper.getCheckoutDetailRangeStr(po.getCheckoutName(),list,rangeBo,controlTarget);
			po.setReferenceRange(rangeStr);
		}
		return poPageResult;
	}

	/**
	 * 使清除存注解生效--同一个缓存区域只能在同一个类里面才会生效
	 * @param dto
	 */
	@Override
	@CacheEvict(value = "checkResources", allEntries = true)
	public void saveDoctorKeyNotes(DoctorKeyNoteDTO dto) {
		this.doctorService.saveDoctorKeyNotes(dto);
	}
	/**
	 * 使清除存注解生效--同一个缓存区域只能在同一个类里面才会生效
	 * @param dto
	 */
	@Override
	@CacheEvict(value = "checkResources", allEntries = true)
	public void addCheckout(AddCheckoutDTO dto, DoctorPO doctorPO) {
		this.checkoutService.addCheckout(dto,doctorPO);
	}

	@Override
	@CacheEvict(value = "checkResources", allEntries = true)
	public void addCheckoutBatch(BatchAddCheckoutDTO batchDto, DoctorPO doctorPO) {
		String memberId = batchDto.getMemberId();
		String hospitalId = doctorPO.getHospitalId();
		String doctorId = doctorPO.getDoctorId();
		String listStr = batchDto.getListStr();
		List<BaseAddCheckoutDTO> checkoutDTOList = JsonSerializer.jsonToList(listStr, BaseAddCheckoutDTO.class);
		batchDto.setList(checkoutDTOList);
        Integer recordOrigin = batchDto.getRecordOrigin();
		Map<String ,CheckoutDetailPO> detailMap = new HashMap<>();
		//系统录入
        if (recordOrigin == CheckoutConstant.RECORD_ORIGIN_SYS){
            if (!checkoutDTOList.isEmpty()){
                for (BaseAddCheckoutDTO baseDto : checkoutDTOList) {
                    AddCheckoutDTO addCheckoutDTO = new AddCheckoutDTO();
                    BeanUtils.copyProperties(addCheckoutDTO,batchDto);
                    BeanUtils.copyProperties(addCheckoutDTO,baseDto);
                    this.checkoutService.addCheckout(addCheckoutDTO,doctorPO);
                }
            }
        }//随访、处方
        else if (recordOrigin == CheckoutConstant.RECORD_ORIGIN_FOLLOW || recordOrigin == CheckoutConstant.RECORD_ORIGIN_PRESCRIPTION){
            for (BaseAddCheckoutDTO baseDto : checkoutDTOList) {
				String recordDt = baseDto.getCheckoutDate() + " "+baseDto.getCheckoutTime();
				String detailJSON = baseDto.getDetailJSON();
				List<Map> mapList = JsonSerializer.jsonToList(detailJSON, Map.class);
				Integer dataType = baseDto.getDataType();
                if (dataType != null) {
                	//血糖
					if (dataType == CheckoutConstant.DATATYPE_BLOOD_SUGAR){
						checkoutBloodSugarHandler(mapList,memberId,doctorId,recordDt,hospitalId);
					}//血压
					else if (dataType == CheckoutConstant.DATATYPE_BLOOD_PRESSURE) {
						checkoutBloodPressureHandler(mapList,memberId,doctorId,recordDt);
					}//bmi
					else if (dataType == CheckoutConstant.DATATYPE_BMI) {
						checkoutBmiHandler(mapList,memberId,doctorId,recordDt);
					}//腰臀比
					else if (dataType == CheckoutConstant.DATATYPE_WHR) {
						checkoutWhrHandler(mapList,memberId,doctorId,recordDt);
					}//hba1c和其他
					else if (dataType == CheckoutConstant.DATATYPE_HBA1C || dataType == CheckoutConstant.DATATYPE_OTHER) {
                        AddCheckoutDTO addCheckoutDTO = new AddCheckoutDTO();
                        BeanUtils.copyProperties(addCheckoutDTO,batchDto);
                        BeanUtils.copyProperties(addCheckoutDTO,baseDto);
                        this.checkoutService.addCheckout(addCheckoutDTO,doctorPO);
					}
					//处理档案同步数据
					checkoutArchiveHandler(detailMap,mapList,memberId,doctorPO,recordDt);
				}
				//同步档案
				this.checkoutService.resolveSyncMemberArchive(detailMap,memberId);
            }
        }
	}

	//处理血糖
	private void checkoutBloodSugarHandler(List<Map> mapList,String memberId,String doctorId,String recordDt,String hospitalId){
		if (mapList.size() > 0) {
			for (Map map : mapList) {
				AddBloodSugarServiceDTO addBloodSugarServiceDTO = new AddBloodSugarServiceDTO();
				String code = String.valueOf(map.get("name"));
				String value = map.get("value").toString();
				addBloodSugarServiceDTO.setMemberId(memberId);
				addBloodSugarServiceDTO.setOperatorId(doctorId);
				addBloodSugarServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
				addBloodSugarServiceDTO.setOrigin(SignConstant.ORIGIN_FOLLOW);
				addBloodSugarServiceDTO.setRecordDt(recordDt);
				addBloodSugarServiceDTO.setParamCode(code);
				addBloodSugarServiceDTO.setParamValue(value);
				//是否住院
				MemberCheckinInfoPO memberCheckinInfoMapper = this.memberCheckinInfoMapper.getMemberCheckinInfoByMemberId(memberId, hospitalId);
				if (null != memberCheckinInfoMapper && memberCheckinInfoMapper.getCheckinStatus() == 1) {
					addBloodSugarServiceDTO.setInHos(1);
				} else {
					addBloodSugarServiceDTO.setInHos(0);
				}
				this.bloodSugarService.addBloodSugar(addBloodSugarServiceDTO);
			}
		}
	}

	//处理血压
	private void checkoutBloodPressureHandler(List<Map> mapList,String memberId,String doctorId,String recordDt){
		AddBloodPressureServiceDTO addBloodPressureServiceDTO = new AddBloodPressureServiceDTO();
		if (mapList.size() > 0) {
			for (Map map : mapList) {
				String name = String.valueOf(map.get("name"));
				String value = map.get("value").toString();
				if (name.trim().equals("收缩压")) {
					addBloodPressureServiceDTO.setSbp(value);
				} else if (name.trim().equals("舒张压")) {
					addBloodPressureServiceDTO.setDbp(value);
				} else if (name.trim().equals("静息心率")) {
					addBloodPressureServiceDTO.setHeartRate(value);
				}
			}
			addBloodPressureServiceDTO.setMemberId(memberId);
			addBloodPressureServiceDTO.setOperatorId(doctorId);
			addBloodPressureServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
			addBloodPressureServiceDTO.setOrigin(SignConstant.ORIGIN_FOLLOW);
			addBloodPressureServiceDTO.setRecordTime(recordDt);
			this.bloodPressureService.addBloodPressure(addBloodPressureServiceDTO);
		}
	}

	//处理bmi
	private void checkoutBmiHandler(List<Map> mapList,String memberId,String doctorId,String recordDt){
		AddBmiServiceDTO addBmiServiceDTO = new AddBmiServiceDTO();
		if (mapList.size()>0) {
			for (Map map : mapList) {
				String name = String.valueOf(map.get("name"));
				String value = map.get("value").toString();
				if (name.trim().equals("BMI")) {
					addBmiServiceDTO.setBmi(value);
				} else if (name.trim().equals("身高")) {
					addBmiServiceDTO.setHeight(Float.parseFloat(value));
				} else if (name.trim().equals("体重")) {
					addBmiServiceDTO.setWeight(Float.parseFloat(value));
				}
			}
			addBmiServiceDTO.setMemberId(memberId);
			addBmiServiceDTO.setRecordDt(recordDt);
			addBmiServiceDTO.setOperatorId(doctorId);
			addBmiServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
			addBmiServiceDTO.setOrigin(SignConstant.ORIGIN_FOLLOW);
			this.bmiService.addBmi(addBmiServiceDTO);
		}
	}

	//处理whr
	private void checkoutWhrHandler(List<Map> mapList,String memberId,String doctorId,String recordDt){
		WhrPO whrPO = new WhrPO();
		if (mapList.size() > 0) {
			for (Map map : mapList) {
				String name = String.valueOf(map.get("name"));
				String value = map.get("value").toString();
				if (name.trim().equals("腰围")) {
					whrPO.setWaist(value);
				} else if (name.trim().equals("臀围")) {
					whrPO.setHip(value);
				} else if (name.trim().equals("腰臀比")) {
					whrPO.setWhr(value);
				}
			}
			whrPO.setMemberId(memberId);
			whrPO.setRecordDt(recordDt);
			whrPO.setOperatorId(doctorId);
			whrPO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
			whrPO.setOrigin(SignConstant.ORIGIN_FOLLOW);
			this.whrService.addWhrMapper(whrPO);
		}
	}

	//处理whr
	private void checkoutArchiveHandler(Map<String ,CheckoutDetailPO> detailMap,List<Map> mapList,String memberId,DoctorPO doctorPO,String recordDt){
		for (Map map : mapList) {
			String checkoutName = String.valueOf(map.get("name"));
			String checkoutCode = String.valueOf(map.get("code"));
			String checkoutResult = String.valueOf(map.get("value"));
			String checkoutUnit = String.valueOf(map.get("unit"));
			RangeBO rangeBo = this.memberService.getMemberRange(memberId);
			MemberControlTargetBO bo = this.memberService.getMemberDefaultControlTarget(memberId);
			ListHospitalAllKeyNoteDTO listHospitalAllKeyNoteDTO = new ListHospitalAllKeyNoteDTO();
			listHospitalAllKeyNoteDTO.setHospitalId(doctorPO.getHospitalId());
			List<KeyNoteModel> keyNotesModels = this.doctorService.listHospitalAllKeyNotes(listHospitalAllKeyNoteDTO);
			String rangeStr = MemberRangeHelper.getCheckoutDetailRangeStr(checkoutName, keyNotesModels, rangeBo, bo);
			String abnormalSign = MemberRangeHelper.getCheckoutAbnormalSign(checkoutName, checkoutResult, rangeStr);

			CheckoutDetailPO checkoutDetailPO = new CheckoutDetailPO();
			checkoutDetailPO.setReferenceRange(rangeStr);
			checkoutDetailPO.setCheckoutCode(checkoutCode);
			checkoutDetailPO.setCheckoutName(checkoutName);
			checkoutDetailPO.setCheckoutResult(checkoutResult);
			checkoutDetailPO.setAbnormalSign(abnormalSign);
			checkoutDetailPO.setResultUnit(checkoutUnit);
			checkoutDetailPO.setCheckoutDatetime(recordDt);
			checkoutDetailPO.setMemberId(memberId);
			checkoutDetailPO.setHospitalId(doctorPO.getHospitalName());
			checkoutDetailPO.setHospitalName(doctorPO.getHospitalName());
			detailMap.put(checkoutCode,checkoutDetailPO);
		}
	}

	@Override
	public CheckoutDetailPO getNewestCheckoutDetail(GetNewestCheckoutDetailDTO getNewestCheckoutDetailDTO) {
		return this.checkoutDetailMapper.getNewestCheckoutDetail(getNewestCheckoutDetailDTO);
	}

	@Override
	public Map<String,Object> loadMemberDefaultCheckoutList(GetMemberCheckoutDTO getMemberCheckoutDTO) {
		String memberId = getMemberCheckoutDTO.getMemberId();
		String hospitalId = getMemberCheckoutDTO.getHospitalId();
		String startDt = getMemberCheckoutDTO.getStartDt();
		String endDt = getMemberCheckoutDTO.getEndDt();
		List<String> codeList = CheckConstant.DEFAULT_CHECKOUT_CODE_LIST;
		List<KeyNoteModel> keyNoteModels = new ArrayList<>();
		Map<String,Object> result = new HashMap<>();
		result.put("ogttDate","");
		result.put("ctDate","");
		result.put("hbaDate","");
		result.put("bloodDate","");//血脂
		result.put("kidneyDate","");//肾
		result.put("otherDate","");//
		for (String code : codeList) {
			result.put(code,"");
			KeyNoteModel keyNoteModel = new KeyNoteModel();
			keyNoteModel.setCheckoutCode(code);
			keyNoteModels.add(keyNoteModel);
		}
		//患者检验数据
		List<CheckoutDetailPO> checkoutDetailPOS = this.checkoutDetailMapper.listCheckoutDetailByNameCode(memberId,
                keyNoteModels,startDt,endDt,hospitalId);
		if (checkoutDetailPOS.size()>0){
			for (CheckoutDetailPO checkoutDetailPO : checkoutDetailPOS) {
				result.put(checkoutDetailPO.getCheckoutCode(),checkoutDetailPO.getCheckoutResult());
				//处理时间
				if (checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.OGTT_0M.getCode())||
						checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.OGTT_120M.getCode())){
					result.put("ogttDate",DateHelper.changeTimeFormat(checkoutDetailPO.getCheckoutDatetime(),DateHelper.DATETIME_FORMAT,DateHelper.DAY_FORMAT));
				}
				if (checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.CT_0M.getCode())||
						checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.CT_120M.getCode())){
					result.put("ctDate",DateHelper.changeTimeFormat(checkoutDetailPO.getCheckoutDatetime(),DateHelper.DATETIME_FORMAT,DateHelper.DAY_FORMAT));
				}
				if (checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.HBA.getCode())){
					result.put("hbaDate",DateHelper.changeTimeFormat(checkoutDetailPO.getCheckoutDatetime(),DateHelper.DATETIME_FORMAT,DateHelper.DAY_FORMAT));
				}
				if (checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.HDL.getCode())||
						checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.TC.getCode())||
						checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.TRIGLYCERIDE.getCode())||
						checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.LDL.getCode())){
					result.put("bloodDate",DateHelper.changeTimeFormat(checkoutDetailPO.getCheckoutDatetime(),DateHelper.DATETIME_FORMAT,DateHelper.DAY_FORMAT));
				}
				if (checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.EGFR.getCode())||
						checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.CKD_G.getCode())||
						checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.NEPH_ACR.getCode())||
						checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.CKD_A.getCode())){
					result.put("kidneyDate",DateHelper.changeTimeFormat(checkoutDetailPO.getCheckoutDatetime(),DateHelper.DATETIME_FORMAT,DateHelper.DAY_FORMAT));
				}
				if (checkoutDetailPO.getCheckoutCode().equals(CheckoutDict.GAST.getCode())){
					result.put("otherDate",DateHelper.changeTimeFormat(checkoutDetailPO.getCheckoutDatetime(),DateHelper.DATETIME_FORMAT,DateHelper.DAY_FORMAT));
				}
			}
		}

		return result;
	}


	@Override
	public PageResult<CheckoutDetailVO> pagerMemberHistoryCheckResult(ListCheckResultDTO listCheckResultDTO, PageRequest pager) {
		listCheckResultDTO.setHospitalId(null);
		//检验项目是匹配过的，则不根据名称查
		if(listCheckResultDTO.getCheckoutCode() != null
			&& !listCheckResultDTO.getCheckoutCode().equals(CheckoutDict.OTHER.getCode())){
			listCheckResultDTO.setCheckoutName(null);
		}
		PageHelper.startPage(pager.getPage(),pager.getRows());
		List<CheckoutDetailPO> checkoutDetailList = this.checkoutDetailMapper.listMemberHistoryCheckResult(listCheckResultDTO);
		PageResult<CheckoutDetailPO> pageResult1 = new PageResult<>(checkoutDetailList);
		if(pageResult1.getRows()!=null && pageResult1.getRows().size()>0){
			List<CheckoutDetailVO> vos = new ArrayList<>(pageResult1.getRows().size());
			pageResult1.getRows().forEach(item->{
				CheckoutDetailVO vo = new CheckoutDetailVO();
				BeanUtils.copyProperties(vo,item);
				DecimalFormat df = new DecimalFormat("###########0.00");
				String checkoutResultLast = vo.getCheckoutResultLast();
				if(StringUtils.isBlank(checkoutResultLast)){
					//首次检查
					vo.setCheckoutResultLastStr("首次检查");
				} else if("--".equals(checkoutResultLast)){
					//补录检查
					vo.setCheckoutResultLastStr("--");
				} else if(StringUtils.checkParam(checkoutResultLast)){
					//是定量检验比较结果
					Double diff = Double.valueOf(checkoutResultLast);
					if(diff > 0) {
						//升高了diff
						vo.setCheckoutResultLastStr("升高了"+df.format(diff));
					}else if(diff < 0) {
						String str = (diff+"").replace("-","");
						//降低了diff
						vo.setCheckoutResultLastStr("降低了"+df.format(Double.parseDouble(str)));
					} else {
						vo.setCheckoutResultLastStr("无变化");
					}
				} else {
					//非定量检验比较结果
					vo.setCheckoutResultLastStr(checkoutResultLast);
				}
				vos.add(vo);
			});
			PageResult<CheckoutDetailVO> pageResult = new PageResult<>(vos);
			pageResult.setTotalRows(pageResult1.getTotalRows());
			pageResult.setTotalPages(pageResult1.getTotalPages());
			pageResult.setPageNum(pager.getPage());
			pageResult.setPageSize(pager.getRows());
			return pageResult;
		}
		return new PageResult<>(null);
	}

	/**
	 * 医院-特别关注项目名称列表
	 * @param keyNoteModels
	 * @return
	 */
	private List<String> initImportantNames(List<KeyNoteModel> keyNoteModels) {
		List<String> returnList = new ArrayList<>(keyNoteModels.size());
		for(KeyNoteModel model : keyNoteModels){
			returnList.add(model.getDisplayName());
		}
		return returnList;
	}

	/**
	 * 医院-特别关注项目代码列表
	 * @param keyNoteModels
	 * @return
	 */
	private List<String> initImportantCodes(List<KeyNoteModel> keyNoteModels) {
		List<String> returnList = new ArrayList<>(keyNoteModels.size());
		for(KeyNoteModel model : keyNoteModels){
			returnList.add(model.getCheckoutCode());
		}
		return returnList;
	}

	/**
	 * 获取患者近6个月的最新检验列表
	 * @param memberId
	 * @param hospitalId
	 * @return
	 */
	private List<CheckoutDetailPO> listCheckoutDetail(String memberId,String hospitalId){
		List<CheckoutPO> checkoutList = this.checkoutService.listCheckoutNearlySixMonths(memberId,hospitalId);
		List<CheckoutPO> checkoutListToRepeat = new ArrayList<>();
		if(checkoutList != null && checkoutList.size()>0) {
			//去重复
			int size = checkoutList.size();
			for(int i=size-1;i>-1;i--){
				boolean isR = false;
				CheckoutPO info1 = checkoutList.get(i);
				if(!StringUtils.isBlank(info1.getCheckoutTitle())){
					String[] temps1 = info1.getCheckoutTitle().split("\\+");
					for(int j=i-1;j>-1;j--){
						CheckoutPO info2 = checkoutList.get(j);
						if(!StringUtils.isBlank(info1.getCheckoutTitle())){
							String[] temps2 = info2.getCheckoutTitle().split("\\+");
							if(temps1.length <= temps2.length){
								int flag = temps1.length;
								for(String str1 : temps1){
									for(String str2 : temps2){
										if(str2.equals(str1)){
											flag--;
											break;
										}
									}
								}
								if(flag<=0){
									isR = true;
								}
							}
						}
					}
				}
				if(!isR){
					checkoutListToRepeat.add(info1);
				}
			}
			//2、封装检验id，获取检验详情列表
			List<String> checkoutIds = new ArrayList<String>();
			for (CheckoutPO checkoutPO : checkoutListToRepeat) {
				checkoutIds.add(checkoutPO.getCheckoutId());
			}
			return this.checkoutService.listCheckoutDetailByParams(checkoutIds,memberId,hospitalId);
		}
		return null;
	}

	/**
	 * 计算egfr值
	 * @author 苏友智
	 * @time：2017-3-27 下午4:33:57
	 * @change 林雨堆
	 * @change-time：2017-06-26
	 * @change wangxy
	 * @change-time：2017-11-01
	 * @param scr 血清肌酐
	 * @param sex 性别 1男 2女
	 * @param birthday 出生年月日
	 */
	private String egfrHandler(String scr,String sex,String birthday){
		if(StringUtils.isBlank(scr) || StringUtils.isBlank(sex)
				|| StringUtils.isBlank(birthday)){
			return null;
		}
		String egfr = "";
		int age = DateHelper.getAge(birthday);
		//*0.01131
		double f_scr = Double.parseDouble(scr);
		f_scr=f_scr/88.4;
		double d_egfr = 0d;
		double agepow = Math.pow(0.993,age);
		//男
		if("1".equals(sex)){
			double flag = f_scr/0.9;
			double bc = 0d;
			if(f_scr<=0.7){
				bc = (Math.pow(flag,-0.411));
			} else {
				bc = (Math.pow(flag,-1.209));
			}
			d_egfr = 141 * bc * agepow;
		}
		//女
		else if ("2".equals(sex)){
			double flag = f_scr/0.7;
			double bc = 0d;
			if(f_scr<=0.7){
				bc = (Math.pow(flag,-0.329));
			} else {
				bc = (Math.pow(flag,-1.209));
			}
			d_egfr = 144 * bc * agepow;
		}

		if(d_egfr>0){
			egfr = String.format("%.2f", d_egfr);
		}
		return egfr;
	}
}
