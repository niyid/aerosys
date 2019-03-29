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
 * Autocompleter tag
 * 
 * @author developer
 * 
 */
@StrutsTag(name = "autocompleter", tldTagClass = "com.vasworks.tags.views.jsp.ui.AutocompleterTag", description = "Renders autocompleter")
public class Autocompleter extends UIBean {
	private static final Log LOG = LogFactory.getLog(Autocompleter.class);

	/**
	 * Template name
	 */
	private static final String TEMPLATE = "autocompleter";

	/**
	 * Autocompleter mode can be set to <code>json</code> or <code>html</code>. If JSON is used, then response received from remote method needs to be a JSON
	 * array of objects.
	 */
	private String mode = "json";

	/**
	 * URL of AJAX method
	 */
	private String url = null;

	private String listKey = "id";
	private String listValue = "name";
	private String displayValue = null;

	private String method;

	private String submitTextTo;

	/**
	 * @param stack
	 * @param request
	 * @param response
	 */
	public Autocompleter(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	/**
	 * @see org.apache.struts2.components.UIBean#evaluateParams()
	 */
	@Override
	public void evaluateParams() {
		super.evaluateParams();

		// now evaluate our params!
		if (this.value != null) {
			LOG.debug("Value: '" + this.value + "'");
			this.addParameter("value", this.findValue(this.value));
			LOG.debug("-->: " + this.getParameters().get("value"));
		}
		if (this.mode != null)
			this.addParameter("mode", this.findString(this.mode));
		else
			this.addParameter("mode", "json");

		if (this.url != null)
			this.addParameter("url", this.findString(this.url));

		if (this.listKey != null)
			this.addParameter("listKey", this.findString(this.listKey));

		if (this.listValue != null)
			this.addParameter("listValue", this.findString(this.listValue));

		if (this.displayValue != null)
			this.addParameter("displayValue", this.findString(this.displayValue));

		if (this.method != null)
			this.addParameter("method", this.findString(this.method));
		
		if (this.submitTextTo!=null) 
			this.addParameter("submitTextTo", this.submitTextTo);
	}

	/**
	 * @see org.apache.struts2.components.UIBean#getDefaultTemplate()
	 */
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	@StrutsTagAttribute(description = "Sets how the autocompleter treats AJAX response, should be one of 'json', 'html'", defaultValue = "json")
	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	@StrutsTagAttribute(description = "Used as HTML id attribute", required = false)
	public void setId(String id) {
		super.setId(StringUtil.sanitizeForJavascript(id));
	}

	@Override
	@StrutsTagAttribute(description = "Name of the field that will contain the value", required = true)
	public void setName(String name) {
		super.setName(name);
	}

	/**
	 * @param url the url to set
	 */
	@StrutsTagAttribute(description = "Url of AJAX method to provide the results. Method needs to accept one String argument (whatever user typed in)", required = true)
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param listKey the listKey to set
	 */
	@StrutsTagAttribute(description = "Object property that should be used as key", defaultValue = "id")
	public void setListKey(String listKey) {
		this.listKey = listKey;
	}

	/**
	 * @param listValue the listValue to set
	 */
	@StrutsTagAttribute(description = "Object property that should be used as text value", defaultValue = "name")
	public void setListValue(String listValue) {
		this.listValue = listValue;
	}

	/**
	 * @param displayValue the displayValue to set
	 */
	@StrutsTagAttribute(description = "Text value displayed in autocompleter")
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	/**
	 * @param method
	 */
	@StrutsTagAttribute(description = "SMD method to invoke")
	public void setMethod(String method) {
		this.method = method;
	}
	
	/**
	 * @param submitTextTo the submitTextTo to set
	 */
	@StrutsTagAttribute(description = "Submit text of autocompleter (off by default)")
	public void setSubmitTextTo(String submitTextTo) {
		if (submitTextTo==null || submitTextTo.trim().length()==0)
			submitTextTo=null;
		this.submitTextTo = submitTextTo;
	}
}
