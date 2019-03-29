/**
 * vasworks-tags Jun 5, 2009
 */
package com.vasworks.tags.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.util.StringUtil;

@StrutsTag(name = "quicksaver", tldTagClass = "com.vasworks.tags.views.jsp.ui.QuickSaverTag", description = "Renders quick-saver tag")
public class QuickSaver extends UIBean {
	public static final String TEMPLATE = "quicksaver";
	final protected static Log LOG = LogFactory.getLog(QuickSaver.class);
	protected String name = "quicksaver";
	// / ID of DOM element showing log
	protected String qsaveLog;
	// / Action name
	protected String action;
	// / Action namespace
	protected String namespace;
	
	private String queryParams;
	
	private String entityClass;
	
	private String entityId;
	
	private String property;

	public QuickSaver(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	@Override
	public void evaluateParams() {
		super.evaluateParams();

		String id = super.getId();
		LOG.debug("ID=" + id);

		if (id == null || id.length() == 0) {
			id = getHTMLID(this.namespace, this.action, this.name);
			LOG.debug("ID for quick-saver not there. Using: " + id);
			this.addParameter("id", id);
		} else {
			this.addParameter("id", super.getId());
		}
		
		if (this.property != null) {
			this.addParameter("property", this.property);
		}

		if (this.value != null) {
			Object val = this.findValue(this.value);
			
			if(value != null && !org.springframework.util.StringUtils.hasText(String.valueOf(val))) {
				this.addParameter("value", val);
			}
		}

		if (this.name != null) {
			this.addParameter("name", this.name);
		}

		if (this.qsaveLog != null) {
			this.addParameter("qsaveLog", this.qsaveLog);
		}

		if (this.entityId != null) {
			this.addParameter("entityId", this.findValue(this.entityId));
		}

		if (this.entityClass != null) {
			this.addParameter("entityClass", this.entityClass);
		}

		HttpServletRequest req = ServletActionContext.getRequest();

		if (this.action != null) {
			HttpServletResponse res = ServletActionContext.getResponse();
			String dest = super.determineActionURL(this.action, this.namespace, null, req, res, null, null, true, true, false, false);
			LOG.debug("DEST = " + dest);
			LOG.debug("findString: " + findString(this.action));
			this.addParameter("destination", dest);
		}

		if (this.queryParams != null) {
			LOG.debug("QP: " + this.queryParams);
			LOG.debug("Evaluating  QP: " + super.findValue(this.queryParams));
			this.addParameter("queryParams", this.queryParams);
		}

		String root = req.getContextPath();
		LOG.debug("ROOT = " + root);
		this.addParameter("root", root);
	}

	/**
	 * @param name
	 * @param string2
	 * @param string
	 * @return
	 */
	private String getHTMLID(String namespace, String action, String name) {
		return ((namespace == null ? "" : namespace) + (action == null ? "" : action) + name).replaceAll("[^0-9a-zA-Z]+", "_");
	}

	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	@Override
	@StrutsTagAttribute(description = "Used as HTML id attribute", required = false)
	public void setId(String id) {
		super.setId(StringUtil.sanitizeForJavascript(id));
	}

	/**
	 * @see org.apache.struts2.components.UIBean#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	@StrutsTagAttribute(description = "Name of the text-area field that contains the text to be edited", required = false)
	public void setName(String name) {
		if (name != null && name.trim().length() > 0) {
			this.name = name;
			super.setName(name);
		}
	}

	@StrutsTagAttribute(description = "ID of element that will contain the quick-save log", required = true)
	public void setQsaveLog(String qsaveLog) {
		this.qsaveLog = qsaveLog;
	}

	/**
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @param namespace2
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * @param queryParams
	 */
	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	public void setEntityClass(String typeId) {
		this.entityClass = typeId;
	}

	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

}
