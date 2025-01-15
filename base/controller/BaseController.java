package com.noaa.base.base.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.noaa.base.AuthorizeException;
import com.noaa.base.common.vo.CommonParamsVO;
import com.noaa.base.global.SysKeyword;

@Controller
public class BaseController {
	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	private String menus;
	private static String fileUploadFolder;
	
	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}
	
	@Autowired
	@Value("#{resourceProp['PATH.upload']}")
	private String path_upload;


	public CommonParamsVO getCommonParams() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session    = request.getSession();
		Object sessionUserID   = session.getAttribute(SysKeyword.SESSION_USER_ID);
		Object sessionUserName = session.getAttribute(SysKeyword.SESSION_USER_NAME);
		Object sessionUserRole = session.getAttribute(SysKeyword.SESSION_USER_ROLE);
		Object sessionAccType  = session.getAttribute(SysKeyword.SESSION_ACC_TYPE);
		Object sessionAccTitle = session.getAttribute(SysKeyword.SESSION_ACC_TITLE);
		
		String userID   = sessionUserID   == null ? null : sessionUserID.toString();
		String userName = sessionUserName == null ? null : sessionUserName.toString();
		String userRole = sessionUserRole == null ? null : sessionUserRole.toString();
		String accType  = sessionAccType  == null ? null : sessionAccType.toString();
		String accTitle = sessionAccTitle == null ? null : sessionAccTitle.toString();
		
		CommonParamsVO p = new CommonParamsVO();
		p.setSessID(session.getId());
		p.setDeviceType(request.getHeader("User-Agent"));
		
		if(request.getHeader("User-Agent").indexOf("Android") > -1 || request.getHeader("User-Agent").indexOf("iPhone") > -1 || request.getHeader("User-Agent").indexOf("iPad") > -1) {
			p.setIsMobilYN("Y");      
		} else {
			p.setIsMobilYN("N");
		}
		p.setRequestIP(request.getRemoteHost());
		p.setRequestDate(new Date());
		p.setLoginUserName(userName);
		p.setLoginUserID(userID);
		p.setUserRole(userRole);
		p.setUserRole(userRole);
		p.setAccType(accType);
		p.setAccTitle(accTitle);
		return p;
	}

	

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest req,	HttpServletResponse response, Exception ex) {
		
	
		ModelAndView model = new ModelAndView("/base/error");

		String contentType = req.getHeader("Content-Type");
		String reason = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
		String exType=ex.getClass().getSimpleName();
		
		int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
	
		logger.error(reason, ex);
		addModelAttr(model,reason,Integer.toString(statusCode),ex.getMessage(),exType);
		
		if (contentType != null && MediaType.APPLICATION_JSON_VALUE.equals(contentType.split(";")[0])) {
			Throwable e = ex.getCause();
			String exMessage= ex.getMessage();
			if (e instanceof AuthorizeException) {
				AuthorizeException target = (AuthorizeException) e;
				if (target.getIsDeny()) {
					exMessage=target.getMessage();
					
				}
				if (target.getIsLogin()) {
					exMessage=target.getMessage();
				}
				reason="Authorize error";
				exType=target.getClass().getSimpleName();
			}
			MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
			model = new ModelAndView(jsonView, "error",exMessage);
			ResponseStatus annotation = ex.getClass().getAnnotation(ResponseStatus.class);

			if (annotation != null) {
				reason = annotation.reason();
				statusCode = annotation.value().value();
			}
			addModelAttr(model,reason,Integer.toString(statusCode),exMessage,exType);
			return model;

		} else {
			Throwable e = ex.getCause();
			if (e instanceof AuthorizeException) {
				AuthorizeException target = (AuthorizeException) e;
				if (target.getIsDeny()) {
					model = new ModelAndView(target.getView());
					
					statusCode = target.getErrorCode();
					addModelAttr(model,"access deny",Integer.toString(statusCode),target.getMessage(),exType);
					response.setStatus(statusCode);
				}
				if (target.getIsLogin()) {
					model = new ModelAndView("redirect:/" + target.getView()+".do");
				}

			}else{
				if (ex instanceof AuthorizeException) {
					AuthorizeException target = (AuthorizeException) ex;
					if (target.getIsDeny()) {
						model = new ModelAndView(target.getView());
						
						statusCode = target.getErrorCode();
						addModelAttr(model,"access deny",Integer.toString(statusCode),target.getMessage(),exType);
						response.setStatus(statusCode);
					}
					if (target.getIsLogin()) {
						model = new ModelAndView("redirect:/" + target.getView()+".do");
					}
				}
				
			}

			return model;
		}

	}
	
	private void addModelAttr(ModelAndView model,String reason,String statusCode,String  message,String exType){
		model.addObject("reason",reason);
		model.addObject("statusCode",statusCode);
		model.addObject("message",message);
		model.addObject("exType",exType);
	}
	
	public String getFileUploadFolder() {
		return fileUploadFolder;
	}
	
	public void setFileUploadFolder(HttpServletRequest req, String page1, String page2) {
		this.fileUploadFolder = req.getSession().getServletContext().getRealPath(path_upload) + page1 + page2;
	}

}
