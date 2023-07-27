/**
 * 
 */
package group.acensi.solutions.dbanonymiser.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import group.acensi.solutions.dbanonymiser.impl.AlphanumericAnonymiser;

/**
 * @author Nadeem
 *
 */
public class AlphanumericAnonymiserTests {
	
	private static Logger logger = LoggerFactory.getLogger(AlphanumericAnonymiserTests.class);
	
	private AlphanumericAnonymiser anonymizer = new AlphanumericAnonymiser();
	
	
	@Test
	public void testAnonymizerIBan() throws Exception {
		String iban = "FR76 3000 4001 5800 0100 5783 991";
		String result = anonymizer.anonymize(iban);
		assertConditions(iban, result);
	}

	
	private void assertConditions(String iban, String result) {
		logger.info("Before: {}, After: {}", iban, result);
		Assert.assertNotEquals(iban, result);
		Assert.assertEquals(iban.length(), result.length());
	}

}
