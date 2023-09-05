/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.impl;

import org.apache.commons.lang3.StringUtils;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.mnnayeck.dbanonymiser.service.DataAnonymiserService;

/**
 * @author Nadeem
 *
 */
public class FirstNameAnonymiser extends DataAnonymiserService<String> {
	
	private Faker faker = new Faker();
	private char spaceChar = ' ';

	@Override
	protected String doAnonymize(String source) {
		int nameCount = StringUtils.countMatches(StringUtils.trim(source), ' ');
		nameCount++;
		StringBuilder builder = new StringBuilder(100);
		Name name = faker.name();
		if (nameCount == 1) {
			builder.append(name.firstName());
		}	
		else if (nameCount > 1) {
			for(int i=0; i<nameCount-2; i++) {
				builder.append(name.firstName()).append(spaceChar);
			}
		}

		return com.github.mnnayeck.dbanonymiser.util.StringUtils.convertToCorrectCase(source, builder);

	}
	
	@Override
	protected boolean isEmpty(String source) {
		return StringUtils.isEmpty(source);
	}


}
