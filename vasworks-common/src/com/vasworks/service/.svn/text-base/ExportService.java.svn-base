/*
 * 
 */
package com.vasworks.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The Interface ExportService.
 */
public interface ExportService {

	/**
	 * Export with specified headings and data.
	 * 
	 * @param headings the headings
	 * @param data the data
	 * @param template the template
	 * @param properties the properties
	 * 
	 * @return the input stream
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	InputStream exportToStream(File template, String[] headings, String[] properties, List<? extends Object> data) throws IOException;

	/**
	 * Export with specified headings and data.
	 * 
	 * @param headings the headings
	 * @param data the data
	 * @param properties the properties
	 * 
	 * @return the input stream
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	InputStream exportToStream(String[] headings, String[] properties, List<? extends Object> data) throws IOException;
}
