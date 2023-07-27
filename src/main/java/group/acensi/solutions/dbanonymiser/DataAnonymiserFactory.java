/**
 * 
 */
package group.acensi.solutions.dbanonymiser;

import java.io.Serializable;

import group.acensi.solutions.dbanonymiser.impl.AlphanumericAnonymiser;
import group.acensi.solutions.dbanonymiser.impl.Anonymisers;
import group.acensi.solutions.dbanonymiser.impl.CityNameAnonymser;
import group.acensi.solutions.dbanonymiser.impl.CompanyNameAnonymiser;
import group.acensi.solutions.dbanonymiser.impl.CreditCardAnonymiser;
import group.acensi.solutions.dbanonymiser.impl.DateAnonymiser;
import group.acensi.solutions.dbanonymiser.impl.EmailAnonymiser;
import group.acensi.solutions.dbanonymiser.impl.FirstNameAnonymiser;
import group.acensi.solutions.dbanonymiser.impl.FullNameAnonymiser;
import group.acensi.solutions.dbanonymiser.impl.LastNameAnonymiser;
import group.acensi.solutions.dbanonymiser.impl.PhoneNumberAnonymiser;
import group.acensi.solutions.dbanonymiser.impl.PostCodeAnonymiser;
import group.acensi.solutions.dbanonymiser.impl.SimpleNameAnonymiser;
import group.acensi.solutions.dbanonymiser.impl.StreetNameAnonymiser;

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
	
	private DataAnonymiserFactory() {}

	/**
	 * @param anonymizer
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DataAnonymiser<Serializable> create(String anonymizer) {
		
		
		DataAnonymiser dataAnonymiser = null;
		
		switch (anonymizer) {
		case Anonymisers.COMPANY_NAME:
			dataAnonymiser = COMPANY_NAME_ANONYMIZER;
			break;
		case Anonymisers.CREDIT_CARD:
			dataAnonymiser = CREDIT_CARD_ANONYMIZER;
			break;
		case Anonymisers.DATE:
			dataAnonymiser = DATE_ANONYMIZER;
			break;
		case Anonymisers.EMAIL:
			dataAnonymiser = EMAIL_ANONYMIZER;
			break;
		case Anonymisers.FIRST_NAME:
			dataAnonymiser = FIRST_NAME_ANONYMIZER;
			break;
		case Anonymisers.FULL_NAME:
			dataAnonymiser = FULL_NAME_ANONYMIZER;
			break;
		case Anonymisers.LAST_NAME:
			dataAnonymiser = LAST_NAME_ANONYMIZER;
			break;
		case Anonymisers.PHONE_NUMBER:
			dataAnonymiser = PHONE_NUMBER_ANONYMIZER;
			break;
		case Anonymisers.SIMPLE_NAME:
			dataAnonymiser = SIMPLE_NAME_ANONYMIZER;
			break;
		case Anonymisers.CITY:
			dataAnonymiser = CITY_NAME_ANONYMIZER;
			break;
		case Anonymisers.POST_CODE:
			dataAnonymiser = POST_CODE_ANONYMIZER;
			break;
		case Anonymisers.STREET_NAME:
			dataAnonymiser = STREET_NAME_ANONYMIZER;
			break;
		case Anonymisers.ALPHANUM:
			dataAnonymiser = ALPHANUMERIC_ANONYMIZER;
			break;
		default:
			break;
		}
		return dataAnonymiser;
	}

}
