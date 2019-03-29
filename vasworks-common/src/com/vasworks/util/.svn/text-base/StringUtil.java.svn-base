/**
 * 
 */
package com.vasworks.util;

import java.util.Calendar;
import java.util.Random;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author developer
 * 
 */
public class StringUtil {
	private static final Log LOG = LogFactory.getLog(StringUtil.class);
	public static final String NEWLINE = "\n";
	public static final String SPACE = " ";
	public static final String EMPTY = "";
	public static final String[] EMPTY_STRINGARRAY = new String[] {};

	/**
	 * Generate an alpha-numeric string with <i>length</i> characters.
	 * 
	 * <pre>
	 * String key = StringUtil.getRandomAlphaNumericString(10);
	 * System.out.println(key);
	 * </pre>
	 * 
	 * would output a 10-character string
	 * 
	 * <pre>
	 * aB3c5s3441
	 * </pre>
	 * 
	 * .
	 * 
	 * 
	 * @param length of generated alpha-numeric string
	 * @return Random alpha-numeric string
	 */
	public static java.lang.String getRandomAlphaNumericString(int length) {
		StringBuilder sb = new StringBuilder(length);
		Random r = new Random(Calendar.getInstance().getTimeInMillis());
		for (int i = 0; i < length; i++) {
			int what = r.nextInt(3);
			switch (what) {
			case 0:
				// lowercase
				sb.append((char) (97 + r.nextInt(25)));
				break;
			case 1:
				// capitals
				sb.append((char) (65 + r.nextInt(25)));
				break;
			case 2:
				// digits
				sb.append((char) (48 + r.nextInt(9)));
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * Generate a string with <i>length</i> characters (no digits).
	 * 
	 * <pre>
	 * String key = StringUtil.getRandomAlphaString(5);
	 * System.out.println(key);
	 * </pre>
	 * 
	 * would output a 5-character string
	 * 
	 * <pre>
	 * abFGe
	 * </pre>
	 * 
	 * .
	 * 
	 * 
	 * @param length of generated string
	 * @return Random string composed of characters only (no digits)
	 */
	public static java.lang.String getRandomAlphaString(int length) {
		StringBuilder sb = new StringBuilder(length);
		Random r = new Random(Calendar.getInstance().getTimeInMillis());
		for (int i = 0; i < length; i++) {
			int what = r.nextInt(2);
			switch (what) {
			case 0:
				// lowercase
				sb.append((char) (97 + r.nextInt(25)));
				break;
			case 1:
				// capitals
				sb.append((char) (65 + r.nextInt(25)));
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * Generate a string with <i>length</i> digits.
	 * 
	 * <pre>
	 * String key = StringUtil.getRandomNumericString(5);
	 * System.out.println(key);
	 * </pre>
	 * 
	 * would output a 5-character string
	 * 
	 * <pre>
	 * 63782
	 * </pre>
	 * 
	 * .
	 * 
	 * @param length of generated string
	 * @return Random string composed of digits only (no characters)
	 */
	public static java.lang.String getRandomNumericString(int length) {
		StringBuilder sb = new StringBuilder(length);
		Random r = new Random(Calendar.getInstance().getTimeInMillis());
		for (int i = 0; i < length; i++) {
			// digits
			sb.append((char) (48 + r.nextInt(9)));
		}
		return sb.toString();
	}

	/**
	 * @param id
	 * @return
	 */
	public static String sanitizeForJavascript(String name) {
		if (name != null) {
			return name.replaceAll("[\\.\\[\\]]", "_");
		} else {
			return "";
		}
	}

	/**
	 * Merges an array of {@link String}s into a printable string
	 * 
	 * @param recipients
	 * @return
	 */
	public static String arrayToString(String[] arrayOfStrings) {
		if (arrayOfStrings == null)
			return "null";
		if (arrayOfStrings.length == 0)
			return "[ ]";
		StringBuffer sb = new StringBuffer();
		sb.append("[ ");
		for (int i = 0; i < arrayOfStrings.length; i++) {
			String x = arrayOfStrings[i];
			if (i > 0)
				sb.append(", ");
			if (x == null)
				sb.append("null");
			else
				sb.append(x);
		}
		sb.append(" ]");
		return sb.toString();
	}

	public static String removeHTML(String text) {
		if (text == null)
			return null;
		// deal with HTML stuff
		return text.replaceAll("<[^>]+>", "");
	}

	/**
	 * @param text
	 * @param maxLength
	 * @param removeHTML
	 * @param addDots
	 */
	public static String shortenText(String text, int maxLength, boolean removeHTML, boolean addDots) {
		if (text == null || text.length() == 0)
			return text;

		// deal with &nbsp;
		text = text.replaceAll("(&nbsp;)+", " ");

		// deal with HTML stuff
		if (removeHTML) {
			LOG.debug("Need to remove HTML tags!");
			text = text.replaceAll("<[^>]+>", "");
			LOG.debug("Got: " + text);
		}

		// trim smart
		if (text.length() <= maxLength) {
			return text;
		}
		LOG.trace("Need to trim from " + text.length() + " to " + maxLength);

		String trimmed = text.substring(0, maxLength + 1);
		char lastCh = trimmed.charAt(maxLength);

		LOG.trace("Last character: " + lastCh);
		if (lastCh != ' ' && lastCh != '\t' && lastCh != '\n' && lastCh != '\r') {
			LOG.trace("Need to find last space");
			int lastSpace = trimmed.lastIndexOf(' ');
			int x = trimmed.lastIndexOf('\t');
			if (x > lastSpace)
				lastSpace = x;
			x = trimmed.lastIndexOf('\r');
			if (x > lastSpace)
				lastSpace = x;
			x = trimmed.lastIndexOf('\n');
			if (x > lastSpace)
				lastSpace = x;

			if (lastSpace > 0)
				trimmed = trimmed.substring(0, lastSpace);
			LOG.trace("Trimmed for last space: " + trimmed);
		}

		Stack<String> tagStack = new Stack<String>();

		// check if any tags need closing
		int len = trimmed.length();
		int status = 0, tagPos = 0;
		String currentTag = "";
		for (int i = 0; i < len; i++) {
			char ch = trimmed.charAt(i);
			if (status == 0 && ch == '<') {
				currentTag = "";
				tagPos = i;
				status = 1;
				continue;
			}
			// if (status==0 && (ch==' ' || ch=='\t' || ch=='\n' || ch=='\r')) { lastSpace=i; }
			if (status == 0) {
				continue;
			}
			if (status == 1 && ch == '/') {
				LOG.trace("END TAG");
				status = 3;
				continue;
			}
			if (status == 1 && (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r')) {
				LOG.trace("TAG1: " + currentTag);
				tagStack.push(currentTag);
				status = 2;
				continue;
			}
			if (status == 1 && ch == '>') {
				LOG.trace("TAG2: " + currentTag);
				tagStack.push(currentTag);
				status = 0;
				continue;
			}
			if (status == 1) {
				currentTag += ch;
				continue;
			}
			if (status == 3 && ch == '>') {
				LOG.trace("END TAG1: " + currentTag);
				if (tagStack.peek().equalsIgnoreCase(currentTag))
					tagStack.pop();
				status = 0;
				continue;
			}
			;
			if (status == 3 && (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r')) {
				LOG.trace("END TAG2: " + currentTag);
				if (tagStack.peek().equalsIgnoreCase(currentTag))
					tagStack.pop();
				status = 0;
				continue;
			}
			;
			if (status == 3) {
				currentTag += ch;
				continue;
			}
			;
		}
		LOG.debug("Was left in status: " + status);
		if (status != 0) {
			LOG.trace(">>" + trimmed);
			trimmed = trimmed.substring(0, tagPos);
			LOG.trace(">>" + trimmed);
		}

		if (addDots) {
			trimmed += " ...";
		}

		LOG.debug("Tag stack: ");
		while (tagStack.size() > 0) {
			currentTag = tagStack.pop();
			LOG.debug("\t" + currentTag);
			trimmed += "</" + currentTag + ">";
		}

		LOG.debug("Returning: " + trimmed);
		return trimmed;
	}

	/**
	 * @param value
	 * @param blockSize 1024 or 1000?
	 * @return
	 */
	public static String toHumanReadableBytes(double value, int blockSize) {
		if (value == 0)
			return "-";
		String humanReadable = "" + Math.round(value) + " B";
		final String[] prefix = new String[] { "", "K", "M", "G", "T", "P" };
		int i = 0;
		while (value > (blockSize * 10) && i < prefix.length - 1) {
			value /= blockSize;
			i++;
			humanReadable = "" + Math.round(value) + " " + prefix[i];
		}
		return humanReadable;
	}

	/**
	 * @param title
	 * @return
	 */
	public static String sanitizeFileName(String title) {
		if (title == null)
			return null;
		return title.replaceAll("[^\\w\\d _]+", "_");
	}

	public static String fromHumanToCamel(String human) {
		if (human == null || human.length() == 0)
			return human;
		String camel = "";
		String[] split = human.split("[^a-zA-Z0-9]+");
		for (int i = 0; i < split.length; i++) {
			LOG.debug("toCamel: " + split[i]);
			if (i == 0 && split[i].length() > 1)
				camel = split[i].substring(0, 1).toLowerCase() + split[i].substring(1);
			else if (i == 0)
				camel = split[i].substring(0, 1).toLowerCase();
			else if (split[i].length() > 1)
				camel += split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
			else
				camel += split[i].substring(0, 1).toUpperCase();
		}
		return camel;
	}

	/**
	 * @param name
	 * @return
	 */
	public static String getOgnlName(String name) {
		if (name == null)
			return null;
		if (name.startsWith("is"))
			name = name.substring(2);
		else
			name = name.substring(3);
		if (name.length() < 1)
			return name.toLowerCase();
		else
			return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	/**
	 * Return HTML version of text, replacing \n with &lt;p&gt;
	 * 
	 * @param text
	 * @return
	 */
	public static String toHtml(String text) {
		if (text == null)
			return text;
		LOG.debug("toHTML: " + text);
		Pattern p = Pattern.compile("([^\\n]*)\\n");
		Matcher matcher = p.matcher(text);
		text = matcher.replaceAll("<p>$1</p>");
		LOG.debug("<p>: " + text);
		Pattern p2 = Pattern.compile("(\\w+://[^ <]+)");
		matcher = p2.matcher(text);
		text = matcher.replaceAll("<a href=\"$1\">$1</a>");
		LOG.debug("<a>: " + text);
		return text;
	}
}
