/**
 * 
 */
package com.vasworks.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.vasworks.service.TemplatingException;
import com.vasworks.service.TemplatingService;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;

/**
 * @author developer
 * 
 */
public class FreemarkerTemplateEngine implements TemplatingService {
	private static final Log log = LogFactory.getLog(FreemarkerTemplateEngine.class);

	private String templateDir = "./WEB-INF/template";

	private Configuration config;

	/**
	 * @return the templateDir
	 */
	public String getTemplateDir() {
		return this.templateDir;
	}

	/**
	 * @param templateDir the templateDir to set
	 */
	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.TemplatingService#fillTemplate(java.lang.String, java.util.Dictionary)
	 */
	@Override
	public String fillTemplate(String templateName, Map<String, Object> data) throws TemplatingException {
		if (config == null) {
			config = new Configuration();
			try {
				File path = null;
				if (this.templateDir.startsWith("/"))
					path = new File(this.templateDir);
				else
					path = new File(ServletActionContext.getServletContext().getRealPath(this.templateDir));
				log.info("Template directory: " + path.getAbsolutePath());
				config.setDirectoryForTemplateLoading(path);
			} catch (IOException e1) {
				log.error(e1);
			}
			config.setObjectWrapper(new DefaultObjectWrapper());
			config.setDateFormat("dd/MM/yyyy");
		}

		try {
			freemarker.template.Template template = config.getTemplate(templateName + ".ftl");

			// add StringUtil
			BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
			TemplateHashModel staticModels = wrapper.getStaticModels();

			TemplateHashModel stringUtilStatics = (TemplateHashModel) staticModels.get("com.vasworks.util.StringUtil");
			data.put("StringUtil", stringUtilStatics);

			StringWriter sw = new StringWriter();
			template.process(data, sw);
			sw.flush();
			if (log.isTraceEnabled())
				log.trace(sw.toString());
			return sw.toString();
		} catch (IOException e) {
			log.error("Failed to fill template " + templateName);
			throw new TemplatingException("Failed to fill template " + templateName, e);
		} catch (TemplateException e) {
			log.error("Failed to fill template " + templateName);
			throw new TemplatingException("Failed to fill template " + templateName, e);
		}
	}
}
