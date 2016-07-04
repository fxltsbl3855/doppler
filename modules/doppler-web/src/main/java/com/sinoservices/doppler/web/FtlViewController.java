package com.sinoservices.doppler.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author Ekay.Wei
 * <pre>
 * 版本号			修改人	修改说明
 * 20150411.1   	Simon	修改规则，URL仅去contextPath,.shtml。解决访问view资源出现两次moduleName
 * </pre>
 */
@Controller
@RequestMapping("/${module-name}")
public class FtlViewController  {
	
	/**
	 * Ftl模板访问的统一处理
	 */
	@RequestMapping(value = "/view/**")
	public @ResponseBody ModelAndView forward2Ftl(HttpServletRequest request) {
		String url = request.getRequestURI();
		String ftlUrl = url.replaceFirst(request.getContextPath(),"").replaceFirst("\\.shtml$", "");
		return new ModelAndView(ftlUrl);
	}
}
