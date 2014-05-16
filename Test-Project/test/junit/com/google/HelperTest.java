package junit.com.google;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.sharecompare.utility.Helper;

public class HelperTest {

	@Test
	public void testCheckNSplit1() {
		assertEquals("Quotes before text", "1990", Helper.checkNSplit("\"1990", Helper.DOUBLE_QUOTES));
	}

	@Test
	public void testCheckNSplit2() {
		assertEquals("Quotes after text", "1990", Helper.checkNSplit("1990\"", Helper.DOUBLE_QUOTES));
	}
	
	@Test
	public void testCheckNSplit3() {
		assertEquals("Quotes before text", "19", Helper.checkNSplit("19\"90", Helper.DOUBLE_QUOTES));
	}
	
	@Test
	public void testCheckNSplit4() {
		assertEquals("Tilda before text", "1990", Helper.checkNSplit("~1990", "~"));
	}
	
	@Test
	public void testCheckInputFileFormat() {
		assertTrue("Valid input file format", Helper.checkInputFileFormat("/work/project/Input.csv"));
	}
	
	@Test
	public void testCheckInputFileFormat1() {
		assertFalse("Invalid input file format", Helper.checkInputFileFormat("/work/project/Input"));
	}
	
	@Test
	public void testCheckInputFileFormat2() {
		assertFalse("Invalid input file format", Helper.checkInputFileFormat("/work/project/Input.xls"));
	}
	
	@Test
	public void testCheckInputFileFormat3() {
		assertTrue("Invalid input file format", Helper.checkInputFileFormat("/work/project.new/Input.csv"));
	}
	
}
