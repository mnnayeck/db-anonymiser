/**
 * 
 */
package group.acensi.solutions.dbanonymiser.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import group.acensi.solutions.dbanonymiser.impl.CreditCardAnonymiser;

/**
 * @author Nadeem
 *
 */
public class CreditCardAnonymiserTests {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CreditCardAnonymiserTests.class);
	
	private CreditCardAnonymiser creditCardAnonymiser = new CreditCardAnonymiser();
	
	@Test
	public void testAnonymizerAmex1() throws Exception {
		String creditCardNumber = "378282246310005";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerAmex2() throws Exception {
		String creditCardNumber = "371449635398431";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerAmexCorporate() throws Exception {
		String creditCardNumber = "378734493671000";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerAustralianBankCard() throws Exception {
		String creditCardNumber = "5610591081018250";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerDinersClub() throws Exception {
		String creditCardNumber = "30569309025904";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerDinersClub2() throws Exception {
		String creditCardNumber = "38520000023237";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerDiscover1() throws Exception {
		String creditCardNumber = "6011111111111117";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerDiscover2() throws Exception {
		String creditCardNumber = "6011000990139424";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerJcb1() throws Exception {
		String creditCardNumber = "3530111333300000";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerJcb2() throws Exception {
		String creditCardNumber = "3566002020360505";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerMastercard1() throws Exception {
		String creditCardNumber = "5555555555554444";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerMastercard2() throws Exception {
		String creditCardNumber = "5105105105105100";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerVisa1() throws Exception {
		String creditCardNumber = "4111111111111111";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerVisa2() throws Exception {
		String creditCardNumber = "4012888888881881";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerVisa3() throws Exception {
		String creditCardNumber = "4222222222222";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerPowercard1() throws Exception {
		String creditCardNumber = "4227480100019400";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	
	@Test
	public void testAnonymizerPowercard2() throws Exception {
		String creditCardNumber = "4227480100018230";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}
	
	@Test
	public void testAnonymizerPowercard3() throws Exception {
		String creditCardNumber = "4227480100016230";
		String result = creditCardAnonymiser.anonymize(creditCardNumber);
		assertConditions(creditCardNumber, result);
	}

	/**
	 * @param creditCardNumber
	 * @param result
	 */
	private void assertConditions(String creditCardNumber, String result) {
		LOGGER.info("Before: {}, After: {}", creditCardNumber, result);
		Assert.assertNotEquals(creditCardNumber, result);
		Assert.assertEquals(creditCardNumber.length(), result.length());
		Assert.assertTrue(StringUtils.startsWith(result, StringUtils.truncate(creditCardNumber, this.creditCardAnonymiser.getBinLength())));
	}

}
