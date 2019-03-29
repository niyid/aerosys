/**
 * 
 */
package com.vasworks.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Reads CSV file format, optionally tries converting Strings to required types and returns <i>List&lt;Object></i>.
 * 
 * <pre>
 * CSVDataReader reader = new CSVDataReader(csvFile, true, &quot;,&quot;, &quot;\&quot;&quot;);
 * reader.setDateFormat(DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK));
 * reader.setColumn(2, DataType.Long);
 * reader.setColumn(8, DataType.Double);
 * reader.setColumn(18, DataType.Date);
 * reader.setColumn(19, DataType.Long);
 * reader.setColumn(22, DataType.Boolean);
 * 
 * List&lt;Object&gt; row = null;
 * while ((row = reader.readLine()) != null) {
 * 	// We have row data
 * }
 * </pre>
 * 
 * DataType.Boolean requires string to match "yes" or "true" for <i>true</i>, any other string results in <i>false</i> value.
 * 
 * 
 * @author developer
 */
public class CSVDataReader {
	private int currentLine = 0;
	private BufferedReader reader = null;
	private List<String> headings = new Vector<String>();
	private ArrayList<String> warnings = new ArrayList<String>();

	/**
	 * @return the headings
	 */
	public List<String> getHeadings() {
		if (!this.firstLineHeadings)
			return new Vector<String>();

		synchronized (this) {
			if (!this.readFirstLine && this.firstLineHeadings) {
				try {
					readHeadings();
				} catch (Exception e) {
					return null;
				}
			}
		}

		return this.headings;
	}

	private Vector<DataType> conversions = new Vector<DataType>();
	private String separator = ",";
	private String textDelimiter = "";
	private boolean firstLineHeadings = true;
	private boolean readFirstLine;
	private DateFormat dateFormat;

	public enum DataType {
		None, Date, Text, Integer, Long, Float, Double, Boolean
	}

	public CSVDataReader(File file) throws FileNotFoundException {
		this(file, true);
	}

	public CSVDataReader(File file, boolean firstLineHeadings) throws FileNotFoundException {
		this(file, firstLineHeadings, ",");
	}

	public CSVDataReader(File file, boolean firstLineHeadings, String separator) throws FileNotFoundException {
		this(file, firstLineHeadings, separator, "");
	}

	public CSVDataReader(File file, boolean firstLineHeadings, String separator, String textDelimiter) throws FileNotFoundException {
		this(file, Charset.forName("UTF-8"), firstLineHeadings, separator, textDelimiter);
	}

	public CSVDataReader(File file, Charset charset, boolean firstLineHeadings, String separator, String textDelimiter) throws FileNotFoundException {
		this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
		this.firstLineHeadings = firstLineHeadings; 
		this.separator = separator;
		this.textDelimiter = textDelimiter;
		this.dateFormat = DateFormat.getDateInstance();
	}
	
