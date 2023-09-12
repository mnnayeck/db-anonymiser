/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mnnayeck.dbanonymiser.impl.FullNameAnonymiser;

/**
 * @author Nadeem
 *
 */
public class NameAnonymiserTests {
	
	private static Logger logger = LoggerFactory.getLogger(NameAnonymiserTests.class);
	
	private FullNameAnonymiser nameAnonymizer = new FullNameAnonymiser();
	
	
	@Test
	public void testAnonymizerName1() throws Exception {
		String name = "GUNNOO";
		String result = nameAnonymizer.anonymize(name);
		logger.info("Before: {}, After: {}", name, result);
		Assert.assertNotEquals(name, result);
		Assert.assertEquals(StringUtils.isAllUpperCase(name), StringUtils.isAllUpperCase(result));
	}
	
	@Test
	public void testAnonymizerName2() throws Exception {
		String name = "Gunnoo";
		String result = nameAnonymizer.anonymize(name);
		assertConditions(name, result);
	}
	
	@Test
	public void testAnonymizerName3() throws Exception {
		String name = "TEST TEK TEST MICHEL";
		String result = nameAnonymizer.anonymize(name);
		assertConditions(name, result);
	}
	
	@Test
	public void testAnonymizerName4() throws Exception {
		String name = "WS CARDONE VALIDITY";
		String result = nameAnonymizer.anonymize(name);
		assertConditions(name, result);
	}
	
	@Test
	public void testAnonymizerName5() throws Exception {
		String name = "WS CARDONE";
		String result = nameAnonymizer.anonymize(name);
		assertConditions(name, result);
	}
	
	@Test
	public void testAnonymizerName6() throws Exception {
		String name = "Will Smith";
		String namelowered = "will smith";
		String result = nameAnonymizer.anonymize(name);
		String resultLowered = nameAnonymizer.anonymize(namelowered);
		Assert.assertTrue(StringUtils.equalsIgnoreCase(result, resultLowered));
		assertConditions(name, result);
		assertConditions(namelowered, resultLowered);
	}
	
	private void assertConditions(String name, String result) {
		logger.info("Before: {}, After: {}", name, result);
		Assert.assertNotEquals(name, result);
		Assert.assertEquals(StringUtils.countMatches(StringUtils.trim(name), ' '), StringUtils.countMatches(StringUtils.trim(result), ' '));
		Assert.assertEquals(StringUtils.isAllUpperCase(name), StringUtils.isAllUpperCase(result));
	}

}
