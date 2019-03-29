package com.vasworks.struts;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;

import com.vasworks.util.DateUtil;
import com.vasworks.util.ReflectionUtil;

public class CompositeIdConverter extends StrutsTypeConverter {
	private static final Log LOG = LogFactory.getLog(CompositeIdConverter.class);
	
	@SuppressWarnings("rawtypes")
	public Object convertFromString(Map ctx, String[] value, Class arg2) {
		
		Object val = null;
		
		LOG.debug("Target Id: " + value[0]);
		
		if (!(value[0] == null || value[0].trim().equals(""))) {
			try {				
				String[] params = value[0].split("__", -1);
				
				LOG.debug("Token Count: " + params.length);
				
				try {
					Class<?> idClass = Class.forName(params[0]);
					
					val = idClass.newInstance();
					String[] nameValuePair = null;
					for(int i = 1; i < params.length; i++) {
						nameValuePair = params[i].split(";", -1);
						Class fieldClass = ReflectionUtil.REFLECTION_UTILS.getFieldType(idClass, nameValuePair[0]);
						if(fieldClass.getName().equals("java.util.Date")) {
							Date dateVal = nameValuePair[1] != null ? DateUtil.convertStringToDate(nameValuePair[1], Calendar.getInstance().getTimeZone()) : null;
							ReflectionUtil.REFLECTION_UTILS.setFieldValue(val, nameValuePair[0], dateVal, true);
						} else {
							ReflectionUtil.REFLECTION_UTILS.setFieldValue(val, nameValuePair[0], nameValuePair[1], true);
						}
					}				
				} catch (ClassNotFoundException e1) {
					LOG.error("Invalid class-name: " + params[0]);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		LOG.debug("Parsed Id: " + val);
		
		return val;
	}

	@SuppressWarnings("rawtypes")
	public String convertToString(Map ctx, Object data) {
		return (data != null) ? data.toString() : null;
	}
}