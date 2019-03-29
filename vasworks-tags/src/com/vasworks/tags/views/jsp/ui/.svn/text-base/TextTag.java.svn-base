package com.vasworks.tags.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.tags.components.Text;

@SuppressWarnings("serial")
public class TextTag extends AbstractUITag {
	private int maxLength = 100;
	private boolean removeHTML;
	private boolean addDots = true;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new Text(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();

		Text text = (Text) this.component;
		text.setMaxLength(this.maxLength);
		text.setRemoveHTML(this.removeHTML);
		text.setAddDots(this.addDots);
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * @param removeHTML the removeHTML to set
	 */
	public void setRemoveHTML(boolean removeHTML) {
		this.removeHTML = removeHTML;
	}

	/**
	 * @param addDots the addDots to set
	 */
	public void setAddDots(boolean addDots) {
		this.addDots = addDots;
	}
}
