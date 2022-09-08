package com.comvee.cdms.packages.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.dto.ListDoctorMemberDTO;
import com.comvee.cdms.member.dto.UpdateDoctorMemberDTO;
import com.comvee.cdms.member.po.DoctorMemberPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.packages.bo.AddPackageDialogueData;
import com.comvee.cdms.packages.cfg.PackageConstant;
import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.packages.dto.*;
import com.comvee.cdms.packages.mapper.PackageMapper;
import com.comvee.cdms.packages.po.*;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.packages.vo.MemberPackageInfoVO;
import com.comvee.cdms.packages.vo.MemberPackageListVO;
import com.comvee.cdms.packages.vo.MemberPackageServiceVO;
import com.comvee.cdms.packages.vo.PackageGroupVO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: suyz
 * @date: 2018/10/10
 */
@Service("packageService")
public class PackageServiceImpl implements PackageService {

    private final static Logger log = LoggerFactory.getLogger(PackageServiceImpl.class);

    @Autowired
    private PackageMapper packageMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private DoctorServiceI doctorService;

    @Override
    public List<PackagePO> listHospitalPackage(String hospitalId) {
        return this.packageMapper.listHospitalPackage(hospitalId);
    }

    @Override
    public String addMemberPackage(AddMemberPackageDTO addMemberPackageDTO) {
        PackagePO packagePO = this.packageMapper.getPackageById(addMemberPackageDTO.getPackageId());
        if(packagePO == null){
            throw new BusinessException("套餐不存在，请确认");
        }
        List<PackageServicePO> servicePOList = this.packageMapper.listPackageService(packagePO.getPackageId());
        if(servicePOList == null || servicePOList.size() == 0){
            throw new BusinessException("套餐不存在有效服务");
        }
        String memberPackageId = DaoHelper.getSeq();
        //新增套餐记录
        AddMemberPackageMapperDTO addMemberPackageMapperDTO = addMemberPackageDTOHandler(addMemberPackageDTO, packagePO);
        addMemberPackageMapperDTO.setSid(memberPackageId);
        addMemberPackageMapperDTO.setDoctorId(addMemberPackageDTO.getDoctorId());
        this.packageMapper.addMemberPackage(addMemberPackageMapperDTO);

        //新增套餐服务记录
        servicePOList.forEach(x -> {
            AddMemberPackageServiceMapperDTO addMemberPackageServiceMapperDTO = new AddMemberPackageServiceMapperDTO();
            BeanUtils.copyProperties(addMemberPackageServiceMapperDTO, x);
            addMemberPackageServiceMapperDTO.setMemberPackageId(memberPackageId);
            addMemberPackageServiceMapperDTO.setSid(DaoHelper.getSeq());
            addMemberPackageServiceMapperDTO.setRemainTime(x.getUseTime());
            addMemberPackageServiceMapperDTO.setTotalTime(x.getUseTime());
            addMemberPackageServiceMapperDTO.setServiceType(x.getUseType());
            this.packageMapper.addMemberPackageService(addMemberPackageServiceMapperDTO);
        });

        addPackageMessageHandler(addMemberPackageMapperDTO);
        payStatusHandler(addMemberPackageMapperDTO);
        return memberPackageId;
    }

    /**
     * 付费状态处理
     * @param addMemberPackageMapperDTO
     */
    private void payStatusHandler(AddMemberPackageMapperDTO addMemberPackageMapperDTO){
        if(Constant.DEFAULT_FOREIGN_ID.equals(addMemberPackageMapperDTO.getDoctorId())){
            return;
        }
        String nowDate = DateHelper.getNowDate();
        if(nowDate.compareToIgnoreCase(addMemberPackageMapperDTO.getStartDt()) >= 0
                && addMemberPackageMapperDTO.getEndDt().compareToIgnoreCase(nowDate) > 0){
            UpdateDoctorMemberDTO updateDoctorMemberDTO = new UpdateDoctorMemberDTO();
            updateDoctorMemberDTO.setMemberId(addMemberPackageMapperDTO.getMemberId());
            updateDoctorMemberDTO.setDoctorId(addMemberPackageMapperDTO.getDoctorId());
            updateDoctorMemberDTO.setPayStatus(MemberDoctorConstant.PAY_STATUS_YES);
            this.memberService.updateMemberDoctor(updateDoctorMemberDTO);
        }
    }

