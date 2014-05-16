package com.google.sharecompare.executor;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.sharecompare.exceptions.SharesCompareException;

/**
 * Main class to start the processing.
 * 
 * @author VDudani
 * 
 */
public class LoadExecuteFile {

	static final Logger LOG = Logger.getLogger(LoadExecuteFile.class.getName());
	
	static final String CSV_FILE_DELIMITER = ",";

	/**
	 * 
	 * @param args
	 * @throws IOException 
	 * @throws Exception
	 */
	public static void main(String[] args) throws SharesCompareException, IOException {
		CompanyDataProcessor allCompData = new CompanyDataProcessor();
		final long startTime = System.currentTimeMillis();
		if (args.length > 0) {
			boolean fileLoaded = allCompData.readFileLoadCompData(args[0], CSV_FILE_DELIMITER);
			LOG.log(Level.INFO, "File loaded: " + (fileLoaded ? "Success" : "failure"));
		}
		else {
			throw new SharesCompareException("Please provide full input file path");
		}
		allCompData.printOutputResult();
		
		final long endTime = System.currentTimeMillis();
		System.out.println("\nTotal execution time: "+ (endTime-startTime) +" miliseconds");
	}

}
