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

/**
 * Text tag renders shortened HTML text -- useful for trimming down longer text to produce better visuals.
 * 
 * @author developer
 * 
 */
@StrutsTag(name = "text", tldTagClass = "com.vasworks.tags.views.jsp.ui.TextTag", description = "Renders shortened HTML text")
public class Text extends UIBean {
	private static final Log LOG = LogFactory.getLog(Text.class);

	/**
	 * Template name
	 */
	private static final String TEMPLATE = "vasworkstext";

	private int maxLength = 100;
	private boolean removeHTML = false;
	private boolean addDots = true;

	/**
	 * @param stack
	 * @param request
	 * @param response
	 */
	public Text(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
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

		LOG.debug("Setting trimmedText");
		if (this.maxLength > 0) {
			this.addParameter("trimmedText", shortenText((String) this.findValue(this.value), this.maxLength));
		} else {
			this.addParameter("trimmedText", this.findValue(this.value));
		}
	}

	/**
	 * @param findValue
	 * @param maxLength2
	 * @return
	 */
	private String shortenText(String text, int maxLength) {
		if (text==null) return "";
		LOG.debug("Shortening to " + maxLength + " characters: " + text);
		return com.vasworks.util.StringUtil.shortenText(text, maxLength, this.removeHTML, this.addDots);		
	}

	/**
	 * @see org.apache.struts2.components.UIBean#getDefaultTemplate()
	 */
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	@StrutsTagAttribute(description = "Sets how much text should be left after trimming", defaultValue = "100")
	public void setMaxLength(int maxLength) {
		LOG.debug("setMaxLength(" + maxLength + ")");
		this.maxLength = maxLength;
	}

	/**
	 * @param removeHTML the removeHTML to set
	 */
	@StrutsTagAttribute(description = "Sets if text should be cleaned all HTML tags before trimming", defaultValue = "false")
	public void setRemoveHTML(boolean removeHTML) {
		this.removeHTML = removeHTML;
	}
	
	/**
	 * @param addDots the addDots to set
	 */
	@StrutsTagAttribute(description = "Sets if three dots should be added", defaultValue = "true")
	public void setAddDots(boolean addDots) {
		this.addDots = addDots;
	}
}
