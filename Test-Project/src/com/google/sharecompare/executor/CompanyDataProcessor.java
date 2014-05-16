package com.google.sharecompare.executor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.sharecompare.dataobject.Company;
import com.google.sharecompare.exceptions.CompanyDataInvalidException;
import com.google.sharecompare.exceptions.SharesCompareException;
import com.google.sharecompare.utility.Helper;

public class CompanyDataProcessor {

	static final int YEAR_IDX = 0;
	static final int MONTH_IDX = 1;

	public final static Logger LOG = Logger.getLogger(CompanyDataProcessor.class.getName());
	
	/**
	 * HashMap holding List of Company with key as Integer
	 */
	private HashMap<Integer, List<Company>> allCompanyData;

	/**
	 * Default Constructor
	 */
	public CompanyDataProcessor() {
		allCompanyData = new HashMap<Integer, List<Company>>();
	}

	public void setAllCompanyData(HashMap<Integer, List<Company>> allCompanyData) {
		this.allCompanyData = allCompanyData;
	}

	public HashMap<Integer, List<Company>> getAllCompanyData() {
		return allCompanyData;
	}

	/**
	 * This method reads the input CSV file and loads the data in the Collection
	 * object. It currently supports CSV files.
	 * 
	 * @param filename
	 *            Full filename to be read
	 * @param delimiter
	 *            Used in the file for separating data
	 * @return true if successfully loaded, false otherwise
	 * @throws IOException 
	 * @throws SharesCompareException 
	 */
	public boolean readFileLoadCompData(String filename, String delimiter) throws IOException, SharesCompareException {
		LOG.info("Reading data from: "+filename);
		boolean result = false;
		BufferedReader br = null;
		String line = null;
		boolean fileHeader = true;
		try {
			if(Helper.checkInputFileFormat(filename)){
				br = new BufferedReader(new FileReader(filename));
				while ((line = br.readLine()) != null) {
					if(!line.contains(delimiter))
						continue;
					String[] inputData = line.split(delimiter);
					if (fileHeader) {
						fileHeader = false;
						for (int i = 2; i < inputData.length; i++) {
							// create a list for each company
							ArrayList<Company> companyData = new ArrayList<Company>();
							companyData.add(new Company(Helper.checkNSplit(inputData[i], Helper.DOUBLE_QUOTES), "", "", 0F));
							allCompanyData.put(i, companyData);
						}
					}
					else
						populateData(inputData);
				}
				result = true;
			}
			else {
				throw new SharesCompareException("Invalid input file. Check the file format.");
			}
			
		} catch(FileNotFoundException e) {
			throw new FileNotFoundException(e.getMessage());
		} catch(IOException e) {
			throw new IOException(e.getMessage());
		} catch(Exception e) {
			LOG.log(Level.SEVERE, "Exception in CompanyDataProcessor class - readFileLoadCompData() - "+ e.getMessage());
			throw new SharesCompareException(e.getMessage());
		}
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * Populate the data for each company in a List.
	 * It is not throwing the exception back to the calling method as that would skip the processing of remaining data.
	 * 
	 * @param input
	 */
	public void populateData(String[] input) {
		String name = null;
		String month = null;
		String year = null;
		try{
			if(input.length < 3)
				throw new CompanyDataInvalidException("No data for any company in this row.");
			for (int i = 2; i < input.length; i++) {
				List<Company> companyData = allCompanyData.get(i);
				if(companyData == null)
					throw new CompanyDataInvalidException("More data than the no. of companies in this line. Ignoring it.");
				final Company company = companyData.get(0);
				name = company != null ? company.getName() : "";
				month = input[MONTH_IDX];
				year = Helper.checkNSplit(input[YEAR_IDX], Helper.DOUBLE_QUOTES);
				Float price = 0F;
				try {	
					price = (input[i] != null || input[i] != "") ? Float.parseFloat(Helper.checkNSplit(input[i], 
							Helper.DOUBLE_QUOTES)) : 0F;
				} 
				catch(NumberFormatException nfe) {
					throw new CompanyDataInvalidException("Price is not a valid number",name,year,month);
				}
				if(price < 0)
					throw new CompanyDataInvalidException("Price is negative",name,year,month);
				
				companyData.add(new Company(name, month, year, price));
				allCompanyData.put(i, companyData);
			}
		} 
		catch(SharesCompareException e) {
			LOG.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * Fetch the loaded lists of each company and show the company's max stock
	 * price along with year and month.
	 * @throws SharesCompareException 
	 */
	public void printOutputResult() throws SharesCompareException {
		try {
			if(allCompanyData.keySet().size()==0) {
				LOG.info("No Data to process. Check the input file contents.");
				return;
			}
				
			for (Integer key : allCompanyData.keySet()) {
				final List<Company> companyList = allCompanyData.get(key);
				if (companyList == null || companyList.size() <= 1)
					throw new SharesCompareException("No company data loaded.");
				final Company company = Collections.max(companyList);
				System.out.println(company.toString());
			}
		} 
		catch (SharesCompareException e) {
			LOG.log(Level.SEVERE, "Exception in CompanyDataProcessor class - printOutputResult() - "
					+e.getMessage());
			throw new SharesCompareException(e.getMessage());
		}
	}	
}
