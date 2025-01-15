package com.noaa.base.admin.viewmodel;

import java.util.List;

import com.noaa.base.base.vo.BaseViewModel;
import com.noaa.base.file.vo.DBInfoVO;

public class SystemViewModel extends BaseViewModel {
	private List<DBInfoVO> dbList;

	public List<DBInfoVO> getDbList() {
		return dbList;
	}

	public void setDbList(List<DBInfoVO> dbList) {
		this.dbList = dbList;
	}
	
	

}
