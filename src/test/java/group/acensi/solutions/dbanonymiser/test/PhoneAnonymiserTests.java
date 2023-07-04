/**
 * 
 */
package group.acensi.solutions.dbanonymiser.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import group.acensi.solutions.dbanonymiser.DataAnonymiser;
import group.acensi.solutions.dbanonymiser.impl.PhoneNumberAnonymiser;

/**
 * @author Nadeem
 *
 */
public class PhoneAnonymiserTests {
	
	private static Logger logger = LoggerFactory.getLogger(PhoneAnonymiserTests.class);
	
	private DataAnonymiser<String> phoneAnonymizer = new PhoneNumberAnonymiser();
	
	
	@Test
	public void testAnonymizerName1() throws Exception {
		String phone = "+23057584228";
		String result = phoneAnonymizer.anonymize(phone);
		assertConditions(phone, result);
	}
	
	@Test
	public void testAnonymizerName2() throws Exception {
		String phone = "+23057584228";
		String result = phoneAnonymizer.anonymize(phone);
		Assert.assertEquals("+23050000000", result);
	}
	
	@Test
	public void testAnonymizerName3() throws Exception {
		String phone = "23057584228";
		String result = phoneAnonymizer.anonymize(phone);
		Assert.assertEquals("23057000000", result);
	}
	
	@Test
	public void testAnonymizerName4() throws Exception {
		String phone = "230-57584228";
		String result = phoneAnonymizer.anonymize(phone);
		Assert.assertEquals("230-50000000", result);
	}
	

	
	private void assertConditions(String name, String result) {
		logger.info("Before: {}, After: {}", name, result);
		Assert.assertNotEquals(name, result);
		Assert.assertEquals(StringUtils.countMatches(StringUtils.trim(name), '+'), StringUtils.countMatches(StringUtils.trim(result), '+'));
	}

}
