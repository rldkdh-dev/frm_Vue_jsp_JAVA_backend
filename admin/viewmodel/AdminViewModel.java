package com.noaa.base.admin.viewmodel;

import java.util.List;

import com.noaa.base.account.vo.UserVO;
import com.noaa.base.base.vo.BaseViewModel;
import com.noaa.base.bbs.vo.BBSContentVO;
import com.noaa.base.menu.vo.MenuLogVO;

public class AdminViewModel extends BaseViewModel {
	
	private UserVO detailUser;
	private List<MenuLogVO> listMenuLog;
	private List<MenuLogVO> listVisitMenuLog;
	private List<BBSContentVO> listBbsContent;
	
	public List<BBSContentVO> getListBbsContent() {
		return listBbsContent;
	}

	public void setListBbsContent(List<BBSContentVO> listBbsContent) {
		this.listBbsContent = listBbsContent;
	}

	public List<MenuLogVO> getListVisitMenuLog() {
		return listVisitMenuLog;
	}

	public void setListVisitMenuLog(List<MenuLogVO> listVisitMenuLog) {
		this.listVisitMenuLog = listVisitMenuLog;
	}

	public List<MenuLogVO> getListMenuLog() {
		return listMenuLog;
	}

	public void setListMenuLog(List<MenuLogVO> listMenuLog) {
		this.listMenuLog = listMenuLog;
	}

	public UserVO getDetailUser() {
		return detailUser;
	}

	public void setDetailUser(UserVO detailUser) {
		this.detailUser = detailUser;
	}
	
}
