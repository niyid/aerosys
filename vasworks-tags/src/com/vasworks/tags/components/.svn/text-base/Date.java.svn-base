/**
 * vasworks-tags Feb 18, 2010
 */
package com.vasworks.tags.components;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author developer
 * 
 */
@StrutsTag(name = "date", tldBodyContent = "empty", tldTagClass = "com.vasworks.tags.views.jsp.ui.DateTag", description = "Render a formatted date.")
public class Date extends Component {

	private static final Log LOG = LogFactory.getLog(Date.class);
	/**
	 * Property name to fall back when no format is specified
	 */
	public static final String DATETAG_PROPERTY = "struts.date.format";
	/**
	 * Property name that defines the past notation (default: {0} ago)
	 */
	public static final String DATETAG_PROPERTY_PAST = "struts.date.format.past";
	private static final String DATETAG_DEFAULT_PAST = "{0} ago";
	/**
	 * Property name that defines the future notation (default: in {0})
	 */
	public static final String DATETAG_PROPERTY_FUTURE = "struts.date.format.future";
	private static final String DATETAG_DEFAULT_FUTURE = "in {0}";
	/**
	 * Property name that defines the seconds notation (default: in instant)
	 */
	public static final String DATETAG_PROPERTY_SECONDS = "struts.date.format.seconds";
	private static final String DATETAG_DEFAULT_SECONDS = "an instant";
	/**
	 * Property name that defines the minutes notation (default: {0,choice,1#one minute|1<{0} minutes})
	 */
	public static final String DATETAG_PROPERTY_MINUTES = "struts.date.format.minutes";
	private static final String DATETAG_DEFAULT_MINUTES = "{0,choice,1#one minute|1<{0} minutes}";
	/**
	 * Property name that defines the hours notation (default: {0,choice,1#one hour|1<{0} hours}{1,choice,0#|1#, one minute|1<, {1} minutes})
	 */
	public static final String DATETAG_PROPERTY_HOURS = "struts.date.format.hours";
	private static final String DATETAG_DEFAULT_HOURS = "{0,choice,1#one hour|1<{0} hours}{1,choice,0#|1#, one minute|1<, {1} minutes}";
	/**
	 * Property name that defines the days notation (default: {0,choice,1#one day|1<{0} days}{1,choice,0#|1#, one hour|1<, {1} hours})
	 */
	public static final String DATETAG_PROPERTY_DAYS = "struts.date.format.days";
	private static final String DATETAG_DEFAULT_DAYS = "{0,choice,1#one day|1<{0} days}{1,choice,0#|1#, one hour|1<, {1} hours}";
	/**
	 * Property name that defines the years notation (default: {0,choice,1#one year|1<{0} years}{1,choice,0#|1#, one day|1<, {1} days})
	 */
	public static final String DATETAG_PROPERTY_YEARS = "struts.date.format.years";
	private static final String DATETAG_DEFAULT_YEARS = "{0,choice,1#one year|1<{0} years}{1,choice,0#|1#, one day|1<, {1} days}";

	private String name;

	private String format;

	private String timezone;

	private boolean nice;

	public Date(ValueStack stack) {
		super(stack);
	}

	private TextProvider findProviderInStack() {
		for (Iterator iterator = getStack().getRoot().iterator(); iterator.hasNext();) {
			Object o = iterator.next();

			if (o instanceof TextProvider) {
				return (TextProvider) o;
			}
		}
		return null;
	}

	/**
	 * Calculates the difference in time from now to the given date, and outputs it nicely.
	 * <p/>
	 * An example: <br/>
	 * Now = 2006/03/12 13:38:00, date = 2006/03/12 15:50:00 will output "in 1 hour, 12 minutes".
	 * 
	 * @param tp text provider
	 * @param date the date
	 * @return the date nicely
	 */
	public String formatTime(TextProvider tp, java.util.Date date) {
		java.util.Date now = new java.util.Date();
		StringBuffer sb = new StringBuffer();
		List args = new ArrayList();
		long secs = Math.abs((now.getTime() - date.getTime()) / 1000);
		long mins = secs / 60;
		long sec = secs % 60;
		int min = (int) mins % 60;
		long hours = mins / 60;
		int hour = (int) hours % 24;
		int days = (int) hours / 24;
		int day = days % 365;
		int years = days / 365;

		if (years > 0) {
			args.add(new Long(years));
			args.add(new Long(day));
			args.add(sb);
			args.add(null);
			sb.append(tp.getText(DATETAG_PROPERTY_YEARS, DATETAG_DEFAULT_YEARS, args));
		} else if (day > 0) {
			args.add(new Long(day));
			args.add(new Long(hour));
			args.add(sb);
			args.add(null);
			sb.append(tp.getText(DATETAG_PROPERTY_DAYS, DATETAG_DEFAULT_DAYS, args));
		} else if (hour > 0) {
			args.add(new Long(hour));
			args.add(new Long(min));
			args.add(sb);
			args.add(null);
			sb.append(tp.getText(DATETAG_PROPERTY_HOURS, DATETAG_DEFAULT_HOURS, args));
		} else if (min > 0) {
			args.add(new Long(min));
			args.add(new Long(sec));
			args.add(sb);
			args.add(null);
			sb.append(tp.getText(DATETAG_PROPERTY_MINUTES, DATETAG_DEFAULT_MINUTES, args));
		} else {
			args.add(new Long(sec));
			args.add(sb);
			args.add(null);
			sb.append(tp.getText(DATETAG_PROPERTY_SECONDS, DATETAG_DEFAULT_SECONDS, args));
		}

		args.clear();
		args.add(sb.toString());
		if (date.before(now)) {
			// looks like this date is passed
			return tp.getText(DATETAG_PROPERTY_PAST, DATETAG_DEFAULT_PAST, args);
		} else {
			return tp.getText(DATETAG_PROPERTY_FUTURE, DATETAG_DEFAULT_FUTURE, args);
		}
	}

