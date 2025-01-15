package com.noaa.base.base.vo;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.noaa.base.code.vo.CodeSysVO;
import com.noaa.base.common.vo.CommonParamsVO;
import com.noaa.base.menu.vo.MenuVO;

public class BaseViewModel {
	private String requestIP;
	private Date requestTime;
	private MenuVO menuDetail;
	private List<MenuVO> menus;
	private List<CodeSysVO> codes;
	private String pageYn;
	private int pageNo;
	private int pageSize;
	private String id;
	private int resultCount;
	private String resultData;
	private String resultMessage;
	private String menuHtml;
    private int totalCount;
    private String isValidYN;
    private String pg;
	private CommonParamsVO commonParams;
	private String rememberMeYn;
	private List<MultipartFile> uploadFiles;
	private String smartEditor;
    private String statusCode;
    private String reason;
    private String returnURI;
    private String loginURI;
    private String loginView;
    private String paramOption;
	private String paramText;
	private String paramStartDate;
	private String paramEndDate;
	private int paramAvgTime; //몇분이내에 접속자수를 가져오기 위한 변수
	private String defaultView;
	private int user_tot_cnt; //총사용자수
	private int avg_use_time; // 평균사용시간
	private int cur_user_cnt; // 현재접속자수
	private boolean enableWriteYN;
	private String keyModule;
	private String keyExponent;
	private String fileGroup;
	
	private String version;

	private String grp_code;
	private String sys_code;
	public String user_id;
	private String img_file_name;
	private List<String> fileTitle;
	private List<Integer> key;
	
	private String originImg;
	private String editImg;
	private int canvasWidth;
	private int canvasHeight;

	private int uid;
	private int sn;
	private String mode;
	private String editableClickYn;
	private String mapperId;
	private int bplc_sn;
	
	
	public int getBplc_sn() {
		return bplc_sn;
	}


	public void setBplc_sn(int bplc_sn) {
		this.bplc_sn = bplc_sn;
	}


	public int getSn() {
		return sn;
	}


	public void setSn(int sn) {
		this.sn = sn;
	}


	public String getMapperId() {
		return mapperId;
	}


	public void setMapperId(String mapperId) {
		this.mapperId = mapperId;
	}


	public String getGbn() {
		return gbn;
	}


	public void setGbn(String gbn) {
		this.gbn = gbn;
	}


	private String gbn;

	public String getMode() {
		return mode;
	}


	public String getEditableClickYn() {
		return editableClickYn;
	}


	public void setEditableClickYn(String editableClickYn) {
		this.editableClickYn = editableClickYn;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}


	public int getUid() {
		return uid;
	}


	public void setUid(int uid) {
		this.uid = uid;
	}


	public String getGrp_code() {
		return grp_code;
	}


	public void setGrp_code(String grp_code) {
		this.grp_code = grp_code;
	}


	public String getSys_code() {
		return sys_code;
	}


