package com.noaa.base.admin.viewmodel;

import java.util.List;

import com.noaa.base.base.vo.BaseViewModel;
import com.noaa.base.code.vo.CodeGrpVO;
import com.noaa.base.code.vo.CodeSysVO;

public class AdminCodeViewModel extends BaseViewModel{
	private List<CodeGrpVO> listCodeGrp;
	private List<CodeSysVO> listCodeSys;
	
	private String paramCodeGrp;
	private String paramCodeGrpTxt;
	private String paramSysCode;
	private String paramSysCodeTxt;
	
	
	public String getParamSysCode() {
		return paramSysCode;
	}
	public void setParamSysCode(String paramSysCode) {
		this.paramSysCode = paramSysCode;
	}
	public String getParamSysCodeTxt() {
		return paramSysCodeTxt;
	}
	public void setParamSysCodeTxt(String paramSysCodeTxt) {
		this.paramSysCodeTxt = paramSysCodeTxt;
	}
	public String getParamCodeGrpTxt() {
		return paramCodeGrpTxt;
	}
	public void setParamCodeGrpTxt(String paramCodeGrpTxt) {
		this.paramCodeGrpTxt = paramCodeGrpTxt;
	}
	public String getParamCodeGrp() {
		return paramCodeGrp;
	}
	public void setParamCodeGrp(String paramCodeGrp) {
		this.paramCodeGrp = paramCodeGrp;
	}
	public List<CodeGrpVO> getListCodeGrp() {
		return listCodeGrp;
	}
	public void setListCodeGrp(List<CodeGrpVO> listCodeGrp) {
		this.listCodeGrp = listCodeGrp;
	}
	public List<CodeSysVO> getListCodeSys() {
		return listCodeSys;
	}
	public void setListCodeSys(List<CodeSysVO> listCodeSys) {
		this.listCodeSys = listCodeSys;
	}
	
	
}
