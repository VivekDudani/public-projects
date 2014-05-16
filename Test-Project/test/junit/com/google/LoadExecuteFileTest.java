package junit.com.google;

import java.io.IOException;

import org.junit.Test;

import com.google.sharecompare.exceptions.SharesCompareException;
import com.google.sharecompare.executor.LoadExecuteFile;

public class LoadExecuteFileTest {

	@Test(expected=SharesCompareException.class)
	public void testMain() throws SharesCompareException, IOException {
		LoadExecuteFile.main(new String[0]);
	}

	@Test(expected=SharesCompareException.class)
	public void testMain1() throws SharesCompareException, IOException {
		String[] input = {"/host/work/incorrect/file.doc"};
		LoadExecuteFile.main(input);
	}
}
