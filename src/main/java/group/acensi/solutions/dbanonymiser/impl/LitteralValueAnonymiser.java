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
public class LitteralValueAnonymiser extends DataAnonymiser<String> {
	

	@Override
	protected String doAnonymize(String source) {
		return null;
	}

	@Override
	protected boolean isEmpty(String source) {
		return StringUtils.isEmpty(source);
	}

}