    /**
     *  新增套餐消息处理
     * @param addMemberPackageMapperDTO
     */
    private void addPackageMessageHandler(AddMemberPackageMapperDTO addMemberPackageMapperDTO){
        if(Constant.DEFAULT_FOREIGN_ID.equals(addMemberPackageMapperDTO.getDoctorId())){
            return;
        }
        //新增对话
        DialoguePO dialoguePO = new DialoguePO();
        dialoguePO.setMemberId(addMemberPackageMapperDTO.getMemberId());
        dialoguePO.setDoctorId(addMemberPackageMapperDTO.getDoctorId());
        dialoguePO.setSenderId(addMemberPackageMapperDTO.getDoctorId());
        dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        dialoguePO.setMsgType(DialogueConstant.DIALOGUE_ADD_PACKAGE_MSG_TYPE);
        dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
        dialoguePO.setDoctorMsg(ADD_PACKAGE_MESSAGE_TITLE);
        dialoguePO.setPatientMsg(ADD_PACKAGE_MESSAGE_TITLE);
        dialoguePO.setForeignId(addMemberPackageMapperDTO.getSid());

        AddPackageDialogueData addPackageDialogueData = new AddPackageDialogueData();
        addPackageDialogueData.setPackageName(addMemberPackageMapperDTO.getPackageName());
        addPackageDialogueData.setPrice(addMemberPackageMapperDTO.getPrice());
        addPackageDialogueData.setDate(DateHelper.getToday());
        addPackageDialogueData.setMemberPackageId(addMemberPackageMapperDTO.getSid());
        dialoguePO.setDataStr(JSON.toJSONString(addPackageDialogueData));
        this.dialogueService.addDialogue(dialoguePO);

        //新增微信消息
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(addMemberPackageMapperDTO.getMemberId());
        addWechatMessageDTO.setForeignId(addMemberPackageMapperDTO.getSid());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_ADD_PACKAGE);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberPackageId", addMemberPackageMapperDTO.getSid());
        jsonObject.put("packageName" , addMemberPackageMapperDTO.getPackageName());
        jsonObject.put("price", addMemberPackageMapperDTO.getPrice());
        jsonObject.put("doctorId" , addMemberPackageMapperDTO.getDoctorId());
        jsonObject.put("date" , DateHelper.getToday());
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    private final static String ADD_PACKAGE_MESSAGE_TITLE = "服务开通提醒";

    /**
     * 患者套餐mapper对象处理
     * @param addMemberPackageDTO
     * @param packagePO
     * @return
     */
    private AddMemberPackageMapperDTO addMemberPackageDTOHandler(AddMemberPackageDTO addMemberPackageDTO, PackagePO packagePO){
        AddMemberPackageMapperDTO addMemberPackageMapperDTO = new AddMemberPackageMapperDTO();
        BeanUtils.copyProperties(addMemberPackageMapperDTO, packagePO);
        addMemberPackageMapperDTO.setMemberId(addMemberPackageDTO.getMemberId());
        if (StringUtils.isBlank(addMemberPackageDTO.getOrderId())){
            addMemberPackageMapperDTO.setOrderId(DaoHelper.getSeq());
        }else {
            addMemberPackageMapperDTO.setOrderId(addMemberPackageDTO.getOrderId());
        }
/*        addMemberPackageMapperDTO.setStartDt(getStartDt());
        addMemberPackageMapperDTO.setEndDt(getEndDt(packagePO));*/
        addMemberPackageMapperDTO.setPackageStatus(1);
        memberPackageDtHandler(addMemberPackageMapperDTO, addMemberPackageDTO, packagePO);
        return addMemberPackageMapperDTO;
    }

