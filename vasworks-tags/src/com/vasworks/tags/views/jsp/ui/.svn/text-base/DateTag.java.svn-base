/**
 * vasworks-tags Feb 18, 2010
 */
package com.vasworks.tags.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.tags.components.Date;

/**
 * @author developer
 * 
 */
public class DateTag extends ComponentTagSupport {

	private static final long serialVersionUID = 5235918740904555531L;
	protected String name;
	protected String format;
	protected String timezone = null;
	protected boolean nice;

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new Date(stack);
	}

	protected void populateParams() {
		super.populateParams();
		Date d = (Date) component;
		d.setName(name);
		d.setFormat(format);
		d.setNice(nice);
		d.setTimezone(timezone);
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setNice(boolean nice) {
		this.nice = nice;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
}
