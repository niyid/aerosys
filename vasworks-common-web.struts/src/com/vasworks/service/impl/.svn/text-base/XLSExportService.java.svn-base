/*
 * 
 */
package com.vasworks.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.vasworks.service.ExportService;
import com.vasworks.util.DeleteFileAfterCloseInputStream;

/**
 * The Class XLSExportService.
 */
public class XLSExportService implements ExportService {

	/** The Constant EMTPY_ARRAY. */
	private static final Object[] EMTPY_ARRAY = new Object[] {};

	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(XLSExportService.class);

	/** The Constant FONT_BOLD. */
	private static final int FONT_BOLD = 0;

	/** The Constant VERTICAL_BOTTOM. */
	private static final int VERTICAL_BOTTOM = 0;

	/** The Constant TITLE_STYLE. */
	private static final int TITLE_STYLE = 1;

	/** The Constant MONEY_STYLE. */
	private static final int MONEY_STYLE = 2;

	/** The Constant DATE_STYLE. */
	private static final int DATE_STYLE = 3;

	/**
	 * @see com.vasworks.service.ExportService#exportToStream(java.io.File, java.lang.String[], java.lang.String[], java.util.List)
	 */
	@Override
	public InputStream exportToStream(File template, String[] headings, String[] properties, List<? extends Object> data) throws IOException {
		return exportToStream(new FileInputStream(template), headings, properties, data);
	}

	public InputStream exportToStream(InputStream inputStream, String[] headings, String[] properties, List<? extends Object> data) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = wb.getSheetAt(0);

