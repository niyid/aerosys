package com.vasworks.airliner.struts;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class SessionCheckInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(SessionCheckInterceptor.class);

	public void destroy() {
		LOG.info(" SessionCheckInterceptor destroy() is called...");
	}

	public void init() {
		LOG.info(" SessionCheckInterceptor init() is called...");
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		ActionContext context = actionInvocation.getInvocationContext();
		Map<String, Object> sessionMap = context.getSession();
		LOG.info("Retrieved session - " + sessionMap);
		if (sessionMap == null || sessionMap.isEmpty() || sessionMap.get("basket_id") == null) {
			LOG.info("Session expired...");
			sessionMap = null;
			return "sessionExpired";
		}
		String actionResult = actionInvocation.invoke();
		return actionResult;
	}

}