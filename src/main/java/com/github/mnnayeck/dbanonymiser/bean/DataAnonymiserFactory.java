/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.bean;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.github.mnnayeck.dbanonymiser.impl.AlphanumericAnonymiser;
import com.github.mnnayeck.dbanonymiser.impl.Anonymisers;
import com.github.mnnayeck.dbanonymiser.impl.CityNameAnonymser;
import com.github.mnnayeck.dbanonymiser.impl.CompanyNameAnonymiser;
import com.github.mnnayeck.dbanonymiser.impl.CreditCardAnonymiser;
import com.github.mnnayeck.dbanonymiser.impl.DateAnonymiser;
import com.github.mnnayeck.dbanonymiser.impl.EmailAnonymiser;
import com.github.mnnayeck.dbanonymiser.impl.FirstNameAnonymiser;
import com.github.mnnayeck.dbanonymiser.impl.FullNameAnonymiser;
import com.github.mnnayeck.dbanonymiser.impl.LastNameAnonymiser;
import com.github.mnnayeck.dbanonymiser.impl.LitteralValueAnonymiser;
import com.github.mnnayeck.dbanonymiser.impl.PhoneNumberAnonymiser;
import com.github.mnnayeck.dbanonymiser.impl.PostCodeAnonymiser;
import com.github.mnnayeck.dbanonymiser.impl.SimpleNameAnonymiser;
import com.github.mnnayeck.dbanonymiser.impl.StreetNameAnonymiser;
import com.github.mnnayeck.dbanonymiser.service.DataAnonymiserService;

/**
 * 
 */
public abstract class DataAnonymiserFactory {
	
	private static final CompanyNameAnonymiser COMPANY_NAME_ANONYMIZER = new CompanyNameAnonymiser();
	private static final CreditCardAnonymiser CREDIT_CARD_ANONYMIZER = new CreditCardAnonymiser();
	private static final DateAnonymiser DATE_ANONYMIZER = new DateAnonymiser();
	private static final EmailAnonymiser EMAIL_ANONYMIZER= new EmailAnonymiser();
	private static final FirstNameAnonymiser FIRST_NAME_ANONYMIZER = new FirstNameAnonymiser();
	private static final FullNameAnonymiser FULL_NAME_ANONYMIZER = new FullNameAnonymiser();
	private static final LastNameAnonymiser LAST_NAME_ANONYMIZER = new LastNameAnonymiser();
	private static final PhoneNumberAnonymiser PHONE_NUMBER_ANONYMIZER = new PhoneNumberAnonymiser();
	private static final SimpleNameAnonymiser SIMPLE_NAME_ANONYMIZER = new SimpleNameAnonymiser();
	private static final CityNameAnonymser CITY_NAME_ANONYMIZER = new CityNameAnonymser();
	private static final PostCodeAnonymiser POST_CODE_ANONYMIZER = new PostCodeAnonymiser();
	private static final StreetNameAnonymiser STREET_NAME_ANONYMIZER = new StreetNameAnonymiser();
	private static final AlphanumericAnonymiser ALPHANUMERIC_ANONYMIZER = new AlphanumericAnonymiser();
	private static final LitteralValueAnonymiser LITTERAL_VALUE_ANONYMISER = new LitteralValueAnonymiser();
	
//	private static final String SPLITTER = ":";
	
	private DataAnonymiserFactory() {}

	/**
	 * @param anonymizer
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DataAnonymiserService<Serializable> create(String anonymizer) {
		
//		if (StringUtils.contains(anonymizer, SPLITTER)) {
//			String[] splits = StringUtils.split(anonymizer, SPLITTER);
//			
//		}
		
		DataAnonymiserService dataAnonymiserService = null;
		
		switch (anonymizer) {
		case Anonymisers.COMPANY_NAME:
			dataAnonymiserService = COMPANY_NAME_ANONYMIZER;
			break;
		case Anonymisers.CREDIT_CARD:
			dataAnonymiserService = CREDIT_CARD_ANONYMIZER;
			break;
		case Anonymisers.DATE:
			dataAnonymiserService = DATE_ANONYMIZER;
			break;
		case Anonymisers.EMAIL:
			dataAnonymiserService = EMAIL_ANONYMIZER;
			break;
		case Anonymisers.FIRST_NAME:
			dataAnonymiserService = FIRST_NAME_ANONYMIZER;
			break;
		case Anonymisers.FULL_NAME:
			dataAnonymiserService = FULL_NAME_ANONYMIZER;
			break;
		case Anonymisers.LAST_NAME:
			dataAnonymiserService = LAST_NAME_ANONYMIZER;
			break;
		case Anonymisers.PHONE_NUMBER:
			dataAnonymiserService = PHONE_NUMBER_ANONYMIZER;
			break;
		case Anonymisers.SIMPLE_NAME:
			dataAnonymiserService = SIMPLE_NAME_ANONYMIZER;
			break;
		case Anonymisers.CITY:
			dataAnonymiserService = CITY_NAME_ANONYMIZER;
			break;
		case Anonymisers.POST_CODE:
			dataAnonymiserService = POST_CODE_ANONYMIZER;
			break;
		case Anonymisers.STREET_NAME:
			dataAnonymiserService = STREET_NAME_ANONYMIZER;
			break;
		case Anonymisers.ALPHANUM:
			dataAnonymiserService = ALPHANUMERIC_ANONYMIZER;
			break;
		case Anonymisers.LITTERAL:
			dataAnonymiserService = LITTERAL_VALUE_ANONYMISER;
			break;
		default:
			break;
		}
		return dataAnonymiserService;
	}

}
