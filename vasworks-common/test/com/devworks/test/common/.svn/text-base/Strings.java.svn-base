package com.devworks.test.common;

import org.testng.annotations.Test;

import com.vasworks.util.StringUtil;

/**
 * Unit testing of StringUtil class. Please add use-cases to particular methods if bugs are found.
 * 
 * @author developer
 * 
 */
public class Strings {
	@Test(groups = { "stringutil" }, testName = "Check arrayToString method")
	public void arrayToString() {
		String[] array = new String[] { "VasWorks", "Some", null, "Value", null };
		String expectedResult = "[ VasWorks, Some, null, Value, null ]";
		String result = StringUtil.arrayToString(array);
		assert result.equalsIgnoreCase(expectedResult) : "Expected result: " + expectedResult + " but got " + result;
	}

	@Test(groups = { "stringutil" }, testName = "Check randomAlphaNumericString")
	public void randomAlphaNumericString() {
		int length = 100;
		String result = StringUtil.getRandomAlphaNumericString(length);
		assert result != null : "Must not return a null value";
		assert result.length() == length : "Length of generated string should be " + length + ", is " + result.length();
		assert result.matches("^[a-zA-Z0-9]{" + length + "}$") : "Unexpected characters found: " + result;
		// assert result.matches("[0-9]") : "Expecting at least one numeric character in " + result;
	}

	@Test(groups = { "stringutil" }, testName = "Check randomAlphaString")
	public void randomAlpha() {
		int length = 100;
		String result = StringUtil.getRandomAlphaString(length);
		assert result != null : "Must not return a null value";
		assert result.length() == length : "Length of generated string should be " + length + ", is " + result.length();
		assert result.matches("^[a-zA-Z]{" + length + "}$") : "Unexpected characters found: " + result;
	}

	@Test(groups = { "stringutil" }, testName = "Check randomNumericString")
	public void randomNumericString() {
		int length = 100;
		String result = StringUtil.getRandomNumericString(length);
		assert result != null : "Must not return a null value";
		assert result.length() == length : "Length of generated string should be " + length + ", is " + result.length();
		assert result.matches("^[0-9]{" + length + "}$") : "Unexpected characters found: " + result;
	}

	@Test(groups = { "stringutil" })
	public void sanitizedJavascript() {
		String name = "test.vasworks[12].name";
		String expectedResult = "test_vasworks_12__name";
		String result = StringUtil.sanitizeForJavascript(name);
		assert expectedResult.equals(result) : "Unexpected sanitized JS name for '" + name + "' = '" + result + "'";
	}

	@Test(groups = { "stringutil" })
	public void shortenText() {
		String text = "<p>Lorem ipsum dolor sit amet, <b>consectetur <em>adipiscing</em></b> elit.</p><p>Phasellus <ul><li>ut purus</li> <li>venenatis odio </li></ul><br />adipiscing molestie.</p> <p>Nunc et sapien urna, a posuere ipsum? Nulla orci mi, feugiat sit amet mollis in, ultrices vel tortor. Mauris et quam nisi. Mauris</p>";
		String result = StringUtil.shortenText(text, 20, true, false);
		assert result.equals("Lorem ipsum dolor") : "Shortened '" + text + "' should not be '" + result + "'";
	}

	@Test(groups = { "stringutil" })
	public void humanReadable() {
		String result;
		Double [] bytes=new Double[] { 1d, 100d, 100d, 2000000d, 2000000d, 20000000d, 200000000d };
		String [] expected=new String[] { "1 B", "100 B", "100 B", "1953 K", "1953 K", "19 M", "191 M"};
		for (int i=0; i<bytes.length; i++) {
			result=StringUtil.toHumanReadableBytes(bytes[i], 1024);
			assert result.equals(expected[i]) : "" + bytes[i] + " bytes should convert to " + expected[i] + " and not " + result;
		}
	}
}