    /**
     * 套餐时间处理
     * @param addMemberPackageMapperDTO
     * @param addMemberPackageDTO
     * @param packagePO
     */
    private void memberPackageDtHandler(AddMemberPackageMapperDTO addMemberPackageMapperDTO
            , AddMemberPackageDTO addMemberPackageDTO, PackagePO packagePO){
        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setDoctorId(addMemberPackageDTO.getDoctorId());
        listValidMemberPackageDTO.setMemberId(addMemberPackageDTO.getMemberId());
        listValidMemberPackageDTO.setPackageId(addMemberPackageDTO.getPackageId());
        MemberPackagePO memberPackagePO = this.packageMapper.getNewestValidMemberPackage(listValidMemberPackageDTO);
        if(memberPackagePO == null){
            addMemberPackageMapperDTO.setStartDt(getStartDt(null));
            addMemberPackageMapperDTO.setEndDt(getEndDt(packagePO, null, addMemberPackageDTO.getQuantity()));
        }else{
            addMemberPackageMapperDTO.setStartDt(getStartDt(memberPackagePO.getEndDt()));
            addMemberPackageMapperDTO.setEndDt(getEndDt(packagePO, memberPackagePO.getEndDt(), addMemberPackageDTO.getQuantity()));
        }
    }



    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final static String TIME_UNIT_Y = "Y";
    private final static String TIME_UNIT_M = "M";
    private final static String TIME_UNIT_D = "D";

