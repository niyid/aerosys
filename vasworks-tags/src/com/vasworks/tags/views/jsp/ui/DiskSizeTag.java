package com.vasworks.tags.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.tags.components.DiskSize;

@SuppressWarnings("serial")
public class DiskSizeTag extends AbstractUITag {
	private int blockSize = 1024;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new DiskSize(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();

		DiskSize diskSize = (DiskSize) this.component;
		diskSize.setBlockSize(this.blockSize);
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setBlockSize(int blockSize) {
		this.blockSize=blockSize;
	}
}
