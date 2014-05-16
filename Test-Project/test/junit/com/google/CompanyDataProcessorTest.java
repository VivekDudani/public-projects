package junit.com.google;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.sharecompare.dataobject.Company;
import com.google.sharecompare.exceptions.SharesCompareException;
import com.google.sharecompare.executor.CompanyDataProcessor;

public class CompanyDataProcessorTest {

	public static String INPUT_FILE_PATH;
	
	static final String DELIMITER = ",";
	static final int MAP_KEY = 2;
	
	CompanyDataProcessor dataProcessor;
	HashMap<Integer, List<Company>> allCompanyData;
	String[] inputData;
	
	@BeforeClass
	public static void initFileName() {
		ResourceBundle testResource = ResourceBundle.getBundle("resources/sharecompare-testing");
		INPUT_FILE_PATH = testResource.getString("input.file.path");
	}
	
	@Before
	public void initTestData() {
		dataProcessor = new CompanyDataProcessor();
		allCompanyData = new HashMap<Integer, List<Company>>();
		inputData = new String[10];
		
		inputData[0] = "Year,Month,A";
		inputData[1] = "1990,Jan,1xx";
		inputData[2] = "1990,Feb,-1";
		inputData[3] = "1990,Apr,1,1,1,12,21";
		inputData[4] = "1990,Apr,1";
		inputData[5] = "1990,Apr,1\"";
		inputData[6] = "\"1990,Mar,1";
		
		ArrayList<Company> companyData = new ArrayList<Company>();
		companyData.add(new Company("A","","",0F));
		allCompanyData.put(MAP_KEY, companyData);
		
		dataProcessor.setAllCompanyData(allCompanyData);
	}
	
	@Test
	public void testReadFileLoadCompDataSuccess() {
		try {
			assertTrue("File loaded successfully", dataProcessor.readFileLoadCompData(INPUT_FILE_PATH, DELIMITER));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SharesCompareException e) {
			e.printStackTrace();
		}		
	}

	@Test
	public void testReadFileLoadCompDataFailure() {
		try {
			assertTrue("Invalid delimiter. No Data processed", dataProcessor.readFileLoadCompData(INPUT_FILE_PATH, ".."));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SharesCompareException e) {
			e.printStackTrace();
		}		
	}
	
	@Test(expected = SharesCompareException.class)
	public void testReadFileLoadCompDataException() throws IOException, SharesCompareException {
		dataProcessor.readFileLoadCompData("", DELIMITER);
	}
	
	@Test
	public void testPopulateDataInvalidPrice() {
		dataProcessor.populateData(inputData[1].split(DELIMITER));
		assertTrue("Invalid price", dataProcessor.getAllCompanyData().get(MAP_KEY).size()==1);
	}

	@Test
	public void testPopulateDataNegativePrice() {
		dataProcessor.populateData(inputData[2].split(DELIMITER));
		assertTrue("Negative price", dataProcessor.getAllCompanyData().get(MAP_KEY).size()==1);
	}
	
	@Test
	public void testPopulateDataExtraData() {
		dataProcessor.populateData(inputData[3].split(DELIMITER));
		assertTrue("More data than company", dataProcessor.getAllCompanyData().get(MAP_KEY).size()==2);
	}
	@Test
	public void testPopulateDataSuccess() {
		dataProcessor.populateData(inputData[4].split(DELIMITER));
		assertTrue("Data added Successfully", dataProcessor.getAllCompanyData().get(MAP_KEY).size() == 2);
	}
	
	@Test
	public void testPopulateDataSuccess1() {
		dataProcessor.populateData(inputData[5].split(DELIMITER));
		assertTrue("Data added Successfully for Price = 1\"", dataProcessor.getAllCompanyData().get(MAP_KEY).size() == 2);
	}
	
	@Test(expected = SharesCompareException.class)
	public void testPrintOutputResult() throws SharesCompareException {
		allCompanyData.clear();
		allCompanyData.put(MAP_KEY, null);
		dataProcessor.printOutputResult();
	}

}
