/**
 * 
 */
package group.acensi.solutions.dbanonymiser.impl;

import com.github.javafaker.Faker;

import group.acensi.solutions.dbanonymiser.DataAnonymiser;

/**
 * @author Nadeem
 *
 */
public class StreetNameAnonymiser extends DataAnonymiser<String> {
	
	private Faker faker = new Faker();

	@Override
	protected String doAnonymize(String source) {
		return faker.address().streetName();

	}
}
