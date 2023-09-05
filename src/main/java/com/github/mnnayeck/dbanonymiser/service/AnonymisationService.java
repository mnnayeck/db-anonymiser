/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.service;

import java.io.IOException;

import com.github.mnnayeck.dbanonymiser.bean.Anonymisation;

/**
 * 
 */
public interface AnonymisationService {

	/**
	 * @param anonymisation
	 */
	public void anonymize(Anonymisation anonymisation);

	/**
	 * @throws IOException 
	 * 
	 */
	public void updateDb() throws IOException;

}
