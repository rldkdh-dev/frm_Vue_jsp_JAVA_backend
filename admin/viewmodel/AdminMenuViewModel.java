package com.noaa.base.admin.viewmodel;

import java.util.List;

import com.noaa.base.account.vo.RoleVO;
import com.noaa.base.base.vo.BaseViewModel;
import com.noaa.base.menu.vo.MenuVO;
import com.noaa.base.menu.vo.MenuLogVO;
import com.noaa.base.menu.vo.MenuRoleVO;

public class AdminMenuViewModel extends BaseViewModel {
	private List<MenuVO> listMenu;
	private MenuVO detailMenu;
	private List<RoleVO> listRole;
	private List<MenuLogVO> listMenuLog;
	
	private String paramMenuID;
	private String paramRoles;
	private String paramWriteRoles;
	private String role_id;
	private String menu_use_type;
	private String menu_code;
	private int paramDurMils;
	

	public String getParamWriteRoles() {
		return paramWriteRoles;
	}
	public void setParamWriteRoles(String paramWriteRoles) {
		this.paramWriteRoles = paramWriteRoles;
	}
	public List<MenuLogVO> getListMenuLog() {
		return listMenuLog;
	}
	public void setListMenuLog(List<MenuLogVO> listMenuLog) {
		this.listMenuLog = listMenuLog;
	}
	public int getParamDurMils() {
		return paramDurMils;
	}
	public void setParamDurMils(int paramDurMils) {
		this.paramDurMils = paramDurMils;
	}
	public String getParamRoles() {
		return paramRoles;
	}
	public void setParamRoles(String paramRoles) {
		this.paramRoles = paramRoles;
	}
	public String getParamMenuID() {
		return paramMenuID;
	}
	public void setParamMenuID(String paramMenuID) {
		this.paramMenuID = paramMenuID;
	}
	public List<RoleVO> getListRole() {
		return listRole;
	}
	public void setListRole(List<RoleVO> listRole) {
		this.listRole = listRole;
	}
	public List<MenuVO> getListMenu() {
		return listMenu;
	}
	public void setListMenu(List<MenuVO> listMenu) {
		this.listMenu = listMenu;
	}
	public MenuVO getDetailMenu() {
		return detailMenu;
	}
	public void setDetailMenu(MenuVO detailMenu) {
		this.detailMenu = detailMenu;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getMenu_use_type() {
		return menu_use_type;
	}
	public void setMenu_use_type(String menu_use_type) {
		this.menu_use_type = menu_use_type;
	}
	public String getMenu_code() {
		return menu_code;
	}
	public void setMenu_code(String menu_code) {
		this.menu_code = menu_code;
	}
	
	
	
}
