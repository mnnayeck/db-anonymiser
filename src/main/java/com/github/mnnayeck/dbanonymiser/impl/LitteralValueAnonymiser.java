/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.mnnayeck.dbanonymiser.service.DataAnonymiserService;

/**
 * @author Nadeem
 *
 */
public class LitteralValueAnonymiser extends DataAnonymiserService<String> {
	

	public static final String LITTERAL_VALUE = "LITTERAL_VALUE";

	@Override
	protected String doAnonymize(String source, Map<String, Object> anonymisationConfig) {
		return (String) anonymisationConfig.get(LITTERAL_VALUE);
	}

	@Override
	protected boolean isEmpty(String source) {
		return StringUtils.isEmpty(source);
	}

}