    /**
     * 获取套餐开始时间
     * @return
     */
    private static String getStartDt(String startDate){
        Calendar calendar = Calendar.getInstance();
        if(!StringUtils.isBlank(startDate)){
            calendar.setTime(DateHelper.getDate(startDate, DATE_FORMAT));
            calendar.add(Calendar.DAY_OF_WEEK , 1);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取套餐结束时间
     * @param packagePO
     * @return
     */
    private static String getEndDt(PackagePO packagePO, String endDate, Integer quantity){
        Calendar calendar = Calendar.getInstance();
        if(!StringUtils.isBlank(endDate)){
            calendar.setTime(DateHelper.getDate(endDate, DATE_FORMAT));
            calendar.add(Calendar.DAY_OF_WEEK , 1);
        }
        if (quantity == null || quantity.equals(1)){
            //原设计为空，或者购买服务包数量为1
            if(TIME_UNIT_Y.equals(packagePO.getTimeUnit())){
                calendar.add(Calendar.YEAR, packagePO.getTimeNum());
            }else if(TIME_UNIT_M.equals(packagePO.getTimeUnit())){
                calendar.add(Calendar.MONTH, packagePO.getTimeNum());
            }else if(TIME_UNIT_D.equals(packagePO.getTimeUnit())){
                calendar.add(Calendar.DAY_OF_MONTH, packagePO.getTimeNum());
            }
        }else {
            //购买服务包数量为多份
            if(TIME_UNIT_Y.equals(packagePO.getTimeUnit())){
                calendar.add(Calendar.YEAR, packagePO.getTimeNum() * quantity);
            }else if(TIME_UNIT_M.equals(packagePO.getTimeUnit())){
                calendar.add(Calendar.MONTH, packagePO.getTimeNum() * quantity);
            }else if(TIME_UNIT_D.equals(packagePO.getTimeUnit())){
                calendar.add(Calendar.DAY_OF_MONTH, packagePO.getTimeNum() * quantity);
            }
        }


        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return new SimpleDateFormat(DATE_FORMAT).format(calendar.getTime());
    }

    @Override
    public PageResult<MemberPackagePO> listMemberPackageById(ListMemberPackageDTO listMemberPackageDTO, PageRequest pr) {
        if(!StringUtils.isBlank(listMemberPackageDTO.getDoctorId())){
            List<String> doctorIds = this.doctorService.listTeamId(listMemberPackageDTO.getDoctorId());
            listMemberPackageDTO.setDoctorId(null);
            listMemberPackageDTO.setDoctorList(doctorIds);
        }
        ListMemberPackageMapperDTO listMemberPackageMapperDTO = new ListMemberPackageMapperDTO();
        BeanUtils.copyProperties(listMemberPackageMapperDTO, listMemberPackageDTO);
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<MemberPackagePO> list = this.packageMapper.listMemberPackage(listMemberPackageMapperDTO);
        return new PageResult<>(list);
    }

    @Override
    public PageResult<MemberPackageListVO> listMemberPackage(ListMemberPackageDTO listMemberPackageDTO, PageRequest pr) {
        ListMemberPackageMapperDTO listMemberPackageMapperDTO = new ListMemberPackageMapperDTO();
        BeanUtils.copyProperties(listMemberPackageMapperDTO, listMemberPackageDTO);
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<MemberPackagePO> list = this.packageMapper.listMemberPackage(listMemberPackageMapperDTO);
        PageResult<MemberPackagePO> packageList = new PageResult<>(list);
        List<String> memberIdList = new ArrayList<>();
        list.forEach( x -> {
            memberIdList.add(x.getMemberId());
        });
        if(memberIdList.size() == 0){
            return new PageResult<>(pr.getPage(), pr.getRows());
        }
        List<MemberPO> memberPOList = this.memberService.listMemberByIdList(memberIdList);
        Map<String,MemberPO> memberMap = new HashMap<>();
        memberPOList.forEach( x -> {
            memberMap.put(x.getMemberId(), x);
        });
        List<MemberPackageListVO> memberList = new ArrayList<>();
        list.forEach(x -> {
            MemberPackageListVO memberPackageListVO = new MemberPackageListVO();
            BeanUtils.copyProperties(memberPackageListVO, x);
            //填充患者信息
            MemberPO memberPO = memberMap.get(x.getMemberId());
            if(memberPO != null){
                memberPackageListVO.setMemberName(memberPO.getMemberName());
                memberPackageListVO.setSex(memberPO.getSex());
            }
            memberList.add(memberPackageListVO);
        });
        PageResult<MemberPackageListVO> result = getMemberPackageListPageResult(packageList);
        result.setRows(memberList);
        return result;
    }

    @Override
    public long sumPackagePrice(SumPackagePriceDTO sumPackagePriceDTO) {
        return this.packageMapper.sumPackagePrice(sumPackagePriceDTO);
    }

    @Override
    public List<PackageMonthPricePO> sumPackageMonthPrice(SumPackageMonthPriceDTO sumPackagePriceDTO) {
        return this.packageMapper.sumPackageMonthPrice(sumPackagePriceDTO);
    }

    @Override
    public List<PackageMonthPricePO> sumPackageMonthPrice(GetStatisticsDTO dto) {
        return this.packageMapper.sumPackageMonthPriceForHos(dto);
    }

    @Override
    public List<CountMonthPackagePO> countMonthPackage(CountMonthPackageDTO countMonthPackageDTO) {
        return this.packageMapper.countMonthPackage(countMonthPackageDTO);
    }

    @Override
    public MemberPackagePO getMemberPackageById(String memberPackageId) {
        MemberPackagePO packagePO = this.packageMapper.getMemberPackageById(memberPackageId);
        try{
            this.packageMapper.setMemberPackageIsRead(memberPackageId);
        }catch (Exception e){
            log.warn("患者套餐记录设置为已读失败");
        }
        return packagePO;
    }

    @Override
    public List<MemberPackagePO> listValidMemberPackage(ListValidMemberPackageDTO listValidMemberPackageDTO) {
        return this.packageMapper.listValidMemberPackage(listValidMemberPackageDTO);
    }

    @Override
    public List<String> listHasValidPackageMember(List<String> memberIdList, String doctorId) {
        return this.packageMapper.listHasValidPackageMember(memberIdList, doctorId);
    }

    @Override
    public boolean usePackageServiceWithLock(UsePackageServiceDTO usePackageServiceDTO) {
        MemberPackageServicePO memberPackageServicePO = this.packageMapper.getValidMemberService(usePackageServiceDTO);
        if(memberPackageServicePO == null){
            return false;
        }
        if(PackageConstant.PACKAGE_SERVICE_TYPE_INFINITE == memberPackageServicePO.getServiceType()){
            return true;
        }
        this.packageMapper.useService(memberPackageServicePO.getSid());

        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(usePackageServiceDTO.getMemberId());
        listValidMemberPackageDTO.setDoctorId(usePackageServiceDTO.getDoctorId());
        List<MemberPackagePO> list = this.packageMapper.listValidMemberPackage(listValidMemberPackageDTO);
        //不存在有效套餐，将医患关系状态修改为过期
        if(list == null || list.size() == 0){
            UpdateDoctorMemberDTO updateDoctorMemberDTO = new UpdateDoctorMemberDTO();
            updateDoctorMemberDTO.setMemberId(usePackageServiceDTO.getMemberId());
            updateDoctorMemberDTO.setDoctorId(usePackageServiceDTO.getDoctorId());
            updateDoctorMemberDTO.setPayStatus(MemberDoctorConstant.PAY_STATUS_EXPIRE);
            this.memberService.updateMemberDoctor(updateDoctorMemberDTO);
        }
        return true;
    }

    @Override
    public PageResult<String> listHasValidServiceMember(ServiceCode serviceCode,int page,int rows) {
        PageHelper.startPage(page, rows);
        List<String> list = this.packageMapper.listHasValidServiceMember(serviceCode.name());
        return new PageResult(list);
    }

    @Override
    public MemberPackageInfoVO getMemberPackageWithServiceInfo(String memberPackageId) {
        MemberPackageInfoVO memberPackageInfoVO = new MemberPackageInfoVO();
        MemberPackagePO memberPackagePO = this.packageMapper.getMemberPackageById(memberPackageId);
        List<MemberPackageServicePO> packageServicePOS = this.packageMapper.listMemberPackageServie(memberPackageId);
        memberPackageInfoVO.setMemberPackage(memberPackagePO);
        memberPackageInfoVO.setServiceList(memberPackageServiceHandler(packageServicePOS));
        return memberPackageInfoVO;
    }

    @Override
    public PageResult<OrderPackagePO> listOrderPackage(ListMemberPackageDTO listMemberPackageDTO, PageRequest pr) {
        ListMemberPackageMapperDTO listMemberPackageMapperDTO = new ListMemberPackageMapperDTO();
        BeanUtils.copyProperties(listMemberPackageMapperDTO, listMemberPackageDTO);
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<OrderPackagePO> orderPackagePOS = this.packageMapper.listOrderPackage(listMemberPackageMapperDTO);
        return new PageResult<>(orderPackagePOS);
    }

    @Override
    public PageResult<MemberPackagePO> listMemberPackageByExpireDay(int page , int rows, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(calendar.getTime());
        PageHelper.startPage(page, rows);
        List<MemberPackagePO> list = this.packageMapper.listMemberPackageByExpireDay(date);
        return new PageResult<>(list);
    }

    @Override
    public long countMemberPackage(CountMemberPackageDTO countMemberPackageDTO) {
        return this.packageMapper.countMemberPackage(countMemberPackageDTO);
    }

    @Override
    public MemberPackagePO getEarlyMemberPackage(SynthesizeDataDTO synthesizeDataDTO) {
        List<MemberPackagePO> list = this.packageMapper.getEarlyMemberPackage(synthesizeDataDTO);
        MemberPackagePO packagePO = new MemberPackagePO();
        if (list != null && list.size() > 0){
            packagePO = list.get(0);
        }
        return packagePO;
    }

    @Override
    public PackagePO getPackageById(String packageId) {
        return this.packageMapper.getPackageById(packageId);
    }

    @Override
    public List<CountMonthPackagePO> countMonthPackage(GetStatisticsDTO dto) {
        return this.packageMapper.countMonthPackageForHos(dto);
    }

    /**
     * 服务处理
     * @param packageServicePOS
     * @return
     */
    private List<MemberPackageServiceVO> memberPackageServiceHandler(List<MemberPackageServicePO> packageServicePOS){
        List<MemberPackageServiceVO> list = new ArrayList<>();
        packageServicePOS.forEach( x -> {
            MemberPackageServiceVO memberPackageServiceVO = new MemberPackageServiceVO();
            BeanUtils.copyProperties(memberPackageServiceVO, x);
            memberPackageServiceVO.setServiceTime(serviceTimeHandler(x));
            memberPackageServiceVO.setRemainTimeText(serviceRemainTimeTextHandler(x));
            list.add(memberPackageServiceVO);
        });
        return list;
    }

    /**
     * 服务次数处理
     * @param memberPackageServicePO
     * @return
     */
    private String serviceTimeHandler(MemberPackageServicePO memberPackageServicePO){
        String serviceTime = "";
        if(memberPackageServicePO.getServiceType() == PackageConstant.PACKAGE_SERVICE_TYPE_INFINITE){
            serviceTime = "不限次";
        }else{
            serviceTime = memberPackageServicePO.getTotalTime().toString().concat("次");
        }
        return serviceTime;
    }

    /**
     * 剩余次数文案处理
     * @param memberPackageServicePO
     * @return
     */
    private String serviceRemainTimeTextHandler(MemberPackageServicePO memberPackageServicePO){
        String text = "";
        if(memberPackageServicePO.getServiceType() == PackageConstant.PACKAGE_SERVICE_TYPE_INFINITE){
            text = "剩余不限次";
        }else{
            text = memberPackageServicePO.getRemainTime() + "次";
        }
        return text;
    }

    /**
     * 获取套餐列表分页结果
     * @param packageList
     * @return
     */
    private PageResult<MemberPackageListVO> getMemberPackageListPageResult(PageResult<MemberPackagePO> packageList){
        PageResult<MemberPackageListVO> p = new PageResult<>();
        p.setPageNum(packageList.getPageNum());
        p.setPageSize(packageList.getPageSize());
        p.setTotalPages(packageList.getTotalPages());
        p.setTotalRows(packageList.getTotalRows());
        return p;
    }

    @Override
    public PageResult<PackageManagePO> listPackageManage(PackageManageDTO packageManageDTO, PageRequest page) {
        PageHelper.startPage(page.getPage(),page.getRows());
        List<PackageManagePO> list = this.packageMapper.listPackageManage(packageManageDTO);
        return new PageResult<>(list);
    }

    @Override
    public long countNewPackage(SynthesizeDataDTO synthesizeDataDTO) {
        return this.packageMapper.countNewPackage(synthesizeDataDTO);
    }

    @Override
    public List<PackagePO> pullpackage() {
        return this.packageMapper.pullpackage();
    }


    @Override
    public void deletePackage(DeleteMemberPackageDTO packageDTO) {
        //删除套餐
        this.packageMapper.deletePackage(packageDTO.getSid());
        //删除套餐服务
        this.packageMapper.updateMemberPackageService(packageDTO.getSid());
        List<MemberPackagePO> packagePo = this.packageMapper.listPackageByMemberDoctor(packageDTO.getMemberId(), packageDTO.getDoctorId());
        if (packagePo == null || packagePo.size()<=0){
            ListDoctorMemberDTO dto = new ListDoctorMemberDTO();
            dto.setMemberId(packageDTO.getMemberId());
            dto.setDoctorId(packageDTO.getDoctorId());
            DoctorMemberPO doctorMember = this.memberService.getDoctorMember(dto);
            UpdateDoctorMemberDTO dto1 = packageParam(doctorMember);
            dto1.setPayStatus(1);
            this.memberService.updateMemberDoctor(dto1);
        }else if (packagePo.size() == 1){
            ListDoctorMemberDTO dto = new ListDoctorMemberDTO();
            dto.setMemberId(packageDTO.getMemberId());
            dto.setDoctorId(packageDTO.getDoctorId());
            DoctorMemberPO doctorMember = this.memberService.getDoctorMember(dto);
            String endDt = packagePo.get(0).getEndDt();
            if (DateHelper.getDate(endDt,"yyyy-MM-dd").getTime()< System.currentTimeMillis()){
                UpdateDoctorMemberDTO dto1 = packageParam(doctorMember);
                dto1.setPayStatus(3);
                this.memberService.updateMemberDoctor(dto1);
            }
        }else {
            ListDoctorMemberDTO dto = new ListDoctorMemberDTO();
            dto.setMemberId(packageDTO.getMemberId());
            dto.setDoctorId(packageDTO.getDoctorId());
            DoctorMemberPO doctorMember = this.memberService.getDoctorMember(dto);
            for (MemberPackagePO po : packagePo) {
                String endDt = po.getEndDt();
                if (DateHelper.getDate(endDt,"yyyy-MM-dd").getTime()>System.currentTimeMillis()){
                    UpdateDoctorMemberDTO dto1 = packageParam(doctorMember);
                    dto1.setPayStatus(2);
                    this.memberService.updateMemberDoctor(dto1);
                    break;
                }
            }
        }

    }

    @Override
    public List<MemberPackagePO> listPackageByMemberDoctor(String memberId, String doctorId) {
        return this.packageMapper.listPackageByMemberDoctor(memberId,doctorId);
    }

    @Override
    public void updateMemberPackageService(String memberPackageId) {
        this.packageMapper.updateMemberPackageService(memberPackageId);
    }

    @Override
    public long sumPackagePrice(GetStatisticsDTO dto) {
        return this.packageMapper.sumPackagePriceForHos(dto);
    }

    private UpdateDoctorMemberDTO packageParam(DoctorMemberPO doctorMember){
        UpdateDoctorMemberDTO dto1 = new UpdateDoctorMemberDTO();
        dto1.setSid(doctorMember.getSid());
        dto1.setMemberId(doctorMember.getMemberId());
        dto1.setDoctorId(doctorMember.getDoctorId());
        dto1.setIsAttend(doctorMember.getIsAttend());
        dto1.setLabel(doctorMember.getLabel());
        dto1.setGroupId(doctorMember.getGroupId());
        dto1.setOperatorId(doctorMember.getOperatorId());
        dto1.setConcernStatus(doctorMember.getConcernStatus());
        dto1.setGroupIdWhere(doctorMember.getGroupId());
        return dto1;
    }


    @Override
    public List<PackageGroupVO> listPackageGroup(String doctorId) {
        List<PackageGroupVO> list = new ArrayList<>();
        //加载医生列表
        List<DoctorPO> doctorList = this.doctorService.listMyDoctor(doctorId);
        List<String> doctorIdList = doctorList.stream().map(DoctorPO::getDoctorId).collect(Collectors.toList());
        List<PackagePO> packageList = this.packageMapper.getAllPackage(doctorIdList);
        if(packageList == null){
            packageList = new ArrayList<>();
        }
        //有套餐的
        packageList.forEach(x ->{
            PackageGroupVO packageGroupVO = new PackageGroupVO();
            packageGroupVO.setPackageId(x.getPackageId());
            packageGroupVO.setPackageName(x.getPackageName());
            long peopleNumber = this.packageMapper.countPackagePeopleNumber(x.getPackageId() ,doctorIdList);
            packageGroupVO.setPeopleNumber(peopleNumber);
            list.add(packageGroupVO);
        });
        //无套餐的
        PackageGroupVO packageGroupVO = new PackageGroupVO();
        packageGroupVO.setPackageName("免费");
        packageGroupVO.setPackageId(Constant.DEFAULT_FOREIGN_ID);
        long noPayNumber = this.memberService.countNoPayMember(doctorIdList);
        packageGroupVO.setPeopleNumber(noPayNumber);
        list.add(packageGroupVO);
        return list;
    }

    @Override
    public List<MemberPackagePO> listMemberAllPackage(ListValidMemberPackageDTO dto) {
        return this.packageMapper.listMemberAllPackage(dto);
    }

    @Override
    public List<MemberPackagePO> listAllMemberValidPackage(String memberId) {
        return this.packageMapper.listAllMemberValidPackage(memberId);
    }
}
