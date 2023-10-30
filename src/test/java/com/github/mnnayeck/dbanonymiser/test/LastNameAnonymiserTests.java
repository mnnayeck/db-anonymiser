/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mnnayeck.dbanonymiser.impl.LastNameAnonymiser;

/**
 * @author Nadeem
 *
 */
public class LastNameAnonymiserTests {
	
	private static Logger logger = LoggerFactory.getLogger(LastNameAnonymiserTests.class);
	
	private LastNameAnonymiser nameAnonymizer = new LastNameAnonymiser();
	
	
	@Test
	public void testAnonymizerName1() throws Exception {
		String name = "EMRITH-NAYECK";
		String result = nameAnonymizer.anonymize(name, null);
		assertConditions(name, result);
	}
	

	
	private void assertConditions(String name, String result) {
		logger.info("Before: {}, After: {}", name, result);
		Assert.assertNotEquals(name, result);
		Assert.assertEquals(StringUtils.countMatches(StringUtils.trim(name), ' '), StringUtils.countMatches(StringUtils.trim(result), ' '));
		Assert.assertEquals(com.github.mnnayeck.dbanonymiser.util.StringUtils.isAllUpperCase(name), 
				com.github.mnnayeck.dbanonymiser.util.StringUtils.isAllUpperCase(result));
	}

}
