/*
 * 
 */
package com.vasworks.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The Interface DataImportService.
 */
public interface DataImportService {

	/**
	 * Import objects of type <b>clazz</b> from XLS file.
	 * 
	 * @param inputStream the input stream
	 * @param clazz the clazz
	 * @param expressions Array of OGNL expressions that are used to set values
	 * 
	 * @return the list<?>
	 * 
	 * @throws IOException * @throws XLSImportException * @throws Exception the exception
	 */
	public abstract List<?> importFromStream(InputStream inputStream, Class<?> clazz, final String[] expressions) throws Exception;
}