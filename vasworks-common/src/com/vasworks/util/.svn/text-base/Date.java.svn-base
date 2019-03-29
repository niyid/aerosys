/**
 * 
 */
package com.vasworks.util;

import java.util.Calendar;

/**
 * @author developer
 * 
 */
public class Date {
	public static Calendar getYearStart(int year) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c;
	}

	public static Calendar getYearEnd(int year) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, year + 1);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MILLISECOND, -1);
		return c;
	}

	/**
	 * @param i
	 * @return
	 */
	public static Calendar getMonthStart(int plusMonths) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.MONTH, plusMonths);
		return c;
	}

	/**
	 * @param i
	 * @return
	 */
	public static Calendar getMonthEnd(int plusMonths) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.MONTH, plusMonths + 1);
		c.add(Calendar.MILLISECOND, -1);
		return c;
	}
}