	public boolean end(Writer writer, String body) {
		String msg = null;
		ValueStack stack = getStack();
		java.util.Date date = null;
		Calendar calendar = null;
		// find the name on the valueStack, and cast it to a date
		try {
			Object val = findValue(name);
			if (val != null && val instanceof java.util.Date)
				date = (java.util.Date) val;
			else if (val != null && val instanceof Calendar) {
				calendar = (Calendar) val;
				date = calendar.getTime();
			} else {
				LOG.error("Unrecognized time object of type " + (val == null ? "NULL" : val.getClass()));
				msg = "";
			}
		} catch (Exception e) {
			LOG.error("Could not convert object with key '" + name + "' to a java.util.Date or Calendar instance");
			// bad date, return a blank instead ?
			msg = "";
		}

		// try to find the format on the stack
		if (format != null) {
			format = findString(format);
		}
		if (date != null) {
			TextProvider tp = findProviderInStack();
			if (tp != null) {
				if (nice) {
					msg = formatTime(tp, date);
				} else {
					// formatter calendar
					TimeZone formatterTimezone = null;
					if (this.timezone == null) {
						formatterTimezone = (TimeZone) ActionContext.getContext().get("WW_TRANS_I18N_TIMEZONE");
						if (formatterTimezone != null) {
							LOG.debug("Using user's timezon " + formatterTimezone.getDisplayName());
						}
					} else {
						LOG.debug("Timezone specified: " + this.timezone);
						formatterTimezone = TimeZone.getTimeZone(this.timezone);
						LOG.debug("Using specified timezone " + formatterTimezone.getDisplayName());
					}

					if (format == null) {
						String globalFormat = null;

						// if the format is not specified, fall back using the
						// defined property DATETAG_PROPERTY
						globalFormat = tp.getText(DATETAG_PROPERTY);

						// if tp.getText can not find the property then the
						// returned string is the same as input =
						// DATETAG_PROPERTY
						if (globalFormat != null && !DATETAG_PROPERTY.equals(globalFormat)) {
							SimpleDateFormat formatter = new SimpleDateFormat(globalFormat, ActionContext.getContext().getLocale());
							if (formatterTimezone != null)
								formatter.setTimeZone(formatterTimezone);
							msg = formatter.format(date);
						} else {
							DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, ActionContext.getContext().getLocale());
							if (formatterTimezone != null)
								formatter.setTimeZone(formatterTimezone);
							msg = formatter.format(date);
						}
					} else {
						SimpleDateFormat formatter = new SimpleDateFormat(format, ActionContext.getContext().getLocale());
						if (formatterTimezone != null)
							formatter.setTimeZone(formatterTimezone);
						msg = formatter.format(date);
					}
				}
				if (msg != null) {
					try {
						if (getName() == null) {
							writer.write(msg);
						} else {
							stack.getContext().put(getName(), msg);
						}
					} catch (IOException e) {
						LOG.error("Could not write out Date tag", e);
					}
				}
			}
		}
		return super.end(writer, "");
	}

	@StrutsTagAttribute(description = "Date or DateTime format pattern", rtexprvalue = false)
	public void setFormat(String format) {
		this.format = format;
	}

	@StrutsTagAttribute(description = "Whether to print out the date nicely", type = "Boolean", defaultValue = "false")
	public void setNice(boolean nice) {
		this.nice = nice;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	@StrutsTagAttribute(description = "The date value to format", required = true)
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the format.
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @return Returns the nice.
	 */
	public boolean isNice() {
		return nice;
	}

	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return this.timezone;
	}
}
