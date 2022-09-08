package com.comvee.cdms.app.doctorapp.model;

public class DoctorModel {


	private static final long serialVersionUID = 1L;
	private String doctorId;// DOCTOR_ID NUMBER(15) Y 医生id
	private String userId;// 登录账号
	private String perName;// PER_NAME VARCHAR2(50) Y 医生名字
	private Integer perSex;// PER_SEX NUMBER(1) Y 性别 1男 2女
	private String departmentName;// TDEPARTMENT_NAME VARCHAR2(256) Y 所在科室
	private String departmentNameText;// TDEPARTMENT_NAME VARCHAR2(256) Y
										// 所在科室（有用户填写，用来展示用）
	private String hospitalName;// THOSPITAL_NAME VARCHAR2(256) Y 所在医院
	private String hospitalNameText;// THOSPITAL_NAME VARCHAR2(256) Y
									// 所在医院（有用户填写，用来展示用）
	private String position;// POSITION VARCHAR2(100) Y 职称
	private String positionText;// POSITION VARCHAR2(100) Y 职称（有用户填写，用来展示用）
	private String perRealPhoto;// PER_REAL_PHOTO VARCHAR2(256) Y 照片
	private String perRealPhotoS;// PER_REAL_PHOTO_S VARCHAR2(256) Y 缩略照片
	private Integer paperType;// PAPER_TYPE NUMBER(2) Y 证书类型
	private String paperNo;// PAPER_NO VARCHAR2(40) Y 证书编号 （执业证书）
	private String perSpacil;// PER_SPACIL VARCHAR2(1024) Y 专家特长
	private String perContent;// PER_CONTENT VARCHAR2(512) Y 个人简介
	private String birthday;// BIRTHDAY VARCHAR2(40) Y 出生
	private String country;// COUNTRY VARCHAR2(32) Y 所在国家
	private String province;// PROVINCE VARCHAR2(32) Y 所在省份
	private String city;// CITY VARCHAR2(32) Y 所在城市
	private String county; // 所在区
	private String countryzText;// COUNTRY VARCHAR2(32) Y 所在国家 文本
	private String provinceText;// PROVINCE VARCHAR2(32) Y 所在省份文本
	private String cityText;// CITY VARCHAR2(32) Y 所在城市 文本
	private String countyText; // 所在区 文本
	private String phone;// PHONE VARCHAR2(15) Y 联系电话
	private String addRess;// ADDRESS VARCHAR2(100) Y 联系地址
	private Integer sort;// SORT NUMBER(3) Y 排序
	private String email;// EMAIL VARCHAR2(64) Y 邮箱
	private String paper; // 证书
	private Integer efficacy; // 医生疗效
	private Integer attitude; // 态度
	private Long employNum;// EMPLOY_NUM NUMBER(8) Y 聘请人数(购买总次数)
	private String workCardImage;// 胸牌或工作证照片
	private Integer identity;// IDENTITY NUMBER(2) Y 0 0：注册医生 1：康为医生
	private String otherDepart;// OTHER_DEPART其他科室
	private String otherHospital;// 其他医院
	private String modiyfDt;
	private Integer isVlid;// 是否有效
	private String insertDt;// 入库时间
	private String idCard;// 身份证
	private String FacadeImg;// 身份证正面图片
	private String backFacadeImg;// 身份证背面图片
	private String signature; // 个性签名
	private String region; // 地区 市+区 （有用户填写，用来展示用）
	private String relateId; // 关联id cms医生id
	private String qrcodePath; // 二维码地址
	private String qrcodeId; // 医生编码
	private String memberPackageId;// MEMBER_PACKAGE_ID 用户购买的套餐
	private String packageName; // 套餐名称
	private String packageId;
	private String seuid = ""; // doctorID的MD5
	private String hosName;// 医院名称
	private String isOnLine; // 是否在线
	private String isConfirm;
	private Integer checkStatus; // 认证状态 0 未填写认证 1 已认证 2 已填写未认证 3 认证失败

	private String qq;
	private Integer hasServer;
	private String platformStr;
	private Long packageNum; // 拥有的套餐数量
	private String tagsId;
	private String tags; // 标签 中文
	private String studioId;
	private String isLead;
	private Integer studioCount;// 工作室数
	private String studioName;
	private String joinStudioTime;
	private String headImageUrl; // 工作室头像
	private String registDt;
	private String standardDepartId; //标准科室id
		
	private Integer doctorIsShow;// 医生是否显示
	
	public Integer getDoctorIsShow() {
		return doctorIsShow;
	}

	public void setDoctorIsShow(Integer doctorIsShow) {
		this.doctorIsShow = doctorIsShow;
	}

	public String getRegistDt() {
		return registDt;
	}

	public void setRegistDt(String registDt) {
		this.registDt = registDt;
	}

	public String getJoinStudioTime() {
		return joinStudioTime;
	}

	public void setJoinStudioTime(String joinStudioTime) {
		this.joinStudioTime = joinStudioTime;
	}

	public String getStudioName() {
		return studioName;
	}

	public void setStudioName(String studioName) {
		this.studioName = studioName;
	}

	public Integer getStudioCount() {
		return studioCount;
	}

	public void setStudioCount(Integer studioCount) {
		this.studioCount = studioCount;
	}

	public String getIsLead() {
		return isLead;
	}

	public void setIsLead(String isLead) {
		this.isLead = isLead;
	}

	public String getStudioId() {
		return studioId;
	}

	public void setStudioId(String studioId) {
		this.studioId = studioId;
	}

