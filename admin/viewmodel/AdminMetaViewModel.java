package com.noaa.base.admin.viewmodel;

import java.util.List;

import com.noaa.base.base.vo.BaseViewModel;
import com.noaa.base.meta.vo.MetaAdmVO;
import com.noaa.base.meta.vo.MetaVO;

public class AdminMetaViewModel extends BaseViewModel {
	
	private List<MetaVO> listMeta;	
	private List<MetaAdmVO> listMetaAdm;
	
	private String paramMetaId;
	private int resultCount;

	public List<MetaVO> getListMeta() {
		return listMeta;
	}

	public void setListMeta(List<MetaVO> listMeta) {
		this.listMeta = listMeta;
	}

	public String getParamMetaId() {
		return paramMetaId;
	}

	public void setParamMetaId(String paramMetaId) {
		this.paramMetaId = paramMetaId;
	}

	public List<MetaAdmVO> getListMetaAdm() {
		return listMetaAdm;
	}

	public void setListMetaAdm(List<MetaAdmVO> listMetaAdm) {
		this.listMetaAdm = listMetaAdm;
	}

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	
	
	
	
	

}
