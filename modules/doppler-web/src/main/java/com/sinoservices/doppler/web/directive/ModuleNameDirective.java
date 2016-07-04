package com.sinoservices.doppler.web.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sinoservices.xframework.core.context.utils.ContextUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 得到模块名的指令
 * @author Simon
 *
 */
public class ModuleNameDirective extends AbstractDirective {

	public void execute(Environment env, Map map, TemplateModel[] templateModels, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Writer out = env.getOut();
		out.append(getModuleName());
		out.flush();
	}
}
