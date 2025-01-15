package com.noaa.base.admin.viewmodel;

import java.util.List;

import com.noaa.base.account.vo.LoginLogVO;
import com.noaa.base.account.vo.RoleVO;
import com.noaa.base.account.vo.UserVO;
import com.noaa.base.base.vo.BaseViewModel;
import com.noaa.base.code.vo.CodeSysVO;
import com.noaa.base.meta.vo.MetaAdmVO;

//import egovframework.let.uss.umt.service.MberManageVO;
//import egovframework.let.uss.umt.service.UserDefaultVO;

public class AdminAccountViewModel extends BaseViewModel {
	private UserVO detail;
	private LoginLogVO loginDetail;
	private List<UserVO> list;
	private List<CodeSysVO> listUserTypeCodes;
	private List<CodeSysVO> listUserStatusCodes;
	private List<CodeSysVO> listPhoneCodes;
	private List<RoleVO> listRoles;
	private List<LoginLogVO> listLoginLog;
	private List<LoginLogVO> chartLoginLog;
	private List<MetaAdmVO> listAdm;
	private String paramUserName;
	private String paramUserID;
	private String paramUserType;
	private String paramUserStatus;
	private String paramUpdateDate;
	private String paramUserEmail;
	private int conn_cnt_tot; //총 접속횟수
	//egov-------------------------------------------
	//private List<MberManageVO> listMberManageVO;
//	private List<CmmnDetailCode> passwordHint_result;
//	private List<CmmnDetailCode> sexdstnCode_result;
//	private List<CmmnDetailCode> mberSttus_result;
//	private List<CmmnDetailCode> groupId_result;
	//private MberManageVO mberManage;
	//private UserDefaultVO userSearchVO;
	
	//egov-------------------------------------------
	private String title;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
//	public UserDefaultVO getUserSearchVO() {
//		return userSearchVO;
//	}
	public String getParamUserEmail() {
		return paramUserEmail;
	}
	public void setParamUserEmail(String paramUserEmail) {
		this.paramUserEmail = paramUserEmail;
	}
	public List<CodeSysVO> getListPhoneCodes() {
		return listPhoneCodes;
	}
	public void setListPhoneCodes(List<CodeSysVO> listPhoneCodes) {
		this.listPhoneCodes = listPhoneCodes;
	}
	public String getParamUserStatus() {
		return paramUserStatus;
	}
	public void setParamUserStatus(String paramUserStatus) {
		this.paramUserStatus = paramUserStatus;
	}
	public List<CodeSysVO> getListUserStatusCodes() {
		return listUserStatusCodes;
	}
	public void setListUserStatusCodes(List<CodeSysVO> listUserStatusCodes) {
		this.listUserStatusCodes = listUserStatusCodes;
	}
	public List<MetaAdmVO> getListAdm() {
		return listAdm;
	}
	public void setListAdm(List<MetaAdmVO> listAdm) {
		this.listAdm = listAdm;
	}
	public int getConn_cnt_tot() {
		return conn_cnt_tot;
	}
	public void setConn_cnt_tot(int conn_cnt_tot) {
		this.conn_cnt_tot = conn_cnt_tot;
	}
	public List<LoginLogVO> getChartLoginLog() {
		return chartLoginLog;
	}
	public void setChartLoginLog(List<LoginLogVO> chartLoginLog) {
		this.chartLoginLog = chartLoginLog;
	}
	public List<LoginLogVO> getListLoginLog() {
		return listLoginLog;
	}
	public void setListLoginLog(List<LoginLogVO> listLoginLog) {
		this.listLoginLog = listLoginLog;
	}
	public LoginLogVO getLoginDetail() {
		return loginDetail;
	}
	public void setLoginDetail(LoginLogVO loginDetail) {
		this.loginDetail = loginDetail;
	}
	public String getParamUpdateDate() {
		return paramUpdateDate;
	}
	public void setParamUpdateDate(String paramUpdateDate) {
		this.paramUpdateDate = paramUpdateDate;
	}
	public String getParamUserName() {
		return paramUserName;
	}
	public void setParamUserName(String paramUserName) {
		this.paramUserName = paramUserName;
	}
//	public List<MberManageVO> getListMberManageVO() {
//		return listMberManageVO;
//	}
//	public void setListMberManageVO(List<MberManageVO> listMberManageVO) {
//		this.listMberManageVO = listMberManageVO;
//	}
//	public void setUserSearchVO(UserDefaultVO userSearchVO) {
//		this.userSearchVO = userSearchVO;
//	}
//	public MberManageVO getMberManage() {
//		return mberManage;
//	}
//	public void setMberManage(MberManageVO mberManage) {
//		this.mberManage = mberManage;
//	}
//	public List<CmmnDetailCode> getPasswordHint_result() {
//		return passwordHint_result;
//	}
//	public void setPasswordHint_result(List<CmmnDetailCode> passwordHint_result) {
//		this.passwordHint_result = passwordHint_result;
//	}
//	public List<CmmnDetailCode> getSexdstnCode_result() {
//		return sexdstnCode_result;
//	}
//	public void setSexdstnCode_result(List<CmmnDetailCode> sexdstnCode_result) {
//		this.sexdstnCode_result = sexdstnCode_result;
//	}
//	public List<CmmnDetailCode> getMberSttus_result() {
//		return mberSttus_result;
//	}
//	public void setMberSttus_result(List<CmmnDetailCode> mberSttus_result) {
//		this.mberSttus_result = mberSttus_result;
//	}
//	public List<CmmnDetailCode> getGroupId_result() {
//		return groupId_result;
//	}
//	public void setGroupId_result(List<CmmnDetailCode> groupId_result) {
//		this.groupId_result = groupId_result;
//	}
	public List<RoleVO> getListRoles() {
		return listRoles;
	}
	public void setListRoles(List<RoleVO> listRoles) {
		this.listRoles = listRoles;
	}
	public String getParamUserID() {
		return paramUserID;
	}
	public void setParamUserID(String paramUserID) {
		this.paramUserID = paramUserID;
	}
	public String getParamUserType() {
		return paramUserType;
	}
	public void setParamUserType(String paramUserType) {
		this.paramUserType = paramUserType;
	}
	public List<CodeSysVO> getListUserTypeCodes() {
		return listUserTypeCodes;
	}
	public void setListUserTypeCodes(List<CodeSysVO> listUserTypeCodes) {
		this.listUserTypeCodes = listUserTypeCodes;
	}

	public UserVO getDetail() {
		return detail;
	}
	public void setDetail(UserVO detail) {
		this.detail = detail;
	}
	public List<UserVO> getList() {
		return list;
	}
	public void setList(List<UserVO> list) {
		this.list = list;
	}
	
}
