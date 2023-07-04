/**
 * 
 */
package group.acensi.solutions.dbanonymiser.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import group.acensi.solutions.dbanonymiser.impl.CompanyNameAnonymiser;

/**
 * @author Nadeem
 *
 */
public class CompanyAnonymiserTests {
	
	private static Logger logger = LoggerFactory.getLogger(CompanyAnonymiserTests.class);
	
	private CompanyNameAnonymiser nameAnonymizer = new CompanyNameAnonymiser();
	
	
	@Test
	public void testAnonymizerCompany1() throws Exception {
		String name = "GUNNOO";
		String result = nameAnonymizer.anonymize(name);
		assertConditions(name, result);
	}
	
	
	@Test
	public void testAnonymizerCompany2() throws Exception {
		String name = "INFOR FRANCE SAS";
		String result = nameAnonymizer.anonymize(name);
		assertConditions(name, result);
	}
	
	
	private void assertConditions(String name, String result) {
		logger.info("Before: {}, After: {}", name, result);
		Assert.assertNotEquals(name, result);
	}

}
