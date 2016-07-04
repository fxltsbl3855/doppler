package com.sinoservices.doppler.controllers;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinoservices.doppler.bo.ServiceBo;
import com.sinoservices.doppler.bo.ServiceStatBo;
import com.sinoservices.doppler.bo.SpanViewBo;
import com.sinoservices.doppler.exception.RunTaskException;
import com.sinoservices.doppler.facade.ServiceFacade;
import com.sinoservices.doppler.model.ServiceStatModel;
import com.sinoservices.doppler.model.SpanViewModel;
import com.sinoservices.xframework.core.utils.BeanUtils;


@Controller
@RequestMapping("/${module-name}/service")
@Scope(value = "prototype")
public class ServiceController  {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
	
	@Autowired
	private ServiceFacade serviceFacade;
	
	@RequestMapping(value="/serviceList")
	@ResponseBody
	public List<ServiceBo> getServiceList(@RequestParam(value = "appId") int appId){
		List<ServiceBo> services = serviceFacade.getServiceList(appId);
		return services;
	}
	
	@RequestMapping(value="/serviceChart")
	@ResponseBody
	public List<SpanViewModel> getServiceChart(@RequestParam(value= "spanViewId") String spanViewId, @RequestParam(value = "date") String date){
		logger.info("start serviceChart process...");
		List<SpanViewModel> models = new ArrayList<SpanViewModel>();
		List<SpanViewBo> spans;
		try {
			spans = serviceFacade.getServiceChart(spanViewId,date);
		} catch(RunTaskException runTaskException){
			logger.error("RunTaskException: " + runTaskException);
			SpanViewModel model = new SpanViewModel();
			model.setIsDefineException(false);
			models.add(model);
			return models;
		}catch (Exception e) {
			logger.error("e: " + e);
			SpanViewModel model = new SpanViewModel();
			model.setIsSystemException(false);
			models.add(model);
			return models;
		}
		for (SpanViewBo spanViewBo : spans) {
			SpanViewModel model = new SpanViewModel();
			BeanUtils.copyProperties(model, spanViewBo);
			models.add(model);
		}
		return models;
	}
	

	@RequestMapping(value="/serviceDetailList")
	@ResponseBody
	public List<ServiceStatModel> getServiceDetailList(@RequestParam(value = "date") String date, @RequestParam(value = "serviceId") String serviceId){
		logger.info("start serviceDetailList process...");
		List<ServiceStatModel> models = new ArrayList<ServiceStatModel>();
		List<ServiceStatBo> list = null;
		try {
			list = serviceFacade.getServiceDetailList(date,serviceId);
		} catch(RunTaskException runTaskException){
			logger.error("RunTaskException: " + runTaskException);
			ServiceStatModel model = new ServiceStatModel();
			model.setIsDefineException(false);
			models.add(model);
			return models;
		}catch (Exception e) {
			logger.error("e: " + e);
			ServiceStatModel model = new ServiceStatModel();
			model.setIsSystemException(false);
			models.add(model);
			return models;
		}
		for (ServiceStatBo serviceStatBo : list) {
			ServiceStatModel model = new ServiceStatModel();
			BeanUtils.copyProperties(model, serviceStatBo);
			models.add(model);
		}
		return models;
	}
	
}