	public String getPlatformStr() {
		return platformStr;
	}

	public void setPlatformStr(String platformStr) {
		this.platformStr = platformStr;
	}

	public Integer getHasServer() {
		return hasServer;
	}

	public void setHasServer(Integer hasServer) {
		this.hasServer = hasServer;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getRelateId() {
		return relateId;
	}

	public String getMemberPackageId() {
		return memberPackageId;
	}

	public String getPackageId() {
		return packageId;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRelateId(String relateId) {
		this.relateId = relateId;
	}

	public void setMemberPackageId(String memberPackageId) {
		this.memberPackageId = memberPackageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public Integer getPerSex() {
		return perSex;
	}

	public void setPerSex(Integer perSex) {
		this.perSex = perSex;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPerRealPhoto() {
		return perRealPhoto;
	}

	public void setPerRealPhoto(String perRealPhoto) {
		this.perRealPhoto = perRealPhoto;
	}

	public Integer getPaperType() {
		return paperType;
	}

	public void setPaperType(Integer paperType) {
		this.paperType = paperType;
	}

	public String getPaperNo() {
		return paperNo;
	}

	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}

	public String getPerSpacil() {
		return perSpacil;
	}

	public void setPerSpacil(String perSpacil) {
		this.perSpacil = perSpacil;
	}

	public String getPerContent() {
		return perContent;
	}

	public void setPerContent(String perContent) {
		this.perContent = perContent;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddRess() {
		return addRess;
	}

	public void setAddRess(String addRess) {
		this.addRess = addRess;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	public Integer getEfficacy() {
		return efficacy;
	}

	public void setEfficacy(Integer efficacy) {
		this.efficacy = efficacy;
	}

	public Integer getAttitude() {
		return attitude;
	}

	public void setAttitude(Integer attitude) {
		this.attitude = attitude;
	}

	public Long getEmployNum() {
		return employNum;
	}

	public void setEmployNum(Long employNum) {
		this.employNum = employNum;
	}

	public String getWorkCardImage() {
		return workCardImage;
	}

	public void setWorkCardImage(String workCardImage) {
		this.workCardImage = workCardImage;
	}

	public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	public String getOtherDepart() {
		return otherDepart;
	}

	public void setOtherDepart(String otherDepart) {
		this.otherDepart = otherDepart;
	}

	public String getOtherHospital() {
		return otherHospital;
	}

	public void setOtherHospital(String otherHospital) {
		this.otherHospital = otherHospital;
	}

	public String getModiyfDt() {
		return modiyfDt;
	}

	public void setModiyfDt(String modiyfDt) {
		this.modiyfDt = modiyfDt;
	}

	public Integer getIsVlid() {
		return isVlid;
	}

	public void setIsVlid(Integer isVlid) {
		this.isVlid = isVlid;
	}

	public String getInsertDt() {
		return insertDt;
	}

	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getFacadeImg() {
		return FacadeImg;
	}

	public void setFacadeImg(String facadeImg) {
		FacadeImg = facadeImg;
	}

	public String getBackFacadeImg() {
		return backFacadeImg;
	}

	public void setBackFacadeImg(String backFacadeImg) {
		this.backFacadeImg = backFacadeImg;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getDepartmentNameText() {
		return departmentNameText;
	}

	public void setDepartmentNameText(String departmentNameText) {
		this.departmentNameText = departmentNameText;
	}

	public String getHospitalNameText() {
		return hospitalNameText;
	}

	public void setHospitalNameText(String hospitalNameText) {
		this.hospitalNameText = hospitalNameText;
	}

	public String getCountryzText() {
		return countryzText;
	}

	public void setCountryzText(String countryzText) {
		this.countryzText = countryzText;
	}

	public String getProvinceText() {
		return provinceText;
	}

	public void setProvinceText(String provinceText) {
		this.provinceText = provinceText;
	}

	public String getCityText() {
		return cityText;
	}

	public void setCityText(String cityText) {
		this.cityText = cityText;
	}

	public String getCountyText() {
		return countyText;
	}

	public void setCountyText(String countyText) {
		this.countyText = countyText;
	}

	public String getPositionText() {
		return positionText;
	}

	public void setPositionText(String positionText) {
		this.positionText = positionText;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPerRealPhotoS() {
		return perRealPhotoS;
	}

	public void setPerRealPhotoS(String perRealPhotoS) {
		this.perRealPhotoS = perRealPhotoS;
	}

	public String getQrcodePath() {
		return qrcodePath;
	}

	public void setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
	}

	public String getQrcodeId() {
		return qrcodeId;
	}

	public void setQrcodeId(String qrcodeId) {
		this.qrcodeId = qrcodeId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSeuid() {
		return seuid;
	}

	public void setSeuid(String seuid) {
		this.seuid = seuid;
	}

	public String getHosName() {
		return hosName;
	}

	public void setHosName(String hosName) {
		this.hosName = hosName;
	}

	public String getIsOnLine() {
		return isOnLine;
	}

	public void setIsOnLine(String isOnLine) {
		this.isOnLine = isOnLine;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Long getPackageNum() {
		return packageNum;
	}

	public void setPackageNum(Long packageNum) {
		this.packageNum = packageNum;
	}

	public String getTagsId() {
		return tagsId;
	}

	public void setTagsId(String tagsId) {
		this.tagsId = tagsId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
	public String getStandardDepartId() {
		return standardDepartId;
	}
	public void setStandardDepartId(String standardDepartId) {
		this.standardDepartId = standardDepartId;
	}
}
