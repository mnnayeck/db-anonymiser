/**
 * 
 */
package group.acensi.solutions.dbanonymiser.service;

import java.io.IOException;

import group.acensi.solutions.dbanonymiser.bean.Anonymisation;

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
