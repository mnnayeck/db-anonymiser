/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.impl;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.mnnayeck.dbanonymiser.service.DataAnonymiserService;

/**
 * @author Nadeem
 *
 */
public class CreditCardAnonymiser extends DataAnonymiserService<String> {
	
	private int binLength=8;

	@Override
	protected String doAnonymize(String source, Map<String, Object> anonymisationConfig) {
		
		//append the BIN to the final result
		StringBuilder builder = new StringBuilder(source.length());
		builder.append(source.substring(0, this.binLength));

		// anonymize middle portion and append to final result
		int anonymizedPortionlength = source.length() - this.binLength;
		String anonymizedPortion = RandomStringUtils.randomNumeric(anonymizedPortionlength, anonymizedPortionlength+1);
		builder.append(anonymizedPortion);

		return builder.toString();
	}
	
	@Override
	protected boolean isEmpty(String source) {
		return StringUtils.isEmpty(source);
	}

	/**
	 * @return the binLength
	 */
	public int getBinLength() {
		return binLength;
	}

	/**
	 * @param binLength the binLength to set
	 */
	public void setBinLength(int binLength) {
		this.binLength = binLength;
	}

}
