/**
 * 
 */
package group.acensi.solutions.dbanonymiser;

import java.io.IOException;

import group.acensi.solutions.dbanonymiser.configuration.Anonymisation;

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
