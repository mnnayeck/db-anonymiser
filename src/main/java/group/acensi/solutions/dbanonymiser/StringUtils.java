/**
 * 
 */
package group.acensi.solutions.dbanonymiser;

/**
 * @author Nadeem
 *
 */
public final class StringUtils {
	
	public static boolean isAllUpperCase(String s) {
	    for(char c : s.toCharArray()) {
	       if(Character.isLetter(c) && Character.isLowerCase(c)) {
	           return false;
	        }
	    }
	    return true;
	}
	
	/**
	 * @param source
	 * @param builder
	 * @return
	 */
	public static String convertToCorrectCase(String source, StringBuilder builder) {
		String anonymized =  builder.toString().trim();
		if (!org.apache.commons.lang3.StringUtils.isMixedCase(source)) {
			anonymized = org.apache.commons.lang3.StringUtils.upperCase(anonymized);
		}
		return anonymized;
	}

}
