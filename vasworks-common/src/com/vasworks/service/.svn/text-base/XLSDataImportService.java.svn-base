/**
 * vasworks-common-web.struts Dec 15, 2009
 */
package com.vasworks.service;

import java.util.Hashtable;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.vasworks.service.impl.XLSImportException;

/**
 * @author developer
 * 
 */
public interface XLSDataImportService {

	/**
	 * @param targetEntity
	 * @param mappings
	 * @param sheetAt
	 * @return
	 * @throws XLSImportException
	 */
	List<?> getObjectsFromXLS(Class<?> targetEntity, Hashtable<String, String> mappings, HSSFSheet sheetAt) throws XLSImportException;

	/**
	 * @param targetEntity
	 * @param mappings
	 * @param xlsSheet
	 * @return
	 * @throws XLSImportException
	 */
	List<?> persistObjectsFromXLS(Class<?> targetEntity, Hashtable<String, String> mappings, HSSFSheet xlsSheet) throws XLSImportException;

	/**
	 * Generate List of <code>Object[]</code>s containing the data from XLS
	 * 
	 * @param mappings
	 * @param sheetAt
	 * @return
	 */
	List<Object[]> getObjectsFromXLS(HSSFSheet sheetAt);
}
