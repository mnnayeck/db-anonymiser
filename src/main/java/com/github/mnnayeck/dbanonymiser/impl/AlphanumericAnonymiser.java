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
public class AlphanumericAnonymiser extends DataAnonymiserService<String> {
	
	private static final String SPACE_CHAR = " ";

	@Override
	protected String doAnonymize(String source, Map<String, Object> anonymisationConfig) {
		
		StringBuilder builder = new StringBuilder(source.length());
		
		for(String section: StringUtils.split(source, SPACE_CHAR)) {
			builder.append(extracted(section)).append(SPACE_CHAR);
		}
		return builder.toString().trim();

	}

	/**
	 * @param source
	 * @return
	 */
	private String extracted(String source) {
		String result = null;
		if (StringUtils.isAlpha(StringUtils.remove(source, " "))) {
			result = RandomStringUtils.randomAlphabetic(source.length());
		} else if (StringUtils.isNumeric(StringUtils.remove(source, " "))) {
			result =  RandomStringUtils.randomNumeric(source.length());
		} else if (StringUtils.isAlphanumeric(StringUtils.remove(source, " "))) {
			result =  RandomStringUtils.randomAlphanumeric(source.length());
		} else {
			result =  RandomStringUtils.randomAlphanumeric(source.length());
		}
		if (StringUtils.isAllUpperCase(source)) {
			result = result.toUpperCase();
		}
		return result;
	}
}