	public void setSys_code(String sys_code) {
		this.sys_code = sys_code;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getImg_file_name() {
		return img_file_name;
	}


	public void setImg_file_name(String img_file_name) {
		this.img_file_name = img_file_name;
	}


	public List<String> getFileTitle() {
		return fileTitle;
	}


	public void setFileTitle(List<String> fileTitle) {
		this.fileTitle = fileTitle;
	}


	public List<Integer> getKey() {
		return key;
	}


	public void setKey(List<Integer> key) {
		this.key = key;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getFileGroup() {
		return fileGroup;
	}


	public void setFileGroup(String fileGroup) {
		this.fileGroup = fileGroup;
	}


	public String getKeyModule() {
		return keyModule;
	}


	public void setKeyModule(String keyModule) {
		this.keyModule = keyModule;
	}


	public String getKeyExponent() {
		return keyExponent;
	}


	public void setKeyExponent(String keyExponent) {
		this.keyExponent = keyExponent;
	}


	public MenuVO getMenuDetail() {
		return menuDetail;
	}


	public void setMenuDetail(MenuVO menuDetail) {
		this.menuDetail = menuDetail;
	}


	public boolean isEnableWriteYN() {
		return enableWriteYN;
	}


	public void setEnableWriteYN(boolean enableWriteYN) {
		this.enableWriteYN = enableWriteYN;
	}


	public String getLoginView() {
		return loginView;
	}


	public void setLoginView(String loginView) {
		this.loginView = loginView;
	}


	public String getLoginURI() {
		return loginURI;
	}


	public void setLoginURI(String loginURI) {
		this.loginURI = loginURI;
	}


	public String getDefaultView() {
		return defaultView;
	}


	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	}


	public int getParamAvgTime() {
		return paramAvgTime;
	}


	public void setParamAvgTime(int paramAvgTime) {
		this.paramAvgTime = paramAvgTime;
	}

	public int getUser_tot_cnt() {
		return user_tot_cnt;
	}


	public void setUser_tot_cnt(int user_tot_cnt) {
		this.user_tot_cnt = user_tot_cnt;
	}


	public int getAvg_use_time() {
		return avg_use_time;
	}


	public void setAvg_use_time(int avg_use_time) {
		this.avg_use_time = avg_use_time;
	}


	public int getCur_user_cnt() {
		return cur_user_cnt;
	}


	public void setCur_user_cnt(int cur_user_cnt) {
		this.cur_user_cnt = cur_user_cnt;
	}


	public String getParamOption() {
		return paramOption;
	}


	public void setParamOption(String paramOption) {
		this.paramOption = paramOption;
	}


	public String getParamText() {
		return paramText;
	}


	public void setParamText(String paramText) {
		this.paramText = paramText;
	}


	public String getParamStartDate() {
		return paramStartDate;
	}


	public void setParamStartDate(String paramStartDate) {
		this.paramStartDate = paramStartDate;
	}


	public String getParamEndDate() {
		return paramEndDate;
	}


	public void setParamEndDate(String paramEndDate) {
		this.paramEndDate = paramEndDate;
	}


	public String getReturnURI() {
		return returnURI;
	}


	public void setReturnURI(String returnURI) {
		this.returnURI = returnURI;
	}


	public String getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getSmartEditor() {
		return smartEditor;
	}


	public void setSmartEditor(String smartEditor) {
		this.smartEditor = smartEditor;
	}


	public List<MultipartFile> getUploadFiles(){
		return uploadFiles;
	}


	public void setUploadFiles(List<MultipartFile> uploadFiles) {
		this.uploadFiles = uploadFiles;
	}


	public String getRememberMeYn() {
		return rememberMeYn;
	}


	public void setRememberMeYn(String rememberMeYn) {
		this.rememberMeYn = rememberMeYn;
	}
	
	public String getPg() {
		return pg;
	}
	public void setPg(String pg) {
		this.pg = pg;
	}
	public CommonParamsVO getCommonParams() {
		return commonParams;
	}
	public void setCommonParams(CommonParamsVO commonParams) {
		this.commonParams = commonParams;
	}
	public String getIsValidYN() {
		return isValidYN;
	}
	public void setIsValidYN(String isValidYN) {
		this.isValidYN = isValidYN;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getResultData() {
		return resultData;
	}
	public void setResultData(String resultData) {
		this.resultData = resultData;
	}
	public String getMenuHtml() {
		return menuHtml;
	}
	public void setMenuHtml(String menuHtml) {
		this.menuHtml = menuHtml;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<CodeSysVO> getCodes() {
		return codes;
	}
	public void setCodes(List<CodeSysVO> codes) {
		this.codes = codes;
	}
	public int getResultCount() {
		return resultCount;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getPageYn() {
		return pageYn;
	}
	public void setPageYn(String pageYn) {
		this.pageYn = pageYn;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<MenuVO> getMenus() {
		return menus;
	}
	public void setMenus(List<MenuVO> menus) {
		this.menus = menus;
	}
	public String getRequestIP() {
		return requestIP;
	}
	public void setRequestIP(String requestIP) {
		this.requestIP = requestIP;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}


	public String getOriginImg() {
		return originImg;
	}


	public void setOriginImg(String originImg) {
		this.originImg = originImg;
	}


	public String getEditImg() {
		return editImg;
	}


	public void setEditImg(String editImg) {
		this.editImg = editImg;
	}


	public int getCanvasWidth() {
		return canvasWidth;
	}


	public void setCanvasWidth(int canvasWidth) {
		this.canvasWidth = canvasWidth;
	}


	public int getCanvasHeight() {
		return canvasHeight;
	}


	public void setCanvasHeight(int canvasHeight) {
		this.canvasHeight = canvasHeight;
	}
	
}
