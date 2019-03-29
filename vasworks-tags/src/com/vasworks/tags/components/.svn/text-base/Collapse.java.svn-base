/**
 * vasworks-tags Jun 5, 2009
 */
package com.vasworks.tags.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.util.StringUtil;

@StrutsTag(name = "collapse", tldTagClass = "com.vasworks.tags.views.jsp.ui.CollapseTag", description = "Collapse element with header")
public class Collapse extends ClosingUIBean {
	public static final String TEMPLATE = "collapse-close";
	public static final String OPENTEMPLATE = "collapse";
	final protected static Log LOG = LogFactory.getLog(Collapse.class);

	// Heading text rendered when closed
	protected String closedHeading = "Show details...";
	// Heading text rendered when open
	protected String heading = "Hide...";
	// Collapsed? Otherwise expanded
	protected boolean collapsed = true;

	public Collapse(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	@Override
	public void evaluateParams() {
		super.evaluateParams();

		this.addParameter("id", getId());
	
		if (this.closedHeading != null) {
			this.addParameter("closedHeading", this.closedHeading);
		}

		if (this.heading != null) {
			this.addParameter("heading", this.heading);
		}

		this.addParameter("collapsed", this.collapsed);
		this.addParameter("cssStyle", this.cssStyle);
		this.addParameter("cssClass", this.cssClass);
	}

	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	/**
	 * @see org.apache.struts2.components.ClosingUIBean#getDefaultOpenTemplate()
	 */
	@Override
	public String getDefaultOpenTemplate() {
		return OPENTEMPLATE;
	}

	@Override
	@StrutsTagAttribute(description = "Used as HTML id attribute", required = false)
	public void setId(String id) {
		super.setId(StringUtil.sanitizeForJavascript(id));
	}

	/**
	 * @param closedHeading the closedHeading to set
	 */
	public void setClosedHeading(String closedHeading) {
		this.closedHeading = closedHeading;
	}

	/**
	 * @param heading the heading to set
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	/**
	 * @param collapsed the collapsed to set
	 */
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
}
