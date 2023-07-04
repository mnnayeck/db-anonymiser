/**
 * 
 */
package group.acensi.solutions.dbanonymiser.impl;

import org.apache.commons.lang3.StringUtils;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import group.acensi.solutions.dbanonymiser.DataAnonymiser;

/**
 * @author Nadeem
 *
 */
public class LastNameAnonymiser extends DataAnonymiser<String> {
	
	private Faker faker = new Faker();
	private char spaceChar = ' ';

	@Override
	protected String doAnonymize(String source) {
		int nameCount = StringUtils.countMatches(StringUtils.trim(source), ' ');
		nameCount++;
		StringBuilder builder = new StringBuilder(100);
		Name name = faker.name();
		if (nameCount == 1) {
			builder.append(name.lastName());
		}	
		else if (nameCount > 1) {
			for(int i=0; i<nameCount-2; i++) {
				builder.append(name.lastName()).append(spaceChar);
			}
		}

		return group.acensi.solutions.dbanonymiser.StringUtils.convertToCorrectCase(source, builder);

	}

	@Override
	protected boolean isEmpty(String source) {
		return StringUtils.isEmpty(source);
	}

}
