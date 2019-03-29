/**
 * vasworks-common May 15, 2009
 */
package com.vasworks.service;

/**
 * 
 * @author developer
 * 
 */
public class SearchException extends Exception {

	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -5055099867146982987L;

	/**
	 * 
	 */
	public SearchException() {

	}

	/**
	 * @param arg0
	 */
	public SearchException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public SearchException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SearchException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
