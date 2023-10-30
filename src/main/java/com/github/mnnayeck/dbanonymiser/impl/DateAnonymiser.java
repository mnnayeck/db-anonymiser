/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.github.javafaker.Faker;
import com.github.mnnayeck.dbanonymiser.service.DataAnonymiserService;

/**
 * @author Nadeem
 *
 */
public class DateAnonymiser extends DataAnonymiserService<Date> {
	
	private Faker faker = new Faker();
	
	@Override
	protected Date doAnonymize(Date source, Map<String, Object> anonymisationConfig) {
		Date anonymizedDate = faker.date().birthday();
		Calendar cal = Calendar.getInstance();
		cal.setTime(source);
		
		Calendar calAnonymized = Calendar.getInstance();
		calAnonymized.setTime(anonymizedDate);
		calAnonymized.set(Calendar.YEAR, cal.get(Calendar.YEAR));

		return calAnonymized.getTime();
	}


}
