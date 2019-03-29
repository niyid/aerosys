/**
 * vasworks-common-web.struts Nov 2, 2009
 */
package com.vasworks.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.vasworks.jsp.ClickFlowFilter;

/**
 * @author developer
 * 
 */
@SuppressWarnings("serial")
public class GoBackResult implements Result {
	protected static final Log LOG = LogFactory.getLog(GoBackResult.class);

	/**
	 * @see com.opensymphony.xwork2.Result#execute(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		HttpServletResponse response = (HttpServletResponse) ac.get(ServletActionContext.HTTP_RESPONSE);
		HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);

		String referer = (String) request.getAttribute(ClickFlowFilter.CLICKFLOW_REQUEST_ATTR2);
		
		if (referer == null) {
			// get more top one if possible
			referer = (String) request.getAttribute(ClickFlowFilter.CLICKFLOW_REQUEST_ATTR);
		}
		
		if (referer!=null) {
			// redirect to referer?
			LOG.error("Referer is null! What now?!");			
		} 
		
		if (referer!=null) {
			LOG.info("Redirecting to " + referer);
			response.reset();
			response.sendRedirect(referer);
		}		
	}

}
