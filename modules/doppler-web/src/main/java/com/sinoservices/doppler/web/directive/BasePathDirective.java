package com.sinoservices.doppler.web.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class BasePathDirective implements TemplateDirectiveModel {

	public void execute(Environment env, Map map, TemplateModel[] templateModels, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		System.out.println("22222222222222222222222222222");
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();    
		Writer out = env.getOut();
		out.append(request.getContextPath());
		out.flush();
	}
}
