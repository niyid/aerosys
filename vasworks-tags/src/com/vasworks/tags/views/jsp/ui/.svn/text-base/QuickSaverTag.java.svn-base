package com.vasworks.tags.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.tags.components.QuickSaver;

@SuppressWarnings("serial")
public class QuickSaverTag extends AbstractUITag {
	/// ID of DOM element showing log
	protected String qsaveLog;
	/// Destination action
	protected String action;
	/// Action namespace
	protected String namespace=null;
	/// Query string
	private String queryParams;
	
	private String entityId;
	
	private String entityClass;
	
	private String property;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new QuickSaver(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();

		QuickSaver qSaver = (QuickSaver) this.component;
		qSaver.setProperty(this.property);
		qSaver.setQsaveLog(this.qsaveLog);
		qSaver.setAction(this.action);
		qSaver.setNamespace(this.namespace);
		qSaver.setQueryParams(this.queryParams);
		qSaver.setEntityId(this.entityId);
		qSaver.setEntityClass(this.entityClass);
	}

	/**
	 * @param qsaveLog the qsaveLog to set
	 */
	public void setQsaveLog(String qsaveLog) {
		this.qsaveLog = qsaveLog;
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

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	public void setProperty(String property) {
		this.property = property;
	}
}
