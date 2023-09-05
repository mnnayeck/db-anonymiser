/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.impl;

import com.github.javafaker.Faker;
import com.github.mnnayeck.dbanonymiser.service.DataAnonymiserService;

/**
 * @author Nadeem
 *
 */
public class CityNameAnonymser extends DataAnonymiserService<String> {
	
	private Faker faker = new Faker();

	@Override
	protected String doAnonymize(String source) {
		return faker.address().city();

	}
}
