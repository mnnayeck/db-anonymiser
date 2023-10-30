/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.impl;

import java.util.Map;

import com.github.javafaker.Faker;
import com.github.mnnayeck.dbanonymiser.service.DataAnonymiserService;

/**
 * @author Nadeem
 *
 */
public class StreetNameAnonymiser extends DataAnonymiserService<String> {
	
	private Faker faker = new Faker();

	@Override
	protected String doAnonymize(String source, Map<String, Object> anonymisationConfig) {
		return faker.address().streetName();

	}
}
