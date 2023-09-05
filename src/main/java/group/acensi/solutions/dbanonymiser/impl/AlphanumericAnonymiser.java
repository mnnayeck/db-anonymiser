/**
 * 
 */
package group.acensi.solutions.dbanonymiser.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import group.acensi.solutions.dbanonymiser.service.DataAnonymiserService;

/**
 * @author Nadeem
 *
 */
public class AlphanumericAnonymiser extends DataAnonymiserService<String> {
	
	private static final String SPACE_CHAR = " ";

	@Override
	protected String doAnonymize(String source) {
		
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
		if (StringUtils.isAlpha(source)) {
			result = RandomStringUtils.randomAlphabetic(source.length());
		} else if (StringUtils.isNumeric(source)) {
			result =  RandomStringUtils.randomNumeric(source.length());
		} else if (StringUtils.isAlphanumeric(source)) {
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
