/**
 * 
 */
package group.acensi.solutions.dbanonymiser.impl;

import org.apache.commons.lang3.StringUtils;

import group.acensi.solutions.dbanonymiser.DataAnonymiser;

/**
 * @author Nadeem
 *
 */
public class PhoneNumberAnonymiser extends DataAnonymiser<String> {
	
	private int prefixLength=5;
	private static final char ANON = '0';

	@Override
	protected String doAnonymize(String source) {
		
		//append the BIN to the final result
		StringBuilder builder = new StringBuilder(source.length());
		for(int i=0; i<source.length(); i++) {
			char c = source.charAt(i);
			
			if (i<prefixLength) {
				builder.append(c);
				continue;
			}
			
			if (StringUtils.isNumeric(Character.toString(c))) {
				builder.append(ANON);
			}
			else {
				builder.append(c);
			}

			
		}

		return builder.toString();
	}
	
	@Override
	protected boolean isEmpty(String source) {
		return StringUtils.isEmpty(source);
	}


}
