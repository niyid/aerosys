package com.vasworks.tags.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.tags.components.Autocompleter;

@SuppressWarnings("serial")
public class AutocompleterTag extends AbstractUITag {
	protected String mode;
	protected String url;
	protected String listKey;
	protected String listValue;
	protected String displayValue;
	protected String method;
	private String submitTextTo;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new Autocompleter(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();

		Autocompleter autocompleter = (Autocompleter) this.component;
		autocompleter.setMode(this.mode);
		autocompleter.setUrl(this.url);
		autocompleter.setListKey(this.listKey);
		autocompleter.setListValue(this.listValue);
		autocompleter.setDisplayValue(this.displayValue);
		autocompleter.setMethod(this.method);
		autocompleter.setSubmitTextTo(this.submitTextTo);
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setListKey(String listKey) {
		this.listKey = listKey;
	}

	public void setListValue(String listValue) {
		this.listValue = listValue;
	}
	
	/**
	 * @param displayValue the displayValue to set
	 */
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	
	/**
	 * @param submitTextTo the submitTextTo to set
	 */
	public void setSubmitTextTo(String submitTextTo) {
		this.submitTextTo = submitTextTo;
	}
}
