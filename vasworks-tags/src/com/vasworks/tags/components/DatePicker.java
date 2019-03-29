/**
 * vasworks-tags Jun 5, 2009
 */
package com.vasworks.tags.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.vasworks.util.StringUtil;

@StrutsTag(name = "datepicker", tldTagClass = "com.vasworks.tags.views.jsp.ui.DatePickerTag", description = "Renders datepicker")
public class DatePicker extends UIBean {
	public static final String TEMPLATE = "datepicker";
	final protected static Log LOG = LogFactory.getLog(DatePicker.class);
	final protected static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	final protected static DateFormat PAGE_DATE_FORMAT = new SimpleDateFormat("MM/yyyy");
	final private static SimpleDateFormat RFC3339_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	final private static SimpleDateFormat RFC3339_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	protected String startDate;
	protected String endDate;
	protected String mode;
	protected String autoClose;
	protected String iconPath;
	protected String iconCssClass;
	protected String formatFunction;
	protected String language;
	protected String format = "dd/MM/yyyy";

	public DatePicker(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	@Override
	public void evaluateParams() {
		super.evaluateParams();

		if (getId()==null) {
			String id=getHTMLID(this.name);
			LOG.debug("ID for datepicker not there. Using: " + id);
			this.addParameter("id", id);
		} else {
			this.addParameter("id", getId());
		}
		
		if (this.value != null)
			this.addParameter("value", this.format(this.findValue(this.value)));
		if (this.format != null)
			this.addParameter("format", this.findString(this.format));

		if (this.startDate != null)
			this.addParameter("uploadLog", this.format(this.findValue(this.startDate)));
		if (this.endDate != null)
			this.addParameter("endDate", this.format(this.findValue(this.endDate)));
		if (this.mode != null)
			this.addParameter("mode", this.findString(this.mode));
		else
			this.addParameter("mode", "input");
		if (this.iconCssClass != null)
			this.addParameter("iconCssClass", this.findString(this.iconCssClass));
		if (this.iconPath != null)
			this.addParameter("iconPath", this.findString(this.iconPath));
		if (this.autoClose != null)
			this.addParameter("autoClose", this.findValue(this.autoClose, Boolean.class));
		if (this.formatFunction != null)
			this.addParameter("formatFunction", this.findString(this.formatFunction));

		String nameValue = null;

		if (this.parameters.containsKey("value")) {
			nameValue = (String) this.parameters.get("value");
			this.addParameter("nameValue", nameValue);
		} else if (this.name != null) {
			nameValue = this.format(this.findValue(this.name));
			this.addParameter("nameValue", nameValue);
		}

		if (nameValue != null) {
			try {
				Date dateValue = DATE_FORMAT.parse(nameValue);
				this.addParameter("pagedate", PAGE_DATE_FORMAT.format(dateValue));
				this.addParameter("rfcValue", RFC3339_FORMAT.format(dateValue));
				if (this.format!=null)
					this.addParameter("formattedValue", new SimpleDateFormat(this.format).format(dateValue));
				this.addParameter("rfcTime", "" + dateValue.getTime());
			} catch (ParseException e) {
				LOG.error("Unable to build page date from specified value: " + nameValue);
			}
		}

		if (this.language != null) {
			this.addParameter("language", this.findString(this.language));
		} else {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
			this.addParameter("language", request.getLocale().getLanguage());
		}
	}

	/**
	 * @param name
	 * @return
	 */
	private String getHTMLID(String name) {
		return name.replaceAll("[0-9a-zA-Z]\\.", "_");
	}

	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	private String format(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Date)
			return DATE_FORMAT.format((Date) obj);
		else if (obj instanceof Calendar)
			return DATE_FORMAT.format(((Calendar) obj).getTime());
		else {
			// try to parse a date
			String dateStr = obj.toString();
			if (dateStr.equalsIgnoreCase("today"))
				return DATE_FORMAT.format(new Date());

			Date date = null;
			// formats used to parse the date
			List<DateFormat> formats = new ArrayList<DateFormat>();
			formats.add(new SimpleDateFormat(this.format));
			formats.add(RFC3339_DATE_FORMAT);
			formats.add(RFC3339_FORMAT);
			formats.add(DateFormat.getDateInstance(DateFormat.SHORT));
			formats.add(DateFormat.getDateInstance(DateFormat.MEDIUM));
			formats.add(DateFormat.getDateInstance(DateFormat.FULL));
			formats.add(DateFormat.getDateInstance(DateFormat.LONG));

			for (DateFormat format : formats) {
				try {
					date = format.parse(dateStr);
					if (date != null)
						return DATE_FORMAT.format(date);
				} catch (Exception e) {
					// keep going
				}
			}

			// last resource, assume already in correct/default format
			if (LOG.isDebugEnabled())
				LOG.debug("Unable to parse date " + dateStr);
			return dateStr;
		}
	}

