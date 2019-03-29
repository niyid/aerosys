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

@StrutsTag(name = "fileupload", tldTagClass = "com.vasworks.tags.views.jsp.ui.FileUploadTag", description = "Renders file uploader")
public class FileUpload extends UIBean {
	public static final String TEMPLATE = "fileupload";
	final protected static Log LOG = LogFactory.getLog(FileUpload.class);

	// / Button title
	protected String value = "Upload files ...";
	// / HTTP post name
	protected String name = "uploads";
	// / ID of DOM element showing log
	protected String uploadLog;
	// / Action name
	protected String action;
	// / Action namespace
	protected String namespace;
	private String queryParams;

	public FileUpload(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	@Override
	public void evaluateParams() {
		super.evaluateParams();

		String id = super.getId();
		LOG.debug("ID=" + id);

		if (id == null || id.length() == 0) {
			id = getHTMLID(this.namespace, this.action, this.name);
			LOG.debug("ID for fileuploader not there. Using: " + id);
			this.addParameter("id", id);
		} else {
			this.addParameter("id", super.getId());
		}

		if (this.value != null) {
			this.addParameter("value", this.value);
		}

		if (this.name != null) {
			this.addParameter("name", this.name);
		}

		if (this.uploadLog != null) {
			this.addParameter("uploadLog", this.uploadLog);
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
	@StrutsTagAttribute(description = "Name of the form upload field that will contain the file", required = false)
	public void setName(String name) {
		if (name != null && name.trim().length() > 0) {
			this.name = name;
			super.setName(name);
		}
	}

	@StrutsTagAttribute(description = "ID of element that will contain the upload log", required = true)
	public void setUploadLog(String uploadLog) {
		this.uploadLog = uploadLog;
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

}
