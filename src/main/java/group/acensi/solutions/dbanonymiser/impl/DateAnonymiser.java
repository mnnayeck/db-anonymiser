/**
 * 
 */
package group.acensi.solutions.dbanonymiser.impl;

import java.util.Calendar;
import java.util.Date;

import com.github.javafaker.Faker;

import group.acensi.solutions.dbanonymiser.service.DataAnonymiserService;

/**
 * @author Nadeem
 *
 */
public class DateAnonymiser extends DataAnonymiserService<Date> {
	
	private Faker faker = new Faker();
	
	@Override
	protected Date doAnonymize(Date source) {
		Date anonymizedDate = faker.date().birthday();
		Calendar cal = Calendar.getInstance();
		cal.setTime(source);
		
		Calendar calAnonymized = Calendar.getInstance();
		calAnonymized.setTime(anonymizedDate);
		calAnonymized.set(Calendar.YEAR, cal.get(Calendar.YEAR));

		return calAnonymized.getTime();
	}


}
