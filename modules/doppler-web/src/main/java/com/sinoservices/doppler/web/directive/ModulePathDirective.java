package com.sinoservices.doppler.web.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 得到模块路径的指令
 * @author Simon
 *
 */
public class ModulePathDirective extends AbstractDirective {

	public void execute(Environment env, Map map, TemplateModel[] templateModels, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();    
		Writer out = env.getOut();
		out.append(request.getContextPath()+"/"+applicationContext.getId());
		out.flush();
	}
}
