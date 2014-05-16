package junit.com.google;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CompanyDataProcessorTest.class, HelperTest.class,
		LoadExecuteFileTest.class })
public class AllTests {

}
