package com.sinoservices.doppler.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinoservices.components.options.OptionGroup;
import com.sinoservices.components.options.OptionItem;
import com.sinoservices.components.options.OptionsFactory;
import com.sinoservices.components.options.exception.OptionsSourceNotFoundException;
import com.sinoservices.xframework.core.context.utils.ContextUtils;
import com.sinoservices.xframework.core.utils.EmptyUtils;
import com.sinoservices.xframework.core.web.HttpRequestHolder;

/**
 */
@Controller
@RequestMapping("/${module-name}/components/options")
public class OptionsController {


	
	/**
	 * 获取数字字典
	 */
	@RequestMapping(value = "/getOptionsList")
	@ResponseBody
	public OptionItem[] getOptionsListByCfgKey(@RequestParam("type") String type) {
		try {
			OptionsFactory optionsFactory = ContextUtils.getContext(HttpRequestHolder.getRequest()).getBean(OptionsFactory.class);
			OptionGroup group = optionsFactory.getOptions(type);
			if (group == null) {
				return new OptionItem[0]; 
			}
			return group.getItems();
		} catch (OptionsSourceNotFoundException e) {
			return new OptionItem[0]; 
		}
	}
	
	/**
	 * 获取数字字典
	 */
	@RequestMapping(value = "/getOptionsMap")
	@ResponseBody
	public Map<String, Map<Object, Object>> getOptionsMapByCfgKeys(@RequestParam("type") String type) {
		Map<String, Map<Object, Object>> result = new HashMap<String, Map<Object, Object>>();
		if (EmptyUtils.isEmpty(type)) {
			return result;
		}
		
		String[] keys = type.split(",");
		for (int i = 0; i < keys.length; i++) {
			String cfgKey = keys[i];
			try {
				OptionsFactory optionsFactory = ContextUtils.getContext(HttpRequestHolder.getRequest()).getBean(OptionsFactory.class);
				OptionGroup group = optionsFactory.getOptions(cfgKey);
				if (group != null) {
					Map<Object, Object> optionMap = new LinkedHashMap<Object, Object>();
					
					OptionItem[] items = group.getItems();
					for (int j = 0; j < items.length; j++) {
						OptionItem item = items[j];
						optionMap.put(item.getKey(), item.getValue());
					}
					
					result.put(cfgKey, optionMap);
				}
			} catch (OptionsSourceNotFoundException e) {
			}
		}
		
		return result;
	}
//	
//	public OptionsFactory getOptionsFactory() {
//		return optionsFactory;
//	}
//
//	public void setOptionsFactory(OptionsFactory optionsFactory) {
//		this.optionsFactory = optionsFactory;
//	}
}
