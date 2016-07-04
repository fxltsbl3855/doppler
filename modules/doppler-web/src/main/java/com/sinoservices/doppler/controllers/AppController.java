package com.sinoservices.doppler.controllers;


import java.util.ArrayList;
import java.util.List;

import com.sinoservices.doppler.exception.RunTaskException;
import com.sinoservices.doppler.facade.TraceFacade;
import com.sinoservices.xframework.core.utils.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinoservices.doppler.bo.AppStatBo;
import com.sinoservices.doppler.bo.DashboardBo;
import com.sinoservices.doppler.facade.AppFacade;
import com.sinoservices.doppler.model.AppStatModel;
import com.sinoservices.doppler.model.DashboardModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
@RequestMapping("/${module-name}/app")
@Scope(value = "prototype")
public class AppController  {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
	@Autowired
	private AppFacade appFacade;
	@Autowired
	private TraceFacade traceFacade;
	
	@RequestMapping(value="/dashboard")
	@ResponseBody
	public DashboardModel dashboard(){
        logger.info("============controller invoked");
        //traceFacade.info(TraceUtil.get("info"));
        DashboardModel dashboardModel = new DashboardModel();
		DashboardBo dashboardBo = null;
		try {
			dashboardBo = appFacade.getDashboard();
		} catch (Exception e) {
			logger.error("Dashboard Exception:" +e);
			dashboardModel.setWebNum(-1);
			dashboardModel.setWebReqNum(-1);
			dashboardModel.setServerNum(-1);
			dashboardModel.setServerReqNum(-1);
			return dashboardModel;
		}
		BeanUtils.copyProperties(dashboardModel, dashboardBo);
		return dashboardModel;
	}
	
	
	@RequestMapping(value="/appDetailList")
	@ResponseBody
	public List<AppStatModel> getAppDetailList(@RequestParam(value = "date") String date){
		logger.info("start appDetailList process...");
		List<AppStatModel> models = new ArrayList<AppStatModel>();
		List<AppStatBo> list = null;
		try {
			list = appFacade.getAppDetailList(date);
		} catch(RunTaskException runTaskException){
			logger.error("RunTaskException: " + runTaskException);
			AppStatModel model = new AppStatModel();
			model.setIsDefineException(false);
			models.add(model);
			return models;
		}catch (Exception e) {
			logger.error("e: " + e);
			AppStatModel model = new AppStatModel();
			model.setIsSystemException(false);
			models.add(model);
			return models;
		}
		for (AppStatBo appStatBo : list) {
			AppStatModel model = new AppStatModel();
			BeanUtils.copyProperties(model, appStatBo);
			models.add(model);
		}
		return models;
	}
}