	@StrutsTagAttribute(description = "Sets how the datepicker is to be rendered, should be one of 'input', 'static', 'label'", defaultValue = "input")
	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	@StrutsTagAttribute(description = "Used as HTML id attribute", required = false)
	public void setId(String id) {
		super.setId(StringUtil.sanitizeForJavascript(id));
	}

	@Override
	@StrutsTagAttribute(description = "Name of the field that will cotainf the date value", required = true)
	public void setName(String name) {
		super.setName(name);
	}

	@Override
	public void setOnblur(String onblur) {
		super.setOnblur(onblur);
	}

	@Override
	public void setOnchange(String onchange) {
		super.setOnchange(onchange);
	}

	@Override
	public void setOnclick(String onclick) {
		super.setOnclick(onclick);
	}

	@Override
	public void setOndblclick(String ondblclick) {
		super.setOndblclick(ondblclick);
	}

	@Override
	public void setOnfocus(String onfocus) {
		super.setOnfocus(onfocus);
	}

	@Override
	public void setOnkeydown(String onkeydown) {
		super.setOnkeydown(onkeydown);
	}

	@Override
	public void setOnkeypress(String onkeypress) {
		super.setOnkeypress(onkeypress);
	}

	@Override
	public void setOnkeyup(String onkeyup) {
		super.setOnkeyup(onkeyup);
	}

	@Override
	public void setOnmousedown(String onmousedown) {
		super.setOnmousedown(onmousedown);
	}

	@Override
	public void setOnmousemove(String onmousemove) {
		super.setOnmousemove(onmousemove);
	}

	@Override
	public void setOnmouseout(String onmouseout) {
		super.setOnmouseout(onmouseout);
	}

	@Override
	public void setOnmouseover(String onmouseover) {
		super.setOnmouseover(onmouseover);
	}

	@Override
	public void setOnmouseup(String onmouseup) {
		super.setOnmouseup(onmouseup);
	}

	@Override
	public void setOnselect(String onselect) {
		super.setOnselect(onselect);
	}

	@StrutsTagAttribute(description = "Last available date in the calendar set", type = "Date")
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@StrutsTagAttribute(description = "First available date in the calendar set", type = "Date")
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@StrutsTagAttribute(description = "Hide datepicker when a date is selected. Applies only when 'mode' is 'input' or 'label'", type = "Boolean", defaultValue = "true")
	public void setAutoClose(String autoClose) {
		this.autoClose = autoClose;
	}

	@StrutsTagAttribute(description = "URL of icon used for the dropdown image. Applies only when 'mode' is 'input' or 'label'")
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	@StrutsTagAttribute(description = "Css class of icon used for the dropdown image. Applies only when 'mode' is 'input' or 'label'")
	public void setIconCssClass(String iconCssClass) {
		this.iconCssClass = iconCssClass;
	}

	@StrutsTagAttribute(description = "Function used to format the selected value. The value returned by this function will be displayed"
			+ " on the input field. When the page loads an String will be passed to this function with the value specified on the 'value' attribute."
			+ "When the user selects a value using the datepicker a JavaScript object of type Date will be passed. Applies only when 'mode' is 'input' or 'label'")
	public void setFormatFunction(String formatFunction) {
		this.formatFunction = formatFunction;
	}

	@StrutsTagAttribute(description = "Language name(2 lowercase characters) to be used on the datepicker. This language must also be specified on the attribute"
			+ " 'languages' in the head tag.")
	public void setLanguage(String language) {
		this.language = language;
	}

	@StrutsTagAttribute(description = "Date rendering format")
	public void setFormat(String format) {
		if (format!=null && format.trim().length()>0) {
			this.format = format;
		} else {
			// we'll use the default!
		}
	}
}