	public void close() {
		try {
			this.reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Reads the first line headings from CSV
	 * 
	 * @throws Exception
	 */
	private void readHeadings() throws Exception {
		if (this.readFirstLine)
			throw new Exception("Cannot read headings twice!");

		String line = getNextLine();
		this.currentLine++;
		String[] splitLine = tokenize(line);
		if (this.textDelimiter != null && this.textDelimiter.length() > 0) {
			for (int i = 0; i < splitLine.length; i++) {
				String sl = splitLine[i];
				if (sl.startsWith(this.textDelimiter)) {
					splitLine[i] = sl = sl.substring(this.textDelimiter.length());
					if (sl.endsWith(this.textDelimiter))
						splitLine[i] = sl = sl.substring(0, sl.length() - this.textDelimiter.length());
				}
			}
		}
		for (int i = 0; i < splitLine.length; i++) {
			this.headings.add(splitLine[i]);
			System.err.println("Heading " + i + ": " + splitLine[i]);
		}
		this.readFirstLine = true;
	}

	/**
	 * Split the raw CSV line/s into a <code>String[]</code>.
	 * 
	 * This method replaces the standard {@link String#split(String)} as CSV may contain CR and LF characters. This method <b>may</b> call
	 * {@link CSVDataReader#readLine()} in this case.
	 * 
	 * @param line Current CSV line
	 * @return A <code>String[]</code> properly altered
	 * @throws IOException
	 */
	private String[] tokenize(String line) throws IOException {
		if (line == null)
			return new String[] {};

		Vector<String> vals = new Vector<String>(20);

		String[] foo = line.split(this.separator);
		if (foo.length > 0) {
			String c = foo[0];
			boolean needMoreData = false;
			for (int i = 1; i < foo.length || c != null || needMoreData; i++) {
				needMoreData = false;
				if (c.startsWith(this.textDelimiter) && !c.endsWith(this.textDelimiter)) {
					needMoreData = true;
					if (i < foo.length) {
						c = c + (foo[i].trim().length() == 1 ? "" : ",") + foo[i];
					} else {
						// if i >= foo.length and we're still missing a closing
						// ", read next line
						line = getNextLine();
						foo = line.split(this.separator);
						i = -1;
					}
				} else {
					vals.add(c);
					c = i < foo.length ? foo[i] : null;
				}
			}
			if (c != null)
				vals.add(c);
		}

		foo = new String[vals.size()];
		int i = 0;
		for (String x : vals) {
			if (x == null || x.length() == 0)
				foo[i++] = "";
			// else if (x.startsWith(this.textDelimiter) &&
			// x.endsWith(this.textDelimiter))
			// foo[i++] = (x.substring(this.textDelimiter.length(), x.length() -
			// this.textDelimiter.length())).trim();
			else
				foo[i++] = x.trim();
		}
		return foo;
	}

	public CSVDataReader setColumn(int column, DataType type) {
		for (int i = conversions.size(); i <= column; i++)
			conversions.add(DataType.None);
		conversions.set(column, type);
		return this;
	}

	public List<Object> readLine() throws IOException {
		synchronized (this) {
			if (!this.readFirstLine && this.firstLineHeadings) {
				try {
					readHeadings();
				} catch (Exception e) {
					return null;
				}
			}
		}

		String line = getNextLine();
		this.currentLine++;
		if (line == null)
			return null;

		String[] splitLine = tokenize(line);
		ArrayList<Object> dataRow = new ArrayList<Object>(splitLine.length);

		for (int i = 0; i < splitLine.length; i++) {
			String sl = splitLine[i];
			if (this.textDelimiter != null && this.textDelimiter.length() > 0) {
				if (sl.startsWith(this.textDelimiter)) {
					splitLine[i] = sl = sl.substring(this.textDelimiter.length());
					if (sl.endsWith(this.textDelimiter))
						splitLine[i] = sl = sl.substring(0, sl.length() - this.textDelimiter.length());
				}
			}

			sl = sl.trim();
			DataType type = DataType.None;
			if (sl.length() == 0) {
				dataRow.add(null);
			} else if (i < this.conversions.size()) {
				type = this.conversions.get(i);
				// System.err.println("Converting to: " + type);
				switch (type) {
				case Integer:
					try {
						dataRow.add(new Integer(sl));
					} catch (java.lang.NumberFormatException e) {
						addWarning("Warning in line " + this.currentLine + ": conversion to Integer " + sl + " " + e.getMessage());
						dataRow.add(null);
					}
					break;
				case Long:
					try {
						dataRow.add(new Long(sl));
					} catch (java.lang.NumberFormatException e) {
						addWarning("Warning in line " + this.currentLine + ": conversion to Long " + sl + " " + e.getMessage());
						dataRow.add(null);
					}
					break;
				case Float:
					try {
						dataRow.add(new Float(sl));
					} catch (java.lang.NumberFormatException e) {
						addWarning("Warning in line " + this.currentLine + ": conversion to Float " + sl + " " + e.getMessage());
						dataRow.add(null);
					}
					break;
				case Double:
					try {
						dataRow.add(new Double(sl));
					} catch (java.lang.NumberFormatException e) {
						addWarning("Warning in line " + this.currentLine + ": conversion to Double " + sl + " " + e.getMessage());
						dataRow.add(null);
					}
					break;
				case Date:
					try {
						dataRow.add(dateFormat.parse(sl));
					} catch (ParseException e) {
						addWarning("Warning in line " + this.currentLine + ": conversion to Date " + sl + " " + e.getMessage());
						dataRow.add(null);
					}
					break;
				case Text:
				case None:
					dataRow.add(sl);
					break;
				case Boolean:
					try {
						if (sl.length() > 0)
							if (sl.equalsIgnoreCase("yes") || sl.equalsIgnoreCase("true"))
								dataRow.add(new Boolean(true));
							else
								dataRow.add(new Boolean(false));
					} catch (Exception e) {
						addWarning("Warning in line " + this.currentLine + ": conversion to Date " + sl + " " + e.getMessage());
						dataRow.add(null);
					}
				default:
					break;
				}
			} else {
				dataRow.add(sl);
			}
		}

		// System.err.println(dataRow);

		/*
		 * for (int i = 0; i < dataRow.length; i++) { System.err.println("Data " + i + ": " + splitLine[i]); }
		 */

		// fill if we have headings!
		if (this.headings != null)
			for (int i = dataRow.size(); i < this.headings.size(); i++)
				dataRow.add(null);

		return dataRow;
	}

	private void addWarning(String message) {
		System.err.println(message);
		warnings.add(message);
	}

	public ArrayList<String> getWarnings() {
		return warnings;
	}

	/**
	 * @return Next line from input <code>reader</code>
	 * @throws IOException
	 */
	private String getNextLine() throws IOException {
		return reader.readLine();
	}

	/**
	 * @return the separator
	 */
	public String getSeparator() {
		return this.separator;
	}

	/**
	 * @param separator the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/**
	 * @return the textDelimiter
	 */
	public String getTextDelimiter() {
		return this.textDelimiter;
	}

	/**
	 * @param textDelimiter the textDelimiter to set
	 */
	public void setTextDelimiter(String textDelimiter) {
		this.textDelimiter = textDelimiter;
	}

	/**
	 * @return the firstLineHeadings
	 */
	public boolean isFirstLineHeadings() {
		return this.firstLineHeadings;
	}

	/**
	 * @param firstLineHeadings the firstLineHeadings to set
	 */
	public void setFirstLineHeadings(boolean firstLineHeadings) {
		this.firstLineHeadings = firstLineHeadings;
	}

	/**
	 * @return the dateFormat
	 */
	public DateFormat getDateFormat() {
		return this.dateFormat;
	}

	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getCurrentLine() {
		return currentLine;
	}
}
