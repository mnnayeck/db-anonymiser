/**
 * 
 */
package group.acensi.solutions.dbanonymiser.impl;

import org.apache.commons.lang3.StringUtils;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

/**
 * @author Nadeem
 *
 */
public class FullNameAnonymiser extends CompanyNameAnonymiser {
	
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
		
		else if (nameCount == 2) {
			builder.append(name.firstName()).append(spaceChar);
			builder.append(name.lastName());
		}
		
		else if (nameCount > 2) {
			builder.append(name.firstName()).append(spaceChar);
			for(int i=0; i<nameCount-2; i++) {
				builder.append(name.firstName()).append(spaceChar);
			}
			builder.append(name.lastName());
		}

		return group.acensi.solutions.dbanonymiser.StringUtils.convertToCorrectCase(source, builder);
	}

}
