/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mnnayeck.dbanonymiser.impl.EmailAnonymiser;

/**
 * @author Nadeem
 *
 */
public class EmailAnonymiserTests {
	
	private static Logger logger = LoggerFactory.getLogger(EmailAnonymiserTests.class);
	
	private EmailAnonymiser anonymizer = new EmailAnonymiser();
	
	
	@Test
	public void testAnonymizerEmail1() throws Exception {
		String name = "nadeem.m.nayeck@gmail.com";
		String result = anonymizer.anonymize(name, null);
		assertConditions(name, result);
	}
	
	
	
	@Test
	public void testAnonymizerEmail2() throws Exception {
		String name = "mohammad.nayeck@icps.mu";
		String result = anonymizer.anonymize(name, null);
		assertConditions(name, result);
	}
	
	@Test
	public void testAnonymizerEmail3() throws Exception {
		String name = "tamas.varga@isupplier.fr";
		String result = anonymizer.anonymize(name, null);
		assertConditions(name, result);
	}
	
	private void assertConditions(String name, String result) {
		logger.info("Before: {}, After: {}", name, result);
		Assert.assertNotEquals(name, result);
	}

}
