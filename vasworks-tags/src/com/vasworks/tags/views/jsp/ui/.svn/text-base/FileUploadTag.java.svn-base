package com.vasworks.tags.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.tags.components.FileUpload;

@SuppressWarnings("serial")
public class FileUploadTag extends AbstractUITag {
	/// ID of DOM element showing log
	protected String uploadLog;
	/// Destination action
	protected String action;
	/// Action namespace
	protected String namespace=null;
	/// Query string
	private String queryParams;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new FileUpload(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();

		FileUpload fileUpload = (FileUpload) this.component;
		fileUpload.setUploadLog(this.uploadLog);
		fileUpload.setAction(this.action);
		fileUpload.setNamespace(this.namespace);
		fileUpload.setQueryParams(this.queryParams);
	}

	/**
	 * @param uploadLog the uploadLog to set
	 */
	public void setUploadLog(String uploadLog) {
		this.uploadLog = uploadLog;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
	/**
	 * @param namespace the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	/**
	 * @param queryParams the queryParams to set
	 */
	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}
}
