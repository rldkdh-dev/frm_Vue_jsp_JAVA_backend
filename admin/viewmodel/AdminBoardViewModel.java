package com.noaa.base.admin.viewmodel;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.noaa.base.account.vo.RoleVO;
import com.noaa.base.base.vo.BaseViewModel;
import com.noaa.base.bbs.vo.BBSCategoryVO;
import com.noaa.base.bbs.vo.BBSContentVO;
import com.noaa.base.bbs.vo.BBSMasterVO;
import com.noaa.base.file.vo.AttachFileVO;

public class AdminBoardViewModel extends BaseViewModel {
	private BBSMasterVO detailMaster;
	private List<BBSMasterVO> listMaster;
	private BBSContentVO detailContent;
	private List<BBSContentVO> listContent;
	private List<BBSCategoryVO> listCategory;
	private List<AttachFileVO> listAttachFiles;
	private List<RoleVO> listRoles;
	private String searchTitle;
	private String searchContent;
	private String searchName;
	private String searchStDate;
	private String searchEnDate;
	private String searchCategorySeq;
	private String searchSpellKor;
	private String searchSpellEn;
	private String searchOption;
	private int paramSeq;
	private String[] paramCategorys;
	
	

	public String[] getParamCategorys() {
		return paramCategorys;
	}

	public void setParamCategorys(String[] paramCategorys) {
		this.paramCategorys = paramCategorys;
	}

	public List<RoleVO> getListRoles() {
		return listRoles;
	}

	public void setListRoles(List<RoleVO> listRoles) {
		this.listRoles = listRoles;
	}


	public List<BBSMasterVO> getListMaster() {
		return listMaster;
	}

	public void setListMaster(List<BBSMasterVO> listMaster) {
		this.listMaster = listMaster;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public List<AttachFileVO> getListAttachFiles() {
		return listAttachFiles;
	}

	public void setListAttachFiles(List<AttachFileVO> listAttachFiles) {
		this.listAttachFiles = listAttachFiles;
	}

	public String getSearchSpellKor() {
		return searchSpellKor;
	}

	public void setSearchSpellKor(String searchSpellKor) {
		this.searchSpellKor = searchSpellKor;
	}

	public String getSearchSpellEn() {
		return searchSpellEn;
	}

	public void setSearchSpellEn(String searchSpellEn) {
		this.searchSpellEn = searchSpellEn;
	}

	public int getParamSeq() {
		return paramSeq;
	}

	public void setParamSeq(int paramSeq) {
		this.paramSeq = paramSeq;
	}

	public String getSearchCategorySeq() {
		return searchCategorySeq;
	}

	public void setSearchCategorySeq(String searchCategorySeq) {
		this.searchCategorySeq = searchCategorySeq;
	}

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public String getSearchStDate() {
		return searchStDate;
	}

	public void setSearchStDate(String searchStDate) {
		this.searchStDate = searchStDate;
	}

	public String getSearchEnDate() {
		return searchEnDate;
	}

	public void setSearchEnDate(String searchEnDate) {
		this.searchEnDate = searchEnDate;
	}

	
	public List<BBSCategoryVO> getListCategory() {
		return listCategory;
	}

	public void setListCategory(List<BBSCategoryVO> listCategory) {
		this.listCategory = listCategory;
	}

	

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public List<BBSContentVO> getListContent() {
		return listContent;
	}

	public void setListContent(List<BBSContentVO> listContent) {
		this.listContent = listContent;
	}

	public BBSContentVO getDetailContent() {
		return detailContent;
	}

	public void setDetailContent(BBSContentVO detailContent) {
		this.detailContent = detailContent;
	}

	public BBSMasterVO getDetailMaster() {
		return detailMaster;
	}

	public void setDetailMaster(BBSMasterVO detailMaster) {
		this.detailMaster = detailMaster;
	}
}
