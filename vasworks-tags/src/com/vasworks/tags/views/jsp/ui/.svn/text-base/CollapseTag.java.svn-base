package com.vasworks.tags.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.tags.components.Collapse;

@SuppressWarnings("serial")
public class CollapseTag extends AbstractUITag {
	// / ID of DOM element showing log
	protected String heading;
	// / Destination action
	protected String closedHeading;
	// / Action namespace
	protected boolean collapsed = true;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new Collapse(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();

		Collapse collapse = (Collapse) this.component;
		if (this.heading != null)
			collapse.setHeading(this.heading);
		if (this.closedHeading != null)
			collapse.setClosedHeading(this.closedHeading);
		collapse.setCollapsed(this.collapsed);
	}

	/**
	 * @param heading the heading to set
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	/**
	 * @param closedHeading the closedHeading to set
	 */
	public void setClosedHeading(String closedHeading) {
		this.closedHeading = closedHeading;
	}

	/**
	 * @param collapsed the collapsed to set
	 */
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

}
