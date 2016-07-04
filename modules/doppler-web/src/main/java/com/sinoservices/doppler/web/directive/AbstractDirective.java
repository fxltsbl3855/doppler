package com.sinoservices.doppler.web.directive;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


import freemarker.template.SimpleSequence;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateScalarModel;
import freemarker.template.TemplateSequenceModel;
import freemarker.template.utility.StringUtil;

/**
 * 抽象的Freemarker指令
 *
 */
public abstract class AbstractDirective implements TemplateDirectiveModel,ApplicationContextAware{
	
	protected ApplicationContext applicationContext = null;


	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.applicationContext = ctx;
	}
	
	protected String getModuleName(){
		return this.applicationContext.getId();
	}
	
	protected <T> T getBean(Class<T> clazz){
		return applicationContext.getBean(clazz);
	}
	
	
	/**
	 * @param map
	 * @param key
	 * @return
	 * @throws TemplateException
	 */
	protected String getStringParam(Map map,String key) throws TemplateException{
		return getStringParam(map, key, null);
	}
	
	/**
	 * @param map
	 * @param key
	 * @param defaultVal
	 * @return
	 * @throws TemplateException
	 */
	protected Boolean getBoolParam(Map map,String key,Boolean defaultVal) throws TemplateException{
		String val = getStringParam(map, key, defaultVal.toString());
		if(val!=null){
			return StringUtil.getYesNo(val);
		}
		return defaultVal; 
	}
	/**
	 * @param map
	 * @param key
	 * @param defaultVal
	 * @return
	 * @throws TemplateException
	 */
	protected String getStringParam(Map map,String key,String defaultVal) throws TemplateException{
		Object val = map.get(key);
		if(val!=null){
			if(val instanceof TemplateScalarModel){
				return ((TemplateScalarModel)val).getAsString();
			}else{
				return val.toString();
			}
		}
		return defaultVal; 
	}
	
	protected void checkRequiredParams(Map map,String... keys){
		if(keys!=null&&keys.length>0){
			for (String key : keys) {
				Object val = map.get(key);
				if(val==null||val.toString().length()<1){
					//throw new ParamerNotFoundException(key);
				}
			}
		}
	}
	/**
	 * 将Map值对连成一个个字符串
	 * @param map
	 * @param ignoreNull
	 * @return
	 */
	protected String joinMap(Map map,boolean ignoreNull,String... ignoreKeys){
		StringBuilder buf = new StringBuilder();
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry entry = (Entry) iterator.next();
			//先判断这些Key是否需要忽略，再判断值是否需要忽略，最后输出
			if(containsItem(ignoreKeys,entry.getKey())==false){
				if(entry.getValue()!=null || ignoreNull){
					buf.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\" ");
				}
			}
		}
		return buf.toString();
	}
	
	/**
	 * 判断组织是否包含在某个Key
	 * @param array
	 * @param key
	 * @return
	 */
	private boolean containsItem(String[] array,Object key){
		if(array == null || array.length<1){
			return false;
		}
		for (String str : array) {
			if(key.equals(str)){
				return true;
			}
		}
		return false;
	}
}