		return internalExport(wb, sheet, headings, properties, data);
	}

	/**
	 * @see com.vasworks.service.ExportService#exportToStream(java.lang.String[], java.lang.String[], java.util.List)
	 */
	@Override
	public InputStream exportToStream(String[] headings, String[] properties, List<? extends Object> data) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Data export");

		return internalExport(wb, sheet, headings, properties, data);
	}

	/**
	 * Internal export.
	 * 
	 * @param wb the wb
	 * @param sheet the sheet
	 * @param headings the headings
	 * @param properties the properties
	 * @param data the data
	 * 
	 * @return the input stream
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private InputStream internalExport(HSSFWorkbook wb, HSSFSheet sheet, String[] headings, String[] properties, List<? extends Object> data)
			throws IOException {
		HSSFDataFormat format = wb.createDataFormat();

		HSSFFont[] fonts = new HSSFFont[1];
		fonts[FONT_BOLD] = wb.createFont();
		fonts[FONT_BOLD].setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fonts[FONT_BOLD].setFontHeightInPoints((short) 12);

		HSSFCellStyle[] styles = new HSSFCellStyle[4];
		styles[VERTICAL_BOTTOM] = wb.createCellStyle();
		styles[VERTICAL_BOTTOM].setWrapText(true);
		styles[VERTICAL_BOTTOM].setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
		styles[VERTICAL_BOTTOM].setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styles[VERTICAL_BOTTOM].setFont(fonts[FONT_BOLD]);

		styles[TITLE_STYLE] = wb.createCellStyle();
		styles[TITLE_STYLE].setFont(fonts[FONT_BOLD]);

		styles[MONEY_STYLE] = wb.createCellStyle();
		styles[MONEY_STYLE].setDataFormat(format.getFormat("#,###.00 $"));

		styles[DATE_STYLE] = wb.createCellStyle();
		styles[DATE_STYLE].setDataFormat(format.getFormat("d/M/yyyy"));

		int rowIndex = 0;
		HSSFRow row = sheet.createRow(rowIndex++);
		fillRow(row, headings, styles[VERTICAL_BOTTOM], styles);

		if (data.size() > 0) {
			Object[] ognlExpressions = getExpressions(properties);

			OgnlContext ognlContext = new OgnlContext();

			for (Object d : data) {
				fillRow(sheet.createRow(rowIndex++), getData(ognlExpressions, ognlContext, d), null, styles);
			}
		}

		File file = File.createTempFile("export", "xls");
		FileOutputStream fs = new FileOutputStream(file);
		wb.write(fs);
		fs.flush();
		fs.close();
		return new DeleteFileAfterCloseInputStream(file);
	}

	/**
	 * Gets the expressions.
	 * 
	 * @param properties the properties
	 * 
	 * @return the expressions
	 */
	private static Object[] getExpressions(String[] properties) {
		Object[] expressions = new Object[properties.length];
		for (int i = 0; i < properties.length; i++) {
			try {
				if (properties[i] != null)
					expressions[i] = Ognl.parseExpression(properties[i]);
			} catch (OgnlException e) {
				LOG.error(e);
			}
		}
		return expressions;
	}

	/**
	 * Gets the data.
	 * 
	 * @param ognlExpressions the ognl expressions
	 * @param context the context
	 * @param d2 the d2
	 * 
	 * @return the data
	 */
	private static Object[] getData(Object[] ognlExpressions, OgnlContext context, Object d2) {
		if (d2 == null)
			return EMTPY_ARRAY;
		Object[] results = new Object[ognlExpressions.length];
		for (int i = 0; i < results.length; i++) {
			results[i] = null;
			try {
				if (ognlExpressions[i] != null)
					results[i] = Ognl.getValue(ognlExpressions[i], context, d2);
			} catch (OgnlException e) {
				//LOG.error(e);
			}
		}

		return results;
	}

	/**
	 * Fill row.
	 * 
	 * @param row the row
	 * @param objects the objects
	 * @param cellStyle the cell style
	 * @param allStyles the all styles
	 */
	private static void fillRow(HSSFRow row, Object[] objects, HSSFCellStyle cellStyle, HSSFCellStyle[] allStyles) {
		if (objects == null)
			return;

		for (int i = 0; i < objects.length; i++) {
			Object o = objects[i];
			HSSFCell cell = row.createCell(i);
			if (cellStyle != null)
				cell.setCellStyle(cellStyle);

			if (o == null) {

			} else if (o instanceof String) {
				cell.setCellValue(new HSSFRichTextString((String) o));
			} else if (o instanceof Long) {
				cell.setCellValue((Long) o);
			} else if (o instanceof Integer) {
				cell.setCellValue((Integer) o);
			} else if (o instanceof Double) {
				cell.setCellValue((Double) o);
			} else if (o instanceof Float) {
				cell.setCellValue((Float) o);
			} else if (o instanceof Date) {
				cell.setCellValue((Date) o);
				cell.setCellStyle(allStyles[DATE_STYLE]);
			} else if (o instanceof Timestamp) {
				cell.setCellValue((Date) o);
				cell.setCellStyle(allStyles[DATE_STYLE]);
			} else if (o instanceof Calendar) {
				cell.setCellValue((Calendar) o);
				cell.setCellStyle(allStyles[DATE_STYLE]);
			} else if (o instanceof Boolean) {
				cell.setCellValue((Boolean) o);
			} else if (o instanceof Enum<?>) {
				cell.setCellValue(new HSSFRichTextString(((Enum<?>) o).name()));
			} else {
				LOG.error("Unmanaged: " + o.getClass());
			}
		}
	}

	public static void fillSheet(HSSFSheet sheet, String[] headings, List<Object[]> data) throws IOException {
		HSSFWorkbook wb=sheet.getWorkbook();
		HSSFDataFormat format = wb.createDataFormat();

		HSSFFont[] fonts = new HSSFFont[1];
		fonts[FONT_BOLD] = wb.createFont();
		fonts[FONT_BOLD].setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fonts[FONT_BOLD].setFontHeightInPoints((short) 12);

		HSSFCellStyle[] styles = new HSSFCellStyle[4];
		styles[VERTICAL_BOTTOM] = wb.createCellStyle();
		styles[VERTICAL_BOTTOM].setWrapText(true);
		styles[VERTICAL_BOTTOM].setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
		styles[VERTICAL_BOTTOM].setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styles[VERTICAL_BOTTOM].setFont(fonts[FONT_BOLD]);

		styles[TITLE_STYLE] = wb.createCellStyle();
		styles[TITLE_STYLE].setFont(fonts[FONT_BOLD]);

		styles[MONEY_STYLE] = wb.createCellStyle();
		styles[MONEY_STYLE].setDataFormat(format.getFormat("#,###.00 $"));

		styles[DATE_STYLE] = wb.createCellStyle();
		styles[DATE_STYLE].setDataFormat(format.getFormat("d/M/yyyy"));

		int rowIndex = 0;
		HSSFRow row = sheet.createRow(rowIndex++);
		fillRow(row, headings, styles[VERTICAL_BOTTOM], styles);

		if (data.size() > 0) {
			for (Object[] d : data) {
				fillRow(sheet.createRow(rowIndex++), d, null, styles);
				if (rowIndex%100==0) {
					LOG.debug("Inserted " + rowIndex + " rows to sheet");
				}
			}
		}
	}
}
