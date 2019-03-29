/**
 * vasworks-tags Jun 8, 2009
 */
package com.vasworks.tags.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.util.StringUtil;

/**
 * Text tag renders shortened HTML text -- useful for trimming down longer text to produce better visuals.
 * 
 * @author developer
 * 
 */
@StrutsTag(name = "text", tldTagClass = "com.vasworks.tags.views.jsp.ui.DiskSizeTag", description = "Renders disk size in human readable format")
public class DiskSize extends UIBean {
	private static final Log LOG = LogFactory.getLog(DiskSize.class);

	/**
	 * Template name
	 */
	private static final String TEMPLATE = "disksize";

	private int blockSize = 1024;

	/**
	 * @param stack
	 * @param request
	 * @param response
	 */
	public DiskSize(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	/**
	 * @see org.apache.struts2.components.UIBean#evaluateParams()
	 */
	@Override
	public void evaluateParams() {
		LOG.debug("Calling super.evaluateParams()");
		super.evaluateParams();

		// now evaluate our params!
		if (this.value != null) {
			LOG.debug("Value: '" + this.value + "'");
			this.addParameter("value", this.findValue(this.value));
			LOG.debug("-->: " + this.getParameters().get("value"));
		}

		LOG.debug("Setting human readable value");
		if (this.value != null) {
			Object val=this.findValue(this.value);
			if (val instanceof Long)
				this.addParameter("humanReadable", StringUtil.toHumanReadableBytes(((Long) val).doubleValue(), this.blockSize));
			else if (val instanceof Integer)
				this.addParameter("humanReadable", StringUtil.toHumanReadableBytes(((Integer) val).doubleValue(), this.blockSize));
			else if (val instanceof Double)
				this.addParameter("humanReadable", StringUtil.toHumanReadableBytes((Double) val, this.blockSize));
			else if (val instanceof Float)
				this.addParameter("humanReadable", StringUtil.toHumanReadableBytes(((Float) val).doubleValue(), this.blockSize));
			else if (val instanceof Short)
				this.addParameter("humanReadable", StringUtil.toHumanReadableBytes(((Short) val).doubleValue(), this.blockSize));
		} 
	}

	/**
	 * @see org.apache.struts2.components.UIBean#getDefaultTemplate()
	 */
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	@StrutsTagAttribute(description = "Set the block size, defaults to 1024", defaultValue = "1024")
	public void setBlockSize(int blockSize) {
		LOG.debug("setMaxLength(" + blockSize + ")");
		this.blockSize = blockSize;
	}
}
