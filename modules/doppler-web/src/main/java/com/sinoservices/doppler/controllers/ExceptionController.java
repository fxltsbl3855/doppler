package com.sinoservices.doppler.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinoservices.doppler.bo.DisplayItemBo;
import com.sinoservices.doppler.bo.DisplayServiceBo;
import com.sinoservices.doppler.bo.ReqDetailBo;
import com.sinoservices.doppler.bo.RequestBo;
import com.sinoservices.doppler.bo.ServiceBo;
import com.sinoservices.doppler.exception.RunTaskException;
import com.sinoservices.doppler.facade.RequestFacade;
import com.sinoservices.doppler.model.ReqDetailModel;
import com.sinoservices.doppler.model.RequestModel;
import com.sinoservices.xframework.core.utils.BeanUtils;

@Controller
@RequestMapping("/${module-name}/exception")
@Scope(value = "prototype")
public class ExceptionController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	 
	@Autowired
	private RequestFacade requestFacade;

	@RequestMapping(value = "/displayItems")
	@ResponseBody
	public List<ServiceBo> getDisplayItems(@RequestParam(value = "itemId") int itemId){
		DisplayItemBo  displayItemBo  = requestFacade.getDisplayItem();
		Map<Integer,String> map = new HashMap<Integer,String>();
		if(itemId == 1){
			map = displayItemBo.getAppMap();
		}else{
			map = displayItemBo.getErrorLevelMap();
		}
		
		List<ServiceBo> list = new ArrayList<ServiceBo>();
		Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			ServiceBo serviceBo = new ServiceBo();
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			serviceBo.setServiceId(String.valueOf(entry.getKey()));
			serviceBo.setName(String.valueOf(entry.getValue()));
			list.add(serviceBo);
		  }
		return list;
	}
	
	@RequestMapping(value = "/serviceItems")
	@ResponseBody
	public List<ServiceBo> getServiceItems(@RequestParam(value = "appId") int appId){
		DisplayServiceBo displayServiceBo = requestFacade.getServicesByAppId(appId);
		Map<Integer,String> map = displayServiceBo.getServiceMap();
		
		List<ServiceBo> list = new ArrayList<ServiceBo>();
		Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			ServiceBo serviceBo = new ServiceBo();
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			serviceBo.setServiceId(String.valueOf(entry.getKey()));
			serviceBo.setName(String.valueOf(entry.getValue()));
			list.add(serviceBo);
		  }
		return list;
	}
	
	@RequestMapping(value = "/exceptionList")
	@ResponseBody
	public List<RequestModel> getExceptionList(
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
			@RequestParam(value = "appId") int appId,
			@RequestParam(value = "serviceId") int serviceId,
			@RequestParam(value = "errorLevel") int errorLevel,
			@RequestParam(value = "addr") String addr,
			@RequestParam(value = "key") String key) {
		logger.info("start exceptionList process...");
		List<RequestModel> models = new ArrayList<RequestModel>();
		List<RequestBo> list = null;
		try {
			list = requestFacade.getReqInfo(startDate, endDate, appId, serviceId, errorLevel, addr, key);
		}catch(RunTaskException runTaskException){
			logger.error("RunTaskException: " + runTaskException);
			RequestModel model = new RequestModel();
			model.setIsDefineException(false);
			models.add(model);
			return models;
		}catch (Exception e) {
			logger.error("e: " + e);
			RequestModel model = new RequestModel();
			model.setIsSystemException(false);
			models.add(model);
			return models;
		}
		for (RequestBo requestBo : list) {
			RequestModel model = new RequestModel();
			BeanUtils.copyProperties(model, requestBo);
			models.add(model);
		}
		return models;
	}
	
	@RequestMapping(value = "/exceptionDetail")
	@ResponseBody
	public ReqDetailModel getExceptionDetail(
			@RequestParam(value = "id") String id) {
		logger.info("start exceptionDetail process...");
		ReqDetailModel model = new ReqDetailModel();
		ReqDetailBo reqDetailBo = null;
		try {
			reqDetailBo = requestFacade.getAppDetailById(Integer.parseInt(id));
		} catch(RunTaskException runTaskException){
			logger.error("RunTaskException: " + runTaskException);
			model.setIsDefineException(false);
			return model;
		}catch (Exception e) {
			logger.error("e: " + e);
			model.setIsSystemException(false);
			return model;
		}
		
		if("null".equals(reqDetailBo.getErrorAddr())){
			reqDetailBo.setErrorAddr(null);
		}
		if("null".equals(reqDetailBo.getErrorInfo())){
			reqDetailBo.setErrorInfo(null);
		}
		if("null".equals(reqDetailBo.getErrorTime())){
			reqDetailBo.setErrorTime(null);
		}
		if("null".equals(reqDetailBo.getSourceAppName())){
			reqDetailBo.setSourceAppName(null);
		}
		if("null".equals(reqDetailBo.getSourceAddrName())){
			reqDetailBo.setSourceAddrName(null);
		}
		if("null".equals(reqDetailBo.getSourceServiceName())){
			reqDetailBo.setSourceServiceName(null);
		}
		if("null".equals(reqDetailBo.getTargetAddrName())){
			reqDetailBo.setTargetAddrName(null);
		}
		if("null".equals(reqDetailBo.getTargetAppName())){
			reqDetailBo.setTargetAppName(null);
		}
		if("null".equals(reqDetailBo.getTargetServiceName())){
			reqDetailBo.setTargetServiceName(null);
		}
		
		BeanUtils.copyProperties(model, reqDetailBo);
		return model;
	}
}
