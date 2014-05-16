package com.google.sharecompare.utility;

import java.util.logging.Logger;

public class Helper {

	public static final String DOUBLE_QUOTES = "\"";
	public static final String CSV_FORMAT = ".csv";
	
	public static Logger log = Logger.getLogger(Helper.class.getName());
	
	public Helper() {

	}

	/**
	 * Checks for the specified delimiter and then splits the input String. 
	 * It is basically used for removing the delimiters from front or end. 
	 * 
	 * @param input String to be operated on
	 * @param splitter The delimiter to be used for splitting
	 * @return
	 */
	public static String checkNSplit(String input, String splitter) {
		if (input.contains(splitter)) {
			String[] value = input.split(splitter);
			final int indexPos = input.indexOf(splitter);
			if (indexPos == 0)
				return value[1];
			else
				return value[0];
		}
		return input;
	}
	
	/**
	 * Check if the input file format is valid
	 * @param filename
	 * @return true if it a valid CSV file
	 */
	public static boolean checkInputFileFormat(String filename) {
		final int idx = filename.lastIndexOf(".");
		if(idx<0)
			return false;
		
		final String split = filename.substring(idx, filename.length());
		if(split.equalsIgnoreCase(Helper.CSV_FORMAT))
			return true;
		
		return false;
	}
}
