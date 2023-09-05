/**
 * 
 */
package group.acensi.solutions.dbanonymiser.impl;

import com.github.javafaker.Faker;

import group.acensi.solutions.dbanonymiser.service.DataAnonymiserService;

/**
 * @author Nadeem
 *
 */
public class PostCodeAnonymiser extends DataAnonymiserService<String> {
	
	private Faker faker = new Faker();

	@Override
	protected String doAnonymize(String source) {
		return faker.address().zipCode();

	}
}
