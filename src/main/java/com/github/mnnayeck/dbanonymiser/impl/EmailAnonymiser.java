/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.impl;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.github.mnnayeck.dbanonymiser.service.DataAnonymiserService;

/**
 * @author Nadeem
 *
 */
public class EmailAnonymiser extends DataAnonymiserService<String> {
	
	private FakeValuesService fakeValuesService = new FakeValuesService(Locale.ENGLISH, new RandomService());
	private static final String ALPHA_REGEX = "?????";
	private static final String NUMERIC_REGEX = "###";
	private static final String DNS = "@doesnotexist.com";
	

	@Override
	protected String doAnonymize(String source) {
		String anonymised = fakeValuesService.bothify(ALPHA_REGEX + NUMERIC_REGEX + DNS);
		if (StringUtils.isAllLowerCase(source)) {
			anonymised = anonymised.toLowerCase();
		}
		return anonymised;
	}

	@Override
	protected boolean isEmpty(String source) {
		return StringUtils.isEmpty(source);
	}
}
