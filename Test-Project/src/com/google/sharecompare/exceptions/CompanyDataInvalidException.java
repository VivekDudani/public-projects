package com.google.sharecompare.exceptions;

/**
 * Exception class for invalid data
 * @author vdudani
 *
 */
public class CompanyDataInvalidException extends SharesCompareException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompanyDataInvalidException() {
		super();
	}

	public CompanyDataInvalidException(String reason) {
		super(reason);
	}

	public CompanyDataInvalidException(String reason, String name, String year, String month) {
		super("Invalid data for Company " + name + " for " + month + ", " 
				+ year + " Reason: "+reason+". Ignoring it.");
	}
}
